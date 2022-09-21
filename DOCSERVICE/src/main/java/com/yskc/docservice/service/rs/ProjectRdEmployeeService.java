package com.yskc.docservice.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.projectrdemployee.BatchProjectRdEmployeeModel;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2020-03-31 11:03
 * @Description: 项目人员费用业务层
 */
public interface ProjectRdEmployeeService {


    /**
     * 导入研发人员工时
     *
     * @param info
     * @param batchModel
     * @param year
     * @return
     * @throws OwnerException
     */
    Boolean importRdHour(RsUserInfo info, BatchProjectRdEmployeeModel batchModel, int year) throws OwnerException;
}
