package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.ProjectTechLogEntity;
import com.yskc.ms.models.tech.ProjectTechLogModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/7/23 15:44
 * description:
 */
@Repository
public interface ProjectTechLogDao extends BaseMapper<ProjectTechLogEntity> {

    List<ProjectTechLogModel> getLogList(@Param("projectIds") List<Integer> projectId);

    /**
     * 获取删除后项目最后的日志
     * @param projectId
     * @param logId
     * @return
     */
    ProjectTechLogEntity queryByCteateTime(@Param("projectId")Integer projectId,@Param("logId")Integer logId);

    /**
     * 根据阶段key和产品id获取logs
     * @param stageKeys
     * @param productId
     * @return
     */
    List<ProjectTechLogEntity> getByStageKey(@Param("stageKeys") List<String> stageKeys, @Param("productId") Integer productId);
}
