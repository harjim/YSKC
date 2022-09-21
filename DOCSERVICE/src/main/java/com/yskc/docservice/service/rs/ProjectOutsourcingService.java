package com.yskc.docservice.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.ProjectOutsourcingExcel;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2021-06-08 15:01
 * @Description: 项目委外费用业务层接口
 */
public interface ProjectOutsourcingService {

    /**
     * 导入委外项目费用
     *
     * @param info
     * @param data
     * @param type
     * @param year
     * @return
     * @throws OwnerException
     */
    Boolean importOutsourcing(RsUserInfo info, List<ProjectOutsourcingExcel> data, Integer type, Integer year) throws OwnerException;
}
