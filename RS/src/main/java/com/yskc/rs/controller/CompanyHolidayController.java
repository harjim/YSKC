package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.models.company.CompanyHolidayModel;
import com.yskc.rs.service.CompanyHolidayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: zhangdingfu
 * @CreateTime: 2021-01-05 08:57
 * @Description: 公司节假日接口层
 */
@Api(tags = "公司节假日接口层", value = "公司节假日接口层")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/companyHoliday")
public class CompanyHolidayController extends BaseController {

    @Autowired
    private CompanyHolidayService companyHolidayService;

    @GetMapping("/getHolidays")
    @ApiOperation(value = "获取节假日", notes = "获取节假日")
    @PermissionRequired(perms = "company:holiday:search")
    public List<CompanyHolidayModel> getHolidays(Integer year) throws OwnerException {
        if (null == year) {
            throw new OwnerException("未获取到年份。");
        }
        return companyHolidayService.getHolidays(year, getUserInfo().getCompanyId());
    }

    @SystemLog(description = "保存节假日", mode = SystemLog.SAVE_DB)
    @PostMapping("/saveHoliday")
    @ApiOperation(value = "保存节假日", notes = "保存节假日")
    @PermissionRequired(perms = "company:holiday:edit")
    public Boolean saveHoliday(@RequestBody List<CompanyHolidayModel> list) throws OwnerException {
        return companyHolidayService.saveHoliday(list, getUserInfo());
    }
}
