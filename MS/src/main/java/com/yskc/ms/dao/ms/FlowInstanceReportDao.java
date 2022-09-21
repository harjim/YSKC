package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.FlowInstanceCurNode;
import com.yskc.ms.entity.ms.FlowInstanceReport;
import com.yskc.ms.entity.ms.FlowInstanceUser;
import com.yskc.ms.models.flowInstance.FlowInstanceInfoModel;
import com.yskc.ms.models.flowInstance.RdInstanceModel;
import com.yskc.ms.models.projectAudit.AuditStatusModel;
import com.yskc.ms.models.rs.ReportAuditInfoModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/3/31 9:00
 * @Description:
 */
@Repository
public interface FlowInstanceReportDao extends BaseMapper<FlowInstanceReport> {

    /**
     * 获取查新报告审核信息
     *
     * @param projectIds
     * @param userId
     * @return
     */
    Integer getAuditInfo(@Param("projectIds") List<Integer> projectIds,
                         @Param("userId") Integer userId, @Param("companyId") Integer companyId);

    /**
     * 获取项目审核状态
     *
     * @param rsProjectId
     * @return
     */
    AuditStatusModel getAuditStatus(@Param("rsProjectId") Integer rsProjectId);

    /**
     * 获取文档审核用户
     *
     * @param projectIds
     * @param userId
     * @return
     */
    List<FlowInstanceUser> getInstanceUsers(@Param("projectIds") List<Integer> projectIds,
                                            @Param("userId") Integer userId);

    /**
     * 获取项目查新报告审核状态
     *
     * @param projectIds
     * @param userId
     * @return
     */
    List<ReportAuditInfoModel> getProjectAuditStatus(@Param("projectIds") List<Integer> projectIds, @Param("userId") Integer userId);

    /**
     * 获取实例信息
     *
     * @param instanceId
     * @return
     */
    FlowInstanceInfoModel getInstanceInfo(@Param("instanceId") Integer instanceId);

    /**
     * 获取项目审核人员
     *
     * @param projectIds
     * @param userId
     * @param moduleId
     * @return
     */
    List<FlowInstanceUser> getProjectInstanceUsers(@Param("projectIds") List<Integer> projectIds, @Param("userId") Integer userId, @Param("moduleId") Integer moduleId);

    /**
     * 获取项目审核信息
     * @param projectIds
     * @param moduleId
     * @return
     */
    List<FlowInstanceCurNode> getProjectInstances(@Param("projectIds") List<Integer> projectIds, @Param("moduleId") Integer moduleId);


    /**
     * 获取用户可审核的项目
     *
     * @param projectIds
     * @param userId
     * @return
     */
    List<FlowInstanceReport> getProjectAudits(@Param("projectIds") List<Integer> projectIds, @Param("userId") Integer userId);

    /**
     * 获取审核信息
     *
     * @param patentInstances
     * @return
     */
    List<RdInstanceModel> getProjectInfo(@Param("instanceIds") List<Integer> patentInstances);
}
