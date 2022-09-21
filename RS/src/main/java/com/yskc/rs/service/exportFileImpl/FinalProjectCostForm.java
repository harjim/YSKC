package com.yskc.rs.service.exportFileImpl;

import com.yskc.common.utils.JsonUtils;
import com.yskc.rs.dao.SummaryDao;
import com.yskc.rs.dao.project.BudgetDao;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.models.document.DataModel;
import com.yskc.rs.models.project.ProjectBudgetModel;
import com.yskc.rs.service.ExportDocFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hck
 * on 2020/7/4 11:13
 * description:
 */
@Component
public class FinalProjectCostForm implements ExportDocFileService {

    @Autowired
    private BudgetDao budgetDao;
    @Autowired
    private SummaryDao summaryDao;

    public static FinalProjectCostForm finalProjectCostForm;

    @PostConstruct
    public void init() {
        finalProjectCostForm = this;
        finalProjectCostForm.budgetDao = this.budgetDao;
        finalProjectCostForm.summaryDao = this.summaryDao;
    }


    @Override
    public Map<String, Object> exportDocFile(ProjectEntity projectEntity, Integer currentYear, String fileData, DataModel dataModel) {
        Map<String, Object> dataMap = new HashMap<>();
        if (!StringUtils.isEmpty(fileData)) {
            //项目人员年度工时统计表
            dataMap = JsonUtils.jsonToPojo(fileData, Map.class);

        }
        getBudgetCost(projectEntity, dataMap);
        return dataMap;

    }

    public void getBudgetCost(ProjectEntity projectEntity, Map<String, Object> dataMap) {
        Map<String, BigDecimal> budgetMap = finalProjectCostForm.budgetDao.getYearMonth(projectEntity.getCompanyId(), projectEntity.getId());
        if (budgetMap != null) {
            dataMap.putAll(budgetMap);
        }
        List<ProjectBudgetModel> budget = finalProjectCostForm.summaryDao.getProjectSummary(projectEntity.getId(), null, null);
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
            dataMap.putAll(summaryMap);
        }
    }

}
