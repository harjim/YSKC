package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.PageResult;
import com.yskc.rs.models.BatchDeleteModel;
import com.yskc.rs.models.KeysAndIdsModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.employee.EmployeeTermModel;
import com.yskc.rs.models.employee.RdEmployeeModel;
import com.yskc.rs.models.excel.EmployeeExcel;
import com.yskc.rs.models.excel.ImportEmployeeExcel;
import com.yskc.rs.models.project.ProjectEmployeeInfo;
import com.yskc.rs.models.projectattendance.DataEmployeeQuery;
import com.yskc.rs.models.rdemployeehour.QueryRdEmployeeHourModel;
import com.yskc.rs.models.rdemployeehour.RdEmployeeHourModel;
import com.yskc.rs.models.reviewCommittee.QueryReviewModel;

import java.io.OutputStream;
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
     * 分页查询研发花名册
     *
     * @param companyId
     * @param termModel
     * @return
     */
    PageResult getRdEmployeeData(Integer companyId, EmployeeTermModel termModel);


    /**
     * @param info
     * @param
     * @return
     * @throws OwnerException
     */
    boolean addRdEmployeeData(UserInfo info, RdEmployeeModel rdEmployeeModel) throws OwnerException;

    /**
     * 删除
     *
     * @param info
     * @param model
     * @return
     * @throws OwnerException
     */
    boolean delRdEmployee(UserInfo info, RdEmployeeModel model) throws OwnerException;


    /**
     * 批设置量研发部门
     *
     * @param info
     * @param model
     * @return
     * @throws OwnerException
     */
    boolean updateRdEmployeeRdDept(UserInfo info, RdEmployeeModel model) throws OwnerException;


    /**
     * 批量设置研发类型
     *
     * @param info
     * @param model
     * @return
     * @throws OwnerException
     */
    boolean updateRdEmployeeEtype(UserInfo info, RdEmployeeModel model) throws OwnerException;

    /**
     * 设置研发花名册人员职位
     *
     * @param userInfo
     * @param model
     * @return
     */
    boolean setPosition(UserInfo userInfo, RdEmployeeModel model) throws OwnerException;

    /**
     * 批量删除
     *
     * @param info
     * @param deleteModel
     * @return
     * @throws OwnerException
     */
    boolean delRdEmployeeBatch(UserInfo info, BatchDeleteModel deleteModel) throws OwnerException;


    /**
     * 导出数据
     *
     * @param companyId
     * @param termModel
     * @return
     */
    List<EmployeeExcel> deriveRdEmployee(Integer companyId, EmployeeTermModel termModel);

    /**
     * 后端导出数据
     * @param companyId
     * @param termModel
     * @param out
     * @param path
     * @throws OwnerException
     */
    void  getRdEmployeeExport(Integer companyId, EmployeeTermModel termModel, OutputStream out, String path) throws OwnerException;
    /**
     * 获取研发人员列表
     *
     * @param companyId
     * @param query
     * @return
     */
    PageModel<List<ProjectEmployeeInfo>> getProjectEmployeeList(Integer companyId, DataEmployeeQuery query);

    /**
     * 获取会议签到研发人员列表
     *
     * @param companyId
     * @param query
     * @return
     */
    PageModel<List<ProjectEmployeeInfo>> getEmployeeList(Integer companyId, QueryReviewModel query);

    /**
     * 导入研发花名册
     *
     * @param info
     * @param employeeExcels
     * @param year
     * @return
     * @throws OwnerException
     */
    String importEmployee(UserInfo info, List<ImportEmployeeExcel> employeeExcels, Integer year) throws OwnerException;


    /**
     * 分配研发花名册人员至项目
     *
     * @param userInfo
     * @param keysAndIds
     * @return
     * @throws OwnerException
     */
    Boolean setProjectMember(UserInfo userInfo, KeysAndIdsModel keysAndIds) throws OwnerException;

    /**
     * 获取研发投入工时
     *
     * @param query
     * @param companyId
     * @return
     */
    PageModel<List<RdEmployeeHourModel>> getRdEmployeeHours(QueryRdEmployeeHourModel query,Integer companyId);
}
