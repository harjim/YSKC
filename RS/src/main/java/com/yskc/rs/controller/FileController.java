package com.yskc.rs.controller;

import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.FileEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.file.FileModel;
import com.yskc.rs.service.FileService;
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

/**
 * 年度研发项目及费用总结报告接口
 *
 * @author huronghua
 */
@Api(tags = "年度研发项目及费用总结报告接口", value = "年度研发项目及费用总结报告接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/file")
public class FileController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    FileService fileService;
    @Autowired
    private RsConfig rsConfig;

    /**
     *
     * @param projectId
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryFilePath")
    @ApiOperation(value = "查询文档路径", notes = "查询文档路径", response = String.class)
    public FileEntity queryFilePath(Integer projectId) throws OwnerException {
        int companyId = getUserInfo().getCompanyId();
        return fileService.queryFilePath(companyId,projectId);
    }

    /**
     *
     * @param entity
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "删除文件",mode = SystemLog.SAVE_DB)
    @PostMapping("/delFile")
    @ApiOperation(value = "删除文件", notes = "删除文件", response = String.class)
    public Boolean delFile(@RequestBody @Validated FileEntity entity) throws OwnerException {
        entity.setCompanyId(getUserInfo().getCompanyId());
        return fileService.delFile(entity);
    }

    /**
     *
     * @param file
     * @param documentFile
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "上传文件",mode = SystemLog.SAVE_DB)
    @PostMapping("/uploadFile")
    @ApiOperation(value = "上传文件", notes = "上传文件", response = String.class)
    public Boolean uploadFile(@RequestParam("file") MultipartFile file, FileModel documentFile) throws OwnerException {
        UserInfo info = getUserInfo();
        Date date = new Date();
        String dateStr = date.getTime()+"";
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                info.getCompanyId()) + "annualReport/";
        File folder = new File(tempPath);
        if (!folder.exists() && !folder.isDirectory()) {
            logger.error("文件夹路径不存在，创建路径:" + tempPath);
            folder.mkdirs();
        }
        File tempFile = new File(folder.getAbsolutePath() + "/" + dateStr + file.getOriginalFilename());
        try {
            file.transferTo(tempFile);
            FileEntity entity = new FileEntity();
            entity.setCreatorId(info.getUserSource() == 0 ? info.getId() : -1);
            entity.setLastUpdatorId(info.getUserSource() == 0 ? info.getId() : -1);
            entity.setCreateTime(date);
            entity.setLastUpdateTime(date);
            entity.setMsCreatorId(info.getUserSource() == 0 ? -1 : info.getId());
            entity.setMsLastUpdatorId(info.getUserSource() == 0 ? -1 : info.getId());
            entity.setProjectId(documentFile.getProjectId());
            entity.setCompanyId(info.getCompanyId());
            entity.setFileName(file.getOriginalFilename());
            entity.setFilePath(info.getCompanyId()+"/annualReport/"+ dateStr + file.getOriginalFilename());
            entity.setType(0);
            fileService.insertFile(entity);

        } catch (IOException e) {
           logger.error("uploadFile",e);
        }
        return true;
    }
}
