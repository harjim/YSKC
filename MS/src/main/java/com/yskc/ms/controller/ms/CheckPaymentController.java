package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.BatchDeleteModel;
import com.yskc.ms.models.FormAuditModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.checkInst.CheckMiniModel;
import com.yskc.ms.models.checkPayment.AddPaymentModel;
import com.yskc.ms.models.checkPayment.CheckPaymentModel;
import com.yskc.ms.models.checkPayment.PaymentAuditModel;
import com.yskc.ms.models.checkPayment.QueryCheckModel;
import com.yskc.ms.models.projectAudit.ProjectAuditModel;
import com.yskc.ms.models.rs.ReportProjectModel;
import com.yskc.ms.service.ms.CheckPaymentService;
import com.yskc.ms.service.ms.FlowInstanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: ms
 * @description: 查新付费
 * @author: wjy
 * @create: 2022-09-06 10:54
 **/
@RestController
@RequestMapping("/api/checkPayment")
@CrossOrigin(origins = "*", allowedHeaders = "", allowCredentials = "true", methods = {})
@Api(tags = "查新付费", value = "查新付费")
public class CheckPaymentController extends BaseController{

    @Autowired
    private CheckPaymentService checkPaymentService;
    @Autowired
    private FlowInstanceService flowInstanceService;


    @GetMapping("/getList")
    @SystemLog(description = "根据条件查询查新付费列表")
    @ApiOperation(value = "根据条件查询查新付费列表", notes = "根据条件查询查新付费列表")
    @PermissionRequired(perms = "innovation:checkPayment:search")
    public PageModel<List<CheckPaymentModel>> getList(QueryCheckModel query) throws OwnerException {
        return checkPaymentService.getList(query,getUserInfo(),getDataPerm());
    }

    @GetMapping("/getInfo")
    @SystemLog(description = "查询查新付费信息")
    @ApiOperation(value = "查询查新付费信息", notes = "查询查新付费信息")
    public CheckPaymentModel getInfo(Integer id) throws OwnerException {
        return checkPaymentService.getInfo(id,getUserInfo());
    }
    @GetMapping("/getProjectList")
    @SystemLog(description = "根据客户查询项目列")
    @ApiOperation(value = "根据客户查询项目列", notes = "根据客户查询项目列")
    public List<ReportProjectModel> getProjectList(@RequestParam("customerId")Integer customerId,
                                                   @RequestParam("year")Integer year){
        return checkPaymentService.getProjectList(customerId, year);
    }
    @GetMapping("/getCheckInstList")
    @SystemLog(description = "查询查新机构")
    @ApiOperation(value = "查询查新机构", notes = "查询查新机构")
    public List<CheckMiniModel> getCheckInstList(String checkInstName){
        return checkPaymentService.getCheckInstList(checkInstName);
    }

    @PostMapping("/addCheckPayment")
    @SystemLog(description = "添加查新付费", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "添加查新付费", notes = "添加查新付费")
    @PermissionRequired(perms = "innovation:checkPayment:add")
    public Boolean addCheckPayment(@RequestBody @Validated AddPaymentModel model) throws OwnerException {
        return checkPaymentService.addCheckPayment(model,getUserInfo());
    }

    @PostMapping("/editCheckPayment")
    @SystemLog(description = "编辑查新付费", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "编辑查新付费", notes = "编辑查新付费")
    @PermissionRequired(perms = "innovation:checkPayment:edit")
    public Boolean editCheckPayment(@RequestBody @Validated AddPaymentModel model) throws OwnerException {
        return checkPaymentService.editCheckPayment(model,getUserInfo());
    }

    @PostMapping("/delCheckPayment")
    @SystemLog(description = "删除查新付费", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "删除查新付费", notes = "删除查新付费")
    @PermissionRequired(perms = "innovation:checkPayment:del")
    public Boolean delCheckPayment(@RequestBody @Validated BatchDeleteModel model) throws OwnerException {
        return checkPaymentService.delCheckPayment(model.getIds());
    }

    @PostMapping("/submit")
    @SystemLog(description = "提交查新付费", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "提交查新付费", notes = "提交查新付费")
    @PermissionRequired(perms = "innovation:checkPayment:submit")
    public Boolean submit(@RequestBody @Validated AddPaymentModel model) throws OwnerException {
        return checkPaymentService.submit(model,getUserInfo());
    }

    @PostMapping("/audit")
    @SystemLog(description = "审核查新付费", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "审核查新付费", notes = "审核查新付费")
    @PermissionRequired(perms = "innovation:checkPayment:audit")
    public Boolean audit(@RequestBody PaymentAuditModel model) throws Exception {
        UserInfo userInfo = getUserInfo();
        if (model.getNodeNumber().equals(4)&& model.getPass()){
            checkPaymentService.addPaymentFile(model.getId(), userInfo, model.getPayType(), model.getPaymentFile());
        }
        FormAuditModel auditModel = new FormAuditModel();
        auditModel.setInstanceId(model.getInstanceId());
        auditModel.setPass(model.getPass());
        auditModel.setSuggestion(model.getSuggestion());
        return flowInstanceService.audit(auditModel, userInfo);
    }
}
