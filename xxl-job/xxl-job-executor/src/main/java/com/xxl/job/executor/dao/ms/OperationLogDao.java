package com.xxl.job.executor.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.ms.OperationLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.dao.ms
 * @Author: zhangdingfu
 * @CreateTime: 2021-10-30 10:04
 * @Description: 操作日志统计dao
 */
@Repository
public interface OperationLogDao extends BaseMapper<OperationLog> {
    /**
     * 插入或更新
     * @param list
     * @return
     */
    int insertOrUpdate(@Param("list") List<OperationLog> list);
}
