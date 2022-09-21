package com.yskc.ms.service.impl.ms;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.ms.dao.ms.ProductDao;
import com.yskc.ms.dao.ms.ProductStageDao;
import com.yskc.ms.dao.ms.ProjectTechLogDao;
import com.yskc.ms.entity.ms.Product;
import com.yskc.ms.entity.ms.ProductStageEntity;
import com.yskc.ms.entity.ms.ProjectTechLogEntity;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.product.AddStageModel;
import com.yskc.ms.models.product.StageModel;
import com.yskc.ms.service.ms.ProductStageService;
import com.yskc.ms.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by hck
 * on 2020/7/28 14:12
 * description:
 */
@Service
public class ProductStageServiceImpl implements ProductStageService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductStageDao productStageDao;
    @Autowired
    private ProjectTechLogDao projectTechLogDao;

    @Override
    public List<StageModel> getStageList(Integer productId) {
        List<StageModel> models = productStageDao.getByProductId(productId);
        return models;
    }

    @Override
    public String checkStageData(List<String> stageKeys, Integer productId) throws OwnerException {
        List<ProjectTechLogEntity> logList = projectTechLogDao.getByStageKey(stageKeys, productId);
        if (!CollectionUtils.isEmpty(logList)) {
            List<String> existStage = logList.stream().map(ProjectTechLogEntity::getStageKey).distinct().collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(existStage)) {
                throw new OwnerException("所选阶段已在技改进度中使用，不能移出！");
            }
        }
        List<ProductStageEntity> stageEntities = productStageDao.checkDelStage(productId, stageKeys);
        String message = "";
        if (!CollectionUtils.isEmpty(stageEntities)) {
            message = "所选阶段已存在配置了截止日期的项目,确定要移除?";
        }
        return message;
    }

    @Override
    public Boolean addProductStage(Integer userId, AddStageModel model) throws OwnerException {
        Date date = new Date();
        if (CollectionUtils.isEmpty(model.getStageKeys())) {
            throw new OwnerException("请选择要添加的阶段");
        }
        Product product = productDao.selectById(model.getProductId());
        if (product != null) {
            product.setProductName(model.getProductName());
            product.setLastUpdateTime(date);
            product.setLastUpdatorId(userId);
        } else {
            throw new OwnerException("获取类型失败，请联系管理员");
        }
        List<ProductStageEntity> entities = new ArrayList<>();
        for (StageModel stageModel : model.getStageKeys()) {
            ProductStageEntity stageEntity = new ProductStageEntity();
            stageEntity.setProductId(model.getProductId());
            stageEntity.setCreateTime(date);
            stageEntity.setCreatorId(userId);
            stageEntity.setLastUpdateTime(date);
            stageEntity.setLastUpdatorId(userId);
            stageEntity.setOrder(stageModel.getOrder());
            stageEntity.setStageKey(stageModel.getStageKey());
            entities.add(stageEntity);
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            productStageDao.insertList(entities);
            productDao.updateById(product);
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException("添加阶段失败，请联系管理员。");
        }
    }

    @Override
    public Boolean updateProductStage(Integer userId, AddStageModel model) throws OwnerException {
        Date date = new Date();
        Integer sign = 0;
        Product product = productDao.selectById(model.getProductId());
        if (!StringUtils.isEmpty(model.getProductName())) {
            product.setProductName(model.getProductName());
            product.setLastUpdateTime(date);
            product.setLastUpdatorId(userId);
            sign = 1;
        }
        List<StageModel> stageEntities = productStageDao.getByProductId(model.getProductId());
        List<StageModel> paramList = model.getStageKeys();
        List<ProductStageEntity> insertList = new ArrayList<>();
        List<StageModel> updateList = new ArrayList<>();
        List<Integer> deleteList = new ArrayList<>();
        if (CollectionUtils.isEmpty(paramList)) {
            return true;
        }
        if (CollectionUtils.isEmpty(stageEntities)) {
            for (StageModel stageModel : model.getStageKeys()) {
                ProductStageEntity stageEntity = new ProductStageEntity();
                stageEntity.setStageKey(stageModel.getStageKey());
                stageEntity.setOrder(stageModel.getOrder());
                stageEntity.setProductId(model.getProductId());
                stageEntity.setCreatorId(userId);
                stageEntity.setCreateTime(date);
                stageEntity.setLastUpdatorId(userId);
                stageEntity.setLastUpdateTime(date);
                insertList.add(stageEntity);
            }
        } else {
            List<Integer> changeIds = new ArrayList<>();
            List<Integer> existList = stageEntities.stream().map(StageModel::getId).collect(Collectors.toList());
            for (StageModel stage : paramList) {
                if (stage.getId() != null && stage.getId() > 0) {
                    updateList.add(stage);
                    changeIds.add(stage.getId());
                } else {
                    ProductStageEntity stageEntity = new ProductStageEntity();
                    stageEntity.setStageKey(stage.getStageKey());
                    stageEntity.setOrder(stage.getOrder());
                    stageEntity.setProductId(model.getProductId());
                    stageEntity.setLastUpdatorId(userId);
                    stageEntity.setCreatorId(userId);
                    stageEntity.setCreateTime(date);
                    stageEntity.setLastUpdateTime(date);
                    insertList.add(stageEntity);
                }

            }
            deleteList = (List<Integer>) CollUtil.disjunction(changeIds, existList);
        }

        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            if (CollectionUtils.isEmpty(paramList)) {
                //删除阶段
                productStageDao.delByProductId(model.getProductId());
            }
            if (sign == 1) {
                //更新产品名称
                productDao.updateById(product);
            }
            if (!CollectionUtils.isEmpty(insertList)) {
                productStageDao.insertList(insertList);
            }
            if (!CollectionUtils.isEmpty(updateList)) {
                productStageDao.updateList(updateList, date, userId);
            }
            if (!CollectionUtils.isEmpty(deleteList)) {
                productStageDao.delByIds(deleteList);
            }
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException("编辑产品阶段失败，请联系管理员。");
        }
    }
}
