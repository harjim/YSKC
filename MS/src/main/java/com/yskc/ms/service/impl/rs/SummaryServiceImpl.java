package com.yskc.ms.service.impl.rs;

import cn.hutool.core.collection.CollUtil;
import com.yskc.common.utils.DateUtil;
import com.yskc.ms.config.Constant;
import com.yskc.ms.dao.ms.*;
import com.yskc.ms.dao.rs.RsSummaryDao;
import com.yskc.ms.entity.ms.Dept;
import com.yskc.ms.entity.ms.ProjectSummaryData;
import com.yskc.ms.enums.CostEnum;
import com.yskc.ms.models.CustomerTotalModel;
import com.yskc.ms.models.dept.DeptCustomerModel;
import com.yskc.ms.models.projectsummary.QuerySummaryMonthModel;
import com.yskc.ms.models.projectsummary.SummaryDataMonthModel;
import com.yskc.ms.service.rs.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service.impl.rs
 * @Author: zhangdingfu
 * @CreateTime: 2020-06-18 13:51
 * @Description: 归集汇总业务实现层
 */
@Service
public class SummaryServiceImpl implements SummaryService {

    @Autowired
    private RsSummaryDao rsSummaryDao;

    @Autowired
    private DistrictSummaryDao districtSummaryDao;

    @Autowired
    private DeptDao deptDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private ProjectSummaryDataDao projectSummaryDataDao;

    @Override
    public CustomerTotalModel getCustomerTotal() {
        CustomerTotalModel total = projectSummaryDataDao.getTotal();
        if (total == null) {
            total = new CustomerTotalModel();
        }
        total.setCustomerCount(projectDao.countCustomer());
        total.rdFundsSumToBit();
        return total;
    }

    @Override
    public List<SummaryDataMonthModel> getMonthFee(QuerySummaryMonthModel query) {
        Date beginMonth = DateUtil.getMonthFirstDay(query.getMonth());
        Date endMonth = DateUtil.getMonthLastDay(query.getMonth());
        Calendar cale = Calendar.getInstance();
        cale.setTime(query.getMonth());
        cale.set(Calendar.DAY_OF_YEAR,cale.getActualMinimum(Calendar.DAY_OF_YEAR));
        Date beginYear = cale.getTime();
        List<SummaryDataMonthModel> summaryDataMonthModelList = new ArrayList<>();
        List<SummaryDataMonthModel> summaryDataMonthModelList1 = rsSummaryDao.getMonthFee(query,beginMonth,endMonth);
        List<SummaryDataMonthModel> summaryDataMonthModelList2 = rsSummaryDao.getMonthFeeTotal(query,beginYear,beginMonth,endMonth);
        if (summaryDataMonthModelList1!=null){
            summaryDataMonthModelList1.forEach(item ->{
                if (item!=null){
                    item.setType(0);
                    summaryDataMonthModelList.add(item);
                }
            });
        }
        if (summaryDataMonthModelList2!=null){
            summaryDataMonthModelList2.forEach(item ->{
                if (item!=null){
                    item.setType(1);
                    summaryDataMonthModelList.add(item);
                }
            });
        }

        return summaryDataMonthModelList;
    }

    @Override
    public List<Map<String, Object>> getDistrictSummary(Integer year) {
        List<Map<String, Object>> list = districtSummaryDao.getYearDistrictSummary(year);
        List<Map<String, Object>> data = new ArrayList<>();
        list.forEach(item -> {
            data.add(toTitleValueMap((String) item.get("deptName"), ((BigDecimal) item.get("rdFunds")).divide(
                    Constant.TEN_THOUSAND,
                    2,
                    BigDecimal.ROUND_HALF_UP)));
        });
        return data;
    }

    public static Map<String, Object> toTitleValueMap(String title, Object value) {
        Map<String, Object> result = new LinkedHashMap<>(2);
        result.put("title", title);
        result.put("value", value);
        return result;
    }

    @Override
    public List<List<Map<String, Object>>> getBranchTotal(Integer year) {
        List<Dept> deptList = deptDao.getGeLevel(1);
        Map<Integer, CustomerTotalModel> branchMap = new LinkedHashMap<>();
        Map<Integer, Integer> deptBranchMap = new HashMap<>();
        for (Dept dept : deptList) {
            deptBranchMap.put(dept.getId(), dept.getBranchId());
            if (dept.getId().equals(dept.getBranchId())) {
                branchMap.put(dept.getId(), CustomerTotalModel.build(dept.getDeptName()));
            }
        }
        List<DeptCustomerModel> projectList = projectDao.getAllCustomer();
        String keyFormat = "{0}_{1}";
        Map<String, Integer> customerYearDeptMap = new HashMap<>();
        Map<Integer, Set<Integer>> branchCustomerCountMap = new HashMap<>();
        Integer branchId;
        for (DeptCustomerModel model : projectList) {
            branchId = deptBranchMap.get(model.getDeptId());
            customerYearDeptMap.put(MessageFormat.format(keyFormat, model.getCustomerId(), model.getYear()),
                    branchId);
            if (!branchCustomerCountMap.containsKey(branchId)) {
                branchCustomerCountMap.put(branchId, new HashSet<>());
            }
            branchCustomerCountMap.get(branchId).add(model.getCustomerId());
        }
        for (Integer branchIdKey : branchCustomerCountMap.keySet()) {
            if (branchMap.containsKey(branchIdKey)) {
                branchMap.get(branchIdKey).setCustomerCount(branchCustomerCountMap.get(branchIdKey).size());
            }
        }
        List<ProjectSummaryData> projectSummaries = projectSummaryDataDao.getByYear(year);
        for (ProjectSummaryData projectSummary : projectSummaries) {
            branchId = customerYearDeptMap.get(MessageFormat.format(keyFormat, projectSummary.getCustomerId(),
                    projectSummary.getYear()));
            if (branchMap.containsKey(branchId)) {
                CustomerTotalModel customerTotalModel = branchMap.get(branchId);
                customerTotalModel.addRdCount(projectSummary.getRdCount());
                customerTotalModel.addRdFundsSum(projectSummary.getRdFunds());
            }
        }
        Collection<CustomerTotalModel> list = branchMap.values();
        List<List<Map<String, Object>>> result = new ArrayList<>();
        list.forEach(item -> {
            result.add(CollUtil.newArrayList(
                    toTitleValueMap("分公司", item.getBranchName()),
                    toTitleValueMap("客户数", item.getCustomerCount()),
                    toTitleValueMap("立项数", item.getRdCount()),
                    toTitleValueMap("研发费用(万元)", item.getRdFundsSum() != null ?
                            item.getRdFundsSum().divide(Constant.TEN_THOUSAND, 2, BigDecimal.ROUND_HALF_UP) :
                            BigDecimal.ZERO)));
        });
        return result;
    }

    @Override
    public List<Map<String, Object>> getSummaryCost(Integer year) {
        Date firstDay = DateUtil.getYearFirstDay(year);
        Date lastDay = DateUtil.getYearLastDay(year);
        List<Integer> unUserList = Arrays.asList(10001, 10101, 10102, 10103, 10104, 10105, 10106, 50100, 50200, 50300);
        List<Map<String, Object>> costDatas = rsSummaryDao.getSummaryCost(firstDay, lastDay, unUserList);
        Map<String, List<Map<String, Object>>> monthData = new HashMap<>();
        for (Map<String, Object> dataMap : costDatas) {
            //筛选同月的数据
            String month = DateUtil.format((Date) dataMap.get("month"), "yyyy-MM");
            if (monthData.containsKey(month)) {
                monthData.get(month).add(dataMap);
            } else {
                List<Map<String, Object>> mapList = new ArrayList<>();
                mapList.add(dataMap);
                monthData.put(month, mapList);
            }
        }
        Set<String> set = monthData.keySet();
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (String monthStr : set) {
            Map<String, BigDecimal> decimalMap = processingData(monthData.get(monthStr));
            Map<String, Object> countMap = new HashMap<>();
            countMap.put("month", monthStr);
            countMap.put("dataMap", decimalMap);
            resultList.add(countMap);
        }
        return resultList;
    }

    //查询数据转为map（key:title,value:summaryData)
    public Map<String, BigDecimal> processingData(List<Map<String, Object>> mapList) {
        Map<String, BigDecimal> resultMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(mapList)) {
            Map<Integer, BigDecimal> dataMap = new HashMap<>();
            for (Map<String, Object> map : mapList) {
                Integer type = (Integer) map.get("rdType");
                BigDecimal countDate = (BigDecimal) map.get("totalData");
                dataMap.put(type, countDate);
            }
            Set<Integer> set = dataMap.keySet();
            BigDecimal power = new BigDecimal(0.00);//动力
            BigDecimal trial = new BigDecimal(0.00);//试制
            BigDecimal repair = new BigDecimal(0.00);//修理
            BigDecimal amortization = new BigDecimal(0.00);//摊销
            BigDecimal other=new BigDecimal(0.00);//其他
            BigDecimal material=new BigDecimal(0.00);//材料
            BigDecimal fuel=new BigDecimal(0.00);//燃料
            CostEnum costEnum;
            for (Integer rdType : set) {
                costEnum=CostEnum.getCostEnum(rdType);
                BigDecimal summaryData = dataMap.get(rdType);
                if(rdType/100==200){
                    material=material.add(summaryData);
                }else if (rdType / 100 == 201) {
                    power = power.add(summaryData);
                } else if(rdType / 100 == 202){
                    fuel=fuel.add(summaryData);
                }else if (rdType / 100 == 203) {
                    trial = trial.add(summaryData);
                } else if (rdType / 100 == 206) {
                    repair = repair.add(summaryData);
                } else if (rdType / 1000 == 40) {
                    amortization = amortization.add(summaryData);
                } else if(rdType==60000||rdType==60100||rdType==60200||rdType==60300||rdType==69900){
                   other=other.add(summaryData);
                }else {
                    String field=costEnum.getField();
                    resultMap.put(field, summaryData.divide(Constant.TEN_THOUSAND, 2, BigDecimal.ROUND_HALF_UP));
                }
            }
            resultMap.put("power", power.divide(Constant.TEN_THOUSAND, 2, BigDecimal.ROUND_HALF_UP));
            resultMap.put("trial", trial.divide(Constant.TEN_THOUSAND, 2, BigDecimal.ROUND_HALF_UP));
            resultMap.put("repair", repair.divide(Constant.TEN_THOUSAND, 2, BigDecimal.ROUND_HALF_UP));
            resultMap.put("amortization", amortization.divide(Constant.TEN_THOUSAND, 2, BigDecimal.ROUND_HALF_UP));
            resultMap.put("other",other.divide(Constant.TEN_THOUSAND, 2, BigDecimal.ROUND_HALF_UP));
            resultMap.put("material",material.divide(Constant.TEN_THOUSAND, 2, BigDecimal.ROUND_HALF_UP));
            resultMap.put("fuel",fuel.divide(Constant.TEN_THOUSAND, 2, BigDecimal.ROUND_HALF_UP));
        }
        return resultMap;
    }

    @Override
    public Map<Integer,Map<String,BigDecimal>> getFundByYearAndProId(List<Integer> projectIds, Integer year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        Date beginDate = cn.hutool.core.date.DateUtil.beginOfYear(calendar.getTime());
        Date endDate = cn.hutool.core.date.DateUtil.endOfYear(beginDate);
        return rsSummaryDao.getFundByYearAndProId(projectIds,beginDate,endDate);
    }
}
