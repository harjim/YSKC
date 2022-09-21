package com.yskc.ms.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.models.rs.RsDocFileTemplateModel;
import com.yskc.ms.models.rs.RsDocFilesModel;
import com.yskc.ms.service.rs.RsDocFileTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "文件模板版本", value = "文件模板版本接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/docFileTemplate")
public class RsDocFileTemplateController extends BaseController {

    @Autowired
    private RsDocFileTemplateService rsDocFileTemplateService;

    @SystemLog(description = "根据模板版本id获取版本内容")
    @GetMapping("/getData")
    @ApiOperation(notes = "获取版本内容", value = "获取版本内容")
    public RsDocFileTemplateModel getData(Integer docFileTemplateId) {
        return rsDocFileTemplateService.getData(docFileTemplateId);
    }

    @SystemLog(description = "获取模板及所有版本")
    @PermissionRequired(perms = "doc:tempt:search")
    @GetMapping("/getDataList")
    @ApiOperation(notes = "获取模板及所有版本", value = "获取模板及所有版本")
    public PageModel<List<RsDocFilesModel>> getDataList(int pageNo, int pageSize,String docName) {
        return rsDocFileTemplateService.getDocFilesList( pageNo, pageSize,docName);
    }

    @SystemLog(description = "保存模板版本内容")
    @PostMapping("/saveData")
    @ApiOperation(notes = "保存模板版本内容", value = "保存模板版本内容")
    public Boolean saveData(@RequestBody RsDocFileTemplateModel rsDocFileTemplateModel) throws OwnerException {
        RsDocFileTemplateModel rsDocFileTemplateModel1=rsDocFileTemplateService.getData(rsDocFileTemplateModel.getId());
        Boolean isHave;
        if(rsDocFileTemplateModel1!=null){
            isHave=true;
        }else {
            isHave=false;
        }
        return rsDocFileTemplateService.saveData(getUserInfo(), rsDocFileTemplateModel,isHave);
    }

    @SystemLog(description = "删除模板")
    @GetMapping("/delTemplate")
    @ApiOperation(notes = "删除模板", value = "删除模板")
    public Boolean delTemplate(Integer id){
        return rsDocFileTemplateService.delTemplate(id);
    }
}
