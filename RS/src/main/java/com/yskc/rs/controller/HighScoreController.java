package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.entity.HighTechScoreEntity;
import com.yskc.rs.entity.hightech.HighFinanceScoreEntity;
import com.yskc.rs.models.highscore.HighFinanceScoreModel;
import com.yskc.rs.models.highscore.HighTechScoreModel;
import com.yskc.rs.models.highscore.HighTotalScoreModel;
import com.yskc.rs.service.HighScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: zhangdingfu
 * @CreateTime: 2021-10-25 08:20
 * @Description: 高新评分接口
 */

@Api(tags = "高新评分评价接口", value = "高新评分评价接口")
@RestController
@RequestMapping("/api/highScore")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
public class HighScoreController extends BaseController {

    @Autowired
    private HighScoreService highScoreService;


    @GetMapping("/getTechScore")
    @ApiOperation(notes = "获取高新评分评价", value = "获取高新评分评价")
    @PermissionRequired(perms = "project:highTechScore:view")
    public HighTechScoreModel getTechScore(Integer year) throws OwnerException {
        return highScoreService.getTechScore(year, getUserInfo().getCompanyId());
    }

    @PostMapping("/saveTechScore")
    @SystemLog(description = "保存高新评分评价", mode = SystemLog.SAVE_DB)
    @ApiOperation(notes = "保存高新评分评价", value = "保存高新评分评价")
    @PermissionRequired(perms = "project:highTechScore:save")
    public Boolean saveTechScore(@RequestBody HighTechScoreEntity highTechScore) throws OwnerException {
        return highScoreService.saveTechScore(highTechScore, getUserInfo());
    }

    @GetMapping("/getFinanceScore")
    @ApiOperation(notes = "获取财务评分评价", value = "获取财务评分评价")
    @PermissionRequired(perms = "project:highFinanceScore:view")
    public HighFinanceScoreModel getFinanceScore(Integer year) throws OwnerException {
        return highScoreService.getFinanceScore(year, getUserInfo().getCompanyId());
    }

    @PostMapping("/saveFinanceScore")
    @SystemLog(description = "保存财务评分评价", mode = SystemLog.SAVE_DB)
    @ApiOperation(notes = "保存财务评分评价", value = "保存财务评分评价")
    @PermissionRequired(perms = "project:highFinanceScore:save")
    public Boolean saveFinanceScore(@RequestBody HighFinanceScoreEntity financeScore) throws OwnerException {
        return highScoreService.saveFinanceScore(financeScore, getUserInfo());
    }

    @GetMapping("/getTotalScore")
    @ApiOperation(value = "获取综合得分", notes = "获取综合得分")
    @PermissionRequired(perms = "project:highTotalScore:view")
    public HighTotalScoreModel getTotalScore(Integer year) throws OwnerException {
        return highScoreService.getTotalScore(year, getUserInfo().getCompanyId());
    }
}

