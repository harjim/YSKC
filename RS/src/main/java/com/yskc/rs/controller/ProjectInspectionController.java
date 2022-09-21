package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.hightech.QueryInspectionModel;
import com.yskc.rs.models.projectinspection.*;
import com.yskc.rs.service.ProjectInspectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 *
 */
@Api(tags = "项目检测修理类接口", value = "项目检测修理类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/projectInspection")
public class ProjectInspectionController extends BaseController {

    @Autowired
    private ProjectInspectionService projectInspectionService;

    @GetMapping("/queryProjectInspectionList")
    @PermissionRequired(perms = "project:data:view")
    @ApiOperation(value = "获取项目检测修理", notes = "获取项目检测修理")
    public PageModel<List<ProjectInspectionModel>> queryProjectInspectionList(QueryProjectInspectionModel query) throws OwnerException {
        return projectInspectionService.queryProjectInspection(getUserInfo().getCompanyId(), query);
    }

    @GetMapping("/getProjectInspectionList")
    @ApiOperation(value = "获取高新进度其他费用明细表", notes = "获取高新进度其他费用明细表")
    public PageModel<List<HighTechInspectionModel>> getProjectInspectionList(QueryInspectionModel query) throws OwnerException {
        return projectInspectionService.getProjectInspection(query);
    }

    /**
     * @param batch
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "添加项目检测修理", mode = SystemLog.SAVE_DB)
    @PostMapping("/addProjectInspection")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "添加项目检测修理", notes = "添加项目检测修理", response = boolean.class)
    public boolean addProjectInspection(@RequestBody @Validated BatchProjectInspectionModel batch) throws OwnerException {
        return projectInspectionService.addProjectInspection(getUserInfo(), batch);
    }

    /**
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "按条件添加项目检测修理费用数据", mode = SystemLog.SAVE_DB)
    @PostMapping("/addProjectInspectionByTerm")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "按条件添加项目检测修理费用数据", notes = "按条件添加项目检测修理费用数据", response = boolean.class)
    public boolean addProjectInspectionByTerm(@RequestBody @Validated QueryProjectInspectionModel query) throws OwnerException {
        return projectInspectionService.addProjectInspectionByTerm(getUserInfo(), query);
    }
    /**
     * @param batch
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "批量删除项目检测修理费用", mode = SystemLog.SAVE_DB)
    @PostMapping("/delInspectionBatch")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "批量删除项目检测修理费用", notes = "批量删除项目检测修理费用", response = boolean.class)
    public boolean delInspectionBatch(@RequestBody @Validated BatchProjectInspectionModel batch) throws OwnerException {
        return projectInspectionService.delInspectionBatch(getUserInfo(), batch);
    }

    /**
     * 单个修改一次提交
     * @param batch
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "批量设置研发费用", mode = SystemLog.SAVE_DB)
    @PostMapping("/setRdAmounts")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "批量设置研发费用", notes = "批量设置研发费用", response = boolean.class)
    public boolean setRdAmount(@RequestBody @Validated SetInspectionAmountModel batch) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return projectInspectionService.setRdAmounts(userInfo, batch);
    }

    /**
     * 多个统一修改
     * @param batch
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "批量设置研发费用", mode = SystemLog.SAVE_DB)
    @PostMapping("/setInspectAmounts")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "批量设置研发费用", notes = "批量设置研发费用", response = boolean.class)
    public boolean setInspectAmounts(@RequestBody @Validated SetInspectionAmountModel batch) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return projectInspectionService.setInspectAmounts(userInfo, batch);
    }

}
