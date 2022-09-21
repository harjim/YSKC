package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.equipment.EquipmentModel;
import com.yskc.rs.models.equipment.QueryEquipmentModel;
import com.yskc.rs.models.excel.EmployeeExcel;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.models.excel.VoucherExcel;
import com.yskc.rs.models.voucher.QueryVoucherModel;
import com.yskc.rs.models.voucher.RelatedVoucherModel;
import com.yskc.rs.models.voucher.VoucherModel;
import com.yskc.rs.service.VoucherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.List;

/**
 * Created by hck
 * on 2020/7/14 15:03
 * description:凭证接口
 */
@Api(tags = "用户类接口", value = "用户类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/voucher")
public class VoucherController extends BaseController{

    @Autowired
    private VoucherService voucherService;
    @Autowired
    private RsConfig rsConfig;


    @GetMapping("/getList")
    @PermissionRequired(perms = "rdvoucher:sheetManages:search")
    @ApiOperation(value = "获取凭证列表", notes = "获取凭证列表", response = PageModel.class)
    public PageModel<List<VoucherModel>> queryAll(QueryVoucherModel query) throws OwnerException {
        UserInfo info = getUserInfo();
        return voucherService.getList(query,info.getCompanyId());
    }

    @SystemLog(description = "检查凭证号是否重复")
    @GetMapping("/checkVoucherNo")
    @ApiOperation(value = "检查凭证号是否重复", notes = "检查凭证号是否重复")
    public Boolean checkVoucherNo(String voucherNo) throws OwnerException {
        UserInfo info = getUserInfo();
        return voucherService.checkVoucherNo(voucherNo, info.getCompanyId());
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @PostMapping("/addVoucher")
    @SystemLog(description = "添加凭证", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "rdvoucher:sheetManages:add")
    @ApiOperation(value = "添加凭证", notes = "添加凭证", response = Boolean.class)
    public Boolean addVoucher(@RequestBody @Validated VoucherModel model) throws OwnerException {
        UserInfo info = getUserInfo();
        return voucherService.addVoucher(model,info);
    }

    @PostMapping("/updateVoucher")
    @SystemLog(description = "修改凭证", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "rdvoucher:sheetManages:edit")
    @ApiOperation(value = "修改凭证", notes = "修改凭证", response = Boolean.class)
    public Boolean updateVoucher(@RequestBody @Validated VoucherModel model) throws OwnerException {
        UserInfo info = getUserInfo();
        return voucherService.updateVoucher(info, model);
    }

    /**
     * @param id
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "删除凭证", mode = SystemLog.SAVE_DB)
    @GetMapping("/delVoucher")
    @PermissionRequired(perms = "rdvoucher:sheetManages:delete")
    @ApiOperation(value = "删除凭证", notes = "删除凭证", response = Boolean.class)
    public Boolean delVoucher(Integer id) throws OwnerException {
        return voucherService.deleteById(getUserInfo().getCompanyId(), id);
    }

    /**
     * @param file
     * @param tableField
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "导入凭证", mode = SystemLog.SAVE_DB)
    @PostMapping("/importVoucher")
    @PermissionRequired(perms = "rdvoucher:sheetManages:import")
    @ApiOperation(value = "导入凭证", notes = "导入凭证", response = String.class)
    public Boolean importVoucher(@RequestParam("file") MultipartFile file, TableField tableField) throws OwnerException {
        UserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                info.getCompanyId());
        ExcelResult<VoucherExcel> excelResult = excelService.getExcelResult(tempPath, file, VoucherExcel.class, tableField);
        return voucherService.importVoucher(info, excelResult.getData());
    }


    @PostMapping("/relatedVoucher")
    @SystemLog(description = "关联项目", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "rdvoucher:SheetManages:related")
    @ApiOperation(value = "关联项目", notes = "关联项目", response = Boolean.class)
    public Boolean relatedVoucher(@RequestBody @Validated RelatedVoucherModel model) throws OwnerException {
        UserInfo info = getUserInfo();
        return voucherService.relatedVoucher(info, model);
    }
}
