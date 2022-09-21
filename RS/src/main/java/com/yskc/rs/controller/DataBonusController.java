package com.yskc.rs.controller;

import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.data.DataBonusInfo;
import com.yskc.rs.models.data.DataBonusModel;
import com.yskc.rs.models.data.DataBonusQuery;
import com.yskc.rs.models.excel.BonusExcel;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.service.DataBonusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.List;

/**
 * 奖金类接口
 *
 * @author huronghua
 */
@Api(tags = "奖金类接口", value = "奖金类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/dataBonus")
public class DataBonusController extends BaseController {
    @Autowired
    private DataBonusService dataBonusService;
    @Autowired
    private RsConfig rsConfig;

    /**
     * 添加奖金
     *
     * @param model
     * @return
     * @throws OwnerException
     */
    @PostMapping("/add")
    @SystemLog(description = "添加奖金",mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "添加奖金", notes = "添加奖金", response = boolean.class)
    public boolean add(@RequestBody @Validated DataBonusModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return dataBonusService.add(userInfo, model);
    }

    /**
     * 获取奖金列表
     *
     * @param dataBonusQuery
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getDataBonusList")
    @ApiOperation(value = "获取奖金列表", notes = "获取奖金列表")
    public PageModel<List<DataBonusInfo>> getDataBonusList(DataBonusQuery dataBonusQuery) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        dataBonusQuery.setCompanyId(userInfo.getCompanyId());
        return dataBonusService.getDataBonusList(dataBonusQuery);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     * @throws OwnerException
     */
    @PostMapping("/deleteList")
    @SystemLog(description = "批量删除奖金",mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "批量删除", notes = "批量删除", response = boolean.class)
    public boolean deleteList(@RequestBody @Validated List<Integer> ids) throws OwnerException {
        return dataBonusService.deleteList(getUserInfo(), ids);
    }

    /**
     * 导入员工奖金
     *
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/importData")
    @SystemLog(description = "导入员工奖金",mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "导入员工奖金", notes = "导入员工奖金", response = String.class)
    public String importData(@RequestParam("file") MultipartFile file, TableField tableField,Integer year)  throws OwnerException {
        UserInfo userInfo = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                userInfo.getCompanyId());
        ExcelResult<BonusExcel> excelExcelResult = excelService.getExcelResult(tempPath, file, BonusExcel.class, tableField);
        String msg = dataBonusService.importData(userInfo, excelExcelResult.getData(),year);
        StringBuilder stringBuilder = new StringBuilder(excelExcelResult.getMsg());
        stringBuilder.append(msg);
        return stringBuilder.toString();
    }

    /**
     * 导出员工奖金
     *
     * @param dataBonusQuery
     * @return
     * @throws OwnerException
     */
    @GetMapping("/exportBonusData")
    @SystemLog(description = "导出员工奖金",mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "导出员工奖金", notes = "导出员工奖金")
    public List<BonusExcel> exportBonusData(DataBonusQuery dataBonusQuery) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        dataBonusQuery.setCompanyId(userInfo.getCompanyId());
        return dataBonusService.exportBonusData(dataBonusQuery);
    }
}
