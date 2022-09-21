package com.yskc.docservice.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.ProjectFinaScheduleExcel;
import com.yskc.docservice.models.rs.excel.TableField;
import com.yskc.docservice.service.rs.ProjectFinaScheduleService;
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
 * @CreateTime: 2021-06-17 14:15
 * @Description: 项目试验试制实际工时controller层
 */
@Api(value = "项目试验试制实际工时表接口", tags = "项目试验试制实际工时表接口")
@RestController
@CrossOrigin(originPatterns = "*", allowedHeaders = "", allowCredentials = "true", methods = {})
@RequestMapping("/doc/projectFinaSchedule")
public class ProjectFinaScheduleController extends RsBaseController {

    @Autowired
    private ProjectFinaScheduleService projectFinaScheduleService;
    @Autowired
    private DocServiceConfig docServiceConfig;

    @SystemLog(description = "导入试验试制实际工时", mode = SystemLog.SAVE_DB)
    @PostMapping("/importFinaSchedule")
    @PermissionRequired(perms = "project:finaSchedule:import")
    @ApiOperation(value = "导入试验试制实际工时", notes = "导入试验试制实际工时")
    public Boolean importFinaSchedule(@RequestParam("file") MultipartFile file, TableField tableField) throws OwnerException {
        RsUserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                docServiceConfig.getRsImportPath(),
                info.getCompanyId());
        ExcelResult<ProjectFinaScheduleExcel> excelResult = excelService.getExcelResult(tempPath, file, ProjectFinaScheduleExcel.class, tableField);
        return projectFinaScheduleService.importFinaSchedule(info, excelResult.getData());
    }
}
