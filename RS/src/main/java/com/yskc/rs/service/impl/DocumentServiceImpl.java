package com.yskc.rs.service.impl;

import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.config.Constant;
import com.yskc.rs.dao.DocumentDao;
import com.yskc.rs.dao.StageDao;
import com.yskc.rs.entity.DocumentEntity;
import com.yskc.rs.entity.StageEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.document.AllDocModel;
import com.yskc.rs.models.document.DocumentModel;
import com.yskc.rs.models.document.ProjectDocModel;
import com.yskc.rs.models.document.TemplateModel;
import com.yskc.rs.models.stage.StageModel;
import com.yskc.rs.service.DocumentService;
import com.yskc.rs.utils.YsExcelUtils;
import com.yskc.rs.utils.YsPptUtils;
import com.yskc.rs.utils.YsWordUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DocumentServiceImpl extends ServiceImpl<DocumentDao, DocumentEntity> implements DocumentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentServiceImpl.class);

    @Autowired
    StageDao stageDao;

    @Autowired
    private DocumentDao documentDao;


    /**
     * @param companyId
     * @param projectId
     * @return
     */
    @Override
    public List<StageModel> queryStage(Integer companyId, Integer projectId) {

        List<StageModel> queryStage = stageDao.queryStage(companyId, projectId);
        for (int i = 0; i < queryStage.size(); i++) {
            List<DocumentModel> docList = documentDao.getDocData(queryStage.get(i).getStageId(), projectId, companyId);
            queryStage.get(i).setDocData(docList);
        }

        return queryStage;
    }

    /**
     * @param userInfo
     * @param model
     * @return
     */
    @Override
    public boolean saveDoc(UserInfo userInfo, DocumentModel model) {
        List<DocumentEntity> docData = model.getDocData();
        Integer companyId = userInfo.getCompanyId();
        Date date = new Date();
        for (int i = 0; i < docData.size(); i++) {
            docData.get(i).setCompanyId(companyId);
            docData.get(i).setContent("");
            docData.get(i).setProcessId(-1);
            docData.get(i).setCreateTime(date);
            docData.get(i).setCreatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
            docData.get(i).setLastUpdatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
            docData.get(i).setMsCreatorId(userInfo.getUserSource() == 1 ? userInfo.getId() : -1);
            docData.get(i).setMsLastUpdatorId(userInfo.getUserSource() == 1 ? userInfo.getId() : -1);
            docData.get(i).setLastUpdateTime(date);
        }

        return insertOrUpdateAllColumnBatch(docData);
    }

    /**
     * @param userInfo
     * @param model
     * @return
     */
    @Override
    public boolean updateDocContent(UserInfo userInfo, DocumentEntity model) {
        model.setLastUpdatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
        model.setMsLastUpdatorId(userInfo.getUserSource() == 1 ? userInfo.getId() : -1);
        model.setLastUpdateTime(new Date());
        return documentDao.updateById(model) > 0;
    }

    /**
     * @param documentId
     * @return
     */
    @Override
    public DocumentEntity getModelById(Integer documentId) {
        return documentDao.selectById(documentId);
    }

    /**
     * 获取当前可用的过程文件模板
     *
     * @return
     */
    @Override
    public List<TemplateModel> getTemplateList() {
        return documentDao.getTemplateList();
    }

    /**
     * 获取当前项目的所有过程文件
     *
     * @param projectId
     * @param companyId
     * @return
     */
    @Override
    public List<ProjectDocModel> docList(Integer projectId, Integer companyId) {
        final List<ProjectDocModel> projectDocModels = documentDao.docList(projectId, companyId);
        return projectDocModels;
    }

    /**
     * @param docModelList
     * @param userInfo
     * @return
     */
    @Override
    public boolean addDoc(List<ProjectDocModel> docModelList, UserInfo userInfo) {
        if (docModelList.size() > 0) {
            List<ProjectDocModel> dList = new ArrayList<>();
            for (ProjectDocModel docModel :
                    docModelList) {
                for (int i = 0; i < docModel.getQuantity(); i++) {
                    dList.add(docModel);
                }
            }
            documentDao.addDocList(dList, userInfo.getCompanyId(), userInfo.getId());
        }
        return true;
    }

    /**
     * @param ids
     * @param companyId
     * @return
     */
    @Override
    public boolean delDoc(Integer[] ids, Integer companyId) {
        if (ids.length > 0) {
            documentDao.delDoc(ids, companyId);
        }
        return true;
    }

    /**
     * @param id
     * @param companyId
     * @return
     */
    @Override
    public String getContent(int id, Integer companyId) {
        DocumentEntity documentEntity = documentDao.selectById(id);
        if (documentEntity != null && documentEntity.getCompanyId().equals(companyId)) {
            return documentEntity.getContent();
        }
        return "{}";
    }

    /**
     * @param projectId
     * @param companyId
     * @return
     */
    @Override
    public List<StageEntity> getStageList(Integer projectId, Integer companyId) {
        return documentDao.getStageList(projectId, companyId);
    }

    /**
     * @param docModelList
     * @param companyId
     * @return
     */
    @Override
    public boolean updateDocModalList(List<ProjectDocModel> docModelList, Integer companyId) {
        for (ProjectDocModel docModel :
                docModelList) {
            DocumentEntity documentEntity = new DocumentEntity();
            documentEntity.setId(docModel.getId());
            documentEntity.setDocNumber(docModel.getDocNumber());
            documentEntity.setDocName(docModel.getDocName());
            documentEntity.setStageId(docModel.getStageId());
            documentDao.updateById(documentEntity);
        }
        return true;
    }

    /**
     * @param filePath
     * @return
     * @throws OwnerException
     */
    @Override
    public void preview(String rootPath, String filePath, OutputStream out) throws OwnerException, IOException {
        if (StringUtils.isEmpty(filePath)) {
            throw new OwnerException("无数据");
        }
        Path path = Paths.get(rootPath, filePath);
        String fullPath = path.toString();
        File file = new File(fullPath);
        if (!file.exists()) {
            throw new OwnerException("不存在文件，请联系管理员");
        }
        String name = path.getFileName().toString().toLowerCase();
        if (name.endsWith(Constant.EXCEL_DOC)) {
            IoUtil.write(out, false, YsWordUtils.docToHtml(fullPath).getBytes());
        } else if (name.endsWith(Constant.EXCEL_DOCX)) {
            IoUtil.write(out, false, YsWordUtils.docxToHtml(fullPath).getBytes());
        } else if (name.endsWith(Constant.EXCEL_XLS) || name.endsWith(Constant.EXCEL_XLSX)) {
            IoUtil.write(out, false, YsExcelUtils.excelToHtml(fullPath, name).getBytes());
        } else if (name.endsWith(Constant.EXCEL_PPT) || name.endsWith(Constant.EXCEL_PPTX)) {
            IoUtil.write(out, false, YsPptUtils.toHtml(fullPath).getBytes());
        } else {
            InputStream in = new FileInputStream(file);
            IoUtil.copy(in, out);
            in.close();
        }
//        else if (name.endsWith(Constant.EXCEL_PDF)) {
//            InputStream in = new FileInputStream(file);
//            IoUtil.copy(in, out);
//            in.close();
//        }
        out.flush();
        out.close();
    }

    /**
     * 转换文档
     *
     * @param filePath
     * @throws OwnerException
     */
    @Override
    public void convertToHtml(String filePath) throws OwnerException {
        Path path = Paths.get(filePath);
        String fileName = path.getFileName().toString().toLowerCase();
        if (fileName.endsWith(Constant.EXCEL_DOC)) {
            YsWordUtils.docToHtml(filePath);
        } else if (fileName.endsWith(Constant.EXCEL_DOCX)) {
            YsWordUtils.docxToHtml(filePath);
        } else if (fileName.endsWith(Constant.EXCEL_XLS) || fileName.endsWith(Constant.EXCEL_XLSX)) {
            YsExcelUtils.excelToHtml(filePath, fileName);
        } else if (fileName.endsWith(Constant.EXCEL_PPT) || fileName.endsWith(Constant.EXCEL_PPTX)) {
            YsPptUtils.toHtml(filePath);
        } else if (fileName.endsWith(Constant.EXCEL_PDF)) {
            LOGGER.debug(filePath);
        } else {
            throw new OwnerException("不支持文件类型，请与管理员联系");
        }
    }

    /**
     * @param companyId
     * @param fileName
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageModel<List<AllDocModel>> getAllDoc(Integer companyId, String fileName, int pageNo, int pageSize) {
        Pagination page = new Pagination(pageNo, pageSize);
        // return PageUtils.build(page, documentDao.getAllDoc(page, companyId, fileName));
        return null;
    }

}
