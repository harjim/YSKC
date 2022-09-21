package com.yskc.docservice.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.TableField;
import com.yskc.docservice.models.rs.excel.VoucherExcel;
import com.yskc.docservice.service.rs.VoucherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
/**
 * Created by hck
 * on 2020/7/14 15:03
 * description:凭证接口
 */
@Api(tags = "用户类接口", value = "用户类接口")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/doc/voucher")
public class VoucherController extends RsBaseController{

    @Autowired
    private VoucherService voucherService;
    @Autowired
    private DocServiceConfig docServiceConfig;

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
        RsUserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                docServiceConfig.getRsImportPath(),
                info.getCompanyId());
        ExcelResult<VoucherExcel> excelResult = excelService.getExcelResult(tempPath, file, VoucherExcel.class, tableField);
        return voucherService.importVoucher(info, excelResult.getData());
    }
}
