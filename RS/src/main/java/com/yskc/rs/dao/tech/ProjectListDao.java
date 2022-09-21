package com.yskc.rs.dao.tech;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.tech.ProjectListEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/10/16 10:39
 * description:
 */
@Repository
public interface ProjectListDao extends BaseMapper<ProjectListEntity> {

    List<ProjectListEntity> getListByStage(@Param("stageListIds") List<Integer> stageListIds,@Param("projectId")Integer projectId);
}
