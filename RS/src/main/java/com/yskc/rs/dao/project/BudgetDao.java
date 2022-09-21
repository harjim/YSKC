package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.project.BudgetEntity;
import com.yskc.rs.models.project.BudgetModel;
import com.yskc.rs.models.project.ReportBudgetModel;
import com.yskc.rs.models.summary.TotalSummaryModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-10-29 11:00:28
 */
@Repository
public interface BudgetDao extends BaseMapper<BudgetEntity> {
    /**
     * @param companyId
     * @param projectId
     * @param month
     * @return
     */
    List<BudgetModel> querySourceBudget(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId, @Param("month") Date month);

    List<ReportBudgetModel> queryProjectBudget(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId);

    /**
     * 根据年份查询所有
     *
     * @param projectIds
     * @return
     */
    List<TotalSummaryModel> getBudgetByProjectIds(@Param("projectIds") List<Integer> projectIds);


    /**
     * 批量插入
     *
     * @param budgetEntitys
     * @return
     */
    Integer addBatch(@Param("budgetEntitys") List<BudgetEntity> budgetEntitys);


    /**
     * 批量修改
     *
     * @param budgetEntitys
     * @return
     */
    Integer updateBatch(@Param("budgetEntitys") List<BudgetEntity> budgetEntitys);

    /**
     * 查询项目各月资金预算
     *
     * @param projectId
     * @param onlyYear
     * @return
     */
    List<BudgetModel> queryAllBudget(@Param("projectId") Integer projectId, @Param("onlyYear") Boolean onlyYear);

    /**
     * 获取其他月份数据
     *
     * @param companyId
     * @param projectId
     * @param month
     * @return
     */
    Map<String, BigDecimal> getOtherMonth(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId, @Param("month") Date month);

    Map<String, BigDecimal> getYearMonth(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId);

    /**
     * 获取年预算
     *
     * @param companyId
     * @param year
     * @return
     */
    Map<String, BigDecimal> getYearBudget(@Param("companyId") Integer companyId, @Param("year") Integer year);

    Integer getBudgetStatus(@Param("projectId") Integer projectId);

    /**
     * 获取经费来源预算
     * @param projectId
     * @return
     */
    List<BudgetEntity> getByProject(@Param("projectId") Integer projectId);

    /**
     * 获取经费支出预算
     * @param projectId
     * @param beginYear
     * @param endYear
     * @return
     */
    List<BudgetEntity> getListByProject(@Param("projectId") Integer projectId,@Param("beginYear") Integer beginYear, @Param("endYear") Integer endYear);

    /**
     * 获取经费支出预算统计
     * @param projectId
     * @return
     */
    List<BudgetEntity> getTotal(@Param("projectId") Integer projectId);
}
