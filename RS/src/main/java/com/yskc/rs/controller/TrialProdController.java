package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.DateUtil;
import com.yskc.rs.entity.TrialProdEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.material.MaterialPlanModel;
import com.yskc.rs.models.material.QueryMaterialTrackModel;
import com.yskc.rs.models.trialprod.*;
import com.yskc.rs.service.TrialProdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Api(tags = "项目试制类接口", value = "项目试制类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/trialProd")
public class TrialProdController extends BaseController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TrialProdService trialProdService;

    /**
     * @param queryTrialModel
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryTrial")
    @PermissionRequired(perms = "project:report:trial:search")
    @ApiOperation(value = "查询试制", notes = "查询试制", response = String.class)
    public PageModel<List<TrialProdModel>> queryTrial(QueryTrialModel queryTrialModel)
            throws OwnerException {
        return trialProdService.queryTrial(getUserInfo().getCompanyId(), queryTrialModel);
    }

    /**
     * @param entity
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "新增试制记录", mode = SystemLog.SAVE_DB)
    @PostMapping("/addTrial")
    @PermissionRequired(perms = "project:report:trial:add")
    @ApiOperation(value = "新增试制记录", notes = "新增试制记录", response = boolean.class)
    public boolean addTrial(@RequestBody @Validated TrialProdEntity entity) throws OwnerException {
        return trialProdService.addTrial(getUserInfo(), entity);
    }


    /**
     * @param entity
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "编辑试制记录", mode = SystemLog.SAVE_DB)
    @PostMapping("/editTrial")
    @PermissionRequired(perms = "project:report:trial:edit")
    @ApiOperation(value = "编辑试制记录", notes = "编辑试制记录", response = boolean.class)
    public boolean editTrial(@RequestBody @Validated TrialProdEntity entity) throws OwnerException {
        return trialProdService.editTrial( getUserInfo(), entity);
    }

    /**
     * @param entity
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "删除试制记录", mode = SystemLog.SAVE_DB)
    @PostMapping("/delTrial")
    @PermissionRequired(perms = "project:report:trial:del")
    @ApiOperation(value = "删除试制记录", notes = "删除试制记录", response = boolean.class)
    public boolean delTrial(@RequestBody @Validated TrialProdEntity entity) throws OwnerException {
        return trialProdService.delTrial(entity,getUserInfo());
    }


    @GetMapping("/getSummaryList")
//    @PermissionRequired(perms = "project:report:trial:search")
    @ApiOperation(value = "查询试制统计数据", notes = "查询试制统计数据", response = String.class)
    public List<StageTrialShowModel> getSummaryList(Integer projectId,Integer pDocFileId)
            throws OwnerException {
        return trialProdService.getSummaryList(getUserInfo().getCompanyId(), projectId,pDocFileId);
    }

    @GetMapping("/getDocFileTrials")
//    @PermissionRequired(perms = "project:report:trial:search")
    @ApiOperation(value = "查询未使用的试制统计数据", notes = "查询未使用的试制统计数据", response = String.class)
    public List<StageTrialModel> getDocFileTrials(Integer projectId,Integer docFileId,String stage)
            throws OwnerException {
        return trialProdService.getDocFileTrials(getUserInfo().getCompanyId(), projectId,stage);
    }

    @SystemLog(description = "查询文档试制", mode = SystemLog.SAVE_DB)
    @GetMapping("/queryDocFileTrial")
    @ApiOperation(value = "查询文档试制", notes = "查询文档试制", response = boolean.class)
    public Map<String,Object> queryDocFileTrial(Integer docFileId, Integer projectId, Date month) throws OwnerException {
        UserInfo userInfo=getUserInfo();
        return trialProdService.queryDocFileTrial(userInfo.getCompanyId(), docFileId,projectId,month);
    }

    @GetMapping("/getSumarryTrialProd")
   @PermissionRequired(perms = "project:trialProdPlan:search")
    @ApiOperation(value = "查询项目试制计划", notes = "查询项目试制计划", response = String.class)
    public PageModel<List<TrialProdPlanModel>> getSumarryTrialProd(QueryTrialPlanModel queryTrialPlanModel)
            throws OwnerException {
        if(queryTrialPlanModel.getMonth()!=null){
            queryTrialPlanModel.setMonthFirstDay(DateUtil.getMonthFirstDay(queryTrialPlanModel.getMonth()));
            queryTrialPlanModel.setMonthLastDay(DateUtil.getMonthLastDay(queryTrialPlanModel.getMonth()));
        }
        return trialProdService.getTrialPlan(getUserInfo().getCompanyId(), queryTrialPlanModel);
    }

    @SystemLog(description = "导出试制计划表", mode = SystemLog.SAVE_DB)
    @GetMapping("/exportTrialProdPlan")
    @ApiOperation(value = "导出试制计划表", notes = "导出试制计划表")
    @PermissionRequired(perms = "project:trialProdPlan:export")
    public void exportTrialProdPlan(QueryTrialPlanModel queryTrialPlanModel) throws IOException, OwnerException {
        try (OutputStream out = outGeneralFile("试制计划表.xlsx")) {
            trialProdService.exportTrialProdPlan( getUserInfo(), out,queryTrialPlanModel);
            out.flush();
        } catch (Exception e) {
          logger.error(e.getMessage(),e);
            throw new OwnerException("导出失败");
        }
    }

    @GetMapping("/getTrialData")
    @ApiOperation(value = "获取试制材料计划表数据", notes = "获取试制材料计划表数据", response = String.class)
    public PageModel<List<MaterialPlanModel>> getTrialData(QueryMaterialTrackModel model) throws OwnerException {
        return trialProdService.getTrialData(model);
    }
}
