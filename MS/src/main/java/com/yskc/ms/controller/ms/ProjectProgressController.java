package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.PageResult;
import com.yskc.common.model.PdfConfigModel;
import com.yskc.common.utils.HtmlToPdfUtils;
import com.yskc.ms.config.Constant;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.entity.rs.RsCompanyEntity;
import com.yskc.ms.entity.rs.models.ProjectListModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.patentPlan.PatentAuditModel;
import com.yskc.ms.models.project.ProjectProgressModel;
import com.yskc.ms.models.project.ProjectStageModel;
import com.yskc.ms.models.project.QueryProjectProgressModel;
import com.yskc.ms.models.projectApproval.QueryProjectEquipmentModel;
import com.yskc.ms.models.projectAudit.*;
import com.yskc.ms.models.rs.*;
import com.yskc.ms.models.rs.summary.InitEquipmentModel;
import com.yskc.ms.service.ms.ProjectProgressService;
import com.yskc.ms.service.rs.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.controller.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-01-08 16:09
 * @Description: 项目进度control层
 */

@Api(tags = "项目进度接口", value = "项目进度接口")
@RestController
@RequestMapping("/api/projectProgress")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
public class ProjectProgressController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ProjectProgressService projectProgressService;
    @Autowired
    private RsProjectService rsProjectService;
    @Autowired
    private MsConfig msConfig;
    @Autowired
    private ProjectDocFileDataService projectDocFileDataService;
    @Autowired
    private HtmlToPdfUtils htmlToPdfUtils;
    @Autowired
    private ProposalManagementService proposalManagementService;
    @Autowired
    private RsAchievementService rsAchievementService;
    @Autowired
    private InitEquipmentService initEquipmentService;
    @Autowired
    private InitMemberService initMemberService;

    @GetMapping("/getList")
    @ApiOperation(notes = "获取项目进度列表", value = "获取项目进度列表")
    @SystemLog(description = "获取项目进度列表")
    @PermissionRequired(perms = "project:projectProgress:search")
    public PageResult getList(QueryProjectProgressModel query) throws OwnerException {
        return projectProgressService.getList(query, this.getUserInfo(), this.getDataPerm());
    }

    @GetMapping("/getFinalList")
    @ApiOperation(notes = "获取归集审核列表", value = "获取归集审核列表")
    @SystemLog(description = "获取归集审核列表")
    @PermissionRequired(perms = "customer:finalization:search")
    public PageModel<List<ProjectProgressModel>> getFinalList(QueryProjectProgressModel query) throws OwnerException {
        return projectProgressService.getData(query, this.getUserInfo(), this.getDataPerm());
    }

    /**
     * 查询项目列表
     *
     * @param year
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryProject")
    @PermissionRequired(perms = "project:projectProgress:rdDetail,project:highTechProject:rdDetail")
    @ApiOperation(value = "查询项目", notes = "查询项目", response = String.class)
    public List<ProjectListModel> queryProject(Integer companyId, Integer year) throws OwnerException {
        return rsProjectService.queryProject(companyId, year, getUserInfo().getId());
    }

    @GetMapping("/getProjectInfo")
    //@PermissionRequired(perms = "project:projectProgress:rdDetail,project:highTechProject:rdDetail")
    @ApiOperation(value = "查询项目详情", notes = "查询项目详情", response = String.class)
    public ProjectListModel getProjectInfo(Integer projectId) throws OwnerException {
        return rsProjectService.getProjectInfo(projectId);
    }

    @GetMapping("/getProgressDetail")
    @ApiOperation(value = "获取项目进度详情", notes = "获取项目进度详情", response = String.class)
    public List<ProjectStageModel> getProgressDetail(Integer year, Integer companyId) throws OwnerException {
        return projectProgressService.getProgressDetail(year, companyId);
    }

    @GetMapping("/getRdDept")
    @PermissionRequired(perms = "project:projectProgress:rdOrg,project:highTechProject:rdOrg")
    @ApiOperation(value = "获取ms项目研发部门", notes = "获取ms项目研发部门")
    public List<RdDeptTree> getRdDept(Integer companyId, Integer year) throws OwnerException {
        return RdDeptTree.getTree(projectProgressService.getRdDept(companyId, year));
    }

    @GetMapping("/getOrgPath")
    @PermissionRequired(perms = "project:projectProgress:rdOrg,project:highTechProject:rdOrg")
    @ApiOperation(value = "获取rs公司组织架构图片路径", notes = "获取rs公司组织架构图片路径")
    public List<Object> getOrgPath(Integer companyId, Integer year) throws OwnerException {
        return projectProgressService.getOrgPath(companyId, year);
    }

    @GetMapping("/export")
    @ApiOperation(value = "导出项目进度列表/归集审核列表", notes = "导出项目进度列表/归集审核列表")
    @PermissionRequired(perms = "customer:projectProgress:export,customer:finalization:export")
    @SystemLog(description = "导出项目进度列表/归集审核列表")
    public void export(QueryProjectProgressModel query) throws OwnerException {
        String fileName;
        String templateName;
        if (query.getSign().equals(1)) {
            fileName = "项目进度列表.xls";
            templateName = "项目进度列表模板.xls";
        } else {
            fileName = "归集审核列表.xls";
            templateName = "归集审核列表模板.xls";
        }
        String path = msConfig.getUploadConfig().getResourcePath() + Constant.TEMPLATE_DIR + templateName;
        try (OutputStream out = outGeneralFile(fileName)) {
            projectProgressService.export(query, this.getUserInfo(), this.getDataPerm(), out, path);
            out.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new OwnerException("导出失败");
        }
    }

    /**
     * 获取公司项目列表
     *
     * @param year
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getListProject")
    @PermissionRequired(perms = "customer:projectProgress:preview,project:highTechProject:preview")
    @ApiOperation(value = "查询项目", notes = "查询项目", response = String.class)
    public List<RsProjectListModel> getAllProject(Integer year, Integer companyId, Integer auditCount) throws OwnerException {
        return rsProjectService.getAllProject(companyId, year, getUserInfo().getId(), auditCount == null ? 0 : auditCount);
    }

    /**
     * 获取查新报告项目列表
     *
     * @param year
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getReportProject")
    @ApiOperation(value = "获取查新报告项目列表", notes = "获取查新报告项目列表", response = String.class)
    public List<ReportProjectModel> getReportProject(Integer year, Integer companyId) throws OwnerException {
        return rsProjectService.getReportProject(companyId, year, getUserInfo().getId());
    }

    @GetMapping("/queryStage")
    @PermissionRequired(perms = "customer:projectProgress:preview,project:highTechProject:preview")
    @ApiOperation(value = "查询阶段文件", notes = "查询阶段文件", response = String.class)
    public List<RsStageModel> queryStage(Integer projectId, Integer companyId) throws OwnerException {
        return projectProgressService.queryStage(companyId, projectId, getUserInfo().getId());
    }


    @GetMapping("/getStageAudit")
    @PermissionRequired(perms = "customer:projectProgress:preview,project:highTechProject:preview")
    @ApiOperation(value = "查询阶段审核", notes = "查询阶段审核", response = String.class)
    public List<RsStageModel> getStageAudit(Integer projectId, Integer companyId) throws OwnerException {
        return projectProgressService.getStageAudit(companyId, projectId, getUserInfo().getId());
    }

    @SystemLog(description = "获取项目文档数据")
    @GetMapping("/getData")
    @PermissionRequired(perms = "customer:projectProgress:preview,project:highTechProject:preview")
    @ApiOperation(notes = "获取项目文档数据", value = "获取项目文档数据")
    public DocFileDataModel getData(Integer pDocFileId) {
        return projectDocFileDataService.getData(pDocFileId, true);
    }


    /**
     * 预览获取过程文件data
     *
     * @param dataModel
     * @throws Exception
     */
    @GetMapping("/previewFile")
    // @SystemLog(description = "预览获取过程文件data", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "customer:projectProgress:preview,project:highTechProject:preview")
    @ApiOperation(value = "预览获取过程文件data", notes = "预览获取过程文件data")
    public String previewFile(DataModel dataModel) throws Exception {
        DocFileDataModel tempInfo = projectDocFileDataService.getData(dataModel.getpDocFileId(), dataModel.getImportFooterName());
        String templateName = tempInfo.getTemplateName();
        if (StringUtils.isEmpty(templateName)) {
            throw new OwnerException("模板尚未确认,不能预览");
        }
        String tempPath = MessageFormat.format(Constant.HTML_TEMPLATE_DIR + "{0}.html", templateName);
        Map<String, Object> dataMap = projectDocFileDataService.exportWord(dataModel, getUserInfo(), false);
        dataMap.put("ftlPath", msConfig.getExpertConfig().getDomainName() + "/static/");
        dataMap.put("ftlDocFileName", tempInfo.getDocFileName());
        return htmlToPdfUtils.generateHtml(tempPath, dataMap);
    }


    /**
     * 过程文件导出pdf
     *
     * @param dataModel
     * @return
     * @throws Exception
     */
    @GetMapping("/exportPdf")
    // @SystemLog(description = "预览获取过程文件data", mode = SystemLog.SAVE_DB)
    // @PermissionRequired(perms = "project:doc:preview")
    @ApiOperation(value = "过程文件导出pdf", notes = "过程文件导出pdf")
    public void exportPdf(DataModel dataModel) throws Exception {
        DocFileDataModel tempInfo = projectDocFileDataService.getData(dataModel.getpDocFileId(), dataModel.getImportFooterName());
        UserInfo userInfo = getUserInfo();
        String templateName = tempInfo.getTemplateName();
        if (StringUtils.isEmpty(templateName)) {
            throw new OwnerException("模板尚未确认,无法导出pdf");
        }
        String tempPath = MessageFormat.format(Constant.HTML_TEMPLATE_DIR + "{0}.html", templateName);
        Map<String, Object> dataMap = projectDocFileDataService.exportWord(dataModel, getUserInfo(), true);
        dataMap.put("ftlDocFileName", tempInfo.getDocFileName());
        OutputStream out = outGeneralFile(MessageFormat.format("{0}.pdf", tempInfo.getDocFileName()));
        RsCompanyEntity companyEntity = projectDocFileDataService.getCompanyInfo(dataModel.getProjectId());
        String logo = StringUtils.isEmpty(companyEntity.getLogo()) ? null : Constant.IMAGES_DIR + companyEntity.getLogo();
        dataMap.put("ftlPath", "/static/");
        htmlToPdfUtils.htmlToPdf(tempPath, dataMap, companyEntity.getCompanyName(), logo, out);
    }

    /**
     * 预览获取过程文件data
     *
     * @param model
     * @throws Exception
     */
    @GetMapping("/previewAllDoc")
    @SystemLog(description = "预览获取过程文件data", mode = SystemLog.SAVE_DB)
    //@PermissionRequired(perms = "project:doc:preview")
    @ApiOperation(value = "预览所有过程文件", notes = "预览所有过程文件")
    public String previewAllDoc(ExportDocFileModel model) throws Exception {
        if (model == null) {
            throw new OwnerException("请选择要预览过程文件的项目！");
        }
        UserInfo info = getUserInfo();
        List<Map<String, Object>> htmlMaps = projectDocFileDataService.exportAllDoc(model, info, false);
        if (CollectionUtils.isEmpty(htmlMaps)) {
            throw new OwnerException("该项目尚未添加过程文件！");
        }
        List<String> htmlList = new ArrayList<>();
        for (Map<String, Object> map : htmlMaps) {
            String path = map.get("exportTemplateFilePath").toString();
            try {
                String str = htmlToPdfUtils.generateHtml(path, map);
                htmlList.add(str);
            } catch (Exception e) {
            }
        }
        return String.join("", htmlList);
    }

    /**
     * 导出所有过程文件
     *
     * @throws Exception
     */
    @GetMapping("/exportAllDoc")
    @PermissionRequired(perms = "customer:projectProgress:exportDocFile")
    @ApiOperation(value = "阶段文件导出word报告", notes = "阶段文件导出word报告")
    public void exportAllDoc(ExportDocFileModel model) throws Exception {
        if (model == null) {
            throw new OwnerException("请选择要导出过程文件的项目！");
        }
        UserInfo info = getUserInfo();
        List<Map<String, Object>> htmlMaps = projectDocFileDataService.exportAllDoc(model, info, true);
        if (CollectionUtils.isEmpty(htmlMaps)) {
            throw new OwnerException("该项目尚未添加过程文件！");
        }
        List<String> htmlList = new ArrayList<>();
        for (Map<String, Object> map : htmlMaps) {
            String path = map.get("exportTemplateFilePath").toString();
            try {
                String str = htmlToPdfUtils.generateHtml(path, map);
                htmlList.add(str);
            } catch (Exception e) {
            }
        }
        RsCompanyEntity companyEntity = projectDocFileDataService.getCompanyInfo(model.getProjectId());
        try (OutputStream out = outGeneralFile("过程文件导出.pdf")){
            String logo = StringUtils.isEmpty(companyEntity.getLogo()) ? null : Constant.IMAGES_DIR + companyEntity.getLogo();
            PdfConfigModel pdfConfig = new PdfConfigModel(model.getHeader(),model.getHeader(),model.getPageNum(),companyEntity.getCompanyName(), logo,model.getCatalogue(),model.getCover());
            htmlToPdfUtils.htmlToPdf(htmlList, out, pdfConfig);
            out.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new OwnerException("导出失败");
        }

    }


    @PostMapping("/projectAudit")
    @PermissionRequired(perms = "customer:projectProgress:audit")
    @ApiOperation(value = "审核流程", notes = "审核流程")
    @SystemLog(description = "审核流程", mode = SystemLog.SAVE_DB)
    public Boolean projectAudit(@RequestBody @Validated ProjectAuditModel model) throws OwnerException {
        return projectProgressService.projectAudit(model, getUserInfo());
    }

    @PostMapping("/checkAudits")
//    @PermissionRequired(perms = "customer:projectProgress:audit")
    @ApiOperation(value = "批量审核查新流程", notes = "批量审核查新流程")
    @SystemLog(description = "批量审核查新流程", mode = SystemLog.SAVE_DB)
    public Boolean checkAudits(@RequestBody @Validated CheckAuditModel model) throws OwnerException {
        return projectProgressService.checkAudits(model,getUserInfo());
    }

    @PostMapping("/projectAudits")
    @PermissionRequired(perms = "customer:projectProgress:auditBatch,patent:auditList:audit,customer:serviceApply:review," +
            "patent:patentPlan:audit,innovation:rdFeeAudit:audit,patent:patentPlanNew:cancel,customer:serviceApply:review")
    @ApiOperation(value = "批量审核流程", notes = "批量审核流程")
    @SystemLog(description = "批量审核流程", mode = SystemLog.SAVE_DB)
    public Boolean projectAudits(@RequestBody @Validated BatchAuditModel batchAudit) throws OwnerException {
        return projectProgressService.projectAudits(batchAudit, getUserInfo());
    }
    @PostMapping("/cancelPatent")
    @PermissionRequired(perms = "patent:patentPlanNew:cancel")
    @ApiOperation(value = "批量取消专利审核", notes = "批量取消专利审核")
    @SystemLog(description = "批量取消专利审核", mode = SystemLog.SAVE_DB)
    public Boolean cancelPatent(@RequestBody @Validated BatchAuditModel batchAudit) throws OwnerException {
        return projectProgressService.cancelPatent(batchAudit, getUserInfo());
    }

    @PostMapping("/activateFlow")
    @PermissionRequired(perms = "customer:projectProgress:activateFlow")
    @ApiOperation(value = "激活流程", notes = "激活流程")
    public Boolean activateFlow(@RequestBody @Validated ProjectAuditModel model) throws OwnerException {
        return projectProgressService.activateFlow(model, getUserInfo());
    }

    @PostMapping("/activateFlows")
    @PermissionRequired(perms = "customer:projectProgress:activateFlows")
    @ApiOperation(value = "批量激活流程", notes = "批量激活流程")
    public Boolean activateFlows(@RequestBody @Validated BatchAuditModel batchAudit) throws OwnerException {
        return projectProgressService.activateFlows(batchAudit, getUserInfo());
    }


    @GetMapping("/getAuditInfo")
    @ApiOperation(value = "获取审核信息", notes = "获取审核信息")
    public List<AuditStatusModel> getAuditInfo(Integer projectId) throws OwnerException {
        return projectProgressService.getAuditInfo(projectId, getUserInfo().getId());
    }


    @GetMapping("/getAuditLog")
    //@PermissionRequired(perms = "customer:projectProgress:view")
    @ApiOperation(value = "获取审核记录", notes = "获取审核记录")
    public Map<String, Object> getAuditLog(Integer companyId, Integer year, Integer moduleId, Integer docFileId, Integer rsProjectId,
                                           Integer patentPlanId, Integer proposalId, Integer achievementId,Integer rdFeeId) throws OwnerException {
        return projectProgressService.getAuditLog(companyId, year, moduleId, docFileId, getUserInfo().getId(),
                rsProjectId, patentPlanId, proposalId, achievementId,rdFeeId);
    }

    @GetMapping("/getEmployeeList")
    @ApiOperation(value = "获取研发花名册列表", notes = "获取研发花名册列表")
    public PageResult getEmployeeList(QueryAuditDataModel query) {
        return projectProgressService.getEmployeeList(query);
    }

    @GetMapping("/exportEmployeeBindInfo")
    @ApiOperation(value = "导出研发人员绑定openid列表", notes = "导出研发人员绑定openid列表")
    public void exportEmployeeBindInfo(QueryAuditDataModel query) throws IOException {
        projectProgressService.exportEmployeeBindInfo(outGeneralFile("研发考勤绑定情况.xlsx"), query);
    }

    @GetMapping("/getEquipmentList")
    @ApiOperation(value = "获取研发设备列表", notes = "获取研发设备列表")
    public PageResult getEquipmentList(QueryAuditDataModel query) {
        return projectProgressService.getEquipmentList(query);
    }

    @GetMapping("/getPatentList")
    @ApiOperation(value = "获取专利交底书审核列表", notes = "获取专利交底书审核列表")
    public PageModel<List<PatentAuditModel>> getPatentList(QueryAuditDataModel query) throws OwnerException {
        return projectProgressService.getPatentList(query, getUserInfo().getId(), 1);
    }

    @GetMapping("/getAuditPatents")
    @ApiOperation(value = "获取专利审批列表", notes = "获取专利审批列表")
    public PageModel<List<PatentAuditModel>> getAuditPatents(QueryAuditDataModel query) throws OwnerException {
        return projectProgressService.getPatentList(query, getUserInfo().getId(), 2);
    }

    @GetMapping("/getProjectReport")
    @ApiOperation(value = "获取项目立项报告", notes = "获取项目立项报告")
    public List<ReportListModel> getProjectReport(Integer year, Integer companyId) throws OwnerException {
        return projectProgressService.getProjectReport(year, companyId, getUserInfo().getId(), 27);
    }

    @GetMapping("/getProjectNewReport")
    @ApiOperation(value = "获取项目查新报告", notes = "获取项目查新报告")
    public List<ReportListModel> getProjectNewReport(Integer year, Integer companyId) throws OwnerException {
        return projectProgressService.getProjectReport(year, companyId, getUserInfo().getId(), 38);
    }


    @GetMapping("/getCountData")
    @ApiOperation(value = "获取统计数据", notes = "获取统计数据")
    public ProjectSummaryDataModel getCountData(Integer year, Integer customerId, Integer companyId) {
        return projectProgressService.getCountData(year, customerId, companyId);
    }


    @GetMapping("/getProjectAudit")
    @ApiOperation(value = "获取当前用户项目审核权限", notes = "获取当前用户项目审核权限")
    public Map<Integer, Object> getProjectAudit(Integer year, Integer companyId) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        Map<Integer, Object> resultMap = new HashMap<>();
        Map<Integer, Boolean> map = projectProgressService.getProjectAudit(companyId, year, userInfo);
        Map<Integer, Integer> dataMap = projectProgressService.getDocAudit(companyId, year, userInfo.getId());
        Map<Integer, Integer> patentMap = projectProgressService.getPatentAudit(companyId, year, userInfo.getId());
        Integer projectCnt = projectProgressService.getProjectAuditInfo(year, companyId, userInfo.getId());
        Integer proposalCnt = proposalManagementService.getProposalAuditInfo(year, companyId, userInfo.getId());
        Integer achievementCnt = rsAchievementService.getAuditCnt(year, companyId, userInfo.getId());
//        Integer reportNum = projectProgressService.getReportAudit(companyId, year, userInfo.getId());
//        resultMap.put(8, reportNum);
        resultMap.putAll(map);
        resultMap.putAll(dataMap);
        resultMap.putAll(patentMap);
        resultMap.put(4, projectCnt);
        resultMap.put(11, proposalCnt);
        resultMap.put(12, achievementCnt);
        return resultMap;
    }

    /**
     * @param query
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getMemberDataList")
    @ApiOperation(value = "获取项目成员列表", notes = "获取项目成员列表")
    public PageModel<List<InitMemberModel>> getMemberDataList(QueryProjectInitMemberModel query) {
        return initMemberService.getList(query);
    }

    /**
     * @param query
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getEquipmentDataList")
    @ApiOperation(value = "获取设备清单列表", notes = "获取设备清单列表")
    public PageModel<List<InitEquipmentModel>> getEquipmentDataList(QueryProjectEquipmentModel query) {
        return initEquipmentService.getList(query);
    }

}
