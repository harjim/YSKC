package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.project.AchievementEntity;
import com.yskc.rs.entity.project.AuditAchievementEntity;
import com.yskc.rs.models.achievement.AchievementFileModel;
import com.yskc.rs.models.achievement.AchievementModel;
import com.yskc.rs.models.achievement.QueryAchievementModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao
 * @Author: zhangdingfu
 * @CreateTime: 2021-11-18 10:35
 * @Description: 成果dao层
 */
@Repository
public interface AchievementDao extends BaseMapper<AchievementEntity> {
    /**
     * 获取成果管理
     *
     * @param page
     * @param companyId
     * @param query
     * @return
     */
    List<AchievementModel> getList(@Param("page") Pagination page, @Param("companyId") Integer companyId, @Param("query") QueryAchievementModel query);

    /**
     * 统计成果名字
     *
     * @param model
     * @param companyId
     * @return
     */
    int countName(@Param("model") AchievementModel model, @Param("companyId") Integer companyId);

    /**
     * 更新成果
     *
     * @param model
     * @param userId
     * @param msUserId
     * @param now
     * @return
     */
    int updateAchievement(@Param("model") AchievementModel model, @Param("userId") Integer userId,
                          @Param("msUserId") Integer msUserId, @Param("now") Date now);

    /**
     * 更新文件数，上传时间
     *
     * @param id
     * @param lastUploadTime
     * @param fileCnt
     * @return
     */
    Integer updateFile(@Param("id") Integer id, @Param("lastUploadTime") Date lastUploadTime, @Param("fileCnt") Integer fileCnt);

    /**
     * 获取审核状态
     *
     * @param id
     * @return
     */
    Integer getStatus(@Param("id") Integer id);

    /**
     * 获取审核的成果
     *
     * @param ids
     * @return
     */
    List<AuditAchievementEntity> getAuditAchievement(@Param("ids") List<Integer> ids);

    /**
     * 获取子类型
     *
     * @param companyId
     * @param beginYear
     * @param endYear
     * @return
     */
    List<Integer> getChildTypes(@Param("companyId") Integer companyId, @Param("beginYear") Integer beginYear, @Param("endYear") Integer endYear);

    /**
     * 统计成果数
     *
     * @param companyId
     * @param beginYear
     * @param endYear
     * @return
     */
    Integer countAchievement(@Param("companyId") Integer companyId, @Param("beginYear") Integer beginYear, @Param("endYear") Integer endYear);

}
