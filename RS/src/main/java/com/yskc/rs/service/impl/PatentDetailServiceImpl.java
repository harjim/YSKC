package com.yskc.rs.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.dao.PatentApplyDao;
import com.yskc.rs.dao.PatentDetailDao;
import com.yskc.rs.dao.PatentPlanDao;
import com.yskc.rs.dao.ProjectDao;
import com.yskc.rs.dao.project.PatentFileDao;
import com.yskc.rs.entity.PatentApplyEntity;
import com.yskc.rs.entity.PatentDetailEntity;
import com.yskc.rs.entity.project.PatentFileEntity;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.models.Patent.PatentDetailModel;
import com.yskc.rs.models.Patent.QueryPatentDetialModel;
import com.yskc.rs.models.PatentPlan.PatentFileModel;
import com.yskc.rs.models.PatentPlan.RelatedProjectModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.PatentDetailExcel;
import com.yskc.rs.service.PatentDetailService;
import com.yskc.rs.utils.ToolUtils;
import com.yskc.rs.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by hck
 * on 2020/6/29 11:37
 * description:
 */
@Service
public class PatentDetailServiceImpl implements PatentDetailService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PatentDetailDao patentDetailDao;
    @Autowired
    private PatentApplyDao patentApplyDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private PatentPlanDao patentPlanDao;
    @Autowired
    private CommonService commonService;
    @Autowired
    private PatentFileDao patentFileDao;

    @Override
    public Boolean checkPatentSole(String patentNo, UserInfo userInfo) throws OwnerException {
        PatentDetailEntity patentDetailEntity = patentDetailDao.checkPatentSole(patentNo);
        if (patentDetailEntity != null) {
            throw new OwnerException("专利号已存在");
        }
        return true;
    }

    @Override
    public Boolean addPatent(PatentDetailModel model, UserInfo userInfo) throws OwnerException {
        PatentDetailEntity patentDetailEntity = patentDetailDao.checkPatentSole(model.getPatentNo());
        if (patentDetailEntity != null) {
            throw new OwnerException("专利号已存在,添加失败");
        }
        Integer year = null;
        if (model.getProjectId() != null && model.getProjectId() != 0) {
            ProjectEntity projectEntity = projectDao.selectById(model.getProjectId());
            year = projectEntity.getBeginYear();
        }
        PatentDetailEntity patent = new PatentDetailEntity();
        BeanUtils.copyProperties(model, patent);
        patent.setCompanyId(userInfo.getCompanyId());
        patent.setYear(year);
        patent.setCreatorId(userInfo.getUserId());
        patent.setLastUpdateTime(new Date());
        patent.setLastUpdatorId(userInfo.getUserId());
        patent.setMsCreatorId(userInfo.getMsUserId());
        patent.setMsLastUpdatorId(userInfo.getMsUserId());
        patent.setCreateTime(new Date());
        patent.setSource(0);
        return patentDetailDao.insert(patent) > 0;
    }

    @Override
    public Boolean updatePatent(PatentDetailModel model, UserInfo userInfo) throws OwnerException {
        PatentDetailEntity patentDetailEntity = patentDetailDao.selectById(model.getId());
        patentDetailEntity.setProjectId(model.getProjectId());
        patentDetailEntity.setApplyDateTime(model.getApplyDateTime());
        patentDetailEntity.setMainType(model.getMainType());
        patentDetailEntity.setLastUpdateTime(new Date());
        patentDetailEntity.setLastUpdatorId(userInfo.getUserId());
        patentDetailEntity.setMsLastUpdatorId(userInfo.getMsUserId());
        patentDetailEntity.setInventor(model.getInventor());
        patentDetailEntity.setClaimNum(model.getClaimNum());
        patentDetailEntity.setClaimContent(model.getClaimContent());
        patentDetailEntity.setUsedCnt(model.getUsedCnt());
        patentDetailEntity.setSpecification(model.getSpecification());
        patentDetailEntity.setSummary(model.getSummary());
        patentDetailEntity.setAuthDate(model.getAuthDate());
        return patentDetailDao.updatePatents(CollUtil.newArrayList(patentDetailEntity), userInfo.getCompanyId()) > 0;
    }

    @Override
    public PageModel<List<PatentDetailModel>> getPatentList(QueryPatentDetialModel query, UserInfo userInfo) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> descs = new ArrayList<>();
            descs.add("p.lastUpdateTime");
            descs.add("p.createTime");
            page.setDescs(descs);
        }
        return PageUtils.build(page, patentDetailDao.getPatentList(userInfo.getCompanyId(), page, query));
    }

    @Override
    public Boolean delPatent(PatentDetailModel patentDetailModel) throws OwnerException {
        PatentDetailEntity patentDetailEntity = patentDetailDao.selectById(patentDetailModel.getId());
        if (patentDetailEntity.getProjectId() != null && patentDetailEntity.getProjectId() != 0) {
            throw new OwnerException("专利已关联项目，删除失败");
        }
        PatentApplyEntity patentApplyEntity = patentApplyDao.getPojoByPatentNo(patentDetailEntity.getPatentNo());
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            if (patentApplyEntity != null) {
                patentApplyDao.deleteById(patentApplyEntity.getId());
            }
            patentDetailDao.deleteById(patentDetailModel.getId());
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            TransactionUtils.rollback(transactionStatus);
            logger.error("delPatent", ex);
            throw new OwnerException("删除失败。");
        }
        return true;
    }

    @Override
    public String importPatent(UserInfo userInfo, List<PatentDetailExcel> list) throws OwnerException {
        Date date = new Date();
        String message = "";
        if (list.size() > 0) {
            //判断要导入的数据中是否存在重复的专利号
            Map<String, PatentDetailExcel> patentNoMap = new HashMap<>();
            for (PatentDetailExcel patentDetailExcel : list) {
                if (patentNoMap.containsKey(patentDetailExcel.getPatentNo())) {
                    message = MessageFormat.format("存在重复专利号：{0}，请修改后导入", patentDetailExcel.getPatentNo());
                    throw new OwnerException(message);
                }
                patentNoMap.put(patentDetailExcel.getPatentNo(), patentDetailExcel);
                String mainType = patentDetailExcel.getMainType();
                if (!mainType.equals("发明专利") && !mainType.equals("实用新型") && !mainType.equals("外观设计")) {
                    message = MessageFormat.format("专利号：{0}的类型不存在，请从中选择：发明专利、实用新型、外观设计，例如：发明专利", patentDetailExcel.getPatentNo());
                    throw new OwnerException(message);
                }
            }
            List<PatentDetailEntity> patentDetailEntities = patentDetailDao.getListByPatentNos(userInfo.getCompanyId());
            Map<String, PatentDetailEntity> patentDetailMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(patentDetailEntities)) {
                patentDetailMap = patentDetailEntities.stream().collect(Collectors.toMap(e -> e.getPatentNo(), e -> e));
            }
            List<PatentDetailEntity> updatePatents = new ArrayList<>();
            List<PatentDetailEntity> insertPatents = new ArrayList<>();
            //查询公司专利,根据导入数据中专利号是否存在判断做更新操作还是修改操作
            for (PatentDetailExcel patentDetailExcel : list) {
                PatentDetailEntity patentDetailEntity = patentDetailMap.get(patentDetailExcel.getPatentNo());
                if (patentDetailEntity != null) {
                    //更新专利表(暂不更新专利号和专利名称)
                    patentDetailEntity.setInventor(patentDetailExcel.getInventor());
                    patentDetailEntity.setMainType(patentDetailExcel.getMainType());
                    patentDetailEntity.setApplyDateTime(patentDetailExcel.getApplyDateTime());
                    patentDetailEntity.setClaimContent(patentDetailExcel.getClaimContent());
                    patentDetailEntity.setClaimNum(patentDetailExcel.getClaimNum());
                    updatePatents.add(patentDetailEntity);
                } else {
                    //添加专利
                    patentDetailEntity = new PatentDetailEntity();
                    BeanUtils.copyProperties(patentDetailExcel, patentDetailEntity);
                    patentDetailEntity.setCreateTime(date);
                    patentDetailEntity.setCreatorId(userInfo.getUserId());
                    patentDetailEntity.setMsLastUpdatorId(userInfo.getMsUserId());
                    patentDetailEntity.setMsCreatorId(userInfo.getMsUserId());
                    patentDetailEntity.setCompanyId(userInfo.getCompanyId());
                    patentDetailEntity.setSource(0);
                    insertPatents.add(patentDetailEntity);
                }
                patentDetailEntity.setAuthDate(patentDetailExcel.getAuthDate());
                patentDetailEntity.setSpecification(patentDetailExcel.getSpecification());
                patentDetailEntity.setUsedCnt(patentDetailExcel.getUsedCnt());
                patentDetailEntity.setLastUpdatorId(userInfo.getUserId());
                patentDetailEntity.setLastUpdateTime(date);
            }
            TransactionStatus transactionStatus = TransactionUtils.newTransaction();
            try {
                if (insertPatents.size() > 0) {
                    patentDetailDao.insertPatents(insertPatents, userInfo.getCompanyId());
                }
                if (updatePatents.size() > 0) {
                    patentDetailDao.updatePatents(updatePatents, userInfo.getCompanyId());
                }
                TransactionUtils.commit(transactionStatus);
            } catch (Exception ex) {
                TransactionUtils.rollback(transactionStatus);
                logger.error("importPatent", ex);
            }
        } else {
            message = "文件上传失败,请重新上传";
            throw new OwnerException(message);
        }
        return "";
    }

    @Override
    public Boolean relatedProject(RelatedProjectModel model, UserInfo userInfo) throws OwnerException {
        Date now = new Date();
        if (!CollectionUtils.isEmpty(model.getPatentNos())) {
            return patentDetailDao.updateProjectId(model.getPatentNos(), model.getProjectId(), now, userInfo.getUserId(), userInfo.getMsUserId()) > 0;
        }
        throw new OwnerException("关联项目失败");
    }

    @Override
    public Map<String, String> getSpecification(Integer id) throws OwnerException {
        if (id == null) {
            throw new OwnerException("无法获取权利要求内容及说明书内容");
        }
        Map<String, String> result = patentDetailDao.getSpecification(id);
        if (result == null) {
            throw new OwnerException("专利不存在，请稍后重试");
        }
        return result;
    }

    @Override
    public Map<Integer, List<PatentFileModel>> getPatentFiles(String patentNo) {
        Map<Integer, List<PatentFileModel>> dataMap = new HashMap<>();
        List<PatentFileModel> files = patentFileDao.getPatentFiles(null, patentNo);
        if (!CollectionUtils.isEmpty(files)) {
            for (PatentFileModel model : files) {
                ToolUtils.putAndAdd(dataMap, model.getFileType(), model);
            }
        }
        return dataMap;
    }

    @Override
    public Integer addFile(Integer fileType, String filePath, String fileName, UserInfo userInfo, String patentNo) {
        PatentFileEntity patentFile = PatentFileEntity.build(fileName, filePath, fileType, userInfo, patentNo);
        patentFileDao.insert(patentFile);
        return patentFile.getId();
    }

    @Override
    public Boolean delPatentFile(PatentFileEntity patentFile) {
        return patentFileDao.deleteById(patentFile.getId()) > 0;
    }

    @Override
    public PatentFileEntity getPatentFile(Integer id) {
        return patentFileDao.selectById(id);
    }
}
