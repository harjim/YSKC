package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.PageResult;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.models.BatchDeleteModel;
import com.yskc.rs.models.KeysAndIdsModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.employee.EmployeeTermModel;
import com.yskc.rs.models.employee.RdEmployeeModel;
import com.yskc.rs.models.excel.EmployeeExcel;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.models.excel.ImportEmployeeExcel;
import com.yskc.rs.models.project.ProjectEmployeeInfo;
import com.yskc.rs.models.projectattendance.DataEmployeeQuery;
import com.yskc.rs.models.reviewCommittee.QueryReviewModel;
import com.yskc.rs.service.RdEmployeeService;
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
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: wangxing
 * @CreateTime: 2019-11-18 15:13
 * @Description: RdEmployeeController
 */
@Api(tags = "研发花名册接口", value = "研发花名册接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/rdEmployee")
public class RdEmployeeController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RdEmployeeService rdEmployeeService;
    @Autowired
    private RsConfig rsConfig;


    /**
     * @param termModel
     * @return
     * @throws OwnerException
     */
    @PermissionRequired(perms = "rdorg:rdEmployee:search")
    @GetMapping("/getRdEmployeeData")
    @ApiOperation(value = "获取研发花名册", notes = "获取研发花名册", response = List.class)
    public PageResult getRdEmployeeData(EmployeeTermModel termModel) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        if (termModel.getEtypes() == null || termModel.getEtypes().length <= 0) {
            termModel.setEtypes(null);
        }
        if (termModel.getEdate() != null) {
            Date beginDate = com.yskc.common.utils.DateUtil.getMonthFirstDay(termModel.getEdate());
            Date endDate = com.yskc.common.utils.DateUtil.getMonthLastDay(termModel.getEdate());
            termModel.setBeginEdate(beginDate);
            termModel.setEndEdate(endDate);
        }
        return rdEmployeeService.getRdEmployeeData(userInfo.getCompanyId(), termModel);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @PermissionRequired(perms = "rdorg:rdEmployee:add")
    @SystemLog(description = "添加研发花名册", mode = SystemLog.SAVE_DB)
    @PostMapping("/addRdEmployeeData")
    @ApiOperation(value = "添加研发花名册", notes = "添加研发花名册", response = boolean.class)
    public boolean addRdEmployeeData(@RequestBody @Validated RdEmployeeModel model) throws OwnerException {
        return rdEmployeeService.addRdEmployeeData(getUserInfo(), model);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @PermissionRequired(perms = "rdorg:rdEmployee:del")
    @SystemLog(description = "删除研发花名册", mode = SystemLog.SAVE_DB)
    @PostMapping("/delRdEmployee")
    @ApiOperation(value = "删除研发花名册", notes = "删除研发花名册", response = boolean.class)
    public boolean delRdEmployee(@RequestBody @Validated RdEmployeeModel model) throws OwnerException {
        return rdEmployeeService.delRdEmployee(getUserInfo(), model);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @PermissionRequired(perms = "rdorg:rdEmployee:setType")
    @SystemLog(description = "批量设置研发花名册人员类型", mode = SystemLog.SAVE_DB)
    @PostMapping("/updateRdEmployeeEtype")
    @ApiOperation(value = "批量设置研发花名册人员类型", notes = "批量设置研发花名册人员类型", response = boolean.class)
    public boolean updateRdEmployeeEtype(@RequestBody @Validated RdEmployeeModel model) throws OwnerException {
        return rdEmployeeService.updateRdEmployeeEtype(getUserInfo(), model);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @PermissionRequired(perms = "rdorg:rdEmployee:setDept")
    @SystemLog(description = "设置研发花名册研发部门", mode = SystemLog.SAVE_DB)
    @PostMapping("/updateEmployeeRdDept")
    @ApiOperation(value = "设置研发花名册研发部门", notes = "设置研发花名册研发部门", response = boolean.class)
    public boolean updateRdEmployeeRdDept(@RequestBody @Validated RdEmployeeModel model) throws OwnerException {
        return rdEmployeeService.updateRdEmployeeRdDept(getUserInfo(), model);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @PermissionRequired(perms = "rdorg:rdEmployee:setPosition")
    @SystemLog(description = "设置研发花名册职位", mode = SystemLog.SAVE_DB)
    @PostMapping("/setPosition")
    @ApiOperation(value = "设置研发花名册职位", notes = "设置研发花名册职位", response = boolean.class)
    public boolean setPosition(@RequestBody @Validated RdEmployeeModel model) throws OwnerException {
        return rdEmployeeService.setPosition(getUserInfo(), model);
    }


    @PermissionRequired(perms = "rdorg:rdEmployee:del")
    @SystemLog(description = "批量删除研发花名人员", mode = SystemLog.SAVE_DB)
    @PostMapping("/delRdEmployeeBatch")
    @ApiOperation(value = "批量删除研发花名人员", notes = "批量删除研发花名人员", response = boolean.class)
    public boolean delRdEmployeeBatch(@RequestBody @Validated BatchDeleteModel deleteModel) throws OwnerException {
        return rdEmployeeService.delRdEmployeeBatch(getUserInfo(), deleteModel);
    }

   /* @GetMapping("/deriveRdEmployee")
    @PermissionRequired(perms = "rdorg:rdEmployee:export")
    @SystemLog(description = "导出研发花名册", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "导出研发花名册", notes = "导出研发花名册")
    public List<EmployeeExcel> deriveRdEmployee(EmployeeTermModel termModel) throws OwnerException {
        if (termModel.getEtypes() == null || termModel.getEtypes().length <= 0) {
            termModel.setEtypes(null);
        }
        if (termModel.getEdate() != null) {
            Date beginDate = com.yskc.common.utils.DateUtil.getMonthFirstDay(termModel.getEdate());
            Date endDate = com.yskc.common.utils.DateUtil.getMonthLastDay(termModel.getEdate());
            termModel.setBeginEdate(beginDate);
            termModel.setEndEdate(endDate);
        }
        return rdEmployeeService.deriveRdEmployee(getUserInfo().getCompanyId(), termModel);
    }*/


    /**
     * 导出研发花名册
     * @param termModel
     * @throws OwnerException
     */
    @GetMapping("/getTableExport")
    @PermissionRequired(perms = "rdorg:rdEmployee:export")
    @SystemLog(description = "导出研发花名册", mode = SystemLog.SAVE_DB)
    @ApiOperation(notes = "导出研发花名册",value = "导出研发花名册")
    public void getTableExport(EmployeeTermModel termModel)throws OwnerException {
        UserInfo userInfo = getUserInfo();
        String templateName = "研发人员花名册模板.xls";
        String fileName = "研发花名册列表数据.xls";
        String path = rsConfig.getUploadConfig().getResourcePath() + Constant.TEMPLATE_DIR + templateName;
//        outGeneralFile(MessageFormat.format("{0}"+fileName, userInfo.getCompanyName()))
        try (OutputStream out = outGeneralFile(fileName)) {
            rdEmployeeService.getRdEmployeeExport(getUserInfo().getCompanyId(),termModel,out,path);
            out.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new OwnerException("导出失败");
        }
    }
    /**
     * 获取研发人员列表 //
     *
     * @param query
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getProjectEmployeeInfoList")
    @ApiOperation(value = "获取研发人员列表", notes = "获取研发人员列表", response = PageModel.class)
    public PageModel<List<ProjectEmployeeInfo>> getProjectEmployeeInfoList(DataEmployeeQuery query) throws OwnerException {
        return rdEmployeeService.getProjectEmployeeList(getUserInfo().getCompanyId(), query);
    }

    /**
     * 获取会议签到研发人员列表 //
     *
     * @param query
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getEmployeeInfoList")
    @ApiOperation(value = "获取会议签到研发人员列表", notes = "获取会议签到研发人员列表", response = PageModel.class)
    public PageModel<List<ProjectEmployeeInfo>> getEmployeeInfoList(QueryReviewModel query) throws OwnerException {
        return rdEmployeeService.getEmployeeList(getUserInfo().getCompanyId(), query);
    }

    /**
     * @param file
     * @param year
     * @param tableField
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "导入研发花名册", mode = SystemLog.SAVE_DB)
    @PostMapping("/importEmployee")
    @PermissionRequired(perms = "rdorg:rdEmployee:import")
    @ApiOperation(value = "导入花名册", notes = "导入花名册", response = String.class)
    public String importEmployee(@RequestParam("file") MultipartFile file, Integer year, TableField tableField) throws OwnerException {
        UserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                info.getCompanyId());
        ExcelResult<ImportEmployeeExcel> excelResult = excelService.getExcelResult(tempPath, file, ImportEmployeeExcel.class, tableField);
        return rdEmployeeService.importEmployee(info, excelResult.getData(), year);
    }

    @SystemLog(description = "分配研发花名册人员至项目", mode = SystemLog.SAVE_DB)
    @PostMapping("/setProjectMember")
    @PermissionRequired(perms = "rdorg:rdEmployee:setProject")
    @ApiOperation(value = "分配研发花名册人员至项目", notes = "分配研发花名册人员至项目")
    public Boolean setProjectMember(@RequestBody KeysAndIdsModel keysAndIds) throws OwnerException {
        return rdEmployeeService.setProjectMember(getUserInfo(), keysAndIds);
    }
}
