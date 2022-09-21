package com.xxl.job.executor.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.rs.PushAttendanceLog;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.dao.rs
 * @Author: zhangdingfu
 * @CreateTime: 2021-01-04 09:00
 * @Description: 推送考勤消息日志dao层
 */
@Repository
public interface PushAttendanceLogDao extends BaseMapper<PushAttendanceLog> {

    /**
     * 批量插入
     *
     * @param list
     * @return
     */
    int addBatch(List<PushAttendanceLog> list);

}
