package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.ReportEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.project.PlanInfo;
import com.yskc.rs.service.ReportService;
import com.yskc.rs.utils.YsWordUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;

/**
 * 申报类接口
 *
 * @author huronghua
 */
@Api(tags = "申报类接口", value = "申报类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/report")
public class ReportController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ReportService reportService;
    @Autowired
    private RsConfig rsConfig;

    @SystemLog(description = "规划项目", mode = SystemLog.SAVE_DB)
    @PostMapping("/setPlanInfo")
    @PermissionRequired(perms = "project:report:list:number")
    public Boolean setPlanInfo(@RequestBody @Validated ReportEntity entity) throws OwnerException {
        return reportService.setCnt(entity, getUserInfo());
    }

    /**
     * @param ryear
     * @return
     */
    @GetMapping("/getPlanInfo")
    @PermissionRequired(perms = "project:report:list:search")
    @ApiOperation(value = "获取项目数", notes = "获取项目数")
    public PlanInfo getPlanInfo(Integer ryear) throws OwnerException {
        return reportService.getPlanInfo(getUserInfo().getCompanyId(), ryear);
    }


    @SystemLog(description = "保存技术中心简介", mode = SystemLog.SAVE_DB)
    @PostMapping("/saveTechIntro")
    @ApiOperation(value = "保存技术中心简介", notes = "保存技术中心简介")
    public Boolean saveTechIntro(@RequestBody @Validated ReportEntity report) throws OwnerException {
        return reportService.saveTechIntro(report, getUserInfo());
    }

    @GetMapping("/exportData")
    @PermissionRequired(perms = "rdorg:arch:techDesc:export")
    @ApiOperation(value = "导出项目中心简介", notes = "导出项目中心简介")
    public void exportData(Integer ryear) throws Exception {
        UserInfo info = getUserInfo();
        String techIntro = reportService.getTechIntro(info.getCompanyId(), ryear);
        HashMap<String, Object> data = new HashMap<>(1);
        data.put("intro",techIntro);
        data.put("companyName",info.getCompanyName());
        OutputStream out = outGeneralFile(MessageFormat.format("{0}-{1,number,#}{2}.docx",
                info.getCompanyName(), ryear,"项目中心简介"));
        YsWordUtils.generalWordReport(data,
                rsConfig.getUploadConfig().getResourcePath() + Constant.INTRO_TEMPLATE_DIR + "项目中心简介" + ".docx",
                doc -> {
                    if (null != doc) {
                        try {
                            doc.write(out);
                            out.flush();
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                }
        );
    }


    /**
     * 获取年技术中心简介
     *
     * @param year
     * @return
     */
    @GetMapping("/getTechIntro")
    @PermissionRequired(perms = "project:report:list:search")
    @ApiOperation(value = "获取项目数", notes = "获取项目数")
    public String getTechIntro(Integer year) throws OwnerException {
        return reportService.getTechIntro(getUserInfo().getCompanyId(), year);
    }

    @GetMapping("/getDeptIds")
    @ApiOperation(value = "获取项目数", notes = "获取项目数")
    public List<String> getDeptIds(Integer year) throws OwnerException {
        return reportService.getDeptIds(getUserInfo().getCompanyId(), year);
    }

    @SystemLog(description = "设置人员总数", mode = SystemLog.SAVE_DB)
    @PostMapping("/saveEmployeeAmount")
    @ApiOperation(value = "设置人员总数", notes = "设置人员总数")
    @PermissionRequired(perms = "rdorg:rdEmployee:setAmount")
    public Boolean saveEmployeeAmount(@RequestBody @Validated ReportEntity report) throws OwnerException {
        return reportService.saveEmployeeAmount(report, getUserInfo());
    }
}
