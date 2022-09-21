package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.entity.project.AchievementFileEntity;
import com.yskc.rs.models.BatchDeleteModel;
import com.yskc.rs.models.IdSwitchModel;
import com.yskc.rs.models.achievement.AchievementFileModel;
import com.yskc.rs.models.achievement.AchievementModel;
import com.yskc.rs.models.achievement.QueryAchievementModel;
import com.yskc.rs.service.AchievementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: zhangdingfu
 * @CreateTime: 2021-11-18 10:27
 * @Description: 成果接口
 */
@Api(value = "成果接口", tags = "成果接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/achievement")
public class AchievementController extends BaseController {

    @Autowired
    private AchievementService achievementService;

    @GetMapping("/getList")
    @PermissionRequired(perms = "project:result:view")
    @ApiOperation(value = "获取研发成果", notes = "获取研发成果")
    public PageModel<List<AchievementModel>> getList(QueryAchievementModel query) throws OwnerException {
        return achievementService.getList(query, getUserInfo().getCompanyId());
    }

    @SystemLog(description = "添加研发成果", mode = SystemLog.SAVE_DB)
    @PostMapping("/add")
    @PermissionRequired(perms = "project:result:add")
    @ApiOperation(value = "添加研发成果", notes = "获取研发成果")
    public boolean add(@RequestBody @Validated AchievementModel model) throws OwnerException {
        return achievementService.add(model, getUserInfo());
    }

    @SystemLog(description = "编辑研发成果", mode = SystemLog.SAVE_DB)
    @PostMapping("/edit")
    @PermissionRequired(perms = "project:result:edit")
    @ApiOperation(value = "编辑研发成果", notes = "编辑研发成果")
    public boolean edit(@RequestBody @Validated AchievementModel model) throws OwnerException {
        return achievementService.edit(model, getUserInfo());
    }

    @SystemLog(description = "删除研发成果", mode = SystemLog.SAVE_DB)
    @PostMapping("/del")
    @PermissionRequired(perms = "project:result:del")
    @ApiOperation(value = "删除研发成果", notes = "删除研发成果")
    public boolean del(@RequestBody BatchDeleteModel delete) throws OwnerException {
        return achievementService.del(delete.getIds(),getUserInfo());
    }

    @SystemLog(description = "添加成果文件", mode = SystemLog.SAVE_DB)
    @PostMapping("/addFile")
    @PermissionRequired(perms = "project:result:upload")
    @ApiOperation(value = "添加成果文件", notes = "添加成果文件")
    public AchievementFileModel addFile(@RequestParam("file") MultipartFile file, AchievementFileModel achievementFile) throws OwnerException {
        return achievementService.addFile(getUserInfo(), achievementFile, file);
    }

    @SystemLog(description = "删除成果文件", mode = SystemLog.SAVE_DB)
    @PostMapping("/deleteFile")
    @PermissionRequired(perms = "project:result:del")
    @ApiOperation(value = "删除成果文件", notes = "删除成果文件")
    public boolean deleteFile(@RequestBody AchievementFileEntity achievementFile)throws OwnerException {
        return achievementService.deleteFile(achievementFile.getAchievementId(),achievementFile.getId(),getUserInfo());
    }

    @SystemLog(description = "编辑成果文件", mode = SystemLog.SAVE_DB)
    @PostMapping("/updateFile")
    @ApiOperation(value = "编辑成果文件", notes = "编辑成果文件")
    public boolean updateFile(MultipartFile file, AchievementFileModel achievementFile) throws OwnerException {
        return achievementService.updateFile(getUserInfo(), achievementFile, file);
    }

    @SystemLog(description = "编辑成果文件排序", mode = SystemLog.SAVE_DB)
    @PostMapping("/updateSeq")
    @ApiOperation(value = "编辑成果文件排序", notes = "编辑成果文件排序")
    public boolean updateSql(@RequestBody IdSwitchModel model)throws OwnerException {
        return achievementService.updateSeq(model.getId1(),model.getId2());
    }

    @GetMapping("/getFiles")
    @ApiOperation(value = "获取成果文件", notes = "获取成果文件")
    public List<AchievementFileModel> getFiles(Integer achievementId) {
        return achievementService.getFiles(achievementId);
    }

}
