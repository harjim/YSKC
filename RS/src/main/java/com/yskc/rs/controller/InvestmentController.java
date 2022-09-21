package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.models.BatchDeleteModel;
import com.yskc.rs.models.tech.*;
import com.yskc.rs.service.InvestmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/3/19 9:05
 * @Description:
 */
@Api(tags = "投资清单", value = "投资清单")
@CrossOrigin(origins = "*", allowedHeaders = "", allowCredentials = "true", methods = {})
@RestController
@RequestMapping("/api/inverstment")
public class InvestmentController extends BaseController{

    @Autowired
    private InvestmentService inverstmentService;

    @GetMapping("/getList")
    @PermissionRequired(perms = "tech:beian:investments:search")
    @ApiOperation(notes = "获取投资列表", value = "获取投资列表")
    public PageModel<List<InvestmentModel>> getList(QueryInverstmentModel query) throws OwnerException {
        return inverstmentService.getList(getUserInfo().getCompanyId(), query);
    }


    @GetMapping("/getInvoices")
    @ApiOperation(notes = "获取设备发票列表", value = "获取设备发票列表")
    public List<InvoiceModel> getInvoices(QueryInvoiceModel query) throws OwnerException {
        return inverstmentService.getInvoices(getUserInfo().getCompanyId(), query);
    }

    @PostMapping("/delInvoiceDetail")
    @SystemLog(description = "删除投资清单关联的发票设备",mode = SystemLog.SAVE_DB)
    @ApiOperation(notes = "删除投资清单关联的发票设备", value = "删除投资清单关联的发票设备")
    public Boolean delInvoiceDetail(@RequestBody @Validated List<InvoiceModel> models) throws OwnerException{
        return inverstmentService.delInvoiceDetail(models,getUserInfo().getCompanyId());
    }

    @PostMapping("/delInvestment")
    @PermissionRequired(perms = "tech:beian:investments:del")
    @SystemLog(description = "删除投资清单",mode = SystemLog.SAVE_DB)
    @ApiOperation(notes = "删除投资清单", value = "删除投资清单")
    public Boolean delInvestment(@RequestBody @Validated List<InvestmentModel> models) throws OwnerException{
        return inverstmentService.delInvestment(models);
    }

    @GetMapping("/getInvoiceDetails")
    @ApiOperation(notes = "获取发票列表", value = "获取发票列表")
    @PermissionRequired(perms = "tech:beian:investments:relatedInvoice")
    public PageModel<List<InvoiceModel>> getInvoiceDetails(QueryInvoiceModel query) throws OwnerException {
        return inverstmentService.getInvoiceDetails(getUserInfo().getCompanyId(), query);
    }

    @GetMapping("/getInvoiceInfo")
    @ApiOperation(notes = "获取发票信息", value = "获取发票信息")
    public InvoiceInfoModel getInvoiceInfo(@Param("invoiceId")Integer invoiceId) throws OwnerException {
        return inverstmentService.getInvoiceInfo(getUserInfo().getCompanyId(),invoiceId);
    }

    @PostMapping("/saveInvoice")
    @PermissionRequired(perms = "tech:beian:investments:saveInvocie")
    @SystemLog(description = "保存发票信息",mode = SystemLog.SAVE_DB)
    @ApiOperation(notes = "保存发票信息", value = "保存发票信息")
    public Boolean saveInvoice(@RequestBody @Validated InvoiceInfoModel model) throws OwnerException{
        return inverstmentService.saveInvoice(model,getUserInfo());
    }



    @GetMapping("/getContracts")
    @ApiOperation(notes = "获取合同设备信息列表", value = "获取合同信息列表")
    public List<ContractInfoModel> getContracts(QueryContractModel query) throws OwnerException {
        return inverstmentService.getContracts(getUserInfo().getCompanyId(), query);
    }

    @PostMapping("/delContractDetail")
    @SystemLog(description = "删除合同设备",mode = SystemLog.SAVE_DB)
    @ApiOperation(notes = "删除合同设备", value = "删除合同设备")
    public Boolean delContractDetail(@RequestBody @Validated List<ContractInfoModel> models) throws OwnerException{
        return inverstmentService.delContractDetail( models);
    }

    @GetMapping("/getContractDetail")
    @PermissionRequired(perms = "tech:beian:investments:relatedContract")
    @ApiOperation(notes = "获取合同列表", value = "获取合同信息列表")
    public PageModel<List<ContractInfoModel>> getContractDetail(QueryContractModel query) throws OwnerException {
        return inverstmentService.getContractDetail(getUserInfo().getCompanyId(), query);
    }

    @GetMapping("/getContractInfo")
    @ApiOperation(notes = "获取合同信息", value = "获取合同信息")
    public ContractModel getContractInfo(@Param("contractId") Integer contractId) throws OwnerException {
        return inverstmentService.getContractInfo(contractId,getUserInfo().getCompanyId());
    }

//    @PostMapping("/relatedContract")
//    @SystemLog(description = "关联合同设备到投资清单",mode = SystemLog.SAVE_DB)
//    @ApiOperation(notes = "关联合同设备到投资清单", value = "关联合同设备到投资清单")
//    public Boolean relatedContract(@RequestBody @Validated List<ContractInfoModel> models) throws OwnerException{
//        return inverstmentService.relatedContract(models,getUserInfo());
//    }


    @PostMapping("/saveContract")
    @PermissionRequired(perms = "tech:beian:investments:saveContract")
    @SystemLog(description = "保存发票信息",mode = SystemLog.SAVE_DB)
    @ApiOperation(notes = "保存发票信息", value = "保存发票信息")
    public Boolean saveContract(@RequestBody @Validated ContractModel model) throws OwnerException{
        return inverstmentService.saveContract(model,getUserInfo());
    }

    @GetMapping("/getPayments")
    @ApiOperation(notes = "获取投资清单的投资付款单（原银行水单）", value = "获取投资清单的投资付款单（原银行水单）")
    public List<PaymentModel> getPayments(@Param("investmentId") Integer investmentId) throws OwnerException {
        return inverstmentService.getPayments(investmentId,getUserInfo().getCompanyId());
    }

    @GetMapping("/getPaymentInfo")
    @ApiOperation(notes = "获取可添加的投资付款单（原银行水单）列表", value = "获取可添加的投资付款单（原银行水单）列表")
    public PageModel<List<PaymentModel>> getPaymentInfo(QueryBankReceiptModel query) throws OwnerException {
        return inverstmentService.getPaymentInfo(query,getUserInfo().getCompanyId());
    }

    @PostMapping("/savePayment")
    @PermissionRequired(perms = "tech:beian:investments:editBankReceipt")
    @SystemLog(description = "保存投资付款单（原银行水单）",mode = SystemLog.SAVE_DB)
    @ApiOperation(notes = "保存投资付款单（原银行水单）", value = "保存投资付款单（原银行水单）")
    public Boolean savePayment(@RequestBody @Validated PaymentModel model) throws OwnerException{
        return inverstmentService.savePayment(model,getUserInfo());
    }

    @PostMapping("/delPayment")
    @SystemLog(description = "删除投资清单关联投资付款单（原银行水单）",mode = SystemLog.SAVE_DB)
    @ApiOperation(notes = "删除投资清单关联投资付款单（原银行水单）", value = "删除投资清单关联投资付款单（原银行水单）")
    public Boolean delPayment(@RequestBody @Validated List<ContractInfoModel> models) throws OwnerException{
        return inverstmentService.delPayment(models);
    }

    @PostMapping("/saveInvestment")
    @PermissionRequired(perms = "tech:beian:investments:add,tech:beian:investments:edit,tech:beian:investments:addNameplate")
    @SystemLog(description = "保存投资清单",mode = SystemLog.SAVE_DB)
    @ApiOperation(notes = "保存投资清单", value = "保存投资清单")
    public Boolean saveInvestment(@RequestBody @Validated AddInvestmentModel model) throws OwnerException{
        return inverstmentService.saveInvestment(model,getUserInfo());
    }

    @PostMapping("/delInvoiceInfo")
    @SystemLog(description = "删除发票详情",mode = SystemLog.SAVE_DB)
    @ApiOperation(notes = "删除发票详情", value = "删除发票详情")
    public Boolean delInvoiceInfo(@RequestBody @Validated List<InvoiceDetailModel> models) throws OwnerException{
        return inverstmentService.delInvoiceInfo(models);
    }

    @PostMapping("/delContractInfo")
    @SystemLog(description = "删除合同详情",mode = SystemLog.SAVE_DB)
    @ApiOperation(notes = "删除合同详情", value = "删除合同详情")
    public Boolean delContractInfo(@RequestBody @Validated List<ContractDetailModel> models) throws OwnerException{
        return inverstmentService.delContractInfo(models);
    }

    @PostMapping("/delPaymentInfo")
    @SystemLog(description = "删除投资付款单（原银行水单）",mode = SystemLog.SAVE_DB)
    @ApiOperation(notes = "删除投资付款单（原银行水单）", value = "删除投资付款单（原银行水单）")
    public Boolean delPaymentInfo(@RequestBody @Validated BatchDeleteModel model) throws OwnerException{
        return inverstmentService.delPaymentInfo(model.getIds());
    }
    @GetMapping("/verifyInvoice")
    @ApiOperation(notes = "验证发票号唯一", value = "验证发票号唯一")
    public Boolean verifyInvoice(String invoiceNo) throws OwnerException{
        return inverstmentService.verifyInvoice(getUserInfo().getCompanyId(),invoiceNo);
    }

    @GetMapping("/verifyContract")
    @ApiOperation(notes = "验证合同号唯一", value = "验证合同号唯一")
    public Boolean verifyContract(String contractNo) throws OwnerException{
        return inverstmentService.verifyContract(getUserInfo().getCompanyId(),contractNo);
    }

    @GetMapping("/getCompanyName")
    @ApiOperation(notes = "查询发票开具方、合同签订对象、投资付款单（原银行水单）收款单位", value = "查询发票开具方、合同签订对象、投资付款单（原银行水单）收款单位")
    public List<String> getCompanyName(@RequestParam(value = "companyName",required = true) String companyName) throws OwnerException{
        return inverstmentService.getCompanyName(companyName,getUserInfo().getCompanyId());
    }

    @GetMapping("/getDeviceName")
    @ApiOperation(notes = "获取设备名称", value = "获取设备名称")
    public List<String> getDeviceName(@RequestParam(value = "deviceName",required = true) String deviceName) throws OwnerException{
        return inverstmentService.getDeviceName(deviceName,getUserInfo().getCompanyId());
    }

}
