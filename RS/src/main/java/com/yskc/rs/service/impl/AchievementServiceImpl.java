package com.yskc.rs.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.FlowInstanceStatusEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.dao.project.AchievementDao;
import com.yskc.rs.dao.project.AchievementFileDao;
import com.yskc.rs.entity.project.AchievementEntity;
import com.yskc.rs.entity.project.AchievementFileEntity;
import com.yskc.rs.entity.project.AuditAchievementEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.achievement.AchievementFileModel;
import com.yskc.rs.models.achievement.AchievementModel;
import com.yskc.rs.models.achievement.QueryAchievementModel;
import com.yskc.rs.service.AchievementService;
import com.yskc.rs.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: zhangdingfu
 * @CreateTime: 2021-11-18 10:36
 * @Description: 成果业务实现层
 */
@Service
public class AchievementServiceImpl implements AchievementService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String ACHIEVEMENT_DIR = "achievement";

    @Autowired
    private AchievementDao achievementDao;
    @Autowired
    private AchievementFileDao achievementFileDao;
    @Autowired
    private RsConfig rsConfig;

    @Override
    public PageModel<List<AchievementModel>> getList(QueryAchievementModel query, Integer companyId) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getDescs()) && CollectionUtils.isEmpty(page.getAscs())) {
            page.setDescs(CollUtil.newArrayList("a.createTime", "lastUploadTime"));
        }
        return PageUtils.build(page, achievementDao.getList(page, companyId, query));
    }

    @Override
    public boolean add(AchievementModel model, UserInfo userInfo) throws OwnerException {
        checkName(model, userInfo);
        AchievementEntity entity = new AchievementEntity();
        BeanUtil.copyProperties(model, entity);
        entity.setCompanyId(userInfo.getCompanyId());
        entity.create(userInfo.getUserId(), userInfo.getMsUserId(), new Date());
        return achievementDao.insert(entity) > 0;
    }

    @Override
    public boolean edit(AchievementModel model, UserInfo userInfo) throws OwnerException {
        checkAuditModify(model.getId(), userInfo);
        checkName(model, userInfo);
        return achievementDao.updateAchievement(model, userInfo.getUserId(), userInfo.getMsUserId(), new Date()) > 0;
    }

    @Override
    public boolean del(List<Integer> ids, UserInfo info) throws OwnerException {
        if (CollectionUtils.isEmpty(ids)) {
            return true;
        }
        List<Integer> achievementIds = achievementFileDao.getAchievementIds(ids);
        if (!CollectionUtils.isEmpty(achievementIds)) {
            ids = new ArrayList<>(CollUtil.disjunction(ids, achievementIds));
            if (CollectionUtils.isEmpty(ids)) {
                throw new OwnerException("所选成果已全部存在文件，不能删除。");
            }
        }
        if (info.getUserSource() != 0) {
            List<AuditAchievementEntity> auditAchievements = achievementDao.getAuditAchievement(ids);
            if (!CollectionUtils.isEmpty(auditAchievements)) {
                List<Integer> submitAchievementIds = auditAchievements.stream()
                        .filter(a -> !FlowInstanceStatusEnum.canModify(a.getStatus()))
                        .map(AuditAchievementEntity::getAchievementId)
                        .collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(submitAchievementIds)) {
                    ids = new ArrayList<>(CollUtil.disjunction(ids, submitAchievementIds));
                    if (CollectionUtils.isEmpty(ids)) {
                        throw new OwnerException("所选成果已存在文件或已提交审核，不能删除。");
                    }
                }
            }
        }
        return achievementDao.deleteBatchIds(ids) > 0;
    }

    @Override
    public AchievementFileModel addFile(UserInfo info, AchievementFileModel achievementFile, MultipartFile file) throws OwnerException {
        Integer achievementId = achievementFile.getAchievementId();
        checkAuditModify(achievementId, info);
        String filename = file.getOriginalFilename();
        String filePath = MessageFormat.format("/{0}/{1}/{2}/{3}",
                ACHIEVEMENT_DIR, info.getCompanyId(), achievementId,
                    System.currentTimeMillis() + filename);
        File dest = new File(rsConfig.getUploadConfig().getDocPath() + filePath);
        if (!dest.getParentFile().exists()) {
            dest.mkdirs();
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction();
            file.transferTo(dest);
            achievementFile.setFilepath(filePath);
            AchievementFileEntity achievementFileEntity = new AchievementFileEntity();
            BeanUtil.copyProperties(achievementFile, achievementFileEntity);
            Integer seq = achievementFileDao.getMaxSeq(achievementId);
            if(seq==null){
                seq = 0;
            }else {
                seq++;
            }
            achievementFileEntity.setSeq(seq);
            achievementFileEntity.setCompanyId(info.getCompanyId());
            Date now = new Date();
            achievementFileEntity.create(info.getUserId(), info.getMsUserId(), now);
            achievementFileEntity.setFileName(filename);
            achievementFileDao.insert(achievementFileEntity);
            achievementFile.setId(achievementFileEntity.getId());
            achievementFile.setLastUpdateTime(now);
            achievementFile.setFileName(filename);
            // 统计文件数，上传时间
            AchievementEntity statFile = achievementFileDao.getStatFile(achievementId);
            achievementDao.updateFile(achievementId, statFile.getLastUploadTime(), statFile.getFileCnt());
            TransactionUtils.commit(transactionStatus);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException("上传成果文件失败。");
        }
        return achievementFile;
    }

    @Override
    public List<AchievementFileModel> getFiles(Integer achievementId) {
        return achievementFileDao.getFiles(achievementId);
    }

    @Override
    public boolean deleteFile(Integer achievementId, Integer id, UserInfo info) throws OwnerException {
        checkAuditModify(achievementId, info);
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction();
            achievementFileDao.deleteById(id);
            AchievementEntity statFile = achievementFileDao.getStatFile(achievementId);
            if (statFile == null) {
                achievementDao.updateFile(achievementId, null, null);
            } else {
                achievementDao.updateFile(achievementId, statFile.getLastUploadTime(), statFile.getFileCnt());
            }
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException("删除成果文件失败");
        }
    }

    @Override
    public boolean updateSeq(Integer id1,Integer id2) {
        AchievementFileModel model1 = achievementFileDao.getSeq(id1);
        AchievementFileModel model2 = achievementFileDao.getSeq(id2);
            model1.setId(id2);
        model2.setId(id1);
        List<AchievementFileModel> list = new ArrayList<>();
        list.add(model1);
        list.add(model2);
        return achievementFileDao.updateSeq(list)>0;
    }

    @Override
    public boolean updateFile(UserInfo info, AchievementFileModel achievementFile, MultipartFile file) throws OwnerException {
        Integer achievementId = achievementFile.getAchievementId();
        checkAuditModify(achievementId,info);
        String filename = null;
        String filePath = null;
        File dest = null;
        if(file!=null){
            filename = file.getOriginalFilename();
            filePath = MessageFormat.format("/{0}/{1}/{2}/{3}",
                    ACHIEVEMENT_DIR, info.getCompanyId(), achievementId,
                    System.currentTimeMillis() + filename);
            dest = new File(rsConfig.getUploadConfig().getDocPath() + filePath);
            if (!dest.getParentFile().exists()) {
                dest.mkdirs();
            }
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction();
            if(file!=null){
                file.transferTo(dest);
                achievementFile.setFilepath(filePath);
                achievementFile.setFileName(filename);
            }
            AchievementFileEntity achievementFileEntity = new AchievementFileEntity();
            BeanUtil.copyProperties(achievementFile, achievementFileEntity);
            achievementFileEntity.setCompanyId(info.getCompanyId());
            Date now = new Date();
            achievementFileEntity.create(info.getUserId(), info.getMsUserId(), now);
            achievementFileDao.updateAchievementFile(achievementFileEntity);
            achievementFile.setLastUpdateTime(now);
            AchievementEntity statFile = achievementFileDao.getStatFile(achievementId);
            achievementDao.updateFile(achievementId, statFile.getLastUploadTime(), statFile.getFileCnt());
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException("编辑成果文件失败");
        }
    }

    private void checkName(AchievementModel model, UserInfo userInfo) throws OwnerException {
        if (achievementDao.countName(model, userInfo.getCompanyId()) > 0) {
            throw new OwnerException(MessageFormat.format("已存在相同的成果名称【{0}】，请重新输入。", model.getAchievementName()));
        }
    }

    private void checkAuditModify(Integer id, UserInfo info) throws OwnerException {
        if (info.getUserSource() == 0) {
            return;
        }
        if (!FlowInstanceStatusEnum.canModify(achievementDao.getStatus(id))) {
            throw new OwnerException("操作失败，已提交审核。");
        }
    }
}
