package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.tech.ProjectImplementEntity;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao
 * @Author: wangxing
 * @CreateTime: 2019-09-24 08:18
 * @Description: ProjectImplementDao
 */
@Repository
public interface ProjectImplementDao extends BaseMapper<ProjectImplementEntity> {

    ProjectImplementEntity queryImplement(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId);
}
