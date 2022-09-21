package com.yskc.docservice.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.entity.rs.project.ProjectYieldConfigEntity;
import com.yskc.docservice.models.rs.RsUserInfo;

import java.util.List;


/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2020-09-02 18:16
 * @Description: 项目试制量配置业务接口层
 */
public interface ProjectYieldConfigService {

    /**
     * 导入试制量配置
     *
     * @param userInfo
     * @param data
     * @param projectId
     * @return
     */
    boolean importYield(RsUserInfo userInfo, List<ProjectYieldConfigEntity> data, Integer projectId) throws OwnerException;


}
