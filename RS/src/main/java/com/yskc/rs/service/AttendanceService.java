package com.yskc.rs.service;

import com.yskc.common.model.PageModel;
import com.yskc.rs.entity.data.DataAttendanceEntity;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.models.BatchAttendanceModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.attendance.DataAttendanceModel;
import com.yskc.rs.models.excel.AttendanceExcel;

import java.util.Date;
import java.util.List;

/**
 * 员工考勤
 * @author huronghua
 */
public interface AttendanceService {
    /**
     * 获取员工考勤
     * @param pageNo
     * @param pageSize
     * @param companyId
     * @param ename
     * @param month
     * @return
     */
    PageModel<List<DataAttendanceModel>> getAttendanceList(int pageNo, int pageSize, Integer companyId, String ename, Date month);

    /**
     * 更新员工考勤
     * @param attendance
     * @return
     */
    boolean update(DataAttendanceEntity attendance);

    /**
     * 删除员工信息
     * @param attendance
     * @return
     */
    boolean delete(DataAttendanceEntity attendance);

    /**
     * 添加员工考勤记录
     * @param attendance
     * @return
     * @throws OwnerException
     */
    boolean add(DataAttendanceEntity attendance)  throws OwnerException;

    /**
     * 导入员工考勤
     * @param userInfo
     * @param attendanceExcels
     * @return
     * @throws Exception
     */
    boolean importInfo(UserInfo userInfo, List<AttendanceExcel> attendanceExcels) throws OwnerException;

    /**
     * 批量添加员工考勤记录
     * @param userInfo
     * @param model
     * @return
     * @throws OwnerException
     */
    boolean addList(UserInfo userInfo, BatchAttendanceModel model)  throws OwnerException;
}
