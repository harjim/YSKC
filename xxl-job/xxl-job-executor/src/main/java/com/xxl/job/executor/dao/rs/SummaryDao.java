package com.xxl.job.executor.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.rs.ProjectDocFileEntity;
import com.xxl.job.executor.entity.rs.StageEntity;
import com.xxl.job.executor.entity.rs.Summary;
import com.xxl.job.executor.models.ProjectInfo.*;
import com.xxl.job.executor.models.projectsummary.FeeInfoModel;
import com.xxl.job.executor.models.projectsummary.ProjectSummaryModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.dao.rs
 * @Author: zhangdingfu
 * @CreateTime: 2020-06-17 09:02
 * @Description: 汇总dao层
 */
@Repository
public interface SummaryDao extends BaseMapper<Summary> {
//    /**
//     * 获取项目研发总额统计
//     * @param companyModels
//     * @return
//     */
//    List<ProjectDataModel> getCountData(@Param("companyModels") List<ProjectCustomerModel> companyModels);

    /**
     * 获取项目研发总额统计
     *
     * @param companyModels
     * @return
     */
    List<ProjectDataModel> getRdAmount(@Param("companyModels") List<ProjectCustomerModel> companyModels);

    /**
     * 统计过程文档数
     *
     * @param companyModels
     * @return
     */
    List<ProjectDataModel> countDocFile(@Param("companyModels") List<ProjectCustomerModel> companyModels);


    /**
     * 获取最后更新项目
     *
     * @param lastTime
     * @return
     */
    List<ProjectBudgetModel> getLastProject(@Param("lastTime") Date lastTime);

    /**
     * 获取最早归集月份
     *
     * @param projectIds
     * @return
     */
    List<ProjectBudgetModel> getMaximumMonths(@Param("projectIds") List<Integer> projectIds);

    /**
     * 获取最早三个月的数据
     *
     * @param list
     * @return
     */
    List<ProjectSummaryModel> getEarlyMonthFunds(@Param("list") List<ProjectBudgetModel> list);

    /**
     * 更新项目预算
     *
     * @param list
     * @return
     */
    int updateProjectBudget(@Param("list") List<ProjectBudgetModel> list);

    /**
     * 获取更新项目
     *
     * @param before
     * @return
     */
    List<ProjectDataModel> getByProject(@Param("before") Date before);

    /**
     * 获取项目信息
     *
     * @param projectIds
     * @return
     */
    List<ProjectModel> getProjects(@Param("projectIds") List<Integer> projectIds);

    /**
     * 获取项目成员统计
     *
     * @param projectIds
     * @return
     */
    List<MemberModel> getProjectMembers(@Param("projectIds") List<Integer> projectIds);

    /**
     * 获取项目设备统计
     *
     * @param projectIds
     * @return
     */
    List<MemberModel> getProjectEquipments(@Param("projectIds") List<Integer> projectIds);

    /**
     * 获取项目立项报告及审核状态
     *
     * @param projectIds
     * @return
     */
    List<MemberModel> getProjectReport(@Param("projectIds") List<Integer> projectIds);

    /**
     * 获取文档阶段
     *
     * @param projectId
     * @param docFileId
     * @return
     */
    StageEntity getStageInfo(@Param("projectId") Integer projectId, @Param("docFileId") Integer docFileId);

    /**
     * 获取项目过程文件
     *
     * @param projectIds
     * @return
     */
    List<ProjectDocFileEntity> getDocByProject(@Param("projectIds") List<Integer> projectIds);

    /**
     * 统计专利数据
     *
     * @param projectIds
     * @return
     */
    List<MemberModel> getPatentData(@Param("projectIds") List<Integer> projectIds);

    /**
     * 获取研发费（月）
     *
     * @param year
     * @param companyIds
     * @param begin
     * @param end
     * @return
     */
    List<FeeInfoModel> getRdFunds(@Param("year") Integer year, @Param("companyIds") Set<Integer> companyIds, @Param("begin") Date begin, @Param("end") Date end);

    /**
     * 获取营收
     *
     * @param year
     * @param companyIds
     * @return
     */
    List<FeeInfoModel> getRevenues(@Param("year") Integer year,@Param("companyIds") Set<Integer> companyIds);
}
