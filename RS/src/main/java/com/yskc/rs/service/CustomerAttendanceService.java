package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.entity.CustomerAttendanceEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.customerattendance.CustomerAttendanceModel;
import com.yskc.rs.models.customerattendance.QueryCustomerAttendanceModel;
import com.yskc.rs.models.excel.CustomerAttendanceExcel;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-06 09:34
 * @Description: 员工考勤业务接口层
 */
public interface CustomerAttendanceService {

    /**
     * 查询考勤列表
     *
     * @param companyId
     * @param query
     * @return
     */
    PageModel<List<CustomerAttendanceModel>> getList(Integer companyId, QueryCustomerAttendanceModel query);

    /**
     * 添加考勤
     *
     * @param userInfo
     * @param entity
     * @return
     * @throws OwnerException
     */
    boolean add(UserInfo userInfo, CustomerAttendanceEntity entity) throws OwnerException;

    /**
     * 更新考勤
     *
     * @param userInfo
     * @param entity
     * @return
     */
    boolean update(UserInfo userInfo, CustomerAttendanceEntity entity) throws OwnerException;

    /**
     * 删除考勤
     *
     * @param id
     * @return
     */
    boolean delete(Integer id);

    /**
     * 批量删除考勤
     *
     * @param ids
     * @return
     */
    boolean deleteList(List<Integer> ids);

    /**
     * 导入考勤
     *
     * @param userInfo
     * @param data
     * @throws OwnerException
     */
    void importAttendance(UserInfo userInfo, List<CustomerAttendanceExcel> data) throws OwnerException;

    /**
     * 导出考勤
     *
     * @param companyId
     * @param query
     * @return
     */
    List<CustomerAttendanceModel> exportAttendance(Integer companyId, QueryCustomerAttendanceModel query);
}
