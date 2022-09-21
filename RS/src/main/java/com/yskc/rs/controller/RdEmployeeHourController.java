package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.models.rdemployeehour.QueryRdEmployeeHourModel;
import com.yskc.rs.models.rdemployeehour.RdEmployeeHourModel;
import com.yskc.rs.service.RdEmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: zhangdingfu
 * @CreateTime: 2022-03-08 08:58
 * @Description: 研发人员投入工时
 */
@Api(tags = "研发人员投入工时接口", value = "研发人员投入工时接口")
@CrossOrigin(origins = "*", allowedHeaders = "", allowCredentials = "true", methods = {})
@RequestMapping("/api/rdEmployeeHour")
@RestController
public class RdEmployeeHourController extends BaseController {

    @Autowired
    private RdEmployeeService rdEmployeeService;


    @GetMapping("/getList")
    @PermissionRequired(perms = "rdorg:rdEmployeeHour:search")
    @ApiOperation(notes = "获取研发人员投入工时列表", value = "获取研发人员投入工时列表")
    public PageModel<List<RdEmployeeHourModel>> getList(QueryRdEmployeeHourModel query) throws OwnerException {
        return rdEmployeeService.getRdEmployeeHours(query, getUserInfo().getCompanyId());
    }

}
