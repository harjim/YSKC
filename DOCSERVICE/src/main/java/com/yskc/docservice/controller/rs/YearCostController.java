package com.yskc.docservice.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.MonthCostExcel;
import com.yskc.docservice.models.rs.excel.TableField;
import com.yskc.docservice.service.rs.YearCostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.List;

/**
 * @BelongsProject:
 * @BelogsPackage: src/main/java/com/yskc/docservice/controller/rs
 * @Author: hm
 * @CreateTime: 2022/8/16
 * @Description:
 */
@RestController
@Api( tags = "月度成本接口", value = "月度成本接口" )
@CrossOrigin( originPatterns = "*", allowCredentials = "true", allowedHeaders = "", methods = {} )
@RequestMapping( "/doc/yearCost" )
public class YearCostController extends RsBaseController {
    @Autowired
    private YearCostService yearCostService;

    @Autowired
    private DocServiceConfig docServiceConfig;

    @SystemLog(description = "导入月度成本", mode = SystemLog.SAVE_DB)
    @PostMapping("/importYearCost")
    @PermissionRequired(perms = "company:yearCost:import")
    @ApiOperation(notes = "导入月度成本", value = "导入月度成本")
    public String importYearCost(@RequestParam("file")MultipartFile file, TableField tableField) throws OwnerException {
        RsUserInfo userInfo = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                docServiceConfig.getRsImportPath(),
                userInfo.getCompanyId());
        ExcelResult<MonthCostExcel> monthCostExcels = excelService.getExcelResult(tempPath, file, MonthCostExcel.class, tableField);
        yearCostService.importMonthCost(userInfo, monthCostExcels.getData());
        return monthCostExcels.msg;
    }
}
