package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.FlowInstance;
import com.yskc.ms.entity.ms.FlowInstanceCurNode;
import com.yskc.ms.entity.ms.FlowInstancePatent;
import com.yskc.ms.entity.ms.FlowInstanceUser;
import com.yskc.ms.models.flowInstance.FlowInstanceInfoModel;
import com.yskc.ms.models.patent.FlowPatentModel;
import com.yskc.ms.models.patentPlan.PatentAuditModel;
import com.yskc.ms.models.patentPlan.PatentPlanModel;
import com.yskc.ms.models.projectAudit.AuditStatusModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/6/9 9:47
 * @Description:
 */
@Repository
public interface FlowInstancePatentDao extends BaseMapper<FlowInstancePatent> {
    /**
     * 获取专利交底书审核状态
     *
     * @param ids
     * @return
     */
    List<PatentAuditModel> getPatentAudit(@Param("ids") List<Integer> ids);

    /**
     * 获取专利交底书审核人员
     *
     * @param patentIds
     * @param userId
     * @return
     */
    List<FlowInstanceUser> getPatentInstanceUsers(@Param("patentIds") List<Integer> patentIds, @Param("userId") Integer userId);

    /**
     * 获取专利交底书审核人员(不验证当前处理人)
     *
     * @param patentIds
     * @return
     */
    List<FlowInstanceUser> getCancelPatentInstanceUsers(@Param("patentIds") List<Integer> patentIds);

    /**
     * 获取当前用户可审核的专利交底书
     *
     * @param patentIds
     * @param userId
     * @return
     */
    List<FlowInstancePatent> getPatents(@Param("patentIds") List<Integer> patentIds, @Param("userId") Integer userId);

    /**
     * 获取专利交底书审核信息
     * @param patentIds
     * @return
     */
    List<FlowInstanceCurNode> getPatentInstances(@Param("patentIds") List<Integer> patentIds);


    /**
     * 获取通过审核的专利申请
     *
     * @return
     */
    List<FlowInstancePatent> getPassInstances();

    /**
     * 获取未通过审核的专利交底书
     *
     * @param patentIds
     * @return
     */
    List<FlowInstancePatent> getPassPatents(@Param("patentIds") List<Integer> patentIds);

    /**
     * 获取专利审核状态
     *
     * @param patentPlanId
     * @return
     */
    AuditStatusModel getAuditStatus(@Param("patentPlanId") Integer patentPlanId);

    /**
     * 获取专利交底书id
     *
     * @param instanceId
     * @return
     */
    FlowInstanceInfoModel getInstanceInfo(@Param("instanceId") Integer instanceId);

    /**
     * 获取专利审核状态信息(通过审核)
     *
     * @param ids
     * @return
     */
    List<FlowPatentModel> getAuditInfo(@Param("ids") List<Integer> ids);

    /**
     * 获取当前用户审核权限
     *
     * @param msUserId
     * @param instanceId
     * @return
     */
    FlowInstance getUserAudit(@Param("msUserId") Integer msUserId, @Param("instanceId") Integer instanceId);

    /**
     * 获取专利申请
     * @param instanceIds
     * @return
     */
    List<PatentPlanModel> getPatentPlanInfo(@Param("instanceIds") List<Integer> instanceIds);

    /**
     * 根据patentPlanId获取instanceId
     * @param ids
     * @return
     */
    List<Integer> getInstanceId(@Param("ids") List<Integer> ids);
}
