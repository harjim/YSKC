package com.yskc.ms.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.entity.rs.DocTemplateEntity;
import com.yskc.ms.entity.rs.models.DocProcessModel;
import com.yskc.ms.entity.rs.models.DocProcessTemplateListModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.service.rs.DocProcessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.controller.rs
 * @Author: wangxing
 * @CreateTime: 2019-08-05 15:10
 * @Description: 模板管理Controller
 */
@Api(tags = "模板管理接口", value = "模板管理接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/docProcess")
public class DocProcessController extends BaseController {
    @Autowired
    private DocProcessService docProcessService;

    @GetMapping("/queryDocProcessList")
    @PermissionRequired(perms = "doc:proc:search")
    @ApiOperation(value = "获取模板管理", notes = "获取模板管理")
    public PageModel<List<DocProcessModel>> queryDocProcessList(int pageNo, int pageSize, String processName) throws OwnerException {
        return docProcessService.queryDocProcess(pageNo, pageSize, processName);
    }
    @GetMapping("/getListPageModel")
    @ApiOperation(value = "获取模板数据", notes = "获取模板数据")
    public  PageModel<List<DocProcessTemplateListModel>> getListPageModel(int pageNo,int pageSize,int id) throws  OwnerException{
        return  docProcessService.getDataById(pageNo,pageSize,id);
    }

    @PostMapping("/addProcessDocTemplate")
    @PermissionRequired(perms = "doc:proc:add")
    @ApiOperation(value = "新增文件过程", notes = "新增文件过程", response = boolean.class)
    public  boolean addProcessDocTemplate(@RequestBody @Validated DocProcessModel model) throws  OwnerException{
        return  docProcessService.addDocProcessAndProcessTemplate(getUserInfo(),model);
    }

    @PostMapping("/updateProcessDocTemplate")
    @PermissionRequired(perms = "doc:proc:edit")
    @ApiOperation(value = "修改文件过程", notes = "修改文件过程", response = boolean.class)
    public  boolean updateProcessDocTemplate(@RequestBody @Validated DocProcessModel model) throws  OwnerException{
        return  docProcessService.updateDocProcessAndProcessTemplate(getUserInfo(),model);
    }

    @PostMapping("/delProcessDocTemplate")
    @PermissionRequired(perms = "doc:proc:del")
    @ApiOperation(value = "删除文件过程", notes = "删除文件过程", response = boolean.class)
    public  boolean delProcessDocTemplate(@RequestBody @Validated DocProcessModel model) throws  OwnerException{
        return  docProcessService.delDocProcessAndProcessTemplate(model);
    }

    @PostMapping("/delProcessTemplate")
    @ApiOperation(value = "删除文件模板", notes = "删除文件模板", response = boolean.class)
    public  boolean delProcessTemplate(@RequestBody @Validated DocProcessModel model) throws  OwnerException{
        return  docProcessService.delProcessTemplate(model.getId());
    }
}
