package com.yskc.rs.dao;

import com.yskc.rs.entity.project.WorktimeRatioEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-08-12 11:19:48
 */
@Repository
public interface WorktimeRatioDao extends BaseMapper<WorktimeRatioEntity> {

    /**
     * 获取研发比例
     *
     * @param companyId
     * @param month
     * @param projectId
     * @return
     */
    WorktimeRatioEntity getWorktimeRatio(@Param("companyId") Integer companyId, @Param("month") Date month, @Param("projectId") Integer projectId);
}
