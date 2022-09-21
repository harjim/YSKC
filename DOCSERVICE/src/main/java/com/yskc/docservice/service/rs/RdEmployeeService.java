package com.yskc.docservice.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.ImportEmployeeExcel;


import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: wangxing
 * @CreateTime: 2019-11-18 14:37
 * @Description: RdEmployeeService
 */
public interface RdEmployeeService {

    /**
     * 导入研发花名册
     *
     * @param info
     * @param employeeExcels
     * @param year
     * @return
     * @throws OwnerException
     */
    String importEmployee(RsUserInfo info, List<ImportEmployeeExcel> employeeExcels, Integer year) throws OwnerException;

}
