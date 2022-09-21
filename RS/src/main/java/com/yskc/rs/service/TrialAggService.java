package com.yskc.rs.service;

import com.yskc.rs.enums.CostEnum;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.projectyieldconfig.AggMsgModel;
import com.yskc.rs.models.projectyieldconfig.TrialAggModel;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2022-04-13 14:59
 * @Description: 试制归集
 */
public interface TrialAggService {

    /**
     * 研发试制计划归集辅助人员费用(全删全增式)
     *
     * @param aggModel
     * @param info
     * @param costEnum
     * @return
     */
    AggMsgModel aggRdTrialFee(TrialAggModel aggModel, CostEnum costEnum, UserInfo info);

}
