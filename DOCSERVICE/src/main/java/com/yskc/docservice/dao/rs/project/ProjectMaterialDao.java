package com.yskc.docservice.dao.rs.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.project.ProjectMaterialEntity;
import com.yskc.docservice.models.rs.material.ProjectRdMaterialTotalModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-16 14:44:47
 */
@Repository
public interface ProjectMaterialDao extends BaseMapper<ProjectMaterialEntity> {

    /**
     * 批量插入
     *
     * @param insertMaterialEntities
     * @return
     */
    Integer addBatch(@Param("insertMaterialEntities") List<ProjectMaterialEntity> insertMaterialEntities);

    /**
     * 获取物料归集total
     *
     * @param projectIds
     * @param minDate
     * @param maxDate
     * @return
     */
    List<ProjectRdMaterialTotalModel> getRdMaterialTotal(@Param("projectIds") List<Integer> projectIds,
                                                         @Param("minDate") Date minDate, @Param("maxDate") Date maxDate);
}
