package com.yskc.rs.controller;

import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.tech.ProjectOtherModel;
import com.yskc.rs.service.ProjectOtherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 现场考察信息类接口
 *
 * @author huronghua
 */
@Api(tags = "现场考察信息类接口", value = "现场考察信息类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/projectOther")
public class ProjectOtherController extends BaseController {

    @Autowired
    ProjectOtherService projectOtherService;

    /**
     *
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "保存考察信息",mode = SystemLog.SAVE_DB)
    @PostMapping("/saveOther")
    @ApiOperation(value = "保存考察信息", notes = "保存考察信息", response = boolean.class)
    public Integer saveOther(@RequestBody @Validated ProjectOtherModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return projectOtherService.saveOther(userInfo, model);
    }

    /**
     *
     * @param projectId
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryAll")
    @ApiOperation(value = "查询考察信息", notes = "查询考察信息", response = String.class)
    public ProjectOtherModel queryAll(Integer projectId) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return projectOtherService.queryAll(userInfo.getCompanyId(),projectId);
    }
}
