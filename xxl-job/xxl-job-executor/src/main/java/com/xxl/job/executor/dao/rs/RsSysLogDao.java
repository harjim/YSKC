package com.xxl.job.executor.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.ms.OperationLog;
import com.xxl.job.executor.entity.rs.RsSysLogEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.dao.rs
 * @Author: zhangdingfu
 * @CreateTime: 2021-10-30 10:22
 * @Description: rs日志Dao层
 */
@Repository
public interface RsSysLogDao extends BaseMapper<RsSysLogEntity> {
    /**
     * 获取最后操作记录
     *
     * @param lastTime
     * @return
     */
    List<OperationLog> getLastOperation(@Param("lastTime") Date lastTime);
}
