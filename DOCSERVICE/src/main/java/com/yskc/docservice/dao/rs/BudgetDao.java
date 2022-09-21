package com.yskc.docservice.dao.rs;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.BudgetEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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
     * 批量插入
     *
     * @param budgetEntitys
     * @return
     */
    Integer addBatch(@Param( "budgetEntitys" ) List<BudgetEntity> budgetEntitys);


    /**
     * 批量修改
     *
     * @param budgetEntitys
     * @return
     */
    Integer updateBatch(@Param( "budgetEntitys" ) List<BudgetEntity> budgetEntitys);

    /**
     * 获取经费支出预算
     * @param projectId
     * @return
     */
    /*List<BudgetEntity> getListByProject(@Param("projectId") Integer projectId);*/

    /**
     * 获取经费支出预算
     *
     * @param projectId
     * @param beginYear
     * @param endYear
     * @return
     */
    List<BudgetEntity> getListByProject(@Param( "projectId" ) Integer projectId, @Param( "beginYear" ) Integer beginYear, @Param( "endYear" ) Integer endYear);


    /**
     * 获取经费支出预算统计
     *
     * @param projectId
     * @return
     */
    List<BudgetEntity> getTotal(@Param( "projectId" ) Integer projectId);

    Map<String, BigDecimal> getYearMonth(@Param( "companyId" ) Integer companyId, @Param( "projectId" ) Integer projectId);
}
