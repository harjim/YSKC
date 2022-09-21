package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.tech.ProductStageModel;
import com.yskc.rs.models.tech.ProjectListModel;
import com.yskc.rs.service.DocumentService;
import com.yskc.rs.service.ProductStageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by hck
 * on 2020/8/14 13:37
 * description:技改资料清单接口
 */
@Api(tags = "技改资料清单接口", value = "技改资料清单接口")
@CrossOrigin(origins = "*", allowedHeaders = "", allowCredentials = "true", methods = {})
@RestController
@RequestMapping("/api/techAttachments")
public class TechAttachmentsController extends BaseController {

    @Autowired
    private RsConfig rsConfig;
    @Autowired
    private ProductStageService productStageService;
    @Autowired
    private DocumentService documentService;


    @GetMapping("/getList")
    @PermissionRequired(perms = "tech:pro:attachment:search")
    @ApiOperation(notes = "获取上传文件列表", value = "获取上传文件列表")
    public Map<Integer, List<ProjectListModel>> getList(Integer stageId, Integer projectId) throws OwnerException {
        return productStageService.getList(stageId, projectId, getUserInfo());
    }


    @GetMapping("/getDirectionStage")
    @ApiOperation(value = "获取申报项目方向阶段", notes = "获取申报项目方向阶段")
    public List<ProductStageModel> getDirectionStage(Integer directionId) throws OwnerException {
        return productStageService.getDirectionStage(directionId);
    }

    @PermissionRequired(perms = "tech:pro:attachment:del")
    @PostMapping("/del")
    @SystemLog(description = "删除上传资料", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "删除上传资料", notes = "删除上传资料", response = boolean.class)
    public boolean del(@RequestBody @Validated ProjectListModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return productStageService.del(userInfo, model);
    }

    /**
     * 上传附件
     *
     * @return
     * @throws OwnerException
     * @throws OwnerException
     */
    @PermissionRequired(perms = "tech:pro:attachment:upload")
    @PostMapping("/upload")
    @SystemLog(description = "上传资料", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "上传资料", notes = "上传资料", response = boolean.class)
    public ProjectListModel upload(@RequestParam("file") MultipartFile multipartFiles, ProjectListModel model) throws OwnerException {
        return productStageService.upload(multipartFiles, model, getUserInfo());
    }

    @PermissionRequired(perms = "tech:pro:attachment:download")
    @SystemLog(description = "下载资料", mode = SystemLog.SAVE_DB)
    @GetMapping("/download")
    @ApiOperation(notes = "下载资料", value = "下载资料")
    public void download(ProjectListModel model) throws OwnerException, IOException {
        if (StringUtils.isEmpty(model.getFilePath())) {
            throw new OwnerException("请检查文件是否存在");
        }
        if (!productStageService.checkFileExist(model)) {
            throw new OwnerException("文件不存在，下载失败。");
        } else {
            download(rsConfig.getUploadConfig().getDocPath() + model.getFilePath(), model.getFileName());
        }
    }

    /**
     * 预览
     *
     * @param filePath
     * @return
     * @throws OwnerException
     */
    @GetMapping("/preview")
    @PermissionRequired(perms = "tech:pro:attachment:preview,highTech:highTechIndex:preview")
    @ApiOperation(value = "预览", notes = "预览", response = String.class)
    public void preview(String filePath) throws OwnerException, IOException {
        documentService.preview(rsConfig.getUploadConfig().getDocPath(),filePath, outGeneralFile(filePath));
    }
}
