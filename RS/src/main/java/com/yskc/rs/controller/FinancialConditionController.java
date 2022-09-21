
package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.FinancialConditionEntity;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.models.excel.FinancialConditionExcel;
import com.yskc.rs.service.FinancialConditionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: wangxing
 * @CreateTime: 2019-09-25 15:28
 * @Description: FinancialConditionController
 */
@Api(tags = "财务状况", value = "财务状况")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/financialCondition")
public class FinancialConditionController extends BaseController {
    @Autowired
    private FinancialConditionService financialConditionService;

    @Autowired
    private RsConfig rsConfig;

    /**
     *
     * @param year
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryFinancialCondList")
    @PermissionRequired(perms = "company:financialCondition:search")
    @ApiOperation(value = "查询所有信息", notes = "查询所有信息", response = String.class)
    public List<FinancialConditionEntity> queryFinancialCondList(Integer year) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return financialConditionService.queryFinancialCond(userInfo.getCompanyId(), year);
    }

    /**
     *
     * @param years
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getFinancialCondList")
    @ApiOperation(value = "查询所有信息", notes = "查询所有信息", response = String.class)
    public List<FinancialConditionEntity> getFinancialCondList(Integer[] years) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return financialConditionService.getFinancialCond(userInfo.getCompanyId(), years);
    }

    /**
     * @param {type}
     * @description:
     * @return:
     */
    @SystemLog(description = "新增财务状况",mode = SystemLog.SAVE_DB)
    @PostMapping("/addFinancialCond")
    @PermissionRequired(perms = "company:financialCondition:add")
    @ApiOperation(value = "新增财务状况", notes = "新增财务状况", response = boolean.class)
    public boolean addFinancialCond(@RequestBody @Validated FinancialConditionEntity entity) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return financialConditionService.addFinancialCond(userInfo, entity);
    }

    /**
     *
     * @param entity
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "更新财务状况",mode = SystemLog.SAVE_DB)
    @PostMapping("/updateFinancialCond")
    @PermissionRequired(perms = "company:financialCondition:edit")
    @ApiOperation(value = "更新财务状况", notes = "更新财务状况", response = boolean.class)
    public boolean updateFinancialCond(@RequestBody @Validated FinancialConditionEntity entity) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return financialConditionService.updateFinancialCond(userInfo, entity);
    }

    /**
     *
     * @param file
     * @param tableField
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "导入财务状况",mode = SystemLog.SAVE_DB)
    @PostMapping("/importFinancialCondition")
    @PermissionRequired(perms = "company:financialCondition:import")
    @ApiOperation(value = "导入财务状况", notes = "导入财务状况", response = String.class)
    public String importFinancialCondition(@RequestParam("file") MultipartFile file, TableField tableField) throws OwnerException {
        UserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                info.getCompanyId());
        ExcelResult<FinancialConditionExcel> excelResult = excelService.getExcelResult(tempPath, file, FinancialConditionExcel.class, tableField);
        financialConditionService.importFinancialCondition(info, excelResult.getData());
        return excelResult.getMsg();
    }

    /**
     * @param {Integer}
     * @description:
     * @return:
     */
    @SystemLog(description = "删除财务状况",mode = SystemLog.SAVE_DB)
    @GetMapping("/delFinancialCondition")
    @ApiOperation(value = "删除财务状况", notes = "删除财务状况", response = boolean.class)
    public Boolean delFinancialCondition(Integer id) {
        return financialConditionService.delFinancialCondition(id);
    }

    /**
     *
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "删除财务状况(year)",mode = SystemLog.SAVE_DB)
    @PostMapping("/delYear")
    @PermissionRequired(perms = "company:financialCondition:delete")
    @ApiOperation(value = "删除财务状况(year)", notes = "删除财务状况(year)", response = boolean.class)
    public boolean delYear(@RequestBody @Validated FinancialConditionEntity model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return financialConditionService.delYear(userInfo.getCompanyId(), model.getYear());
    }

    /**
     *
     * @param year
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getDataByTerm")
    @ApiOperation(value = "根据年份获取数据", notes = "根据年份获取数据", response = boolean.class)
    public FinancialConditionEntity get(Integer year) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        List<Integer> years = new ArrayList<>();
        years.add(year);
        return financialConditionService.getDataByTerm(userInfo.getCompanyId(), years);
    }
}
