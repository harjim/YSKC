package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.MonthlyReportDetail;
import com.yskc.ms.enums.DailyReportEnum;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.dailyreport.QueryDailyReportModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.dao.ms
 * @Author: zhangdingfu
 * @CreateTime: 2021-08-10 09:39
 * @Description: 月统计
 */
@Repository
public interface MonthlyReportDetailDao extends BaseMapper<MonthlyReportDetail> {

    /**
     * 按月获取
     *
     * @param page
     * @param query
     * @param perm
     * @param types
     * @return
     */
    List<Map<String, Object>> getMonthList(@Param("page") Pagination page, @Param("query") QueryDailyReportModel query,
                                           @Param("perm") DataPermModel perm, @Param("types") List<DailyReportEnum> types);
}
