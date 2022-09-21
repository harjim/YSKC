package com.yskc.rs.controller;

import cn.hutool.core.io.FileUtil;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.tech.ProjectAppendixEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.tech.ProjectAppendixModel;
import com.yskc.rs.service.DocumentService;
import com.yskc.rs.service.ProjectAppendixService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * 附件列表类接口
 *
 * @author huronghua
 */
@Api(tags = "附件列表类接口", value = "附件列表类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/projectAppendix")
public class ProjectAppendixController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    ProjectAppendixService projectAppendixService;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private RsConfig rsConfig;

    /**
     *
     * @param projectId
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryAll")
    @ApiOperation(value = "查询附件", notes = "查询附件", response = String.class)
    public List<ProjectAppendixEntity> queryAll(Integer projectId) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return projectAppendixService.selectByProjectId(userInfo.getCompanyId(),projectId);
    }

    /**
     *
     * @param entity
     * @return
     * @throws OwnerException
     */
    @PostMapping("/del")
    @SystemLog(description = "删除附件",mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "删除附件", notes = "删除附件", response = String.class)
    public boolean del(@RequestBody @Validated ProjectAppendixEntity entity) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return projectAppendixService.del(entity.getType(),entity.getProjectId());
    }

    /**
     *
     * @param entity
     * @return
     * @throws OwnerException
     */
    @PostMapping("/delAuditReport")
    @SystemLog(description = "删除附件",mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "删除附件", notes = "删除附件", response = String.class)
    public boolean delAuditReport(@RequestBody @Validated ProjectAppendixEntity entity) throws OwnerException {
        return projectAppendixService.delAuditReport(entity.getId());
    }

    /**
     *
     * @param file
     * @param documentFile
     * @return
     * @throws OwnerException
     */
    @PostMapping("/uploadFile")
    @SystemLog(description = "上传附件",mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "上传附件", notes = "上传附件", response = String.class)
    public Integer uploadFile(@RequestParam("file") MultipartFile file, ProjectAppendixEntity documentFile) throws OwnerException {
        UserInfo info = getUserInfo();
        Date date = new Date();
        String dateStr = date.getTime()+"";
        documentFile.setFileName(file.getOriginalFilename());
        Integer fileId = 0;
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                info.getCompanyId()) + "projectAppendix/";
        File folder = new File(tempPath);
        if (!folder.exists() && !folder.isDirectory()) {
            logger.error("文件夹路径不存在，创建路径:" + tempPath);
            folder.mkdirs();
        }
        File tempFile = new File(folder.getAbsolutePath() + "/" + dateStr + documentFile.getFileName());
        try {
            FileUtil.writeBytes(file.getBytes(),tempFile);
            // file.transferTo(tempFile);
            documentFile.setCompanyId(info.getCompanyId());
            Integer userId = info.getId();
            documentFile.setCreateTime(date);
            documentFile.setCreatorId(userId);
            documentFile.setLastUpdateTime(date);
            documentFile.setLastUpdatorId(userId);
            documentFile.setFilePath(info.getCompanyId()+"/projectAppendix/"+dateStr + documentFile.getFileName());
            documentFile.setDownloadTimes(0);
            fileId = projectAppendixService.insertFile(documentFile);
            documentService.convertToHtml(tempFile.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileId;
    }

    /**
     *
     * @param fileName
     * @param projectId
     * @param pageNo
     * @param pageSize
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryDocument")
    @ApiOperation(value = "查询文档", notes = "查询文档", response = String.class)
    public PageModel<List<ProjectAppendixModel>> queryDocument(String fileName, Integer projectId, int pageNo, int pageSize)
            throws OwnerException {
        int companyId = getUserInfo().getCompanyId();
        return projectAppendixService.queryDocument(fileName, projectId, companyId, pageNo, pageSize);
    }

    /**
     *
     * @param file
     * @param documentFile
     * @return
     * @throws OwnerException
     */
    @PostMapping("/uploadReport")
    @ApiOperation(value = "上传文件", notes = "上传文件", response = String.class)
    public boolean uploadReport(@RequestParam("file") MultipartFile file, ProjectAppendixEntity documentFile) throws OwnerException {
        UserInfo info = getUserInfo();
        documentFile.setFileName(file.getOriginalFilename());
        Integer fileId = 0;
        Date date = new Date();
        String dateStr = date.getTime()+"";
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                info.getCompanyId()) + "auditReport/";
        File folder = new File(tempPath);
        if (!folder.exists() && !folder.isDirectory()) {
            logger.error("文件夹路径不存在，创建路径:" + tempPath);
            folder.mkdirs();
        }
        File tempFile = new File(folder.getAbsolutePath() + "/" + dateStr + documentFile.getFileName());
        try {
            FileUtil.writeBytes(file.getBytes(),tempFile);
            //file.transferTo(tempFile);
            documentFile.setCompanyId(info.getCompanyId());
            documentFile.setCreateTime(date);
            documentFile.setCreatorId(info.getUserSource() == 0 ? info.getId() : -1);
            documentFile.setLastUpdatorId(info.getUserSource() == 0 ? info.getId() : -1);
            documentFile.setLastUpdateTime(date);
            documentFile.setMsCreatorId(info.getUserSource() == 0 ? -1 : info.getId());
            documentFile.setMsLastUpdatorId(info.getUserSource() == 0 ? -1 : info.getId());
            documentFile.setFilePath(info.getCompanyId()+"/auditReport/"+dateStr + documentFile.getFileName());
            documentFile.setDownloadTimes(0);
            documentFile.setType(6);
            projectAppendixService.uploadReport(documentFile);
            documentService.convertToHtml(tempFile.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
