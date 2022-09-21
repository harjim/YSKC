package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.ProjectBasicEntity;
import com.yskc.ms.models.project.ProjectBasicModel;
import com.yskc.ms.models.project.ProjectServeModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by hck
 * on 2020/10/31 10:08
 * description:
 */
@Repository
public interface ProjectBasicDao extends BaseMapper<ProjectBasicEntity> {

    ProjectBasicModel getInfo(@Param("projectId") Integer projectId);

    /**
     * 根据项目获取基本信息
     * @param projectId
     * @return
     */
    ProjectBasicEntity getByProject(@Param("projectId") Integer projectId);
}
