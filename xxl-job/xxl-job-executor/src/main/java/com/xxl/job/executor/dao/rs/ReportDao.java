package com.xxl.job.executor.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.ms.ProjectProgressDetail;
import com.xxl.job.executor.entity.rs.CompanyRdSummary;
import com.xxl.job.executor.entity.rs.Report;
import com.xxl.job.executor.models.projectsummary.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.dao.rs
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-14 15:21
 * @Description: rsReport dao层
 */
@Repository
public interface ReportDao extends BaseMapper<Report> {
    /**
     * 获取最后操作的人
     *
     * @param tableName
     * @param stage
     * @param hasYear
     * @param selfYear
     * @param lastTime
     * @return
     */
    List<ProjectProgressDetail> getLastOperationUser(@Param("tableName") String tableName,
                                                     @Param("stage") Integer stage,
                                                     @Param("hasYear") Boolean hasYear,
                                                     @Param("selfYear") Boolean selfYear,
                                                     @Param("lastTime") Date lastTime);

    List<ProjectProgressDetail> getEmployeeOpenid(@Param("lastTime") Date lastTime, @Param("stage") Integer stage);
    /**
     * 获取
     *
     * @param companyIds
     * @param lastTime
     * @param stage
     * @return
     */
//    List<ProjectProgressDetail> getBudgetSummary(@Param("companyIds") List<Integer> companyIds,
//                                                 @Param("lastTime") Date lastTime, @Param("stage") Integer stage);

    /**
     * 获取report cnt
     *
     * @param lastTime
     * @return
     */
    List<ProjectProgressDetail> getReportCnt(@Param("lastTime") Date lastTime);

    /**
     * 获取规划立项数
     *
     * @param data
     * @return
     */
    List<ReportSummaryModel> getAllReportCnt(@Param("data") List<BaseSummaryModel> data);

    /**
     * 获取项目立项数
     *
     * @param year
     * @param companyIds
     * @return
     */
    List<RdCntBudgetSummaryModel> getProjectCntAndBudget(@Param("year") Integer year,
                                                         @Param("companyIds") Set<Integer> companyIds);

    /**
     * 获取总归集费用
     *
     * @param year
     * @param companyIds
     * @param begin
     * @param end
     * @return
     */
    List<ProjectSummaryModel> getProjectRdFunds(@Param("year") Integer year,
                                                @Param("companyIds") Set<Integer> companyIds,
                                                @Param("begin") Date begin, @Param("end") Date end);


    /**
     * 获取项目
     *
     * @param year
     * @param companyIds
     * @param begin
     * @param end
     * @return
     */
    List<DocFileSummaryModel> getProjectDocFileDataCnt(@Param("year") Integer year,
                                                       @Param("companyIds") Set<Integer> companyIds,
                                                       @Param("begin") Date begin,
                                                       @Param("end") Date end);

    /**
     * 获取最后更新的公司id
     *
     * @param beforeFive
     * @return
     */
    List<Integer> getLastUpdateCompanyId(@Param("beforeFive") Date beforeFive);

    /**
     * 获取项目归集最新数据
     *
     * @param lastTime
     * @param stage
     * @return
     */
    List<ProjectProgressDetail> getRdFunds(@Param("lastTime") Date lastTime,
                                           @Param("stage") Integer stage);

    /**
     * 获取项目过程更新
     *
     * @param lastTime
     * @param stage
     * @return
     */
    List<ProjectProgressDetail> getDocFileData(@Param("lastTime") Date lastTime,
                                               @Param("stage") Integer stage);

    /**
     * 获取人员数
     *
     * @param year
     * @param companyIds
     * @return
     */
    List<RdCntEmployeeSummaryModel> getEmployeeCnt(@Param("year") Integer year, @Param("companyIds") Set<Integer> companyIds);

    /**
     * 获取设备数
     *
     * @param year
     * @param companyIds
     * @return
     */
    List<RdCntEquipmentSummaryModel> getEquipmentCnt(@Param("year") Integer year, @Param("companyIds") Set<Integer> companyIds);

    /**
     * 获取最新更新的规划费，归集数
     *
     * @param lastTime
     * @return
     */
    List<CompanyRdSummary> getLastUpdateData(@Param("lastTime") Date lastTime);

    /**
     * 获取年度成本
     *
     * @param year
     * @param companyIds
     * @return
     */
    List<FeeInfoModel> getYearCost(@Param("year") Integer year, @Param("companyIds") Set<Integer> companyIds);

    /**
     * 获取专利数
     *
     * @param year
     * @param companyIds
     * @param begin
     * @param end
     * @return
     */
    List<PatentSummaryModel> getPatentCnt(@Param("year") Integer year, @Param("companyIds") Set<Integer> companyIds,
                                          @Param("begin") Date begin, @Param("end") Date end);

    /**
     * 获取过程文件
     *
     * @param year
     * @param companyIds
     * @param begin
     * @param end
     * @return
     */
    List<LevelFileSummaryModel> getLevelFileCnt(@Param("year") Integer year, @Param("companyIds") Set<Integer> companyIds,
                                                @Param("begin") Date begin, @Param("end") Date end);

    /**
     * 提案管理
     *
     * @param year
     * @param companyIds
     * @return
     */
    List<ProposalSummaryModel> getProposalCnt(@Param("year") Integer year, @Param("companyIds") Set<Integer> companyIds);

    /**
     * 成果管理
     *
     * @param year
     * @param companyIds
     * @return
     */
    List<AchievementSummaryModel> getAchievementCnt(@Param("year") Integer year, @Param("companyIds") Set<Integer> companyIds);

    /**
     * 获取高品收入
     *
     * @param year
     * @param companyIds
     * @param begin
     * @param end
     * @return
     */
    List<HighTechSummaryModel> getHighTechIncome(@Param("year") Integer year, @Param("companyIds") Set<Integer> companyIds,
                                                 @Param("begin") Date begin, @Param("end") Date end);

    /**
     * 获取高品cnt
     *
     * @param year
     * @param companyIds
     * @return
     */
    List<HighTechSummaryModel> getHighTechCnt(@Param("year") Integer year, @Param("companyIds") Set<Integer> companyIds);

}
