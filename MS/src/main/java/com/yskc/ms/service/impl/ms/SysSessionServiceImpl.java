package com.yskc.ms.service.impl.ms;

import com.yskc.ms.dao.ms.SysSessionDao;
import com.yskc.ms.entity.ms.SysSession;
import com.yskc.ms.service.ms.SysSessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 会话服务
 * @author huronghua
 */
@Service
public class SysSessionServiceImpl implements SysSessionService {
    @Autowired
    private SysSessionDao sysSessionDao;

    /**
     * 获取会话信息
     * @param token
     * @return
     */
    @Override
    public SysSession getSessionByToken(String token)  {
        return sysSessionDao.getSessionByToken(token);
    }

    /**
     * 更新会话
     * @param sysSession
     * @return
     */
    @Override
    public Integer UpdateSysSession(SysSession sysSession) {
        return sysSessionDao.updateById(sysSession);
    }
}
