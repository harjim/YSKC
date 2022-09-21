package com.yskc.ms.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageResult;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.innovationproject.InnovationMemberSelectModel;
import com.yskc.ms.models.project.QueryProjectDetailModel;
import com.yskc.ms.models.project.RsProjectMasterModel;
import com.yskc.ms.service.rs.ProjectDetailSummaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/7/24 10:50
 * @Description:rs立项负责人
 */
@Api(tags = "rs立项负责人接口", value = "rs立项负责人接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/rsProjectMaster")
public class RsProjectMasterController extends BaseController {

    @Autowired
    private ProjectDetailSummaryService projectDetailSummaryService;

    @GetMapping("/getList")
    @PermissionRequired(perms = "innovation:rdManager:search")
    @ApiOperation(value = "rs立项负责人列表", notes = "rs立项负责人列表", response = List.class)
    public PageResult getList(QueryProjectDetailModel query) throws OwnerException {
        return projectDetailSummaryService.getList(query, getDataPerm());
    }

    @PostMapping("/saveMaster")
    @PermissionRequired(perms = "innovation:rdManager:relateMaster")
    @SystemLog(description = "保存项目负责人", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "保存项目负责人", notes = "保存项目负责人")
    public Boolean saveDemand(@RequestBody @Validated RsProjectMasterModel model) {
        return projectDetailSummaryService.saveMaster(model);
    }

    @GetMapping("/getMemberList")
    @PermissionRequired(perms = "innovation:rdManager:relateMaster")
    @ApiOperation(value = "获取创新项目成员", notes = "获取创新项目成员")
    public List<InnovationMemberSelectModel> getMemberList(Integer companyId, Integer year,Integer mType) {
        return projectDetailSummaryService.getMemberList(companyId, year,mType);
    }

    @SystemLog(description = "导出立项管理数据", mode = SystemLog.SAVE_DB)
    @GetMapping("/export")
    @PermissionRequired(perms = "innovation:rdManager:export")
    @ApiOperation(value = "导出立项管理数据", notes = "导出立项管理数据")
    public void exportPlan(QueryProjectDetailModel query) throws  OwnerException {
        UserInfo info = getUserInfo();
        try (OutputStream out = outGeneralFile(MessageFormat.format("立项管理数据表.xlsx", query.getYear()))) {
            projectDetailSummaryService.exportPlan(query,info,out,getDataPerm());
            out.flush();
        } catch (OwnerException oe){
            throw oe;
        }catch (Exception e) {
            throw new OwnerException("导出失败");
        }finally {

        }

    }
}
