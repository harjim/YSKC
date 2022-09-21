package com.yskc.rs.service.impl;

import com.yskc.rs.dao.SysLogDao;
import com.yskc.rs.entity.SysLog;
import com.yskc.rs.service.SysLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-13 08:24
 * @Description: 日志业务层实现
 */
@Service
public class SysLogServiceImpl implements SysLogService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysLogDao sysLogDao;

    @Override
    public void save(SysLog sysLog) {
        try {
            sysLogDao.insert(sysLog);
        } catch (Exception e) {
            logger.error("保存日志失败", e);
        }
    }
}
