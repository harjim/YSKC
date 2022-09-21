package com.yskc.docservice.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.FinancialConditionExcel;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: wangxing
 * @CreateTime: 2019-09-25 11:30
 * @Description: FinancialConditionService
 */
public interface FinancialConditionService {


    /**
     * 导入财务状况
     *
     * @param info
     * @param excels
     * @return
     * @throws OwnerException
     */
    boolean importFinancialCondition(RsUserInfo info, List<FinancialConditionExcel> excels) throws OwnerException;
}
