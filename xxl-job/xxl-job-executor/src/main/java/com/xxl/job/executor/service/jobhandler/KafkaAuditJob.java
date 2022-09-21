package com.xxl.job.executor.service.jobhandler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.executor.dao.rs.KafkaQueueDao;
import com.xxl.job.executor.entity.rs.KafkaQueueEntity;
import com.xxl.job.executor.utils.ToolUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.service.jobhandler
 * @Author: zhangdingfu
 * @CreateTime: 2021-03-29 17:03
 * @Description: kafka审核job
 */
@Component
public class KafkaAuditJob {

    @Autowired
    private KafkaQueueDao kafkaQueueDao;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @XxlJob("consumerAudit")
    public ReturnT<String> consumerAudit(String params) {
        Date lastTime = ToolUtils.getBeforeTime(new Date(), params, -60);
        List<KafkaQueueEntity> lists = kafkaQueueDao.getNoAudits(lastTime);
        if (CollectionUtils.isEmpty(lists)) {
            XxlJobLogger.log("无未消费消息");
            return ReturnT.SUCCESS;
        }
        for (KafkaQueueEntity item : lists) {
            kafkaTemplate.send(item.getTopic(), item.getKey(), item.getData());
            XxlJobLogger.log("当前发送消息:" + item.toString());
        }
        return ReturnT.SUCCESS;
    }

}
