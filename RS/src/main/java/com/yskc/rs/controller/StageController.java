package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.StageEntity;
import com.yskc.rs.entity.company.CompanyStageEntity;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.company.CompanyStageModel;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.models.excel.StageExcel;
import com.yskc.rs.models.stage.AddStageModel;
import com.yskc.rs.models.stage.QueryStageModel;
import com.yskc.rs.models.stage.StageModel;
import com.yskc.rs.service.StageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.*;

/**
 * 数据录入物料类接口
 *
 * @author huronghua
 */
@Api(tags = "项目阶段类接口", value = "项目阶段类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/stage")
public class StageController extends BaseController {
    @Autowired
    private StageService stageService;

    @Autowired
    private RsConfig rsConfig;


    /**
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryStage")
    @PermissionRequired(perms = "project:report:stage:search")
    @ApiOperation(value = "查询阶段", notes = "查询阶段", response = String.class)
    public Map<String, List<StageModel>> queryStage(QueryStageModel query)
            throws OwnerException {
        UserInfo userInfo = getUserInfo();
        Map<String, List<StageModel>> map = new HashMap<>();
        map.put("data", stageService.queryStage(userInfo.getCompanyId(), query));
        return map;
    }

    /**
     * 查询c_stage获取stageName
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getCStage")
    @PermissionRequired(perms = "project:report:stage:search")
    @ApiOperation(value = "查询公司阶段", notes = "查询公司阶段", response = String.class)
    public List<Map<String,Object>> getCStage()
            throws OwnerException {
        UserInfo userInfo = getUserInfo();
        List<Map<String,Object>> list =stageService.getCStage(userInfo.getCompanyId());
        return list;
    }
    /**
     * 查询项目立项阶段
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getStage")
    @PermissionRequired(perms = "project:report:stage:rname")
    @ApiOperation(value = "查询项目立项阶段", notes = "查询项目立项阶段", response = String.class)
    public Map<String,List<CompanyStageEntity>> getStage()
            throws OwnerException {
        UserInfo userInfo = getUserInfo();
        List<CompanyStageEntity> list = stageService.getStage(userInfo.getCompanyId());
        List<CompanyStageEntity> defaultStages = new ArrayList<>();
        List<CompanyStageEntity> companyStages = new ArrayList<>();
        Map<String,List<CompanyStageEntity>> map = new HashMap<>();
        for (CompanyStageEntity entity : list) {
            if (entity.getCompanyId()==0){
                defaultStages.add(entity);
            }else {
                companyStages.add(entity);
            }
        }
        map.put("defaultStageList",defaultStages);
        map.put("companyStageList",companyStages);
        return map;
    }

    /**
     * 修改/添加 项目立项阶段
     * @param model
     * @return
     * @throws OwnerException
     */
    @PostMapping("/saveStage")
    @PermissionRequired(perms = "project:report:stage:rname")
    @ApiOperation(value = "修改/添加 项目立项阶段", notes = "修改/添加 项目立项阶段", response = String.class)
    public boolean saveStage(@RequestBody @Validated CompanyStageModel model)
            throws OwnerException {
        UserInfo userInfo = getUserInfo();
        Boolean right = stageService.saveStage(userInfo.getCompanyId(), model.getProjectId(), model.getList(), userInfo,model.getChangeCStage());
        return right;
    }


    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "添加项目阶段", mode = SystemLog.SAVE_DB)
    @PostMapping("/add")
    @PermissionRequired(perms = "project:report:stage:add")
    @ApiOperation(value = "添加项目阶段", notes = "添加项目阶段", response = boolean.class)
    public boolean addStage(@RequestBody @Validated StageModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return stageService.addStage(userInfo, model);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "编辑项目阶段", mode = SystemLog.SAVE_DB)
    @PostMapping("/edit")
    @PermissionRequired(perms = "project:report:stage:edit")
    @ApiOperation(value = "编辑项目阶段", notes = "编辑项目阶段", response = boolean.class)
    public boolean editStage(@RequestBody @Validated StageEntity model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return stageService.editStage(userInfo, model);
    }

    /**
     * @param stageList
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "批量编辑项目阶段", mode = SystemLog.SAVE_DB)
    @PostMapping("/editList")
    @PermissionRequired(perms = "project:report:stage:edit")
    @ApiOperation(value = "批量编辑项目阶段", notes = "批量编辑项目阶段", response = boolean.class)
    public boolean editList(@RequestBody @Validated List<AddStageModel> stageList) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        if(CollectionUtils.isEmpty(stageList)){return true;}
        List<StageEntity> stages=new ArrayList<>();
        for (AddStageModel model:stageList){
            if(model.getBeginDate().compareTo(model.getEndDate())>1){
                throw new OwnerException("阶段开始日期不能大于结束时间");
            }
            StageEntity entity=new StageEntity();
            BeanUtils.copyProperties(model,entity);
            if(!StringUtils.isEmpty(model.getStageType())){
                entity.setStageName(model.getStageType());
            }
            stages.add(entity);
        }
        return stageService.editList(userInfo, stages,stageList.get(0).getAutoAdd(),stageList.get(0).getType());
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "删除项目阶段", mode = SystemLog.SAVE_DB)
    @PostMapping("/del")
    @PermissionRequired(perms = "project:report:stage:del")
    @ApiOperation(value = "删除项目阶段", notes = "删除项目阶段", response = boolean.class)
    public boolean delStage(@RequestBody @Validated StageEntity model) throws OwnerException {
        return stageService.delStage(model,getUserInfo());
    }


    /**
     * 导入项目阶段
     *
     * @param file
     * @param tableField
     * @param projectId
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "导入项目阶段", mode = SystemLog.SAVE_DB)
    @PostMapping("/importStage")
    @ApiOperation(value = "导入项目阶段", notes = "导入项目阶段", response = String.class)
    public String importStage(@RequestParam("file") MultipartFile file, String projectId, TableField tableField) throws OwnerException {
        UserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                info.getCompanyId());
        ExcelResult<StageExcel> excelResult = excelService.getExcelResult(tempPath, file, StageExcel.class, tableField);
        stageService.importStage(info, excelResult.getData(), Integer.parseInt(projectId));
        return excelResult.getMsg();
    }
}
