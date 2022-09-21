package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.rs.RsAchievementEntity;
import com.yskc.ms.models.rs.achievement.AchievementFileModel;
import com.yskc.ms.models.rs.achievement.AchievementModel;
import com.yskc.ms.models.rs.achievement.QueryAchievementModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.dao.rs
 * @Author: zhangdingfu
 * @CreateTime: 2021-11-19 16:43
 * @Description: rs成果dao层
 */
@Repository
public interface RsAchievementDao extends BaseMapper<RsAchievementEntity> {
    /**
     * 获取成果
     *
     * @param page
     * @param query
     * @return
     */
    List<AchievementModel> getList(@Param("page") Pagination page, @Param("query") QueryAchievementModel query);

    /**
     * 获取成果文件
     *
     * @param achievementId
     * @return
     */
    List<AchievementFileModel> getFiles(@Param("achievementId") Integer achievementId);

    /**
     * 获取成果信息
     *
     * @param id
     * @return
     */
    AchievementModel getInfo(@Param("id") Integer id);

    /**
     * 获取审核中的成果id
     *
     * @param year
     * @param companyId
     * @return
     */
    List<Integer> getAchievementIds(@Param("year") Integer year, @Param("companyId") Integer companyId);

    /**
     * 获取成果
     *
     * @param ids
     * @return
     */
    List<RsAchievementEntity> getAchievements(@Param("ids") List<Integer> ids);
}
