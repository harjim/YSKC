package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.KafkaQueueEntity;
import org.springframework.stereotype.Repository;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.dao.rs
 * @Author: zhangdingfu
 * @CreateTime: 2021-03-16 08:15
 * @Description: kafka队列记录dao层
 */
@Repository
public interface KafkaQueueDao extends BaseMapper<KafkaQueueEntity> {

}
