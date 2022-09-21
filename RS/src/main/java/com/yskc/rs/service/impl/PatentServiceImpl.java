package com.yskc.rs.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.google.common.base.Joiner;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.dao.PatentDao;
import com.yskc.rs.dao.project.PatentFileDao;
import com.yskc.rs.entity.PatentEntity;
import com.yskc.rs.entity.project.PatentFileEntity;
import com.yskc.rs.models.Patent.PatentModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.PatentExcel;
import com.yskc.rs.service.PatentService;
import com.yskc.rs.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PatentServiceImpl implements PatentService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PatentDao patentDao;

    @Override
    public PageModel<List<PatentModel>> queryPatent(PatentModel model) {
        Pagination pagination = new Pagination(model.getPageNo(), model.getPageSize());
        return PageUtils.build(pagination, patentDao.queryPatent(pagination, model));
    }

    @Override
    public boolean addPatent(UserInfo userInfo, PatentEntity entity) {
        Date nowDate = new Date();
        entity.setCompanyId(userInfo.getCompanyId());
        entity.setCreatorId(userInfo.getUserId());
        entity.setCreateTime(nowDate);
        entity.setLastUpdatorId(userInfo.getUserId());
        entity.setMsCreatorId(userInfo.getMsUserId());
        entity.setMsLastUpdatorId(userInfo.getMsUserId());
        entity.setLastUpdateTime(nowDate);
        if (entity.getSearchDate() != null) {
            entity.setSearchDate(DateUtil.getMonthFirstDay(entity.getSearchDate()));
        }
        return patentDao.insert(entity) > 0;
    }

    @Override
    public boolean editPatent(UserInfo userInfo, PatentEntity entity) {
        entity.setLastUpdatorId(userInfo.getUserId());
        entity.setLastUpdateTime(new Date());
        entity.setMsLastUpdatorId(userInfo.getMsUserId());
        if (entity.getSearchDate() != null) {
            entity.setSearchDate(DateUtil.getMonthFirstDay(entity.getSearchDate()));
        }
        return patentDao.updateById(entity) > 0;
    }

    @Override
    public boolean checkPatentNo(String patentNo, Integer projectId, Integer pid) {
        List<PatentEntity> patentEntities = patentDao.checkPatentNo(patentNo, projectId, pid);
        if (patentEntities != null && patentEntities.size() > 0) {
            return false;
        }
        return true;
    }

    @Override
    public boolean delPatent(PatentEntity entity) {
        //有文档类型的专利不可以删除(暂时注释掉)
//        List<SysDocumentEntity> sysDocumentEntities = sysDocumentDao.selectByPatentNo(entity.getPatentNo());
//        if (sysDocumentEntities.size() > 0) {
//            return false;
//        }
        return patentDao.deleteById(entity.getId()) > 0;
    }

    @Override
    public String importPatent(UserInfo info, List<PatentExcel> patentExcels, Integer projectId) throws OwnerException {
        if (patentExcels.size() > 0) {
            List<String> patentNoList = patentExcels.stream().filter(a -> !StringUtils.isEmpty(a.getPatentNo())).map(PatentExcel::getPatentNo).collect(Collectors.toList());
            List<String> checkPatentNo = new ArrayList<>();
            List<String> repeatPatentNo = new ArrayList<>();
            for (String patentNo : patentNoList) {
                if (!checkPatentNo.contains(patentNo)) {
                    checkPatentNo.add(patentNo);
                } else {
                    repeatPatentNo.add(patentNo);
                }
            }
            if (repeatPatentNo.size() > 0) {
                String patentNoStr = Joiner.on(",").join(repeatPatentNo);
                return "有重复的专利号:" + patentNoStr + ",请修改后重新导入!";
            }
            List<PatentEntity> patentEntityList = patentDao.queryPatentByProjectId(info.getCompanyId(), projectId);
            Map<String, PatentEntity> patentEntityMap = new HashMap<>();
            if (!patentEntityList.isEmpty()) {
                patentEntityMap = patentEntityList.stream().collect(Collectors.toMap(PatentEntity::getPatentNo, b -> b));
            }
            Date date = new Date();
            Integer userId = info.getId();
            List<PatentEntity> insertList = new ArrayList<>();
            List<PatentEntity> updateList = new ArrayList<>();
            for (int i = 0; i < patentExcels.size(); i++) {
                PatentExcel patentExcel = patentExcels.get(i);
                PatentEntity patentEntity = patentEntityMap.get(patentExcel.getPatentNo());

                if (patentEntity != null) {
                    BeanUtil.copyProperties(patentExcel, patentEntity);
                    patentEntity.setLastUpdateTime(date);
                    patentEntity.setLastUpdatorId(info.getUserId());
                    patentEntity.setMsLastUpdatorId(info.getMsUserId());
                    updateList.add(patentEntity);
                } else {
                    patentEntity = new PatentEntity();
                    BeanUtil.copyProperties(patentExcel, patentEntity);
                    patentEntity.setCompanyId(info.getCompanyId());
                    patentEntity.setProjectId(projectId);
                    patentEntity.setCreateTime(date);
                    patentEntity.setCreatorId(info.getUserId());
                    patentEntity.setMsCreatorId(info.getMsUserId());
                    patentEntity.setLastUpdateTime(date);
                    patentEntity.setLastUpdatorId(info.getUserId());
                    patentEntity.setMsLastUpdatorId(info.getMsUserId());
                    insertList.add(patentEntity);
                }
            }

            TransactionStatus transactionStatus = TransactionUtils.newTransaction();
            try {
                if (insertList.size() > 0) {
                    patentDao.insertPatentList(insertList);
                }
                if (updateList.size() > 0) {
                    patentDao.updatePatentList(updateList);
                }
                TransactionUtils.commit(transactionStatus);
            } catch (Exception ex) {
                TransactionUtils.rollback(transactionStatus);
                logger.error("importPatent", ex);
            }
        } else {
            throw new OwnerException("未获取到任何数据，请检查表格是否存在数据，上传模板是否正确。");
        }
        return "";
    }

}
