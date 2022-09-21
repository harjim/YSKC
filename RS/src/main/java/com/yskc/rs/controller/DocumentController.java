package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.DocumentEntity;
import com.yskc.rs.entity.StageEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.document.AllDocModel;
import com.yskc.rs.models.document.DocumentModel;
import com.yskc.rs.models.document.ProjectDocModel;
import com.yskc.rs.models.document.TemplateModel;
import com.yskc.rs.models.stage.StageModel;
import com.yskc.rs.service.DocumentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * 文档管理类接口
 *
 * @author huronghua
 */
@Api(tags = "文档管理类接口", value = "文档管理类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/document")
public class DocumentController extends BaseController {
    @Autowired
    DocumentService documentService;
    @Autowired
    RsConfig rsConfig;

    /**
     * @param projectId
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryStage")
    @PermissionRequired(perms = "project:report:set:stage")
    @ApiOperation(value = "查询阶段", notes = "查询阶段", response = String.class)
    public List<StageModel> queryStage(Integer projectId) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return documentService.queryStage(userInfo.getCompanyId(), projectId);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @PostMapping("/save")
    @SystemLog(description = "选择模板新建文档", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "选择模板新建文档", notes = "选择模板新建文档", response = boolean.class)
    public boolean saveDoc(@RequestBody @Validated DocumentModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return documentService.saveDoc(userInfo, model);
    }

    /**
     * @param projectId
     * @return
     * @throws OwnerException
     */
    @GetMapping("/stageList")
    public List<StageEntity> getStageList(Integer projectId) throws OwnerException {
        return documentService.getStageList(projectId, this.getUserInfo().getCompanyId());
    }

    /**
     * @return
     */
    @GetMapping("/templateList")
    public List<TemplateModel> templateList() {
        return documentService.getTemplateList();
    }

    /**
     * @param projectId
     * @return
     * @throws OwnerException
     */
    @GetMapping("/docList")
    public List<ProjectDocModel> docList(Integer projectId) throws OwnerException {
        return documentService.docList(projectId, this.getUserInfo().getCompanyId());
    }

    /**
     * @param docModelList
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "添加文档", mode = SystemLog.SAVE_DB)
    @PostMapping("/addDoc")
    public boolean addDoc(@RequestBody List<ProjectDocModel> docModelList) throws OwnerException {
        return documentService.addDoc(docModelList, this.getUserInfo());
    }

    /**
     * @param ids
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "删除文档", mode = SystemLog.SAVE_DB)
    @GetMapping("/delDoc")
    public boolean delDoc(Integer[] ids) throws OwnerException {
        return documentService.delDoc(ids, this.getUserInfo().getCompanyId());
    }

    /**
     * @param id
     * @return
     * @throws OwnerException
     */
    @GetMapping("/content")
    public String getContent(int id) throws OwnerException {
        return documentService.getContent(id, this.getUserInfo().getCompanyId());
    }

    /**
     * @param docModelList
     * @return
     * @throws OwnerException
     */
    @PostMapping("/updateDocModalList")
    public boolean updateDocModalList(@RequestBody List<ProjectDocModel> docModelList) throws OwnerException {
        return documentService.updateDocModalList(docModelList, this.getUserInfo().getCompanyId());
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "更新/保存文档", mode = SystemLog.SAVE_DB)
    @PostMapping("/updateDocContent")
    @ApiOperation(value = "编辑文档", notes = "编辑文档", response = boolean.class)
    public boolean updateDocContent(@RequestBody @Validated DocumentEntity model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return documentService.updateDocContent(userInfo, model);
    }

    /**
     * 预览
     *
     * @param filePath
     * @return
     * @throws OwnerException
     */
    @GetMapping("/preview")
//    @PermissionRequired(perms = "rdorg:buildList:preview")
    @ApiOperation(value = "预览", notes = "预览", response = String.class)
    public void preview(String filePath) throws IOException, OwnerException {
        documentService.preview(rsConfig.getUploadConfig().getDocPath(),filePath, outGeneralFile(filePath));
    }

    /**
     * 预览过程文档记录及附件
     *
     * @param filePath
     * @return
     * @throws OwnerException
     */
    @GetMapping("/appendixPreview")
    @ApiOperation(value = "预览过程文档记录及附件", notes = "预览过程文档记录及附件", response = String.class)
    public void appendixPreview(String filePath) throws IOException, OwnerException {
        documentService.preview(rsConfig.getUploadConfig().getResourcePath(),filePath, outGeneralFile(filePath));
    }

    /**
     * @param fileName
     * @param pageNo
     * @param pageSize
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getAllDoc")
    @ApiOperation(value = "获取所有文档", notes = "获取所有文档")
    public PageModel<List<AllDocModel>> getAllDoc(String fileName, int pageNo, int pageSize) throws OwnerException {
        return documentService.getAllDoc(getUserInfo().getCompanyId(), fileName, pageNo, pageSize);
    }

}
