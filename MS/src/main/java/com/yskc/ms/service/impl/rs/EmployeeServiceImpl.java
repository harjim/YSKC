package com.yskc.ms.service.impl.rs;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yskc.common.exception.OwnerException;

import com.yskc.ms.dao.rs.EmployeeDao;
import com.yskc.ms.entity.rs.EmployeeEntity;
import com.yskc.ms.models.employee.EmployeeModel;
import com.yskc.ms.service.rs.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeDao, EmployeeEntity> implements EmployeeService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private EmployeeDao employeeDao;


    /**
     * @param companyId
     * @param enumber
     * @return
     * @throws OwnerException
     */
    @Override
    public EmployeeModel getEmployeeByenumber(int companyId, String enumber) throws OwnerException {
        return employeeDao.getEmployeeByenumber(companyId, enumber);
    }



}
