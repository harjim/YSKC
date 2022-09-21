package com.yskc.rs.service.impl;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.DateUtil;
import com.yskc.rs.dao.ProjectDao;
import com.yskc.rs.dao.SummaryDao;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.enums.CostEnum;
import com.yskc.rs.models.project.ProjectSelectModel;
import com.yskc.rs.models.rdAgg.AggDetailModel;
import com.yskc.rs.models.rdAgg.AggSummaryModel;
import com.yskc.rs.models.summary.DataReportModel;
import com.yskc.rs.models.summary.RdAggDataModel;
import com.yskc.rs.service.RdAggService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * @program: rs
 * @description: 项目归集总表
 * @author: cyj
 * @create: 2022-06-17 15:26
 **/
@Service
public class RdAggServiceImpl implements RdAggService {
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private SummaryDao summaryDao;

    @Override
    public Map< String, Object > getDataReportFunds(Integer companyId, Integer year) throws OwnerException {
        Map< String, Object > result = new HashMap<>(2);
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();//年
        Calendar calendar1 = Calendar.getInstance();
        calendar1.clear();
        calendar1.set(Calendar.YEAR, year);
        calendar1.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar1.getTime();

        //sign==2 是没有子项目的项目
        List< ProjectSelectModel > projects = projectDao.getSelectList(companyId, year, 2);
        if (CollectionUtils.isEmpty(projects)) {
            return result;
        }
        String format = "yyyy年MM月";
        Map< Integer, RdAggDataModel > resultMap = new LinkedHashMap<>();
        Boolean noOutsourcing = true;
        for (ProjectSelectModel item : projects) {
            if (Integer.parseInt(item.getFormula()) > 10) {
                noOutsourcing = false;
            }
            resultMap.put(item.getId(),
                    new RdAggDataModel(item.getId(), item.getRdTitle(),
                            DateUtil.format(item.getBeginDate(), format) + "-" + DateUtil.format(item.getEndDate(), format),
                            item.getPname(), item.getTrialProd(), item.gettBeginDate(), item.gettEndDate()));
        }
        result.put("noOutsourcing", noOutsourcing);

        Map< Integer, Map< String, BigDecimal > > map = summaryDao.getSummaryByProjectList(currYearFirst, currYearLast, resultMap.keySet(), year);
        for (Integer projectId : resultMap.keySet()) {
            if (map.containsKey(projectId)) {
                map.get(projectId).remove("projectId");
                resultMap.get(projectId).setTotalFunds(map.get(projectId));
            }
        }
        Map< String, BigDecimal > companyFunds = summaryDao.getSummaryByCompany(currYearFirst, currYearLast, companyId, year);
        DataReportModel model = new DataReportModel();
        model.setTotalFunds(companyFunds);

        result.put("companyFunds", model);
        result.put("list", resultMap.values());
        return result;
    }

    @Override
    public Map< String, Object > getProjectDataReport(Integer year, Integer projectId) throws OwnerException {
        Map< String, Object > result = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();//年
        Calendar calendar1 = Calendar.getInstance();
        calendar1.clear();
        calendar1.set(Calendar.YEAR, year);
        calendar1.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar1.getTime();

        List< DataReportModel > list = new ArrayList<>();
        ProjectEntity projectEntity = projectDao.selectById(projectId);
        Date beginDate = projectEntity.getBeginDate();
        Date endDate = projectEntity.getEndDate();
        Integer endMonth;
        Integer beginMonth;
        if (endDate.before(currYearLast)) {
            calendar.setTime(endDate);
            endMonth = calendar.get(Calendar.MONTH) + 1;
        } else {
            calendar.setTime(currYearLast);
            endMonth = calendar.get(Calendar.MONTH) + 1;
        }
        if (beginDate.after(currYearFirst)) {
            calendar.setTime(beginDate);
            beginMonth = calendar.get(Calendar.MONTH) + 1;
        } else {
            calendar.setTime(currYearFirst);
            beginMonth = calendar.get(Calendar.MONTH) + 1;
        }

        Map< Integer, Map< String, BigDecimal > > map = summaryDao.getSummaryByProject(currYearFirst, currYearLast, projectId, year);
        for (Number month : map.keySet()) {
            map.get(month).remove("month");
            Integer date = month.intValue();
            DataReportModel model = new DataReportModel();
            model.setMonth(date + "月份");
            model.setTotalFunds(map.get(month));
            model.setId(projectId);
            list.add(model);
        }

        result.put("list", list);
        result.put("beginMonth", beginMonth);
        result.put("endMonth", endMonth);
        return result;
    }

    @Override
    public List< AggSummaryModel > getMonthCostByRdType(Integer year, Integer rdType, Integer companyId) {
        // 根据 rdType 获取对应 rdTypeList
        Map< Integer, CostEnum > parentCostMap = CostEnum.getParentMap(false);
        List< Integer > rdTypeList = CostEnum.getRdTypesByParent(rdType);

        Date beginMonth = DateUtil.getYearFirstDay(year);
        Date endMonth = DateUtil.getYearFirstDay(year + 1);
        List< AggDetailModel > aggDetailList = summaryDao.getMonthCostByRdType(beginMonth, endMonth, rdTypeList, companyId);

        // 按项目归集
        List< AggSummaryModel > aggSummaryList = new ArrayList<>();
        String costTitle = parentCostMap.get(rdType).getTitle();
        AggSummaryModel totalAgg = new AggSummaryModel();
        totalAgg.setRdTitle("合计");
        totalAgg.setCostTitle(costTitle);
        totalAgg.setFunds(new BigDecimal[12]);
        totalAgg.setSum(BigDecimal.ZERO);
        BigDecimal totalSum = BigDecimal.ZERO;
        AggSummaryModel tempAggModel = new AggSummaryModel();
        // 合并项目并计算合计
        for (AggDetailModel aggDetail : aggDetailList) {
            String tempAggModelRdTitle = tempAggModel.getRdTitle();
            if (!aggDetail.getRdTitle().equals(tempAggModelRdTitle)) {
                tempAggModel = new AggSummaryModel();
                tempAggModel.setRdTitle(aggDetail.getRdTitle());
                tempAggModel.setCostTitle(costTitle);
                tempAggModel.setSum(BigDecimal.ZERO);
                tempAggModel.setFunds(new BigDecimal[12]);
                aggSummaryList.add(tempAggModel);
            }
            Integer month = cn.hutool.core.date.DateUtil.month(aggDetail.getRdMonth());
            BigDecimal aggSummarySum = aggDetail.getRdFunds().add(tempAggModel.getSum());
            tempAggModel.setSum(aggSummarySum);
            BigDecimal[] aggSummaryFunds = tempAggModel.getFunds();
            aggSummaryFunds[month] = aggDetail.getRdFunds();
            // 合计行
            BigDecimal totalFund = totalAgg.getFunds()[month];
            if (totalFund != null) {
                totalFund = totalFund.add(aggDetail.getRdFunds());
            } else {
                totalFund = aggDetail.getRdFunds();
            }
            BigDecimal[] totalFunds = totalAgg.getFunds();
            totalFunds[month] = totalFund;
            totalSum = totalSum.add(aggDetail.getRdFunds());
        }
        totalAgg.setSum(totalSum);

        aggSummaryList.add(totalAgg);
        return aggSummaryList;
    }
}
