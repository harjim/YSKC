package com.xxl.job.executor.service.jobhandler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.executor.core.config.Constant;
import com.xxl.job.executor.dao.ms.*;
import com.xxl.job.executor.dao.rs.TechProjectDao;
import com.xxl.job.executor.entity.ms.DailyReport;
import com.xxl.job.executor.entity.ms.DistrictSummary;
import com.xxl.job.executor.entity.ms.MonthlyReport;
import com.xxl.job.executor.entity.ms.TechSummaryEntity;
import com.xxl.job.executor.models.dailyreport.DailyReportDetailModel;
import com.xxl.job.executor.models.district.RsSummaryModel;
import com.xxl.job.executor.models.monthlyreport.MonthlyReportDetailModel;
import com.xxl.job.executor.models.techproject.CustomerIdYearModel;
import com.xxl.job.executor.models.techproject.StageCountModel;
import com.xxl.job.executor.models.techproject.TechSummaryModel;
import com.xxl.job.executor.utils.ToolUtils;
import com.xxl.job.executor.utils.TransactionUtils;
import com.yskc.common.enums.DataSourceEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.service.jobhandler
 * @Author: zhangdingfu
 * @CreateTime: 2020-06-16 17:42
 * @Description: 区域费用统计job
 */
@Component
public class SummaryJob {

    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private DistrictSummaryDao districtSummaryDao;
    @Autowired
    private ProjectSummaryDataDao projectSummaryDataDao;
    @Autowired
    private TechProjectDao techProjectDao;
    @Autowired
    private TechSummaryDao techSummaryDao;
    @Autowired
    private FlowInstanceLogDao flowInstanceLogDao;
    @Autowired
    private DailyReportDao dailyReportDao;
    @Autowired
    private DailyReportDetailDao dailyReportDetailDao;
    @Autowired
    private ServiceLogDao serviceLogDao;
    @Autowired
    private MonthlyReportDao monthlyReportDao;
    @Autowired
    private MonthlyReportDetailDao monthlyReportDetailDao;

    @XxlJob("districtSummaryJob")
    public ReturnT<String> districtSummaryJob(String param) {
        if (StringUtils.isEmpty(param)) {
            XxlJobLogger.log("任务执行失败，缺少任务参数。");
            return ReturnT.FAIL;
        }
        List<Integer> deptIds = new ArrayList<>();
        String[] arr = param.split(",");
        for (String num : arr) {
            deptIds.add(Integer.valueOf(num));
        }
        List<DistrictSummary> data = projectDao.getCustomerByDeptIds(deptIds);
        if (CollectionUtils.isEmpty(data)) {
            XxlJobLogger.log("未获取任何数据，退出当前job。");
            return ReturnT.SUCCESS;
        }
        Set<Integer> years = new HashSet<>();
        Map<String, DistrictSummary> deptYearMap = new HashMap<>();
        Map<String, String> customerYearMap = new HashMap<>();
        String format = "{0}_{1}";
        data.forEach(item -> {
            years.add(item.getYear());
            String deptYearKey = MessageFormat.format(format, item.getDeptId(), item.getYear());
            customerYearMap.put(MessageFormat.format(format, item.getCustomerId(), item.getYear()),
                    deptYearKey);
        });
        Date now = new Date();
        for (Integer deptId : deptIds) {
            for (Integer year : years) {
                DistrictSummary district = new DistrictSummary();
                district.setCreateTime(now);
                district.setUpdateTime(now);
                district.setYear(year);
                district.setDeptId(deptId);
                district.setRdFunds(BigDecimal.ZERO);
                deptYearMap.put(MessageFormat.format(format, deptId, year), district);
            }
        }
        List<RsSummaryModel> rsSummaryModels = projectSummaryDataDao.getCustomerYearRdFunds(data);
        for (RsSummaryModel rsSummary : rsSummaryModels) {
            String key = customerYearMap.getOrDefault(
                    MessageFormat.format(format, rsSummary.getCustomerId(), rsSummary.getYear()), null);
            if (!StringUtils.isEmpty(key) && rsSummary.getRdFunds() != null && deptYearMap.containsKey(key)) {
                deptYearMap.get(key).addRdFunds(rsSummary.getRdFunds());
            }
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            Collection<DistrictSummary> saveList = deptYearMap.values();
            List<List<DistrictSummary>> dataList = CollUtil.split(saveList, Constant.MAX_INSERT_OR_UPDATE);
            for (List<DistrictSummary> insertOrUpdates : dataList) {
                districtSummaryDao.insertOrUpdate(insertOrUpdates);
            }
            XxlJobLogger.log(MessageFormat.format("当前保存记录数：{0}", saveList.size()));
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            XxlJobLogger.log("保存数据失败!");
            XxlJobLogger.log(e);
            return ReturnT.FAIL;
        }
    }

    @XxlJob("techSummaryJob")
    public ReturnT<String> techSummaryJob(String param) {
        Date now = new Date();
        Date beforeTime = ToolUtils.getBeforeTime(now, param, -300);
        List<TechSummaryModel> techSummaries = techProjectDao.getLastOperation(beforeTime);
        if (CollectionUtils.isEmpty(techSummaries)) {
            XxlJobLogger.log("未获取任何数据，退出当前job。");
            return ReturnT.SUCCESS;
        }
        List<Integer> sourceProjectIds = new ArrayList<>();
        List<Integer> directionIds = new ArrayList<>();
        techSummaries.forEach(item -> {
            sourceProjectIds.add(item.getSourceProjectId());
            directionIds.add(item.getDirectionId());
        });
        List<CustomerIdYearModel> cIdYears = projectDao.getCustomerByIds(sourceProjectIds);
        List<StageCountModel> stageCounts = techProjectDao.getStageCount(directionIds);
        Map<Integer, CustomerIdYearModel> cIdYearModelMap = cIdYears.stream().collect(Collectors.toMap(a -> a.getId(), b -> b, (c, d) -> d));
        Map<Integer, StageCountModel> stageCountMap = stageCounts.stream().collect(Collectors.toMap(a -> a.getDirectionId(), b -> b, (c, d) -> d));
        List<TechSummaryEntity> saveList = new ArrayList<>();
        techSummaries.forEach(item -> {
            CustomerIdYearModel cIdYear = cIdYearModelMap.get(item.getSourceProjectId());
            if (null == cIdYear) {
                return;
            }
            StageCountModel stageCount = stageCountMap.get(item.getDirectionId());
            if (null == stageCount) {
                return;
            }
            saveList.add(TechSummaryEntity.build(item, cIdYear, stageCount, now));
        });
        if (!CollectionUtils.isEmpty(saveList)) {
            TransactionStatus transactionStatus = null;
            try {
                transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
                List<List<TechSummaryEntity>> dataList = CollUtil.split(saveList, Constant.MAX_INSERT_OR_UPDATE);
                for (List<TechSummaryEntity> insertOrUpdates : dataList) {
                    techSummaryDao.insertOrUpdate(insertOrUpdates);
                }
                TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
            } catch (Exception e) {
                XxlJobLogger.log("保存数据失败!");
                XxlJobLogger.log(e);
                TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
                return ReturnT.FAIL;
            }
        }
        return ReturnT.SUCCESS;
    }

    @XxlJob("workReportJob")
    public ReturnT<String> workReportJob(String param) {
        Date now = new Date();
        Date begin = ToolUtils.offsetDay(param, now);
        List<DailyReportDetailModel> data = flowInstanceLogDao.getLastData(begin, now);
        if (CollectionUtils.isEmpty(data)) {
            XxlJobLogger.log("未获取任何数据，退出当前job。");
            return ReturnT.SUCCESS;
        }
        String monthFormat = "{0}_{1}";
        String dayFormat = monthFormat + "_{2}";
        Map<String, DailyReport> summaryMap = new LinkedHashMap<>();
        data.forEach(item -> {
            String key = MessageFormat.format(dayFormat, item.getWorkDate(), item.getUserId(), item.getCompanyId());
            if (!summaryMap.containsKey(key)) {
                summaryMap.put(key, new DailyReport(item.getWorkDate(), item.getUserId(), item.getCompanyId(), now));
            }
            summaryMap.get(key).addCnt(item.getCommit(), item.getDone(), item.getReject());
        });
        //按月统计
        Date month = DateUtil.beginOfMonth(begin);
        List<MonthlyReportDetailModel> serviceCnt = serviceLogDao.getServiceCnt(month);
        Map<String, MonthlyReport> monthlyReportMap = new LinkedHashMap<>();
        if (!CollectionUtils.isEmpty(serviceCnt)) {
            serviceCnt.forEach(item -> monthlyReportMap.put(MessageFormat.format(monthFormat, item.getUserId(), item.getWorkMonth()),
                    new MonthlyReport(item, now)));
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            List<List<DailyReport>> summaryList = CollUtil.split(summaryMap.values(), Constant.MAX_INSERT_OR_UPDATE);
            int summaryCount = 0;
            for (List<DailyReport> curList : summaryList) {
                summaryCount += curList.size();
                dailyReportDao.insertOrUpdate(curList);
            }
            List<List<DailyReportDetailModel>> detailList = CollUtil.split(data, Constant.MAX_INSERT_OR_UPDATE);
            for (List<DailyReportDetailModel> curList : detailList) {
                dailyReportDetailDao.insertOrUpdate(curList, now);
            }
            XxlJobLogger.log("当次插入/更新日汇总信息数：" + summaryCount + "，插入/更新日明细数：" + data.size());
            List<MonthlyReportDetailModel> monthlyData = dailyReportDetailDao.getMonthlyData(month);
            Map<String, MonthlyReportDetailModel> monthlyDetailMap = new LinkedHashMap<>();
            if (!CollectionUtils.isEmpty(monthlyData)) {
                String monthlyKey;
                String moduleKey;
                Integer userId;
                String workMonth;
                for (MonthlyReportDetailModel item : monthlyData) {
                    userId = item.getUserId();
                    workMonth = item.getWorkMonth();
                    monthlyKey = MessageFormat.format(monthFormat, userId, workMonth);
                    if (!monthlyReportMap.containsKey(monthlyKey)) {
                        monthlyReportMap.put(monthlyKey, new MonthlyReport(item, now));
                    } else {
                        monthlyReportMap.get(monthlyKey).addCnt(item);
                    }
                    moduleKey = MessageFormat.format(dayFormat, userId, workMonth, item.getModuleId());
                    if (!monthlyDetailMap.containsKey(moduleKey)) {
                        item.reset();
                        monthlyDetailMap.put(moduleKey, item);
                    } else {
                        monthlyDetailMap.get(moduleKey).addCnt(item);
                    }
                }
            }
            List<MonthlyReport> monthlyReports = new ArrayList<>(monthlyReportMap.values());
            List<MonthlyReportDetailModel> monthlyDetails = new ArrayList<>(monthlyDetailMap.values());
            if (!CollectionUtils.isEmpty(monthlyReports)) {
                for (List<MonthlyReport> list : CollUtil.split(monthlyReports, Constant.MAX_INSERT_OR_UPDATE)) {
                    monthlyReportDao.insertOrUpdate(list);
                }
            }
            if (!CollectionUtils.isEmpty(monthlyDetails)) {
                for (List<MonthlyReportDetailModel> list : CollUtil.split(monthlyDetails, Constant.MAX_INSERT_OR_UPDATE)) {
                    monthlyReportDetailDao.insertOrUpdate(list, now);
                }
            }
            XxlJobLogger.log("当次插入/更新月汇总信息数：" + monthlyReports.size() + "，插入/更新月明细数：" + monthlyDetails.size());
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
        } catch (Exception e) {
            XxlJobLogger.log(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            return ReturnT.FAIL;
        }
        return ReturnT.SUCCESS;
    }
}
