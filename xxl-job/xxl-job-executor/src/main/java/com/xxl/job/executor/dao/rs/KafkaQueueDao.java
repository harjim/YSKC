package com.xxl.job.executor.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.rs.KafkaQueueEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Author: zhangdingfu
 * @CreateTime: 2021-03-16 08:15
 * @Description: kafka队列记录dao层
 */
@Repository
public interface KafkaQueueDao extends BaseMapper<KafkaQueueEntity> {

    /**
     * 未审核，审核失败的数据【默认获取1小时前的数据】
     * @param lastTime
     * @return
     */
    List<KafkaQueueEntity> getNoAudits(@Param("lastTime") Date lastTime);
}
