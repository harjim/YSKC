package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.flowInstance.FeeFlowInstanceLogModel;
import com.yskc.ms.models.projectAudit.*;
import com.yskc.ms.models.projectsummary.QuerySummaryMonthModel;
import com.yskc.ms.models.projectsummary.SummaryDataMonthModel;
import com.yskc.ms.models.rdfunds.*;
import com.yskc.ms.service.ms.FlowInstanceLogService;
import com.yskc.ms.service.ms.RdFeeAuditService;
import com.yskc.ms.service.rs.SummaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @program: ms
 * @description: 财务审批control层
 * @author: cyj
 * @create: 2022-04-22 09:46
 **/
@Api(tags = "财务审批接口", value = "财务审批接口")
@RestController
@RequestMapping("/api/rdFeeAudit")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
public class RdFeeAuditController extends BaseController  {
    @Autowired
    private SummaryService summaryService;
    @Autowired
    private FlowInstanceLogService flowInstanceLogService;
    @Autowired
    private RdFeeAuditService rdFeeAuditService;

    @GetMapping("/getList")
    @ApiOperation(notes = "获取财务审批列表", value = "获取财务审批列表")
    @SystemLog(description = "获取财务审批列表")
    @PermissionRequired(perms = "innovation:rdFeeAudit:search")
    public PageModel getList(QueryFinaAuditModel query) throws OwnerException {
        return rdFeeAuditService.getList(query,this.getDataPerm());
    }

    /**
     *  按月份获取费用详情
     * @param query
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getMonthFee")
    @ApiOperation(notes = "按月份费用汇总", value = "按月份费用汇总")
    @SystemLog(description = "按月份费用汇总")
    @PermissionRequired(perms = "innovation:rdFeeAudit:detail")
    public List<SummaryDataMonthModel> getMonthFee(QuerySummaryMonthModel query) throws OwnerException {
        return summaryService.getMonthFee(query);
    }

    /**
     *  审核日志查询
     * @param instanceId
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getAuditLog")
    @ApiOperation(notes = "审核日志查询", value = "审核日志查询")
    @SystemLog(description = "审核日志查询")
    /*@PermissionRequired(perms = "innovation:rdFeeAudit:auditLog,customer:serviceApply:audit," +
            "customer:workRecord:log,contract:contract:log")*/
    public List<FeeFlowInstanceLogModel> getAuditLog(Integer instanceId) {
        return flowInstanceLogService.getAuditLog(instanceId);
    }


    @GetMapping("/getRdFunds")
    @ApiOperation(notes = "RD列表查询", value = "RD列表查询")
    @SystemLog(description = "RD列表查询")
    @PermissionRequired(perms = "innovation:rdFeeAudit:detail")
    public List<? extends RdFinaFundsModel> getRdFunds(QueryRdFundsModel model) throws Exception {
        model.setUserId(getUserInfo().getId());
        return rdFeeAuditService.getRdFunds(model);
    }

    @GetMapping("/getEquipmentFees")
    @ApiOperation(notes = "设备归集费用查询", value = "设备归集费用查询")
    @SystemLog(description = "设备归集费用查询")
    @PermissionRequired(perms = "innovation:rdFeeAudit:detail")
    public PageModel<List<EquipmentFeesModel>> getEquipmentFees(QueryRdFeeModel model){
        return rdFeeAuditService.getEquipmentFees(model);
    }

    @GetMapping("/getEnergyFees")
    @ApiOperation(notes = "动力归集费用查询", value = "动力归集费用查询")
    @SystemLog(description = "动力归集费用查询")
    @PermissionRequired(perms = "innovation:rdFeeAudit:detail")
    public PageModel<List<EnergyFeesModel>> getEnergyFees(QueryRdFeeModel model){
        return rdFeeAuditService.getEnergyFees(model);
    }

    @GetMapping("/getDesignFees")
    @ApiOperation(notes = "设计归集费用查询", value = "设计归集费用查询")
    @SystemLog(description = "设计归集费用查询")
    @PermissionRequired(perms = "innovation:rdFeeAudit:detail")
    public PageModel<List<DesignFeesModel>> getDesignFees(QueryRdFeeModel model){
        return rdFeeAuditService.getDesignFees(model);
    }

    @GetMapping("/getEquipmentPowerFees")
    @ApiOperation(notes = "设备用电归集费用查询", value = "设备用电归集费用查询")
    @SystemLog(description = "设备用电归集费用查询")
    @PermissionRequired(perms = "innovation:rdFeeAudit:detail")
    public PageModel<List<EquipmentPowerFeesModel>> getEquipmentPowerFees(QueryRdFeeModel model) {
        return rdFeeAuditService.getEquipmentPowerFees(model);
    }

    /**
     * 据项目月份返回人员数据
     * @param query
     * @return
     */
    @GetMapping("/getEmployeeFees")
    @ApiOperation(notes = "项目当月人员费用列表", value = "项目当月人员费用列表")
    @SystemLog(description = "项目当月人员费用列表")
    @PermissionRequired(perms = "innovation:rdFeeAudit:detail")
    public PageModel<List<RdFeeEmployeeModel>> getEmployeeFees(QueryRdFeeModel query) {

        return rdFeeAuditService.getEmployeeFees(query);
    }

    /**
     * 据项目月份返回材料数据
     * @param query
     * @return
     */
    @GetMapping("/getMaterialFees")
    @ApiOperation(notes = "项目当月材料费用列表", value = "项目当月材料费用列表")
    @SystemLog(description = "项目当月材料费用列表")
    @PermissionRequired(perms = "innovation:rdFeeAudit:detail")
    public PageModel<List<RdFeeMaterialModel>> getMaterialFees(QueryRdFeeModel query) {

        return rdFeeAuditService.getMaterialFees(query);
    }
    /**
     * 据项目月份返回其他数据
     * @param query
     * @return
     */
    @GetMapping("/getInspectionFees")
    @ApiOperation(notes = "项目当月其他费用列表", value = "项目当月其他费用列表")
    @SystemLog(description = "项目当月其他费用列表")
    @PermissionRequired(perms = "innovation:rdFeeAudit:detail")
    public PageModel<List<RdFeeInspectionModel>> getInspectionFees(QueryRdFeeModel query) {

        return rdFeeAuditService.getInspectionFees(query);
    }

    /**
     * 用户可审核数统计
     * @param query
     * @return
     */
    @GetMapping("/getAuditCnt")
    @ApiOperation(notes = "用户可审核数统计", value = "用户可审核数统计")
    @SystemLog(description = "用户可审核数统计")
    @PermissionRequired(perms = "innovation:rdFeeAudit:detail")
    public Map<String,Object> getAuditCnt(QueryRdFeeModel query) throws OwnerException {
        Integer userId = getUserInfo().getId();
        return rdFeeAuditService.getAuditCnt(query,userId);
    }

    @GetMapping("/getRdFund")
    @ApiOperation(notes = "获取归集审批信息", value = "获取归集审批信息")
    @SystemLog(description = "获取归集审批信息")
    @PermissionRequired(perms = "innovation:rdFeeAudit:detail")
    public List<? extends RdFinaFundsModel> getRdFund(QueryRdFundsModel model) throws OwnerException {
        model.setUserId(getUserInfo().getId());
        return rdFeeAuditService.getRdFund(model);
    }
    @GetMapping("/getRdFundsByMonth")
    @ApiOperation(notes = "按月获取归集费用", value = "按月获取归集费用")
    @SystemLog(description = "按月获取归集费用")
    @PermissionRequired(perms = "innovation:rdFeeAudit:detail")
    public List<RdFundsAllModel> getRdFundsByMonth(QueryRdFundsModel model) throws OwnerException {
//        model.setUserId(getUserInfo().getId());
        return rdFeeAuditService.getRdFundByMonth(model);
    }
}
