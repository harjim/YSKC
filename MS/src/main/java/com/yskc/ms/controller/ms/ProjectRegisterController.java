package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.models.projectRegister.ProjectRegisterDataModel;
import com.yskc.ms.models.projectRegister.ProjectRegisterModel;
import com.yskc.ms.models.projectRegister.QueryRegisterModel;
import com.yskc.ms.models.projectRegister.UploadRegisterFileModel;
import com.yskc.ms.service.ms.ProjectRegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by hck
 * on 2020/8/6 13:31
 * description:
 */
@Api(tags = "项目备案管理接口", value = "项目备案管理接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/projectRegister")
public class ProjectRegisterController extends BaseController {

    @Autowired
    private MsConfig msConfig;

    @Autowired
    private ProjectRegisterService projectRegisterService;

    @GetMapping("/getData")
    @PermissionRequired(perms = "project:register:search")
    @ApiOperation(value = "查询产品列表", notes = "查询产品列表", response = List.class)
    public PageModel<List<ProjectRegisterDataModel>> getData(QueryRegisterModel model) throws OwnerException {
        return projectRegisterService.getData(model, getDataPerm());
    }

    @GetMapping("/getList")
    @PermissionRequired(perms = "project:register:search")
    @ApiOperation(value = "获取项目备案列表", notes = "获取项目备案列表", response = List.class)
    public List<ProjectRegisterModel> getData(@RequestParam("projectId") Integer projectId) throws OwnerException {
        return projectRegisterService.getList(projectId);
    }

    @PostMapping("/add")
    @ApiOperation(notes = "添加项目备案", value = "添加项目备案")
    @SystemLog(description = "添加项目备案", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "project:register:add")
    public boolean add(@RequestBody @Validated ProjectRegisterModel model) throws OwnerException {
        return projectRegisterService.addProjectRegister(getUserInfo(), model);
    }

    @PostMapping("/edit")
    @SystemLog(description = "编辑项目备案", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "project:register:edit")
    @ApiOperation(value = "编辑项目备案", notes = "编辑项目备案", response = boolean.class)
    public Boolean editProduct(@RequestBody @Validated ProjectRegisterModel model) throws OwnerException {
        return projectRegisterService.updateProjectRegister(getUserInfo(), model);
    }

    @PostMapping("/del")
    @ApiOperation(notes = "删除项目备案", value = "删除项目备案")
    @SystemLog(description = "删除项目备案", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "project:register:del")
    public boolean del(@RequestBody @Validated ProjectRegisterModel model) throws OwnerException {
        return projectRegisterService.delProjectRegister(getUserInfo(), model);
    }

    @PostMapping("/upload")
    @PermissionRequired(perms = "project:register:upload")
    @ApiOperation(value = "上传文件", notes = "上传文件", response = boolean.class)
    public Map<String, List<String>> uploadFiles(@RequestParam("file") MultipartFile[] multipartFile, @RequestParam("registerFile") MultipartFile[] registerFile, @RequestParam("dir") String dir) throws OwnerException {
        String expertPath = msConfig.getUploadConfig().getImportPath();
        Map<String, List<String>> result = new LinkedHashMap<>();
        try {
            for (MultipartFile file : multipartFile) {
                String fileName = System.currentTimeMillis() + "/" + file.getOriginalFilename();
                String filePath = dir + fileName;
                File tempFile = new File(expertPath + filePath);
                if (!tempFile.getParentFile().exists()) {
                    tempFile.getParentFile().mkdirs();
                }
                file.transferTo(tempFile);
                if (!result.containsKey("filePath")) {
                    result.put("filePath", new ArrayList<>());
                }
                result.get("filePath").add(filePath);
            }
            for (MultipartFile register : registerFile) {
                String fileName = System.currentTimeMillis() + "/" + register.getOriginalFilename();
                String filePath = dir + fileName;
                File tempFile = new File(expertPath + filePath);
                if (!tempFile.getParentFile().exists()) {
                    tempFile.getParentFile().mkdirs();
                }
                register.transferTo(tempFile);
                if (!result.containsKey("registerFile")) {
                    result.put("registerFile", new ArrayList<>());
                }
                result.get("registerFile").add(filePath);
            }
            return result;
        } catch (Exception ex) {
            throw new OwnerException("上传失败");
        }
    }


    @PostMapping("/addFile")
    @ApiOperation(notes = "上传项目备案附件", value = "上传项目备案附件")
    @SystemLog(description = "上传项目备案附件", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "project:register:upload")
    public boolean addFile(@RequestBody @Validated UploadRegisterFileModel model) throws OwnerException {
        return projectRegisterService.uploadRegisterFile(getUserInfo(), model);
    }

    @SystemLog(description = "下载备案附件", mode = SystemLog.SAVE_DB)
    @GetMapping("/download")
    @ApiOperation(notes = "下载备案附件", value = "下载备案附件")
    @PermissionRequired(perms = "project:register:download")
    public void download(Integer registerId, String filePath, Integer sign, String fileName) throws
            OwnerException, IOException {
        if (projectRegisterService.downloadFile(registerId, filePath, sign)) {
            download(msConfig.getUploadConfig().getImportPath() + filePath, fileName);
        } else {
            throw new OwnerException("下载失败，请稍后再试。");
        }
    }
}
