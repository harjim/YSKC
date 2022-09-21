package com.yskc.ms.service.ms;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.PageResult;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.patentPlan.PatentAuditModel;
import com.yskc.ms.models.project.ProjectProgressModel;
import com.yskc.ms.models.project.ProjectStageModel;
import com.yskc.ms.models.project.QueryProjectProgressModel;
import com.yskc.ms.models.projectAudit.*;
import com.yskc.ms.models.rs.RdDeptTree;
import com.yskc.ms.models.rs.RsStageModel;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-01-08 16:12
 * @Description: 项目进度业务接口层
 */
public interface ProjectProgressService {
    /**
     * 获取进度列表
     *
     * @param query
     * @param userInfo
     * @param dataPerm
     * @return
     */
    PageResult getList(QueryProjectProgressModel query, UserInfo userInfo, DataPermModel dataPerm);


    List<ProjectStageModel> getProgressDetail(Integer year, Integer companyId);

    /**
     * 获取研发部门
     *
     * @param year
     * @param companyId
     * @return
     */
    List<RdDeptTree> getRdDept(Integer companyId, Integer year);

    void export(QueryProjectProgressModel query, UserInfo userInfo, DataPermModel dataPerm, OutputStream out, String path) throws OwnerException;

    /**
     * 获取归集审核数据
     *
     * @param query
     * @param userInfo
     * @param dataPerm
     * @return
     */
    PageModel<List<ProjectProgressModel>> getData(QueryProjectProgressModel query, UserInfo userInfo, DataPermModel dataPerm);

    /**
     * 项目审核
     *
     * @param model
     * @param userInfo
     * @return
     */
    Boolean projectAudit(ProjectAuditModel model, UserInfo userInfo) throws OwnerException;

    /**
     * 项目审核
     *
     * @param model
     * @param userInfo
     * @return
     */
    Boolean patentAudit(ProjectAuditModel model, UserInfo userInfo)throws OwnerException;

    /**
     * 获取审核记录
     *
     * @param companyId
     * @param year
     * @param moduleId
     * @param docFileId
     * @param userId
     * @return
     */
    Map<String, Object> getAuditLog(Integer companyId, Integer year, Integer moduleId, Integer docFileId,
                                    Integer userId, Integer rsProjectId, Integer patentPlanId,
                                    Integer proposalId, Integer achievementId,Integer rdFeeId);


    /**
     * 获取审核信息
     *
     * @param projectId
     * @param userId
     * @return
     */
    List<AuditStatusModel> getAuditInfo(Integer projectId, Integer userId);

    /**
     * 获取研发花名册列表
     *
     * @param query
     * @return
     */
    PageResult getEmployeeList(QueryAuditDataModel query);

    /**
     * 获取研发设备列表
     *
     * @param query
     * @return
     */
    PageResult getEquipmentList(QueryAuditDataModel query);

    /**
     * 获取专利列表
     *
     * @param query
     * @return
     */
    PageModel<List<PatentAuditModel>> getPatentList(QueryAuditDataModel query, Integer userId, Integer type);

    /**
     * 获取项目立项报告/查新报告
     *
     * @param year
     * @param companyId
     * @param userId
     * @param docFileId
     * @return
     */
    List<ReportListModel> getProjectReport(Integer year, Integer companyId, Integer userId, Integer docFileId);


    /**
     * 获取统计数据
     *
     * @param year
     * @param customerId
     * @return
     */
    ProjectSummaryDataModel getCountData(Integer year, Integer customerId, Integer companyId);

    /**
     * 激活流程
     *
     * @param model
     * @param userInfo
     * @return
     */
    Boolean activateFlow(ProjectAuditModel model, UserInfo userInfo) throws OwnerException;

    /**
     * 批量激活流程
     *
     * @param batchAudit
     * @param userInfo
     * @return
     */
    Boolean activateFlows(BatchAuditModel batchAudit, UserInfo userInfo) throws OwnerException;

    /**
     * 获取阶段文档
     *
     * @param companyId
     * @param projectId
     * @return
     */
    List<RsStageModel> queryStage(Integer companyId, Integer projectId, Integer userId);

    /**
     * 获取阶段文档审核
     *
     * @param companyId
     * @param projectId
     * @return
     */
    List<RsStageModel> getStageAudit(Integer companyId, Integer projectId, Integer userId);

    /**
     * 审核
     *
     * @param batchAudit
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    Boolean projectAudits(BatchAuditModel batchAudit, UserInfo userInfo) throws OwnerException;

    /**
     * 批量审核查新流程
     * @param model
     * @param info
     * @return
     * @throws OwnerException
     */
    Boolean checkAudits(CheckAuditModel model,UserInfo info) throws OwnerException;

    /**
     * 取消专利审核
     *
     * @param batchAudit
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    Boolean cancelPatent(BatchAuditModel batchAudit, UserInfo userInfo) throws OwnerException;

    /**
     * 批量审核专利
     * @param batchAudit
     * @param userInfo
     * @param id
     * @return
     * @throws OwnerException
     */
    Boolean patentAudits(BatchAuditModel batchAudit, UserInfo userInfo, Integer id) throws OwnerException;

    /**
     * 获取当前用户项目审核权限
     *
     * @param companyId
     * @param year
     * @param userInfo
     * @return
     */
    Map<Integer, Boolean> getProjectAudit(Integer companyId, Integer year, UserInfo userInfo);

    /**
     * 获取当前用户过程文件审核情况
     *
     * @param companyId
     * @param year
     * @param userId
     * @return
     */
    Map<Integer, Integer> getDocAudit(Integer companyId, Integer year, Integer userId);

    /**
     * 获取当前可审核查新报告
     *
     * @param companyId
     * @param userId
     * @return
     */
    Integer getReportAudit(Integer companyId, Integer year, Integer userId);

    /**
     * 根据年和公司获取生成研发组织架构图片路径
     *
     * @param companyId
     * @param year
     * @return
     */
    List<Object> getOrgPath(Integer companyId, Integer year) throws OwnerException;

    /**
     * 统计当前用户可审核的专利交底书
     *
     * @param companyId
     * @param year
     * @param id
     * @return
     */
    Map<Integer, Integer> getPatentAudit(Integer companyId, Integer year, Integer id);

    /**
     * 统计当前用户可审核的项目
     *
     * @param year
     * @param companyId
     * @param userId
     * @return
     */
    Integer getProjectAuditInfo(Integer year, Integer companyId, Integer userId);

    /**
     * 获取当前用户提案审核情况
     *
     * @param companyId
     * @param year
     * @param userId
     * @return
     */
    Map<Integer, Integer> getProposalAudit(Integer companyId, Integer year, Integer userId);


    /**
     * 获取当前用户成果文件审核情况
     *
     * @param companyId
     * @param year
     * @param userId
     * @return
     */
    Map<Integer, Integer> getResultAudit(Integer companyId, Integer year, Integer userId);

    /**
     * 导出研发考勤绑定情况
     *
     * @param out
     * @param query
     */
    void exportEmployeeBindInfo(OutputStream out, QueryAuditDataModel query);
}
