package com.xxl.job.executor.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.ms.PatentEntity;
import org.springframework.stereotype.Repository;

/**
 * @DateTime: 2021/10/9 17:07
 * @Description:
 * @author: hsx
 */
@Repository
public interface PatentDao extends BaseMapper<PatentEntity> {

    /**
     * 获取专利总数
     * @return
     */
    Integer getPatentStatNumber();
}
