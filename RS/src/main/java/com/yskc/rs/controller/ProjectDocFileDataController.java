package com.yskc.rs.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ZipUtil;
import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.PdfConfigModel;
import com.yskc.common.utils.HtmlToPdfUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.SysDocumentEntity;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.docFile.*;
import com.yskc.rs.models.document.DataModel;
import com.yskc.rs.models.employee.EmployeeAutographModel;
import com.yskc.rs.models.workshop.BackupDataModel;
import com.yskc.rs.service.DocFileService;
import com.yskc.rs.service.ProjectDocFileDataService;
import com.yskc.rs.service.ProjectService;
import com.yskc.rs.service.SysDocumentService;
import com.yskc.rs.utils.YsWordUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.MessageFormat;
import java.util.*;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: zhangdingfu
 * @CreateTime: 2020-03-26 10:10
 * @Description: 项目文档数据接口
 */
@RestController
@Api(tags = "项目文档数据接口", value = "项目文档数据接口")
@CrossOrigin(origins = "*", allowedHeaders = "", allowCredentials = "true", methods = {})
@RequestMapping("/api/projectDocFileData")
public class ProjectDocFileDataController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ProjectDocFileDataService projectDocFileDataService;
    @Autowired
    private RsConfig rsConfig;
    @Autowired
    private HtmlToPdfUtils htmlToPdfUtils;
    @Autowired
    private DocFileService docFileService;
    @Autowired
    private SysDocumentService sysDocumentService;
    @Autowired
    private ProjectService projectService;

    @SystemLog(description = "获取项目文档数据")
    @GetMapping("/getData")
    //@PermissionRequired(perms = "project:doc:view")
    @ApiOperation(notes = "获取项目文档数据", value = "获取项目文档数据")
    public DocFileDataModel getData(Integer pDocFileId) throws OwnerException {
        return projectDocFileDataService.getData(pDocFileId, getUserInfo(), true);
    }

    @SystemLog(description = "获取项目文档footer")
    @GetMapping("/getDocFooter")
    //@PermissionRequired(perms = "project:doc:view")
    @ApiOperation(notes = "获取项目文档footer", value = "获取项目文档footer")
    public Map<String, EmployeeAutographModel> getDocFooter(Integer projectId, Integer year) {
        return projectDocFileDataService.getDocFooter(projectId, year);
    }


    @SystemLog(description = "保存项目文档数据", mode = SystemLog.SAVE_DB)
    @PostMapping("/saveData")
    @PermissionRequired(perms = "project:doc:edit,project:finalExpenseReport:edit,rdvoucher:depreciationCost:edit,project:forFutureReferenceData:edit")
    @ApiOperation(notes = "保存项目文档数据", value = "保存项目文档数据")
    public Boolean saveData(@RequestBody @Validated DocFileDataSaveModel dataModel) throws OwnerException {
        return projectDocFileDataService.saveData(getUserInfo(), dataModel);
    }

    @GetMapping("/getMemberStr")
    @ApiOperation(notes = "获取项目成员段（limit20）", value = "获取项目成员段（limit20）")
    public String getMemberStr(Integer projectId, Integer pDocFileId) {
        return projectDocFileDataService.getMemberStr(projectId, pDocFileId);
    }

    @GetMapping("/getMeetingData")
    @ApiOperation(notes = "获取会议纪要数据", value = "获取会议纪要数据")
    public Map<String,Object> getMeetingData(DataModel dataModel) throws Exception {
        return projectDocFileDataService.getMeetingData(dataModel.getpDocFileIds(), getUserInfo().getCompanyId(), dataModel.getYear());
    }

    /**
     * @param dataModel
     * @throws Exception
     */
    @GetMapping("/exportWord")
    @PermissionRequired(perms = "project:doc:export")
    @SystemLog(description = "阶段文件导出word报告", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "阶段文件导出word报告", notes = "阶段文件导出word报告")
    public void exportWord(DataModel dataModel) throws Exception {
        OutputStream out = outGeneralFile(dataModel.getDocName() + ".docx");
        UserInfo userInfo = this.getUserInfo();
        Map<String, Object> dataMap = projectDocFileDataService.exportWord(dataModel, userInfo, true);
        String path = dataMap.get("exportTemplateFilePath").toString();
        YsWordUtils.generalWordReport(dataMap, path, (workBook) -> {
            try {
                workBook.write(out);
                out.flush();
                out.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        });
    }

    /**
     * 导出项目所有过程文件
     *
     * @param model
     * @throws Exception
     */
    @GetMapping("/exportAllDoc")
    @PermissionRequired(perms = "project:doc:export")
    @SystemLog(description = "导出项目所有过程文件", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "导出项目所有过程文件", notes = "导出项目所有过程文件")
    @ResponseBody
    public void exportAllDoc(ExportDocFileModel model) throws Exception {
        if (model == null) {
            throw new OwnerException("请选择要导出文件的项目");
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
                logger.error(e.getMessage(), e);
            }
        }
        try {
            OutputStream out = outGeneralFile("过程文件导出.pdf");
            String logo = StringUtils.isEmpty(info.getCompanyLogoPath()) ? null : Constant.IMAGES_DIR + info.getCompanyLogoPath();
            PdfConfigModel pdfConfig = new PdfConfigModel(model.getHeader(),model.getHeader(),model.getPageNum(),info.getCompanyName(), logo,model.getCatalogue(),model.getCover());
            htmlToPdfUtils.htmlToPdf(htmlList, out, pdfConfig);
            out.flush();
            out.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @GetMapping("/exportBackupData")
    @PermissionRequired(perms = "project:forFutureReferenceData:export")
    @SystemLog(description = "导出选择的备查资料", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "导出选择的备查资料", notes = "导出选择的备查资料")
    @ResponseBody
    public void exportChooseData(Integer projectId, @RequestParam(value = "chooseFiles", required = false) List<Integer> chooseFiles,Boolean merge,
                                 Integer year, Boolean importFooterName, Boolean cover, Boolean pageNum,Boolean budgetDetail,Boolean utility,Boolean header) throws Exception {
        Boolean hasUtility = false;
        UserInfo info = getUserInfo();
        ProjectEntity project = projectService.getProject(projectId);
        List<Map<String, Object>> dataMaps = projectDocFileDataService.getDataList(chooseFiles, project, info, year, importFooterName, cover, budgetDetail, utility);
        String logo = StringUtils.isEmpty(info.getCompanyLogoPath()) ? null : Constant.IMAGES_DIR + info.getCompanyLogoPath();
        String format = "{0}/backupData/{1,number,#}/{2}";
        Integer fileNum = 0;
        PdfConfigModel pdfConfig = new PdfConfigModel(header, header, pageNum == null || pageNum, info.getCompanyName(), logo, true);
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
                    String filePath = MessageFormat.format(format, rsConfig.getUploadConfig().getDocPath(), projectId, fileName);
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
                    Map<String,Object> map = new HashMap<>();
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
                        File file = new File(MessageFormat.format("{0}/{1}", rsConfig.getUploadConfig().getDocPath(), document.getFilePath()));
                        if (!file.exists()) {
                            continue;
                        }
                        String docName = "7." + document.getFileName();
                        String filePath = MessageFormat.format(format, rsConfig.getUploadConfig().getDocPath(), projectId, docName);
                        File reportFile = new File(filePath);
                        FileUtil.copy(file, reportFile, false);
                    }
                    fileNum += 1;
                }
            }
        }
        OutputStream outputStream ;
        InputStream is ;
        if (!CollectionUtils.isEmpty(htmlList)) {
            String outFileName = MessageFormat.format("{0}-{1}-{2}-({3})备查资料.pdf", info.getCompanyName(), project.getRdTitle(), project.getPname(), fileNum - 1);
            String filePath = MessageFormat.format(format, rsConfig.getUploadConfig().getDocPath(), projectId, outFileName);
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
            String sourceFile = MessageFormat.format("{0}/backupData/{1,number,#}", rsConfig.getUploadConfig().getDocPath(), projectId);
            String outFileName = MessageFormat.format("{0}-{1}-{2}-({3})备查资料.zip", info.getCompanyName(), project.getRdTitle(), project.getPname(), fileNum - 1);
            outputStream = outGeneralFile(outFileName);
            is = new FileInputStream(ZipUtil.zip(sourceFile));
        }
        IoUtil.copy(is, outputStream);
        is.close();
        outputStream.flush();
        outputStream.close();
    }

//    @GetMapping("/exportBackupData")
//    //@PermissionRequired(perms = "project:doc:export")
//    @SystemLog(description = "导出备查资料", mode = SystemLog.SAVE_DB)
//    @ApiOperation(value = "导出备查资料", notes = "导出备查资料")
//    @ResponseBody
//    public void exportBackupData(Integer projectId) throws Exception {
//        UserInfo info = getUserInfo();
//        List<Map<String, Object>> htmlMaps = projectDocFileDataService.exportBackupData(projectId, info);
//        if (CollectionUtils.isEmpty(htmlMaps)) {
//            throw new OwnerException("该项目不存在可导出的备查资料！");
//        }
//        List<String> htmlList = new ArrayList<>();
//        for (Map<String, Object> map : htmlMaps) {
//            String path = map.get("exportTemplateFilePath").toString();
//            try {
//                String str = htmlToPdfUtils.generateHtml(path, map);
//                htmlList.add(str);
//            } catch (Exception e) {
//            }
//        }
//        try {
//            OutputStream out = outGeneralFile("备查资料.pdf");
//            String logo = StringUtils.isEmpty(info.getCompanyLogoPath()) ? null : Constant.IMAGES_DIR + info.getCompanyLogoPath();
//            htmlToPdfUtils.htmlToPdf(htmlList, info.getCompanyName(), logo, out);
//            out.flush();
//            out.close();
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//
//        }
//    }

    @PostMapping("/uploadOrgImg")
    @ApiOperation(value = "上传组织架构图片", notes = "上传组织架构图片", response = String.class)
    @SystemLog(description = "上传组织架构图片", mode = SystemLog.SAVE_DB)
    public Boolean uploadFile(@RequestParam(value = "file",required = false) MultipartFile file, Integer beginYear) throws OwnerException, IOException {
        UserInfo userInfo = getUserInfo();
        String fileName = MessageFormat.format("org_{0,number,#}_{1,number,#}.png", userInfo.getCompanyId(), beginYear);
        String tempPath = MessageFormat.format("{0}/static/images/{1,number,#}/org", rsConfig.getUploadConfig().getResourcePath(), userInfo.getCompanyId());
        File folder = new File(tempPath);
        if (!folder.exists() && !folder.isDirectory()) {
            logger.warn("文件夹路径不存在，创建路径:" + tempPath);
            folder.mkdirs();
        }
        String orgUrl = tempPath + "/" + fileName;
        File existFile = new File(orgUrl);
        if(null==file){
            existFile.delete();
            return true;
        }
        if (existFile.exists()) {
            existFile.delete();
        }
        File tempFile = new File(orgUrl);
        try {
            file.transferTo(tempFile.getAbsoluteFile());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new OwnerException("生成组织架构图片失败！");
        }
        return true;
    }


    /**
     * 上传文件
     *
     * @return
     * @throws OwnerException
     * @throws OwnerException
     */
    @PostMapping("/upload")
    @SystemLog(description = "上传附件", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "上传附件", notes = "上传附件", response = boolean.class)
    public Map<String, String> upload(@RequestParam("file") MultipartFile[] multipartFile, @RequestParam("dir") String dir) throws OwnerException {
        String expertPath = rsConfig.getUploadConfig().getMsImportPath();
        Map<String, String> result = new LinkedHashMap<>();
        for (MultipartFile file : multipartFile) {
            String fileName = System.currentTimeMillis() + file.getOriginalFilename();
            String filePath = dir + fileName;
            File tempFile = new File(expertPath + filePath);
            if (!tempFile.getParentFile().exists()) {
                tempFile.getParentFile().mkdirs();
            }
            try {
                file.transferTo(tempFile);
                result.put("filePath", filePath);
                result.put("fileName", file.getOriginalFilename());
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
                throw new OwnerException("上传失败");
            }
        }
        return result;
    }

    @SystemLog(description = "下载附件", mode = SystemLog.SAVE_DB)
    @GetMapping("/downloadFile")
    @ApiOperation(notes = "下载附件", value = "下载附件")
    public void downloadFile(String path, String fileName) throws OwnerException, IOException {
        if (StringUtils.isEmpty(path)) {
            throw new OwnerException("文件不存在，下载失败，请稍后再试。");
        }
        download(rsConfig.getUploadConfig().getMsImportPath() + path, fileName);
    }

    /**
     * 预览获取过程文件data
     *
     * @param dataModel
     * @throws Exception
     */
    @GetMapping("/previewFile")
    // @SystemLog(description = "预览获取过程文件data", mode = SystemLog.SAVE_DB)
    //@PermissionRequired(perms = "project:doc:preview")
    @ApiOperation(value = "预览获取过程文件data", notes = "预览获取过程文件data")
    public String previewFile(DataModel dataModel) throws Exception {
        DocFileDataModel tempInfo = projectDocFileDataService.getData(dataModel.getpDocFileId(), getUserInfo(), dataModel.getImportFooterName());
        String templateName = tempInfo.getTemplateName();
        if (StringUtils.isEmpty(templateName)) {
            throw new OwnerException("模板尚未确认,不能预览");
        }
        String tempPath = MessageFormat.format(Constant.HTML_TEMPLATE_DIR + "{0}.html", templateName);
        dataModel.setTemplateName(templateName);
        Map<String, Object> dataMap = projectDocFileDataService.exportWord(dataModel, getUserInfo(), false);
        return htmlToPdfUtils.generateHtml(tempPath, dataMap);
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
        DocFileDataModel tempInfo = projectDocFileDataService.getData(dataModel.getpDocFileId(), getUserInfo(), dataModel.getImportFooterName());
        UserInfo userInfo = getUserInfo();
        String templateName = tempInfo.getTemplateName();
        dataModel.setTemplateName(templateName);
        if (StringUtils.isEmpty(templateName)) {
            throw new OwnerException("模板尚未确认,无法导出pdf");
        }
        String tempPath = MessageFormat.format(Constant.HTML_TEMPLATE_DIR + "{0}.html", templateName);
        Map<String, Object> dataMap = projectDocFileDataService.exportWord(dataModel, getUserInfo(), true);
        dataMap.put("ftlDocFileName", tempInfo.getDocFileName());
        OutputStream out = outGeneralFile(MessageFormat.format("{0}.pdf", tempInfo.getDocFileName()));
        String logo = StringUtils.isEmpty(userInfo.getCompanyLogoPath()) ? null : Constant.IMAGES_DIR + userInfo.getCompanyLogoPath();
        PdfConfigModel pdfConfig = new PdfConfigModel(dataModel.getHeader(), dataModel.getHeader(),
                dataModel.getPageNum() == null || dataModel.getPageNum(), userInfo.getCompanyName(), logo, false);
        htmlToPdfUtils.htmlToPdf(tempPath, dataMap, out, pdfConfig);
    }

    /**
     * 预览获取过程文件data
     *
     * @param model
     * @throws Exception
     */
    @GetMapping("/previewAllDoc")
    // @SystemLog(description = "预览获取过程文件data", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "project:doc:preview")
    @ApiOperation(value = "预览所有过程文件", notes = "预览所有过程文件")
    public String previewAllDoc(ExportDocFileModel model) throws Exception {
        if (model == null) {
            throw new OwnerException("请选择要预览的项目！");
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
                logger.error(e.getMessage(), e);
            }
        }
        return String.join("", htmlList);
    }

    @PostMapping("/setDocFooter")
    @SystemLog(description = "设置过程文件页脚", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "project:doc:edit")
    @ApiOperation(value = "设置过程文件页脚", notes = "设置过程文件页脚")
    public Boolean setDocFooter(@RequestBody @Validated DocFileFooterModel model) throws OwnerException {
        return projectDocFileDataService.setDocFooter(getUserInfo(), model);
    }

    @PostMapping("/changeDocStage")
    @SystemLog(description = "调整文档阶段", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "project:doc:changeStage")
    @ApiOperation(value = "调整文档阶段", notes = "调整文档阶段")
    public Boolean changeDocStage(@RequestBody @Validated ChangeDocStageModel model) throws OwnerException {
        return projectDocFileDataService.changeDocStage(getUserInfo(), model);
    }

    @GetMapping("/getDocInfo")
    @PermissionRequired(perms = "project:finalExpenseReport:view,project:forFutureReferenceData:view,rdvoucher:depreciationCost:edit")
    @ApiOperation(value = "获取过程文件", notes = "获取过程文件")
    public DocFileInfoModel getDocInfo(Integer projectId, Integer docFileId, Integer year) throws OwnerException {
        return projectDocFileDataService.getDocInfo(getUserInfo(), projectId, docFileId, year);
    }

    @GetMapping("/getBackupData")
    //@PermissionRequired(perms = "project:finalExpenseReport:view,project:forFutureReferenceData:view,rdvoucher:depreciationCost:edit")
    @ApiOperation(value = "获取备查资料列表", notes = "获取备查资料列表")
    public List<BackupDataModel> getBackupData(Integer projectId, Integer year) {
        return projectDocFileDataService.getBackupData(projectId, year);
    }

    @GetMapping("/getEquipmentTemplate")
    @PermissionRequired(perms = "rdvoucher:depreciationCost:setTemplate")
    @ApiOperation(value = "获取设备折旧模板", notes = "获取设备折旧模板")
    public List<DocFileTemplateModel> getEquipmentTemplate() {
        return projectDocFileDataService.getEquipmentTemplate();
    }

    @GetMapping("/getAuditStatus")
    @ApiOperation(value = "获取备查资料审核状态", notes = "获取备查资料审核状态")
    public Map<Integer, Integer> getAuditStatus(Integer projectId, Integer year) {
        return projectDocFileDataService.getAuditStatus(projectId, year);
    }

    @GetMapping("/getNewReports")
    @ApiOperation(value = "获取查新报告列表", notes = "获取查新报告列表")
    @PermissionRequired(perms = "project:forFutureReferenceData:view")
    public PageModel<List<NewReportsModel>> getNewReports(QueryReportsModel model) {
        return docFileService.getNewReports(model);
    }

    @GetMapping("/getProjectReports")
    @ApiOperation(value = "获取项目查新报告列表", notes = "获取项目查新报告列表")
    public List<NewReportsModel> getProjectReports(Integer projectId) {
        return docFileService.getProjectReports(projectId);
    }

    @GetMapping("/importReportData")
    @ApiOperation(value = "引入验证报告数据", notes = "引入验证报告数据")
    public List<TissueFormModel> importReportData(Integer projectId) throws OwnerException {
        return docFileService.importReportData(projectId, getUserInfo().getCompanyId());
    }

    @PostMapping("/setDocTemplate")
    @SystemLog(description = "切换过程文档模板", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "project:forFutureReferenceData:setTemplate,rdvoucher:depreciationCost:setTemplate")
    @ApiOperation(value = "切换过程文档模板", notes = "切换过程文档模板")
    public Boolean setDocTemplate(@RequestBody @Validated DataModel model) throws OwnerException {
        return projectDocFileDataService.setDocTemplate(model, getUserInfo());
    }

    @GetMapping("/getProjectAnalysis")
    @ApiOperation(value = "获取项目完成情况分析", notes = "获取项目完成情况分析")
    public Map<String, Object> getProjectAnalysis(Integer projectId) throws OwnerException {
        return projectDocFileDataService.getProjectAnalysis(projectId, getUserInfo().getCompanyId());
    }
}


