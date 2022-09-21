package com.yskc.ms.controller.ms;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.PageResult;
import com.yskc.ms.config.Constant;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.entity.ms.CheckInstEntity;
import com.yskc.ms.entity.rs.DocListEntity;
import com.yskc.ms.models.BatchDeleteModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.checkInst.CheckInstModel;
import com.yskc.ms.models.checkInst.QueryCheckInstModel;
import com.yskc.ms.service.ms.CheckInstService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.List;

/**
 * @program: ms
 * @description: 查新机构接口
 * @author: wjy
 * @create: 2022-08-09 09:42
 **/
@Api(tags = "查新机构接口", value = "查新机构接口")
@RestController
@RequestMapping("/api/checkInst")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
public class CheckInstController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CheckInstService checkInstService;

    @GetMapping("/getList")
    @ApiOperation(notes = "获取查新机构列表", value = "获取查新机构列表")
    @SystemLog(description = "获取查新机构列表")
    @PermissionRequired(perms = "sys:checkInst:search")
    public PageModel<List<CheckInstModel>> getList(QueryCheckInstModel query)throws OwnerException {
        return checkInstService.getList(query,getUserInfo());
    }

    @PostMapping("/addCheckInst")
    @PermissionRequired(perms = "sys:checkInst:add")
    @SystemLog(description = "添加查新机构", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "添加查新机构", notes = "添加查新机构")
    public Boolean addCheckInst(@RequestBody @Validated CheckInstModel model) throws OwnerException {
        return checkInstService.addCheckInst(model,getUserInfo());
    }

    @PostMapping("/updateCheckInst")
    @PermissionRequired(perms = "sys:checkInst:edit")
    @SystemLog(description = "编辑查新机构", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "编辑查新机构", notes = "编辑查新机构")
    public Boolean updateCheckInst(@RequestBody @Validated CheckInstModel model) throws OwnerException {
        return checkInstService.updateCheckInst(model,getUserInfo());
    }

    @PostMapping("/delCheckInst")
    @PermissionRequired(perms = "sys:checkInst:del")
    @SystemLog(description = "删除查新机构", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "删除查新机构", notes = "删除查新机构")
    public Boolean delCheckInst(@RequestBody @Validated BatchDeleteModel model) throws OwnerException {
        return checkInstService.delCheckInst(model.getIds());
    }
}
