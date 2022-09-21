package com.yskc.rs.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.PageResult;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.dao.EnergyDao;
import com.yskc.rs.dao.ProjectDao;
import com.yskc.rs.dao.SummaryDao;
import com.yskc.rs.dao.project.ProjectEnergyDao;
import com.yskc.rs.dao.project.ProjectRdEquipmentDao;
import com.yskc.rs.entity.EnergyEntity;
import com.yskc.rs.entity.SummaryEntity;
import com.yskc.rs.entity.project.ProjectEnergyEntity;
import com.yskc.rs.enums.CostEnum;
import com.yskc.rs.models.CheckStatusModel;
import com.yskc.rs.models.UsedModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.projectenergy.*;
import com.yskc.rs.models.projectfinaschedule.ProjectFinaScheduleModel;
import com.yskc.rs.models.projectrdequipment.ProjectRdEquipmentModel;
import com.yskc.rs.models.projectyieldconfig.*;
import com.yskc.rs.service.ProjectEnergyService;
import com.yskc.rs.utils.ListUtils;
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
 * @author zdf
 */
@Service
public class ProjectEnergyServiceImpl implements ProjectEnergyService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProjectEnergyDao projectEnergyDao;
    @Autowired
    private SummaryDao summaryDao;

    @Autowired
    private EnergyDao energyDao;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ProjectRdEquipmentDao projectRdEquipmentDao;
    @Autowired
    private ProjectDao projectDao;

    @Override
    public PageResult queryProjectEnergy(Integer companyId, QueryProjectEnergy queryEnergy) {
        Pagination pagination = queryEnergy.getPagination();
        setSort(pagination);
        List<ProjectEnergyModel> list = projectEnergyDao.getList(pagination, companyId, queryEnergy, CollUtil.newArrayList(queryEnergy.getProjectId()));
        if (CollectionUtils.isEmpty(list)) {
            return PageUtils.buildPageResult(pagination, list, null, null);
        }
        List<Integer> energyDataIds = new ArrayList<>();
        list.forEach(item -> {
            energyDataIds.add(item.getEnergyDataId());
        });
        List<EnergyUsedModel> usedList = projectEnergyDao.getUsedList(energyDataIds, queryEnergy.getProjectId());
        Map<Integer, List<UsedModel>> usedMap = new HashMap<>();
        usedList.forEach(item -> {
            if (!usedMap.containsKey(item.getId())) {
                usedMap.put(item.getId(), new ArrayList<>());
            }
            usedMap.get(item.getId()).add(item);
        });
        list.forEach(item -> {
            if (usedMap.containsKey(item.getEnergyDataId())) {
                item.setUsedList(usedMap.get(item.getEnergyDataId()));
            }
        });
        return PageUtils.buildPageResult(pagination, list, null,
                projectEnergyDao.getProjectEnergyTotal(companyId, queryEnergy));
    }

    /**
     * 设置默认排序
     *
     * @param page
     */
    private void setSort(Pagination page) {
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("occDate");
            page.setDescs(desc);
        }
    }

    @Override
    public PageResult getEnergyList(Integer companyId, QueryProjectEnergy queryEnergy) {
        Pagination page = queryEnergy.getPagination();
        setSort(page);
        return PageUtils.buildPageResult(page,
                projectEnergyDao.getEnergyList(page, companyId, queryEnergy),
                null,
                projectEnergyDao.getEnergyTotal(companyId, queryEnergy));
    }

    @Override
    public boolean delEnergies(UserInfo userInfo, BatchProjectEnergyModel delBatchModel) throws OwnerException {
        List<CheckStatusModel> models = new ArrayList<>();
        CheckStatusModel checkStatusModel = new CheckStatusModel();
        checkStatusModel.setMonth(delBatchModel.getMonth());
        checkStatusModel.setProjectId(delBatchModel.getProjectId());
        models.add(checkStatusModel);
        List<Integer> rdTypes = new ArrayList<>();
        rdTypes.add(delBatchModel.getEtype());
        commonService.checkStatus(models,rdTypes,userInfo);
        List<EnergyEntity> energies = energyDao.getByProjectEnergyIds(delBatchModel.getIds());
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            projectEnergyDao.deleteBatchIds(delBatchModel.getIds());
            Date now = new Date();
            energyDao.updateList(energies, now, userInfo.getUserId(), userInfo.getMsUserId());
            insertSummaryEntity(delBatchModel.getProjectId(), now, delBatchModel.getMonth(), userInfo, delBatchModel.getEtype(), delBatchModel.getType());
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            logger.error("批量删除能源失败", e);
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException("删除失败，请稍后再试。");
        }
    }

    @Override
    public boolean addList(UserInfo userInfo, EnergyAddListModel energyAddListModel) throws OwnerException {
        return saveList(energyAddListModel.getEnergyModels(), userInfo, energyAddListModel.getMonth(),
                energyAddListModel.getProjectId(), energyAddListModel.getEtype(), energyAddListModel.getType());
    }

    /**
     * 批量添加
     *
     * @param energyListModel
     * @param info
     * @param month
     * @param projectId
     * @return
     * @throws OwnerException
     */
    private boolean saveList(List<EnergyListModel> energyListModel, UserInfo info, Date month, Integer projectId, Integer etype, Integer type) throws OwnerException {
        List<CheckStatusModel> models = new ArrayList<>();
        CheckStatusModel checkStatusModel = new CheckStatusModel();
        checkStatusModel.setMonth(month);
        checkStatusModel.setProjectId(projectId);
        models.add(checkStatusModel);
        List<Integer> rdTypes = new ArrayList<>();
        rdTypes.add(etype);
        commonService.checkStatus(models,rdTypes,info);
        List<ProjectEnergyEntity> projectEnergies = new ArrayList<>();
        Date now = new Date();
        Integer userId = info.getUserId(), msUserId = info.getMsUserId();
        List<EnergyEntity> energies = new ArrayList<>();
        energyListModel.stream().forEach(item -> {
            projectEnergies.add(ProjectEnergyEntity.build(item, info, now, projectId, BigDecimal.ZERO, BigDecimal.ZERO, item.getRemainAmount(), etype));
            EnergyEntity energyEntity = EnergyEntity.build(item.getId(), BigDecimal.ZERO);
            energies.add(energyEntity);
        });
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            projectEnergyDao.addList(projectEnergies);
            energyDao.updateList(energies, now, userId, msUserId);
            insertSummaryEntity(projectId, now, month, info, etype, type);
            TransactionUtils.commit(transactionStatus);

            return true;
        } catch (Exception e) {
            logger.error("批量添加能源失败", e);
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException("添加失败，请稍后再试。");
        }
    }

    @Override
    public boolean addByCondition(UserInfo userInfo, QueryProjectEnergy queryEnergy) throws OwnerException {
        Date month = queryEnergy.getMonth();
        List<EnergyListModel> energyListModels = projectEnergyDao.getEnergyList(userInfo.getCompanyId(), queryEnergy);
        if (CollectionUtils.isEmpty(energyListModels)) {
            throw new OwnerException("没有查询到数据");
        }
        return saveList(energyListModels, userInfo, month, queryEnergy.getProjectId(), queryEnergy.getEtype(), queryEnergy.getType());
    }

    @Override
    public boolean updateList(UserInfo userInfo, UpdateProjectEnergy updateProjectEnergy, Boolean subtract) throws OwnerException {
        List<CheckStatusModel> models = new ArrayList<>();
        CheckStatusModel checkStatusModel = new CheckStatusModel();
        checkStatusModel.setMonth(updateProjectEnergy.getMonth());
        checkStatusModel.setProjectId(updateProjectEnergy.getProjectId());
        models.add(checkStatusModel);
        List<Integer> rdTypes = new ArrayList<>();
        rdTypes.add(updateProjectEnergy.getEtype());
        commonService.checkStatus(models,rdTypes,userInfo);
        Date now = new Date();
        List<ProjectEnergyEntity> projectEnergys = new ArrayList<>();
        List<EnergyEntity> energys = new ArrayList<>();
        updateProjectEnergy.getModelList().forEach(item -> {
            projectEnergys.add(ProjectEnergyEntity.build(item, userInfo, now));
            energys.add(EnergyEntity.build(item.getEnergyDataId(), subtract ? item.getRemainAmount().subtract(item.getRdAmount()) : item.getRemainAmount()));
        });
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            for (List<ProjectEnergyEntity> updateList : ListUtils.subList(projectEnergys, Constant.MAX_INSERT_OR_UPDATE_COUNT)) {
                projectEnergyDao.updateList(updateList);
            }
            for (List<EnergyEntity> updateList : ListUtils.subList(energys, Constant.MAX_INSERT_OR_UPDATE_COUNT)) {
                energyDao.updateList(updateList, now, userInfo.getUserId(), userInfo.getMsUserId());
            }
            insertSummaryEntity(updateProjectEnergy.getProjectId(), now, updateProjectEnergy.getMonth(),
                    userInfo, updateProjectEnergy.getEtype(), updateProjectEnergy.getType());
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException("保存数据失败，请稍后再试。");
        }
    }

    @Override
    public boolean syncDepreciation(UserInfo userInfo, QueryProjectEnergy query) throws OwnerException {
        List<CheckStatusModel> models = new ArrayList<>();
        CheckStatusModel checkStatusModel = new CheckStatusModel();
        checkStatusModel.setMonth(query.getMonth());
        checkStatusModel.setProjectId(query.getProjectId());
        models.add(checkStatusModel);
        List<Integer> rdTypes = new ArrayList<>();
        rdTypes.add(query.getEtype());
        commonService.checkStatus(models,rdTypes,userInfo);
        ProjectRdEquipmentModel projectRdEquipmentModel = projectRdEquipmentDao.getMaxRdHour(query);
        if (projectRdEquipmentModel == null) {
            throw new OwnerException("同步失败，未获取到折旧工时，请先归集折旧。");
        }
        List<ProjectEnergyModel> projectEnergies = projectEnergyDao.getList(userInfo.getCompanyId(), query, CollUtil.newArrayList(query.getProjectId()));
        if (CollectionUtils.isEmpty(projectEnergies)) {
            throw new OwnerException("同步失败，请先添加需要归集的数据。");
        }
        BigDecimal totalHour = projectRdEquipmentModel.getWorkHours();
        BigDecimal rdHour = projectRdEquipmentModel.getRdHour();
        BigDecimal ratio = rdHour.divide(totalHour, Constant.MAX_SCALE, BigDecimal.ROUND_HALF_UP);
        projectEnergies.forEach(item -> {
            item.setTotalHour(totalHour);
            item.setRdHour(rdHour);
            item.setRemainAmount(item.getRemainAmount().add(item.getRdAmount()));
            BigDecimal rdAmount = ratio.multiply(item.getAmount());
            if (rdAmount.abs().compareTo(item.getRemainAmount().abs()) > 0) {
                rdAmount = item.getRemainAmount();
            }
            item.setRdAmount(rdAmount);
        });
        return updateList(userInfo, UpdateProjectEnergy.build(projectEnergies, query.getMonth(), query.getProjectId(),
                query.getEtype(), query.getType()), true);
    }

    @Override
    public boolean setRdHour(UserInfo userInfo, UpdateProjectEnergy update) throws OwnerException {
        List<CheckStatusModel> models = new ArrayList<>();
        CheckStatusModel checkStatusModel = new CheckStatusModel();
        checkStatusModel.setMonth(update.getMonth());
        checkStatusModel.setProjectId(update.getProjectId());
        models.add(checkStatusModel);
        List<Integer> rdTypes = new ArrayList<>();
        rdTypes.add(update.getEtype());
        commonService.checkStatus(models,rdTypes,userInfo);
        List<Integer> ids = update.getModelList().stream().map(a -> a.getId()).collect(Collectors.toList());
        List<ProjectEnergyModel> dataList = projectEnergyDao.getListByIds(ids);
        if (CollectionUtils.isEmpty(dataList)) {
            throw new OwnerException("获取数据失败。");
        }
        BigDecimal rdHour = update.getRdHour();
        if (rdHour.compareTo(BigDecimal.ZERO) == 0) {
            dataList.forEach(item -> {
                item.setRemainAmount(item.getRemainAmount().add(item.getRdAmount()));
                item.setRdAmount(BigDecimal.ZERO);
                item.setRdHour(rdHour);
            });
        } else {
            dataList.forEach(item -> {
                item.setRemainAmount(item.getRemainAmount().add(item.getRdAmount()));
                BigDecimal rdAmount;
                BigDecimal currentRdHour = rdHour;
                if (item.getTotalHour().abs().compareTo(currentRdHour.abs()) == -1) {
                    currentRdHour = item.getTotalHour();
                }
                if (item.getTotalHour().compareTo(BigDecimal.ZERO) == 0) {
                    rdAmount = BigDecimal.ZERO;
                } else {
                    rdAmount = currentRdHour.divide(item.getTotalHour(), Constant.MAX_SCALE, BigDecimal.ROUND_HALF_UP).multiply(item.getAmount());
                    if (rdAmount.abs().compareTo(item.getRemainAmount().abs()) == 1) {
                        rdAmount = item.getRemainAmount();
                    }
                }
                item.setRdHour(currentRdHour);
                item.setRdAmount(rdAmount);
            });
        }
        update.setModelList(dataList);
        return updateList(userInfo, update, true);
    }

    @Override
    public boolean setYield(UserInfo userInfo, Map<String, ProjectYieldConfigModel> yieldMap, Map<String, BigDecimal> ratioMap,
                            QueryYieldConfigModel query) throws OwnerException {
        Date month = DateUtil.getMonthFirstDay(query.getMonth());
        List<CheckStatusModel> models = new ArrayList<>();
        CheckStatusModel checkStatusModel = new CheckStatusModel();
        checkStatusModel.setMonth(month);
        checkStatusModel.setProjectId(query.getProjectId());
        models.add(checkStatusModel);
        List<Integer> rdTypes = new ArrayList<>();
        rdTypes.add(query.getRdType());
        commonService.checkStatus(models,rdTypes,userInfo);
        List<ProjectEnergyModel> dataList = projectEnergyDao.getProjectEnergyList(month,
                DateUtil.getMonthLastDay(query.getMonth()), query.getProjectId(), query.getRdType());
        if (CollectionUtils.isEmpty(dataList)) {
            throw new OwnerException("应用失败，未获取到归集数据。");
        }
        String keyFormat = "{0}{1}";
        List<ProjectEnergyModel> updateList = new ArrayList<>();
        dataList.forEach(item -> {
            String key = MessageFormat.format(keyFormat, DateUtil.format(item.getOccDate(), DateUtil.DEFAULT_DATE_FORMAT), item.getDeptName());
            ProjectYieldConfigModel yield = yieldMap.get(key);
            if (null != yield) {
                item.setTotalYield(yield.getTotalYield());
                item.setRdYield(yield.getRdYield());
                item.setRemainAmount(item.getRemainAmount().add(item.getRdAmount()));
                BigDecimal rdAmount = item.getAmount().multiply(ratioMap.get(key));
                if (rdAmount.compareTo(item.getRemainAmount()) > 0) {
                    rdAmount = item.getRemainAmount();
                }
                item.setRdAmount(rdAmount);
                updateList.add(item);
            }
        });
        if (CollectionUtils.isEmpty(updateList)) {
            throw new OwnerException("应用失败，未获取到对应部门的归集数据。");
        }
        return updateList(userInfo, UpdateProjectEnergy.build(updateList, query.getMonth(), query.getProjectId(),
                query.getRdType(), query.getType()), true);
    }

    @Override
    public boolean aggByFinaScheduleHours(Map<String, ProjectFinaScheduleModel> hourMap, Date month, UserInfo userInfo, int type, String keyFormat) throws OwnerException {
        QueryProjectEnergy query = new QueryProjectEnergy();
        query.setEtype(type);
        query.setType(type);
        month = DateUtil.getMonthFirstDay(month);
        query.setMonth(month);

        List<ProjectEnergyModel> projectEnergies = projectEnergyDao.getList(userInfo.getCompanyId(), query, hourMap.values().stream().map(ProjectFinaScheduleModel::getProjectId).distinct().collect(Collectors.toList()));
        if (CollectionUtils.isEmpty(projectEnergies)) {
            return true;
        }
        Map<Integer, BigDecimal> remainMap = new HashMap<>();
        Map<Integer, List<ProjectEnergyModel>> dataMap = new HashMap<>();
        BigDecimal workHour, rdHour, rdRatio, remainAmount, rdAmount;
        for (ProjectEnergyModel item : projectEnergies) {
            Integer energyDataId = item.getEnergyDataId();
            if (!remainMap.containsKey(energyDataId)) {
                remainMap.put(energyDataId, item.getRemainAmount());
            }
            ProjectFinaScheduleModel finaSchedule = hourMap.get(MessageFormat.format(keyFormat, item.getProjectId(), item.getDeptName()));
            if (null == finaSchedule) {
                continue;
            }
            workHour = finaSchedule.getWorkHours();
            if (null == workHour) {
                continue;
            }
            rdHour = (finaSchedule.getTestHour() != null ? finaSchedule.getTestHour() : BigDecimal.ZERO).add(finaSchedule.getTrialHour() != null ? finaSchedule.getTrialHour() : BigDecimal.ZERO);
            rdRatio = BigDecimal.ZERO;
            if (workHour.compareTo(BigDecimal.ZERO) != 0) {
                rdRatio = rdHour.divide(workHour, Constant.MAX_SCALE, BigDecimal.ROUND_HALF_UP);
            }
            remainAmount = remainMap.get(energyDataId).add(item.getRdAmount());
            rdAmount = rdRatio.multiply(item.getAmount());
            if (rdAmount.abs().compareTo(remainAmount.abs()) > 0) {
                rdAmount = remainAmount;
            }
            remainAmount = remainAmount.subtract(rdAmount);
            remainMap.put(energyDataId, remainAmount);
            item.setRdAmount(rdAmount);
            item.setTotalHour(workHour);
            item.setRdHour(rdHour);
            ToolUtils.putAndAdd(dataMap, item.getProjectId(), item);
        }
        for (Integer projectId : dataMap.keySet()) {
            List<ProjectEnergyModel> modelList = dataMap.get(projectId);
            modelList.forEach(item -> item.setRemainAmount(remainMap.getOrDefault(item.getEnergyDataId(), item.getRemainAmount())));
            updateList(userInfo, UpdateProjectEnergy.build(modelList, month, projectId, type, type), false);
        }
        return true;
    }

    @Override
    public PageModel<List<HighTechProjectEnergyModel>> getEnergies(Integer companyId, QueryProjectEnergy query) {
        Pagination page = query.getPagination();
        setSort(page);
        List<HighTechProjectEnergyModel> list = projectEnergyDao.getEnergies(companyId, query, page);
        if (!CollectionUtils.isEmpty(list)) {
            for (HighTechProjectEnergyModel model : list) {
                BigDecimal rdAmount = model.getRdAmount();
                BigDecimal unitPrice = model.getUnitPrice();
                if (null != rdAmount) {
                    if (null != unitPrice && unitPrice.compareTo(BigDecimal.ZERO) != 0) {
                        model.setRdQuantity(rdAmount.divide(unitPrice, 2, BigDecimal.ROUND_HALF_UP));
                    }
                }
            }
        }
        return PageUtils.build(page,list);
    }

    /**
     * 添加汇总信息
     *
     * @param projectId
     * @param now
     * @param month
     * @return
     */
    private boolean insertSummaryEntity(Integer projectId, Date now, Date month, UserInfo userInfo, Integer etype, Integer type) {
        Date monthBegin = DateUtil.getMonthFirstDay(month);
        Date monthEnd = DateUtil.getMonthLastDay(month);
        List<EnergySumModel> energySum = projectEnergyDao.getEnergySum(projectId, monthBegin, monthEnd, etype, type);
        energySum.remove(null);
        if (energySum.size() > 0) {
            List<SummaryEntity> summaryEntities = getSummaryEntityList(now, energySum, month, userInfo, etype);
            if (summaryEntities.size() > 0) {
                summaryDao.insertOrUpdate(summaryEntities);
                return true;
            }
        } else {
            summaryDao.deleteInfo(projectId, month, CollUtil.newArrayList(etype));
            return true;
        }
        return false;
    }

    private List<SummaryEntity> getSummaryEntityList(Date now, List<EnergySumModel> energySum, Date month, UserInfo userInfo, Integer etype) {
        List<SummaryEntity> summaryEntities = new ArrayList<>();
        energySum.forEach(item -> {
            summaryEntities.add(ToolUtils.build(now, month, item.getProjectId(), etype, item.getRdAmount(), userInfo));
        });
        return summaryEntities;
    }

    @Override
    public AggMsgModel aggRdTrialFee(TrialAggModel aggModel, CostEnum costEnum, UserInfo info) {
        try {
            Date month = aggModel.getMonth(), monthEnd = DateUtil.getMonthLastDay(month);
            Integer companyId = info.getCompanyId(), rdType = costEnum.getType();
            List<ProjectEnergyModel> projectEnergies = projectEnergyDao.getTermData(companyId, month, monthEnd, rdType);
            if (CollectionUtils.isEmpty(projectEnergies)) {
                return AggMsgModel.buildFail(MessageFormat.format("无可归集的数据，归集{0}费用失败！", costEnum.getTitle()));
            }
            Map<Integer, ProjectYieldConfigTotalModel> projectTotalMap = aggModel.getProjectTotalMap();
            Map<String, ProjectYieldConfigTotalModel> deptTrialDateTotalMap = new HashMap<>();
            Map<Integer, EnergyEntity> baseDataMap = new HashMap<>();
            EnergyEntity curEnergy;
            String curDeptKey,projectDeptKey;
            BigDecimal agg, total, rdAmount;
            boolean isHourAgg = aggModel.isHourAgg();
            Set<Integer> projectIds = new HashSet<>();
            Integer dataId, curProjectId;
            List<ProjectEnergyEntity> projectEnergyList = new ArrayList<>();
            for (ProjectEnergyModel item : projectEnergies) {
                dataId = item.getEnergyDataId();
                curEnergy = baseDataMap.get(dataId);
                if (null == curEnergy) {
                    curEnergy = EnergyEntity.build(dataId, item.getAmount(), item.getRemainAmount());
                    baseDataMap.put(dataId, curEnergy);
                }
                // 剩余总量添加上一次归集的RD量
                curEnergy.addRemainAmount(item.getRdAmount());
                ProjectEnergyEntity curProjectEnergy = ProjectEnergyEntity.buildDefault(item.getId());
                projectEnergyList.add(curProjectEnergy);
                curProjectId = item.getProjectId();
                projectIds.add(curProjectId);
                curDeptKey = item.getDeptName();
                if (null == curDeptKey) {
                    continue;
                }
                curDeptKey = curDeptKey.trim()+DateUtil.format(item.getOccDate(),DateUtil.DEFAULT_DATE_FORMAT);
                if (!projectTotalMap.containsKey(curProjectId) || !projectTotalMap.get(curProjectId).getInfos().containsKey(curDeptKey)) {
                    continue;
                }
                projectDeptKey = curProjectId + curDeptKey;
                ProjectYieldConfigTotalModel deptTrialDateTotal = deptTrialDateTotalMap.get(projectDeptKey);
                if (null == deptTrialDateTotal) {
                    deptTrialDateTotal = ProjectYieldConfigTotalModel.build(projectTotalMap.get(curProjectId).getInfos().get(curDeptKey), isHourAgg);
                    deptTrialDateTotalMap.put(projectDeptKey, deptTrialDateTotal);
                }
                agg = deptTrialDateTotal.getAgg();
                total = deptTrialDateTotal.getTotal();
                if (isHourAgg) {
                    curProjectEnergy.setRdHour(agg);
                    curProjectEnergy.setTotalHour(total);
                } else {
                    curProjectEnergy.setRdYield(agg);
                    curProjectEnergy.setTotalYield(total);
                }
                BigDecimal ratio = total.compareTo(BigDecimal.ZERO) <= 0 ? BigDecimal.ZERO : agg.divide(total, Constant.MAX_SCALE, BigDecimal.ROUND_HALF_UP);
                rdAmount = curEnergy.getAmount().multiply(ratio);
                if (rdAmount.abs().compareTo(curEnergy.getRemainAmount().abs()) > 0) {
                    rdAmount = curEnergy.getRemainAmount();
                }
                curEnergy.setRemainAmount(curEnergy.getRemainAmount().subtract(rdAmount));
                curProjectEnergy.setRdAmount(rdAmount);
            }
            Date now = new Date();
            Integer userId = info.getUserId(), msUserId = info.getMsUserId();
            TransactionStatus transactionStatus = null;
            try {
                transactionStatus = TransactionUtils.newTransaction();
                for (List<ProjectEnergyEntity> updateList : ListUtils.subList(projectEnergyList, Constant.MAX_INSERT_OR_UPDATE_COUNT)) {
                    projectEnergyDao.updateBatch(updateList, now, userId, msUserId);
                }
                for (List<EnergyEntity> updateList : ListUtils.subList(new ArrayList<>(baseDataMap.values()), Constant.MAX_INSERT_OR_UPDATE_COUNT)) {
                    energyDao.updateList(updateList, now, userId, msUserId);
                }
                List<EnergySumModel> energySum = projectEnergyDao.getEnergySums(projectIds, month, monthEnd, rdType);
                energySum.remove(null);
                if (!CollectionUtils.isEmpty(energySum)) {
                    List<SummaryEntity> summaryEntities = getSummaryEntityList(now, energySum, month, info, rdType);
                    if (summaryEntities.size() > 0) {
                        summaryDao.insertOrUpdate(summaryEntities);
                    }
                }
                TransactionUtils.commit(transactionStatus);
            } catch (Exception e1) {
                logger.error(e1.getMessage(), e1);
                TransactionUtils.rollback(transactionStatus);
                return AggMsgModel.buildFail(MessageFormat.format("归集{0}费用失败！", costEnum.getTitle()));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return AggMsgModel.buildFail(MessageFormat.format("归集{0}费用失败！", costEnum.getTitle()));
        }
        return AggMsgModel.buildSuccess(MessageFormat.format("归集{0}费用成功！", costEnum.getTitle()));
    }
}
