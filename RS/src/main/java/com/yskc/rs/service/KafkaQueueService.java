package com.yskc.rs.service;

import com.yskc.common.enums.FlowModuleTypeEnum;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2021-03-16 08:17
 * @Description: kafka队列业务接口层
 */
public interface KafkaQueueService {
    /**
     * 提交审核
     *
     * @param ids
     * @param moduleType
     * @param msUserId
     * @return
     */
    boolean submitAudit(List<Integer> ids, Integer msUserId,FlowModuleTypeEnum moduleType);
}
