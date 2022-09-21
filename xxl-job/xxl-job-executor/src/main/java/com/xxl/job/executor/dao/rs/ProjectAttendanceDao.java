package com.xxl.job.executor.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.rs.ProjectAttendance;
import com.xxl.job.executor.models.attendance.TotalAttendanceModel;
import com.xxl.job.executor.models.wechat.AccountPeriodModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 研发人员Dao
 *
 * @author huronghua
 */
@Repository
public interface ProjectAttendanceDao extends BaseMapper<ProjectAttendance> {
    /**
     * 获取月工时（计划工时及总工时）
     *
     * @param list
     * @return
     */
    List<TotalAttendanceModel> getMonthHour(@Param("list") List<AccountPeriodModel> list);
}
