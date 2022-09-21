package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.DateUtil;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.data.DataAttendanceEntity;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.models.BatchAttendanceModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.attendance.DataAttendanceModel;
import com.yskc.rs.models.excel.AttendanceExcel;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.service.AttendanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * 考勤类接口
 * @author huronghua
 */
@Api(tags = "考勤类接口",value = "考勤类接口")
@CrossOrigin(origins = "*",allowCredentials="true",allowedHeaders = "",methods = {})
@RestController
@RequestMapping("/api/attendance")
public class AttendanceController extends BaseController {
    @Autowired
    private RsConfig rsConfig;
    @Autowired
    private AttendanceService attendanceService;

    /**
     * 获取员工考勤
     *
     * @param pageNo
     * @param pageSize
     * @param ename
     * @param month
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getList")
    @SystemLog(description = "获取员工考勤记录")
    @ApiOperation(value = "获取员工考勤", notes = "获取员工考勤")
    @PermissionRequired(perms = "dataentry:attendance:search")
    public PageModel<List<DataAttendanceModel>> getList(int pageNo, int pageSize, String ename, Date month) throws OwnerException {
        return attendanceService.getAttendanceList(pageNo, pageSize, getUserInfo().getCompanyId(), ename, month);
    }

    /**
     * 更新员工考勤
     *
     * @param attendance
     * @return
     */
    @PostMapping("/update")
    @ApiOperation(value = "编辑保存员工考勤", notes = "编辑保存员工考勤")
    @SystemLog(description = "编辑保存员工考勤", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "dataentry:attendance:edit")
    public boolean update(@RequestBody @Validated DataAttendanceEntity attendance) {
        return attendanceService.update(attendance);
    }

    /**
     * 删除员工考勤信息
     *
     * @param attendance
     * @return
     */
    @PostMapping("/del")
    @ApiOperation(value = "删除员工考勤", notes = "删除员工考勤")
    @SystemLog(description = "删除员工考勤", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "dataentry:attendance:del")
    public boolean delete(@RequestBody @Validated DataAttendanceEntity attendance) {
        return attendanceService.delete(attendance);
    }

    /**
     * 添加员工考勤记录
     *
     * @param attendance
     * @return
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加员工考勤", notes = "添加员工考勤")
    @SystemLog(description = "添加员工考勤", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "dataentry:attendance:add")
    public boolean add(@RequestBody @Validated DataAttendanceEntity attendance) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        attendance.setCreatorId(userInfo.getId());
        Date month = DateUtil.getMonthFirstDay(attendance.getMonth());
        attendance.setMonth(month);
        attendance.setAttData("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0");
        attendance.setCompanyId(userInfo.getCompanyId());
        attendance.setCreateTime(new Date());
        return attendanceService.add(attendance);
    }

    /**
     * 添加员工考勤
     * @param model
     * @return
     * @throws OwnerException
     */
    @PostMapping("/addList")
    @SystemLog(description = "添加员工考勤", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "添加员工考勤", notes = "添加员工考勤")
    public boolean addList(@RequestBody @Validated BatchAttendanceModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return attendanceService.addList(userInfo,model);
    }

    @PostMapping("/import")
    @ApiOperation(value = "导入员工考勤", notes = "导入员工考勤", response = boolean.class)
    @PermissionRequired(perms = "dataentry:attendance:import")
    @SystemLog(description = "导入员工考勤", mode = SystemLog.SAVE_DB)
    public String importInfo(@RequestParam("file") MultipartFile file, TableField tableField) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                userInfo.getCompanyId());
        ExcelResult<AttendanceExcel> attendanceExcelResult = excelService.getExcelResult(tempPath, file, AttendanceExcel.class, tableField);
        attendanceService.importInfo(userInfo, attendanceExcelResult.getData());
        return attendanceExcelResult.getMsg();
    }

}
