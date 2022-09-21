package com.yskc.ms.service.rs;

import com.yskc.ms.models.CustomerTotalModel;
import com.yskc.ms.models.projectsummary.QuerySummaryMonthModel;
import com.yskc.ms.models.projectsummary.SummaryDataMonthModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service.rs
 * @Author: zhangdingfu
 * @CreateTime: 2020-06-18 13:51
 * @Description: 归集汇总业务接口层
 */
public interface SummaryService {
    /**
     * 获取客户数/项目数/归集额
     * @return
     */
    CustomerTotalModel getCustomerTotal();
    /**
     * 按月份项目费用汇总(人员，设备...)
     */
    List<SummaryDataMonthModel> getMonthFee(QuerySummaryMonthModel query);

    /**
     * 按年获取部门区域归集费
     * @param year
     * @return
     */
    List<Map<String, Object>> getDistrictSummary(Integer year);

    /**
     * 获取分公司年客户数/项目数/归集额
     * @param year
     * @return
     */
    List<List<Map<String, Object>>>  getBranchTotal(Integer year);

    /**
     * 统计研发费用
     * @param year
     * @return
     */
    List<Map<String,Object>> getSummaryCost(Integer year);

    /**
     * 按年获取项目总费用支出
     * @param projectIds
     * @param year
     * @return
     */
    Map<Integer,Map<String, BigDecimal>> getFundByYearAndProId(List<Integer> projectIds, Integer year);
}
