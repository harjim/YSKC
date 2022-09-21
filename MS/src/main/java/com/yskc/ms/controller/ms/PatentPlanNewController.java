package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.PageResult;
import com.yskc.ms.models.BatchDeleteModel;
import com.yskc.ms.models.patent.PatentMasterModel;
import com.yskc.ms.models.patentPlan.*;
import com.yskc.ms.models.projectAudit.ProjectAuditModel;
import com.yskc.ms.models.projectAudit.QueryAuditDataModel;
import com.yskc.ms.models.rs.RsProjectBaseModel;
import com.yskc.ms.service.ms.PatentPlanNewService;
import com.yskc.ms.service.ms.ProjectProgressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @DateTime: 2022/2/21 10:05
 * @Description: 专利请求（新）接口
 * @author: hsx
 */
@Api(tags = "专利列表（新）接口", value = "专利列表（新）接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/patentPlanNew")
public class PatentPlanNewController extends BaseController{

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProjectProgressService projectProgressService;

    @Autowired
    private PatentPlanNewService patentPlanNewService;

    @GetMapping("/getProjectSelect")
    //@PermissionRequired(perms = "patent:demandList:search")
    @ApiOperation(value = "获取专利申请项目下拉列表", notes = "获取专利申请项目下拉列表")
    public List<RsProjectBaseModel> getProjectSelect(Integer companyId, Integer year) throws OwnerException {
        return patentPlanNewService.getProjectSelect(companyId, year);
    }

    @GetMapping("/getList")
    @PermissionRequired(perms = "patent:patentPlanNew:search")
    @ApiOperation(value = "获取专利申请列表", notes = "获取专利申请列表")
    public PageResult getList(QueryPatentPlanModel query) throws OwnerException {
        return patentPlanNewService.getList(query, getDataPerm());
    }

    @PostMapping("/save")
    @SystemLog(description = "保存专利申请", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "patent:patentPlanNew:add,patent:patentPlanNew:editBase")
    @ApiOperation(value = "保存专利申请", notes = "保存专利申请")
    public Boolean save(@RequestBody @Validated PatentPlanModel model) throws OwnerException {
        return patentPlanNewService.save(model, getUserInfo());
    }

    @PostMapping("/edit")
    @SystemLog(description = "编辑专利申请信息", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "patent:patentPlanNew:editBase")
    @ApiOperation(value = "编辑专利申请信息", notes = "编辑专利申请信息")
    public Boolean edit(@RequestBody @Validated PatentPlanModel model) throws OwnerException {
        return patentPlanNewService.edit(model, getUserInfo());
    }

    @PostMapping("/del")
    @PermissionRequired(perms = "patent:patentPlanNew:delete")
    @SystemLog(description = "删除专利申请", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "删除专利申请", notes = "删除专利申请")
    public Boolean del(@RequestBody @Validated BatchDeleteModel deleteModel) throws OwnerException {
        return patentPlanNewService.del(deleteModel.getIds());
    }

    @GetMapping("/getInfo")
    @PermissionRequired(perms = "patent:patentPlanNew:patent")
    @ApiOperation(value = "获取专利申请信息", notes = "获取专利申请信息")
    public Map<String, Object> getInfo(@RequestParam(value = "patentPlanId") Integer patentPlanId, @RequestParam(value = "patentNo", required = false) String patentNo) throws OwnerException {
        return patentPlanNewService.getInfo(patentPlanId, patentNo, getUserInfo().getId());
    }

    @PostMapping("/savePatent")
    @SystemLog(description = "保存专利", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "patent:patentPlanNew:edit")
    @ApiOperation(value = "保存专利", notes = "保存专利")
    public Boolean savePatent(@RequestBody @Validated PatentInfoModel model) throws OwnerException {
        return patentPlanNewService.savePatent(model, getUserInfo());
    }

    @PostMapping("/submitPatent")
    @SystemLog(description = "提交专利审核", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "patent:patentPlanNew:add")
    @ApiOperation(value = "提交专利审核", notes = "提交专利审核")
    public Boolean submitPatent(@RequestBody @Validated PatentPlanModel model) throws OwnerException {
        return patentPlanNewService.submitPatent(model, getUserInfo());
    }

    @PostMapping("/setMaster")
    @PermissionRequired(perms = "patent:patentPlanNew:setMaster")
    @SystemLog(description = "设置专利代理人", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "设置专利代理人", notes = "设置专利代理人")
    public Boolean setMaster(@RequestBody List<PatentMasterModel> models) throws OwnerException {
        return patentPlanNewService.setMaster(models, getUserInfo().getId(), getUserInfo());
    }

    @PostMapping("/patentAudit")
    @ApiOperation(value = "专利审核流程", notes = "专利审核流程")
    @SystemLog(description = "专利审核流程", mode = SystemLog.SAVE_DB)
    public Boolean patentAudit(@RequestBody @Validated ProjectAuditModel model) throws OwnerException {
        return projectProgressService.patentAudit(model, getUserInfo());
    }

    @PostMapping("/setEngineer")
    @PermissionRequired(perms = "patent:patentPlanNew:engineer")
    @SystemLog(description = "分配专利申请工程师", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "分配专利申请工程师", notes = "分配专利申请工程师")
    public Boolean setEngineer(@RequestBody SetPatentEngineerModel setEngineer) throws OwnerException {
        return patentPlanNewService.setEngineer(setEngineer, getUserInfo().getId());
    }

    @GetMapping("/getPatentInfo")
    //@PermissionRequired(perms = "patent:demandList:search")
    @ApiOperation(value = "获取专利申请信息", notes = "获取专利申请信息")
    public PatentPlanModel getPatentInfo(Integer patentPlanId) throws OwnerException {
        return patentPlanNewService.getPatentInfo(patentPlanId);
    }

    @GetMapping("/getAuditPatents")
    @ApiOperation(value = "获取专利审批列表", notes = "获取专利审批列表")
    public PageModel<List<PatentPlanModel>> getAuditPatents(QueryAuditDataModel query) throws OwnerException {
        return patentPlanNewService.getPatentList(query, getUserInfo().getId());
    }

    @GetMapping("/checkPatentNo")
    //@PermissionRequired(perms = "patent:demandList:search")
    @ApiOperation(value = "检查专利号是否唯一", notes = "检查专利号是否唯一")
    public Boolean checkPatentNo(@RequestParam(value = "patentNo") String patentNo, @RequestParam(value = "patentId", required = false) Integer patentId) throws OwnerException {
        return patentPlanNewService.checkPatentNo(patentNo, patentId);
    }

    @GetMapping("/getPatentFiles")
    //@PermissionRequired(perms = "patent:demandList:search")
    @ApiOperation(value = "获取专利上传文件", notes = "获取专利上传文件")
    public Map<Integer, List<PatentFileModel>> getPatentFiles(@RequestParam(value = "patentPlanId", required = false) Integer patentPlanId, @RequestParam(value = "patentNo", required = false) String patentNo) throws OwnerException {
        return patentPlanNewService.getPatentFiles(patentPlanId, patentNo);
    }


}
