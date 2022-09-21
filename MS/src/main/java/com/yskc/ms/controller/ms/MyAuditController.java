package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.enums.FlowInstanceStatusEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.config.Constant;
import com.yskc.ms.models.flowInstance.FlowInstanceInfoModel;
import com.yskc.ms.models.flowInstance.FlowInstanceModel;
import com.yskc.ms.models.params.FlowInstanceLogParams;
import com.yskc.ms.models.params.MyAuditParams;
import com.yskc.ms.models.projectAudit.BatchAuditModel;
import com.yskc.ms.models.projectAudit.FlowInstanceLogModel;
import com.yskc.ms.service.ms.FlowInstanceLogService;
import com.yskc.ms.service.ms.FlowInstanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "我的审批接口", value = "我的审批接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/myAudit")
public class MyAuditController extends BaseController {

    @Autowired
    private FlowInstanceService flowInstanceService;

    @Autowired
    private FlowInstanceLogService flowInstanceLogService;

    @GetMapping("/getList")
//    @PermissionRequired(perms = "sys:menu:search")
    @ApiOperation(value = "获取我的审批", notes = "获取我的审批", response = String.class)
    public PageModel<List<FlowInstanceModel>> getList(MyAuditParams params) throws OwnerException {
        return flowInstanceService.getList(params, getUserInfo().getId());
    }

    @GetMapping("/getLogList")
    @ApiOperation(value = "获取我的审批操作日志", notes = "获取我的审批操作日志", response = String.class)
    public PageModel<List<FlowInstanceLogModel>> getLogList(FlowInstanceLogParams flowInstanceLogParams) throws OwnerException {
        return flowInstanceLogService.getLogList(flowInstanceLogParams);
    }

    @GetMapping("/getInstanceInfo")
    @ApiOperation(value = "获取实例信息", notes = "获取实例信息")
    public FlowInstanceInfoModel getInstanceInfo(Integer id, Integer type) {
        return flowInstanceService.getInstanceInfo(id, type);
    }
    @PostMapping("/recall")
    @PermissionRequired(perms = "customer:myAudit:recall")
    @ApiOperation(value = "批量撤回我发起的审核", notes = "批量撤回我发起的审核")
    @SystemLog(description = "批量撤回我发起的审核", mode = SystemLog.SAVE_DB)
    public Boolean recall(@RequestBody @Validated BatchAuditModel batchAudit) throws OwnerException {
        return flowInstanceService.cancelOrRecall(getUserInfo().getId(), FlowInstanceStatusEnum.NO_SUBMIT.getStatus(),
                Constant.TOPIC_REVOKE_AUDIT,batchAudit);
    }
}
