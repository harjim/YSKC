package com.yskc.ms.service.ms;

import com.yskc.ms.entity.ms.SysSession;

/**
 * 会话服务
 * @author huronghua
 */
public interface SysSessionService {
    /**
     * 根据token获取会话
     * @param token
     * @return
     */
    SysSession getSessionByToken( String token)  ;

    /**
     * 更新会话信息
     * @param sysSession
     * @return
     */
    Integer UpdateSysSession(SysSession sysSession);
}
