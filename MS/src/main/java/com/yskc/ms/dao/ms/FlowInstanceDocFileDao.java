package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.FlowInstanceCurNode;
import com.yskc.ms.entity.ms.FlowInstanceDocFile;
import com.yskc.ms.entity.ms.FlowInstanceUser;
import com.yskc.ms.entity.rs.ProjectDocFileEntity;
import com.yskc.ms.models.flowInstance.FlowInstanceInfoModel;
import com.yskc.ms.models.flowInstance.RdInstanceModel;
import com.yskc.ms.models.projectAudit.AuditDocFileModel;
import com.yskc.ms.models.projectAudit.AuditStatusModel;
import com.yskc.ms.models.projectAudit.DocFileAuditModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.dao.ms
 * @Author: zhangdingfu
 * @CreateTime: 2021-01-28 15:10
 * @Description: 文档流程实例dao层
 */
@Repository
public interface FlowInstanceDocFileDao extends BaseMapper<FlowInstanceDocFile> {
    /**
     * 获取文档审核
     *
     * @param docFileIds
     * @return
     */
    List<DocFileAuditModel> getDocFileAudits(@Param("docFileIds") List<Integer> docFileIds);

    /**
     * 获取当前用户文档审核
     *
     * @param projectIds
     * @param pDocFileIds
     * @param userId
     * @return
     */
    List<FlowInstanceDocFile> getDocAudit(@Param("projectIds") List<Integer> projectIds, @Param("pDocFileIds") List<Integer> pDocFileIds, @Param("userId") Integer userId);

    /**
     * 获取审核状态
     *
     * @param docFileId
     * @return
     */
    AuditStatusModel getAuditStatus(@Param("docFileId") Integer docFileId);

    /**
     * 获取文档审核用户
     *
     * @param docFileIds
     * @param userId
     * @param projectId
     * @return
     */
    List<FlowInstanceUser> getInstanceUsers(@Param("docFileIds") List<Integer> docFileIds, @Param("userId") Integer userId, @Param("projectId") Integer projectId);

    /**
     * 获取文档审核信息
     * @param docFileIds
     * @param projectId
     * @return
     */
    List<FlowInstanceCurNode> getInstances(@Param("docFileIds") List<Integer> docFileIds, @Param("projectId") Integer projectId);

    /**
     * 获取立项报告审核信息
     * @param projectDocFiles
     * @return
     */
    List<FlowInstanceCurNode> getDocFileInstance(@Param("projectDocFiles") List<ProjectDocFileEntity> projectDocFiles);

    /**
     * 统计项目当前用户可审核过程文件数量
     *
     * @param projectIds
     * @param userId
     * @param docFileIds
     * @return
     */
    List<AuditDocFileModel> countAuditDocNum(@Param("projectIds") List<Integer> projectIds,
                                             @Param("userId") Integer userId,
                                             @Param("docFileIds") List<Integer> docFileIds);

    /**
     * 获取过程文档提交数量
     *
     * @param projectIds
     * @param nodeStatus
     * @param docFileIds null 查所有
     * @return
     */
    List<AuditDocFileModel> getDocNum(@Param("projectIds") List<Integer> projectIds,
                                      @Param("nodeStatus") List<Integer> nodeStatus,
                                      @Param("docFileIds") List<Integer> docFileIds);

    /**
     * 获取实例信息
     *
     * @param instanceId
     * @return
     */
    FlowInstanceInfoModel getInstanceInfo(@Param("instanceId") Integer instanceId);

    /**
     * 获取项目id
     *
     * @param instanceIds
     * @return
     */
    List<RdInstanceModel> getProjectInfo(@Param("instanceIds") List<Integer> instanceIds);

    /**
     * 获取
     *
     * @param projectIds
     * @param userId
     * @param moduleId
     * @return
     */
    List<FlowInstanceUser> getReportInstanceUsers(@Param("projectIds") List<Integer> projectIds, @Param("userId") Integer userId, @Param("moduleId") int moduleId);

    /**
     * 获取查新报告审核信息
     * @param projectIds
     * @param moduleId
     * @return
     */
    List<FlowInstanceCurNode> getReportInstances(@Param("projectIds") List<Integer> projectIds, @Param("moduleId") int moduleId);

    /**
     * 获取留存备查资料所包括的文件的docFileId
     * @param rsProjectId
     * @return
     */
    List<Integer> geDocFileIds(@Param("projectId") Integer rsProjectId);
}
