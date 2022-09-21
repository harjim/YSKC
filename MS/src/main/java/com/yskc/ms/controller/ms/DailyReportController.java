package com.yskc.ms.controller.ms;

import cn.hutool.core.bean.BeanUtil;
import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.enums.DailyReportEnum;
import com.yskc.ms.models.dailyreport.QueryDailyReportModel;
import com.yskc.ms.service.ms.DailyReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.controller.ms
 * @Author: zhangdingfu
 * @CreateTime: 2021-08-06 14:28
 * @Description: 工作报表接口
 */
@RestController
@RequestMapping("/api/dailyReport")
@CrossOrigin(origins = "*", allowedHeaders = "", allowCredentials = "true", methods = {})
@Api(tags = "工作日报表接口", value = "工作日报表接口")
public class DailyReportController extends BaseController {

    @Autowired
    private DailyReportService dailyReportService;

    @GetMapping("/getDayList")
    @ApiOperation(notes = "获取工作日报表", value = "获取工作日报表")
    @PermissionRequired(perms = "dailyReport:dayReport:search")
    public PageModel<List<Map<String, Object>>> getDayList(QueryDailyReportModel query) throws OwnerException {
        return dailyReportService.getDayList(query, getDataPerm());
    }

    @GetMapping("/getCols")
    @ApiOperation(notes = "获取工作报表列", value = "获取工作报表列")
    public List<Map<String, Object>> getCols() {
        List<DailyReportEnum> list = DailyReportEnum.getList();
        List<Map<String, Object>> result = new ArrayList<>();
        list.forEach(item->{
            result.add(BeanUtil.beanToMap(item));
        });
        return result;
    }

}
