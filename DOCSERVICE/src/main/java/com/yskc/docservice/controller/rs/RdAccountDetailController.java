package com.yskc.docservice.controller.rs;

import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.RdAccountDetailExcel;
import com.yskc.docservice.models.rs.excel.TableField;
import com.yskc.docservice.service.rs.RdAccountDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: zhangdingfu
 * @CreateTime: 2019-09-30 10:56
 * @Description: 研发费用明细control层
 */
@RestController
@Api(value = "研发费用明细接口层", tags = "研发费用明细接口层")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RequestMapping("/doc/rdAccountDetail")
public class RdAccountDetailController extends RsBaseController {

    @Autowired
    private RdAccountDetailService accountDetailService;

    @Autowired
    private DocServiceConfig docServiceConfig;

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
        RsUserInfo userInfo = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                docServiceConfig.getRsImportPath(),
                userInfo.getCompanyId());
        ExcelResult<RdAccountDetailExcel> techProjectCostExcelExcels = excelService.getExcelResult(tempPath, file, RdAccountDetailExcel.class, tableField);
        accountDetailService.importInfo(userInfo, techProjectCostExcelExcels.getData(),accType);
        return techProjectCostExcelExcels.msg;
    }
}
