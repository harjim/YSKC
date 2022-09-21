package com.yskc.rs.service;

import com.yskc.rs.entity.SysLog;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-13 08:23
 * @Description: 日志业务层接口
 */
public interface SysLogService {

    /**
     * 保存日志
     * @param sysLog
     */
    void save(SysLog sysLog);
}
