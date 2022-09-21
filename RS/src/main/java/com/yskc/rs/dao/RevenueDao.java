package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.RevenueEntity;
import com.yskc.rs.models.revenuemanage.RevenueModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @DateTime: 2022/1/14 8:28
 * @Description:
 * @author: hsx
 */
@Repository
public interface RevenueDao extends BaseMapper<RevenueEntity> {

    /**
     * 获取对应公司某一年的营收数据
     * @param year
     * @param companyId
     * @return
     */
    List<RevenueModel> getByYearAndCompanyId(@Param("year") Integer year,@Param("companyId") Integer companyId);

    /**
     * 以年份删除公司营收数据
     * @param year
     * @param companyId
     * @return
     */
    int del(@Param("year") Integer year, @Param("companyId") Integer companyId);

    /**
     * 检验是否重复添加
     * @param year
     * @param companyId
     * @return
     */
    List<RevenueEntity> checkout(@Param("year") Integer year, @Param("companyId") Integer companyId);

    /**
     * 批量插入
     * @param list
     * @return
     */
    int batchInsert(@Param("list") List<RevenueEntity> list);

    /**
     * 批量编辑
     *
     * @param list
     * @return
     */
    int batchUpdate(@Param("list") List<RevenueEntity> list);
}
