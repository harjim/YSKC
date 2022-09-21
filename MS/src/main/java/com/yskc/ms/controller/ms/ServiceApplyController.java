package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.BatchDeleteModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.serviceApply.*;
import com.yskc.ms.service.ms.FlowInstanceService;
import com.yskc.ms.service.ms.ServiceApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: ms
 * @description: 服务申请
 * @author: cyj
 * @create: 2022-08-11 10:21
 **/
@Api(tags = "客户服务申请", value = "客户服务申请")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/serviceApply")
public class ServiceApplyController extends BaseController {

    @Autowired
    private ServiceApplyService serviceApplyService;
    @Autowired
    private FlowInstanceService flowInstanceService;

    @GetMapping("/getList")
    @SystemLog(description = "根据条件查询服务申请")
    @ApiOperation(value = "根据条件查询服务申请", notes = "根据条件查询服务申请")
    @PermissionRequired(perms = "customer:serviceApply:search")
    public PageModel<List<ServiceApplyModel>> getList(QueryApplyModel query) throws OwnerException {
        return serviceApplyService.getList(query,getUserInfo(),getDataPerm());
    }
    @GetMapping("/getMemberList")
    @SystemLog(description = "查询服务申请各类人员")
    @ApiOperation(value = "查询服务申请各类人员", notes = "查询服务申请各类人员")
    public SelectMemberModel getMemberList(QueryUserModel model) throws OwnerException {
        return serviceApplyService.getMemberList(model);
    }
    @GetMapping("/getCustomerList")
    @SystemLog(description = "查询服务申请公司名称")
    @ApiOperation(value = "查询服务申请公司名称", notes = "查询服务申请公司名称")
    public List<ServiceCustomerModel> getCustomerList(QueryCustomerModel model) throws OwnerException {
        return serviceApplyService.getCustomerList(model);
    }
    @GetMapping("/getServiceInfo")
    @SystemLog(description = "根据id查询服务申请")
    @ApiOperation(value = "根据id查询服务申请", notes = "根据id查询服务申请")
    public ServiceApplyModel getServiceInfo(Integer serviceId) throws OwnerException {
        return serviceApplyService.getApplyInfo(null,serviceId,getUserInfo());
    }

    @PostMapping("/addServiceApply")
    @SystemLog(description = "添加服务申请", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "添加服务申请", notes = "添加服务申请")
    @PermissionRequired(perms = "customer:serviceApply:add")
    public Boolean addServiceApply(@RequestBody @Validated ServiceApplyModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return serviceApplyService.addServiceApply(model,userInfo);
    }

    @PostMapping("/editServiceApply")
    @SystemLog(description = "编辑服务申请", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "编辑服务申请", notes = "编辑服务申请")
    @PermissionRequired(perms = "customer:serviceApply:edit")
    public Boolean editServiceApply(@RequestBody @Validated ServiceApplyModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return serviceApplyService.editServiceApply(model,userInfo);
    }

    @PostMapping("/delServiceApply")
    @SystemLog(description = "删除服务申请", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "删除服务申请", notes = "删除服务申请")
    @PermissionRequired(perms = "customer:serviceApply:del")
    public Boolean delServiceApply(@RequestBody @Validated BatchDeleteModel model) throws OwnerException {
        return serviceApplyService.delServiceApply(model.getIds());
    }

    @PostMapping("/submit")
    @SystemLog(description = "提交服务申请", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "提交服务申请", notes = "提交服务申请")
    @PermissionRequired(perms = "customer:serviceApply:submit")
    public Boolean submit(@RequestBody @Validated ServiceApplyModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return serviceApplyService.submit(model,userInfo);
    }

    @PostMapping("/audit")
    @SystemLog(description = "审核服务流程", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "审核服务流程", notes = "审核服务流程")
    @PermissionRequired(perms = "customer:serviceApply:review,customer:workRecord:review")
    public Boolean audit(@RequestBody QueryEditModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        if (model.getApplyModel()!=null){
            serviceApplyService.editAuditApply(model.getApplyModel(),userInfo);
        }
        return flowInstanceService.audit(model.getModel(), userInfo);
    }
}
