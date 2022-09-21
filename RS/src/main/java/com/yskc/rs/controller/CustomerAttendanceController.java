package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.CustomerAttendanceEntity;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.customerattendance.CustomerAttendanceModel;
import com.yskc.rs.models.customerattendance.QueryCustomerAttendanceModel;
import com.yskc.rs.models.excel.CustomerAttendanceExcel;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.service.CustomerAttendanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 * @CreateTime: 2019-12-06 09:36
 * @Description: 员工考勤接口
 */
@RestController
@Api(tags = "员工考勤接口", value = "员工考勤接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RequestMapping("/api/customerAttendance")
public class CustomerAttendanceController extends BaseController {

    @Autowired
    private CustomerAttendanceService customerAttendanceService;

    @Autowired
    private RsConfig rsConfig;

    @GetMapping("/getList")
    @PermissionRequired(perms = "dataentry:customerAttendance:search")
    @ApiOperation(notes = "获取考勤列表", value = "获取考勤列表")
    public PageModel<List<CustomerAttendanceModel>> getList(QueryCustomerAttendanceModel query) throws OwnerException {
        return customerAttendanceService.getList(getUserInfo().getCompanyId(), query);
    }

    @SystemLog(description = "添加人员考勤",mode = SystemLog.SAVE_DB)
    @PostMapping("/add")
    @PermissionRequired(perms = "dataentry:customerAttendance:add")
    @ApiOperation(notes = "添加人员考勤", value = "添加人员考勤")
    public boolean add(@RequestBody @Validated CustomerAttendanceEntity entity) throws OwnerException {
        return customerAttendanceService.add(getUserInfo(), entity);
    }

    @SystemLog(description = "更新人员考勤",mode = SystemLog.SAVE_DB)
    @PostMapping("/update")
    @PermissionRequired(perms = "dataentry:customerAttendance:edit")
    @ApiOperation(notes = "更新人员考勤", value = "更新人员考勤")
    public boolean update(@RequestBody @Validated CustomerAttendanceEntity entity) throws OwnerException {
        return customerAttendanceService.update(getUserInfo(), entity);
    }

    @SystemLog(description = "删除人员考勤",mode = SystemLog.SAVE_DB)
    @PostMapping("/delete")
    @PermissionRequired(perms = "dataentry:customerAttendance:del")
    @ApiOperation(notes = "删除人员考勤", value = "删除人员考勤")
    public boolean delete(@RequestBody @Validated CustomerAttendanceEntity entity) {
        return customerAttendanceService.delete(entity.getId());
    }

    @SystemLog(description = "批量删除人员考勤",mode = SystemLog.SAVE_DB)
    @PostMapping("/deleteList")
    @PermissionRequired(perms = "dataentry:customerAttendance:del")
    @ApiOperation(notes = "批量删除人员考勤", value = "批量删除人员考勤")
    public boolean deleteList(@RequestBody @Validated List<CustomerAttendanceEntity> list) {
        if (CollectionUtils.isEmpty(list)) {
            return true;
        }
        List<Integer> ids = new ArrayList<>();
        list.forEach(item -> ids.add(item.getId()));
        return customerAttendanceService.deleteList(ids);
    }

    @SystemLog(description = "导入人员考勤",mode = SystemLog.SAVE_DB)
    @PostMapping("/importAttendance")
    @PermissionRequired(perms = "dataentry:customerAttendance:import")
    @ApiOperation(notes = "导入人员考勤", value = "导入人员考勤")
    public String importAttendance(@RequestParam("file") MultipartFile file, TableField tableField) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                userInfo.getCompanyId());
        ExcelResult<CustomerAttendanceExcel> customerAttendanceExcels = excelService.getExcelResult(tempPath, file, CustomerAttendanceExcel.class, tableField);
        customerAttendanceService.importAttendance(userInfo, customerAttendanceExcels.getData());
        return customerAttendanceExcels.msg;
    }

    @GetMapping("/exportAttendance")
    @PermissionRequired(perms = "dataentry:customerAttendance:export")
    @ApiOperation(notes = "导出考勤", value = "导出考勤")
    @SystemLog(description = "导出考勤",mode = SystemLog.SAVE_DB)
    public List<CustomerAttendanceModel> exportAttendance(QueryCustomerAttendanceModel query) throws OwnerException {
        return customerAttendanceService.exportAttendance(getUserInfo().getCompanyId(), query);
    }
}
