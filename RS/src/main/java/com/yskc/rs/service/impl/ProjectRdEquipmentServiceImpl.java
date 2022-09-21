package com.yskc.rs.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.PageResult;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.dao.ProjectDao;
import com.yskc.rs.dao.StageDao;
import com.yskc.rs.dao.SummaryDao;
import com.yskc.rs.dao.data.DataEquipmentDao;
import com.yskc.rs.dao.init.InitEquipmentDao;
import com.yskc.rs.dao.project.EquipmentUsedDao;
import com.yskc.rs.dao.project.ProjectEquipmentDao;
import com.yskc.rs.dao.project.ProjectRdEquipmentDao;
import com.yskc.rs.entity.StageEntity;
import com.yskc.rs.entity.SummaryEntity;
import com.yskc.rs.entity.init.InitEquipmentEntity;
import com.yskc.rs.entity.project.*;
import com.yskc.rs.enums.AuditRdTypeEnum;
import com.yskc.rs.enums.CostEnum;
import com.yskc.rs.models.CheckStatusModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.data.DataEquipmentInfoModel;
import com.yskc.rs.models.init.equipment.InitEquipmentMiniModel;
import com.yskc.rs.models.project.RdUsedHourModel;
import com.yskc.rs.models.projectequipment.RdEquipmentResultModel;
import com.yskc.rs.models.projectfinaschedule.ProjectFinaScheduleModel;
import com.yskc.rs.models.projectrdequipment.*;
import com.yskc.rs.models.projectyieldconfig.AggMsgModel;
import com.yskc.rs.models.projectyieldconfig.ProjectYieldConfigInfoModel;
import com.yskc.rs.models.projectyieldconfig.ProjectYieldConfigTotalModel;
import com.yskc.rs.models.projectyieldconfig.TrialAggModel;
import com.yskc.rs.service.CompanySettingService;
import com.yskc.rs.service.ProjectRdEquipmentService;
import com.yskc.rs.utils.AttDataUtils;
import com.yskc.rs.utils.ListUtils;
import com.yskc.rs.utils.ToolUtils;
import com.yskc.rs.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: zhangdingfu
 * @CreateTime: 2020-04-01 11:02
 * @Description: 项目设备研发折旧业务实现层
 */
@Service
public class ProjectRdEquipmentServiceImpl implements ProjectRdEquipmentService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProjectRdEquipmentDao projectRdEquipmentDao;
    @Autowired
    private DataEquipmentDao dataEquipmentDao;
    @Autowired
    private SummaryDao summaryDao;
    @Autowired
    private CommonService commonService;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private InitEquipmentDao initEquipmentDao;
    @Autowired
    private ProjectEquipmentDao projectEquipmentDao;
    @Autowired
    private EquipmentUsedDao equipmentUsedDao;
    @Autowired
    private StageDao stageDao;
    @Autowired
    private CompanySettingService companySettingService;

    @Override
    public PageResult getList(UserInfo userInfo, QueryProjectRdEquipmentModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            page.setAscs(CollUtil.newArrayList("ecode"));
        }
        List<ProjectRdEquipmentModel> list = projectRdEquipmentDao.getList(page, userInfo.getCompanyId(), query);
        setUsedList(list, userInfo, query);
        return PageUtils.buildPageResult(page, list, companySettingService.getEquipmentHourBit(userInfo.getCompanyId()), projectRdEquipmentDao.getTotal(userInfo.getCompanyId(), query));
    }

    @Override
    public PageResult getEquipmentPowerList(UserInfo userInfo, QueryProjectRdEquipmentModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("ecode");
            page.setDescs(desc);
        }
        List<ProjectRdEquipmentModel> list = projectRdEquipmentDao.getEquipmentPowerList(page, userInfo.getCompanyId(), query);
        setUsedList(list, userInfo, query);
        return PageUtils.buildPageResult(page, list);
    }

    @Override
    public Boolean saveList(UserInfo userInfo, BatchProjectRdEquipmentModel batchModel) throws OwnerException {
        Date month = DateUtil.getMonthFirstDay(batchModel.getMonth());
        Integer projectId = batchModel.getProjectId();
        List<CheckStatusModel> models = new ArrayList<>();
        CheckStatusModel checkStatusModel = new CheckStatusModel();
        checkStatusModel.setMonth(month);
        checkStatusModel.setProjectId(batchModel.getProjectId());
        models.add(checkStatusModel);
        List<Integer> rdTypes = new ArrayList<>();
        rdTypes.add(batchModel.getList().get(0).getEtype());
        batchModel.getList().forEach(item -> {
            item.setMonth(month);
            item.setProjectId(projectId);
        });
        return saveData(CollUtil.newArrayList(month), CollUtil.newArrayList(projectId), batchModel.getList(), userInfo, companySettingService.getEquipmentHourBit(userInfo.getCompanyId()));
    }

    @Override
    public Boolean setPowerUnitPrice(UserInfo userInfo, ProjectRdEquipmentEntity entity) throws OwnerException {
        List<CheckStatusModel> models = new ArrayList<>();
        CheckStatusModel checkStatusModel = new CheckStatusModel();
        checkStatusModel.setMonth(entity.getMonth());
        checkStatusModel.setProjectId(entity.getProjectId());
        models.add(checkStatusModel);
        List<Integer> rdTypes = new ArrayList<>();
        rdTypes.add(AuditRdTypeEnum.EQUIPMENT.getRdType());
        commonService.checkStatus(models, rdTypes, userInfo);
        List<ProjectRdEquipmentModel> projectRdEquipmentModels = projectRdEquipmentDao.queryByProjectIdAndMonth(entity.getProjectId(), entity.getMonth());
        List<ProjectRdEquipmentEntity> entityList = new ArrayList<>();
        BigDecimal totalFunds = BigDecimal.ZERO;
        int hourBit = companySettingService.getEquipmentHourBit(userInfo.getCompanyId());
        BigDecimal powerUnitPrice;
        for (ProjectRdEquipmentModel model : projectRdEquipmentModels) {
            ProjectRdEquipmentEntity equipmentEntity = new ProjectRdEquipmentEntity();
            equipmentEntity.setId(model.getId());
            BigDecimal powerNum = model.getRdHourByBit(hourBit).multiply(model.getUsagePower());
            powerUnitPrice = entity.getPowerUnitPrice();
            equipmentEntity.setPowerUnitPrice(powerUnitPrice);
            equipmentEntity.setPowerRate(powerNum.multiply(powerUnitPrice));
            entityList.add(equipmentEntity);
            totalFunds = totalFunds.add(equipmentEntity.getPowerRate());
        }
        if (entityList.size() > 0) {
            Integer userId = userInfo.getUserId();
            Integer msUserId = userInfo.getMsUserId();
            TransactionStatus transactionStatus = TransactionUtils.newTransaction();
            try {
                Date date = new Date();
                projectRdEquipmentDao.updateBatch(entityList, date, userId, msUserId);
                List<SummaryEntity> summaryEntities = new ArrayList<SummaryEntity>();
                SummaryEntity summaryMaterial = new SummaryEntity();
                summaryMaterial.setProjectId(entity.getProjectId());
                summaryMaterial.setMonth(entity.getMonth());
                summaryMaterial.setRdType(CostEnum.STIMULUS_PROD.getType());
                summaryMaterial.setAccountNumber("");
                summaryMaterial.setRdFunds(totalFunds);
                summaryMaterial.setCreatorId(userId);
                summaryMaterial.setCreateTime(date);
                summaryMaterial.setUpdatorId(userInfo.getId());
                summaryMaterial.setLastUpdateTime(date);
                summaryMaterial.setMsCreatorId(msUserId);
                summaryMaterial.setMsLastUpdatorId(msUserId);
                summaryMaterial.setLastUpdatorId(userId);
                summaryEntities.add(summaryMaterial);
                summaryDao.insertOrUpdate(summaryEntities);

                TransactionUtils.commit(transactionStatus);
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
                TransactionUtils.rollback(transactionStatus);
                throw new OwnerException(ErrorEnum.FAIL);
            }
        }


        return true;
    }

    @Override
    public PageModel<List<RdEquipmentResultModel>> queryList(UserInfo userInfo, QueryProjectRdEquipmentModel query) {
        Pagination page = query.getPagination();
        List<RdEquipmentResultModel> rdEquipmentModels = new ArrayList<>();
        if (query.getMonth() != null) {
            Date month = DateUtil.getMonthFirstDay(query.getMonth());
            // 2021年8月10日16:56:28 姚娟 更改为只查询归集的设备
            rdEquipmentModels = projectRdEquipmentDao.queryList(page, userInfo.getCompanyId(), query.getProjectId(),
                    month, cn.hutool.core.date.DateUtil.year(month));
        }
        return PageUtils.build(page, rdEquipmentModels);

    }

    @Override
    public List<RdEquipmentResultModel> queryListByYear(UserInfo userInfo, QueryProjectRdEquipmentModel query) {
        ProjectEntity projectEntity = projectDao.selectById(query.getProjectId());
//        Date beginDate = DateUtil.getMonthFirstDay(projectEntity.getBeginDate());
//        Date endDate = DateUtil.getMonthLastDay(projectEntity.getEndDate());
        StageEntity stage = stageDao.getStageInfo(projectEntity.getId(), query.getpDocFileId());
        Integer year = cn.hutool.core.date.DateUtil.year(stage != null && stage.getBeginDate() != null ? stage.getBeginDate() : projectEntity.getBeginDate());
        // 2021年8月10日16:56:28 姚娟 更改为只查询归集的设备
        List<RdEquipmentResultModel> rdEquipmentModels = projectRdEquipmentDao.queryYearList(userInfo.getCompanyId(), query.getProjectId(), year);
        return rdEquipmentModels;
    }

    @Override
    public Boolean importRdHour(UserInfo info, BatchProjectRdEquipmentModel batchModel, int year) throws OwnerException {
        List<ProjectRdEquipmentModel> dataList = batchModel.getList();
        if (CollectionUtils.isEmpty(dataList)) {
            throw new OwnerException("未导入任何数据。");
        }
        Integer projectId = batchModel.getProjectId();
        Map<String, ProjectRdEquipmentModel> monthDataMap = new LinkedHashMap<>();
        String keyFormat = "{0}_{1}";
        Set<String> ecodes = new HashSet<>();
        Set<Date> months = new HashSet<>();
        checkAndLoadMonthData(monthDataMap, ecodes, months, projectId, dataList, year, keyFormat);
        int hourBit = companySettingService.getEquipmentHourBit(info.getCompanyId());
        List<String> existInit = initEquipmentDao.getByEcodes(info.getCompanyId(), projectId, ecodes, year);
        Collection<String> initDisjunction = CollUtil.disjunction(ecodes, existInit);
        if (!CollectionUtils.isEmpty(initDisjunction)) {
            throw new OwnerException(MessageFormat.format("资产清单不存在资产代码为：【{0}】 的设备。", String.join(",", initDisjunction)));
        }
        List<ProjectRdEquipmentModel> dataEquipments = projectRdEquipmentDao.getUsedSum(info.getCompanyId(), projectId, months, ecodes);
        Map<String, ProjectRdEquipmentModel> dataEquipmentMap = dataEquipments.stream().filter(a -> a != null).collect(
                Collectors.toMap(
                        a -> MessageFormat.format(keyFormat, DateUtil.format(a.getMonth(), DateUtil.DEFAULT_YYMM_FORMAT), a.getEcode()),
                        b -> b, (e, n) -> n));
        List<ProjectRdEquipmentEntity> oldRdEquipments = projectRdEquipmentDao.getEcodeId(projectId, months, ecodes);
        Map<String, Integer> oldRdEquipmentMap = oldRdEquipments.stream().collect(Collectors.toMap(
                a -> MessageFormat.format(keyFormat, DateUtil.format(a.getMonth(), DateUtil.DEFAULT_YYMM_FORMAT), a.getEcode()),
                b -> b.getId(), (e, n) -> n));
        for (String key : monthDataMap.keySet()) {
            ProjectRdEquipmentModel current = monthDataMap.get(key);
            ProjectRdEquipmentModel dataEquipment = dataEquipmentMap.get(key);
            if (null == dataEquipment) {
                throw new OwnerException(MessageFormat.format("不存在【{0}】月，资产代码为【{1}】的设备使用。",
                        key.substring(0, 7), current.getEcode()));
            }
            current.setProjectId(projectId);
            BigDecimal curRdHour = current.getRdHourByBit(hourBit);
            if (curRdHour.compareTo(BigDecimal.ZERO) < 0) {
                throw new OwnerException(MessageFormat.format(
                        "月份【{3}】、资产代码【{1}】、设备名称【{2}】，分配工时【{0,number,#.#}】不能小于0。", curRdHour,
                        current.getEcode(), current.getEname(), key.substring(0, 7)));
            }
            if (dataEquipment.getRemainHour() == null) {
                dataEquipment.setRemainHour(dataEquipment.getWorkHours());
            }
            if (curRdHour.compareTo(dataEquipment.getRemainHour()) > 0) {
                throw new OwnerException(MessageFormat.format(
                        "月份【{4}】、资产代码【{2}】、设备名称【{3}】,分配工时【{0,number,#.#}】超过最大可分配工时【{1,number,#.#}】。",
                        curRdHour, dataEquipment.getRemainHour(), current.getEcode(), current.getEname(), key.substring(0, 7)));
            }
            BigDecimal ratio = BigDecimal.ZERO;
            if (dataEquipment.getWorkHours().compareTo(BigDecimal.ZERO) > 0) {
                ratio = curRdHour.divide(dataEquipment.getWorkHours(), Constant.MAX_SCALE, BigDecimal.ROUND_HALF_UP);
            }
            current.setRdDepreciation(ratio.multiply(dataEquipment.getDepreciation()).setScale(4, BigDecimal.ROUND_HALF_UP));
            current.setId(oldRdEquipmentMap.get(key));
        }
        return saveData(new ArrayList<>(months), CollUtil.newArrayList(projectId), new ArrayList<>(monthDataMap.values()), info, hourBit);
    }

    @Override
    public void insertSummary(Date now, List<Integer> projectIds, List<Date> months, UserInfo userInfo, Boolean filterZero) {
        List<ProjectRdEquipmentTotalModel> totals = projectRdEquipmentDao.getSummary(projectIds, months, cn.hutool.core.date.DateUtil.year(months.get(0)));
        List<SummaryEntity> summaryList = new ArrayList<>();
        Map<String, ProjectRdEquipmentTotalModel> totalMap = new HashMap<>();
        String keyFormat = "{0}_{1}";
        if (!CollectionUtils.isEmpty(totals)) {
            totals.forEach(item -> totalMap.put(MessageFormat.format(keyFormat, item.getProjectId(), DateUtil.format(item.getMonth(), DateUtil.DEFAULT_YYMM_FORMAT)), item));
        }
        months.forEach(m ->
                projectIds.forEach(pId -> {
                    ProjectRdEquipmentTotalModel total = totalMap.get(MessageFormat.format(keyFormat, pId, DateUtil.format(m, DateUtil.DEFAULT_YYMM_FORMAT)));
                    if (filterZero && total == null) {
                        return;
                    }
                    BigDecimal lab = BigDecimal.ZERO, assets = BigDecimal.ZERO, prod = BigDecimal.ZERO, power = BigDecimal.ZERO;
                    if (null != total) {
                        lab = total.getLab();
                        assets = total.getAssets();
                        prod = total.getProd();
                        power = total.getPower();
                    }
                    summaryList.add(ToolUtils.build(now, m, pId, CostEnum.LAB.getType(), lab, userInfo));
                    summaryList.add(ToolUtils.build(now, m, pId, CostEnum.PROD.getType(), prod, userInfo));
                    summaryList.add(ToolUtils.build(now, m, pId, CostEnum.ASSETS_AMORTIZATION.getType(), assets, userInfo));
                    summaryList.add(ToolUtils.build(now, m, pId, CostEnum.STIMULUS_PROD.getType(), power, userInfo));
                }));
        if (!CollectionUtils.isEmpty(summaryList)) {
            summaryDao.insertOrUpdate(summaryList);
        }
        // 已在删除项目时清理
        // summaryDao.delRdFundsForZero(projectId,months);
    }

    @Override
    public boolean aggByFinaScheduleHours(Map<Integer, ProjectFinaScheduleModel> hourMap, Date month, UserInfo userInfo) throws OwnerException {
        Integer companyId = userInfo.getCompanyId();
        List<Integer> projectIds = new ArrayList<>(hourMap.keySet());
        List<InitEquipmentEntity> initList = initEquipmentDao.getInitList(companyId, projectIds, cn.hutool.core.date.DateUtil.year(month));
        if (CollectionUtils.isEmpty(initList)) {
            return true;
        }
        List<ProjectRdEquipmentModel> usedSumList = projectRdEquipmentDao.getUsedByMonth(month, companyId);
        if (CollectionUtils.isEmpty(usedSumList)) {
            return true;
        }
        Map<String, ProjectRdEquipmentModel> dataEquipmentMap = usedSumList.stream().filter(Objects::nonNull).collect(
                Collectors.toMap(ProjectRdEquipmentModel::getEcode, b -> {
                    if (null == b.getRemainHour()) {
                        b.setRemainHour(b.getWorkHours());
                    }
                    return b;
                }, (e, n) -> n));
        List<ProjectRdEquipmentModel> existList = projectRdEquipmentDao.getExist(hourMap.keySet(), month, companyId);
        String keyFormat = "{0}_{1}";
        Map<String, ProjectRdEquipmentModel> projectEcodeMap = CollectionUtils.isEmpty(existList) ? new HashMap<>() :
                existList.stream().collect(Collectors.toMap(a -> MessageFormat.format(keyFormat, a.getProjectId(), a.getEcode()), b -> b, (o, n) -> n));
        List<ProjectRdEquipmentModel> data = new ArrayList<>();
        String currentKey, currentEcode;
        ProjectRdEquipmentModel current;
        BigDecimal remainHour, rdHour, rdRatio;
        ProjectFinaScheduleModel currentSchedule;
        int hourBit = companySettingService.getEquipmentHourBit(companyId);
        for (InitEquipmentEntity item : initList) {
            currentEcode = item.getEcode();
            ProjectRdEquipmentModel dataEquipment = dataEquipmentMap.get(currentEcode);
            if (null == dataEquipment) {
                continue;
            }
            currentSchedule = hourMap.get(item.getProjectId());
            if (null == currentSchedule) {
                continue;
            }
            rdHour = (currentSchedule.getTestHour() != null ? currentSchedule.getTestHour() : BigDecimal.ZERO).add(currentSchedule.getTrialHour() != null ? currentSchedule.getTrialHour() : BigDecimal.ZERO);
            currentKey = MessageFormat.format(keyFormat, item.getProjectId(), currentEcode);
            if (projectEcodeMap.containsKey(currentKey)) {
                current = projectEcodeMap.get(currentKey);
                remainHour = dataEquipment.getRemainHour().add(current.getRdHourByBit(hourBit)).subtract(rdHour);
            } else {
                remainHour = dataEquipment.getRemainHour().subtract(rdHour);
                current = new ProjectRdEquipmentModel(item.getProjectId(), currentEcode, month);
            }
            if (remainHour.compareTo(dataEquipment.getWorkHours()) > 0) {
                remainHour = dataEquipment.getWorkHours();
            } else if (remainHour.compareTo(BigDecimal.ZERO) < 0) {
                remainHour = rdHour = BigDecimal.ZERO;
            }
            dataEquipment.setRemainHour(remainHour);
            current.setRdHour(rdHour);
            rdRatio = BigDecimal.ZERO;
            if (dataEquipment.getWorkHours().compareTo(BigDecimal.ZERO) != 0) {
                rdRatio = rdHour.divide(dataEquipment.getWorkHours(), Constant.MAX_SCALE, BigDecimal.ROUND_HALF_UP);
            }
            current.setRdDepreciation(rdRatio.multiply(dataEquipment.getDepreciation()));
            data.add(current);
        }
        return saveData(CollUtil.newArrayList(month), projectIds, data, userInfo, hourBit);
    }

    @Override
    public PageModel<List<RdEquipmentResultModel>> getMonthTotalProd(UserInfo userInfo, QueryProjectRdEquipmentModel query) {
        Pagination page = query.getPagination();
        Date month = DateUtil.getMonthFirstDay(query.getMonth());
        return PageUtils.build(page, projectRdEquipmentDao.getMonthTotalProd(page, userInfo.getCompanyId(), month, query.getYear()));
    }

    @Override
    public PageModel<List<HighTechProjectRdEquipmentModel>> getEquipments(Integer companyId, QueryProjectRdEquipmentModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            page.setAscs(CollUtil.newArrayList("ecode"));
        }
        List<HighTechProjectRdEquipmentModel> list = projectRdEquipmentDao.getEquipments(companyId, query, page);
        return PageUtils.build(page, list);
    }

    private Boolean saveData(List<Date> months, List<Integer> projectIds, List<ProjectRdEquipmentModel> data, UserInfo userInfo, Integer hourBit) throws OwnerException {
        commonService.checkStatus(new HashSet<Integer>(projectIds), DateUtil.getMonthFirstDay(months.get(0)), Arrays.asList(AuditRdTypeEnum.EQUIPMENT.getRdType()), userInfo);
        Date now = new Date();
        List<ProjectRdEquipmentEntity> insertOrUpdateList = new ArrayList<>();
        List<Integer> deleteIds = new ArrayList<>();
        Map<Integer, List<ProjectRdEquipmentModel>> deleteMap = new HashMap<>();
        Integer userId = userInfo.getUserId();
        Integer msUserId = userInfo.getMsUserId();
        Integer companyId = userInfo.getCompanyId();
        data.forEach(item -> {
            if (null == item.getRdHour() || item.getRdHour().compareTo(BigDecimal.ZERO) == 0) {
                if (null != item.getId()) {
                    deleteIds.add(item.getId());
                    if (null == item.getMonth()) {
                        item.setMonth(months.get(0));
                    }
                    ToolUtils.putAndAdd(deleteMap, item.getProjectId(), item);
                }
                return;
            }
            ProjectRdEquipmentEntity entity = ProjectRdEquipmentEntity.build(now, userId, msUserId, companyId, item, hourBit);
            insertOrUpdateList.add(entity);
        });
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            if (!CollectionUtils.isEmpty(insertOrUpdateList)) {
                projectRdEquipmentDao.insertOrUpdate(insertOrUpdateList);
            }
            if (!CollectionUtils.isEmpty(deleteMap)) {
                projectRdEquipmentDao.deleteBatchIds(deleteIds);
                for (Integer projectId : deleteMap.keySet()) {
                    deleteProjectEquipment(deleteMap.get(projectId), projectId, userInfo, now);
                }
            }
            insertSummary(now, projectIds, months, userInfo, false);
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException("保存失败");
        }
    }

    private void deleteProjectEquipment(List<ProjectRdEquipmentModel> deleteList, Integer projectId, UserInfo info, Date now) {
        List<ProjectEquipmentEntity> list = projectEquipmentDao.getProjectEquipments(deleteList, projectId);
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        List<EquipmentUsedEntity> usedList = equipmentUsedDao.getUseless(info.getCompanyId(), list);
        boolean hasUsed = !CollectionUtils.isEmpty(usedList);
        if (hasUsed) {
            Map<String, String> equipmentEquDataMap = new HashMap<>();
            String keyFormat = "{0}_{1}";

            Integer userId = info.getUserId();
            Integer msUserId = info.getMsUserId();
            list.forEach(item -> equipmentEquDataMap.put(MessageFormat.format(keyFormat, DateUtil.format(item.getMonth(), DateUtil.DEFAULT_YYMM_FORMAT), item.getEcode()), item.getEquData()));
            usedList.forEach(item -> {
                String equData = equipmentEquDataMap.get(MessageFormat.format(keyFormat, DateUtil.format(item.getMonth(), DateUtil.DEFAULT_YYMM_FORMAT), item.getEcode()));
                item.setMsLastUpdatorId(msUserId);
                item.setLastUpdatorId(userId);
                item.setLastUpdateTime(now);
                if (StringUtils.isEmpty(equData)) {
                    return;
                }
                BigDecimal total = AttDataUtils.getTotal(equData);
                item.setUsedEquData(AttDataUtils.subAttData(item.getUsedEquData(), equData));
                item.setRemainHours(item.getRemainHours().add(total));
            });
        }
        projectEquipmentDao.deleteBatchIds(list.stream().map(ProjectEquipmentEntity::getId).collect(Collectors.toList()));
        if (hasUsed) {
            equipmentUsedDao.updateBatch(usedList);
        }
    }

    /**
     * 设置使用过的项目列表
     *
     * @param list
     * @param userInfo
     * @param query
     */
    private void setUsedList(List<ProjectRdEquipmentModel> list, UserInfo userInfo, QueryProjectRdEquipmentModel query) {
        if (!CollectionUtils.isEmpty(list)) {
            List<String> ecodes = list.stream().map(ProjectRdEquipmentModel::getEcode).collect(Collectors.toList());
            List<RdUsedHourModel> usedHourModels = projectRdEquipmentDao.getAllUsed(userInfo.getCompanyId(), query, ecodes);
            Map<String, List<RdUsedHourModel>> keyMap = new HashMap<>();
            Map<String, BigDecimal> hourMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(usedHourModels)) {
                usedHourModels.forEach(item -> {
                    if (!keyMap.containsKey(item.getKey())) {
                        keyMap.put(item.getKey(), new ArrayList<>());
                    }
                    keyMap.get(item.getKey()).add(item);
                    hourMap.put(item.getKey(), hourMap.getOrDefault(item.getKey(), BigDecimal.ZERO).add(
                            item.getRdHour() != null ? item.getRdHour() : BigDecimal.ZERO));
                });
            }
            list.forEach(item -> {
                item.setUsedList(keyMap.getOrDefault(item.getEcode(), null));
                if (item.getWorkHours() != null) {
                    BigDecimal remainHour = item.getWorkHours().subtract(hourMap.getOrDefault(item.getEcode(), BigDecimal.ZERO));
                    if (item.getRdHour() != null) {
                        remainHour = remainHour.add(item.getRdHour());
                    }
                    item.setRemainHour(remainHour);
                }
            });
        }
    }

    private void checkAndLoadMonthData(Map<String, ProjectRdEquipmentModel> monthDataMap,
                                       Set<String> ecodes, Set<Date> months,
                                       Integer projectId, List<ProjectRdEquipmentModel> dataList,
                                       int year, String keyFormat) throws OwnerException {
        ProjectEntity projectEntity = projectDao.selectById(projectId);
        long beginTime = DateUtil.getMonthFirstDay(projectEntity.getBeginDate()).getTime();
        long endTime = projectEntity.getEndDate().getTime();
        for (ProjectRdEquipmentModel item : dataList) {
            Date m = DateUtil.getMonthFirstDay(item.getMonth());
            item.setMonth(m);
            months.add(m);
            if (cn.hutool.core.date.DateUtil.year(m) != year) {
                throw new OwnerException(MessageFormat.format(
                        "当前操作年：{0,number,#}不能导入月份【{1}】的数据。", year,
                        DateUtil.format(m, DateUtil.DEFAULT_YYMM_FORMAT)
                ));
            }
            if (beginTime > m.getTime() || endTime < m.getTime()) {
                throw new OwnerException(MessageFormat.format(
                        "月份【{0}】不在当前项目【{1}】的起止日期【{2}~{3}】内。", DateUtil.format(m, DateUtil.DEFAULT_YYMM_FORMAT),
                        projectEntity.getRdTitle(), DateUtil.format(projectEntity.getBeginDate(), DateUtil.DEFAULT_DATE_FORMAT),
                        DateUtil.format(projectEntity.getEndDate(), DateUtil.DEFAULT_DATE_FORMAT)
                ));
            }
            monthDataMap.put(
                    MessageFormat.format(keyFormat, DateUtil.format(item.getMonth(), DateUtil.DEFAULT_YYMM_FORMAT), item.getEcode()),
                    item
            );
            ecodes.add(item.getEcode());
        }
    }

    @Override
    public AggMsgModel aggRdTrialFee(TrialAggModel aggModel, CostEnum costEnum, UserInfo info) {
        try {
            Date month = aggModel.getMonth();
            Integer companyId = info.getCompanyId(), year = cn.hutool.core.date.DateUtil.year(month);
            List<InitEquipmentMiniModel> initEquipments = initEquipmentDao.getInitEquipments(companyId, year, DateUtil.getMonthLastDay(month));
            if (CollectionUtils.isEmpty(initEquipments)) {
                return AggMsgModel.buildFail("无可归集的设备，归集设备折旧失败！");
            }
            Map<String, List<Integer>> ecodeProjectMap = new LinkedHashMap<>();
            initEquipments.forEach(item -> ToolUtils.putAndAdd(ecodeProjectMap, item.getEcode(), item.getProjectId()));
            List<DataEquipmentInfoModel> dataEquipments = dataEquipmentDao.getByEcodes(companyId, month, new ArrayList<>(ecodeProjectMap.keySet()), true);
            if (CollectionUtils.isEmpty(dataEquipments)) {
                return AggMsgModel.buildFail("所选月份相关设备无设备使用记录，归集设备折旧失败！");
            }
            Map<String, DataEquipmentInfoModel> dataEquipmentMap = dataEquipments.stream().collect(Collectors.toMap(
                    DataEquipmentInfoModel::getEcode, b -> b, (o, n) -> n));
            int hourBit = companySettingService.getEquipmentHourBit(companyId);
            List<ProjectRdEquipmentEntity> insertList = new ArrayList<>();
            Map<Integer, ProjectYieldConfigTotalModel> projectTotalMap = aggModel.getProjectTotalMap();
            Boolean equipmentHour = aggModel.getEquipmentHour();
            List<ProjectEquipmentEntity> attList = null;
            Map<String, EquipmentUsedEntity> attUsedMap = null;
            if (equipmentHour) {
                attList = new ArrayList<>();
                attUsedMap = new HashMap<>();
            }
            Integer userId = info.getUserId(), msUserId = info.getMsUserId();
            DataEquipmentInfoModel equipmentInfo;
            Date now = new Date();
            for (String ecode : ecodeProjectMap.keySet()) {
                equipmentInfo = dataEquipmentMap.get(ecode);
                if (equipmentInfo == null || equipmentInfo.getWorkHours().compareTo(BigDecimal.ZERO) <= 0) {
                    continue;
                }
                for (Integer projectId : ecodeProjectMap.get(ecode)) {
                    if (!projectTotalMap.containsKey(projectId)) {
                        continue;
                    }
                    ProjectYieldConfigTotalModel yield = projectTotalMap.get(projectId);
                    BigDecimal rdHour = yield.getBitAgg(hourBit);
                    BigDecimal remainHour = equipmentInfo.getRemainHours().subtract(rdHour);
                    if (remainHour.compareTo(BigDecimal.ZERO) < 0) {
                        BigDecimal total = BigDecimal.ZERO;
                        for (Integer temProjectId : ecodeProjectMap.get(ecode)) {
                            if (!projectTotalMap.containsKey(temProjectId)) {
                                continue;
                            }
                            total = total.add(projectTotalMap.get(temProjectId).getBitAgg(hourBit));
                        }
                        return AggMsgModel.buildFail(MessageFormat.format("{0}月资产代码为【{1}】的设备运行工时为：{2}，总研发工时为：{3}，总研发工时超过设备运行工时，归集设备折旧失败！",
                                DateUtil.format(month, DateUtil.DEFAULT_YYMM_FORMAT), ecode, equipmentInfo.getWorkHours(), total));
                    }
                    equipmentInfo.setRemainHours(remainHour);
                    BigDecimal ratio = rdHour.divide(equipmentInfo.getWorkHours(), Constant.MAX_SCALE, BigDecimal.ROUND_HALF_UP);
                    insertList.add(ProjectRdEquipmentEntity.build(now, companyId, userId, msUserId, projectId, month, ecode, rdHour,
                            equipmentInfo.getDepreciation().multiply(ratio), ratio));
                    if (equipmentHour) {
                        EquipmentUsedEntity used = attUsedMap.get(ecode);
                        if (used == null) {
                            used = EquipmentUsedEntity.build(ecode, BigDecimal.ZERO, equipmentInfo.getWorkHours(),
                                    Constant.DEFAULT_HOUR_ATT_DATA, companyId, userId, msUserId, month, now);
                            attUsedMap.put(ecode, used);
                        }
                        ProjectEquipmentEntity att = ProjectEquipmentEntity.build(now, ecode, month, projectId, userId, msUserId, companyId, BigDecimal.ZERO, equipmentInfo.getId());
                        loadAttEquipment(yield.getInfos().values(), used, att);
                        att.reloadEquData();
                        used.reloadUsedEquData();
                        attList.add(att);
                    }
                }
            }
            Date monthEnd = DateUtil.getMonthLastDay(month);
            List<Integer> projectIds = projectDao.getProjectIds(companyId, month, monthEnd);
            TransactionStatus transactionStatus = null;
            try {
                transactionStatus = TransactionUtils.newTransaction();
                projectRdEquipmentDao.deleteData(companyId, month);
                projectEquipmentDao.deleteData(companyId, month);
                summaryDao.deleteByProjects(projectIds, month, CollUtil.newArrayList(CostEnum.PROD.getType(),
                        CostEnum.LAB.getType(), CostEnum.ASSETS_AMORTIZATION.getType(), CostEnum.STIMULUS_PROD.getType()));
                if (!CollectionUtils.isEmpty(insertList)) {
                    List<List<ProjectRdEquipmentEntity>> subList = ListUtils.subList(insertList, Constant.MAX_INSERT_OR_UPDATE_COUNT);
                    for (List<ProjectRdEquipmentEntity> currentList : subList) {
                        projectRdEquipmentDao.insertOrUpdate(currentList);
                    }
                }
                if (!CollectionUtils.isEmpty(attList)) {
                    List<List<ProjectEquipmentEntity>> subList = ListUtils.subList(attList, Constant.MAX_INSERT_OR_UPDATE_COUNT);
                    for (List<ProjectEquipmentEntity> currentList : subList) {
                        projectEquipmentDao.insertBatch(currentList);
                    }
                }
                if (!CollectionUtils.isEmpty(attUsedMap)) {
                    List<List<EquipmentUsedEntity>> subList = ListUtils.subList(new ArrayList<>(attUsedMap.values()), Constant.MAX_INSERT_OR_UPDATE_COUNT);
                    for (List<EquipmentUsedEntity> currentList : subList) {
                        equipmentUsedDao.insertBatch(currentList);
                    }
                }
                insertSummary(now, projectIds, CollUtil.newArrayList(month), info, true);
                TransactionUtils.commit(transactionStatus);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                TransactionUtils.rollback(transactionStatus);
                throw new OwnerException("归集失败。");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return AggMsgModel.buildFail("归集设备折旧失败！");
        }
        return AggMsgModel.buildSuccess("归集设备折旧成功！");
    }

    private void loadAttEquipment(Collection<List<ProjectYieldConfigInfoModel>> data, EquipmentUsedEntity attUsed, ProjectEquipmentEntity att) {
        for (List<ProjectYieldConfigInfoModel> list : data) {
            int dayIndex = cn.hutool.core.date.DateUtil.dayOfMonth(list.get(0).getTrialDate()) - 1;
            for (ProjectYieldConfigInfoModel item : list) {
                att.addTime(dayIndex, item.getAgg(), attUsed);
            }
        }
    }
}
