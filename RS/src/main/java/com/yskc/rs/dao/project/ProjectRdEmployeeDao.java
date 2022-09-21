package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.project.ProjectRdEmployeeEntity;
import com.yskc.rs.models.aggregation.EmployeeExportModel;
import com.yskc.rs.models.aggregation.ExportTermModel;
import com.yskc.rs.models.employee.RdEmployeeSummaryModel;
import com.yskc.rs.models.init.InitRdUsedModel;
import com.yskc.rs.models.project.RdUsedHourModel;
import com.yskc.rs.models.projectDocFile.ProjectAnalysisModel;
import com.yskc.rs.models.projectattendance.ExportProjectAttendanceModel;
import com.yskc.rs.models.projectrdemployee.*;
import com.yskc.rs.models.workSheet.MonthWorkSheetItem;
import com.yskc.rs.models.workSheet.WorkSheetQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
     * 获取项目人员费用列表
     *
     * @param page
     * @param companyId
     * @param query
     * @return
     */
    List<ProjectRdEmployeeModel> getList(@Param("page") Pagination page, @Param("companyId") Integer companyId, @Param("query") QueryProjectRdEmployeeModel query);

    /**
     * 获取人员费用总计
     *
     * @param companyId
     * @param query
     * @return
     */
    ProjectRdEmployeeTotalModel getTotal(@Param("companyId") Integer companyId, @Param("query") QueryProjectRdEmployeeModel query);

    /**
     * 批量添加数据
     *
     * @param insertList
     * @return
     */
    int addBatch(@Param("list") List<ProjectRdEmployeeEntity> insertList);

    /**
     * 批量更新
     *
     * @param list
     * @return
     */
    int updateBatch(@Param("list") List<ProjectRdEmployeeEntity> list);

    /**
     * 获取费用summary
     *
     * @param projectIds
     * @param months
     * @return
     */
    List<ProjectRdEmployeeTotalModel> getSummary(@Param("projectIds") List<Integer> projectIds, @Param("months") List<Date> months);

    /**
     * 获取所有使用过得研发工时
     *
     * @param companyId
     * @param query
     * @param enumbers
     * @return
     */
    List<RdUsedHourModel> getAllUsed(@Param("companyId") Integer companyId, @Param("query") QueryProjectRdEmployeeModel query
            , @Param("enumbers") List<String> enumbers);

    /**
     * 获取已归集数据
     *
     * @param ids
     * @param projectId
     * @param end
     * @param year
     * @param begin
     * @return
     */
    List<InitRdUsedModel> getRdUsed(@Param("ids") List<Integer> ids, @Param("projectId") Integer projectId,
                                    @Param("end") Date end, @Param("year") Integer year,
                                    @Param("begin") Date begin);

    /**
     * 通过enumber 获取拥有研发考勤的数据
     *
     * @param companyId
     * @param enumbers
     * @param month
     * @param all
     * @param projectId
     * @param etype
     * @return
     */
    List<ExportProjectAttendanceModel> getByEnumbers(@Param("companyId") Integer companyId,
                                               @Param("enumbers") Set<String> enumbers,
                                               @Param("month") Date month,
                                               @Param("all") Boolean all,
                                               @Param("projectId") Integer projectId,
                                               @Param("year") Integer year,
                                               @Param("etype") Integer etype);

    /**
     * 获取研发工单记录
     *
     * @param query
     * @return
     */
    List<MonthWorkSheetItem> getMonthWorkSheetList(@Param("query") WorkSheetQuery query);

    /**
     * 根据月份获取研发人员信息
     *
     * @param companyId
     * @param projectIds
     * @param month
     * @param year
     * @return
     */
    List<RdEmployeeSummaryModel> queryEmployeeList(@Param("page") Pagination page, @Param("companyId") Integer companyId,
                                                   @Param("projectIds") List<Integer> projectIds, @Param("month") Date month,
                                                   @Param("year") Integer year);

    /**
     * 根据月份获取研发人员信息
     *
     * @param companyId
     * @param projectIds
     * @param month
     * @param year
     * @return
     */
    List<RdEmployeeSummaryModel> queryEmployees(@Param("companyId") Integer companyId,
                                                @Param("projectIds") List<Integer> projectIds, @Param("month") Date month,
                                                @Param("year") Integer year);


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
     * 导出人员研发费用
     *
     * @param term
     * @param companyId
     * @param year
     * @return
     */
    List<EmployeeExportModel> exportFromEmployee(@Param("term") ExportTermModel term,
                                                 @Param("companyId") Integer companyId, @Param("year") Integer year);

    /**
     * 获取研发项目人员列表
     *
     * @param ids
     * @return
     */
    List<ProjectRdEmployeeModel> getRdEmployeeList(@Param("ids") List<Integer> ids);

    /**
     * 获取使用过的sum
     *
     * @param companyId
     * @param projectIds
     * @param mKeysList
     * @return
     */
    List<ProjectRdEmployeeModel> getUsedSum(@Param("companyId") Integer companyId, @Param("projectIds") List<Integer> projectIds,
                                            @Param("mKeysList") List<MonthThingSetModel> mKeysList);

    /**
     * 获取enumber 的id
     *
     * @param projectId
     * @param mKeysList
     * @return
     */
    List<ProjectRdEmployeeEntity> getEnumberId(@Param("projectId") Integer projectId,
                                               @Param("mKeysList") List<MonthThingSetModel> mKeysList);

    /**
     * 批量更新总考勤工时
     *
     * @param list
     * @return
     */
    int updateAttendances(@Param("list") List<ProjectRdEmployeeEntity> list);

    /**
     * 获取研发人员项目考勤数据
     *
     * @param enumbers
     * @param projectId
     * @param month
     * @return
     */
    List<ProjectRdEmployeeEntity> getAtteByEnumbers(@Param("enumbers") Set<String> enumbers,
                                                    @Param("projectId") Integer projectId, @Param("month") Date month);

    /**
     * 获取研发工资核算列表
     *
     * @param page
     * @param companyId
     * @param query
     * @return
     */
    List<SalaryAccountingModel> getRdAccountingList(@Param("page") Pagination page, @Param("companyId") Integer companyId,
                                                    @Param("query") QueryProjectRdEmployeeModel query);

    /**
     * 获取月归集列表
     *
     * @param month
     * @param companyId
     * @return
     */
    List<ProjectRdEmployeeModel> getAggList(@Param("month") Date month, @Param("companyId") Integer companyId);

    /**
     * 统计项目分析预计，实际参与研发人数
     *
     * @param companyId
     * @param projectId
     * @return
     */
    ProjectAnalysisModel countAnalysisMember(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId);

    /**
     * 获取月份人员统计工时
     *
     * @param page
     * @param companyId
     * @param month
     * @param year
     * @return
     */
    List<RdEmployeeSummaryModel> getMonthTotalStaff(@Param("page") Pagination page, @Param("companyId") Integer companyId,
                                                    @Param("month") Date month, @Param("year") Integer year);

    /**
     * 删除数据
     *
     * @param companyId
     * @param year
     * @param month
     * @param etypes
     * @return
     */
    int deleteData(@Param("companyId") Integer companyId, @Param("year") Integer year, @Param("month") Date month,
                   @Param("etypes") List<Integer> etypes);

    /**
     * 获取费用明细
     *
     * @param query
     * @return
     */
    List<SalaryRdFeeBaseModel> getFeeDetail(@Param("query") QueryProjectRdEmployeeModel query);
}
