package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.project.ProjectFinaSchedule;
import com.yskc.rs.models.excel.ProjectFinaScheduleExcel;
import com.yskc.rs.models.projectfinaschedule.ProjectFinaScheduleModel;
import com.yskc.rs.models.projectfinaschedule.QueryProjectFinaScheduleModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao.project
 * @Author: zhangdingfu
 * @CreateTime: 2021-06-17 14:19
 * @Description: 项目试验试制实际工时表dao层
 */
@Repository
public interface ProjectFinaScheduleDao extends BaseMapper<ProjectFinaSchedule> {
    /**
     * 获取试验试制实际工时表(分页)
     *
     * @param page
     * @param query
     * @param companyId
     * @return
     */
    List<ProjectFinaScheduleModel> getList(@Param("page") Pagination page,
                                           @Param("query") QueryProjectFinaScheduleModel query,
                                           @Param("companyId") Integer companyId);

    /**
     * 获取试验试制实际工时表（不分页）
     * @param query
     * @param companyId
     * @return
     */
    List<ProjectFinaScheduleModel> getData(@Param("query") QueryProjectFinaScheduleModel query,
                                           @Param("companyId") Integer companyId);

    /**
     * 插入或更新
     *
     * @param list
     * @return
     */
    int insertOrUpdate(@Param("list") List<ProjectFinaSchedule> list);

    /**
     * 获取存在id
     *
     * @param list
     * @param companyId
     * @return
     */
    List<Integer> getExistIds(@Param("list") List<ProjectFinaScheduleExcel> list, @Param("companyId") Integer companyId);

    /**
     * 通过月份获取财务排期
     *
     * @param month
     * @param companyId
     * @return
     */
    List<ProjectFinaScheduleModel> getByMonth(@Param("month") Date month, @Param("companyId") Integer companyId);

    /**
     * 获取相同数据
     *
     * @param companyId
     * @param projectId
     * @param month
     * @param deptName
     * @return
     */
    Integer getSameDataId(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId,
                          @Param("month") Date month, @Param("deptName") String deptName);

    /**
     * 更新
     *
     * @param e
     * @return
     */
    Integer updateEntity(@Param("e") ProjectFinaSchedule e);

}
