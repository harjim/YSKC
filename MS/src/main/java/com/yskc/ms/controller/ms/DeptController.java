package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.dept.DeptModel;
import com.yskc.ms.models.dept.DeptTree;
import com.yskc.ms.models.dept.DeptUserInfo;
import com.yskc.ms.models.dept.InsertUserDeptModel;
import com.yskc.ms.models.user.QueryUserListModel;
import com.yskc.ms.service.ms.DeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门类接口
 *
 * @author huronghua
 */
@Api(tags = "部门类接口", value = "部门类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
    @RequestMapping("/api/dept")
public class DeptController extends BaseController {

    @Autowired
    private DeptService deptService;

    @GetMapping("/tree")
    @ApiOperation(value = "部门树", notes = "部门树", response = List.class)
    public List<DeptTree> tree() {
        return DeptTree.getTree(deptService.queryAll());
    }

    @GetMapping("/getUser")
    @PermissionRequired(perms = "sys:user:search")
    @ApiOperation(value = "获取部门用户", notes = "获取部门用户", response = List.class)
    public PageModel<List<DeptUserInfo>> getUser(QueryUserListModel query) throws OwnerException {
        return deptService.getUser(query,getDataPerm());
    }

    @GetMapping("/delUser")
    @PermissionRequired(perms = "sys:user:del")
    @SystemLog(description = "删除用户", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "删除用户", notes = "删除用户", response = Boolean.class)
    public Boolean delUser(Integer id) throws OwnerException {
        UserInfo info = getUserInfo();
        return deptService.delUser(info.getId(), id);
    }


    @GetMapping("/queryDeptList")
    @PermissionRequired(perms = "sys:dept:search")
    @ApiOperation(value = "获取组织架构信息", notes = "获取组织架构信息", response = String.class)
    public List<DeptModel> queryDeptList(String deptName) throws OwnerException {
        return deptService.queryDept(deptName);
    }

    @PostMapping("/addDept")
    @PermissionRequired(perms = "sys:dept:add")
    @SystemLog(description = "添加部门", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "添加部门", notes = "添加部门", response = boolean.class)
    public boolean addDept(@RequestBody @Validated DeptModel model) throws OwnerException {
        return deptService.addDept(model);
    }

    @PostMapping("/delDept")
    @PermissionRequired(perms = "sys:dept:del")
    @SystemLog(description = "删除部门", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "删除部门", notes = "删除部门", response = boolean.class)
    public boolean delDept(@RequestBody @Validated DeptModel model) throws OwnerException {
        return deptService.delDept(model);
    }

    @PostMapping("/updateDept")
    @PermissionRequired(perms = "sys:dept:edit")
    @SystemLog(description = "更新部门", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "更新部门", notes = "更新部门", response = boolean.class)
    public boolean updateDept(@RequestBody @Validated DeptModel model) throws OwnerException {
        return deptService.updateDept(model);
    }

    @GetMapping("/selsynDept")
    @PermissionRequired(perms = "sys:user:synchronization,sys:dept:synchronization")
    @ApiOperation(value = "同步部门", notes = "同步部门", response = Boolean.class)
    public Boolean selsynDept() throws OwnerException {
        UserInfo userInfo = this.getUserInfo();
        return deptService.synDingUser(userInfo);
    }


    @GetMapping("/getUserDataByDeptId")
    @ApiOperation(value = "根据部门Id查询该部门下的用户", notes = "根据部门Id查询该部门下的用户")
    public PageModel<List<DeptUserInfo>> getUserDataByDeptId(int pageNo, int pageSize, String realName, Integer deptId) throws OwnerException {
        return deptService.getUserDataByDeptId(pageNo, pageSize, realName, deptId);
    }

    @GetMapping("/getSelectUserData")
    @ApiOperation(value = "查询不存在当前部门下的用户", notes = "查询不存在当前部门下的用户")
    public PageModel<List<DeptUserInfo>> getSelectUserData(int pageNo, int pageSize, String realName, Integer deptId,String fullPath) throws OwnerException {
        return deptService.getSelectUserData(pageNo, pageSize, realName, deptId,fullPath);
    }

    @PostMapping("/deptAddUsers")
    @SystemLog(description = "部门批量添加用户", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "部门批量添加用户", notes = "部门批量添加用户", response = boolean.class)
    public boolean deptAddUsers(@RequestBody @Validated InsertUserDeptModel model) throws OwnerException {
        return deptService.deptAddUser( model);
    }

    @PostMapping("/deptAddPrincipal")
    @SystemLog(description = "部门批量添加负责人", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "部门批量添加负责人", notes = "部门批量添加负责人", response = boolean.class)
    public boolean deptAddPrincipal(@RequestBody @Validated InsertUserDeptModel model) throws OwnerException {
        return deptService.deptAddPrincipal( model,model.getAddTo());
    }

    @PostMapping("/deleteDeptUser")
    @ApiOperation(value = "批量删除部门下的用户", notes = "批量删除部门下的用户", response = boolean.class)
    public boolean deleteDeptUser(@RequestBody @Validated List<DeptUserInfo> list) throws OwnerException {
        return deptService.deleteDeptUser(list);
    }

}
