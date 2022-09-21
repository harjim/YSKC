package com.yskc.ms.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.exception.OwnerException;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.entity.rs.DocTemplateEntity;
import com.yskc.ms.models.doctemplate.DocTemplateModel;
import com.yskc.ms.service.rs.DocTemplateService;
import com.yskc.common.model.PageModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: wangxing
 * @CreateTime: 2019-08-02 17:01
 * @Description: 文档模板controller
 */
@Api(tags = "文档模板接口", value = "文档模板接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/docTemlate")
public class DocTemplateController extends BaseController {

    @Autowired
    private DocTemplateService docTemplateService;

    @GetMapping("/queryDocTemplateList")
    @PermissionRequired(perms = "doc:tempt:search")
    @ApiOperation(value = "获取文档模板", notes = "获取文档模板")
    public PageModel<List<DocTemplateEntity>> queryDocTemplateList(int pageNo, int pageSize, String docName) throws OwnerException {
        return docTemplateService.queryDocTemplateList(pageNo, pageSize, docName);
    }

    @PostMapping("/addDocTemplate")
    @PermissionRequired(perms = "doc:tempt:add")
    @ApiOperation(value = "新增文档模板", notes = "新增文档模板", response = boolean.class)
    public boolean addDocTemplate(@RequestBody @Validated DocTemplateModel model) throws OwnerException {
        return docTemplateService.addDocTemplate(getUserInfo(), model);
    }

    @PostMapping("/updateDocTemplate")
    @PermissionRequired(perms = "doc:tempt:edit")
    @ApiOperation(value = "修改文档模板", notes = "修改文档模板", response = boolean.class)
    public boolean updateDocTemplate(@RequestBody @Validated DocTemplateModel model) throws OwnerException {
        return docTemplateService.updateDocTemplate(getUserInfo(), model);
    }


    @PostMapping("/delDocTemplate")
    @PermissionRequired(perms = "doc:tempt:del")
    @ApiOperation(value = "删除文档模板", notes = "删除文档模板", response = boolean.class)
    public boolean delDocTemplate(@RequestBody @Validated DocTemplateModel model) throws OwnerException {
        return docTemplateService.delDocTemplate(model);
    }
}
