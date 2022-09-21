package com.yskc.docservice.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.entity.rs.project.ProjectYieldConfigEntity;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.TableField;
import com.yskc.docservice.service.rs.ExcelService;
import com.yskc.docservice.service.rs.ProjectYieldConfigService;
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
 * @CreateTime: 2020-09-02 18:15
 * @Description: 项目试制量配置接口
 */

@Api(tags = "项目试制量配置接口", value = "项目试制量配置接口")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/doc/projectYieldConfig")
public class ProjectYieldConfigController extends RsBaseController {

    @Autowired
    private ProjectYieldConfigService projectYieldConfigService;
    @Autowired
    private ExcelService excelService;
    @Autowired
    private DocServiceConfig docServiceConfig;

    @SystemLog(description = "导入试制量配置",mode=SystemLog.SAVE_DB)
    @PostMapping("/importYield")
    @PermissionRequired(perms = "project:data:agg,project:trialProdPlan:import")
    @ApiOperation(value = "导入试制量配置",notes = "导入试制量配置")
    public boolean importYield(@RequestParam("file") MultipartFile file, Integer projectId, TableField tableField)throws OwnerException{
        RsUserInfo userInfo = getUserInfo();
        //读取文件路径
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                docServiceConfig.getRsImportPath(),
                userInfo.getCompanyId());
        // 传入文件夹  读数据
        ExcelResult<ProjectYieldConfigEntity> excelResult = excelService.getExcelResult(tempPath, file, ProjectYieldConfigEntity.class, tableField);
        return  projectYieldConfigService.importYield(userInfo, excelResult.getData(),projectId);
    }

}
