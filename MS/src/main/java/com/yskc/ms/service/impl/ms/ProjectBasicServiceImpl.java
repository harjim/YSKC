package com.yskc.ms.service.impl.ms;

import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.ms.dao.ms.*;
import com.yskc.ms.entity.ms.ProjectBasicEntity;
import com.yskc.ms.entity.ms.ProjectFinanceInfoEntity;
import com.yskc.ms.entity.ms.ProjectTechInfoEntity;
import com.yskc.ms.entity.ms.ProjectTimelineEntity;
import com.yskc.ms.entity.rs.models.CompanyModel;
import com.yskc.ms.models.customer.CustomerModel;
import com.yskc.ms.models.customer.QueryFollowModel;
import com.yskc.ms.models.customer.QueryTraceModel;
import com.yskc.ms.models.project.ProjectBasicModel;
import com.yskc.ms.models.project.ProjectFinanceInfoModel;
import com.yskc.ms.models.project.ProjectTechInfoModel;
import com.yskc.ms.models.project.ProjectTimelineModel;
import com.yskc.ms.service.ms.ProjectBasicService;
import com.yskc.ms.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/10/31 10:39
 * description:项目信息接口实现
 */
@Service
public class ProjectBasicServiceImpl implements ProjectBasicService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ProjectBasicDao projectBasicDao;
    @Autowired
    private ProjectTimelineDao projectTimelineDao;
    @Autowired
    private ProjectTechInfoDao projectTechInfoDao;
    @Autowired
    private ProjectFinanceInfoDao projectFinanceInfoDao;

    @Override
    public ProjectBasicModel getProjectInfo(Integer projectId) {
        ProjectBasicModel model = projectBasicDao.getInfo(projectId);
        return model;
    }

    @Override
    public ProjectTechInfoModel getTechInfo(Integer projectId) {
        ProjectTechInfoEntity techInfo = projectTechInfoDao.getInfo(projectId);
        ProjectTechInfoModel model = new ProjectTechInfoModel();
        if (techInfo != null) {
            BeanUtils.copyProperties(techInfo, model);
            List<Integer> techKeys = Arrays.asList(20, 30, 40, 100, 200, 300, 400, 500, 600, 700, 800, 900, 9999);
            List<ProjectTimelineModel> timelines = projectTimelineDao.getByType(projectId, techKeys);
            model.setModels(timelines);
        }

        return model;
    }

    @Override
    public ProjectFinanceInfoModel getFinanceInfo(Integer projectId) {
        ProjectFinanceInfoModel financeInfo = projectFinanceInfoDao.getInfo(projectId, 50);
        return financeInfo;
    }

    @Override
    public Boolean editProjectInfo(ProjectBasicModel model, Integer userId) throws OwnerException {
        Date date = new Date();
        if (model == null) {
            return true;
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            ProjectBasicEntity projectBasicEntity = projectBasicDao.getByProject(model.getProjectId());
            if (projectBasicEntity != null) {
                projectBasicEntity.setApplyStatus(model.getApplyStatus());
                projectBasicEntity.setFinanceStaff(model.getFinanceStaff());
                projectBasicEntity.setHasPayPatent(model.getHasPayPatent());
                projectBasicEntity.setIncome(model.getIncome());
                projectBasicEntity.setIncomeTax(model.getIncomeTax());
                projectBasicEntity.setIsImplemented(model.getIsImplemented());
                projectBasicEntity.setManager(model.getManager());
                projectBasicEntity.setOther(model.getOther());
                projectBasicEntity.setResearchFee(model.getResearchFee());
                projectBasicEntity.setStartTime(model.getStartTime());
                projectBasicEntity.setTaxRefiefs(model.getTaxRefiefs());
                projectBasicEntity.setTechStaff(model.getTechStaff());
                projectBasicEntity.setTotalStaff(model.getTotalStaff());
                projectBasicEntity.setLastUpdateTime(date);
                projectBasicEntity.setLastUpdatorId(userId);
                projectBasicDao.updateById(projectBasicEntity);
                List<ProjectTimelineModel> timelineModels = projectTimelineDao.getByType(model.getProjectId(), Arrays.asList(10));
                if (CollectionUtils.isEmpty(timelineModels)) {
                    throw new OwnerException("项目信息时间线数据已删除或未创建，请联系管理员");
                }
                ProjectTimelineEntity entity = new ProjectTimelineEntity();
                BeanUtils.copyProperties(timelineModels.get(0), entity);
                entity.setUpdateTime(date);
                entity.setEndTime(date);
                projectTimelineDao.updateById(entity);
            } else {
                projectBasicEntity = new ProjectBasicEntity();
                BeanUtils.copyProperties(model, projectBasicEntity);
                projectBasicEntity.setCreatorId(userId);
                projectBasicEntity.setCreateTime(date);
                projectBasicEntity.setLastUpdatorId(userId);
                projectBasicEntity.setLastUpdateTime(date);
                projectBasicDao.insert(projectBasicEntity);
                ProjectTimelineEntity timelineEntity = new ProjectTimelineEntity();
                timelineEntity.setCreateTime(date);
                timelineEntity.setUpdateTime(date);
                timelineEntity.setItemType(10);
                timelineEntity.setBeginTime(date);
                timelineEntity.setEndTime(date);
                timelineEntity.setProjectId(model.getProjectId());
                projectTimelineDao.insert(timelineEntity);
            }
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException("编辑项目基本信息失败，请联系管理员。");
        }
    }

    @Override
    public Boolean editTechInfo(ProjectTechInfoModel model, Integer userId) {
        Date date = new Date();
        ProjectTechInfoEntity techInfoEntity = projectTechInfoDao.getInfo(model.getProjectId());
        if (techInfoEntity != null) {
            techInfoEntity.setStartTime(model.getStartTime());
            techInfoEntity.setDockingTime(model.getDockingTime());
            techInfoEntity.setContent(model.getContent());
            techInfoEntity.setLastUpdateTime(date);
            techInfoEntity.setLastUpdatorId(userId);
            return projectTechInfoDao.updateById(techInfoEntity) > 0;
        } else {
            techInfoEntity = new ProjectTechInfoEntity();
            BeanUtils.copyProperties(model, techInfoEntity);
            techInfoEntity.setLastUpdatorId(userId);
            techInfoEntity.setCreatorId(userId);
            techInfoEntity.setCreateTime(date);
            techInfoEntity.setLastUpdateTime(date);
            return projectTechInfoDao.insert(techInfoEntity) > 0;
        }
    }

    @Override
    public Boolean editFinanceInfo(ProjectFinanceInfoModel model, Integer userId) {
        Date date = new Date();
        ProjectFinanceInfoEntity financeInfoEntity = projectFinanceInfoDao.getByProject(model.getProjectId());
        if (financeInfoEntity != null) {
            financeInfoEntity.setDockingTime(model.getDockingTime());
            financeInfoEntity.setEstimateRdAmount(model.getEstimateRdAmount());
            financeInfoEntity.setLastUpdateTime(date);
            financeInfoEntity.setLastUpdatorId(userId);
            return projectFinanceInfoDao.updateById(financeInfoEntity) > 0;
        } else {
            financeInfoEntity = new ProjectFinanceInfoEntity();
            financeInfoEntity.setLastUpdatorId(userId);
            financeInfoEntity.setLastUpdateTime(date);
            financeInfoEntity.setEstimateRdAmount(model.getEstimateRdAmount());
            financeInfoEntity.setDockingTime(model.getDockingTime());
            financeInfoEntity.setCreatorId(userId);
            financeInfoEntity.setCreateTime(date);
            financeInfoEntity.setProjectId(model.getProjectId());
            return projectFinanceInfoDao.insert(financeInfoEntity) > 0;
        }
    }
}
