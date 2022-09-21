package com.yskc.ms.service.ms;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.params.ProjectParams;
import com.yskc.ms.models.project.BatchStaffModel;
import com.yskc.ms.models.project.ProjectInfoModel;
import com.yskc.ms.models.project.SetOwerModel;

import java.util.List;

public interface ProjectService {

    PageModel<List<ProjectInfoModel>> getProjectList(ProjectParams query, UserInfo userInfo, DataPermModel dataPerm);

    ProjectInfoModel getProjectDetail(UserInfo userInfo, Integer projectId);

    boolean updateProject(UserInfo userInfo, ProjectInfoModel projectInfo) throws OwnerException;

    /**
     * 批量设置技术人员
     *
     * @param batch
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    boolean setProjectTechIds(BatchStaffModel batch, UserInfo userInfo, Boolean isAdd) throws OwnerException;

    /**
     * 批量设置财务人员
     *
     * @param batch
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    boolean setProjectFinanceIds(BatchStaffModel batch, UserInfo userInfo, Boolean isAdd) throws OwnerException;

    boolean addProject(UserInfo userInfo, ProjectInfoModel projectInfo) throws OwnerException;

    /**
     * 设置业务人员
     *
     * @param setOwer
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    boolean setProjectOwerIds(SetOwerModel setOwer, UserInfo userInfo)throws OwnerException;
}
