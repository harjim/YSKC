package com.yskc.ms.service.rs;

import com.yskc.ms.models.SysDictionaryModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: wangxing
 * @CreateTime: 2019-10-29 11:04
 * @Description: 项目资金预算接口
 */
public interface BudgetService {
    /**
     * @param projectId
     * @return
     */
    Map<String, Object> queryBudgetList(Integer projectId);

    /**
     * @return
     */

    List<SysDictionaryModel> getSysDictionaryModel();

    /**
     * 查询项目各月资金预算
     *
     * @param projectId
     * @return
     */
    Map<String, BigDecimal> queryAllBudget(Integer projectId);
}
