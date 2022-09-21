package com.xxl.job.executor.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.ms.ServiceLog;
import com.xxl.job.executor.models.monthlyreport.MonthlyReportDetailModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/5/5 14:41
 * 客户服务记录
 */
@Repository
public interface ServiceLogDao extends BaseMapper<ServiceLog> {

    /**
     * 服务统计
     * @param begin
     * @return
     */
    List<MonthlyReportDetailModel> getServiceCnt(@Param("begin") Date begin);
}
