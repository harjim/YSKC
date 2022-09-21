package com.yskc.rs.controller;

import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.models.excel.RdAccountDetailExcel;
import com.yskc.rs.models.rdaccountdetail.QueryRdAccountDetailModel;
import com.yskc.rs.models.rdaccountdetail.RdAccountDetailModel;
import com.yskc.rs.service.RdAccountDetailService;
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
 * @CreateTime: 2019-09-30 10:56
 * @Description: 研发费用明细control层
 */
@RestController
@Api(value = "研发费用明细接口层", tags = "研发费用明细接口层")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RequestMapping("/api/rdAccountDetail")
public class RdAccountDetailController extends BaseController {

    @Autowired
    private RdAccountDetailService accountDetailService;

    @Autowired
    private RsConfig rsConfig;

    /**
     *
     * @param query
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getList")
    @ApiOperation(value = "获取研发费用明细", notes = "获取研发费用明细")
    public PageModel<List<RdAccountDetailModel>> getList(QueryRdAccountDetailModel query) throws OwnerException {
        return accountDetailService.getList(getUserInfo().getCompanyId(), query);
    }

    /**
     *
     * @param rdAccountDetailModel
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "添加研发费用明细",mode = SystemLog.SAVE_DB)
    @PostMapping("/add")
    @ApiOperation(value = "添加研发费用明细", notes = "添加研发费用明细")
    public boolean add(@RequestBody @Validated RdAccountDetailModel rdAccountDetailModel) throws OwnerException {
        return accountDetailService.add(getUserInfo(), rdAccountDetailModel);
    }

    /**
     *
     * @param rdAccountDetailModel
     * @return
     * @throws OwnerException
     */
    @PostMapping("/update")
    @SystemLog(description = "更新研发费用明细",mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "更新研发费用明细", notes = "更新费研发费用明细")
    public boolean update(@RequestBody @Validated RdAccountDetailModel rdAccountDetailModel) throws OwnerException {
        return accountDetailService.update(getUserInfo(), rdAccountDetailModel);
    }

    /**
     *
     * @param rdAccountDetailModel
     * @return
     */
    @PostMapping("/del")
    @SystemLog(description = "删除研发费用明细",mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "删除研发费用明细", notes = "删除研发费用明细")
    public boolean del(@RequestBody @Validated RdAccountDetailModel rdAccountDetailModel) {
        return accountDetailService.del(rdAccountDetailModel);
    }

    /**
     *
     * @param rdAccountDetailModels
     * @return
     */
    @PostMapping("/dels")
    @SystemLog(description = "批量删除研发费用明细",mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "批量删除研发费用明细", notes = "批量删除研发费用明细")
    public boolean dels(@RequestBody @Validated List<RdAccountDetailModel> rdAccountDetailModels) {
        return accountDetailService.dels(rdAccountDetailModels);
    }

    /**
     *
     * @param file
     * @param tableField
     * @param accType
     * @return
     * @throws OwnerException
     */
    @PostMapping("/importInfo")
    @SystemLog(description = "导入研发费用明细",mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "导入研发费用明细")
    public String importInfo(@RequestParam("file") MultipartFile file, TableField tableField, int accType) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                userInfo.getCompanyId());
        ExcelResult<RdAccountDetailExcel> techProjectCostExcelExcels = excelService.getExcelResult(tempPath, file, RdAccountDetailExcel.class, tableField);
        accountDetailService.importInfo(userInfo, techProjectCostExcelExcels.getData(),accType);
        return techProjectCostExcelExcels.msg;
    }
}
