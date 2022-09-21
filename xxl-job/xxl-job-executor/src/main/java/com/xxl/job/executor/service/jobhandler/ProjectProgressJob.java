package com.xxl.job.executor.service.jobhandler;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.executor.core.config.Constant;
import com.xxl.job.executor.dao.ms.*;
import com.xxl.job.executor.dao.rs.*;
import com.xxl.job.executor.entity.ms.*;
import com.xxl.job.executor.entity.rs.RdDeptEntity;
import com.xxl.job.executor.enums.RsStageTableEnum;
import com.xxl.job.executor.models.ProjectInfo.EmployeeNumModel;
import com.xxl.job.executor.models.ProjectInfo.ProjectCustomerModel;
import com.xxl.job.executor.models.ProjectInfo.ProjectDataModel;
import com.xxl.job.executor.models.audit.ProjectAuditCntModel;
import com.xxl.job.executor.models.projectsummary.BaseSummaryModel;
import com.xxl.job.executor.models.projectsummary.CostSummaryModel;
import com.xxl.job.executor.models.projectsummary.FeeInfoModel;
import com.xxl.job.executor.utils.ListUtils;
import com.xxl.job.executor.utils.ToolUtils;
import com.xxl.job.executor.utils.TransactionUtils;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.utils.DateUtil;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.service.jobhandler
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-13 18:28
 * @Description: 项目进度job
 */
@Component
public class ProjectProgressJob {

    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private ProjectProgressDetailDao projectProgressDetailDao;
    @Autowired
    private ProjectDao msProjectDao;
    @Autowired
    private ReportDao reportDao;
    @Autowired
    private ProjectSummaryDataDao projectSummaryDataDao;
    @Autowired
    private RdDeptDao rdDeptDao;
    @Autowired
    private ProjectTimelineDao projectTimelineDao;
    @Autowired
    private SummaryDao summaryDao;
    @Autowired
    private RdEmployeeDao rdEmployeeDao;
    @Autowired
    private ProjectAuditDocFileDao projectAuditDocFileDao;
    @Autowired
    private ProjectAuditDao projectAuditDao;
    @Autowired
    private ProjectAuditSummaryDao projectAuditSummaryDao;
    @Autowired
    private CompanyRdSummaryDao companyRdSummaryDao;
    @Autowired
    private PatentPlanDao patentPlanDao;
    @Autowired
    private CompanyRdFundsDao companyRdFundsDao;

    @XxlJob("projectSummaryDataJob")
    public ReturnT<String> projectSummaryDataJob(String param) {
        Date now = new Date();
        Date beforeFive = ToolUtils.getBeforeTime(now, param, -30);
        List<BaseSummaryModel> summaryDatas = projectProgressDetailDao.getAllCompany(beforeFive);
        if (CollectionUtils.isEmpty(summaryDatas)) {
            XxlJobLogger.log("无最新操作，退出。");
            return ReturnT.SUCCESS;
        }
        Map<Integer, Set<Integer>> yearCompanyMap = new LinkedHashMap<>();
        Map<Integer, Set<Integer>> yearBranchMap = new LinkedHashMap<>();
        Map<String, ProjectSummaryData> dataMap = new HashMap<>();
        String format = "{0}_{1}";
        summaryDatas.forEach(item -> {
            Integer companyId = item.getCompanyId();
            Integer year = item.getYear();
            ToolUtils.putAndAddSet(yearCompanyMap, year, companyId);
            if (null != item.getBranchId()) {
                ToolUtils.putAndAddSet(yearBranchMap, year, item.getBranchId());
            }
            dataMap.put(MessageFormat.format(format, companyId, year),
                    ProjectSummaryData.build(year, item.getCustomerId(), now, companyId));
        });
        List<CompanyRdFunds> fundsAndRevenueList = new ArrayList<>();
        loadSummaryData(yearCompanyMap, format, summaryDatas, fundsAndRevenueList, dataMap);
        List<ProjectSummaryData> insertOrUpdate = new ArrayList<>(dataMap.values());
        List<List<ProjectSummaryData>> datas = ListUtils.subList(insertOrUpdate, Constant.MAX_INSERT_OR_UPDATE);
        List<List<CompanyRdFunds>> fundsList = ListUtils.subList(fundsAndRevenueList, Constant.MAX_INSERT_OR_UPDATE);
        TransactionStatus msTransactionStatus = null;
        try {
            msTransactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            for (List<ProjectSummaryData> summaries : datas) {
                projectSummaryDataDao.insertOrUpdate(summaries);
            }
            for (List<CompanyRdFunds> list : fundsList) {
                companyRdFundsDao.insertOrUpdate(list, now);
            }
            List<ProjectSummaryData> districtData = new ArrayList<>();
            List<CompanyRdFunds> districtFunds = new ArrayList<>();
            Set<Integer> branchIds;
            for (Integer year : yearBranchMap.keySet()) {
                branchIds = yearBranchMap.get(year);
                districtData.addAll(projectSummaryDataDao.getDistrictData(year, branchIds));
                districtFunds.addAll(companyRdFundsDao.getDistrictFunds(year, branchIds));
            }
            List<List<ProjectSummaryData>> districtDataList = ListUtils.subList(districtData, Constant.MAX_INSERT_OR_UPDATE);
            for (List<ProjectSummaryData> list : districtDataList) {
                projectSummaryDataDao.insertOrUpdateDistrict(list, now);
            }
            List<List<CompanyRdFunds>> districtFundsList = ListUtils.subList(districtFunds, Constant.MAX_INSERT_OR_UPDATE);
            for (List<CompanyRdFunds> list : districtFundsList) {
                companyRdFundsDao.insertOrUpdateDistrict(list, now);
            }
            XxlJobLogger.log(MessageFormat.format("当前保存分公司统计数：{0},。保存分公司研发费统计数：{1}",
                    districtData.size(), districtFunds.size()));
            TransactionUtils.commit(DataSourceEnum.MS, msTransactionStatus);
        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.MS, msTransactionStatus);
            XxlJobLogger.log("保存数据失败!");
            XxlJobLogger.log(e);
            return ReturnT.FAIL;
        }
        TransactionStatus rsTransactionStatus = null;
        try {
            rsTransactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
            for (List<ProjectSummaryData> summaries : datas) {
                companyRdSummaryDao.insertOrUpdate(summaries);
            }
            for (List<CompanyRdFunds> list : fundsList) {
                companyRdSummaryDao.insertOrUpdateFund(list, now);
            }
            TransactionUtils.commit(DataSourceEnum.RS, rsTransactionStatus);
        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.RS, rsTransactionStatus);
            XxlJobLogger.log("保存数据失败!");
            XxlJobLogger.log(e);
            return ReturnT.FAIL;
        }
        XxlJobLogger.log(MessageFormat.format("当前保存公司记录数：{0},年份数:{1}。保存研发费记录数：{2}",
                insertOrUpdate.size(), yearCompanyMap.size(), fundsAndRevenueList.size()));
        return ReturnT.SUCCESS;
    }

    @XxlJob("projectProgressJob")
    public ReturnT<String> projectProgressJob(String param) {
        List<ProjectProgressDetail> data = new ArrayList<>();
        Date now = new Date();
        Date beforeFive = ToolUtils.getBeforeTime(now, param, -10);
        List<ProjectProgressDetail> companyData = null;
        List<ProjectProgressDetail> userData;
        //List<Integer> companyIds = msProjectDao.getCompanyIds();
        for (RsStageTableEnum table : RsStageTableEnum.getTables()) {
            String tableName = table.getTableName();
            if ("p_report".equals(tableName)) {
                companyData = reportDao.getReportCnt(beforeFive);
                if (!CollectionUtils.isEmpty(companyData)) {
                    data.addAll(companyData);
                }
                continue;
            } else if ("p_summary".equals(tableName)) {
                companyData = reportDao.getRdFunds(beforeFive, table.getStage());
                if (!CollectionUtils.isEmpty(companyData)) {
                    data.addAll(companyData);
                }
                continue;
            } else if ("patent_plan".equals(tableName)) {
                companyData = patentPlanDao.getPatentPlan(beforeFive, table.getStage());
                if (!CollectionUtils.isEmpty(companyData)) {
                    data.addAll(companyData);
                }
                continue;
            } else if ("employeeOpenid".equals(tableName)) {
                companyData = reportDao.getEmployeeOpenid(beforeFive, table.getStage());
                if (!CollectionUtils.isEmpty(companyData)) {
                    data.addAll(companyData);
                }
                continue;
            } else if ("p_docFile_data".equals(tableName)) {
                companyData = reportDao.getDocFileData(beforeFive, table.getStage());
                if (!CollectionUtils.isEmpty(companyData)) {
                    data.addAll(companyData);
                }
                continue;
            }
            userData = reportDao.getLastOperationUser(tableName, table.getStage(), table.isHasYear(), table.isSelfYear(), beforeFive);
            if (!CollectionUtils.isEmpty(userData)) {
                data.addAll(userData);
            }
        }
        Set<Integer> saveCompanyIds = new HashSet<>();
        List<ProjectProgressDetail> insertOrUpdate = buildCompanyYear(data, now, saveCompanyIds);
        if (CollectionUtils.isEmpty(insertOrUpdate)) {
            XxlJobLogger.log("无任何最新操作。");
            return ReturnT.SUCCESS;
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            List<List<ProjectProgressDetail>> subList = ListUtils.subList(insertOrUpdate, Constant.MAX_INSERT_OR_UPDATE);
            for (List<ProjectProgressDetail> details : subList) {
                projectProgressDetailDao.insertOrUpdate(details);
            }
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
            XxlJobLogger.log(MessageFormat.format("当前保存数:{0},companyIds:{1}", insertOrUpdate.size(), saveCompanyIds));
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            XxlJobLogger.log("保存数据失败!");
            XxlJobLogger.log(e);
            return ReturnT.FAIL;
        }
    }

    @XxlJob("ProjectServeJob")
    public ReturnT<String> ProjectServeJob(String param) {
        Date now = new Date();
        Date beforeFive = ToolUtils.getBeforeTime(now, param, -5);
        List<ProjectCustomerModel> companyModels = projectDao.queryCompany();
        if (CollectionUtils.isEmpty(companyModels)) {
            return ReturnT.SUCCESS;
        }
        List<ProjectTimelineEntity> insertList = new ArrayList<>();
        List<ProjectTimelineEntity> updateList = new ArrayList<>();
        //研发架构
        Map<String, List<ProjectTimelineEntity>> orgMap = getRdArchitecture(companyModels, beforeFive, now);
        if (orgMap.containsKey("insert")) {
            insertList.addAll(orgMap.get("insert"));
        }
        if (orgMap.containsKey("update")) {
            updateList.addAll(orgMap.get("update"));
        }
        //研发人员
        Map<String, List<ProjectTimelineEntity>> rdEmployeeMap = countRdEmployee(companyModels, beforeFive, now);
        if (rdEmployeeMap.containsKey("insert")) {
            insertList.addAll(rdEmployeeMap.get("insert"));
        }
        if (rdEmployeeMap.containsKey("update")) {
            updateList.addAll(rdEmployeeMap.get("update"));
        }
        //rdtitle
        Map<String, List<ProjectTimelineEntity>> rdTitleMap = getRdTitle(companyModels, beforeFive, now);
        if (rdTitleMap.containsKey("insert")) {
            insertList.addAll(rdTitleMap.get("insert"));
        }
        if (rdTitleMap.containsKey("update")) {
            updateList.addAll(rdTitleMap.get("update"));
        }
        //过程文档数
        Map<String, List<ProjectTimelineEntity>> fileMap = countDocFileNum(companyModels, beforeFive, now);
        if (fileMap.containsKey("insert")) {
            insertList.addAll(fileMap.get("insert"));
        }
        if (fileMap.containsKey("update")) {
            updateList.addAll(fileMap.get("update"));
        }
        //归集费用
        Map<String, List<ProjectTimelineEntity>> summaryMap = getTotalSummary(companyModels, beforeFive, now);
        if (summaryMap.containsKey("insert")) {
            insertList.addAll(summaryMap.get("insert"));
        }
        if (summaryMap.containsKey("update")) {
            updateList.addAll(summaryMap.get("update"));
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            Integer insertNum = 0;
            Integer updateNum = 0;
            if (!CollectionUtils.isEmpty(insertList)) {
                for(List<ProjectTimelineEntity> insert : ListUtils.subList(insertList,Constant.MAX_INSERT_OR_UPDATE)){
                    insertNum += projectTimelineDao.inertList(insert);
                }
            }
            if (!CollectionUtils.isEmpty(updateList)) {
                for(List<ProjectTimelineEntity> update : ListUtils.subList(updateList,Constant.MAX_INSERT_OR_UPDATE)){
                    updateNum += projectTimelineDao.updateList(update);
                }
            }
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
            XxlJobLogger.log(MessageFormat.format("成功插入:{0}条,更新:{1}条", insertNum, updateNum));
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            XxlJobLogger.log("保存数据失败!");
            XxlJobLogger.log(e);
            return ReturnT.FAIL;
        }
    }

    @XxlJob("auditSummaryJob")
    public ReturnT<String> auditSummaryJob(String param) {
        Date now = new Date();
        Date lastTime = ToolUtils.getBeforeTime(now, param, -5);
        Map<Integer, ProjectAuditCntModel> cntMap = getAuditCnt(lastTime);
        Map<Integer, ProjectAuditCntModel> docCntMap = getDocAuditCnt(lastTime);
        Set<Integer> projectIds = new HashSet<>();
        projectIds.addAll(cntMap.keySet());
        projectIds.addAll(docCntMap.keySet());
        if (CollectionUtils.isEmpty(projectIds)) {
            XxlJobLogger.log("当前无可保存数据！");
            return ReturnT.SUCCESS;
        }
        List<ProjectAuditSummary> auditSummaries = new ArrayList<>();
        List<ProjectAuditSummary> docAuditSummaries = new ArrayList<>();
        cntMap.values().forEach(item -> auditSummaries.add(ProjectAuditSummary.buildCnt(item.getProjectId(), item.getDoneCnt(), item.getOngoingCnt(), now)));
        docCntMap.values().forEach(item -> docAuditSummaries.add(ProjectAuditSummary.buildDocCnt(item.getProjectId(), item.getDoneCnt(), item.getOngoingCnt(), now)));
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            if (!CollectionUtils.isEmpty(auditSummaries)) {
                for (List<ProjectAuditSummary> insertOrUpdate : ListUtils.subList(auditSummaries, Constant.MAX_INSERT_OR_UPDATE)) {
                    projectAuditSummaryDao.insertOrUpdate(insertOrUpdate, false);
                }
            }
            if (!CollectionUtils.isEmpty(docAuditSummaries)) {
                for (List<ProjectAuditSummary> insertOrUpdate : ListUtils.subList(docAuditSummaries, Constant.MAX_INSERT_OR_UPDATE)) {
                    projectAuditSummaryDao.insertOrUpdate(insertOrUpdate, true);
                }
            }
            projectAuditSummaryDao.updateTotal(projectIds, now);
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            XxlJobLogger.log("保存数据失败!");
            XxlJobLogger.log(e);
            return ReturnT.FAIL;
        }
    }

    private Map<Integer, ProjectAuditCntModel> getAuditCnt(Date lastTime) {
        Map<Integer, ProjectAuditCntModel> result = new HashMap<>();
        List<Integer> sourceProjectIds = projectAuditDao.getLastProjectIds(lastTime);
        if (CollectionUtils.isEmpty(sourceProjectIds)) {
            return result;
        }
        List<ProjectAuditCntModel> projectAuditCnts = projectAuditDao.getAuditCnt(sourceProjectIds);
        if (CollectionUtils.isEmpty(projectAuditCnts)) {
            return result;
        }
        projectAuditCnts.forEach(item -> {
            result.put(item.getProjectId(), item);
        });
        return result;
    }


    private Map<Integer, ProjectAuditCntModel> getDocAuditCnt(Date lastTime) {
        Map<Integer, ProjectAuditCntModel> result = new HashMap<>();
        List<Integer> projectIds = projectAuditDocFileDao.getLastProjectIds(lastTime);
        if (CollectionUtils.isEmpty(projectIds)) {
            return result;
        }
        List<ProjectAuditCntModel> docAuditCnt = projectAuditDocFileDao.getDocAuditCnt(projectIds);
        if (CollectionUtils.isEmpty(docAuditCnt)) {
            return result;
        }
        List<ProjectCustomerModel> list = new ArrayList<>();
        docAuditCnt.forEach(item -> list.add(new ProjectCustomerModel(item.getCompanyId(), item.getYear())));
        List<ProjectCustomerModel> projects = projectDao.getCompanyIdYear(list);
        if (CollectionUtils.isEmpty(projectIds)) {
            return result;
        }
        String keyFormat = "{0}_{1}";
        Map<String, Integer> projectMap = new HashMap<>();
        projects.forEach(item -> projectMap.put(MessageFormat.format(keyFormat, item.getCompanyId(), item.getYear()), item.getId()));
        docAuditCnt.forEach(item -> {
            Integer projectId = projectMap.get(MessageFormat.format(keyFormat, item.getCompanyId(), item.getYear()));
            if (null != projectId) {
                item.setProjectId(projectId);
                result.put(projectId, item);
            }
        });
        return result;
    }

    private List<ProjectProgressDetail> buildCompanyYear(List<ProjectProgressDetail> data,
                                                         Date now, Set<Integer> saveCompanyIds) {
        if (CollectionUtils.isEmpty(data)) {
            return null;
        }
        Map<Integer, Set<Integer>> companyYearMap = new HashMap<>();
        Integer reportStage = RsStageTableEnum.P_REPORT.getStage();
        data.forEach(item -> {
            if (item.getStage() >= reportStage) {
                Integer key = item.getCompanyId();
                if (!companyYearMap.containsKey(key)) {
                    companyYearMap.put(key, new HashSet<>());
                }
                companyYearMap.get(key).add(item.getYear());
            }
        });
        List<ProjectProgressDetail> companyYearList = projectProgressDetailDao.getCompanyYear();
        companyYearList.forEach(item -> {
            Integer key = item.getCompanyId();
            if (!companyYearMap.containsKey(key)) {
                companyYearMap.put(key, new HashSet<>());
            }
            companyYearMap.get(key).add(item.getYear());
        });
        List<ProjectProgressDetail> result = new ArrayList<>();
        data.forEach(item -> {
            Integer companyId = item.getCompanyId();
            saveCompanyIds.add(companyId);
            if (item.getStage() < reportStage) {
                if (companyYearMap.containsKey(companyId)) {
                    for (Integer year : companyYearMap.get(companyId)) {
                        ProjectProgressDetail detail = new ProjectProgressDetail();
                        detail.setUpdateTime(now);
                        detail.setYear(year);
                        detail.setStage(item.getStage());
                        detail.setUserId(item.getUserId());
                        detail.setCompanyId(companyId);
                        detail.setOperationTime(item.getOperationTime());
                        detail.setResult(item.getResult());
                        result.add(detail);
                    }
                }
            } else {
                item.setUpdateTime(now);
                result.add(item);
            }
        });
        return result;
    }

    private Map<String, List<ProjectTimelineEntity>> getRdArchitecture(List<ProjectCustomerModel> companyModels, Date countTime, Date now) {
        Map<String, List<ProjectTimelineEntity>> data = new HashMap<>();
        Map<String, RdDeptEntity> dataMap = new HashMap<>();//研发架构map
        List<RdDeptEntity> rdDepts = rdDeptDao.getByCompany(companyModels, countTime);
        for (RdDeptEntity entity : rdDepts) {
            String sign = MessageFormat.format("{0}-{1}", entity.getCompanyId(), entity.getYear());
            dataMap.put(sign, entity);
        }
        List<ProjectTimelineEntity> entities = projectTimelineDao.getByProjectId(20);
        Map<Integer, ProjectTimelineEntity> timelineMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(entities)) {
            timelineMap = entities.stream().collect(Collectors.toMap(e -> e.getProjectId(), e -> e, (a, b) -> b));
        }
        List<ProjectTimelineEntity> insertList = new ArrayList<>();
        List<ProjectTimelineEntity> updateList = new ArrayList<>();
        for (ProjectCustomerModel model : companyModels) {
            String str = MessageFormat.format("{0}-{1}", model.getCompanyId(), model.getYear());
            ProjectTimelineEntity timelineEntity;
            if (dataMap.containsKey(str)) {
                //有查询到数据
                RdDeptEntity rdDept = dataMap.get(str);
                if (!timelineMap.containsKey(model.getId())) {
                    //插入
                    timelineEntity = new ProjectTimelineEntity();
                    timelineEntity.setItemType(20);
                    timelineEntity.setProjectId(model.getId());
                    timelineEntity.setCreateTime(now);
                    timelineEntity.setUpdateTime(now);
                    timelineEntity.setResult("有");
                    timelineEntity.setBeginTime(rdDept.getCreateTime());
                    timelineEntity.setEndTime(rdDept.getLastUpdateTime());
                    insertList.add(timelineEntity);
                } else {
                    timelineEntity = timelineMap.get(model.getId());
                    timelineEntity.setBeginTime(rdDept.getCreateTime());
                    timelineEntity.setEndTime(rdDept.getLastUpdateTime());
                    timelineEntity.setUpdateTime(now);
                    timelineEntity.setResult("有");
                    updateList.add(timelineEntity);
                }
            }
        }
        if (!CollectionUtils.isEmpty(insertList)) {
            data.put("insert", insertList);
        }
        if (!CollectionUtils.isEmpty(updateList)) {
            data.put("update", updateList);
        }
        return data;
    }


    private Map<String, List<ProjectTimelineEntity>> countRdEmployee(List<ProjectCustomerModel> companyModels, Date countTime, Date now) {
        Map<String, List<ProjectTimelineEntity>> dataMap = new HashMap<>();
        List<Integer> companyIds = companyModels.stream().map(e -> e.getCompanyId()).collect(Collectors.toList());
        List<ProjectTimelineEntity> entities = projectTimelineDao.getByProjectId(30);
        Map<Integer, ProjectTimelineEntity> timelineMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(entities)) {
            timelineMap = entities.stream().collect(Collectors.toMap(e -> e.getProjectId(), e -> e));
        }
        List<EmployeeNumModel> models = rdEmployeeDao.getRdEmployee(companyIds, countTime);
        Map<String, EmployeeNumModel> numModelMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(models)) {
            for (EmployeeNumModel emodel : models) {
                String sign = MessageFormat.format("{0}-{1}", emodel.getCompanyId(), emodel.getYear());
                numModelMap.put(sign, emodel);
            }
        }
        List<ProjectTimelineEntity> insertList = new ArrayList<>();
        List<ProjectTimelineEntity> updateList = new ArrayList<>();
        for (ProjectCustomerModel model : companyModels) {
            String key = MessageFormat.format("{0}-{1}", model.getCompanyId(), model.getYear());
            if (numModelMap.containsKey(key)) {
                EmployeeNumModel numModel = numModelMap.get(key);
                ProjectTimelineEntity entity;
                if (timelineMap.containsKey(model.getId())) {
                    //更新
                    entity = timelineMap.get(model.getId());
                    entity.setBeginTime(numModel.getBeginTime());
                    entity.setEndTime(numModel.getEndTime());
                    entity.setUpdateTime(now);
                    entity.setResult(String.valueOf(numModel.getNum()));
                    updateList.add(entity);
                } else {
                    entity = new ProjectTimelineEntity();
                    entity.setResult(String.valueOf(numModel.getNum()));
                    entity.setUpdateTime(now);
                    entity.setCreateTime(now);
                    entity.setBeginTime(numModel.getBeginTime());
                    entity.setEndTime(numModel.getEndTime());
                    entity.setProjectId(model.getId());
                    entity.setItemType(30);
                    insertList.add(entity);
                }
            }
        }
        if (!CollectionUtils.isEmpty(insertList)) {
            dataMap.put("insert", insertList);
        }
        if (!CollectionUtils.isEmpty(updateList)) {
            dataMap.put("update", updateList);
        }
        return dataMap;
    }

    private Map<String, List<ProjectTimelineEntity>> getRdTitle(List<ProjectCustomerModel> companyModels, Date countTime, Date date) {
        Map<String, List<ProjectTimelineEntity>> dataMap = new HashMap<>();
        List<ProjectTimelineEntity> entities = projectTimelineDao.getByProjectId(40);
        Map<Integer, ProjectTimelineEntity> timelineMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(entities)) {
            timelineMap = entities.stream().collect(Collectors.toMap(e -> e.getProjectId(), e -> e));
        }
        List<ProjectDataModel> rdTitles = rdEmployeeDao.getRdTitle(companyModels, countTime);
        Map<String, ProjectDataModel> titleMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(rdTitles)) {
            for (ProjectDataModel tmodel : rdTitles) {
                String sign = MessageFormat.format("{0}-{1}", tmodel.getCompanyId(), tmodel.getYear());
                titleMap.put(sign, tmodel);
            }
        }
        List<ProjectTimelineEntity> insertList = new ArrayList<>();
        List<ProjectTimelineEntity> updateList = new ArrayList<>();
        for (ProjectCustomerModel model : companyModels) {
            String key = MessageFormat.format("{0}-{1}", model.getCompanyId(), model.getYear());
            if (titleMap.containsKey(key)) {
                ProjectDataModel titleModel = titleMap.get(key);
                ProjectTimelineEntity entity;
                if (timelineMap.containsKey(model.getId())) {
                    //更新
                    entity = timelineMap.get(model.getId());
                    entity.setBeginTime(titleModel.getBeginTime());
                    entity.setEndTime(titleModel.getEndTime());
                    entity.setUpdateTime(date);
                    entity.setResult(titleModel.getRdTitle());
                    updateList.add(entity);
                } else {
                    entity = new ProjectTimelineEntity();
                    entity.setResult(titleModel.getRdTitle());
                    entity.setUpdateTime(date);
                    entity.setCreateTime(date);
                    entity.setBeginTime(titleModel.getBeginTime());
                    entity.setEndTime(titleModel.getEndTime());
                    entity.setProjectId(model.getId());
                    entity.setItemType(40);
                    insertList.add(entity);
                }
            }
        }
        if (!CollectionUtils.isEmpty(insertList)) {
            dataMap.put("insert", insertList);
        }
        if (!CollectionUtils.isEmpty(updateList)) {
            dataMap.put("update", updateList);
        }
        return dataMap;
    }

    private Map<String, List<ProjectTimelineEntity>> getTotalSummary(List<ProjectCustomerModel> companyModels, Date countTime, Date date) {
        Map<String, List<ProjectTimelineEntity>> dataMap = new HashMap<>();
        List<ProjectTimelineEntity> entities = projectTimelineDao.getByProjectId(50);
        Map<Integer, ProjectTimelineEntity> timelineMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(entities)) {
            timelineMap = entities.stream().collect(Collectors.toMap(e -> e.getProjectId(), e -> e));
        }
        List<ProjectDataModel> sumarrys = summaryDao.getRdAmount(companyModels);
        Map<String, ProjectDataModel> titleMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(sumarrys)) {
            for (ProjectDataModel tmodel : sumarrys) {
                String sign = MessageFormat.format("{0}-{1}", tmodel.getCompanyId(), tmodel.getYear());
                titleMap.put(sign, tmodel);
            }
        }
        List<ProjectTimelineEntity> insertList = new ArrayList<>();
        List<ProjectTimelineEntity> updateList = new ArrayList<>();
        for (ProjectCustomerModel model : companyModels) {
            String key = MessageFormat.format("{0}-{1}", model.getCompanyId(), model.getYear());
            if (titleMap.containsKey(key)) {
                ProjectDataModel summaryModel = titleMap.get(key);
                ProjectTimelineEntity entity;
                if (timelineMap.containsKey(model.getId())) {
                    //更新
                    entity = timelineMap.get(model.getId());
                    entity.setBeginTime(summaryModel.getBeginTime());
                    entity.setEndTime(summaryModel.getEndTime());
                    entity.setUpdateTime(date);
                    entity.setResult(summaryModel.getTotalAmount().toString());
                    updateList.add(entity);
                } else {
                    entity = new ProjectTimelineEntity();
                    entity.setResult(summaryModel.getTotalAmount().toString());
                    entity.setUpdateTime(date);
                    entity.setCreateTime(date);
                    entity.setBeginTime(summaryModel.getBeginTime());
                    entity.setEndTime(summaryModel.getEndTime());
                    entity.setProjectId(model.getId());
                    entity.setItemType(50);
                    insertList.add(entity);
                }
            }
        }
        if (!CollectionUtils.isEmpty(insertList)) {
            dataMap.put("insert", insertList);
        }
        if (!CollectionUtils.isEmpty(updateList)) {
            dataMap.put("update", updateList);
        }
        return dataMap;
    }

    private Map<String, List<ProjectTimelineEntity>> countDocFileNum(List<ProjectCustomerModel> companyModels, Date countTime, Date date) {
        Map<String, List<ProjectTimelineEntity>> dataMap = new HashMap<>();
        List<ProjectTimelineEntity> entities = projectTimelineDao.getDocByStage();
        Map<String, ProjectTimelineEntity> timelineMap = new HashMap<>();
        for (ProjectTimelineEntity entity : entities) {
            String stage = MessageFormat.format("{0}-{1}", entity.getItemType(), entity.getProjectId());
            timelineMap.put(stage, entity);
        }
        List<ProjectDataModel> docModel = summaryDao.countDocFile(companyModels);
        Map<String, List<ProjectDataModel>> fileMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(docModel)) {
            for (ProjectDataModel dmodel : docModel) {
                String sign = MessageFormat.format("{0}-{1}", dmodel.getCompanyId(), dmodel.getYear());
                if (!fileMap.containsKey(sign)) {
                    List<ProjectDataModel> list = new ArrayList<>();
                    fileMap.put(sign, list);
                }
                fileMap.get(sign).add(dmodel);
            }
        }
        List<ProjectTimelineEntity> insertList = new ArrayList<>();
        List<ProjectTimelineEntity> updateList = new ArrayList<>();
        for (ProjectCustomerModel model : companyModels) {
            String key = MessageFormat.format("{0}-{1}", model.getCompanyId(), model.getYear());
            if (fileMap.containsKey(key)) {
                List<ProjectDataModel> fileModels = fileMap.get(key);
                for (ProjectDataModel file : fileModels) {
                    Integer stage = Integer.valueOf(file.getStage());
                    String stageKey = MessageFormat.format("{0}-{1}", stage, model.getId());
                    ProjectTimelineEntity entity;
                    if (timelineMap.containsKey(stageKey)) {
                        //更新
                        entity = timelineMap.get(stageKey);
                        entity.setBeginTime(file.getBeginTime());
                        entity.setEndTime(file.getEndTime());
                        entity.setUpdateTime(date);
                        entity.setResult(file.getDocNum().toString());
                        updateList.add(entity);
                    } else {
                        entity = new ProjectTimelineEntity();
                        entity.setResult(file.getDocNum().toString());
                        entity.setUpdateTime(date);
                        entity.setCreateTime(date);
                        entity.setBeginTime(file.getBeginTime());
                        entity.setEndTime(file.getEndTime());
                        entity.setProjectId(model.getId());
                        entity.setItemType(stage);
                        insertList.add(entity);
                    }
                }

            }
        }
        if (!CollectionUtils.isEmpty(insertList)) {
            dataMap.put("insert", insertList);
        }
        if (!CollectionUtils.isEmpty(updateList)) {
            dataMap.put("update", updateList);
        }
        return dataMap;
    }

    private void loadSummaryData(Map<Integer, Set<Integer>> yearCompanyMap, String format,
                                 List<BaseSummaryModel> summaryDatas, List<CompanyRdFunds> fundsAndRevenueList,
                                 Map<String, ProjectSummaryData> dataMap) {
        Date begin, end;
        Set<Integer> companyIds;
        List<FeeInfoModel> costs;
        for (Integer year : yearCompanyMap.keySet()) {
            companyIds = yearCompanyMap.get(year);
            begin = com.yskc.common.utils.DateUtil.getYearFirstDay(year);
            end = com.yskc.common.utils.DateUtil.getYearLastDay(year);
            copyToData(reportDao.getProjectCntAndBudget(year, companyIds), format, dataMap);
            copyToData(reportDao.getProjectDocFileDataCnt(year, companyIds, begin, end), format, dataMap);
            copyToData(reportDao.getEmployeeCnt(year, companyIds), format, dataMap);
            copyToData(reportDao.getEquipmentCnt(year, companyIds), format, dataMap);
            copyToData(reportDao.getPatentCnt(year, companyIds, begin, end), format, dataMap);
            copyToData(reportDao.getLevelFileCnt(year, companyIds, begin, end), format, dataMap);
            copyToData(reportDao.getProposalCnt(year, companyIds), format, dataMap);
            copyToData(reportDao.getAchievementCnt(year, companyIds), format, dataMap);
            copyToData(reportDao.getHighTechIncome(year, companyIds, begin, end), format, dataMap);
            copyToData(reportDao.getHighTechCnt(year, companyIds), format, dataMap);
            copyToData(projectSummaryDataDao.getPatentApplyCnt(year, companyIds), format, dataMap);
            copyToData(companyRdSummaryDao.getBuildCnt(year, companyIds, 6001), format, dataMap);
            loadRdFundsAndRevenue(year, companyIds, begin, end, format, fundsAndRevenueList, dataMap);
            costs = reportDao.getYearCost(year, companyIds);
            if (!CollectionUtils.isEmpty(costs)) {
                Map<String, Map<String, Object>> costMap = new HashMap<>();
                costs.forEach(item -> {
                    String key = MessageFormat.format(format, item.getYear(), item.getCompanyId());
                    if (!costMap.containsKey(key)) {
                        Map<String, Object> tempCostMap = new HashMap<>();
                        tempCostMap.put("year", item.getYear());
                        tempCostMap.put("companyId", item.getCompanyId());
                        costMap.put(key, tempCostMap);
                    }
                    costMap.get(key).put("c" + item.getRdType(), item.getFee());
                });
                List<CostSummaryModel> costList = new ArrayList<>();
                costMap.values().forEach(item -> {
                    costList.add(BeanUtil.mapToBean(item, CostSummaryModel.class, true));
                });
                copyToData(costList, format, dataMap);
            }
        }
        copyToData(reportDao.getAllReportCnt(summaryDatas), format, dataMap);
        copyToData(projectProgressDetailDao.getLastOperation(summaryDatas), format, dataMap);
    }

    /**
     * 装载研发费(同时加载营收)
     *
     * @param year
     * @param companyIds
     * @param fundsMap
     * @param dataMap
     */
    private void loadRdFundsAndRevenue(Integer year, Set<Integer> companyIds, Date begin, Date end, String format,
                                       List<CompanyRdFunds> fundsAndRevenueList, Map<String, ProjectSummaryData> dataMap) {
        Integer companyId;
        Date month;
        BigDecimal fee;
        String fundKey, dataKey, addKey;
        Map<String, Map<String, Object>> feeMap = new HashMap<>();
        List<FeeInfoModel> rdFunds = summaryDao.getRdFunds(year, companyIds, begin, end);
        if (!CollectionUtils.isEmpty(rdFunds)) {
            for (FeeInfoModel item : rdFunds) {
                companyId = item.getCompanyId();
                month = item.getMonth();
                fundKey = MessageFormat.format(format, companyId, DateUtil.format(month, DateUtil.DEFAULT_YYMM_FORMAT));
                Map<String, Object> infoMap;
                if (!feeMap.containsKey(fundKey)) {
                    infoMap = new HashMap<>();
                    infoMap.put("year", year);
                    infoMap.put("companyId", companyId);
                    infoMap.put("month", month);
                    infoMap.put("type", 0);
                    infoMap.put("amount", BigDecimal.ZERO);
                    feeMap.put(fundKey, infoMap);
                }
                infoMap = feeMap.get(fundKey);
                String rdTypeStr = getKRdType(item.getRdType());
                fee = item.getFee();
                infoMap.put(rdTypeStr, ((BigDecimal) feeMap.get(fundKey).getOrDefault(rdTypeStr, BigDecimal.ZERO)).add(fee));
                infoMap.put("amount",((BigDecimal) infoMap.get("amount")).add(fee));
            }
        }
        List<FeeInfoModel> revenues = summaryDao.getRevenues(year, companyIds);
        if (!CollectionUtils.isEmpty(revenues)) {
            for (FeeInfoModel item : revenues) {
                month = item.getMonth();
                companyId = item.getCompanyId();
                fundKey = MessageFormat.format(format, companyId, DateUtil.format(month, DateUtil.DEFAULT_YYMM_FORMAT));
                if (!feeMap.containsKey(fundKey)) {
                    Map<String, Object> infoMap = new HashMap<>();
                    infoMap.put("year", year);
                    infoMap.put("companyId", companyId);
                    infoMap.put("month", month);
                    infoMap.put("type", 0);
                    feeMap.put(fundKey, infoMap);
                }
                feeMap.get(fundKey).put("revenue", item.getFee());
            }
        }
        Map<String, List<CompanyRdFunds>> feeAddMap = new LinkedHashMap<>();
        ProjectSummaryData curData;
        for (Map<String, Object> item : feeMap.values()) {
            CompanyRdFunds fund = BeanUtil.mapToBean(item, CompanyRdFunds.class, true);
            companyId = fund.getCompanyId();
            addKey = MessageFormat.format(format, year, companyId);
            if (!feeAddMap.containsKey(addKey)) {
                for (int i = 0; i < 12; i++) {
                    CompanyRdFunds curAdd = CompanyRdFunds.buildAddData(year, companyId, cn.hutool.core.date.DateUtil.offsetMonth(begin, i));
                    // 统计数同时添加到fundsAndRevenueList 和feeAddMap里。
                    ToolUtils.putAndAddList(feeAddMap, addKey, curAdd);
                    fundsAndRevenueList.add(curAdd);
                }
            }
            dataKey = MessageFormat.format(format, companyId, year);
            curData = dataMap.get(dataKey);
            if (null != curData) {
                curData.addRdFund(fund.getAmount());
                curData.addRevenue(fund.getRevenue());
            }
            fundsAndRevenueList.add(fund);
            feeAddMap.get(addKey).forEach(curAdd -> {
                if (curAdd.getMonth().compareTo(fund.getMonth()) >= 0) {
                    curAdd.addFee(fund);
                }
            });
        }
    }

    /**
     * rdType添加k前缀
     *
     * @param rdType
     * @return
     */
    private String getKRdType(int rdType) {
        rdType = rdType / 100;
        // 为仪器，转为设备统计
        if (rdType == 301) {
            rdType = 300;
            // 为专利摊销，转为其他摊销
        } else if (rdType == 401) {
            rdType = 402;
            //在600及以上的费用，转为其他费用
        } else if (rdType >= 600) {
            rdType = 699;
        }
        return "k" + rdType+"00";
    }

    /**
     * 统计数据copy到data
     *
     * @param list
     * @param format
     * @param dataMap
     */
    private void copyToData(List<? extends BaseSummaryModel> list, String format, Map<String, ProjectSummaryData> dataMap) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        String curKey;
        for (BaseSummaryModel item : list) {
            curKey = MessageFormat.format(format, item.getCompanyId(), item.getYear());
            if (dataMap.containsKey(curKey)) {
                item.copyToData(dataMap.get(curKey));
            }
        }
    }
}
