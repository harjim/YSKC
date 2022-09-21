package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.entity.StageEntity;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.models.HighTecIndustryModel;
import com.yskc.rs.models.SysDictionaryModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.TableFieldInfo;
import com.yskc.rs.service.SysDictionaryService;
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

    /**
     * 高新领域
     * @return
     */
    @GetMapping("/getHighTecIndustryModels")
    @ApiOperation(value = "高新领域", notes = "高新领域", response = List.class)
    public List<HighTecIndustryModel> getHighTecIndustryModels(){
        return sysDictionaryService.getHighTecIndustryModels();
    }

    /**
     * 获取表格头字段配置
     * @param tableId
     * @return
     */
    @GetMapping("/getTableField")
    @ApiOperation(value = "获取表格头字段配置", notes = "获取表格头字段配置", response = List.class)
    public TableFieldInfo getTableField(String tableId) throws OwnerException {
     return sysDictionaryService.getTableField(getUserInfo(),tableId);
    }

    /**
     * 保存表格头字段配置
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "保存表格头字段配置")
    @PostMapping("/saveTableField")
    @ApiOperation(value = "保存表格头字段配置", notes = "保存表格头字段配置", response = boolean.class)
    public boolean saveTableField(@RequestBody @Validated TableField model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return sysDictionaryService.saveTableField(userInfo, model);
    }
}
