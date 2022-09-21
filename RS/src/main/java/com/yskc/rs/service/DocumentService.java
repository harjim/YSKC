package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.entity.DocumentEntity;
import com.yskc.rs.entity.StageEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.document.*;
import com.yskc.rs.models.stage.StageModel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
/**
 * @author Administrator
 */
public interface DocumentService {
    /**
     *
     * @param companyId
     * @param projectId
     * @return
     */
    List<StageModel> queryStage(Integer companyId, Integer projectId);

    /**
     *
     * @param userInfo
     * @param model
     * @return
     */
    boolean saveDoc(UserInfo userInfo, DocumentModel model);

    /**
     *
     * @param userInfo
     * @param model
     * @return
     */
    boolean updateDocContent(UserInfo userInfo, DocumentEntity model);

    /**
     *
     * @param documentId
     * @return
     */
    DocumentEntity getModelById(Integer documentId);

    /**
     *
     * @return
     */
    List<TemplateModel> getTemplateList();

    /**
     *
     * @param projectId
     * @param companyId
     * @return
     */
    List<ProjectDocModel> docList(Integer projectId, Integer companyId);

    /**
     *
     * @param docModelList
     * @param userInfo
     * @return
     */
    boolean addDoc(List<ProjectDocModel> docModelList, UserInfo userInfo);

    /**
     *
     * @param ids
     * @param companyId
     * @return
     */
    boolean delDoc(Integer[] ids, Integer companyId);

    /**
     *
     * @param id
     * @param companyId
     * @return
     */
    String getContent(int id, Integer companyId);

    /**
     *
     * @param projectId
     * @param companyId
     * @return
     */
    List<StageEntity> getStageList(Integer projectId, Integer companyId);

    /**
     *
     * @param docModelList
     * @param companyId
     * @return
     */
    boolean updateDocModalList(List<ProjectDocModel> docModelList, Integer companyId);

    /**
     *
     * @param filePath
     * @return
     * @throws OwnerException
     */
    void preview(String rootPath,String filePath,OutputStream out) throws OwnerException, IOException;

    /**
     *
     * @param filePath
     * @throws OwnerException
     */
    void convertToHtml(String filePath) throws OwnerException;

    /**
     * 获取所有文档
     *
     * @param companyId
     * @param fileName
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageModel<List<AllDocModel>> getAllDoc(Integer companyId, String fileName, int pageNo, int pageSize);
}
