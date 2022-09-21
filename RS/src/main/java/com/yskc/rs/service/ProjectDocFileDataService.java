package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.docFile.*;
import com.yskc.rs.models.document.DataModel;
import com.yskc.rs.models.employee.EmployeeAutographModel;
import com.yskc.rs.models.workshop.BackupDataModel;

import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2020-03-26 10:08
 * @Description: 项目文档service层
 */
public interface ProjectDocFileDataService {
    /**
     * 获取项目文档数据
     *
     * @param pDocFileId
     * @param userInfo
     * @param importFooterName
     * @return
     */
    DocFileDataModel getData(Integer pDocFileId, UserInfo userInfo, Boolean importFooterName);

    /**
     * 保存项目文档
     *
     * @param userInfo
     * @param dataModel
     * @return
     */
    Boolean saveData(UserInfo userInfo, DocFileDataSaveModel dataModel) throws OwnerException;


    /**
     * 阶段文件导出word
     *
     * @param dataModel
     * @param userInfo
     */
    Map<String, Object> exportWord(DataModel dataModel, UserInfo userInfo, Boolean export) throws Exception;

    /**
     * 阶段文件导出word
     *
     * @param ids
     */
    Map<String, Object> getMeetingData(List<Integer> ids,Integer companyId,Integer year) throws OwnerException;

    /**
     * 获取项目成员段（limit20）
     *
     * @param projectId
     * @return
     */
    String getMemberStr(Integer projectId, Integer pDocFileId);

    /**
     * 导出所有过程文件
     *
     * @param model
     * @param userInfo
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> exportAllDoc(ExportDocFileModel model, UserInfo userInfo, Boolean export) throws Exception;

    /**
     * 设置过程文件页脚编制审核批准人员
     *
     * @param userInfo
     * @param model
     * @return
     */
    Boolean setDocFooter(UserInfo userInfo, DocFileFooterModel model);

    /**
     * 获取过程文件footer
     *
     * @param projectId
     * @return
     */
    Map<String, EmployeeAutographModel> getDocFooter(Integer projectId, Integer year);

    /**
     * 调整文档阶段
     *
     * @param userInfo
     * @param model
     * @return
     */
    Boolean changeDocStage(UserInfo userInfo, ChangeDocStageModel model) throws OwnerException;

    /**
     * 查看过程文件
     *
     * @param userInfo
     * @param projectId
     * @param docFileId
     * @return
     */
    DocFileInfoModel getDocInfo(UserInfo userInfo, Integer projectId, Integer docFileId, Integer year) throws OwnerException;

    /**
     * 获取设备折旧模板
     * @return
     */
    List<DocFileTemplateModel> getEquipmentTemplate();
    /**
     * 获取项目备查资料审核状态
     *
     * @param projectId
     * @return
     */
    Map<Integer, Integer> getAuditStatus(Integer projectId, Integer year);

    /**
     * 导出备查资料
     * @param projectId
     * @param userInfo
     * @return
     */
    //List<Map<String, Object>> exportBackupData(Integer projectId,UserInfo userInfo) throws Exception;

    /**
     * 导出已选的备查资料
     *
     * @param projectId
     * @param info
     * @param chooseIndexs
     * @param budgetDetail
     * @return
     */
    List<Map<String, Object>> exportChooseData(Integer projectId, UserInfo info, List<Integer> chooseIndexs,
                                               Integer year,Boolean importFooterName,Boolean budgetDetail) throws Exception;

    /**
     * 获取留存备查资料数据list
     * @param chooseFiles
     * @param project
     * @param year
     * @param importFooterName
     * @param cover
     * @param budgetDetail
     * @param utility
     * @return
     */
    List<Map<String, Object>> getDataList(List<Integer> chooseFiles, ProjectEntity project, UserInfo info,
                                          Integer year, Boolean importFooterName, Boolean cover,
                                          Boolean budgetDetail, Boolean utility) throws Exception;

    /**
     * 获取项目组编制情况
     *
     * @param projectId
     * @param userInfo
     * @return
     */
    Map<String, Object> getReportData(Integer projectId, UserInfo userInfo, Integer year);

    /**
     * 获取备查资料列表
     *
     * @param projectId
     * @param year
     * @return
     */
    List<BackupDataModel> getBackupData(Integer projectId, Integer year);

    /**
     * 切换过程文件模板
     *
     * @param model
     * @param userInfo
     * @return
     */
    Boolean setDocTemplate(DataModel model, UserInfo userInfo) throws OwnerException;

    /**
     * 获取项目情况分析
     *
     * @param projectId
     * @param companyId
     * @return
     * @throws OwnerException
     */
    Map<String, Object> getProjectAnalysis(Integer projectId, Integer companyId) throws OwnerException;
}
