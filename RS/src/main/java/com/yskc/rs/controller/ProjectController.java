package com.yskc.rs.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PdfConfigModel;
import com.yskc.common.utils.HtmlToPdfUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.entity.project.DocFileMeetingEntity;
import com.yskc.rs.models.BatchDeleteModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.cdocument.CDocumentModel;
import com.yskc.rs.models.docFile.DocFileFooterModel;
import com.yskc.rs.models.docFile.MeetingCountModel;
import com.yskc.rs.models.docFile.MeetingFromModel;
import com.yskc.rs.models.employee.EmployeeSelectModel;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.models.excel.ProjectExcel;
import com.yskc.rs.models.project.*;
import com.yskc.rs.service.DocumentService;
import com.yskc.rs.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 部门类接口
 *
 * @author huronghua
 */
@Api(tags = "项目类接口", value = "项目类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/project")
public class ProjectController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ProjectService projectService;
    @Autowired
    private RsConfig rsConfig;
    @Autowired
    private HtmlToPdfUtils htmlToPdfUtils;
    @Autowired
    private DocumentService documentService;


    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "更新项目", mode = SystemLog.SAVE_DB)
    @PostMapping("/update")
    @PermissionRequired(perms = "project:report:base:save,project:report:base:updateYear")
    @ApiOperation(value = "更新项目", notes = "更新项目")
    public Boolean update(@RequestBody @Validated ProjectModel model) throws OwnerException {
        return projectService.update(model, getUserInfo());
    }


    /**
     * @param ename
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getEmployeeList")
    @ApiOperation(value = "人员下拉列表", notes = "人员下拉列表")
    public List<EmployeeSelectModel> getEmployeeList(String ename, Integer year) throws OwnerException {
        return projectService.getEmployeeList(getUserInfo().getCompanyId(), year, ename);
    }

    @GetMapping("/getBaseEmployeeSelect")
    @ApiOperation(value = "花名册下拉列表", notes = "花名册下拉列表")
    public List<EmployeeSelectModel> getBaseEmployeeSelect(String ename) throws OwnerException {
        return projectService.getBaseEmployeeSelect(getUserInfo().getCompanyId(), ename);
    }

    @GetMapping("/getEmployeeNames")
    @ApiOperation(value = "花名册下拉列表(可自定义)", notes = "花名册下拉列表(可自定义)")
    public List<EmployeeSelectModel> getEmployeeNames(String ename) throws OwnerException {
        return projectService.getEmployeeNames(getUserInfo().getCompanyId(), ename);
    }

    @GetMapping("/getEmployeeSelect")
    @ApiOperation(value = "研发人员，项目人员下拉列表", notes = "研发人员，项目人员下拉列表")
    public List<EmployeeSelectModel> getEmployeeSelect(QueryProjectEmployeeModel model) throws OwnerException {
        return projectService.getEmployeeSelect(getUserInfo(), model);
    }

    /**
     * 导出RD列表
     *
     * @param year
     * @param year
     * @return
     * @throws Exception
     */
    @SystemLog(description = "导出RD列表")
    @PermissionRequired(perms = "project:report:list:export")
    @GetMapping("/exportProject")
    @ApiOperation(value = "导出RD列表 ", notes = "导出RD列表")
    public void exportProject(Integer year) throws Exception {
        UserInfo userInfo = getUserInfo();
        String templateName = "项目列表模板.xlsx";
        String path = rsConfig.getUploadConfig().getResourcePath() + Constant.TEMPLATE_DIR + templateName;
        try (OutputStream out =outGeneralFile(MessageFormat.format("{0}{1}年项目列表.xlsx", userInfo.getCompanyName(),year))) {
            projectService.exportProject(getUserInfo(),year,out,path);
            out.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new OwnerException("导出失败");
        }
    }

    /**
     * @param typeModel
     * @return
     * @throws OwnerException
     */
    @PostMapping("/insertData")
    @SystemLog(description = "插入委外项目费用")
    @PermissionRequired(perms = "project:report:outSource:save")
    @ApiOperation(value = "插入委外项目费用", notes = "插入委外项目费用")
    public Boolean insertData(@RequestBody List<TypeModel> typeModel) throws OwnerException {
        return projectService.addBatch(getUserInfo(), typeModel);

    }

    /**
     * @param projectId
     * @return
     */
    @GetMapping("/querySummaryByProjectId")
    @PermissionRequired(perms = "project:report:outSource:view")
    @ApiOperation(value = "获取委外费用", notes = "获取委外费用")
    public List<TypeModel> querySummaryByProjectId(Integer projectId) {
        return projectService.querySummaryByProjectId(projectId);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "删除项目", mode = SystemLog.SAVE_DB)
    @PostMapping("/delete")
    @ApiOperation(value = "删除项目", notes = "删除项目", response = boolean.class)
    @PermissionRequired(perms = "project:report:list:del")
    public boolean delete(@RequestBody @Validated DelProjectModel model) throws OwnerException {
        return projectService.deleteProject(getUserInfo(), model);
    }


    /**
     * @param rdIndex
     * @param year
     * @param projectId
     * @return
     * @throws OwnerException
     */
    @GetMapping("/checkRD")
    @ApiOperation(value = "检查重复RD", notes = "检查重复RD")
    public boolean checkRD(Integer rdIndex, Integer year, Integer projectId) throws OwnerException {
        return projectService.checkRD(getUserInfo().getCompanyId(), rdIndex, year, projectId);
    }

    @GetMapping("/checkPname")
    @ApiOperation(value = "检查重复项目名", notes = "检查重复项目名")
    public boolean checkPname(String pname, Integer projectId) throws OwnerException {
        return projectService.checkPname(pname, projectId);
    }


    /**
     * @param year
     * @return
     * @throws OwnerException
     */
    @GetMapping("/selectMaxRd")
    @ApiOperation(value = "查询最大RD", notes = "查询最大RD")
    public Integer selectMaxRd(Integer year) throws OwnerException {
        return projectService.selectMaxRd(getUserInfo().getCompanyId(), year);
    }


    /**
     * 根据年份获取项目列表
     *
     * @param year
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getProjectsByYear")
    @ApiOperation(value = "根据年份获取项目列表", notes = "根据年份获取项目列表")
    public List<ProjectListModel> getProjectsByYear(Integer year, Integer formula) throws OwnerException {
        return projectService.getProjectsByYear(getUserInfo().getCompanyId(), year, formula);
    }

    /**
     * 查询项目列表
     *
     * @param year
     * @param pname
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryProject")
    @ApiOperation(value = "查询项目", notes = "查询项目", response = String.class)
    public List<ProjectListModel> queryProject(Integer year, String pname, String rd) throws OwnerException {
        return projectService.queryProject(getUserInfo().getCompanyId(), year, pname, rd);
    }

    /**
     * 查询项目列表(父项目中含子项目列表)
     *
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getList")
    @PermissionRequired(perms = "project:report:list:search")
    @ApiOperation(value = "查询项目", notes = "查询项目", response = String.class)
    public List<ProjectListModel> getList(QueryProjectListModel query) throws OwnerException {
        return projectService.getProjectList(getUserInfo().getCompanyId(), query);
    }

    /**
     * 查询项目
     *
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getMaster")
    @PermissionRequired(perms = "project:report:list:search")
    @ApiOperation(value = "查询项目负责人", notes = "查询项目负责人", response = String.class)
    public Map<String, String> getMaster(Integer projectId, Integer year) throws OwnerException {
        return projectService.getMaster(projectId, year);
    }

    /**
     * 查询项目列表(研发过程材料项目下拉框数据)
     *
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getAllProject")
    //@PermissionRequired(perms = "project:report:list:search")
    @ApiOperation(value = "查询项目", notes = "查询项目", response = String.class)
    public List<ProjectListModel> getAllProject(Integer year) throws OwnerException {
        return projectService.getAllProject(getUserInfo().getCompanyId(), year);
    }

    @GetMapping("/checkParentNo")
    @ApiOperation(value = "合并项目检查重复项目编号", notes = "合并项目检查重复项目编号")
    public boolean checkParentNo(@RequestParam(value = "rdIndex") Integer rdIndex, @RequestParam(value = "projectIds") List<Integer> projectIds) throws OwnerException {
        return projectService.checkParentNo(getUserInfo(), rdIndex, projectIds);
    }

    @SystemLog(description = "合并项目", mode = SystemLog.SAVE_DB)
    @PostMapping("/mergeProject")
    @PermissionRequired(perms = "project:report:list:merge")
    @ApiOperation(value = "合并项目", notes = "合并项目")
    public Boolean mergeProject(@RequestBody @Validated ParentProjectModel model) throws OwnerException {
        return projectService.mergeProject(getUserInfo(), model);
    }

    @GetMapping("/queryParentList")
    @ApiOperation(value = "查询所有父项目", notes = "查询所有父项目")
    public List<ProjectSelectModel> queryParentList(@RequestParam(value = "projectIds") List<Integer> projectIds, @RequestParam(value = "currentYear") Integer currentYear) throws OwnerException {
        return projectService.queryParentList(getUserInfo().getCompanyId(), projectIds, currentYear);
    }

    @SystemLog(description = "移出项目", mode = SystemLog.SAVE_DB)
    @PostMapping("/removeProject")
    @PermissionRequired(perms = "project:report:list:remove")
    @ApiOperation(value = "移出项目", notes = "移出项目")
    public Boolean removeProject(@RequestBody @Validated ProjectListModel model) throws OwnerException {
        return projectService.removeProject(model.getParentId(), model.getRdIndex(), getUserInfo(), model.getId());
    }

    @GetMapping("/getSelectList")
    //@PermissionRequired(perms = "project:report:list:search")
    @ApiOperation(value = "查询项目下拉列表", notes = "查询项目下拉列表", response = String.class)
    public List<ProjectSelectModel> getSelectList(Integer year, @RequestParam(required = false) Integer sign) throws OwnerException {
        return projectService.getSelectList(getUserInfo().getCompanyId(), year, sign);
    }

    @GetMapping("/getSimpleList")
    //@PermissionRequired(perms = "project:report:list:search")
    @ApiOperation(value = "查询项目下拉列表", notes = "查询项目下拉列表", response = String.class)
    public List<ProjectSelectModel> getSimpleList(Integer year, @RequestParam(required = false) Integer sign) throws OwnerException {
        return projectService.getSimpleList(getUserInfo().getCompanyId(), year, sign);
    }

    @GetMapping("/getSignatureList")
    //@PermissionRequired(perms = "project:report:list:search")
    @ApiOperation(value = "查询项目下拉列表(签名)", notes = "查询项目下拉列表(签名)", response = String.class)
    public List<DocFileFooterModel> getSignatureList(Integer year) throws OwnerException {
        return projectService.getSignatureList(getUserInfo().getCompanyId(), year);
    }

    @GetMapping("/getYearSelectList")
    @ApiOperation(value = "按开始年查询原子项目下拉列表", notes = "按开始年查询原子项目下拉列表", response = String.class)
    public List<ProjectInfoModel> getYearSelectList(Integer year) throws OwnerException {
        return projectService.getYearSelectList(getUserInfo().getCompanyId(), year);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "添加项目", mode = SystemLog.SAVE_DB)
    @PostMapping("/addProject")
    @PermissionRequired(perms = "project:report:list:add")
    @ApiOperation(value = "添加项目", notes = "添加项目")
    public Boolean addProject(@RequestBody @Validated ProjectModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        model.setCompanyId(userInfo.getCompanyId());
        return projectService.addProject(model, userInfo);
    }

    /**
     * @param file
     * @param year
     * @param tableField
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "导入RD列表", mode = SystemLog.SAVE_DB)
    @PostMapping("/importProject")
    @PermissionRequired(perms = "project:report:list:import")
    @ApiOperation(value = "导入RD列表", notes = "导入RD列表", response = String.class)
    public String importProject(@RequestParam("file") MultipartFile file, Integer year, TableField tableField) throws OwnerException {
        UserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                info.getCompanyId());
        ExcelResult<ProjectExcel> excelResult = excelService.getExcelResult(tempPath, file, ProjectExcel.class, tableField);
        return projectService.importProject(info, excelResult.getData(), year);
    }

    @SystemLog(description = "归集总表", mode = SystemLog.SAVE_DB)
    @GetMapping("/getDataReportFunds")
    @PermissionRequired(perms = "project:summaryReport:view,rdagg:aggregation:view")
    @ApiOperation(value = "归集总表", notes = "归集总表")
    public Map<String, Object> getDataReportFunds(Integer year, Boolean child) throws OwnerException {
        if (child == null) {
            child = true;
        }
        return projectService.getDataReportFunds(getUserInfo().getCompanyId(), year, child);
    }

    /**
     * 项目明细表
     *
     * @param year
     * @return
     * @throws OwnerException
     */
    @GetMapping("/querySubsidiaryLedger")
    @PermissionRequired(perms = "project:subsidiaryLedger:view")
    @ApiOperation(value = "项目明细表", notes = "项目明细表")
    public List<Map<String, Object>> querySubsidiaryLedger(Integer year, Boolean child) throws OwnerException, ParseException {
        if (child == null) {
            child = true;
        }
        return projectService.querySubsidiaryLedger(getUserInfo().getCompanyId(), year, child, 0);
    }

    /**
     * 研发工资月报表
     *
     * @param year
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getRdSalaryMonth")
    @PermissionRequired(perms = "project:rdSalaryRepot:view")
    @ApiOperation(value = "获取研发工资月报表数据", notes = "获取研发工资月报表数据")
    public List<Map<String, Object>> getRdSalaryMonth(Integer year, Integer type) throws OwnerException, ParseException {
        return projectService.querySubsidiaryLedger(getUserInfo().getCompanyId(), year, false, type);
    }

    @SystemLog(description = "导出辅助帐总表", mode = SystemLog.SAVE_DB)
    @GetMapping("/exportGeneralLedger")
    @PermissionRequired(perms = "project:summaryReport:auxiliary,project:rdGeneralLedger:export")
    @ApiOperation(value = "导出辅助帐总表", notes = "导出辅助帐总表")
    public void exportGeneralLedger(Integer year, Boolean old) throws IOException, OwnerException {
        try (OutputStream out = outGeneralFile(MessageFormat.format("{0}辅助账总表{1}.xls", year, DateUtil.format(new Date(), "yyyy-MM-dd-HH:mm:ss")))) {
            projectService.exportGeneralLedger(year, old, getUserInfo(), out);
            out.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new OwnerException("导出失败");
        }
    }

    @GetMapping("getGeneralLedgerData")
    @PermissionRequired(perms = "project:rdGeneralLedger:view")
    @ApiOperation(value = "获取辅助帐总表数据", notes = "获取辅助帐总表数据")
    public Map<String, Object> getGeneralLedgerData(Integer year) throws OwnerException {
        return projectService.getGeneralLedgerData(year, getUserInfo());
    }

    @SystemLog(description = "设置研发费用", mode = SystemLog.SAVE_DB)
    @PostMapping("/setAdjustAmount")
    @PermissionRequired(perms = "project:data:agg")
    @ApiOperation(value = "设置研发费用", notes = "设置研发费用")
    public Boolean setAdjustAmount(@RequestBody @Validated SetRdAmountModel model) throws OwnerException {
        return projectService.setRdAmount(getUserInfo(), model);
    }

    @SystemLog(description = "设置项目预算", mode = SystemLog.SAVE_DB)
    @PostMapping("/setBudget")
    @PermissionRequired(perms = "project:report:budget:save")
    @ApiOperation(value = "设置项目预算", notes = "设置项目预算")
    public Boolean setBudget(@RequestBody @Validated List<BudgetInfoModel> models) throws OwnerException {
        return projectService.setBudget(getUserInfo(), models);
    }

    @SystemLog(description = "保存项目预算表", mode = SystemLog.SAVE_DB)
    @PostMapping("/setBudgetTable")
    @PermissionRequired(perms = "project:report:budget:save")
    @ApiOperation(value = "保存项目预算表", notes = "保存项目预算表")
    public Boolean setBudgetTable(@RequestBody @Validated List<SaveBudgetModel> models) throws OwnerException {
        return projectService.setBudgetTable(getUserInfo(), models);
    }

    @GetMapping("/exportRdAccountRiskReport")
    @PermissionRequired(perms = "project:rdAccountRiskReport:export")
    @ApiOperation(value = "导出核算风险报告", notes = "导出核算风险报告")
    public void exportRdAccountRiskReport(Integer year) throws Exception {
        UserInfo info = getUserInfo();
        String templatePath = Constant.TEMPLATE_DOCUMENT_DIR + "RdAccountRiskReport.html";
        Map<String, Object> dataMap = projectService.getRdAccountRiskData(year, info.getCompanyId());
        dataMap.put("ftlDocFileName", "核算风险报告");
        dataMap.put("companyName", info.getCompanyName());
        dataMap.put("year", year);
        dataMap.put("ftlPath", "/static/");
        OutputStream out = outGeneralFile("核算风险报告.pdf");
        PdfConfigModel pdfConfig = new PdfConfigModel(false, true,
                true, info.getCompanyName(), null, false);
        htmlToPdfUtils.htmlToPdf(templatePath, dataMap, out, pdfConfig);
    }

    @GetMapping("/previewRdAccountRiskReport")
    @PermissionRequired(perms = "project:rdAccountRiskReport:preview")
    @ApiOperation(value = "预览核算风险报告", notes = "预览核算风险报告")
    public String previewRdAccountRiskReport(@RequestParam("year") Integer year) throws Exception {
        UserInfo info = getUserInfo();
        String tempPath = Constant.TEMPLATE_DOCUMENT_DIR + "RdAccountRiskReport.html";
        Map<String, Object> dataMap = projectService.getRdAccountRiskData(year, info.getCompanyId());
        dataMap.put("companyName", info.getCompanyName());
        dataMap.put("year", year);
        dataMap.put("ftlPath", "/static/");
        return htmlToPdfUtils.generateHtml(tempPath, dataMap);
    }

    @GetMapping("/getRdAccountRiskData")
    @PermissionRequired(perms = "project:rdAccountRiskReport:view")
    @ApiOperation(value = "获取核算风险报告", notes = "获取核算风险报告")
    public Map<String, Object> getRdAccountRiskData(@RequestParam("year") Integer year) throws Exception {
        UserInfo info = getUserInfo();
        Map<String, Object> map = projectService.getRdAccountRiskData(year, info.getCompanyId());
        return map;
    }

    @PostMapping("/saveRdAccountRiskData")
    @PermissionRequired(perms = "project:rdAccountRiskReport:edit")
    @SystemLog(description = "编辑风险核算报告", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "编辑风险核算报告", notes = "编辑风险核算报告")
    public Boolean saveRdAccountRiskData(@RequestBody CDocumentModel model) throws Exception {
        UserInfo info = getUserInfo();
        return projectService.saveRdAccountData(model, info);
    }

    @GetMapping("/exportRdAccountMethod")
    @PermissionRequired(perms = "project:rdAccountMethod:export")
    @ApiOperation(value = "导出核算标准方法", notes = "导出核算标准方法")
    public void exportRdAccountMethod(Integer year) throws Exception {
        UserInfo info = getUserInfo();
        String templatePath = Constant.TEMPLATE_DOCUMENT_DIR + "RdAccountMethod.html";
        Map<String, Object> dataMap = projectService.getRdAccountData(year, info.getCompanyId());
        dataMap.put("ftlDocFileName", "核算标准方法");
        dataMap.put("companyName", info.getCompanyName());
        dataMap.put("ftlPath", "/static/");
        OutputStream out = outGeneralFile("核算标准方法.pdf");
        PdfConfigModel pdfConfig = new PdfConfigModel(false, true,
                true, info.getCompanyName(), null, false);
        htmlToPdfUtils.htmlToPdf(templatePath, dataMap, out, pdfConfig);
    }

    @GetMapping("/previewRdAccountMethod")
    @PermissionRequired(perms = "project:rdAccountMethod:preview")
    @ApiOperation(value = "预览核算标准方法", notes = "预览核算标准方法")
    public String previewRdAccountMethod(@RequestParam("year") Integer year) throws Exception {
        UserInfo info = getUserInfo();
        String tempPath = Constant.TEMPLATE_DOCUMENT_DIR + "RdAccountMethod.html";
        Map<String, Object> dataMap = projectService.getRdAccountData(year, info.getCompanyId());
        dataMap.put("companyName", info.getCompanyName());
        dataMap.put("ftlPath", "/static/");
        return htmlToPdfUtils.generateHtml(tempPath, dataMap);
    }

    @GetMapping("/getRdAccountData")
    @PermissionRequired(perms = "project:rdAccountMethod:view")
    @ApiOperation(value = "获取核算标准方法", notes = "获取核算标准方法")
    public Map<String, Object> getRdAccountData(@RequestParam("year") Integer year) throws Exception {
        UserInfo info = getUserInfo();
        Map<String, Object> map = projectService.getRdAccountData(year, info.getCompanyId());
        return map;
    }

    @PostMapping("/saveRdAccountData")
    @PermissionRequired(perms = "project:rdAccountMethod:edit")
    @SystemLog(description = "编辑核算标准方法", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "编辑核算标准方法", notes = "编辑核算标准方法")
    public Boolean saveRdAccountData(@RequestBody CDocumentModel model) throws Exception {
        UserInfo info = getUserInfo();
        return projectService.saveRdAccountData(model, info);
    }

    @GetMapping("/getAnnualData")
    @PermissionRequired(perms = "project:meeting:search")
    @ApiOperation(value = "获取年度会议纪要统计数据", notes = "获取年度会议纪要统计数据")
    public List<MeetingCountModel> getAnnualData(@RequestParam("year") Integer year, Date month) throws Exception {
        UserInfo info = getUserInfo();
        return projectService.getAnnualData(year, info.getCompanyId(), month);
    }

    @GetMapping("/getMeetingFromList")
    @PermissionRequired(perms = "project:meeting:searchData")
    @ApiOperation(value = "获取公司当年月份会议纪要列表数据", notes = "获取公司当年月份会议纪要列表数据")
    public List<MeetingFromModel> getMeetingFromList(@RequestParam("month") Date month) throws Exception {
        UserInfo info = getUserInfo();
        return projectService.getMeetingFromList(month, info.getCompanyId());
    }

    @PostMapping("delMeetingFile")
    @PermissionRequired(perms = "project:meeting:delFile")
    @SystemLog(description = "删除会议纪要附件", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "删除会议纪要附件", notes = "删除会议纪要附件")
    public Boolean delMeetingFile(@RequestBody BatchDeleteModel model) {
        return projectService.delMeetingFile(model.getIds());
    }

    @PostMapping("/uploadFile")
    @PermissionRequired(perms = "project:meeting:upload")
    @ApiOperation(value = "上传文件", notes = "上传文件", response = String.class)
    @SystemLog(description = "上传文件", mode = SystemLog.SAVE_DB)
    public Boolean uploadFile(@RequestParam("file") MultipartFile file, DocFileMeetingEntity meetingEntity) throws OwnerException {
        UserInfo info = getUserInfo();
        Date date = new Date();
        String dateStr = date.getTime() + "";
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getResourcePath(),
                "static/image/meetingFrom/") + info.getCompanyId() + "/" + meetingEntity.getProjectId() + "/" + meetingEntity.getDocFileId() + "/";
        File folder = new File(tempPath);
        if (!folder.exists() && !folder.isDirectory()) {
            logger.error("文件夹路径不存在，创建路径:" + tempPath);
            folder.mkdirs();
        }
        File tempFile = new File(folder.getAbsolutePath() + "/" + dateStr + file.getOriginalFilename());
        try {
            file.transferTo(tempFile);
            DocFileMeetingEntity entity = new DocFileMeetingEntity();
            BeanUtil.copyProperties(meetingEntity, entity);
            entity.create(info.getUserId(), info.getMsUserId(), date);
            entity.setCompanyId(info.getCompanyId());
            String[] strArray = file.getOriginalFilename().split("\\.");
            int suffixIndex = strArray.length - 1;
            entity.setFileName(entity.getFileName() + "." + strArray[suffixIndex]);
            entity.setFilePath("/static/image/meetingFrom/" + info.getCompanyId() + "/" + meetingEntity.getProjectId() + "/" + meetingEntity.getDocFileId() + "/" + dateStr + file.getOriginalFilename());
            projectService.insertFile(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 预览
     *
     * @param filePath
     * @return
     * @throws OwnerException
     */
    @GetMapping("/preview")
    @PermissionRequired(perms = "project:meeting:preview")
    @ApiOperation(value = "预览", notes = "预览", response = String.class)
    public void preview(String filePath) throws Exception {
        // // TODO: 22/02/21 zdf 切换该请求到documentController.appendixPreview
        documentService.preview(rsConfig.getUploadConfig().getResourcePath(),filePath, outGeneralFile(filePath));
       //  projectService.preview(rsConfig.getUploadConfig().getResourcePath(), filePath, outGeneralFile(filePath));
    }

    @GetMapping("/downloadFile")
    @PermissionRequired(perms = "project:meeting:download")
    @SystemLog(description = "下载附件", mode = SystemLog.SAVE_DB)
    @ApiOperation(notes = "下载附件", value = "下载附件")
    public void downloadFile(String path, String fileName) throws OwnerException, IOException {
        if (StringUtils.isEmpty(path)) {
            throw new OwnerException("文件不存在，下载失败，请稍后再试。");
        }
        download(rsConfig.getUploadConfig().getResourcePath() + path, fileName);
    }


    /**
     * @param projectId 项目id
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "获取项目变更纪录", mode = SystemLog.SAVE_DB)
    @GetMapping("/getProjectChangeRecord")
    @ApiOperation(value = "获取项目变更纪录", notes = "获取项目变更纪录")
    @PermissionRequired(perms = "project:report:changeLog:view")
    public List<ChangeRecordModel> getProjectChangeRecord(@Param("projectId")Integer projectId) throws OwnerException {
        return projectService.getProjectChangeRecord(projectId, getUserInfo());
    }

    /**
     * @param changeRecordModel 保存内容
     * @return 当前记录id
     * @throws OwnerException 异常
     */
    @SystemLog(description = "保存项目变更记录", mode = SystemLog.SAVE_DB)
    @PostMapping("/saveChangeRecord")
    @ApiOperation(value = "保存项目变更记录", notes = "保存项目变更记录")
    @PermissionRequired(perms = "project:report:changeLog:add")
    public Integer saveProjectChangeRecord(@RequestBody @Validated ChangeRecordModel changeRecordModel) throws OwnerException {
        return projectService.saveProjectChangeRecord(changeRecordModel, getUserInfo());
    }

    /**
     * @param changeRecordModel 删除
     * @return 是否成功
     * @throws OwnerException 异常
     */
    @SystemLog(description = "删除项目变更记录", mode = SystemLog.SAVE_DB)
    @PostMapping("/delChangeRecord")
    @ApiOperation(value = "删除项目变更记录", notes = "删除项目变更记录")
    @PermissionRequired(perms = "project:report:changeLog:del")
    public String delProjectChangeRecord(@RequestBody @Validated ChangeRecordModel changeRecordModel) throws OwnerException {
        return projectService.delProjectChangeRecord(changeRecordModel, getUserInfo());
    }
}
