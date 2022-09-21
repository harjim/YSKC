package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.rs.entity.project.DocFileMeetingEntity;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.cdocument.CDocumentModel;
import com.yskc.rs.models.docFile.DocFileFooterModel;
import com.yskc.rs.models.docFile.MeetingCountModel;
import com.yskc.rs.models.docFile.MeetingFromModel;
import com.yskc.rs.models.employee.EmployeeSelectModel;
import com.yskc.rs.models.excel.ProjectExcel;
import com.yskc.rs.models.hightech.QueryAssistModel;
import com.yskc.rs.models.project.*;

import java.io.OutputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-15 10:34
 * @Description: 项目业务接口层
 */
public interface ProjectService {
    /**
     *@Description: 获取项目列表
     *@Param: [companyId, ryear, pname, deptId, pageNo, pageSize]
     *@return: com.yskc.rs.models.PageModel<java.util.List < com.yskc.rs.models.project.ProjectListModel>>
     *@Author: zhangdingfu
     *@date: 2019-07-16
     */
//    PageModel<List<ProjectListModel>> getList(Integer companyId, Integer ryear, String pname, Integer deptId, int pageNo, int pageSize);

    /**
     * 更新
     *
     * @param model
     * @param userInfo
     * @return
     */
    Boolean update(ProjectModel model, UserInfo userInfo) throws OwnerException;


    /**
     * 获取人员列表
     *
     * @param companyId
     * @param ename
     * @return
     */
    List<EmployeeSelectModel> getEmployeeList(Integer companyId, Integer year, String ename);

    /**
     * 导出RD列表
     *
     * @param userInfo
     * @param year
     * @return
     */
    void exportProject(UserInfo userInfo, Integer year, OutputStream out, String path) throws OwnerException;

    /**
     * @param info
     * @param typeModel
     * @return
     */
    boolean addBatch(UserInfo info, List<TypeModel> typeModel);

    /**
     * @param projectId
     * @return
     */
    List<TypeModel> querySummaryByProjectId(Integer projectId);

    /**
     * 删除项目
     *
     * @param model
     * @param info
     * @return
     * @throws OwnerException
     */
    boolean deleteProject(UserInfo info, DelProjectModel model) throws OwnerException;


    /**
     * 检查重复RD
     *
     * @param companyId
     * @param rdIndex
     * @param year
     * @param projectId
     * @return
     * @throws OwnerException
     */
    boolean checkRD(Integer companyId, Integer rdIndex, Integer year, Integer projectId) throws OwnerException;

    /**
     * 查询最大RD
     *
     * @param companyId
     * @param year
     * @return
     */
    Integer selectMaxRd(Integer companyId, Integer year);

    /**
     * 检查重复项目名
     *
     * @param pname
     * @param projectId
     * @return
     */
    boolean checkPname(String pname, Integer projectId);

    //////////////////////////////////////////////////Report迁移代码//////////////////////////////////////////////////

    /**
     * 获取公司申报的项目列表
     *
     * @param companyId
     * @param year
     * @param formula
     * @return
     * @throws OwnerException
     */
    List<ProjectListModel> getProjectsByYear(Integer companyId, Integer year, Integer formula);

    /**
     * 查询项目列表
     *
     * @param companyId
     * @param year
     * @param pname
     * @return
     */
    List<ProjectListModel> queryProject(Integer companyId, Integer year, String pname, String rd);

    /**
     * 添加项目
     *
     * @param model
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    Boolean addProject(ProjectModel model, UserInfo userInfo) throws OwnerException;

    /**
     * 导入RD列表
     *
     * @param info
     * @param projectExcels
     * @param year
     * @return
     * @throws OwnerException
     */
    String importProject(UserInfo info, List<ProjectExcel> projectExcels, Integer year) throws OwnerException;

    /**
     * 归集总表
     *
     * @param companyId
     * @param year
     * @param child
     * @return
     * @throws OwnerException
     */
    Map<String, Object> getDataReportFunds(Integer companyId, Integer year, Boolean child) throws OwnerException;



    /**
     * 导出辅助帐
     *
     * @param year
     * @param old
     * @param info
     * @param out
     * @throws OwnerException
     */
    void exportGeneralLedger(int year, Boolean old,UserInfo info, OutputStream out) throws OwnerException;

    /**
     * 研发人员，项目人员下拉列表
     * @param userInfo
     * @param model
     * @return
     * @throws OwnerException
     */
    List<EmployeeSelectModel> getEmployeeSelect(UserInfo userInfo, QueryProjectEmployeeModel model) throws OwnerException;


    /**
     * 查询归集明细
     *
     * @param companyId
     * @param year
     * @param child
     * @param type      0:所有 100：研发工资
     * @return
     * @throws ParseException
     */
    List<Map<String, Object>> querySubsidiaryLedger(Integer companyId, Integer year, Boolean child, Integer type) throws ParseException;


    /**
     * 获取花名册下拉
     *
     * @param companyId
     * @param ename
     * @return
     */
    List<EmployeeSelectModel> getBaseEmployeeSelect(Integer companyId, String ename);

    /**
     * 获取花名册下拉(可自定义)
     *
     * @param companyId
     * @param ename
     * @return
     */
    List<EmployeeSelectModel> getEmployeeNames(Integer companyId, String ename);

    /**
     * 获取项目列表(含子项目)
     *
     * @param companyId
     * @param model
     * @return
     */
    List<ProjectListModel> getProjectList(Integer companyId, QueryProjectListModel model);

    /**
     * 根据年获取项目列表
     *
     * @param companyId
     * @param year
     * @return
     */
    List<ProjectListModel> getAllProject(Integer companyId, Integer year);

    /**
     * 合并项目
     *
     * @param model
     * @param userInfo
     * @return
     */
    Boolean mergeProject(UserInfo userInfo, ParentProjectModel model) throws OwnerException;

    /**
     * 合并项目检查项目rd是否重复
     *
     * @param userInfo
     * @param rdIndex
     * @param projectIds
     * @return
     */
    Boolean checkParentNo(UserInfo userInfo, Integer rdIndex, List<Integer> projectIds) throws OwnerException;

    /**
     * 获取父项目列表
     *
     * @param companyId
     * @return
     */
    List<ProjectSelectModel> queryParentList(Integer companyId, List<Integer> projectIds, Integer currentYear) throws OwnerException;

    /**
     * 移出项目
     *
     * @param parentId
     * @param userInfo
     * @param childId
     * @return
     */
    Boolean removeProject(Integer parentId, Integer rdIndex, UserInfo userInfo, Integer childId) throws OwnerException;

    /**
     * 根据标识获取项目的下拉列表
     *
     * @param companyId
     * @param year
     * @param sign
     * @return
     */
    List<ProjectSelectModel> getSelectList(Integer companyId, Integer year, Integer sign);
    /**
     * 根据标识获取项目的下拉列表(签名)
     *
     * @param companyId
     * @param year
     * @return
     */
    List<DocFileFooterModel> getSignatureList(Integer companyId, Integer year);

    /**
     * 设置归集的研发费用
     *
     * @param userInfo
     * @param model
     * @return
     */
    Boolean setRdAmount(UserInfo userInfo, SetRdAmountModel model) throws OwnerException;


    /**
     * 按开始年查询原子项目下拉列表
     *
     * @param companyId
     * @param year
     * @return
     */
    List<ProjectInfoModel> getYearSelectList(Integer companyId, Integer year);

    /**
     * 获取项目
     *
     * @param projectId
     * @return
     */
    ProjectEntity getProject(Integer projectId);

    /**
     * 设置项目预算
     *
     * @param userInfo
     * @param models
     * @return
     */
    Boolean setBudget(UserInfo userInfo, List<BudgetInfoModel> models) throws OwnerException;

    /**
     * 获取辅助帐总表数据
     *
     * @param year
     * @param userInfo
     * @return
     */
    Map<String, Object> getGeneralLedgerData(Integer year, UserInfo userInfo);

    /**
     * 高新进度获取项目辅助账
     * @param model
     * @return
     */
    Map<String, Object> getAssistData(QueryAssistModel model);

    /**
     * 获取风险核算报告
     *
     * @param year
     * @param companyId
     * @return
     */
    Map<String, Object> getRdAccountRiskData(Integer year, Integer companyId);

    /**
     * 获取核算标准方法
     *
     * @param year
     * @param companyId
     * @return
     */
    Map<String, Object> getRdAccountData(Integer year, Integer companyId);

    /**
     * 编辑核算标准方法
     * @param model
     * @param info
     * @return
     */
    Boolean saveRdAccountData(CDocumentModel model, UserInfo info);

    Map<String,String> getMaster(Integer projectId,Integer year);

    /**
     * 获取年度会议纪要统计数据
     * @param year
     * @param companyId
     * @param month
     * @return
     */
    List<MeetingCountModel> getAnnualData(Integer year, Integer companyId, Date month) throws ParseException;

    /**
     * 按月获取公司会议纪要数据
     * @param month
     * @param companyId
     * @return
     */
    List<MeetingFromModel> getMeetingFromList(Date month, Integer companyId);

    /**
     * 删除会议纪要附件
     * @param ids
     * @return
     */
    Boolean delMeetingFile(List<Integer> ids);

    /**
     * 新增会议记录附件
     * @param entity
     */
    void insertFile(DocFileMeetingEntity entity);

    /**
     * 预览会议纪要附件
     * @param docPath
     * @param filePath
     * @param outGeneralFile
     */
    void preview(String docPath, String filePath, OutputStream outGeneralFile) throws Exception;

    /**
     * 获取项目简单数据下拉框
     * @param companyId
     * @param year
     * @param sign
     * @return
     */
    List<ProjectSelectModel> getSimpleList(Integer companyId, Integer year, Integer sign);

    /**
     * 保存预算表数据
     * @param userInfo
     * @param models
     * @return
     */
    Boolean setBudgetTable(UserInfo userInfo, List<SaveBudgetModel> models) throws OwnerException;

    List< ChangeRecordModel> getProjectChangeRecord(Integer projectId, UserInfo userInfo);

    Integer saveProjectChangeRecord(ChangeRecordModel changeRecordModel, UserInfo userInfo) throws OwnerException;

    String delProjectChangeRecord(ChangeRecordModel changeRecordModel, UserInfo userInfo) throws OwnerException;
}
