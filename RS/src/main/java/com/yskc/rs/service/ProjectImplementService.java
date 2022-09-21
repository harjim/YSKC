package com.yskc.rs.service;

import com.yskc.rs.entity.tech.ProjectImplementEntity;
import com.yskc.rs.models.UserInfo;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: wangxing
 * @CreateTime: 2019-09-24 08:29
 * @Description: 项目实效效果接口类
 */
public interface ProjectImplementService {

    /**
     * 根据项目获取信息
     * @param companyId
     * @param projectId
     * @return
     */
    ProjectImplementEntity queryImplement(Integer companyId, Integer projectId);

    /**
     * 新增
     * @param userInfo
     * @param entity
     * @return
     */
    Integer addProjectImplement(UserInfo userInfo, ProjectImplementEntity entity);
}
