package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.models.patent.PatentDemandModel;
import com.yskc.ms.models.patent.PatentFileModel;
import com.yskc.ms.models.patent.QueryPatentApplicationModel;
import com.yskc.ms.models.patentPlan.PatentPlanModel;
import com.yskc.ms.models.patentPlan.QueryPatentPlanModel;
import com.yskc.ms.models.project.BatchStaffModel;
import com.yskc.ms.service.ms.PatentApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
/**
 * @program: ms
 * @description: 专利申请(新)接口
 * @author: cyj
 * @create: 2022-02-09 09:09
 **/
@Api(tags = "专利申请(新)接口", value = "专利申请(新)接口")
@RequestMapping("/api/patentAppliaction")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
public class PatentApplicationController extends BaseController{
    @Autowired
    private PatentApplicationService patentApplicationService;
    @Autowired
    private MsConfig msConfig;
    @GetMapping("/getList")
    @PermissionRequired(perms = "patent:application:search")
    @ApiOperation(value = "获取需求列表", notes = "获取需求列表")
    public PageModel<List<PatentDemandModel>> getList(QueryPatentApplicationModel query) throws OwnerException {
        return patentApplicationService.getList(query,getDataPerm());
    }

    @GetMapping("/download")
    @SystemLog(description = "下载文件")
    @PermissionRequired(perms = "patent:application:download")
    @ApiOperation(value = "下载文件", notes = "下载文件", response = boolean.class)
    public void download(String filePath) throws OwnerException {
        Path path = Paths.get(msConfig.getUploadConfig().getImportPath(), filePath);
        String fullPath = path.toString();
        String fileName =new File( fullPath.trim()).getName();
        super.download(fullPath, fileName);
    }

    @PostMapping("/upload")
    @SystemLog(description = "上传附件")
    @PermissionRequired(perms = "patent:application:upload")
    @ApiOperation(value = "上传附件", notes = "上传附件", response = boolean.class)
    public boolean upload(@RequestParam("file") MultipartFile[] multipartFile, @RequestParam("type") String type, @RequestParam("id") int id) throws OwnerException {
        String path = msConfig.getUploadConfig().getImportPath();
        String dir = "/demand/";
        for (MultipartFile file : multipartFile) {
            String fileName = System.currentTimeMillis() + file.getOriginalFilename();
            String filePath = dir + fileName;
            File tempFile = new File(path + filePath);
            if (!tempFile.getParentFile().exists()) {
                tempFile.getParentFile().mkdirs();
            }
            try {
                file.transferTo(tempFile);
                boolean upload = patentApplicationService.upload(type, id, filePath);
            } catch (IOException io) {
                throw new OwnerException("上传失败");
            } catch (DataIntegrityViolationException e){
                throw new OwnerException("上传文件达到限制");
            }
        }
        return true;
    }

    @PostMapping("/del")
    @SystemLog(description = "删除附件")
    @PermissionRequired(perms = "patent:application:del")
    @ApiOperation(value = "删除附件", notes = "删除附件", response = boolean.class)
    public boolean upload(@RequestBody PatentFileModel model){
        return patentApplicationService.del(model.getType(), model.getId(),model.getFile());
    }

    @PostMapping("/setEngineer")
    @SystemLog(description = "分配工程师")
    @PermissionRequired(perms = "patent:application:setTech")
    @ApiOperation(value = "分配工程师", notes = "分配工程师", response = boolean.class)
    public boolean setEngineer(@Validated @RequestBody BatchStaffModel model){
        return patentApplicationService.setEngineer(model);
    }

    @PostMapping("/setOwner")
    @SystemLog(description = "分配业务人员")
    @PermissionRequired(perms = "patent:application:setOwner")
    @ApiOperation(value = "分配业务人员", notes = "分配业务人员", response = boolean.class)
    public boolean setOwner(@Validated @RequestBody BatchStaffModel model){
        return patentApplicationService.setOwner(model);
    }

    @GetMapping("/getPlanList")
    @ApiOperation(value = "根据需求id获取专利申请列表", notes = "获取专利申请列表")
    public PageModel<List<PatentPlanModel>> getPlanList(QueryPatentPlanModel query) throws OwnerException {
        return patentApplicationService.getPlanList(query);
    }
}
