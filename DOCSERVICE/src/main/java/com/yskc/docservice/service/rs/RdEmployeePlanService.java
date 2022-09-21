package com.yskc.docservice.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.RdPlanExcel;
import com.yskc.docservice.models.rs.rdplan.RdPlanTableField;

import java.util.List;

/**
 * Created by hck
 * on 2020/11/16 17:19
 * description:
 */
public interface RdEmployeePlanService {

    /**
     * 导入研发人员计划（多项目多月）
     *
     * @param info
     * @param data
     * @return
     */
    Boolean importPlanTime(RsUserInfo info, List<RdPlanExcel> data) throws OwnerException;

    /**
     * 导入分配研发人员计划（多月）
     *
     * @param info
     * @param excelResult
     * @param tableField
     * @return
     * @throws OwnerException
     */
    Boolean importAllocation(RsUserInfo info, ExcelResult<RdPlanExcel> excelResult, RdPlanTableField tableField)throws OwnerException;
}
