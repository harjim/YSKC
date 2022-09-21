package com.yskc.rs.controller;

import cn.hutool.core.io.IoUtil;
import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.SysDocumentEntity;
import com.yskc.rs.entity.SysLog;
import com.yskc.rs.enums.DocEnum;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.docList.QueryDocListModel;
import com.yskc.rs.models.stage.StageModel;
import com.yskc.rs.models.sysDocument.*;
import com.yskc.rs.service.DocListService;
import com.yskc.rs.service.SysDocumentService;
import com.yskc.rs.service.SysLogService;
import com.yskc.rs.utils.YsWordUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 文档管理接口
 *
 * @author huronghua
 */
@Api(tags = "文档管理类接口", value = "文档管理类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/sysDocument")
public class SysDocumentController extends BaseController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
        binder.registerCustomEditor(Date.class, editor);
    }

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    SysDocumentService sysDocumentService;
    @Autowired
    private RsConfig rsConfig;
    @Autowired
    private DocListService docListService;
    @Autowired
    private SysLogService sysLogService;

    /**
     * @param query
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryDocument")
    // @PermissionRequired(perms = "company:audit:search,company:settpay:search,project:monthlyReport:search")
    @ApiOperation(value = "查询文档", notes = "查询文档", response = String.class)
    public PageModel<List<SysDocumentModel>> queryDocument(QuerySysDocumentModel query)
            throws OwnerException {
        return sysDocumentService.queryDocument(query, getUserInfo().getCompanyId());
    }

    /**
     * @param listType
     * @param projectId
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryDocList")
    @ApiOperation(value = "查询附件清单(带项目)", notes = "查询附件清单(带项目)", response = String.class)
    public List<DocListModel> queryDocList(int listType, Integer projectId) throws OwnerException {
        int companyId = getUserInfo().getCompanyId();
        return sysDocumentService.queryDocList(listType, companyId, projectId);
    }

    /**
     * @param listType
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryAppendixDocList")
    @ApiOperation(value = "查询附件清单", notes = "查询附件清单", response = String.class)
    public List<DocListModel> queryAppendixDocList(int listType, String patentNo, int year) throws OwnerException {
        int companyId = getUserInfo().getCompanyId();
        return sysDocumentService.queryAppendixDocList(listType, companyId, patentNo, year);
    }

    /**
     * @param fileName
     * @param fileType
     * @param pageNo
     * @param pageSize
     * @param year
     * @param projectId
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryAppendixList")
    @ApiOperation(value = "查询附件清单", notes = "查询附件清单", response = String.class)
    public PageModel<List<SysDocumentModel>> queryAppendixList(String fileName, Integer[] fileType, int pageNo, int pageSize, String year, String projectId)
            throws OwnerException {
        int companyId = getUserInfo().getCompanyId();
        return sysDocumentService.queryAppendixList(fileName, fileType, companyId, pageNo, pageSize, year, projectId);
    }

    /**
     * @param id
     * @throws OwnerException
     * @throws IOException
     */
    @GetMapping("/downloadFile")
    @SystemLog(description = "下载文档", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "下载文档", notes = "下载文档", response = String.class)
    public void downloadFile(int id) throws OwnerException, IOException {
        UserInfo info = getUserInfo();
        SysDocumentEntity entity = sysDocumentService.getFileById(id);
        OutputStream os = outGeneralFile(entity.getFileName());
        entity.setDownloadTimes(entity.getDownloadTimes() + 1);
        entity.setLastUpdatorId(info.getUserSource() == 0 ? info.getId() : -1);
        entity.setMsLastUpdatorId(info.getUserSource() == 0 ? -1 : info.getId());
        entity.setLastUpdateTime(new Date());
        sysDocumentService.updateFile(entity);
        insertLog(info, entity, "下载文档", "/sysDocument/downloadFile");
        Path path = Paths.get(rsConfig.getUploadConfig().getDocPath(), entity.getFilePath());
        String fullPath = path.toString();
        InputStream fis = new BufferedInputStream(new FileInputStream(fullPath));
        IoUtil.copy(fis, os);
        os.flush();
        os.close();
        fis.close();
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "删除文档管理文件", mode = SystemLog.SAVE_DB)
    @PostMapping("/del")
    @ApiOperation(value = "删除文件", notes = "删除文件", response = boolean.class)
    public boolean delFile(@RequestBody @Validated SysDocumentEntity model) throws OwnerException {
        return sysDocumentService.delFile(model);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "删除成果文件", mode = SystemLog.SAVE_DB)
    @PostMapping("/delResultFile")
    @PermissionRequired(perms = "project:result:del")
    @ApiOperation(value = "删除成果文件", notes = "删除成果文件", response = boolean.class)
    public boolean delResultFile(@RequestBody @Validated SysDocumentEntity model) throws OwnerException {
        return sysDocumentService.delResultFile(model);
    }

    /**
     * @param file
     * @param documentFile
     * @return
     * @throws OwnerException
     */
    @PostMapping("/uploadFile")
    @SystemLog(description = "上传文件", mode = SystemLog.SAVE_DB)
    // @PermissionRequired(perms = "company:audit:upload,company:settpay:upload,project:monthlyReport:upload")
    @ApiOperation(value = "上传文件", notes = "上传文件", response = String.class)
    public Boolean uploadFile(@RequestParam("file") MultipartFile file, SysDocumentFile documentFile) throws OwnerException {
        UserInfo info = getUserInfo();
        sysDocumentService.uploadFile(info, documentFile, file, false);
        return true;
    }

    /**
     * @param file
     * @param documentFile
     * @return
     * @throws OwnerException
     */
    @PostMapping("/insertOrUpdateFile")
//    @PermissionRequired(perms = "company:audit:upload")
    @ApiOperation(value = "上传文件", notes = "上传文件", response = String.class)
    public Boolean insertOrUpdateFile(@RequestParam("file") MultipartFile file, SysDocumentFile documentFile) throws OwnerException {
        UserInfo info = getUserInfo();
        sysDocumentService.uploadFile(info, documentFile, file, true);
        return true;
    }

    /**
     * @param file
     * @param documentFile
     * @return
     * @throws OwnerException
     */
    @PostMapping("/uploadDocList")
    @ApiOperation(value = "上传附件列表", notes = "上传附件列表", response = String.class)
    public Boolean uploadDocList(@RequestParam("file") MultipartFile file, SysDocumentFile documentFile) throws OwnerException {
        UserInfo info = getUserInfo();
        sysDocumentService.uploadFile(info, documentFile, file, false);
        return true;
    }

    @GetMapping("/getFileType")
    @ApiOperation(value = "获取文件类型", notes = "获取文件类型", response = String.class)
    public List<FileTypeModel> getFileType() {
        return sysDocumentService.getFileType();
    }

    @GetMapping("/getStage")
    @ApiOperation(value = "查询阶段", notes = "查询阶段", response = String.class)
    public List<StageModel> getStage(Integer projectId) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return sysDocumentService.queryStage(userInfo.getCompanyId(), projectId);
    }

    @PostMapping("/uploadResult")
    @PermissionRequired(perms = "project:result:upload")
    @ApiOperation(value = "成果上传文件", notes = "成果上传文件", response = String.class)
    public Boolean uploadResult(@RequestParam("file") MultipartFile file, SysDocumentFile documentFile) throws OwnerException {
        UserInfo info = getUserInfo();
        sysDocumentService.uploadFile(info, documentFile, file, false);
        return true;
    }


    @SystemLog(description = "下载过程文件记录及附件", mode = SystemLog.SAVE_DB)
    @GetMapping("/downloadAttachment")
    @ApiOperation(notes = "下载过程文件记录及附件", value = "下载过程文件记录及附件")
    public void downloadAttachment(String path, String fileName) throws OwnerException, IOException {
        if (StringUtils.isEmpty(path)) {
            throw new OwnerException("文件不存在，下载失败。");
        }
        String filePath = rsConfig.getUploadConfig().getResourcePath() + path;
        download(filePath, fileName);
    }

    @SystemLog(description = "查询建设事项下对应年份的document附件", mode = SystemLog.SAVE_DB)
    @GetMapping("/getDocuments")
    @ApiOperation(notes = "查询建设事项下对应年份的document附件，引入建设事项配置", value = "查询建设事项下对应年份的document附件，引入建设事项配置")
    public PageModel<List<SysDocumentModel>> getDocuments(QueryDocListModel query) throws OwnerException {
        if (null == query.getDocListId()) {
            throw new OwnerException("建设事项不存在，查询失败。");
        }
        return docListService.getDocuments(query, getUserInfo().getCompanyId());
    }

    @GetMapping("/generateBuildFile")
    @ApiOperation(notes="下载默认机构建设事项制度文件",value = "下载默认机构建设事项制度文件")
    @PermissionRequired(perms = "rdorg:buildList:download")
    public void generateBuildFile(GenerateBuildFileModel model) throws Exception {
        UserInfo info = getUserInfo();
        Map<String, Object> data = sysDocumentService.getBuildFileData(model, info);
        OutputStream out = outGeneralFile(MessageFormat.format("{0}-{1,number,#}{2}.docx",
                info.getCompanyName(), model.getYear(), model.getDocName()));
        YsWordUtils.generalWordReport(data,
                rsConfig.getUploadConfig().getResourcePath() + Constant.BUILD_TEMPLATE_DIR + model.getDocName() + ".docx",
                doc -> {
                    if (null != doc) {
                        try {
                            doc.write(out);
                            out.flush();
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                }
        );
    }

    /**
     * 过程文档-问题记录及日志上传
     *
     * @return
     * @throws OwnerException
     */
//    @PostMapping("/uploadStageFile")
////    @PermissionRequired(perms = "company:audit:upload")
//    @ApiOperation(value = "上传附件列表", notes = "上传附件列表", response = String.class)
//    public SysDocumentModel uploadStageFile(@RequestParam("file") MultipartFile file) throws OwnerException {
//        String expertPath = rsConfig.getUploadConfig().getMsImportPath();
//        Map<String, String> result = new LinkedHashMap<>();
//        String fileName = System.currentTimeMillis() + file.getOriginalFilename();
//        String filePath = dir + fileName;
//        File tempFile = new File(expertPath + filePath);
//        if (!tempFile.getParentFile().exists()) {
//            tempFile.getParentFile().mkdirs();
//        }
//        try {
//            file.transferTo(tempFile);
//            result.put("filePath", filePath);
//            result.put("fileName", file.getOriginalFilename());
//        } catch (Exception ex) {
//            logger.error(ex.getMessage(), ex);
//            throw new OwnerException("上传失败");
//        }
//        SysDocumentModel model = new SysDocumentModel();
//        UserInfo info = getUserInfo();
//        return sysDocumentService.uploadFile(info,documentFile,file,false);
//        return null;
//    }
    private void insertLog(UserInfo info, SysDocumentEntity entity, String description, String logUrl) {
        int listType = 0;
        if (entity.getListId() != null && entity.getListId() != 0) {
            listType = docListService.getListType(entity.getListId());
        }
        String fileType = DocEnum.getCostEnum(entity.getFileType(), listType);
        SysLog log = new SysLog();
        log.setDescription(description);
        log.setLogType(0);
        log.setUserId(info.getUserId());
        log.setUserName(info.getUserName());
        log.setSource(info.getUserSource());
        log.setLogTime(new Date());
        log.setLogUrl(logUrl);
        log.setLogParams("文档类型:" + fileType + ",文档名:" + entity.getFileName());
        log.setCompanyId(info.getCompanyId());
        log.setCompanyName(info.getCompanyName());
        sysLogService.save(log);
    }
}
