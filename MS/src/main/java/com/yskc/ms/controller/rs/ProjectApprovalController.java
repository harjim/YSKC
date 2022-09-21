package com.yskc.ms.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.entity.rs.RsProjectEntity;
import com.yskc.ms.models.projectApproval.ProjectApprovalModel;
import com.yskc.ms.models.projectApproval.QueryProjectApprovalModel;
import com.yskc.ms.models.projectApproval.QueryProjectEquipmentModel;
import com.yskc.ms.models.projectApproval.QueryProjectMemberModel;
import com.yskc.ms.models.rs.InitMemberModel;
import com.yskc.ms.models.rs.summary.BatchCleanFundsModel;
import com.yskc.ms.models.rs.summary.InitEquipmentModel;
import com.yskc.ms.service.rs.RsProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by hck
 * on 2020/6/16 15:19
 * description:立项列表接口
 */
@Api(tags = "立项列表接口", value = "立项列表接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/projectApproval")
public class ProjectApprovalController extends BaseController {

    @Autowired
    private RsProjectService rsProjectService;

    @GetMapping("/getProjectApproval")
    @PermissionRequired(perms = "customer:projectApproval:search")
    @ApiOperation(value = "获取立项列表", notes = "获取立项列表")
    public PageModel<List<ProjectApprovalModel>> getProjectApproval(QueryProjectApprovalModel query) {
        return rsProjectService.getProjectApprovalList(query);
    }

    @SystemLog(description = "清除研发项目归集费用",mode = SystemLog.SAVE_DB)
    @PostMapping("/cleanRdFunds")
    @PermissionRequired(perms = "customer:projectApproval:cleanAll")
    @ApiOperation(value = "清除研发项目归集费用",notes = "清除研发项目归集费用")
    public Boolean cleanRdFunds(@RequestBody @Validated RsProjectEntity project)throws OwnerException {
        return rsProjectService.cleanRdFunds(project.getId(),getUserInfo().getId());
    }

    @SystemLog(description = "清除项目成员归集费用",mode = SystemLog.SAVE_DB)
    @PostMapping("/cleanEmployeeFunds")
    @PermissionRequired(perms = "customer:projectApproval:cleanEmployee")
    @ApiOperation(value = "清除项目成员归集费用",notes = "清除项目成员归集费用")
    public Boolean cleanEmployeeFunds(@RequestBody @Validated BatchCleanFundsModel cleanFunds)throws OwnerException {
        return rsProjectService.cleanEmployeeFunds(cleanFunds,getUserInfo().getId());
    }


    @SystemLog(description = "清除项目资产清单归集费用",mode = SystemLog.SAVE_DB)
    @PostMapping("/cleanEquipmentFunds")
    @PermissionRequired(perms = "customer:projectApproval:cleanEquipment")
    @ApiOperation(value = "清除项目资产清单归集费用",notes = "清除项目资产清单归集费用")
    public Boolean cleanEquipmentFunds(@RequestBody @Validated BatchCleanFundsModel cleanFunds)throws OwnerException {
        return rsProjectService.cleanEquipmentFunds(cleanFunds,getUserInfo().getId());
    }

    @GetMapping("/getProjectMemberList")
    @ApiOperation(value = "获取项目成员列表", notes = "获取项目成员列表")
    public PageModel<List<InitMemberModel>> getProjectMemberList(QueryProjectMemberModel query) {
        return rsProjectService.getProjectMemberList(query);
    }

    @GetMapping("/getProjectEquipmentList")
    @ApiOperation(value = "获取项目资产清单列表", notes = "获取项目资产清单列表")
    public PageModel<List<InitEquipmentModel>> getProjectEquipmentList(QueryProjectEquipmentModel query) {
        return rsProjectService.getProjectEquipmentList(query);
    }
}
