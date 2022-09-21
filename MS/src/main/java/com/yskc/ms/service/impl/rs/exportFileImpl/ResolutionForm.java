package com.yskc.ms.service.impl.rs.exportFileImpl;

import cn.hutool.core.date.DateUtil;
import com.yskc.common.utils.JsonUtils;
import com.yskc.ms.dao.rs.ProjectYearInfoDao;
import com.yskc.ms.entity.rs.ProjectEntity;
import com.yskc.ms.entity.rs.ProjectYearInfoEntity;
import com.yskc.ms.models.rs.DataModel;
import com.yskc.ms.service.rs.ExportDocFileService;
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
 * @Author: hck
 * @DateTime: 2021/4/9 14:49
 * @Description:立项决议
 */
@Component
public class ResolutionForm implements ExportDocFileService {

    @Autowired
    private ProjectYearInfoDao projectYearInfoDao;

    public static ResolutionForm resolutionForm;

    @PostConstruct
    public void init() {
        resolutionForm = this;
        resolutionForm.projectYearInfoDao = this.projectYearInfoDao;
    }

    @Override
    public Map<String, Object> exportDocFile(ProjectEntity projectEntity, Integer currentYear, String fileData, DataModel dataModel) {
        Map<String, Object> dataMap = new HashMap<>();
        if (!StringUtils.isEmpty(fileData)) {
            dataMap = JsonUtils.jsonToPojo(fileData, Map.class);
        }
        String beginDate = DateUtil.format(projectEntity.getBeginDate(), "yyyy年MM月");
        String endDate = DateUtil.format(projectEntity.getEndDate(), "yyyy年MM月");
        dataMap.put("beginDate", beginDate);
        dataMap.put("endDate", endDate);
//        CostBudgetForm costBudgetForm = new CostBudgetForm();
//        Map<String, Object> budgetMap = costBudgetForm.getProjectBudget(projectEntity);
        //dataMap.put("budget", budgetMap != null && budgetMap.containsKey("spending01") ? budgetMap.get("spending01") : BigDecimal.ZERO);
        //       dataMap.put("budget", projectEntity.getEstimateExpense() != null ? projectEntity.getEstimateExpense() : BigDecimal.ZERO);
        List<ProjectYearInfoEntity> yearInfos = resolutionForm.projectYearInfoDao.getInfoByYear(projectEntity.getId(), projectEntity.getEndYear());
        BigDecimal totalBudget = BigDecimal.ZERO;
        if (!CollectionUtils.isEmpty(yearInfos)) {
            for (ProjectYearInfoEntity info : yearInfos) {
                totalBudget = totalBudget.add(info.getBudget());
            }
        }
        dataMap.put("budget", totalBudget);
        return dataMap;
    }
}
