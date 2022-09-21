package com.xxl.job.executor.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.ms.FlowInstanceUser;
import com.xxl.job.executor.models.flowInstance.FlowInstanceModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.dao.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-12-15 16:47
 * @Description: 流程实例用户dao层
 */
@Repository
public interface FlowInstanceUserDao extends BaseMapper<FlowInstanceUser> {
    /**
     * 删除instanceId相关的数据
     *
     * @param instanceIds
     * @return
     */
    int deleteInstance(@Param("instanceIds") List<Integer> instanceIds);

    /**
     * list
     *
     * @param list
     * @return
     */
    int addBatch(@Param("list") List<FlowInstanceUser> list);

    /**
     * 获取用户
     *
     * @param list
     * @return
     */
    List<FlowInstanceUser> getUsers(@Param("list") List<FlowInstanceModel> list);

    /**
     * 获取已存在的实例，节点
     *
     * @param list
     * @return
     */
    List<FlowInstanceUser> getExitInstanceNodes(@Param("list") List<FlowInstanceModel> list);
}
