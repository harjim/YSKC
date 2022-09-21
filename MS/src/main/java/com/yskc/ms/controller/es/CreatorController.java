package com.yskc.ms.controller.es;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.newexpert.creator.CreatorModel;
import com.yskc.ms.models.newexpert.talentrequirement.QueryTalentRequirementModel;
import com.yskc.ms.models.newexpert.talentrequirement.TalentRequirementModel;
import com.yskc.ms.service.es.CreatorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: ms
 * @description: 获取创建人下拉框接口
 * @author: cyj
 * @create: 2021-12-10 09:02
 **/
@RequestMapping("/api/Creator")
@RestController
@Api(tags = "获取创建人下拉框接口层", value = "获取创建人下拉框接口层")
public class CreatorController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CreatorService creatorService;

    @GetMapping("/getCreator")
    @ApiOperation(value = "获取创建人下拉框", notes = "获取创建人下拉框")
    public List<CreatorModel> getCreator(){
        return creatorService.getCreator();
    }

}
