package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.TechRequirement;
import com.yskc.rs.models.BatchDeleteModel;
import com.yskc.rs.models.techrequirement.QueryTechRequirement;
import com.yskc.rs.models.techrequirement.TechRequirementModel;
import com.yskc.rs.service.TechRequirementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.controller.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-21 09:24
 * @Description: 技术需求接口
 */

@Api(tags = "技术需求接口", value = "技术需求接口")
@CrossOrigin(origins = "*", allowedHeaders = "", allowCredentials = "true", methods = {})
@RestController
@RequestMapping("/api/techRequirement")
public class TechRequirementController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TechRequirementService techRequirementService;

    @Autowired
    private RsConfig rsConfig;

    @GetMapping("/getList")
    @PermissionRequired(perms = "rdorg:techRequirement:search")
    @ApiOperation(notes = "获取技术需求列表", value = "获取技术需求列表")
    public PageModel<List<TechRequirementModel>> getList(QueryTechRequirement query) throws OwnerException {
        return techRequirementService.getList(query, getUserInfo().getCompanyId());
    }

    @SystemLog(description = "添加技术需求", mode = SystemLog.SAVE_DB)
    @PostMapping("/add")
    @PermissionRequired(perms = "rdorg:techRequirement:add")
    @ApiOperation(notes = "添加技术需求", value = "添加技术需求")
    public Boolean add(@RequestBody @Validated TechRequirement techRequirement) throws OwnerException {
        return techRequirementService.add(techRequirement, getUserInfo());
    }

    @SystemLog(description = "更新技术需求", mode = SystemLog.SAVE_DB)
    @PostMapping("/update")
    @PermissionRequired(perms = "rdorg:techRequirement:edit")
    @ApiOperation(notes = "更新技术需求", value = "更新技术需求")
    public Boolean update(@RequestBody @Validated TechRequirement techRequirement) throws OwnerException {
        return techRequirementService.update(techRequirement, getUserInfo());
    }

    @SystemLog(description = "删除技术需求", mode = SystemLog.SAVE_DB)
    @PostMapping("/del")
    @PermissionRequired(perms = "rdorg:techRequirement:del")
    @ApiOperation(notes = "删除技术需求", value = "删除技术需求")
    public Boolean delete(@RequestBody @Validated BatchDeleteModel deleteModel) {
        return techRequirementService.delete(deleteModel);
    }

    @SystemLog(description = "作废技术需求", mode = SystemLog.SAVE_DB)
    @PostMapping("/invalid")
    @PermissionRequired(perms = "rdorg:techRequirement:invalid")
    @ApiOperation(notes = "作废技术需求", value = "作废技术需求")
    public boolean invalid(@RequestBody @Validated BatchDeleteModel batchModel) throws OwnerException {
        return techRequirementService.invalid(getUserInfo(), batchModel);
    }

    @SystemLog(description = "下载附件", mode = SystemLog.SAVE_DB)
    @GetMapping("/download")
    @ApiOperation(notes = "下载附件", value = "下载附件")
    public void download(Integer id, String path, String fileName) throws OwnerException, IOException {
        String filePath = techRequirementService.getFilePath(id);
        if (StringUtils.isEmpty(filePath)) {
            throw new OwnerException("文件不存在，下载失败。");
        }
        if (filePath.contains(path)) {
            download(rsConfig.getUploadConfig().getMsImportPath() + path, fileName);
        } else {
            throw new OwnerException("下载失败，请稍后再试。");
        }
    }

    /**
     * 上传文件
     *
     * @return
     * @throws OwnerException
     * @throws OwnerException
     */
    @PostMapping("/upload")
    @SystemLog(description = "上传附件", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "上传附件", notes = "上传附件", response = boolean.class)
    public Map<String, String> upload(@RequestParam("file") MultipartFile[] multipartFile, @RequestParam("dir") String dir) throws OwnerException {
        String expertPath = rsConfig.getUploadConfig().getMsImportPath();
        Map<String, String> result = new LinkedHashMap<>();
        for (MultipartFile file : multipartFile) {
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
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
                throw new OwnerException("上传失败");
            }
        }
        return result;
    }
}
