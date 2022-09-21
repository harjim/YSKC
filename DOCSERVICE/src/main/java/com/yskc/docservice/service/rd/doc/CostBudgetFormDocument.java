package com.yskc.docservice.service.rd.doc;

import com.yskc.docservice.config.Constant;
import com.yskc.docservice.dao.rs.BudgetDao;
import com.yskc.docservice.dao.rs.SummaryDao;
import com.yskc.docservice.entity.rs.BudgetEntity;
import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.models.rs.project.ProjectBudgetModel;
import com.yskc.docservice.service.rd.RDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

@Component("CostBudgetForm_Doc")
@Scope("prototype")
public class CostBudgetFormDocument extends RDDocument {

    @Autowired
    private BudgetDao budgetDao;
    @Autowired
    private SummaryDao summaryDao;

    @Override
    protected Map getDocMap(){
        Map map = new HashMap();
        map.put("totalBudget", this.dataFactory.getTotalBudget());
        Map totalMap = getYearBudget(dataFactory.getProjectInfo(), dataFactory.getProjectInfo().getEndYear());
        if (map != null) {
            map.putAll(totalMap);
        }
        return map;
    }

    private void updateTotalBudget(Map<String, BigDecimal> totalMap,String key, BigDecimal budgetVal) {
        if (totalMap.get(key)==null){
            totalMap.put(key,BigDecimal.ZERO);
        }
        if (budgetVal!=null){
            totalMap.put(key,totalMap.get(key).add(budgetVal));
        }
    }

    @Override
    protected Map getDocMap(ProjectEntity projectEntity, Integer currentYear) {
        Map map = getYearBudget(projectEntity, projectEntity.getEndYear());
        if (map != null) {
            map.putAll(map);
        }
        return map;
    }

    public Map getYearBudget(ProjectEntity project,Integer currentYear){
/*        List<BudgetEntity> list = budgetDao.getListByProject(project.getId());
        Map yearMap = new HashMap();
        if (!CollectionUtils.isEmpty(list)) {
            Map resultMap = new HashMap<>();
            Map<String, List<BudgetEntity>> map = new HashMap<>();
            list.forEach(item->{
                String year = item.getYear()+"";
                if (map.containsKey(year)) {
                    map.get(year).add(item);
                } else {
                    List<BudgetEntity> budgetEntityList = new ArrayList<>();
                    budgetEntityList.add(item);
                    map.put(year, budgetEntityList);
                }
            });
            for (String key : map.keySet()) {
                List<BudgetEntity> entities = map.get(key);
                Map<String, Object> detailMap = new HashMap<>();
                entities.forEach(item->{
                    String key1 = item.getKey();
                    if ("spending01".equals(key1)) {
                        detailMap.put(item.getKey(), item.getValue());
                    } else {
                        detailMap.put("t" + item.getKey(), item.getValue());
                    }
                });
                yearMap.put(key,detailMap);
            }
            resultMap.put("map", yearMap);
            List<BudgetEntity> totalList = budgetDao.getTotal(project.getId());
            Map<String,Object> tMap=new HashMap<>();
            totalList.forEach(item->{
                String key = item.getKey();
                if ("spending01".equals(key)) {
                    tMap.put(item.getKey(), item.getValue());
                }
                tMap.put("t" + item.getKey(), item.getValue());
            });
            resultMap.put("total", tMap);
            return resultMap;
        } else {
            Map map = new LinkedHashMap<>();
            Map<String,Integer> totalMap=new HashMap<>();
            for (int i=project.getBeginYear();i<=currentYear;i++){
                List<ProjectBudgetModel> budget = summaryDao.getBudgetByYear(project.getId(),i);
                Map<String,Object> resultMap=new HashMap<>();
                if (!CollectionUtils.isEmpty(budget)) {
                    Map<String, Integer> summaryMap =getBudgetData(budget,new BigDecimal(1.00));
                    for (String str:summaryMap.keySet()){
                        Integer data=summaryMap.get(str);
                        if(!totalMap.containsKey(str)){
                            totalMap.put(str,data);
                        }else {
                            Integer total=totalMap.get(str)+data;
                            totalMap.put(str,total);
                        }
                    }
                    resultMap.putAll(summaryMap);
                }
                String key=String.valueOf(i);
                yearMap.put(key,resultMap);
            }
            map.put("map", yearMap);
            map.put("total",totalMap);
            return map;
        }*/
        Integer beginYear = project.getBeginYear();
        Integer endYear = project.getEndYear();
        List<BudgetEntity> list = budgetDao.getListByProject(project.getId(), beginYear, endYear);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, Map<String, Object>> resultMap = new HashMap<>();
            Map<String, Object> yearMap = new HashMap<>();
            Map<String, List<BudgetEntity>> map = new HashMap<>();
            list.forEach(item->{
                String year = item.getYear()+"";
                if (map.containsKey(year)) {
                    map.get(year).add(item);
                } else {
                    List<BudgetEntity> entities = new ArrayList<>();
                    entities.add(item);
                    map.put(year,entities);
                }
            });
            for (String key : map.keySet()) {
                List<BudgetEntity> entities = map.get(key);
                Map<String, Object> dataMap = new HashMap<>();
                entities.forEach(item->{
                    String key1 = item.getKey();
                    if ("spending01".equals(key1)) {
                        dataMap.put(item.getKey(), item.getValue());
                    } else {
                        dataMap.put("t" + item.getKey(), item.getValue());
                    }
                });
                yearMap.put(key, dataMap);
            }
            resultMap.put("map", yearMap);

            List<BudgetEntity> totalList = budgetDao.getTotal(project.getId());
            Map<String,Object> tMap=new HashMap<>();
            totalList.forEach(item->{
                String key = item.getKey();
                if ("spending01".equals(key)) {
                    tMap.put(item.getKey(), item.getValue());
                }
                tMap.put("t" + item.getKey(), item.getValue());
            });
            resultMap.put("total", tMap);
            return resultMap;
        } else {
            return getYearSummary(project, currentYear);
        }
    }

    public Map<String,Map<String,Object>> getYearSummary(ProjectEntity project,Integer currentYear){
        Map<String,Map<String,Object>> map=new LinkedHashMap<>();
        Map<String,Object> yearMap=new LinkedHashMap<>();
        Map<String,Integer> totalMap=new HashMap<>();
        for (int i=project.getBeginYear();i<=currentYear;i++){
            List<ProjectBudgetModel> budget = summaryDao.getBudgetByYear(project.getId(),i);
            Map<String,Object> resultMap=new HashMap<>();
            if (!CollectionUtils.isEmpty(budget)) {
                Map<String, Integer> summaryMap =getBudgetData(budget,new BigDecimal(1.00));
                for (String str:summaryMap.keySet()){
                    Integer data=summaryMap.get(str);
                    if(!totalMap.containsKey(str)){
                        totalMap.put(str,data);
                    }else {
                        Integer total=totalMap.get(str)+data;
                        totalMap.put(str,total);
                    }
                }
                resultMap.putAll(summaryMap);
            }
            String key=String.valueOf(i);
            yearMap.put(key,resultMap);
        }
        map.put("map", yearMap);
        Map<String,Object> tMap=new HashMap<>();
        tMap.putAll(totalMap);
        map.put("total",tMap);
        return map;
    }

    //  二、2020年系统预算取数：预算表全部以已经归集数据为依据，分级取数四舍五入的原则；归集数据十万位及以上，
// 预算数据取数到万位四舍五入(五进制，万位只有5和0）；归集数据万位，预算数据四舍五入（一进制）。
// 例如：工资：1234321.55，取预算数120万；社保264567.11取预算数30万；其他费用17454.23，取预算数：2万；其他费用12454.23，取数1万
//
    private Map<String, Integer> getBudgetData(List<ProjectBudgetModel> budget,BigDecimal percent){
        Map<String, Integer> summaryMap = new HashMap<>();
        int totalSummary=0;
        for (ProjectBudgetModel model : budget) {
            int summary = computeBudget(model.getRdFunds().multiply(percent), Constant.TEN_THOUSAND);
            Integer rdType = model.getRdType();
            String key = "t" + rdType / 100 * 100;
            if (!summaryMap.containsKey(key)) {
                summaryMap.put(key, summary);
            } else {
                summaryMap.put(key, summaryMap.get(key) + summary);
            }
            String preKey = "t" + rdType / 10000 * 100;
            if (!summaryMap.containsKey(preKey)) {
                summaryMap.put(preKey, summary);
            } else {
                summaryMap.put(preKey, summaryMap.get(preKey) + summary);
            }
            totalSummary+=summary;
        }
        summaryMap.put("spending01",totalSummary);
        return summaryMap;
    }

    public static int computeBudget(BigDecimal funds, BigDecimal radix) {
        BigDecimal ten = BigDecimal.TEN;
        if (funds.compareTo(BigDecimal.ZERO) <= 0) {
            return 0;
        } else if (funds.compareTo(radix) <= 0) {
            return 1;
        } else {
            BigDecimal divFunds = funds.divide(radix, 2, 4);
            if (divFunds.compareTo(ten) <= 0) {
                return (int)Math.ceil(divFunds.doubleValue());
            } else {
                double remainder = divFunds.doubleValue() % (double)ten.intValue();
                if (remainder == 0.0D) {
                    return divFunds.intValue();
                } else {
                    int base = divFunds.divide(ten, 0, 3).intValue();
                    return remainder > 5.0D ? (base + 1) * 10 : base * 10 + 5;
                }
            }
        }
    }
}
