package com.yskc.rs.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import com.yskc.common.enums.FlowInstanceStatusEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.CommonUtils;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.JsonUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.dao.*;
import com.yskc.rs.dao.company.CompanyDao;
import com.yskc.rs.dao.init.InitMemberDao;
import com.yskc.rs.dao.project.*;
import com.yskc.rs.entity.*;
import com.yskc.rs.entity.company.CompanyEntity;
import com.yskc.rs.entity.init.InitMemberEntity;
import com.yskc.rs.entity.project.*;
import com.yskc.rs.enums.ProjectTypeEnum;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.docFile.*;
import com.yskc.rs.models.document.DataModel;
import com.yskc.rs.models.employee.EmployeeAutographModel;
import com.yskc.rs.models.employee.EmployeeModel;
import com.yskc.rs.models.employee.RdEmployeeSummaryModel;
import com.yskc.rs.models.init.member.InitMemberListModel;
import com.yskc.rs.models.init.member.InitMemberModel;
import com.yskc.rs.models.material.MaterialPlanModel;
import com.yskc.rs.models.project.ProjectCollectModel;
import com.yskc.rs.models.project.ProjectEmployeeInfo;
import com.yskc.rs.models.project.ProjectListModel;
import com.yskc.rs.models.projectDocFile.ProjectAnalysisModel;
import com.yskc.rs.models.projectequipment.RdEquipmentResultModel;
import com.yskc.rs.models.workshop.BackupDataModel;
import com.yskc.rs.service.*;
import com.yskc.rs.service.exportFileImpl.CostBudgetForm;
import com.yskc.rs.service.exportFileImpl.DocFileExportBeanFactory;
import com.yskc.rs.service.exportFileImpl.FinalProjectCostForm;
import com.yskc.rs.utils.ProjectAnalysisUtils;
import com.yskc.rs.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: zhangdingfu
 * @CreateTime: 2020-03-26 10:09
 * @Description: 项目文档service实现层
 */
@Service
public class ProjectDocFileDataServiceImpl implements ProjectDocFileDataService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProjectDocFileDataDao projectDocFileDataDao;
    @Autowired
    private InitMemberDao initMemberDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private CommonService commonService;
    @Autowired
    private DocFileFooterDao docFileFooterDao;
    @Autowired
    private ProjectDocFileDao projectDocFileDao;
    @Autowired
    private RdEmployeeDao rdEmployeeDao;
    @Autowired
    private StageDao stageDao;
    @Autowired
    private DocFileDao docFileDao;
    @Autowired
    private AuditDocFileDao auditDocFileDao;
    @Autowired
    private DocFileTemplateDao docFileTemplateDao;
    @Autowired
    private ProjectRdEmployeeDao projectRdEmployeeDao;
    @Autowired
    private ProjectRdEquipmentDao projectRdEquipmentDao;
    @Autowired
    private MaterialDao materialDao;
    @Autowired
    private ReviewCommitteeDao reviewCommitteeDao;
    @Autowired
    private DocFileStageDao docFileStageDao;
    @Autowired
    private RdDeptDao rdDeptDao;
    @Autowired
    private ProjectDocFileService projectDocFileService;
    @Autowired
    private ProjectYearInfoDao projectYearInfoDao;
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private SysDictionaryDao sysDictionaryDao;
    @Autowired
    private ProjectYieldConfigDao projectYieldConfigDao;
    @Autowired
    private SummaryDao summaryDao;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private InitMemberService initMemberService;
    @Autowired
    private SummaryService summaryService;
    @Autowired
    private RsConfig rsConfig;
    @Autowired
    private ProjectService projectService;

    @Override
    public DocFileDataModel getData(Integer pDocFileId, UserInfo userInfo, Boolean importFooterName) {
//        TODO 嵌套footer可删除
        DocFileDataModel model = projectDocFileDataDao.getData(pDocFileId);
        if (model != null) {
            Date docMonth = getDocYear(pDocFileId, model.getProjectId());
            ProjectEntity project = projectDao.selectById(model.getProjectId());
            model.setDocDate(docMonth);
            Integer docYear = cn.hutool.core.date.DateUtil.year(docMonth);
            Map<String, Object> commonMap = getCommonParam(userInfo, project, pDocFileId, docYear, importFooterName);
            StringJoiner tecIndustry = new StringJoiner("/");
            if (null != model.getTecIndustry() && !model.getTecIndustry().isEmpty()) {
                String[] s = model.getTecIndustry().split(",");
                List<Integer> list = new ArrayList<>();
                for (String str : s) {
                    list.add(Integer.valueOf(str));
                }
                List<String> tecList = sysDictionaryDao.getHighTecIndustry(list);
                for (String tec : tecList) {
                    tecIndustry.add(tec);
                }
            }
            //效用报告带出项目当年负责人和项目的总归集费
            if (model.getDocFileId() == 50) {
                commonMap.put("projectMasterName", initMemberDao.getMasterNameByYear(model.getProjectId(), docYear));
                BigDecimal projectRdFundsSum = summaryDao.getProjectRdFundsSum(model.getProjectId());
                commonMap.put("rdFundsSum", null == projectRdFundsSum ? BigDecimal.ZERO : projectRdFundsSum);
            }
            commonMap.put("tecIndustry", tecIndustry.toString());
            commonMap.put("formula", model.getFormula());
            model.setCommonMap(commonMap);
//            BigDecimal totalBudget=projectYearInfoDao.getTotalBudget(model.getProjectId());
//            model.setTotalBudget(totalBudget!=null?totalBudget:BigDecimal.ZERO);
//            ProjectYearInfoEntity yearInfo=projectYearInfoDao.getByYear(model.getProjectId(),docYear);
//            if(yearInfo!=null && !StringUtils.isEmpty(yearInfo.getMasterENumber())){
//                EmployeeEntity employee=employeeDao.getByNumber(project.getCompanyId(),yearInfo.getMasterENumber());
//                model.setProjectMasterName(employee.getEname());
//            }
            Map<String, EmployeeAutographModel> map = getDocFooterMap(model.getProjectId(), docYear, importFooterName);
            model.setEmployeeMap(map);

            //立项评审报告和项目验收报告处理数据
            if (!StringUtils.isEmpty(model.getData())) {
                if (model.getDocFileId() == 6 || model.getDocFileId() == 33) {
                    Map<String, Object> dataMap = JsonUtils.jsonToPojo(model.getData(), Map.class);
                    if (dataMap != null && dataMap.containsKey("list")) {
                        List<ProjectEmployeeInfo> infos = JsonUtils.jsonToList(JsonUtils.objectToJson(dataMap.get("list")), ProjectEmployeeInfo.class);
                        if (!CollectionUtils.isEmpty(infos)) {
                            List<String> enumbers = infos.stream().map(e -> e.getEnumber()).collect(Collectors.toList());
                            List<ProjectEmployeeInfo> employeeInfos = reviewCommitteeDao.getEmployees(enumbers, project.getCompanyId());
                            Map<String, ProjectEmployeeInfo> infoMap = new HashMap<>();
                            if (!CollectionUtils.isEmpty(employeeInfos)) {
                                infoMap = employeeInfos.stream().collect(Collectors.toMap(e -> e.getEnumber(), e -> e));
                            }
                            for (ProjectEmployeeInfo info : infos) {
                                if (infoMap.containsKey(info.getEnumber())) {
                                    ProjectEmployeeInfo employeeInfo = infoMap.get(info.getEnumber());
                                    info.setDeptName(employeeInfo.getDeptName());
                                    info.setPosition(employeeInfo.getPosition());
                                    info.setAutographUrl(employeeInfo.getAutographUrl());
                                    info.setEname(employeeInfo.getEname());
                                }
                            }
                            dataMap.put("list", infos);
                            String jsonData = JsonUtils.objectToJson(dataMap);
                            model.setData(jsonData);
                        }
                    }
                }
            }
        }
        return model;
    }

    @Override
    public Map<String, EmployeeAutographModel> getDocFooter(Integer projectId, Integer year) {
        return getDocFooterMap(projectId, year, true);
    }

    @Override
    public Boolean saveData(UserInfo userInfo, DocFileDataSaveModel dataModel) throws OwnerException {
        commonService.checkDocModify(userInfo.getUserSource(), dataModel.getProjectId(), dataModel.getDocFileId());
//        if (!verifySaveFile(dataModel.getpDocFileId())) {
//            throw new OwnerException("未查询到归集数据，不能添加");
//        }
        ProjectDocFileEntity docFileEntity = projectDocFileDao.selectById(dataModel.getpDocFileId());
        //限制文档可否保存
        projectDocFileService.limitDocFileOnly(docFileEntity, dataModel.getProjectId(), dataModel.getMonth());
        ProjectDocFileDataEntity search = projectDocFileDataDao.getByDocId(dataModel.getpDocFileId());
        ProjectDocFileDataEntity entity = search != null ? search : new ProjectDocFileDataEntity();
        Date now = new Date();

        String data = dataModel.getData();
        entity.setLastUpdateTime(now);
        entity.setLastUpdatorId(userInfo.getUserId());
        entity.setMsLastUpdatorId(userInfo.getMsUserId());
        entity.setData(data);
        entity.setFilledItems(dataModel.getFilledItems());
        entity.setTotalItems(dataModel.getTotalItems());
        entity.setWordLength(dataModel.getWordLength());

        Map<String, Object> map = null;
        List activeIds = null;
        List inactiveIds = null;
        if (!StringUtils.isEmpty(data)) {
            map = JsonUtils.jsonToPojo(data, Map.class);
            if (!CollectionUtils.isEmpty(map)) {
                if (map.containsKey("activeIds")) {
                    activeIds = (List<Integer>) map.get("activeIds");
                }
                if (map.containsKey("inactiveIds")) {
                    inactiveIds = (List<Integer>) map.get("inactiveIds");
                }
            }
        }

        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            docFileEntity.setMonth(dataModel.getMonth() != null ? cn.hutool.core.date.DateUtil.beginOfMonth(dataModel.getMonth()) : docFileEntity.getMonth());
            docFileEntity.setLastUpdatorId(userInfo.getUserId());
            docFileEntity.setLastUpdateTime(now);
            docFileEntity.setMsLastUpdatorId(userInfo.getMsUserId());
            docFileEntity.setFinished(dataModel.getFinished());
            projectDocFileDao.updateById(docFileEntity);
            if (null == entity.getId()) {
                entity.setpDocFileId(dataModel.getpDocFileId());
                entity.setCreateTime(now);
                entity.setCreatorId(userInfo.getUserId());
                entity.setMsCreatorId(userInfo.getMsUserId());
                projectDocFileDataDao.insert(entity);
            } else {
                projectDocFileDataDao.updateById(entity);
            }
            ProjectYieldConfigEntity pEntity = new ProjectYieldConfigEntity();
            pEntity.buildUpdate(new Date(), userInfo);
            if (!CollectionUtils.isEmpty(activeIds)) {
                projectYieldConfigDao.editSelected(activeIds, pEntity, 1);
            }
            if (!CollectionUtils.isEmpty(inactiveIds)) {
                projectYieldConfigDao.editSelected(inactiveIds, pEntity, 0);
            }
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            TransactionUtils.rollback(transactionStatus);
            logger.error(ex.getMessage(), ex);
            throw new OwnerException("过程文档保存数据失败。");
        }
        return true;
    }

    @Override
    public String getMemberStr(Integer projectId, Integer pDocFileId) {
        StringBuilder builder = new StringBuilder();
        ProjectEntity projectEntity = projectDao.selectById(projectId);
        StageEntity stage = stageDao.getStageInfo(projectId, pDocFileId);
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
            memberList.forEach(item -> {
                builder.append(item.getEname()).append("、");
            });
            return builder.substring(0, builder.length() - 1);
        }
        return builder.toString();
    }

    /**
     * 导出过程文件共用参数
     *
     * @param userInfo
     * @param projectEntity
     * @return
     */
    public Map<String, Object> getCommonParam(UserInfo userInfo, ProjectEntity projectEntity, Integer pDocFileId, Integer year, Boolean importFooterName) {
        Map<String, Object> commonMap = new HashMap<>();
        //项目起止时间
        String startDate = DateUtil.getDateTime(projectEntity.getBeginDate(), "yyyy-MM-dd");
        String endDate = DateUtil.getDateTime(projectEntity.getEndDate(), "yyyy-MM-dd");
        String beginAndEnd = MessageFormat.format("{0} 至 {1}", startDate, endDate);
        String startDateMonth = DateUtil.getDateTime(projectEntity.getBeginDate(), "yyyy年MM月");
        String endDateMonth = DateUtil.getDateTime(projectEntity.getEndDate(), "yyyy年MM月");
        String startYear = DateUtil.getDateTime(projectEntity.getBeginDate(), "yyyy");
        String startMonth = DateUtil.getDateTime(projectEntity.getBeginDate(), "MM");
        String endYear = DateUtil.getDateTime(projectEntity.getEndDate(), "yyyy");
        String endMonth = DateUtil.getDateTime(projectEntity.getEndDate(), "MM");
        String beginAndEndMonth = MessageFormat.format("{0} 至 {1}", startDateMonth, endDateMonth);
        commonMap.put("beginAndEnd", beginAndEnd);
        commonMap.put("beginAndEndMonth", beginAndEndMonth);
        //项目负责人
//        String projectMasterName = "";
//        if (!StringUtils.isEmpty(projectEntity.getMasterENumber())) {
//            EmployeeEntity employeeEntity = employeeDao.getByNumber(userInfo.getCompanyId(), projectEntity.getMasterENumber());
//            if (employeeEntity != null) {
//                projectMasterName = employeeEntity.getEname();
//            }
//        }
        // commonMap.put("projectMasterName", projectMasterName);
        //公司名称
        CompanyEntity company = companyDao.selectById(projectEntity.getCompanyId());
        commonMap.put("companyName", company.getCompanyName());
        //项目名称
        commonMap.put("pname", projectEntity.getPname());
        //项目编号
        commonMap.put("rdIndex", projectEntity.getRdTitle());
        commonMap.put("cname", userInfo.getCompanyName());
        //编制审核批准
        Integer docYear = cn.hutool.core.date.DateUtil.year(getDocYear(pDocFileId, projectEntity.getId()));
        Map<String, EmployeeAutographModel> footerMap = getDocFooterMap(projectEntity.getId(), docYear, importFooterName);
        commonMap.putAll(footerMap);
        RdDeptEntity rdDeptEntity = rdDeptDao.getParentNode(projectEntity.getBeginYear(), projectEntity.getCompanyId());
        commonMap.put("parentRdDept", rdDeptEntity != null ? rdDeptEntity.getDeptName() : "");
        commonMap.put("beginYear", projectEntity.getBeginYear());
        commonMap.put("endYear", projectEntity.getEndYear());
        Integer rdDeptId = projectEntity.getRdDeptId();
        if (rdDeptId != null && rdDeptId > 0) {
            commonMap.put("projectRdDept", rdDeptDao.getDeptName(rdDeptId));
        }
        //文档所属阶段
        StageEntity stage = stageDao.getStageInfo(projectEntity.getId(), pDocFileId);
        if (stage != null) {
            commonMap.put("currentStage", stage.getStageName());
            commonMap.put("stageBeginMonth", cn.hutool.core.date.DateUtil.format(stage.getBeginDate(), "yyyy年MM月"));
            commonMap.put("stageEndMonth", cn.hutool.core.date.DateUtil.format(stage.getEndDate(), "yyyy年MM月"));
        }
        //项目负责人
        String projectMasterName = "";
        InitMemberEntity member = initMemberDao.getMasterByYear(projectEntity.getId(), docYear);
        if (member != null) {
            EmployeeEntity employeeEntity = employeeDao.getByNumber(userInfo.getCompanyId(), member.getEnumber());
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
     * 获取项目过程文件footer
     *
     * @param projectId
     * @return
     */
    private Map<String, EmployeeAutographModel> getDocFooterMap(Integer projectId, Integer year, Boolean importFooterName) {
        EmployeeAutographModel approval = null, audit = null, toCompile = null;
        if (importFooterName == null || importFooterName) {
            DocFileFooterEntity docFooter = docFileFooterDao.getFooter(projectId, year);
            Map<String, EmployeeAutographModel> userMap = new HashMap<>();
            if (docFooter != null) {
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
                    List<EmployeeAutographModel> users = rdEmployeeDao.getEmployeeByEnumber(docFooter.getCompanyId(), enumbers);
                    if (!CollectionUtils.isEmpty(users)) {
                        userMap = users.stream().collect(Collectors.toMap(e -> e.getEnumber(), e -> e));
                    }
                }
                approval = userMap.get(docFooter.getApprovalEnumber());
                audit = userMap.get(docFooter.getAuditEnumber());
                toCompile = userMap.get(docFooter.getToCompileEnumber());
            }
        } else {
            approval = audit = toCompile = new EmployeeAutographModel();
        }
        Map<String, EmployeeAutographModel> employeeMap = new HashMap<>();
        employeeMap.put("approval", approval);
        employeeMap.put("audit", audit);
        employeeMap.put("toCompile", toCompile);
        return employeeMap;
    }

    @Override
    public Map<String, Object> exportWord(DataModel dataModel, UserInfo userInfo, Boolean export) throws Exception {
        Integer projectId = dataModel.getProjectId();
        Integer currentYear = dataModel.getCurrentYear();
        Boolean importFooterName = dataModel.getImportFooterName();
        DocFileDataModel docFileDataModel = this.getData(dataModel.getpDocFileId(), userInfo, importFooterName);
        if (StringUtils.isEmpty(docFileDataModel.getTemplateName())) {
            throw new OwnerException("模板尚未确认,无法导出");
        }
        ProjectEntity projectEntity = projectDao.selectById(projectId);
        //根据模板版本拿到titledata,并转换为map
//        Map<String, Object> titleMap = new HashMap<>();
//        Map<String, Object> map = new HashMap<>();
//        String titleData = "";
//        if (docFileDataModel.getTitleData() != null) {
//            titleData = docFileDataModel.getTitleData();
//            map = JsonUtils.jsonToPojo(titleData, Map.class);
//        } else {
//            if (docFileDataModel.getDefaultTitle() != null) {
//                map = JsonUtils.jsonToPojo(docFileDataModel.getDefaultTitle(), Map.class);
//                titleData = docFileDataModel.getDefaultTitle();
//            }
//        }
//        if (map != null) {
//            for (String key : map.keySet()) {
//                String newKey = "t" + key;
//                titleMap.put(newKey, map.get(key));
//            }
//        }
        Map<String, Object> allData = new HashMap<>();
//       allData.put("titleData", titleData);
//        allData.putAll(titleMap);
        String fileData = docFileDataModel.getData();
        String templateName = docFileDataModel.getTemplateName();
        if (templateName.contains("_")) {
            templateName = templateName.substring(0, templateName.indexOf("_"));
        }
        //ExportDocFileService exportDocFileService = dynamicGetObject(templateName);
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
        Map<String, Object> commonMap = getCommonParam(userInfo, projectEntity, dataModel.getpDocFileId(), dataModel.getCurrentYear(), importFooterName);
        String path = Constant.HTML_TEMPLATE_DIR + docFileDataModel.getTemplateName() + ".html";
        allData.putAll(dataMap);
        allData.putAll(commonMap);
        allData.put("exportTemplateFilePath", path);
        allData.put("ftlPath", "/static/");
        allData.put("currentYear", dataModel.getCurrentYear());
        allData.put("map", dataMap);
//        List<ProjectYearInfoEntity> projectYearInfos=projectYearInfoDao.getInfoByYear(projectId,projectEntity.getEndYear());
//        BigDecimal totalBudget=BigDecimal.ZERO;
//        for(ProjectYearInfoEntity entity:projectYearInfos){
//            if(entity.getBudget()!=null){
//                totalBudget=totalBudget.add(entity.getBudget());
//            }
//        }
//        allData.put("totalBudget",totalBudget);
        if (null != docFileDataModel.getCommonMap() && null != docFileDataModel.getCommonMap().get("rdFundsSum")) {
            allData.put("rdFundsSum", docFileDataModel.getCommonMap().get("rdFundsSum"));
        }
        return allData;
    }

    @Override
    public Map<String, Object> getMeetingData(List<Integer> ids, Integer companyId, Integer year) throws OwnerException {
        List<DocFileDataModel> list = projectDocFileDataDao.getDataByFileIds(ids);
        if (CollectionUtils.isEmpty(list)) {
            return new HashMap<>();
        }
        Map<String, Object> result = new HashMap<>();
        List<LinkedHashMap<String, Object>> memberList = new ArrayList<>();
        Set<String> enumbers = new HashSet<>();
        for (DocFileDataModel model : list) {
            Map map = JsonUtils.jsonToPojo(model.getData(), Map.class);
            if (map.get("hostTime") != null) {
                result.put("hostTime", map.get("hostTime"));
            }
            if (map.get("theme") != null) {
                result.put("theme", map.get("theme"));
            }
            if (map.get("members") != null) {
                List<LinkedHashMap<String, Object>> members = (List<LinkedHashMap<String, Object>>) map.get("members");
                memberList.addAll(members);
            }
        }
        for (LinkedHashMap<String, Object> map : memberList) {
            String enumber = (String) map.get("enumber");
            enumbers.add(enumber);
        }
        List<ProjectEmployeeInfo> employeeInfos = employeeDao.getEmployeeByEnumbers(companyId, year, enumbers);
        result.put("list", employeeInfos);
        return result;
    }

    @Override
    public List<Map<String, Object>> exportAllDoc(ExportDocFileModel model, UserInfo userInfo, Boolean export) throws Exception {
        Integer projectId = model.getProjectId();
        List<Integer> docIds;
        if (CollectionUtils.isEmpty(model.getpDocFileIds())) {
            List<ProjectDocFileEntity> docFiles = projectDocFileDao.queryByProject(projectId, null);
            if (CollectionUtils.isEmpty(docFiles)) {
                return new ArrayList<>();
            }
            docIds = docFiles.stream().map(e -> e.getId()).collect(Collectors.toList());
        } else {
            docIds = model.getpDocFileIds();
        }
        if (export && userInfo.getUserSource() == 1) {
            //审核通过可导出
            List<Integer> status = Arrays.asList(0, 2, 3, 4, 5, 6, 7);
            verifyDealData(docIds, status);
            if (CollectionUtils.isEmpty(docIds)) {
                throw new OwnerException("未通过审核的文档不能导出！");
            }
        }
        List<DocFileDataModel> docFileDataModels = projectDocFileDataDao.getFileDatas(docIds);
        ProjectEntity projectEntity = projectDao.selectById(projectId);
        return exportDocFile(docFileDataModels, userInfo, projectEntity, model, model.getCurrentYear(), export);
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
            Class formClass = Class.forName("com.yskc.rs.service.exportFileImpl." + formName);
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


    private List<Map<String, Object>> exportDocFile(List<DocFileDataModel> docFileDataModels, UserInfo userInfo,
                                                    ProjectEntity projectEntity, ExportDocFileModel model,
                                                    Integer currentYear, Boolean export) throws OwnerException {
        List<Map<String, Object>> dataMaps = new ArrayList<>();
        // List<Map<String,Object>> contentsPageData=new ArrayList<>();
        // Map<String,Object> contentsPageMap=new HashMap<>();
        // dataMaps.add(contentsPageMap);
        Integer seq = 0;
        List<Integer> otherDoc = Arrays.asList(8);//未确认模板但有数据
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
                EmployeeModel employeeByenumber = employeeService.getEmployeeByenumber(userInfo.getCompanyId(), projectEntity.getMasterENumber());
                if (!Objects.equals(employeeByenumber, null)) {
                    coverMap2.put("ename", employeeByenumber.getEname());
                } else {
                    coverMap2.put("ename", "无");
                }
            }
            coverMap2.put("pname", projectEntity.getPname());
            coverMap2.put("companyName", userInfo.getCompanyName());
            coverMap2.put("rdTitle", projectEntity.getRdTitle());
            String rdDeptName = "";
            if (projectEntity.getRdDeptId()!=null&&projectEntity.getRdDeptId()!=0){
                rdDeptName = rdDeptDao.getDeptName(projectEntity.getRdDeptId());
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
            dataModel.setBudgetDetail(model.getBudgetDetail());
            dataModel.setpDocFileId(docFileDataModel.getpDocFileId());
            dataModel.setpDocFileIds(model.getpDocFileIds());
            //根据模板版本拿到titledata,并转换为map
            Map<String, Object> titleMap = new HashMap<>();
//            Map<String, Object> map = new HashMap<>();
//            String titleData = docFileDataModel.getTitleData();
//            if (titleData != null) {
//                map = JsonUtils.jsonToPojo(titleData, Map.class);
//            } else {
//                if (docFileDataModel.getDefaultTitle() != null) {
//                    map = JsonUtils.jsonToPojo(docFileDataModel.getDefaultTitle(), Map.class);
//                }
//            }
//            if (map != null) {
//                for (String key : map.keySet()) {
//                    String newKey = "t" + key;
//                    titleMap.put(newKey, map.get(key));
//                }
//            }
            Map<String, Object> allData = new HashMap<>();
            allData.put("ftlDocFileName", docFileDataModel.getDocFileName());
            allData.put("ftlPath", "/static/");
            String fileData = docFileDataModel.getData();
            String templateName = docFileDataModel.getTemplateName();
            if (templateName.contains("_")) {
                templateName = templateName.substring(0, templateName.indexOf("_"));
            }
            // ExportDocFileService exportDocFileService = dynamicGetObject(templateName);
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
            Map<String, Object> commonMap = getCommonParam(userInfo, projectEntity, docFileDataModel.getpDocFileId(), currentYear, model.getImportFooterName());
            String path = Constant.HTML_TEMPLATE_DIR + docFileDataModel.getTemplateName() + ".html";
            allData.put("exportTemplateFilePath", path);
            allData.putAll(titleMap);
            allData.putAll(dataMap);
            allData.putAll(commonMap);
            allData.put("currentYear", dataModel.getCurrentYear());
            allData.put("map", dataMap);
//            List<ProjectYearInfoEntity> projectYearInfos = projectYearInfoDao.getInfoByYear(projectEntity.getId(), projectEntity.getEndYear());
//            BigDecimal totalBudget=BigDecimal.ZERO;
//            for(ProjectYearInfoEntity entity:projectYearInfos){
//                if(entity.getBudget()!=null){
//                    totalBudget=totalBudget.add(entity.getBudget());
//                }
//            }
//            allData.put("totalBudget",totalBudget);
            dataMaps.add(allData);
        }


        return dataMaps;
    }


    @Override
    public Boolean setDocFooter(UserInfo userInfo, DocFileFooterModel model) {
        Date date = new Date();
        DocFileFooterEntity docFooter = docFileFooterDao.getFooter(model.getProjectId(), model.getYear());
        if (docFooter != null) {
            return docFileFooterDao.updateFooter(docFooter.getId(), userInfo.getMsUserId(), userInfo.getUserId(),
                    date, model.getApprovalEnumber(), model.getAuditEnumber(), model.getToCompileEnumber()) > 0;
        } else {
            docFooter = new DocFileFooterEntity(model.getProjectId(), userInfo.getCompanyId(),
                    model.getAuditEnumber(), model.getToCompileEnumber(),
                    model.getApprovalEnumber(), userInfo.getMsUserId(),
                    userInfo.getUserId(), date, model.getYear());
            return docFileFooterDao.insert(docFooter) > 0;
        }
    }

    @Override
    public Boolean changeDocStage(UserInfo userInfo, ChangeDocStageModel model) throws OwnerException {

        List<Integer> docIds = new ArrayList<>(model.getPdocFileIds());
        if (CollectionUtils.isEmpty(docIds)) {
            return true;
        }

        //不可调整阶段的状态
        List<Integer> limitStatus = Arrays.asList(2, 3, 5);
        if (userInfo.getUserSource() == 1) {
            verifyDealData(model.getPdocFileIds(), limitStatus);
            if (!CollectionUtils.isEmpty(model.getPdocFileIds())) {
                throw new OwnerException("提交审核的过程文档不能调整阶段！");
            }
        }
        Date date = new Date();
        Integer userId = userInfo.getUserId();
        Integer msUserId = userInfo.getMsUserId();

        Integer pDocFileId = model.getTargetPDocFileId();
        Integer seq = 0;
        List<ProjectDocFileEntity> list = new ArrayList<>();
        if (null != pDocFileId) {
            ProjectDocFileEntity entity = projectDocFileDao.selectById(pDocFileId);
            list = projectDocFileDao.selectEditList(model, entity.getSeq(), docIds);
            if (!CollectionUtils.isEmpty(list)) {
                int size = docIds.size();
                seq = list.get(0).getSeq() - 1;
                list.forEach(item -> {
                    item.setSeq(item.getSeq() + size);
                    item.setLastUpdateTime(date);
                    item.setLastUpdatorId(userId);
                    item.setMsLastUpdatorId(msUserId);
                });
            } else {
                Integer maxSeq = projectDocFileDao.getMaxSeq(model.getProjectId(), model.getStage());
                if (maxSeq == null) {
                    seq = 0;
                } else {
                    seq = maxSeq;
                }
            }
        }

        /*Integer maxSeq = projectDocFileDao.getMaxSeq(model.getProjectId(), model.getStage());
        if (maxSeq == null) {
            maxSeq = 0;
        }

        List<Integer> docIds = new ArrayList<>(model.getPdocFileIds());
        //不可调整阶段的状态
        List<Integer> limitStatus = Arrays.asList(2, 3, 5);
        if (userInfo.getUserSource() == 1) {
            verifyDealData(model.getPdocFileIds(), limitStatus);
            if (!CollectionUtils.isEmpty(model.getPdocFileIds())) {
                throw new OwnerException("提交审核的过程文档不能调整阶段！");
            }
        }*/

        List<ProjectDocFileEntity> docFiles = projectDocFileDao.selectBatchIds(docIds);
        for (ProjectDocFileEntity docFile : docFiles) {
            docFile.setStage(model.getStage());
            seq += 1;
            docFile.setSeq(seq);
            docFile.setLastUpdateTime(date);
            docFile.setLastUpdatorId(userId);
            docFile.setMsLastUpdatorId(msUserId);
        }
        if (!CollectionUtils.isEmpty(list)) {
            docFiles.addAll(list);
        }
        return projectDocFileDao.updateStages(docFiles);
    }

    @Override
    public DocFileInfoModel getDocInfo(UserInfo userInfo, Integer projectId, Integer docFileId, Integer year) throws OwnerException {
        ProjectEntity project = projectDao.selectById(projectId);
        DocFileEntity docFile = docFileDao.selectById(docFileId);
        if (docFile == null) {
            throw new OwnerException("不存在的过程文档，请联系管理员核对数据！");
        }
        DocFileStageEntity docFileStage = docFileStageDao.getByDocFileId(docFileId);
        if (docFileStage == null) {
            throw new OwnerException("文档尚未配置，请联系管理员！");
        }
        DocFileInfoModel model = null;
        /*Date date = new Date();
        DocFileTemplateEntity docFileTemplate = docFileTemplateDao.getByDocId(docFileId);*/
        Integer hasSubmit = 5;
        if (docFileId.equals(Constant.ANNUAL_REPORT_FORM)) {
            ProjectDocFileEntity docFileEntity = projectDocFileDao.getDocFile(projectId, year);
            if (null == docFileEntity) {
                return model;
            } else {
                model = projectDocFileDao.getDocInfo(docFileEntity.getId());
            }
            //model = addSummaryReport(projectId, year, docFileTemplate, docFile, date, userInfo);
        } else if (docFileId.equals(Constant.UTILITY_REPORT_FORM)) {
            ProjectDocFileEntity docFileEntity = projectDocFileDao.getUtilityReportForm(projectId, year);
            if (null == docFileEntity) {
                return model;
            } else {
                model = projectDocFileDao.getDocInfo(docFileEntity.getId());
            }
        } else {
            List<DocFileInfoModel> docFiles = projectDocFileDao.getFileInfo(projectId, docFileId);
            if (CollectionUtils.isEmpty(docFiles)) {
                return model;
//                Integer maxSeq = projectDocFileDao.getMaxSeq(projectId, docFileStage.getStageKey());
//                ProjectDocFileEntity entity = new ProjectDocFileEntity(projectId, docFileStage.getStageKey(),
//                        docFileId, docFile.getDocName(), date, userInfo, maxSeq != null ? maxSeq + 1 : 1, docFileTemplate.getId(), null);
//                projectDocFileService.filterRepeatAdd(project, Arrays.asList(docFileId), docFileStage.getStageKey(), null);
//                projectDocFileDao.insert(entity);
//                model = new DocFileInfoModel();
//                model.setDocFileId(entity.getId());
//                model.setDocFileName(docFile.getDocName());
//                model.setTemplateName(docFileTemplate.getDocTemplateName());
            } else {
                model = docFiles.get(0);
                List<AuditDocFileEntity> auditDocFile = auditDocFileDao.getDocAuditStatus(projectId, Arrays.asList(model.getDocFileId()));
                if (!CollectionUtils.isEmpty(auditDocFile)) {
                    hasSubmit = auditDocFile.get(0).getStatus();
                }
            }
            model.setHasSubmit(hasSubmit);
        }
        Date month = getDocYear(model.getDocFileId(), projectId);
        Map<String, EmployeeAutographModel> footMap = getDocFooterMap(projectId, cn.hutool.core.date.DateUtil.year(month), true);
        model.setFooterMap(footMap);
        Map<String, Object> commonMap = getCommonParam(userInfo, project, model.getDocFileId(), year, true);
        Map<String, Object> costMap = new HashMap<>();
        if (docFileId.equals(Constant.FINAL_COST_FORM)) {
            FinalProjectCostForm costForm = new FinalProjectCostForm();
            costForm.getBudgetCost(project, costMap);
        } else if (docFileId.equals(Constant.PROJECT_REPORT_FORM)) {
            CostBudgetForm budgetForm = new CostBudgetForm();
            Map<String, Map<String, Object>> budgetMap = budgetForm.getYearBudget(project, project.getEndYear(), null, null);
            costMap.putAll(budgetMap);
        }
        model.setDataMap(costMap);
        model.setProjectId(projectId);
        model.setCommonMap(commonMap);
        model.setOwner(docFile.getOwner());
        model.setDocDate(month);
        model.setNeedEdit(docFile.getNeedEdit());
        return model;
    }

    @Override
    public List<DocFileTemplateModel> getEquipmentTemplate() {

        return docFileTemplateDao.getEquipmentTemplate();
    }

//    private DocFileInfoModel addSummaryReport(Integer projectId, Integer year,
//                                              DocFileTemplateEntity docFileTemplate, DocFileEntity docFile,
//                                              Date date, UserInfo userInfo) throws OwnerException {
//        DocFileInfoModel model=null;
//        ProjectEntity project=projectDao.selectById(projectId);
//        ProjectDocFileEntity docFileEntity = projectDocFileDao.getDocFile(projectId, year);
//        if (docFileEntity == null) {
//            String stageKey = "";
//            Integer fileSeq = 1;
//            Date month = null;
//            List<StageEntity> stages = stageDao.getByYears(year, projectId);
//            if (!CollectionUtils.isEmpty(stages)) {
//                List<ProjectDocFileEntity> seqs = projectDocFileDao.getStageMaxSeq(projectId);
//                Map<String, Integer> seqMap = new HashMap<>();
//                if (!CollectionUtils.isEmpty(seqs)) {
//                    seqMap = seqs.stream().collect(Collectors.toMap(e -> e.getStage(), e -> e.getSeq()));
//                }
//                StageEntity stage = stages.get(0);
//                stageKey = stage.getStageKey();
//                if (seqMap.containsKey(stageKey)) {
//                    fileSeq = seqMap.get(stageKey) + 1;
//                }
//            } else {
//                month = DateUtil.getMonthFirstDay(DateUtil.getYearLastDay(year));
//            }
//            docFileEntity = new ProjectDocFileEntity(projectId, stageKey,
//                    docFile.getId(), docFile.getDocName(), date, userInfo, fileSeq, docFileTemplate.getId(), month);
//            projectDocFileService.filterRepeatAdd(project, Arrays.asList(Constant.ANNUAL_REPORT_FORM), stageKey, month != null ? year : null);
//            projectDocFileDao.insert(docFileEntity);
//            model = new DocFileInfoModel();
//            model.setDocFileId(docFileEntity.getId());
//            model.setDocFileName(docFile.getDocName());
//            model.setTemplateName(docFileTemplate.getDocTemplateName());
//            model.setHasSubmit(5);
//        } else {
//            model = projectDocFileDao.getDocInfo(docFileEntity.getId());
//        }
//        return model;
    //   }

    @Override
    public Boolean setDocTemplate(DataModel model, UserInfo userInfo) throws OwnerException {
        if (null == model) {
            throw new OwnerException("请选择要切换模板的过程文档！");
        }
        DocFileEntity docFile = docFileDao.selectById(model.getDocFileId());
        List<Integer> nrDocList = docFileDao.getNRDocList();
        Boolean finished = false;
        DocFileStageEntity docFileStage = docFileStageDao.getByDocFileId(model.getDocFileId());
        if (!CollectionUtils.isEmpty(nrDocList) && nrDocList.contains(model.getDocFileId())) {
            finished = true;
        }
        if (docFileStage == null) {
            throw new OwnerException("文档尚未配置，请联系管理员！");
        }
        Date date = new Date();
        ProjectEntity project = projectDao.selectById(model.getProjectId());
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            if (model.getId() != null) {
                ProjectDocFileEntity doc = projectDocFileDao.selectById(model.getId());
                if (null == docFile) {
                    throw new OwnerException("过程文档不存在或已删除，操作失败！");
                }
                doc.setDocFileTemplateId(model.getTemplateId());
                doc.setLastUpdateTime(date);
                doc.setLastUpdatorId(userInfo.getUserId());
                doc.setMsLastUpdatorId(userInfo.getMsUserId());
                doc.setFinished(finished);
                projectDocFileDao.updateById(doc);
                int countAuditDoc = auditDocFileDao.countAuditDoc(doc.getId(), FlowInstanceStatusEnum.getUnModifyStatus());
                if (countAuditDoc > 0) {
                    throw new OwnerException("已提交审核的文档不能切换模板！");
                }
                projectDocFileDataDao.deleteByDocId(doc.getId());
            } else {
                if (model.getDocFileId().equals(Constant.ANNUAL_REPORT_FORM)) {
                    ProjectDocFileEntity docFileEntity = projectDocFileDao.getDocFile(model.getProjectId(), model.getYear());
                    if (docFileEntity == null) {
                        String stageKey = "";
                        Integer fileSeq = 1;
                        Date month = null;
                        List<StageEntity> stages = stageDao.getByYears(model.getYear(), model.getProjectId());
                        if (!CollectionUtils.isEmpty(stages)) {
                            List<ProjectDocFileEntity> seqs = projectDocFileDao.getStageMaxSeq(model.getProjectId());
                            Map<String, Integer> seqMap = new HashMap<>();
                            if (!CollectionUtils.isEmpty(seqs)) {
                                seqMap = seqs.stream().collect(Collectors.toMap(e -> e.getStage(), e -> e.getSeq()));
                            }
                            StageEntity stage = stages.get(0);
                            stageKey = stage.getStageKey();
                            if (seqMap.containsKey(stageKey)) {
                                fileSeq = seqMap.get(stageKey) + 1;
                            }
                        } else {
                            month = DateUtil.getMonthFirstDay(DateUtil.getYearLastDay(model.getYear()));
                        }
                        docFileEntity = new ProjectDocFileEntity(model.getProjectId(), stageKey,
                                docFile.getId(), docFile.getDocName(), date, userInfo, fileSeq, model.getTemplateId(), month);
                        docFileEntity.setFinished(finished);
                        projectDocFileService.filterRepeatAdd(project, Arrays.asList(Constant.ANNUAL_REPORT_FORM), stageKey, month != null ? model.getYear() : null);
                        projectDocFileDao.insert(docFileEntity);
                    }
                } else if (model.getDocFileId().equals(Constant.UTILITY_REPORT_FORM)) {
                    ProjectDocFileEntity docFileEntity = projectDocFileDao.getUtilityReportForm(model.getProjectId(), model.getYear());
                    if (docFileEntity == null) {
                        String stageKey = "";
                        Integer fileSeq = 1;
                        Date month = null;
                        List<StageEntity> stages = stageDao.getByYears(model.getYear(), model.getProjectId());
                        if (!CollectionUtils.isEmpty(stages)) {
                            List<ProjectDocFileEntity> seqs = projectDocFileDao.getStageMaxSeq(model.getProjectId());
                            Map<String, Integer> seqMap = new HashMap<>();
                            if (!CollectionUtils.isEmpty(seqs)) {
                                seqMap = seqs.stream().collect(Collectors.toMap(e -> e.getStage(), e -> e.getSeq()));
                            }
                            StageEntity stage = stages.get(0);
                            for (StageEntity entity : stages) {
                                if (Objects.equals(entity.getStageKey(), "800")) {
                                    stage = entity;
                                    break;
                                }
                            }
                            stageKey = stage.getStageKey();
                            if (seqMap.containsKey(stageKey)) {
                                fileSeq = seqMap.get(stageKey) + 1;
                            }
                        } else {
                            month = DateUtil.getMonthFirstDay(DateUtil.getYearLastDay(model.getYear()));
                        }
                        docFileEntity = new ProjectDocFileEntity(model.getProjectId(), stageKey,
                                docFile.getId(), docFile.getDocName(), date, userInfo, fileSeq, model.getTemplateId(), month);
                        docFileEntity.setFinished(finished);
                        projectDocFileService.filterRepeatAdd(project, Arrays.asList(Constant.UTILITY_REPORT_FORM), stageKey, month != null ? model.getYear() : null);
                        projectDocFileDao.insert(docFileEntity);
                    }
                } else {
                    Integer maxSeq = projectDocFileDao.getMaxSeq(model.getProjectId(), docFileStage.getStageKey());
                    ProjectDocFileEntity entity = new ProjectDocFileEntity(model.getProjectId(), docFileStage.getStageKey(),
                            model.getDocFileId(), docFile.getDocName(), date, userInfo, maxSeq != null ? maxSeq + 1 : 1, model.getTemplateId(), null);
                    projectDocFileService.filterRepeatAdd(project, Arrays.asList(model.getDocFileId()), docFileStage.getStageKey(), null);
                    entity.setFinished(finished);
                    projectDocFileDao.insert(entity);
                }
            }
            TransactionUtils.commit(transactionStatus);
        } catch (OwnerException own) {
            TransactionUtils.rollback(transactionStatus);
            logger.error(own.getMessage(), own);
            throw own;
        } catch (Exception ex) {
            TransactionUtils.rollback(transactionStatus);
            logger.error(ex.getMessage(), ex);
            throw new OwnerException("过程文档保存数据失败。");
        }
        return true;
    }

    @Override
    public Map<String, Object> getProjectAnalysis(Integer projectId, Integer companyId) throws OwnerException {
        ProjectEntity project = projectDao.getBeginAndEndDate(projectId);
        if (project == null) {
            throw new OwnerException("获取当前项目失败，请稍后重试。");
        }
        List<ProjectAnalysisModel> result = new ArrayList<>();
        // 项目周期
        result.add(new ProjectAnalysisModel(1, MessageFormat.format("{0}--{1}",
                DateUtil.format(project.getBeginDate(), "yyyy年MM月"),
                DateUtil.format(project.getEndDate(), "yyyy年MM月")), MessageFormat.format("{0}--{1}",
                DateUtil.format(project.getBeginDate(), "yyyy年MM月dd日"),
                DateUtil.format(project.getEndDate(), "yyyy年MM月dd日"))));
        ProjectAnalysisModel costAnalysis = projectYearInfoDao.getCostAnalysis(projectId);
        // 项目费用情况
        if (costAnalysis != null) {
            costAnalysis.setIndex(2);
            costAnalysis.reloadTotal();
            result.add(costAnalysis);
        }
        // 文档内容对应
        List<DocFileDataModel> docFiles = projectDocFileDataDao.getAnalysisDocFile(projectId, ProjectAnalysisUtils.getAnalysisDocFileIds());
        result.addAll(ProjectAnalysisUtils.getDocAnalysis(docFiles));
        // 试制数对应
        List<ProjectYieldConfigEntity> yieldList = projectDocFileDataDao.getTrialList(projectId);
        if (!CollectionUtil.isEmpty(yieldList)) {
            ProjectAnalysisModel trialCount = new ProjectAnalysisModel(3, "", "");
            ProjectAnalysisModel trialTotal = new ProjectAnalysisModel(5, "", "");
            Date curTrialDate = yieldList.get(0).getTrialDate();
            trialCount.addActuality(false);
            for (ProjectYieldConfigEntity yield : yieldList) {
                if (cn.hutool.core.date.DateUtil.betweenDay(curTrialDate, yield.getTrialDate(), true) >= 2) {
                    trialCount.addActuality(false);
                }
                trialTotal.addNumber(yield.getPlanYield(), yield.getRdYield());
            }
            result.add(trialCount);
            result.add(trialTotal);
        }
        // 研发人员对应
        ProjectAnalysisModel members = projectRdEmployeeDao.countAnalysisMember(companyId, projectId);
        if (members != null) {
            members.setIndex(6);
            result.add(members);
        }
        // 研发设备对应
        ProjectAnalysisModel equipments = projectRdEquipmentDao.countAnalysisEquipment(companyId, projectId);
        if (equipments != null) {
            equipments.setIndex(7);
            result.add(equipments);
        }
        // 记录及附件对应
        Integer attachmentCnt = projectDocFileDataDao.countAttachments(companyId, projectId);
        if (attachmentCnt > 0) {
            ProjectAnalysisModel attanchments = new ProjectAnalysisModel(11, "5x", "");
            attanchments.setCount(attachmentCnt);
            result.add(attanchments);
        }
        // 成果对应
        int resultCnt = projectDocFileDataDao.countAnalysisResult(companyId, projectId);
        if (resultCnt > 0) {
            ProjectAnalysisModel results = new ProjectAnalysisModel(12, "1", resultCnt + "");
            result.add(results);
        }
        // 专利对应
        int patentCnt = projectDocFileDataDao.countAnalysisPatent(companyId, projectId);
        if (patentCnt > 0) {
            ProjectAnalysisModel patents = new ProjectAnalysisModel(13, "1", patentCnt + "");
            result.add(patents);
        }
        // 查新对应
        int newReportCnt = projectDocFileDataDao.countAnalysisNewReport(companyId, projectId);
        if (newReportCnt > 0) {
            ProjectAnalysisModel patents = new ProjectAnalysisModel(14, "1", "1");
            result.add(patents);
        }

        return ProjectAnalysisModel.loadIndexMap(result);
    }

    @Override
    public Map<Integer, Integer> getAuditStatus(Integer projectId, Integer year) {
        Map<Integer, Integer> dataMap = new HashMap<>();
        List<DocFileInfoModel> models = projectDocFileDao.getAuditStatus(projectId);
        ProjectDocFileEntity entity = projectDocFileDao.getDocFile(projectId, year);
        ProjectEntity project = projectDao.selectById(projectId);
        if (!CollectionUtils.isEmpty(models)) {
            for (DocFileInfoModel model : models) {
                switch (model.getDocFileId()) {
                    case 3:
                        dataMap.put(2, model.getStatus());
                        break;
                    case 22:
                        dataMap.put(4, model.getStatus());
                        break;
                    case 27:
                        dataMap.put(1, model.getStatus());
                        break;
                    case 33:
                        dataMap.put(6, model.getStatus());
                        break;
                    case 38:
                        dataMap.put(7, model.getStatus());
                        break;
                    case 50:
                        dataMap.put(8, model.getStatus());
                        break;
                }
            }
        }
//        AuditReportEntity auditReportEntity = auditReportDao.getByProject(projectId);
//        if (auditReportEntity != null) {
//            dataMap.put(7, auditReportEntity.getStatus());
//        }
        if (project.getEndYear() > project.getBeginYear() && year < project.getEndYear()) {
            if (entity != null) {
                List<AuditDocFileEntity> auditDocFile = auditDocFileDao.getDocAuditStatus(projectId, Arrays.asList(entity.getId()));
                if (!CollectionUtils.isEmpty(auditDocFile)) {
                    dataMap.put(6, auditDocFile.get(0).getStatus());
                } else {
                    dataMap.put(6, 5);
                }
            }
        }
        return dataMap;
    }


    @Override
    public List<Map<String, Object>> exportChooseData(Integer projectId, UserInfo userInfo, List<Integer> chooseIndexs,
                                                      Integer year, Boolean importFooterName, Boolean budgetDetail) throws Exception {
        List<Map<String, Object>> dataMaps = new ArrayList<>();
        List<DataModel> dataModels = projectDocFileDao.getBackupDataFile(projectId);
        ProjectEntity project = projectDao.selectById(projectId);
        Map<Integer, Integer> auditMap = getAuditStatus(projectId, year);
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
                dataModel.setCurrentYear(project.getBeginYear());
            }
        }
        for (Integer index : chooseIndexs) {
            Map<String, Object> dataMap = new HashMap<>();
            switch (index) {
                case 1:
                    addBackupData(fileMap, dataMaps, userInfo, Constant.PROJECT_REPORT_FORM, auditMap, index);
                    break;
                case 2:
                    addBackupData(fileMap, dataMaps, userInfo, Constant.RESOLUTION_FORM, auditMap, index);
                    break;
                case 3:
                    //项目组编制情况
                    String fileName = MessageFormat.format("org_{0,number,#}_{1,number,#}.png", userInfo.getCompanyId(), year);
                    String orgUrl = MessageFormat.format("/static/images/{0,number,#}/org/{1}",
                            userInfo.getCompanyId(), fileName);
                    dataMap.put("orgUrl", orgUrl);
                    List<InitMemberListModel> members = initMemberDao.getStaffsInfo(projectId, userInfo.getCompanyId(), year, null);
                    dataMap.put("projectMembers", CollectionUtils.isEmpty(members) ? new ArrayList<>() : members);
                    String path = Constant.HTML_TEMPLATE_DIR + "ProjectBaseSituation.html";
                    dataMap.put("exportTemplateFilePath", path);
                    dataMap.put("ftlPath", "/static/");
                    dataMap.put("ftlDocFileName", "项目组编制情况");
                    dataMap.put("fileIndex", 3);
                    dataMaps.add(dataMap);
                    break;
                case 4:
                    addBackupData(fileMap, dataMaps, userInfo, Constant.RD_REPORT_FORM, auditMap, index);
                    break;
                case 6:
                    if (project.getEndYear() > project.getBeginYear() && year < project.getEndYear()) {
                        addBackupData(fileMap, dataMaps, userInfo, Constant.ANNUAL_REPORT_FORM, auditMap, index);
                    } else {
                        addBackupData(fileMap, dataMaps, userInfo, Constant.CHECK_REPORT_FORM, auditMap, index);
                    }
                    break;
                case 8:
                    addBackupData(fileMap, dataMaps, userInfo, Constant.UTILITY_REPORT_FORM, auditMap, index);
                    break;
            }
        }
        return dataMaps;
    }

    @Override
    public Map<String, Object> getReportData(Integer projectId, UserInfo userInfo, Integer year) {
        Map<String, Object> dataMap = new HashMap<>();
        //项目组编制情况
        String fileName = MessageFormat.format("org_{0,number,#}_{1,number,#}.png", userInfo.getCompanyId(), year);
        String orgUrl = MessageFormat.format("/static/images/{0,number,#}/org/{1}?time={2}",
                userInfo.getCompanyId(), fileName, System.currentTimeMillis());
        dataMap.put("orgUrl", orgUrl);
        List<InitMemberListModel> members = initMemberDao.getStaffsInfo(projectId, userInfo.getCompanyId(), year, null);
        dataMap.put("projectMembers", CollectionUtils.isEmpty(members) ? new ArrayList<>() : members);
        String path = Constant.HTML_TEMPLATE_DIR + "ProjectBaseSituation.html";
        dataMap.put("exportTemplateFilePath", path);
        dataMap.put("ftlPath", "/static/");
        dataMap.put("ftlDocFileName", "项目组编制情况");
        return dataMap;
    }

    @Override
    public List<BackupDataModel> getBackupData(Integer projectId, Integer year) {
        List<DocFileTemplateEntity> templates = docFileTemplateDao.getTemplates(Arrays.asList(3, 22, 24, 27, 33, 38, 50));
        Map<Integer, List<DocFileTemplateEntity>> templateMap = new HashMap<>();
        for (DocFileTemplateEntity entity : templates) {
            if (!templateMap.containsKey(entity.getDocFileId())) {
                List<DocFileTemplateEntity> templateEntities = new ArrayList<>();
                templateMap.put(entity.getDocFileId(), templateEntities);
            }
            templateMap.get(entity.getDocFileId()).add(entity);
        }
        List<BackupDataModel> models = new ArrayList<>();
        ProjectEntity project = projectDao.selectById(projectId);
        BackupDataModel model = new BackupDataModel(1, 1, "项目立项报告", Constant.PROJECT_REPORT_FORM, true, templateMap.get(Constant.PROJECT_REPORT_FORM));
        models.add(model);
        BackupDataModel model1 = new BackupDataModel(2, 2, "立项决议", Constant.RESOLUTION_FORM, true, templateMap.get(Constant.RESOLUTION_FORM));
        models.add(model1);
        BackupDataModel model2 = new BackupDataModel(3, 3, "项目组编制情况", null, true, null);
        models.add(model2);
        BackupDataModel model3 = new BackupDataModel(4, 4, "RD人员设备折旧分配说明", Constant.RD_REPORT_FORM, true, templateMap.get(Constant.RD_REPORT_FORM));
        models.add(model3);
        BackupDataModel model4 = new BackupDataModel(5, 5, "研发支出辅助帐及汇总表", null, false, null);
        models.add(model4);
        BackupDataModel model5;
        if (project.getBeginYear() < project.getEndYear() && year < project.getEndYear()) {
            model5 = new BackupDataModel(6, 6, "项目年度技术总结", Constant.ANNUAL_REPORT_FORM, true, templateMap.get(Constant.ANNUAL_REPORT_FORM));
        } else {
            model5 = new BackupDataModel(6, 6, "项目验收报告", Constant.CHECK_REPORT_FORM, true, templateMap.get(Constant.CHECK_REPORT_FORM));
        }
        models.add(model5);
        BackupDataModel model6 = new BackupDataModel(7, 7, "查新报告", Constant.NEW_REPORT_FORM, true, templateMap.get(Constant.NEW_REPORT_FORM));
        models.add(model6);
        BackupDataModel model8 = new BackupDataModel(8, 8, "效用报告", Constant.UTILITY_REPORT_FORM, true, templateMap.get(Constant.UTILITY_REPORT_FORM));
        models.add(model8);
        return models;
    }

    @Override
    public List<Map<String, Object>> getDataList(List<Integer> chooseFiles, ProjectEntity project, UserInfo info,
                                                 Integer year, Boolean importFooterName, Boolean cover,
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
        File parentFile = new File(rsConfig.getUploadConfig().getDocPath() + "/backupData/" + projectId);
        if (parentFile.exists()) {
            FileUtil.del(parentFile);
        }
        parentFile.mkdirs();
        String companyName = info.getCompanyName();
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
                EmployeeModel employeeByenumber = employeeService.getEmployeeByenumber(info.getCompanyId(), project.getMasterENumber());
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
            List<ProjectListModel> allProject = projectService.getAllProject(project.getCompanyId(), year);
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
            for (ProjectListModel projectListModel : allProject) {
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

    /**
     * 验证过程文档是否可导出、调整阶段
     */
    private void verifyDealData(List<Integer> pdocFileIds, List<Integer> limitStatus) {
        if (CollectionUtils.isEmpty(pdocFileIds)) {
            return;
        }
        List<AuditDocFileEntity> list = auditDocFileDao.getByDocFileId(pdocFileIds, limitStatus);
        if (!CollectionUtils.isEmpty(list)) {
            List<Integer> docIds = list.stream().map(e -> e.getDocFileId()).distinct().collect(Collectors.toList());
            pdocFileIds.removeAll(docIds);
        }
    }

    /**
     * 验证归集数据文档保存
     *
     * @param docFileId
     * @return
     */
    private Boolean verifySaveFile(Integer docFileId) {
        ProjectDocFileEntity docFile = projectDocFileDao.selectById(docFileId);
        if (docFile.getMonth() == null) {
            return true;
        }
        Date lastMonthDay = DateUtil.getMonthLastDay(docFile.getMonth());
        ProjectEntity project = projectDao.selectById(docFile.getProjectId());
        Integer year = cn.hutool.core.date.DateUtil.year(docFile.getMonth());
        switch (docFile.getDocFileId()) {
            case 12:
                List<RdEmployeeSummaryModel> models = projectRdEmployeeDao.queryEmployees(project.getCompanyId(),
                        Arrays.asList(project.getId()), docFile.getMonth(), year);
                if (CollectionUtils.isEmpty(models)) {
                    return false;
                }
                break;
            case 16:
                // 2021年8月10日16:56:28 姚娟 更改为只查询归集的设备
                List<RdEquipmentResultModel> equipmentList = projectRdEquipmentDao.queryEquipmentList(project.getCompanyId(), project.getId(), docFile.getMonth(), year);
                if (CollectionUtils.isEmpty(equipmentList)) {
                    return false;
                }
                break;
            case 35:
            case 39:
                List<Integer> keys = Arrays.asList(20000, 20001, 20002);
                List<MaterialPlanModel> merials = materialDao.getMaterialPlan(project.getId(), docFile.getMonth(),
                        lastMonthDay, keys, docFile.getDocFileId() == 35 ? 0 : 1);
                if (CollectionUtils.isEmpty(merials)) {
                    return false;
                }
                break;
            case 36:
            case 40:
            case 41:
                List<Integer> values = Arrays.asList(20301, 20303, 20304);
                List<MaterialPlanModel> trials = materialDao.getMaterialPlan(project.getId(), docFile.getMonth(),
                        lastMonthDay, values, docFile.getDocFileId() == 36 ? 2 : docFile.getDocFileId() == 40 ? 0 : 1);
                if (CollectionUtils.isEmpty(trials)) {
                    return false;
                }
                break;
            case 49:
                List<MaterialPlanModel> list = materialDao.getMaterialPlan(project.getId(), docFile.getMonth(),
                        lastMonthDay, Arrays.asList(20601), 0);
                if (CollectionUtils.isEmpty(list)) {
                    return false;
                }
                break;

            default:
                return true;
        }
        return true;
    }

    private void addBackupData(Map<Integer, DataModel> fileMap, List<Map<String, Object>> dataMaps, UserInfo userInfo,
                               int fileIndex, Map<Integer, Integer> auditMap, int chooseIndex) throws Exception {
        if (fileMap.containsKey(fileIndex)) {
            if (fileIndex == Constant.RD_REPORT_FORM || userInfo.getUserSource() == 0 || (auditMap != null && auditMap.containsKey(chooseIndex) && auditMap.get(chooseIndex) == 1)) {
                DataModel model = fileMap.get(fileIndex);
                Map<String, Object> dataMap = this.exportWord(model, userInfo, true);
                dataMap.put("ftlDocFileName", model.getDocFileName());
                dataMap.put("fileIndex", chooseIndex);
                dataMaps.add(dataMap);
            }
        }
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
            StageEntity stage = stageDao.getStageInfo(projectId, pDocFileId);
            if (stage != null && stage.getBeginDate() != null) {
                month = stage.getBeginDate();
            } else {
                ProjectEntity project = projectDao.selectById(projectId);
                month = project.getBeginDate();
            }
        }
        return month;
    }
}
