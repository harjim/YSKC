package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.entity.ms.SolutionEntity;
import com.yskc.ms.entity.ms.SolutionShareEntity;
import com.yskc.ms.models.BatchDeleteModel;
import com.yskc.ms.models.solution.QuerySolutionModel;
import com.yskc.ms.models.solution.SolutionModel;
import com.yskc.ms.models.solution.SolutionShareModel;
import com.yskc.ms.service.ms.SolutionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.controller.ms
 * @Author: zhangdingfu
 * @CreateTime: 2021-07-09 09:49
 * @Description: 知识库接口
 */
@RestController
@RequestMapping("/api/solution")
@Api(tags = "知识库接口", value = "知识库接口")
@CrossOrigin(origins = "*", allowCredentials = "", allowedHeaders = "true", methods = {})
public class SolutionController extends BaseController {
    @Autowired
    private SolutionService solutionService;

    @GetMapping("/getList")
    @PermissionRequired(perms = "sys:solution:search")
    @ApiOperation(notes = "获取解决方案列表",value = "获取解决方案列表")
    public PageModel<List<SolutionModel>> getList(QuerySolutionModel query)throws OwnerException {
        return solutionService.getList(query,getDataPerm());
    }

    @GetMapping("/getShareList")
    @PermissionRequired(perms = "sys:solution:search")
    @ApiOperation(notes = "获取分享的解决方案列表",value = "获取分享的解决方案列表")
    public PageModel<List<SolutionModel>> getShareList(QuerySolutionModel query)throws OwnerException {
        return solutionService.getShareList(query,getUserInfo());
    }

    @PostMapping("/add")
    @SystemLog(description = "添加解决方案",mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "sys:solution:add")
    @ApiOperation(notes="添加解决方案",value = "添加解决方案")
    public Boolean add(@RequestBody @Validated SolutionModel solution)throws OwnerException{
        return  solutionService.add(solution,getUserInfo());
    }

    @PostMapping("/edit")
    @SystemLog(description = "编辑解决方案",mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "sys:solution:edit")
    @ApiOperation(notes="编辑解决方案",value = "编辑解决方案")
    public Boolean edit(@RequestBody @Validated SolutionModel solution)throws OwnerException{
        return  solutionService.edit(solution,getUserInfo());
    }

    @PostMapping("/delete")
    @SystemLog(description = "删除解决方案",mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "sys:solution:delete")
    @ApiOperation(notes="删除解决方案",value = "删除解决方案")
    public Boolean delete(@RequestBody @Validated BatchDeleteModel delete)throws OwnerException{
        return  solutionService.delete(delete.getIds());
    }

    @GetMapping("/getShareInfo")
    @PermissionRequired(perms = "sys:solution:share")
    @ApiOperation(notes = "获取解决方案分享详情",value = "获取解决方案分享详情")
    public List<SolutionShareModel> getShareInfo(Integer solutionId){
        return solutionService.getShareInfo(solutionId);
    }

    @PostMapping("/share")
    @SystemLog(description = "分享解决方案",mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "sys:solution:share")
    @ApiOperation(notes = "分享解决方案",value = "分享解决方案")
    public Boolean share(@RequestBody @Validated List<SolutionShareEntity> share)throws OwnerException{
        return solutionService.share(share,getUserInfo());
    }

    @PostMapping("/deleteShare")
    @SystemLog(description = "删除解决方案分享",mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "sys:solution:share")
    @ApiOperation(notes = "删除解决方案分享",value = "删除解决方案分享")
    public Boolean deleteShare(@RequestBody @Validated BatchDeleteModel delete)throws OwnerException{
        return solutionService.deleteShare(delete.getIds());
    }
    @PostMapping("/rate")
    @SystemLog(description = "解决方案评星",mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "sys:solution:rate")
    @ApiOperation(notes = "解决方案评星",value = "解决方案评星")
    public Boolean rate(@RequestBody @Validated SolutionEntity solution)throws OwnerException{
        return solutionService.rate(solution,getUserInfo().getId());
    }

}
