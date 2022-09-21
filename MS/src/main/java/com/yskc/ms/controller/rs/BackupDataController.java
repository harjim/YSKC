package com.yskc.ms.controller.rs;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ZipUtil;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.PdfConfigModel;
import com.yskc.common.utils.HtmlToPdfUtils;
import com.yskc.ms.config.Constant;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.dao.rs.CompanyDao;
import com.yskc.ms.entity.rs.ProjectEntity;
import com.yskc.ms.entity.rs.RsCompanyEntity;
import com.yskc.ms.entity.rs.SysDocumentEntity;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.rs.*;
import com.yskc.ms.models.rs.summary.BackupDataListModel;
import com.yskc.ms.service.rs.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: hck
 * @DateTime: 2021/5/12 10:53
 * @Description:备查资料接口
 */
@Api(tags = "备查资料接口", value = "备查资料接口")
@RestController
@RequestMapping("/api/backupData")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
public class BackupDataController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ProjectDocFileDataService projectDocFileDataService;
    @Autowired
    private MsConfig msConfig;
    @Autowired
    private RsProjectService projectService;
    @Autowired
    private HtmlToPdfUtils htmlToPdfUtils;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private SummaryService summaryService;
    @Autowired
    private InitMemberService initMemberService;
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private SysDocumentService sysDocumentService;


    @GetMapping("/getDocInfo")
    //@PermissionRequired(perms = "project:finalExpenseReport:view,project:forFutureReferenceData:view,rdvoucher:depreciationCost:edit")
    @ApiOperation(value = "获取过程文件", notes = "获取过程文件")
    public List<DocFileInfoModel> getDocInfo(@RequestParam(value = "projectId", required = true) Integer projectId, @RequestParam(value = "year", required = true) Integer year) throws OwnerException {
        return projectDocFileDataService.getDocInfo(getUserInfo(), projectId, year);
    }

//    @GetMapping("/getAuditStatus")
//    @ApiOperation(value = "获取备查资料审核状态", notes = "获取备查资料审核状态")
//    public Map<Integer, Integer> getAuditStatus(Integer projectId) {
//        return projectDocFileDataService.getAuditStatus(projectId);
//    }

    @GetMapping("/getNewReports")
    @ApiOperation(value = "获取查新报告列表", notes = "获取查新报告列表")
    public PageModel<List<NewReportsModel>> getNewReports(QueryReportsModel model) {
        return projectDocFileDataService.getNewReports(model);
    }

    @GetMapping("/exportBackupData")
    //@PermissionRequired(perms = "project:forFutureReferenceData:export")
    @SystemLog(description = "导出选择的备查资料", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "导出选择的备查资料", notes = "导出选择的备查资料")
    @ResponseBody
    public void exportChooseData(Integer projectId, @RequestParam(value = "chooseFiles", required = false) List<Integer> chooseFiles, @RequestParam(value = "merge", defaultValue = "false") Boolean merge,
                                 Integer year, Boolean importFooterName, Boolean cover, Boolean pageNum, Boolean budgetDetail, Boolean utility, Boolean header) throws Exception {

        Boolean hasUtility = false;
        UserInfo info = getUserInfo();
        ProjectEntity project = projectService.getProject(projectId);
        RsCompanyEntity companyEntity = projectService.getCompanyById(project.getCompanyId());
        List<Map<String, Object>> dataMaps = projectDocFileDataService.getDataList(chooseFiles, project, info, year, importFooterName, cover, companyEntity,budgetDetail, utility);
        String logo = StringUtils.isEmpty(companyEntity.getLogo()) ? null : Constant.IMAGES_DIR + companyEntity.getLogo();
        String format = "{0}/backupData/{1,number,#}/{2}";
        Integer fileNum = 0;
        PdfConfigModel pdfConfig = new PdfConfigModel(header, header, pageNum == null || pageNum, companyEntity.getCompanyName(), logo, true);
        List<String> htmlList = new ArrayList<>();
        for (Map<String, Object> map : dataMaps) {
            String fileName = MessageFormat.format("{0}.{1}.pdf", map.get("fileIndex"), map.get("ftlDocFileName").toString());
//                    map.get("fileIndex")+"."+map.get("ftlDocFileName").toString() + ".pdf";
            String templateName = map.get("exportTemplateFilePath").toString();
            if (templateName.equals(Constant.HTML_TEMPLATE_DIR + "UtilityReportForm.html")) {
                hasUtility = true;
            }
            if (null == merge?false:merge) {
                htmlList.add(htmlToPdfUtils.generateHtml(templateName, map));
            } else {
                try {
                    String filePath = MessageFormat.format(format, msConfig.getUploadConfig().getDocPath(), projectId, fileName);
                    File file = new File(filePath);
                    if (file.exists()) {
                        file.delete();
                    }
                    OutputStream out = new FileOutputStream(filePath);

                    htmlToPdfUtils.htmlToPdf(templateName, map, out, pdfConfig);
                    fileNum += 1;
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        if (chooseFiles.contains(7)) {
            List<SysDocumentEntity> newReports = sysDocumentService.getReportByProject(projectId);
            if (!CollectionUtils.isEmpty(newReports)) {
                if (null == merge?false:merge) {
                    String templateName = Constant.HTML_TEMPLATE_DIR + "NewReportForm.html";
                    Map<String, Object> map = new HashMap<>();
                    map.put("newReports", newReports);
                    if (hasUtility) {
                        int size = htmlList.size();
                        int index;
                        if (size <= 1) {
                            index = 0;
                        } else {
                            index = size - 1;
                        }
                        htmlList.add(index, htmlToPdfUtils.generateHtml(templateName, map));
                    } else {
                        htmlList.add(htmlToPdfUtils.generateHtml(templateName, map));
                    }
                } else {
                    for (SysDocumentEntity document : newReports) {
                        File file = new File(MessageFormat.format("{0}/{1}", msConfig.getUploadConfig().getDocPath(), document.getFilePath()));
                        if (!file.exists()) {
                            continue;
                        }
                        String docName = "7." + document.getFileName();
                        String filePath = MessageFormat.format(format, msConfig.getUploadConfig().getDocPath(), projectId, docName);
                        File reportFile = new File(filePath);
                        FileUtil.copy(file, reportFile, false);
                    }
                    fileNum += 1;
                }
            }
        }
        OutputStream outputStream;
        InputStream is;
        if (!CollectionUtils.isEmpty(htmlList)) {
            String outFileName = MessageFormat.format("{0}-{1}-{2}-({3})备查资料.pdf", companyEntity.getCompanyName(), project.getRdTitle(), project.getPname(), fileNum - 1);
            String filePath = MessageFormat.format(format, msConfig.getUploadConfig().getDocPath(), projectId, outFileName);
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
            OutputStream out = new FileOutputStream(filePath);
            htmlToPdfUtils.htmlToPdf(htmlList, out, pdfConfig);
            out.flush();
            out.close();
            outputStream = outGeneralFile(outFileName);
            is = new FileInputStream(file);
        } else {
            String sourceFile = MessageFormat.format("{0}/backupData/{1,number,#}", msConfig.getUploadConfig().getDocPath(), projectId);
            String outFileName = MessageFormat.format("{0}-{1}-{2}-({3})备查资料.zip", companyEntity.getCompanyName(), project.getRdTitle(), project.getPname(), fileNum - 1);
            outputStream = outGeneralFile(outFileName);
            is = new FileInputStream(ZipUtil.zip(sourceFile));
        }
        IoUtil.copy(is, outputStream);
        is.close();
        outputStream.flush();
        outputStream.close();


        /*cover = cover == null || cover;
        pageNum = pageNum == null || pageNum;
        UserInfo info = getUserInfo();
        if (CollectionUtils.isEmpty(chooseFiles)) {
            chooseFiles = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 6, 7, 8));
        }
        if (!utility){
            Iterator<Integer> iterator = chooseFiles.iterator();
            while(iterator.hasNext()){
                int i = iterator.next();
                if(i == 8){
                    iterator.remove();
                }
            }
        }
        List<Map<String, Object>> dataMaps = projectDocFileDataService.exportChooseData(projectId, info, chooseFiles, year,importFooterName,budgetDetail);
        if (!chooseFiles.contains(7) && CollectionUtils.isEmpty(dataMaps)) {
            throw new OwnerException("该项目不存在可导出的备查资料！");
        }
        RsCompanyEntity companyEntity = projectService.getCompanyByProject(projectId);
        ProjectEntity project = projectService.getProject(projectId);

        String companyName = companyDao.getInfo(project.getCompanyId()).getCompanyName();
        String durationTime = DateUtil.format(project.getBeginDate(),"yyyy.MM") + "~" + DateUtil.format(project.getEndDate(),"yyyy.MM");
        String logo = StringUtils.isEmpty(companyEntity.getLogo()) ? null : Constant.IMAGES_DIR + companyEntity.getLogo();
        String format = "{0}/backupData/{1,number,#}/{2}";
        File parentFile = new File(msConfig.getUploadConfig().getDocPath() + "/backupData/" + projectId);
        if (parentFile.exists()) {
            FileUtil.del(parentFile);
        }
        if(cover){
            //添加封面
            Map<String, Object> coverMap = new HashMap<>();
            String path = Constant.HTML_TEMPLATE_DIR + "BackupDataCover.html";
            coverMap.put("exportTemplateFilePath", path);
            coverMap.put("ftlPath", "/static/");
            coverMap.put("ftlDocFileName", "项目资料清单");
            coverMap.put("fileIndex", "0-3");
            coverMap.put("pname", project.getPname());
            coverMap.put("year", year);
            coverMap.put("rdTitle", project.getRdTitle());
            coverMap.put("companyName", companyEntity.getCompanyName());
            coverMap.put("isEndYear", year.equals(project.getEndYear()));
            dataMaps.add(coverMap);
            //添加封面(封面)
            Map<String, Object> coverMap2 = new HashMap<>();
            String path2 = Constant.HTML_TEMPLATE_DIR + "ReportCover.html";
            coverMap2.put("exportTemplateFilePath", path2);
            coverMap2.put("ftlPath", "/static/");
            coverMap2.put("ftlDocFileName", "封面");
            coverMap2.put("fileIndex", "0-1");
            coverMap2.put("durationTime", durationTime);
            if (Objects.equals(project.getMasterENumber(), null)) {
                coverMap2.put("ename", "无");
            } else {
                EmployeeModel employeeByenumber = employeeService.getEmployeeByenumber(project.getCompanyId(), project.getMasterENumber());
                if (!Objects.equals(employeeByenumber, null)) {
                    coverMap2.put("ename", employeeByenumber.getEname());
                } else {
                    coverMap2.put("ename", "无");
                }
            }
            coverMap2.put("pname", project.getPname());
            coverMap2.put("companyName", companyName);
            coverMap2.put("rdTitle", project.getRdTitle());
            dataMaps.add(coverMap2);
            //添加封面(项目汇总表)
            Map<String, Object> coverMap3 = new HashMap<>();
            String path3 = Constant.HTML_TEMPLATE_DIR + "AnnualSummaryForm.html";
            coverMap3.put("exportTemplateFilePath", path3);
            coverMap3.put("ftlPath", "/static/");
            coverMap3.put("ftlDocFileName", "项目年度汇总表");
            coverMap3.put("fileIndex", "0-2");
            Date now = new Date();
            coverMap3.put("projectId", projectId);
            coverMap3.put("year", year);
            List<RsProjectListModel> allProject = projectService.getAllProject(project.getCompanyId(), year);
            List<ProjectCollectModel> list = new ArrayList<>();
            BigDecimal bigDecimal = new BigDecimal("0");
            List<Integer> projectIds = allProject.stream().map(e -> e.getId()).collect((Collectors.toList()));
            Map<Integer, Map<String,BigDecimal>> bigDecimalMap = summaryService.getFundByYearAndProId(projectIds, year);
            Map<Integer, Map<String,Long>> memberMap = initMemberService.getMemberByProAndYear(projectIds, year);
            ProjectTypeEnum[] values = ProjectTypeEnum.values();
            Map<Integer, String> typeMap = new HashMap<>();
            for (ProjectTypeEnum value : values) {
                typeMap.put(value.getType(), value.getTypeName());
            }

            BigDecimal divisor = new BigDecimal("1000");
            for (RsProjectListModel projectListModel : allProject) {
                ProjectCollectModel projectCollectModel = new ProjectCollectModel();
                projectCollectModel.setProjectName(projectListModel.getPname());
                projectCollectModel.setDurationTime(DateUtil.format(projectListModel.getBeginDate(),"yyyy.MM") + "-" + DateUtil.format(projectListModel.getEndDate(),"yyyy.MM"));

                if (memberMap.containsKey(projectListModel.getId())) {
                    projectCollectModel.setNumbers(memberMap.get(projectListModel.getId()).get("members"));
                }else {
                    projectCollectModel.setNumbers(0L);
                }

                if (bigDecimalMap.containsKey(projectListModel.getId())) {

                    *//**
         * 如果rdFunds等于0则不保留四位小数
         *//*
                    BigDecimal rdFunds = bigDecimalMap.get(projectListModel.getId()).get("rdFunds");
                    if (rdFunds.compareTo(BigDecimal.ZERO) != 0) {
                        projectCollectModel.setEstimateExpense(rdFunds.divide(divisor).setScale(4, RoundingMode.HALF_UP).toString());
                    } else {
                        projectCollectModel.setEstimateExpense("0");
                    }

                    bigDecimal = bigDecimal.add(rdFunds);
                }else {
                    projectCollectModel.setEstimateExpense(new BigDecimal("0").toString());
                }
                if (Objects.equals(projectListModel.getId(), projectId)) {
                    projectCollectModel.setPresent(true);
                } else {
                    projectCollectModel.setPresent(false);
                }

                //为数值映射枚举对应的值
                if (typeMap.containsKey(projectListModel.getFormula())) {
                    projectCollectModel.setType(typeMap.get(projectListModel.getFormula()));
                } else {
                    projectCollectModel.setType(ProjectTypeEnum.OTHER.getTypeName());
                }

                if (projectListModel.getEndDate().compareTo(now) <= 0 && projectListModel.getEndYear() <= year) {
                    projectCollectModel.setStatus("完成");
                } else {
                    projectCollectModel.setStatus("未完成");
                }
                projectCollectModel.setRdTitle(projectListModel.getRdTitle());
                list.add(projectCollectModel);
            }
            coverMap3.put("companyName", companyName);
            coverMap3.put("projects", list);

            *//**
         * 如果summarySum不为0则进行四舍五入保留四位小数
         *//*
            BigDecimal summarySum = bigDecimal.divide(divisor);
            if (summarySum.compareTo(BigDecimal.ZERO) != 0) {
                coverMap3.put("summarySum", summarySum.setScale(4, RoundingMode.HALF_UP).toString());
            } else {
                coverMap3.put("summarySum", 0);
            }

            dataMaps.add(coverMap3);
        }

        Integer fileNum = 0;
        parentFile.mkdirs();
        PdfConfigModel pdfConfig = new PdfConfigModel(true, true, pageNum, companyEntity.getCompanyName(), logo,false);
        for (Map<String, Object> map : dataMaps) {
            String fileName = MessageFormat.format("{0}.{1}.pdf", map.get("fileIndex"), map.get("ftlDocFileName").toString());
//            String fileName = map.get("ftlDocFileName").toString() + ".pdf";
            try {
                String filePath = MessageFormat.format(format, msConfig.getUploadConfig().getDocPath(), projectId, fileName);
                File file = new File(filePath);
                if (file.exists()) {
                    file.delete();
                }
                OutputStream out = new FileOutputStream(filePath);
                String templateName = map.get("exportTemplateFilePath").toString();
                htmlToPdfUtils.htmlToPdf(templateName, map, out, pdfConfig);
                fileNum += 1;
                out.flush();
                out.close();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        if (chooseFiles.contains(7)) {
            List<SysDocumentEntity> newReports = projectService.getReportByProject(projectId);
            if (!CollectionUtils.isEmpty(newReports)) {
                for (SysDocumentEntity document : newReports) {
                    File file = new File(MessageFormat.format("{0}/{1}", msConfig.getUploadConfig().getDocPath(), document.getFilePath()));
                    if (!file.exists()) {
                        continue;
                    }
                    String docName = "7." + document.getFileName();
                    String filePath = MessageFormat.format(format, msConfig.getUploadConfig().getDocPath(), projectId, docName);
                    File reportFile = new File(filePath);
                    FileUtil.copy(file, reportFile, false);
                }
                fileNum += 1;
            }
        }
        String sourceFile = MessageFormat.format("{0}/backupData/{1,number,#}", msConfig.getUploadConfig().getDocPath(), projectId);
        String outFileName = MessageFormat.format("{0}-{1}-{2}-({3})备查资料.zip", companyEntity.getCompanyName(), project.getRdTitle(), project.getPname(), fileNum - 1);
        OutputStream out = outGeneralFile(outFileName);
        InputStream is = new FileInputStream(ZipUtil.zip(sourceFile));
        IoUtil.copy(is, out);
        is.close();
        out.flush();
        out.close();*/
    }

    /**
     * @param query
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getList")
    //@PermissionRequired(perms = "project:report:member:search")
    @ApiOperation(value = "获取项目成员列表", tags = "获取项目成员列表")
    public PageModel<List<InitMemberModel>> getList(QueryProjectInitMemberModel query) throws OwnerException {
        return projectService.getInitmemberList(query);
    }

    @GetMapping("/getProjectSummary")
    @ApiOperation(value = "获取项目的归集数据", notes = "获取项目的归集数据")
    //@PermissionRequired(perms = "project:forFutureReferenceData:view")
    public List<Map<String, Object>> getProjectSummary(Integer projectId, Integer year) throws OwnerException {
        return projectService.getProjectSummary(projectId, year);
    }

    @GetMapping("/getProjectReports")
    @ApiOperation(value = "获取项目查新报告列表", notes = "获取项目查新报告列表")
    public List<NewReportsModel> getProjectReports(Integer projectId) {
        return projectDocFileDataService.getProjectReports(projectId);
    }


    @GetMapping("/previewReport")
    // @SystemLog(description = "预览获取过程文件data", mode = SystemLog.SAVE_DB)
    //@PermissionRequired(perms = "project:doc:preview")
    @ApiOperation(value = "预览获取项目组编制data", notes = "预览获取项目组编制data")
    public String previewReport(DataModel dataModel) throws Exception {
        String template = "ProjectBaseSituation";
        String tempPath = MessageFormat.format(Constant.HTML_TEMPLATE_DIR + "{0}.html", template);
        Map<String, Object> dataMap = projectDocFileDataService.getReportData(dataModel.getProjectId(), getUserInfo(), dataModel.getYear());
        return htmlToPdfUtils.generateHtml(tempPath, dataMap);
    }

    @GetMapping("/getBackupData")
    //@PermissionRequired(perms = "project:finalExpenseReport:view,project:forFutureReferenceData:view,rdvoucher:depreciationCost:edit")
    @ApiOperation(value = "获取备查资料列表", notes = "获取备查资料列表")
    public List<BackupDataListModel> getBackupData(Integer projectId, Integer year) {
        return projectDocFileDataService.getBackupData(projectId, year);
    }
}
