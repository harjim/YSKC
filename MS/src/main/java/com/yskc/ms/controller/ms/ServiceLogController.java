package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.entity.ms.QueryServiceModel;
import com.yskc.ms.entity.ms.ServiceLog;
import com.yskc.ms.models.servicelog.ServiceAddLogModel;
import com.yskc.ms.models.servicelog.ServiceInfoModel;
import com.yskc.ms.models.servicelog.ServiceLogModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.service.ms.ServiceLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hck
 * on 2020/5/5 14:49
 */
@Api(tags = "客户服务记录", value = "客户服务记录")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/serviceLog")
public class ServiceLogController extends BaseController {
    @Autowired
    private ServiceLogService serviceLogService;

    @Autowired
    private MsConfig msConfig;

    @GetMapping("/queryServiceLog")
    @ApiOperation(value = "根据条件查询服务记录", notes = "根据条件查询服务记录", response = List.class)
    @PermissionRequired(perms = "customer:customerServices:search")
    public PageModel<List<ServiceInfoModel>> queryServiceLog(QueryServiceModel model) throws OwnerException {
        return serviceLogService.queryByParams(model, getUserInfo(), this.getDataPerm());
    }

    @PostMapping("/addServiceLog")
    @SystemLog(description = "添加服务记录", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "添加服务记录", notes = "添加服务记录", response = boolean.class)
    public Boolean addServiceLog(@RequestBody @Validated ServiceAddLogModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return serviceLogService.addServiceLog(model, userInfo.getId());
    }

    @PostMapping("/delServiceLog")
    @SystemLog(description = "删除服务记录", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "删除服务记录", notes = "删除服务记录", response = boolean.class)
    public Boolean delServiceLog(@RequestBody @Validated ServiceLogModel model) throws OwnerException {
        return serviceLogService.delServiceLog(model.getId());
    }

    @PostMapping("/editServiceLog")
    @SystemLog(description = "编辑服务记录", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "编辑服务记录", notes = "编辑服务记录", response = boolean.class)
    public Boolean editServiceLog(@RequestBody @Validated ServiceLogModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return serviceLogService.updateStatus(model, userInfo.getId());
    }


    /**
     * 上传文件
     *
     * @return
     * @throws OwnerException
     * @throws OwnerException
     */
    @PostMapping("/upload")
    @ApiOperation(value = "上传附件", notes = "上传附件", response = boolean.class)
    public Map<String, String> upload(@RequestParam("file") MultipartFile[] multipartFile, @RequestParam("dir") String dir) throws OwnerException {
        String path = msConfig.getUploadConfig().getImportPath();
        Map<String, String> result = new LinkedHashMap<>();
        for (MultipartFile file : multipartFile) {
            String fileName = System.currentTimeMillis() + file.getOriginalFilename();
            String filePath = dir + fileName;
            File tempFile = new File(path + filePath);
            if (!tempFile.getParentFile().exists()) {
                tempFile.getParentFile().mkdirs();
            }
            try {
                file.transferTo(tempFile);
                result.put("filePath", filePath);
                result.put("fileName", file.getOriginalFilename());
            } catch (Exception ex) {
                throw new OwnerException("上传失败");
            }
        }
        return result;
    }

    @GetMapping("/download")
    @ApiOperation(value = "下载附件", notes = "下载附件", response = boolean.class)
    public void download(Integer id, String fileName, String filePath) throws OwnerException, IOException {
        ServiceLog serviceLog = serviceLogService.getLogById(id);
        if (null == serviceLog) {
            throw new OwnerException("文件不存在或已删除，下载失败！");
        }
        String fileNames = serviceLog.getFilePath();
        List<String> list = Arrays.asList(fileNames.split(","));
        if (list.contains(filePath)) {
            Path path = Paths.get(msConfig.getUploadConfig().getImportPath(), filePath);
            String fullPath = path.toString();
            File tempFile = new File(fullPath);
            if (!tempFile.exists()) {
                throw new OwnerException("文件不存在或已删除，下载失败！");
            }
            download(fullPath, fileName);
        } else {
            throw new OwnerException("下载失败");
        }
    }

    @GetMapping("/exportServiceLog")
    @ApiOperation(value = "导出服务记录", notes = "导出服务记录")
    @SystemLog(description = "导出服务记录", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "customer:customerServices:export")
    public void exportServiceLog(QueryServiceModel model) throws Exception {
        OutputStream out = outGeneralFile("客户服务记录表.xlsx");
        serviceLogService.exportServiceLog(out, model,getDataPerm());
        out.flush();
        out.close();
    }

}
