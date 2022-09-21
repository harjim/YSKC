package com.yskc.ms.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.exception.OwnerException;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.models.QueryStageModel;
import com.yskc.ms.models.SysDictionaryModel;
import com.yskc.ms.models.product.StageModel;
import com.yskc.ms.models.rs.RsStageModel;
import com.yskc.ms.service.rs.StageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @DateTime: 2021/9/15 14:25
 * @Description:
 * @author: hsx
 */

@Api(tags = "项目阶段类接口", value = "项目阶段类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/RsStage")
public class RsStageController extends BaseController {

    @Autowired
    private StageService stageService;

    /**
     * @return
     * @throws OwnerException
     */
    @GetMapping("/searchStage")
    @ApiOperation(value = "查询阶段", notes = "查询阶段", response = String.class)
    public List<SysDictionaryModel> searchStage() throws OwnerException {
        return stageService.searchStage();
    }

    /**
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryStage")
    @ApiOperation(value = "查询项目阶段", notes = "查询项目阶段", response = String.class)
    public List<RsStageModel> queryStage(QueryStageModel query) throws OwnerException {
        return stageService.queryStage(query);
    }
}
