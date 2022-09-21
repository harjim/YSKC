package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.FlowBranch;
import com.yskc.ms.models.flow.FlowBranchTree;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/12/16 15:10
 * description:
 */
@Repository
public interface FlowBranchDao extends BaseMapper<FlowBranch> {
    /**
     * 批量插入分支
     *
     * @param insertList
     * @return
     */
    Integer insertList(@Param("insertList") List<FlowBranch> insertList);

    /**
     * 获取节点分支
     *
     * @param nodeIds
     * @return
     */
    List<FlowBranchTree> getByNode(@Param("nodeIds") List<Integer> nodeIds);

    /**
     * 删除节点分支
     *
     * @param nodeIds
     * @return
     */
    Integer delByNode(@Param("nodeIds") List<Integer> nodeIds);

    /**
     * 获取分支节点下优先级最高的分支
     *
     * @param nextNode
     * @return
     */
    FlowBranch getBranchBySeq(@Param("nextNode") Integer nextNode);

    /**
     * 批量更新分支
     *
     * @param updateList
     * @return
     */
    Integer updateList(@Param("updateList") List<FlowBranch> updateList);

    /**
     * @param nodeIds
     * @return
     */
    List<FlowBranch> getNodeBranch(@Param("nodeIds") List<Integer> nodeIds);


}
