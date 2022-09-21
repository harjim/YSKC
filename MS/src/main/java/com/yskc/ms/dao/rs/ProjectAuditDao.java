package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.ProjectAuditEntity;
import com.yskc.ms.models.project.ProjectProgressModel;
import com.yskc.ms.models.projectAudit.AuditListModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/11/21 11:50
 * description:
 */
@Repository
public interface ProjectAuditDao extends BaseMapper<ProjectAuditEntity> {
//    /**
//     * 根据项目id获取审核记录
//     *
//     * @param projectId
//     * @return
//     */
//    List<AuditListModel> getByProject(@Param("projectId") Integer projectId);


    /**
     * 根据项目和mode获取审核节点
     * @param mode
     * @param projectId
     * @return
     */
    ProjectAuditEntity getAuditInfo(@Param("mode") Integer mode, @Param("projectId") Integer projectId);

    /**
     * 获取项目审核状态
     * @param projectId
     * @return
     */
    List<ProjectAuditEntity> getAuditStatus(@Param("projectId") Integer projectId);


}
