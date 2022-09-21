package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.enums.FileTypeEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.entity.tech.TechProjectBasicEntity;
import com.yskc.rs.entity.tech.TechProjectEntity;
import com.yskc.rs.models.tech.DeclarationInfo;
import com.yskc.rs.models.tech.ProductStageModel;
import com.yskc.rs.models.tech.QueryTechProjectModel;
import com.yskc.rs.models.tech.TechProjectModel;
import com.yskc.rs.service.TechProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: zhangdingfu
 * @CreateTime: 2019-09-23 11:10
 * @Description: 技改项目control层
 */
@RestController
@Api(tags = "技改项目接口", value = "技改项目接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RequestMapping("/api/techProject")
public class TechProjectController extends BaseController {

    @Autowired
    private TechProjectService techProjectService;


    /**
     * 获取技改项目
     *
     * @param query
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getTechProjects")
    @PermissionRequired(perms = "tech:pro:search")
    @ApiOperation(value = "获取技改项目", notes = "获取技改项目")
    public PageModel<List<TechProjectModel>> getTechProjects(QueryTechProjectModel query) throws OwnerException {
        return techProjectService.getTechProjects(getUserInfo().getCompanyId(), query);
    }

    /**
     * 添加技改项目
     *
     * @param techProjectModel
     * @return
     * @throws OwnerException
     */
    @PostMapping("/add")
    @SystemLog(description = "添加技改项目",mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "tech:pro:add")
    @ApiOperation(value = "添加技改项目", notes = "添加技改项目")
    public boolean add(@RequestBody @Validated TechProjectModel techProjectModel) throws OwnerException {
        return techProjectService.add(getUserInfo(), techProjectModel);
    }

    /**
     * 更新技改项目
     *
     * @param techProjectModel
     * @return
     * @throws OwnerException
     */
    @PostMapping("/update")
    @SystemLog(description = "更新技改项目",mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "tech:pro:edit")
    @ApiOperation(value = "更新技改项目", notes = "更新技改项目")
    public boolean update(@RequestBody @Validated TechProjectModel techProjectModel) throws OwnerException {
        return techProjectService.update(getUserInfo(), techProjectModel);
    }

    /**
     *
     * @return
     */
    @PostMapping("/del")
    @SystemLog(description = "删除技改项目",mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "tech:pro:del")
    @ApiOperation(value = "删除技改项目", notes = "删除技改项目")
    public boolean del(@RequestBody @Validated TechProjectEntity entity) {
        return techProjectService.del(entity.getId());
    }

    /**
     *
     * @return
     */
    @PostMapping("/dels")
    @SystemLog(description = "批量删除技改项目",mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "tech:pro:del")
    @ApiOperation(value = "批量删除技改项目", notes = "批量删除技改项目")
    public boolean dels(@RequestBody @Validated List<TechProjectEntity> techProjectEntityList) {
        return techProjectService.dels(techProjectEntityList);
    }

    /**
     * 获取公司申报列表
     *
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getYears")
    @ApiOperation(value = "获取公司申报列表", notes = "获取公司申报列表")
    public List<Integer> getYears() throws OwnerException {
        return techProjectService.getYears(getUserInfo().getCompanyId());
    }

    /**
     * 获取公司技改项目
     *
     * @param year
     * @return
     */
    @GetMapping("/getTechProjectsByYear")
    @ApiOperation(value = "获取公司技改项目", notes = "获取公司技改项目")
    public List<TechProjectEntity> getTechProjectsByYear(Integer year) throws OwnerException {
        return techProjectService.getTechProjectsByYear(getUserInfo().getCompanyId(), year);
    }

    /**
     * 获取公司技改项目基本信息
     *
     * @param projectId
     * @return
     */
    @GetMapping("/getTechProjectBasic")
    @ApiOperation(value = "获取公司技改项目基本信息", notes = "获取公司技改项目基本信息")
    public TechProjectBasicEntity getTechProjectBasic(Integer projectId) throws OwnerException {
        return techProjectService.getTechProjectBasic(getUserInfo().getCompanyId(), projectId);
    }

    /**
     * 保存技改项目基本信息
     *
     * @param techProjectBasicEntity
     * @return
     */
    @PostMapping("/saveProjectBasic")
    @SystemLog(description = "保存技改项目基本信息",mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "保存技改项目基本信息", notes = "保存技改项目基本信息")
    public boolean saveProjectBasic(@Validated @RequestBody TechProjectBasicEntity techProjectBasicEntity) throws OwnerException {
        return techProjectService.saveProjectBasic(getUserInfo(), techProjectBasicEntity);
    }

    /**
     * 根据标题获取技改申请报告
     *
     * @param projectId
     * @param key
     * @return
     */
    @GetMapping("/getDeclarationList")
    @ApiOperation(value = "根据标题获取技改申请报告", notes = "根据标题获取技改申请报告")
    public List<DeclarationInfo> getDeclarationList(Integer projectId, String key) throws OwnerException {
        return techProjectService.getDeclarationList(projectId, key, getUserInfo());
    }

    /**
     * 保存技改项目申请报告
     *
     * @param declarationInfos
     * @return
     * @throws OwnerException
     */
    @PostMapping("/saveDeclarationInfoList")
    @SystemLog(description = "保存技改项目申请报告",mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "保存技改项目申请报告", notes = "保存技改项目申请报告")
    public boolean saveDeclarationInfoList(@Validated @RequestBody List<DeclarationInfo> declarationInfos) throws OwnerException {
        return techProjectService.saveDeclarationInfoList(getUserInfo(), declarationInfos);
    }

    /**
     *
     * @param projectId
     * @throws Exception
     */
    @GetMapping("/exportDeclarationInfo")
    @ApiOperation(value = "导出技改项目申请报告", notes = "导出技改项目申请报告")
    public void exportDeclarationInfo(Integer projectId) throws Exception {
        OutputStream out = outGeneralFile("技改项目申请报告.doc");
        techProjectService.exportDeclarationInfo(getUserInfo(), projectId, out);
    }

    @GetMapping("/getFileTypes")
    @SystemLog(description = "获取文件类型")
    @ApiOperation(value = "获取文件类型", notes = "获取文件类型")
    public List<Map<String, String>> getFileTypes() throws OwnerException {
        return FileTypeEnum.getFileTypes();
    }


}
