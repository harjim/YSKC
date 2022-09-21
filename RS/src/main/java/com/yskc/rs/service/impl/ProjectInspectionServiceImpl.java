package com.yskc.rs.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.dao.InspectionDao;
import com.yskc.rs.dao.SummaryDao;
import com.yskc.rs.dao.project.ProjectInspectionDao;
import com.yskc.rs.entity.InspectionEntity;
import com.yskc.rs.entity.SummaryEntity;
import com.yskc.rs.entity.project.ProjectInspectionEntity;
import com.yskc.rs.enums.AuditRdTypeEnum;
import com.yskc.rs.models.CheckStatusModel;
import com.yskc.rs.models.UsedModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.hightech.QueryInspectionModel;
import com.yskc.rs.models.projectinspection.*;
import com.yskc.rs.service.ProjectInspectionService;
import com.yskc.rs.utils.ToolUtils;
import com.yskc.rs.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@Service
public class ProjectInspectionServiceImpl implements ProjectInspectionService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProjectInspectionDao projectInspectionDao;
    @Autowired
    private InspectionDao inspectionDao;
    @Autowired
    private SummaryDao summaryDao;
    @Autowired
    private CommonService commonService;

    @Override
    public PageModel<List<ProjectInspectionModel>> queryProjectInspection(Integer companyId,
                                                                          QueryProjectInspectionModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("accDate");
            page.setDescs(desc);
        }
        Date beginDate = null;
        Date endDate = null;
        if (query.getProjectMonth() != null) {
            beginDate = DateUtil.beginOfMonth(query.getProjectMonth());
            endDate = DateUtil.endOfMonth(beginDate);
        }
        List<ProjectInspectionModel> models = projectInspectionDao.queryProjectInspection(page, companyId, beginDate, endDate, query);
        if (!CollectionUtils.isEmpty(models)) {
            List<Integer> ids = models.stream().map(ProjectInspectionModel::getInspectionDataId).collect(Collectors.toList());
            List<UsedModel> usedModels = projectInspectionDao.getUsedList(ids, query.getProjectId());
            Map<Integer, List<UsedModel>> dataMap = new HashMap<>();
            usedModels.forEach(item -> {
                if (!dataMap.containsKey(item.getId())) {
                    List<UsedModel> modelList = new ArrayList<>();
                    dataMap.put(item.getId(), modelList);
                }
                dataMap.get(item.getId()).add(item);
            });
            models.forEach(model -> {
                model.setUsedModels(dataMap.get(model.getInspectionDataId()));
            });
        }
        return PageUtils.buildPageResult(
                page, models,
                null,
                query.getTypes().length == 5 ? projectInspectionDao.getOtherSum(companyId, beginDate, endDate, query) :
                        query.getTypes().length == 3 ?
                                projectInspectionDao.getAmortizationSum(companyId, beginDate, endDate, query) :
                                projectInspectionDao.getProjectInspectionSum(companyId, beginDate, endDate, query));
    }

    @Override
    public PageModel<List<HighTechInspectionModel>> getProjectInspection(QueryInspectionModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("accDate");
            page.setDescs(desc);
        }
        Date beginDate = null;
        Date endDate = null;
        if (query.getMonth() != null) {
            beginDate = DateUtil.beginOfMonth(query.getMonth());
            endDate = DateUtil.endOfMonth(beginDate);
        }
        List<HighTechInspectionModel> modelList = projectInspectionDao.getProjectInspection(page, beginDate, endDate, query);
        return PageUtils.build(page,modelList);
    }


    @Override
    public boolean addProjectInspection(UserInfo info, BatchProjectInspectionModel batch) throws OwnerException {
        Date beginDate = com.yskc.common.utils.DateUtil.getMonthFirstDay(batch.getProjectMonth());
        List<CheckStatusModel> models = new ArrayList<>();
        CheckStatusModel checkStatusModel = new CheckStatusModel();
        checkStatusModel.setMonth(beginDate);
        checkStatusModel.setProjectId(batch.getProjectId());
        models.add(checkStatusModel);
        List<Integer> rdTypes = new ArrayList<>();
        rdTypes.add(batch.getInspectionEntities().get(0).getType());
        commonService.checkStatus(models,rdTypes,info);
        List<InspectionEntity> entityList = batch.getInspectionEntities();
        Date endDate = com.yskc.common.utils.DateUtil.getMonthLastDay(beginDate);
        Date now = new Date();
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            List<Integer> inspectIds = entityList.stream().map(InspectionEntity::getId).collect(Collectors.toList());
            List<InspectionEntity> inspectionEntities = inspectionDao.selectBatchIds(inspectIds);
            inspectionDao.updateAmount(inspectIds, now, info.getMsUserId(), info.getUserId());
            projectInspectionDao.addInspectionBatch(setValue(info, inspectionEntities, batch.getProjectId()));
            insertSummaryEntity(batch.getProjectId(), now, info, beginDate, endDate);
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException(ErrorEnum.FAIL);
        }
        return true;
    }


    /**
     * 获取汇总信息表数据
     *
     * @param now
     * @param userInfo
     * @param projectInspectionModels
     * @return
     */
    private List<SummaryEntity> getSummaryEntityList(Date now, UserInfo userInfo, List<ProjectInspectionModel> projectInspectionModels) {
        List<SummaryEntity> summaryEntities = new ArrayList<>();
        Map<Date, Map<Integer, BigDecimal>> monthTypeMap = new HashMap<>();
        projectInspectionModels.forEach((model) -> {
            Date month = DateUtil.beginOfMonth(model.getAccDate());
            if (!monthTypeMap.containsKey(month)) {
                monthTypeMap.put(month, new HashMap<>());
            }
            monthTypeMap.get(month).put(model.getType(),
                    monthTypeMap.get(month).getOrDefault(model.getType(), BigDecimal.ZERO).add(model.getExpense()));
        });

        Integer projectId = projectInspectionModels.get(0).getProjectId();
        monthTypeMap.forEach((month, typeMap) -> {
            typeMap.forEach((type, value) -> {
                summaryEntities.add(ToolUtils.build(now, month, projectId, type, value, userInfo));
            });
        });
        return summaryEntities;
    }

    /**
     * 添加汇总信息
     *
     * @param projectId
     * @param now
     * @param userInfo
     */
    private void insertSummaryEntity(Integer projectId, Date now, UserInfo userInfo, Date beginDate, Date endDate) {
        List<ProjectInspectionModel> projectDesignModelList = projectInspectionDao.getInspectionDate(projectId, beginDate, endDate, null);
        projectDesignModelList.remove(null);
        if (projectDesignModelList.size() > 0) {
            List<SummaryEntity> summaryEntities = getSummaryEntityList(now, userInfo, projectDesignModelList);
            summaryDao.insertOrUpdate(summaryEntities);
        }
    }

    /**
     * 删除
     *
     * @param projectId
     * @param now
     * @param userInfo
     * @param month
     */
    private void deleteSummaryEntity(Integer projectId, Date now, UserInfo userInfo, Date month, Date beginDate, Date endDate, List<Integer> types) {
        List<ProjectInspectionModel> projectInspectionModels = projectInspectionDao.getInspectionDate(projectId, beginDate, endDate, types);
        projectInspectionModels.remove(null);
        Set<Integer> hasDataType = new HashSet<>();
        if (projectInspectionModels.size() > 0) {
            List<SummaryEntity> summaryEntities = getSummaryEntityList(now, userInfo, projectInspectionModels);
            if(!CollectionUtils.isEmpty(summaryEntities)){
                summaryEntities.forEach(item->hasDataType.add(item.getRdType()));
                summaryDao.insertOrUpdate(summaryEntities);
            }
        }
        Collection<Integer> deleteTypes = CollUtil.disjunction(hasDataType, types);
        if(!CollUtil.isEmpty(deleteTypes)){
            summaryDao.deleteInfo(projectId, month, new ArrayList<>(deleteTypes));
        }
    }

    @Override
    public boolean addProjectInspectionByTerm(UserInfo info, QueryProjectInspectionModel query) throws OwnerException {
        Date beginDate = DateUtil.beginOfMonth(query.getProjectMonth());
        Integer projectId = query.getProjectId();
        String[] types = query.getTypes();
        List<CheckStatusModel> models = new ArrayList<>();
        CheckStatusModel checkStatusModel = new CheckStatusModel();
        checkStatusModel.setMonth(beginDate);
        checkStatusModel.setProjectId(projectId);
        models.add(checkStatusModel);
        List<Integer> rdTypes = new ArrayList<>();
        for (String type : types) {
            rdTypes.add(Integer.parseInt(type));
        }
        commonService.checkStatus(models,rdTypes,info);
        Date endDate = DateUtil.endOfMonth(beginDate);
        List<InspectionEntity> entities = inspectionDao.queryInspectionDataByTerm(info.getCompanyId(), beginDate, endDate, query);
        Date now = new Date();
        if (entities.size() == 0) {
            throw new OwnerException(ErrorEnum.INSPECTION_NO_CONSISTENT_DATA);
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            List<Integer> inspectIds = entities.stream().map(InspectionEntity::getId).collect(Collectors.toList());
            inspectionDao.updateAmount(inspectIds, now, info.getMsUserId(), info.getUserId());
            projectInspectionDao.addInspectionBatch(setValue(info, entities, projectId));
            insertSummaryEntity(projectId, now, info, beginDate, endDate);
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException(ErrorEnum.FAIL);
        }
        return true;
    }

    private List<ProjectInspectionEntity> setValue(UserInfo info, List<InspectionEntity> entities, Integer projectId) {
        List<ProjectInspectionEntity> entityList = new ArrayList<>();
        Date now = new Date();
        for (int i = 0; i < entities.size(); i++) {
            ProjectInspectionEntity entity = new ProjectInspectionEntity();
            entity.setCompanyId(info.getCompanyId());
            entity.setCreatorId(info.getUserId());
            entity.setInspectionDataId(entities.get(i).getId());
            entity.setProjectId(projectId);
            entity.setLastUpdatorId(info.getUserId());
            entity.setLastUpdateTime(now);
            entity.setCreateTime(now);
            entity.setMsCreatorId(info.getMsUserId());
            entity.setMsLastUpdatorId(info.getMsUserId());
            entity.setRdAmount(entities.get(i).getRemainExpense());
            entityList.add(entity);
        }
        return entityList;
    }

    @Override
    public boolean delInspectionBatch(UserInfo userInfo, BatchProjectInspectionModel batch) throws OwnerException {
        Date month = com.yskc.common.utils.DateUtil.getMonthFirstDay(batch.getProjectMonth());
        Integer projectId = batch.getProjectId();
        List<CheckStatusModel> models = new ArrayList<>();
        CheckStatusModel checkStatusModel = new CheckStatusModel();
        checkStatusModel.setMonth(month);
        checkStatusModel.setProjectId(batch.getProjectId());
        models.add(checkStatusModel);
        List<Integer> rdTypes = new ArrayList<>();
        rdTypes.add(batch.getInspectionEntities().get(0).getType());
        commonService.checkStatus(models,rdTypes,userInfo);
        Date endDate = com.yskc.common.utils.DateUtil.getMonthLastDay(month);
        List<Integer> projectInspectionIds = new ArrayList<Integer>();
        Set<Integer> projectTypes = new HashSet<>();
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            batch.getInspectionEntities().forEach(item -> {
                projectTypes.add(item.getType());
                projectInspectionIds.add(item.getId());
            });
            List<ProjectInspectionEntity> inspects = projectInspectionDao.selectBatchIds(projectInspectionIds);
            Map<Integer, BigDecimal> dataMap = new HashMap<>();
            List<Integer> integerIds = new ArrayList<>();
            inspects.forEach(item -> {
                dataMap.put(item.getInspectionDataId(), item.getRdAmount());
                integerIds.add(item.getInspectionDataId());
            });
            if (CollectionUtils.isEmpty(integerIds)) {
                return true;
            }
            List<InspectionEntity> inspectionEntities = inspectionDao.selectBatchIds(integerIds);
            inspectionEntities.forEach(item -> {
                item.setLastUpdateTime(new Date());
                item.setLastUpdatorId(userInfo.getUserId());
                item.setMsLastUpdatorId(userInfo.getMsUserId());
                item.setRemainExpense(item.getRemainExpense().add(dataMap.get(item.getId())));
            });
            inspectionDao.updateList(inspectionEntities);
            projectInspectionDao.deleteBatchIds(projectInspectionIds);
            deleteSummaryEntity(projectId, new Date(), userInfo, month, month, endDate, new ArrayList<>(projectTypes));
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException(ErrorEnum.FAIL);
        }
        return true;
    }

    @Override
    public boolean setInspectAmounts(UserInfo userInfo, SetInspectionAmountModel batch) throws OwnerException {
        Date date = new Date();
        if (CollectionUtils.isEmpty(batch.getModels())) {
            throw new OwnerException("请选择要修改的数据");
        }
        List<Integer> ids = batch.getModels().stream().map(ProjectInspectionModel::getId).collect(Collectors.toList());
        List<ProjectInspectionEntity> entityList = projectInspectionDao.selectBatchIds(ids);
        if (CollectionUtils.isEmpty(entityList)) {
            throw new OwnerException("选择数据已删除或不存在，设置失败");
        }
        CheckStatusModel statusModel = new CheckStatusModel(batch.getProjectId(),
                com.yskc.common.utils.DateUtil.getMonthFirstDay(batch.getModels().get(0).getAccDate()));
        commonService.checkStatus(Arrays.asList(statusModel), Arrays.asList(AuditRdTypeEnum.SAMPLE_MACHINE.getRdType()), userInfo);
        Map<Integer, BigDecimal> dataMap = new HashMap<>();
        List<Integer> inspectIds = new ArrayList<>();
        List<Integer> projectInspectIds = new ArrayList<>();
        entityList.forEach(item -> {
            dataMap.put(item.getInspectionDataId(), item.getRdAmount());
            inspectIds.add(item.getInspectionDataId());
            projectInspectIds.add(item.getId());
        });
        List<InspectionEntity> inspects = inspectionDao.selectBatchIds(inspectIds);
        for (InspectionEntity item : inspects) {
            BigDecimal maxAmount = item.getRemainExpense().add(dataMap.get(item.getId()));
            if (maxAmount.compareTo(batch.getSetAmount()) < 0) {
                String message = MessageFormat.format("{0}设置的研发费用不能高于最大可归集的费用（{1}）", item.getAccNumber(), maxAmount);
                throw new OwnerException(message);
            }
            item.setRemainExpense(maxAmount.subtract(batch.getSetAmount()));
            item.setLastUpdateTime(date);
            item.setLastUpdatorId(userInfo.getUserId());
            item.setMsLastUpdatorId(userInfo.getMsUserId());
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            Date beginDate = DateUtil.beginOfMonth(batch.getProjectMonth());
            Date endDate = DateUtil.endOfMonth(batch.getProjectMonth());
            inspectionDao.updateList(inspects);
            projectInspectionDao.updateList(projectInspectIds, date, userInfo.getUserId(), userInfo.getMsUserId(), batch.getSetAmount());
            insertSummaryEntity(batch.getProjectId(), date, userInfo, beginDate, endDate);
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException(ErrorEnum.FAIL);
        }
        return true;
    }

    @Override
    public boolean setRdAmounts(UserInfo userInfo, SetInspectionAmountModel batch) throws OwnerException {
        Date date = new Date();
        if (CollectionUtils.isEmpty(batch.getModels())) {
            return true;
        }
        ProjectInspectionModel inspectionModel = batch.getModels().get(0);
        commonService.checkStatus(Arrays.asList(new CheckStatusModel(inspectionModel.getProjectId(), com.yskc.common.utils.DateUtil.getMonthFirstDay(inspectionModel.getAccDate()))),
                Arrays.asList(AuditRdTypeEnum.INSPECTION.getRdType()), userInfo);
        List<Integer> pInspectIds = new ArrayList<>();
        Map<Integer, BigDecimal> dataMap = new HashMap<>();
        for (ProjectInspectionModel model : batch.getModels()) {
            pInspectIds.add(model.getId());
            dataMap.put(model.getId(), model.getRdAmount());
        }
        List<ProjectInspectionEntity> pInspects = projectInspectionDao.selectBatchIds(pInspectIds);
        List<Integer> inspectIds = pInspects.stream().map(a -> a.getInspectionDataId()).collect(Collectors.toList());
        List<InspectionEntity> inspects = inspectionDao.selectBatchIds(inspectIds);
        Map<Integer, BigDecimal> pDataMap = new HashMap<>();
        Map<Integer, BigDecimal> paramMap = new HashMap<>();
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        for (ProjectInspectionEntity pEntity : pInspects) {
            pDataMap.put(pEntity.getInspectionDataId(), pEntity.getRdAmount());
            paramMap.put(pEntity.getInspectionDataId(), dataMap.get(pEntity.getId()));
            pEntity.setRdAmount(dataMap.get(pEntity.getId()));
            pEntity.setLastUpdateTime(date);
            pEntity.setLastUpdatorId(userInfo.getUserId());
            pEntity.setMsLastUpdatorId(userInfo.getMsUserId());
        }
        for (InspectionEntity inspect : inspects) {
            BigDecimal maxAmount = inspect.getRemainExpense().add(pDataMap.get(inspect.getId()));
            if (maxAmount.compareTo(paramMap.get(inspect.getId())) < 0) {
                String message = MessageFormat.format("{0}设置的研发费用不能高于最大可归集的费用（{1}）", inspect.getAccNumber(), maxAmount);
                throw new OwnerException(message);
            }
            inspect.setRemainExpense(maxAmount.subtract(paramMap.get(inspect.getId())));
            inspect.setLastUpdatorId(userInfo.getUserId());
            inspect.setLastUpdateTime(date);
            inspect.setMsLastUpdatorId(userInfo.getMsUserId());
        }
        try {
            Date beginDate = DateUtil.beginOfMonth(batch.getProjectMonth());
            Date endDate = DateUtil.endOfMonth(batch.getProjectMonth());
            projectInspectionDao.updateRdAmounts(pInspects);
            inspectionDao.updateList(inspects);
            insertSummaryEntity(batch.getProjectId(), date, userInfo, beginDate, endDate);
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException(ErrorEnum.FAIL);
        }
        return true;

    }
}
