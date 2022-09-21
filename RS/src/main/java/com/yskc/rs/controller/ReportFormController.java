package com.yskc.rs.controller;

import cn.hutool.core.date.DateUtil;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.reportform.IndexModel;
import com.yskc.rs.models.reportform.MonthReportModel;
import com.yskc.rs.service.ReportFormService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;


/**
 * @program: rs
 * @description: 报表类接口
 * @author: cyj
 * @create: 2022-01-19 16:16
 **/

@Api(tags = "报表类接口", value = "报表类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/reportForm")
public class ReportFormController extends BaseController{
    @Autowired
    private ReportFormService reportFormService;

    @GetMapping("/getIndex")
    @ApiOperation(value = "查询指控报表", notes = "查询指控报表", response = String.class)
    public IndexModel getIndex(Date month) throws OwnerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        UserInfo userInfo = getUserInfo();
        Integer year = DateUtil.year(month);
        return reportFormService.getIndex(year,month,userInfo.getCompanyId());
    }

    @GetMapping("/getMonthReport")
    @ApiOperation(value = "查询研发月报表", notes = "查询研发月报表", response = String.class)
    public List<MonthReportModel> getMonthReport(Integer year) throws OwnerException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        UserInfo userInfo = getUserInfo();
        return reportFormService.getMonthReport(year, userInfo.getCompanyId());
    }
}
