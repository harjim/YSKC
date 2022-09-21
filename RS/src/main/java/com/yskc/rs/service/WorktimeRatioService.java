package com.yskc.rs.service;

import com.yskc.rs.entity.project.WorktimeRatioEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.ratio.EquipmentRatioModel;

import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2019-08-12 13:36
 * @Description: 研发比例业务接口层
 */
public interface WorktimeRatioService {
    /**
     * 设置设备研发比例
     *
     * @param userInfo
     * @param equipmentRatioModel
     * @return
     */
    Boolean setEquipmentRatio(UserInfo userInfo, EquipmentRatioModel equipmentRatioModel);

    /**
     * 获取当前配置
     *
     * @param userId
     * @param companyId
     * @param projectId
     * @param month
     * @return
     */
    WorktimeRatioEntity getWorktimeRatioEntity(Integer userId, Integer companyId, Integer projectId, Date month);

    /**
     * 获取当前月份配置
     * 不存在时，获取上个月的配置
     * 再不存在，就生成新的记录
     *
     * @param userId
     * @param companyId
     * @param projectId
     * @param month
     * @param userInfo
     * @return
     */
    WorktimeRatioEntity getWorktimeRatioByMonth(Integer userId, Integer companyId, Integer projectId, Date month, UserInfo userInfo);
}
