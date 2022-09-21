package com.xxl.job.executor.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.ms.DailyReportDetail;
import com.xxl.job.executor.models.dailyreport.DailyReportDetailModel;
import com.xxl.job.executor.models.monthlyreport.MonthlyReportDetailModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.dao.ms
 * @Author: zhangdingfu
 * @CreateTime: 2021-08-03 16:45
 * @Description: 用户审核汇总dao层
 */
@Repository
public interface DailyReportDetailDao extends BaseMapper<DailyReportDetail> {
    /**
     * 插入或更新
     *
     * @param list
     * @param now
     * @return
     */
    int insertOrUpdate(@Param("list") List<DailyReportDetailModel> list, @Param("now") Date now);

    /**
     * 获取月份数据
     * @param month
     * @return
     */
    List<MonthlyReportDetailModel> getMonthlyData(@Param("month") Date month);
}
