package com.yskc.rs.dao;

import com.yskc.rs.models.reportform.ComputeModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface ReportFormDao {
    Map<String,Integer> getIndex(@Param("companyId")Integer companyId,@Param("year")Integer year);
    Map<String,BigDecimal> getMonthData(@Param("companyId")Integer companyId, @Param("year")Integer year, @Param("month") Date month, @Param("type")Integer type);
    Map<String,BigDecimal> getYearCost(@Param("companyId")Integer companyId,@Param("year")Integer year);
    Map<String,BigDecimal> getLastYearData(@Param("companyId")Integer companyId, @Param("year")Integer year, @Param("month")Date month,@Param("type")Integer type);
    List<Map<String,Object>> getMonthReport(@Param("companyId")Integer companyId, @Param("year")Integer year);
}
