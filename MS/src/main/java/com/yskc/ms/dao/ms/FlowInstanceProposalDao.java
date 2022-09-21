package com.yskc.ms.dao.ms;

import com.yskc.ms.entity.ms.FlowInstanceCurNode;
import com.yskc.ms.entity.ms.FlowInstanceProposal;
import com.yskc.ms.entity.ms.FlowInstanceUser;
import com.yskc.ms.models.flowInstance.FlowInstanceInfoModel;
import com.yskc.ms.models.flowInstance.RdInstanceModel;
import com.yskc.ms.models.projectAudit.AuditStatusModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @DateTime: 2021/9/6 15:03
 * @Description:
 */
@Repository
public interface FlowInstanceProposalDao {

    /**
     * 获取提案审核状态
     *
     * @param proposalId
     * @return
     */
    AuditStatusModel getAuditStatus(@Param("proposalId") Integer proposalId);

    List<FlowInstanceProposal> getProposalAuditStatus(@Param("proposalIds") List<Integer> proposalIds, @Param("userId") Integer userId);

    /**
     * 获取实例
     *
     * @param instanceId
     * @return
     */
    FlowInstanceInfoModel getInstanceInfo(@Param("instanceId") Integer instanceId);

    /**
     * 获取提案审核人员
     *
     * @param proposalIds
     * @param userId
     * @return
     */
    List<FlowInstanceUser> getProposalInstanceUsers(@Param("proposalIds") List<Integer> proposalIds, @Param("userId") Integer userId);

    /**
     * 获取提案审核信息
     * @param proposalIds
     * @return
     */
    List<FlowInstanceCurNode> getProposalInstances(@Param("proposalIds") List<Integer> proposalIds);


    List<FlowInstanceProposal> getProposalAudits(@Param("proposalIds")List<Integer> proposalIds,@Param("userId")Integer userId);

    /**
     * 获取项目信息
     *
     * @param instanceIds
     * @return
     */
    List<RdInstanceModel> getProposalInfo(@Param("instanceIds") List<Integer> instanceIds);
}
