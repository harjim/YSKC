package com.yskc.ms.service.rs;


import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.entity.rs.ProjectEntity;
import com.yskc.ms.entity.rs.RsCompanyEntity;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.rs.*;
import com.yskc.ms.models.rs.summary.BackupDataListModel;
import org.apache.ibatis.annotations.Param;

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
     * @return
     */
    DocFileDataModel getData(Integer pDocFileId,Boolean importFooterName);

    /**
     * 获取项目成员段（limit20）
     *
     * @param projectId
     * @return
     */
    String getMemberStr(@Param("projectId") Integer projectId, Integer pDocFileId);

    /**
     * 阶段文件导出word
     *
     * @param dataModel
     * @param userInfo
     */
    Map<String, Object> exportWord(DataModel dataModel, UserInfo userInfo, Boolean export) throws Exception;


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
     * 根据项目获取公司
     *
     * @param projectId
     * @return
     */
    RsCompanyEntity getCompanyInfo(Integer projectId);

    /**
     * 获取文档数据
     *
     * @param userInfo
     * @param projectId
     * @return
     */
    List<DocFileInfoModel> getDocInfo(UserInfo userInfo, Integer projectId, Integer year) throws OwnerException;

    /**
     * 获取查新报告列表
     *
     * @param model
     * @return
     */
    PageModel<List<NewReportsModel>> getNewReports(QueryReportsModel model);

    /**
     * 导出已选的备查资料
     * @param projectId
     * @param info
     * @param chooseIndexs
     * @param year
     * @param importFooterName
     * @param budgetDetail
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> exportChooseData(Integer projectId, UserInfo info, List<Integer> chooseIndexs,
                                               Integer year,Boolean importFooterName,Boolean budgetDetail) throws Exception;

    /**
     * 获取项目组编制情况
     *
     * @param projectId
     * @param userInfo
     * @return
     */
    Map<String, Object> getReportData(Integer projectId, UserInfo userInfo, Integer year);

    /**
     * 获取查新报告列表
     *
     * @param projectId
     * @return
     */
    List<NewReportsModel> getProjectReports(Integer projectId);

    /**
     * 获取备查资料列表
     *
     * @param projectId
     * @param year
     * @return
     */
    List<BackupDataListModel> getBackupData(Integer projectId, Integer year);

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
                                          Integer year, Boolean importFooterName, Boolean cover,RsCompanyEntity company,
                                          Boolean budgetDetail, Boolean utility) throws Exception;
}
