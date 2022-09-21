package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.Dept;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.models.company.ProjectDataModel;
import com.yskc.rs.models.docFile.DocFileFooterModel;
import com.yskc.rs.models.employee.EmployeeSelectModel;
import com.yskc.rs.models.hightech.HighTechProgressModel;
import com.yskc.rs.models.hightechprogress.HighTechProjectModel;
import com.yskc.rs.models.hightechprogress.QueryHighTechFileModel;
import com.yskc.rs.models.outsourcing.ProjectOutsourcingModel;
import com.yskc.rs.models.project.*;
import com.yskc.rs.models.projectSummary.ProjectSituationModel;
import com.yskc.rs.models.summary.TotalSummaryModel;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-15 10:15:45
 */
@Repository
public interface ProjectDao extends BaseMapper<ProjectEntity> {

    /**
     * 检查重复RD
     *
     * @param companyId
     * @param rdIndex
     * @param year
     * @param projectId
     * @return
     */
    List<ProjectModel> selectRd(@Param("companyId") Integer companyId, @Param("rdIndex") Integer rdIndex, @Param("year") Integer year, @Param("projectId") Integer projectId);

    List<HighTechProgressModel> gethtProgressList(@Param("page") Pagination page, @Param("companyIds") List<Integer> companyIds,@Param("year") Integer year);

    @Options(useGeneratedKeys = true)
    Integer insertProjectList(List<ProjectEntity> projectEntitys);

    Integer updateProjectList(@Param("projectEntitys") List<ProjectEntity> updateProjectEntitys);

    /**
     * 保存项目政府资金
     *
     * @param id
     * @param govCost
     * @param lastRsId
     * @param lastMsId
     * @param now
     * @return
     */
    int saveProjectGovCost(@Param("id") Integer id, @Param("govCost") BigDecimal govCost, @Param("lastRsId") Integer lastRsId,
                           @Param("lastMsId") Integer lastMsId, @Param("now") Date now);

    Integer selectMaxRd(@Param("companyId") Integer companyId, @Param("year") Integer year);

    /**
     * 修改项目预算
     *
     * @param projectId
     * @param estimateExpense
     * @return
     */
    Integer updateProjectFee(@Param("projectId") Integer projectId,
                             @Param("estimateExpense") BigDecimal estimateExpense);

    /**
     * 检查重复项目名
     *
     * @param pname
     * @param projectId
     * @return
     */
    Integer countPname(@Param("pname") String pname, @Param("projectId") Integer projectId);

    /**
     * 根据年份获取项目列表
     *
     * @param year
     * @param companyId
     * @param formula
     * @param pname
     * @return
     */
    List<ProjectListModel> getProjectsByYear(@Param("year") Integer year, @Param("companyId") Integer companyId, @Param("formula") Integer formula, @Param("pname") String pname);

    /**
     * 导出项目列表根据年份获取所有项目列表
     *
     * @param year
     * @param companyId
     * @return
     */
    List<ProjectListModel> queryListByYear(@Param("year") Integer year, @Param("companyId") Integer companyId);

    /**
     * 根据年份获取项目列表(导入用,只查传进year当年)
     *
     * @param year
     * @param companyId
     * @return
     */
    List<ProjectListModel> getProjectsForImport(@Param("year") Integer year, @Param("companyId") Integer companyId);

    /**
     * 申报汇总费用列表根据年份查项目
     *
     * @param companyId
     * @param year
     * @param child
     * @return
     */
    List<ProjectDetailModel> getProjectIdsByYear(@Param("companyId") Integer companyId, @Param("year") Integer year,
                                                 @Param("child") Boolean child);
    List<ProjectDetailModel> QueryProjectIdsByYear(@Param("companyId") Integer companyId, @Param("year") Integer year);


    /**
     * 获取申报费用
     *
     * @param companyId
     * @param year
     * @param beginMonth
     * @param endMonth
     * @return
     */
    List<TotalSummaryModel> getTotalSummary(@Param("companyId") Integer companyId, @Param("year") Integer year,
                                            @Param("beginMonth") Date beginMonth, @Param("endMonth") Date endMonth);

    /**
     * 更新项目负责人
     *
     * @param id
     * @param enumber
     * @return
     */
    int updateMaster(@Param("id") Integer id, @Param("enumber") String enumber);

    /**
     * 获取公司所有项目
     *
     * @param companyId
     * @return
     */
    List<ProjectEntity> getCompanyProjectList(Integer companyId);


    List<ProjectListModel> getProjectList(@Param("companyId") Integer companyId, @Param("query") QueryProjectListModel query);

    List<ProjectListModel> getAllProject(@Param("companyId") Integer companyId, @Param("year") Integer year);

    List<Dept> getDeptIds(@Param("companyId") Integer companyId, @Param("year") Integer year);
    /**
     * 按年获取公司项目
     *
     * @param companyId
     * @param year
     * @return
     */
    List<ProjectEntity> getProjectByCompany(@Param("companyId") Integer companyId, @Param("year") Integer year);

    /**
     * 检查合并项目rd是否存在
     *
     * @param rdIndex
     * @param beginYear
     * @param companyId
     * @param projectIds
     * @return
     */
    ProjectEntity checkParentRd(@Param("rdIndex") Integer rdIndex, @Param("beginYear") Integer beginYear, @Param("companyId") Integer companyId, @Param("projectIds") List<Integer> projectIds);

    /**
     * 合并项目更新为子项目
     *
     * @return
     */
    Boolean updateChildProject(@Param("entities") List<ProjectEntity> entities);

    /**
     * 获取所有父项目
     *
     * @param companyId
     * @return
     */
    List<ProjectSelectModel> queryAllParent(@Param("companyId") Integer companyId, @Param("parentIds") List<Integer> parentIds, @Param("beginYear") Integer beginYear);

    /**
     * 获取父项目下的子项目列表
     *
     * @param parentIds
     * @param companyId
     * @return
     */
    List<ProjectEntity> getChildByParent(@Param("parentIds") List<Integer> parentIds, @Param("companyId") Integer companyId);

    /**
     * 获取项目下拉列表
     *
     * @param companyId
     * @param year
     * @param sign（默认：所有项目 1：rd项目（parentId=0） 2：子项目（hasChild=false）
     * @return
     */
    List<ProjectSelectModel> getSelectList(@Param("companyId") Integer companyId, @Param("year") Integer year, @Param("sign") Integer sign);
    /**
     * 获取项目下拉列表(签名)
     *
     * @param companyId
     * @param year
     * @return
     */
    List<DocFileFooterModel> getSignatureList(@Param("companyId") Integer companyId, @Param("year") Integer year);

    List<ProjectEntity> getListByIds(@Param("ids") List<Integer> ids, @Param("companyId") Integer companyId);

    /**
     * 获取父项目下的子项目列表
     *
     * @param parentId
     * @return
     */
    List<ProjectEntity> getChildsById(@Param("parentId") Integer parentId);

    /**
     * 通过rdTitles获取数据
     *
     * @param rdTitles
     * @param companyId
     * @return
     */
    List<ProjectEntity> getByRdTitles(@Param("rdTitles") Set<String> rdTitles, @Param("companyId") Integer companyId);

    /**
     * 获取项目状态
     *
     * @param projectIds
     * @return
     */
    List<RdStatusModel> getRdsStatus(@Param("projectIds") List<Integer> projectIds);

    /**
     * 查询子项目数
     *
     * @param companyId
     * @param year
     * @return
     */
    int countChild(@Param("companyId") Integer companyId, @Param("year") Integer year);

    /**
     * 获取月项目数据
     *
     * @param companyId
     * @param begin
     * @param end
     * @return
     */
    List<ProjectDetailModel> getProjectsByMonth(@Param("companyId") Integer companyId,
                                                @Param("begin") Date begin, @Param("end") Date end);

    /**
     * 获取开始年
     *
     * @param id
     * @return
     */
    Integer getBeginYear(@Param("id") Integer id);

    /**
     * 更新阶段时，项目更新操作时间和人
     *
     * @param now
     * @param id
     * @param userId
     * @param msUserId
     * @return
     */
    Integer updateStage(@Param("now") Date now, @Param("id") Integer id, @Param("userId") Integer userId, @Param("msUserId") Integer msUserId);

    /**
     * 获取项目参会人员列表
     *
     * @param projectId
     * @param companyId
     * @param year
     * @return
     */
    List<EmployeeSelectModel> getMeetingEmployees(@Param("projectId") Integer projectId,
                                                  @Param("companyId") Integer companyId,
                                                  @Param("year") Integer year,@Param("docDate") Date docDate);

    /**
     * 获取评审委员会名单
     *
     * @param projectId
     * @param companyId
     * @param year
     * @return
     */
    List<EmployeeSelectModel> getAuditors(@Param("projectId") Integer projectId,
                                          @Param("companyId") Integer companyId,
                                          @Param("year") Integer year,@Param("docDate") Date docDate);

    /**
     * 获取归集总表项目信息表
     *
     * @param year
     * @param companyId
     * @return
     */
    List<ProjectListModel> getSummaryProjectInfo(@Param("year") Integer year, @Param("companyId") Integer companyId);

    /**
     * 按开始年查询原子项目下拉列表
     *
     * @param companyId
     * @param year
     * @return
     */
    List<ProjectInfoModel> getYearSelectList(@Param("companyId") Integer companyId, @Param("year") Integer year);

    /**
     * 查询项目总结中项目信息
     *
     * @param year
     * @param companyId
     * @return
     */
    List<ProjectSituationModel> getProjectInfo(@Param("year") Integer year, @Param("companyId") Integer companyId);

    List<ProjectEntity> getRdTitle(@Param("rdTitles") Set<String> rdTitles, @Param("companyId") Integer companyId);

    /**
     * 根据高品开始年或许项目列表
     *
     * @param startYear
     * @param companyId
     * @return
     */
    List<ProjectInfoModel> getByTechYear(@Param("startYear") Integer startYear, @Param("companyId") Integer companyId);

    /**
     * 获取委外项目
     *
     * @param year
     * @param companyId
     * @return
     */
    List<ProjectOutsourcingModel> getOutsourcingProject(@Param("year") Integer year, @Param("companyId") Integer companyId);

    /**
     * 获取多层级管理列表项目列表
     *
     * @param year
     * @param companyId
     * @return
     */
    List<ProjectEntity> getRdManages(@Param("year") Integer year, @Param("companyId") Integer companyId);

    /**
     * 获取项目立项数
     *
     * @param companyId
     * @param year
     * @param sign      1（大项目+原子项目） 2 子项目
     * @return
     */
    Integer getProjectNum(@Param("companyId") Integer companyId, @Param("year") Integer year, Integer sign);

    /**
     * 根据年获取所有项目
     *
     * @param companyId
     * @param year
     * @return
     */
    List<ProjectEntity> getProjects(@Param("companyId") Integer companyId, @Param("year") Integer year);

    /**
     * 获取项目预算和归集费用
     *
     * @param projectIds
     * @return
     */
    List<ProjectDataModel> getBudgetAndCost(@Param("projectIds") List<Integer> projectIds, @Param("year") Integer year);

    /**
     * 获取项目开始，结束日期
     *
     * @param id
     * @return
     */
    ProjectEntity getBeginAndEndDate(@Param("id") Integer id);

    /**
     * 获取研发试制计划部门（项目的部门/车间/产线/工艺段）
     *
     * @param id
     * @return
     */
    ProjectEntity getYieldDept(@Param("id") Integer id);

    /**
     * 获取项目开展类型
     *
     * @param projectId
     * @return
     */
    ProjectDetailModel getProject(@Param("projectId") Integer projectId);

    List<EmployeeSelectModel> getList(@Param("companyId") Integer companyId, @Param("year") Integer year, @Param("ename") String ename);

    /**
     * 获取项目简单列表
     *
     * @param companyId
     * @param year
     * @param sign
     * @return
     */
    List<ProjectSelectModel> getSimpleList(@Param("companyId") Integer companyId, @Param("year") Integer year, @Param("sign") Integer sign);

    /**
     * 获取月份区间内的项目
     *
     * @param companyId
     * @param begin
     * @param end
     * @return
     */
    List<Integer> getProjectIds(@Param("companyId") Integer companyId,@Param("begin") Date begin,@Param("end") Date end);

    /**
     * 公司分类查询高新进度明细
     * @param model
     * @param companyIds
     * @return
     */
    List<HighTechProjectModel> getHighTechProjects(@Param("model") QueryHighTechFileModel model, @Param("companyIds") List<Integer> companyIds);

    Integer checkProjectName(@Param("pname") String pname);
}
