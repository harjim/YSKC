package com.yskc.rs.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.dao.*;
import com.yskc.rs.dao.project.ProjectYearInfoDao;
import com.yskc.rs.entity.PolicySummaryEntity;
import com.yskc.rs.entity.SituationSummaryEntity;
import com.yskc.rs.entity.StandardImplementEntity;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.entity.project.ProjectInfoSummaryEntity;
import com.yskc.rs.entity.project.ProjectYearInfoEntity;
import com.yskc.rs.enums.CostEnum;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.material.MaterialPlanModel;
import com.yskc.rs.models.material.QueryMaterialTrackModel;
import com.yskc.rs.models.project.ProjectBudgetModel;
import com.yskc.rs.models.project.ProjectInfoSummaryModel;
import com.yskc.rs.models.projectSummary.*;
import com.yskc.rs.models.projectinfosummary.MaterialFeeModel;
import com.yskc.rs.models.summary.DataFundsModel;
import com.yskc.rs.models.trialprod.StageTrialModel;
import com.yskc.rs.service.SituationSummaryService;
import com.yskc.rs.service.exportFileImpl.CostBudgetForm;
import com.yskc.rs.service.exportFileImpl.FinalProjectCostForm;
import com.yskc.rs.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: hck
 * @DateTime: 2021/2/24 13:39
 * @Description: 项目情况总结实现
 */
@Service
public class SituationSummaryServiceImpl implements SituationSummaryService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SituationSummaryDao situationSummaryDao;

    @Autowired
    private ProblemSummaryDao problemSummaryDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private StandardImplementDao standardImplementDao;
    @Autowired
    private PolicySummaryDao policySummaryDao;
    @Autowired
    private ProjectInfoSummaryDao projectInfoSummaryDao;
    @Autowired
    private SummaryDao summaryDao;
    @Autowired
    private TrialProdDao trialProdDao;
    @Autowired
    private MaterialDao materialDao;
    @Autowired
    private ProjectYearInfoDao projectYearInfoDao;

    @Override
    public SituationSummaryModel querySituation(Integer year, Integer companyId) {
        SituationSummaryModel summaryModel = null;
        List<SituationSummaryModel> list = situationSummaryDao.querySituation(year, companyId);
        if (list.size() > 0) {
            summaryModel = list.get(0);
        }
        List<ProjectSituationModel> projectInfos = projectDao.getProjectInfo(year, companyId);
        if (summaryModel != null) {
            List<ProblemSummaryModel> problems = problemSummaryDao.getBySituationId(summaryModel.getId());
            summaryModel.setProblemSummarys(problems);
            summaryModel.setProjectSituations(projectInfos);
        } else {
            if (!CollectionUtils.isEmpty(projectInfos)) {
                summaryModel = new SituationSummaryModel();
                summaryModel.setProjectSituations(projectInfos);
            }
        }
        return summaryModel;
    }

    @Override
    public Integer saveSituation(SituationSummaryModel model, UserInfo userInfo) {
        Date date = new Date();
        SituationSummaryEntity situationSummary = situationSummaryDao.selectById(model.getId());
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            if (situationSummary != null) {
                BeanUtils.copyProperties(model, situationSummary);
                situationSummary.setLastUpdateTime(date);
                situationSummary.setLastUpdatorId(userInfo.getUserId());
                situationSummary.setMsLastUpdatorId(userInfo.getMsUserId());
                problemSummaryDao.deleteBySituationId(situationSummary.getId());
            } else {
                situationSummary = new SituationSummaryEntity(model, userInfo.getMsUserId(), userInfo.getUserId(), date, userInfo.getCompanyId());
            }
            situationSummaryDao.insertOrUpdate(situationSummary);
            if (!CollectionUtils.isEmpty(model.getProblemSummarys())) {
                problemSummaryDao.insertList(model.getProblemSummarys(), userInfo.getUserId(), userInfo.getMsUserId(), date, situationSummary.getId());
            }
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            TransactionUtils.rollback(transactionStatus);
            logger.error("saveSituation", ex);
        }
        return situationSummary.getId();
    }

    @Override
    public StandardModel queryStandard(Integer year, Integer companyId) {
        StandardImplementEntity standard = standardImplementDao.getInfo(year, companyId);
        StandardModel standardModel = null;
        if (standard != null) {
            standardModel = new StandardModel();
            BeanUtils.copyProperties(standard, standardModel);
        }
        return standardModel;
    }

    @Override
    public Boolean saveStandardInfo(StandardModel model, UserInfo userInfo) {
        Date date = new Date();
        StandardImplementEntity standardImplement = standardImplementDao.getInfo(model.getYear(), userInfo.getCompanyId());
        if (standardImplement == null) {
            standardImplement = new StandardImplementEntity();
            BeanUtils.copyProperties(model, standardImplement);
            standardImplement.setCompanyId(userInfo.getCompanyId());
            standardImplement.setCreateTime(date);
            standardImplement.setCreatorId(userInfo.getUserId());
            standardImplement.setMsCreatorId(userInfo.getMsUserId());
            standardImplement.setLastUpdateTime(date);
            standardImplement.setLastUpdatorId(userInfo.getUserId());
            standardImplement.setMsLastUpdatorId(userInfo.getMsUserId());
            return standardImplementDao.insert(standardImplement) > 0;
        } else {
            model.setId(standardImplement.getId());
            BeanUtils.copyProperties(model, standardImplement);
            standardImplement.setLastUpdatorId(userInfo.getUserId());
            standardImplement.setLastUpdateTime(date);
            standardImplement.setMsLastUpdatorId(userInfo.getMsUserId());
            return standardImplementDao.updateById(standardImplement) > 0;
        }
    }

    @Override
    public PolicySummaryModel queryPolicy() {
        return policySummaryDao.getInfo();
    }

    @Override
    public Boolean savePolicy(PolicySummaryModel model, UserInfo userInfo) {
        Date date = new Date();
        PolicySummaryEntity entity = new PolicySummaryEntity();
        if (model.getId() != null) {
            entity = policySummaryDao.selectById(model.getId());
            BeanUtils.copyProperties(model, entity);
            entity.setLastUpdateTime(date);
            entity.setLastUpdatorId(userInfo.getUserId());
            entity.setMsLastUpdatorId(userInfo.getMsUserId());
            return policySummaryDao.updateById(entity) > 0;
        }
        BeanUtils.copyProperties(model, entity);
        entity.setCreateTime(date);
        entity.setCreatorId(userInfo.getUserId());
        entity.setMsCreatorId(userInfo.getMsUserId());
        entity.setLastUpdatorId(userInfo.getUserId());
        entity.setMsLastUpdatorId(userInfo.getMsUserId());
        entity.setLastUpdateTime(date);
        return policySummaryDao.insert(entity) > 0;
    }

    @Override
    public List<ProjectInfoSummaryModel> getSummaryInfo(Integer year, Integer companyId) {
        List<ProjectInfoSummaryModel> models = new ArrayList<>();
        List<ProjectEntity> projects = projectDao.getProjectByCompany(companyId, year);
        if (CollectionUtils.isEmpty(projects)) {
            return models;
        }
        List<Integer> projectIds = projects.stream().map(ProjectEntity::getId).collect(Collectors.toList());
        Date firstDay = DateUtil.getYearFirstDay(year);
        Date lastDay = DateUtil.getYearLastDay(year);
        models = projectInfoSummaryDao.getInfoSummary(projectIds, firstDay, lastDay);
        List<ProjectYearInfoEntity> yearInfos = projectYearInfoDao.getInfoByProjects(projectIds, year);
        Map<Integer, BigDecimal> yearBudgetMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(yearInfos)) {
            yearBudgetMap = yearInfos.stream().collect(Collectors.toMap(e -> e.getProjectId(), e -> e.getBudget()));
        }
        if (!CollectionUtils.isEmpty(models)) {
            List<ProjectBudgetModel> budgets = summaryDao.getSummaryByYear(firstDay, lastDay, projectIds);

            Map<Integer, BigDecimal> budgetMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(budgets)) {
                budgetMap = budgets.stream().collect(Collectors.toMap(e -> e.getProjectId(), e -> e.getRdFunds().divide(Constant.TEN_THOUSAND, 2, BigDecimal.ROUND_HALF_UP)));
            }
            Map<Integer, BigDecimal> dataMap = new HashMap<>();
            //CostBudgetForm costBudgetForm = new CostBudgetForm();
            for (ProjectEntity project : projects) {
                // Map<String, Object> data = costBudgetForm.getProjectBudget(project);
                // BigDecimal budgetData = data.get("spending01") != null ? new BigDecimal((Integer) data.get("spending01")) : new BigDecimal(0.00);
                dataMap.put(project.getId(), yearBudgetMap.get(project.getId()));
            }
            BigDecimal budgetTotal, staffHourTotal, reqTotal, materialTotal, trialTotal, repairTotal, yieldTotal, budgetCostTotal, summaryTotal;
            budgetTotal = staffHourTotal = reqTotal = materialTotal = trialTotal = repairTotal = yieldTotal = budgetCostTotal = summaryTotal = BigDecimal.ZERO;
            for (ProjectInfoSummaryModel summary : models) {
                if (summary.getStaffRdHour() != null) {
                    staffHourTotal = staffHourTotal.add(summary.getStaffRdHour());
                }
                if (summary.getProdRdHour() != null) {
                    reqTotal = reqTotal.add(summary.getProdRdHour());
                }
                if (summary.getMaterialTotal() != null) {
                    materialTotal = materialTotal.add(summary.getMaterialTotal());
                    // summary.setMaterialTotal(summary.getMaterialTotal().divide(Constant.TEN_THOUSAND, 2, BigDecimal.ROUND_HALF_UP));
                }
                if (summary.getRepairTotal() != null) {
                    repairTotal = summary.getRepairTotal().add(repairTotal);
                    // summary.setRepairTotal(summary.getRepairTotal().divide(Constant.TEN_THOUSAND, 2, BigDecimal.ROUND_HALF_UP));
                }
                if (summary.getTrialTotal() != null) {
                    trialTotal = trialTotal.add(summary.getTrialTotal());
                    // summary.setTrialTotal((summary.getTrialTotal().divide(Constant.TEN_THOUSAND, 2, BigDecimal.ROUND_HALF_UP)));
                }
                if (summary.getYieldAmount() != null) {
                    yieldTotal = yieldTotal.add(summary.getYieldAmount());
                }
                summary.setBudget(dataMap.get(summary.getProjectId()));
                if (summary.getBudget() != null) {
                    budgetTotal = budgetTotal.add(summary.getBudget());
                }
                summary.setBudgetCost(budgetMap.get(summary.getProjectId()));
                if (summary.getBudgetCost() != null) {
                    budgetCostTotal = budgetCostTotal.add(summary.getBudgetCost());
                }
                summary.setSummary(budgetMap.get(summary.getProjectId()));
                if (summary.getSummary() != null) {
                    summaryTotal = summaryTotal.add(summary.getSummary());
                }

            }
            ProjectInfoSummaryModel summaryModel = new ProjectInfoSummaryModel(budgetTotal, staffHourTotal, reqTotal, materialTotal,
                    repairTotal, trialTotal, yieldTotal, summaryTotal, budgetCostTotal);
            models.add(summaryModel);
        }

        return models;
    }

    @Override
    public Map<String, Object> getBudgetCost(Integer projectId) {
        ProjectEntity project = projectDao.selectById(projectId);
        Map<String, Object> datamap = new HashMap<>();
        FinalProjectCostForm costForm = new FinalProjectCostForm();
        costForm.getBudgetCost(project, datamap);
        return datamap;
    }

    @Override
    public Map<String, Map<String, Object>> getBudgetInfo(Integer projectId, Integer year) {
        ProjectEntity project = projectDao.selectById(projectId);
        CostBudgetForm costBudgetForm = new CostBudgetForm();
        //Map<String, Object> dataMap = costBudgetForm.getProjectBudget(project);
        Map<String, Map<String, Object>> dataMap = costBudgetForm.getYearBudget(project, project.getEndYear(), null, null);
        return dataMap;
    }

    @Override
    public List<Map<String, Object>> getStaffHour(Integer year, Integer companyId) throws Exception {
        List<Map<String, Object>> dataList = getCommonSummaryData(year, companyId, null, "staffRdHour");
        return dataList;
    }

    @Override
    public List<Map<String, Object>> getEquipmentHour(Integer year, Integer companyId) throws Exception {
        List<Map<String, Object>> dataList = getCommonSummaryData(year, companyId, null, "prodRdHour");
        return dataList;
    }

    @Override
    public List<Map<String, Object>> getMaterialRaw(Integer year, Integer type, Integer companyId) throws Exception {
        List<Map<String, Object>> dataList = getCommonSummaryData(year, companyId, type, "Raw");
        return dataList;
    }

    @Override
    public List<Map<String, Object>> getMaterialAuxiliary(Integer year, Integer type, Integer companyId) throws Exception {
        List<Map<String, Object>> dataList = getCommonSummaryData(year, companyId, type, "Auxiliary");
        return dataList;
    }

    @Override
    public List<Map<String, Object>> getMaterialReserve(Integer year, Integer type, Integer companyId) throws Exception {
        List<Map<String, Object>> dataList = getCommonSummaryData(year, companyId, type, "Reserve");
        return dataList;
    }

    @Override
    public Map<Integer, Map<Object, BigDecimal>> getProjectSummary(Integer year, Integer companyId) {
        Map<Integer, Map<Object, BigDecimal>> dataMap = new HashMap<>();
        Date firstDay = DateUtil.getYearFirstDay(year);
        Date lastDay = DateUtil.getYearLastDay(year);
        List<DataFundsModel> fundsModels = summaryDao.getYearFunds(companyId, firstDay, lastDay);
        if (!CollectionUtils.isEmpty(fundsModels)) {
            int parent;
            BigDecimal fundData = BigDecimal.ZERO;
            for (DataFundsModel funds : fundsModels) {
                parent = funds.getRdType() / 100;
                // 其他费用，且不是差旅费
                if (parent >= CostEnum.BOOK.getParent() && parent != CostEnum.TRAVEL.getParent()) {
                    parent = CostEnum.OTHER.getParent();
                }
                if (!dataMap.containsKey(parent)) {
                    Map<Object, BigDecimal> map = new HashMap<>();
                    map.put(funds.getProjectId(), funds.getRdFunds().divide(Constant.TEN_THOUSAND, 2, BigDecimal.ROUND_HALF_UP));
                    map.put("total", funds.getRdFunds().divide(Constant.TEN_THOUSAND, 2, BigDecimal.ROUND_HALF_UP));
                    dataMap.put(parent, map);
                } else {
                    Map<Object, BigDecimal> umap = dataMap.get(parent);
                    umap.put("total", umap.get("total").add(funds.getRdFunds().divide(Constant.TEN_THOUSAND, 2, BigDecimal.ROUND_HALF_UP)));
                    if (!umap.containsKey(funds.getProjectId())) {
                        umap.put(funds.getProjectId(), fundData);

                    }
                    BigDecimal result = umap.get(funds.getProjectId()).add(funds.getRdFunds().divide(Constant.TEN_THOUSAND, 2, BigDecimal.ROUND_HALF_UP));
                    umap.put(funds.getProjectId(), result);
                }
            }
        }
        return dataMap;
    }

    @Override
    public PageModel<List<StageTrialModel>> getYieldInfo(QueryMaterialTrackModel model) {
        Date monthOfFirst = DateUtil.getMonthFirstDay(model.getMonth());
        Pagination page = model.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> ascs = new ArrayList<>();
            ascs.add("trialDate");
            page.setAscs(ascs);
        }
        return PageUtils.build(page, trialProdDao.getTrialInfo(page, model.getProjectId(), monthOfFirst));


    }

    @Override
    public PageModel<List<MaterialPlanModel>> getMaterials(QueryMaterialTrackModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> ascs = new ArrayList<>();
            ascs.add("acqDate");
            page.setAscs(ascs);
        }
        List<MaterialPlanModel> list = new ArrayList<>();
        if (query.getMonth() != null) {
            Date beginDate = DateUtil.getMonthFirstDay(query.getMonth());
            Date endDate = DateUtil.getMonthLastDay(query.getMonth());
            list = materialDao.getMaterials(page, query.getProjectId(), beginDate, endDate, query.getType() - 1, query.getRdType());
        }
        return PageUtils.build(page, list);
    }

    @Override
    public List<Map<String, Object>> getMaterialSummary(int type, int year, Integer companyId) {
        Date firstDay = DateUtil.getYearFirstDay(year);
        Date lastDay = DateUtil.getYearLastDay(year);
        List<MaterialFeeModel> list = projectInfoSummaryDao.getMaterialSummary(type, companyId, firstDay, lastDay);
        Map<String, Object> rawMap = new HashMap<>();
        rawMap.put("RD", "原材料(元)");
        Map<String, Object> auxiliaryMap = new HashMap<>();
        auxiliaryMap.put("RD", "辅料(元)");
        Map<String, Object> reserveMap = new HashMap<>();
        reserveMap.put("RD", "工艺装备用料(元)");
        BigDecimal rawTotal, auxiliaryTotal, reserveTotal;
        rawTotal = auxiliaryTotal = reserveTotal = BigDecimal.ZERO;
        // 2022年7月22日 zdf 注释每个费用除万操作及万元 邓总 .divide(Constant.TEN_THOUSAND,2,BigDecimal.ROUND_HALF_UP)
        for (MaterialFeeModel item : list) {
            String key = item.getProjectId().toString();
            BigDecimal current = item.getRaw() != null ? item.getRaw() : BigDecimal.ZERO;
            rawMap.put(key, current);
            rawTotal = rawTotal.add(current);
            current = item.getAuxiliary() != null ? item.getAuxiliary() : BigDecimal.ZERO;
            auxiliaryMap.put(key, current);
            auxiliaryTotal = auxiliaryTotal.add(current);
            current = item.getReserve() != null ? item.getReserve() : BigDecimal.ZERO;
            reserveMap.put(key, current);
            reserveTotal = reserveTotal.add(current);
        }
        rawMap.put("type", type);
        auxiliaryMap.put("type", type);
        reserveMap.put("type", type);
        rawMap.put("total", rawTotal);
        auxiliaryMap.put("total", auxiliaryTotal);
        reserveMap.put("total", reserveTotal);
        return CollUtil.newArrayList(rawMap, auxiliaryMap, reserveMap);
    }

    @Override
    public List<Map<String, Object>> getYieldAmount(Integer year, Integer companyId) throws Exception {
        List<Map<String, Object>> dataList = getCommonSummaryData(year, companyId, null, "yieldAmount");
        return dataList;
    }

    private List<Map<String, Object>> getCommonSummaryData(Integer year, Integer companyId, Integer type, String attributeName) throws Exception {
        List<Map<String, Object>> dataList = new ArrayList<>();
        List<ProjectInfoSummaryEntity> models = new ArrayList<>();
        List<ProjectEntity> projects = projectDao.getProjectByCompany(companyId, year);
        if (!CollectionUtils.isEmpty(projects)) {
            List<Integer> projectIds = projects.stream().map(e -> e.getId()).collect(Collectors.toList());
            Date firstDay = DateUtil.getYearFirstDay(year);
            Date lastDay = DateUtil.getYearLastDay(year);
            if (type != null) {
                if (type == 0) {
                    attributeName = "material" + attributeName;
                } else if (type == 1) {
                    attributeName = "trial" + attributeName;
                } else if (type == 2) {
                    attributeName = "repair" + attributeName;
                }
            }
            models = projectInfoSummaryDao.getSummaryByMonth(projectIds, firstDay, lastDay, type, attributeName);
            if (CollectionUtils.isEmpty(models)) {
                return dataList;
            }
            Map<String, Map<String, Object>> dataMap = new LinkedHashMap<>();
            for (ProjectInfoSummaryEntity entity : models) {
                String dateStr = DateUtil.format(entity.getMonth(), "yyyy-MM-dd hh:mm:ss");
                if (!dataMap.containsKey(dateStr)) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("total", BigDecimal.ZERO);
                    dataMap.put(dateStr, map);

                }
                Map<String, Object> map = dataMap.get(dateStr);
                BigDecimal data = (BigDecimal) ProjectInfoSummaryEntity.getValue(entity, attributeName);
                if (data == null) {
                    data = BigDecimal.ZERO;
                }
                BigDecimal total = (BigDecimal) map.get("total");
                total = total.add(data);
                map.put("total", total);
                map.put(entity.getProjectId().toString(), data);
            }
            for (String key : dataMap.keySet()) {
                Map<String, Object> data = dataMap.get(key);
                data.put("month", key);
                dataList.add(data);
            }
        }
        return dataList;
    }
}
