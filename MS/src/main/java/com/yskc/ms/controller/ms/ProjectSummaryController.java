package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.projectsummary.ProjectSummaryModel;
import com.yskc.ms.models.projectsummary.QuerySummaryModel;
import com.yskc.ms.service.ms.SummaryDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by hck
 * on 2020/11/10 11:51
 * description:高新项目信息接口
 */
@Api(tags = "高新项目信息接口", value = "高新项目信息接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/projectSummary")
public class ProjectSummaryController extends BaseController{

    @Autowired
    private SummaryDataService summaryDataService;

    @GetMapping("/getData")
    @PermissionRequired(perms = "project:highTechProject:search")
    @ApiOperation(value = "查询高新项目数据", notes = "查询高新项目数据", response = List.class)
    public PageModel<List<ProjectSummaryModel>> getData(QuerySummaryModel model) throws OwnerException {
        return summaryDataService.getList(model, getDataPerm());
    }
}
