package com.yskc.rs.controller;
import com.yskc.common.annotation.NotLoginRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.utils.WordToHtmlUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.models.FilePathInfo;
import com.yskc.rs.models.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.stream.Collectors;

/**
 * 导入word
 * @author huronghua
 */
@Api(tags = "导入类接口",value = "导入类接口")
@CrossOrigin(origins = "*",allowCredentials="true",allowedHeaders = "",methods = {})
@RestController
@RequestMapping("/api/import")
public class ImportController extends  BaseController {
    private static Logger logger = LoggerFactory.getLogger(BaseController.class);
    @Autowired
    private RsConfig rsConfig;

    /**
     *
     * @param file
     * @return
     * @throws Exception
     */
    @NotLoginRequired
    @PostMapping("/importWord")
    @SystemLog(description = "导入word文档", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "导入word文档", notes = "导入word文档", response = boolean.class)
    public String importWord(@RequestParam("file") MultipartFile file) throws Exception {
        InputStream inputStream = null;
        if (file.getOriginalFilename().endsWith(Constant.DOC_NAME)) {
            inputStream = WordToHtmlUtils.docToHtml(file.getInputStream());
        } else if (file.getOriginalFilename().endsWith(Constant.DOCX_NAME)) {
            inputStream = WordToHtmlUtils.docxToHtml(file.getInputStream());
        } else {
            return "导入文件不是word文档";
        }
        return new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining(System.lineSeparator()));
    }

    /**
     * 导入图片
     *
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/importImages")
    @SystemLog(description = "导入图片", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "导入图片", notes = "导入图片", response = boolean.class)
    public FilePathInfo importImages(@RequestParam("file") MultipartFile file, @RequestParam("projectId") Integer projectId, @RequestParam("key") String key) throws Exception {
        FilePathInfo filePathInfo=new FilePathInfo();
        filePathInfo.setKey(key);
        filePathInfo.setFileName(file.getOriginalFilename());
        UserInfo userInfo = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_IMAGE_FILE_NAME_FORMAT,
                "project",
                userInfo.getCompanyId(),
                projectId);
        String[] fileNames = file.getOriginalFilename().split("\\.");

        fileNames[0] = fileNames[0] + "." + System.currentTimeMillis();
        String fileName = String.join(".", fileNames);
        filePathInfo.setNewFileName(fileName);
        File tempFile = new File(new File(rsConfig.getUploadConfig().getResourcePath() + Constant. IMAGES_DIR , tempPath).getAbsolutePath() + "/" + fileName);
        if (!tempFile.getParentFile().exists()) {
            tempFile.getParentFile().mkdirs();
        }
        try {
            file.transferTo(tempFile);
        } catch (Exception ex) {
            logger.error("transferTo", ex);
        }
        String returnFileName =Constant.IMAGES_DIR + "/" + tempPath + "/" + fileName;
        filePathInfo.setFilePath(returnFileName);
        return filePathInfo;

    }
}
