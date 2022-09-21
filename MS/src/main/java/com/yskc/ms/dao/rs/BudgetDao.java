package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.BudgetEntity;
import com.yskc.ms.models.rs.BudgetModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Repository
public interface BudgetDao extends BaseMapper<BudgetEntity> {
    /**
     * @param projectId
     * @param month
     * @return
     */
    List<BudgetModel> querySourceBudget(@Param("projectId") Integer projectId, @Param("month") Date month);

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
     * @param projectId
     * @param month
     * @return
     */
    Map<String, BigDecimal> getOtherMonth(@Param("projectId") Integer projectId, @Param("month") Date month);

    Map<String, BigDecimal> getYearMonth(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId);


    /**
     * 获取经费支出预算
     * @param projectId
     * @return
     */
    List<BudgetEntity> getListByProject(@Param("projectId") Integer projectId);

    /**
     * 获取经费支出预算统计
     * @param projectId
     * @return
     */
    List<BudgetEntity> getTotal(@Param("projectId") Integer projectId);
}
