package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.DateUtil;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.entity.project.ProjectAttendance;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.attendance.AttendanceModel;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.models.excel.ProjectAttendanceExcel;
import com.yskc.rs.models.projectattendance.*;
import com.yskc.rs.service.ProjectAttendanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.*;

/**
 * 研发人员类接口
 *
 * @author huronghua
 */
@Api(tags = "研发人员考勤类接口", value = "研发人员考勤类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/projectAttendance")
public class ProjectAttendanceController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ProjectAttendanceService projectAttendanceService;
    @Autowired
    private RsConfig rsConfig;

    @GetMapping("/getHourList")
    @PermissionRequired(perms = "project:data:view")
    @ApiOperation(value = "获取人员研发考勤工时列表", notes = "获取人员研发考勤工时列表")
    public PageModel<List<ProjectHourAttendance>> getHourList(QueryBatchEmployee query) throws OwnerException {
        return projectAttendanceService.getHourList(getUserInfo().getCompanyId(), query);
    }

    @GetMapping("/getTimeList")
    @PermissionRequired(perms = "project:data:view")
    @ApiOperation(value = "获取人员研发考勤时间列表", notes = "获取人员研发考勤时间列表")
    public PageModel<List<TimeAttendanceModel>> getTimeList(QueryBatchEmployee query) {
        return projectAttendanceService.getTimeList(query);
    }

    @SystemLog(description = "刷新研发人员考勤", mode = SystemLog.SAVE_DB)
    @PostMapping("/refresh")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "刷新研发人员考勤", notes = "刷新研发人员考勤")
    public Boolean refresh(@RequestBody @Validated RefreshAttendance refresh) throws OwnerException {
        return projectAttendanceService.refresh(getUserInfo(), refresh);
    }

    @SystemLog(description = "保存研发人员考勤", mode = SystemLog.SAVE_DB)
    @PostMapping("/saveData")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "保存研发人员考勤", notes = "保存研发人员考勤")
    public Boolean saveData(@RequestBody @Validated BatchAttendanceUsedModel batch) throws OwnerException {
        return projectAttendanceService.saveData(getUserInfo(), batch, false);
    }

    @GetMapping("/exportHourData")
    @PermissionRequired(perms = "project:data:view")
    @SystemLog(description = "导出研发考勤工时数据", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "导出研发考勤工时数据", notes = "导出研发考勤工时数据")
    public void exportHourData(QueryBatchEmployee query) throws Exception {
        UserInfo info = getUserInfo();
        try (OutputStream out = outGeneralFile(info.getCompanyName() + ".xlsx")) {
            projectAttendanceService.exportHourData(query, info, out);
            out.flush();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new OwnerException("导出失败," + ex.getMessage());
        }
    }

    @SystemLog(description = "删除研发考勤", mode = SystemLog.SAVE_DB)
    @PostMapping("/delData")
    @PermissionRequired(perms = "project:rdHour:del")
    @ApiOperation(value = "删除研发考勤", notes = "删除研发考勤")
    public Boolean delData(@RequestBody @Validated List<AttendanceModel> model) throws OwnerException {
        return projectAttendanceService.delData(getUserInfo(), model);
    }

    @GetMapping("/exportTimeData")
    @PermissionRequired(perms = "project:data:view")
    @SystemLog(description = "导出研发考勤时间数据", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "导出研发考勤时间数据", notes = "导出研发考勤时间数据")
    public List<TimeAttendanceModel> exportTimeData(QueryBatchEmployee query) {
        return projectAttendanceService.exportTimeData(query);
    }

    @GetMapping("/getAllRdHour")
    @PermissionRequired(perms = "project:rdHour:search")
    @ApiOperation(value = "获取所有项目研发工时", notes = "获取所有项目研发工时")
    public PageModel<List<ProjectAllRdHourModel>> getAllRdHour(QueryAllRdHourModel query) throws OwnerException {
        return projectAttendanceService.getAllRdHour(query, getUserInfo().getCompanyId());
    }

    @GetMapping("/getAllRdTime")
    @PermissionRequired(perms = "project:rdHour:search")
    @ApiOperation(value = "获取所有项目研发时间", notes = "获取所有项目研发时间")
    public PageModel<List<ProjectAllRdTimeModel>> getAllRdTime(QueryAllRdHourModel query) throws OwnerException {
        return projectAttendanceService.getAllRdTime(query, getUserInfo().getCompanyId());
    }


    @GetMapping("/exportAllRdHour")
    @SystemLog(description = "导出所有项目研发工时", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "project:rdHour:export")
    @ApiOperation(value = "导出所有项目研发工时", notes = "导出所有项目研发工时")
    public List<ProjectAllRdHourModel> exportAllRdHour(QueryAllRdHourModel query) throws OwnerException {
        /*String templateName = "研发设备列表模板.xls";
        String fileName = "研发设备列表数据.xls";
        String path = rsConfig.getUploadConfig().getResourcePath() + Constant.TEMPLATE_DIR + templateName;
        try (OutputStream out = outGeneralFile(fileName)) {
            projectAttendanceService.exportAllRdHour(query, getUserInfo().getCompanyId(),out,path);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw new OwnerException("导出失败");
        }*/

        return projectAttendanceService.exportAllRdHour(query, getUserInfo().getCompanyId());
    }

    @GetMapping("/exportAllRdTime")
    @PermissionRequired(perms = "project:rdHour:export")
    @SystemLog(description = "导出所有项目研发时间", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "导出所有项目研发时间", notes = "导出所有项目研发时间")
    public List<ProjectAllRdTimeModel> exportAllRdTime(QueryAllRdHourModel query) throws OwnerException {
        return projectAttendanceService.exportAllRdTime(query, getUserInfo().getCompanyId());
    }

    @GetMapping("/hasAttendance")
    @ApiOperation(value = "是否存在考勤记录", notes = "是否存在考勤记录")
    public Boolean hasAttendance() throws OwnerException {
        return projectAttendanceService.hasAttendance(getUserInfo().getCompanyId());
    }

    @PostMapping("/editAttendance")
    @ApiOperation(value = "编辑研发考勤", notes = "编辑研发考勤")
    @SystemLog(description = "编辑研发考勤", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "project:rdHour:edit")
    public Boolean editAttendance(@RequestBody @Validated ProjectAttendance attendance) throws OwnerException {
        return projectAttendanceService.editAttendance(getUserInfo(), attendance);
    }

    @SystemLog(description = "导入研发考勤记录", mode = SystemLog.SAVE_DB)
    @PostMapping("/importRdAttendance")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "导入研发考勤记录", notes = "导入研发考勤记录")
    public String importRdAttendance(@RequestParam("file") MultipartFile file, Date month, TableField tableField) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                userInfo.getCompanyId());
        ExcelResult<ProjectAttendanceExcel> excelResult = excelService.getExcelResult(tempPath, file, ProjectAttendanceExcel.class, tableField);
        projectAttendanceService.importRdAttendance(userInfo, excelResult.getData(), month);
        return excelResult.msg;
    }
}
