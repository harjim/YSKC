package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.docFile.AttachmentModel;
import com.yskc.rs.models.project.DocFileAttachmentModel;
import com.yskc.rs.models.project.QueryAttachmentModel;
import com.yskc.rs.models.project.RdManageModel;
import com.yskc.rs.service.DocFileAttachmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/6/17 14:57
 * @Description:文档附件
 */
@Api(tags = "文档附件接口",value = "文档附件接口")
@CrossOrigin(origins = "*",allowCredentials="true",allowedHeaders = "",methods = {})
@RestController
@RequestMapping("/api/docFileAttachment")
public class DocFileAttachmentController extends BaseController{
    private static Logger logger = LoggerFactory.getLogger(BaseController.class);
    @Autowired
    private RsConfig rsConfig;
    @Autowired
    private DocFileAttachmentService docFileAttachmentService;

    @GetMapping("/getAttachments")
    @ApiOperation(value = "获取附件列表", notes = "获取附件列表")
    public List<AttachmentModel> getAttachments(Integer docFileId, Integer projectId) throws OwnerException {
        return docFileAttachmentService.getAttachments(docFileId,projectId);
    }

    @PostMapping("/delUploadFile")
    @SystemLog(description = "删除上传文件", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "删除上传文件", notes = "删除上传文件", response = boolean.class)
//    @PermissionRequired(perms = "project:rdLevelManage:del")
    public Boolean delUploadFile(@RequestBody @Validated AttachmentModel model){
       return docFileAttachmentService.del(model.getId());
    }

    /**
     * 上传附件
     *
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/upload")
    @SystemLog(description = "上传图片", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "上传图片", notes = "导入图片", response = boolean.class)
//    @PermissionRequired(perms = "project:rdLevelManage:upload")
    public AttachmentModel upload(@RequestParam(value = "file",required = false) MultipartFile file, AttachmentModel model) throws Exception {
        UserInfo userInfo = getUserInfo();
        if(file!=null && !file.isEmpty()) {
            uploadAndInsert(file, model,userInfo);
        }
        return  docFileAttachmentService.save(userInfo,model);
    }

    private void uploadAndInsert(MultipartFile file, AttachmentModel model,UserInfo userInfo) throws Exception{
        if(model==null){
            throw new OwnerException("上传未关联过程文档，请联系管理员！");
        }

        String tempPath = MessageFormat.format("{0}/{1,number,#}/{2,number,#}",
                "project",
                userInfo.getCompanyId(),
                model.getProjectId());
        String[] fileNames = file.getOriginalFilename().split("\\.");

        fileNames[0] = fileNames[0] + "." + System.currentTimeMillis();
        String fileName = String.join(".", fileNames);
        File tempFile = new File(new File(rsConfig.getUploadConfig().getResourcePath() + Constant. IMAGES_DIR , tempPath).getAbsolutePath() + "/" + fileName);
        String filePath=Constant. IMAGES_DIR+"/"+tempPath+"/" + fileName;
        if (!tempFile.getParentFile().exists()) {
            tempFile.getParentFile().mkdirs();
        }
        try {
            file.transferTo(tempFile);
            model.setFilePath(filePath);
        } catch (Exception ex) {
            logger.error("transferTo", ex);
        }
    }

    @GetMapping("/getRdManagerMenu")
    @ApiOperation(value = "获取多层级研发管理菜单列表", notes = "获取多层级研发管理菜单列表")
    public List<RdManageModel> getRdManagerMenu(Integer year) throws OwnerException {
        return docFileAttachmentService.getRdManagerMenu(year,getUserInfo().getCompanyId());
    }

    @GetMapping("/getList")
    @PermissionRequired(perms = "project:rdLevelManage:search")
    @ApiOperation(value = "获取多级研发管理列表", notes = "获取多级研发管理列表")
    public PageModel<List<DocFileAttachmentModel>> getList(QueryAttachmentModel query) throws OwnerException {
        return docFileAttachmentService.getList(getUserInfo().getCompanyId(), query);
    }

}
