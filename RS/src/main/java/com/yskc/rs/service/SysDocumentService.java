package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.entity.SysDocumentEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.stage.StageModel;
import com.yskc.rs.models.sysDocument.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
public interface SysDocumentService {
    /**
     * 查询文档
     *
     * @param query
     * @param companyId
     * @return
     */
    PageModel<List<SysDocumentModel>> queryDocument(QuerySysDocumentModel query, Integer companyId);

    /**
     * @param id
     * @return
     */
    SysDocumentEntity getFileById(int id);

    /**
     * @param entity
     * @return
     */
    Boolean updateFile(SysDocumentEntity entity);

    /**
     * @param model
     * @return
     */
    boolean delFile(SysDocumentEntity model) throws OwnerException;

    /**
     * 上传文件
     * @param info
     * @param documentFile
     * @param file
     * @param needUpdate 需要更新上传文件信息,一般来说针对单个文件上传的界面
     * @return
     */
    SysDocumentModel uploadFile(UserInfo info, SysDocumentFile documentFile, MultipartFile file, Boolean needUpdate);
    /**
     * @param fileName
     * @param fileType
     * @param companyId
     * @param pageNo
     * @param pageSize
     * @param year
     * @param projectId
     * @return
     */
    PageModel<List<SysDocumentModel>> queryAppendixList(String fileName, Integer[] fileType, int companyId, int pageNo, int pageSize, String year, String projectId);

    /**
     * @param listType
     * @param companyId
     * @param projectId
     * @return
     */
    List<DocListModel> queryDocList(int listType, int companyId, Integer projectId);

    /**
     * @param listType
     * @param companyId
     * @return
     */
    List<DocListModel> queryAppendixDocList(int listType, int companyId, String patentNo, int year);

    /**
     * 获取文档类型
     * @return
     */
    List<FileTypeModel> getFileType();

    /**
     * 获取项目阶段
     * @param companyId
     * @param projectId
     * @return
     */
    List<StageModel> queryStage(Integer companyId, Integer projectId);

    /**
     * 获取项目查新报告
     * @param projectId
     * @return
     */
    List<SysDocumentEntity> getReportByProject(Integer projectId);

    /**
     * 删除成果文件
     * @param model
     * @return
     */
    boolean delResultFile(SysDocumentEntity model)throws OwnerException;

    /**
     * 获取建设文件数据
     * @param model
     * @param info
     * @return
     * @throws OwnerException
     */
    Map<String, Object> getBuildFileData(GenerateBuildFileModel model, UserInfo info)throws OwnerException;
}
