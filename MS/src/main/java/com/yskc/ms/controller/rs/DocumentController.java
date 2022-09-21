package com.yskc.ms.controller.rs;

import cn.hutool.core.io.IoUtil;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.ms.config.Constant;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.utils.YsExcelUtils;
import com.yskc.ms.utils.YsPptUtils;
import com.yskc.ms.utils.YsWordUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    private MsConfig msConfig;

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
    public void preview(String filePath) throws OwnerException, IOException {
        if (StringUtils.isEmpty(filePath)) {
            throw new OwnerException("无数据");
        }
        OutputStream out = outGeneralFile(filePath);
        Path path = Paths.get(msConfig.getUploadConfig().getDocPath(), filePath);
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
        }  else {
            InputStream in = new FileInputStream(file);
            IoUtil.copy(in, out);
            in.close();
        }
        out.flush();
        out.close();
    }

    @PostMapping("/upload")
    @SystemLog(description = "上传文档文件", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "上传附件", notes = "上传附件", response = boolean.class)
    public List<Map<String, String>> upload(@RequestParam("file") MultipartFile[] multipartFile, @RequestParam("dir") String dir) throws OwnerException {
        String expertPath = msConfig.getUploadConfig().getDocPath();
        List<Map<String, String>> list = new ArrayList<>();
        for (MultipartFile file : multipartFile) {
            Map<String, String> result = new LinkedHashMap<>();
            String fileName = System.currentTimeMillis() + file.getOriginalFilename();
            String filePath = dir + fileName;
            File tempFile = new File(expertPath + filePath);
            if (!tempFile.getParentFile().exists()) {
                tempFile.getParentFile().mkdirs();
            }
            try {
                file.transferTo(tempFile);
                result.put("filePath", filePath);
                result.put("fileName", file.getOriginalFilename());
                list.add(result);
            } catch (Exception ex) {
                throw new OwnerException("上传失败");
            }
        }
        return list;
    }

    @GetMapping("/downloadFile")
    @SystemLog(description = "下载文档", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "下载文档", notes = "下载文档", response = String.class)
    public void downloadFile(String fileName, String filePath) throws IOException {
        OutputStream os = outGeneralFile(fileName);
        Path path = Paths.get(msConfig.getUploadConfig().getDocPath(), filePath);
        String fullPath = path.toString();
        InputStream fis = new BufferedInputStream(new FileInputStream(fullPath));
        IoUtil.copy(fis, os);
        os.flush();
        os.close();
        fis.close();
    }
}
