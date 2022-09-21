package com.yskc.docservice.service.ms;

import com.yskc.docservice.entity.ms.MsSysLog;

/**
 * @BelongsProject: DOCSERVICE
 * @BelongsPackage: com.yskc.docservice.service.ms
 * @Author: zhangdingfu
 * @CreateTime: 2022-03-17 11:29
 * @Description: ms日志业务接口层
 */
public interface MsSysLogService {

    /**
     * 保存日志
     * @param msSysLog
     */
    void save(MsSysLog msSysLog);
}
