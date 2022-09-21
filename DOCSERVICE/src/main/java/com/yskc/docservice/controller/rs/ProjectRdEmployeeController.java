package com.yskc.docservice.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.TableField;
import com.yskc.docservice.models.rs.projectrdemployee.BatchProjectRdEmployeeModel;
import com.yskc.docservice.models.rs.projectrdemployee.ProjectRdEmployeeModel;
import com.yskc.docservice.service.rs.ExcelService;
import com.yskc.docservice.service.rs.ProjectRdEmployeeService;
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
 * @CreateTime: 2020-03-31 11:06
 * @Description: 项目人员费用接口
 */
@RequestMapping("/doc/projectRdEmployee")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@Api(tags = "项目人员费用接口", value = "项目人员费用接口")
@RestController
public class ProjectRdEmployeeController extends RsBaseController {

    @Autowired
    private ProjectRdEmployeeService projectRdEmployeeService;
    @Autowired
    private ExcelService excelService;
    @Autowired
    private DocServiceConfig docServiceConfig;

    @SystemLog(description = "导入人员研发工时", mode = SystemLog.SAVE_DB)
    @PostMapping("/importRdHour")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "导入人员研发工时", notes = "导入人员研发工时")
    public Boolean importRdHour(@RequestParam("file") MultipartFile file, Integer projectId, Integer year, TableField tableField) throws OwnerException {
        RsUserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                docServiceConfig.getRsImportPath(),
                info.getCompanyId());
        ExcelResult<ProjectRdEmployeeModel> excelResult = excelService.getExcelResult(tempPath, file, ProjectRdEmployeeModel.class, tableField);
        BatchProjectRdEmployeeModel batchModel = new BatchProjectRdEmployeeModel();
        batchModel.setProjectId(projectId);
        batchModel.setList(excelResult.getData());
        return projectRdEmployeeService.importRdHour(info, batchModel, year);
    }

}
