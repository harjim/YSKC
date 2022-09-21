package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.FlowNodeUserEntity;
import com.yskc.ms.models.flow.FlowNodeUserModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/11/21 11:47
 * description:
 */
@Repository
public interface FlowNodeUserDao extends BaseMapper<FlowNodeUserEntity> {
    /**
     * 获取用户的节点审核权限
     *
     * @param userId
     * @return
     */
    List<FlowNodeUserModel> getUserPerms(@Param("userId") Integer userId, @Param("nodeType") Integer nodeType);

    /**
     * 获取流程用户
     *
     * @param flowId
     * @return
     */
    List<FlowNodeUserModel> getFlowUser(@Param("flowId") Integer flowId);

    /**
     * 获取节点用户
     *
     * @param nodeIds
     * @return
     */
    List<FlowNodeUserEntity> getUserByNode(@Param("nodeIds") List<Integer> nodeIds);

    /**
     * 获取节点用户
     *
     * @param nodeIds
     * @return
     */
    List<FlowNodeUserModel> getNodeUser(@Param("nodeIds") List<Integer> nodeIds);

    /**
     * 批量插入
     *
     * @param insertList
     * @return
     */
    Integer insertList(@Param("insertList") List<FlowNodeUserEntity> insertList);

    /**
     * 删除用户
     *
     * @param userIds
     * @param nodeId
     * @return
     */
    Integer delUser(@Param("userIds") List<Integer> userIds, @Param("nodeId") Integer nodeId);

    /**
     * 删除节点的审核人员
     *
     * @param nodeIds
     * @return
     */
    Boolean delByNodes(@Param("nodeIds") List<Integer> nodeIds);

    /**
     * 根据流程删除用户
     *
     * @param nodeUserIds
     * @return
     */
    Boolean delByFlowId(@Param("nodeUserIds") List<Integer> nodeUserIds);

    /**
     * 获取流程用户
     *
     * @param flowId
     * @return
     */
    List<FlowNodeUserEntity> getUserByFlow(@Param("flowId") Integer flowId);
}
