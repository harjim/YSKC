package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.project.ProjectStateEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by hck
 * on 2021/1/25 9:10
 * description:
 */
@Repository
public interface ProjectStateDao extends BaseMapper<ProjectStateEntity> {
    /**
     * 获取项目结项信息
     * @param projectId
     * @return
     */
    ProjectStateEntity getByProject(@Param("projectId") Integer projectId);

}
