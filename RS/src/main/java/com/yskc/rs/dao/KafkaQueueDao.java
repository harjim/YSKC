package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.KafkaQueueEntity;
import org.springframework.stereotype.Repository;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao
 * @Author: zhangdingfu
 * @CreateTime: 2021-03-16 08:15
 * @Description: kafka队列记录dao层
 */
@Repository
public interface KafkaQueueDao extends BaseMapper<KafkaQueueEntity> {

}
