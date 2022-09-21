package com.yskc.ms.controller.es;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.models.newexpert.cooperation.CooperationModel;
import com.yskc.ms.models.newexpert.cooperation.QueryCooperationModel;
import com.yskc.ms.models.newexpert.cooperation.CooperationInfoModel;
import com.yskc.ms.service.es.CooperationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @DateTime: 2021/11/12 14:41
 * @Description:
 * @author: hsx
 */
@Api(tags = "ES意向管理接口", value = "ES意向管理接口")
@RestController
@RequestMapping("/api/cooperation")
public class CooperationController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CooperationService cooperationService;

    @GetMapping("/getList")
    @ApiOperation(notes = "成果和技术意向列表查询", value = "成果和技术意向列表查询")
    @PermissionRequired(perms = "es:achievement:interests,es:tech:interests")
    public PageModel<List<CooperationInfoModel>> getList(QueryCooperationModel model){
        return cooperationService.getList(model);
    }

    @GetMapping("/getExpertList")
    @ApiOperation(notes = "专家意向列表查询", value = "专家意向列表查询")
    @PermissionRequired(perms = "es:cooperation:search")
    public PageModel<List<CooperationModel>> getExpertList(QueryCooperationModel model){
        return cooperationService.getExpertList(model);
    }


    @PostMapping("/update")
    @ApiOperation(notes = "更新合作意向", value = "更新合作意向")
    @PermissionRequired(perms = "es:cooperation:update")
    @SystemLog(description = "更新合作意向", mode = SystemLog.SAVE_DB)
    public Boolean update(@RequestBody CooperationModel model) throws OwnerException {
        return cooperationService.update(model,getUserInfo().getId());
    }

    @GetMapping("/getData")
    @ApiOperation(notes = "专家详情查询", value = "专家详情查询")
    @PermissionRequired(perms = "es:cooperation:search")
    public CooperationModel getData(@RequestParam("id") Integer id){
        return cooperationService.getData(id);
    }

    @GetMapping("/getCooperation")
    @ApiOperation(notes = "服务中/已合作专家状态查询", value = "服务中/已合作专家状态查询")
    @PermissionRequired(perms = "es:cooperation:search")
    public Integer getCooperationStatus(@RequestParam("intentionId") Integer intentionId) { return cooperationService.getCooperationStatus(intentionId); }

}
