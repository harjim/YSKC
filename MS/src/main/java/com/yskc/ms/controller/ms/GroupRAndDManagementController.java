package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.ms.models.GroupRAndDManagementModel;
import com.yskc.ms.service.ms.GroupRAndDManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @DateTime: 2022/1/20 13:39
 * @Description:集团研发管理接口
 * @author: hsx
 */

@RestController
@RequestMapping("/api/groupRAndDManagement")
@Api(tags = "集团研发管理接口", value = "集团研发管理接口")
@CrossOrigin(origins = "*", allowCredentials = "", allowedHeaders = "true", methods = {})
public class GroupRAndDManagementController extends BaseController{

    @Autowired
    private GroupRAndDManagementService groupRAndDManagementService;

    @GetMapping("/getTableData")
    @PermissionRequired(perms = "innovation:groupRdManagement:getData")
    @ApiOperation(notes = "获取集团研发管理数据",value = "获取集团研发管理数据")
    public Map<String, Object> getTableData(Date date)throws Exception {
        return groupRAndDManagementService.getTableData(date);
    }
}
