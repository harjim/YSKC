package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.DailyReportDetail;
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
 * @CreateTime: 2021-08-03 16:45
 * @Description: 用户审核汇总dao层
 */
@Repository
public interface DailyReportDetailDao extends BaseMapper<DailyReportDetail> {
    /**
     * 按日获取
     *
     * @param page
     * @param query
     * @param perm
     * @param types
     * @return
     */
    List<Map<String, Object>> getDayList(@Param("page") Pagination page, @Param("query") QueryDailyReportModel query,
                                         @Param("perm") DataPermModel perm, @Param("types") List<DailyReportEnum> types);
}
