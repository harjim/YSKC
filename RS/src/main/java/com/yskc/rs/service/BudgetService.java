package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.rs.models.SysDictionaryModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.project.BudgetAddandUpdateModel;
import com.yskc.rs.models.project.BudgetInfoModel;
import com.yskc.rs.models.project.ReportBudgetModel;
import com.yskc.rs.models.summary.TotalSummaryModel;

import java.math.BigDecimal;
import java.util.Date;
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
    Map<String, Map<String,Object>> queryBudget(Integer projectId, Boolean fromBudget);

    /**
     * @param info
     * @param model
     * @return
     * @throws OwnerException
     */
    boolean update(UserInfo info, BudgetAddandUpdateModel model) throws OwnerException;

    /**
     * @return
     */

    List<SysDictionaryModel> getSysDictionaryModel();

    /**
     * @param year
     * @return
     */
    List<TotalSummaryModel> getSummaryBudget(Integer companyId,Integer year);


    /**
     * 查询项目各月资金预算
     *
     * @param projectId
     * @return
     */
    Map<String, Object> queryAllBudget(Integer projectId);

    List<ReportBudgetModel> queryProjectBudget(Integer companyId, Integer projectId);

    /**
     * 获取总预算
     *
     * @param projectId
     * @return
     */
    BigDecimal queryTotalBudget(Integer projectId);

    /**
     * 保存项目经费来源预算
     * @param userInfo
     * @param model
     * @return
     */
    boolean save(UserInfo userInfo, BudgetInfoModel model) throws OwnerException;
}
