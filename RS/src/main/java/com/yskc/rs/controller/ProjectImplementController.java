package com.yskc.rs.controller;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: wangxing
 * @CreateTime: 2019-09-24 08:41
 * @Description: 项目实现效果Controller
 */

import com.yskc.common.exception.OwnerException;
import com.yskc.rs.entity.tech.ProjectImplementEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.service.ProjectImplementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 项目实现效果类
 *
 * @author huronghua
 */
@Api(tags = "项目实现效果类接口", value = "项目实现效果类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/projectImplement")
public class ProjectImplementController extends BaseController {

    @Autowired
    ProjectImplementService projectImplementService;

    /**
     *
     * @param projectId
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryImplement")
    @ApiOperation(value = "查询所有信息", notes = "查询所有信息", response = String.class)
    public ProjectImplementEntity queryImplement(Integer projectId) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return projectImplementService.queryImplement(userInfo.getCompanyId(), projectId);
    }

    /**
     *
     * @param entity
     * @return
     * @throws OwnerException
     */
    @PostMapping("/addProjectImplement")
    @ApiOperation(value = "新增", notes = "新增", response = boolean.class)
    public Integer addProjectImplement(@RequestBody @Validated ProjectImplementEntity entity) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return projectImplementService.addProjectImplement(userInfo, entity);
    }
}
