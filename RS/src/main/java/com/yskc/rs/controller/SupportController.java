package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.entity.SupportEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.service.SupportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: wangxing
 * @CreateTime: 2019-09-25 13:36
 * @Description: SupportController
 */
@Api(tags = "扶持情况接口类", value = "扶持情况接口类")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/support")
public class SupportController extends BaseController {
    @Autowired
    private SupportService supportService;

    /**
     *
     * @param entity
     * @return
     * @throws OwnerException
     */
    @PostMapping("/addSupport")
    @PermissionRequired(perms = "company:support:add")
    @SystemLog(description = "添加扶持情况",mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "添加扶持情况", notes = "添加扶持情况", response = boolean.class)
    public boolean addSupport(@RequestBody @Validated SupportEntity entity) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return supportService.addSupport(userInfo, entity);
    }

    /**
     *
     * @param entity
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "更新扶持情况",mode = SystemLog.SAVE_DB)
    @PostMapping("/updateSupport")
    @PermissionRequired(perms = "company:support:edit")
    @ApiOperation(value = "更新扶持情况", notes = "更新扶持情况", response = boolean.class)
    public boolean updateSupport(@RequestBody @Validated SupportEntity entity) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return supportService.updateSupport(userInfo, entity);
    }

    /**
     *
     * @param checkTime
     * @param supportTime
     * @param supportDeptName
     * @param projectName
     * @return
     * @throws OwnerException
     */
    @GetMapping("/querySupportList")
    @PermissionRequired(perms = "company:support:search")
    @ApiOperation(value = "查询所有信息", notes = "查询所有信息", response = String.class)
    public List<SupportEntity> querySupportList(Date checkTime, String supportTime, String supportDeptName, String projectName) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return supportService.querySupportList(userInfo.getCompanyId(), checkTime, supportTime, supportDeptName, projectName);
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/delSupport")
    @PermissionRequired(perms = "company:support:delete")
    @SystemLog(description = "删除扶持情况",mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "删除扶持情况", notes = "删除扶持情况", response = boolean.class)
    public Boolean delSupport(Integer id) {
        return supportService.delSupport(id);
    }
}
