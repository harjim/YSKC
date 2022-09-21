package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.ActivityEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-11-04 16:06:23
 */
@Repository
public interface ActivityDao extends BaseMapper<ActivityEntity> {


    /**
     * 获取开发情况表map
     * @param companyId
     * @param year
     * @return
     */
    List<Map<String, String>> getActiveMap(@Param("companyId") Integer companyId, @Param("year") Integer year);

    /**
     * 通过年份 + pkey +公司id获取数据
     * @param companyId
     * @param year
     * @param pKey
     * @return
     */
    ActivityEntity getActivityByYearAndPkey(@Param("companyId") Integer companyId,@Param("year") Integer year,@Param("pkey")String pKey);
}
