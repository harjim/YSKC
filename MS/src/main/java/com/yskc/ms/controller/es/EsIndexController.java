package com.yskc.ms.controller.es;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.config.Constant;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.models.BatchDeleteModel;
import com.yskc.ms.models.FilePathInfo;
import com.yskc.ms.models.newexpert.index.ConfigDataModel;
import com.yskc.ms.models.newexpert.index.ConfigModuleModel;
import com.yskc.ms.models.newexpert.index.QueryConfigDataModel;
import com.yskc.ms.models.newexpert.index.QueryConfigModuleModel;
import com.yskc.ms.service.es.ExpertIndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * @DateTime: 2021/9/26 16:10
 * @Description:
 * @author: hsx
 */
@Api(tags = "es首页管理接口", value = "es首页管理接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/esIndex")
public class EsIndexController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private ExpertIndexService expertIndexService;
    @Autowired
    private MsConfig msConfig;


    @GetMapping("/getDataList")
    @PermissionRequired(perms = "es:esIndexData:search")
    @ApiOperation(value = "获取es首页数值配置数据", notes = "获取es首页数值配置数据")
    public PageModel<List<ConfigDataModel>> getDataList(QueryConfigDataModel query) {
        return expertIndexService.getDataList(query);
    }

    @PostMapping("/addConfigData")
    @SystemLog(description = "添加es首页数值配置数据", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "es:esIndexData:add")
    @ApiOperation(value = "添加es首页数值配置数据", notes = "添加es首页数值配置数据")
    public Boolean addConfigData(@RequestBody ConfigDataModel model) throws OwnerException {
        return expertIndexService.addConfigData(model, getUserInfo());
    }

    @PostMapping("/editConfigData")
    @SystemLog(description = "编辑es首页数值配置数据", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "es:esIndexData:edit")
    @ApiOperation(value = "编辑es首页数值配置数据", notes = "编辑es首页数值配置数据")
    public Boolean editConfigData(@RequestBody ConfigDataModel model) throws OwnerException {
        return expertIndexService.editConfigData(model, getUserInfo());
    }

    @PostMapping("/delData")
    @SystemLog(description = "批量删除es首页数值配置数据", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "es:esIndexData:delete")
    @ApiOperation(value = "批量删除es首页数值配置数据", notes = "批量删除es首页数值配置数据")
    public Boolean delData(@RequestBody BatchDeleteModel model) {
        return expertIndexService.delData(model.getIds());
    }

    @PostMapping("/switchConfigData")
    @SystemLog(description = "禁用/启用es数值配置数据", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "es:esIndexData:disabled,es:esIndexData:enabled")
    @ApiOperation(value = "禁用/启用es数值配置数据", notes = "禁用/启用es数值配置数据")
    public Boolean switchConfigData(@RequestBody ConfigDataModel model) throws OwnerException {
        return expertIndexService.switchConfigData(model, getUserInfo());
    }


    @GetMapping("/getModuleList")
    @SystemLog(description = "获取es首页模块配置")
    @PermissionRequired(perms = "es:esIndexModule:search")
    @ApiOperation(value = "获取es首页模块配置", notes = "获取es首页模块配置")
    public PageModel<List<ConfigModuleModel>> getModuleList(QueryConfigModuleModel query) {
        return expertIndexService.getModuleList(query);
    }


    @PostMapping("/addConfigModule")
    @SystemLog(description = "添加es首页模块配置", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "es:esIndexData:add")
    @ApiOperation(value = "添加es首页模块配置", notes = "添加es首页模块配置")
    public Boolean addConfigModule(@RequestBody ConfigModuleModel model) throws OwnerException {
        return expertIndexService.addConfigModule(model, getUserInfo());
    }

    @PostMapping("/editConfigModule")
    @SystemLog(description = "编辑es首页模块配置", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "es:esIndexData:edit")
    @ApiOperation(value = "编辑es首页模块配置", notes = "编辑es首页模块配置")
    public Boolean editConfigModule(@RequestBody ConfigModuleModel model) throws OwnerException {
        return expertIndexService.editConfigModule(model, getUserInfo());
    }

    @PostMapping("/switchConfigModule")
    @SystemLog(description = "禁用/启用es首页模块配置", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "es:esIndexModule:enabled,es:esIndexModule:disabled")
    @ApiOperation(value = "禁用/启用es首页模块配置", notes = "禁用/启用es首页模块配置")
    public Boolean switchConfigModule(@RequestBody ConfigModuleModel model) throws OwnerException {
        return expertIndexService.switchConfigModule(model, getUserInfo());
    }

    @PostMapping("/delModule")
    @SystemLog(description = "批量删除专家库首页模块", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "es:esIndexModule:delete")
    @ApiOperation(value = "批量删除专家库首页模块", notes = "批量删除专家库首页模块")
    public Boolean delModule(@RequestBody BatchDeleteModel model) {
        return expertIndexService.delModule(model.getIds());
    }


    @PostMapping("/importImages")
    @SystemLog(description = "导入图片", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "导入图片", notes = "导入图片", response = boolean.class)
    public FilePathInfo importImages(@RequestParam("file") MultipartFile file, String key) throws Exception {
        FilePathInfo filePathInfo = new FilePathInfo();
        filePathInfo.setKey(key);
        filePathInfo.setFileName(file.getOriginalFilename());

        String fileName = System.currentTimeMillis() + file.getOriginalFilename();
        filePathInfo.setNewFileName(fileName);
        String dir = "es_module";
        File tempFile = new File(new File(msConfig.getUploadConfig().getResourcePath() + Constant.IMAGES_DIR, dir).getAbsolutePath() + "/" + fileName);
        if (!tempFile.getParentFile().exists()) {
            tempFile.getParentFile().mkdirs();
        }
        try {
            file.transferTo(tempFile);
        } catch (Exception ex) {
            logger.error("transferTo", ex);
        }
        String returnFileName = Constant.IMAGES_DIR + "/" + dir + "/" + fileName;
        filePathInfo.setFilePath(returnFileName);
        return filePathInfo;

    }
}
