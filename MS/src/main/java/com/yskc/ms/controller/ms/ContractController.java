package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.BatchDeleteModel;
import com.yskc.ms.models.FormAuditModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.contract.*;
import com.yskc.ms.models.projectAudit.ProjectAuditModel;
import com.yskc.ms.models.serviceApply.ApplyEditModel;
import com.yskc.ms.models.serviceApply.QueryApplyModel;
import com.yskc.ms.models.serviceApply.ServiceApplyModel;
import com.yskc.ms.service.ms.ContractService;
import com.yskc.ms.service.ms.FlowInstanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: ms
 * @description:
 * @author: wjy
 * @create: 2022-08-30 11:44
 **/
@RestController
@RequestMapping("/api/contract")
@CrossOrigin(origins = "*", allowedHeaders = "", allowCredentials = "true", methods = {})
@Api(tags = "合同列表", value = "合同列表")
public class ContractController extends BaseController{

    @Autowired
    private FlowInstanceService flowInstanceService;
    @Autowired
    private ContractService contractService;


    @GetMapping("/getList")
    @SystemLog(description = "根据条件查询合同列表")
    @ApiOperation(value = "根据条件查询合同列表", notes = "根据条件查询合同列表")
    @PermissionRequired(perms = "customer:contract:search")
    public PageModel<List<ContractModel>> getList(QueryContractModel query) throws OwnerException {
        return contractService.getList(query,getUserInfo(),getDataPerm());
    }

    @GetMapping("/getInfo")
    @SystemLog(description = "查询合同信息")
    @ApiOperation(value = "查询合同信息", notes = "查询合同信息")
//    @PermissionRequired(perms = "contract:contract:search")
    public ContractModel getInfo(Integer id) throws OwnerException {
        return contractService.getInfo(id,getUserInfo());
    }

    @GetMapping("/getProduct")
    @SystemLog(description = "根据条件查询签约次数")
    @ApiOperation(value = "根据条件查询签约次数", notes = "根据条件查询签约次数")
    public Integer getProduct(QueryProductModel query) throws OwnerException {
        return contractService.getProduct(query,getUserInfo());
    }

    @PostMapping("/checkProduct")
    @SystemLog(description = "根据条件查询重复项目类型")
    @ApiOperation(value = "根据条件查询重复项目类型", notes = "根据条件查询重复项目类型")
    public String checkProduct(@RequestBody @Validated CheckProductModel model) throws OwnerException {
        return contractService.checkProduct(model);
    }

    @PostMapping("/addContract")
    @SystemLog(description = "添加合同", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "添加合同", notes = "添加合同")
    @PermissionRequired(perms = "customer:contract:add")
    public Boolean addContract(@RequestBody @Validated AddContractModel model) throws OwnerException {
        return contractService.addContract(model,getUserInfo());
    }

    @PostMapping("/editContract")
    @SystemLog(description = "编辑合同", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "编辑合同", notes = "编辑合同")
    @PermissionRequired(perms = "customer:contract:edit")
    public Boolean editContract(@RequestBody @Validated AddContractModel model) throws OwnerException {
        return contractService.editContract(model,getUserInfo());
    }

    @PostMapping("/delContract")
    @SystemLog(description = "删除合同", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "删除合同", notes = "删除合同")
    @PermissionRequired(perms = "customer:contract:del")
    public Boolean delContract(@RequestBody @Validated BatchDeleteModel model) throws OwnerException {
        return contractService.delContract(model.getIds());
    }

    @PostMapping("/submit")
    @SystemLog(description = "提交合同", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "提交合同", notes = "提交合同")
    @PermissionRequired(perms = "customer:contract:submit")
    public Boolean submit(@RequestBody @Validated AddContractModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return contractService.submit(model,userInfo);
    }
    @PostMapping("/audit")
    @SystemLog(description = "审核合同", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "审核合同", notes = "审核合同")
    @PermissionRequired(perms = "customer:contract:audit")
    public Boolean audit(@RequestBody ContractAuditModel model) throws Exception {
        UserInfo userInfo = getUserInfo();
        if (model.getNodeNumber().equals(2)&& model.getPass()){
            contractService.addPassContract(model.getId(), userInfo);
        }else if (model.getNodeNumber().equals(3)&& model.getPass()){
            contractService.addExpress(model.getId(), userInfo, model.getExpressNo(), model.getExpressAddress());
        }
        FormAuditModel auditModel = new FormAuditModel();
        auditModel.setInstanceId(model.getInstanceId());
        auditModel.setPass(model.getPass());
        auditModel.setSuggestion(model.getSuggestion());
        return flowInstanceService.audit(auditModel, userInfo);
    }
}
