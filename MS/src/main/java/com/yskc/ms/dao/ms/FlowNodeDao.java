package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.FlowNodeEntity;
import com.yskc.ms.models.flow.FlowNodeModel;
import com.yskc.ms.models.flow.FlowNodeTreeModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/11/21 11:46
 * description:
 */
@Repository
public interface FlowNodeDao extends BaseMapper<FlowNodeEntity> {
    /**
     * 获取流程节点
     *
     * @param nodeType
     * @return
     */
    List<FlowNodeModel> getNodes(@Param("nodeType") int nodeType);

    /**
     * 批量更新流程节点
     *
     * @param updateList
     * @return
     */
    Integer updateList(@Param("updateList") List<FlowNodeEntity> updateList);

    /**
     * 获取流程节点
     *
     * @param flowId
     * @return
     */
    List<FlowNodeTreeModel> getByFlow(@Param("flowId") Integer flowId);

    /**
     * 删除流程节点
     *
     * @param flowId
     */
    Integer delByFlow(@Param("flowId") Integer flowId);

    /**
     * 获取流程第一个节点
     *
     * @param flowId
     * @return
     */
    FlowNodeEntity getFirstNode(@Param("flowId") Integer flowId);

    /**
     * 获取分支下所有节点
     *
     * @param branchIds
     * @return
     */
    List<FlowNodeEntity> getNodeByBranch(@Param("branchIds") List<Integer> branchIds);

    Integer updateFlow(@Param("entity") FlowNodeEntity entity);
}
