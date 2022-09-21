package com.yskc.ms.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.entity.rs.SysDictionaryEntity;
import com.yskc.ms.entity.rs.models.SysDictionaryEntityModel;
import com.yskc.ms.models.BatchDeleteModel;
import com.yskc.ms.models.SysDictionaryModel;
import com.yskc.ms.models.SysDictionaryParams;
import com.yskc.ms.service.rs.SysDictionaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "系统字典类接口", value = "系统字典类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/sysDictionary")
public class SysDictionaryController extends BaseController {
    @Autowired
    private SysDictionaryService sysDictionaryService;

    /**
     * 获取字典
     * @param type
     * @return
     */
    @GetMapping("/getDictionary")
    @ApiOperation(value = "获取字典", notes = "获取字典", response = List.class)
    public List<SysDictionaryModel> getDictionary(Integer type)  {
         return sysDictionaryService.getDictionaryByType(type);
    }

    @GetMapping("/getFinanceDictionary")
    @ApiOperation(value = "获取单位财务字典", notes = "获取单位财务字典", response = String.class)
    // @PermissionRequired(perms = "customer:register:search")
    public List<SysDictionaryModel> getFinanceDictionary() throws OwnerException {
        return sysDictionaryService.getFinanceDictionary();
    }

    /**
     * 列表查询
     */
    @GetMapping("/queryList")
    @PermissionRequired(perms = "sys:dictionary:search")
    @ApiOperation(value = "列表查询",notes = "列表查询")
    public PageModel<List<SysDictionaryEntityModel >> queryList(SysDictionaryParams params){
        return sysDictionaryService.queryList(params);
    }

    @GetMapping("/findKey")
    @ApiOperation(value = "key-value查询",notes = "key-value查询")
    public List<SysDictionaryEntity> findKey(String key, Integer type){
        return sysDictionaryService.findKey(key,type);
    }

    @PostMapping("/insert")
    @PermissionRequired(perms = "sys:dictionary:add")
    @SystemLog(description = "添加字典数据", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "添加字典数据",notes = "添加字典数据")
    public Integer add(@RequestBody @Validated SysDictionaryEntity entity) throws OwnerException {
        return sysDictionaryService.insert(entity);
    }

    @PostMapping("/del")
    @PermissionRequired(perms = "sys:dictionary:del")
    @SystemLog(description = "删除字典数据", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "删除字典数据",notes = "删除字典数据")
    public Integer del(@RequestBody @Validated SysDictionaryEntity entity) throws OwnerException {
        return sysDictionaryService.del(entity.getId());
    }

    @PostMapping("/edit")
    @PermissionRequired(perms = "sys:dictionary:edit")
    @SystemLog(description = "编辑字典数据", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "编辑字典数据",notes = "编辑字典数据")
    public Integer edit(@RequestBody @Validated SysDictionaryEntity entity) throws OwnerException {
        return sysDictionaryService.edit(entity);
    }

    @PermissionRequired(perms = "sys:dictionary:del")
    @SystemLog(description = "批量删除字典数据", mode = SystemLog.SAVE_DB)
    @PostMapping("/delAll")
    @ApiOperation(value = "批量删除字典数据",notes = "批量删除字典数据")
    public Integer delAll(@RequestBody @Validated BatchDeleteModel deleteModel) throws OwnerException {
        return sysDictionaryService.delAll(deleteModel.getIds());
    }



}
