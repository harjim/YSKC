package com.yskc.rs.controller;

import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.AnnualReportEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.sysDocument.SysDocumentFile;
import com.yskc.rs.models.sysDocument.SysDocumentModel;
import com.yskc.rs.service.AnnualReportService;
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
 * 上传报告接口
 *
 * @author huronghua
 */
@Api(tags = "上传报告接口", value = "上传报告接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/annualReport")
public class AnnualReportController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AnnualReportService annualReportService;
    @Autowired
    private RsConfig rsConfig;

    /**
     * 查询文档
     * @param fileName
     * @param fileType
     * @param year
     * @param pageNo
     * @param pageSize
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryDocument")
    @ApiOperation(value = "查询文档", notes = "查询文档", response = String.class)
    public PageModel<List<SysDocumentModel>> queryDocument(String fileName, int fileType, String year, int pageNo, int pageSize)
            throws OwnerException {
        int companyId = getUserInfo().getCompanyId();
        return annualReportService.queryDocument(fileName, fileType, year, companyId, pageNo, pageSize);
    }

    /**
     * 上传文件
     * @param file
     * @param documentFile
     * @return
     * @throws OwnerException
     */
    @PostMapping("/uploadFile")
    @ApiOperation(value = "上传文件", notes = "上传文件", response = String.class)
    @SystemLog(description = "上传文件", mode = SystemLog.SAVE_DB)
    public Boolean uploadFile(@RequestParam("file") MultipartFile file, SysDocumentFile documentFile) throws OwnerException {
        UserInfo info = getUserInfo();
        Date date = new Date();
        String dateStr = date.getTime() + "";
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                info.getCompanyId()) + "document/" + documentFile.getTypeName() + "/";
        File folder = new File(tempPath);
        if (!folder.exists() && !folder.isDirectory()) {
            logger.error("文件夹路径不存在，创建路径:" + tempPath);
            folder.mkdirs();
        }
        File tempFile = new File(folder.getAbsolutePath() + "/" + dateStr + documentFile.getFileName());
        try {
            file.transferTo(tempFile);
            AnnualReportEntity entity = new AnnualReportEntity();
            entity.setCreateTime(date);
            entity.setCreatorId(info.getUserSource() == 0 ? info.getId() : -1);
            entity.setLastUpdatorId(info.getUserSource() == 0 ? info.getId() : -1);
            entity.setLastUpdateTime(date);
            entity.setMsCreatorId(info.getUserSource() == 0 ? -1 : info.getId());
            entity.setMsLastUpdatorId(info.getUserSource() == 0 ? -1 : info.getId());
            entity.setCompanyId(info.getCompanyId());
            entity.setYear(documentFile.getYear());
            entity.setType(documentFile.getFileType());
            entity.setReportName(documentFile.getFileName());
            entity.setFilePath(info.getCompanyId() + "/document/"+ documentFile.getTypeName() + "/" + dateStr + documentFile.getFileName());
            annualReportService.insertFile(entity);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 上传月度报告
     * @param file
     * @param documentFile
     * @return
     * @throws OwnerException
     */
    @PostMapping("/uploadMonthlyReport")
    @SystemLog(description = "上传月度报告", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "上传月度报告", notes = "上传月度报告", response = String.class)
    public Boolean uploadMonthlyReport(@RequestParam("file") MultipartFile file, SysDocumentFile documentFile) throws OwnerException {
        UserInfo info = getUserInfo();
        Date date = new Date();
        String dateStr = date.getTime() + "";
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                info.getCompanyId()) + "document/" + documentFile.getTypeName() + "/";
        File folder = new File(tempPath);
        if (!folder.exists() && !folder.isDirectory()) {
            logger.error("文件夹路径不存在，创建路径:" + tempPath);
            folder.mkdirs();
        }
        File tempFile = new File(folder.getAbsolutePath() + "/" + dateStr + documentFile.getFileName());
        try {
            file.transferTo(tempFile);
            AnnualReportEntity entity = new AnnualReportEntity();
            entity.setCreateTime(date);
            entity.setCreatorId(info.getId());
            entity.setLastUpdateTime(date);
            entity.setLastUpdatorId(info.getId());
            entity.setCompanyId(info.getCompanyId());
            String month = documentFile.getMonth();
            String[] dateArr = month.split("-");
            entity.setYear(Integer.parseInt(dateArr[0]));
            entity.setMonth(Integer.parseInt(dateArr[1]));
            entity.setType(documentFile.getFileType());
            entity.setReportName(documentFile.getFileName());
            entity.setFilePath(info.getCompanyId() + "/document/"+ documentFile.getTypeName() + "/" + dateStr + documentFile.getFileName());
            annualReportService.insertFile(entity);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 删除文件
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "删除文件",mode = SystemLog.SAVE_DB)
    @PostMapping("/del")
    @ApiOperation(value = "删除文件", notes = "删除文件", response = boolean.class)
    public boolean delFile(@RequestBody @Validated AnnualReportEntity model) throws OwnerException {
        return annualReportService.delFile(model);
    }
}
