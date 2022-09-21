package com.yskc.ms.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.ms.entity.rs.models.StepResultModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.patent.ParentStepModel;
import com.yskc.ms.models.project.QueryProjectStepModel;
import com.yskc.ms.models.project.StepListModel;
import com.yskc.ms.models.project.StepModel;
import com.yskc.ms.models.project.StepTreeModel;
import org.apache.xpath.operations.Bool;

import java.util.List;

/**
 * Created by hck
 * on 2020/5/19 17:18
 */
public interface RsProjectStepService {

    /**
     * 提交层级步骤完成增删改(功能待优化)
     *
     * @param userInfo
     * @param parentStepModels
     * @return
     */

    Boolean saveStep(UserInfo userInfo, ParentStepModel[] parentStepModels);

    /**
     * 获取类型步骤
     *
     * @param productId
     * @return
     */
    StepResultModel getStep(Integer productId);

    /**
     * 添加步骤
     *
     * @param model
     * @return
     */
    StepModel addStep(UserInfo info, QueryProjectStepModel model) throws OwnerException;

    /**
     * 批量添加步骤
     *
     * @param info
     * @param models
     * @return
     * @throws OwnerException
     */
    Boolean addStepList(UserInfo info, List<StepListModel> models) throws OwnerException;

    /**
     * 删除步骤
     *
     * @param model
     * @return
     * @throws OwnerException
     */
    Boolean delStep(QueryProjectStepModel model) throws OwnerException;

    /**
     * 编辑步骤
     *
     * @param userInfo
     * @param model
     * @return
     * @throws OwnerException
     */
    Boolean editStep(UserInfo userInfo, QueryProjectStepModel model) throws OwnerException;


}
