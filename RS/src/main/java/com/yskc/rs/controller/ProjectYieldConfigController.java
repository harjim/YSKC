package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.entity.project.ProjectYieldConfigEntity;
import com.yskc.rs.models.BatchDeleteModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.models.projectyieldconfig.*;
import com.yskc.rs.service.ExcelService;
import com.yskc.rs.service.ProjectYieldConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: zhangdingfu
 * @CreateTime: 2020-09-02 18:15
 * @Description: 项目试制量配置接口
 */

@Api(tags = "项目试制量配置接口", value = "项目试制量配置接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/projectYieldConfig")
public class ProjectYieldConfigController extends BaseController {

    @Autowired
    private ProjectYieldConfigService projectYieldConfigService;
    @Autowired
    private ExcelService excelService;
    @Autowired
    private RsConfig rsConfig;

    @GetMapping("/getList")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "获取试制量配置", notes = "获取试制量配置")
    public List<ProjectYieldConfigModel> getList(QueryYieldConfigModel query) {
        return projectYieldConfigService.getList(query);
    }


    @SystemLog(description = "添加试制量配置", mode = SystemLog.SAVE_DB)
    @PostMapping("/add")
    @PermissionRequired(perms = "project:data:agg,project:trialProdPlan:add")
    @ApiOperation(value = "添加试制量配置", notes = "添加试制量配置")
    public boolean add(@RequestBody @Validated ProjectYieldConfigEntity config) throws OwnerException {
        return projectYieldConfigService.add(getUserInfo(), config);
    }


    @SystemLog(description = "编辑试制量配置", mode = SystemLog.SAVE_DB)
    @PostMapping("/edit")
    @PermissionRequired(perms = "project:data:agg,project:trialProdPlan:edit")
    @ApiOperation(value = "编辑试制量配置", notes = "编辑试制量配置")
    public boolean edit(@RequestBody @Validated ProjectYieldConfigEntity config) throws OwnerException {
        return projectYieldConfigService.edit(getUserInfo(), config);
    }


    @SystemLog(description = "删除试制量配置", mode = SystemLog.SAVE_DB)
    @PostMapping("/delete")
    @PermissionRequired(perms = "project:data:agg,project:trialProdPlan:delete")
    @ApiOperation(value = "删除试制量配置", notes = "删除试制量配置")
    public boolean delete(@RequestBody @Validated BatchDeleteModel model) throws OwnerException {
        return projectYieldConfigService.delete(model);
    }


    @SystemLog(description = "应用试制量配置", mode = SystemLog.SAVE_DB)
    @PostMapping("/handleRd")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "应用试制量配置", notes = "应用试制量配置")
    public boolean handleRd(@RequestBody @Validated QueryYieldConfigModel config) throws OwnerException {
        return projectYieldConfigService.handleRd(getUserInfo(),config);
    }

    @SystemLog(description = "导入试制量配置",mode=SystemLog.SAVE_DB)
    @PostMapping("/importYield")
    @PermissionRequired(perms = "project:data:agg,project:trialProdPlan:import")
    @ApiOperation(value = "导入试制量配置",notes = "导入试制量配置")
    public boolean importYield(@RequestParam("file") MultipartFile file, Integer projectId,TableField tableField)throws OwnerException{
        UserInfo userInfo = getUserInfo();
        //读取文件路径
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                userInfo.getCompanyId());
        // 传入文件夹  读数据
        ExcelResult<ProjectYieldConfigEntity> excelResult = excelService.getExcelResult(tempPath, file, ProjectYieldConfigEntity.class, tableField);
        return  projectYieldConfigService.importYield(userInfo, excelResult.getData(),projectId);
    }

    @GetMapping("/queryPYieldConfigList")
    @PermissionRequired(perms = "project:trialProdPlan:search")
    @ApiOperation(value = "查询研发试制计划", notes = "查询研发试制计划", response = String.class)
    public PageModel<List<ProjectYieldConfigModel>> queryPYieldConfigList(QueryYieldConfigParams param)throws OwnerException {
        return projectYieldConfigService.queryPYieldConfigList(param,getUserInfo().getCompanyId());
    }


    @SystemLog(description = "导出研发试制计划", mode = SystemLog.SAVE_DB)
    @GetMapping("/exportYield")
    @PermissionRequired(perms = "project:trialProdPlan:export")
    @ApiOperation(value = "导出研发试制计划", notes = "导出研发试制计划")
    public void exportPlan(QueryYieldConfigParams param) throws IOException, OwnerException {
        UserInfo info = getUserInfo();
        try (OutputStream out = outGeneralFile(MessageFormat.format("{0,number,#}年{1}研发试制计划表.xlsx", param.getYear(), info.getCompanyName()))) {
            projectYieldConfigService.exportPlan(param,info,out);
            out.flush();
        } catch (Exception e) {
            throw new OwnerException("导出失败");
        }

    }


    @SystemLog(description = "设备工时刷新研发试制计划", mode = SystemLog.SAVE_DB)
    @PostMapping("/refreshYieldConfig")
//    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "设备工时刷新研发试制计划", notes = "设备工时刷新研发试制计划")
    public Boolean refreshYieldConfig(@RequestBody @Validated RefreshYieldConfigModel refresh) throws OwnerException {
        return projectYieldConfigService.refreshYieldConfig(refresh,getUserInfo());
    }
    @GetMapping("/getTrialConfig")
    @ApiOperation(value = "获取刷新研发试制计划配置", notes = "获取刷新研发试制计划配置")
    public RefreshYieldConfigModel getTrialConfig() throws OwnerException {
        return projectYieldConfigService.getTrialConfig(getUserInfo().getCompanyId());
    }

    @GetMapping("/getDate")
    @ApiOperation(value = "试验试制通知单引入日期", notes = "试验试制通知单引入日期")
    public Map<String,List<String>> getDate(QueryYieldConfigModel model) throws OwnerException {
        return projectYieldConfigService.getDate(model.getProjectId(),model.getMonth(),getUserInfo().getCompanyId());
    }

    @GetMapping("/calculateDate")
    @ApiOperation(value = "试验试制通知单引入试制时间和发出时间", notes = "试验试制通知单引入试制时间和发出时间")
    public Map<String,String> calculateDate(QueryYieldConfigModel model) throws OwnerException {
        return projectYieldConfigService.calculateDate(model);
    }

    @SystemLog(description = "修改研发试制计划显示状态", mode = SystemLog.SAVE_DB)
    @PostMapping("/editSelected")
    @ApiOperation(value = "修改研发试制计划显示状态", notes = "修改研发试制计划显示状态")
    public Boolean editSelected(@RequestBody @Validated UpdateSelectedModel model) throws OwnerException {
        return projectYieldConfigService.editSelected(model,getUserInfo());
    }

    @SystemLog(description = "研发试制归集费用",mode = SystemLog.SAVE_DB)
    @PostMapping("/aggFee")
    @PermissionRequired(perms = "project:trialProdPlan:agg")
    @ApiOperation(value = "研发试制归集费用",notes = "研发试制归集费用")
    public List<AggMsgModel> aggFee(@RequestBody @Validated TrialAggModel aggModel)throws OwnerException{
        return projectYieldConfigService.aggFee(aggModel,getUserInfo());
    }
}
