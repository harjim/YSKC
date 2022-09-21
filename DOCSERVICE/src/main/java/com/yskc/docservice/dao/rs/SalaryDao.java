package com.yskc.docservice.dao.rs;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.SalaryEntity;
import com.yskc.docservice.models.rs.excel.SalaryExcel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-11 09:04:31
 */
@Repository
public interface SalaryDao extends BaseMapper<SalaryEntity> {

    /**
     * 查询同一公司同一员工编号同月份是否存在
     *
     * @param companyId
     * @param queryModels
     * @return
     */
    List<SalaryEntity> getDataByTerm(@Param("companyId") Integer companyId, @Param("queryModels") List<SalaryExcel> queryModels);

    /**
     * 批量插入
     *
     * @param salaryEntitielist
     * @return
     */
    Integer addBatch(@Param("salaryEntitielist") List<SalaryEntity> salaryEntitielist);

    /**
     * 批量修改
     *
     * @param salaryEntityList
     * @return
     */
    Integer updateBatch(@Param("salaryEntityList") List<SalaryEntity> salaryEntityList);
}
