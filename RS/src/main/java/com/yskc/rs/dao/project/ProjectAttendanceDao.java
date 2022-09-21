package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.project.ProjectAttendance;
import com.yskc.rs.models.attendance.AttendanceModel;
import com.yskc.rs.models.projectattendance.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 研发人员Dao
 *
 * @author huronghua
 */
@Repository
public interface ProjectAttendanceDao extends BaseMapper<ProjectAttendance> {

    /**
     * 获取人员考勤列表
     *
     * @param page
     * @param query
     * @return
     */
    List<ProjectHourAttendance> getHourList(@Param("page") Pagination page, @Param("query") QueryBatchEmployee query, @Param("year")String year);

    /**
     * 获取研发考勤记录的人员数据
     *
     * @param projectId
     * @param month
     * @param ids
     * @return
     */
    List<ProjectAttendanceModel> getRdAttendanceList(@Param("projectId") Integer projectId, @Param("month") Date month, @Param("ids") List<Integer> ids);


    /**
     * 获取无用的研发考勤（修改过的归集数据已存在的研发考勤无效）
     *
     * @param enumbers
     * @param projectId
     * @param monthBegin
     * @param monthEnd
     * @return
     */
    List<BatchProjectAttendance> getUseless(@Param("enumbers") Set<String> enumbers, @Param("monthBegin") Date monthBegin,
                                            @Param("monthEnd") Date monthEnd, @Param("projectId") Integer projectId);

    /**
     * 插入研发考勤
     *
     * @param list
     * @return
     */
    int insertBatch(@Param("list") List<ProjectAttendance> list);

    /**
     * 批量更新
     *
     * @param list
     * @return
     */
    int updateBatch(@Param("list") List<ProjectAttendance> list);

    /**
     * 导出数据
     *
     * @param monthBegin
     * @param monthEnd
     * @param companyId
     * @param query
     * @return
     */
    List<ProjectAttendance> getExportData(@Param("monthBegin") Date monthBegin, @Param("monthEnd") Date monthEnd,
                                          @Param("companyId") Integer companyId, @Param("query") QueryBatchEmployee query);

    /**
     * 获取研发考勤
     *
     * @param projectId
     * @param enumbers
     * @param monthBegin
     * @param monthEnd
     * @return
     */
    List<BatchProjectHourAttendance> getRdAttendanceInfoList(@Param("projectId") Integer projectId,
                                                             @Param("enumbers") List<String> enumbers,
                                                             @Param("monthBegin") Date monthBegin,
                                                             @Param("monthEnd") Date monthEnd);

    /**
     * 获取人员研发考勤时间列表
     *
     * @param page
     * @param query
     * @param monthBegin
     * @param monthEnd
     * @return
     */
    List<TimeAttendanceModel> getTimeList(@Param("page") Pagination page, @Param("query") QueryBatchEmployee query,
                                          @Param("monthBegin") Date monthBegin,
                                          @Param("monthEnd") Date monthEnd, @Param("year")String year);

    /**
     * 获取人员研发考勤时间列表(不分页，导出操作)
     *
     * @param query
     * @param monthBegin
     * @param monthEnd
     * @return
     */
    List<TimeAttendanceModel> getTimeList(@Param("query") QueryBatchEmployee query,
                                          @Param("monthBegin") Date monthBegin, @Param("monthEnd") Date monthEnd, @Param("year")String year);

    /**
     * 通过id 获取（部分关键字段）
     *
     * @param ids
     * @return
     */
    List<ProjectAttendance> getByIds(@Param("ids") List<Integer> ids);

    /**
     * 获取对应月份天数存在人员研发记录总数
     *
     * @param projectId
     * @param monthBegin
     * @param monthEnd
     * @return
     */
    List<Date> getAttData(@Param("projectId") Integer projectId, @Param("monthBegin") Date monthBegin, @Param("monthEnd") Date monthEnd);

    /**
     * month获取考勤记录
     *
     * @param projectId
     * @param monthBegin
     * @param monthEnd
     * @return
     */
    List<ProjectAttendance> getByMonth(@Param("projectId") Integer projectId,
                                       @Param("monthBegin") Date monthBegin, @Param("monthEnd") Date monthEnd);

    /**
     * 获取所有项目研发工时
     *
     * @param projectIds
     * @param begin
     * @param end
     * @return
     */
    List<MiniRdHourModel> getAllRdHour(@Param("projectIds") Set<Integer> projectIds, @Param("begin") Date begin,
                                       @Param("end") Date end);

    /**
     * 获取所有项目研发时间
     *
     * @param page
     * @param query
     * @param companyId
     * @return
     */
    List<ProjectAllRdTimeModel> getAllRdTime(@Param("page") Pagination page, @Param("query") QueryAllRdHourModel query, @Param("companyId") Integer companyId);

    /**
     * 获取所有项目研发时间
     *
     * @param query
     * @param companyId
     * @return
     */
    List<ProjectAllRdTimeModel> getAllRdTime(@Param("query") QueryAllRdHourModel query, @Param("companyId") Integer companyId);

    /**
     * 研发考勤分页
     *
     * @param page
     * @param query
     * @param companyId
     * @return
     */
    List<ProjectAllRdHourModel> getForRdHour(@Param("page") Pagination page, @Param("query") QueryAllRdHourModel query,
                                             @Param("companyId") Integer companyId);


    /**
     * 研发考勤(不分页)
     *
     * @param query
     * @param companyId
     * @return
     */
    List<ProjectAllRdHourModel> getForRdHour(@Param("query") QueryAllRdHourModel query,
                                             @Param("companyId") Integer companyId);

    /**
     * 获取月份工时
     *
     * @param companyId
     * @param monthBegin
     * @param monthEnd
     * @param enumbers
     * @param projectId
     * @return
     */
    List<MiniRdHourModel> getMonthRdHour(@Param("companyId") Integer companyId, @Param("monthBegin") Date monthBegin,
                                         @Param("monthEnd") Date monthEnd, @Param("enumbers") List<String> enumbers,
                                         @Param("projectId") Integer projectId);

    /**
     * 获取有工资的enumber
     *
     * @param projectId
     * @param monthBegin
     * @param monthEnd
     * @param companyId
     * @return
     */
    List<String> getHasSalaryEnumbers(@Param("projectId") Integer projectId, @Param("month") Date month, @Param("monthBegin") Date monthBegin,
                                      @Param("monthEnd") Date monthEnd, @Param("companyId") Integer companyId);

    /**
     * 获取考勤数据
     *
     * @param models
     * @return
     */
    List<ProjectAttendance> getAttendances(@Param("models") List<AttendanceModel> models);

    /**
     * 更新时间
     *
     * @param pa
     * @return
     */
    int updateTime(@Param("pa") ProjectAttendance pa);

    /**
     * 统计当前月是否存在打卡记录
     *
     * @param companyId
     * @param start
     * @param end
     * @return
     */
    int countOwner(@Param("companyId") Integer companyId, @Param("start") Date start, @Param("end") Date end);

    /**
     * 获取备份数据
     *
     * @param companyId
     * @return
     */
    List<String> getBackData(@Param("companyId") int companyId);

    /**
     * 通过id插入数据
     *
     * @param list
     * @return
     */
    int insertBatchAndId(@Param("list") List<ProjectAttendance> list);

    /**
     * 获取存在的id
     *
     * @param enumbers
     * @param companyId
     * @param begin
     * @param end
     * @return
     */
    List<BatchProjectHourAttendance> getUsedData(@Param("enumbers") Set<String> enumbers,
                                                 @Param("companyId") Integer companyId, @Param("begin") Date begin,
                                                 @Param("end") Date end);

    /**
     * 删除数据
     *
     * @param companyId
     * @param year
     * @param begin
     * @param end
     * @param etypes
     * @return
     */
    int deleteData(@Param("companyId") Integer companyId, @Param("year") Integer year, @Param("begin") Date begin,
                   @Param("end")Date end, @Param("etypes") List<Integer> etypes);
}
