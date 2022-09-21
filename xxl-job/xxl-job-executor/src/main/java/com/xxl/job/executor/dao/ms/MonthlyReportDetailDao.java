package com.xxl.job.executor.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.ms.MonthlyReportDetail;
import com.xxl.job.executor.models.monthlyreport.MonthlyReportDetailModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.dao.ms
 * @Author: zhangdingfu
 * @CreateTime: 2021-08-10 09:39
 * @Description: 月统计
 */
@Repository
public interface MonthlyReportDetailDao extends BaseMapper<MonthlyReportDetail> {
    /**
     * 插入或更新
     *
     * @param list
     * @param now
     * @return
     */
    int insertOrUpdate(@Param("list") List<MonthlyReportDetailModel> list,@Param("now") Date now);
}
