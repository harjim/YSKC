package com.yskc.ms.service.impl.rs;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.ms.UserDao;
import com.yskc.ms.dao.ms.UserDeptDao;
import com.yskc.ms.dao.rs.*;
import com.yskc.ms.entity.ms.User;
import com.yskc.ms.entity.rs.*;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.SysDictionaryModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.rs.*;
import com.yskc.ms.service.rs.RsProductService;
import com.yskc.ms.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by hck
 * on 2020/10/13 8:29
 * description:申报项目接口实现类
 */
@Service
public class RsProductServiceImpl implements RsProductService {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RsProductDao rsProductDao;
    @Autowired
    private RsDirectionDao rsDirectionDao;
    @Autowired
    private RsProductStageDao rsProductStageDao;
    @Autowired
    private RsProductStageListDao rsProductStageListDao;
    @Autowired
    private RsProjectListDao rsProjectListDao;

    @Autowired
    private SysDictionaryDao sysDictionaryDao;
    @Autowired
    private UserDeptDao userDeptDao;
    @Autowired
    private UserDao userDao;

    @Override
    public Boolean add(RsProductModel model, UserInfo userInfo) throws OwnerException {
        Date date = new Date();
        if(!checkProductExist(model.getProductName(),model.getYear(),model.getAddressCode(),null)){
            throw new OwnerException("已存在项目名称、年份、地区相同的申报项目，不能重复添加！");
        }
        RsProductEntity entity = new RsProductEntity();
        BeanUtils.copyProperties(model, entity);
        entity.setCreateTime(date);
        entity.setCreatorId(userInfo.getId());
        entity.setLastUpdateTime(date);
        entity.setLastUpdatorId(userInfo.getId());
        entity.setManagerId(userInfo.getId());
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
        try {
            rsProductDao.insert(entity);
            List<RsDirectionEntity> directionEntities = new ArrayList<>();
            if (!CollectionUtils.isEmpty(model.getDirectionModels())) {

                for (RsDirectionModel directionModel : model.getDirectionModels()) {
                    RsDirectionEntity rsDirectionEntity = new RsDirectionEntity();
                    BeanUtils.copyProperties(directionModel, rsDirectionEntity);
                    rsDirectionEntity.setCreateTime(date);
                    rsDirectionEntity.setCreatorId(userInfo.getId());
                    rsDirectionEntity.setLastUpdateTime(date);
                    rsDirectionEntity.setLastUpdatorId(userInfo.getId());
                    rsDirectionEntity.setProductId(entity.getId());
                    rsDirectionEntity.setHasStage(false);
                    directionEntities.add(rsDirectionEntity);
                }
                rsDirectionDao.insertList(directionEntities);
            }
            TransactionUtils.commit(DataSourceEnum.RS, transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.RS, transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException(ErrorEnum.FAIL);
        }
    }

    @Override
    public List<RsDirectionModel> update(RsProductModel model, UserInfo userInfo) throws OwnerException {
        Date date = new Date();
        RsProductEntity entity = rsProductDao.selectById(model.getId());
        if(!checkProductExist(model.getProductName(),model.getYear(),model.getAddressCode(),model.getId())){
            throw new OwnerException("已存在项目名称、年份、地区相同的申报项目，请重新编辑！");
        }
        BeanUtils.copyProperties(model, entity);
        entity.setCreateTime(date);
        entity.setCreatorId(userInfo.getId());
        entity.setLastUpdateTime(date);
        entity.setLastUpdatorId(userInfo.getId());
        List<RsDirectionEntity> insertEntites = new ArrayList<>();
        List<RsDirectionModel> updateList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(model.getDirectionModels())) {
            for (RsDirectionModel dModel : model.getDirectionModels()) {
                if (dModel.getId() != null) {
                    updateList.add(dModel);
                } else {
                    RsDirectionEntity rsDirectionEntity = new RsDirectionEntity();
                    BeanUtils.copyProperties(dModel, rsDirectionEntity);
                    rsDirectionEntity.setCreateTime(date);
                    rsDirectionEntity.setCreatorId(userInfo.getId());
                    rsDirectionEntity.setLastUpdatorId(userInfo.getId());
                    rsDirectionEntity.setLastUpdateTime(date);
                    rsDirectionEntity.setHasStage(false);
                    insertEntites.add(rsDirectionEntity);
                }
            }
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
        try {
            rsProductDao.updateById(entity);
            //插入方向
            if (!CollectionUtils.isEmpty(insertEntites)) {
                rsDirectionDao.insertList(insertEntites);
            }
            //更新方向
            if (!CollectionUtils.isEmpty(updateList)) {
                rsDirectionDao.updateList(updateList, date, userInfo.getId());
            }
            TransactionUtils.commit(DataSourceEnum.RS, transactionStatus);
            return rsDirectionDao.getListByProduct(Arrays.asList(model.getId()));
        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.RS, transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException(ErrorEnum.FAIL);
        }
    }

    @Override
    public PageModel<List<RsProductModel>> getList(QueryRsProductModel query, DataPermModel dataPermModel) {
        Pagination page = query.getPagination();
        List<Integer> userIds = null;
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("tp.lastUpdateTime");
            page.setDescs(desc);
        }
        if(dataPermModel.getPermType() == 0){
            userIds = CollUtil.newArrayList(dataPermModel.getUserId());
        }else if(dataPermModel.getPermType() == 1){
            userIds = userDeptDao.getUserIdByFullPath(dataPermModel.getDeptPaths());
        }

        List<RsProductModel> productModels = rsProductDao.queryList(query, page,userIds);
        Map<Integer,String> managerMap=new HashMap<>();
        if (!CollectionUtils.isEmpty(productModels)) {
            List<Integer> managerIds=productModels.stream().map(e->e.getManagerId()).collect(Collectors.toList());
            List<User> users=userDao.selectBatchIds(managerIds);
            managerMap=users.stream().collect(Collectors.toMap(e->e.getId(),e->e.getRealName()));
            List<Integer> ids = productModels.stream().map(e -> e.getId()).collect(Collectors.toList());
            List<RsDirectionModel> rsDirectionEntities = rsDirectionDao.getListByProduct(ids);
            Map<Integer, List<RsDirectionModel>> dataMap = new HashMap<>();
            if(!CollectionUtils.isEmpty(rsDirectionEntities)) {
                for (RsDirectionModel model : rsDirectionEntities) {
                    if (!dataMap.containsKey(model.getProductId())) {
                        List<RsDirectionModel> modelList = new ArrayList<>();
                        dataMap.put(model.getProductId(), modelList);
                    }
                    dataMap.get(model.getProductId()).add(model);
                }
            }
            for (RsProductModel productModel : productModels) {
                if(managerMap.containsKey(productModel.getManagerId())){
                    productModel.setRealName(managerMap.get(productModel.getManagerId()));
                }
                if (dataMap.containsKey(productModel.getId())) {
                    productModel.setDirectionModels(dataMap.get(productModel.getId()));
                }
            }
        }
        return PageUtils.build(page, productModels);
    }

    @Override
    public Boolean del(List<RsProductModel> models, Integer userId) throws OwnerException {
        if (CollectionUtils.isEmpty(models)) {
            throw new OwnerException("请选择要删除的数据");
        }
        // // TODO: 21/03/18 删除申报项目问题 zdf
        List<Integer> ids = models.stream().map(e -> e.getId()).collect(Collectors.toList());
        List<RsProductEntity> productEntity = rsProductDao.selectBatchIds(ids);
        if (CollectionUtils.isEmpty(productEntity)) {
            throw new OwnerException("数据已删除或不存在，删除失败");
        }
        List<RsDirectionEntity> directionEntities = rsDirectionDao.getByProductId(ids);
        if (!CollectionUtils.isEmpty(directionEntities)) {
            throw new OwnerException("所选申报项目已添加方向，请先删除方向！");
        }
        return rsProductDao.deleteBatchIds(ids) > 0;

    }

    @Override
    public List<RsProductStageModel> getDirectionStage(Integer directionId) {
        List<RsProductStageModel> list = rsProductStageDao.getByDirectionId(directionId);
        if (!CollectionUtils.isEmpty(list)) {
            List<RsProductStageListEntity> stageList = rsProductStageListDao.getByDirectionIds(Arrays.asList(directionId));
            Map<Integer, List<RsProductStageListModel>> dataMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(stageList)) {
                List<Integer> stageListIds = stageList.stream().map(e -> e.getId()).collect(Collectors.toList());
                List<RsProjectListEntity> projectList = rsProjectListDao.getListByStage(stageListIds);
                Map<Integer, Boolean> projectMap = new HashMap<>();
                if (!CollectionUtils.isEmpty(projectList)) {
                    for (RsProjectListEntity entity : projectList) {
                        if (!projectMap.containsKey(entity.getStageListId())) {
                            projectMap.put(entity.getStageListId(), true);
                        }
                    }
                }
                Map<Integer, Boolean> stageMap = new HashMap<>();
                for (RsProductStageListEntity stage : stageList) {
                    RsProductStageListModel model = new RsProductStageListModel();
                    BeanUtils.copyProperties(stage, model);
                    if (projectMap.containsKey(stage.getId())) {
                        model.setLimitChange(true);
                        if (!stageMap.containsKey(stage.getStageId())) {
                            stageMap.put(stage.getStageId(), true);
                        }
                    }
                    if (!dataMap.containsKey(stage.getStageId())) {
                        List<RsProductStageListModel> listModel = new ArrayList<>();
                        dataMap.put(stage.getStageId(), listModel);
                    }
                    dataMap.get(stage.getStageId()).add(model);
                }
                for (RsProductStageModel productStage : list) {
                    if (dataMap.containsKey(productStage.getId())) {
                        productStage.setModels(dataMap.get(productStage.getId()));
                    }
                    if (stageMap.containsKey(productStage.getId())) {
                        productStage.setLimitChange(true);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<RsDirectionModel> getDirection(Integer productId) {
        List<RsDirectionModel> models = new ArrayList<>();
        List<RsDirectionEntity> directions = rsDirectionDao.getByProductId(Arrays.asList(productId));
        if (!CollectionUtils.isEmpty(directions)) {
            for (RsDirectionEntity entity : directions) {
                RsDirectionModel model = new RsDirectionModel();
                BeanUtils.copyProperties(entity, model);
                models.add(model);
            }
        }
        return models;
    }

    @Override
    public boolean addStages(List<ProductAddStageModel> models, UserInfo userInfo) throws OwnerException {
        Date date = new Date();
        List<RsProductStageEntity> addList = new ArrayList<>();
        if (CollectionUtils.isEmpty(models)) {
            throw new OwnerException("请选择要添加的阶段");
        }
        Integer directionId = models.get(0).getDirectionId();
        for (ProductAddStageModel model : models) {
            RsProductStageEntity entity = new RsProductStageEntity();
            BeanUtils.copyProperties(model, entity);
            entity.setCreateTime(date);
            entity.setCreatorId(userInfo.getId());
            entity.setLastUpdateTime(date);
            entity.setLastUpdatorId(userInfo.getId());
            addList.add(entity);
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
        try {
            RsDirectionEntity entity = rsDirectionDao.selectById(directionId);
            if (!entity.getHasStage()) {
                entity.setHasStage(true);
                entity.setLastUpdateTime(date);
                entity.setLastUpdatorId(userInfo.getId());
                rsDirectionDao.updateById(entity);
            }
            rsProductStageDao.insertList(addList);
            TransactionUtils.commit(DataSourceEnum.RS, transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.RS, transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException(ErrorEnum.FAIL);
        }

    }

    @Override
    public boolean delStages(ProductAddStageModel model, UserInfo userInfo) throws OwnerException {
        if (model == null) {
            throw new OwnerException("请选择要删除的阶段");
        }
        List<RsProductStageListModel> stageListEntities = rsProductStageListDao.getByStageId(Arrays.asList(model.getId()));
        if (!CollectionUtils.isEmpty(stageListEntities)) {
                throw new OwnerException("该阶段已配置资料，不能删除");
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
        try {
            rsProductStageDao.deleteById(model.getId());
            List<RsProductStageModel> stageEntities = rsProductStageDao.getByDirectionId(model.getDirectionId());
            //没有阶段设置方向hasStage
            if (CollectionUtils.isEmpty(stageEntities)) {
                RsDirectionEntity rsDirectionEntity = rsDirectionDao.selectById(model.getDirectionId());
                rsDirectionEntity.setHasStage(false);
                rsDirectionEntity.setLastUpdatorId(userInfo.getId());
                rsDirectionEntity.setLastUpdateTime(new Date());
                rsDirectionDao.updateById(rsDirectionEntity);
            }
            TransactionUtils.commit(DataSourceEnum.RS, transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.RS, transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException(ErrorEnum.FAIL);
        }
    }

    @Override
    public boolean editStages(List<ProductAddStageModel> models, UserInfo userInfo) throws OwnerException {
        Date date = new Date();
        if (CollectionUtils.isEmpty(models)) {
            return true;
        }
        Integer directionId = models.get(0).getDirectionId();
        List<ProductAddStageModel> updateList = new ArrayList<>();
        List<RsProductStageEntity> insertList = new ArrayList<>();
        for (ProductAddStageModel model : models) {
            if (model.getId() != null) {
                updateList.add(model);
            } else {
                RsProductStageEntity stageEntity = new RsProductStageEntity();
                BeanUtils.copyProperties(model, stageEntity);
                stageEntity.setLastUpdatorId(userInfo.getId());
                stageEntity.setCreatorId(userInfo.getId());
                stageEntity.setCreateTime(date);
                stageEntity.setLastUpdateTime(date);
                insertList.add(stageEntity);
            }
        }

        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
        try {
            if (!CollectionUtils.isEmpty(insertList)) {
                rsProductStageDao.insertList(insertList);
                RsDirectionEntity entity = rsDirectionDao.selectById(directionId);
                if (!entity.getHasStage()) {
                    entity.setHasStage(true);
                    entity.setLastUpdateTime(date);
                    entity.setLastUpdatorId(userInfo.getId());
                    rsDirectionDao.updateById(entity);
                }
            }
            if (!CollectionUtils.isEmpty(updateList)) {
                rsProductStageDao.updateList(updateList, date, userInfo.getId());
            }
            TransactionUtils.commit(DataSourceEnum.RS, transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.RS, transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException(ErrorEnum.FAIL);
        }
    }

    @Override
    public List<RsProductStageListModel> SetStageList(List<RsProductStageListModel> models, UserInfo userInfo) throws OwnerException {
        Date date = new Date();
        if (CollectionUtils.isEmpty(models)) {
            throw new OwnerException("请选择要保存的数据");
        }
        Integer stageId=models.get(0).getStageId();
        Integer itemType=models.get(0).getItemType();
        Map<String, RsProductStageListModel> dataMap = new HashMap<>();
        List<RsProductStageListEntity> insertList = new ArrayList<>();
        List<RsProductStageListModel> updateList = new ArrayList<>();
        for (RsProductStageListModel model : models) {
            if(dataMap.containsKey(model.getItemName())){
                if(dataMap.get(model.getItemName()).getItemType().equals(model.getItemType())) {
                    throw new OwnerException("存在相同的资料名：" + model.getItemName() + "，请修改后再保存");
                }
            }
            dataMap.put(model.getItemName(),model);
            if (model.getId() != null) {
                updateList.add(model);
            } else {
                RsProductStageListEntity entity = new RsProductStageListEntity();
                BeanUtils.copyProperties(model, entity);
                entity.setCreateTime(date);
                entity.setLastUpdateTime(date);
                entity.setCreatorId(userInfo.getId());
                entity.setLastUpdatorId(userInfo.getId());
                insertList.add(entity);
            }
        }

        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
        try {
            if (!CollectionUtils.isEmpty(insertList)) {
                rsProductStageListDao.insertList(insertList);
            }
            if (!CollectionUtils.isEmpty(updateList)) {
                rsProductStageListDao.updateList(updateList, date, userInfo.getId());
            }
            TransactionUtils.commit(DataSourceEnum.RS, transactionStatus);
            List<RsProductStageListModel> list=rsProductStageListDao.getByType(stageId,itemType);
            return list;
        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.RS, transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException(ErrorEnum.FAIL);
        }
    }


    @Override
    public boolean delStageList(List<RsProductStageListModel> models, UserInfo userInfo) throws OwnerException {
        if(CollectionUtils.isEmpty(models)){
            throw new  OwnerException("请选择要删除的资料！");
        }
        List<Integer> ids=models.stream().map(e->e.getId()).collect(Collectors.toList());
        List<RsProjectListEntity> projectList = rsProjectListDao.getListByStage(ids);
        if (!CollectionUtils.isEmpty(projectList)) {
            throw new OwnerException("所选资料已在技改项目中使用，不能删除");
        }
        return rsProductStageListDao.deleteBatchIds(ids) > 0;
    }

    @Override
    public Boolean delDirection(RsDirectionModel model, Integer id) throws OwnerException {
        List<RsProductStageModel> productStages=rsProductStageDao.getByDirectionId(model.getId());
        if (!CollectionUtils.isEmpty(productStages)) {
            throw new OwnerException("该方向已配置阶段，不能删除！");
        }
        return rsDirectionDao.deleteById(model.getId()) > 0;
    }

    @Override
    public Boolean copyProject(String addressCode, Integer year, Integer id,UserInfo info) throws OwnerException {
        Date date=new Date();
        RsProductEntity productEntity=rsProductDao.selectById(id);
        if(!checkProductExist(productEntity.getProductName(),year,addressCode,null)){
            throw new OwnerException("已存在项目名称、年份、地区相同的申报项目，请修改年份或地区！");
        }
        List<RsDirectionEntity> directions=rsDirectionDao.getByProductId(Arrays.asList(id));
        Map<Integer,RsDirectionEntity> directionMap=new HashMap<>();
        Map<Integer,List<RsProductStageEntity>> stageMap=new HashMap<>();
        Map<Integer,List<RsProductStageListEntity>> stageListMap=new HashMap<>();
        if(!CollectionUtils.isEmpty(directions)){
            List<Integer> directionIds=new ArrayList<>();
            for (RsDirectionEntity direction:directions) {
                directionMap.put(direction.getId(),direction);
                directionIds.add(direction.getId());
            }
            List<RsProductStageModel> productStages=rsProductStageDao.getByDirections(directionIds);
            if(!CollectionUtils.isEmpty(productStages)){
                List<Integer> stageIds=new ArrayList<>();
                for (RsProductStageModel productStage:productStages) {
                    stageIds.add(productStage.getId());
                    if(!stageMap.containsKey(productStage.getDirectionId())){
                        List<RsProductStageEntity> productStageModels=new ArrayList<>();
                        stageMap.put(productStage.getDirectionId(),productStageModels);
                    }
                    RsProductStageEntity entity=new RsProductStageEntity();
                    BeanUtils.copyProperties(productStage,entity);
                    stageMap.get(productStage.getDirectionId()).add(entity);
                }
                List<RsProductStageListModel> stageList=rsProductStageListDao.getByStageId(stageIds);
                if(!CollectionUtils.isEmpty(stageList)){
                    for (RsProductStageListModel stage:stageList) {
                        if(!stageListMap.containsKey(stage.getStageId())){
                            List<RsProductStageListEntity> models=new ArrayList<>();
                            stageListMap.put(stage.getStageId(),models);
                        }
                        RsProductStageListEntity stageListEntity=new RsProductStageListEntity();
                        BeanUtils.copyProperties(stage,stageListEntity);
                        stageListMap.get(stage.getStageId()).add(stageListEntity);
                    }
                }
            }
        }

        //插入申报项目
        productEntity.setId(null);
        productEntity.setYear(year);
        productEntity.setAddressCode(addressCode);
        productEntity.setCreatorId(info.getId());
        productEntity.setCreateTime(date);
        productEntity.setLastUpdatorId(info.getId());
        productEntity.setLastUpdateTime(date);
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
        try {
            rsProductDao.insert(productEntity);
            Map<Integer,Integer> directMap=new HashMap<>();//方向新旧id映射map
            Map<Integer,Integer> stageIdMap=new HashMap<>();//阶段新旧id映射map
            Map<Integer,Integer> dataMap=new HashMap<>();//新阶段id对应方向idmap
            Integer productId=productEntity.getId();
            if(directionMap!=null){
                for (Integer directionId:directionMap.keySet()){
                    RsDirectionEntity entity=directionMap.get(directionId);
                    Integer oldId=entity.getId();
                    entity.setId(null);
                    entity.setProductId(productId);
                    entity.setCreateTime(date);
                    entity.setCreatorId(info.getId());
                    entity.setLastUpdateTime(date);
                    entity.setLastUpdatorId(info.getId());
                    rsDirectionDao.insert(entity);
                    directMap.put(oldId,entity.getId());
                }
                if(stageMap!=null){
                    for (Integer oldDirectId:stageMap.keySet()){
                        List<RsProductStageEntity> productStages=stageMap.get(oldDirectId);
                        for (RsProductStageEntity stage:productStages){
                            Integer oldStageId=stage.getId();
                            stage.setId(null);
                            stage.setDirectionId(directMap.get(stage.getDirectionId()));
                            stage.setCreateTime(date);
                            stage.setCreatorId(info.getId());
                            stage.setLastUpdatorId(info.getId());
                            stage.setLastUpdateTime(date);
                            rsProductStageDao.insert(stage);
                            stageIdMap.put(oldStageId,stage.getId());
                            dataMap.put(stage.getId(),stage.getDirectionId());
                        }
                    }
                    if(stageListMap!=null){
                        List<RsProductStageListEntity> insertList=new ArrayList<>();
                        for (Integer oldStageId:stageListMap.keySet()){
                            List<RsProductStageListEntity> stageList=stageListMap.get(oldStageId);
                            for (RsProductStageListEntity entity:stageList){
                                entity.setId(null);
                                entity.setStageId(stageIdMap.get(oldStageId));
                                entity.setCreatorId(info.getId());
                                entity.setCreateTime(date);
                                entity.setLastUpdateTime(date);
                                entity.setLastUpdatorId(info.getId());
                                entity.setDirectionId(dataMap.get(stageIdMap.get(oldStageId)));
                                insertList.add(entity);
                            }
                        }
                        rsProductStageListDao.insertList(insertList);
                    }
                }
            }
            TransactionUtils.commit(DataSourceEnum.RS, transactionStatus);
         return true;
        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.RS, transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException(ErrorEnum.FAIL);
        }
    }

    @Override
    public Boolean allocatingStaff(List<RsProductModel> models, UserInfo userInfo) {
        Date date=new Date();
        if(CollectionUtils.isEmpty(models)){
            return true;
        }
        List<Integer> ids=models.stream().map(e->e.getId()).collect(Collectors.toList());
       return rsProductDao.updateManager(ids,models.get(0).getManagerId(),date,userInfo.getId())>0;

    }

    @Override
    public boolean importProduct(UserInfo info, List<Map<String, Object>> list) throws OwnerException{
        if(CollectionUtils.isEmpty(list)){
            return true;
        }
        Date date=new Date();
        Map<String,String> productMap=new HashMap<>();
        Map<String,String> addressMap=new HashMap<>();
        List<SysDictionaryModel> models=sysDictionaryDao.getProductNeedData();
        for (SysDictionaryModel model:models) {
            if(model.getType()==1){
                if("0".equals(model.getParentKey())){
                    addressMap.put(MessageFormat.format("{0}-{1}",model.getValue(),"0"),model.getKey());
                }else {
                    addressMap.put(model.getValue(),model.getKey());
                }

            }else {
                productMap.put(MessageFormat.format("{0}-{1}",model.getValue(),model.getType()),model.getKey());
            }

        }
        List<RsProductEntity> products=new ArrayList<>();
        for (Map<String, Object> map:list) {
            String productName=map.get("项目名称")!=null?map.get("项目名称").toString():"";
            String yearStr=map.get("年份")!=null?map.get("年份").toString():"";
            String address=map.get("地区")!=null?map.get("地区").toString():"";
            String type=map.get("项目类型")!=null?map.get("项目类型").toString():"";
            String level=map.get("项目级别")!=null?map.get("项目级别").toString():"";
            String govName=map.get("所属机构")!=null?map.get("所属机构").toString():"";
            String workSite=map.get("办公地址")!=null?map.get("办公地址").toString():"";
            if(StringUtils.isEmpty(productName) || StringUtils.isEmpty(yearStr)|| StringUtils.isEmpty(address)|| StringUtils.isEmpty(type)|| StringUtils.isEmpty(level)){
                throw new OwnerException("项目名称、年份、地区、项目类型、项目级别为必填项，请完善信息后再导入！");
            }
            if(!productMap.containsKey(type+"-10")){
                throw new OwnerException("项目类型应为经信类、商务类、科技类其中之一");
            }
            if(!productMap.containsKey(level+"-11")){
                throw new OwnerException("项目级别应为省级、市级、区级其中之一");
            }
            List<String> addressCode=Arrays.asList(address.split("/"));
            StringBuffer sbf=new StringBuffer();
            for (int i=0;i<addressCode.size();i++){
                if(i==0){
                    if(addressMap.containsKey(addressCode.get(0)+"-0")){
                        sbf.append(addressMap.get(addressCode.get(0)+"-0"));
                    }else {
                        throw new OwnerException("请填写正确的地区格式，如xx省(市)/xx市/xx区");
                    }
                }else {
                    if(addressMap.containsKey(addressCode.get(i))){
                        sbf.append("/"+addressMap.get(addressCode.get(i)));
                    }else{
                        throw new OwnerException("未查询到地区："+addressCode.get(i)+",请修改后再导入");
                    }
                }
            }
            RsProductEntity product=new RsProductEntity();
            product.setAddressCode(sbf.toString());
            product.setAddress(workSite);
            product.setGovName(govName);
            product.setProductName(productName);
            product.setpLevel(productMap.get(type+"-11"));
            product.setpType(productMap.get(type+"-10"));
            product.setYear(Integer.parseInt(yearStr));
            product.setCreatorId(info.getId());
            product.setCreateTime(date);
            product.setLastUpdatorId(info.getId());
            product.setLastUpdateTime(date);
            products.add(product);
        }
        return rsProductDao.insertList(products);
    }

    private Boolean checkProductExist(String productName,Integer year,String addressCode,Integer id){
        List<RsProductEntity> products=rsProductDao.checkExist(productName,year,addressCode,id);
            if (CollectionUtils.isEmpty(products)) {
                return true;
            }
            return false;

    }
}
