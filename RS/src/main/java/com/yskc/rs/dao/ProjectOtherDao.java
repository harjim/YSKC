package com.yskc.rs.dao;

import com.yskc.rs.entity.tech.ProjectOtherEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
/**
 * 
 * 
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-09-23 14:05:59
 */
@Repository
public interface ProjectOtherDao extends BaseMapper<ProjectOtherEntity> {

    ProjectOtherEntity selectByProjectId(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId);
}
