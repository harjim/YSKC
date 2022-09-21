package com.yskc.docservice.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.InitMemberExcel;
import com.yskc.docservice.models.rs.excel.TableField;
import com.yskc.docservice.service.rs.ExcelService;
import com.yskc.docservice.service.rs.InitMemberService;
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
 * @CreateTime: 2019-07-23 17:10
 * @Description: 初始化项目人员列表controller层
 */
@Api(tags = "项目成员接口", value = "项目成员接口")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/doc/initMember")
public class InitMemberController extends RsBaseController {

    @Autowired
    private InitMemberService initMemberService;

    @Autowired
    private DocServiceConfig docServiceConfig;

    @Autowired
    private ExcelService excelService;


    @SystemLog(description = "导入项目成员", mode = SystemLog.SAVE_DB)
    @PostMapping("/importMember")
    @PermissionRequired(perms = "project:report:member:import")
    @ApiOperation(value = "导入项目成员", notes = "导入项目成员")
    public String importMember(@RequestParam("file") MultipartFile file, TableField tableField, int year, Integer projectId) throws OwnerException {
        RsUserInfo userInfo = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                docServiceConfig.getRsImportPath(),
                userInfo.getCompanyId());
        ExcelResult<InitMemberExcel> excelResult = excelService.getExcelResult(tempPath, file, InitMemberExcel.class, tableField);
        initMemberService.importMember(userInfo, excelResult.getData(), year, projectId);
        return excelResult.msg;
    }
}
