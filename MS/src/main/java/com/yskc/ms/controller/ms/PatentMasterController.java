package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.MiniModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.patentMaster.PatentMasterModel;
import com.yskc.ms.models.patentMaster.QueryMasterModel;
import com.yskc.ms.service.ms.PatentMasterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by hck
 * on 2020/7/7 14:56
 * description:专利负责人
 */
@Api(tags = "专利负责人接口", value = "专利负责人接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/patentMaster")
public class PatentMasterController extends BaseController {

    @Autowired
    private PatentMasterService patentMasterService;

    @PermissionRequired(perms = "patent:patentMaster:search")
    @GetMapping("/queryMasterList")
    @ApiOperation(value = "查询专利负责人列表 ", notes = "查询专利负责人列表")
    public PageModel<List<PatentMasterModel>> queryMasterList(QueryMasterModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return patentMasterService.queryMasterList(model, userInfo, getDataPerm());
    }

    @PermissionRequired(perms = "patent:patentMaster:search")
    @GetMapping("/getMasterList")
    @ApiOperation(value = "获取负责人列表 ", notes = "获取负责人列表")
    public List<PatentMasterModel> getMasterList(QueryMasterModel model) {
        return patentMasterService.getMasterList(model);
    }

    @SystemLog(description = "更新负责人列表", mode = SystemLog.SAVE_DB)
    @PostMapping("/update")
    @PermissionRequired(perms = "patent:patentMaster:edit")
    @ApiOperation(value = "更新负责人", notes = "更新负责人", response = boolean.class)
    public boolean update(@RequestBody @Validated PatentMasterModel model) throws OwnerException {
        return patentMasterService.updateMaster(model, getUserInfo());
    }

    @SystemLog(description = "添加负责人", mode = SystemLog.SAVE_DB)
    @PostMapping("/add")
    @PermissionRequired(perms = "patent:patentMaster:add")
    @ApiOperation(value = "添加负责人", notes = "添加负责人", response = boolean.class)
    public boolean add(@RequestBody @Validated PatentMasterModel model) throws OwnerException {
        return patentMasterService.addMaster(model, getUserInfo());
    }

    @GetMapping("/checkMasterName")
    //@PermissionRequired(perms = "patent:patentMaster:add")
    @ApiOperation(value = "负责人名称唯一", notes = "负责人名称唯一", response = boolean.class)
    public boolean checkMasterName(PatentMasterModel model) throws OwnerException {
        return patentMasterService.checkMasterName(model, getUserInfo());
    }

    @SystemLog(description = "删除负责人", mode = SystemLog.SAVE_DB)
    @PostMapping("/del")
    @PermissionRequired(perms = "patent:patentMaster:del")
    @ApiOperation(value = "删除负责人", notes = "删除负责人", response = boolean.class)
    public boolean del(@RequestBody @Validated PatentMasterModel model) throws OwnerException {
        return patentMasterService.delMaster(model);
    }


    @GetMapping("/getSelect")
    @ApiOperation(value = "代理人下拉", notes = "代理人下拉")
    public List<MiniModel> getSelect() {
        return patentMasterService.getSelect();
    }

}
