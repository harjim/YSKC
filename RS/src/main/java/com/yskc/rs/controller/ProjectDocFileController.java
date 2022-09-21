package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.entity.project.ProjectDocFileEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.docFile.MeetingSignInModel;
import com.yskc.rs.models.docFile.ReceptionModel;
import com.yskc.rs.models.employee.EmployeeSelectModel;
import com.yskc.rs.models.hightechprogress.HighTechFileModel;
import com.yskc.rs.models.hightechprogress.QueryHighTechFileModel;
import com.yskc.rs.models.projectDocFile.ProjectDocFileModel;
import com.yskc.rs.models.stage.StageModel;
import com.yskc.rs.service.ProjectDocFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Api(tags = "过程文件接口", value = "过程文件接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/projectDocFile")
public class ProjectDocFileController extends BaseController {

    @Autowired
    private ProjectDocFileService projectDocFileService;

    @SystemLog(description = "新增过程文档", mode = SystemLog.SAVE_DB)
    @PostMapping("/addDocList")
    @PermissionRequired(perms = "project:doc:add")
    @ApiOperation(value = "新增过程文档", notes = "新增过程文档", response = Boolean.class)
    public Boolean addDocList(@RequestBody @Validated ProjectDocFileModel model) throws OwnerException {
        UserInfo info = getUserInfo();
        return projectDocFileService.addDocList(info, model);
    }


    @GetMapping("/queryStage")
    @PermissionRequired(perms = "project:doc:view")
    @ApiOperation(value = "查询阶段文件", notes = "查询阶段文件", response = String.class)
    public List<StageModel> queryStage(Integer projectId, int year) throws OwnerException {
        return projectDocFileService.queryStage(getUserInfo(), projectId,year);
    }


    @SystemLog(description = "删除阶段过程文件", mode = SystemLog.SAVE_DB)
    @PostMapping("/delete")
    @PermissionRequired(perms = "project:doc:del")
    @ApiOperation(value = "删除阶段过程文件", notes = "删除阶段过程文件", response = boolean.class)
    public boolean delete(@RequestBody @Validated ProjectDocFileModel model) throws OwnerException {
        return projectDocFileService.delete(model, getUserInfo());
    }

    @SystemLog(description = "批量删除阶段过程文件", mode = SystemLog.SAVE_DB)
    @PostMapping("/deleteDocFiles")
    @PermissionRequired(perms = "project:doc:delEmpty")
    @ApiOperation(value = "删除阶段过程文件", notes = "删除阶段过程文件", response = boolean.class)
    public boolean deleteDocFiles(@RequestBody @Validated List<Integer> ids) throws OwnerException {
        return projectDocFileService.deleteDocFiles(ids, getUserInfo());
    }

    @SystemLog(description = "修改过程文件名", mode = SystemLog.SAVE_DB)
    @PostMapping("/editDocFileName")
    @PermissionRequired(perms = "project:doc:edit")
    @ApiOperation(value = "修改文件名", notes = "修改文件名", response = boolean.class)
    public boolean editDocFileName(@RequestBody @Validated ProjectDocFileEntity entity) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return projectDocFileService.editDocFileName(userInfo, entity);
    }


    @GetMapping("/queryFileList")
    @ApiOperation(value = "研发项目资料清单", notes = "研发项目资料清单", response = String.class)
    public List<StageModel> queryFileList(Integer projectId, int year) {
        return projectDocFileService.queryFileList(projectId, year);
    }


    @GetMapping("/getMeetingEmployee")
    @ApiOperation(value = "获取项目参会人员列表", notes = "获取项目参会人员列表", response = String.class)
    public List<EmployeeSelectModel> getMeetingEmployee(Integer projectId, Date docDate,Integer pDocFileId) throws OwnerException {
        return projectDocFileService.getMeetingEmployee(projectId,getUserInfo().getCompanyId(),docDate,pDocFileId);
    }

    @GetMapping("/getAuditor")
    @ApiOperation(value = "获取评审委员会名单", notes = "获取评审委员会名单", response = String.class)
    public List<EmployeeSelectModel> getAuditor(Integer projectId,@RequestParam(value = "docDate",required = false) Date docDate,Integer pDocFileId) throws OwnerException {
        return projectDocFileService.getAuditor(projectId,getUserInfo().getCompanyId(),docDate,pDocFileId);
    }

    @GetMapping("/getReception")
    @ApiOperation(value = "获取试验试制通知单接收情况列表", notes = "获取试验试制通知单接收情况列表", response = String.class)
    public List<ReceptionModel> getReception(Integer projectId,Integer id)  {
        return projectDocFileService.getReception(projectId,id);
    }

    @GetMapping("/getMeeting")
    @ApiOperation(value = "获取会议纪要情况", notes = "获取会议纪要情况", response = String.class)
    public List<MeetingSignInModel> getMeeting(Integer projectId, Integer year) throws Exception {
        return projectDocFileService.getMeeting(projectId, getUserInfo().getCompanyId(), year);
    }

    @GetMapping("/getReportStage")
    @ApiOperation(value = "获取项目立项报告项目进度计划", notes = "获取项目立项报告项目进度计划", response = String.class)
    public List<StageModel> getReportStage(Integer projectId) throws OwnerException {
        return projectDocFileService.getReportStage(projectId,getUserInfo().getCompanyId());
    }

    @GetMapping("/getList")
    @ApiOperation(value = "获取评审委员会列表", notes = "获取评审委员会列表", response = String.class)
    public List<EmployeeSelectModel> getList(Integer year,String ename) throws OwnerException{
        return projectDocFileService.getList(getUserInfo().getCompanyId(),year,ename);
    }

    @GetMapping("getHighTechFiles")
    @ApiOperation(value = "获取高新材料文档", notes = "获取高新材料文档", response = String.class)
    public List<HighTechFileModel> getHighTechFiles(QueryHighTechFileModel model) throws OwnerException{
        return projectDocFileService.getHighTechFiles(model);
    }
}
