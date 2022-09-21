package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.FlowInstanceAchievement;
import com.yskc.ms.entity.ms.FlowInstanceCurNode;
import com.yskc.ms.entity.ms.FlowInstanceUser;
import com.yskc.ms.models.flowInstance.FlowInstanceInfoModel;
import com.yskc.ms.models.projectAudit.AuditStatusModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.dao.ms
 * @Author: zhangdingfu
 * @CreateTime: 2021-11-19 17:00
 * @Description: 成果流程dao层
 */
@Repository
public interface FlowInstanceAchievementDao extends BaseMapper<FlowInstanceAchievement> {
    /**
     * 获取用户审核
     *
     * @param achievementIds
     * @param userId
     * @return
     */
    List<Integer> getUserAudit(@Param("achievementIds") List<Integer> achievementIds, @Param("userId") Integer userId);

    /**
     * 获取achievementId
     *
     * @param instanceId
     * @return
     */
    FlowInstanceInfoModel getInstanceInfo(@Param("instanceId") Integer instanceId);


    /**
     * 获取成果流程审核用户
     *
     * @param achievementIds
     * @param userId
     * @return
     */
    List<FlowInstanceUser> getAchievementInstanceUsers(@Param("achievementIds") List<Integer> achievementIds, @Param("userId") Integer userId);

    /**
     * 获取成果审核信息
     * @param achievementIds
     * @return
     */
    List<FlowInstanceCurNode> getAchievementInstances(@Param("achievementIds") List<Integer> achievementIds);

    /**
     * 获取审核权限
     *
     * @param achievementId
     * @return
     */
    AuditStatusModel getAuditStatus(@Param("achievementId") Integer achievementId);

    /**
     * 获取成果id
     * @param instanceIds
     * @return
     */
    List<Integer> getAchievementIds(@Param("instanceIds") List<Integer> instanceIds);
}
