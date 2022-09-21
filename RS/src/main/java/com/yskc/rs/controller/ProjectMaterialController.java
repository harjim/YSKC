package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.PageResult;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.material.AppMaterialModel;
import com.yskc.rs.models.material.ProjectMaterialModel;
import com.yskc.rs.models.material.QueryMaterialTrackModel;
import com.yskc.rs.models.project.DocMaterialModel;
import com.yskc.rs.models.project.QueryProjectMaterialModel;
import com.yskc.rs.models.projectmaterial.DepreciationRatioModel;
import com.yskc.rs.service.ProjectMaterialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据归集物料类接口
 *
 * @author yezhihao
 */
@Api(tags = "数据归集物料类接口", value = "数据归集物料类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/projectMaterial")
public class ProjectMaterialController extends BaseController {

    @Autowired
    private ProjectMaterialService projectMaterialService;

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "批量添加项目物料", mode = SystemLog.SAVE_DB)
    @PostMapping("/save")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "批量添加项目物料", notes = "批量添加项目物料", response = boolean.class)
    public boolean save(@RequestBody @Validated ProjectMaterialModel model) throws OwnerException {
        return projectMaterialService.saveMaterial(getUserInfo(), model);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "批量添加项目物料(相同出库单号添加)", mode = SystemLog.SAVE_DB)
    @PostMapping("/saveBillNo")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "批量添加项目物料(相同出库单号添加)", notes = "批量添加项目物料(相同出库单号添加)", response = boolean.class)
    public boolean saveBillNo(@RequestBody @Validated ProjectMaterialModel model) throws OwnerException {
        return projectMaterialService.saveBillNo(getUserInfo(), model);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "条件添加项目物料", mode = SystemLog.SAVE_DB)
    @PostMapping("/conditionalAddition")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "条件添加项目物料", notes = "条件添加项目物料", response = boolean.class)
    public boolean conditionalAddition(@RequestBody @Validated AppMaterialModel model) throws OwnerException {
        return projectMaterialService.conditionalAddition(getUserInfo(), model);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "条件添加项目物料(相同出库单号添加)", mode = SystemLog.SAVE_DB)
    @PostMapping("/conditionalAdditionBillNo")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "条件添加项目物料(相同出库单号添加)", notes = "条件添加项目物料(相同出库单号添加)", response = boolean.class)
    public boolean conditionalAdditionBillNo(@RequestBody @Validated AppMaterialModel model) throws OwnerException {
        return projectMaterialService.conditionalAdditionBillNo(getUserInfo(), model);
    }

    @GetMapping("/queryProjectMaterial")
    @PermissionRequired(perms = "project:data:view")
    @ApiOperation(value = "查询项目使用物料", notes = "查询项目使用物料", response = String.class)
    public PageResult queryProjectMaterial(QueryProjectMaterialModel query) throws OwnerException {
        return projectMaterialService.queryProjectMaterial(getUserInfo().getCompanyId(), query);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "删除项目物料", mode = SystemLog.SAVE_DB)
    @PostMapping("/del")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "删除项目物料", notes = "删除项目物料", response = boolean.class)
    public boolean delMaterial(@RequestBody @Validated ProjectMaterialModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return projectMaterialService.delMaterial(userInfo, model.getProjectId(), model.getMaterialDataId(),
                model.getAcqMonth(), model.getRdType(), model.getUsed(), model.getId());
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "批量删除项目物料", mode = SystemLog.SAVE_DB)
    @PostMapping("/delSelect")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "批量删除项目物料", notes = "批量删除项目物料", response = boolean.class)
    public boolean delSelect(@RequestBody @Validated ProjectMaterialModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return projectMaterialService.delSelect(userInfo, model);
    }

    @GetMapping("/queryMaterialAndQuantity")
    @PermissionRequired(perms = "project:data:view")
    @ApiOperation(value = "查询物料库存", notes = "查询物料库存", response = String.class)
    public PageResult queryMaterialAndQuantity(QueryProjectMaterialModel query)
            throws OwnerException {
        return projectMaterialService.queryMaterialAndQuantity(getUserInfo().getCompanyId(), query);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "修改保存项目物料", mode = SystemLog.SAVE_DB)
    @PostMapping("/edit")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "修改保存项目物料", notes = "修改保存项目物料", response = boolean.class)
    public boolean editMaterial(@RequestBody @Validated List<AppMaterialModel> model) throws OwnerException {
        return projectMaterialService.editMaterial(getUserInfo(), model);
    }

    /**
     * @param query
     * @return
     * @throws OwnerException
     */
    @GetMapping("getDocMaterialList")
    @ApiOperation(value = "获取项目物料（文档）", notes = "获取项目物料（文档）")
    public PageModel<List<DocMaterialModel>> getDocMaterialList(QueryProjectMaterialModel query) throws OwnerException {
        return projectMaterialService.getDocMaterialList(getUserInfo(), query);
    }

    /**
     * @param query
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryMaterialTrack")
    @PermissionRequired(perms = "project:materialTrack:search")
    @ApiOperation(value = "查询材料跟踪表", notes = "查询材料跟踪表", response = String.class)
    public PageModel<List<AppMaterialModel>> queryMaterialTrack(QueryMaterialTrackModel query) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return projectMaterialService.queryMaterialTrack(userInfo.getCompanyId(), query);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "修改项目成品废品", mode = SystemLog.SAVE_DB)
    @PostMapping("/editMaterialTrack")
    @PermissionRequired(perms = "project:materialTrack:edit")
    @ApiOperation(value = "修改成品废品", notes = "修改成品废品", response = boolean.class)
    public boolean editMaterialTrack(@RequestBody @Validated List<AppMaterialModel> model) throws OwnerException {
        return projectMaterialService.editMaterialTrack(getUserInfo(), model);
    }


    @SystemLog(description = "设置损耗率", mode = SystemLog.SAVE_DB)
    @PostMapping("/setDepreciationRatio")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "设置损耗率", notes = "设置损耗率", response = boolean.class)
    public boolean setDepreciationRatio(@RequestBody @Validated DepreciationRatioModel ratioModel)throws OwnerException {
        return projectMaterialService.setDepreciationRatio(getUserInfo(),ratioModel);
    }
}
