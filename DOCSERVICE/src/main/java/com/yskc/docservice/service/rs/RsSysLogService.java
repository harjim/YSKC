package com.yskc.docservice.service.rs;

import com.yskc.docservice.entity.ms.RsSysLog;

/**
 * @BelongsProject: DOCSERVICE
 * @BelongsPackage: com.yskc.docservice.service.rs
 * @Author: zhangdingfu
 * @CreateTime: 2022-03-17 11:28
 * @Description: rs日志业务接口层
 */
public interface RsSysLogService {

    /**
     * 保存日志
     * @param rsSysLog
     */
    void save(RsSysLog rsSysLog);
}
