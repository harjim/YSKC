package com.yskc.rs.service;

import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.YearCost.YearCostExModel;

import java.util.List;

public interface YearCostService {
    /**
     * 获取年度成本(废弃)
     *
     * @param year
     * @param companyId
     * @return
     */
    YearCostExModel getYearCost(Integer year,Integer companyId);

    /**
     * 按公司获取月度成本列表
     *
     * @param companyId
     * @return
     */
    List<YearCostExModel> getList(Integer companyId);

    /**
     * 保存月度成本信息
     *
     * @param model
     * @param info
     * @return
     */
    Boolean saveYearCost(YearCostExModel model, UserInfo info);

    /**
     * 批量删除记录
     *
     * @param years ：[{year, month}]
     * @param info
     * @return
     */
    Boolean delYearCost(List< Object > years, UserInfo info);

    /**
     * 获取月度成本
     *
     * @param year
     * @param companyId
     * @return
     */
    YearCostExModel getMonthCost( Integer year, Integer month, Integer companyId );
}
