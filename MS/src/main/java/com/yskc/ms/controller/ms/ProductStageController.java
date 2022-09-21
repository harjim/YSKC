package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.ms.models.product.AddStageModel;
import com.yskc.ms.models.product.StageModel;
import com.yskc.ms.service.ms.ProductStageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by hck
 * on 2020/7/28 14:06
 * description:产品技改阶段
 */
@Api(tags = "产品技改阶段接口", value = "产品技改阶段接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/productStage")
public class ProductStageController extends BaseController {

    @Autowired
    private ProductStageService productStageService;

    @GetMapping("/getStageList")
    @PermissionRequired(perms = "project:tech:searchStage")
    @ApiOperation(value = "根据项目类型获取阶段", notes = "根据项目类型获取阶段")
    public List<StageModel> getStageList(@RequestParam("productId") Integer productId) throws OwnerException {
        return productStageService.getStageList(productId);
    }

    @GetMapping("/checkStageData")
    @ApiOperation(value = "查询阶段是否可以删除", notes = "查询阶段是否可以删除")
    public String checkStageData(@RequestParam("stageKeys") List<String> stageKeys, @RequestParam("productId")Integer productId) throws OwnerException {
        return productStageService.checkStageData(stageKeys,productId);
    }
    @SystemLog(description = "添加阶段", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "project:tech:setStage")
    @PostMapping("/addProductStage")
    @ApiOperation(value = "添加阶段", notes = "添加阶段", response = boolean.class)
    public Boolean addProductStage(@RequestBody @Validated AddStageModel model) throws OwnerException {
        return productStageService.addProductStage(getUserInfo().getId(), model);
    }

    @SystemLog(description = "更新阶段", mode = SystemLog.SAVE_DB)
    @PostMapping("/updateProductStage")
    @PermissionRequired(perms = "project:tech:setStage")
    @ApiOperation(value = "更新阶段", notes = "更新阶段", response = boolean.class)
    public Boolean updateProductStage(@RequestBody @Validated AddStageModel model) throws OwnerException {
        return productStageService.updateProductStage(getUserInfo().getId(), model);
    }
}
