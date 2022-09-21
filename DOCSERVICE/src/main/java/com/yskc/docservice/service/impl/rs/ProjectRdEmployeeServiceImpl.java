package com.yskc.docservice.service.impl.rs;

import cn.hutool.core.collection.CollUtil;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.JsonUtils;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.dao.rs.SummaryDao;
import com.yskc.docservice.dao.rs.init.InitMemberDao;
import com.yskc.docservice.dao.rs.project.ProjectDao;
import com.yskc.docservice.dao.rs.project.ProjectRdEmployeeDao;
import com.yskc.docservice.entity.rs.SummaryEntity;
import com.yskc.docservice.entity.rs.init.InitMemberEntity;
import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.entity.rs.project.ProjectRdEmployeeEntity;
import com.yskc.docservice.enums.AuditRdTypeEnum;
import com.yskc.docservice.enums.CostEnum;
import com.yskc.docservice.enums.SalaryConfigTypeEnum;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.fieldconfig.FieldConfigModel;
import com.yskc.docservice.models.rs.projectrdemployee.BatchProjectRdEmployeeModel;
import com.yskc.docservice.models.rs.projectrdemployee.MonthThingSetModel;
import com.yskc.docservice.models.rs.projectrdemployee.ProjectRdEmployeeModel;
import com.yskc.docservice.models.rs.projectrdemployee.ProjectRdEmployeeTotalModel;
import com.yskc.docservice.service.rs.CompanySettingService;
import com.yskc.docservice.service.rs.ProjectAttendanceService;
import com.yskc.docservice.service.rs.ProjectRdEmployeeService;
import com.yskc.docservice.service.rs.SalaryService;
import com.yskc.docservice.utils.ToolUtils;
import com.yskc.docservice.utils.TransactionUtils;
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
    private ProjectDao projectDao;
    @Autowired
    private InitMemberDao initMemberDao;
    @Autowired
    private SalaryService salaryService;
    @Autowired
    private CompanySettingService companySettingService;
    @Autowired
    private ProjectAttendanceService projectAttendanceService;
    @Autowired
    private CommonService commonService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public Boolean importRdHour(RsUserInfo info, BatchProjectRdEmployeeModel batchModel, int year) throws OwnerException {
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
       /* List<Integer> rdTypes = new ArrayList<>();
        rdTypes.add(AuditRdTypeEnum.EMPLOYEE.getRdType());*/
        for (Date m : monthKeysMap.keySet()) {
            enumbers.addAll(monthKeysMap.get(m).getKeys());
        }
        List<Integer> rdTypes = Arrays.asList(AuditRdTypeEnum.EMPLOYEE.getRdType());
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

//    @Override
    public void insertSummary(Date now, List<Integer> projectIds, List<Date> months, RsUserInfo userInfo) {
        List<ProjectRdEmployeeTotalModel> totals = projectRdEmployeeDao.getSummary(projectIds, months);
        List<SummaryEntity> summaryList = new ArrayList<>();
        Map<String, ProjectRdEmployeeTotalModel> monthMap = new HashMap<>();
        String keyFormat = "{0}_{1}";
        if (!CollectionUtils.isEmpty(totals)) {
            totals.forEach(item -> monthMap.put(MessageFormat.format(keyFormat, item.getProjectId(), item.getMonth().getTime()), item));
        }
        months.forEach(m -> {
            projectIds.forEach(projectId -> {
                BigDecimal insurance = BigDecimal.ZERO, salary = BigDecimal.ZERO;
                ProjectRdEmployeeTotalModel current = monthMap.get(MessageFormat.format(keyFormat, projectId, m.getTime()));
                if (null != current) {
                    insurance = current.getInsuranceFund();
                    salary = current.getPay();
                }
                summaryList.add(ToolUtils.build(now, m, projectId, CostEnum.INSURANCE.getType(), insurance, userInfo));
                summaryList.add(ToolUtils.build(now, m, projectId, CostEnum.SALARY.getType(), salary, userInfo));
            });

        });
        summaryDao.insertOrUpdate(summaryList);
        // 已在删除项目时清理
        // summaryDao.delRdFundsForZero(projectId,months);
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


    private Boolean saveData(List<Date> months, List<Integer> projectIds, RsUserInfo userInfo, List<ProjectRdEmployeeModel> dataList, int hourBit) throws OwnerException {
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
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
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
            insertSummary(now, projectIds, months, userInfo);
            TransactionUtils.commit(DataSourceEnum.RS,transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.RS,transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException("保存失败");
        }
    }


    private void loadRdAmount(Map<String, ProjectRdEmployeeModel> monthDataMap, Integer projectId,
                              List<MonthThingSetModel> monthKeysList, String keyFormat,
                              Map<String, ProjectRdEmployeeModel> dataEmployeeMap,
                              Map<String, FieldConfigModel> salaryConfigMap, String label, Boolean skipHourFilter,int hourBit) throws OwnerException {
        List<ProjectRdEmployeeEntity> oldEmployees = projectRdEmployeeDao.getEnumberId(projectId, monthKeysList);
        Map<String, Integer> oldEmployeeMap = oldEmployees.stream().collect(Collectors.toMap(
                a -> MessageFormat.format(keyFormat, DateUtil.format(a.getMonth(), DateUtil.DEFAULT_YYMM_FORMAT), a.getEnumber()),
                b -> b.getId(), (e, n) -> n));
        Boolean hasPayDetail = salaryConfigMap.containsKey(SalaryConfigTypeEnum.SALARY.getTitle());
        List<String> payCols = new ArrayList<>();
        if (hasPayDetail) {
            FieldConfigModel payConfigModel = salaryConfigMap.get(SalaryConfigTypeEnum.SALARY.getTitle());
            List<HashMap> payColMaps = JsonUtils.jsonToList(payConfigModel.getConfig(), HashMap.class);
            payColMaps.forEach(item -> {
                payCols.add(item.get("name").toString());
            });
        }
        List<String> insuranceCols = new ArrayList<>();
        FieldConfigModel insuranceConfigModel = salaryConfigMap.get(SalaryConfigTypeEnum.INSURANCE.getTitle());
        List<HashMap> insuranceColMaps = JsonUtils.jsonToList(insuranceConfigModel.getConfig(), HashMap.class);
        insuranceColMaps.forEach(item -> {
            insuranceCols.add(item.get("name").toString());
        });
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
                            current.getEnumber(), current.getEname(), key.substring(0, 7),remainHour,curRdHour));
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
                rdAmount = rdAmount.add(ratio.multiply(new BigDecimal(obj.toString())).setScale(4,BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP));
            }
        }
        return rdAmount;
    }


}
