package com.yskc.ms.service.ms;

import com.yskc.common.model.PageModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.dailyreport.QueryDailyReportModel;

import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service
 * @Author: zhangdingfu
 * @CreateTime: 2021-08-10 10:38
 * @Description: 月报业务接口层
 */
public interface MonthlyReportService {
    /**
     * 获取月份列表
     *
     * @param query
     * @param dataPerm
     * @return
     */
    PageModel<List<Map<String, Object>>> getMonthList(QueryDailyReportModel query, DataPermModel dataPerm);
}
