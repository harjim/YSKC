package com.yskc.ms.controller.rs;

import cn.hutool.core.io.IoUtil;
import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.ms.config.Constant;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.entity.rs.SysDocumentEntity;
import com.yskc.ms.entity.rs.models.AttachmentModel;
import com.yskc.ms.entity.rs.models.DocListModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.generatebuildfile.GenerateBuildFileModel;
import com.yskc.ms.service.rs.DocFileAttachmentService;
import com.yskc.ms.service.rs.SysDocumentService;
import com.yskc.ms.utils.YsExcelUtils;
import com.yskc.ms.utils.YsPptUtils;
import com.yskc.ms.utils.YsWordUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
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
    @Autowired
    private MsConfig msConfig;
    @Autowired
    private SysDocumentService sysDocumentService;
    @Autowired
    private DocFileAttachmentService docFileAttachmentService;


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
        if (org.apache.commons.lang3.StringUtils.isEmpty(filePath)) {
            throw new OwnerException("无数据");
        }
        OutputStream out = outGeneralFile(filePath);
        Path path = Paths.get(msConfig.getUploadConfig().getResourcePath(), filePath);
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
        } else if (name.endsWith(Constant.EXCEL_PDF)) {
            IoUtil.copy(new FileInputStream(file), out);
        } else {
            IoUtil.copy(new FileInputStream(file), out);
        }
        out.flush();
        out.close();
    }

    @SystemLog(description = "下载过程文件记录及附件", mode = SystemLog.SAVE_DB)
    @GetMapping("/downloadAttachment")
    @ApiOperation(notes = "下载过程文件记录及附件", value = "下载过程文件记录及附件")
    public void downloadAttachment(String path, String fileName) throws OwnerException, IOException {
        if (StringUtils.isEmpty(path)) {
            throw new OwnerException("文件不存在，下载失败。");
        }
        String filePath = msConfig.getUploadConfig().getResourcePath() + path;
        download(filePath, fileName);
    }

    @GetMapping("/getAttachments")
    @ApiOperation(notes = "获取记录及附件", value = "获取记录及附件")
    public List<AttachmentModel> getAttachments(Integer docFileId, Integer projectId) {
        return docFileAttachmentService.getAttachments(docFileId, projectId);
    }

    @GetMapping("/downloadFile")
    @SystemLog(description = "下载文档", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "下载文档", notes = "下载文档", response = String.class)
    public void downloadFile(int id) throws OwnerException, IOException {
        UserInfo info = getUserInfo();
        SysDocumentEntity entity = sysDocumentService.getFileById(id);
        OutputStream os = outGeneralFile(entity.getFileName());
        entity.setDownloadTimes(entity.getDownloadTimes() + 1);
        entity.setLastUpdatorId(info.getId()==null ? info.getId() : -1);
        entity.setMsLastUpdatorId(info.getId()==null ? -1 : info.getId());
        entity.setLastUpdateTime(new Date());
        sysDocumentService.updateFile(entity);
        Path path = Paths.get(msConfig.getUploadConfig().getDocPath(), entity.getFilePath());
        String fullPath = path.toString();
        File file = new File(fullPath);
        if(file.exists()){
            InputStream fis = new BufferedInputStream(new FileInputStream(file));
            IoUtil.copy(fis, os);
            os.flush();
            os.close();
            fis.close();
        }else{
            throw new OwnerException("文件不存在，请联系管理员");
        }
    }

    @GetMapping("/generateBuildFile")
    @ApiOperation(notes="下载默认机构建设事项制度文件",value = "下载默认机构建设事项制度文件")
    public void generateBuildFile(Integer year,String docName,String companyName,Integer companyId) throws Exception {
        String path = msConfig.getUploadConfig().getRsBasicPath();
        Map<String, Object> data = sysDocumentService.getBuildFileData(year,companyName,companyId,path);
        OutputStream out = outGeneralFile(MessageFormat.format("{0}-{1,number,#}{2}.docx",
                companyName, year, docName));
        YsWordUtils.generalWordReport(data,
                msConfig.getUploadConfig().getResourcePath() + Constant.BUILD_TEMPLATE_DIR + docName + ".docx",
                doc -> {
                    if (null != doc) {
                        try {
                            doc.write(out);
                            out.flush();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }

    /**
     * @param listType
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryAppendixDocList")
    @ApiOperation(value = "查询附件清单", notes = "查询附件清单", response = String.class)
    public List<DocListModel> queryAppendixDocList(Integer listType, String patentNo, Integer year, Integer companyId){
        return sysDocumentService.queryAppendixDocList(listType, companyId, patentNo, year);
    }
}

