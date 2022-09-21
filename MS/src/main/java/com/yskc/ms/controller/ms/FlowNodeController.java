package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.ms.models.flow.FlowNodeModel;
import com.yskc.ms.models.flow.FlowNodeUserModel;
import com.yskc.ms.models.flow.SetNodeUserModel;
import com.yskc.ms.models.user.UserDeptModel;
import com.yskc.ms.service.ms.FlowNodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by hck
 * on 2020/12/3 8:37
 * description:流程节点接口
 */
@Api(tags = "流程节点接口", value = "流程节点接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/flowNode")
public class FlowNodeController extends BaseController {

    @Autowired
    private FlowNodeService flowNodeService;

      @GetMapping("/getFlowNode")
    //@PermissionRequired(perms = "sys:product:search")
    @ApiOperation(value = "获取流程列表", notes = "获取流程列表", response = List.class)
    public List<FlowNodeModel> getFlowNode(Integer flowId) throws OwnerException {
        return flowNodeService.getNodes(flowId);
    }



//    @PostMapping("/setNodeUser")
//   // @PermissionRequired(perms = "sys:user:edit")
//    @SystemLog(description = "设置节点用户", mode = SystemLog.SAVE_DB)
//    @ApiOperation(value = "设置节点用户", notes = "设置节点用户", response = Boolean.class)
//    public Boolean setNodeUser(@RequestBody @Validated SetNodeUserModel model) throws OwnerException {
//        return flowNodeService.setNodeUser(getUserInfo(), model);
//    }

}
