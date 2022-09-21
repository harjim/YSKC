package com.yskc.ms.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.ms.models.employee.EmployeeModel;


/**
 * @author Administrator
 */
public interface EmployeeService {


    /**
     * 查询工号的唯一性
     *
     * @param companyId
     * @param enumber
     * @return
     * @throws OwnerException
     */
    EmployeeModel getEmployeeByenumber(int companyId, String enumber) throws OwnerException;


}
