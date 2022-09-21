package com.yskc.rs.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.PageResult;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.JsonUtils;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.dao.*;
import com.yskc.rs.dao.init.InitMemberDao;
import com.yskc.rs.dao.project.*;
import com.yskc.rs.entity.EmployeeEntity;
import com.yskc.rs.entity.StageEntity;
import com.yskc.rs.entity.SummaryEntity;
import com.yskc.rs.entity.init.InitMemberEntity;
import com.yskc.rs.entity.project.*;
import com.yskc.rs.enums.AuditRdTypeEnum;
import com.yskc.rs.enums.CostEnum;
import com.yskc.rs.enums.EmployeeTypeEnum;
import com.yskc.rs.enums.FieldConfigTypeEnum;
import com.yskc.rs.models.CheckStatusModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.accountingrdsalary.AccountPeriodModel;
import com.yskc.rs.models.accountingrdsalary.AccountingRdSalaryModel;
import com.yskc.rs.models.aggregation.AggDeeConfigDetailModel;
import com.yskc.rs.models.aggregation.AggFeeModel;
import com.yskc.rs.models.employee.RdEmployeeSummaryModel;
import com.yskc.rs.models.employeePlan.RdEmployeePlanModel;
import com.yskc.rs.models.fieldconfig.FieldConfigModel;
import com.yskc.rs.models.init.member.InitMemberModel;
import com.yskc.rs.models.init.member.ProjectEmployeeModel;
import com.yskc.rs.models.project.RdUsedHourModel;
import com.yskc.rs.models.projectattendance.MiniRdHourModel;
import com.yskc.rs.models.projectrdemployee.*;
import com.yskc.rs.models.projectyieldconfig.AggMsgModel;
import com.yskc.rs.models.projectyieldconfig.ProjectYieldConfigInfoModel;
import com.yskc.rs.models.projectyieldconfig.ProjectYieldConfigTotalModel;
import com.yskc.rs.models.projectyieldconfig.TrialAggModel;
import com.yskc.rs.models.rdequipment.FullYearProjectModel;
import com.yskc.rs.models.salary.SalaryInfoModel;
import com.yskc.rs.service.CompanySettingService;
import com.yskc.rs.service.ProjectAttendanceService;
import com.yskc.rs.service.ProjectRdEmployeeService;
import com.yskc.rs.service.SalaryService;
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
 * @CreateTime: 2020-03-31 11:03
 * @Description: 项目人员费用业务实现层
 */
@Service
public class ProjectRdEmployeeServiceImpl implements ProjectRdEmployeeService {

    @Autowired
    private ProjectRdEmployeeDao projectRdEmployeeDao;
    @Autowired
    private SummaryDao summaryDao;
    @Autowired
    private CommonService commonService;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private InitMemberDao initMemberDao;
    @Autowired
    private SalaryService salaryService;
    @Autowired
    private ProjectAttendanceDao projectAttendanceDao;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private CompanySettingService companySettingService;
    @Autowired
    private ProjectAttendanceService projectAttendanceService;
    @Autowired
    private RdEmployeeDao rdEmployeeDao;
    @Autowired
    private ProjectRdAggConfigDao projectRdAggConfigDao;
    @Autowired
    private StageDao stageDao;
    @Autowired
    private RdEmployeePlanDao rdEmployeePlanDao;
    @Autowired
    private ProjectAttendanceUsedDao projectAttendanceUsedDao;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public PageResult getList(UserInfo userInfo, QueryProjectRdEmployeeModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            page.setAscs(CollUtil.newArrayList("enumber"));
        }
        List<ProjectRdEmployeeModel> list = projectRdEmployeeDao.getList(page, userInfo.getCompanyId(), query);
        if (!CollectionUtils.isEmpty(list)) {
            List<String> enumbers = list.stream().map(ProjectRdEmployeeModel::getEnumber).collect(Collectors.toList());
            List<RdUsedHourModel> usedModels = projectRdEmployeeDao.getAllUsed(userInfo.getCompanyId(), query, enumbers);
            Map<String, List<RdUsedHourModel>> keyMap = new HashMap<>();
            Map<String, BigDecimal> hourMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(usedModels)) {
                usedModels.forEach(item -> {
                    if (!keyMap.containsKey(item.getKey())) {
                        keyMap.put(item.getKey(), new ArrayList<>());
                    }
                    keyMap.get(item.getKey()).add(item);
                    hourMap.put(item.getKey(), hourMap.getOrDefault(item.getKey(), BigDecimal.ZERO).add(
                            item.getRdHour() != null ? item.getRdHour() : BigDecimal.ZERO));
                });
            }
            list.forEach(item -> {
                item.setUsedList(keyMap.getOrDefault(item.getEnumber(), null));
                if (item.getWorkHours() != null) {
                    BigDecimal remainHour = item.getWorkHours().subtract(hourMap.getOrDefault(item.getEnumber(), BigDecimal.ZERO));
                    if (item.getRdHour() != null) {
                        remainHour = remainHour.add(item.getRdHour());
                    }
                    item.setRemainHour(remainHour);
                }
            });
        }
        return PageUtils.buildPageResult(page, list, companySettingService.getEmployeeHourBit(userInfo.getCompanyId()), projectRdEmployeeDao.getTotal(userInfo.getCompanyId(), query));
    }

    @Override
    public Boolean saveList(UserInfo userInfo, BatchProjectRdEmployeeModel batchModel) throws OwnerException {
        Date month = DateUtil.getMonthFirstDay(batchModel.getMonth());
        Integer projectId = batchModel.getProjectId();
        List<CheckStatusModel> models = new ArrayList<>();
        CheckStatusModel checkStatusModel = new CheckStatusModel();
        checkStatusModel.setMonth(month);
        checkStatusModel.setProjectId(projectId);
        models.add(checkStatusModel);
        List<Integer> rdTypes = new ArrayList<>();
        rdTypes.add(AuditRdTypeEnum.EMPLOYEE.getRdType());
        commonService.checkStatus(models, rdTypes, userInfo);
        batchModel.getList().forEach(item -> {
            item.setMonth(month);
            item.setProjectId(projectId);
        });
        return saveData(CollUtil.newArrayList(month), CollUtil.newArrayList(projectId), userInfo, batchModel.getList(), companySettingService.getEquipmentHourBit(userInfo.getCompanyId()));
    }

    @Override
    public PageModel<List<RdEmployeeSummaryModel>> queryListByMonth(UserInfo userInfo, QueryProjectRdEmployeeModel model) {
        Pagination page = model.getPagination();
        List<RdEmployeeSummaryModel> rdEmployeeSummaryModels = new ArrayList<>();
        if (model.getMonth() != null) {
            Date month = DateUtil.getMonthFirstDay(model.getMonth());
            Integer year = cn.hutool.core.date.DateUtil.year(month);
            ProjectEntity projectEntity = projectDao.selectById(model.getProjectId());
            List<Integer> projectIds = new ArrayList<>();
            //父项目查所有
            if (projectEntity.getParentId() == 0 && projectEntity.getHasChild()) {
                List<ProjectEntity> entityList = projectDao.getChildsById(model.getProjectId());
                if (CollectionUtils.isEmpty(entityList)) {
                    return PageUtils.build(page, new ArrayList<>());
                }
                List<Integer> projects = entityList.stream().map(ProjectEntity::getId).collect(Collectors.toList());
                projectIds.addAll(projects);
            } else {
                projectIds.add(model.getProjectId());
            }
            rdEmployeeSummaryModels = projectRdEmployeeDao.queryEmployeeList(page, userInfo.getCompanyId(), projectIds, month, year);
            int num = 0;
            for (RdEmployeeSummaryModel re : rdEmployeeSummaryModels) {
                num += 1;
                re.setNum(num);
            }
        }
        return PageUtils.buildPageResult(page, rdEmployeeSummaryModels);

    }

    @Override
    public List<RdEmployeeSummaryModel> queryListByYear(UserInfo userInfo, QueryProjectRdEmployeeModel model) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.clear();
//        calendar.set(Calendar.YEAR, model.getYear());
//        Date currYearFirst = calendar.getTime();
//        Date currYearLast = cn.hutool.core.date.DateUtil.endOfYear(currYearFirst);
        ProjectEntity projectEntity = projectDao.selectById(model.getProjectId());
        StageEntity stage = stageDao.getStageInfo(model.getProjectId(), model.getpDocFileId());
        Integer year = cn.hutool.core.date.DateUtil.year(stage != null ? stage.getBeginDate() : projectEntity.getBeginDate());
        List<Integer> projectIds = new ArrayList<>();
        if (projectEntity.getHasChild() && projectEntity.getParentId() == 0) {
            List<ProjectEntity> projectEntities = projectDao.getChildsById(model.getProjectId());
            if (!CollectionUtils.isEmpty(projectEntities)) {
                List<Integer> projects = projectEntities.stream().map(ProjectEntity::getId).collect(Collectors.toList());
                projectIds.addAll(projects);
            } else {
                return new ArrayList<>();
            }
        } else {
            projectIds.add(model.getProjectId());
        }
        return projectRdEmployeeDao.queryListByYear(userInfo.getCompanyId(), projectIds, year);
    }

    @Override
    public Boolean importRdHour(UserInfo info, BatchProjectRdEmployeeModel batchModel, int year) throws OwnerException {
        List<ProjectRdEmployeeModel> dataList = batchModel.getList();
        if (CollectionUtils.isEmpty(dataList)) {
            throw new OwnerException("未导入任何数据。");
        }
        Integer projectId = batchModel.getProjectId();
        // 月份 ： enumber ： 研发工时对象（）过滤重复enumber
        Map<String, ProjectRdEmployeeModel> monthDataMap = new LinkedHashMap<>();
        Map<Date, MonthThingSetModel> monthKeysMap = new HashMap<>();
        String keyFormat = "{0}{1}";
        // 检测项目月份，并加载月份数据
        checkAndLoadMonthData(monthDataMap, monthKeysMap, projectId, dataList, year, keyFormat);
        Set<String> enumbers = new HashSet<>();
        for (Date m : monthKeysMap.keySet()) {
            enumbers.addAll(monthKeysMap.get(m).getKeys());
        }
        List<Integer> rdTypes = new ArrayList<>();
        rdTypes.add(AuditRdTypeEnum.EMPLOYEE.getRdType());
        commonService.checkStatus(projectId, monthKeysMap.keySet(), rdTypes, info);
        List<InitMemberEntity> existInit = initMemberDao.getByEnumbers(info.getCompanyId(), projectId, new ArrayList<>(enumbers), year);
        Set<String> existEnumber = new HashSet<>();
        if (!CollectionUtils.isEmpty(existInit)) {
            existInit.forEach(item -> existEnumber.add(item.getEnumber()));
        }
        Collection<String> initDisjunction = CollUtil.disjunction(enumbers, existEnumber);
        if (!CollectionUtils.isEmpty(initDisjunction)) {
            throw new OwnerException(MessageFormat.format("项目成员不存在工号为：【{0}】 的人员。", String.join(",", initDisjunction)));
        }
        List<Integer> projectIds = CollUtil.newArrayList(projectId);
        List<MonthThingSetModel> monthKeysList = new ArrayList<>(monthKeysMap.values());
        List<ProjectRdEmployeeModel> dataEmployees = projectRdEmployeeDao.getUsedSum(info.getCompanyId(), projectIds, monthKeysList);
        Map<String, ProjectRdEmployeeModel> dataEmployeeMap = dataEmployees.stream().filter(a -> a != null).collect(
                Collectors.toMap(
                        a -> MessageFormat.format(keyFormat, DateUtil.format(a.getMonth(), DateUtil.DEFAULT_YYMM_FORMAT), a.getEnumber()),
                        b -> b, (e, n) -> n));
        List<String> dataDisjunction = new ArrayList<>(CollUtil.disjunction(monthDataMap.keySet(), dataEmployeeMap.keySet()));
        if (!CollectionUtils.isEmpty(dataDisjunction)) {
            String s = dataDisjunction.get(dataDisjunction.size() - 1);
            throw new OwnerException(MessageFormat.format("不存在【{0}】月，工号为【{1}】的薪资。",
                    s.substring(0, 7), s.substring(7)));
        }
        int hourBit = companySettingService.getEmployeeHourBit(info.getCompanyId());
        Map<String, FieldConfigModel> salaryConfigMap = salaryService.getSalaryConfig(info.getCompanyId());
        loadRdAmount(monthDataMap, projectId, monthKeysList, keyFormat, dataEmployeeMap, salaryConfigMap, "分配", false, hourBit);
        return saveData(new ArrayList<>(monthKeysMap.keySet()), projectIds, info, new ArrayList<>(monthDataMap.values()), hourBit);
    }

    @Override
    public void insertSummary(Date now, List<Integer> projectIds, List<Date> months, UserInfo userInfo, Boolean filterZero) {
        List<ProjectRdEmployeeTotalModel> totals = projectRdEmployeeDao.getSummary(projectIds, months);
        List<SummaryEntity> summaryList = new ArrayList<>();
        Map<String, ProjectRdEmployeeTotalModel> monthMap = new HashMap<>();
        String keyFormat = "{0}_{1}";
        if (!CollectionUtils.isEmpty(totals)) {
            totals.forEach(item -> monthMap.put(MessageFormat.format(keyFormat, item.getProjectId(), item.getMonth().getTime()), item));
        }
        months.forEach(m ->
                projectIds.forEach(projectId -> {
                    ProjectRdEmployeeTotalModel current = monthMap.get(MessageFormat.format(keyFormat, projectId, m.getTime()));
                    if (filterZero && current == null) {
                        return;
                    }
                    BigDecimal insurance = BigDecimal.ZERO, salary = BigDecimal.ZERO;
                    if (null != current) {
                        insurance = current.getInsuranceFund();
                        salary = current.getPay();
                    }
                    summaryList.add(ToolUtils.build(now, m, projectId, CostEnum.INSURANCE.getType(), insurance, userInfo));
                    summaryList.add(ToolUtils.build(now, m, projectId, CostEnum.SALARY.getType(), salary, userInfo));
                }));
        summaryDao.insertOrUpdate(summaryList);
        // 已在删除项目时清理
        // summaryDao.delRdFundsForZero(projectId,months);
    }

    @Override
    public PageResult getRdAccountingList(UserInfo info, QueryProjectRdEmployeeModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            page.setAscs(CollUtil.newArrayList("enumber"));
        }
        Integer companyId = info.getCompanyId();
        List<SalaryAccountingModel> list = projectRdEmployeeDao.getRdAccountingList(page, companyId, query);
        if (!CollectionUtils.isEmpty(list)) {
            List<String> enumbers = list.stream().map(SalaryAccountingModel::getEnumber).collect(Collectors.toList());
            AccountPeriodModel accountPeriod = companySettingService.getAccountPeriod(query.getMonth(), companyId);
            List<MiniRdHourModel> attendanceHours = projectAttendanceDao.getMonthRdHour(
                    companyId, accountPeriod.getBegin(), accountPeriod.getEnd(), enumbers, query.getProjectId());
            if (!CollectionUtils.isEmpty(attendanceHours)) {
                Map<String, BigDecimal> attendanceHourMap = attendanceHours.stream().collect(
                        Collectors.toMap(MiniRdHourModel::getEnumber, MiniRdHourModel::getWorkHour, (o, n) -> n));
                list.forEach(item -> {
                    item.setAttendanceHour(attendanceHourMap.get(item.getEnumber()));
                });
            }
        }
        return PageUtils.buildPageResult(page, list, null, projectRdEmployeeDao.getTotal(companyId, query));
    }

    @Override
    public Boolean accountingRdSalary(UserInfo userInfo, AccountingRdSalaryModel accountingRdSalary) throws OwnerException {
        Integer projectId = accountingRdSalary.getProjectId();
        Integer companyId = userInfo.getCompanyId();
        Date month = DateUtil.getMonthFirstDay(accountingRdSalary.getMonth());
        CheckStatusModel statusModel = new CheckStatusModel(projectId, month);
        commonService.checkStatus(Arrays.asList(statusModel), Arrays.asList(AuditRdTypeEnum.EMPLOYEE.getRdType()), userInfo);
        AccountPeriodModel accountPeriod = companySettingService.getAccountPeriod(month, companyId);
        List<MiniRdHourModel> attendanceHours = projectAttendanceDao.getMonthRdHour(
                companyId, accountPeriod.getBegin(), accountPeriod.getEnd(), accountingRdSalary.getEnumbers(), projectId);
        if (CollectionUtils.isEmpty(attendanceHours)) {
            throw new OwnerException("不存在可核算人员。");
        }
        Map<String, BigDecimal> attendanceHourMap = attendanceHours.stream().collect(
                Collectors.toMap(MiniRdHourModel::getEnumber, MiniRdHourModel::getWorkHour, (o, n) -> n));
        Set<String> enumbers = attendanceHourMap.keySet();
        List<MonthThingSetModel> mKeysList = new ArrayList<>();
        MonthThingSetModel monthKeySet = new MonthThingSetModel(month);
        monthKeySet.getKeys().addAll(enumbers);
        mKeysList.add(monthKeySet);
        Map<String, String> employeeMap = getEnumberName(companyId, enumbers);
        List<Integer> projectIds = CollUtil.newArrayList(projectId);
        List<ProjectRdEmployeeModel> dataEmployees = projectRdEmployeeDao.getUsedSum(companyId, projectIds, mKeysList);
        if (CollectionUtils.isEmpty(dataEmployees)) {
            throw new OwnerException("核算人员不存在工资，请先添加工资。");
        }
        String keyFormat = "{0}{1}";
        Map<String, ProjectRdEmployeeModel> dataEmployeeMap = new HashMap<>();
        Map<String, ProjectRdEmployeeModel> dataMap = new HashMap<>();
        for (ProjectRdEmployeeModel item : dataEmployees) {
            if (null == item) {
                continue;
            }
            String enumber = item.getEnumber();
            if (attendanceHourMap.containsKey(enumber)) {
                String key = MessageFormat.format(keyFormat, DateUtil.format(item.getMonth(), DateUtil.DEFAULT_YYMM_FORMAT), enumber);
                dataEmployeeMap.put(key, item);
                dataMap.put(key, ProjectRdEmployeeModel.build(employeeMap.get(enumber), enumber, month, attendanceHourMap.get(enumber)));
            }
        }

        Map<String, FieldConfigModel> salaryConfigMap = salaryService.getSalaryConfig(userInfo.getCompanyId());
        int hourBit = companySettingService.getEmployeeHourBit(companyId);
        loadRdAmount(dataMap, projectId, mKeysList, keyFormat, dataEmployeeMap, salaryConfigMap, "核算", false, hourBit);
        return saveData(CollUtil.newArrayList(month), projectIds, userInfo, new ArrayList<>(dataMap.values()), hourBit);
    }

    @Override
    public Boolean accountingAllRdSalary(UserInfo userInfo, AccountingRdSalaryModel accountingRdSalary) throws OwnerException {
        Date month = accountingRdSalary.getMonth();
        AccountPeriodModel accountPeriod = companySettingService.getAccountPeriod(month, userInfo.getCompanyId());
        List<String> enumbrs = projectAttendanceDao.getHasSalaryEnumbers(accountingRdSalary.getProjectId(), month,
                accountPeriod.getBegin(), accountPeriod.getEnd(), userInfo.getCompanyId());
        if (CollectionUtils.isEmpty(enumbrs)) {
            throw new OwnerException("不存在可核算人员。");
        }
        accountingRdSalary.setEnumbers(enumbrs);
        return accountingRdSalary(userInfo, accountingRdSalary);
    }

    @Override
    public PageModel<List<RdEmployeeAggModel>> getRdEmployeeRdsList(Integer companyId, QueryProjectRdEmployeeModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> ascs = new ArrayList<>();
            ascs.add("re.enumber");
            page.setAscs(ascs);
        }
        List<RdEmployeeAggModel> result = rdEmployeeDao.getRdEmployeeRdsList(page, companyId, query);
        if (!CollectionUtils.isEmpty(result)) {
            List<String> enumbers = result.stream().map(RdEmployeeAggModel::getEnumber).collect(Collectors.toList());
            List<FullYearProjectModel> enumberRdInfos = rdEmployeeDao.getRdTitles(enumbers, companyId, query.getYear(), false);
            if (!CollectionUtils.isEmpty(enumberRdInfos)) {
                Map<String, String> enumberRdMap = new HashMap<>();
                enumberRdInfos.forEach(item -> enumberRdMap.put(item.getKey(), item.getRdTitle()));
                result.forEach(item -> {
                    if (enumberRdMap.containsKey(item.getEnumber())) {
                        item.setRds(enumberRdMap.get(item.getEnumber()));
                    }
                });
            }
        }
        return PageUtils.build(page, result);
    }

    @Override
    public String getRdAggConfig(Integer type, Date month, Integer companyId) {
        return projectRdAggConfigDao.getConfig(type, DateUtil.getMonthFirstDay(month), companyId);
    }

    @Override
    public Boolean aggFee(AggFeeModel aggFee, UserInfo userInfo) throws OwnerException {
        Date month = DateUtil.getMonthFirstDay(aggFee.getMonth());
        // 先保存配置
        projectRdAggConfigDao.insertOrUpdate(CollUtil.newArrayList(
                ProjectRdAggConfig.build(userInfo, new Date(), aggFee.getConfig(), aggFee.getType(), month)
        ));
        Integer companyId = userInfo.getCompanyId();
        int year = cn.hutool.core.date.DateUtil.year(month);
        // 有序，按每个人的rdTitle 正序
        List<InitMemberModel> list = initMemberDao.getAggMembers(aggFee.getKeys(), DateUtil.getMonthLastDay(month), month, year, companyId);
        if (CollUtil.isEmpty(list)) {
            throw new OwnerException("不存在可归集的人员");
        }
        Map<String, Set<Integer>> enumberProjectMap = new LinkedHashMap<>();
        Map<Integer, Set<String>> etypeEnumberMap = new HashMap<>();
        Set<Integer> projectIdSet = new HashSet<>();
        list.forEach(item -> {
            ToolUtils.putAndAddSet(etypeEnumberMap, item.getEtype(), item.getEnumber());
            ToolUtils.putAndAddLinkedSet(enumberProjectMap, item.getEnumber(), item.getProjectId());
            projectIdSet.add(item.getProjectId());
        });
        List<Integer> rdTypes = new ArrayList<>();
        rdTypes.add(AuditRdTypeEnum.EMPLOYEE.getRdType());
        commonService.checkStatus(projectIdSet, month, rdTypes, userInfo);
        List<Integer> projectIds = new ArrayList<>(projectIdSet);
        List<MonthThingSetModel> monthKeysList = CollUtil.newArrayList(new MonthThingSetModel(month, enumberProjectMap.keySet()));
        List<ProjectRdEmployeeModel> usedList = projectRdEmployeeDao.getUsedSum(companyId, projectIds, monthKeysList);
        if (CollUtil.isEmpty(usedList)) {
            throw new OwnerException("归集人员不存在工资，请先添加工资。");
        }
        String keyFormat = "{0}{1}";
        Map<String, ProjectRdEmployeeModel> dataEmployeeMap = usedList.stream().filter(Objects::nonNull).collect(
                Collectors.toMap(a -> MessageFormat.format(keyFormat, DateUtil.format(a.getMonth(), DateUtil.DEFAULT_YYMM_FORMAT), a.getEnumber()),
                        b -> b, (e, n) -> n));
        List<ProjectRdEmployeeModel> data = new ArrayList<>();
        Map<Integer, Map<String, ProjectRdEmployeeModel>> projectAggMap = getProjectAgg(aggFee.getConfig(), companyId,
                etypeEnumberMap, enumberProjectMap, dataEmployeeMap, month, keyFormat);
        Map<String, FieldConfigModel> salaryConfigMap = salaryService.getSalaryConfig(companyId);
        int hourBit = companySettingService.getEmployeeHourBit(companyId);
        for (Integer projectId : projectAggMap.keySet()) {
            Map<String, ProjectRdEmployeeModel> monthDataMap = projectAggMap.get(projectId);
            loadRdAmount(monthDataMap, projectId,
                    monthKeysList, keyFormat, dataEmployeeMap, salaryConfigMap, "配置", true, hourBit);
            data.addAll(monthDataMap.values());
        }
        return saveData(CollUtil.newArrayList(month), projectIds, userInfo, data, hourBit);
    }

    @Override
    public Boolean refreshFee(AggFeeModel aggFee, UserInfo userInfo) throws OwnerException {
        Date month = aggFee.getMonth();
        Integer companyId = userInfo.getCompanyId();
        String monthKey = DateUtil.format(month, DateUtil.DEFAULT_YYMM_FORMAT);
        List<ProjectRdEmployeeModel> list = projectRdEmployeeDao.getAggList(month, companyId);
        Set<Integer> set = new HashSet<>();
        list.forEach(item -> {
            set.add(item.getProjectId());
        });
        commonService.checkStatus(set, DateUtil.getMonthFirstDay(month), Arrays.asList(AuditRdTypeEnum.EMPLOYEE.getRdType()), userInfo);
        if (CollectionUtils.isEmpty(list)) {
            throw new OwnerException("【" + monthKey + "】不存在人员归集费用，刷新失败。");
        }
        String keyFormat = "{0}{1}";
        Map<Integer, Map<String, ProjectRdEmployeeModel>> projectDataMap = new HashMap<>();
        Map<String, ProjectRdEmployeeModel> dataEmployeeMap = new HashMap<>();
        Set<String> enumbers = new HashSet<>();
        int hourBit = companySettingService.getEmployeeHourBit(companyId);
        list.forEach(item -> {
            String key = MessageFormat.format(keyFormat, monthKey, item.getEnumber());
            dataEmployeeMap.put(key, item);
            Integer projectId = item.getProjectId();
            if (!projectDataMap.containsKey(projectId)) {
                projectDataMap.put(projectId, new HashMap<>());
            }
            projectDataMap.get(projectId).put(key, ProjectRdEmployeeModel.build(item.getEname(), item.getEnumber(), month, item.getRdHourByBit(hourBit)));
            enumbers.add(item.getEnumber());
        });
        List<Integer> projectIds = new ArrayList<>(projectDataMap.keySet());
        List<MonthThingSetModel> monthKeyList = CollUtil.newArrayList(new MonthThingSetModel(month, enumbers));
        Map<String, FieldConfigModel> salaryConfigMap = salaryService.getSalaryConfig(companyId);
        List<ProjectRdEmployeeModel> data = new ArrayList<>();
        for (Integer projectId : projectIds) {
            Map<String, ProjectRdEmployeeModel> monthData = projectDataMap.get(projectId);
            loadRdAmount(monthData, projectId, monthKeyList, keyFormat, dataEmployeeMap, salaryConfigMap, "同步", true, hourBit);
            data.addAll(monthData.values());
        }
        return saveData(CollUtil.newArrayList(month), projectIds, userInfo, data, hourBit);
    }

    @Override
    public Boolean aggRdPlanFee(AggFeeModel aggFee, UserInfo userInfo) throws OwnerException {
        if (null == aggFee.getMonth() || CollectionUtils.isEmpty(aggFee.getEtypes())) {
            throw new OwnerException("无法获取月份、人月类型，归集失败！");
        }
        Date month = DateUtil.getMonthFirstDay(aggFee.getMonth());
        Integer companyId = userInfo.getCompanyId(), year = cn.hutool.core.date.DateUtil.year(month);
        List<Integer> etypes = aggFee.getEtypes();
        List<RdEmployeePlanModel> rdPlans = rdEmployeePlanDao.getRdPlans(companyId, year, month, etypes);
        if (CollectionUtils.isEmpty(rdPlans)) {
            throw new OwnerException("所选月份相关人员无小程序计划工时记录，请重新选择！");
        }
        Map<String, List<RdEmployeePlanModel>> dataMap = new LinkedHashMap<>();
        rdPlans.forEach(item -> ToolUtils.putAndAdd(dataMap, item.getEnumber(), item));
        List<SalaryInfoModel> salaryInfos = salaryService.getSalaryInfos(companyId, month, dataMap.keySet());
        if (CollectionUtils.isEmpty(salaryInfos)) {
            throw new OwnerException("所选月份相关人员无薪资记录，请重新选择！");
        }
        Map<String, SalaryInfoModel> salaryMap = salaryInfos.stream().collect(Collectors.toMap(SalaryInfoModel::getEnumber, b -> b, (o, n) -> n));
        Map<String, FieldConfigModel> salaryConfigMap = salaryService.getSalaryConfig(companyId);
        int hourBit = companySettingService.getEmployeeHourBit(companyId);
        List<String> payCols = new ArrayList<>(), insuranceCols = new ArrayList<>();
        // 装载薪资，五险一金配置列
        loadConfigCols(salaryConfigMap, payCols, FieldConfigTypeEnum.SALARY.getTitle());
        loadConfigCols(salaryConfigMap, insuranceCols, FieldConfigTypeEnum.INSURANCE.getTitle());
        Boolean hasPayDetail = !payCols.isEmpty();
        SalaryInfoModel currentSalary;
        List<ProjectRdEmployeeEntity> insertList = new ArrayList<>();
        BigDecimal ratio, rdHour, remainHour;
        Date now = new Date();
        Integer userId = userInfo.getUserId(), msUserId = userInfo.getMsUserId();
        for (String enumber : dataMap.keySet()) {
            currentSalary = salaryMap.get(enumber);
            if (null == currentSalary || currentSalary.getWorkHours().compareTo(BigDecimal.ZERO) < 0) {
                continue;
            }
            for (RdEmployeePlanModel currentRdPlan : dataMap.get(enumber)) {
                rdHour = currentRdPlan.getBitPlanTime(hourBit);
                remainHour = currentSalary.getRemainHours().subtract(rdHour);
                if (remainHour.compareTo(BigDecimal.ZERO) < 0) {
                    BigDecimal total = BigDecimal.ZERO;
                    for (RdEmployeePlanModel temp : dataMap.get(enumber)) {
                        total = total.add(temp.getBitPlanTime(hourBit));
                    }
                    throw new OwnerException(MessageFormat.format("{0}月工号为【{1}】的人员总工时为：{2}，总计划工时为:{3}，总计划工时超过人员总工时，请检查！",
                            DateUtil.format(month, DateUtil.DEFAULT_YYMM_FORMAT), enumber, currentSalary.getWorkHours(), total));
                }
                currentSalary.setRemainHours(remainHour);
                ratio = rdHour.divide(currentSalary.getWorkHours(), Constant.MAX_SCALE, BigDecimal.ROUND_HALF_UP);
                insertList.add(ProjectRdEmployeeEntity.build(now, companyId, userId, msUserId, currentRdPlan.getProjectId(), month,
                        enumber, rdHour, getRdAmount(ratio, currentSalary.getPay(), hasPayDetail, currentSalary.getPayDetail(), payCols),
                        getRdAmount(ratio, currentSalary.getInsuranceFund(), true, currentSalary.getInsuranceDetail(), insuranceCols), ratio));
            }
        }
        saveAggFee(month, now, userInfo, year, etypes, insertList, null, null);
        return true;
    }

    @Override
    public List<SalaryRdFeeInfoModel> getFeeDetail(QueryProjectRdEmployeeModel query) {
        List<SalaryRdFeeBaseModel> detailList = projectRdEmployeeDao.getFeeDetail(query);
        if (CollectionUtils.isEmpty(detailList)) {
            return new ArrayList<>();
        }
        Map<Integer, SalaryRdFeeInfoModel> resultMap = new LinkedHashMap<>();
        SalaryRdFeeInfoModel total = SalaryRdFeeInfoModel.build("总计");
        Integer accountTitleId;
        for (SalaryRdFeeBaseModel item : detailList) {
            accountTitleId = item.getAccountTitleId();
            if (!resultMap.containsKey(accountTitleId)) {
                resultMap.put(accountTitleId, SalaryRdFeeInfoModel.build(item.getAccountName()));
            }
            resultMap.get(accountTitleId).add(item);
            total.add(item);
        }
        resultMap.values().forEach(SalaryRdFeeInfoModel::loadDetailMap);
        total.loadDetailMap();
        resultMap.put(-2, total);
        return CollUtil.newArrayList(resultMap.values());
    }

    @Override
    public PageModel<List<RdEmployeeSummaryModel>> getMonthTotalStaff(UserInfo userInfo, QueryProjectRdEmployeeModel model) {
        Pagination page = model.getPagination();
        Date month = DateUtil.getMonthFirstDay(model.getMonth());
        return PageUtils.build(page, projectRdEmployeeDao.getMonthTotalStaff(page, userInfo.getCompanyId(), month, model.getYear()));
    }

    private Map<Integer, Map<String, ProjectRdEmployeeModel>> getProjectAgg(
            Map<Integer, AggDeeConfigDetailModel> configMap, Integer companyId, Map<Integer, Set<String>> etypeEnumberMap,
            Map<String, Set<Integer>> enumberProjectMap, Map<String, ProjectRdEmployeeModel> dataEmployeeMap,
            Date month, String keyFormat) throws OwnerException {
        Map<String, String> employeeMap = getEnumberName(companyId, enumberProjectMap.keySet());
        Map<Integer, Map<String, ProjectRdEmployeeModel>> result = new HashMap<>();
        String mStr = DateUtil.format(month, DateUtil.DEFAULT_YYMM_FORMAT);
        String currentKey;
        BigDecimal div = BigDecimal.valueOf(100d);
        BigDecimal etypeRatio;
        for (Integer etype : configMap.keySet()) {
            AggDeeConfigDetailModel detail = configMap.get(etype);
            if (null == detail || detail.noMax()) {
                continue;
            }
            etypeRatio = detail.getEtypeRatio();
            if (null != etypeRatio && etypeRatio.compareTo(BigDecimal.ZERO) >= 0) {
                etypeRatio = detail.getEtypeRatio().divide(div, 2, BigDecimal.ROUND_HALF_UP);
            } else {
                etypeRatio = BigDecimal.ZERO;
            }
            Set<String> currentEnumbers = etypeEnumberMap.get(etype);
            for (String enumber : currentEnumbers) {
                Set<Integer> projectIds = enumberProjectMap.get(enumber);
                if (CollUtil.isEmpty(projectIds)) {
                    continue;
                }
                int maxIndex = projectIds.size() - 1;
                List<BigDecimal> rdRatio = detail.getConfigs().get(maxIndex);
                if (CollUtil.isEmpty(rdRatio)) {
                    continue;
                }
                int i = 0;
                for (Integer projectId : projectIds) {

                    if (i >= rdRatio.size()) {
                        break;
                    }
                    currentKey = MessageFormat.format(keyFormat, mStr, enumber);
                    ProjectRdEmployeeModel item = dataEmployeeMap.get(currentKey);
                    // 不存在薪资数据，则跳过
                    if (item == null) {
                        break; // // TODO: 22/04/11 zdf 用 continue 跳过问题
//                        continue;
                    }
                    BigDecimal ratio = rdRatio.get(i);
                    i++;
                    if (ratio == null) {
                        ratio = BigDecimal.ZERO;
                    }
                    ratio = ratio.divide(div, 2, BigDecimal.ROUND_HALF_UP);
                    if (!result.containsKey(projectId)) {
                        result.put(projectId, new HashMap<>());
                    }
                    result.get(projectId).put(currentKey,
                            ProjectRdEmployeeModel.build(employeeMap.get(enumber), enumber, month, item.getWorkHours().multiply(etypeRatio).multiply(ratio)));
                }
            }
        }
        return result;
    }

    /**
     * 获取enumber对应的ename的map
     *
     * @param companyId
     * @param enumbers
     * @return
     * @throws OwnerException
     */
    private Map<String, String> getEnumberName(Integer companyId, Set<String> enumbers) throws OwnerException {
        List<EmployeeEntity> employees = employeeDao.getEnameByEnumbers(companyId, enumbers);
        if (CollectionUtils.isEmpty(employees)) {
            throw new OwnerException("不存在可核算人员。");
        }
        return employees.stream().collect(Collectors.toMap(EmployeeEntity::getEnumber, EmployeeEntity::getEname, (o, n) -> n));
    }

    private void loadRdAmount(Map<String, ProjectRdEmployeeModel> monthDataMap, Integer projectId,
                              List<MonthThingSetModel> monthKeysList, String keyFormat,
                              Map<String, ProjectRdEmployeeModel> dataEmployeeMap,
                              Map<String, FieldConfigModel> salaryConfigMap, String label, Boolean skipHourFilter, int hourBit) throws OwnerException {
        List<ProjectRdEmployeeEntity> oldEmployees = projectRdEmployeeDao.getEnumberId(projectId, monthKeysList);
        Map<String, Integer> oldEmployeeMap = oldEmployees.stream().collect(Collectors.toMap(
                a -> MessageFormat.format(keyFormat, DateUtil.format(a.getMonth(), DateUtil.DEFAULT_YYMM_FORMAT), a.getEnumber()),
                b -> b.getId(), (e, n) -> n));
        List<String> payCols = new ArrayList<>(), insuranceCols = new ArrayList<>();
        loadConfigCols(salaryConfigMap, payCols, FieldConfigTypeEnum.SALARY.getTitle());
        loadConfigCols(salaryConfigMap, insuranceCols, FieldConfigTypeEnum.INSURANCE.getTitle());
        Boolean hasPayDetail = !payCols.isEmpty();
        for (String key : monthDataMap.keySet()) {
            ProjectRdEmployeeModel current = monthDataMap.get(key);
            current.setProjectId(projectId);
            BigDecimal curRdHour = current.getRdHourByBit(hourBit);
            if (curRdHour.compareTo(BigDecimal.ZERO) < 0) {
                throw new OwnerException(MessageFormat.format(
                        "月份【{3}】、工号【{1}】、姓名【{2}】，{4}工时【{0,number,#.#}】不能小于0。", curRdHour,
                        current.getEnumber(), current.getEname(), key.substring(0, 7), label));
            }
            ProjectRdEmployeeModel dataEmployee = dataEmployeeMap.get(key);
            BigDecimal remainHour = dataEmployee.getRemainHour();
            if (remainHour == null) {
                remainHour = dataEmployee.getWorkHours();
            }
            // 跳过时间拦截，则迭代remainHour，供下次循环使用
            if (skipHourFilter) {
                if (curRdHour.compareTo(remainHour) > 0) {
                    current.setRdHour(remainHour);
                    logger.warn(MessageFormat.format(
                            "月份【{2}】、工号【{0}】、姓名【{1}】可分配工时{3,number,#.#}小于研发工时{3,number,#.#}",
                            current.getEnumber(), current.getEname(), key.substring(0, 7), remainHour, curRdHour));
                }
                dataEmployee.setRemainHour(remainHour.subtract(curRdHour));
            } else {
                if (curRdHour.compareTo(remainHour) > 0) {
                    throw new OwnerException(MessageFormat.format(
                            "月份【{4}】、工号【{2}】、姓名【{3}】，{4}工时【{0,number,#.#}】超过最大可分配工时【{1,number,#.#}】。",
                            curRdHour, remainHour, current.getEnumber(), current.getEname(), key.substring(0, 7), label));
                }
            }

            BigDecimal ratio = BigDecimal.ZERO;
            if (dataEmployee.getWorkHours().compareTo(BigDecimal.ZERO) > 0) {
                ratio = curRdHour.divide(dataEmployee.getWorkHours(), Constant.MAX_SCALE, BigDecimal.ROUND_HALF_UP);
            }
            current.setRdPay(getRdAmount(ratio, dataEmployee.getPay(), hasPayDetail, dataEmployee.getPayDetail(), payCols));
            current.setRdInsuranceFund(getRdAmount(ratio, dataEmployee.getInsuranceFund(), true, dataEmployee.getInsuranceDetail(), insuranceCols));
            current.setId(oldEmployeeMap.get(key));
        }
    }

    private void checkAndLoadMonthData(Map<String, ProjectRdEmployeeModel> monthDataMap,
                                       Map<Date, MonthThingSetModel> monthKeysMap, Integer projectId,
                                       List<ProjectRdEmployeeModel> dataList, int year, String keyFormat) throws OwnerException {
        ProjectEntity projectEntity = projectDao.selectById(projectId);
        long beginTime = DateUtil.getMonthFirstDay(projectEntity.getBeginDate()).getTime();
        long endTime = projectEntity.getEndDate().getTime();
        for (ProjectRdEmployeeModel item : dataList) {
            Date m = DateUtil.getMonthFirstDay(item.getMonth());
            item.setMonth(m);
            if (!monthKeysMap.containsKey(m)) {
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
                monthKeysMap.put(m, new MonthThingSetModel(m));
            }
            monthDataMap.put(
                    MessageFormat.format(keyFormat, DateUtil.format(item.getMonth(), DateUtil.DEFAULT_YYMM_FORMAT), item.getEnumber()),
                    item
            );
            monthKeysMap.get(m).getKeys().add(item.getEnumber());
        }
    }

    private BigDecimal getRdAmount(BigDecimal ratio, BigDecimal amount, Boolean hasDetail, String detail, List<String> cols) {
        if (!hasDetail || cols.isEmpty() || StringUtils.isEmpty(detail)) {
            return ratio.multiply(amount);
        }
        BigDecimal rdAmount = BigDecimal.ZERO;
        Map<String, Object> map = JsonUtils.jsonToPojo(detail, Map.class);
        if (CollectionUtils.isEmpty(map)) {
            return ratio.multiply(amount);
        }
        for (String col : cols) {
            Object obj = map.get(col);
            if (!StringUtils.isEmpty(obj)) {
                // 按4位，再按2位补位
                rdAmount = rdAmount.add(ratio.multiply(new BigDecimal(obj.toString())).setScale(4, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP));
            }
        }
        return rdAmount;
    }

    private Boolean saveData(List<Date> months, List<Integer> projectIds, UserInfo userInfo, List<ProjectRdEmployeeModel> dataList, int hourBit) throws OwnerException {
        if (CollectionUtils.isEmpty(dataList)) {
            throw new OwnerException("保存失败，未获取到任何有效数据。");
        }
        List<ProjectRdEmployeeEntity> insertList = new ArrayList<>();
        List<ProjectRdEmployeeEntity> updateList = new ArrayList<>();
        Date now = new Date();
        List<Integer> deleteIds = new ArrayList<>();
        Map<Integer, Map<Date, Set<String>>> projectMonthEnumberMap = new HashMap<>();
        dataList.forEach(item -> {
            Integer projectId = item.getProjectId();
            if (null == item.getRdHour() || item.getRdHour().compareTo(BigDecimal.ZERO) == 0) {
                if (null != item.getId()) {
                    deleteIds.add(item.getId());
                    if (!projectMonthEnumberMap.containsKey(projectId)) {
                        projectMonthEnumberMap.put(projectId, new HashMap<>());
                    }
                    ToolUtils.putAndAddSet(projectMonthEnumberMap.get(projectId), null == item.getMonth() ? months.get(0) : item.getMonth(), item.getEnumber());
                }
                return;
            }
            ProjectRdEmployeeEntity entity = ProjectRdEmployeeEntity.build(now, projectId, userInfo, item, hourBit);
            if (null != item.getId()) {
                entity.setId(item.getId());
                updateList.add(entity);
            } else {
                entity.setCreateTime(now);
                entity.setMsCreatorId(userInfo.getMsUserId());
                entity.setCreatorId(userInfo.getUserId());
                insertList.add(entity);
            }
        });
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            if (!CollectionUtils.isEmpty(insertList)) {
                projectRdEmployeeDao.addBatch(insertList);
            }
            if (!CollectionUtils.isEmpty(updateList)) {
                projectRdEmployeeDao.updateBatch(updateList);
            }
            if (!CollectionUtils.isEmpty(deleteIds)) {
                for (Integer projectId : projectMonthEnumberMap.keySet()) {
                    for (Date m : projectMonthEnumberMap.get(projectId).keySet()) {
                        projectAttendanceService.deleteUsed(userInfo, projectMonthEnumberMap.get(projectId).get(m), m, DateUtil.getMonthLastDay(m), projectId);
                    }
                }
                projectRdEmployeeDao.deleteBatchIds(deleteIds);
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

    /**
     * 装载配置列
     */
    private void loadConfigCols(Map<String, FieldConfigModel> salaryConfigMap, List<String> cols, String key) {
        FieldConfigModel config = salaryConfigMap.get(key);
        if (null != config) {
            List<HashMap> configMap = JsonUtils.jsonToList(config.getConfig(), HashMap.class);
            configMap.forEach(item -> cols.add(item.get("name").toString()));
        }
    }

    @Override
    public AggMsgModel aggRdTrialFee(TrialAggModel aggModel, CostEnum rdType, UserInfo info) {
        try {
            Integer companyId = info.getCompanyId();
            Date month = aggModel.getMonth();
            Integer year = cn.hutool.core.date.DateUtil.year(month), etype = EmployeeTypeEnum.SUPPORTSTAFF.getValue();
            List<ProjectEmployeeModel> members = initMemberDao.getByEtypes(companyId, year, etype, DateUtil.getMonthLastDay(month));
            if (CollectionUtils.isEmpty(members)) {
                return AggMsgModel.buildFail("无可归集的辅助人员，归集辅助人员费用失败！");
            }
            Map<String, List<Integer>> enumberProjectMap = new HashMap<>();
            members.forEach(item -> ToolUtils.putAndAdd(enumberProjectMap, item.getEnumber(), item.getProjectId()));
            List<SalaryInfoModel> salaryInfos = salaryService.getSalaryInfos(companyId, month, enumberProjectMap.keySet());
            if (CollectionUtils.isEmpty(salaryInfos)) {
                return AggMsgModel.buildFail("所选月份相关人员无薪资记录，归集辅助人员费用失败！");
            }
            Map<Integer, ProjectYieldConfigTotalModel> projectTotalMap = aggModel.getProjectTotalMap();
            Map<String, SalaryInfoModel> salaryMap = salaryInfos.stream().collect(Collectors.toMap(SalaryInfoModel::getEnumber, b -> b, (o, n) -> n));
            Map<String, FieldConfigModel> salaryConfigMap = salaryService.getSalaryConfig(companyId);
            int hourBit = companySettingService.getEmployeeHourBit(companyId);
            List<String> payCols = new ArrayList<>(), insuranceCols = new ArrayList<>();
            // 装载薪资，五险一金配置列
            List<ProjectRdEmployeeEntity> insertList = new ArrayList<>();
            loadConfigCols(salaryConfigMap, payCols, FieldConfigTypeEnum.SALARY.getTitle());
            loadConfigCols(salaryConfigMap, insuranceCols, FieldConfigTypeEnum.INSURANCE.getTitle());
            Boolean hasPayDetail = !payCols.isEmpty(), employeeHour = aggModel.getEmployeeHour();
            Map<String, ProjectAttendance> attMap = null;
            Map<String, ProjectAttendanceUsed> attUsedMap = null;
            if (employeeHour) {
                attMap = new LinkedHashMap<>();
                attUsedMap = new LinkedHashMap<>();
            }
            SalaryInfoModel currentSalary;
            Date now = new Date();
            Integer userId = info.getUserId(), msUserId = info.getMsUserId();
            for (String enumber : enumberProjectMap.keySet()) {
                currentSalary = salaryMap.get(enumber);
                if (currentSalary == null || currentSalary.getWorkHours().compareTo(BigDecimal.ZERO) <= 0) {
                    continue;
                }
                for (Integer projectId : enumberProjectMap.get(enumber)) {
                    if (!projectTotalMap.containsKey(projectId)) {
                        continue;
                    }
                    ProjectYieldConfigTotalModel yield = projectTotalMap.get(projectId);
                    BigDecimal rdHour = yield.getBitAgg(hourBit);
                    BigDecimal remainHour = currentSalary.getRemainHours().subtract(rdHour);
                    if (remainHour.compareTo(BigDecimal.ZERO) < 0) {
                        BigDecimal total = BigDecimal.ZERO;
                        for (Integer temProjectId : enumberProjectMap.get(enumber)) {
                            if (!projectTotalMap.containsKey(temProjectId)) {
                                continue;
                            }
                            total = total.add(projectTotalMap.get(temProjectId).getBitAgg(hourBit));
                        }
                        return AggMsgModel.buildFail(MessageFormat.format("{0}月工号为【{1}】的人员总工时为：{2}，总研发工时为：{3}，总研发工时超过人员总工时，归集辅助人员费用失败！",
                                DateUtil.format(month, DateUtil.DEFAULT_YYMM_FORMAT), enumber, currentSalary.getWorkHours(), total));
                    }
                    currentSalary.setRemainHours(remainHour);
                    BigDecimal ratio = rdHour.divide(currentSalary.getWorkHours(), Constant.MAX_SCALE, BigDecimal.ROUND_HALF_UP);
                    ProjectRdEmployeeEntity projectRdEmployee = ProjectRdEmployeeEntity.build(now, companyId, userId, msUserId, projectId, month,
                            enumber, rdHour, getRdAmount(ratio, currentSalary.getInsuranceFund(), true, currentSalary.getInsuranceDetail(), insuranceCols),
                            getRdAmount(ratio, currentSalary.getPay(), hasPayDetail, currentSalary.getPayDetail(), payCols), ratio);
                    if (employeeHour) {
                        Map<String, ProjectAttendance> curAttMap = new LinkedHashMap<>();
                        loadAttendance(yield.getInfos().values(), curAttMap, attUsedMap, enumber, companyId, userId, msUserId, now);
                        if (!CollectionUtils.isEmpty(curAttMap)) {
                            attMap.putAll(curAttMap);
                            BigDecimal attendanceHour = BigDecimal.ZERO;
                            for (ProjectAttendance att : curAttMap.values()) {
                                attendanceHour = attendanceHour.add(att.getWorkHour());
                            }
                            projectRdEmployee.setAttendanceHour(attendanceHour);
                        }
                    }
                    insertList.add(projectRdEmployee);
                }
            }
            if (CollectionUtils.isEmpty(insertList)) {
                return AggMsgModel.buildFail("无可归集的辅助人员，归集辅助人员费用失败！");
            }
            saveAggFee(month, now, info, year, CollUtil.newArrayList(etype), insertList, employeeHour ? new ArrayList<>(attMap.values()) : null,
                    employeeHour ? new ArrayList<>(attUsedMap.values()) : null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return AggMsgModel.buildFail("归集辅助人员费用失败！");
        }

        return AggMsgModel.buildSuccess("归集辅助人员费用成功！");
    }

    private void loadAttendance(Collection<List<ProjectYieldConfigInfoModel>> data, Map<String, ProjectAttendance> attMap,
                                Map<String, ProjectAttendanceUsed> attUsedMap, String enumber, Integer companyId, Integer userId, Integer msUserId, Date now) {
        for (List<ProjectYieldConfigInfoModel> list : data) {
            ProjectYieldConfigInfoModel first = list.get(0);
            String usedKey = DateUtil.format(first.getTrialDate(), DateUtil.DEFAULT_DATE_FORMAT) + enumber;
            if (!attUsedMap.containsKey(usedKey)) {
                attUsedMap.put(usedKey, ProjectAttendanceUsed.build(null, null, BigDecimal.ZERO,
                        "", companyId, userId, msUserId, now, enumber, first.getTrialDate()));
            }
            String attKey = usedKey + first.getProjectId();
            if (!attMap.containsKey(attKey)) {
                attMap.put(attKey, ProjectAttendance.build(enumber, companyId, first.getProjectId(), first.getTrialDate(), userId, msUserId, now));
            }
            for (ProjectYieldConfigInfoModel item : list) {
                attMap.get(attKey).addTime(item, attUsedMap.get(usedKey));
            }
        }
    }

    /**
     * 保存归集费用（研发试制，小程序计划工时用）
     *
     * @param month
     * @param now
     * @param info
     * @param year
     * @param etypes
     * @param insertList
     * @param attendanceList
     * @param attendanceUsedList
     * @throws OwnerException
     */
    private void saveAggFee(Date month, Date now, UserInfo info, Integer year, List<Integer> etypes, List<ProjectRdEmployeeEntity> insertList,
                            List<ProjectAttendance> attendanceList, List<ProjectAttendanceUsed> attendanceUsedList) throws OwnerException {
        Integer companyId = info.getCompanyId();
        Date monthEnd = DateUtil.getMonthLastDay(month);
        List<Integer> projectIds = projectDao.getProjectIds(companyId, month, monthEnd);
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction();
            projectRdEmployeeDao.deleteData(companyId, year, month, etypes);
            projectAttendanceDao.deleteData(companyId, year, month, monthEnd, etypes);
            if (!CollectionUtils.isEmpty(insertList)) {
                List<List<ProjectRdEmployeeEntity>> subList = ListUtils.subList(insertList, Constant.MAX_INSERT_OR_UPDATE_COUNT);
                for (List<ProjectRdEmployeeEntity> currentList : subList) {
                    projectRdEmployeeDao.addBatch(currentList);
                }
            }
            if (!CollectionUtils.isEmpty(attendanceList)) {
                List<List<ProjectAttendance>> subList = ListUtils.subList(attendanceList, Constant.MAX_INSERT_OR_UPDATE_COUNT);
                for (List<ProjectAttendance> currentList : subList) {
                    projectAttendanceDao.insertBatch(currentList);
                }
            }
            if (!CollectionUtils.isEmpty(attendanceUsedList)) {
                List<List<ProjectAttendanceUsed>> subList = ListUtils.subList(attendanceUsedList, Constant.MAX_INSERT_OR_UPDATE_COUNT);
                for (List<ProjectAttendanceUsed> currentList : subList) {
                    projectAttendanceUsedDao.insertBatch(currentList);
                }
            }
            insertSummary(now, projectIds, CollUtil.newArrayList(month), info, true);
            TransactionUtils.commit(transactionStatus);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException("归集失败。");
        }
    }
}
