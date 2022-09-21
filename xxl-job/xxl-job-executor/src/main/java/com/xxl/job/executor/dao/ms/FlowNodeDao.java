package com.xxl.job.executor.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.ms.FlowNodeEntity;
import com.xxl.job.executor.models.flow.FlowModeModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.dao.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-12-15 15:49
 * @Description: 流节点
 */
@Repository
public interface FlowNodeDao extends BaseMapper<FlowNodeEntity> {
    /**
     * 通过mode获取节点
     * @param moduleIds
     * @return
     */
    List<FlowModeModel> getFirstNodeByModes(@Param("moduleIds") Set<Integer> moduleIds);
}
