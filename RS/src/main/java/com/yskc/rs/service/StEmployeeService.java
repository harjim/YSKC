package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.models.BatchDeleteModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.StEmployeeExcel;
import com.yskc.rs.models.stEmployee.QueryStEmployeeModel;
import com.yskc.rs.models.stEmployee.StEmployeeModel;

import java.io.OutputStream;
import java.util.List;

public interface StEmployeeService {
    /**
     * 查询科技管理人员
     * @param query
     * @param info
     * @return
     */
    PageModel<List<StEmployeeModel>> getList(QueryStEmployeeModel query, UserInfo info);

    /**
     * 添加人员
     * @param model
     * @param info
     * @return
     */
    Boolean addStEmployee(StEmployeeModel model,UserInfo info);

    /**
     * 删除人员
     * @param deleteModel
     * @return
     */
    Boolean delStEmployee(BatchDeleteModel deleteModel);

    /**
     * 导出人员
     * @param companyId
     * @param query
     * @param out
     * @param path
     */
    void exportStEmployee(Integer companyId, QueryStEmployeeModel query, OutputStream out, String path);

    /**
     * 导入人员
     * @param info
     * @param excels
     * @param year
     * @return
     * @throws OwnerException
     */
    String importStEmployee(UserInfo info, List<StEmployeeExcel> excels, Integer year) throws OwnerException;
}
