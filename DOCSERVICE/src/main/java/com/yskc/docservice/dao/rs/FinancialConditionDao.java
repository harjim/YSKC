package com.yskc.docservice.dao.rs;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.FinancialConditionEntity;
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
     * 获取公司年份的财务状况
     * @param companyId
     * @param years
     * @return
     */
    List<FinancialConditionEntity> getDataByTerm(@Param("companyId")Integer companyId, @Param("years")List<Integer> years);

    /**
     * 批量新增
     * @param list
     * @return
     */
    int batchInsert(@Param("list") List<FinancialConditionEntity> list);

    /**
     * 批量修改
     * @param list
     */
    int batchUpdate(@Param("list") List<FinancialConditionEntity> list);
}
