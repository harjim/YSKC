package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.entity.ms.PatentPlanEntity;
import com.yskc.ms.models.BatchDeleteModel;
import com.yskc.ms.models.customer.CustomerSelectModel;
import com.yskc.ms.models.patentPlan.*;
import com.yskc.ms.models.projectAudit.QueryAuditDataModel;
import com.yskc.ms.models.rs.RsProjectBaseModel;
import com.yskc.ms.service.ms.PatentPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: hck
 * @DateTime: 2021/8/19 10:55
 * @Description:
 */
@Api(tags = "专利列表接口", value = "专利列表接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/patentPlan")
public class PatentPlanController extends BaseController {
    @Autowired
    private PatentPlanService patentPlanService;


    @GetMapping("/getCustomers")
    //@PermissionRequired(perms = "patent:demandList:search")
    @ApiOperation(value = "获取专利申请客户下拉列表", notes = "获取专利申请客户下拉列表")
    public List<CustomerSelectModel> getCustomers(String companyName) throws OwnerException {
        return patentPlanService.getCustomers(companyName);
    }

    @GetMapping("/getProjectSelect")
    //@PermissionRequired(perms = "patent:demandList:search")
    @ApiOperation(value = "获取专利申请项目下拉列表", notes = "获取专利申请项目下拉列表")
    public List<RsProjectBaseModel> getProjectSelect(Integer companyId, Integer year) throws OwnerException {
        return patentPlanService.getProjectSelect(companyId, year);
    }

    @GetMapping("/getList")
    @PermissionRequired(perms = "patent:patentPlan:search")
    @ApiOperation(value = "获取专利申请列表", notes = "获取专利申请列表")
    public PageModel<List<PatentPlanModel>> getList(QueryPatentPlanModel query) throws OwnerException {
        return patentPlanService.getList(query, getDataPerm());
    }

    @PostMapping("/save")
    @SystemLog(description = "保存专利申请", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "patent:patentPlan:add,patent:patentPlan:edit")
    @ApiOperation(value = "保存专利申请", notes = "保存专利申请")
    public Boolean save(@RequestBody @Validated PatentPlanModel model) throws OwnerException {
        return patentPlanService.save(model, getUserInfo());
    }

    @PostMapping("/del")
    @PermissionRequired(perms = "patent:patentPlan:delete")
    @SystemLog(description = "删除专利申请", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "删除专利申请", notes = "删除专利申请")
    public Boolean del(@RequestBody @Validated BatchDeleteModel deleteModel) throws OwnerException {
        return patentPlanService.del(deleteModel.getIds());
    }

    @GetMapping("/getInfo")
    @PermissionRequired(perms = "patent:patentPlan:view")
    @ApiOperation(value = "获取专利申请信息", notes = "获取专利申请信息")
    public Map<String, Object> getInfo(@RequestParam(value = "patentPlanId") Integer patentPlanId, @RequestParam(value = "patentNo", required = false) String patentNo) throws OwnerException {
        return patentPlanService.getInfo(patentPlanId, patentNo, getUserInfo().getId());
    }

    @GetMapping("/getPatentFiles")
    //@PermissionRequired(perms = "patent:demandList:search")
    @ApiOperation(value = "获取专利上传文件", notes = "获取专利上传文件")
    public Map<Integer, List<PatentFileModel>> getPatentFiles(@RequestParam(value = "patentPlanId", required = false) Integer patentPlanId, @RequestParam(value = "patentNo", required = false) String patentNo) throws OwnerException {
        return patentPlanService.getPatentFiles(patentPlanId, patentNo);
    }

    @PostMapping("/savePatent")
    @SystemLog(description = "保存专利", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "patent:patentPlan:process")
    @ApiOperation(value = "保存专利", notes = "保存专利")
    public Boolean savePatent(@RequestBody @Validated PatentInfoModel model) throws OwnerException {
        return patentPlanService.savePatent(model, getUserInfo());
    }

    @GetMapping("/checkPatentNo")
    //@PermissionRequired(perms = "patent:demandList:search")
    @ApiOperation(value = "检查专利号是否唯一", notes = "检查专利号是否唯一")
    public Boolean checkPatentNo(@RequestParam(value = "patentNo") String patentNo, @RequestParam(value = "patentId", required = false) Integer patentId) throws OwnerException {
        return patentPlanService.checkPatentNo(patentNo, patentId);
    }

    @PostMapping("/setProcess")
    @SystemLog(description = "设置进度", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "patent:patentPlan:process")
    @ApiOperation(value = "设置进度", notes = "设置进度")
    public Boolean setProcess(@RequestBody @Validated PatentPlanModel model) throws OwnerException {
        return patentPlanService.setProcess(model, getUserInfo());
    }

    @PostMapping("/submitPatent")
    @SystemLog(description = "提交专利审核", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "patent:patentPlan:submit")
    @ApiOperation(value = "提交专利审核", notes = "提交专利审核")
    public Boolean submitPatent(@RequestBody @Validated PatentPlanModel model) throws OwnerException {
        return patentPlanService.submitPatent(model, getUserInfo());
    }

    @GetMapping("/getAuditPatents")
    @ApiOperation(value = "获取专利审批列表", notes = "获取专利审批列表")
    public PageModel<List<PatentPlanModel>> getAuditPatents(QueryAuditDataModel query) throws OwnerException {
        return patentPlanService.getPatentList(query, getUserInfo().getId());
    }

    @GetMapping("/getPatentInfo")
    //@PermissionRequired(perms = "patent:demandList:search")
    @ApiOperation(value = "获取专利申请信息", notes = "获取专利申请信息")
    public PatentPlanModel getPatentInfo(Integer patentPlanId) throws OwnerException {
        return patentPlanService.getPatentInfo(patentPlanId);
    }

    @PostMapping("/setEngineer")
    @PermissionRequired(perms = "patent:patentPlan:engineer")
    @SystemLog(description = "分配专利申请工程师", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "分配专利申请工程师", notes = "分配专利申请工程师")
    public Boolean setEngineer(@RequestBody SetPatentEngineerModel setEngineer) throws OwnerException {
        return patentPlanService.setEngineer(setEngineer, getUserInfo().getId());
    }

    @PostMapping("/setMaster")
    @PermissionRequired(perms = "patent:patentPlan:master")
    @SystemLog(description = "设置专利代理人", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "设置专利代理人", notes = "设置专利代理人")
    public Boolean setMaster(@RequestBody PatentPlanEntity patentPlan) throws OwnerException {
        return patentPlanService.setMaster(patentPlan, getUserInfo().getId());
    }
}
