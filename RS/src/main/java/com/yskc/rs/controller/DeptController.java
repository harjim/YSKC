package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.dept.DeptModel;
import com.yskc.rs.models.dept.DeptTree;
import com.yskc.rs.models.dept.DeptUserInfo;
import com.yskc.rs.models.excel.DeptExcel;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.service.DeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
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
    @Autowired
    private RsConfig rsConfig;

    /**
     * 部门树
     *
     * @return
     * @throws OwnerException
     */
    @GetMapping("/tree")
    @ApiOperation(value = "部门树", notes = "部门树", response = List.class)
    public List<DeptTree> tree() throws OwnerException {
        return DeptTree.getTree(deptService.queryAll(getUserInfo()));
    }

    /**
     * 获取组织架构
     *
     * @param deptId
     * @param realName
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("/getUser")
    @ApiOperation(value = "获取组织架构", notes = "获取组织架构", response = List.class)
    public PageModel<List<DeptUserInfo>> getUser(Integer deptId, String realName, int pageNo, int pageSize) {
        return deptService.getUser(deptId, realName, pageNo, pageSize);
    }

    /**
     * 删除组织架构
     *
     * @param id
     * @return
     */
    @SystemLog(description = "删除组织架构", mode = SystemLog.SAVE_DB)
    @GetMapping("/delUser")
    @ApiOperation(value = "删除组织架构", notes = "删除组织架构", response = Boolean.class)
    public Boolean delUser(Integer id) {
        return deptService.delUser(id);
    }

    /**
     * 获取组织架构信息
     *
     * @param deptName
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryDeptList")
    @PermissionRequired(perms = "company:org:search")
    @ApiOperation(value = "获取组织架构信息", notes = "获取组织架构信息", response = String.class)
    public List<DeptModel> queryDeptList(String deptName) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return deptService.queryDept(userInfo.getCompanyId(), deptName);
    }

    /**
     * 添加下级部门
     *
     * @param model
     * @return
     * @throws OwnerException
     */
    @PostMapping("/addDept")
    @SystemLog(description = "添加部门", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "company:org:add")
    @ApiOperation(value = "添加部门", notes = "添加部门", response = boolean.class)
    public boolean addDept(@RequestBody @Validated DeptModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return deptService.addDept(userInfo, model);
    }

    /**
     * 删除部门
     *
     * @param model
     * @return
     * @throws OwnerException
     */
    @PostMapping("/delDept")
    @SystemLog(description = "删除部门", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "company:org:del")
    @ApiOperation(value = "删除部门", notes = "删除部门", response = boolean.class)
    public boolean delDept(@RequestBody @Validated DeptModel model) throws OwnerException {
        return deptService.delDept(getUserInfo().getCompanyId(), model.getId());
    }

    /**
     * 更新部门
     *
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "更新部门", mode = SystemLog.SAVE_DB)
    @PostMapping("/updateDept")
    @PermissionRequired(perms = "company:org:edit")
    @ApiOperation(value = "更新部门", notes = "更新部门", response = boolean.class)
    public boolean updateDept(@RequestBody @Validated DeptModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return deptService.updateDept(userInfo, model);
    }

    /**
     * @param file
     * @param tableField
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "导入组织架构", mode = SystemLog.SAVE_DB)
    @PostMapping("/importDept")
    @ApiOperation(value = "导入组织架构", notes = "导入组织架构", response = String.class)
    public String importProject(@RequestParam("file") MultipartFile file, TableField tableField) throws OwnerException {
        UserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                info.getCompanyId());
        ExcelResult<DeptExcel> excelResult = excelService.getExcelResult(tempPath, file, DeptExcel.class, tableField);

        return deptService.importDept(info, excelResult.getData());
    }
}
