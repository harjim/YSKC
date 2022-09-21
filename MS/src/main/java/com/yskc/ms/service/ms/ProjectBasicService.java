package com.yskc.ms.service.ms;

import com.yskc.common.exception.OwnerException;
import com.yskc.ms.models.project.*;

/**
 * Created by hck
 * on 2020/10/31 10:37
 * description:项目信息接口
 */
public interface ProjectBasicService {
    /**
     * 获取项目信息
     * @param projectId
     * @return
     */
    ProjectBasicModel getProjectInfo(Integer projectId);

    /**
     * 修改项目信息
     * @param model
     * @param userId
     * @return
     */
    Boolean editProjectInfo(ProjectBasicModel model, Integer userId) throws OwnerException;

    /**
     * 获取技术信息
     * @param projectId
     * @return
     */
    ProjectTechInfoModel getTechInfo(Integer projectId);

    /**
     * 获取财务信息
     * @param projectId
     * @return
     */
    ProjectFinanceInfoModel getFinanceInfo(Integer projectId);

    /**
     * 编辑技术信息
     * @param model
     * @param userId
     * @return
     */
    Boolean editTechInfo(ProjectTechInfoModel model, Integer userId);

    /**
     * 编辑财务信息
     * @param model
     * @param id
     * @return
     */
    Boolean editFinanceInfo(ProjectFinanceInfoModel model, Integer id);
}
