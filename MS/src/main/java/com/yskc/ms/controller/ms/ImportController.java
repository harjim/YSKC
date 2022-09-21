package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.SystemLog;
import com.yskc.ms.config.Constant;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.models.FilePathInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

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
    private MsConfig msConfig;

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
    public FilePathInfo importImages(@RequestParam("file") MultipartFile file, @RequestParam("dir") String dir, @RequestParam("key") String key) throws Exception {
        FilePathInfo filePathInfo=new FilePathInfo();
        filePathInfo.setKey(key);
        filePathInfo.setFileName(file.getOriginalFilename());
        String[] fileNames = file.getOriginalFilename().split("\\.");
        if(!dir.endsWith(Constant.PATH_SEPARATOR)){
            dir = dir + Constant.PATH_SEPARATOR;
        }
        if(!dir.startsWith(Constant.PATH_SEPARATOR)){
            dir = Constant.PATH_SEPARATOR + dir;
        }
        fileNames[0] = fileNames[0] + "." + System.currentTimeMillis();
        String fileName = String.join(".", fileNames);
        filePathInfo.setNewFileName(fileName);
        File tempFile = new File(new File(msConfig.getUploadConfig().getResourcePath() + Constant. IMAGES_DIR , dir).getAbsolutePath() + "/" + fileName);
        if (!tempFile.getParentFile().exists()) {
            tempFile.getParentFile().mkdirs();
        }
        try {
            file.transferTo(tempFile);
        } catch (Exception ex) {
            logger.error("transferTo", ex);
        }
        String returnFileName =Constant.IMAGES_DIR + dir + fileName;
        filePathInfo.setFilePath(returnFileName);
        return filePathInfo;

    }
}
