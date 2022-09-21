package com.xxl.job.executor.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.ms.FlowInstanceLog;
import com.xxl.job.executor.models.dailyreport.DailyReportDetailModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/12/26 9:17
 * description:
 */
@Repository
public interface FlowInstanceLogDao extends BaseMapper<FlowInstanceLog> {
    /**
     * 获取最新数据
     *
     * @param begin
     * @param end
     * @return
     */
    List<DailyReportDetailModel> getLastData(@Param("begin") Date begin, @Param("end") Date end);
}
