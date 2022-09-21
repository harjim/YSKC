package com.yskc.docservice.controller.rs;

import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.TableField;
import com.yskc.docservice.models.rs.excel.TechProjectCostExcel;
import com.yskc.docservice.service.rs.TechProjectCostService;
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
 * @CreateTime: 2019-09-25 08:29
 * @Description: 项目支出control
 */
@RestController
@Api(value = "项目支出接口类", tags = "项目支出接口类")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RequestMapping("/doc/techProjectCost")
public class TechProjectCostController extends RsBaseController {

    @Autowired
    private TechProjectCostService techProjectCostService;

    @Autowired
    private DocServiceConfig docServiceConfig;

    /**
     * @param file
     * @param tableField
     * @param projectId
     * @return
     * @throws OwnerException
     */
    @PostMapping("/importInfo")
    @SystemLog(description = "导入项目支出",mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "导入项目支出", notes = "导入项目支出")
    public String importInfo(@RequestParam("file") MultipartFile file, TableField tableField, Integer projectId) throws OwnerException {
        RsUserInfo userInfo = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                docServiceConfig.getRsImportPath(),
                userInfo.getCompanyId());
        ExcelResult<TechProjectCostExcel> techProjectCostExcelExcels = excelService.getExcelResult(tempPath, file, TechProjectCostExcel.class, tableField);
        techProjectCostService.importInfo(userInfo, techProjectCostExcelExcels.getData(), projectId);
        return techProjectCostExcelExcels.msg;
    }
}
