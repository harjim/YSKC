package com.yskc.ms.service.impl.ms;

import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.ms.dao.ms.ProductDao;
import com.yskc.ms.dao.ms.ProductStageDao;
import com.yskc.ms.dao.ms.ProjectDao;
import com.yskc.ms.dao.ms.ProjectTechStageDao;
import com.yskc.ms.entity.ms.Product;
import com.yskc.ms.entity.ms.ProductStageEntity;
import com.yskc.ms.entity.ms.Project;
import com.yskc.ms.entity.ms.ProjectTechStageEntity;
import com.yskc.ms.models.ProjectTechStage.ProjectStageModel;
import com.yskc.ms.models.ProjectTechStage.SetDeadlineModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.product.AddStageModel;
import com.yskc.ms.models.product.StageModel;
import com.yskc.ms.service.ms.ProjectTechStageService;
import com.yskc.ms.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by hck
 * on 2020/7/28 15:15
 * description:
 */
@Service
public class ProjectTechStageServiceImpl implements ProjectTechStageService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ProjectTechStageDao projectTechStageDao;
    @Autowired
    private ProductStageDao productStageDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private ProductDao productDao;

    @Override
    public List<StageModel> getStageList(UserInfo userInfo, Integer projectId,Integer productId) throws OwnerException {
        List<StageModel> stageModels=productStageDao.getByProductId(productId);
        if (CollectionUtils.isEmpty(stageModels)){
            throw new OwnerException("请先指定类型阶段");
        }
        List<ProjectStageModel> projectStageModels=projectTechStageDao.getByProjectId(projectId);
        if(!CollectionUtils.isEmpty(projectStageModels)){
            Map<Integer,ProjectStageModel> map=projectStageModels.stream().collect(Collectors.toMap(e->e.getProjectStageId(),e->e));
            for (StageModel model:stageModels) {
                if(map.containsKey(model.getId())){
                    model.setDeadline(map.get(model.getId()).getDeadline());
                    model.setProjectStageId(map.get(model.getId()).getId());
                }
            }
        }
        return stageModels;
    }

    @Override
    public List<StageModel> queryProjectStage(UserInfo userInfo, Integer productId) {
        List<StageModel> stageModels=productStageDao.getByProductId(productId);
        return stageModels;
    }

    @Override
    public Boolean editDeadline(List<ProjectStageModel> models, UserInfo userInfo) throws OwnerException{
        Date date=new Date();
      if(CollectionUtils.isEmpty(models)){
          return true;
      }
      List<ProjectStageModel> updateList=new ArrayList<>();
      List<Integer> deleteIds=new ArrayList<>();
      List<ProjectTechStageEntity> insertList=new ArrayList<>();
        for (ProjectStageModel model:models) {
            if(model.getProjectStageId()!=null&&model.getProjectStageId()>0){
                if(model.getDeadline()!=null) {
                    updateList.add(model);
                }else {
                    deleteIds.add(model.getProjectStageId());
                }
            }else{
                ProjectTechStageEntity entity=new ProjectTechStageEntity();
                entity.setCreateTime(date);
                entity.setLastUpdateTime(date);
                entity.setCreatorId(userInfo.getId());
                entity.setLastUpdatorId(userInfo.getId());
                entity.setStageId(model.getId());
                entity.setProjectId(model.getProjectId());
                entity.setDeadline(model.getDeadline());
                insertList.add(entity);
            }
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            if(!CollectionUtils.isEmpty(updateList)){
                projectTechStageDao.updateList(updateList,date,userInfo.getId());
            }
            if (!CollectionUtils.isEmpty(insertList)) {
                projectTechStageDao.insertList(insertList);
            }
            if(!CollectionUtils.isEmpty(deleteIds)){
                projectTechStageDao.deleteBatchIds(deleteIds);
            }
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException("编辑项目阶段截止日期失败，请联系管理员。");
        }
    }

    @Override
    public Boolean setDeadlines(SetDeadlineModel model, UserInfo userInfo) throws OwnerException{
        Date date=new Date();
        if(CollectionUtils.isEmpty(model.getProjectIds())){
            return true;
        }
        //项目列表
        List<Project> projects=projectDao.selectBatchIds(model.getProjectIds());
        //产品ids
        List<Integer> productIds=projects.stream().map(project ->project.getProductId()).distinct().collect(Collectors.toList());
       //有该阶段的产品列表
        List<ProductStageEntity> stageEntities=productStageDao.getListByStage(productIds,model.getStageKey());
        if(CollectionUtils.isEmpty(stageEntities)){
            return true;
        }
        Map<Integer,Integer> map=new HashMap<>();
        List<Integer> stageIds=new ArrayList<>();
        for(ProductStageEntity entity:stageEntities){
            if(!map.containsKey(entity.getProductId())){
                map.put(entity.getProductId(),entity.getId());
            }
            stageIds.add(entity.getId());
        }
        List<ProjectTechStageEntity> insertList=new ArrayList<>();
        for (Project project:projects) {
            if(map.containsKey(project.getProductId())){
                ProjectTechStageEntity stageEntity=new ProjectTechStageEntity();
                stageEntity.setDeadline(model.getDeadline());
                stageEntity.setProjectId(project.getId());
                stageEntity.setStageId(map.get(project.getProductId()));
                stageEntity.setCreateTime(date);
                stageEntity.setLastUpdateTime(date);
                stageEntity.setCreatorId(userInfo.getId());
                stageEntity.setLastUpdatorId(userInfo.getId());
                insertList.add(stageEntity);
            }
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);

            projectTechStageDao.deleteByStage(model.getProjectIds(),stageIds);
            projectTechStageDao.insertList(insertList);
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException("设置项目阶段截止日期失败，请联系管理员。");
        }
    }
}
