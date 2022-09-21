package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.rs.ProjectEntity;
import com.yskc.ms.entity.rs.SysDocumentEntity;
import com.yskc.ms.entity.rs.models.ProjectListModel;
import com.yskc.ms.models.project.RsProjectSummaryModel;
import com.yskc.ms.models.projectApproval.ProjectApprovalModel;
import com.yskc.ms.models.projectApproval.QueryProjectApprovalModel;
import com.yskc.ms.models.projectApproval.QueryProjectEquipmentModel;
import com.yskc.ms.models.projectAudit.QueryRdFeeModel;
import com.yskc.ms.models.projectAudit.QueryRdFundsModel;
import com.yskc.ms.models.projectAudit.RdFeeInspectionModel;
import com.yskc.ms.models.rdfunds.RdEquipmentFundsModel;
import com.yskc.ms.models.rdfunds.RdFinaFundsModel;
import com.yskc.ms.models.rdfunds.RdFundsAllModel;
import com.yskc.ms.models.rdfunds.RdMemberFundsModel;
import com.yskc.ms.models.rs.*;
import com.yskc.ms.models.rs.summary.InitEquipmentModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;


@Repository
public interface RsProjectDao extends BaseMapper<ProjectEntity> {


    /**
     * 申报汇总费用列表根据年份查项目
     *
     * @param companyId
     * @param year
     * @return
     */
    List<RdFundsModel> getProjectIdsByYear(@Param("companyId") Integer companyId, @Param("year") Integer year);

    /**
     * 据项目月份返回其他数据
     * @param page
     * @param query
     * @param monthend
     * @return
     */
    List<RdFeeInspectionModel> getInspectionFeesData(@Param("page") Pagination page, @Param("query") QueryRdFeeModel query,
                                                     @Param("monthend")Date monthend);


    List<ProjectListModel> getProjectsByYear(@Param("year") Integer year, @Param("companyId") Integer companyId);


    List<ProjectApprovalModel> getProjectApprovalList(Pagination page, @Param("query") QueryProjectApprovalModel query);

    /**
     * 获取项目列表
     *
     * @param companyId
     * @param year
     * @return
     */
    List<RsProjectBaseModel> getProjectList(@Param("companyId") Integer companyId, @Param("year") Integer year);

    /**
     * 获取项目列表
     *
     * @param companyId
     * @param year
     * @return
     */
    List<RsProjectListModel> getAllProject(@Param("companyId") Integer companyId, @Param("year") Integer year);

    /**
     * 获取父项目下的子项目列表
     *
     * @param parentId
     * @return
     */
    List<ProjectEntity> getChildsById(@Param("parentId") Integer parentId);

    /**
     * 获取查新报告项目列表
     *
     * @param companyId
     * @param year
     * @return
     */
    List<ReportProjectModel> getProjects(@Param("companyId") Integer companyId, @Param("year") Integer year);

    /**
     * 根据客户id查项目
     * @param companyId
     * @param year
     * @return
     */
    List<ReportProjectModel> getProByCustomerId(@Param("companyId") Integer companyId, @Param("year") Integer year);
    /**
     * 获取项目公司id
     *
     * @param id
     * @return
     */
    Integer getCompanyId(@Param("id") Integer id);

    /**
     * 获取成员，设备统计数
     *
     * @param list
     * @return
     */
    List<ProjectApprovalModel> getMemberEquipmentCnt(@Param("list") List<ProjectApprovalModel> list);

    /**
     * 获取项目资产清单列表
     *
     * @param page
     * @param query
     * @return
     */
    List<InitEquipmentModel> getProjectEquipmentList(@Param("page") Pagination page, @Param("query") QueryProjectEquipmentModel query);

    /**
     * 获取项目基本信息
     *
     * @param projectIds
     * @return
     */
    List<ProjectEntity> getInfos(@Param("projectIds") Collection<Integer> projectIds);

    /**
     * 获取项目上传的查新报告列表
     *
     * @param projectId
     * @return
     */
    List<SysDocumentEntity> getReportByProject(@Param("projectId") Integer projectId);

    /**
     * 获取项目详情
     *
     * @param projectId
     * @return
     */
    ProjectListModel getProjectInfo(@Param("projectId") Integer projectId);

    /**
     * 获取车间和工艺段
     *
     * @param list
     * @return
     */
    List<ProjectListModel> getWorkshopSections(@Param("list") List<RsProjectSummaryModel> list);

    /**
     * 获取项目下拉列表
     *
     * @param companyId
     * @param year
     * @return
     */
    List<RsProjectBaseModel> getProjectSelect(@Param("companyId") Integer companyId, @Param("year") Integer year);

    /**
     * 获取人员费用
     * @param model
     * @param monthLastDay
     * @return
     */
    List<RdMemberFundsModel> getRdMemberFunds(@Param("model") QueryRdFundsModel model,
                                              @Param("monthLastDay") Date monthLastDay);

    /**
     * 获取设备折旧
     * @param model
     * @param monthLastDay
     *  @return
     */
    List<RdEquipmentFundsModel> getRdEquipmentFunds(@Param("model") QueryRdFundsModel model,
                                                    @Param("monthLastDay") Date monthLastDay);

    /**
     * 获取其他费用
     * @param model
     * @param monthLastDay
     * @return
     */
    List<RdFinaFundsModel> getRdFunds(@Param("model") QueryRdFundsModel model,
                                      @Param("monthLastDay") Date monthLastDay);

    /**
     * 获取项目开始年
     * @param id
     * @return
     */
    Integer getBeginYear(@Param("id") Integer id);

    /**
     * 获取项目的name和rdTitle
     * @param id
     * @return
     */
    RsProjectBaseModel getProjectNameAndRd(@Param("id") Integer id);

    List<RsProjectBaseModel> getCheckProject();
    List<ReportCheckModel> getCheckReport();

    /**
     * 获取项目对应归集研发费
     * @param month
     * @param companyId
     * @return 研发费列表
     */
    List< RdFundsAllModel> getProjectFundsAll(@Param("month") Date month, @Param("companyId") Integer companyId);

    /**
     * 获取指定公司和月份的所有项目
     * @param startMonth
     * @param endMonth
     * @param companyId
     * @return 项目列表
     */
    List<RdFundsAllModel> getProjectListByMonth(@Param("startMonth") Date startMonth, @Param("endMonth") Date endMonth, @Param("companyId") Integer companyId);
}
