package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.energy.EnergyModel;
import com.yskc.rs.models.energy.EnergyModifyModel;
import com.yskc.rs.models.energy.QueryEnergy;
import com.yskc.rs.models.excel.EnergyExcel;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.service.EnergyService;
import com.yskc.rs.service.ExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-09 09:23
 * @Description: 能源类接口
 */

@Api(tags = "能源类接口", value = "能源类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/energy")
public class EnergyController extends BaseController {

    @Autowired
    private EnergyService energyService;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private RsConfig rsConfig;

    /**
     * @param queryEnergy
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryAll")
    @PermissionRequired(perms = "dataentry:stimulus:search,dataentry:fuel:search")
    @ApiOperation(value = "查询能源", notes = "查询能源", response = PageModel.class)
    public PageModel<List<EnergyModel>> queryAll(QueryEnergy queryEnergy) throws OwnerException {
        return energyService.queryAll(getUserInfo().getCompanyId(), queryEnergy);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "添加能源", mode = SystemLog.SAVE_DB)
    @PostMapping("/addEnergy")
    @PermissionRequired(perms = "dataentry:stimulus:add,dataentry:fuel:add")
    @ApiOperation(value = "添加能源", notes = "添加能源", response = Boolean.class)
    public Boolean addEnergy(@RequestBody @Validated EnergyModel model) throws OwnerException {
        return energyService.addEnergy(getUserInfo(), model);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "修改能源", mode = SystemLog.SAVE_DB)
    @PostMapping("/updateEnergy")
    @PermissionRequired(perms = "dataentry:stimulus:edit,dataentry:fuel:edit")
    @ApiOperation(value = "修改能源", notes = "修改能源", response = Boolean.class)
    public Boolean updateEnergy(@RequestBody @Validated EnergyModel model) throws OwnerException {
        return energyService.updateEnergy(getUserInfo(), model);
    }

    /**
     * @param id
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "删除能源", mode = SystemLog.SAVE_DB)
    @GetMapping("/delEnergy")
    @PermissionRequired(perms = "dataentry:stimulus:del,dataentry:fuel:del")
    @ApiOperation(value = "删除能源", notes = "删除能源", response = Boolean.class)
    public Boolean delEnergy(Integer id) throws OwnerException {
        return energyService.deleteById(id);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "批量删除能源", mode = SystemLog.SAVE_DB)
    @PostMapping("/delEnergys")
    @PermissionRequired(perms = "dataentry:stimulus:del,dataentry:fuel:del")
    @ApiOperation(value = "批量删除能源", notes = "批量删除能源")
    public Boolean delEnergys(@RequestBody @Validated EnergyModifyModel model) throws OwnerException {
        return energyService.deleteByIds(model);
    }

    /**
     * @param file
     * @param type
     * @param tableField
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "导入能源", mode = SystemLog.SAVE_DB)
    @PostMapping("/importEnergy")
    @PermissionRequired(perms = "dataentry:stimulus:import,dataentry:fuel:import")
    @ApiOperation(value = "导入能源", notes = "导入能源")
    public String importEnergy(@RequestParam("file") MultipartFile file, Integer type, Integer year, TableField tableField) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                userInfo.getCompanyId());
        ExcelResult<EnergyExcel> excelResult = excelService.getExcelResult(tempPath, file, EnergyExcel.class, tableField);
        energyService.importEnergy(userInfo, excelResult.getData(), type, year);
        return excelResult.msg;
    }

    /**
     * 能源报表导出
     *
     * @param queryEnergy
     * @throws Exception
     */
    @GetMapping("/exportEnergy")
    @SystemLog(description = "导出能源")
    @PermissionRequired(perms = "dataentry:stimulus:export,dataentry:fuel:export")
    @ApiOperation(value = "导出能源", notes = "导出能源")
    public List<EnergyExcel> expertEnergy(QueryEnergy queryEnergy) throws OwnerException {
        return energyService.getExportEnergyList(getUserInfo().getCompanyId(), queryEnergy);
    }
}
