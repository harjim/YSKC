package com.yskc.rs.service;

import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.tech.ProjectOtherModel;

/**
 * 项目other 业务接口层
 *
 * @author zhangdingfu
 */
public interface ProjectOtherService {
    /**
     *
     * @param userInfo
     * @param model
     * @return
     */
    Integer saveOther(UserInfo userInfo, ProjectOtherModel model);

    /**
     *
     * @param companyId
     * @param projectId
     * @return
     */
    ProjectOtherModel queryAll(Integer companyId, Integer projectId);
}
