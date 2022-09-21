package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.FinancialConditionEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * 
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-09-25 11:23:27
 */
@Repository
public interface FinancialConditionDao extends BaseMapper<FinancialConditionEntity> {
    /**
     *
     * @param companyId
     * @param year
     * @return
     */
    List<FinancialConditionEntity>  queryFinancialCondition(@Param("companyId")Integer companyId,@Param("year")Integer year);

    /**
     * 获取公司年份的财务状况
     * @param companyId
     * @param years
     * @return
     */
    List<FinancialConditionEntity> getDataByTerm(@Param("companyId")Integer companyId,@Param("years")List<Integer> years);

    /**
     *
     * @param entity
     * @return
     */
    Integer updateFinancial(@Param("entity") FinancialConditionEntity entity);

    boolean deleteByYear(@Param("companyId")Integer companyId,@Param("year")Integer year);
}
