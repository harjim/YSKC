package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.FlowInstanceUser;
import com.yskc.ms.models.flow.FlowInstanceUserModel;
import com.yskc.ms.models.projectAudit.AuditStatusModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/12/15 11:11
 * description:
 */
@Repository
public interface FlowInstanceUserDao extends BaseMapper<FlowInstanceUser> {
    /**
     * 获取实例用户列表
     * @param curNodeUser
     * @return
     */
    List<FlowInstanceUserModel> getByNode(@Param("curNodeUser") List<AuditStatusModel> curNodeUser);

    /**
     * 查询节点用户有无权限
     * @param projectId
     * @param mode
     * @param nodeId
     * @param userId
     * @return
     */
    FlowInstanceUser getByUser(@Param("projectId") Integer projectId, @Param("mode") Integer mode, @Param("nodeId") Integer nodeId, @Param("userId") Integer userId);

    /**
     * 获取节点未审核用户列表(userId不为空是排除该userId)
     * @param instanceId
     * @param curNodeId
     * @param userId
     * @return
     */
    int countNodeUser(@Param("instanceId") Integer instanceId, @Param("curNodeId") Integer curNodeId, @Param("userId") Integer userId);
    /**
     * 获取实例用户表id
     *
     * @param instanceIds
     * @param userId
     * @return
     */
    List<FlowInstanceUser> getInstanceUsers(@Param("instanceIds") List<Integer> instanceIds,@Param("userId") Integer userId);

    /**
     * 更新状态
     * @param instanceUserIds
     * @param status
     * @param now
     * @param userId
     * @return
     */
    int updateStatus(@Param("instanceUserIds") List<Integer> instanceUserIds, @Param("status") boolean status,
                     @Param("now")Date now,@Param("userId")Integer userId);

    /**
     * 获取未审核过的实例id
     * @param instanceIds
     * @param userId
     * @return
     */
    List<FlowInstanceUser> getUnAuditInstanceIds(@Param("instanceIds") List<Integer> instanceIds,@Param("userId") Integer userId);

    /**
     * 获取当前flowInstance的处理人
     * @param instanceIds
     * @param nodeId
     * @return
     */
    List<FlowInstanceUser> getByInstance(@Param("instanceIds") List<Integer> instanceIds,@Param("nodeId") Integer nodeId);
}
