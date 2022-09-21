package com.yskc.ms.controller.es;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.models.newexpert.expert.*;
import com.yskc.ms.models.user.ResetPasswordModel;
import com.yskc.ms.service.es.ExpertManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * @DateTime: 2021/9/23 11:43
 * @Description:
 * @author: hsx
 */

@Api(tags = "专家管理接口", value = "专家管理接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/esManage")
public class EsManageController extends BaseController {
    @Autowired
    private ExpertManageService expertManageService;
    @Autowired
    private MsConfig msConfig;

    @GetMapping("/getExpertList")
    @PermissionRequired(perms = "es:expertManage:search")
    @ApiOperation(value = "获取专家库用户列表", notes = "获取专家库用户列表")
    public PageModel<List<ExpertModel>> getExpertList(QueryExpertModel query) {
        return expertManageService.getExpertList(query);
    }

    @PostMapping("/changeUserStatus")
    @SystemLog(description = "禁用/启用用户", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "es:expertManage:enabled ,expert:expertManage:disabled")
    @ApiOperation(value = "禁用/启用 用户", notes = "禁用/启用 用户")
        public Boolean ChangeUserStatus(@RequestBody ExpertModel model) throws OwnerException {
        return expertManageService.ChangeUserStatus(model.getId(), model.getDisabled());
    }

    @PostMapping("/resetPassword")
    @PermissionRequired(perms = "es:expertManage:reset")
    @SystemLog(description = "重置用户密码", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "重置用户密码", notes = "重置用户密码")
    public Boolean resetPassword(@RequestBody ResetPasswordModel rpm) throws OwnerException {
        return expertManageService.resetPassword(rpm);
    }

    @PostMapping("/updateUser")
    @PermissionRequired(perms = "es:expertManage:audit")
    @SystemLog(description = "编辑用户信息", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "编辑用户信息", notes = "编辑用户信息")
    public Boolean updateUser(@RequestBody ExpertModel model) throws OwnerException {
        return expertManageService.updateUser(model);
    }

    @GetMapping("/getExpertAuditList")
    @PermissionRequired(perms = "es:esUserExpert:search")
    @ApiOperation(value = "获取专家审核列表", notes = "获取专家审核列表")
    public PageModel<List<ExpertAuditModel>> getExpertAuditList(QueryExpertModel query) {
        return expertManageService.getExpertAuditList(query);
    }

    @PostMapping("/updateTags")
    @PermissionRequired(perms = "es:expertManage:updateTags")
    @SystemLog(description = "编辑用户标签", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "编辑用户标签", notes = "编辑用户标签")
    public Boolean updateTags(@RequestBody ExpertTagsModel model){
        return expertManageService.updateExpertAuditList(model);
    }

    @GetMapping("/getUserAuditLog")
    @PermissionRequired(perms = "es:esUserExpert:log")
    @ApiOperation(value = "获取审核日志记录", notes = "获取审核日志记录")
    public List<UserExpertLogModel> getUserAuditLog(Integer id) {
        return expertManageService.getUserAuditLog(id);
    }

    @PostMapping("/expertAudit")
    @PermissionRequired(perms = "es:esUserExpert:audit")
    @SystemLog(description = "批量审核专家入驻信息", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "批量审核专家入驻信息", notes = "批量审核专家入驻信息")
    public Boolean expertAudit(@RequestBody UserExpertLogModel model) throws OwnerException {
        return expertManageService.expertAudit(model,getUserInfo().getId());
    }

    @GetMapping("/getAuditTotal")
    @ApiOperation(value = "获取待审核用户统计", notes = "获取待审核用户统计")
    public Map<String, Integer> getUserAuditTotal() {
        return expertManageService.getStatusTotal();
    }

    @GetMapping("/getUserEnterData")
    @PermissionRequired(perms = "es:esUserExpert:view")
    @ApiOperation(value = "获取用户入驻时提交的数据", notes = "获取用户入驻时提交的数据")
    public UserEnterModel getUserEnterData(Integer id) {
        return expertManageService.getUserEnterData(id);
    }

    @GetMapping("/download")
    @SystemLog(description = "下载文件")
    @ApiOperation(value = "下载文件", notes = "下载文件", response = boolean.class)
    public void downloadFile(String filePath,String fileName) throws OwnerException {
        Path path = Paths.get(msConfig.getUploadConfig().getImportPath(), filePath);
        String fullPath = path.toString();
        super.download(fullPath, fileName);
    }
}
