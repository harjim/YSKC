package com.yskc.ms.controller.rs;


import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.enums.FileTypeEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.entity.rs.ProductAddStageModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.rs.*;
import com.yskc.ms.service.rs.RsProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hck
 * on 2020/10/13 8:30
 * description:申报项目接口
 */
@Api(tags = "申报项目接口", value = "申报项目接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/rsProduct")
public class RsProductController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RsProductService rsProductService;
    @Autowired
    private MsConfig msConfig;


    @PostMapping("/allocatingStaff")
    @PermissionRequired(perms = "project:declareProject:setManager")
    @SystemLog(description = "分配负责人", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "分配负责人", notes = "分配负责人", response = boolean.class)
    public Boolean allocatingStaff(@RequestBody @Validated List<RsProductModel> models) throws OwnerException {
        return rsProductService.allocatingStaff(models, getUserInfo());
    }

    @PostMapping("/add")
    @PermissionRequired(perms = "project:declareProject:add")
    @SystemLog(description = "添加申报项目", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "添加申报项目", notes = "添加申报项目", response = boolean.class)
    public Boolean add(@RequestBody @Validated RsProductModel model) throws OwnerException {
        return rsProductService.add(model, getUserInfo());
    }


    @PostMapping("/update")
    @PermissionRequired(perms = "project:declareProject:edit")
    @SystemLog(description = "修改申报项目", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "修改申报项目", notes = "修改申报项目", response = boolean.class)
    public List<RsDirectionModel> update(@RequestBody @Validated RsProductModel model) throws OwnerException {
        return rsProductService.update(model, getUserInfo());
    }

    @GetMapping("/getList")
    @PermissionRequired(perms = "project:declareProject:search")
    @ApiOperation(value = "查询申报项目", notes = "查询申报项目")
    public PageModel<List<RsProductModel>> getList(QueryRsProductModel query) throws OwnerException {
        return rsProductService.getList(query,getDataPerm());
    }

    @PostMapping("/del")
    @PermissionRequired(perms = "project:declareProject:del")
    @SystemLog(description = "删除申报项目", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "删除申报项目", notes = "删除申报项目", response = Boolean.class)
    public Boolean del(@RequestBody @Validated List<RsProductModel> models) throws OwnerException {
        return rsProductService.del(models, getUserInfo().getId());
    }

    @PostMapping("/delDirection")
    @PermissionRequired(perms = "project:declareProject:edit")
    @SystemLog(description = "删除申报项目方向", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "删除申报项目方向", notes = "删除申报项目方向", response = Boolean.class)
    public Boolean delDirection(@RequestBody @Validated RsDirectionModel model) throws OwnerException {
        UserInfo info = getUserInfo();
        return rsProductService.delDirection(model, info.getId());
    }

    @GetMapping("/getFileTypes")
    @SystemLog(description = "获取文件类型")
    @ApiOperation(value = "获取文件类型", notes = "获取文件类型")
    public List<Map<String, String>> getFileTypes() throws OwnerException {
        return FileTypeEnum.getFileTypes();

    }

    @GetMapping("/getDirection")
    @SystemLog(description = "获取申报项目方向列表")
    @ApiOperation(value = "获取申报项目阶段", notes = "获取申报项目阶段")
    public List<RsDirectionModel> getDirection(Integer productId) throws OwnerException {
        return rsProductService.getDirection(productId);
    }

    @GetMapping("/getDirectionStage")
    @SystemLog(description = "获取申报项目方向阶段")
    @ApiOperation(value = "获取申报项目方向阶段", notes = "获取申报项目方向阶段")
    public List<RsProductStageModel> getDirectionStage(Integer directionId) throws OwnerException {
        return rsProductService.getDirectionStage(directionId);
    }

    @PostMapping("/addStages")
    @PermissionRequired(perms = "project:declareProject:editStage")
    @SystemLog(description = "方向添加阶段", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "方向添加阶段", notes = "方向添加阶段", response = boolean.class)
    public boolean addStages(@RequestBody @Validated List<ProductAddStageModel> models) throws OwnerException {
        return rsProductService.addStages(models, getUserInfo());
    }

    @PostMapping("/editStages")
    @PermissionRequired(perms = "project:declareProject:editStage")
    @SystemLog(description = "申报项目编辑阶段", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "申报项目编辑阶段", notes = "申报项目编辑阶段", response = boolean.class)
    public boolean editStages(@RequestBody @Validated List<ProductAddStageModel> models) throws OwnerException {
        return rsProductService.editStages(models, getUserInfo());
    }

    @PostMapping("/delStages")
    @PermissionRequired(perms = "project:declareProject:delStage")
    @SystemLog(description = "申报项目删除阶段", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "申报项目删除阶段", notes = "申报项目删除阶段", response = boolean.class)
    public boolean delStages(@RequestBody @Validated ProductAddStageModel model) throws OwnerException {
        return rsProductService.delStages(model, getUserInfo());
    }

    @PostMapping("/delStageList")
    @PermissionRequired(perms = "project:declareProject:delData")
    @SystemLog(description = "删除阶段资料", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "删除阶段资料", notes = "删除阶段资料", response = boolean.class)
    public boolean delStageList(@RequestBody @Validated List<RsProductStageListModel> models) throws OwnerException {
        return rsProductService.delStageList(models, getUserInfo());
    }


    @PostMapping("/SetStageList")
    @PermissionRequired(perms = "project:declareProject:setData")
    @SystemLog(description = "保存阶段资料", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "保存阶段资料", notes = "保存阶段资料", response = boolean.class)
    public List<RsProductStageListModel> SetStageList(@RequestBody @Validated List<RsProductStageListModel> models) throws OwnerException {
        return rsProductService.SetStageList(models, getUserInfo());
    }

    /**
     * 上传文件
     *
     * @return
     * @throws OwnerException
     * @throws OwnerException
     */
    @PostMapping("/upload")
    @SystemLog(description = "申报项目方向上传文件", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "project:declareProject:upload")
    @ApiOperation(value = "上传附件", notes = "上传附件", response = boolean.class)
    public List<Map<String, String>> upload(@RequestParam("file") MultipartFile[] multipartFile, @RequestParam("dir") String dir) throws OwnerException {
        String expertPath = msConfig.getUploadConfig().getImportPath();
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

    @GetMapping("/download")
    @SystemLog(description = "下载申报项目方向文件", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "project:declareProject:download")
    @ApiOperation(value = "下载附件", notes = "下载附件", response = boolean.class)
    public void download(String filePath, String fileName) throws OwnerException {
        Path path = Paths.get(msConfig.getUploadConfig().getImportPath(), filePath);
        String fullPath = path.toString();
        super.download(fullPath, fileName);
    }

    @GetMapping("/copyProject")
    @SystemLog(description = "复制申报项目", mode = SystemLog.SAVE_DB)
   // @PermissionRequired(perms = "project:declareProject:copy")
    @ApiOperation(value = "复制申报项目", notes = "复制申报项目", response = boolean.class)
    public Boolean copyProject(String addressCode, Integer year,Integer id) throws OwnerException {
        return rsProductService.copyProject(addressCode,year,id,getUserInfo());
    }

//    @PostMapping("/importProduct")
//    //@PermissionRequired(perms = "Patent:PatentList:import")
//    @ApiOperation(value = "导入申报项目", notes = "导入申报项目", response = String.class)
//    public boolean importProduct(@RequestParam("file") MultipartFile file) throws OwnerException {
//        UserInfo info = getUserInfo();
//        if (file.isEmpty()) {
//            throw new OwnerException("文件没有上传成功，请重新上传");
//        }
//        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
//                msConfig.getUploadConfig().getImportPath());
//        String tempFileName = file.getOriginalFilename().replace(".xls", System.currentTimeMillis() + ".xls");
//        File tempFile = new File(new File(tempPath).getAbsolutePath() + "/" + tempFileName);
//        if (!tempFile.getParentFile().exists()) {
//            tempFile.getParentFile().mkdirs();
//        }
//        try {
//            file.transferTo(tempFile);
//        } catch (Exception ex) {
//            logger.error("importProduct", ex);
//            throw new OwnerException(ErrorEnum.FAIL);
//        }
//        ExcelReader reader = ExcelUtil.getReader(tempFile);
//        List<Map<String, Object>> readAll = reader.readAll();
//        return rsProductService.importProduct(info, readAll);
//    }

}
