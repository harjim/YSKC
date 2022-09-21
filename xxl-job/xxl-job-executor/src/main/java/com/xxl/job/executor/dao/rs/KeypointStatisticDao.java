package com.xxl.job.executor.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.rs.KeypointStatisticEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @DateTime: 2021/10/9 17:32
 * @Description:
 * @author: hsx
 */
@Repository
public interface KeypointStatisticDao extends BaseMapper<KeypointStatisticEntity> {

    /**
     * 更新表数据
     * @return
     */
    Integer updateStatisticData(@Param("entity") KeypointStatisticEntity entity);
}
