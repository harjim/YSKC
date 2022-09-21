package com.yskc.docservice.service.rd.doc;

import com.yskc.common.utils.JsonUtils;
import com.yskc.docservice.dao.rs.BudgetDao;
import com.yskc.docservice.dao.rs.SummaryDao;
import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.models.rs.project.ProjectBudgetModel;
import com.yskc.docservice.service.rd.RDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hck
 * on 2020/7/4 11:13
 * description:项目费用决算报告
 */
@Component("FinalProjectCostForm_Doc")
@Scope("prototype")
public class FinalProjectCostFormDocument extends RDDocument {

    @Autowired
    private BudgetDao budgetDao;
    @Autowired
    private SummaryDao summaryDao;

    @Override
    protected Map getDocMap(){
        Map<String, Object> map = new HashMap<>();
        ProjectEntity projectInfo = this.dataFactory.getProjectInfo();
        Map<String, BigDecimal> budgetMap = budgetDao.getYearMonth(projectInfo.getCompanyId(), projectInfo.getId());
        if (budgetMap != null) {
            map.putAll(budgetMap);
        }
        List<ProjectBudgetModel> budget = summaryDao.getProjectSummary(projectInfo.getId(), null, null);
        if (!CollectionUtils.isEmpty(budget)) {
            Map<String, BigDecimal> summaryMap = new HashMap<>();
            BigDecimal totalSummary = BigDecimal.ZERO;
            for (ProjectBudgetModel model : budget) {
                Integer rdType = model.getRdType();
                //.divide(Constant.TEN_THOUSAND, 2, BigDecimal.ROUND_HALF_UP);
                BigDecimal summary = model.getRdFunds();
                String key = "t" + rdType / 100 * 100;
                if (!summaryMap.containsKey(key)) {
                    summaryMap.put(key, summary);
                } else {
                    summaryMap.put(key, summaryMap.get(key).add(summary));
                }
                String preKey = "t" + rdType / 10000 * 100;
                if (!summaryMap.containsKey(preKey)) {
                    summaryMap.put(preKey, summary);
                } else {
                    summaryMap.put(preKey, summaryMap.get(preKey).add(summary));
                }
                totalSummary = totalSummary.add(summary);
            }
            summaryMap.put("spending01", totalSummary);
            map.putAll(summaryMap);
        }
        return map;
    }

    @Override
    protected Map getDocMap(ProjectEntity project, Integer currentYear) {
        Map<String, Object> map = new HashMap<>();
        if (!StringUtils.isEmpty(this.docFile.getData())) {
            //项目人员年度工时统计表
            map = JsonUtils.jsonToPojo(this.docFile.getData(), Map.class);

        }
        getBudgetCost(project, map);
        return map;
    }

    public void getBudgetCost(ProjectEntity projectEntity, Map<String, Object> map) {
        Map<String, BigDecimal> budgetMap = budgetDao.getYearMonth(projectEntity.getCompanyId(), projectEntity.getId());
        if (budgetMap != null) {
            map.putAll(budgetMap);
        }
        List<ProjectBudgetModel> budget = summaryDao.getProjectSummary(projectEntity.getId(), null, null);
        if (!CollectionUtils.isEmpty(budget)) {
            Map<String, BigDecimal> summaryMap = new HashMap<>();
            BigDecimal totalSummary = BigDecimal.ZERO;
            for (ProjectBudgetModel model : budget) {
                Integer rdType = model.getRdType();
                //.divide(Constant.TEN_THOUSAND, 2, BigDecimal.ROUND_HALF_UP);
                BigDecimal summary = model.getRdFunds();
                String key = "t" + rdType / 100 * 100;
                if (!summaryMap.containsKey(key)) {
                    summaryMap.put(key, summary);
                } else {
                    summaryMap.put(key, summaryMap.get(key).add(summary));
                }
                String preKey = "t" + rdType / 10000 * 100;
                if (!summaryMap.containsKey(preKey)) {
                    summaryMap.put(preKey, summary);
                } else {
                    summaryMap.put(preKey, summaryMap.get(preKey).add(summary));
                }
                totalSummary = totalSummary.add(summary);
            }
            summaryMap.put("spending01", totalSummary);
            map.putAll(summaryMap);
        }
    }

}
