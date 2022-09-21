package com.yskc.rs.controller;

import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.entity.MeetMemberEntity;
import com.yskc.rs.models.docFile.DocFileModel;
import com.yskc.rs.service.DocFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "过程文档接口", value = "过程文档接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/docFile")
public class DocFileController extends BaseController {

    @Autowired
    private DocFileService docFileService;

    @GetMapping("/queryDocFile")
    @ApiOperation(value = "查询过程文档列表", notes = "查询过程文档列表", response = String.class)
    public List<DocFileModel> queryDocFile(String stage) {
        return docFileService.queryDocFile(stage);
    }

    @GetMapping("/getMeetMember")
    @ApiOperation(value = "获取参会人员", notes = "获取参会人员", response = String.class)
    public String getMeetMember(Integer projectId) {
        return docFileService.getMeetMember(projectId);
    }

    @PostMapping("/setMeetMember")
    @SystemLog(description = "保存参会人员", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "保存参会人员", notes = "保存参会人员", response = String.class)
    public Boolean setMeetMember(@RequestBody @Validated MeetMemberEntity model) throws OwnerException {
        return docFileService.setMeetMember(model,getUserInfo());
    }
}
