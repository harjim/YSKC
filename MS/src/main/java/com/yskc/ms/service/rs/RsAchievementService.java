package com.yskc.ms.service.rs;

import com.yskc.common.model.PageResult;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.rs.achievement.AchievementFileModel;
import com.yskc.ms.models.rs.achievement.QueryAchievementModel;

import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service.rs
 * @Author: zhangdingfu
 * @CreateTime: 2021-11-19 16:44
 * @Description: rs成果业务接口层
 */
public interface RsAchievementService {
    /**
     * 获取成果
     *
     * @param query
     * @param userInfo
     * @return
     */
    PageResult getList(QueryAchievementModel query, UserInfo userInfo);

    /**
     * 获取成果文件
     *
     * @param achievementId
     * @return
     */
    List<AchievementFileModel> getFiles(Integer achievementId);

    /**
     * 获取成果信息
     *
     * @param id
     * @return
     */
    Map<String, Object> getInfo(Integer id);

    /**
     * 获取审核数
     * @param year
     * @param companyId
     * @param userId
     * @return
     */
    Integer getAuditCnt(Integer year, Integer companyId, Integer userId);
}
