package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.StandardImplementEntity;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author: hck
 * @DateTime: 2021/2/25 14:44
 * @Description:
 */
@Repository
public interface StandardImplementDao extends BaseMapper<StandardImplementEntity> {
    /**
     * 获取标准化实施情况
     * @param year
     * @param companyId
     * @return
     */
    StandardImplementEntity getInfo(@Param("year") Integer year, @Param("companyId") Integer companyId);
}
