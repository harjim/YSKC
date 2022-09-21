package com.yskc.rs.controller;

import com.yskc.common.annotation.NotLoginRequired;
import com.yskc.rs.entity.Expert;
import com.yskc.rs.service.ExpertService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 专家接口类
 *
 * @author Administrator
 */
@Api(tags = "获取专家接口", value = "获取专家接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/expert")
public class ExpertController extends BaseController {

    @Autowired
    private ExpertService expertService;

    /**
     *
     * @param expertUuid
     * @return
     */
    @NotLoginRequired
    @GetMapping("/detail/{uuid}")
    @ApiOperation(value = "查询详情", notes = "查询详情", response = String.class)
    public Expert getExperModelByuuid(@PathVariable("uuid") String expertUuid) {
        return expertService.getExpertByUuid(expertUuid);
    }


}
