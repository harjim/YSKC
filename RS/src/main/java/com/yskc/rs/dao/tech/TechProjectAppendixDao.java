package com.yskc.rs.dao.tech;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.tech.ProjectAppendixEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.models.tech.ProjectAppendixModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * 
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-09-23 14:05:59
 */
@Repository
public interface TechProjectAppendixDao extends BaseMapper<ProjectAppendixEntity> {

    List<ProjectAppendixEntity> selectByProjectId(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId);

    ProjectAppendixEntity selectByType(@Param("projectId") Integer projectId, @Param("type") Integer type);

    List<ProjectAppendixModel> queryDocument(@Param("pagination") Pagination pagination, @Param("fileName") String fileName, @Param("projectId") Integer projectId, @Param("companyId") int companyId);

    Integer updateByType(@Param("projectId") Integer projectId, @Param("type") Integer type);
}
