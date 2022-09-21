package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.enums.FlowInstanceStatusEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.config.Constant;
import com.yskc.ms.models.FormAuditModel;
import com.yskc.ms.models.flow.FlowListModel;
import com.yskc.ms.models.flow.QueryFlowModel;
import com.yskc.ms.models.flow.SetFlowMasterModel;
import com.yskc.ms.models.flowInstance.FlowInstanceActivateModel;
import com.yskc.ms.models.projectAudit.BatchAuditModel;
import com.yskc.ms.models.projectAudit.ProjectAuditModel;
import com.yskc.ms.service.ms.FlowInstanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @DateTime: 2022/3/17 8:01
 * @Description:
 * @author: hsx
 */

@Api(tags = "流程列表接口", value = "流程列表接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/flowList")
public class FlowListController extends BaseController{

    @Autowired
    private FlowInstanceService flowInstanceService;

    @GetMapping("/getList")
    @PermissionRequired(perms = "flow:flowList:search")
    @ApiOperation(value = "获取流程列表", notes = "获取流程列表", response = List.class)
    public PageModel<List<FlowListModel>> getList(QueryFlowModel query) throws OwnerException {
        return flowInstanceService.search(query,getDataPerm());
    }

    @PostMapping("/deliverMaster")
    @PermissionRequired(perms = "flow:flowList:deliverMaster")
    @ApiOperation(value = "转交处理人", notes = "转交处理人")
    public Boolean deliverMaster(@RequestBody @Validated SetFlowMasterModel model) throws OwnerException {
        return flowInstanceService.deliverMaster(model,getUserInfo());
    }
    @PostMapping("/activates")
    @PermissionRequired(perms = "flow:flowList:activates,innovation:rdFeeAudit:activate")
    @ApiOperation(value = "批量激活", notes = "批量激活")
    public Boolean activates(@RequestBody @Validated FlowInstanceActivateModel model) throws OwnerException {
        return flowInstanceService.activates(model,getUserInfo());
    }

    @PostMapping("/cancelAudits")
    @PermissionRequired(perms = "flow:flowList:cancel")
    @ApiOperation(value = "批量取消审核流程", notes = "批量取消审核流程")
    @SystemLog(description = "批量取消审核流程", mode = SystemLog.SAVE_DB)
    public Boolean cancelAudits(@RequestBody @Validated BatchAuditModel batchAudit) throws OwnerException {
        return flowInstanceService.cancelOrRecall(getUserInfo().getId(), FlowInstanceStatusEnum.CANCEL.getStatus(),
                Constant.TOPIC_PUSH_AUDIT, batchAudit);
    }

    @PostMapping("/audit")
    @SystemLog(description = "审核表单流程", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "审核表单流程", notes = "审核表单流程")
    public Boolean audit(@RequestBody FormAuditModel model) throws Exception {
        return flowInstanceService.audit(model,getUserInfo());
    }
}
