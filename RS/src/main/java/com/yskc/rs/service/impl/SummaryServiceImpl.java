package com.yskc.rs.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.JsonUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.dao.ActivityDao;
import com.yskc.rs.dao.ProjectDao;
import com.yskc.rs.dao.ProjectMaterialDao;
import com.yskc.rs.dao.SummaryDao;
import com.yskc.rs.dao.company.CompanyDao;
import com.yskc.rs.dao.data.CompanyCostDao;
import com.yskc.rs.dao.init.InitEquipmentDao;
import com.yskc.rs.dao.init.InitMemberDao;
import com.yskc.rs.dao.project.*;
import com.yskc.rs.entity.ActivityEntity;
import com.yskc.rs.entity.company.CompanyEntity;
import com.yskc.rs.entity.data.CompanyCostEntity;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.enums.CostEnum;
import com.yskc.rs.enums.EmployeeTypeEnum;
import com.yskc.rs.enums.FieldConfigTypeEnum;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.activity.ModifyModel;
import com.yskc.rs.models.aggregation.AggExportModel;
import com.yskc.rs.models.aggregation.EmployeeExportModel;
import com.yskc.rs.models.aggregation.ExportSheetModel;
import com.yskc.rs.models.aggregation.ExportTermModel;
import com.yskc.rs.models.employee.ExportEmployeeModel;
import com.yskc.rs.models.equipment.ExportEquipmentModel;
import com.yskc.rs.models.fieldconfig.FieldConfigModel;
import com.yskc.rs.models.finance.AuditStatusModel;
import com.yskc.rs.models.outsourcing.OutsourcingSummaryModel;
import com.yskc.rs.models.project.ProjectDetailModel;
import com.yskc.rs.models.project.ProjectInfoModel;
import com.yskc.rs.models.project.ProjectListModel;
import com.yskc.rs.models.project.RdSalaryDetailModel;
import com.yskc.rs.models.summary.*;
import com.yskc.rs.service.SalaryService;
import com.yskc.rs.service.SummaryService;
import com.yskc.rs.utils.ToolUtils;
import com.yskc.rs.utils.YsExcelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-19 14:44
 * @Description: 费用汇总业务层实现
 */
@Service
public class SummaryServiceImpl implements SummaryService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String KEYFORMAT = "{0}_{1}";

    @Autowired
    private SummaryDao summaryDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private RsConfig rsConfig;
    @Autowired
    private CompanyCostDao companyCostDao;

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private CommonService commonService;

    @Autowired
    private BudgetDao budgetDao;

    @Autowired
    private ProjectMaterialDao projectMaterialDao;

    @Autowired
    private ProjectRdEquipmentDao projectRdEquipmentDao;

    @Autowired
    private ProjectDesignDao projectDesignDao;

    @Autowired
    private ProjectInspectionDao projectInspectionDao;

    @Autowired
    private ProjectEnergyDao projectEnergyDao;

    @Autowired
    private ProjectRdEmployeeDao projectRdEmployeeDao;

    @Autowired
    private SalaryService salaryService;

    @Autowired
    private InitMemberDao initMemberDao;
    @Autowired
    private InitEquipmentDao initEquipmentDao;
    @Autowired
    private ProjectOutsourcingDao projectOutsourcingDao;
    @Autowired
    private AuditRdFeeDao auditRdFeeDao;


    @Override
    public Map<String, Object> getRdFundsByProjectId(Integer projectId, Integer year) {
        Map<String, Object> result = new HashMap<>(2);
        Date beginDate = DateUtil.getYearFirstDay(year);
        Date endDate = DateUtil.getYearLastDay(year);
        result.put("rdFunds", summaryDao.getRdFundsByProjectId(projectId, beginDate, endDate));
        //result.put("status", commonService.getStatusMap(projectId));
        Map<String, AuditStatusModel> map = new HashMap<>();
        List<AuditStatusModel> models = auditRdFeeDao.getStatus(projectId, beginDate, endDate);
        if (!CollectionUtils.isEmpty(models)) {
            models.forEach(item -> {
                Date month = item.getMonth();
                String format = DateUtil.format(month, "yyyy-MM");
                map.put(format + "_" + item.getRdType(), item);
            });
        }
        result.put("status", map);
        return result;
    }

    @Override
    public List<SimpleSummaryModel> getRdSummaryByMonth(Integer companyId, Integer rdYear, Date rdMonth, int[] rdTypes, Integer projectId) {
        return summaryDao.getRdSummaryByMonth(companyId, rdYear, rdMonth, rdTypes, projectId);
    }

    @Override
    public List<RdSituationModel> rdSituation(Integer companyId, Integer year) {
        List<RdSituationModel> rdSituation = summaryDao.getRdSituation(companyId, year);
        if (CollectionUtils.isEmpty(rdSituation)) {
            return rdSituation;
        }
        Map<Integer, RdSituationModel> idMap = new LinkedHashMap<>();
        List<Integer> ids = new ArrayList<>();
        BigDecimal thousand = new BigDecimal(1000);
        for (int i = 0; i < rdSituation.size(); i++) {
            RdSituationModel item = rdSituation.get(i);
            item.setNum(i + 1);
            idMap.put(item.getId(), item);
            ids.add(item.getId());
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        Date yearBegin = cn.hutool.core.date.DateUtil.beginOfYear(calendar.getTime());
        Date yearEnd = cn.hutool.core.date.DateUtil.endOfYear(yearBegin);
        List<RdSituationModel> situationFunds = summaryDao.getRdSituationFunds(companyId, yearBegin, yearEnd);
        situationFunds.forEach(item -> {
            Integer projectId = item.getParentId() > 0 ? item.getParentId() : item.getId();
            if (idMap.containsKey(projectId)) {
                RdSituationModel current = idMap.get(projectId);
                current.addCost(item.getCost().divide(thousand, 2, BigDecimal.ROUND_HALF_UP));
            }
        });
        List<MiniRdSituationModel> minis = summaryDao.getRdSituationEmployee(yearBegin, yearEnd);
        if (CollectionUtils.isEmpty(minis)) {
            return rdSituation;
        }
        // 计算人月 = 项目人员总工时 / 22.75* 8
        BigDecimal div = BigDecimal.valueOf(22.75 * 8);
        for (MiniRdSituationModel mini : minis) {
            Integer projectId = mini.getParentId() > 0 ? mini.getParentId() : mini.getId();
            if (idMap.containsKey(projectId)) {
                if (null != mini.getEmployee()) {
                    idMap.get(projectId).addEmployee(mini.getEmployee());
                }
                if (null != mini.getTotalWork()) {
                    idMap.get(projectId).addTotalWork(mini.getTotalWork().divide(div, 2, BigDecimal.ROUND_HALF_UP));
                }
            }
        }
        return rdSituation;
    }

    @Override
    public Map<String, Object> rdActive(Integer companyId, Integer year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        Date yearBegin = cn.hutool.core.date.DateUtil.beginOfYear(calendar.getTime());
        Date yearEnd = cn.hutool.core.date.DateUtil.endOfYear(yearBegin);
        Map<String, Object> activeMap = summaryDao.getRdActive(companyId, yearBegin, yearEnd);
        Map<String, Object> temp = summaryDao.getActiveSummary(companyId, year, yearBegin, yearEnd);
        if (null == temp) {
            temp = new HashMap<>();
        }
        if (CollectionUtils.isEmpty(activeMap)) {
            activeMap = new HashMap<>();
        } else {
            // 如果存在activeMap，则放入temp中，进行整除千操作
            temp.put("equipmentAmount", activeMap.get("equipmentAmount"));
        }
        if (!CollectionUtils.isEmpty(temp)) {
            BigDecimal thousand = new BigDecimal(1000);
            for (String key : temp.keySet()) {
                if (null != temp.get(key)) {
                    activeMap.put(key, ((BigDecimal) temp.get(key)).divide(thousand, 2, BigDecimal.ROUND_HALF_UP));
                }
            }
        }
        List<Map<String, String>> mapList = activityDao.getActiveMap(companyId, year);
        for (Map<String, String> m : mapList) {
            activeMap.put(m.get("pKey"), m.get("pValue"));
        }
        String k22 = "k22";// 期末机构数 默认为1
        if (!activeMap.containsKey(k22)) {
            activeMap.put(k22, 1);
        }
        return activeMap;
    }


    @Override
    public void exportSituation(Integer companyId, Integer year, OutputStream out) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(year, Calendar.DECEMBER, 31, 0, 0, 0);
        Date yearEnd = calendar.getTime();
        CompanyEntity companyEntity = companyDao.selectById(companyId);
        List<RdSituationModel> tableList = rdSituation(companyId, year);

        Map<String, Object> data = new HashMap<>();
        data.put("companyName", companyEntity.getCompanyName());
        data.put("validDate", DateUtil.format(yearEnd, "yyyy年MM月"));
        data.put("taxpayerId", companyEntity.getTaxpayerId());
        Integer employee = 0;
        BigDecimal avgWork = BigDecimal.ZERO;
        BigDecimal cost = BigDecimal.ZERO;
        BigDecimal gov = BigDecimal.ZERO;
        int count = 0;
        try {
            for (RdSituationModel model : tableList) {
                model.setNum(++count);
                model.setBeginDateStr(null != model.getBeginDate() ? DateUtil.format(model.getBeginDate(), "yyyyMM") : "");
                model.setEndDateStr(null != model.getEndDate() ? DateUtil.format(model.getEndDate(), "yyyyMM") : "");
                if (null != model.getAvgWork()) {
                    avgWork = avgWork.add(model.getAvgWork());
                    model.setAvgWork(model.getAvgWork().setScale(2, BigDecimal.ROUND_HALF_UP));
                }
                if (null != model.getEmployee()) {
                    employee += model.getEmployee();
                }
                if (null != model.getCost()) {
                    cost = cost.add(model.getCost());
                }
                if (null != model.getGovCost()) {
                    gov = gov.add(model.getGovCost());
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        data.put("list", tableList);
        data.put("employee", employee);
        data.put("cost", cost.setScale(2, BigDecimal.ROUND_HALF_UP));
        data.put("avgWork", avgWork.setScale(2, BigDecimal.ROUND_HALF_UP));
        data.put("gov", gov.setScale(2, BigDecimal.ROUND_HALF_UP));
        data.put("y", DateUtil.getCurrentYear());
        data.put("year", year);
        data.put("m", DateUtil.getCurrentMonth());
        data.put("d", DateUtil.getCurrentDay());
        YsExcelUtils.generalReport(data, rsConfig.getUploadConfig().getResourcePath() + Constant.TEMPLATE_DIR + "/RDSituation.xls", (workbook) -> {
            if (workbook != null) {
                try {
                    workbook.write(out);
                    workbook.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        });
    }

    @Override
    public void exportActive(Integer companyId, Integer year, OutputStream out) {

        Map<String, Object> map = rdActive(companyId, year);
        CompanyEntity companyEntity = companyDao.selectById(companyId);
        map.put("companyName", companyEntity.getCompanyName());
        String template = "RDActive.xls";
        if (year >= 2021) {
            template = 2021 + template;
            map.put("nextYear", year + 1);
        } else {
            map.put("validDate", DateUtil.format(DateUtil.getYearLastDay(year), "yyyy年MM月"));
        }
        map.put("taxpayerId", companyEntity.getTaxpayerId());
        map.put("year", year);
        map.put("issueDate", DateUtil.format(new Date(), "yyyy年MM月dd日"));
        YsExcelUtils.generalReport(map, rsConfig.getUploadConfig().getResourcePath() + Constant.TEMPLATE_DIR + template, (workbook) -> {
            if (workbook != null) {
                try {
                    workbook.write(out);
                    workbook.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }

        });
    }

    /**
     * 保存公司成本
     *
     * @param userInfo
     * @param costEntity
     * @return
     * @throws OwnerException
     */
    @Override
    public Boolean saveCompanyCost(UserInfo userInfo, CompanyCostEntity costEntity) throws OwnerException {
        try {
            Date now = new Date();
            Map<String, Object> columnMap = new HashMap<>();
            columnMap.put("companyId", userInfo.getCompanyId());
            columnMap.put("year", costEntity.getYear());
            columnMap.put("rdType", costEntity.getRdType());
            List<CompanyCostEntity> companyCostEntityList = companyCostDao.selectByMap(columnMap);
            if (companyCostEntityList.isEmpty()) {
                costEntity.setLastUpdateTime(now);
                costEntity.setMonth(-1);
                costEntity.setMsLastUpdatorId(userInfo.getMsUserId());
                costEntity.setLastUpdatorId(userInfo.getUserId());
                costEntity.setCompanyId(userInfo.getCompanyId());
                costEntity.setCreateTime(now);
                costEntity.setCreatorId(userInfo.getUserId());
                costEntity.setMsCreatorId(userInfo.getMsUserId());
                companyCostDao.insert(costEntity);
            } else {
                CompanyCostEntity companyCostEntity = companyCostEntityList.get(0);
                companyCostEntity.setRdFunds(costEntity.getRdFunds());
                companyCostEntity.setLastUpdateTime(now);
                companyCostEntity.setMsLastUpdatorId(userInfo.getMsUserId());
                companyCostEntity.setLastUpdatorId(userInfo.getUserId());
                companyCostDao.updateById(companyCostEntity);
            }
        } catch (Exception ex) {
            logger.error("saveCompanyCost", ex);
            throw new OwnerException(ErrorEnum.FAIL);
        }
        return true;
    }

    /**
     * 获取公司年度成本
     *
     * @param year
     * @param userInfo
     * @return
     */
    @Override
    public List<CompanyCostEntity> getCompanyCostList(Integer year, UserInfo userInfo) {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("companyId", userInfo.getCompanyId());
        columnMap.put("year", year);
        return companyCostDao.selectByMap(columnMap);
    }

    @Override
    public Boolean saveProjectGovCost(UserInfo userInfo, RdSituationModel rdSituationModel) {
        Integer lastRsId = userInfo.getUserSource() == 0 ? userInfo.getId() : -1;
        Integer lastMsId = userInfo.getUserSource() == 1 ? userInfo.getId() : -1;
        Date now = new Date();
        return projectDao.saveProjectGovCost(rdSituationModel.getId(), rdSituationModel.getGovCost(), lastRsId, lastMsId, now) > 0;
    }

    @Override
    public Boolean saveActivity(UserInfo userInfo, ModifyModel modifyModel) {
        Integer rsId = userInfo.getUserId();
        Integer msId = userInfo.getMsUserId();
        Date now = new Date();
        ActivityEntity activityEntity = activityDao.getActivityByYearAndPkey(userInfo.getCompanyId(), modifyModel.getYear(), modifyModel.getpKey());
        if (null == activityEntity) {
            activityEntity = new ActivityEntity(rsId, rsId, now, now, msId, msId, userInfo.getCompanyId(), modifyModel.getYear(), modifyModel.getpKey(), modifyModel.getpValue());
            return activityDao.insert(activityEntity) > 0;
        }
        activityEntity.setLastUpdateTime(now);
        activityEntity.setLastUpdatorId(rsId);
        activityEntity.setMsLastUpdatorId(msId);
        activityEntity.setPValue(modifyModel.getpValue());
        return activityDao.updateById(activityEntity) > 0;
    }

    @Override
    public List<RdSalaryDetailModel> getRdSalaryDetail(Date month, UserInfo userInfo) {
        List<RdSalaryDetailModel> result = new ArrayList<>();
        Date beginMonth = DateUtil.getMonthFirstDay(month);
        Date endMonth = DateUtil.getMonthLastDay(month);
        Integer companyId = userInfo.getCompanyId();
        List<ProjectInfoModel> projectInfoList = summaryDao.getProjectInfo(beginMonth, endMonth, companyId);
        if (!CollectionUtils.isEmpty(projectInfoList)) {
            Map<Integer, RdSalaryDetailModel> projectMap = new LinkedHashMap<>();
            List<Integer> projectIds = new ArrayList<>();
            Integer num = 1;
            for (ProjectInfoModel info : projectInfoList) {
                RdSalaryDetailModel rdSalaryDetailModel = new RdSalaryDetailModel();
                rdSalaryDetailModel.setRdTitle(info.getRdTitle());
                rdSalaryDetailModel.setProjectId(info.getId());
                rdSalaryDetailModel.setWorkshop(info.getWorkshop());
                rdSalaryDetailModel.setNum(++num);
                rdSalaryDetailModel.setPname(info.getPname());
                rdSalaryDetailModel.setRdNumber(info.getRdNumber());
                rdSalaryDetailModel.setAccountTitleId(0);
                projectMap.put(info.getId(), rdSalaryDetailModel);
                projectIds.add(info.getId());
            }
            List<RdSalaryDetailModel> detailList = summaryDao.getAccountSalaryDetail(month, projectIds, companyId);
            Map<Integer, List<RdSalaryDetailModel>> accountMap = new HashMap<>();
            Map<Integer, String> accountNameMap = new HashMap<>();
            detailList.forEach(item -> {
                Integer key = item.getAccountTitleId() <= 0 ? 0 : item.getAccountTitleId();
                if (!accountMap.containsKey(key)) {
                    accountMap.put(key, new ArrayList<>());
                    accountNameMap.put(key, item.getAccountName());
                }
                accountMap.get(key).add(item);
            });
            RdSalaryDetailModel allTotal = new RdSalaryDetailModel();
            allTotal.setSalaryMap(new LinkedHashMap<>());
            allTotal.setInsuranceMap(new LinkedHashMap<>());
            for (Integer accountId : accountMap.keySet()) {
                Map<Integer, RdSalaryDetailModel> tempMap = new LinkedHashMap<>();
                projectMap.values().forEach(v -> {
                    tempMap.put(v.getProjectId(), RdSalaryDetailModel.copyValue(v));
                });
                RdSalaryDetailModel total = new RdSalaryDetailModel();
                total.setSalaryMap(new LinkedHashMap<>());
                total.setInsuranceMap(new LinkedHashMap<>());
                accountMap.get(accountId).forEach(item -> {
                    RdSalaryDetailModel rdSalary = tempMap.get(item.getProjectId());
                    setDefaultDetail(rdSalary, item);
                    rdSalary.setSalaryMap(ToolUtils.loadDetailMap(item.getPayDetail(), rdSalary.getSalaryMap(), item.getRdRatio(), true));
                    rdSalary.setInsuranceMap(ToolUtils.loadDetailMap(item.getInsuranceDetail(), rdSalary.getInsuranceMap(), item.getRdRatio(), true));
                    rdSalary.setAccountTitleId(accountId);
                });
                List<RdSalaryDetailModel> dList = new ArrayList<>(tempMap.values());
                for (RdSalaryDetailModel m : dList) {
                    m.setNum(++num);
                    m.setAccountTitleId(accountId);
                    m.setAccountName(accountNameMap.get(accountId));
                    RdSalaryDetailModel.compareToDetail(m);
                    setDefaultDetail(total, m);
                    m.setTotal((null != m.getRdPay() ? m.getRdPay() : BigDecimal.ZERO).add(null != m.getRdInsuranceFund() ? m.getRdInsuranceFund() : BigDecimal.ZERO));
                    if (!CollectionUtils.isEmpty(m.getSalaryMap())) {
                        for (String col : m.getSalaryMap().keySet()) {
                            total.getSalaryMap().put(col, total.getSalaryMap().getOrDefault(col, BigDecimal.ZERO).add(m.getSalaryMap().get(col)));
                            allTotal.getSalaryMap().put(col, allTotal.getSalaryMap().getOrDefault(col, BigDecimal.ZERO).add(m.getSalaryMap().get(col)));
                        }
                    }
                    if (!CollectionUtils.isEmpty(m.getInsuranceMap())) {
                        for (String col : m.getInsuranceMap().keySet()) {
                            total.getInsuranceMap().put(col, total.getInsuranceMap().getOrDefault(col, BigDecimal.ZERO).add(m.getInsuranceMap().get(col)));
                            allTotal.getInsuranceMap().put(col, allTotal.getInsuranceMap().getOrDefault(col, BigDecimal.ZERO).add(m.getInsuranceMap().get(col)));
                        }
                    }
                }
                total.setAccountName(accountNameMap.get(accountId));
                total.setAccountTitleId(accountId);
                total.setNum(++num);
                total.setTotal((null != total.getRdPay() ? total.getRdPay() : BigDecimal.ZERO).add(null != total.getRdInsuranceFund() ? total.getRdInsuranceFund() : BigDecimal.ZERO));
                total.setRdTitle("小计");
                dList.add(total);
                setDefaultDetail(allTotal, total);
                result.addAll(dList);
            }
            allTotal.setNum(++num);
            allTotal.setTotal((null != allTotal.getRdPay() ? allTotal.getRdPay() : BigDecimal.ZERO).add(null != allTotal.getRdInsuranceFund() ? allTotal.getRdInsuranceFund() : BigDecimal.ZERO));
            allTotal.setAccountName("总计");
            allTotal.setAccountTitleId(-2);
            result.add(allTotal);
        }
        return result;
    }

    @Override
    public Map<String, Object> getSummaryCostByYear(Integer year, Integer companyId) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();//年
        Calendar calendar1 = Calendar.getInstance();
        calendar1.clear();
        calendar1.set(Calendar.YEAR, year);
        calendar1.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar1.getTime();
        //研发费用曲线图数据
        List<Map<String, Object>> graphDataList = new ArrayList<>();
        //饼状图数据
        Map<String, BigDecimal> pieChartData = new HashMap<>();
        List<Map<String, Object>> summaryResult = summaryDao.getSummaryCostByYear(currYearFirst, currYearLast, companyId);
        for (Map<String, Object> graphMap : summaryResult) {
            Map<String, Object> graphDataMap = new HashMap<>();
            Set<String> KeySet = graphMap.keySet();
            for (String key : KeySet) {
                if (!key.equals("month")) {
                    BigDecimal cost = ((BigDecimal) graphMap.get(key)).divide(Constant.TEN_THOUSAND, 2, BigDecimal.ROUND_HALF_UP);
                    graphDataMap.put(key, cost);
                    if (!pieChartData.containsKey(key)) {
                        pieChartData.put(key, cost);
                    } else {
                        BigDecimal sumCost = pieChartData.get(key).add(cost);
                        pieChartData.put(key, sumCost);
                    }
                }
                String month = DateUtil.getTimeStr((Date) graphMap.get("month"), "yyyy-MM-dd");
                graphDataMap.put("month", month);
            }
            graphDataList.add(graphDataMap);
        }
        //获取年预算
        Map<String, BigDecimal> budgetYearData = budgetDao.getYearBudget(companyId, year);
        //柱状图数据
        Map<String, SummaryBudgetModel> histogramData = new HashMap<>();
        if (budgetYearData != null) {
            Set<String> budgetSet = budgetYearData.keySet();
            for (String budgetKey : budgetSet) {
                SummaryBudgetModel summaryBudgetModel = new SummaryBudgetModel();
                BigDecimal budgetCost = budgetYearData.get(budgetKey).divide(Constant.TEN_THOUSAND, 2, BigDecimal.ROUND_HALF_UP);
                summaryBudgetModel.setBudgetCost(budgetCost);
                summaryBudgetModel.setSummaryCost(pieChartData.get(budgetKey) != null ? pieChartData.get(budgetKey) : new BigDecimal(0.00));
                histogramData.put(budgetKey, summaryBudgetModel);
            }
        } else {
            Set<String> titleSet = pieChartData.keySet();
            for (String title : titleSet) {
                SummaryBudgetModel summaryBudgetModel = new SummaryBudgetModel();
                summaryBudgetModel.setBudgetCost(new BigDecimal(0.00));
                summaryBudgetModel.setSummaryCost(pieChartData.get(title) != null ? pieChartData.get(title) : new BigDecimal(0.00));
                histogramData.put(title, summaryBudgetModel);
            }
        }
        Map<String, Object> allData = new HashMap<>();
        allData.put("graph", graphDataList);
        allData.put("pieChart", pieChartData);
        allData.put("histogram", histogramData);
        return allData;
    }

    @Override
    public void exportFunds(ExportTermModel exportTerm, UserInfo info, OutputStream out) throws OwnerException {
        String templatePath = null;
        List<String> sheetNames = new ArrayList<>();
        List<? extends AggExportModel> exportData = null;
        String basePath = rsConfig.getUploadConfig().getResourcePath() + Constant.AGG_TEMPLATE_DIR;
        Map<String, Object> colTitleMap = new HashMap<>();
        switch (CostEnum.getCostEnum(exportTerm.getTypes().get(0))) {
            case SALARY:
            case INSURANCE:
                List<EmployeeExportModel> dataList = projectRdEmployeeDao.exportFromEmployee(exportTerm, info.getCompanyId(),
                        cn.hutool.core.date.DateUtil.year(exportTerm.getMonths().get(0)));
                loadEmployeeData(dataList, info.getCompanyId(), colTitleMap);
                exportData = dataList;
                templatePath = basePath + "人员归集费用模板.xls";
                break;
            case PROD:
            case LAB:
            case ASSETS_AMORTIZATION:
                exportData = projectRdEquipmentDao.exportFromEquipment(exportTerm, info.getCompanyId(),
                        cn.hutool.core.date.DateUtil.year(exportTerm.getMonths().get(0)));
                templatePath = basePath + "/设备折旧归集费用模板.xls";
                break;
            case MATERIAL:
            case TRIAL_MATERIAL:
            case REPAIR_MATERIAL:
                exportData = projectMaterialDao.exportFromMaterial(exportTerm);
                templatePath = basePath + "材料归集费用模板.xls";
                break;
            case PAPER_MATERIAL:
            case PAPER_TRIAL:
                exportData = projectMaterialDao.exportFromMaterial(exportTerm);
                templatePath = basePath + "造纸材料归集费用模板.xls";
                break;
            case IRON_MATERIAL:
            case IRON_TRIAL:
                exportData = projectMaterialDao.exportFromMaterial(exportTerm);
                templatePath = basePath + "流程型材料归集费用模板.xls";
                break;
            case STIMULUS_PROD:
                exportData = projectRdEquipmentDao.getEquipmentPowerData(exportTerm, info.getCompanyId(),
                        cn.hutool.core.date.DateUtil.year(exportTerm.getMonths().get(0)));
                templatePath = basePath + "设备动力归集费用模板.xls";
                break;
            case TRIAL_STIMULUS:
            case STIMULUS:
            case FUEL:
                exportData = projectEnergyDao.exportFromStimulus(exportTerm, exportTerm.getTypes().get(0));
                templatePath = basePath + "动力归集费用模板.xls";
                break;
            case IRON_FUEL:
            case IRON_STIMULUS:
                exportData = projectEnergyDao.exportFromStimulus(exportTerm, exportTerm.getTypes().get(0));
                templatePath = basePath + "流程型动力归集费用模板.xls";
                break;
            case DESIGN:
                exportData = projectDesignDao.exportFromDesign(exportTerm);
                templatePath = basePath + "设计归集费用模板.xls";
                break;
            case INSPECTION:
            case REPAIR:
            case TRAVEL:
            case SOFT_AMORTIZATION:
            case PATENT_AMORTIZATION:
            case OTHER_AMORTIZATION:
            case TRIAL_PROD:
            case OTHER:
            case BENEFITS:
            case COPYRIGHT:
            case RD_PRODUCTION:
            case BOOK:
            case SAMPLE_MACHINE:
                exportData = projectInspectionDao.ExportFromOther(exportTerm);
                templatePath = basePath + "其他归集费用模板.xls";
                break;
            default:
                break;
        }
        if (templatePath == null) {
            throw new OwnerException("未获取到相关类型的费用。");
        }
        List<Map<String, Object>> dataList;
        String name = cn.hutool.core.date.DateUtil.year(exportTerm.getMonths().get(0)) + "年" + exportTerm.getName() + "核算明细表";
        switch (exportTerm.getExportType()) {
            case 0://单sheet
                sheetNames.add(info.getCompanyName() + name);
                dataList = exportAllData(name, info.getCompanyName(), (List<AggExportModel>) exportData, colTitleMap);
                break;
            case 1:
                dataList = exportDataByProject(name, info.getCompanyName(), (List<AggExportModel>) exportData, sheetNames, colTitleMap);
                break;
            case 2:
                dataList = loadExportData(name, info.getCompanyName(), (List<AggExportModel>) exportData, sheetNames, colTitleMap);
                break;
            default:
                dataList = new ArrayList<>();
        }
        Map<Integer, List<Map<String, Object>>> map = new HashMap<>();
        map.put(0, dataList);
        YsExcelUtils.generalMoreSheetsReport(map, templatePath, workbook -> {
            if (workbook != null) {
                try {
                    YsExcelUtils.setSheetName(workbook, sheetNames);
                    workbook.write(out);
                    workbook.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        });
    }

    @Override
    public void exportDataDetail(Integer year, UserInfo info, Integer exportType, OutputStream out) throws OwnerException {
        List<String> sheetNames = new ArrayList<>();
        List<DataFundsModel> fundsData = getYearFundsData(year, info.getCompanyId());
        List<Map<String, Object>> resultMap = getDetailData(fundsData, year, info, exportType, sheetNames);
        Map<Integer, List<Map<String, Object>>> finalData = new LinkedHashMap<>();
        finalData.put(0, resultMap);
        String templatePath = rsConfig.getUploadConfig().getResourcePath() + Constant.AGG_TEMPLATE_DIR + "归集明细表模板.xls";
        YsExcelUtils.generalMoreSheetsReport(finalData, templatePath, workbook -> {
            if (workbook != null) {
                try {
                    YsExcelUtils.setSheetName(workbook, sheetNames);
                    workbook.write(out);
                    workbook.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        });
    }

    @Override
    public List<Map<String, Object>> getDataDetail(Integer year, UserInfo userInfo, Integer exportType) {
        List<DataFundsModel> fundsData = getYearFundsData(year, userInfo.getCompanyId());
        List<Map<String, Object>> resultMap = getDetailData(fundsData, year, userInfo, exportType, null);
        return resultMap;
    }

    @Override
    public void exportSubsidiaryLedger(Integer year, UserInfo userInfo, OutputStream out, Integer type) {
        List<DataFundsModel> fundsData;
        String firstSheet;
        String secondSheet;
        String title;
        if (type.equals(100)) {
            fundsData = getSalaryFundsData(year, userInfo.getCompanyId());
            firstSheet = "研发工资月报表";
            secondSheet = "研发工资月报表(含子项目)";
            title = "年研发工资月报表";
        } else {
            fundsData = getYearFundsData(year, userInfo.getCompanyId());
            firstSheet = "归集明细表";
            secondSheet = "归集明细表(含子项目)";
            title = "年研究归集明细表";
        }
        List<String> sheetNames = new ArrayList<>();
        List<Map<String, Object>> sheetList = new ArrayList<>();
        sheetNames.add(userInfo.getCompanyName() + year + firstSheet);
        if (CollectionUtils.isEmpty(fundsData)) {
            Map<String, Object> map = new HashMap<>();
            map.put("title", userInfo.getCompanyName() + year);
            map.put("list", new ArrayList<>());
            map.put("fund", new HashMap<>());
            sheetList.add(map);
        } else {
            List<ProjectDetailModel> atomProjects = projectDao.getProjectIdsByYear(userInfo.getCompanyId(), year, false);
            Map<Integer, ProjectDetailModel> atomMap = new LinkedHashMap<>();
            atomProjects.forEach(item -> atomMap.put(item.getId(), item));
            Map<Integer, ProjectDetailModel> unionChildMap = new LinkedHashMap<>();
            Boolean hasChild = projectDao.countChild(userInfo.getCompanyId(), year) > 0;
            if (hasChild) {
                List<ProjectDetailModel> unionChilds = projectDao.getProjectIdsByYear(userInfo.getCompanyId(), year, true);
                unionChilds.forEach(item -> unionChildMap.put(item.getId(), item));
            }
            Map<String, List<DataFundsModel>> dataMap = getDataFundsMap(fundsData, true, true);
            List<Map<String, Object>> atomList = new ArrayList<>();
            Map<String, Object> atomTotal = new HashMap<>();
            atomTotal.put("workshop", "合计");
            List<Map<String, Object>> unionList = new ArrayList<>();
            Map<String, Object> unionTotal = new HashMap<>();
            Map<Integer, CostEnum> parentCostMap = CostEnum.getParentMap(false);
            parentCostMap.keySet().forEach(parentType -> {
                if (type.equals(100)) {
                    if (parentType > 101) {
                        return;
                    }
                } else {
                    if (parentType == 204 || (parentType >= 501 && parentType <= 503) || (parentType >= 600 && parentType <= 603)) {
                        return;
                    }
                }
                loadSubsidiaryLedger(atomMap, atomList, parentCostMap.get(parentType).getTitle(), parentType, dataMap, atomTotal);
                if (hasChild) {
                    loadSubsidiaryLedger(unionChildMap, unionList, parentCostMap.get(parentType).getTitle(), parentType, dataMap, unionTotal);
                }
            });
            Map<String, Object> map = new HashMap<>();
            map.put("title", userInfo.getCompanyName() + year + title);
            map.put("list", atomList);
            map.put("year", year);
            map.put("fund", atomTotal);
            sheetList.add(map);
            if (hasChild) {
                sheetNames.add(userInfo.getCompanyName() + year + secondSheet);
                Map<String, Object> unionMap = new HashMap<>();
                unionMap.put("title", userInfo.getCompanyName() + year + title);
                unionMap.put("list", unionList);
                unionMap.put("year", year);
                unionMap.put("fund", unionTotal);
                sheetList.add(unionMap);
            }
        }
        CollUtil.reverse(sheetList);
        Map<Integer, List<Map<String, Object>>> data = new HashMap<>();
        data.put(0, sheetList);
        String templatePath = rsConfig.getUploadConfig().getResourcePath() + Constant.TEMPLATE_DIR + "归集明细表模板.xls";
        YsExcelUtils.generalMoreSheetsReport(data, templatePath, workbook -> {
            if (workbook != null) {
                try {
                    YsExcelUtils.setSheetName(workbook, sheetNames);
                    workbook.write(out);
                    workbook.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        });
    }


    @Override
    public void exportSummaryReport(Integer year, UserInfo userInfo, OutputStream out) {
        Integer companyId = userInfo.getCompanyId();
        List<DataFundsModel> fundsData = getYearFundsData(year, companyId);
        List<OutsourcingSummaryModel> outsourcingList = projectOutsourcingDao.getMonthOutsourcing(
                DateUtil.getYearFirstDay(year), DateUtil.getYearLastDay(year), companyId);
        List<String> sheetNames = new ArrayList<>();
        List<Map<String, Object>> sheetList = new ArrayList<>();
        if (CollectionUtils.isEmpty(fundsData) && CollectionUtils.isEmpty(outsourcingList)) {
            sheetNames.add(userInfo.getCompanyName() + year + "归集总表");
            Map<String, Object> map = new HashMap<>();
            map.put("title", userInfo.getCompanyName() + year);
            map.put("list", new ArrayList<>());
            map.put("fund", new HashMap<>());
            map.put("noOutsourcing", true);
            sheetList.add(map);
        } else {
            Map<String, List<DataFundsModel>> monthDataMap = new LinkedHashMap<>();
            Map<Integer, CostEnum> parentCostMap = CostEnum.getParentMap(false);
            List<ProjectDetailModel> atomProjects = projectDao.getProjectIdsByYear(companyId, year, false);
            Boolean noOutsourcing = true;
            Map<Integer, ProjectDetailModel> atomMap = new LinkedHashMap<>();
            for (ProjectDetailModel item : atomProjects) {
                if (item.getFormula() > 10) {
                    noOutsourcing = false;
                }
                atomMap.put(item.getId(), item);

            }
            Boolean hasChild = projectDao.countChild(companyId, year) > 0;
            fundsData.forEach(item -> {
                ToolUtils.putAndAdd(monthDataMap, MessageFormat.format(KEYFORMAT,
                        item.getParentId() > 0 ? item.getParentId() : item.getProjectId(),
                        cn.hutool.core.date.DateUtil.month(item.getMonth()) + 1), item);
                if (hasChild && item.getParentId() > 0) {
                    ToolUtils.putAndAdd(monthDataMap, MessageFormat.format(KEYFORMAT,
                            item.getProjectId(), cn.hutool.core.date.DateUtil.month(item.getMonth()) + 1), item);
                }
            });
            Map<String, List<OutsourcingSummaryModel>> monthOutsourcingMap = new LinkedHashMap<>();
            outsourcingList.forEach(item -> {
                if (item.getParentId() > 0) {
                    ToolUtils.putAndAdd(monthOutsourcingMap, MessageFormat.format(KEYFORMAT, item.getParentId(),
                            cn.hutool.core.date.DateUtil.month(item.getMonth()) + 1), item);
                }
                ToolUtils.putAndAdd(monthOutsourcingMap, MessageFormat.format(KEYFORMAT, item.getProjectId(),
                        cn.hutool.core.date.DateUtil.month(item.getMonth()) + 1), item);
            });
            loadSummaryReport(atomMap, parentCostMap, monthDataMap, userInfo.getCompanyName(), year, sheetList, noOutsourcing, monthOutsourcingMap);
            sheetNames.add(userInfo.getCompanyName() + year + "归集总表");
            if (hasChild) {
                Map<Integer, ProjectDetailModel> unionChildMap = new LinkedHashMap<>();
                List<ProjectDetailModel> unionChilds = projectDao.getProjectIdsByYear(companyId, year, true);
                for (ProjectDetailModel item : unionChilds) {
                    if (item.getFormula() > 10) {
                        noOutsourcing = false;
                    }
                    unionChildMap.put(item.getId(), item);
                }
                loadSummaryReport(unionChildMap, parentCostMap, monthDataMap, userInfo.getCompanyName(), year, sheetList, noOutsourcing, monthOutsourcingMap);
                sheetNames.add(userInfo.getCompanyName() + year + "归集总表（含子项目）");
            }
        }
        CollUtil.reverse(sheetList);
        Map<Integer, List<Map<String, Object>>> data = new HashMap<>();
        data.put(0, sheetList);
        //人员清单
        List<Map<String, Object>> employeeData = getEmployeeData(year, userInfo);
        sheetNames.add("研发人员清单");
        data.put(1, employeeData);
        //设备列表
        List<Map<String, Object>> equipmentData = getEquipmentData(year, userInfo);
        sheetNames.add("研发折旧清单");
        data.put(2, equipmentData);
        //项目列表
        List<Map<String, Object>> projectData = getProjectData(year, userInfo);
        sheetNames.add("项目信息表");
        data.put(3, projectData);
        String templatePath = rsConfig.getUploadConfig().getResourcePath() + Constant.TEMPLATE_DIR + "归集总表模板.xls";
        YsExcelUtils.generalMoreSheetsReport(data, templatePath, workbook -> {
            if (workbook != null) {
                try {
                    YsExcelUtils.setSheetName(workbook, sheetNames);
                    workbook.write(out);
                    workbook.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        });
    }

    private List<Map<String, Object>> getProjectData(Integer year, UserInfo userInfo) {
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        List<ProjectListModel> projectList = projectDao.getSummaryProjectInfo(year, userInfo.getCompanyId());
        if (CollectionUtils.isEmpty(projectList)) {
            data.put("list", new ArrayList<>());
        } else {
            String dateFormat = "yyyy.MM.dd";
            projectList.forEach(item -> item.setBeginAndEndDate(
                    DateUtil.format(item.getBeginDate(), dateFormat) + Constant.HYPHEN +
                            DateUtil.format(item.getEndDate(), dateFormat)));
            data.put("list", projectList);
        }
        data.put("year", year);
        data.put("companyName", userInfo.getCompanyName());
        result.add(data);
        return result;
    }

    private List<Map<String, Object>> getEquipmentData(Integer year, UserInfo userInfo) {
        List<ExportEquipmentModel> equipmentModels = initEquipmentDao.getEquipmentsData(year, userInfo.getCompanyId());
        List<Map<String, Object>> sheetList = new ArrayList<>();
        Map<String, Object> dataMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(equipmentModels)) {
            equipmentModels.forEach(item -> {
                item.setDate(cn.hutool.core.date.DateUtil.formatDate(item.getPurchaseDate()));
            });
            dataMap.put("list", equipmentModels);
        } else {
            dataMap.put("list", new ArrayList<>());
        }
        dataMap.put("companyName", userInfo.getCompanyName());
        dataMap.put("year", year);
        sheetList.add(dataMap);
        return sheetList;
    }

    private List<Map<String, Object>> getEmployeeData(Integer year, UserInfo userInfo) {
        List<Map<String, Object>> sheetList = new ArrayList<>();
        List<ExportEmployeeModel> employeeModels = initMemberDao.getExportDatas(userInfo.getCompanyId(), year);
        Map<String, Object> dataMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(employeeModels)) {
            employeeModels.forEach(item -> {
                EmployeeTypeEnum employeeTypeEnum = EmployeeTypeEnum.getEmployeeTypeEnum(item.getEtype());
                if (item.getMaster()) {
                    item.setEmployeeType("负责人");
                } else {
                    item.setEmployeeType(employeeTypeEnum.getType());
                }
                item.setIntoDate(cn.hutool.core.date.DateUtil.formatDate(item.getEdate()));
            });
            dataMap.put("list", employeeModels);
        }
        dataMap.put("companyName", userInfo.getCompanyName());
        dataMap.put("year", year);
        sheetList.add(dataMap);
        return sheetList;
    }

    @Override
    public void exportRdSalaryDetail(Date month, UserInfo userInfo, OutputStream out) {
        List<RdSalaryDetailModel> dataList = getRdSalaryDetail(month, userInfo);
        Map<String, FieldConfigModel> configMap = salaryService.getSalaryConfig(userInfo.getCompanyId());
        String salaryKey = FieldConfigTypeEnum.SALARY.getTitle();
        String insuranceKey = FieldConfigTypeEnum.INSURANCE.getTitle();
        List<HashMap> insuranceConfList = JsonUtils.jsonToList(configMap.get(insuranceKey).getConfig(), HashMap.class);
        List<HashMap> salaryConfList;
        Boolean hasSalary = configMap.containsKey(salaryKey);
        String name = "name";
        String alias = "alias";
        List<Map<String, String>> payTitles = new ArrayList<>();
        if (hasSalary) {
            salaryConfList = JsonUtils.jsonToList(configMap.get(salaryKey).getConfig(), HashMap.class);
            salaryConfList.forEach(item -> {
                Map<String, String> temp = new HashMap<>(1);
                temp.put(alias, item.get(alias).toString());
                payTitles.add(temp);
            });
        } else {
            salaryConfList = new ArrayList<>();
            Map<String, String> temp = new HashMap<>(1);
            temp.put(alias, "应发工资");
            payTitles.add(temp);
        }
        insuranceConfList.forEach(item -> {
            Map<String, String> temp = new HashMap<>(1);
            temp.put(alias, item.get(alias).toString());
            payTitles.add(temp);
        });
        Map<String, Object> dataMap = new HashMap<>();
        String sheetName = userInfo.getCompanyName() + DateUtil.format(month, DateUtil.DEFAULT_YYMM_FORMAT);
        dataMap.put("title", sheetName);
        dataMap.put("payTitles", payTitles);
        List<Map<String, Object>> list = new ArrayList<>();
        dataList.forEach(item -> {
            Map<String, Object> row = new HashMap<>();
            row.put("accountName", item.getAccountName());
            row.put("rdTitle", item.getRdTitle());
            row.put("workshop", item.getWorkshop());
            row.put("rdPay", item.getRdPay());
            row.put("rdInsuranceFund", item.getRdInsuranceFund());
            row.put("total", item.getTotal());
            row.put("pname", item.getPname());
            row.put("rdNumber", item.getRdNumber());

            List<BigDecimal> fees = new ArrayList<>();
            if (hasSalary) {
                Map<String, BigDecimal> salaryData = CollectionUtils.isEmpty(item.getSalaryMap()) ? new HashMap<>() : item.getSalaryMap();
                salaryConfList.forEach(s -> {
                    BigDecimal pay = salaryData.get(s.get(name));
                    if (pay != null) {
                        pay = pay.setScale(2, BigDecimal.ROUND_HALF_UP);
                    }
                    fees.add(pay);
                });
            } else {
                if (null != item.getPay()) {
                    fees.add(item.getPay().setScale(2, BigDecimal.ROUND_HALF_UP));
                }
            }
            Map<String, BigDecimal> inData = CollectionUtils.isEmpty(item.getInsuranceMap()) ? new HashMap<>() : item.getInsuranceMap();
            insuranceConfList.forEach(s -> {
                BigDecimal insurance = inData.get(s.get(name));
                if (insurance != null) {
                    insurance = insurance.setScale(2, BigDecimal.ROUND_HALF_UP);
                }
                fees.add(insurance);
            });
            row.put("fees", fees);
            list.add(row);
        });
        dataMap.put("list", list);
        Map<Integer, List<Map<String, Object>>> data = new HashMap<>();
        data.put(0, CollUtil.newArrayList(dataMap));
        String templatePath = rsConfig.getUploadConfig().getResourcePath() + Constant.TEMPLATE_DIR + "研发薪资明细模板.xls";
        YsExcelUtils.generalMoreSheetsReport(data, templatePath, workbook -> {
            if (workbook != null) {
                try {
                    YsExcelUtils.setSheetName(workbook, CollUtil.newArrayList(sheetName));
                    workbook.write(out);
                    workbook.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        });

    }

    @Override
    public void exportByRD(Integer projectId, Integer year, UserInfo userInfo, OutputStream out) {
        String companyName = userInfo.getCompanyName();
        ProjectEntity projectEntity = projectDao.selectById(projectId);
        String rdTitle = projectEntity.getRdTitle();
        String pname = projectEntity.getPname();
        Integer companyId = userInfo.getCompanyId();
        Date beginMonth = DateUtil.getYearFirstDay(year);
        Date endMonth = DateUtil.getYearLastDay(year);
        Map<Integer, List<Map<String, Object>>> sheetDataMap = new HashMap<>();
        List<DataFundsModel> funds = summaryDao.getDataReportFunds(companyId, beginMonth, endMonth, projectId);
        if (CollectionUtils.isEmpty(funds)) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("list", new ArrayList<>());
            resultMap.put("rdTitle", rdTitle);
            resultMap.put("pname", pname);
            resultMap.put("year", year);
            sheetDataMap.put(0, CollUtil.newArrayList(resultMap));
        } else {
            //key:projectId_rdType value:model
            Map<String, List<DataFundsModel>> dataMap = getDataFundsMap(funds, true, false);
            Map<Integer, CostEnum> parentMap = CostEnum.getParentMap(false);
            Map<Integer, Set<Integer>> sheetType = new LinkedHashMap<>();
            List<Map<String, Object>> resultList = new ArrayList<>();
            String monStr = "month";
            Map<String, Object> totalMap = new LinkedHashMap<>();
            totalMap.put("typeName", "合计");
            BigDecimal total = BigDecimal.ZERO;
            for (Integer parentType : parentMap.keySet()) {
                String key = MessageFormat.format(KEYFORMAT, projectId, parentType);
                if (!dataMap.containsKey(key)) {
                    continue;
                }
                Integer rdType = parentMap.get(parentType).getType();
                Map<String, Object> colMap = new LinkedHashMap<>();
                colMap.put("typeName", parentMap.get(parentType).getTitle());
                List<DataFundsModel> fundsModels = dataMap.get(key);
                Map<String, BigDecimal> monthData = new HashMap<>();
                BigDecimal totalFee = BigDecimal.ZERO;
                for (DataFundsModel model : fundsModels) {
                    Integer index = ExportSheetModel.SHEET_MAP.get(model.getRdType()).getSheetIndex();
                    if (!sheetType.containsKey(index)) {
                        sheetType.put(index, new HashSet<>());
                    }
                    sheetType.get(index).add(model.getRdType());
                    Integer month = cn.hutool.core.date.DateUtil.month(model.getMonth()) + 1;
                    String dataKey = MessageFormat.format("{0}{1}", monStr, month);
                    monthData.put(dataKey, monthData.getOrDefault(dataKey, BigDecimal.ZERO).add(model.getRdFunds()));
                    totalMap.put(dataKey, ((BigDecimal) totalMap.getOrDefault(dataKey, BigDecimal.ZERO)).add(model.getRdFunds()));
                    totalFee = totalFee.add(model.getRdFunds());
                    total = total.add(model.getRdFunds());
                }
                colMap.put("totalFee", totalFee);
                colMap.putAll(monthData);
                resultList.add(colMap);
            }
            totalMap.put("totalFee", total);
            resultList.add(totalMap);
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("list", resultList);
            resultMap.put("rdTitle", rdTitle);
            resultMap.put("pname", pname);
            resultMap.put("year", year);
            sheetDataMap.put(0, CollUtil.newArrayList(resultMap));
            loadRDorMonthData(sheetDataMap, sheetType, CollUtil.newArrayList(projectId), DateUtil.getMonthBetween(beginMonth, endMonth), companyId, year, companyName);
        }
        String path = rsConfig.getUploadConfig().getResourcePath() + Constant.TEMPLATE_DIR + "分月分RD.xls";
        YsExcelUtils.generalMoreSheetsReport(sheetDataMap, path, workbook -> {
            try {
                YsExcelUtils.removeSheetNameBrace(workbook);
                workbook.write(out);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        });
    }

    @Override
    public void exportByMonth(Date month, Integer year, UserInfo userInfo, OutputStream out) {
        Integer companyId = userInfo.getCompanyId();
        String companyName = userInfo.getCompanyName();
        Date begin = DateUtil.getMonthFirstDay(month), end = DateUtil.getMonthLastDay(month);
        List<ProjectDetailModel> atomProjects = projectDao.getProjectsByMonth(companyId, begin, end);
        List<DataFundsModel> funds = summaryDao.getDataReportFunds(companyId, begin, end, null);
        Map<Integer, List<Map<String, Object>>> dataMap = new HashMap<>();
        Map<String, Object> indexMap = new HashMap<>();
        Map<Integer, Set<Integer>> sheetType = new LinkedHashMap<>();
        indexMap.put("title", companyName + DateUtil.format(month, "yyyy年MM月") + "研发费用汇总");
        dataMap.put(0, CollUtil.newArrayList(indexMap));
        if (CollectionUtils.isEmpty(atomProjects) || CollectionUtils.isEmpty(funds)) {
            indexMap.put("list", new ArrayList<>());
        } else {
            Set<Integer> projectIdsSet = new HashSet<>();
            Map<String, List<DataFundsModel>> fundMap = getDataFundsMap(funds, false, true);
            Map<Integer, CostEnum> parentCost = CostEnum.getParentMap(false);
            List<Map<String, Object>> projectFunds = new ArrayList<>();
            Map<String, Object> totalMap = new HashMap<>();
            BigDecimal total = BigDecimal.ZERO;
            for (ProjectDetailModel item : atomProjects) {
                Map<String, Object> row = new HashMap<>();
                row.put("rdTitle", item.getRdTitle());
                BigDecimal rowTotal = BigDecimal.ZERO;
                for (Integer parentType : parentCost.keySet()) {
                    String key = MessageFormat.format(KEYFORMAT, item.getId(), parentType);
                    if (fundMap.containsKey(key)) {
                        for (DataFundsModel fund : fundMap.get(key)) {
                            projectIdsSet.add(fund.getProjectId());
                            Integer index = ExportSheetModel.SHEET_MAP.get(fund.getRdType()).getSheetIndex();
                            if (!sheetType.containsKey(index)) {
                                sheetType.put(index, new HashSet<>());
                            }
                            sheetType.get(index).add(fund.getRdType());
                            String field = parentCost.get(parentType).getField();
                            row.put(field, ((BigDecimal) row.getOrDefault(field, BigDecimal.ZERO)).add(fund.getRdFunds()));
                            rowTotal = rowTotal.add(fund.getRdFunds());
                            total = total.add(fund.getRdFunds());
                            totalMap.put(field, ((BigDecimal) totalMap.getOrDefault(field, BigDecimal.ZERO)).add(fund.getRdFunds()));
                        }
                    }
                }
                row.put("total", rowTotal);
                projectFunds.add(row);
            }
            totalMap.put("rdTitle", "合计");
            totalMap.put("total", total);
            projectFunds.add(totalMap);
            indexMap.put("list", projectFunds);
            loadRDorMonthData(dataMap, sheetType, new ArrayList<>(projectIdsSet), CollUtil.newArrayList(month), companyId, year, companyName);
        }
        String path = rsConfig.getUploadConfig().getResourcePath() + Constant.TEMPLATE_DIR + "月度汇总.xls";
        YsExcelUtils.generalMoreSheetsReport(dataMap, path, workbook -> {
            try {
//                List<Integer> notExistPages = new ArrayList<>(CollUtil.disjunction(sheetType.keySet(), ExportSheetModel.getPages()));
//                CollUtil.sort(notExistPages, new Comparator<Integer>() {
//                    @Override
//                    public int compare(Integer o1, Integer o2) {
//                        return o2.compareTo(o1);
//                    }
//                });
//                notExistPages.forEach(index -> {
//                    workbook.removeSheetAt(index);
//                });
                YsExcelUtils.removeSheetNameBrace(workbook);
                workbook.write(out);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        });
    }

    @Override
    public List<Map<String, Object>> getProjectSummary(Integer projectId, Integer year, Integer companyId) {
        Date begin = DateUtil.getYearFirstDay(year);
        Date end = DateUtil.getYearLastDay(year);
        List<Map<String, Object>> list = new ArrayList<>();
        List<DataFundsModel> dataFunds = summaryDao.getDataReportFunds(companyId, begin, end, projectId);
        if (!CollectionUtils.isEmpty(dataFunds)) {
            Map<Date, List<DataFundsModel>> dataMap = new LinkedHashMap<>();
            for (DataFundsModel model : dataFunds) {
                if (!dataMap.containsKey(model.getMonth())) {
                    dataMap.put(model.getMonth(), new ArrayList<>());
                }
                dataMap.get(model.getMonth()).add(model);
            }

            for (Date date : dataMap.keySet()) {
                Map<String, Object> fundMap = new LinkedHashMap<>();
                fundMap.put("month", date);
                List<DataFundsModel> models = dataMap.get(date);
                BigDecimal total = BigDecimal.ZERO;
                for (DataFundsModel model : models) {
                    fundMap.put(model.getRdType().toString(), model.getRdFunds());
                    total = total.add(model.getRdFunds());
                }
                fundMap.put("colTotal", total);
                list.add(fundMap);
            }
        }
        return list;
    }

    @Override
    public Map<Integer, Map<String, BigDecimal>> getFundByYearAndProId(List<Integer> projectIds, Integer year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        Date beginDate = cn.hutool.core.date.DateUtil.beginOfYear(calendar.getTime());
        Date endDate = cn.hutool.core.date.DateUtil.endOfYear(beginDate);
        return summaryDao.getFundByYearAndProId(projectIds, beginDate, endDate);
    }

    /**
     * 按月或按RD加载数据费用
     *
     * @param dataMap
     * @param sheetType
     * @param projectIds
     * @param months
     * @param companyId
     * @param year
     * @param companyName
     */
    private void loadRDorMonthData(Map<Integer, List<Map<String, Object>>> dataMap, Map<Integer, Set<Integer>> sheetType, List<Integer> projectIds,
                                   List<Date> months, Integer companyId, Integer year, String companyName) {
        sheetType.keySet().forEach(sheetPage -> {
            List<Integer> rdTypes = new ArrayList<>(sheetType.get(sheetPage));
            Integer rdType = rdTypes.get(0);
            ExportSheetModel sheetModel = ExportSheetModel.SHEET_MAP.get(rdType);
            ExportTermModel term = new ExportTermModel();
            term.setProjectIds(projectIds);
            term.setMonths(months);
            term.setTypes(rdTypes);
            term.setType(sheetModel.getType());
            dataMap.put(sheetPage, getTypeData(companyId, year, rdType, new HashMap<>(), term,
                    companyName, sheetModel));
        });
    }

    private void setDefaultDetail(RdSalaryDetailModel current, RdSalaryDetailModel data) {
        if (null == data.getRdPay() || null == data.getRdInsuranceFund()) {
            return;
        }
        BigDecimal ratio = data.getRdRatio();
        if (ratio == null) {
            ratio = BigDecimal.ONE;
        }
        current.setRdInsuranceFund(null != current.getRdInsuranceFund() ? current.getRdInsuranceFund().add(data.getRdInsuranceFund()) : data.getRdInsuranceFund());
        current.setRdPay(null != current.getRdPay() ? current.getRdPay().add(data.getRdPay()) : data.getRdPay());
        current.setPay(null != current.getPay() ? current.getPay().add(data.getPay().multiply(ratio)) : data.getPay().multiply(ratio));
        current.setInsuranceFund(null != current.getInsuranceFund() ? current.getInsuranceFund().add(data.getInsuranceFund().multiply(ratio)) : data.getInsuranceFund().multiply(ratio));
    }

    /**
     * 装载人员薪资，五险一金数据
     *
     * @param dataList
     * @param companyId
     * @param colTitleMap
     */
    private void loadEmployeeData(List<EmployeeExportModel> dataList, Integer companyId,
                                  Map<String, Object> colTitleMap) {
        Map<String, FieldConfigModel> configMap = salaryService.getSalaryConfig(companyId);
        String insuranceKey = FieldConfigTypeEnum.INSURANCE.getTitle();
        String salaryKey = FieldConfigTypeEnum.SALARY.getTitle();
        List<Map<String, String>> insuranceCols = new ArrayList<>();
        List<Map<String, String>> payCols = new ArrayList<>();
        List<HashMap> salaryConfList;
        List<HashMap> insuranceConfList = JsonUtils.jsonToList(configMap.get(insuranceKey).getConfig(), HashMap.class);
        Boolean hasSalary = configMap.containsKey(salaryKey);
        String name = "name";
        String alias = "alias";
        insuranceConfList.forEach(item -> {
            Map<String, String> temp = new HashMap<>(2);
            temp.put(alias, "公司承担" + item.get(alias).toString());
            temp.put("rdAlias", "研发" + item.get(alias).toString());
            insuranceCols.add(temp);
        });
        if (hasSalary) {
            salaryConfList = JsonUtils.jsonToList(configMap.get(salaryKey).getConfig(), HashMap.class);
            salaryConfList.forEach(item -> {
                Map<String, String> temp = new HashMap<>(2);
                temp.put(alias, item.get(alias).toString());
                temp.put("rdAlias", "研发" + item.get(alias).toString());
                payCols.add(temp);
            });
        } else salaryConfList = new ArrayList<>();
        colTitleMap.put("payTitles", payCols);
        colTitleMap.put("insuranceTitles", insuranceCols);
        List<Map<String, String>> payTitleList = new ArrayList<>();
        List<Map<String, String>> insuranceTitleList = new ArrayList<>();
        dataList.forEach(item -> {
            if (hasSalary) {
                Map<String, BigDecimal> m;
                if (!StringUtils.isEmpty(item.getPayDetail())) {
                    m = ToolUtils.loadDetailMap(item.getPayDetail(), new HashMap<>(), BigDecimal.ONE, false);
                } else {
                    m = new HashMap<>();
                }
                BigDecimal payTotal = BigDecimal.ZERO;
                for (Map s : salaryConfList) {
                    BigDecimal pay = m.get(s.get(name));
                    item.getPays().add(pay);
                    BigDecimal salary = m.getOrDefault(s.get(name), BigDecimal.ZERO).multiply(item.getRdRatio()).setScale(4, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);
                    m.put((String) s.get(name), salary);
                    payTotal = payTotal.add(salary);
                }

                RdSalaryDetailModel.loadDetailMap(item.getPay(), payTotal.setScale(2, BigDecimal.ROUND_HALF_UP), item.getRdPay(), m);
                salaryConfList.forEach(s -> {
                    item.getRdPays().add(m.get(s.get(name)));
                });
            }
            Map<String, BigDecimal> m = ToolUtils.loadDetailMap(item.getInsuranceDetail(), new HashMap<>(), BigDecimal.ONE, false);
            BigDecimal insuranceTotal = BigDecimal.ZERO;
            for (Map s : insuranceConfList) {
                BigDecimal insurance = m.get(s.get(name));
                item.getInsurances().add(insurance);
                BigDecimal fund = m.getOrDefault(s.get(name), BigDecimal.ZERO).multiply(item.getRdRatio()).setScale(4, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);
                m.put((String) s.get(name), fund);
                insuranceTotal = insuranceTotal.add(fund);
            }
            RdSalaryDetailModel.loadDetailMap(item.getInsuranceFund(), insuranceTotal.setScale(2, BigDecimal.ROUND_HALF_UP), item.getRdInsuranceFund(), m);
            insuranceConfList.forEach(s -> {
                item.getRdInsurances().add(m.get(s.get(name)));
            });
        });
    }


    //按月份分sheet导出数据
    private List<Map<String, Object>> loadExportData(String name, String companyName, List<AggExportModel> dataList, List<String> sheetNames, Map<String, Object> colTitleMap) {
        List<Map<String, Object>> result = new ArrayList<>();
        if (CollectionUtils.isEmpty(dataList)) {
            sheetNames.add(companyName + name);
            HashMap map = new HashMap(2);
            map.put("title", companyName + name);
            map.put("list", new ArrayList<>());
            map.putAll(colTitleMap);
            result.add(map);
            return result;
        }
        Map<String, List<AggExportModel>> monthDataMap = new LinkedHashMap<>();
        Map<String, AggExportModel> sumMap = new HashMap<>();
        dataList.forEach(item -> {
            String month = DateUtil.format(item.getDate(), DateUtil.DEFAULT_YYMM_FORMAT);
            ToolUtils.putAndAdd(monthDataMap, month, item);
            if (!sumMap.containsKey(month)) {
                sumMap.put(month, item.createSumObj());
            }
            sumMap.get(month).sum(item);
        });
        monthDataMap.keySet().forEach(key -> {
            sheetNames.add(key);
            HashMap map = new HashMap(2);
            map.put("title", companyName + name);
            List<AggExportModel> list = monthDataMap.get(key);
            list.add(sumMap.get(key));
            map.put("list", list);
            map.putAll(colTitleMap);
            result.add(map);
        });
        CollUtil.reverse(result);
        return result;
    }

    //单sheet导出所有数据
    private List<Map<String, Object>> exportAllData(String name, String companyName, List<AggExportModel> dataList,
                                                    Map<String, Object> colTitleMap) {
        List<Map<String, Object>> result = new ArrayList<>();
        HashMap map = new HashMap(2);
        map.put("title", companyName + name);
        if (!CollectionUtils.isEmpty(dataList)) {
            AggExportModel sumAgg = dataList.get(0).createSumObj();
            dataList.forEach(item -> {
                sumAgg.sum(item);
            });
            dataList.add(sumAgg);
            map.put("list", dataList);
        } else {
            map.put("list", new ArrayList<>());
        }
        map.putAll(colTitleMap);
        result.add(map);
        return result;
    }

    //按项目rd分sheet导出数据
    private List<Map<String, Object>> exportDataByProject(String name, String companyName, List<AggExportModel> dataList, List<String> sheetNames,
                                                          Map<String, Object> colTitleMap) {
        List<Map<String, Object>> result = new ArrayList<>();
        if (CollectionUtils.isEmpty(dataList)) {
            sheetNames.add(companyName + name);
            HashMap map = new HashMap(2);
            map.put("title", companyName + name);
            map.put("list", new ArrayList<>());
            map.putAll(colTitleMap);
            result.add(map);
            return result;
        }
        Map<String, List<AggExportModel>> rdDataMap = new LinkedHashMap<>();
        Map<String, AggExportModel> sumMap = new HashMap<>();
        dataList.forEach(item -> {
            String rdTitle = item.getRdTitle();
            ToolUtils.putAndAdd(rdDataMap, rdTitle, item);
            if (!sumMap.containsKey(rdTitle)) {
                sumMap.put(rdTitle, item.createSumObj());
            }
            sumMap.get(rdTitle).sum(item);
        });
        rdDataMap.keySet().forEach(rdTitle -> {
            sheetNames.add(rdTitle);
            HashMap map = new HashMap(2);
            map.put("title", companyName + name);
            List<AggExportModel> list = rdDataMap.get(rdTitle);
            list.add(sumMap.get(rdTitle));
            map.put("list", list);
            map.putAll(colTitleMap);
            result.add(map);
        });
        CollUtil.reverse(result);
        return result;
    }


    private List<DataFundsModel> getYearFundsData(Integer year, Integer companyId) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        Date beginDate = cn.hutool.core.date.DateUtil.beginOfYear(calendar.getTime());
        Date endDate = cn.hutool.core.date.DateUtil.endOfYear(beginDate);
        return summaryDao.getDataReportFunds(companyId, beginDate, endDate, null);

    }

    private List<DataFundsModel> getSalaryFundsData(Integer year, Integer companyId) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        Date beginDate = cn.hutool.core.date.DateUtil.beginOfYear(calendar.getTime());
        Date endDate = cn.hutool.core.date.DateUtil.endOfYear(beginDate);
        List<Integer> rdTypes = Arrays.asList(CostEnum.SALARY.getType(), CostEnum.INSURANCE.getType());
        List<DataFundsModel> fundsData = summaryDao.getDataByRdType(companyId, beginDate, endDate, rdTypes);
        return fundsData;

    }

    private List<Map<String, Object>> getDetailData(List<DataFundsModel> fundsData, Integer year, UserInfo info, Integer exportType, List<String> sheetNames) {
        List<ProjectDetailModel> detailModels = projectDao.getProjectIdsByYear(info.getCompanyId(), year, false);
        if (CollectionUtils.isEmpty(detailModels) || CollectionUtils.isEmpty(fundsData)) {
            return new ArrayList<>();
        }
        Map<Integer, String> projectMap = new LinkedHashMap<>();
        detailModels.forEach(item -> {
            projectMap.put(item.getId(), item.getRdTitle());
        });
        Map<String, List<DataFundsModel>> dataMap = getDataFundsMap(fundsData, false, true);
        String companyName = info.getCompanyName();
        Map<Integer, CostEnum> parentMap = CostEnum.getParentMap(false);
        List<Map<String, Object>> resultList = new ArrayList<>();
        if (exportType == 1) {
            projectMap.keySet().forEach(projectId -> {
                List<Map<String, Object>> listMap = new ArrayList<>();
                parentMap.keySet().forEach(parentType -> {
                    String key = MessageFormat.format(KEYFORMAT, projectId, parentType);
                    if (!dataMap.containsKey(key)) {
                        return;
                    }
                    listMap.add(getRow(parentMap.get(parentType).getTitle(), dataMap.get(key)));
                });
                if (CollectionUtils.isEmpty(listMap)) {
                    return;
                }
                loadSheetData(listMap, sheetNames, projectMap.get(projectId), companyName, resultList, year, "费用类型");
            });
        } else {
            parentMap.keySet().forEach(parentType -> {
                List<Map<String, Object>> listMap = new ArrayList<>();
                projectMap.keySet().forEach(projectId -> {
                    String key = MessageFormat.format(KEYFORMAT, projectId, parentType);
                    if (!dataMap.containsKey(key)) {
                        return;
                    }
                    listMap.add(getRow(projectMap.get(projectId), dataMap.get(key)));
                });
                if (CollectionUtils.isEmpty(listMap)) {
                    return;
                }
                loadSheetData(listMap, sheetNames, parentMap.get(parentType).getTitle(), companyName, resultList, year, "项目");
            });
        }
        if (null != sheetNames) {
            CollUtil.reverse(resultList);
        }
        return resultList;
    }

    private Map<String, Object> getRow(String typeName, List<DataFundsModel> dataList) {
        BigDecimal total = BigDecimal.ZERO;
        Map<String, Object> row = new HashMap<>();
        row.put("typeName", typeName);
        for (DataFundsModel data : dataList) {
            String month = "month" + (cn.hutool.core.date.DateUtil.month(data.getMonth()) + 1);
            row.put(month, ((BigDecimal) row.getOrDefault(month, BigDecimal.ZERO)).add(data.getRdFunds()));
            total = total.add(data.getRdFunds());
        }
        row.put("total", total);
        return row;
    }

    private void loadSheetData(List<Map<String, Object>> listMap, List<String> sheetNames, String typeName,
                               String companyName, List<Map<String, Object>> resultList, Integer year, String exportType) {
        if (CollectionUtils.isEmpty(listMap)) {
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("list", listMap);
        if (null != sheetNames) {
            sheetNames.add(typeName);
            map.put("title", companyName + typeName + "归集费用");
        } else {
            map.put("title", typeName);
        }
        map.put("year", year);
        map.put("exportType", exportType);
        resultList.add(map);
    }

    private Map<String, List<DataFundsModel>> getDataFundsMap(List<DataFundsModel> fundsData, Boolean child, Boolean parent) {
        Map<String, List<DataFundsModel>> dataFundsMap = new HashMap<>();
        fundsData.forEach(item -> {
            Integer rdType = item.getRdType() / 100;
            if (rdType == 204 || (rdType >= 501 && rdType <= 503)) {
                return;
            } else if (rdType >= 600 && rdType <= 603) {
                rdType = 699;
            }
            if (parent) {
                ToolUtils.putAndAdd(dataFundsMap, MessageFormat.format(KEYFORMAT, item.getParentId() > 0 ? item.getParentId() : item.getProjectId(), rdType), item);
            }
            // 当取子项目但当前项目不是一个子项目且不取父项目时，直接取当前项目
            if ((child && item.getParentId() > 0) || !parent) {
                ToolUtils.putAndAdd(dataFundsMap, MessageFormat.format(KEYFORMAT, item.getProjectId(), rdType), item);
            }
        });
        return dataFundsMap;
    }

    private void loadSubsidiaryLedger(Map<Integer, ProjectDetailModel> projectMap, List<Map<String, Object>> rows, String title, Integer parentType,
                                      Map<String, List<DataFundsModel>> dataMap, Map<String, Object> rowsTotal) {
        Map<String, Object> titleRow = new HashMap<>();
        titleRow.put("name", title + "（单位：元）");
        rows.add(titleRow);
        Map<String, Object> totalRow = new HashMap<>();
        totalRow.put("workshop", "合计");
        projectMap.keySet().forEach(projectId -> {
            Map<String, Object> row = new HashMap<>();
            ProjectDetailModel detail = projectMap.get(projectId);
            row.put("name", detail.getRdTitle() + "--" + detail.getPname());
            row.put("workshop", detail.getWorkshop());
            String key = MessageFormat.format(KEYFORMAT, projectId, parentType);
            if (dataMap.containsKey(key)) {
                BigDecimal total = BigDecimal.ZERO;
                for (DataFundsModel data : dataMap.get(key)) {
                    String month = "m" + (cn.hutool.core.date.DateUtil.month(data.getMonth()) + 1);
                    row.put(month, ((BigDecimal) row.getOrDefault(month, BigDecimal.ZERO)).add(data.getRdFunds()));
                    totalRow.put(month, ((BigDecimal) totalRow.getOrDefault(month, BigDecimal.ZERO)).add(data.getRdFunds()));
                    rowsTotal.put(month, ((BigDecimal) rowsTotal.getOrDefault(month, BigDecimal.ZERO)).add(data.getRdFunds()));
                    total = total.add(data.getRdFunds());
                }
                row.put("total", total);
                totalRow.put("total", ((BigDecimal) totalRow.getOrDefault("total", BigDecimal.ZERO)).add(total));
                rowsTotal.put("total", ((BigDecimal) rowsTotal.getOrDefault("total", BigDecimal.ZERO)).add(total));
            }
            rows.add(row);
        });
        rows.add(totalRow);
        rows.add(new HashMap<>());
    }

    private void loadSummaryReport(Map<Integer, ProjectDetailModel> projectMap, Map<Integer, CostEnum> parentCostMap,
                                   Map<String, List<DataFundsModel>> monthDataMap, String companyName, Integer year,
                                   List<Map<String, Object>> sheetList, Boolean noOutsourcing,
                                   Map<String, List<OutsourcingSummaryModel>> monthOutsourcingMap) {
        List<Map<String, Object>> rows = new ArrayList<>();
        Map<String, Object> totalMap = new HashMap<>();
        projectMap.keySet().forEach(projectId -> {
            Map<String, Object> rowsTotal = new HashMap<>();
            boolean fundContainKey, outsourcingContainKey;
            for (int i = 1; i <= 12; i++) {
                String key = MessageFormat.format(KEYFORMAT, projectId, i);
                fundContainKey = monthDataMap.containsKey(key);
                outsourcingContainKey = monthOutsourcingMap.containsKey(key);
                if (!outsourcingContainKey && !fundContainKey) {
                    continue;
                }
                Map<String, Object> row = new HashMap<>();
                row.put("rdTitle", projectMap.get(projectId).getRdTitle());
                row.put("pname", projectMap.get(projectId).getPname());
                row.put("m", i + "月份");
                BigDecimal total = BigDecimal.ZERO;
                if (fundContainKey) {
                    for (DataFundsModel item : monthDataMap.get(key)) {
                        Integer parentType = item.getRdType() / 100;
                        if (parentType >= 600 && parentType <= 603) {
                            parentType = 699;
                        }
                        String field = parentCostMap.get(parentType).getField();
                        row.put(field, ((BigDecimal) row.getOrDefault(field, BigDecimal.ZERO)).add(item.getRdFunds()));
                        rowsTotal.put(field, ((BigDecimal) rowsTotal.getOrDefault(field, BigDecimal.ZERO)).add(item.getRdFunds()));
                        totalMap.put(field, ((BigDecimal) totalMap.getOrDefault(field, BigDecimal.ZERO)).add(item.getRdFunds()));
                        total = total.add(item.getRdFunds());
                    }
                }
                if (outsourcingContainKey) {
                    for (OutsourcingSummaryModel item : monthOutsourcingMap.get(key)) {
                        String curKey = item.getType() == 0 ? "inside" : "outside";
                        row.put(curKey, ((BigDecimal) row.getOrDefault(curKey, BigDecimal.ZERO)).add(item.getRdFunds()));
                        rowsTotal.put(curKey, ((BigDecimal) rowsTotal.getOrDefault(curKey, BigDecimal.ZERO)).add(item.getRdFunds()));
                        totalMap.put(curKey, ((BigDecimal) totalMap.getOrDefault(curKey, BigDecimal.ZERO)).add(item.getRdFunds()));
                        total = total.add(item.getRdFunds());
                    }
                }
                row.put("total", total);
                rowsTotal.put("total", ((BigDecimal) rowsTotal.getOrDefault("total", BigDecimal.ZERO)).add(total));
                totalMap.put("total", ((BigDecimal) totalMap.getOrDefault("total", BigDecimal.ZERO)).add(total));
                rows.add(row);
            }
            if (!CollectionUtils.isEmpty(rowsTotal)) {
                rowsTotal.put("m", "合计");
                rows.add(rowsTotal);
                rows.add(new HashMap<>());
            }
        });
        Map<String, Object> map = new HashMap<>();
        map.put("title", companyName + year);
        map.put("list", rows);
        map.put("fund", totalMap);
        map.put("noOutsourcing", noOutsourcing);
        sheetList.add(map);
    }

    /**
     * 获取类型数据费用
     *
     * @param companyId
     * @param year
     * @param rdType
     * @param colTitleMap
     * @param exportTerm
     * @param companyName
     * @param exportSheetModel
     * @return
     */
    private List<Map<String, Object>> getTypeData(Integer companyId, Integer year, Integer rdType, Map<String, Object> colTitleMap,
                                                  ExportTermModel exportTerm, String companyName,
                                                  ExportSheetModel exportSheetModel) {
        List<? extends AggExportModel> exportData = null;
        switch (CostEnum.getCostEnum(rdType)) {
            case SALARY:
            case INSURANCE:
                List<EmployeeExportModel> dataList = projectRdEmployeeDao.exportFromEmployee(exportTerm, companyId,
                        year);
                loadEmployeeData(dataList, companyId, colTitleMap);
                exportData = dataList;
                break;
            case PROD:
            case LAB:
            case ASSETS_AMORTIZATION:
                exportData = projectRdEquipmentDao.exportFromEquipment(exportTerm, companyId, year);
                break;
            case MATERIAL:
            case TRIAL_MATERIAL:
            case REPAIR_MATERIAL:
                exportData = projectMaterialDao.exportFromMaterial(exportTerm);
                break;
            case PAPER_MATERIAL:
            case PAPER_TRIAL:
                exportData = projectMaterialDao.exportFromMaterial(exportTerm);
                break;
            case IRON_MATERIAL:
            case IRON_TRIAL:
                exportData = projectMaterialDao.exportFromMaterial(exportTerm);
                break;
            case STIMULUS_PROD:
                exportData = projectRdEquipmentDao.getEquipmentPowerData(exportTerm, companyId, year);
                break;
            case TRIAL_STIMULUS:
            case STIMULUS:
            case FUEL:
                exportData = projectEnergyDao.exportFromStimulus(exportTerm, rdType);
                break;
            case IRON_FUEL:
            case IRON_STIMULUS:
                exportData = projectEnergyDao.exportFromStimulus(exportTerm, rdType);
                break;
            case DESIGN:
                exportData = projectDesignDao.exportFromDesign(exportTerm);
                break;
            case INSPECTION:
            case REPAIR:
            case TRAVEL:
            case SOFT_AMORTIZATION:
            case PATENT_AMORTIZATION:
            case OTHER_AMORTIZATION:
            case TRIAL_PROD:
            case OTHER:
            case BENEFITS:
            case COPYRIGHT:
            case RD_PRODUCTION:
            case BOOK:
            case SAMPLE_MACHINE:
                exportData = projectInspectionDao.ExportFromOther(exportTerm);
                break;
            default:
                break;
        }
        return exportAllData(exportSheetModel.getSheetName(), companyName, (List<AggExportModel>) exportData, colTitleMap);
    }


}



