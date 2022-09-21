package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.entity.project.ProjectAttendance;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.attendance.AttendanceModel;
import com.yskc.rs.models.excel.ProjectAttendanceExcel;
import com.yskc.rs.models.projectattendance.*;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 研发人员费用
 *
 * @author huronghua
 */
public interface ProjectAttendanceService {
    /**
     * 获取人员研发考勤列表
     *
     * @param companyId
     * @param query
     * @return
     */
    PageModel<List<ProjectHourAttendance>> getHourList(Integer companyId, QueryBatchEmployee query);

    /**
     * 刷新研发考勤记录
     *
     * @param userInfo
     * @param refresh
     * @return
     * @throws OwnerException
     */
    Boolean refresh(UserInfo userInfo, RefreshAttendance refresh) throws OwnerException;

    /**
     * 保存研发考勤
     *
     * @param userInfo
     * @param batch
     * @param isImport
     * @return
     * @throws OwnerException
     */
    Boolean saveData(UserInfo userInfo, BatchAttendanceUsedModel batch,boolean isImport) throws OwnerException;

    /**
     * 导出研发考勤数据
     *
     * @param query
     * @param info
     * @param out
     * @return
     * @throws OwnerException
     */
    void exportHourData(QueryBatchEmployee query, UserInfo info,OutputStream out) throws OwnerException;

    /**
     * 获取人员研发考勤时间列表
     *
     * @param query
     * @return
     */
    PageModel<List<TimeAttendanceModel>> getTimeList(QueryBatchEmployee query);

    /**
     * 导出研发考勤时间数据
     *
     * @param query
     * @return
     */
    List<TimeAttendanceModel> exportTimeData(QueryBatchEmployee query);

    /**
     * 获取所有项目研发工时
     *
     * @param query
     * @param companyId
     * @return
     */
    PageModel<List<ProjectAllRdHourModel>> getAllRdHour(QueryAllRdHourModel query, Integer companyId);

    /**
     * 获取所有项目研发时间
     *
     * @param query
     * @param companyId
     * @return
     */
    PageModel<List<ProjectAllRdTimeModel>> getAllRdTime(QueryAllRdHourModel query, Integer companyId);

    /**
     * 导出所有项目研发工时
     *
     * @param query
     * @param companyId
     * @return
     */
    List<ProjectAllRdHourModel> exportAllRdHour(QueryAllRdHourModel query, Integer companyId);

    /**
     * 后端导出所有项目研发工时
     * @param query
     * @param companyId
     * @param out
     * @param path
     */
    void exportAllRdHour(QueryAllRdHourModel query, Integer companyId, OutputStream out, String path) throws OwnerException;
    /**
     * 导出所有项目研发时间
     *
     * @param query
     * @param companyId
     * @return
     */
    List<ProjectAllRdTimeModel> exportAllRdTime(QueryAllRdHourModel query, Integer companyId);

    /**
     * 获取是否存在考勤记录
     *
     * @param companyId
     * @return
     */
    Boolean hasAttendance(Integer companyId);

    /**
     * 删除研发考勤
     *
     * @param userInfo
     * @param model
     * @return
     */
    Boolean delData(UserInfo userInfo, List<AttendanceModel> model) throws OwnerException;

    /**
     * editAttendance
     *
     * @param userInfo
     * @param attendance
     * @return
     * @throws OwnerException
     */
    Boolean editAttendance(UserInfo userInfo, ProjectAttendance attendance) throws OwnerException;

    /**
     * 删除已使用工时
     *
     * @param info
     * @param enumbers
     * @param monthBegin
     * @param monthEnd
     * @param projectId
     */
    void deleteUsed(UserInfo info, Set<String> enumbers, Date monthBegin, Date monthEnd, Integer projectId);

    /**
     * 导入研发考勤记录
     *
     * @param userInfo
     * @param data
     * @param month
     * @throws OwnerException
     */
    void importRdAttendance(UserInfo userInfo, List<ProjectAttendanceExcel> data,Date month)throws OwnerException;
}
