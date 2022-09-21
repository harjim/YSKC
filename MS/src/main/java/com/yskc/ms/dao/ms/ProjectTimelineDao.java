package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.ProjectTimelineEntity;
import com.yskc.ms.models.project.ProjectTimelineModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/10/31 10:13
 * description:
 */
@Repository
public interface ProjectTimelineDao extends BaseMapper<ProjectTimelineEntity> {
    /**
     * 获取项目时间线
     * @param projectId
     * @return
     */
    List<ProjectTimelineModel> getByProjectId(@Param("projectId") Integer projectId);

    /**
     * 根据项目类型和id获取时间线
     * @param projectId
     * @param types
     * @return
     */
    List<ProjectTimelineModel> getByType(@Param("projectId") Integer projectId, @Param("types") List<Integer> types);
}
