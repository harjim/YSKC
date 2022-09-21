package com.yskc.ms.service.impl.rs;

import cn.hutool.core.util.IdUtil;
import com.yskc.common.enums.FlowModuleTypeEnum;
import com.yskc.common.model.AuditParamsModel;
import com.yskc.common.utils.JsonUtils;
import com.yskc.ms.config.Constant;
import com.yskc.ms.dao.rs.KafkaQueueDao;
import com.yskc.ms.entity.rs.KafkaQueueEntity;
import com.yskc.ms.service.rs.KafkaQueueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service.impl.rs
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
    private final static int DATACENTERID = 2;
    @Autowired
    private KafkaQueueDao kafkaQueueDao;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Override
    public boolean sendAudit(String topic, List<Integer> ids, Integer userId) {
        return sendAudit(topic, ids, userId, null, null, null);
    }

    @Override
    public boolean sendAudit(String topic, List<Integer> ids, Integer userId, List<Integer> appointUserIds, Integer nodeId) {
        return sendAudit(topic, ids, userId, appointUserIds, null, null, nodeId);
    }

    @Override
    public boolean sendAudit(String topic, List<Integer> ids, Integer userId, List<Integer> appointUserIds, Date expired, Integer moduleType) {
        return sendAudit(topic, ids, userId, appointUserIds, expired, moduleType, null);
    }

    public boolean sendAudit(String topic, List<Integer> ids, Integer userId, List<Integer> appointUserIds, Date expired, Integer moduleType, Integer nodeId) {
        String key = getKey();
        saveAndSend(topic, key, JsonUtils.objectToJson(new AuditParamsModel(ids, userId, key, appointUserIds, expired, moduleType, nodeId)));
        return true;
    }

    @Override
    public boolean submitAudit(List<Integer> dataIds, Integer userId, FlowModuleTypeEnum moduleType) {
        String key = getKey();
        saveAndSend(Constant.TOPIC_SUBMIT_AUDIT, key, JsonUtils.objectToJson(new AuditParamsModel(dataIds, userId, key, moduleType.getType())));
        return true;
    }

    @Override
    public boolean sendPatentDemand(String topicPatentDemandDone, Integer demandId) {
        String key = getKey();
        saveAndSend(topicPatentDemandDone, key, JsonUtils.objectToJson(demandId));
        return true;
    }

    public void saveAndSend(String topic, String key, String dataStr) {
        try {
            kafkaQueueDao.insert(new KafkaQueueEntity(topic, key, dataStr, new Date()));
            kafkaTemplate.send(topic, key, dataStr);
        } catch (Exception e) {
            logger.error("发送消息失败。");
            logger.error(e.getMessage(), e);
        }
    }

    private String getKey() {
        return IdUtil.getSnowflake(WORKERID, DATACENTERID).nextIdStr();
    }

}
