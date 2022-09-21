package com.yskc.rs.controller;

import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.fieldconfig.FieldConfigModel;
import com.yskc.rs.service.FieldConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * @program: rs
 * @description: 配置明细接口
 * @author: wjy
 * @create: 2022-06-14 10:45
 **/
@Api(tags = "配置明细接口", value = "配置明细接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/fieldConfig")
public class FieldConfigController extends BaseController {
    @Autowired
    private FieldConfigService fieldConfigService;
    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "自定义列", mode = SystemLog.SAVE_DB)
    @PostMapping("/addFieldConfigCol")
    @ApiOperation(value = "自定义列", notes = "自定义列", response = boolean.class)
    public boolean addFieldConfigCol(@RequestBody @Validated FieldConfigModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return fieldConfigService.addFieldConfigCol(model,userInfo);
    }

    @SystemLog(description = "修改配置明细", mode = SystemLog.SAVE_DB)
    @PostMapping("/editFieldConfigCol")
    @ApiOperation(value = "修改配置明细", notes = "修改配置明细")
    public boolean editFieldConfigCol(@RequestBody @Validated FieldConfigModel model) throws OwnerException {
        return fieldConfigService.editFieldConfigCol(model, getUserInfo());
    }

    @SystemLog(description = "通过类型获取配置")
    @GetMapping("/getTypeConfig")
    //@PermissionRequired(perms = "dataentry:salary:config")
    @ApiOperation(value = "通过类型获取配置", notes = "通过类型获取配置")
    public FieldConfigModel getTypeConfig(Integer type) throws OwnerException {
        return fieldConfigService.getTypeConfig(getUserInfo().getCompanyId(), type);
    }
}
