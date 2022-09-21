package com.yskc.docservice.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.ProjectExcel;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-15 10:34
 * @Description: 项目业务接口层
 */
public interface ProjectService {

    /**
     * 导入RD列表
     *
     * @param info
     * @param projectExcels
     * @param year
     * @return
     * @throws OwnerException
     */
    String importProject(RsUserInfo info, List<ProjectExcel> projectExcels, Integer year) throws OwnerException;

}
