package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.models.excel.InspectionExcel;
import com.yskc.rs.models.inspection.InspectionModel;
import com.yskc.rs.models.inspection.QueryInspectionModel;
import com.yskc.rs.models.projectinspection.QueryProjectInspectionModel;
import com.yskc.rs.service.InspectionService;
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
 * @CreateTime: 2019-07-11 16:03
 * @Description: 费用录入controller层
 */
@Api(tags = "费用录入类接口", value = "费用录入类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/inspection")
public class InspectionController extends BaseController {

    @Autowired
    private InspectionService inspectionService;

    @Autowired
    private RsConfig rsConfig;

    /**
     * @param inspectionQuery
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryAll")
    @ApiOperation(value = "查询inspection", notes = "查询inspection", response = PageModel.class)
    public PageModel<List<InspectionModel>> queryAll(QueryInspectionModel inspectionQuery) throws OwnerException {
        UserInfo info = getUserInfo();
        if (inspectionQuery.getType() == null || inspectionQuery.getType().length <= 0) {
            inspectionQuery.setType(null);
        }
        return inspectionService.queryAll(info.getCompanyId(), inspectionQuery);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "保存费用录入", mode = SystemLog.SAVE_DB)
    @PostMapping("/modifyInspection")
    @ApiOperation(value = "操作费用录入", notes = "操作费用录入", response = Boolean.class)
    public Boolean modifyInspection(@RequestBody @Validated InspectionModel model) throws OwnerException {
        UserInfo info = getUserInfo();
        return inspectionService.modifyInspection(info, model);
    }

    /**
     * @param id
     * @return
     * @throws OwnerException
     */
    @GetMapping("/delInspection")
    @SystemLog(description = "删除费用录入", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "删除费用录入", notes = "删除费用录入", response = Boolean.class)
    public Boolean delInspection(Integer id) throws OwnerException {
        return inspectionService.deleteById(id);
    }

    /**
     * @param file
     * @param type
     * @param tableField
     * @return
     * @throws OwnerException
     */
    @PostMapping("/importInspection")
    @SystemLog(description = "导入费用录入", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "导入费用录入", notes = "导入费用录入", response = String.class)
    public String importInspection(@RequestParam("file") MultipartFile file, Integer type, TableField tableField, Integer year) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                userInfo.getCompanyId());
        ExcelResult<InspectionExcel> excelResult = excelService.getExcelResult(tempPath, file, InspectionExcel.class, tableField);
        String msg = inspectionService.importInspection(userInfo, excelResult.getData(), type, year);
        StringBuilder stringBuilder = new StringBuilder(excelResult.getMsg());
        stringBuilder.append(msg);
        return stringBuilder.toString();
    }

    /**
     * @param inspectionQuery
     * @return
     * @throws Exception
     */
    @GetMapping("/exportInspection")
    @SystemLog(description = "导出费用录入", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "导出费用录入", notes = "导出费用录入")
    public List<InspectionExcel> expertInspection(QueryInspectionModel inspectionQuery) throws Exception {
        return inspectionService.exportInspection(getUserInfo().getCompanyId(), inspectionQuery);
    }

    @GetMapping("/queryInspectionByTerm")
    @PermissionRequired(perms = "project:data:view")
    @ApiOperation(value = "获取费用录入信息", notes = "获取费用录入信息")
    public PageModel<List<InspectionModel>> queryInspectionByTerm(QueryProjectInspectionModel query) throws OwnerException {
        return inspectionService.queryInspectionByTerm(getUserInfo().getCompanyId(), query);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "批量设置费用录入研发部门", mode = SystemLog.SAVE_DB)
    @PostMapping("/updateInspectionRdDept")
    @ApiOperation(value = "批量设置费用录入研发部门", notes = "批量设置费用录入研发部门", response = boolean.class)
    public boolean updateInspectionRdDept(@RequestBody @Validated InspectionModel model) throws OwnerException {
        return inspectionService.updateInspectionRdDept(getUserInfo(), model);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @PostMapping("/updateInspetioneType")
    @PermissionRequired(perms = "dataentry:amortizeFee:setFeeType")
    @SystemLog(description = "批量设置费用录入类型", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "批量设置费用录入类型", notes = "批量设置费用录入类型", response = boolean.class)
    public boolean updateInspetioneType(@RequestBody @Validated InspectionModel model) throws OwnerException {
        return inspectionService.updateInspetioneType(getUserInfo(), model);
    }

    /**
     * @param modelList
     * @return
     * @throws OwnerException
     */
    @PostMapping("/delInspetionBatch")
    @SystemLog(description = "批量删除费用录入", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "批量删除费用录入", notes = "批量删除费用录入", response = boolean.class)
    public boolean delInspetionBatch(@RequestBody @Validated List<InspectionModel> modelList) throws OwnerException {
        return inspectionService.delInspetiontBatch(modelList);
    }

    @GetMapping("/queryTravelFee")
    @PermissionRequired(perms = "project:data:view")
    @ApiOperation(value = "获取费用录入信息", notes = "获取费用录入信息")
    public PageModel<List<InspectionModel>> queryTravelFee(QueryProjectInspectionModel query) throws OwnerException {
        return inspectionService.queryTravelFee(getUserInfo().getCompanyId(), query);
    }
}
