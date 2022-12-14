package com.yskc.rs.service.exportFileImpl;

import cn.hutool.core.date.DateUtil;
import com.yskc.common.utils.CommonUtils;
import com.yskc.common.utils.JsonUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.dao.SummaryDao;
import com.yskc.rs.dao.project.BudgetDao;
import com.yskc.rs.entity.project.BudgetEntity;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.models.document.DataModel;
import com.yskc.rs.models.project.BudgetModel;
import com.yskc.rs.models.project.ProjectBudgetModel;
import com.yskc.rs.service.ExportDocFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by hck
 * on 2020/6/28 15:26
 * description:
 */
@Component
public class CostBudgetForm implements ExportDocFileService {

    @Autowired
    private BudgetDao budgetDao;
    @Autowired
    private SummaryDao summaryDao;

    public static CostBudgetForm costBudgetForm;

    @PostConstruct
    public void init() {
        costBudgetForm = this;
        costBudgetForm.budgetDao = this.budgetDao;
        costBudgetForm.summaryDao = this.summaryDao;
    }

    @Override
    public Map<String, Object> exportDocFile(ProjectEntity projectEntity, Integer currentYear, String fileData, DataModel dataModel) {
        Map<String, Object> dataMap = new HashMap<>();
        if (!StringUtils.isEmpty(fileData)) {
            dataMap = JsonUtils.jsonToPojo(fileData, Map.class);
        }
        Map<String, Map<String,Object>> map = getYearBudget(projectEntity, projectEntity.getEndYear(),null,null);
        if (map != null) {
            dataMap.putAll(map);
        }
        return dataMap;
    }
    public Map<String,Map<String,Object>> getYearSummary(ProjectEntity project,Integer currentYear){
        Map<String,Map<String,Object>> map=new LinkedHashMap<>();
        Map<String,Integer> totalMap=new HashMap<>();
        for (int i=project.getBeginYear();i<=currentYear;i++){
            List<ProjectBudgetModel> budget = costBudgetForm.summaryDao.getBudgetByYear(project.getId(),i);
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
            map.put(key,resultMap);
        }
        Map<String,Object> tMap=new HashMap<>();
        tMap.putAll(totalMap);
        map.put("total",tMap);
        return map;
    }
    public Map<String,Map<String,Object>> getYearBudget(ProjectEntity project,Integer currentYear,Integer beginYear,Integer endYear){
        List<BudgetEntity> list = costBudgetForm.budgetDao.getListByProject(project.getId(), beginYear, endYear);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, Map<String, Object>> resultMap = new HashMap<>();
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
                resultMap.put(key, dataMap);
            }
            List<BudgetEntity> totalList = costBudgetForm.budgetDao.getTotal(project.getId());
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

    /**
     * ??????????????????
     * @return
     */
    public Map<String, Object> getProjectBudget(ProjectEntity projectEntity) {
        Map<String, Object> dataMap = new HashMap<>();
        Map<String, BigDecimal> budgetMap = costBudgetForm.budgetDao.getYearMonth(projectEntity.getCompanyId(), projectEntity.getId());
        if(budgetMap!=null){
            dataMap.putAll(budgetMap);
        }
        Map<String, Integer> summaryMap ;
        Date beginSummary=null;
        Date endSummary=null;
        int result = projectEntity.getEndDate().compareTo(new Date());
        BigDecimal percent=BigDecimal.ONE;
        if (result > 0) {
            //??????
            Map<String,Date> dateMap= costBudgetForm.summaryDao.getMinMaxMonth(projectEntity.getId());//???????????????????????????
            int sumMonth=0;
            if(dateMap!=null && dateMap.get("minMonth")!=null) {
                Long summaryMonth = DateUtil.betweenMonth(dateMap.get("minMonth"), dateMap.get("maxMonth"), true);//???????????????
                //Long projectMonth = DateUtil.betweenMonth(projectEntity.getBeginDate(), projectEntity.getEndDate(), true);//???????????????
                 sumMonth = summaryMonth.intValue() + 1;
                beginSummary=DateUtil.beginOfMonth(dateMap.get("minMonth"));
                endSummary=DateUtil.offsetMonth(beginSummary,2);
            }
            //int proMonth=projectMonth.intValue() + 1;
            //????????????=???????????? ?????????=????????? ????????????>3 ?????????=?????????*7 ????????????<3 ?????????=0
           if(sumMonth>=3){
                percent=new BigDecimal(7.00);
            }else{
                percent=BigDecimal.ZERO;
            }
        }
        List<ProjectBudgetModel> budget = costBudgetForm.summaryDao.getProjectSummary(projectEntity.getId(),beginSummary,endSummary);
        if (CollectionUtils.isEmpty(budget)) {
            return dataMap;
        }
        summaryMap=getBudgetData(budget,percent);
        dataMap.putAll(summaryMap);
        return dataMap;
    }

//  ??????2020?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
// ???????????????????????????????????????(????????????????????????5???0?????????????????????????????????????????????????????????????????????
// ??????????????????1234321.55???????????????120????????????264567.11????????????30??????????????????17454.23??????????????????2??????????????????12454.23?????????1???
//
    private Map<String, Integer> getBudgetData(List<ProjectBudgetModel> budget,BigDecimal percent){
        Map<String, Integer> summaryMap = new HashMap<>();
        int totalSummary=0;
        for (ProjectBudgetModel model : budget) {
            int summary = CommonUtils.computeBudget(model.getRdFunds().multiply(percent), Constant.TEN_THOUSAND);
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
}
