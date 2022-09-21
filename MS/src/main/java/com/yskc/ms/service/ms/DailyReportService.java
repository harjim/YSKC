package com.yskc.ms.service.ms;

import com.yskc.common.model.PageModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.dailyreport.QueryDailyReportModel;

import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service.ms
 * @Author: zhangdingfu
 * @CreateTime: 2021-08-06 14:30
 * @Description: 工作报表业务接口层
 */
public interface DailyReportService {
    /**
     * 按日获取
     *
     * @param query
     * @param dataPerm
     * @return
     */
    PageModel<List<Map<String, Object>>> getDayList(QueryDailyReportModel query, DataPermModel dataPerm);
}
