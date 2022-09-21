package com.yskc.docservice.controller.rs;


import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.ProjectExcel;
import com.yskc.docservice.models.rs.excel.TableField;
import com.yskc.docservice.service.rs.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.text.MessageFormat;


/**
 * 部门类接口
 *
 * @author huronghua
 */
@Api(tags = "项目类接口", value = "项目类接口")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/doc/project")
public class ProjectController extends RsBaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ProjectService projectService;
    @Autowired
    private DocServiceConfig docServiceConfig;

    /**
     * @param file
     * @param year
     * @param tableField
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "导入RD列表", mode = SystemLog.SAVE_DB)
    @PostMapping("/importProject")
    @PermissionRequired(perms = "project:report:list:import")
    @ApiOperation(value = "导入RD列表", notes = "导入RD列表", response = String.class)
    public String importProject(@RequestParam("file") MultipartFile file, Integer year, TableField tableField) throws OwnerException {
        RsUserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                docServiceConfig.getRsImportPath(),
                info.getCompanyId());
        ExcelResult<ProjectExcel> excelResult = excelService.getExcelResult(tempPath, file, ProjectExcel.class, tableField);
        return projectService.importProject(info, excelResult.getData(), year);
    }
}
