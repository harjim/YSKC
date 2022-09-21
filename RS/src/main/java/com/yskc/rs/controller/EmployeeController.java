package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.DateUtil;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.EmployeeEntity;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.models.KeysAndIdsModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.employee.EmployeeModel;
import com.yskc.rs.models.employee.EmployeeSelectTableModel;
import com.yskc.rs.models.employee.EmployeeTermModel;
import com.yskc.rs.models.excel.EmployeeExcel;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.models.init.member.QueryInitMemberModel;
import com.yskc.rs.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * 花名册类接口
 *
 * @author huronghua
 */
@Api(tags = "花名册类接口", value = "花名册类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/employee")
public class EmployeeController extends BaseController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RsConfig rsConfig;

    /**
     * @param termModel
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryEmployeeList")
    @PermissionRequired(perms = "company:employee:search")
    @ApiOperation(value = "获取花名册", notes = "获取花名册", response = List.class)
    public PageModel<List<EmployeeModel>> queryEmployeeList(EmployeeTermModel termModel) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        if (termModel.getEtypes() == null || termModel.getEtypes().length <= 0) {
            termModel.setEtypes(null);
        }
        if (termModel.getEdate() != null) {
            Date beginDate = DateUtil.getMonthFirstDay(termModel.getEdate());
            Date endDate = DateUtil.getMonthLastDay(termModel.getEdate());
            termModel.setBeginEdate(beginDate);
            termModel.setEndEdate(endDate);
        }
        return employeeService.queryEmployeeList(userInfo.getCompanyId(), termModel);
    }

    @GetMapping("/getList")
    @PermissionRequired(perms = "company:employee:search")
    @ApiOperation(value = "获取花名册", notes = "获取花名册", response = List.class)
    public List<EmployeeModel> getEmployeeList() throws OwnerException {
        return employeeService.getEmployeeList(getUserInfo().getCompanyId());
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "删除花名册", mode = SystemLog.SAVE_DB)
    @PostMapping("/delEmployee")
    @PermissionRequired(perms = "company:employee:del")
    @ApiOperation(value = "删除花名册", notes = "删除花名册", response = boolean.class)
    public boolean delEmployee(@RequestBody @Validated EmployeeModel model) throws OwnerException {
        return employeeService.delEy(getUserInfo().getCompanyId(), model.getId());
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "更新花名册", mode = SystemLog.SAVE_DB)
    @PostMapping("/updateEmployee")
    @PermissionRequired(perms = "company:employee:edit")
    @ApiOperation(value = "更新花名册", notes = "更新花名册", response = boolean.class)
    public boolean updateEmployee(@RequestBody @Validated EmployeeModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return employeeService.updateEy(userInfo, model);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "添加花名册", mode = SystemLog.SAVE_DB)
    @PostMapping("/addEmployee")
    @PermissionRequired(perms = "company:employee:add")
    @ApiOperation(value = "添加花名册", notes = "添加花名册", response = boolean.class)
    public boolean addEmployee(@RequestBody @Validated EmployeeModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return employeeService.addEy(userInfo, model);
    }

    /**
     * @param file
     * @param tableField
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "导入花名册", mode = SystemLog.SAVE_DB)
    @PostMapping("/importEmployee")
    @PermissionRequired(perms = "company:employee:improt")
    @ApiOperation(value = "导入花名册", notes = "导入花名册", response = String.class)
    public String importEmployee(@RequestParam("file") MultipartFile file, TableField tableField) throws OwnerException {
        UserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                info.getCompanyId());
        ExcelResult<EmployeeExcel> excelResult = excelService.getExcelResult(tempPath, file, EmployeeExcel.class, tableField);
        employeeService.importEmployee(info, excelResult.getData());
        return excelResult.getMsg();
    }

    /**
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getAllList")
    @ApiOperation(value = "获取所有员工", notes = "获取所有员工", response = List.class)
    public List<EmployeeModel> getAllList() throws OwnerException {
        return employeeService.getAllList(getUserInfo().getCompanyId());
    }

    /**
     * 根据员工考勤获取员工列表
     *
     * @param ename
     * @param month
     * @param pageNo
     * @param pageSize
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getEmployeeListByMonth")
    @ApiOperation(value = "根据员工考勤获取员工列表", notes = "根据员工考勤获取员工列表", response = PageModel.class)
    public PageModel<List<EmployeeModel>> getEmployeeListByMonth(String ename, Date month, int pageNo, int pageSize) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return employeeService.getEmployeeListByMonth(userInfo.getCompanyId(), ename, month, pageNo, pageSize);
    }


    /**
     * @param termModel
     * @return
     * @throws OwnerException
     */
    @GetMapping("/employeEexport")
    @PermissionRequired(perms = "company:employee:export")
    @ApiOperation(value = "导出花名册", notes = "导出花名册")
    @SystemLog(description = "导出花名册")
    public List<EmployeeExcel> employeeExport(EmployeeTermModel termModel) throws OwnerException {
        if (termModel.getEtypes() == null || termModel.getEtypes().length <= 0) {
            termModel.setEtypes(null);
        }
        if (termModel.getEdate() != null) {
            Date beginDate = com.yskc.common.utils.DateUtil.getMonthFirstDay(termModel.getEdate());
            Date endDate = com.yskc.common.utils.DateUtil.getMonthLastDay(termModel.getEdate());
            termModel.setBeginEdate(beginDate);
            termModel.setEndEdate(endDate);
        }
        return employeeService.exportEmployeeModel(getUserInfo().getCompanyId(), termModel);
    }

    /**
     * @param modelList
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "批量删除花名册", mode = SystemLog.SAVE_DB)
    @PostMapping("/delEmployeeBatch")
    @PermissionRequired(perms = "company:employee:del")
    @ApiOperation(value = "批量删除花名册", notes = "批量删除花名册", response = boolean.class)
    public boolean delEmployeeBatch(@RequestBody @Validated List<EmployeeModel> modelList) throws OwnerException {
        return employeeService.delEmployeeBatch(getUserInfo().getCompanyId(), modelList);
    }

    /**
     * @param query
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getSelectEmployeeList")
    @ApiOperation(value = "人员下拉表格", notes = "人员下拉表格")
    public PageModel<List<EmployeeSelectTableModel>> getSelectEmployeeList(QueryInitMemberModel query) throws OwnerException {
        return employeeService.getSelectEmployeeList(getUserInfo(), query);
    }


    /**
     * @param termModel
     * @return
     * @throws OwnerException
     */
    @GetMapping("/selectEmployeeList")
    @ApiOperation(value = "获取花名册", notes = "获取花名册", response = List.class)
    public PageModel<List<EmployeeModel>> selectEmployeeList(EmployeeTermModel termModel) throws OwnerException {
        return employeeService.selectEmployeeList(getUserInfo().getCompanyId(), termModel);

    }

    /**
     * 添加评审人员时获取花名册
     *
     * @param termModel
     * @return
     * @throws OwnerException
     */
    @GetMapping("/selectEmployees")
    @ApiOperation(value = "获取花名册", notes = "获取花名册", response = List.class)
    public PageModel<List<EmployeeModel>> selectEmployees(EmployeeTermModel termModel) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return employeeService.selectEmployees(userInfo.getCompanyId(), termModel);

    }

    /**
     * 添加科技管理人员时获取花名册
     *
     * @param termModel
     * @return
     * @throws OwnerException
     */
    @GetMapping("/selectStEmployees")
    @ApiOperation(value = "获取花名册", notes = "获取花名册", response = List.class)
    public PageModel<List<EmployeeModel>> selectStEmployees(EmployeeTermModel termModel) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return employeeService.selectStEmployees(userInfo.getCompanyId(), termModel);

    }

    /**
     * @param enumber
     * @param id
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "检查工号是否重复")
    @GetMapping("/checkEnumber")
    @ApiOperation(value = "检查工号是否重复", notes = "检查工号是否重复")
    public Boolean checkEnumber(String enumber, Integer id) throws OwnerException {
        return employeeService.checkEnumber(getUserInfo().getCompanyId(), enumber, id);
    }

    @SystemLog(description = "解绑研发考勤", mode = SystemLog.SAVE_DB)
    @PostMapping("/unbindAtt")
    @ApiOperation(value = "解绑研发考勤", notes = "解绑研发考勤")
    @PermissionRequired(perms = "company:employee:unbindAtt")
    public Boolean unbindAtt(@RequestBody KeysAndIdsModel model) throws OwnerException {
        return employeeService.unbindAtt(model.getIds());
    }

    @SystemLog(description = "禁用研发考勤", mode = SystemLog.SAVE_DB)
    @PostMapping("/disabledAtt")
    @ApiOperation(value = "禁用研发考勤", notes = "禁用研发考勤")
    @PermissionRequired(perms = "company:employee:disabledAtt")
    public Boolean disabledAtt(@RequestBody KeysAndIdsModel model) throws OwnerException {
        return employeeService.toggleAtt(model.getIds(), true, getUserInfo());
    }

    @SystemLog(description = "启用研发考勤", mode = SystemLog.SAVE_DB)
    @PostMapping("/enabledAtt")
    @ApiOperation(value = "启用研发考勤", notes = "启用研发考勤")
    @PermissionRequired(perms = "company:employee:enabledAtt")
    public Boolean enabledAtt(@RequestBody KeysAndIdsModel model) throws OwnerException {
        return employeeService.toggleAtt(model.getIds(), false, getUserInfo());
    }

    @SystemLog(description = "设置角色类型", mode = SystemLog.SAVE_DB)
    @PostMapping("/setRoleType")
    @ApiOperation(value = "设置角色类型", notes = "设置角色类型")
    @PermissionRequired(perms = "company:employee:setRoleType")
    public Boolean setRoleType(@RequestBody KeysAndIdsModel model) throws OwnerException {
        return employeeService.setRoleType(model, getUserInfo());
    }

    @SystemLog(description = "上传亲笔签名", mode = SystemLog.SAVE_DB)
    @PostMapping("/uploadAutograph")
    @ApiOperation(value = "上传亲笔签名", notes = "上传亲笔签名")
    @PermissionRequired(perms = "company:employee:uploadAutograph")
    public Boolean uploadAutograph(@RequestParam("file") MultipartFile file, String enumber, Integer id) throws OwnerException, IOException {
        UserInfo info = getUserInfo();
        String[] fileNames = file.getOriginalFilename().split("\\.");
        fileNames[0] = enumber + System.currentTimeMillis();
        String fileName = String.join(".", fileNames);
        String tempPath = MessageFormat.format("/autograph/{0,number,#}", info.getCompanyId());
        String autographUrl = Constant.IMAGES_DIR + tempPath + "/" + fileName;
        File transferFile = new File(rsConfig.getUploadConfig().getResourcePath() + autographUrl);
        if (!transferFile.getParentFile().exists()) {
            transferFile.getParentFile().mkdirs();
        }
        file.transferTo(transferFile);
        return employeeService.saveAutographUrl(id, autographUrl, getUserInfo());
    }


    @SystemLog(description = "删除亲笔签名", mode = SystemLog.SAVE_DB)
    @PostMapping("/deleteAutograph")
    @ApiOperation(value = "删除亲笔签名", notes = "删除亲笔签名")
    @PermissionRequired(perms = "company:employee:uploadAutograph")
    public Boolean deleteAutograph(@RequestBody EmployeeEntity employee) throws OwnerException {
        return employeeService.saveAutographUrl(employee.getId(), null, getUserInfo());
    }
}
