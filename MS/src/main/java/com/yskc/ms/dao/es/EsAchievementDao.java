package com.yskc.ms.dao.es;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.es.EsAchievementEntity;
import com.yskc.ms.models.CountModel;
import com.yskc.ms.models.newexpert.achievement.AchievementLogModel;
import com.yskc.ms.models.newexpert.achievement.AchievementModel;
import com.yskc.ms.models.newexpert.achievement.QueryAchievementModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @DateTime: 2021/11/12 9:26
 * @Description:
 * @author: hsx
 */
@Repository
public interface EsAchievementDao extends BaseMapper<EsAchievementEntity> {

    /**
     * 获取审核列表
     * @param model
     * @param page
     * @return
     */
    List<AchievementModel> getList(@Param("page")Pagination page, @Param("model") QueryAchievementModel model);

    /**
     * 获取成果数据
     * @param id
     * @return
     */
    AchievementModel getData(@Param("id") Integer id);

    /**
     * 修改成果审核状态
     * @param model
     * @return
     */
    int editStatus(@Param("model")AchievementLogModel model);

    /**
     * 新增日志记录
     * @param model
     * @return
     */
    int insertLog(@Param("model") AchievementLogModel model);

    /**
     * 获取成果审核日志
     * @param id
     * @return
     */
    List<AchievementLogModel> getLogs(@Param("id") Integer id);

    /**
     * 获取不同审核状态的统计
     * @return
     */
    List<CountModel> getCount();
}
