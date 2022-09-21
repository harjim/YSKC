package com.xxl.job.executor.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.ms.FlowNodeUserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.dao.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-12-16 08:37
 * @Description: 节点用户数据dao层
 */
@Repository
public interface FlowNodeUserDao extends BaseMapper<FlowNodeUserEntity> {
    /**
     * 根据nodeIds获取数据
     *
     * @param nodeIds
     * @return
     */
    List<FlowNodeUserEntity> getByNodeIds(@Param("nodeIds") Set<Integer> nodeIds);
}
