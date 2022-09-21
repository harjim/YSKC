package com.yskc.docservice.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.ProjectOutsourcingExcel;
import com.yskc.docservice.models.rs.excel.TableField;
import com.yskc.docservice.service.rs.ProjectOutsourcingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: zhangdingfu
 * @CreateTime: 2021-06-08 15:04
 * @Description: 项目委外费用接口
 */
@Api(tags = "项目委外费用接口", value = "项目委外费用接口")
@CrossOrigin(originPatterns = "*", allowedHeaders = "", allowCredentials = "true", methods = {})
@RestController
@RequestMapping("/doc/projectOutsourcing")
public class ProjectOutsourcingController extends RsBaseController {

    @Autowired
    private ProjectOutsourcingService projectOutsourcingService;
    @Autowired
    private DocServiceConfig docServiceConfig;

    @PostMapping("/importOutsourcing")
    @PermissionRequired(perms = "project:outsourcing:import")
    @ApiOperation(value = "导入委外项目费用", notes = "导入委外项目费用")
    @SystemLog(description = "导入委外项目费用", mode = SystemLog.SAVE_DB)
    public Boolean importOutsourcing(@RequestParam("file") MultipartFile file, TableField tableField, Integer type, Integer year) throws OwnerException {
        RsUserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                docServiceConfig.getRsImportPath(),
                info.getCompanyId());
        ExcelResult<ProjectOutsourcingExcel> excelResult = excelService.getExcelResult(tempPath, file, ProjectOutsourcingExcel.class, tableField);
        return projectOutsourcingService.importOutsourcing(info, excelResult.getData(), type, year);
    }

}
