package com.yskc.ms.service.rs;

import com.yskc.common.enums.FlowModuleTypeEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.ms.models.projectAudit.BatchAuditModel;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service.rs
 * @Author: zhangdingfu
 * @CreateTime: 2021-03-16 08:17
 * @Description: kafka队列业务接口层
 */
public interface KafkaQueueService {
    /**
     * 发送消息
     *
     * @param topic
     * @param ids
     * @param userId
     * @return
     */
    boolean sendAudit(String topic, List<Integer> ids,Integer userId);

    /**
     * 发送消息
     *
     * @param topic          主题
     * @param ids            数据ids
     * @param userId         用户id
     * @param appointUserIds 指定用户
     * @param nodeId        节点id
     * @return
     */
    boolean sendAudit(String topic, List<Integer> ids,Integer userId, List<Integer> appointUserIds, Integer nodeId);

    /**
     * 发送消息
     *
     * @param topic          主题
     * @param ids            数据ids
     * @param userId         用户id
     * @param appointUserIds 指定用户
     * @param expired        指定有效期
     * @param moduleType     模块类型（提交时指定）
     * @return
     */
    boolean sendAudit(String topic, List<Integer> ids,Integer userId, List<Integer> appointUserIds, Date expired, Integer moduleType);

    /**
     * 提交审核
     *
     * @param dataIds
     * @param userId
     * @param moduleType
     * @return
     */
    boolean submitAudit(List<Integer> dataIds, Integer userId,FlowModuleTypeEnum moduleType);

    /**
     * 发送专利需求通知
     *
     * @param topicPatentDemandDone
     * @param demandId
     * @return
     */
    boolean sendPatentDemand(String topicPatentDemandDone, Integer demandId);
}
