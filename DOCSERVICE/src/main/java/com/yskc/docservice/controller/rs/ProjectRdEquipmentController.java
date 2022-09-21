package com.yskc.docservice.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.TableField;
import com.yskc.docservice.models.rs.projectrdequipment.BatchProjectRdEquipmentModel;
import com.yskc.docservice.models.rs.projectrdequipment.ProjectRdEquipmentModel;
import com.yskc.docservice.service.rs.ExcelService;
import com.yskc.docservice.service.rs.ProjectRdEquipmentService;
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
 * @CreateTime: 2020-04-01 11:03
 * @Description: 项目设备研发折旧接口
 */
@RestController
@RequestMapping("/doc/projectRdEquipment")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@Api(tags = "项目设备研发折旧接口", value = "项目设备研发折旧接口")
public class ProjectRdEquipmentController extends RsBaseController {

    @Autowired
    private ProjectRdEquipmentService projectRdEquipmentService;
    @Autowired
    private ExcelService excelService;
    @Autowired
    private DocServiceConfig docServiceConfig;

    @SystemLog(description = "导入设备研发工时", mode = SystemLog.SAVE_DB)
    @PostMapping("/importRdHour")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "导入设备研发工时", notes = "导入设备研发工时")
    public Boolean importRdHour(@RequestParam("file") MultipartFile file, Integer year, Integer projectId, TableField tableField) throws OwnerException {
        RsUserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                docServiceConfig.getRsImportPath(),
                info.getCompanyId());
        ExcelResult<ProjectRdEquipmentModel> excelResult = excelService.getExcelResult(tempPath, file, ProjectRdEquipmentModel.class, tableField);
        BatchProjectRdEquipmentModel batchModel = new BatchProjectRdEquipmentModel();
        batchModel.setProjectId(projectId);
        batchModel.setList(excelResult.getData());
        return projectRdEquipmentService.importRdHour(info,batchModel,year);
    }
}
