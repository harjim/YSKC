package com.xxl.job.executor.service.jobhandler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.executor.core.config.Constant;
import com.xxl.job.executor.dao.ms.*;
import com.xxl.job.executor.dao.rs.*;
import com.xxl.job.executor.entity.ms.RsProjectSummary;
import com.xxl.job.executor.entity.rs.*;
import com.xxl.job.executor.models.ProjectInfo.MemberModel;
import com.xxl.job.executor.models.ProjectInfo.ProjectDataModel;
import com.xxl.job.executor.models.ProjectInfo.ProjectModel;
import com.xxl.job.executor.models.innovationmember.InnovationMemberModel;
import com.xxl.job.executor.utils.ToolUtils;
import com.xxl.job.executor.utils.TransactionUtils;
import com.yskc.common.enums.DataSourceEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.service.jobhandler
 * @Author: zhangdingfu
 * @CreateTime: 2021-03-05 16:56
 * @Description: rs汇总job
 */
@Component
public class RsSummaryJob {

    private final String keyFormat = "{0}_{1}";

    @Autowired
    private ProjectInfoSummaryDao projectInfoSummaryDao;
    @Autowired
    private SummaryDao summaryDao;
    @Autowired
    private ProjectSummaryDataDao projectSummaryDataDao;
    @Autowired
    private ReportDao reportDao;
    @Autowired
    private CompanyRdSummaryDao companyRdSummaryDao;
    @Autowired
    private RsProjectSummaryDao rsProjectSummaryDao;
    @Autowired
    private KeypointStatisticDao keypointStatisticDao;
    @Autowired
    private RsProjectDao rsProjectDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private PatentDao patentDao;
    @Autowired
    private InitMemberDao initMemberDao;
    @Autowired
    private PatentPlanDao patentPlanDao;
    @Autowired
    private ProjectAuditDocFileDao projectAuditDocFileDao;
    @Autowired
    private CWorkerDao cWorkerDao;
    @Autowired
    private InnovationMemberDao innovationMemberDao;

    @XxlJob("projectInfoSummaryJob")
    public ReturnT<String> projectInfoSummaryJob(String param) {
        Date now = new Date();
        boolean clear = false;
        // 如果是凌晨3:00~3:10
        if ( DateUtil.hour(now, true) == 3 && DateUtil.minute(now) < 10) {
            // 当前时间在3:00 时，执行一次清空
            clear = true;
            param = "2019-01-01";
        }
        Date before = ToolUtils.getBeforeTime(now, param, -10);
        List<ProjectInfoSummary> allData = new ArrayList<>();

        List<ProjectInfoSummary> materialAmounts = projectInfoSummaryDao.getMaterialInfoSummary(before);
        if (!CollectionUtils.isEmpty(materialAmounts)) {
            allData.addAll(materialAmounts);
        }
        List<ProjectInfoSummary> employeeHours = projectInfoSummaryDao.countStaffHour(before);
        if (!CollectionUtils.isEmpty(employeeHours)) {
            allData.addAll(employeeHours);
        }
        List<ProjectInfoSummary> equipmentHours = projectInfoSummaryDao.countEquipmentHour(before);
        if (!CollectionUtils.isEmpty(equipmentHours)) {
            allData.addAll(equipmentHours);
        }
        List<ProjectInfoSummary> yieldAmount = projectInfoSummaryDao.countYieldAmount(before);
        if (!CollectionUtils.isEmpty(yieldAmount)) {
            allData.addAll(yieldAmount);
        }
        if (CollectionUtils.isEmpty(allData)) {
            XxlJobLogger.log("无最新数据，退出job.");
            return ReturnT.SUCCESS;
        }
        Map<String, ProjectInfoSummary> summaryMap = new HashMap<>();
        Map<Integer, Set<String>> projectUpdateStrMap = new HashMap<>();
        allData.forEach(item -> {
            String key = MessageFormat.format(keyFormat, item.getProjectId(), DateUtil.format(item.getMonth(), "yyyy-MM"));
            if (!summaryMap.containsKey(key)) {
                item.setTime(now);
                summaryMap.put(key, item);
            } else {
                summaryMap.get(key).resetData(item);
            }
            ToolUtils.putAndAddSet(projectUpdateStrMap, item.getProjectId(), item.getNotNullStr());
        });
        // 若clear 为真，则所有数据做为全新数据插入[不引入旧数据覆盖空数据]
        List<ProjectInfoSummary> projectInfos = clear ? null : projectInfoSummaryDao.getByProject(projectUpdateStrMap.keySet());
        if (!CollectionUtils.isEmpty(projectInfos)) {
            projectInfos.forEach(item -> {
                String key = MessageFormat.format(keyFormat, item.getProjectId(), DateUtil.format(item.getMonth(), "yyyy-MM"));
                if (!summaryMap.containsKey(key)) {
                    item.clear(projectUpdateStrMap.get(item.getProjectId()), now);
                    summaryMap.put(key, item);
                } else {
                    summaryMap.get(key).updateData(item);
                }
            });
        }
        List<ProjectInfoSummary> dataList = new ArrayList<>(summaryMap.values());
        List<List<ProjectInfoSummary>> insertOrUpdateList = CollUtil.split(dataList, Constant.MAX_INSERT_OR_UPDATE);
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
            if (clear) {
                XxlJobLogger.log("清空了所有数据，本次操作将全表扫描。");
                projectInfoSummaryDao.truncateTable();
            }
            for (List<ProjectInfoSummary> insertOrUpdate : insertOrUpdateList) {
                projectInfoSummaryDao.insertOrUpdate(insertOrUpdate);
            }
            TransactionUtils.commit(DataSourceEnum.RS, transactionStatus);
        } catch (Exception e) {
            XxlJobLogger.log(e);
            TransactionUtils.rollback(DataSourceEnum.RS, transactionStatus);
            return ReturnT.FAIL;
        }
        XxlJobLogger.log("当次更新数据：" + dataList.size());
        return ReturnT.SUCCESS;
    }

    @XxlJob("syncRsWorkerJob")
    public ReturnT<String> syncRsWorkerJob(String param) {
        Date date = new Date();
        Date lastTime = ToolUtils.getBeforeTime(date, param, -360);
        List<InnovationMemberModel> list = innovationMemberDao.getMessage(lastTime);
        Map<String, CWorker> mapPro = new HashMap<>();
        if (CollectionUtils.isEmpty(list)) {
            XxlJobLogger.log("数据为空！退出");
            return ReturnT.SUCCESS;
        }
//        Map<Integer,List<Integer>> yearComMap = new HashMap<>();
        list.forEach(e -> {
            String key = String.valueOf(e.getCompanyId()) + String.valueOf(e.getYear());
            if (!mapPro.containsKey(key)) {
//                ToolUtils.putAndAddList(yearComMap,e.getYear(),e.getCompanyId());
                mapPro.put(key, new CWorker(e.getCompanyId(), e.getYear(), date, date));
            }
            mapPro.get(key).setYsTechOrYsFina(e.getmType(), e.getRealName());
        });
        List<CWorker> list1 = new ArrayList<>();

        list1.addAll(mapPro.values());

        Boolean success = cWorkerDao.insertCWorker(list1);

        XxlJobLogger.log("当次更新数：" + list1.size());
        if (success) {
            return ReturnT.SUCCESS;
        }
        return ReturnT.FAIL;
    }

    @Deprecated
    @XxlJob("projectBudgetJob")
    public ReturnT<String> projectBudgetJob(String param) {
        XxlJobLogger.log("该job已废弃。");
        return ReturnT.FAIL;
    }

    /**
     * 公司研发相关汇总
     *
     * @param param
     * @return
     */
//    @XxlJob("companyRdSummary")
//    public ReturnT<String> companyRdSummary(String param) {
//        Date now = new Date();
//        List<CompanyRdSummary> data = buildCompanyRdSummaryData(param, now);
//        if (CollectionUtils.isEmpty(data)) {
//            XxlJobLogger.log("无最新操作，退出。");
//            return ReturnT.SUCCESS;
//        }
//        Map<String, CompanyRdSummary> dataMap = new HashMap<>();
//        data.forEach(item -> {
//            String key = MessageFormat.format(keyFormat, item.getCompanyId(), item.getYear());
//            if (!dataMap.containsKey(key)) {
//                item.setCreate(now);
//                dataMap.put(key, item);
//            } else {
//                dataMap.get(key).addData(item);
//            }
//        });
//        List<CompanyRdSummary> existData = companyRdSummaryDao.getExistData(new ArrayList<>(dataMap.values()));
//        if (!CollectionUtils.isEmpty(existData)) {
//            existData.forEach(item -> dataMap.get(MessageFormat.format(keyFormat, item.getCompanyId(), item.getYear())).addData(item));
//        }
//
//        List<CompanyRdSummary> dataList = new ArrayList<>(dataMap.values());
//        TransactionStatus transactionStatus = null;
//        try {
//            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
//            for (List<CompanyRdSummary> curList : CollUtil.split(dataList, Constant.MAX_INSERT_OR_UPDATE)) {
//                companyRdSummaryDao.insertOrUpdate(curList);
//            }
//            TransactionUtils.commit(DataSourceEnum.RS, transactionStatus);
//        } catch (Exception e) {
//            XxlJobLogger.log(e);
//            TransactionUtils.rollback(DataSourceEnum.RS, transactionStatus);
//            return ReturnT.FAIL;
//        }
//        XxlJobLogger.log("当次更新数：" + dataList.size());
//        return ReturnT.SUCCESS;
//    }
    @XxlJob("rsProjectSummaryJob")
    public ReturnT<String> rsProjectSummaryJob(String param) {
        Date date = new Date();
        List<RsProjectSummary> summaryList = buildProjectDetailData(param, date);
        if (CollectionUtils.isEmpty(summaryList)) {
            XxlJobLogger.log("无最新操作，退出。");
            return ReturnT.SUCCESS;
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
            for (List<RsProjectSummary> list : CollUtil.split(summaryList, Constant.MAX_INSERT_OR_UPDATE)) {
                rsProjectSummaryDao.insertOrUpdate(list);
            }
            TransactionUtils.commit(DataSourceEnum.RS, transactionStatus);
        } catch (Exception e) {
            XxlJobLogger.log(e);
            TransactionUtils.rollback(DataSourceEnum.RS, transactionStatus);
            return ReturnT.FAIL;
        }
        XxlJobLogger.log("当次更新数：" + summaryList.size());
        return ReturnT.SUCCESS;
    }

    /**
     * rs登录页显示数据统计
     *
     * @param param
     * @return
     */
    @XxlJob("rsKeypointStatJob")
    public ReturnT<String> rsKeypointStatJob(String param) {
        try {
            KeypointStatisticEntity kStatisticEntity = new KeypointStatisticEntity();
            //集团统计
            kStatisticEntity.setCustomerCnt(customerDao.countCustomer());
            //研发人员统计
            kStatisticEntity.setMemberCnt(initMemberDao.getMemberStatNumber());
            //专利统计
            kStatisticEntity.setPatentCnt(patentDao.getPatentStatNumber() + patentPlanDao.getPatentStatNumber());
            //立项统计
            kStatisticEntity.setRdCnt(rsProjectDao.getProjectStatNumber());

            Date now = new Date();
            kStatisticEntity.setLastUpdateTime(now);
            keypointStatisticDao.updateStatisticData(kStatisticEntity);
            XxlJobLogger.log("当次更新-->集团统计数：" + kStatisticEntity.getCustomerCnt()
                    + ",研发人员统计数：" + kStatisticEntity.getMemberCnt()
                    + ",专利统计数：" + kStatisticEntity.getPatentCnt()
                    + ",立项统计数：" + kStatisticEntity.getRdCnt());
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            XxlJobLogger.log(e);
            return ReturnT.FAIL;
        }
    }

    private List<RsProjectSummary> buildProjectDetailData(String param, Date date) {
        Date before = ToolUtils.getBeforeTime(date, param, -10);
        List<ProjectDataModel> updateProjects = summaryDao.getByProject(before);
        List<RsProjectSummary> details = new ArrayList<>();
        if (!CollectionUtils.isEmpty(updateProjects)) {
            List<Integer> projectIds = updateProjects.stream().map(e -> e.getProjectId()).collect(Collectors.toList());
            List<ProjectModel> projectModels = summaryDao.getProjects(projectIds);
            for (ProjectModel project : projectModels) {
                if (project.getEndYear() > project.getBeginYear()) {
                    for (int i = project.getBeginYear(); i <= project.getEndYear(); i++) {
                        RsProjectSummary detailSummary = new RsProjectSummary(i, project.getId(), project.getCompanyId(), project.getRdTitle(), project.getPname());
                        details.add(detailSummary);
                    }
                } else {
                    RsProjectSummary detailSummary = new RsProjectSummary(project.getBeginYear(), project.getId(), project.getCompanyId(), project.getRdTitle(), project.getPname());
                    details.add(detailSummary);
                }
            }
            //人员
            List<MemberModel> members = summaryDao.getProjectMembers(projectIds);
            String format = "{0}-{1}";
            Map<String, Integer> memberMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(members)) {
                members.forEach(item -> {
                    String key = MessageFormat.format(format, item.getYear(), item.getProjectId());
                    memberMap.put(key, item.getCountData());
                });
            }
            //设备
            List<MemberModel> equipments = summaryDao.getProjectEquipments(projectIds);
            Map<String, Integer> equipmentMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(equipments)) {
                equipments.forEach(item -> {
                    String key = MessageFormat.format(format, item.getYear(), item.getProjectId());
                    equipmentMap.put(key, item.getCountData());
                });
            }
            //专利
            List<MemberModel> patents = summaryDao.getPatentData(projectIds);
            Map<Integer, Integer> patentMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(patents)) {
                patents.forEach(item -> {
                    //String key = MessageFormat.format(format, item.getYear(), item.getProjectId());
                    patentMap.put(item.getProjectId(), item.getCountData());
                });
            }
            //立项报告
            List<MemberModel> docs = summaryDao.getProjectReport(projectIds);
            Map<Integer, Integer> docAuditMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(docs)) {
                docAuditMap = docs.stream().collect(Collectors.toMap(e -> e.getProjectId(), e -> e.getStatus(), (o, n) -> n));
            }
            List<ProjectDocFileEntity> docFiles = summaryDao.getDocByProject(projectIds);
            Map<String, Integer> editDocMap = new HashMap<>();
            Map<String, Integer> submitDocMap = new HashMap<>();
            Map<String, Integer> passDocMap = new HashMap<>();
            Map<Integer, Integer> newReportMap = new HashMap<>();
            Map<Integer, Integer> projectMap = new HashMap<>();
            Map<String, Integer> backupDataMap = new HashMap<>();//备查资料
            List<ProjectDocFileEntity> backupDatas = new ArrayList<>();
            if (!CollectionUtils.isEmpty(docFiles)) {
                for (ProjectDocFileEntity docFile : docFiles) {
                    Integer year = getDocYear(docFile);
                    if (docFile.getDocFileId() == 38) {
                        newReportMap.put(docFile.getProjectId(), docFile.getStatus());
                    }
                    if (docFile.getDocFileId() == 3 || docFile.getDocFileId() == 27 || docFile.getDocFileId() == 38) {
                        if (docFile.getStatus() == 1) {
                            if (projectMap.containsKey(docFile.getProjectId())) {
                                Integer num = projectMap.get(docFile.getProjectId());
                                projectMap.put(docFile.getProjectId(), num + 1);
                            } else {
                                projectMap.put(docFile.getProjectId(), 1);
                            }
                        }
                    }
                    if (docFile.getDocFileId() == 24 || docFile.getDocFileId() == 33) {
                        backupDatas.add(docFile);
                    }
                    String key = MessageFormat.format(format, year, docFile.getProjectId());
                    if (editDocMap.containsKey(key)) {
                        Integer editNum = editDocMap.get(key) + 1;
                        editDocMap.put(key, editNum);
                    } else {
                        editDocMap.put(key, 1);
                    }
                    if (docFile.getStatus() == 0 || docFile.getStatus() == 1 || docFile.getStatus() == 4) {
                        if (submitDocMap.containsKey(key)) {
                            Integer submitNum = submitDocMap.get(key) + 1;
                            submitDocMap.put(key, submitNum);
                        } else {
                            submitDocMap.put(key, 1);
                        }
                        if (docFile.getStatus() == 1) {
                            if (passDocMap.containsKey(key)) {
                                Integer passNum = passDocMap.get(key) + 1;
                                passDocMap.put(key, passNum);
                            } else {
                                passDocMap.put(key, 1);
                            }
                        }
                    }
                }
                Map<String, Integer> dataMap = new HashMap<>();
                //备查资料统计
                if (!CollectionUtils.isEmpty(backupDatas)) {
                    for (ProjectDocFileEntity entity : backupDatas) {
                        if (entity.getBeginYear() == entity.getEndYear() && entity.getDocFileId() == 33 && entity.getStatus() == 1) {
                            dataMap.put(MessageFormat.format(format, entity.getBeginYear(), entity.getProjectId()), 1);
                        }
                        if (entity.getBeginYear() < entity.getEndYear()) {
                            Integer year = getDocYear(entity);
                            if (year < entity.getEndYear() && entity.getDocFileId() == 24 && entity.getStatus() == 1) {
                                dataMap.put(MessageFormat.format(format, year, entity.getProjectId()), 1);
                            } else if (year == entity.getEndYear() && entity.getDocFileId() == 33 && entity.getStatus() == 1) {
                                dataMap.put(MessageFormat.format(format, year, entity.getProjectId()), 1);
                            }
                        }
                    }
                }
                for (ProjectModel project : projectModels) {
                    Integer docNum = projectMap.containsKey(project.getId()) ? projectMap.get(project.getId()) : 0;
                    for (int i = project.getBeginYear(); i <= project.getEndYear(); i++) {
                        String signKey = MessageFormat.format(format, i, project.getId());
                        Integer otherNum = dataMap.containsKey(signKey) ? dataMap.get(signKey) : 0;
                        backupDataMap.put(signKey, docNum + otherNum);
                    }

                }

            }
            for (RsProjectSummary summary : details) {
                String sign = MessageFormat.format(format, summary.getYear(), summary.getRsProjectId());
                summary.setBackupDataTotalCnt(backupDataMap.containsKey(sign) ? backupDataMap.get(sign) : 0);
                summary.setCreateTime(date);
                summary.setUpdateTime(date);
                summary.setDocTotal(editDocMap.get(sign));
                summary.setDocSubmitCnt(submitDocMap.get(sign));
                summary.setDocDoneCnt(passDocMap.get(sign));
                summary.setMemberCnt(memberMap.get(sign));
                summary.setEquipmentCnt(equipmentMap.get(sign));
                if (docAuditMap.containsKey(summary.getRsProjectId())) {
                    summary.setReportStatus(docAuditMap.get(summary.getRsProjectId()));
                    summary.setHasReport(true);
                } else {
                    summary.setHasReport(false);
                }
                if (newReportMap.containsKey(summary.getRsProjectId())) {
                    summary.setNewReportStatus(newReportMap.get(summary.getRsProjectId()));
                    summary.setHasNewReport(true);
                } else {
                    summary.setHasNewReport(false);
                }
                if (patentMap.containsKey(summary.getRsProjectId())) {
                    summary.setPatentCnt(patentMap.get(summary.getRsProjectId()));
                }
            }

        }
        return details;
    }

    /**
     * 获取文档年份
     *
     * @param docFile
     * @return
     */
    private Integer getDocYear(ProjectDocFileEntity docFile) {
        Integer year;
        if (!docFile.getDeleted() && docFile.getMonth() != null) {
            year = DateUtil.year(docFile.getMonth());
        } else {
            StageEntity stage = summaryDao.getStageInfo(docFile.getProjectId(), docFile.getId());
            if (stage != null && stage.getBeginDate() != null) {
                year = DateUtil.year(stage.getBeginDate());
            } else {
                year = docFile.getBeginYear();
            }
        }
        return year;
    }


//    private List<CompanyRdSummary> buildCompanyRdSummaryData(String param, Date now) {
//        Date before = ToolUtils.getBeforeTime(now, param, -10);
//        List<CompanyRdSummary> data = new ArrayList<>();
//        List<CompanyRdSummary> summaryList = projectSummaryDataDao.getData(before);
//        if (!CollectionUtils.isEmpty(summaryList)) {
//            data.addAll(summaryList);
//        }
//        List<CompanyRdSummary> reports = reportDao.getLastUpdateData(before);
//        if (!CollectionUtils.isEmpty(reports)) {
//            data.addAll(reports);
//        }
//        List<CompanyRdSummary> highTechIncomes = companyRdSummaryDao.getHighTechIncomeData(before);
//        if (!CollectionUtils.isEmpty(highTechIncomes)) {
//            data.addAll(highTechIncomes);
//        }
//        List<HighTechModel> highTechList = companyRdSummaryDao.getHighTechData(before);
//        if (!CollectionUtils.isEmpty(highTechList)) {
//            Map<String, CompanyRdSummary> highTechMap = new HashMap<>();
//            highTechList.forEach(item -> {
//                for (int i = 0; i < item.getEndYear() - item.getStartYear(); i++) {
//                    int curYear = item.getStartYear() + i;
//                    String key = MessageFormat.format(keyFormat, item.getCompanyId(), curYear);
//                    if (!highTechMap.containsKey(key)) {
//                        highTechMap.put(key, CompanyRdSummary.buildByHighTech(curYear, item.getCompanyId()));
//                    }
//                    highTechMap.get(key).addHighTechCount();
//                }
//            });
//            if (!CollectionUtils.isEmpty(highTechMap)) {
//                data.addAll(highTechMap.values());
//            }
//        }
//        return data;
//    }


}
