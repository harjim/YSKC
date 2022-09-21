package com.yskc.rs.service;

import com.yskc.rs.entity.tech.ProjectInvestMentEntity;
import com.yskc.rs.models.UserInfo;
/**
 * @author Administrator
 */
public interface ProjectInvestMentService {
    /**
     *
     * @param companyId
     * @param projectId
     * @return
     */
    ProjectInvestMentEntity selectByProjectId(Integer companyId, Integer projectId);

    /**
     *
     * @param userInfo
     * @param model
     * @return
     */
    Integer saveInvestMent(UserInfo userInfo, ProjectInvestMentEntity model);
}
