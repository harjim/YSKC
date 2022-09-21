package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.YearCostEntity;
import com.yskc.rs.models.YearCost.YearCostExModel;
import com.yskc.rs.models.YearCost.YearCostModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YearCostDao extends BaseMapper<YearCostEntity> {
    /**
     * 获取年度信息(废弃)
     *
     * @param companyId
     * @param year
     * @return
     */
    List<YearCostModel> getListByYear(@Param("companyId") Integer companyId,@Param("year")Integer year);

    /**
     * 获取月度信息
     *
     * @param companyId
     * @param year
     * @param month
     * @return
     */
    YearCostExModel getListByMonth(@Param("year") Integer year,@Param("month") Integer month,@Param("companyId") Integer companyId );

    /**
     * 获取月度信息列表
     *
     * @param companyId
     * @return
     */
    List< YearCostExModel > getList( @Param("companyId") Integer companyId);

    /**
     * 保存月度信息
     *
     * @param companyId
     * @return
     */
    Integer saveYearCost(@Param("list") List<YearCostModel> list,@Param("companyId") Integer companyId);

    /**
     * 保存月度信息 -删除金额为空的数据
     *
     * @param companyId
     * @return
     */
    Integer delYearCost(@Param("list") List<YearCostModel> list,@Param("companyId") Integer companyId);

    /**
     * 批量删除数据
     *
     * @param companyId
     * @return
     */
    Integer batchDelYearCost(@Param("years") List< Object > years, @Param("companyId") Integer companyId);
}
