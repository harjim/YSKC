package com.yskc.rs.controller;

import cn.hutool.core.date.DateUtil;
import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.entity.project.ProjectYearFeeEntity;
import com.yskc.rs.service.ProjectYearFeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: zhangdingfu
 * @CreateTime: 2022-05-07 08:41
 * @Description: 优惠明细表年费用接口
 */
@Api(value = "优惠明细表年费用", tags = "优惠明细表年费用")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RequestMapping("/api/projectYearFee")
@RestController
public class ProjectYearFeeController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ProjectYearFeeService projectYearFeeService;

    /**
     * @param year
     * @throws IOException
     * @throws OwnerException
     */
    @SystemLog(description = "导出明细表", mode = SystemLog.SAVE_DB)
    @GetMapping("/exportDetailData")
    @PermissionRequired(perms = "project:summaryReport:exportDetail,project:rdFeeDetail:export")
    @ApiOperation(value = "导出明细表", notes = "导出明细表")
    public void exportDetailData(Integer year) throws IOException, OwnerException {
        try (OutputStream out = outGeneralFile(MessageFormat.format("{0}明细表{1}.xls", year, DateUtil.format(new Date(), "yyyy-MM-dd-HH:mm:ss")))) {
            projectYearFeeService.exportDetailData(year, getUserInfo(), out);
            out.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new OwnerException("导出失败");
        }
    }

    @GetMapping("/getDetailData")
    @PermissionRequired(perms = "project:rdFeeDetail:view")
    @ApiOperation(value = "获取明细表数据", notes = "获取明细表数据")
    public Map<String, Object> getDetailData(Integer year) throws OwnerException {
        return projectYearFeeService.getDetailData(year, getUserInfo(), null, null);
    }

    @PostMapping("/save")
    @PermissionRequired(perms = "project:rdFeeDetail:save")
    @ApiOperation(value = "保存明细表数据", notes = "保存明细表数据")
    public Boolean save(@RequestBody ProjectYearFeeEntity entity) throws OwnerException {
        return projectYearFeeService.save(entity, getUserInfo());
    }
}
