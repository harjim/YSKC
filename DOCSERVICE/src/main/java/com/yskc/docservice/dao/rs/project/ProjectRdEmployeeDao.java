package com.yskc.docservice.dao.rs.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.project.ProjectRdEmployeeEntity;
import com.yskc.docservice.models.rs.RdEmployeeSummaryModel;
import com.yskc.docservice.models.rs.projectrdemployee.MonthThingSetModel;
import com.yskc.docservice.models.rs.projectrdemployee.ProjectRdEmployeeModel;
import com.yskc.docservice.models.rs.projectrdemployee.ProjectRdEmployeeTotalModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
     * 获取费用summary
     *
     * @param projectIds
     * @param months
     * @return
     */
    List<ProjectRdEmployeeTotalModel> getSummary(@Param("projectIds") List<Integer> projectIds, @Param("months") List<Date> months);


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
     * 获取enumber 的id
     *
     * @param projectId
     * @param mKeysList
     * @return
     */
    List<ProjectRdEmployeeEntity> getEnumberId(@Param("projectId") Integer projectId,
                                               @Param("mKeysList") List<MonthThingSetModel> mKeysList);

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
     * 批量更新总考勤工时
     *
     * @param list
     * @return
     */
    int updateAttendances(@Param("list") List<ProjectRdEmployeeEntity> list);

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
}
