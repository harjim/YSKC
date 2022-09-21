package com.yskc.ms.controller.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageResult;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.models.rs.achievement.AchievementFileModel;
import com.yskc.ms.models.rs.achievement.QueryAchievementModel;
import com.yskc.ms.service.rs.RsAchievementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.controller.rs
 * @Author: zhangdingfu
 * @CreateTime: 2021-11-19 16:40
 * @Description: rs成果接口
 */
@Api(tags = "rs成果接口", value = "rs成果接口")
@RestController
@RequestMapping("/api/rsAchievement")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
public class RsAchievementController extends BaseController {

    @Autowired
    private RsAchievementService rsAchievementService;

    @GetMapping("/getList")
    @ApiOperation(notes = "获取成果列表", value = "获取成果列表")
    public PageResult getList(QueryAchievementModel query) throws OwnerException {
        return rsAchievementService.getList(query, getUserInfo());
    }

    @GetMapping("/getFiles")
    @ApiOperation(notes = "获取成果文件", value = "获取成果文件")
    public List<AchievementFileModel> getFiles(Integer achievementId) {
        return rsAchievementService.getFiles(achievementId);
    }

    @GetMapping("/getInfo")
    @ApiOperation(notes = "获取成果详细",value = "获取成果详细")
    public Map<String,Object> getInfo(Integer id){
        return rsAchievementService.getInfo(id);
    }
}
