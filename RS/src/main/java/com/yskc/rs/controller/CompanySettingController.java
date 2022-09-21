package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.models.company.CompanySettingModel;
import com.yskc.rs.service.CompanySettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: zhangdingfu
 * @CreateTime: 2021-02-03 11:32
 * @Description: 客户设置controller层
 */
@Api(tags = "系统设置-客户设置接口", value = "系统设置-客户设置接口")
@CrossOrigin(origins = "*", allowedHeaders = "", allowCredentials = "true", methods = {})
@RestController
@RequestMapping("/api/companySetting")
public class CompanySettingController extends BaseController {

    @Autowired
    private CompanySettingService companySettingService;

    @GetMapping("/getSetting")
    @PermissionRequired(perms = "company:setting:checkDate:view,company:setting:punchCard:view,company:setting:percentage:view,company:setting:docNo:view")
    @ApiOperation(value = "获取系统设置-客户设置", notes = "获取系统设置-客户设置")
    public CompanySettingModel getSetting() throws OwnerException {
        return companySettingService.getSetting(getUserInfo().getCompanyId());
    }

    @SystemLog(description = "保存工资核算开始日期", mode = SystemLog.SAVE_DB)
    @PostMapping("/accountPeriodSave")
    @PermissionRequired(perms = "company:setting:checkDate:edit")
    @ApiOperation(value = "保存工资核算开始日期", notes = "保存工资核算开始日期")
    public Boolean accountPeriodSave(@RequestBody Map<String, Object> map) throws OwnerException {
        return saveSetting("accountPeriod", map);
    }

    @PermissionRequired(perms = "company:setting:punchCard:edit")
    @SystemLog(description = "保存小程序研发打卡范围", mode = SystemLog.SAVE_DB)
    @PostMapping("/miniProgramSave")
    @ApiOperation(value = "保存小程序研发打卡范围", notes = "保存小程序研发打卡范围")
    public Boolean miniProgramSave(@RequestBody Map<String, Object> map) throws OwnerException {
        return saveSetting("miniProgram", map);
    }

    @PermissionRequired(perms = "company:setting:percentage:edit")
    @SystemLog(description = "保存优惠明细表加计扣除比例", mode = SystemLog.SAVE_DB)
    @PostMapping("/rdRatioSave")
    @ApiOperation(value = "保存优惠明细表加计扣除比例", notes = "保存优惠明细表加计扣除比例")
    public Boolean rdRatioSave(@RequestBody Map<String, Object> map) throws OwnerException {
        return saveSetting("rdRatio", map);
    }

    @PermissionRequired(perms = "company:setting:docNo:edit")
    @SystemLog(description = "保存过程文档编号", mode = SystemLog.SAVE_DB)
    @PostMapping("/documentNoSave")
    @ApiOperation(value = "保存过程文档编号", notes = "保存过程文档编号")
    public Boolean documentNoSave(@RequestBody Map<String, Object> map) throws OwnerException {
        return saveSetting("documentNo", map);
    }
    @PermissionRequired(perms = "company:setting:hourBit:edit")
    @SystemLog(description = "保存工时位数", mode = SystemLog.SAVE_DB)
    @PostMapping("/hourBitSave")
    @ApiOperation(value = "保存工时位数", notes = "保存工时位数")
    public Boolean hourBitSave(@RequestBody Map<String, Object> map) throws OwnerException {
        return saveSetting("hourBit", map);
    }

    public Boolean saveSetting(String key, Map<String, Object> map) throws OwnerException {
        return companySettingService.saveSetting(key, map, getUserInfo());
    }

    @GetMapping("/getAccountPeriod")
    @ApiOperation(value = "获取账期区间", notes = "获取账期区间")
    public Map<String, Object> getAccountPeriod() throws OwnerException {
        return companySettingService.getSetting(getUserInfo().getCompanyId()).getAccountPeriod();
    }
    @GetMapping("/getEquipmentHourBit")
    @ApiOperation(value = "获取设备工时位数", notes = "获取设备工时位数")
    public Integer getEquipmentHourBit() throws OwnerException {
        return companySettingService.getEquipmentHourBit(getUserInfo().getCompanyId());
    }
    @GetMapping("/getEmployeeHourBit")
    @ApiOperation(value = "获取人员工时位数", notes = "获取人员工时位数")
    public Integer getEmployeeHourBit() throws OwnerException {
        return companySettingService.getEmployeeHourBit(getUserInfo().getCompanyId());
    }

}
