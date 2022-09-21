package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.design.DesignAmountModel;
import com.yskc.rs.models.projectdesign.ProjectDesignModel;
import com.yskc.rs.models.projectdesign.QueryProjectDesignModel;
import com.yskc.rs.service.ProjectDesignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 *
 */
@Api(tags = "项目研发设计类接口", value = "项目研发设计类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/designProject")
public class ProjectDesignController extends BaseController {

    @Autowired
    private ProjectDesignService designProjectService;

    @GetMapping("/queryProjectDesignList")
    @PermissionRequired(perms = "project:data:view")
    @ApiOperation(value = "获取项目研发设计费用", notes = "获取项目研发设计费用")
    public PageModel<List<ProjectDesignModel>> queryProjectDesignList(QueryProjectDesignModel query) throws OwnerException {
        return designProjectService.queryProjectDesign(getUserInfo().getCompanyId(), query);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "添加项目研发设计费用", mode = SystemLog.SAVE_DB)
    @PostMapping("/addProjectDesign")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "添加项目研发设计费用", notes = "添加项目研发设计费用", response = boolean.class)
    public boolean addProjectDesign(@RequestBody @Validated ProjectDesignModel model) throws OwnerException {
        return designProjectService.addProjectDesign(getUserInfo(), model);
    }

    @SystemLog(description = "按条件添加设计费用", mode = SystemLog.SAVE_DB)
    @PostMapping("/addProjectDesignByTerm")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "按条件添加设计费用", notes = "按条件添加项目设计费用", response = boolean.class)
    public boolean addProjectDesignByTerm(@RequestBody @Validated QueryProjectDesignModel query) throws OwnerException {
        return designProjectService.addProjectDesignByTerm(getUserInfo(), query.getName(), query.getProjectId(), query.getProjectMonth(), query.getDeptName());
    }

    /**
     * @param modelList
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "批量删除项目研发设计", mode = SystemLog.SAVE_DB)
    @PostMapping("/delProjectDesignBatch")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "批量删除项目研发设计", notes = "批量删除项目研发设计", response = boolean.class)
    public boolean delProjectDesignBatch(@RequestBody @Validated List<ProjectDesignModel> modelList) throws OwnerException {
        return designProjectService.delDesignBatch(getUserInfo(), modelList);
    }

    /**
     * 单个修改一次提交
     * @param modelList
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "批量设置研发费用", mode = SystemLog.SAVE_DB)
    @PostMapping("/setRdAmounts")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "批量设置研发费用", notes = "批量设置研发费用", response = boolean.class)
    public boolean setRdAmount(@RequestBody @Validated List<ProjectDesignModel> modelList) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return designProjectService.setRdAmounts(userInfo, modelList);
    }

    /**
     * 多个统一修改
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "批量设置研发费用", mode = SystemLog.SAVE_DB)
    @PostMapping("/setDesignRdAmounts")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "批量设置研发费用", notes = "批量设置研发费用", response = boolean.class)
    public boolean setDesignRdAmounts(@RequestBody @Validated DesignAmountModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return designProjectService.setDesignRdAmounts(userInfo, model);
    }

}
