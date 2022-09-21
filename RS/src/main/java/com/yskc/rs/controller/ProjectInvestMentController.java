package com.yskc.rs.controller;

import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.entity.tech.ProjectInvestMentEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.service.ProjectInvestMentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 项目投资情况类接口
 *
 * @author huronghua
 */
@Api(tags = "项目投资情况类接口", value = "项目投资情况类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/projectInvestMent")
public class ProjectInvestMentController extends BaseController {

    @Autowired
    ProjectInvestMentService projectInvestMentService;

    /**
     *
     * @param projectId
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryAll")
    @ApiOperation(value = "查询考察信息", notes = "查询考察信息", response = String.class)
    public ProjectInvestMentEntity queryAll(Integer projectId) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return projectInvestMentService.selectByProjectId(userInfo.getCompanyId(),projectId);
    }

    /**
     *
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "保存考察信息",mode = SystemLog.SAVE_DB)
    @PostMapping("/saveInvestMent")
    @ApiOperation(value = "保存考察信息", notes = "保存考察信息", response = boolean.class)
    public Integer saveInvestMent(@RequestBody @Validated ProjectInvestMentEntity model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return projectInvestMentService.saveInvestMent(userInfo, model);
    }
}
