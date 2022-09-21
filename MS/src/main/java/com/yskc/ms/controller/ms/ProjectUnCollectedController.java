package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.newexpert.talentrequirement.QueryTalentRequirementModel;
import com.yskc.ms.models.newexpert.talentrequirement.TalentRequirementModel;
import com.yskc.ms.models.params.PageParams;
import com.yskc.ms.models.projectuncollected.ProjectUnCollectedModel;
import com.yskc.ms.service.es.TalentRequirementService;
import com.yskc.ms.service.ms.ProjectUnCollectedService;
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
 * @description: 未归集项目类接口
 * @author: cyj
 * @create: 2021-12-11 11:52
 **/
@Api(tags = "未归集项目类接口", value = "未归集项目类接口")
@RestController
@RequestMapping("/api/projectUnCollected")
public class ProjectUnCollectedController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProjectUnCollectedService projectUnCollectedService;

    @GetMapping("/getList")
    @ApiOperation(value = "获取未归集项目列表", notes = "获取未归集项目列表")
    @PermissionRequired(perms = "ms:projectUncollected:search")
    public PageModel<List<ProjectUnCollectedModel>> getList(PageParams query) {
        return projectUnCollectedService.getList(query);
    }
}
