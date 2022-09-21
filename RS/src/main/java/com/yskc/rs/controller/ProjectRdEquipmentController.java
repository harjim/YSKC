package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.PageResult;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.entity.project.ProjectRdEquipmentEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.models.projectequipment.RdEquipmentResultModel;
import com.yskc.rs.models.projectrdequipment.BatchProjectRdEquipmentModel;
import com.yskc.rs.models.projectrdequipment.HighTechProjectRdEquipmentModel;
import com.yskc.rs.models.projectrdequipment.ProjectRdEquipmentModel;
import com.yskc.rs.models.projectrdequipment.QueryProjectRdEquipmentModel;
import com.yskc.rs.service.ExcelService;
import com.yskc.rs.service.ProjectRdEquipmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: zhangdingfu
 * @CreateTime: 2020-04-01 11:03
 * @Description: 项目设备研发折旧接口
 */
@RestController
@RequestMapping("/api/projectRdEquipment")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@Api(tags = "项目设备研发折旧接口", value = "项目设备研发折旧接口")
public class ProjectRdEquipmentController extends BaseController {

    @Autowired
    private ProjectRdEquipmentService projectRdEquipmentService;
    @Autowired
    private ExcelService excelService;
    @Autowired
    private RsConfig rsConfig;

    @GetMapping("/getList")
    @PermissionRequired(perms = "project:data:view")
    @ApiOperation(value = "获取项目设备研发折旧", notes = "获取项目设备研发折旧")
    public PageResult getList(QueryProjectRdEquipmentModel query) throws OwnerException {
        return projectRdEquipmentService.getList(getUserInfo(), query);
    }

    @GetMapping("/queryList")
    @ApiOperation(value = "根据月份获取项目设备使用情况", notes = "根据月份获取项目设备使用情况")
    public PageModel<List<RdEquipmentResultModel>> queryList(QueryProjectRdEquipmentModel query) throws OwnerException {
        return projectRdEquipmentService.queryList(getUserInfo(), query);
    }

    @GetMapping("/queryListByYear")
    @ApiOperation(value = "根据年获取项目设备统计表", notes = "根据年获取项目设备统计表")
    public List<RdEquipmentResultModel> queryListByYear(QueryProjectRdEquipmentModel query) throws OwnerException {
        return projectRdEquipmentService.queryListByYear(getUserInfo(), query);
    }

    @GetMapping("/getEquipmentPowerList")
    @PermissionRequired(perms = "project:data:view")
    @ApiOperation(value = "获取项目设备动力列表", notes = "获取项目设备动力列表")
    public PageResult getEquipmentPowerList(QueryProjectRdEquipmentModel query) throws OwnerException {
        return projectRdEquipmentService.getEquipmentPowerList(getUserInfo(), query);
    }

    @SystemLog(description = "批量保存项目设备折旧费用", mode = SystemLog.SAVE_DB)
    @PostMapping("/saveList")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "批量保存项目设备折旧费用", notes = "批量保存项目设备折旧费用")
    public Boolean saveList(@RequestBody @Validated BatchProjectRdEquipmentModel batchModel) throws OwnerException {
        return projectRdEquipmentService.saveList(getUserInfo(), batchModel);
    }

    @SystemLog(description = "设置项目设备折旧电费", mode = SystemLog.SAVE_DB)
    @PostMapping("/setPowerUnitPrice")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "设置项目设备折旧电费", notes = "设置项目设备折旧电费")
    public Boolean setPowerUnitPrice(@RequestBody @Validated ProjectRdEquipmentEntity entity) throws OwnerException {
        return projectRdEquipmentService.setPowerUnitPrice(getUserInfo(), entity);
    }

    @SystemLog(description = "导入设备研发工时", mode = SystemLog.SAVE_DB)
    @PostMapping("/importRdHour")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "导入设备研发工时", notes = "导入设备研发工时")
    public Boolean importRdHour(@RequestParam("file") MultipartFile file, Integer year, Integer projectId,TableField tableField) throws OwnerException {
        UserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                info.getCompanyId());
        ExcelResult<ProjectRdEquipmentModel> excelResult = excelService.getExcelResult(tempPath, file, ProjectRdEquipmentModel.class, tableField);
        BatchProjectRdEquipmentModel batchModel = new BatchProjectRdEquipmentModel();
        batchModel.setProjectId(projectId);
        batchModel.setList(excelResult.getData());
        return projectRdEquipmentService.importRdHour(info,batchModel,year);
    }
    @GetMapping("/getMonthTotalProd")
    @ApiOperation(value = "根据月份获取所有设备使用情况", notes = "根据月份获取所有设备使用情况")
    public PageModel<List<RdEquipmentResultModel>> getMonthTotalProd(QueryProjectRdEquipmentModel query) throws OwnerException {
        return projectRdEquipmentService.getMonthTotalProd(getUserInfo(), query);
    }

    @GetMapping("/getEquipments")
    @ApiOperation(value = "获取项目设备研发折旧", notes = "获取项目设备研发折旧")
    public PageModel<List<HighTechProjectRdEquipmentModel>> getEquipments(QueryProjectRdEquipmentModel query) throws OwnerException {
        return projectRdEquipmentService.getEquipments(getUserInfo().getCompanyId(), query);
    }
}
