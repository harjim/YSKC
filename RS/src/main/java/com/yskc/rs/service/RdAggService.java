package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.rs.models.rdAgg.AggSummaryModel;

import java.util.List;
import java.util.Map;

/**
 * @author wjy
 */
public interface RdAggService {

    /**
     * 归集总表
     * @param companyId
     * @param year
     * @return
     * @throws OwnerException
     */
    Map<String, Object> getDataReportFunds(Integer companyId, Integer year) throws OwnerException;

    /**
     * 项目归集数据
     * @param year
     * @param projectId
     * @return
     * @throws OwnerException
     */
    Map<String, Object> getProjectDataReport(Integer year,Integer projectId) throws OwnerException;

    /**
     * 按年、费用类型获取归集数据
     * @param year
     * @param rdType
     * @return
     * @throws OwnerException
     */
    List< AggSummaryModel > getMonthCostByRdType(Integer year, Integer rdType, Integer companyId);
}
