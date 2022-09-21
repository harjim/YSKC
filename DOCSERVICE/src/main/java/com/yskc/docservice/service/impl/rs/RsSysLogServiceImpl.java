package com.yskc.docservice.service.impl.rs;

import com.yskc.docservice.dao.rs.RsSysLogDao;
import com.yskc.docservice.entity.ms.RsSysLog;
import com.yskc.docservice.service.rs.RsSysLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: DOCSERVICE
 * @BelongsPackage: com.yskc.docservice.service.impl.rs
 * @Author: zhangdingfu
 * @CreateTime: 2022-03-17 11:29
 * @Description: rs日志业务实现层
 */
@Service
public class RsSysLogServiceImpl implements RsSysLogService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RsSysLogDao rsSysLogDao;

    @Override
    public void save(RsSysLog rsSysLog) {
        if (null != rsSysLog) {
            try {
                rsSysLogDao.insert(rsSysLog);
            } catch (Exception e) {
                logger.error("保存日志失败", e);
            }
        }
    }
}
