package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.models.BatchDeleteModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.models.hightech.BindHighTechModel;
import com.yskc.rs.models.hightech.HighTechIncomeModel;
import com.yskc.rs.models.hightech.QueryHighTechIncomeModel;
import com.yskc.rs.service.HighTechIncomeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: hck
 * @DateTime: 2021/5/28 8:21
 * @Description: 高品收入
 */
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/highTechIncome")
public class HighTechIncomeController extends BaseController {

    @Autowired
    private HighTechIncomeService highTechIncomeService;
    @Autowired
    private RsConfig rsConfig;

    /**
     * @param query
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getList")
    @PermissionRequired(perms = "highTech:highTechIncome:search")
    @ApiOperation(value = "获取高品收入列表", notes = "获取高品收入列表")
    public PageModel<List<HighTechIncomeModel>> getList(QueryHighTechIncomeModel query) throws OwnerException {
        return highTechIncomeService.getList(getUserInfo().getCompanyId(), query);
    }

    @SystemLog(description = "添加高品收入",mode = SystemLog.SAVE_DB)
    @PostMapping("/add")
    @PermissionRequired(perms = "highTech:highTechIncome:add")
    @ApiOperation(value = "添加高品收入", notes = "添加高品收入")
    public boolean add(@RequestBody @Validated HighTechIncomeModel income) throws OwnerException {
        return highTechIncomeService.add(getUserInfo(), income);
    }

    @SystemLog(description = "编辑高品收入",mode = SystemLog.SAVE_DB)
    @PostMapping("/edit")
    @PermissionRequired(perms = "highTech:highTechIncome:edit")
    @ApiOperation(value = "编辑高品收入", notes = "编辑高品收入")
    public boolean edit(@RequestBody @Validated HighTechIncomeModel income) throws OwnerException {
        return highTechIncomeService.edit(getUserInfo(), income);
    }

    @SystemLog(description = "删除高品收入",mode = SystemLog.SAVE_DB)
    @PostMapping("/deleteList")
    @PermissionRequired(perms = "highTech:highTechIncome:del")
    @ApiOperation(value = "删除高品收入", notes = "删除高品收入")
    public boolean deleteList(@RequestBody @Validated BatchDeleteModel deleteModel) throws OwnerException {
        return highTechIncomeService.deleteList(deleteModel.getIds());
    }

    @SystemLog(description = "导入高品收入",mode = SystemLog.SAVE_DB)
    @PostMapping("/importIncome")
    @PermissionRequired(perms = "highTech:highTechIncome:import")
    @ApiOperation(value = "导入高品收入", notes = "导入高品收入")
    public String importIncome(@RequestParam("file") MultipartFile file, TableField tableField) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                userInfo.getCompanyId());
        ExcelResult<HighTechIncomeModel> excelResult = excelService.getExcelResult(tempPath, file, HighTechIncomeModel.class, tableField);
        highTechIncomeService.importIncome(userInfo, excelResult.getData());
        return excelResult.msg;
    }

    @GetMapping("/exportIncome")
    @PermissionRequired(perms = "highTech:highTechIncome:export")
    @ApiOperation(value = "导出高品收入", notes = "导出高品收入")
    public void exportIncome(QueryHighTechIncomeModel query) throws OwnerException {
        UserInfo info = getUserInfo();
        List<HighTechIncomeModel> data = highTechIncomeService.getExportIncome(info.getCompanyId(), query);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("list", data);
        String title = info.getCompanyName() + query.getYear() + "高品收入";
        dataMap.put("title", title);
        exportFileByTemplate(dataMap, Constant.TEMPLATE_DIR + "高品收入模板.xlsx", title + ".xlsx");
    }

    @SystemLog(description = "高品收入关联高新产品",mode = SystemLog.SAVE_DB)
    @PostMapping("/bindHighTech")
    @PermissionRequired(perms = "highTech:highTechIncome:bind")
    @ApiOperation(value = "高品收入关联高新产品", notes = "高品收入关联高新产品")
    public boolean bindHighTech(@RequestBody @Validated BindHighTechModel bind) throws OwnerException {
        return highTechIncomeService.bindHighTech(getUserInfo(), bind);
    }

    @SystemLog(description = "高品收入解除关联高新产品",mode = SystemLog.SAVE_DB)
    @PostMapping("/unbindHighTech")
    @PermissionRequired(perms = "highTech:highTechIncome:unbind")
    @ApiOperation(value = "高品收入解除关联高新产品", notes = "高品收入解除关联高新产品")
    public boolean unbindHighTech(@RequestBody @Validated BindHighTechModel bind) throws OwnerException {
        // 高新产品id强制设为null
        bind.setHighTechId(null);
        return highTechIncomeService.bindHighTech(getUserInfo(), bind);
    }
}
