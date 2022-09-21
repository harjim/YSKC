package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.models.excel.InitMemberExcel;
import com.yskc.rs.models.init.ImportProjectInfoModel;
import com.yskc.rs.models.init.InitUsedRoleModel;
import com.yskc.rs.models.init.member.*;
import com.yskc.rs.service.ExcelService;
import com.yskc.rs.service.InitMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-23 17:10
 * @Description: 初始化项目人员列表controller层
 */
@Api(tags = "项目成员接口", value = "项目成员接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/initMember")
public class InitMemberController extends BaseController {

    @Autowired
    private InitMemberService initMemberService;

    @Autowired
    private RsConfig rsConfig;

    @Autowired
    private ExcelService excelService;

    /**
     * @param query
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getList")
    @PermissionRequired(perms = "project:report:member:search")
    @ApiOperation(value = "获取项目成员列表", notes = "获取项目成员列表")
    public PageModel<List<InitMemberModel>> getList(QueryProjectInitMemberModel query) throws OwnerException {
        return initMemberService.getList(getUserInfo().getCompanyId(), query);
    }

    /**
     * @param query
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getEmployeeList")
    @PermissionRequired(perms = "project:report:member:add")
    @ApiOperation(value = "获取非当前项目成员列表", notes = "获取非当前项目成员列表")
    public PageModel<List<InitMemberModel>> getEmployeeList(QueryInitMemberModel query) throws OwnerException {
        return initMemberService.getEmployeeList(getUserInfo().getCompanyId(), query);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @PostMapping("/addList")
    @PermissionRequired(perms = "project:report:member:add")
    @SystemLog(description = "批量添加项目成员", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "批量添加项目成员", notes = "批量添加项目成员")
    public boolean addList(@RequestBody @Validated BatchInitMemberModel model) throws OwnerException {
        if (null == model || CollectionUtils.isEmpty(model.getEnumbers())) {
            return false;
        }
        return initMemberService.addList(getUserInfo(), model);
    }

    /**
     * @param model
     * @return
     */
    @SystemLog(description = "删除项目成员", mode = SystemLog.SAVE_DB)
    @PostMapping("/del")
    @PermissionRequired(perms = "project:report:member:del")
    @ApiOperation(value = "删除项目成员", notes = "删除项目成员")
    public boolean del(@RequestBody @Validated InitMemberModel model) throws OwnerException {
        return initMemberService.del(model, getUserInfo());
    }

    /**
     * 批量设置项目角色
     *
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "批量设置项目成员角色", mode = SystemLog.SAVE_DB)
    @PostMapping("/updateInitMemberRole")
    @PermissionRequired(perms = "project:report:member:edit")
    @ApiOperation(value = "批量设置项目成员角色", notes = "批量设置项目成员角色", response = boolean.class)
    public boolean updateInitMemberRole(@RequestBody @Validated InitUsedRoleModel model) throws OwnerException {
        return initMemberService.updateInitMemberRole(model);
    }

    @SystemLog(description = "导入项目成员", mode = SystemLog.SAVE_DB)
    @PostMapping("/importMember")
    @PermissionRequired(perms = "project:report:member:import")
    @ApiOperation(value = "导入项目成员", notes = "导入项目成员")
    public String importMember(@RequestParam("file") MultipartFile file, TableField tableField, int year, Integer projectId) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                userInfo.getCompanyId());
        ExcelResult<InitMemberExcel> excelResult = excelService.getExcelResult(tempPath, file, InitMemberExcel.class, tableField);
        initMemberService.importMember(userInfo, excelResult.getData(), year, projectId);
        return excelResult.msg;
    }

    @PostMapping("/delInitMembers")
    @PermissionRequired(perms = "project:report:member:del")
    @SystemLog(description = "批量删除项目成员", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "批量删除项目成员", notes = "批量删除项目成员")
    public boolean delInitMembers(@RequestBody @Validated InitUsedRoleModel model) throws OwnerException {
        return initMemberService.delInitMembers(model, getUserInfo());
    }

    @SystemLog(description = "设置项目负责人", mode = SystemLog.SAVE_DB)
    @PostMapping("/setMaster")
    @PermissionRequired(perms = "project:report:member:edit")
    @ApiOperation(value = "设置项目负责人", notes = "设置项目负责人")
    public boolean setMaster(@RequestBody @Validated InitMemberModel memberModel) throws OwnerException {
        return initMemberService.setMaster(getUserInfo(), memberModel);
    }

    @PostMapping("/setEntryDate")
    @PermissionRequired(perms = "project:report:member:edit")
    @SystemLog(description = "设置项目成员加入日期", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "设置项目成员加入日期", notes = "设置项目成员加入日期")
    public boolean setEntryDate(@RequestBody @Validated InitUsedRoleModel model) throws OwnerException {
        return initMemberService.setEntryDate(getUserInfo(), model);
    }

    @SystemLog(description = "检查项目成员是否已经归集研发费用")
    @GetMapping("/checkRdUsed")
    @ApiOperation(value = "检查项目成员是否已经归集研发费用", notes = "检查项目成员是否已经归集研发费用")
    public String checkRdUsed(InitUsedRoleModel model) {
        return initMemberService.checkRdUsed(model);
    }

    /**
     * 获取项目组成员名单
     * @param projectId
     * @param memberIds 已存在的项目成员id
     * @param addData true 添加项目成员 false 查询项目id
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "获取项目组成员名单")
    @GetMapping("/getStaffList")
    @ApiOperation(value = "获取项目组成员名单", notes = "获取项目组成员名单")
    public List<InitMemberListModel> getStaffList( Integer projectId, Integer[] memberIds, Boolean addData,Integer pDocFileId) throws OwnerException {
        return initMemberService.getStaffInfoList(projectId, getUserInfo().getCompanyId(),memberIds,addData,pDocFileId);
    }


    /**
     * @param query
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getAllStaff")
    @ApiOperation(value = "获取可添加的项目成员列表", notes = "获取可添加的项目成员列表")
    public PageModel<List<InitMemberModel>> getAllStaff(QueryInitMemberModel query) throws OwnerException {
        return initMemberService.getAllStaff(getUserInfo().getCompanyId(), query);
    }

    @PostMapping("/importMembers")
    @PermissionRequired(perms = "project:report:member:edit")
    @SystemLog(description = "根据年引入项目成员", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "根据年引入项目成员", notes = "根据年引入项目成员")
    public boolean importMembers(@RequestBody @Validated ImportProjectInfoModel model) throws OwnerException {
        return initMemberService.importMembers(getUserInfo(), model);
    }

    @SystemLog(description = "获取可引入项目成员的年份列表")
    @GetMapping("/getMembersYear")
    @ApiOperation(value = "获取可引入项目成员的年份列表", notes = "获取可引入项目成员的年份列表")
    public List<Integer> getMembersYear(Integer year,Integer projectId){
        return initMemberService.getMembersYear(year,projectId);
    }

}
