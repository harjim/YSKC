package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.PageResult;
import com.yskc.ms.config.Constant;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.models.GroupRAndDManagementModel;
import com.yskc.ms.models.innovationproject.BatchMasterStaffModel;
import com.yskc.ms.models.project.QueryProjectProgressModel;
import com.yskc.ms.models.rdfunds.QueryFundsModel;
import com.yskc.ms.service.ms.InnovationProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.OutputStream;
import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/7/24 9:54
 * @Description:
 */
@Api(tags = "创新项目接口", value = "创新项目接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/innovationProject")
public class InnovationProjectController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private InnovationProjectService innovationProjectService;
    @Autowired
    private MsConfig msConfig;

    @GetMapping("/getList")
    @PermissionRequired(perms="innovation:innovationProject:search")
    @ApiOperation(value = "获取流程列表", notes = "获取流程列表")
    public PageResult getList(QueryProjectProgressModel query) throws OwnerException {
        return innovationProjectService.getList(query, getUserInfo(), getDataPerm());
    }

    @PostMapping("/setTechIds")
    @SystemLog(description = "设置技术负责人及成员",mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms="innovation:innovationProject:relateTech")
    @ApiOperation(value = "设置技术负责人及成员", notes = "设置技术负责人及成员")
    public Boolean setProjectTechIds(@RequestBody @Validated BatchMasterStaffModel batch) throws OwnerException {
        return innovationProjectService.setProjectTechIds(batch, getUserInfo());
    }

    @PostMapping("/setFinanceIds")
    @SystemLog(description = "设置财务负责人及成员",mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms="innovation:innovationProject:relateFina")
    @ApiOperation(value = "设置财务负责人及成员", notes = "设置财务负责人及成员")
    public Boolean setFinanceIds(@RequestBody @Validated BatchMasterStaffModel batch) throws OwnerException {
        return innovationProjectService.setFinanceIds(batch, getUserInfo());
    }

    @GetMapping("/getTableData")
    @PermissionRequired(perms = "innovation:innovationProjectReport:getData")
    @ApiOperation(notes = "获取创新项目报表数据",value = "获取创新项目报表数据")
    public PageResult getTableData(QueryFundsModel model)throws OwnerException {
        return innovationProjectService.getTableData(model,getDataPerm());
    }

    @GetMapping("/export")
    @PermissionRequired(perms = "innovation:innovationProjectReport:export")
    @ApiOperation(notes = "导出创新项目报表",value = "导出创新项目报表")
    public void getTableExport(QueryFundsModel model)throws OwnerException {
        String fileName;
        String templateName;
        fileName = "创新项目报表.xls";
        templateName = "创新项目报表模板.xls";
        String path = msConfig.getUploadConfig().getResourcePath() + Constant.TEMPLATE_DIR + templateName;
        try (OutputStream out = outGeneralFile(fileName)) {
            innovationProjectService.getTableExport(model, this.getDataPerm(), out, path);
            out.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new OwnerException("导出失败");
        }
    }
}
