package com.xxl.job.executor.dao.ms;

import com.xxl.job.executor.models.customer.CustomerStatusModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CustomerTraceDao {
    /**
     * 逾期未跟进
     * @param inviteDate
     * @param visitDate
     * @param info
     * @return
     */
    Boolean insertInfoList(@Param("inviteDate") Date inviteDate, @Param("visitDate")Date visitDate,
                           @Param("info")String info);
}
