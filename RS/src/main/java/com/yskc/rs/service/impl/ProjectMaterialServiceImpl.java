package com.yskc.rs.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.PageResult;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.dao.*;
import com.yskc.rs.dao.project.ProjectDocFileDataDao;
import com.yskc.rs.entity.MaterialEntity;
import com.yskc.rs.entity.project.ProjectDocFileEntity;
import com.yskc.rs.entity.project.ProjectMaterialEntity;
import com.yskc.rs.enums.CostEnum;
import com.yskc.rs.models.CheckStatusModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.material.AppMaterialModel;
import com.yskc.rs.models.material.ProjectMaterialModel;
import com.yskc.rs.models.material.QueryMaterialTrackModel;
import com.yskc.rs.models.project.DocMaterialModel;
import com.yskc.rs.models.project.QueryProjectMaterialModel;
import com.yskc.rs.models.projectmaterial.DepreciationRatioModel;
import com.yskc.rs.models.projectyieldconfig.ProjectYieldConfigModel;
import com.yskc.rs.models.projectyieldconfig.QueryYieldConfigModel;
import com.yskc.rs.service.ProjectMaterialService;
import com.yskc.rs.utils.ToolUtils;
import com.yskc.rs.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectMaterialServiceImpl extends ServiceImpl<ProjectMaterialDao, ProjectMaterialEntity>
        implements ProjectMaterialService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ProjectMaterialDao projectMaterialDao;
    @Autowired
    private MaterialDao materialDao;
    @Autowired
    private SummaryDao summaryDao;
    @Autowired
    private TraceDao traceDao;
    @Autowired
    private CommonService commonService;
    @Autowired
    private StageDao stageDao;
    @Autowired
    private ProjectDocFileDao projectDocFileDao;
    @Autowired
    private ProjectDocFileDataDao projectDocFileDataDao;

    /**
     * 选中添加
     */
    @Override
    public boolean saveMaterial(UserInfo userInfo, ProjectMaterialModel model) throws OwnerException {
        CheckStatusModel statusModel = new CheckStatusModel(model.getProjectId(), model.getAcqMonth());
        commonService.checkStatus(Arrays.asList(statusModel), Arrays.asList(model.getRdType()),userInfo);
        List<MaterialEntity> materialList = model.getMaterialList();
        Integer type = model.getRdType();
        Integer projectId = model.getProjectId();
        Integer companyId = userInfo.getCompanyId();
        Date nowTime = new Date();
        List<MaterialEntity> materialRemainQuantiries = new ArrayList<MaterialEntity>();
        List<Integer> materialIdList = materialList.stream().filter(a -> !StringUtils.isEmpty(a.getId())).map(MaterialEntity::getId).collect(Collectors.toList());
        List<ProjectMaterialEntity> pMaterialList = projectMaterialDao.queryPMaterialList(userInfo.getCompanyId(), materialIdList, projectId, type);
        Map<Integer, ProjectMaterialEntity> pMaterialMap = pMaterialList.stream().collect(Collectors.toMap(ProjectMaterialEntity::getMaterialDataId, b -> b, (c, d) -> d));
        List<MaterialEntity> mList = materialDao.queryMaterialList(materialIdList);
        Map<Integer, MaterialEntity> materialMap = mList.stream().collect(Collectors.toMap(MaterialEntity::getId, b -> b));
        List<ProjectMaterialEntity> insertMaterialEntities = new ArrayList<ProjectMaterialEntity>();
        List<ProjectMaterialEntity> updateMaterialEntities = new ArrayList<ProjectMaterialEntity>();
        Integer userId = userInfo.getUserId(), msUserId = userInfo.getMsUserId();
        for (int i = 0; i < materialList.size(); i++) {
            MaterialEntity materialEntity = materialList.get(i);
            Integer materialDataId = materialEntity.getId();
            // 当次要取的量
            BigDecimal remainQuantity = materialEntity.getRemainQuantity();
            //根据id查出对应的物料
            MaterialEntity materialModel = materialMap.get(materialDataId);

            if (remainQuantity.abs().compareTo(materialModel.getRemainQuantity().abs()) == 1) {
                throw new OwnerException(ErrorEnum.MATERIAL_INSUFFICIENT_STOCK);
            }
            // 总额减去使用量得出库存
            materialModel.setRemainQuantity(materialModel.getRemainQuantity().subtract(materialEntity.getRemainQuantity()));
            materialRemainQuantiries.add(materialModel);

            ProjectMaterialEntity projectMaterialEntity = pMaterialMap.get(materialDataId);
            if (projectMaterialEntity != null) {
                updateMaterialEntities.add(ProjectMaterialEntity.build(projectMaterialEntity, userId, msUserId,
                        nowTime, remainQuantity, materialEntity.getUnitPrice()));
            } else {
                insertMaterialEntities.add(ProjectMaterialEntity.build(userId, msUserId, nowTime, materialEntity.getId(),
                        projectId, type, companyId, materialEntity.getRemainQuantity(),
                        materialEntity.getRemainQuantity().multiply(materialModel.getUnitPrice()), materialModel.getUnitPrice()));
            }
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            if (insertMaterialEntities.size() > 0) {
                projectMaterialDao.addBatch(insertMaterialEntities);
            }
            if (updateMaterialEntities.size() > 0) {
                projectMaterialDao.updateBatch(updateMaterialEntities);
            }
            materialDao.updateRemainQuantityById(materialRemainQuantiries);
            saveOrDeleteSummary(nowTime, projectId, model.getAcqMonth(), userInfo, type);
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException(ErrorEnum.FAIL);
        }

        return true;
    }

    /**
     * 选中添加(相同出库单号添加)
     */
    @Override
    public boolean saveBillNo(UserInfo userInfo, ProjectMaterialModel model) throws OwnerException {
        Integer projectId = model.getProjectId();
        Integer rdType = model.getRdType();
        CheckStatusModel statusModel = new CheckStatusModel(projectId, model.getAcqMonth());
        commonService.checkStatus(Arrays.asList(statusModel), Arrays.asList(rdType),userInfo);
        Integer companyId = userInfo.getCompanyId();
        List<AppMaterialModel> materialList = model.getModelList();
        List<String> billNoList = new ArrayList<String>();
        List<AppMaterialModel> materialModels2 = new ArrayList<AppMaterialModel>();
        for (int i = 0; i < materialList.size(); i++) {
            String billNo = materialList.get(i).getBillNo();
            if (!StringUtils.isEmpty(billNo)) {
                if (!billNoList.contains(billNo)) {
                    billNoList.add(billNo);
                }
            } else {
                materialModels2.add(materialList.get(i));
            }
        }
        List<AppMaterialModel> materialModels = new ArrayList<AppMaterialModel>();
        if (billNoList.size() > 0) {
            materialModels = materialDao.queryMaterialByBillNoList(companyId, billNoList,rdType);
            materialModels.addAll(materialModels2);
        } else {
            materialModels.addAll(materialModels2);
        }

        // 判断有没可添加的数据
        if (materialModels.size() == 0) {
            throw new OwnerException(ErrorEnum.MATERIAL_NO_CONSISTENT_DATA);
        }

        List<MaterialEntity> materialEntities = new ArrayList<MaterialEntity>();
        Date date = new Date();
        List<Integer> materialIdList = materialModels.stream().filter(a -> !StringUtils.isEmpty(a.getId())).map(AppMaterialModel::getId).collect(Collectors.toList());
        List<ProjectMaterialEntity> pMaterialList = projectMaterialDao.queryPMaterialList(userInfo.getCompanyId(), materialIdList, projectId, rdType);
        Map<Integer, ProjectMaterialEntity> pMaterialMap = pMaterialList.stream().collect(Collectors.toMap(ProjectMaterialEntity::getMaterialDataId, b -> b));
        List<ProjectMaterialEntity> insertMaterialEntities = new ArrayList<ProjectMaterialEntity>();
        List<ProjectMaterialEntity> updateMaterialEntities = new ArrayList<ProjectMaterialEntity>();

        Integer userId = userInfo.getUserId();
        Integer msUserId = userInfo.getMsUserId();
        for (int i = 0; i < materialModels.size(); i++) {
            MaterialEntity materialEntity = new MaterialEntity();
            AppMaterialModel appMaterialModel = materialModels.get(i);
            BeanUtils.copyProperties(appMaterialModel, materialEntity);
            materialEntity.setRemainQuantity(BigDecimal.ZERO);
            materialEntities.add(materialEntity);
            ProjectMaterialEntity projectMaterialEntity = pMaterialMap.get(appMaterialModel.getId());
            if (projectMaterialEntity != null) {
                updateMaterialEntities.add(ProjectMaterialEntity.build(projectMaterialEntity, userId, msUserId,
                        date, appMaterialModel.getRemainQuantity(), appMaterialModel.getUnitPrice()));
            } else {
                //// TODO: 21/05/11 单号判空问题导致used为null值，改为按单号判空并取反 zdf
                BigDecimal used = !StringUtils.isEmpty(appMaterialModel.getBillNo()) ?
                        appMaterialModel.getRemainQuantity() : appMaterialModel.getMaxQuantity();
                insertMaterialEntities.add(ProjectMaterialEntity.build(userId, msUserId, date, appMaterialModel.getId(),
                        projectId, rdType, companyId, used, used.multiply(appMaterialModel.getUnitPrice()),
                        appMaterialModel.getUnitPrice()
                ));
            }
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            if (insertMaterialEntities.size() > 0) {
                projectMaterialDao.addBatch(insertMaterialEntities);
            }
            if (updateMaterialEntities.size() > 0) {
                projectMaterialDao.updateBatch(updateMaterialEntities);
            }
            materialDao.updateRemainQuantityById(materialEntities);
            saveOrDeleteSummary(date, projectId, model.getAcqMonth(), userInfo, rdType);
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException(ErrorEnum.FAIL);
        }
        return true;
    }

    /**
     * 查询项目使用物料
     */
    @Override
    public PageResult queryProjectMaterial(Integer companyId, QueryProjectMaterialModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            page.setDescs(CollUtil.newArrayList("acqDate"));
        }
        Date startDate = null;
        Date endDate = null;
        if (query.getSelectDate() != null) {
            startDate = DateUtil.getMonthFirstDay(query.getSelectDate());
            endDate = DateUtil.getMonthLastDay(startDate);
        }
        List<AppMaterialModel> materialDataList = projectMaterialDao.queryMaterialDataIdList(
                page, companyId, startDate, endDate, query);
        for (int i = 0; i < materialDataList.size(); i++) {
            AppMaterialModel appMaterialModel = materialDataList.get(i);
            appMaterialModel.setMaxQuantity(appMaterialModel.getRemainQuantity().add(appMaterialModel.getUsed()));
            appMaterialModel.setOriginalQuantity(appMaterialModel.getQuantity());
            appMaterialModel.setOldUsed(appMaterialModel.getUsed());
        }
        return PageUtils.buildPageResult(page, materialDataList,
                projectMaterialDao.getProjectMaterialSum(companyId, startDate, endDate, query));
    }

    /**
     * 单条删除
     */
    @Override
    public boolean delMaterial(UserInfo userInfo, Integer projectId, Integer materialDataId, Date acqMonth,
                               Integer rdType, BigDecimal used, int id) throws OwnerException {
        CheckStatusModel statusModel = new CheckStatusModel(projectId, acqMonth);
        commonService.checkStatus(Arrays.asList(statusModel), Arrays.asList(rdType),userInfo);
        MaterialEntity materialEntity = materialDao.selectById(materialDataId);
        materialEntity.setRemainQuantity(materialEntity.getRemainQuantity().add(used));
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            projectMaterialDao.delMaterial(projectId, materialDataId, rdType);
            materialDao.updateById(materialEntity);
            traceDao.deleteByPid(id);
            saveOrDeleteSummary(new Date(), projectId, acqMonth, userInfo, rdType);
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException(ErrorEnum.FAIL);
        }

        return true;
    }

    /**
     * 查询可分配的库存量
     */
    @Override
    public PageResult queryMaterialAndQuantity(Integer companyId, QueryProjectMaterialModel query) {
        Pagination pagination = query.getPagination();
        if (CollectionUtils.isEmpty(pagination.getAscs()) && CollectionUtils.isEmpty(pagination.getDescs())) {
            pagination.setDescs(CollUtil.newArrayList("acqDate"));
        }
        Date startDate = null;
        Date endDate = null;
        if (query.getSelectDate() != null) {
            startDate = DateUtil.getMonthFirstDay(query.getSelectDate());
            endDate = DateUtil.getMonthLastDay(startDate);
        }

        List<AppMaterialModel> queryMaterial = materialDao.queryMaterialPage(pagination, companyId, startDate, endDate, query);
        List<AppMaterialModel> queryQuantity = queryQuantity(queryMaterial);
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 0; i < queryQuantity.size(); i++) {
            AppMaterialModel appMaterialModel = queryQuantity.get(i);
            if (appMaterialModel.getRemainQuantity().compareTo(appMaterialModel.getQuantity()) == 0) {
                sum = sum.add(appMaterialModel.getTotalAmount());
            } else {
                sum = sum.add(appMaterialModel.getRemainQuantity().multiply(appMaterialModel.getUnitPrice()));
            }
        }
        return PageUtils.buildPageResult(pagination, queryQuantity, sum);
    }

    /**
     * 条件添加
     */
    @Override
    public boolean conditionalAddition(UserInfo userInfo, AppMaterialModel model) throws OwnerException {
        CheckStatusModel statusModel = new CheckStatusModel(model.getProjectId(), model.getAcqMonth());
        commonService.checkStatus(Arrays.asList(statusModel),Arrays.asList(model.getRdType()),userInfo);
        Integer companyId = userInfo.getCompanyId();
        model.setCompanyId(companyId);
        Integer rdType = model.getRdType();

        Date selectDate = model.getSelectDate();
        if (selectDate != null) {
            model.setStartDate(DateUtil.getMonthFirstDay(selectDate));
            model.setEndDate(DateUtil.getMonthLastDay(selectDate));
        }
        List<AppMaterialModel> queryMaterial = materialDao.queryMaterialRemoveDataId(model);
        List<AppMaterialModel> queryQuantity = queryQuantity(queryMaterial);
        // 判断有没可添加的数据
        if (queryQuantity.size() == 0) {
            throw new OwnerException("没有符合的数据");
        }
        List<MaterialEntity> materialEntities = new ArrayList<MaterialEntity>();
        Date date = new Date();
        List<Integer> materialIdList = queryQuantity.stream().filter(a -> !StringUtils.isEmpty(a.getId())).map(AppMaterialModel::getId).collect(Collectors.toList());
        List<ProjectMaterialEntity> pMaterialList = projectMaterialDao.queryPMaterialList(userInfo.getCompanyId(), materialIdList, model.getProjectId(), rdType);
        Map<Integer, ProjectMaterialEntity> pMaterialMap = pMaterialList.stream().collect(Collectors.toMap(ProjectMaterialEntity::getMaterialDataId, b -> b));
        List<ProjectMaterialEntity> insertMaterialEntities = new ArrayList<ProjectMaterialEntity>();
        List<ProjectMaterialEntity> updateMaterialEntities = new ArrayList<ProjectMaterialEntity>();
        Integer userId = userInfo.getUserId(), msUserId = userInfo.getMsUserId();
        for (int i = 0; i < queryQuantity.size(); i++) {
            AppMaterialModel appMaterialModel = queryQuantity.get(i);
            MaterialEntity materialEntity = new MaterialEntity();
            materialEntity.setId(appMaterialModel.getId());
            materialEntity.setRemainQuantity(BigDecimal.ZERO);
            materialEntities.add(materialEntity);
            ProjectMaterialEntity projectMaterialEntity = pMaterialMap.get(appMaterialModel.getId());
            if (projectMaterialEntity != null) {
                updateMaterialEntities.add(ProjectMaterialEntity.build(projectMaterialEntity, userId, msUserId, date,
                        appMaterialModel.getRemainQuantity(), appMaterialModel.getUnitPrice()));
            } else {
                insertMaterialEntities.add(ProjectMaterialEntity.build(userId, msUserId, date, appMaterialModel.getId(),
                        model.getProjectId(), rdType, companyId, appMaterialModel.getRemainQuantity(),
                        appMaterialModel.getRemainQuantity().multiply(appMaterialModel.getUnitPrice()), appMaterialModel.getUnitPrice()));
            }
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            if (insertMaterialEntities.size() > 0) {
                projectMaterialDao.addBatch(insertMaterialEntities);
            }
            if (updateMaterialEntities.size() > 0) {
                projectMaterialDao.updateBatch(updateMaterialEntities);
            }
            materialDao.updateRemainQuantityById(materialEntities);
            saveOrDeleteSummary(date, model.getProjectId(), model.getAcqMonth(), userInfo, rdType);
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException(ErrorEnum.FAIL);
        }
        return true;
    }

    /**
     * 条件添加(相同出库单号添加)
     */
    @Override
    public boolean conditionalAdditionBillNo(UserInfo userInfo, AppMaterialModel model) throws OwnerException {
        CheckStatusModel statusModel = new CheckStatusModel(model.getProjectId(), model.getAcqMonth());
        commonService.checkStatus(Arrays.asList(statusModel),Arrays.asList(model.getRdType()),userInfo);
        Integer companyId = userInfo.getCompanyId();
        model.setCompanyId(companyId);
        Integer rdType = model.getRdType();
        Date selectDate = model.getSelectDate();
        if (selectDate != null) {
            model.setStartDate(DateUtil.getMonthFirstDay(selectDate));
            model.setEndDate(DateUtil.getMonthLastDay(selectDate));
        }
        List<AppMaterialModel> queryMaterial = materialDao.queryMaterialRemoveDataId(model);
        List<String> billNoList = new ArrayList<String>();
        List<AppMaterialModel> materialModels2 = new ArrayList<AppMaterialModel>();
        for (int i = 0; i < queryMaterial.size(); i++) {
            String billNo = queryMaterial.get(i).getBillNo();
            if (!StringUtils.isEmpty(billNo)) {
                if (!billNoList.contains(billNo)) {
                    billNoList.add(billNo);
                }
            } else {
                materialModels2.add(queryMaterial.get(i));
            }
        }
        List<AppMaterialModel> materialModels = new ArrayList<>();
        if (!CollectionUtils.isEmpty(billNoList)) {
            materialModels = materialDao.queryMaterialByBillNoList(companyId, billNoList,rdType);
        }
        materialModels.addAll(materialModels2);

        List<AppMaterialModel> queryQuantity = queryQuantity(materialModels);
        // 判断有没可添加的数据
        if (queryQuantity.size() == 0) {
            throw new OwnerException("没有符合的数据");
        }
        List<MaterialEntity> materialEntities = new ArrayList<MaterialEntity>();
        Date date = new Date();
        List<Integer> materialIdList = queryQuantity.stream().filter(a -> !StringUtils.isEmpty(a.getId())).map(AppMaterialModel::getId).collect(Collectors.toList());
        List<ProjectMaterialEntity> pMaterialList = projectMaterialDao.queryPMaterialList(userInfo.getCompanyId(), materialIdList, model.getProjectId(), rdType);
        Map<Integer, ProjectMaterialEntity> pMaterialMap = pMaterialList.stream().collect(Collectors.toMap(ProjectMaterialEntity::getMaterialDataId, b -> b));
        List<ProjectMaterialEntity> insertMaterialEntities = new ArrayList<ProjectMaterialEntity>();
        List<ProjectMaterialEntity> updateMaterialEntities = new ArrayList<ProjectMaterialEntity>();
        Integer userId = userInfo.getUserId(), msUserId = userInfo.getMsUserId();
        for (int i = 0; i < queryQuantity.size(); i++) {
            AppMaterialModel appMaterialModel = queryQuantity.get(i);
            MaterialEntity materialEntity = new MaterialEntity();
            materialEntity.setId(appMaterialModel.getId());
            materialEntity.setRemainQuantity(BigDecimal.ZERO);
            materialEntities.add(materialEntity);
            ProjectMaterialEntity projectMaterialEntity = pMaterialMap.get(appMaterialModel.getId());
            if (projectMaterialEntity != null) {
                updateMaterialEntities.add(ProjectMaterialEntity.build(projectMaterialEntity, userId, msUserId, date,
                        appMaterialModel.getRemainQuantity(), appMaterialModel.getUnitPrice()));
            } else {
                insertMaterialEntities.add(ProjectMaterialEntity.build(userId, msUserId, date,
                        appMaterialModel.getId(), model.getProjectId(), rdType, companyId, appMaterialModel.getRemainQuantity(),
                        appMaterialModel.getRemainQuantity().multiply(appMaterialModel.getUnitPrice()), appMaterialModel.getUnitPrice()));
            }
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            if (insertMaterialEntities.size() > 0) {
                projectMaterialDao.addBatch(insertMaterialEntities);
            }
            if (updateMaterialEntities.size() > 0) {
                projectMaterialDao.updateBatch(updateMaterialEntities);
            }
            materialDao.updateRemainQuantityById(materialEntities);
            saveOrDeleteSummary(date, model.getProjectId(), model.getAcqMonth(), userInfo, rdType);
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException(ErrorEnum.FAIL);
        }
        return true;
    }

    /**
     * 查询库存
     *
     * @param queryMaterial
     * @return
     */
    public List<AppMaterialModel> queryQuantity(List<AppMaterialModel> queryMaterial) {
        for (int i = 0; i < queryMaterial.size(); i++) {
            AppMaterialModel appMaterialModel = queryMaterial.get(i);
            queryMaterial.get(i).setMaxQuantity(appMaterialModel.getRemainQuantity());
            queryMaterial.get(i).setOriginalQuantity(queryMaterial.get(i).getQuantity());
        }
        return queryMaterial;
    }


    @Override
    public boolean delSelect(UserInfo userInfo, ProjectMaterialModel model)
            throws OwnerException {
        CheckStatusModel statusModel = new CheckStatusModel(model.getProjectId(), model.getAcqMonth());
        commonService.checkStatus(Arrays.asList(statusModel),Arrays.asList(model.getRdType()),userInfo);
        List<AppMaterialModel> modelList = model.getModelList();

        List<Integer> ids = new ArrayList<Integer>();
        List<MaterialEntity> materialEntities = new ArrayList<MaterialEntity>();
        for (int i = 0; i < modelList.size(); i++) {
            MaterialEntity entity = new MaterialEntity();
            AppMaterialModel appMaterialModel = modelList.get(i);
            entity.setId(appMaterialModel.getMaterialDataId());
            entity.setRemainQuantity(appMaterialModel.getRemainQuantity().add(appMaterialModel.getUsed()));
            materialEntities.add(entity);
            ids.add(appMaterialModel.getId());
        }

        TransactionStatus transactionStatus = TransactionUtils.newTransaction();

        try {
            materialDao.updateRemainQuantityById(materialEntities);
            deleteBatchIds(ids);
            traceDao.deleteByPids(ids);
            saveOrDeleteSummary(new Date(), model.getProjectId(), model.getAcqMonth(), userInfo, model.getRdType());
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException(ErrorEnum.FAIL);
        }
        return false;
    }

    @Override
    public boolean editMaterial(UserInfo userInfo, List<AppMaterialModel> modelList) throws OwnerException {
        Integer companyId = userInfo.getCompanyId();
        Integer projectId = modelList.get(0).getProjectId();
        Integer rdType = modelList.get(0).getRdType();
        Date now = new Date();
        Date acqMonth = DateUtil.getMonthFirstDay(modelList.get(0).getAcqDate());
        Set<Integer> rdTypes = new HashSet<>();
        modelList.forEach(item->{
            rdTypes.add(item.getRdType());
        });
        CheckStatusModel statusModel = new CheckStatusModel(projectId, acqMonth);
        commonService.checkStatus(Arrays.asList(statusModel),new ArrayList<Integer>(rdTypes),userInfo);
        List<ProjectMaterialEntity> projectMaterialEntities = new ArrayList<ProjectMaterialEntity>();
        List<MaterialEntity> materialEntities = new ArrayList<MaterialEntity>();
        for (int i = 0; i < modelList.size(); i++) {
            AppMaterialModel model = modelList.get(i);

            BigDecimal used = model.getUsed();
            ProjectMaterialEntity projectMaterialEntity = projectMaterialDao
                    .queryProjectMaterialByMaterialIdAndProjectId(companyId, model.getMaterialDataId(), projectId,
                            rdType);
            MaterialEntity materialEntity = materialDao.selectById(model.getMaterialDataId());
            materialEntity.setRemainQuantity(materialEntity.getRemainQuantity().add(model.getOldUsed().subtract(model.getUsed())));
            if (used.abs().compareTo(model.getRemainQuantity().add(model.getOldUsed()).abs()) == 1) {
                throw new OwnerException(ErrorEnum.MATERIAL_INSUFFICIENT_STOCK);
            }
            projectMaterialEntity.setUsed(used);
            projectMaterialEntity.setLastUpdateTime(now);
            projectMaterialEntity.setMsLastUpdatorId(userInfo.getMsUserId());
            projectMaterialEntity.setLastUpdatorId(userInfo.getUserId());
            if (rdType == CostEnum.PAPER_MATERIAL.getType() || rdType == CostEnum.PAPER_TRIAL.getType()) {
                projectMaterialEntity.setRdAmount(model.getUnitPrice().multiply(model.getUsed()).subtract(model.getFinishAmount().add(model.getWasteAmount())));
            } else if (rdType == CostEnum.IRON_MATERIAL.getType() || rdType == CostEnum.IRON_TRIAL.getType()) {
                projectMaterialEntity.setRdAmount(model.getUnitPrice().multiply(model.getUsed()).multiply(model.getDepreciationRatio()));
            } else {
                projectMaterialEntity.setRdAmount(used.multiply(model.getUnitPrice()));
            }
            projectMaterialEntity.setFinishAmount(model.getFinishAmount());
            projectMaterialEntity.setFinishQuantity(model.getFinishQuantity());
            projectMaterialEntity.setFinishUnitPrice(model.getFinishUnitPrice());
            projectMaterialEntity.setWasteAmount(model.getWasteAmount());
            projectMaterialEntity.setWasteQuantity(model.getWasteQuantity());
            projectMaterialEntity.setWasteUnitPrice(model.getWasteUnitPrice());
            projectMaterialEntity.setTotalYield(model.getTotalYield());
            projectMaterialEntity.setRdYield(model.getRdYield());
            projectMaterialEntity.setDepreciationRatio(model.getDepreciationRatio());
            projectMaterialEntities.add(projectMaterialEntity);
            materialEntities.add(materialEntity);
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            materialDao.updateRemainQuantityById(materialEntities);
            projectMaterialDao.updateUsedById(projectMaterialEntities);
            saveOrDeleteSummary(now, projectId, acqMonth, userInfo, rdType);
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException(ErrorEnum.FAIL);
        }

        return true;
    }

    @Override
    public PageModel<List<DocMaterialModel>> getDocMaterialList(UserInfo userInfo, QueryProjectMaterialModel query) {
        Pagination page = new Pagination(query.getPageNo(), query.getPageSize());
        return PageUtils.build(page, projectMaterialDao.getDocMaterialList(page, query, userInfo.getCompanyId()));
    }

    @Override
    public PageModel<List<AppMaterialModel>> queryMaterialTrack(Integer companyId, QueryMaterialTrackModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> descs = new ArrayList<>();
            descs.add("d.acqDate");
            page.setDescs(descs);
        }
        if (query.getProjectId() == 0) {
            return PageUtils.build(page, new ArrayList<>());
        }
        return PageUtils.build(page, projectMaterialDao.queryMaterialTrack(page, companyId, query));
    }

    @Override
    public boolean editMaterialTrack(UserInfo userInfo, List<AppMaterialModel> model) throws OwnerException {
        Date now = new Date();
        List<AppMaterialModel> insertModels = new ArrayList<>();
        List<AppMaterialModel> updateModels = new ArrayList<>();
        model.forEach(item -> {
            item.setLastUpdateTime(now);
            item.setMsLastUpdatorId(userInfo.getMsUserId());
            item.setLastUpdatorId(userInfo.getUserId());
            BigDecimal totalPrice;
            if (item.getUsed().compareTo(item.getQuantity()) == 0) {
                totalPrice = item.getTotalAmount();
            } else {
                totalPrice = item.getUsed().multiply(item.getUnitPrice());
            }
            item.setRdOutPut(item.getUsed().multiply(item.getRdOutputRate()));
            item.setRdOutputAmount(totalPrice.multiply(item.getRdOutputRate()));
            item.setRdLoss(item.getUsed().multiply(item.getRdLossRate()));
            item.setRdLossAmount(totalPrice.multiply(item.getRdLossRate()));
            item.setScrap(item.getUsed().multiply(item.getScrapRate()));
            item.setScrapAmount(totalPrice.multiply(item.getScrapRate()));
            if (item.getId() == null) {
                item.setMsCreatorId(userInfo.getMsUserId());
                item.setCreatorId(userInfo.getUserId());
                insertModels.add(item);
            } else {
                updateModels.add(item);
            }
        });
        if (insertModels.size() > 0) {
            traceDao.insertTrace(insertModels);
        }
        if (updateModels.size() > 0) {
            traceDao.updateTrace(updateModels);
        }
        return true;
    }

    @Override
    public boolean setDepreciationRatio(UserInfo userInfo, DepreciationRatioModel ratioModel) throws OwnerException {
        List<AppMaterialModel> dataList = projectMaterialDao.getByIds(ratioModel.getIds());
        if (CollectionUtils.isEmpty(dataList)) {
            throw new OwnerException("当前数据不存在，设置失败。");
        }
        CheckStatusModel statusModel = new CheckStatusModel(ratioModel.getProjectId(), DateUtil.getMonthFirstDay(ratioModel.getMonth()));
        commonService.checkStatus(Arrays.asList(statusModel), Arrays.asList(ratioModel.getRdType()), userInfo);
        Date now = new Date();
        BigDecimal ratio = ratioModel.getDepreciationRatio();
        dataList.forEach(item -> {
            item.setRdAmount(item.getUnitPrice().multiply(item.getUsed()).multiply(ratio));
            item.setLastUpdateTime(now);
            item.setDepreciationRatio(ratio);
            item.setLastUpdatorId(userInfo.getUserId());
            item.setMsLastUpdatorId(userInfo.getMsUserId());
        });
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction();
            projectMaterialDao.setDepreciationRatio(dataList);
            saveOrDeleteSummary(now, ratioModel.getProjectId(), ratioModel.getMonth(), userInfo, ratioModel.getRdType());
            TransactionUtils.commit(transactionStatus);
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException("保存失败，请联系管理员。");
        }
        return true;
    }

    @Override
    public boolean setYield(UserInfo userInfo, Map<String, ProjectYieldConfigModel> yieldMap, Map<String, BigDecimal> ratioMap, QueryYieldConfigModel config) throws OwnerException {
        Date month = DateUtil.getMonthFirstDay(config.getMonth());
        CheckStatusModel statusModel = new CheckStatusModel(config.getProjectId(), month);
        commonService.checkStatus(Arrays.asList(statusModel),Arrays.asList(config.getRdType()),userInfo);
        List<AppMaterialModel> dataList = projectMaterialDao.getMaterialList(month,
                DateUtil.getMonthLastDay(month), config.getProjectId(), config.getRdType());
        if (CollectionUtils.isEmpty(dataList)) {
            throw new OwnerException("应用失败，未获取到归集数据。");
        }
        List<AppMaterialModel> updateProjectMaterialList = new ArrayList<>();
        List<MaterialEntity> updateMaterialList = new ArrayList<>();
        Date now = new Date();
        String keyFormat = "{0}{1}";
        dataList.forEach(item -> {
            String key = MessageFormat.format(keyFormat,DateUtil.format(item.getAcqDate(),DateUtil.DEFAULT_DATE_FORMAT),item.getDeptName());
            ProjectYieldConfigModel yield = yieldMap.get(key);
            if (null != yield) {
                BigDecimal used = ratioMap.get(key).multiply(item.getQuantity());
                BigDecimal maxUsed = item.getRemainQuantity().add(item.getUsed());
                if (used.abs().compareTo(maxUsed.abs()) == 1) {
                    used = maxUsed;
                }
                item.setUsed(used);
                item.setLastUpdateTime(now);
                item.setLastUpdatorId(userInfo.getUserId());
                item.setMsLastUpdatorId(userInfo.getMsUserId());
                item.setTotalYield(yield.getTotalYield());
                item.setRdYield(yield.getRdYield());
                item.setRdAmount(item.getUnitPrice().multiply(used.multiply(item.getDepreciationRatio())));
                updateProjectMaterialList.add(item);
                updateMaterialList.add(MaterialEntity.buildRemain(item.getMaterialDataId(), maxUsed.subtract(used)));
            }
        });
        if (CollectionUtils.isEmpty(updateProjectMaterialList)) {
            throw new OwnerException("应用失败，未获取到对应部门的归集数据。");
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction();
            projectMaterialDao.updateYield(updateProjectMaterialList);
            materialDao.updateRemainQuantityById(updateMaterialList);
            saveOrDeleteSummary(now, config.getProjectId(), month, userInfo, config.getRdType());
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException("应用失败，保存数据出错。");
        }
    }

    private void saveOrDeleteSummary(Date now, Integer projectId, Date month, UserInfo info, Integer type) {
        Date startDate = DateUtil.getMonthFirstDay(month);
        Date endDate = DateUtil.getMonthLastDay(startDate);
        // 查出该项目该月份内的物料list
        BigDecimal rdFunds = projectMaterialDao
                .queryMaterialListByProjectIdAndDateAndType(info.getCompanyId(), projectId, startDate, endDate,
                        type);
        if (rdFunds == null || rdFunds.compareTo(BigDecimal.ZERO) == 0) {
            summaryDao.deleteInfo(projectId, startDate, CollUtil.newArrayList(type));
            return;
        }
        summaryDao.insertOrUpdate(CollUtil.newArrayList(
                ToolUtils.build(now, startDate, projectId, type, rdFunds, info)
        ));
    }
}
