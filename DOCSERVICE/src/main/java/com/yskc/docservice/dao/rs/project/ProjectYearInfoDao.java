package com.yskc.docservice.dao.rs.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.project.ProjectYearInfoEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/6/30 9:35
 * @Description:
 */
@Repository
public interface ProjectYearInfoDao extends BaseMapper<ProjectYearInfoEntity> {

    /**
     * 插入并更新项目负责人
     * @param list
     * @return
     */
    Boolean addBatchMaster(@Param("list") List<ProjectYearInfoEntity> list);

    /**
     * 批量更新项目负责人
     * @param list
     */
    Boolean updateBatch(@Param("list") List<ProjectYearInfoEntity> list);

    /**
     * 获取项目信息按年  （<=year）
     * @param projectId
     * @param year
     * @return
     */
    List<ProjectYearInfoEntity> getInfoByYear(@Param("projectId") Integer projectId, @Param("year") Integer year);
}
