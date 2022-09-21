package com.yskc.ms.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.config.Constant;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.entity.rs.TechRequirement;
import com.yskc.ms.models.BatchDeleteModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.techrequirement.QueryTechRequirement;
import com.yskc.ms.models.techrequirement.TechRequirementModel;
import com.yskc.ms.service.rs.TechRequirementService;
import com.yskc.ms.utils.YsWordUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
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

    @Autowired
    private TechRequirementService techRequirementService;

    @Autowired
    private MsConfig msConfig;

    @GetMapping("/getList")
    @PermissionRequired(perms = "customer:requirement:search")
    @ApiOperation(notes = "获取技术需求列表", value = "获取技术需求列表")
    public PageModel<List<TechRequirementModel>> getList(QueryTechRequirement query)throws OwnerException {
        return techRequirementService.getList(query,getDataPerm());
    }

    @SystemLog(description = "添加技术需求", mode = SystemLog.SAVE_DB)
    @PostMapping("/add")
    @PermissionRequired(perms = "customer:requirement:add")
    @ApiOperation(notes = "添加技术需求", value = "添加技术需求")
    public Boolean add(@RequestBody @Validated TechRequirement techRequirement) throws OwnerException {
        return techRequirementService.add(techRequirement, getUserInfo());
    }

    @SystemLog(description = "更新技术需求", mode = SystemLog.SAVE_DB)
    @PostMapping("/update")
    @PermissionRequired(perms = "customer:requirement:edit")
    @ApiOperation(notes = "更新技术需求", value = "更新技术需求")
    public Boolean update(@RequestBody @Validated TechRequirement techRequirement) throws OwnerException {
        return techRequirementService.update(techRequirement, getUserInfo());
    }

    @SystemLog(description = "删除技术需求", mode = SystemLog.SAVE_DB)
    @PostMapping("/del")
    @PermissionRequired(perms = "customer:requirement:del")
    @ApiOperation(notes = "删除技术需求", value = "删除技术需求")
    public Boolean delete(@RequestBody @Validated BatchDeleteModel deleteModel) {
        return techRequirementService.delete(deleteModel);
    }

    @SystemLog(description = "作废技术需求", mode=SystemLog.SAVE_DB)
    @PostMapping("/invalid")
    @PermissionRequired(perms = "customer:requirement:invalid")
    @ApiOperation(notes = "作废技术需求", value = "作废技术需求")
    public boolean invalid(@RequestBody @Validated BatchDeleteModel batchModel)throws OwnerException{
        return techRequirementService.invalid(getUserInfo(),batchModel);
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
            download(msConfig.getUploadConfig().getImportPath() + path, fileName);
        } else {
            throw new OwnerException("下载失败，请稍后再试。");
        }
    }

    @SystemLog(description = "导出技术需求", mode = SystemLog.SAVE_DB)
    @GetMapping("/export")
    @ApiOperation(notes = "导出技术需求", value = "导出技术需求")
    public void export(Integer id) throws Exception{
        OutputStream out = outGeneralFile( "技术需求.docx");
        UserInfo userInfo = this.getUserInfo();
        Map<String,Object> dataMap=techRequirementService.exportWord(id,userInfo);
        String path = msConfig.getUploadConfig().getResourcePath()+ Constant.TEMPLATE_DIR  + "技术需求模板.docx";
        YsWordUtils.generalWordReport(dataMap, path, (workBook) -> {
            try {
                workBook.write(out);
                out.flush();
                out.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        });
    }

}
