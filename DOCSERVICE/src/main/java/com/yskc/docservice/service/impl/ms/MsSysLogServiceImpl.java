package com.yskc.docservice.service.impl.ms;

import com.yskc.docservice.dao.ms.MsSysLogDao;
import com.yskc.docservice.entity.ms.MsSysLog;
import com.yskc.docservice.service.ms.MsSysLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: DOCSERVICE
 * @BelongsPackage: com.yskc.docservice.service.impl.ms
 * @Author: zhangdingfu
 * @CreateTime: 2022-03-17 11:30
 * @Description: ms日志业务实现层
 */
@Service
public class MsSysLogServiceImpl implements MsSysLogService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MsSysLogDao msSysLogDao;

    @Override
    public void save(MsSysLog msSysLog) {
        if (null != msSysLog) {
            try {
                msSysLogDao.insert(msSysLog);
            } catch (Exception e) {
                logger.error("保存日志失败", e);
            }
        }
    }
}
