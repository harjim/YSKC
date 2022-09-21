package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.docFile.QueryDocFileFooterModel;
import com.yskc.rs.models.docFile.DocFileFooterModel;
import com.yskc.rs.service.DocFileFooterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.List;

/**
 * @DateTime: 2022/3/7 8:49
 * @Description:
 * @author: hsx
 */
@Api(tags = "项目签名汇总接口", value = "项目签名汇总接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/signatureSummary")
public class SignatureSummaryController extends BaseController{

    @Autowired
    private DocFileFooterService docFileFooterService;

    @GetMapping("/getList")
    @PermissionRequired(perms = "project:signatureSummary:search")
    @ApiOperation(value = "查询项目签名汇总数据", notes = "查询项目签名汇总数据", response = String.class)
    public PageModel<List<DocFileFooterModel>> getList(QueryDocFileFooterModel model) throws OwnerException {
        model.setCompanyId(getUserInfo().getCompanyId());
        return docFileFooterService.getList(model);
    }
    @PostMapping("/addDocFileFooter")
    @SystemLog(description = "添加项目签名", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "添加项目签名", notes = "添加项目签名")
    @PermissionRequired(perms = "project:signatureSummary:add")
    public Boolean addDocFileFooter(@RequestBody @Validated DocFileFooterModel model) throws OwnerException {
        return docFileFooterService.addDocFileFooter(model,getUserInfo());
    }
    @PostMapping("/editDocFileFooter")
    @SystemLog(description = "编辑项目签名", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "编辑项目签名", notes = "编辑项目签名")
    @PermissionRequired(perms = "project:signatureSummary:edit")
    public Boolean editDocFileFooter(@RequestBody @Validated DocFileFooterModel model) throws OwnerException {
        return docFileFooterService.editDocFileFooter(model,getUserInfo());
    }
    @SystemLog(description = "导出项目签名汇总", mode = SystemLog.SAVE_DB)
    @GetMapping("/export")
    @PermissionRequired(perms = "project:signatureSummary:export")
    @ApiOperation(value = "导出项目签名汇总", notes = "导出项目签名汇总")
    public void exportPlan(QueryDocFileFooterModel model) throws IOException, OwnerException {
        UserInfo info = getUserInfo();
        model.setCompanyId(getUserInfo().getCompanyId());
        String fileName= MessageFormat.format("{0}{1}年项目签名汇总表.xls", info.getCompanyName(),model.getYear());
        try (OutputStream out = outGeneralFile(fileName)) {
            docFileFooterService.exportEquipment(model,info, out);
            out.flush();
        } catch (OwnerException oe){
            throw oe;
        } catch (Exception e) {
            throw new OwnerException("导出失败");
        }

    }
}
