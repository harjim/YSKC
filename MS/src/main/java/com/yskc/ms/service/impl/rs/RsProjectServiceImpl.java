package com.yskc.ms.service.impl.rs;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.common.utils.RedisUtils;
import com.yskc.ms.config.Constant;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.dao.ms.FlowInstanceDocFileDao;
import com.yskc.ms.dao.ms.FlowInstanceReportDao;
import com.yskc.ms.dao.rs.*;
import com.yskc.ms.entity.ms.FlowInstanceReport;
import com.yskc.ms.entity.rs.*;
import com.yskc.ms.entity.rs.models.ProjectListModel;
import com.yskc.ms.enums.CostEnum;
import com.yskc.ms.enums.RsProjectStatusEnum;
import com.yskc.ms.enums.RsRdTypeTableEnum;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.project.DataReportModel;
import com.yskc.ms.models.projectApproval.ProjectApprovalModel;
import com.yskc.ms.models.projectApproval.QueryProjectApprovalModel;
import com.yskc.ms.models.projectApproval.QueryProjectEquipmentModel;
import com.yskc.ms.models.projectApproval.QueryProjectMemberModel;
import com.yskc.ms.models.projectAudit.AuditDocFileModel;
import com.yskc.ms.models.rs.*;
import com.yskc.ms.models.rs.summary.*;
import com.yskc.ms.service.rs.RsProjectService;
import com.yskc.ms.utils.ToolUtil;
import com.yskc.ms.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RsProjectServiceImpl implements RsProjectService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RsProjectDao projectDao;
    @Autowired
    private RsSummaryDao rsSummaryDao;
    @Autowired
    private ProjectStatusDao projectStatusDao;
    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private BudgetStatusDao budgetStatusDao;
    @Autowired
    private ProjectDocFileDao projectDocFileDao;

    @Autowired
    private FlowInstanceDocFileDao flowInstanceDocFileDao;
    @Autowired
    private FlowInstanceReportDao flowInstanceReportDao;
    @Autowired
    private ProjectRdEquipmentDao projectRdEquipmentDao;
    @Autowired
    private ProjectRdEmployeeDao projectRdEmployeeDao;
    @Autowired
    private InitMemberDao initMemberDao;

    @Autowired
    private RsCompanyDao rsCompanyDao;
    @Autowired
    private MsConfig msConfig;
    @Autowired
    private RsSummaryDao summaryDao;
    @Autowired
    private RsProjectDao rsProjectDao;

    @Override
    public List<ProjectListModel> queryProject(Integer companyId, Integer year, Integer msUserId) throws OwnerException {
        List<ProjectListModel> projects = projectDao.getProjectsByYear(year, companyId);
        if (!CollectionUtils.isEmpty(projects)) {
            List<Integer> projectIds = projects.stream().map(e -> e.getId()).collect(Collectors.toList());
            List<FlowInstanceReport> projectAudits = flowInstanceReportDao.getProjectAudits(projectIds, msUserId);
            Map<Integer, FlowInstanceReport> permissionMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(projectAudits)) {
                permissionMap = projectAudits.stream().collect(Collectors.toMap(e -> e.getRsProjectId(), e -> e));
            }
            for (ProjectListModel project : projects) {
                project.setHasPermission(permissionMap.containsKey(project.getId()));
            }
        }
        return projects;
    }

    @Override
    public List<DataReportModel> getDataReportFundsData(Integer year, Integer companyId) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        Date beginDate = DateUtil.beginOfYear(calendar.getTime());
        Date endDate = DateUtil.endOfYear(beginDate);
        List<DataReportModel> summaryModels = rsSummaryDao.getDataReportFunds(companyId, year, beginDate, endDate);
        if (CollectionUtils.isEmpty(summaryModels)) {
            return null;
        }
        DataReportModel totalModel = new DataReportModel();
        totalModel.setId(0);
        totalModel.setPname("总计");
        summaryModels.forEach(item -> {
            DataReportModel.sum(item, totalModel);
        });
        summaryModels.add(totalModel);
        return summaryModels;
    }

    @Override
    public List<RdFundsModel> getRdFunds(Integer companyId, Integer year) {
        List<RdFundsModel> projects = projectDao.getProjectIdsByYear(companyId, year);
        if (CollectionUtils.isEmpty(projects)) {
            return new ArrayList<>();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        Date beginDate = DateUtil.beginOfYear(calendar.getTime());
        Date endDate = DateUtil.endOfYear(beginDate);
        List<RdMonthFundsStatusModel> monthFunds = rsSummaryDao.getRdMonthFunds(companyId, beginDate, endDate);
        // projectId::month::rdMonthFundsModel
        Map<Integer, Map<String, RdMonthFundsStatusModel>> projectMonthFundsMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(monthFunds)) {
            monthFunds.forEach(item -> {
                Integer projectId = item.getProjectId();
                if (!projectMonthFundsMap.containsKey(projectId)) {
                    projectMonthFundsMap.put(projectId, new HashMap<>());
                }
                projectMonthFundsMap.get(projectId).put(DateUtil.format(item.getMonth(), "yyyy-MM"), item);
            });
        }
        projects.forEach(item -> {
            item.setMonthFundsStatus(new ArrayList<>());
            int month = 0;
            if (item.getBeginDate().compareTo(beginDate) > 0) {
                month = DateUtil.month(item.getBeginDate());
            }
            int max = 11;
            if (item.getEndDate().compareTo(endDate) < 0) {
                max = DateUtil.month(item.getEndDate());
            }
            Map<String, RdMonthFundsStatusModel> map = projectMonthFundsMap.getOrDefault(item.getId(), new HashMap<>());
            for (; month <= max; month++) {
                calendar.set(year, month, 1, 0, 0, 0);
                Date currentMonth = calendar.getTime();
                RdMonthFundsStatusModel fund = map.get(DateUtil.format(currentMonth, com.yskc.common.utils.DateUtil.DEFAULT_YYMM_FORMAT));
                if (fund == null) {
                    fund = RdMonthFundsStatusModel.build(item.getId(), currentMonth);
                }
                item.addFund(fund);
                item.getMonthFundsStatus().add(fund);
            }
        });
        return projects;
    }

    @Override
    public Boolean setStatus(ProjectStatusModel projectStatus, UserInfo userInfo) throws OwnerException {
        List<ProjectBaseStatusModel> audits = projectStatus.getAudits();
        List<ProjectStatus> existList = projectStatusDao.getExist(audits);
        Date now = new Date();
        try {
            String keyFormat = "{0}_{1}";
            Map<String, Boolean> existMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(existList)) {
                existList.forEach(item -> {
                    existMap.put(MessageFormat.format(keyFormat, item.getProjectId(), DateUtil.format(item.getMonth(), "yyyy-MM")), true);
                });
                projectStatusDao.updateStatus(existList, projectStatus.getStatus(), now, userInfo.getId());
            }
            List<ProjectStatus> insertList = new ArrayList<>();
            Integer msUserId = userInfo.getId();
            Integer status = projectStatus.getStatus();
            Integer companyId = projectStatus.getCompanyId();
            audits.forEach(item -> {
                item.getMonths().forEach(m -> {
                    if (!existMap.containsKey(MessageFormat.format(keyFormat, item.getProjectId(), DateUtil.format(m, "yyyy-MM")))) {
                        insertList.add(ProjectStatus.build(item.getProjectId(), m, companyId, status, msUserId, now));
                    }
                });
            });
            if (!CollectionUtils.isEmpty(insertList)) {
                projectStatusDao.addBatch(insertList);
            }
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new OwnerException("操作失败，请稍后再试。");
        } finally {
            List<ProjectStatus> allStatus = projectStatusDao.getProjectStatus(audits);
            if (!CollectionUtils.isEmpty(allStatus)) {
                Map<Integer, Map<String, Integer>> projectMonthStatus = new HashMap<>();
                allStatus.forEach(item -> {
                    Integer projectId = item.getProjectId();
                    if (!projectMonthStatus.containsKey(projectId)) {
                        projectMonthStatus.put(projectId, new HashMap<>());
                    }
                    projectMonthStatus.get(projectId).put(DateUtil.format(item.getMonth(), "yyyy-MM"), item.getStatus());
                });
                for (Integer pId : projectMonthStatus.keySet()) {
                    String key = MessageFormat.format(Constant.REDIS_KEY_PROJECT_STATUS, pId);
                    // 当前存在项目缓存，更新缓存
                    if (redisUtils.hasKey(key)) {
                        redisUtils.set(key, projectMonthStatus.get(pId), Constant.RS_PROJECT_STATUS_TIMEOUT);
                    }
                }
            }
        }
    }


    @Override
    public PageModel<List<ProjectApprovalModel>> getProjectApprovalList(QueryProjectApprovalModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("p.createTime");
            page.setDescs(desc);
        }
        List<ProjectApprovalModel> list = projectDao.getProjectApprovalList(page, query);
        if (CollectionUtils.isEmpty(list)) {
            return PageUtils.build(page, new ArrayList<>());
        }
        List<ProjectApprovalModel> cntList = projectDao.getMemberEquipmentCnt(list);
        if (!CollectionUtils.isEmpty(cntList)) {
            Map<Integer, ProjectApprovalModel> cntMap = cntList.stream().collect(Collectors.toMap(
                    ProjectApprovalModel::getId, b -> b, (o, n) -> n
            ));
            list.forEach(item -> {
                ProjectApprovalModel cnt = cntMap.get(item.getId());
                if (null != cnt) {
                    item.setMemberCnt(cnt.getMemberCnt());
                    item.setEquipmentCnt(cnt.getEquipmentCnt());
                }
            });
        }
        return PageUtils.build(page, list);
    }

    @Override
    public List<RsProjectBaseModel> getProjectList(Integer companyId, Integer year) {
        return projectDao.getProjectList(companyId, year);
    }

    @Override
    public Boolean budgetFinal(ProjectStatusModel projectStatus, UserInfo userInfo) {
        return setBudgetStatus(RsProjectStatusEnum.FINALIZATION, projectStatus, userInfo);
    }

    @Override
    public Boolean budgetRecall(ProjectStatusModel projectStatus, UserInfo userInfo) {
        return setBudgetStatus(RsProjectStatusEnum.RECALL, projectStatus, userInfo);
    }

    @Override
    public List<ReportProjectModel> getReportProject(Integer companyId, Integer year, Integer userId) {
        List<ReportProjectModel> projects = projectDao.getProjects(companyId, year);
        if (!CollectionUtils.isEmpty(projects)) {
            List<Integer> projectIds = projects.stream().map(e -> e.getProjectId()).distinct().collect(Collectors.toList());
            List<ReportAuditInfoModel> reports = flowInstanceReportDao.getProjectAuditStatus(projectIds, userId);
            Map<Integer, ReportAuditInfoModel> dataMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(reports)) {
                dataMap = reports.stream().collect(Collectors.toMap(e -> e.getRsProjectId(), e -> e));
            }
            for (ReportProjectModel model : projects) {
                if (dataMap.containsKey(model.getProjectId())) {
                    ReportAuditInfoModel auditInfoModel = dataMap.get(model.getProjectId());
                    model.setAuditStatus(auditInfoModel.getStatus());
                    model.setHasPermission(auditInfoModel.getUserId() != null);
                }
            }
        }
        return projects;
    }

    @Override
    public Boolean cleanRdFunds(Integer projectId, Integer msUserId) throws OwnerException {
        if (null == projectId || projectId <= 0) {
            throw new OwnerException("清除失败，项目不存在。");
        }
        Integer companyId = projectDao.getCompanyId(projectId);
        if (null == companyId) {
            throw new OwnerException("清除失败，项目不存在。");
        }
        List<Integer> rdTypes = rsSummaryDao.getRdTypes(projectId);
        if (CollectionUtils.isEmpty(rdTypes)) {
            return true;
        }
        Map<RsRdTypeTableEnum, Boolean> rdTypeMap = buildRdTypeEnumMap(rdTypes);
        if (CollectionUtils.isEmpty(rdTypeMap)) {
            return true;
        }
        Map<RsRdTypeTableEnum, List<BaseRemainModel>> rdTypeRemainMap = buildRdTypeRemainMap(projectId, rdTypeMap.keySet());
        Date now = new Date();
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
            // 回写d_material,d_energy,d_design,d_inspection 剩余数量/费用
            if (!CollectionUtils.isEmpty(rdTypeRemainMap)) {
                List<BaseRemainModel> list;
                for (RsRdTypeTableEnum rdTypeTable : rdTypeRemainMap.keySet()) {
                    list = rdTypeRemainMap.get(rdTypeTable);
                    switch (rdTypeTable) {
                        case MATERIAL:
                            rsSummaryDao.updateDataMaterial(now, msUserId, list);
                            rsSummaryDao.deleteProjectMaterial(projectId);
                            break;
                        case ENERGY:
                            rsSummaryDao.updateDataEnergy(now, msUserId, list);
                            rsSummaryDao.deleteProjectEnergy(projectId);
                            break;
                        case DESIGN:
                            rsSummaryDao.updateDataDesign(now, msUserId, list);
                            rsSummaryDao.deleteProjectDesign(projectId);
                            break;
                        case OTHER:
                            rsSummaryDao.updateDataInspection(now, msUserId, list);
                            rsSummaryDao.deleteProjectInspection(projectId);
                        default:
                            break;
                    }
                }
            }
            if (rdTypeMap.containsKey(RsRdTypeTableEnum.EMPLOYEE)) {
                projectRdEmployeeDao.deleteRdEmployee(projectId, null);
                updateAttendanceUsed(projectId, companyId, now, msUserId, null);
            }
            if (rdTypeMap.containsKey(RsRdTypeTableEnum.EQUIPMENT)) {
                projectRdEquipmentDao.deleteRdEquipment(projectId, null);
                updateEquipmentUsed(projectId, companyId, now, msUserId, null);
            }
            rsSummaryDao.deleteByProjectId(projectId);
            rsSummaryDao.deleteInfoSummary(projectId);
            TransactionUtils.commit(DataSourceEnum.RS, transactionStatus);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.RS, transactionStatus);
            throw new OwnerException("清除失败");
        }
        return true;
    }

    @Override
    public Boolean cleanEmployeeFunds(BatchCleanFundsModel cleanFunds, Integer msUserId) throws OwnerException {
        List<InitMemberModel> list = projectRdEmployeeDao.getYearEnumbers(cleanFunds.getIds());
        if(CollectionUtils.isEmpty(list)){
            throw new OwnerException("不存在人员，清除失败。");
        }
        Map<Integer,YearKeysModel> yearKeyMap = new HashMap<>();
        list.forEach(item->{
            Integer year = item.getYear();
            if(!yearKeyMap.containsKey(year)){
                yearKeyMap.put(year,new YearKeysModel(year));
            }
            yearKeyMap.get(year).addKey(item.getEnumber());
        });
        List<YearKeysModel> yearKeys = new ArrayList<>(yearKeyMap.values());
        Integer projectId = cleanFunds.getProjectId();
        Integer companyId = projectDao.getCompanyId(projectId);
        if (null == companyId) {
            throw new OwnerException("项目不存在，清除失败。");
        }
        List<Date> months = projectRdEmployeeDao.getMonths(yearKeys, projectId);
        // 不存在归集月份，则认为没归集过
        if (CollectionUtils.isEmpty(months)) {
            return true;
        }
        Date now = new Date();
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
            projectRdEmployeeDao.deleteRdEmployee(projectId, yearKeys);
            updateAttendanceUsed(projectId, companyId, now, msUserId, yearKeys);
            List<ProjectRdEmployeeTotalModel> totals = projectRdEmployeeDao.getSummary(projectId, months);
            List<SummaryEntity> summaryList = new ArrayList<>();
            Map<Date, ProjectRdEmployeeTotalModel> monthMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(totals)) {
                totals.forEach(item -> monthMap.put(item.getMonth(), item));
            }
            months.forEach(m -> {
                BigDecimal insurance = BigDecimal.ZERO, salary = BigDecimal.ZERO;
                ProjectRdEmployeeTotalModel current = monthMap.get(m);
                if (null != current) {
                    insurance = current.getInsuranceFund();
                    salary = current.getPay();
                }
                summaryList.add(SummaryEntity.build(now, m, projectId, CostEnum.INSURANCE.getType(), insurance, msUserId));
                summaryList.add(SummaryEntity.build(now, m, projectId, CostEnum.SALARY.getType(), salary, msUserId));
            });
            rsSummaryDao.insertOrUpdate(summaryList);
            TransactionUtils.commit(DataSourceEnum.RS, transactionStatus);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.RS, transactionStatus);
            throw new OwnerException("清除失败");
        }
        return true;
    }

    @Override
    public Boolean cleanEquipmentFunds(BatchCleanFundsModel cleanFunds, Integer msUserId) throws OwnerException {
        List<InitEquipmentModel> list = projectRdEquipmentDao.getYearEcodes(cleanFunds.getIds());
        if(CollectionUtils.isEmpty(list)){
            throw new OwnerException("不存在设备，清除失败。");
        }
        Map<Integer,YearKeysModel> yearKeyMap = new HashMap<>();
        list.forEach(item->{
            Integer year = item.getYear();
            if(!yearKeyMap.containsKey(year)){
                yearKeyMap.put(year,new YearKeysModel(year));
            }
            yearKeyMap.get(year).addKey(item.getEcode());
        });
        List<YearKeysModel> yearKeys = new ArrayList<>(yearKeyMap.values());
        Integer projectId = cleanFunds.getProjectId();
        Integer companyId = projectDao.getCompanyId(projectId);
        if (null == companyId) {
            throw new OwnerException("项目不存在，清除失败。");
        }
        List<Date> months = projectRdEquipmentDao.getMonths(yearKeys, projectId);
        // 不存在归集月份，则认为没归集过
        if (CollectionUtils.isEmpty(months)) {
            return true;
        }
        Date now = new Date();
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
            projectRdEquipmentDao.deleteRdEquipment(projectId, yearKeys);
            updateEquipmentUsed(projectId, companyId, now, msUserId, yearKeys);
            List<ProjectRdEquipmentTotalModel> totals = projectRdEquipmentDao.getSummary(projectId, months);
            List<SummaryEntity> summaryList = new ArrayList<>();
            Map<Date, ProjectRdEquipmentTotalModel> totalMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(totals)) {
                totals.forEach(item -> totalMap.put(item.getMonth(), item));
            }
            months.forEach(m -> {
                BigDecimal lab = BigDecimal.ZERO, assets = BigDecimal.ZERO, prod = BigDecimal.ZERO, power = BigDecimal.ZERO;
                ProjectRdEquipmentTotalModel total = totalMap.get(m);
                if (null != total) {
                    lab = total.getLab();
                    assets = total.getAssets();
                    prod = total.getProd();
                    power = total.getPower();
                }
                summaryList.add(SummaryEntity.build(now, m, projectId, CostEnum.LAB.getType(), lab, msUserId));
                summaryList.add(SummaryEntity.build(now, m, projectId, CostEnum.PROD.getType(), prod, msUserId));
                summaryList.add(SummaryEntity.build(now, m, projectId, CostEnum.ASSETS_AMORTIZATION.getType(), assets, msUserId));
                summaryList.add(SummaryEntity.build(now, m, projectId, CostEnum.STIMULUS_PROD.getType(), power, msUserId));
            });
            rsSummaryDao.insertOrUpdate(summaryList);
            TransactionUtils.commit(DataSourceEnum.RS, transactionStatus);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.RS, transactionStatus);
            throw new OwnerException("清除失败");
        }
        return true;
    }

    @Override
    public PageModel<List<InitMemberModel>> getProjectMemberList(QueryProjectMemberModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            page.setDescs(CollUtil.newArrayList("isMaster", "enumber"));
            page.setAscs(CollUtil.newArrayList("year", "etype"));
        }
        return PageUtils.build(page, initMemberDao.getMemberList(page, query));
    }

    @Override
    public PageModel<List<InitEquipmentModel>> getProjectEquipmentList(QueryProjectEquipmentModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> ascs = new ArrayList<>();
            ascs.add("ie.year");
            ascs.add("ie.ecode");
            page.setAscs(ascs);
        }
        return PageUtils.build(page, projectDao.getProjectEquipmentList(page, query));
    }

    /**
     * 更新打卡使用
     *
     * @param projectId
     * @param companyId
     * @param now
     * @param msUserId
     * @Param yearKeys
     */
    private void updateAttendanceUsed(Integer projectId, Integer companyId, Date now, Integer msUserId, List<YearKeysModel> yearKeys) {
        List<ProjectAttendanceModel> attendanceList = projectRdEmployeeDao.getAttendanceList(projectId, yearKeys);
        if (CollectionUtils.isEmpty(attendanceList)) {
            return;
        }
        List<Integer> deleteUsedIds = new ArrayList<>();
        List<ProjectAttendanceUsedModel> updateUsedList = new ArrayList<>();
        List<ProjectAttendanceUsedModel> usedList = projectRdEmployeeDao.getAttendanceUsed(attendanceList, companyId);
        List<Integer> deleteIds = new ArrayList<>();
        if (!CollectionUtils.isEmpty(usedList)) {
            String keyFormat = "{0}_{1}";
            Map<String, ProjectAttendanceUsedModel> usedMap = usedList.stream().collect(Collectors.toMap(
                    a -> MessageFormat.format(keyFormat, a.getEnumber(), DateUtil.formatDate(a.getWorkDate())), b -> b, (o, n) -> n
            ));
            attendanceList.forEach(item -> {
                deleteIds.add(item.getId());
                ProjectAttendanceUsedModel used = usedMap.get(MessageFormat.format(keyFormat, item.getEnumber(), DateUtil.formatDate(item.getWorkDate())));
                if (null == used) {
                    return;
                }
                List<TimeRangeModel> rangeList = new ArrayList<>();
                if (null != item.getOnTime1()) {
                    rangeList.add(new TimeRangeModel(item.getOnTime1(), item.getOffTime1()));
                }
                if (null != item.getOffTime2()) {
                    rangeList.add(new TimeRangeModel(item.getOnTime2(), item.getOffTime2()));
                }
                if (null != item.getOffTime3()) {
                    rangeList.add(new TimeRangeModel(item.getOnTime3(), item.getOffTime3()));
                }
                String range = ToolUtil.removeRange(used.getTimeRange(), rangeList);
                if (StringUtils.isEmpty(range)) {
                    deleteUsedIds.add(used.getId());
                } else {
                    BigDecimal remainHours = used.getRemainHours().add(item.getWorkHour());
                    if (remainHours.compareTo(used.getWorkHour()) > 0) {
                        remainHours = used.getWorkHour();
                    }
                    used.setRemainHours(remainHours);
                    used.setTimeRange(range);
                    updateUsedList.add(used);
                }
            });
        }
        projectRdEmployeeDao.deleteAttendances(!CollectionUtils.isEmpty(deleteIds) ? deleteIds : attendanceList.stream().map(ProjectAttendanceModel::getId).collect(Collectors.toList()));
        if (!CollectionUtils.isEmpty(deleteUsedIds)) {
            projectRdEmployeeDao.deleteAttendanceUsed(deleteUsedIds);
        }
        if (!CollectionUtils.isEmpty(updateUsedList)) {
            for (List<ProjectAttendanceUsedModel> updateList : ToolUtil.subList(updateUsedList, Constant.MAX_INSERT_OR_UPDATE_COUNT)) {
                projectRdEmployeeDao.updateAttendanceUsed(updateList, now, msUserId);
            }
        }
    }

    /**
     * 更新设备工时使用
     *
     * @param projectId
     * @param companyId
     * @param now
     * @param msUserId
     */
    private void updateEquipmentUsed(Integer projectId, Integer companyId, Date now, Integer msUserId, List<YearKeysModel> yearKeys) {
        List<ProjectEquipmentModel> list = projectRdEquipmentDao.getAttList(projectId, yearKeys);
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        List<Integer> deleteUsedIds = new ArrayList<>();
        List<Integer> deleteIds = new ArrayList<>();
        List<ProjectEquipmentUsedModel> updateUsedList = new ArrayList<>();
        List<ProjectEquipmentUsedModel> usedList = projectRdEquipmentDao.getAttUsedList(list, companyId);
        if (!CollectionUtils.isEmpty(usedList)) {
            String keyFormat = "{0}_{1}";
            Map<String, ProjectEquipmentUsedModel> usedMap = usedList.stream().collect(Collectors.toMap(
                    a -> MessageFormat.format(keyFormat, a.getEcode(), DateUtil.format(a.getMonth(),
                            com.yskc.common.utils.DateUtil.DEFAULT_YYMM_FORMAT)), b -> b, (o, n) -> n
            ));
            list.forEach(item -> {
                deleteIds.add(item.getId());
                ProjectEquipmentUsedModel used = usedMap.get(MessageFormat.format(keyFormat, item.getEcode(),
                        DateUtil.format(item.getMonth(), com.yskc.common.utils.DateUtil.DEFAULT_YYMM_FORMAT)));
                if (null == used) {
                    return;
                }
                BigDecimal temp = used.getRemainHours().add(item.getWorkHours());
                if (temp.compareTo(used.getWorkHours()) >= 0) {
                    deleteUsedIds.add(used.getId());
                } else {
                    String equData = ToolUtil.subAttData(used.getUsedEquData(), item.getEquData());
                    used.setUsedEquData(equData);
                    updateUsedList.add(used);
                }
            });
        }
        projectRdEquipmentDao.deleteEquData(!CollectionUtils.isEmpty(deleteIds) ? deleteIds : list.stream().map(ProjectEquipmentModel::getId).collect(Collectors.toList()));
        if (!CollectionUtils.isEmpty(deleteUsedIds)) {
            projectRdEquipmentDao.deleteEquDataUsed(deleteUsedIds);
        }
        if (!CollectionUtils.isEmpty(updateUsedList)) {
            for (List<ProjectEquipmentUsedModel> updateList : ToolUtil.subList(updateUsedList, Constant.MAX_INSERT_OR_UPDATE_COUNT)) {
                projectRdEquipmentDao.updateUsed(updateList, now, msUserId);
            }
        }
    }

    private Map<RsRdTypeTableEnum, List<BaseRemainModel>> buildRdTypeRemainMap(Integer projectId, Set<RsRdTypeTableEnum> types) {
        Map<RsRdTypeTableEnum, List<BaseRemainModel>> result = new HashMap<>();
        List<BaseRemainModel> list;
        for (RsRdTypeTableEnum rdTypeTable : types) {
            switch (rdTypeTable) {
                case MATERIAL:
                    list = rsSummaryDao.getMaterialUsedAndRemain(projectId);
                    break;
                case ENERGY:
                    list = rsSummaryDao.getEnergyFeeAndRemain(projectId);
                    break;
                case DESIGN:
                    list = rsSummaryDao.getDesignFeeAndRemain(projectId);
                    break;
                case OTHER:
                    list = rsSummaryDao.getOtherFeeAndRemain(projectId);
                    break;
                default:
                    list = null;
                    break;
            }
            if (!CollectionUtils.isEmpty(list)) {
                result.put(rdTypeTable, buildRemainList(list));
            }
        }
        return result;
    }

    private List<BaseRemainModel> buildRemainList(List<BaseRemainModel> usedAndRemainList) {
        if (CollectionUtils.isEmpty(usedAndRemainList)) {
            return null;
        }
        Map<Integer, BaseRemainModel> remainMap = new HashMap<>();
        usedAndRemainList.forEach(item -> {
            Integer id = item.getId();
            if (!remainMap.containsKey(id)) {
                remainMap.put(id, item);
            }
            remainMap.get(id).addRemain(item.getRdUsed());
        });
        return new ArrayList<>(remainMap.values());
    }

    /**
     * 构建涉及到的表枚举Set
     *
     * @param rdTypes
     * @return
     */
    private Map<RsRdTypeTableEnum, Boolean> buildRdTypeEnumMap(List<Integer> rdTypes) {
        Map<RsRdTypeTableEnum, Boolean> result = new HashMap<>();
        for (Integer rdType : rdTypes) {
            switch (CostEnum.getCostEnum(rdType)) {
                case SALARY:
                case INSURANCE:
                    result.put(RsRdTypeTableEnum.EMPLOYEE, true);
                    break;
                case PROD:
                case LAB:
                case ASSETS_AMORTIZATION:
                case STIMULUS_PROD:
                    result.put(RsRdTypeTableEnum.EQUIPMENT, true);
                    break;
                case MATERIAL:
                case TRIAL_MATERIAL:
                case REPAIR_MATERIAL:
                case PAPER_MATERIAL:
                case PAPER_TRIAL:
                case IRON_MATERIAL:
                case IRON_TRIAL:
                    result.put(RsRdTypeTableEnum.MATERIAL, true);
                    break;
                case TRIAL_STIMULUS:
                case STIMULUS:
                case FUEL:
                case IRON_FUEL:
                case IRON_STIMULUS:
                    result.put(RsRdTypeTableEnum.ENERGY, true);
                    break;
                case DESIGN:
                    result.put(RsRdTypeTableEnum.DESIGN, true);
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
                    result.put(RsRdTypeTableEnum.OTHER, true);
                    break;
                default:
                    break;
            }
        }
        return result;
    }

    @Override
    public List<RsProjectListModel> getAllProject(Integer companyId, Integer year, Integer userId, Integer auditCount) {
        List<RsProjectListModel> projects = projectDao.getAllProject(companyId, year);
        if (auditCount > 0 && !CollectionUtils.isEmpty(projects)) {
            //获取每个项目的过程文件数 已审核/已提交
            List<Integer> projectIds = projects.stream().map(RsProjectListModel::getId).collect(Collectors.toList());
            List<RsProjectListModel> rdDocCnt;
            List<AuditDocFileModel> auditDocFile;
            List<AuditDocFileModel> submitDocs;
            List<AuditDocFileModel> passDocs;
            List<AuditDocFileModel> totalDocNum;

            if (auditCount == 1) {
                rdDocCnt = projectDocFileDao.getDocCnt(projectIds, null);
                auditDocFile = flowInstanceDocFileDao.countAuditDocNum(projectIds, userId, null);
                submitDocs = flowInstanceDocFileDao.getDocNum(projectIds, Arrays.asList(0, 1), null);
                passDocs = flowInstanceDocFileDao.getDocNum(projectIds, Arrays.asList(1), null);
                totalDocNum = projectDocFileDao.getDocNum(projectIds, null);
            } else {
                List<Integer> docFileIds = projectDocFileDao.getBackupDataFiles(projectIds);
//                List<Integer>  = new ArrayList<>();
//                if (!CollectionUtils.isEmpty(docFiles)) {
//                    docFileIds = docFiles.stream().map(e -> e.getId()).distinct().collect(Collectors.toList());
//                }
                // TODO: 22/05/21 zdf 这上下两段写的什么玩意，待优化；
                rdDocCnt = projectDocFileDao.getDocCnt(projectIds, auditCount);
                auditDocFile = flowInstanceDocFileDao.countAuditDocNum(projectIds, userId, docFileIds);
                submitDocs = flowInstanceDocFileDao.getDocNum(projectIds, Arrays.asList(0, 1), docFileIds);
                passDocs = flowInstanceDocFileDao.getDocNum(projectIds, Arrays.asList(1), docFileIds);
                totalDocNum = projectDocFileDao.getDocNum(projectIds, auditCount);
            }
            Map<Integer, RsProjectListModel> projectDocCntMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(rdDocCnt)) {
                projectDocCntMap = rdDocCnt.stream().collect(Collectors.toMap(
                        RsProjectListModel::getId, b -> b, (o, n) -> n
                ));
            }
            //获取当前用户可审核文档数
            Map<Integer, Integer> docMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(auditDocFile)) {
                docMap = auditDocFile.stream().collect(Collectors.toMap(e -> e.getRsProjectId(), e -> e.getAuditDocNum()));
            }
            //获取已提交过程文档数
            Map<Integer, Integer> sumbitMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(submitDocs)) {
                sumbitMap = submitDocs.stream().collect(Collectors.toMap(e -> e.getRsProjectId(), e -> e.getAuditDocNum()));
            }
            //获取已通过的过程文档数
            Map<Integer, Integer> pasMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(passDocs)) {
                pasMap = passDocs.stream().collect(Collectors.toMap(e -> e.getRsProjectId(), e -> e.getAuditDocNum()));
            }
            //获取项目过程文档总数
            Map<Integer, Integer> totalDocMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(totalDocNum)) {
                totalDocMap = totalDocNum.stream().collect(Collectors.toMap(e -> e.getRsProjectId(), e -> e.getAuditDocNum()));
            }
            for (RsProjectListModel project : projects) {
                int fileNumber = 0, currentFileNum = 0;
                RsProjectListModel cnt = projectDocCntMap.get(project.getId());
                if (cnt != null) {
                    fileNumber = cnt.getFileNumber();
                    currentFileNum = cnt.getCurrentFileNum();
                }
                project.setFileNumber(fileNumber);
                project.setCurrentFileNum(currentFileNum);
                if (docMap.containsKey(project.getId())) {
                    project.setAuditDocNum(docMap.get(project.getId()));
                }
                if (sumbitMap.containsKey(project.getId())) {
                    project.setSubmitNum(sumbitMap.get(project.getId()));
                }
                if (pasMap.containsKey(project.getId())) {
                    project.setPassNum(pasMap.get(project.getId()));
                }
                if (totalDocMap.containsKey(project.getId())) {
                    project.setTotalDocNum(totalDocMap.get(project.getId()));
                }
            }
        }
        return projects;
    }

    public Boolean setBudgetStatus(RsProjectStatusEnum statusEnum, ProjectStatusModel projectStatus, UserInfo
            userInfo) {
        BudgetStatus budgetStatus = budgetStatusDao.getExist(projectStatus.getProjectId());
        Date now = new Date();
        Integer msUserId = userInfo.getId();
        if (budgetStatus != null) {
            budgetStatus.setStatus(statusEnum.getStatus());
            budgetStatus.setLastUpdateTime(now);
            budgetStatus.setMsLastUpdatorId(msUserId);
            return budgetStatusDao.updateById(budgetStatus) > 0;
        }
        budgetStatus = BudgetStatus.build(projectStatus, msUserId, now, statusEnum.getStatus());
        return budgetStatusDao.insert(budgetStatus) > 0;
    }

    @Override
    public RsCompanyEntity getCompanyById(Integer companyId) {
        return rsCompanyDao.selectById(companyId);
    }

    @Override
    public PageModel<List<InitMemberModel>> getInitmemberList(QueryProjectInitMemberModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            page.setDescs(CollUtil.newArrayList("isMaster,enumber"));
            page.setAscs(CollUtil.newArrayList("etype"));
        }
        ProjectEntity project = projectDao.selectById(query.getProjectId());
        RsCompanyEntity company = rsCompanyDao.selectById(project.getCompanyId());
        query.setYear(project.getBeginYear());
        String fileName = MessageFormat.format("org_{0,number,#}_{1,number,#}.png", company.getId(), project.getBeginYear());
        String tempPath = MessageFormat.format("{0}/static/images/{1,number,#}/org", msConfig.getUploadConfig().getResourcePath(), company.getId());
        String orgUrl = null;
        File file = new File(tempPath + "/" + fileName);
        if (file.exists()) {
            orgUrl = msConfig.getExpertConfig().getDomainName() + MessageFormat.format("/static/images/{0,number,#}/org/{1}", company.getId(), fileName);
        }
        return PageUtils.buildPageResult(page, initMemberDao.getList(page, company.getId(), query), orgUrl);
    }

    @Override
    public List<Map<String, Object>> getProjectSummary(Integer projectId, Integer year) {
        ProjectEntity projectEntity = projectDao.selectById(projectId);
        RsCompanyEntity company = rsCompanyDao.selectById(projectEntity.getCompanyId());
        Date begin = com.yskc.common.utils.DateUtil.getYearFirstDay(year);
        Date end = com.yskc.common.utils.DateUtil.getYearLastDay(year);
        List<Map<String, Object>> list = new ArrayList<>();
        List<DataFundsModel> dataFunds = summaryDao.getDataFunds(company.getId(), begin, end, projectId);
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
    public List<SysDocumentEntity> getReportByProject(Integer projectId) {
        return projectDao.getReportByProject(projectId);
    }

    @Override
    public ProjectEntity getProject(Integer projectId) {
        return projectDao.selectById(projectId);
    }

    @Override
    public ProjectListModel getProjectInfo(Integer projectId) {
        return projectDao.getProjectInfo(projectId);
    }

    @Override
    public List<RsProjectListModel> getAllProject(Integer companyId, Integer year) {
        List<RsProjectListModel> listModels = rsProjectDao.getAllProject(companyId, year);
        return listModels;
    }
}
