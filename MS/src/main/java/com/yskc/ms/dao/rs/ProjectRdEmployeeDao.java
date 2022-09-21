package com.yskc.ms.dao.rs;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.rs.ProjectRdEmployeeEntity;
import com.yskc.ms.models.projectAudit.QueryAuditDataModel;
import com.yskc.ms.models.projectAudit.QueryRdFeeModel;
import com.yskc.ms.models.projectAudit.RdEmployeeListModel;
import com.yskc.ms.models.projectAudit.RdFeeEmployeeModel;
import com.yskc.ms.models.rs.InitMemberModel;
import com.yskc.ms.models.rs.RdEmployeeSummaryModel;
import com.yskc.ms.models.rs.summary.ProjectAttendanceModel;
import com.yskc.ms.models.rs.summary.ProjectAttendanceUsedModel;
import com.yskc.ms.models.rs.summary.ProjectRdEmployeeTotalModel;
import com.yskc.ms.models.rs.summary.YearKeysModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao.project
 * @Author: zhangdingfu
 * @CreateTime: 2020-03-31 11:02
 * @Description: 项目人员费用dao层
 */
@Repository
public interface ProjectRdEmployeeDao extends BaseMapper<ProjectRdEmployeeEntity> {
    /**
     * 根据月份获取研发人员信息
     *
     * @param companyId
     * @param projectIds
     * @param month
     * @param year
     * @return
     */
    List<RdEmployeeSummaryModel> queryEmployeeList(@Param("companyId") Integer companyId, @Param("projectIds") List<Integer> projectIds, @Param("month") Date month, @Param("year") Integer year);

    /**
     * 据项目月份返回人员数据
     *
     * @param page
     * @param query
     * @return
     */
    List<RdFeeEmployeeModel> getEmployeeFees(@Param("page") Pagination page, @Param("year") String year, @Param("query") QueryRdFeeModel query,
                                             @Param("monthend") Date monthend);

    /**
     * 根据年获取研发人员信息
     *
     * @param companyId
     * @param projectIds
     * @return
     */
    List<RdEmployeeSummaryModel> queryListByYear(@Param("companyId") Integer companyId,
                                                 @Param("projectIds") List<Integer> projectIds,
                                                 @Param("year") Integer year);

    /**
     * 获取研发花名册列表
     *
     * @param page
     * @param query
     * @return
     */
    List<RdEmployeeListModel> getList(@Param("page") Pagination page, @Param("query") QueryAuditDataModel query);

    /**
     * 不分页
     *
     * @param query
     * @return
     */
    List<RdEmployeeListModel> getList(@Param("query") QueryAuditDataModel query);

    /**
     * 统计
     *
     * @param query
     * @return
     */
    Map<String, Object> getTotal(@Param("query") QueryAuditDataModel query);

    /**
     * 删除项目相关费用
     *
     * @param projectId
     * @param yearKeys
     * @return
     */
    int deleteRdEmployee(@Param("projectId") Integer projectId, @Param("yearKeys") List<YearKeysModel> yearKeys);

    /**
     * 获取项目打卡记录
     *
     * @param projectId
     * @param yearKeys
     * @return
     */
    List<ProjectAttendanceModel> getAttendanceList(@Param("projectId") Integer projectId,
                                                   @Param("yearKeys") List<YearKeysModel> yearKeys);

    /**
     * 获取使用工时
     *
     * @param attendanceList
     * @param companyId
     * @return
     */
    List<ProjectAttendanceUsedModel> getAttendanceUsed(@Param("list") List<ProjectAttendanceModel> attendanceList,
                                                       @Param("companyId") Integer companyId);

    /**
     * 删除打卡工时
     *
     * @param ids
     * @return
     */
    int deleteAttendances(@Param("ids") List<Integer> ids);

    /**
     * 删除使用
     *
     * @param ids
     * @return
     */
    int deleteAttendanceUsed(@Param("ids") List<Integer> ids);

    /**
     * 更新使用
     *
     * @param list
     * @param now
     * @param msUserId
     * @return
     */
    int updateAttendanceUsed(@Param("list") List<ProjectAttendanceUsedModel> list, @Param("now") Date now,
                             @Param("msUserId") Integer msUserId);

    /**
     * 获取项目成员的归集月份
     *
     * @param yearKeys
     * @param projectId
     * @return
     */
    List<Date> getMonths(@Param("yearKeys") List<YearKeysModel> yearKeys, @Param("projectId") Integer projectId);

    List<ProjectRdEmployeeTotalModel> getSummary(@Param("projectId") Integer projectId, @Param("months") List<Date> months);

    /**
     * 获取人员年份，enumbers
     *
     * @param ids
     * @return
     */
    List<InitMemberModel> getYearEnumbers(@Param("ids") List<Integer> ids);

    /**
     * 获取区间月份
     *
     * @param projectId
     * @param begin
     * @param end
     * @return
     */
    List<Date> getRangeMonths(@Param("projectId") Integer projectId, @Param("begin") Date begin, @Param("end") Date end);
}
