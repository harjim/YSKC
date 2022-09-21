package com.yskc.ms.service.impl.ms;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.ms.ProductStageDao;
import com.yskc.ms.dao.ms.ProjectDao;
import com.yskc.ms.dao.ms.ProjectMemberDao;
import com.yskc.ms.dao.ms.ProjectTechLogDao;
import com.yskc.ms.entity.ms.ProductStageEntity;
import com.yskc.ms.entity.ms.Project;
import com.yskc.ms.entity.ms.ProjectTechLogEntity;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.product.StageModel;
import com.yskc.ms.models.project.ProjectMembersModel;
import com.yskc.ms.models.tech.ProjectTechLogModel;
import com.yskc.ms.models.tech.ProjectTechProgressModel;
import com.yskc.ms.models.tech.QueryTechModel;
import com.yskc.ms.service.ms.ProjectTechService;
import com.yskc.ms.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by hck
 * on 2020/7/23 15:49
 * description:
 */
@Service
public class ProjectTechServiceImpl implements ProjectTechService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProjectTechLogDao projectTechLogDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private ProductStageDao productStageDao;
    @Autowired
    private ProjectMemberDao projectMemberDao;

    @Override
    public PageModel<List<ProjectTechProgressModel>> getTechList(QueryTechModel query, UserInfo userInfo, DataPermModel dataPerm) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> descs = new ArrayList<>();
            descs.add("pj.lastUpdateTime");
            page.setDescs(descs);
        }
        List<ProjectTechProgressModel> listModels = projectDao.getTechList(page, query, dataPerm);
        if (CollectionUtils.isEmpty(listModels)) {
            return PageUtils.build(page, listModels);
        }
        List<Integer> projectIds = new ArrayList<>();
        List<Integer> productIds = new ArrayList<>();
        for (ProjectTechProgressModel progressModel : listModels) {
            projectIds.add(progressModel.getId());
            productIds.add(progressModel.getProductId());
        }
        List<ProductStageEntity> stageEntities = productStageDao.selectByProductIds(productIds.stream().distinct().collect(Collectors.toList()));
        Map<Integer, Integer> productStageMap = new HashMap<>();
        for (ProductStageEntity proEntity : stageEntities) {
            if (!productStageMap.containsKey(proEntity.getProductId())) {
                productStageMap.put(proEntity.getProductId(), proEntity.getId());
            }
        }
        if (!CollectionUtils.isEmpty(projectIds)) {
            List<ProjectTechLogModel> logModels = projectTechLogDao.getLogList(projectIds);
            Map<Integer, List<ProjectTechLogModel>> logMap = new HashMap<>();
            for (ProjectTechLogModel model : logModels) {
                if (!logMap.containsKey(model.getProjectId())) {
                    logMap.put(model.getProjectId(), new ArrayList<>());
                }
                logMap.get(model.getProjectId()).add(model);
            }
            Map<Integer, List<ProjectMembersModel>> memberMap = new HashMap<>();
            List<ProjectMembersModel> members = projectMemberDao.getByProjectIds(projectIds);
            if (!CollectionUtils.isEmpty(members)) {
                for (ProjectMembersModel member : members) {
                    if (!memberMap.containsKey(member.getProjectId())) {
                        memberMap.put(member.getProjectId(),new ArrayList<>());
                    }
                    memberMap.get(member.getProjectId()).add(member);
                }
            }
            for (ProjectTechProgressModel progressModel : listModels) {
                if (logMap.containsKey(progressModel.getId())) {
                    progressModel.setLogList(logMap.get(progressModel.getId()));
                }
                progressModel.setHasStage(productStageMap.containsKey(progressModel.getProductId())?true:false);
                if (memberMap.containsKey(progressModel.getId())) {
                    progressModel.setTechs(memberMap.get(progressModel.getId()));
                }
            }
        }
        return PageUtils.build(page, listModels);
    }

    @Override
    public List<ProjectTechLogModel> getLogList(Integer projectId) {
        List<Integer> projectIds = new ArrayList<>();
        projectIds.add(projectId);
        List<ProjectTechLogModel> techLogModels = projectTechLogDao.getLogList(projectIds);
        return techLogModels;
    }

    @Override
    public Boolean addTechLog(ProjectTechLogModel model, UserInfo userInfo) throws OwnerException {
        Project project = projectDao.selectById(model.getProjectId());
        List<StageModel> stageModels = productStageDao.getByProductId(project.getProductId());
        Map<String, Integer> stageMap = stageModels.stream().collect(Collectors.toMap(e -> e.getStageKey(), e -> e.getOrder()));
        if (project.getLastLogId() != null && project.getLastLogId() > 0) {
            ProjectTechLogEntity techLogEntity = projectTechLogDao.selectById(project.getLastLogId());
            if (techLogEntity != null) {
                if (stageMap.get(techLogEntity.getStageKey()) > stageMap.get(model.getStageKey())) {
                    throw new OwnerException("该阶段已完成，不能添加");
                }
                if (stageMap.get(model.getStageKey()) > stageMap.get(techLogEntity.getStageKey()) + 1) {
                    throw new OwnerException("当前阶段与添加阶段间存在其他阶段，不能添加！");
                }
            }
        } else {
            if (stageMap.get(model.getStageKey()) != 1) {
                throw new OwnerException("请从初始阶段开始添加!");
            }
        }

        Date date = new Date();
        ProjectTechLogEntity entity = new ProjectTechLogEntity();
        BeanUtils.copyProperties(model, entity);
        entity.setCreateTime(date);
        entity.setLastUpdateTime(date);
        entity.setCreatorId(userInfo.getId());
        entity.setLastUpdatorId(userInfo.getId());
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            projectTechLogDao.insert(entity);
            project.setLastLogId(entity.getId());
            project.setLastUpdateTime(date);
            project.setLastUpdatorId(userInfo.getId());
            projectDao.updateById(project);
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException("添加失败，请联系管理员。");
        }
    }

    @Override
    public Boolean updateTechLog(ProjectTechLogModel model, UserInfo userInfo) throws OwnerException {
        ProjectTechLogEntity techLogEntity = projectTechLogDao.selectById(model.getId());
        if (techLogEntity != null) {
            BeanUtils.copyProperties(model, techLogEntity);
            techLogEntity.setLastUpdateTime(new Date());
            techLogEntity.setLastUpdatorId(userInfo.getId());
            return projectTechLogDao.updateById(techLogEntity) > 0;
        }
        throw new OwnerException("未找到该条日志，编辑失败");
    }

    @Override
    public Boolean delTechLog(ProjectTechLogModel model, UserInfo userInfo) throws OwnerException {
        Project project = projectDao.getByLogId(model.getId(), model.getProjectId());
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            if (project != null) {
                ProjectTechLogEntity logEntity = projectTechLogDao.queryByCteateTime(model.getProjectId(), model.getId());
                if (logEntity != null) {
                    project.setLastLogId(logEntity.getId());
                } else {
                    project.setLastLogId(null);
                }
                project.setLastUpdateTime(new Date());
                project.setLastUpdatorId(userInfo.getId());
                projectDao.updateById(project);
            }
            projectTechLogDao.deleteById(model.getId());
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException("删除失败，请联系管理员。");
        }
    }
}
