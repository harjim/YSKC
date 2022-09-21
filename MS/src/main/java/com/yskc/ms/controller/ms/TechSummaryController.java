package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageResult;
import com.yskc.ms.models.techsummary.QueryTechSummaryModel;
import com.yskc.ms.models.techsummary.TechStageFilesModel;
import com.yskc.ms.service.ms.TechSummaryService;
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
 * @BelongsPackage: com.yskc.ms.controller.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-10-15 09:01
 * @Description: 技改汇总controller
 */
@RestController
@RequestMapping("/api/techSummary")
@Api(tags = "技改汇总接口", value = "技改汇总接口")
@CrossOrigin(origins = "*", allowedHeaders = "", allowCredentials = "true", methods = {})
public class TechSummaryController extends BaseController {

    @Autowired
    private TechSummaryService techSummaryService;

    @GetMapping("/getList")
    @PermissionRequired(perms = "tech:summary:search")
    @ApiOperation(value = "获取技改汇总列表", notes = "获取技改汇总列表")
    public PageResult getList(QueryTechSummaryModel query) throws OwnerException {
        return techSummaryService.getList(query, getDataPerm());
    }

    @GetMapping("/getStageFiles")
    @PermissionRequired(perms = "tech:summary:viewFiles")
    @ApiOperation(value = "查看技改项目资料清单", notes = "查看技改项目资料清单")
    public Map<String,  Map<String,List<TechStageFilesModel>>> getStageFiles(Integer projectId) {
        return techSummaryService.getStageFiles(projectId);
    }

}
