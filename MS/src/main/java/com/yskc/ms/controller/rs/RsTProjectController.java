package com.yskc.ms.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.models.rs.QueryTechProjectModel;
import com.yskc.ms.models.rs.RelatedProjectModel;
import com.yskc.ms.models.rs.RsProductListModel;
import com.yskc.ms.models.rs.RsTechProjectModel;
import com.yskc.ms.service.rs.RsTProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by hck
 * on 2020/10/15 11:36
 * description:技改项目接口
 */
@Api(tags = "技改项目接口", value = "技改项目接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/tProject")
public class RsTProjectController extends BaseController {

    @Autowired
    private RsTProjectService rsTProjectService;

    @GetMapping("/getList")
    @PermissionRequired(perms = "project:techProject:search")
    @ApiOperation(value = "获取技改项目列表", notes = "获取技改项目列表")
    public PageModel<List<RsTechProjectModel>> getList(QueryTechProjectModel query) throws OwnerException {
        return rsTProjectService.getList(query, getDataPerm());
    }


    @GetMapping("/getProductList")
    @ApiOperation(value = "获取申报项目列表", notes = "获取申报项目列表")
    public List<RsProductListModel> getProductList(String addressCode, Integer year) {
        return rsTProjectService.getProductList(addressCode, year);
    }

    @GetMapping("/getProject")
    @ApiOperation(value = "获取技改项目", notes = "获取技改项目")
    public RelatedProjectModel getProject(Integer projectId, Boolean beian) {
        return rsTProjectService.getProject(projectId, beian);
    }


    @PostMapping("/relatedProject")
    @PermissionRequired(perms = "project:techProject:relatedProject")
    @SystemLog(description = "关联申报项目", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "关联申报项目", notes = "关联申报项目", response = boolean.class)
    public boolean relatedProject(@RequestBody @Validated RelatedProjectModel model) throws OwnerException {
        return rsTProjectService.relatedProject(model, getUserInfo());
    }

    @PostMapping("/disassociation")
    @PermissionRequired(perms = "project:techProject:disconnect")
    @SystemLog(description = "取消关联申报项目", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "取消关联申报项目", notes = "取消关联申报项目", response = boolean.class)
    public boolean disassociation(@RequestBody @Validated RsTechProjectModel model) throws OwnerException {
        return rsTProjectService.disassociation(model, getUserInfo());
    }

    @PostMapping("/saveBeian")
    @PermissionRequired(perms = "project:techProject:relatedProject")
    @SystemLog(description = "关联备案项目", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "关联备案项目", notes = "关联备案项目", response = boolean.class)
    public boolean saveBeian(@RequestBody @Validated RelatedProjectModel model) throws OwnerException {
        return rsTProjectService.saveBeian(model, getUserInfo());
    }
}
