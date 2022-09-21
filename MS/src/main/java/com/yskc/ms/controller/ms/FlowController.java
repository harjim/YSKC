package com.yskc.ms.controller.ms;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.entity.ms.FlowEntity;
import com.yskc.ms.models.flow.FlowModel;
import com.yskc.ms.models.flow.FlowNodeTreeModel;
import com.yskc.ms.models.flow.QueryFlowModel;
import com.yskc.ms.models.role.AppRoleModel;
import com.yskc.ms.service.ms.FlowNodeService;
import com.yskc.ms.service.ms.FlowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by hck
 * on 2020/12/15 11:32
 * description:流程接口
 */
@Api(tags = "流程接口", value = "流程接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/flow")
public class FlowController extends BaseController {

    @Autowired
    private FlowService flowService;
    @Autowired
    private FlowNodeService flowNodeService;

    @GetMapping("/getList")
    //@PermissionRequired(perms = "sys:product:search")
    @ApiOperation(value = "获取流程列表", notes = "获取流程列表", response = List.class)
    public PageModel<List<FlowModel>> getList(QueryFlowModel query) {
        return flowService.getList(query);
    }

    @PostMapping("/addFlow")
    //@PermissionRequired(perms = "sys:product:search")
    @ApiOperation(value = "添加流程", notes = "添加流程", response = List.class)
    public Boolean addFlow(@RequestBody @Validated FlowEntity entity) {
        return flowService.addFlow(entity);
    }


//    @PostMapping("/addFlowNode")
//    //@PermissionRequired(perms = "sys:product:search")
//    @ApiOperation(value = "添加流程节点", notes = "添加流程节点", response = List.class)
//    public Boolean addFlowNode(FlowNodeModel model) throws OwnerException {
//        return flowNodeService.addFlowNode(model,getUserInfo());
//    }


    @PostMapping("/saveFlow")
    //@PermissionRequired(perms = "sys:product:search")
    @ApiOperation(value = "保存流程", notes = "保存流程", response = List.class)
    public List<FlowNodeTreeModel> saveFlow(@RequestBody @Validated List<FlowNodeTreeModel> models) throws OwnerException {
        return flowService.saveFlow(models, getUserInfo());
    }

    @PostMapping("/delFlow")
    //@PermissionRequired(perms = "sys:product:search")
    @ApiOperation(value = "删除流程", notes = "删除流程", response = List.class)
    public Boolean delFlow(@RequestBody @Validated FlowEntity flow) throws OwnerException {
        return flowService.delFlow(flow.getId());
    }

    @GetMapping("/getFlowInfo")
    //@PermissionRequired(perms = "sys:product:search")
    @ApiOperation(value = "获取流程", notes = "获取流程", response = List.class)
    public List<FlowNodeTreeModel> getFlowInfo(Integer flowId) {
        return flowService.getFlowInfo(flowId);
    }

//    @PostMapping("/setFlowUser")
//    //@PermissionRequired(perms = "sys:product:search")
//    @ApiOperation(value = "设置节点用户", notes = "设置节点用户", response = List.class)
//    public Boolean setFlowUser(@RequestBody @Validated SetNodeUserModel model) throws OwnerException {
//        return flowService.setFlowUser(model,getUserInfo());
//    }

    @GetMapping("/getRoleList")
    @ApiOperation(value = "获取角色列表", notes = "获取角色列表")
    public List<AppRoleModel> getAppRoleList(String roleName) throws OwnerException {
        return flowService.getRoleList(roleName);
    }
}
