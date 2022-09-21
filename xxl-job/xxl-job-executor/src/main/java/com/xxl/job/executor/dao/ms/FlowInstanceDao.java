package com.xxl.job.executor.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.ms.FlowInstance;
import com.xxl.job.executor.models.flow.FlowBranchModel;
import com.xxl.job.executor.models.flowInstance.FlowInstanceModel;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.dao.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-12-15 16:46
 * @Description: 流程实例dao层
 */
@Repository
public interface FlowInstanceDao extends BaseMapper<FlowInstance> {

    /**
     * 批量添加
     *
     * @param list
     * @return
     */
    @Options(useGeneratedKeys = true)
    int addBatch(List<FlowInstance> list);

    /**
     * 获取最新流程实例
     *
     * @param lastTime
     * @return
     */
    List<FlowInstanceModel> getLastInstance(@Param("lastTime") Date lastTime);


    /**
     * 获取下一个流程及节点
     *
     * @param flowIds
     * @return
     */
    List<FlowBranchModel> getNextFlowNode(@Param("flowIds") Set<Integer> flowIds);

    /**
     * 获取下一个分支节点及流程
     *
     * @param nodeIds
     * @return
     */
    List<FlowBranchModel> getNextBranchNode(@Param("nodeIds") Set<Integer> nodeIds);

    /**
     * 更新实例
     *
     * @param list
     * @return
     */
    int updateInstances(@Param("list") List<FlowInstance> list);
}
