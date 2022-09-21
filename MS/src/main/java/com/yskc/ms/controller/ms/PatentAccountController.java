package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.patent.PatentAccountModel;
import com.yskc.ms.models.patent.PatentAccountTreeModel;
import com.yskc.ms.models.patent.PatentModel;
import com.yskc.ms.models.patent.QueryPatentListModel;
import com.yskc.ms.service.ms.PatentAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.controller.ms
 * @Author: wangxing
 * @CreateTime: 2020-01-03 14:16
 * @Description:
 */
@Api(tags = "专利列表类接口", value = "专利列表类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/patentAccount")
public class PatentAccountController extends BaseController {
    @Autowired
    private PatentAccountService patentAccountService;

    @GetMapping("/queryPatentAccountList")
    @PermissionRequired(perms = "patent:patentAccount:search")
    @ApiOperation(value = "获取专利账号列表", notes = "获取专利账号列表")
    public PageModel<List<PatentAccountModel>> queryPatentAccountList(QueryPatentListModel query) throws OwnerException {
        return patentAccountService.queryPatentAccountList(query,getDataPerm());
    }

    @PostMapping("/addPatentAccount")
    @PermissionRequired(perms = "patent:patentAccount:add")
    @ApiOperation(value = "新增账号", notes = "新增账号", response = boolean.class)
    public boolean addPatentAccount(@RequestBody @Validated PatentAccountModel patentAccountModel) throws OwnerException {
        return patentAccountService.addPatentAccount(patentAccountModel, getUserInfo());
    }

    @PostMapping("/updatePatentAccount")
    @PermissionRequired(perms = "patent:patentList:edit")
    @ApiOperation(value = "修改账号", notes = "修改账号", response = boolean.class)
    public boolean updatePatentAccount(@RequestBody @Validated PatentAccountModel patentAccountModel) throws OwnerException {
        return patentAccountService.updatePatentAccount(patentAccountModel, getUserInfo());
    }

    @PostMapping("/delPatentAccount")
    @PermissionRequired(perms = "patent:patentAccount:del")
    @ApiOperation(value = "删除", notes = "删除", response = boolean.class)
    public boolean delPatentAccount(@RequestBody @Validated PatentModel model) throws OwnerException {
        return patentAccountService.delPatentAccount(model.getId());
    }

    @GetMapping("/checkUsername")
    @ApiOperation(value = "检查用户名是否重复", notes = "检查用户名是否重复")
    public Boolean checkUsername(String userName,Integer id) throws OwnerException {
        return patentAccountService.checkUsername(userName,id);
    }

    @PostMapping("/delBatch")
    @ApiOperation(value = "批量删除账号", notes = "批量删除账号")
    @PermissionRequired(perms = "patent:patentAccount:del")
    public boolean delBatch(@RequestBody @Validated List<PatentAccountModel> models) {
        return patentAccountService.delBatch(models);
    }

    @GetMapping("/getPatentAccountTree")
    @ApiOperation(value = "获取专利账号树", notes= "获取专利账号树")
    public List<PatentAccountTreeModel> getPatentAccountTree(){
        return patentAccountService.getPatentAccountTree();
    }

}
