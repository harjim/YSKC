package com.yskc.docservice.dao.rs.project;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.project.ProjectEquipmentEntity;
import com.yskc.docservice.models.rs.projectrdequipment.ProjectRdEquipmentModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao.project
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-17 14:34
 * @Description: 项目设备研发记录Dao层
 */
@Repository
public interface ProjectEquipmentDao extends BaseMapper<ProjectEquipmentEntity> {

    /**
     * 批量更新
     *
     * @param list
     * @return
     */
    int updateBatch(@Param("list") List<ProjectEquipmentEntity> list);

    /**
     * 获取设备运行工时记录
     * @param list
     * @param projectId
     * @return
     */
    List<ProjectEquipmentEntity> getProjectEquipments(@Param("list") List<ProjectRdEquipmentModel> list,
                                                      @Param("projectId") Integer projectId);
}
