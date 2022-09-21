package com.yskc.ms.service.ms;

import com.yskc.common.exception.OwnerException;
import com.yskc.ms.models.ProjectTechStage.ProjectStageModel;
import com.yskc.ms.models.ProjectTechStage.SetDeadlineModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.product.AddStageModel;
import com.yskc.ms.models.product.StageModel;

import java.util.List;

/**
 * Created by hck
 * on 2020/7/28 15:14
 * description:
 */
public interface ProjectTechStageService {

    List<StageModel> getStageList(UserInfo userInfo, Integer projectId, Integer productId) throws OwnerException;

    /**
     * 查询项目类型阶段
     * @param userInfo
     * @param productId
     * @return
     */
    List<StageModel> queryProjectStage (UserInfo userInfo, Integer productId);
    /**
     * 编辑阶段截止时间
     * @param models
     * @param userInfo
     * @return
     */
    Boolean editDeadline(List<ProjectStageModel> models, UserInfo userInfo) throws OwnerException;

    /**
     * 批量设置截止时间
     * @param model
     * @param userInfo
     * @return
     */
    Boolean setDeadlines(SetDeadlineModel model,UserInfo userInfo) throws OwnerException;

}
