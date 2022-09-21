package com.yskc.rs.service.impl;

import cn.hutool.core.util.IdUtil;
import com.yskc.common.enums.FlowModuleTypeEnum;
import com.yskc.common.model.AuditParamsModel;
import com.yskc.common.utils.JsonUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.dao.KafkaQueueDao;
import com.yskc.rs.entity.KafkaQueueEntity;
import com.yskc.rs.service.KafkaQueueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: zhangdingfu
 * @CreateTime: 2021-03-16 08:18
 * @Description: kafka队列业务实现层
 */
@Service
public class KafkaQueueServiceImpl implements KafkaQueueService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    // 终端ID
    private final static int WORKERID = 1;
    // 数据中心ID
    private final static int DATACENTERID = 1;

    @Autowired
    private KafkaQueueDao kafkaQueueDao;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public boolean submitAudit(List<Integer> ids, Integer msUserId, FlowModuleTypeEnum moduleType) {
        String key = IdUtil.getSnowflake(WORKERID, DATACENTERID).nextIdStr();
        String params = JsonUtils.objectToJson(new AuditParamsModel(ids, msUserId, key, moduleType.getType()));
        try {
            kafkaQueueDao.insert(new KafkaQueueEntity(Constant.TOPIC_SUBMIT_AUDIT, key, params, new Date()));
            kafkaTemplate.send(Constant.TOPIC_SUBMIT_AUDIT, params);
        } catch (Exception e) {
            logger.error("发送消息失败：" + params);
            logger.error(e.getMessage(), e);
        }
        return true;
    }

}
