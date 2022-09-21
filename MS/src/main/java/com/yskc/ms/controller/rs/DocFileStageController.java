package com.yskc.ms.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.entity.rs.DocFileStageEntity;
import com.yskc.ms.models.doc.DocFileBaseModel;
import com.yskc.ms.models.params.DocFileStageParams;
import com.yskc.ms.models.rs.DocFileStageModel;
import com.yskc.ms.service.rs.DocFileStageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "阶段文件列表接口", value = "阶段文件列表接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/docFileStage")
public class DocFileStageController extends BaseController {

    @Autowired
    private DocFileStageService docFileStageService;

    @PermissionRequired(perms = "doc:docFileStage:search")
    @GetMapping("/queryDocFileStageList")
    @ApiOperation(value = "查询阶段文件", notes = "查询阶段文件", response = String.class)
    public List<DocFileStageModel> queryDocFileStageList(String stageKey) throws OwnerException {
        return docFileStageService.queryDocFileStageList(stageKey);
    }

    @PermissionRequired(perms = "doc:docFileStage:save")
    @SystemLog(description = "新增阶段文件",mode = SystemLog.SAVE_DB)
    @PostMapping("/saveDocFileStage")
    @ApiOperation(value = "新增阶段文件", notes = "新增阶段文件")
    public Integer saveDocFieStage(@RequestBody @Validated DocFileStageParams docFileStageParams) throws OwnerException {
        return docFileStageService.saveDocFileStages(docFileStageParams, getUserInfo().getId());
    }

    @PermissionRequired(perms = "doc:docFileStage:setting")
    @SystemLog(description = "编辑阶段文件",mode = SystemLog.SAVE_DB)
    @PostMapping("/updateDocFileStage")
    @ApiOperation(value = "编辑阶段文件", notes = "编辑阶段文件")
    public int updateDocFileStage(@RequestBody @Validated DocFileStageEntity docFileStageEntity) throws OwnerException {
        return docFileStageService.updateDocFileStage(docFileStageEntity, getUserInfo().getId());
    }

    @PermissionRequired(perms = "doc:docFileStage:order")
    @SystemLog(description = "编辑阶段文件Seq",mode = SystemLog.SAVE_DB)
    @PostMapping("/updateSeq")
    @ApiOperation(value = "编辑阶段文件Seq", notes = "编辑阶段文件Seq")
    public Boolean updateSeq(@RequestBody @Validated Integer[] ids) throws OwnerException {

        return docFileStageService.updateSeq(ids, getUserInfo().getId());
    }

    @PermissionRequired(perms = "doc:docFileStage:del")
    @SystemLog(description = "删除阶段文件",mode = SystemLog.SAVE_DB)
    @PostMapping("/del")
    @ApiOperation(value = "删除阶段文件", notes = "删除阶段文件")
    public Integer del(@RequestBody @Validated DocFileStageEntity docFileStageEntity) throws OwnerException {
        return docFileStageService.delDocFileStage(docFileStageEntity.getId());
    }

    @GetMapping("/getStageNoDocFiles")
    @ApiOperation(value = "获取阶段不存在的文档列表", notes = "获取阶段不存在的文档列表")
    public List<DocFileBaseModel> getStageNoDocFiles(String stageKey) {
        return docFileStageService.getStageNoDocFiles(stageKey);
    }

}
