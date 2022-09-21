package com.yskc.rs.dao.tech;

import com.yskc.rs.entity.tech.ProjectOtherEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.models.tech.ProjectOtherModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-09-23 14:05:59
 */
@Repository
public interface TechProjectOtherDao extends BaseMapper<ProjectOtherEntity> {

    /**
     * 获取项目其他数据
     *
     * @param companyId
     * @param projectId
     * @return
     */
    ProjectOtherModel selectByProjectId(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId);
}
