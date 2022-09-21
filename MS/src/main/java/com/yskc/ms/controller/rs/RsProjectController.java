package com.yskc.ms.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.models.project.DataReportModel;
import com.yskc.ms.models.rs.ProjectStatusModel;
import com.yskc.ms.models.rs.RdFundsModel;
import com.yskc.ms.models.rs.RsProjectBaseModel;
import com.yskc.ms.models.rs.RsProjectListModel;
import com.yskc.ms.service.rs.RsProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@Api(tags = "获取项目进度接口", value = "获取项目进度接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/rsProject")
public class RsProjectController extends BaseController {
    @Autowired
    private RsProjectService rsProjectService;

    /**
     * 归集总表
     *
     * @param year
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getDataReportFunds")
    @ApiOperation(value = "归集总表", notes = "归集总表")
    public List<DataReportModel> getDataReportFunds(Integer companyId, Integer year) throws OwnerException, ParseException {
        return rsProjectService.getDataReportFundsData(year, companyId);
    }

    @GetMapping("/getRdFunds")
    @PermissionRequired(perms = "customer:finalization:rdDetail")
    @ApiOperation(value = "获取项目月份归集明细", notes = "获取项目月份归集明细")
    public List<RdFundsModel> getRdFunds(Integer companyId, Integer year) {
        return rsProjectService.getRdFunds(companyId, year);
    }

    @PostMapping("/setStatus")
    @SystemLog(description = "设置rs项目定稿状态", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "customer:finalization:submit,customer:finalization:refuse,customer:finalization:final,customer:finalization:recall")
    @ApiOperation(value = "设置rs项目定稿状态", notes = "设置rs项目定稿状态")
    public Boolean setStatus(@RequestBody @Validated ProjectStatusModel projectStatus) throws OwnerException {
        return rsProjectService.setStatus(projectStatus, getUserInfo());
    }

    @GetMapping("/getProjectList")
    @PermissionRequired(perms = "customer:finalization:rdDetail")
    public List<RsProjectBaseModel> getProjectList(Integer companyId, Integer year) {
        return rsProjectService.getProjectList(companyId, year);
    }

    @PostMapping("/budgetFinal")
    @SystemLog(description = "审核rs项目预算", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "customer:finalization:final")
    @ApiOperation(value = "审核rs项目预算", notes = "审核rs项目预算")
    public Boolean budgetFinal(@Validated @RequestBody ProjectStatusModel projectStatus) throws OwnerException {
        return rsProjectService.budgetFinal(projectStatus, getUserInfo());
    }

    @PostMapping("/budgetRecall")
    @SystemLog(description = "撤回rs项目预算审核", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "customer:finalization:final")
    @ApiOperation(value = "撤回rs项目预算审核", notes = "撤回rs项目预算审核")
    public Boolean budgetRecall(@Validated @RequestBody ProjectStatusModel projectStatus) throws OwnerException {
        return rsProjectService.budgetRecall(projectStatus, getUserInfo());
    }
}
