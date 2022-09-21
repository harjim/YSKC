package com.yskc.ms.service.es;

import com.yskc.common.model.PageModel;
import com.yskc.ms.models.newexpert.achievement.AchievementLogModel;
import com.yskc.ms.models.newexpert.achievement.AchievementModel;
import com.yskc.ms.models.newexpert.achievement.QueryAchievementModel;

import java.util.List;
import java.util.Map;

/**
 * @DateTime: 2021/11/12 9:29
 * @Description:
 * @author: hsx
 */
public interface EsAchievementService {

    /**
     * 获取审核列表
     * @param model
     * @return
     */
    PageModel<List<AchievementModel>> getList(QueryAchievementModel model);

    /**
     * 获取成果数据
     * @param id
     * @return
     */
    AchievementModel getData(Integer id);

    /**
     * 成果审核
     * @param model
     * @return
     */
    Boolean audit(AchievementLogModel model);

    /**
     * 获取成果审核日志
     * @param id
     * @return
     */
    List<AchievementLogModel> getLogs(Integer id);

    /**
     * 获取不同审核状态的统计
     * @return
     */
    Map<String, Integer> getCount();
}
