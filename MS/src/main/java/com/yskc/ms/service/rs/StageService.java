package com.yskc.ms.service.rs;

import com.yskc.ms.models.QueryStageModel;
import com.yskc.ms.models.SysDictionaryModel;
import com.yskc.ms.models.rs.RsStageModel;

import java.util.List;

/**
 * @DateTime: 2021/9/15 14:29
 * @Description:
 * @author: hsx
 */
public interface StageService {

    /**
     * @return
     */
    List<SysDictionaryModel> searchStage();

    /**
     * @param queryStageModel
     * @return
     */
    List<RsStageModel> queryStage(QueryStageModel queryStageModel);
}
