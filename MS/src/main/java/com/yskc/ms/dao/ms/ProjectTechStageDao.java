package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.ProjectTechStageEntity;
import com.yskc.ms.models.ProjectTechStage.ProjectStageModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/7/28 15:10
 * description:
 */
@Repository
public interface ProjectTechStageDao extends BaseMapper<ProjectTechStageEntity> {

    List<ProjectStageModel> getByProjectId(@Param("projectId") Integer projectId);

    Integer insertList(@Param("entities") List<ProjectTechStageEntity> entities);

    Integer updateList(@Param("stageModels") List<ProjectStageModel> stageModels, @Param("date") Date date, @Param("userId") Integer userId);

    Integer deleteByStage(@Param("projectIds") List<Integer> projectIds, @Param("stageIds") List<Integer> stageIds);
}
