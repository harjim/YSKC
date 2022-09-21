package com.yskc.ms.service.impl.rs;


import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.CommonUtils;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.JsonUtils;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.config.Constant;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.dao.ms.FlowInstanceDocFileDao;
import com.yskc.ms.dao.rs.*;
import com.yskc.ms.entity.ms.FlowInstanceDocFile;
import com.yskc.ms.entity.rs.*;
import com.yskc.ms.entity.rs.models.ProjectCollectModel;
import com.yskc.ms.enums.DocFileDataEnum;
import com.yskc.ms.enums.ProjectTypeEnum;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.doc.DocFileDataRangeModel;
import com.yskc.ms.models.doc.MaterialTypeModel;
import com.yskc.ms.models.employee.EmployeeModel;
import com.yskc.ms.models.rs.*;
import com.yskc.ms.models.rs.summary.BackupDataListModel;
import com.yskc.ms.service.impl.rs.exportFileImpl.CostBudgetForm;
import com.yskc.ms.service.impl.rs.exportFileImpl.FinalProjectCostForm;
import com.yskc.ms.service.impl.rs.exportFileImpl.MaterialPlanForm;
import com.yskc.ms.service.rs.*;
import com.yskc.ms.utils.DocFileExportBeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by hck
 * on 2020/9/2 15:19
 * description:
 */
@Service
public class ProjectDocFileDataServiceImpl implements ProjectDocFileDataService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ProjectDocFileDataDao projectDocFileDataDao;
    @Autowired
    private InitMemberDao initMemberDao;
    @Autowired
    private RsProjectDao projectDao;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private RsCompanyDao rsCompanyDao;
    @Autowired
    private MsConfig msConfig;
    @Autowired
    private DocFileFooterDao docFileFooterDao;
    @Autowired
    private RsStageDao stageDao;
    @Autowired
    private RdEmployeeDao rdEmployeeDao;
    @Autowired
    private ProjectDocFileDao projectDocFileDao;
    @Autowired
    private AuditReportDao auditReportDao;
    @Autowired
    private FlowInstanceDocFileDao flowInstanceDocFileDao;
    @Autowired
    private DocListDao docListDao;
    @Autowired
    private ProjectYearInfoDao projectYearInfoDao;
    @Autowired
    private RsProjectService projectService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private SummaryService summaryService;
    @Autowired
    private InitMemberService initMemberService;
    @Autowired
    private MaterialDao materialDao;
    @Autowired
    private ProjectYieldConfigDao projectYieldConfigDao;
    @Autowired
    private ProjectRdEquipmentDao projectRdEquipmentDao;
    @Autowired
    private ProjectRdEmployeeDao projectRdEmployeeDao;
    @Autowired
    private RsDeptDao rsDeptDao;

    /**
     * 验证报告
     */
    private final static int DESIGN_REPORT_ID = 17;
    /**
     * 研发人员工时记录
     */
    private final static int ATTENDANCE_AGG_ID = 12;
    /**
     * 仪器设备使用记录
     */
    private final static int EQUIPMENT_AGG_ID = 16;


    @Override
    public DocFileDataModel getData(Integer pDocFileId, Boolean importFooterName) {
        DocFileDataModel model = projectDocFileDataDao.getData(pDocFileId);
        if (model != null) {
            String formula = model.getFormula();
            if (null != formula) {
                Map<Integer, String> enumMap = new HashMap<>();
                for (ProjectTypeEnum value : ProjectTypeEnum.values()) {
                    enumMap.put(value.getType(), value.getTypeName());
                }
                model.setFormula(enumMap.get(Integer.valueOf(formula)));
            }
            Integer year;
            if (model.getMonth() != null) {
                year = cn.hutool.core.date.DateUtil.year(model.getMonth());
            } else {
                RsStageEntity stage = stageDao.getStageInfo(model.getProjectId(), pDocFileId);
                if (stage != null && stage.getBeginDate() != null) {
                    year = cn.hutool.core.date.DateUtil.year(stage.getBeginDate());
                } else {
                    ProjectEntity project = projectDao.selectById(model.getProjectId());
                    year = project.getBeginYear();
                }
            }
            Map<String, EmployeeSelectModel> map = getDocFooterMap(model.getProjectId(), year, importFooterName);
            model.setEmployeeMap(map);
        }
        return model;
    }

    @Override
    public RsCompanyEntity getCompanyInfo(Integer projectId) {
        ProjectEntity projectEntity = projectDao.selectById(projectId);
        RsCompanyEntity companyEntity = rsCompanyDao.selectById(projectEntity.getCompanyId());
        return companyEntity;
    }

    @Override
    public String getMemberStr(Integer projectId, Integer pDocFileId) {
        StringBuilder builder = new StringBuilder();
        ProjectEntity projectEntity = projectDao.selectById(projectId);
        RsStageEntity stage = stageDao.getStageInfo(projectId, pDocFileId);
        Integer year = cn.hutool.core.date.DateUtil.year(stage != null && stage.getBeginDate() != null ? stage.getBeginDate() : projectEntity.getBeginDate());
        List<InitMemberModel> memberList;
        if (projectEntity.getParentId() == 0 && projectEntity.getHasChild()) {
            //查询所有子项目成员列表
            List<ProjectEntity> projectEntityList = projectDao.getChildsById(projectId);
            if (CollectionUtils.isEmpty(projectEntityList)) {
                return "";
            }
            List<Integer> projectIds = projectEntityList.stream().map(ProjectEntity::getId).collect(Collectors.toList());
            memberList = initMemberDao.getMemberEnames(projectIds, year);
        } else {
            memberList = initMemberDao.getMemberEname(projectId, year);
        }
        if (!CollectionUtils.isEmpty(memberList)) {
            for (InitMemberModel model : memberList) {
                builder.append(model.getEname()).append("、");
            }
            return builder.substring(0, builder.length() - 1);
        }
        return builder.toString();
    }

    @Override
    public Map<String, Object> exportWord(DataModel dataModel, UserInfo userInfo, Boolean export) throws Exception {
        Integer projectId = dataModel.getProjectId();
        Integer currentYear = dataModel.getCurrentYear();
        DocFileDataModel docFileDataModel = this.getData(dataModel.getpDocFileId(), dataModel.getImportFooterName());
        if (StringUtils.isEmpty(docFileDataModel.getTemplateName())) {
            throw new OwnerException("模板尚未确认,无法导出");
        }
        ProjectEntity projectEntity = projectDao.selectById(projectId);
        RsCompanyEntity companyEntity = rsCompanyDao.selectById(projectEntity.getCompanyId());
        Map<String, Object> allData = new HashMap<>();
        String fileData = docFileDataModel.getData();
        String templateName = docFileDataModel.getTemplateName();
        dataModel.setTemplateName(templateName);
        if (templateName.contains("_")) {
            templateName = templateName.substring(0, templateName.indexOf("_"));
        }
        //ExportDocFileService exportDocFileService = dynamicGetObject(docFileDataModel.getTemplateName());
        ExportDocFileService exportDocFileService = DocFileExportBeanFactory.getBean(templateName);
        Map<String, Object> dataMap = new HashMap<>();
        if (exportDocFileService != null) {
            dataMap = exportDocFileService.exportDocFile(projectEntity, currentYear, fileData, dataModel);
        } else {
            if (!StringUtils.isEmpty(fileData)) {
                dataMap = JsonUtils.jsonToPojo(fileData, Map.class);
            }
            if (!dataMap.containsKey("list")) {
                dataMap.put("list", new ArrayList<>());
            }
        }
        CommonUtils.docSpecialTransfer(dataMap, export);
        Map<String, Object> commonMap = getCommonParam(companyEntity, projectEntity, dataModel.getpDocFileId(), dataModel.getImportFooterName());
        String path = Constant.HTML_TEMPLATE_DIR + docFileDataModel.getTemplateName() + ".html";
        allData.putAll(dataMap);
        allData.putAll(commonMap);
        allData.put("exportTemplateFilePath", path);
        allData.put("currentYear", dataModel.getCurrentYear());
        allData.put("map", dataMap);
//        List<ProjectYearInfoEntity> projectYearInfos = projectYearInfoDao.getInfoByYear(projectId, projectEntity.getEndYear());
//        BigDecimal totalBudget = BigDecimal.ZERO;
//        for (ProjectYearInfoEntity entity : projectYearInfos) {
//            if (entity.getBudget() != null) {
//                totalBudget = totalBudget.add(entity.getBudget());
//            }
//        }
//        allData.put("totalBudget", totalBudget);
        return allData;
    }

    /**
     * 获取导出文件实体类
     *
     * @param formName
     * @return
     */
    private ExportDocFileService dynamicGetObject(String formName) {
        ExportDocFileService exportDocFileService = null;
        try {
            Class formClass = Class.forName("com.yskc.ms.service.impl.rs.exportFileImpl." + formName);
            Object obj = formClass.newInstance();
            if (obj instanceof ExportDocFileService) {
                exportDocFileService = (ExportDocFileService) obj;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            return exportDocFileService;
        }
    }

    /**
     * 导出过程文件共用参数
     *
     * @param companyEntity
     * @param projectEntity
     * @return
     */
    public Map<String, Object> getCommonParam(RsCompanyEntity companyEntity, ProjectEntity projectEntity, Integer pDocFileId, Boolean importFooterName) {
        Map<String, Object> commonMap = new HashMap<>();
        //项目起止时间
        String startDate = DateUtil.getDateTime(projectEntity.getBeginDate(), "yyyy-MM-dd");
        String endDate = DateUtil.getDateTime(projectEntity.getEndDate(), "yyyy-MM-dd");
        String beginAndEnd = MessageFormat.format("{0} 至 {1}", startDate, endDate);
        String startDateMonth = DateUtil.getDateTime(projectEntity.getBeginDate(), "yyyy年MM月");
        String endDateMonth = DateUtil.getDateTime(projectEntity.getEndDate(), "yyyy年MM月");
        String beginAndEndMonth = MessageFormat.format("{0} 至 {1}", startDateMonth, endDateMonth);
        commonMap.put("beginAndEnd", beginAndEnd);
        commonMap.put("beginAndEndMonth", beginAndEndMonth);
        //项目负责人
//        String projectMasterName = "";
//        if (!StringUtils.isEmpty(projectEntity.getMasterENumber())) {
//            EmployeeEntity employeeEntity = employeeDao.getByNumber(companyEntity.getId(), projectEntity.getMasterENumber());
//            if (employeeEntity != null) {
//                projectMasterName = employeeEntity.getEname();
//            }
//        }
//        commonMap.put("projectMasterName", projectMasterName);
        commonMap.put("companyName", companyEntity.getCompanyName());
        //项目名称
        commonMap.put("pname", projectEntity.getPname());
        //项目编号
        commonMap.put("rdIndex", projectEntity.getRdTitle());
        commonMap.put("cname", companyEntity.getCompanyName());
        //编制审核批准
        Integer year = cn.hutool.core.date.DateUtil.year(getDocYear(pDocFileId, projectEntity.getId()));
        Map<String, EmployeeSelectModel> footerMap = getDocFooterMap(projectEntity.getId(), year, importFooterName);
        commonMap.putAll(footerMap);
        RdDeptEntity rdDeptEntity = employeeDao.getParentNode(projectEntity.getBeginYear(), projectEntity.getCompanyId());
        commonMap.put("parentRdDept", rdDeptEntity != null ? rdDeptEntity.getDeptName() : "");
        commonMap.put("beginYear", projectEntity.getBeginYear());
        commonMap.put("endYear", projectEntity.getEndYear());
        Integer rdDeptId = projectEntity.getRdDeptId();
        if (rdDeptId != null && rdDeptId > 0) {
            commonMap.put("projectRdDept", employeeDao.getDeptName(rdDeptId));
        }
        //文档所属阶段
        if (null != pDocFileId) {
            RsStageEntity stage = stageDao.getStageInfo(projectEntity.getId(), pDocFileId);
            if (stage != null) {
                commonMap.put("currentStage", stage.getStageName());
                commonMap.put("stageBeginMonth", cn.hutool.core.date.DateUtil.format(stage.getBeginDate(), "yyyy年MM月"));
                commonMap.put("stageEndMonth", cn.hutool.core.date.DateUtil.format(stage.getEndDate(), "yyyy年MM月"));
            }
        }
        //项目负责人
        String projectMasterName = "";
        InitMemberEntity member = initMemberDao.getMasterByYear(projectEntity.getId(), year);
        if (member != null) {
            EmployeeEntity employeeEntity = employeeDao.getByNumber(companyEntity.getId(), member.getEnumber());
            if (employeeEntity != null) {
                projectMasterName = employeeEntity.getEname();
            }
        }
        commonMap.put("projectMasterName", projectMasterName);
        //总预算
        List<ProjectYearInfoEntity> projectYearInfos = projectYearInfoDao.getInfoByYear(projectEntity.getId(), projectEntity.getEndYear());
        BigDecimal totalBudget = BigDecimal.ZERO;
        for (ProjectYearInfoEntity entity : projectYearInfos) {
            if (entity.getBudget() != null) {
                totalBudget = totalBudget.add(entity.getBudget());
            }
        }
        commonMap.put("totalBudget", totalBudget);
        return commonMap;
    }

    /**
     * 获取文档年份
     *
     * @param pDocFileId
     * @return
     */
    private Date getDocYear(Integer pDocFileId, Integer projectId) {
        ProjectDocFileEntity docFile = projectDocFileDao.selectById(pDocFileId);
        Date month;
        if (!docFile.getDeleted() && docFile.getMonth() != null) {
            month = docFile.getMonth();
        } else {
            RsStageEntity stage = stageDao.getStageInfo(projectId, pDocFileId);
            if (stage != null && stage.getBeginDate() != null) {
                month = stage.getBeginDate();
            } else {
                ProjectEntity project = projectDao.selectById(projectId);
                month = project.getBeginDate();
            }
        }
        return month;
    }

    /**
     * 获取项目过程文件footer
     *
     * @param projectId
     * @return
     */
    private Map<String, EmployeeSelectModel> getDocFooterMap(Integer projectId, Integer year, Boolean importFooterName) {
        EmployeeSelectModel approval, audit, toCompile;
        approval = audit = toCompile = new EmployeeSelectModel();
        Map<String, EmployeeSelectModel> employeeMap = new HashMap<>();
        if (null == importFooterName || importFooterName) {
            DocFileFooterEntity docFooter = docFileFooterDao.getFooter(projectId, year);
            if (docFooter != null) {
                Map<String, EmployeeSelectModel> userMap = new HashMap<>();
                List<String> enumbers = new ArrayList<>();
                if (!StringUtils.isEmpty(docFooter.getApprovalEnumber())) {
                    enumbers.add(docFooter.getApprovalEnumber());
                }
                if (!StringUtils.isEmpty(docFooter.getAuditEnumber())) {
                    enumbers.add(docFooter.getAuditEnumber());
                }
                if (!StringUtils.isEmpty(docFooter.getToCompileEnumber())) {
                    enumbers.add(docFooter.getToCompileEnumber());
                }

                if (!CollectionUtils.isEmpty(enumbers)) {
                    List<EmployeeSelectModel> users = rdEmployeeDao.getEmployeeByEnumber(docFooter.getCompanyId(), enumbers);
                    if (!CollectionUtils.isEmpty(users)) {
                        userMap = users.stream().collect(Collectors.toMap(e -> e.getEnumber(), e -> e));
                    }
                }
                approval = userMap.get(docFooter.getApprovalEnumber());
                audit = userMap.get(docFooter.getAuditEnumber());
                toCompile = userMap.get(docFooter.getToCompileEnumber());
            }
        }
        employeeMap.put("approval", approval);
        employeeMap.put("audit", audit);
        employeeMap.put("toCompile", toCompile);
        return employeeMap;
    }


    private List<Map<String, Object>> exportDocFile(List<DocFileDataModel> docFileDataModels,
                                                    ProjectEntity projectEntity, ExportDocFileModel model,
                                                    Integer currentYear, Boolean export) throws OwnerException {
//        //项目负责人
//        String projectMasterName = "";
//        if (!StringUtils.isEmpty(projectEntity.getMasterENumber())) {
//            EmployeeEntity employeeEntity = employeeDao.getByNumber(projectEntity.getCompanyId(), projectEntity.getMasterENumber());
//            if (employeeEntity != null) {
//                projectMasterName = employeeEntity.getEname();
//            }
//        }

        List<Map<String, Object>> dataMaps = new ArrayList<>();
        // List<Map<String,Object>> contentsPageData=new ArrayList<>();
        // Map<String,Object> contentsPageMap=new HashMap<>();
        // dataMaps.add(contentsPageMap);
        Integer seq = 0;
        List<Integer> otherDoc = Arrays.asList(8);//未确认模板但有数据
        RsCompanyEntity companyEntity = rsCompanyDao.selectById(projectEntity.getCompanyId());

        String durationTime = DateUtil.format(projectEntity.getBeginDate(), "yyyy.MM") + "~" + DateUtil.format(projectEntity.getEndDate(), "yyyy.MM");
        if (model.getCover()!=null&& model.getCover()){
            Map<String, Object> coverMap2 = new HashMap<>();
            String path2 = Constant.HTML_TEMPLATE_DIR + "ReportCourse.html";
            coverMap2.put("exportTemplateFilePath", path2);
            coverMap2.put("ftlPath", "/static/");
            coverMap2.put("ftlDocFileName", "封面");
            coverMap2.put("fileIndex", "0-1");
            coverMap2.put("durationTime", durationTime);
            if (Objects.equals(projectEntity.getMasterENumber(), null)) {
                coverMap2.put("ename", "无");
            } else {
                EmployeeModel employeeByenumber = employeeService.getEmployeeByenumber(companyEntity.getId(), projectEntity.getMasterENumber());
                if (!Objects.equals(employeeByenumber, null)) {
                    coverMap2.put("ename", employeeByenumber.getEname());
                } else {
                    coverMap2.put("ename", "无");
                }
            }
            coverMap2.put("pname", projectEntity.getPname());
            coverMap2.put("companyName",companyEntity.getCompanyName());
            coverMap2.put("rdTitle", projectEntity.getRdTitle());
            String rdDeptName = "";
            if (projectEntity.getRdDeptId()!=null&&projectEntity.getRdDeptId()!=0){
                rdDeptName = rsDeptDao.getDeptName(projectEntity.getRdDeptId());
            }
            coverMap2.put("projectRdDept",rdDeptName);
            dataMaps.add(coverMap2);
        }
        for (DocFileDataModel docFileDataModel : docFileDataModels) {
            if (StringUtils.isEmpty(docFileDataModel.getTemplateName())) {
                //logger.warn("模板尚未确认,无法导出");
                continue;
            }
            if (otherDoc.contains(docFileDataModel.getDocFileId())) {
                continue;
            }
            DataModel dataModel = new DataModel();
            dataModel.setCompanyId(model.getCompanyId());
            dataModel.setCurrentYear(model.getCurrentYear());
            dataModel.setProjectId(model.getProjectId());
            dataModel.setpDocFileId(docFileDataModel.getpDocFileId());
            dataModel.setBudgetDetail(model.getBudgetDetail());
            dataModel.setpDocFileIds(model.getpDocFileIds());
            Map<String, Object> allData = new HashMap<>();
            allData.put("ftlDocFileName", docFileDataModel.getDocFileName());
            if (!export) {
                allData.put("ftlPath", msConfig.getExpertConfig().getDomainName() + "/static/");
            } else {
                allData.put("ftlPath", "/static/");
            }
            String fileData = docFileDataModel.getData();
            String templateName = docFileDataModel.getTemplateName();
            dataModel.setTemplateName(templateName);
            if (templateName.contains("_")) {
                templateName = templateName.substring(0, templateName.indexOf("_"));
            }
            // ExportDocFileService exportDocFileService = dynamicGetObject(docFileDataModel.getTemplateName());
            ExportDocFileService exportDocFileService = DocFileExportBeanFactory.getBean(templateName);
            Map<String, Object> dataMap = new HashMap<>();
            if (exportDocFileService != null) {
                dataMap = exportDocFileService.exportDocFile(projectEntity, currentYear, fileData, dataModel);
            } else {
                if (!StringUtils.isEmpty(fileData)) {
                    dataMap = JsonUtils.jsonToPojo(fileData, Map.class);
                } else {
                    dataMap.put("list", new ArrayList<>());
                }
            }
            CommonUtils.docSpecialTransfer(dataMap, export);
            Map<String, Object> commonMap = getCommonParam(companyEntity, projectEntity, dataModel.getpDocFileId(), model.getImportFooterName());
//            commonMap.put("projectMasterName", projectMasterName);
            String path = Constant.HTML_TEMPLATE_DIR + docFileDataModel.getTemplateName() + ".html";
            allData.put("exportTemplateFilePath", path);
            allData.putAll(dataMap);
            allData.putAll(commonMap);
            allData.put("currentYear", dataModel.getCurrentYear());
            allData.put("map", dataMap);
//            List<ProjectYearInfoEntity> projectYearInfos = projectYearInfoDao.getInfoByYear(projectEntity.getId(), projectEntity.getEndYear());
//            BigDecimal totalBudget = BigDecimal.ZERO;
//            for (ProjectYearInfoEntity entity : projectYearInfos) {
//                if (entity.getBudget() != null) {
//                    totalBudget = totalBudget.add(entity.getBudget());
//                }
//            }
//            allData.put("totalBudget", totalBudget);
            dataMaps.add(allData);
        }


        return dataMaps;
    }


    @Override
    public List<Map<String, Object>> exportAllDoc(ExportDocFileModel model, UserInfo userInfo, Boolean export) throws
            Exception {
        Integer projectId = model.getProjectId();
        List<DocFileDataModel> docFileDataModels = projectDocFileDataDao.getFileDatas(projectId, model.getpDocFileIds());
        if (CollectionUtils.isEmpty(docFileDataModels)) {
            return new ArrayList<>();
        }
        checkHasFinaData(docFileDataModels, projectId);
        ProjectEntity projectEntity = projectDao.selectById(projectId);
        return exportDocFile(docFileDataModels, projectEntity, model, model.getCurrentYear(), export);
    }

    /**
     * 检查财务数据是否存在
     *
     * @param docFiles
     */
    private void checkHasFinaData(List<DocFileDataModel> docFiles, Integer projectId) throws OwnerException {
        Map<Integer, Integer> docIdTypeMap = MaterialPlanForm.getMaterialDocIdTypeMap();
//        docIdTypeMap.put(DESIGN_REPORT_ID, DocFileDataEnum.DESIGN_REPORT.getType());
        docIdTypeMap.put(ATTENDANCE_AGG_ID, DocFileDataEnum.ATTENDANCE_AGG.getType());
        docIdTypeMap.put(EQUIPMENT_AGG_ID, DocFileDataEnum.EQUIPMENT_AGG.getType());
        Map<Integer, DocFileDataRangeModel> typeDataMap = new HashMap<>();
        Integer type;
        DocFileDataRangeModel current;
        for (DocFileDataModel docFile : docFiles) {
            type = docIdTypeMap.get(docFile.getDocFileId());
            if (type != null) {
                if (null == docFile.getMonth()) {
                    continue;
                    // zdf 文档月份存放在data的，不进行处理
                  //  throw new OwnerException(MessageFormat.format("{0}-{1}未保存月份，无法获取财务数据，导出失败！", docFile.getStageType(), docFile.getDocFileName()));
                }
                current = typeDataMap.get(type);
                if (null == current) {
                    current = new DocFileDataRangeModel(docFile);
                    typeDataMap.put(type, current);
                } else {
                    current.addDoc(docFile);
                }
            }
        }
        for (Integer curType : typeDataMap.keySet()) {
            current = typeDataMap.get(curType);
            List<Date> months = null;
            DocFileDataEnum docFileDataEnum = DocFileDataEnum.getByType(curType);
            switch (docFileDataEnum) {
                case MATERIAL:
                    List<MaterialTypeModel> typeData = materialDao.getRangeMaterials(projectId, current.getBegin(), current.getEnd());
                    if (CollectionUtils.isEmpty(typeData)) {
                        throw new OwnerException(MessageFormat.format("文档：{0}，无财务数据，导出失败！", String.join("，", current.getData().stream().map(DocFileDataModel::getDocFileName).distinct().collect(Collectors.toList()))));
                    }
                    String keyFormat = "{0}_{1}_{2}",curMonth;
                    Map<String, Boolean> existMap = typeData.stream().collect(Collectors.toMap((a) -> MessageFormat.format(keyFormat, a.getMonth(), a.getRdType(), a.getType()), b -> Boolean.TRUE,(o,n)->n));
                    int materialType;
                    for (DocFileDataModel docFile : current.getData()) {
                        curMonth = DateUtil.format(docFile.getMonth(),DateUtil.DEFAULT_YYMM_FORMAT);
                        List<Integer> costTypes = new ArrayList<>();
                        materialType = MaterialPlanForm.getType(docFile.getDocFileId(), costTypes);
                        boolean hasData = false;
                        for (Integer costType : costTypes) {
                            if(existMap.containsKey(MessageFormat.format(keyFormat,curMonth,costType,materialType))){
                                hasData = true;
                                break;
                            }
                        }
                        if(!hasData){
                            throw new OwnerException(MessageFormat.format("{0}-{1}无财务数据，导出失败！", docFile.getStageType(), docFile.getDocFileName()));
                        }
                    }
                    break;
//                case DESIGN_REPORT:
//                    months = projectYieldConfigDao.getRangeMonths(projectId,current.getBegin(), current.getEnd());
//                    break;
                case ATTENDANCE_AGG:
                    months = projectRdEmployeeDao.getRangeMonths(projectId,current.getBegin(), current.getEnd());
                    break;
                case EQUIPMENT_AGG:
                    months = projectRdEquipmentDao.getRangeMonths(projectId,current.getBegin(), current.getEnd());
                    break;
                default:
                    break;
            }
            if(docFileDataEnum != DocFileDataEnum.MATERIAL){
                if(CollectionUtils.isEmpty(months)){
                    throw new OwnerException(MessageFormat.format("文档：{0}，无财务数据，导出失败！", String.join("，", current.getData().stream().map(DocFileDataModel::getDocFileName).collect(Collectors.toList()))));
                }
                Map<Date,Boolean> monthMap = months.stream().collect(Collectors.toMap(a->a,b->Boolean.TRUE,(o,n)->n));
                for (DocFileDataModel docFile : current.getData()) {
                    if(!monthMap.containsKey(docFile.getMonth())){
                        throw new OwnerException(MessageFormat.format("{0}-{1}无财务数据，导出失败！", docFile.getStageType(), docFile.getDocFileName()));
                    }
                }
            }
        }
    }

    @Override
    public List<DocFileInfoModel> getDocInfo(UserInfo userInfo, Integer projectId, Integer year) throws OwnerException {
//        BackupDataModel dataModel = new BackupDataModel();
//        Map<String, EmployeeSelectModel> footMap = getDocFooterMap(projectId);
//        ProjectEntity project = projectDao.selectById(projectId);
//        RsCompanyEntity rsCompanyEntity = rsCompanyDao.selectById(project.getCompanyId());
//        Map<String, Object> commonMap = getCommonParam(rsCompanyEntity, project, null);
//        dataModel.setFootMap(footMap);
//        dataModel.setCommonMap(commonMap);
//        List<Integer> docFileIds = new ArrayList<>();
//        if (null != docFileId && docFileId > 0) {
//            docFileIds.add(docFileId);
//        } else {
//            docFileIds = Arrays.asList(3, 22, 27, 33, 38);
//        }
        ProjectEntity project = projectDao.selectById(projectId);
        List<DocFileInfoModel> models = new ArrayList<>();
        List<DocFileInfoModel> docFiles = projectDocFileDao.getFileInfo(projectId, Arrays.asList(3, 22, 27, 38, 50));
        if (project.getEndYear() > project.getBeginYear() && project.getEndYear() > year) {
            ProjectDocFileEntity docFile = projectDocFileDao.getDocFile(projectId, year);
            if (docFile != null) {
                DocFileInfoModel infoModel = projectDocFileDao.getDocInfo(docFile.getId());
                docFiles.add(infoModel);
            }
        } else {
            List<DocFileInfoModel> docs = projectDocFileDao.getFileInfo(projectId, Arrays.asList(33));
            if (!CollectionUtils.isEmpty(docs)) {
                docFiles.add(docs.get(0));
            }
        }
        if (!CollectionUtils.isEmpty(docFiles)) {
            List<Integer> existFileIds = docFiles.stream().map(e -> e.getDocFileId()).distinct().collect(Collectors.toList());
            List<FlowInstanceDocFile> docInstances = flowInstanceDocFileDao.getDocAudit(Arrays.asList(projectId), existFileIds, userInfo.getId());
            Map<Integer, FlowInstanceDocFile> permissionMap = docInstances.stream().collect(Collectors.toMap(e -> e.getDocFileId(), e -> e));
            for (DocFileInfoModel infoModel : docFiles) {
                infoModel.setHasSubmit(infoModel.getHasSubmit() != null ? infoModel.getHasSubmit() : 5);
                Map<String, Object> costMap = new HashMap<>();
                // // TODO: 22/03/25 zdf docFileId=34/费用决算报告问题 (留存备查资料不存在该文档)
                if (infoModel.getId() == 34) {
                    FinalProjectCostForm costForm = new FinalProjectCostForm();
                    costForm.getBudgetCost(project, costMap);
                    infoModel.setDataMap(costMap);
                } else if (infoModel.getId() == 27) {
                    CostBudgetForm budgetForm = new CostBudgetForm();
                    Map<String, Map<String, Object>> costDataMap = budgetForm.getYearBudget(project, project.getEndYear());
                    costMap.putAll(costDataMap);
                    infoModel.setDataMap(costMap);
                }
                infoModel.setHasPermission(permissionMap.containsKey(infoModel.getDocFileId()));
                models.add(infoModel);
            }
        }
        return models;
    }

    @Override
    public PageModel<List<NewReportsModel>> getNewReports(QueryReportsModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("createTime");
            page.setDescs(desc);
        }
        AuditReportEntity reportEntity = auditReportDao.getByProject(query.getProjectId());
        List<NewReportsModel> models = docListDao.getDocList(page, query.getProjectId());
        Integer reportStatus;
        if (reportEntity == null || reportEntity.getStatus() == 5) {
            reportStatus = 5;
        } else {
            reportStatus = reportEntity.getStatus();
        }
        return PageUtils.buildPageResult(page, models, reportStatus);
    }

    @Override
    public List<Map<String, Object>> exportChooseData(Integer projectId, UserInfo userInfo, List<Integer> chooseIndexs,
                                                      Integer year, Boolean importFooterName, Boolean budgetDetail) throws Exception {
        List<Map<String, Object>> dataMaps = new ArrayList<>();
        List<DataModel> dataModels = projectDocFileDao.getBackupDataFile(projectId);
        ProjectEntity project = projectDao.selectById(projectId);
        RsCompanyEntity company = rsCompanyDao.selectById(project.getCompanyId());
        Integer companyId = company.getId();
        //Map<Integer, Integer> auditMap = getAuditStatus(projectId);
        Map<Integer, DataModel> fileMap = new HashMap<>();
        if (project.getEndYear() > project.getBeginYear() && year < project.getEndYear()) {
            ProjectDocFileEntity docFileEntity = projectDocFileDao.getDocFile(projectId, year);
            if (docFileEntity != null) {
                ProjectDocFileDataEntity dataEntity = projectDocFileDataDao.getByDocId(docFileEntity.getId());
                DataModel dataModel = new DataModel(docFileEntity.getDocFileId(), dataEntity != null ? dataEntity.getData() : null,
                        docFileEntity.getId(), projectId, docFileEntity.getDocFileName(), importFooterName, budgetDetail);
                fileMap.put(dataModel.getId(), dataModel);
            }

        }
        if (!CollectionUtils.isEmpty(dataModels)) {
            for (DataModel dataModel : dataModels) {
                if (!fileMap.containsKey(dataModel.getId())) {
                    dataModel.setBudgetDetail(budgetDetail);
                    fileMap.put(dataModel.getId(), dataModel);
                }
                dataModel.setImportFooterName(importFooterName);
                dataModel.setCurrentYear(year);
            }
        }
        for (Integer index : chooseIndexs) {
            Map<String, Object> dataMap = new HashMap<>();
            switch (index) {
                case 1:
                    addBackupData(fileMap, dataMaps, userInfo, 27, index);
                    break;
                case 2:
                    addBackupData(fileMap, dataMaps, userInfo, 3, index);
                    break;
                case 3:
                    //项目组编制情况
                    String fileName = MessageFormat.format("org_{0,number,#}_{1,number,#}.png", companyId, year);
                    String orgUrl = MessageFormat.format("/static/images/{0,number,#}/org/{1}",
                            company.getId(), fileName);
                    dataMap.put("orgUrl", orgUrl);
                    List<InitMemberListModel> members = initMemberDao.getStaffsInfo(projectId, companyId, year, null);
                    dataMap.put("projectMembers", CollectionUtils.isEmpty(members) ? new ArrayList<>() : members);
                    String path = Constant.HTML_TEMPLATE_DIR + "ProjectBaseSituation.html";
                    dataMap.put("exportTemplateFilePath", path);
                    dataMap.put("ftlPath", "/static/");
                    dataMap.put("ftlDocFileName", "项目组编制情况");
                    dataMap.put("fileIndex", 3);
                    dataMaps.add(dataMap);
                    break;
                case 4:
                    addBackupData(fileMap, dataMaps, userInfo, 22, index);
                    break;
                case 6:
                    if (project.getEndYear() > project.getBeginYear() && year < project.getEndYear()) {
                        addBackupData(fileMap, dataMaps, userInfo, 24, index);
                    } else {
                        addBackupData(fileMap, dataMaps, userInfo, 33, index);
                    }
                    break;
                case 8:
                    addBackupData(fileMap, dataMaps, userInfo, 50, index);
                    break;
            }
        }

        return dataMaps;
    }

    @Override
    public Map<String, Object> getReportData(Integer projectId, UserInfo userInfo, Integer year) {
        ProjectEntity project = projectDao.selectById(projectId);
        Map<String, Object> dataMap = new HashMap<>();
        //项目组编制情况
        String fileName = MessageFormat.format("org_{0,number,#}_{1,number,#}.png", project.getCompanyId(), year);
        String orgUrl = MessageFormat.format("/static/images/{0,number,#}/org/{1}?time={2}",
                project.getCompanyId(), fileName, System.currentTimeMillis());
        dataMap.put("orgUrl", msConfig.getExpertConfig().getDomainName() + orgUrl);
        List<InitMemberListModel> members = initMemberDao.getStaffsInfo(projectId, project.getCompanyId(), year, null);
        dataMap.put("projectMembers", CollectionUtils.isEmpty(members) ? new ArrayList<>() : members);
        String path = Constant.HTML_TEMPLATE_DIR + "ProjectBaseSituation.html";
        dataMap.put("exportTemplateFilePath", path);
        dataMap.put("ftlPath", msConfig.getExpertConfig().getDomainName() + "/static/");
        dataMap.put("ftlDocFileName", "3.项目组编制情况");
        return dataMap;
    }

    @Override
    public List<NewReportsModel> getProjectReports(Integer projectId) {
        List<NewReportsModel> models = docListDao.getReports(projectId);
        return models;
    }

    @Override
    public List<BackupDataListModel> getBackupData(Integer projectId, Integer year) {
        List<BackupDataListModel> models = new ArrayList<>();
        ProjectEntity project = projectDao.selectById(projectId);
        BackupDataListModel model = new BackupDataListModel(1, 1, "项目立项报告", 27, true);
        models.add(model);
        BackupDataListModel model1 = new BackupDataListModel(2, 2, "立项决议", 3, true);
        models.add(model1);
        BackupDataListModel model2 = new BackupDataListModel(3, 3, "项目组编制情况", null, true);
        models.add(model2);
        BackupDataListModel model3 = new BackupDataListModel(4, 4, "RD人员设备折旧分配说明", 22, true);
        models.add(model3);
        BackupDataListModel model4 = new BackupDataListModel(5, 5, "研发支出辅助帐及汇总表", null, false);
        models.add(model4);
        BackupDataListModel model5;
        if (project.getBeginYear() < project.getEndYear() && year < project.getEndYear()) {
            model5 = new BackupDataListModel(6, 6, "项目年度技术总结", 24, true);
        } else {
            model5 = new BackupDataListModel(6, 6, "项目验收报告", 33, true);
        }
        models.add(model5);
        BackupDataListModel model6 = new BackupDataListModel(7, 7, "查新报告", 38, true);
        models.add(model6);
        BackupDataListModel model7 = new BackupDataListModel(8, 8, "效用报告", 50, true);
        models.add(model7);
        return models;
    }

    @Override
    public List<Map<String, Object>> getDataList(List<Integer> chooseFiles, ProjectEntity project, UserInfo info,
                                                 Integer year, Boolean importFooterName, Boolean cover, RsCompanyEntity company,
                                                 Boolean budgetDetail, Boolean utility) throws Exception {
        if (CollectionUtils.isEmpty(chooseFiles)) {
            chooseFiles = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 6, 7, 8));
        }
        if (!utility) {
            Iterator<Integer> iterator = chooseFiles.iterator();
            while (iterator.hasNext()) {
                int i = iterator.next();
                if (i == 8) {
                    iterator.remove();
                }
            }
        }
        Integer projectId = project.getId();
        List<Map<String, Object>> dataMaps = new ArrayList<>();
        List<Map<String, Object>> maps = exportChooseData(project.getId(), info, chooseFiles, year, importFooterName, budgetDetail);
        if (!chooseFiles.contains(7) && CollectionUtils.isEmpty(maps)) {
            throw new OwnerException("该项目不存在可导出的备查资料！");
        }
        String durationTime = DateUtil.format(project.getBeginDate(), "yyyy.MM") + "~" + DateUtil.format(project.getEndDate(), "yyyy.MM");
        File parentFile = new File(msConfig.getUploadConfig().getDocPath() + "/backupData/" + projectId);
        if (parentFile.exists()) {
            FileUtil.del(parentFile);
        }
        parentFile.mkdirs();
        String companyName = company.getCompanyName();
        if (cover == null || cover) {
            //添加封面(项目资料清单)
            Map<String, Object> coverMap = new HashMap<>();
            String path = Constant.HTML_TEMPLATE_DIR + "BackupDataCover.html";
            coverMap.put("exportTemplateFilePath", path);
            coverMap.put("ftlPath", "/static/");
            coverMap.put("ftlDocFileName", "项目资料清单");
            coverMap.put("fileIndex", "0-3");
            coverMap.put("pname", project.getPname());
            coverMap.put("year", year);
            coverMap.put("rdTitle", project.getRdTitle());
            coverMap.put("companyName", companyName);
            coverMap.put("isEndYear", year.equals(project.getEndYear()));
            boolean has6 = false, has8 = false;
            for (int fileNum : chooseFiles) {
                if (fileNum == 6) {
                    // 存在年度总结或项目验收报告
                    has6 = true;
                } else if (fileNum == 8) {
                    // 存在效用报告
                    has8 = true;
                }
            }
            coverMap.put("has6", has6);
            coverMap.put("has8", has8);
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
                EmployeeModel employeeByenumber = employeeService.getEmployeeByenumber(company.getId(), project.getMasterENumber());
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
            coverMap3.put("projectId", project.getId());
            coverMap3.put("year", year);

            Date now = new Date();
            List<RsProjectListModel> allProject = projectService.getAllProject(project.getCompanyId(), year);
            List<ProjectCollectModel> list = new ArrayList<>();
            BigDecimal bigDecimal = new BigDecimal("0");
            List<Integer> projectIds = allProject.stream().map(e -> e.getId()).collect((Collectors.toList()));
            Map<Integer, Map<String, BigDecimal>> bigDecimalMap = summaryService.getFundByYearAndProId(projectIds, year);
            Map<Integer, Map<String, Long>> memberMap = initMemberService.getMemberByProAndYear(projectIds, year);
            ProjectTypeEnum[] values = ProjectTypeEnum.values();
            Map<Integer, String> typeMap = new HashMap<>();
            for (ProjectTypeEnum value : values) {
                typeMap.put(value.getType(), value.getTypeName());
            }

            BigDecimal divisor = new BigDecimal("1000");
            for (RsProjectListModel projectListModel : allProject) {
                ProjectCollectModel projectCollectModel = new ProjectCollectModel();
                projectCollectModel.setProjectName(projectListModel.getPname());
                projectCollectModel.setDurationTime(DateUtil.format(projectListModel.getBeginDate(), "yyyy.MM") + "-" + DateUtil.format(projectListModel.getEndDate(), "yyyy.MM"));

                if (memberMap.containsKey(projectListModel.getId())) {
                    projectCollectModel.setNumbers(memberMap.get(projectListModel.getId()).get("members"));
                } else {
                    projectCollectModel.setNumbers(0L);
                }

                if (bigDecimalMap.containsKey(projectListModel.getId())) {

                    /**
                     * 如果rdFunds等于0则不保留四位小数
                     */
                    BigDecimal rdFunds = bigDecimalMap.get(projectListModel.getId()).get("rdFunds");
                    if (rdFunds.compareTo(BigDecimal.ZERO) != 0) {
                        projectCollectModel.setEstimateExpense(rdFunds.divide(divisor).setScale(4, RoundingMode.HALF_UP).toString());
                    } else {
                        projectCollectModel.setEstimateExpense("0");
                    }

                    bigDecimal = bigDecimal.add(rdFunds);
                } else {
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

            /**
             * 如果summarySum不为0则进行四舍五入保留四位小数
             */
            BigDecimal summarySum = bigDecimal.divide(divisor);
            if (summarySum.compareTo(BigDecimal.ZERO) != 0) {
                coverMap3.put("summarySum", summarySum.setScale(4, RoundingMode.HALF_UP).toString());
            } else {
                coverMap3.put("summarySum", 0);
            }

            dataMaps.add(coverMap3);
        }
        dataMaps.addAll(maps);
        return dataMaps;
    }

    private void addBackupData(Map<Integer, DataModel> fileMap, List<Map<String, Object>> dataMaps, UserInfo userInfo,
                               int fileIndex, int chooseIndex) throws Exception {
        if (fileMap.containsKey(fileIndex)) {
            DataModel model = fileMap.get(fileIndex);
            Map<String, Object> dataMap = this.exportWord(model, userInfo, true);
            dataMap.put("ftlDocFileName", model.getDocFileName());
            dataMap.put("ftlPath", "/static/");
            dataMap.put("fileIndex", chooseIndex);
            dataMaps.add(dataMap);
        }

    }

}
