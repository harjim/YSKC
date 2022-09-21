package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.PageResult;
import com.yskc.ms.models.projectAudit.BatchAuditModel;
import com.yskc.ms.models.qualityscore.*;
import com.yskc.ms.service.ms.QualityScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @DateTime: 2022/5/13 15:43
 * @Description:
 * @author: hsx
 */
@Api(tags = "质量评分接口", value = "质量评分接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/qualityScore")
public class QualityScoreController extends BaseController{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QualityScoreService qualityScoreService;

    @SystemLog(description = "保存评分", mode = SystemLog.SAVE_DB)
    @PostMapping("/saveScore")
    @PermissionRequired(perms = "customer:projectProgress:score")
    @ApiOperation(value = "保存评分", notes = "保存评分", response = boolean.class)
    public Boolean saveScore(@RequestBody @Validated QualityScoreModel model) throws OwnerException {
        return qualityScoreService.saveScore(model,getUserInfo());
    }

    @GetMapping("/getList")
    @ApiOperation(value = "获取评分历史日志", notes = "获取评分历史日志")
    @PermissionRequired(perms = "customer:projectProgress:scoreLog")
    public Map<String,List<QualityScoreLogModel>> getList(QueryQualityScoreModel model) throws OwnerException {
        return qualityScoreService.getList(model);
    }

    @GetMapping("/getScoreConfig")
    @ApiOperation(value = "获取评分配置信息", notes = "获取评分配置信息")
    public QualityScoreInfoModel getScoreConfig(QueryQualityScoreModel model) throws OwnerException {
        return qualityScoreService.getScoreConfig(model);
    }

    @GetMapping("/getCheckConfig")
    @ApiOperation(value = "获取批量评分工程师信息", notes = "获取批量评分工程师信息")
    public List<EngineerModel> getCheckConfig(BatchAuditModel model) throws OwnerException {
        return qualityScoreService.getCheckConfig(model.getInstanceIds());
    }

    @SystemLog(description = "修改评分", mode = SystemLog.SAVE_DB)
    @PostMapping("/editScore")
    @ApiOperation(value = "修改评分", notes = "修改评分", response = boolean.class)
    public Boolean editScore(@RequestBody @Validated QualityScoreModel model) throws OwnerException {
        return qualityScoreService.editScore(model,getUserInfo());
    }

    @SystemLog(description = "修改权值", mode = SystemLog.SAVE_DB)
    @PostMapping("/saveWeight")
    @PermissionRequired(perms = "innovation:TimeAccount:weightSet")
    @ApiOperation(value = "修改权值", notes = "修改权值", response = boolean.class)
    public Boolean saveWeight(@RequestBody @Validated SaveScoreTypeModel model) throws OwnerException {
        return qualityScoreService.saveWeight(model.getList(), getUserInfo().getId());
    }

    @GetMapping("/getWeights")
    @ApiOperation(value = "获取权值配置信息", notes = "获取权值配置信息")
    public List<ScoreTypeModel> getWeights(){
        return qualityScoreService.getWeights();
    }

    @GetMapping("/getCollectList")
    @PermissionRequired(perms = "innovation:TimeAccount:search")
    @ApiOperation(value = "获取评分汇总数据", notes = "获取评分汇总数据")
    public PageResult getCollectList(QueryScoreCollectModel model)throws OwnerException{
        return qualityScoreService.getCollectList(model,getDataPerm());
    }

    @GetMapping("/getScoreList")
    @PermissionRequired(perms = "innovation:TimeAccount:search")
    @ApiOperation(value = "获取评分下拉列表数据", notes = "获取评分下拉列表数据")
    public PageModel<List<ScoreModel>> getScoreList(QueryScoreModel model)throws OwnerException{
        return qualityScoreService.getScoreList(model,getDataPerm());
    }

    @GetMapping("/export")
    @PermissionRequired(perms = "innovation:TimeAccount:export")
    @ApiOperation(value = "导出评分下拉列表数据", notes = "导出评分下拉列表数据")
    @SystemLog(description = "导出评分下拉列表数据")
    public void export(QueryScoreCollectModel model) throws Exception {
        OutputStream out = outGeneralFile("审核序时账表.xlsx");
        qualityScoreService.export(out, model, getDataPerm());
        out.flush();
        out.close();
    }


    @SystemLog(description = "修改记录完成情况", mode = SystemLog.SAVE_DB)
    @PostMapping("/editCompletion ")
    @PermissionRequired(perms = "innovation:TimeAccount:completionSet")
    @ApiOperation(value = "修改记录完成情况", notes = "修改记录完成情况", response = boolean.class)
    public Boolean editCompletion(@RequestBody @Validated CompletionEditModel model) throws OwnerException {
        return qualityScoreService.editCompletion(model, getUserInfo().getId());
    }
}
