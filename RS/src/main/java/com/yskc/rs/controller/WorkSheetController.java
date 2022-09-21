package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.voucher.VoucherAccount;
import com.yskc.rs.models.voucher.VoucherInfo;
import com.yskc.rs.models.workSheet.*;
import com.yskc.rs.service.WorkSheetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 工单接口
 *
 * @author huronghua
 */
@Api(tags = "工单接口", value = "工单接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/workSheet")
public class WorkSheetController extends BaseController {
    @Autowired
    private WorkSheetService workSheetService;

    /**
     * 获取研发人员工单
     *
     * @param workSheetQuery
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getStaffWorkSheetList")
    @ApiOperation(value = "获取研发人员工单", notes = "获取研发人员工单", response = String.class)
    public List<WorkSheetModel> getStaffWorkSheetList(WorkSheetQuery workSheetQuery) throws OwnerException {
        return workSheetService.getStaffWorkSheetList(getUserInfo(), workSheetQuery);
    }
    /**
     * 获取研发人员凭证
     * @param workSheetQuery
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getWorkVoucherList")
    @PermissionRequired(perms = "rdvoucher:sheets:search")
    @ApiOperation(value = "获取研发人员凭证", notes = "获取研发人员工单", response = List.class)
    public List<VoucherInfo> getWorkVoucherList(WorkSheetQuery workSheetQuery) throws OwnerException {
        return workSheetService.getWorkVoucherList(getUserInfo(), workSheetQuery);
    }

    
    /** 
     * getMonthWorkSheetList
     * @param workSheetQuery
     * @return List<MonthWorkSheetItem>
     * @throws OwnerException
     */
    @GetMapping("/getMonthWorkSheetList")
    @ApiOperation(value = "获取研发人员工单", notes = "获取研发人员工单", response = String.class)
    public List<WorkSheetMonthModel> getMonthWorkSheetList(WorkSheetQuery workSheetQuery) throws OwnerException {
        return workSheetService.getMonthWorkSheetList(getUserInfo(), workSheetQuery);
    }

    
    /** 
     * getEquipmentWorkSheetList
     * @param workSheetQuery
     * @return List<WorkSheetModel>
     * @throws OwnerException
     */
    @GetMapping("/getEquipmentWorkSheetList")
    @ApiOperation(value = "获取日研发设备工单", notes = "获取日研发设备工单")
    public List<WorkSheetModel> getEquipmentWorkSheetList(WorkSheetQuery workSheetQuery) throws OwnerException {
        return workSheetService.getEquipmentWorkSheetList(getUserInfo(), workSheetQuery);
    }

    
    /** 
     * 获取月研发设备工单
     * @param workSheetQuery
     * @return List<WorkSheetMonthModel>
     * @throws OwnerException
     */
    @GetMapping("/getEquipmentWorkSheetMonthList")
    @ApiOperation(value = "获取月研发设备工单", notes = "获取月研发设备工单")
    public List<WorkSheetMonthModel> getEquipmentWorkSheetMonthList(WorkSheetQuery workSheetQuery) throws OwnerException {
        return workSheetService.getEquipmentWorkSheetMonthList(getUserInfo(), workSheetQuery);
    }

    
    /** 
     * 获取研发设备凭证
     * @param workSheetQuery
     * @return List<VoucherModel>
     * @throws OwnerException
     */
    @GetMapping("/getEquipmentVoucherList")
    @ApiOperation(value = "获取研发设备凭证", notes = "获取月研发设备工单")
    public List<VoucherModel> getEquipmentVoucherList(WorkSheetQuery workSheetQuery) throws OwnerException {
        return workSheetService.getEquipmentVoucherList(getUserInfo(), workSheetQuery);
    }

    
    /** 
     * 获取日能源工单
     * @param workSheetQuery
     * @return List<WorkSheetModel>
     * @throws OwnerException
     */
    @GetMapping("/getEnergyWorkSheetList")
    @ApiOperation(value = "获取日能源工单", notes = "获取日能源工单")
    public List<WorkSheetModel> getEnergyWorkSheetList(WorkSheetQuery workSheetQuery) throws OwnerException {
        return workSheetService.getEnergyWorkSheetList(getUserInfo(), workSheetQuery);
    }

    
    /** 
     * 获取月能源工单
     * @param workSheetQuery
     * @return List<WorkSheetMonthModel>
     * @throws OwnerException
     */
    @GetMapping("/getEnergyWorkSheetMonthList")
    @ApiOperation(value = "获取月能源工单", notes = "获取月能源工单")
    public List<WorkSheetMonthModel> getEnergyWorkSheetMonthList(WorkSheetQuery workSheetQuery) throws OwnerException {
        return workSheetService.getEnergyWorkSheetMonthList(getUserInfo(), workSheetQuery);
    }

    
    /** 
     * 获取能源凭证
     * @param workSheetQuery
     * @return List<VoucherModel>
     * @throws OwnerException
     */
    @GetMapping("/getEnergyVoucherList")
    @ApiOperation(value = "获取能源凭证", notes = "获取能源凭证")
    public List<VoucherModel> getEnergyVoucherList(WorkSheetQuery workSheetQuery) throws OwnerException {
        return workSheetService.getEnergyVoucherList(getUserInfo(), workSheetQuery);
    }

    
    /** 
     * 获取工单
     * @param workSheetQuery
     * @return List<WorkSheetModel>
     * @throws OwnerException
     */
    @GetMapping("/getInspectionList")
    @ApiOperation(value = "获取工单", notes = "获取工单")
    public List<WorkSheetModel> getInspectionList(WorkSheetQuery workSheetQuery) throws OwnerException {
        return workSheetService.getInspectionList(getUserInfo(), workSheetQuery);
    }

    
    /** 
     * 获取月工单
     * @param workSheetQuery
     * @return List<WorkSheetMonthModel>
     * @throws OwnerException
     */
    @GetMapping("/getInspectionListByMonth")
    @ApiOperation(value = "获取月工单", notes = "获取月工单")
    public List<WorkSheetMonthModel> getInspectionListByMonth(WorkSheetQuery workSheetQuery) throws OwnerException {
        return workSheetService.getInspectionListByMonth(getUserInfo(), workSheetQuery);
    }


    
    /** 
     * 获取费用凭证
     * @param workSheetQuery
     * @return List<VoucherModel>
     * @throws OwnerException
     */
    @GetMapping("/getInspectionVoucherList")
    @ApiOperation(value = "获取费用凭证", notes = "获取费用凭证")
    public List<VoucherModel> getInspectionVoucherList(WorkSheetQuery workSheetQuery) throws OwnerException {
        return workSheetService.getInspectionVoucherList(getUserInfo(), workSheetQuery);
    }

    
    /** 
     * 获取设计费用工单
     * @param workSheetQuery
     * @return List<WorkSheetModel>
     * @throws OwnerException
     */
    @GetMapping("/getDesignList")
    @ApiOperation(value = "获取设计费用工单", notes = "获取设计费用工单")
    public List<WorkSheetModel> getDesignList(WorkSheetQuery workSheetQuery) throws OwnerException {
        return workSheetService.getDesignList(getUserInfo(), workSheetQuery);
    }

    
    /** 
     * 获取月设计费用工单
     * @param workSheetQuery
     * @return List<WorkSheetMonthModel>
     * @throws OwnerException
     */
    @GetMapping("/getDesignListByMonth")
    @ApiOperation(value = "获取月设计费用工单", notes = "获取月设计费用工单")
    public List<WorkSheetMonthModel> getDesignListByMonth(WorkSheetQuery workSheetQuery) throws OwnerException {
        return workSheetService.getDesignListByMonth(getUserInfo(), workSheetQuery);
    }

    
    /** 
     * 获取设计费用凭证
     * @param workSheetQuery
     * @return List<VoucherModel>
     * @throws OwnerException
     */
    @GetMapping("/getDesignListVoucherList")
    @ApiOperation(value = "获取设计费用凭证", notes = "获取设计费用凭证")
    public List<VoucherModel> getDesignListVoucherList(WorkSheetQuery workSheetQuery) throws OwnerException {
        return workSheetService.getDesignListVoucherList(getUserInfo(), workSheetQuery);
    }

    
    /** 
     * 获取材料工单
     * @param workSheetQuery
     * @return List<WorkSheetModel>
     * @throws OwnerException
     */
    @GetMapping("/getMaterialWorkSheetList")
    @ApiOperation(value = "获取材料工单", notes = "获取材料工单")
    public List<WorkSheetModel> getMaterialWorkSheetList(WorkSheetQuery workSheetQuery) throws OwnerException {
        return workSheetService.getMaterialWorkSheetList(getUserInfo(), workSheetQuery);
    }

    
    /** 
     * 获取月材料工单
     * @param workSheetQuery
     * @return List<WorkSheetMonthModel>
     * @throws OwnerException
     */
    @GetMapping("/getMaterialWorkSheetMonthList")
    @ApiOperation(value = "获取月材料工单", notes = "获取月材料工单")
    public List<WorkSheetMonthModel> getMaterialWorkSheetMonthList(WorkSheetQuery workSheetQuery) throws OwnerException {
        return workSheetService.getMaterialWorkSheetMonthList(getUserInfo(), workSheetQuery);
    }

    
    /**
     * 获取材料凭证
     * 
     * @param workSheetQuery
     * @return List<VoucherModel>
     * @throws OwnerException
     */
    @GetMapping("/getMaterialVoucherList")
    @ApiOperation(value = "获取材料凭证", notes = "获取材料凭证")
    public List<VoucherModel> getMaterialVoucherList(WorkSheetQuery workSheetQuery) throws OwnerException {
        return workSheetService.getMaterialVoucherList(getUserInfo(), workSheetQuery);
    }

    /**
     * 保存凭证科目
     * @param voucherAccount
     * @return
     * @throws OwnerException
     */
    @PostMapping("/saveVoucherAccount")
    @PermissionRequired(perms = "rdvoucher:sheets:edit")
    @SystemLog(description = "保存凭证科目",mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "保存凭证科目", notes = "保存凭证科目", response = Boolean.class)
    public Boolean saveVoucherAccount(@RequestBody @Validated VoucherAccount voucherAccount) throws OwnerException {
        UserInfo userInfo = this.getUserInfo();
        return workSheetService.saveVoucherAccount(userInfo,voucherAccount);
    }

}
