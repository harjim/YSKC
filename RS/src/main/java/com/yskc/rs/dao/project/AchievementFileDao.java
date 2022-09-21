package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.project.AchievementEntity;
import com.yskc.rs.entity.project.AchievementFileEntity;
import com.yskc.rs.models.achievement.AchievementFileModel;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao
 * @Author: zhangdingfu
 * @CreateTime: 2021-11-18 10:35
 * @Description: 成果文件dao层
 */
@Repository
public interface AchievementFileDao extends BaseMapper<AchievementFileEntity> {

    /**
     * 获取成果id
     *
     * @param achievementIds
     * @return
     */
    List<Integer> getAchievementIds(@Param("achievementIds") List<Integer> achievementIds);

    /**
     * 获取最大上传时间，文件个数
     *
     * @param achievementId
     * @return
     */
    AchievementEntity getStatFile(@Param("achievementId") Integer achievementId);

    /**
     * 获取成果文件
     *
     * @param achievementId
     * @return
     */
    List<AchievementFileModel> getFiles(@Param("achievementId") Integer achievementId);

    Integer getMaxSeq(@Param("achievementId")Integer achievementId);

    Integer updateAchievementFile(@Param("entity")AchievementFileEntity entity);

    AchievementFileModel getSeq(@Param("id")Integer id);
    Integer updateSeq(@Param("list")List<AchievementFileModel> list);
}
