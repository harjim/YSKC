package com.yskc.ms.controller.es;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.models.newexpert.achievement.AchievementLogModel;
import com.yskc.ms.models.newexpert.achievement.AchievementModel;
import com.yskc.ms.models.newexpert.achievement.QueryAchievementModel;
import com.yskc.ms.service.es.EsAchievementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @DateTime: 2021/11/13 10:50
 * @Description:
 * @author: hsx
 */
@RestController
@RequestMapping("/api/esAchievement")
@Api(tags = "ES科技成果管理接口", value = "ES科技成果管理接口")
public class EsAchievementController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EsAchievementService esAchievementService;

    @GetMapping("/getList")
    @ApiOperation(notes = "成果列表查询", value = "成果列表查询")
    @PermissionRequired(perms = "es:achievement:search")
    public PageModel<List<AchievementModel>> getList(QueryAchievementModel model){
        return esAchievementService.getList(model);
    }

    @GetMapping("/getData")
    @ApiOperation(notes = "获取成果数据", value = "获取成果数据")
    @PermissionRequired(perms = "es:achievement:view")
    public AchievementModel getData(@RequestParam("id") Integer id){
        return esAchievementService.getData(id);
    }

    @PostMapping("/audit")
    @ApiOperation(notes = "成果审核和撤回成果", value = "成果审核和撤回成果")
    @SystemLog(description = "成果审核和撤回成果",mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "es:achievement:audit,es:achievement:recall")
    public Boolean audit(@RequestBody AchievementLogModel model)throws OwnerException {
        model.setMsCreatorId(getUserInfo().getId());
        return esAchievementService.audit(model);
    }

    @GetMapping("/getLogs")
    @ApiOperation(notes = "获取成果审核日志", value = "获取成果审核日志")
    public List<AchievementLogModel> getLogs(@RequestParam("id") Integer id){
        return esAchievementService.getLogs(id);
    }

    @GetMapping("/getCount")
    @PermissionRequired(perms = "es:achievement:search")
    @ApiOperation(notes = "获取成果状态统计", value = "获取成果状态统计")
    public Map<String, Integer> getCount(){
        return esAchievementService.getCount();
    }
}
