package com.yskc.ms.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.config.Constant;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.entity.rs.RsPatentCostEntity;
import com.yskc.ms.models.patent.PatentCostModel;
import com.yskc.ms.models.patent.QueryPatentCostModel;
import com.yskc.ms.service.rs.RsPatentCostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.OutputStream;
import java.util.List;

/**
 * Created by hck
 * on 2020/7/2 11:08
 * description:
 */
@Api(tags = "专利费用类接口", value = "专利费用类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/rsPatentCost")
public class RsPatentCostController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RsPatentCostService rsPatentCostService;
    @Autowired
    private MsConfig msConfig;

    @GetMapping("/queryPatentCostList")
    @PermissionRequired(perms = "patent:PatentFeeList:search")
    @ApiOperation(value = "获取所有专利费用", notes = "获取所有专利费用")
    public PageModel<List<PatentCostModel>> queryPatentCostList(QueryPatentCostModel query) throws OwnerException {
        return rsPatentCostService.queryPatentCost(query, getDataPerm());
    }


    @PostMapping("/updateRemark")
    @PermissionRequired(perms = "patent:PatentFeeList:setRemark")
    @ApiOperation(value = "批量设置备注", notes = "批量设置备注", response = boolean.class)
    public boolean updateRemark(@RequestBody @Validated PatentCostModel model) throws OwnerException {
        return rsPatentCostService.updateRemark(getUserInfo(), model);
    }

    @PostMapping("/updateRemindDateTime")
    @PermissionRequired(perms = "patent:PatentFeeList:setReminderdate")
    @ApiOperation(value = "编辑提醒日期", notes = "编辑提醒日期", response = boolean.class)
    public boolean updateRemindDateTime(@RequestBody @Validated PatentCostModel model) throws OwnerException {
        return rsPatentCostService.updateRemindDateTime(model,getUserInfo());
    }

    @PostMapping("/delPatentCost")
    @PermissionRequired(perms = "patent:PatentFeeList:del")
    @ApiOperation(value = "删除", notes = "删除", response = boolean.class)
    public boolean delPatentCost(@RequestBody @Validated PatentCostModel model) throws OwnerException {
        return rsPatentCostService.delete(model);
    }

    @PostMapping("/deletePatentCosts")
    @PermissionRequired(perms = "patent:PatentFeeList:del")
    @ApiOperation(value = "批量删除专利费用", notes = "批量删除专利费用")
    public boolean deletePatentCosts(@RequestBody @Validated List<RsPatentCostEntity> patentCosts) {
        return rsPatentCostService.deletePatentCosts(patentCosts);
    }

    @GetMapping("/getPayDataBypatentNo")
    @ApiOperation(value = "获取专利费用", notes = "获取专利费用")
    public List<PatentCostModel> getPayDataBypatentNo(String patentNo) throws OwnerException {
        return rsPatentCostService.getPayDataBypatentNo(patentNo);
    }

    @GetMapping("/export")
    @ApiOperation(value = "导出专利费用列表", notes = "导出专利费用列表")
    @PermissionRequired(perms = "patent:PatentFeeList:export")
    @SystemLog(description = "导出专利费用列表")
    public void export(QueryPatentCostModel query) throws OwnerException {
        String fileName = "专利费用列表.xls";
        String templateName = "专利费用列表模板.xls";
        String path = msConfig.getUploadConfig().getResourcePath() + Constant.TEMPLATE_DIR + templateName;
        try (OutputStream out = outGeneralFile(fileName)) {
            rsPatentCostService.export(query, this.getUserInfo(), this.getDataPerm(), out, path);
            out.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new OwnerException("导出失败");
        }
    }
}
