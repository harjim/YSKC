package com.yskc.ms.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.entity.rs.ProjectEntity;
import com.yskc.ms.entity.rs.RsCompanyEntity;
import com.yskc.ms.entity.rs.SysDocumentEntity;
import com.yskc.ms.entity.rs.models.ProjectListModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.project.DataReportModel;
import com.yskc.ms.models.projectApproval.ProjectApprovalModel;
import com.yskc.ms.models.projectApproval.QueryProjectApprovalModel;
import com.yskc.ms.models.projectApproval.QueryProjectEquipmentModel;
import com.yskc.ms.models.projectApproval.QueryProjectMemberModel;
import com.yskc.ms.models.rs.*;
import com.yskc.ms.models.rs.summary.BatchCleanFundsModel;
import com.yskc.ms.models.rs.summary.InitEquipmentModel;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface RsProjectService {
    List<ProjectListModel> queryProject(Integer companyId, Integer year, Integer msUserId) throws OwnerException;

    /**
     * 根据公司id及年份获取归集费用
     */
    List<DataReportModel> getDataReportFundsData(Integer year, Integer companyId) throws ParseException;



    /**
     * 获取研发费用明细
     *
     * @param companyId
     * @param year
     * @return
     */
    List<RdFundsModel> getRdFunds(Integer companyId, Integer year);

    /**
     * 设置rs项目定稿状态
     *
     * @param projectStatus
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    Boolean setStatus(ProjectStatusModel projectStatus, UserInfo userInfo) throws OwnerException;

    /**
     * 查询立项列表
     *
     * @param query
     * @return
     */
    PageModel<List<ProjectApprovalModel>> getProjectApprovalList(QueryProjectApprovalModel query);

    /**
     * 获取项目列表
     *
     * @param companyId
     * @param year
     * @return
     */
    List<RsProjectBaseModel> getProjectList(Integer companyId, Integer year);

    /**
     * 审核rs项目预算
     *
     * @param projectStatus
     * @param userInfo
     * @return
     */
    Boolean budgetFinal(ProjectStatusModel projectStatus, UserInfo userInfo);

    /**
     * 撤回rs项目预算审核
     *
     * @param projectStatus
     * @param userInfo
     * @return
     */
    Boolean budgetRecall(ProjectStatusModel projectStatus, UserInfo userInfo);

    /**
     * 获取项目列表
     *
     * @param companyId
     * @param year
     * @param userId
     * @param auditCount 0 不统计 1 过程文件统计 2 备查资料统计
     * @return
     */
    List<RsProjectListModel> getAllProject(Integer companyId, Integer year, Integer userId, Integer auditCount);

    /**
     * 获取查新报告项目列表
     *
     * @param companyId
     * @param year
     * @param userId
     * @return
     */
    List<ReportProjectModel> getReportProject(Integer companyId, Integer year, Integer userId);

    /**
     * 删除研发项目及其关联数据
     *
     * @param projectId
     * @param msUserId
     * @return
     * @throws OwnerException
     */
    Boolean cleanRdFunds(Integer projectId, Integer msUserId) throws OwnerException;

    /**
     * 清除项目成员归集费用
     *
     * @param cleanFunds
     * @param msUserId
     * @return
     * @throws OwnerException
     */
    Boolean cleanEmployeeFunds(BatchCleanFundsModel cleanFunds, Integer msUserId) throws OwnerException;

    /**
     * cleanEquipmentFunds
     *
     * @param cleanFunds
     * @param msUserId
     * @return
     * @throws OwnerException
     */
    Boolean cleanEquipmentFunds(BatchCleanFundsModel cleanFunds, Integer msUserId) throws OwnerException;

    /**
     * 获取项目成员列表
     *
     * @param query
     * @return
     */
    PageModel<List<InitMemberModel>> getProjectMemberList(QueryProjectMemberModel query);

    /**
     * 获取项目资产清单列表
     *
     * @param query
     * @return
     */
    PageModel<List<InitEquipmentModel>> getProjectEquipmentList(QueryProjectEquipmentModel query);

    /**
     * 获取认识项目公司
     *
     * @param projectId
     * @return
     */
    RsCompanyEntity getCompanyById(Integer projectId);

    /**
     * 获取项目成员
     *
     * @param query
     * @return
     */
    PageModel<List<InitMemberModel>> getInitmemberList(QueryProjectInitMemberModel query);

    /**
     * 获取项目归集数据
     *
     * @param projectId
     * @param year
     * @return
     */
    List<Map<String, Object>> getProjectSummary(Integer projectId, Integer year);

    /**
     * 获取项目上传查新报告列表
     *
     * @param projectId
     * @return
     */
    List<SysDocumentEntity> getReportByProject(Integer projectId);

    /**
     * 获取项目
     *
     * @param projectId
     * @return
     */
    ProjectEntity getProject(Integer projectId);

    /**
     * 查询项目详情
     *
     * @param projectId
     * @return
     */
    ProjectListModel getProjectInfo(Integer projectId);

    /**
     * 根据年获取项目列表
     *
     * @param companyId
     * @param year
     * @return
     */
    List<RsProjectListModel> getAllProject(Integer companyId, Integer year);
}
