package com.yskc.docservice.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.EmployeeExcel;

import java.util.List;

/**
 * @author Administrator
 */
public interface EmployeeService {


    /**
     * 导入数据
     *
     * @param info
     * @param employeeExcels
     * @return
     * @throws OwnerException
     */
    boolean importEmployee(RsUserInfo info, List<EmployeeExcel> employeeExcels) throws OwnerException;

}
