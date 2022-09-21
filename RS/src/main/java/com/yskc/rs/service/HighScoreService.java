package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.rs.entity.HighTechScoreEntity;
import com.yskc.rs.entity.hightech.HighFinanceScoreEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.highscore.HighFinanceScoreModel;
import com.yskc.rs.models.highscore.HighTechScoreModel;
import com.yskc.rs.models.highscore.HighTotalScoreModel;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2021-10-25 08:31
 * @Description: 高新评分评价业务接口层
 */
public interface HighScoreService {
    /**
     * 获取高新评分评价
     *
     * @param year
     * @param companyId
     * @return
     */
    HighTechScoreModel getTechScore(Integer year, Integer companyId);

    /**
     * 保存高新评分评价
     *
     * @param highScore
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    Boolean saveTechScore(HighTechScoreEntity highScore, UserInfo userInfo) throws OwnerException;

    /**
     * 获取财务评分评价
     *
     * @param year
     * @param companyId
     * @return
     */
    HighFinanceScoreModel getFinanceScore(Integer year, Integer companyId);

    /**
     * 保存财务评分评价
     *
     * @param financeScore
     * @param info
     * @return
     */
    Boolean saveFinanceScore(HighFinanceScoreEntity financeScore, UserInfo info);

    /**
     * 获取综合得分
     *
     * @param year
     * @param companyId
     */
    HighTotalScoreModel getTotalScore(Integer year, Integer companyId);
}
