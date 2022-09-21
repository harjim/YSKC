package com.yskc.ms.service.impl.rs;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yskc.common.utils.DateUtil;
import com.yskc.ms.config.Constant;
import com.yskc.ms.dao.rs.BudgetDao;
import com.yskc.ms.dao.rs.RsProjectDao;
import com.yskc.ms.dao.rs.SysDictionaryDao;
import com.yskc.ms.entity.rs.BudgetEntity;
import com.yskc.ms.entity.rs.ProjectEntity;
import com.yskc.ms.models.SysDictionaryModel;
import com.yskc.ms.models.rs.BudgetModel;
import com.yskc.ms.service.impl.rs.exportFileImpl.CostBudgetForm;
import com.yskc.ms.service.rs.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: wangxing
 * @CreateTime: 2019-10-29 11:05
 * @Description: 项目资金预算业务层
 */
@Service
public class BudgetServiceImpl extends ServiceImpl<BudgetDao, BudgetEntity> implements BudgetService {
    @Autowired
    private BudgetDao budgetDao;
    @Autowired
    private SysDictionaryDao sysDictionaryDao;
    @Autowired
    private RsProjectDao rsProjectDao;

    /**
     * @param projectId
     * @return
     */
    @Override
    public Map<String, Object> queryBudgetList(Integer projectId) {
        ProjectEntity projectEntity = rsProjectDao.selectById(projectId);
        CostBudgetForm costBudgetForm = new CostBudgetForm();
        Map<String, Object> result = costBudgetForm.getProjectBudget(projectEntity);
        return result;
    }


    /**
     * @return
     */
    @Override
    public List<SysDictionaryModel> getSysDictionaryModel() {
        return sysDictionaryDao.getDictionaryByBudgetType();
    }

    @Override
    public Map<String, BigDecimal> queryAllBudget(Integer projectId) {
        List<BudgetModel> models = budgetDao.queryAllBudget(projectId, false);
        Map<String, BigDecimal> budgetMap = new HashMap<>();
        for (BudgetModel model : models) {
            Date month = model.getMonth();
            if (month == null) {
                budgetMap.put("总预算", model.getValue().divide(Constant.TEN_THOUSAND, BigDecimal.ROUND_HALF_UP));
            } else {
                budgetMap.put(DateUtil.format(month, DateUtil.DEFAULT_YYMM_FORMAT), model.getValue().divide(Constant.TEN_THOUSAND, BigDecimal.ROUND_HALF_UP));
            }
        }
        return budgetMap;
    }


}
