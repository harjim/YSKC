package com.yskc.docservice.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.SalaryExcel;
import com.yskc.docservice.models.rs.fieldconfig.FieldConfigModel;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
public interface SalaryService {
    /**
     * 获取薪资配置
     *
     * @param companyId
     * @return
     */
    Map<String, FieldConfigModel> getSalaryConfig(Integer companyId);

    /**
     * 导入薪资数据
     *
     * @param info
     * @param salaryExcels
     * @return
     * @throws OwnerException
     */
    boolean importSalary(RsUserInfo info, List<SalaryExcel> salaryExcels) throws OwnerException;
}
