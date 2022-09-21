package com.yskc.rs.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.base.Joiner;
import com.yskc.common.enums.AuditModeEnum;
import com.yskc.common.enums.FlowInstanceStatusEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.JsonUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.dao.*;
import com.yskc.rs.dao.company.CompanyDao;
import com.yskc.rs.dao.init.InitEquipmentDao;
import com.yskc.rs.dao.init.InitMemberDao;
import com.yskc.rs.dao.project.*;
import com.yskc.rs.entity.*;
import com.yskc.rs.entity.company.CompanyEntity;
import com.yskc.rs.entity.init.InitEquipmentEntity;
import com.yskc.rs.entity.init.InitMemberEntity;
import com.yskc.rs.entity.project.*;
import com.yskc.rs.enums.CostEnum;
import com.yskc.rs.enums.OrgEnum;
import com.yskc.rs.enums.ProjectTypeEnum;
import com.yskc.rs.models.CheckStatusModel;
import com.yskc.rs.models.HighTecIndustryModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.cdocument.CDocumentModel;
import com.yskc.rs.models.docFile.DocFileFooterModel;
import com.yskc.rs.models.docFile.MeetingCountModel;
import com.yskc.rs.models.docFile.MeetingFromModel;
import com.yskc.rs.models.employee.EmployeeModel;
import com.yskc.rs.models.employee.EmployeeSelectModel;
import com.yskc.rs.models.employee.RdEmployeeModel;
import com.yskc.rs.models.excel.ProjectExcel;
import com.yskc.rs.models.hightech.QueryAssistModel;
import com.yskc.rs.models.outsourcing.OutsourcingSummaryModel;
import com.yskc.rs.models.project.*;
import com.yskc.rs.models.projectenergy.EnergySumModel;
import com.yskc.rs.models.projectrdemployee.ProjectRdEmployeeModel;
import com.yskc.rs.models.projectrdequipment.ProjectRdEquipmentModel;
import com.yskc.rs.models.proposal.ProposalListModel;
import com.yskc.rs.models.stage.StageModel;
import com.yskc.rs.models.summary.DataFundsModel;
import com.yskc.rs.models.summary.DataReportModel;
import com.yskc.rs.models.voucher.ProjectVoucherModel;
import com.yskc.rs.models.voucher.VoucherAppendModel;
import com.yskc.rs.service.CompanySettingService;
import com.yskc.rs.service.ProjectRdEmployeeService;
import com.yskc.rs.service.ProjectRdEquipmentService;
import com.yskc.rs.service.ProjectService;
import com.yskc.rs.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-15 10:35
 * @Description: 项目业务实现层
 */
@Service
public class ProjectServiceImpl implements ProjectService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String SHANG_HAI_CODE = "310000";
    @Autowired
    private ProjectYearInfoDao projectYearInfoDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private SummaryDao summaryDao;
    @Autowired
    private InitMemberDao initMemberDao;
    @Autowired
    private InitEquipmentDao initEquipmentDao;
    @Autowired
    private SysDocumentDao sysDocumentDao;
    @Autowired
    private RsConfig rsConfig;
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private StageDao stageDao;
    @Autowired
    private RdEmployeeDao rdEmployeeDao;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private CommonService commonService;
    @Autowired
    private RdVoucherDao rdVoucherDao;
    @Autowired
    private PatentDetailDao patentDetailDao;
    @Autowired
    private ProjectMaterialDao projectMaterialDao;
    @Autowired
    private ProjectRdEquipmentDao projectRdEquipmentDao;
    @Autowired
    private ProjectEnergyDao projectEnergyDao;
    @Autowired
    private EnergyDao energyDao;
    @Autowired
    private ProjectRdEmployeeDao projectRdEmployeeDao;
    @Autowired
    private ProjectRdEmployeeService projectRdEmployeeService;
    @Autowired
    private ProjectRdEquipmentService projectRdEquipmentService;
    @Autowired
    private CompanySettingService companySettingService;
    @Autowired
    private ProjectOutsourcingDao projectOutsourcingDao;
    @Autowired
    private AuditReportDao auditReportDao;
    @Autowired
    private CDocumentDao cDocumentDao;
    @Autowired
    private ReportDao reportDao;
    @Autowired
    ProjectDocFileDao projectDocFileDao;
    @Autowired
    ProjectDocFileDataDao projectDocFileDataDao;
    @Autowired
    DocFileMeetingDao docFileMeetingDao;
    @Autowired
    private BudgetDao budgetDao;
    @Autowired
    private ProjectChangeDao projectChangeDao;

    @Autowired
    private SysDictionaryDao sysDictionaryDao;

    @Autowired
    private ProposalListDao proposalListDao;


    @Override
    public Boolean update(ProjectModel model, UserInfo userInfo) throws OwnerException {
        Date date = new Date();
        ProjectEntity projectEntity = projectDao.selectById(model.getId());
        if (projectEntity != null) {
            checkProjectAudit(Arrays.asList(model.getId()), Arrays.asList(projectEntity), userInfo.getUserSource());
            //commonService.checkAuditModify(userInfo.getCompanyId(), projectEntity.getBeginYear(), AuditModeEnum.RD_PROJECT, userInfo.getUserSource());
            String rdTitle = projectEntity.getRdTitle();
            Integer rdIndex = projectEntity.getRdIndex();
            String rdNumber = projectEntity.getRdNumber();
            Integer baseYear = projectEntity.getBeginYear();
            int sign = 0;//标识是否需要修改父项目
            //修改的项目周期是否存在归集费用
            checkRdFundAndMembers(projectEntity.getBeginDate(), model.getBeginDate(), projectEntity.getEndDate(), model.getEndDate(), model.getId());
            if (model.getDeptId() == null) {
                model.setDeptId(0);
            }
            //TODO:当无试制时仍然会有试制日期
            BeanUtil.copyProperties(model, projectEntity);
            projectEntity.setBeginYear(cn.hutool.core.date.DateUtil.year(projectEntity.getBeginDate()));
            projectEntity.setEndYear(cn.hutool.core.date.DateUtil.year(projectEntity.getEndDate()));
            ProjectEntity parent = null;
            if (!baseYear.equals(projectEntity.getBeginYear()) && (projectEntity.getHasChild() || projectEntity.getParentId() != 0)) {
                throw new OwnerException("项目开始日期不能提前到" + baseYear + "年之前");
            }
            if (projectEntity.getParentId() == 0) {
                rdTitle = getRdTitle(projectEntity.getBeginYear(), model.getRdIndex(), userInfo.getCompanyId(), null);
            } else {
                if (model.getRdNumber() != null && !model.getRdNumber().equals(rdNumber)) {
                    rdTitle = getRdTitle(projectEntity.getBeginYear(), rdIndex, userInfo.getCompanyId(), model.getRdNumber() != null ? model.getRdNumber() : "");
                }
                parent = projectDao.selectById(projectEntity.getParentId());
                //更新父项目项目开始日期
                if (parent.getBeginDate().getTime() > projectEntity.getBeginDate().getTime()) {
                    parent.setBeginDate(projectEntity.getBeginDate());
                    sign = 1;
                }
                //更新父项目的结束日期
                if (parent.getEndDate().getTime() < projectEntity.getEndDate().getTime()) {
                    parent.setEndDate(projectEntity.getEndDate());
                    parent.setEndYear(projectEntity.getEndYear());
                    sign = 1;
                }
                parent.setLastUpdatorId(userInfo.getUserId());
                parent.setLastUpdateTime(new Date());
                parent.setMsLastUpdatorId(userInfo.getMsUserId());
            }
            projectEntity.setRdTitle(rdTitle);
            projectEntity.setLastUpdateTime(new Date());
            projectEntity.setMsLastUpdatorId(userInfo.getMsUserId());
            projectEntity.setLastUpdatorId(userInfo.getUserId());
            projectEntity.setMasterENumber(model.getMasterENumber() != null ? model.getMasterENumber() : "");

            TransactionStatus transactionStatus = TransactionUtils.newTransaction();
            try {
                projectDao.updateById(projectEntity);
                if (parent != null && sign == 1) {
                    projectDao.updateById(parent);
                }
                if (!StringUtils.isEmpty(projectEntity.getMasterENumber())) {
                    // 项目基本信息更新负责人
                    List< InitMemberEntity > initMemberEntitys = initMemberDao.queryByProjectIdAndEnumber(projectEntity.getId(), projectEntity.getMasterENumber(), model.getCurrentYear());
                    if (CollectionUtils.isEmpty(initMemberEntitys)) {
                        InitMemberEntity initMember = new InitMemberEntity(userInfo.getUserId(), userInfo.getMsUserId(), date, projectEntity.getMasterENumber(),
                                true, userInfo.getCompanyId(), model.getId(), model.getCurrentYear(), null);
                        initMemberDao.cleanProjectMaster(projectEntity.getId(), new Date(), userInfo.getMsUserId(), userInfo.getUserId(), model.getCurrentYear());
                        initMemberDao.insert(initMember);
                    } else {
                        InitMemberEntity existInitMember = initMemberEntitys.get(0);
                        if (!existInitMember.getMaster()) {
                            initMemberDao.cleanProjectMaster(projectEntity.getId(), new Date(), userInfo.getMsUserId(), userInfo.getUserId(), model.getCurrentYear());
                            existInitMember.setMaster(true);
                            existInitMember.setLastUpdateTime(date);
                            existInitMember.setLastUpdatorId(userInfo.getUserId());
                            existInitMember.setMsLastUpdatorId(userInfo.getMsUserId());
                            initMemberDao.updateById(existInitMember);
                        }
                    }
                    ProjectYearInfoEntity yearInfo = projectYearInfoDao.getByYear(model.getId(), model.getCurrentYear());
                    if (yearInfo != null) {
                        if (yearInfo.getMasterENumber() == null || !yearInfo.getMasterENumber().equals(projectEntity.getMasterENumber())) {
                            yearInfo.setMasterENumber(projectEntity.getMasterENumber());
                            yearInfo.update(userInfo.getUserId(), userInfo.getMsUserId(), date);
                            projectYearInfoDao.updateById(yearInfo);
                        }
                    } else {
                        yearInfo = new ProjectYearInfoEntity(model.getCurrentYear(), projectEntity.getMasterENumber(), null, model.getId(), userInfo.getCompanyId());
                        yearInfo.create(userInfo.getUserId(), userInfo.getMsUserId(), date);
                        projectYearInfoDao.insert(yearInfo);
                    }
                }
                TransactionUtils.commit(transactionStatus);
            } catch (Exception ex) {
                TransactionUtils.rollback(transactionStatus);
                logger.error("updateProject", ex);
            }
            return true;
        } else {
            return false;
        }
    }

    private void checkRdFundAndMembers(Date oldBeginDate, Date newBeginDate, Date oldEndDate, Date newEndDate, Integer projectId) throws OwnerException {
        Date oldMonth = DateUtil.getMonthFirstDay(oldBeginDate);
        Date newMonth = DateUtil.getMonthFirstDay(newBeginDate);
        if (oldMonth.compareTo(newMonth) < 0) {
            checkMembers(cn.hutool.core.date.DateUtil.year(oldBeginDate), cn.hutool.core.date.DateUtil.year(newBeginDate), projectId);
            if (summaryDao.countExist(oldMonth, newMonth, projectId) > 0) {
                throw new OwnerException(MessageFormat.format("{0}~{1}存在归集费用，不能调整项目周期。",
                        DateUtil.format(oldBeginDate, DateUtil.DEFAULT_DATE_FORMAT), DateUtil.format(DateUtil.getMonthLastDay(cn.hutool.core.date.DateUtil.offsetMonth(newBeginDate, -1)), DateUtil.DEFAULT_DATE_FORMAT)));
            }
        }
        // 新旧结束日期都向前调整一个月
        oldMonth = cn.hutool.core.date.DateUtil.offsetMonth(DateUtil.getMonthFirstDay(oldEndDate), 1);
        newMonth = cn.hutool.core.date.DateUtil.offsetMonth(DateUtil.getMonthFirstDay(newEndDate), 1);
        if (oldMonth.compareTo(newMonth) > 0) {
            checkMembers(cn.hutool.core.date.DateUtil.year(newEndDate) + 1, cn.hutool.core.date.DateUtil.year(oldEndDate) + 1, projectId);
            if (summaryDao.countExist(newMonth, oldMonth, projectId) > 0) {
                throw new OwnerException(MessageFormat.format("{0}~{1}存在归集费用，不能调整项目周期。",
                        DateUtil.format(cn.hutool.core.date.DateUtil.offsetMonth(newEndDate, 1), DateUtil.DEFAULT_DATE_FORMAT), DateUtil.format(oldEndDate, DateUtil.DEFAULT_DATE_FORMAT)));
            }
        }
    }

    /**
     * 检查年份区间是否存在项目人员及设备
     *
     * @param beginYear
     * @param endYear
     * @param projectId
     * @throws OwnerException
     */
    private void checkMembers(int beginYear, int endYear, Integer projectId) throws OwnerException {
        if (beginYear < endYear) {
            Integer year = initMemberDao.getExistYear(beginYear, endYear, projectId);
            if (null != year) {
                throw new OwnerException(MessageFormat.format("{0,number,#}年存在研发人员及资产清单，不能调整项目周期。", year));
            }
        }
    }


    @Override
    public List< EmployeeSelectModel > getEmployeeList(Integer companyId, Integer year, String ename) {
        if (StrUtil.isEmpty(ename)) {
            return new ArrayList<>();
        }
        return rdEmployeeDao.getEmployeeSelect(companyId, year, ename);
    }

    @Override
    public void exportProject(UserInfo userInfo, Integer year, OutputStream out, String path) throws OwnerException {
        List< ProjectListModel > result = projectDao.queryListByYear(year, userInfo.getCompanyId());
        List< HighTecIndustryModel > htms = sysDictionaryDao.getHighTecIndustryModels();
        Map< String, String > map = new HashMap<>();
        String[] split;
        Map< String, String > ResultMap = new HashMap<>();
        Map< Integer, String > TargetsMap = new HashMap<>();
        Map< Integer, String > ProjectSourceMap = new HashMap<>();

        ResultMap.put("01", ("论文或专著"));
        ResultMap.put("02", ("新产品、新工艺等推广与示范活动"));
        ResultMap.put("03", ("对已有产品、工艺等进行一般性改进"));
        ResultMap.put("04", ("对已有产品、工艺等实现突破性变革"));
        ResultMap.put("05", ("软件著作权"));
        ResultMap.put("06", ("应用软件"));
        ResultMap.put("07", ("中间件或新算法"));
        ResultMap.put("08", ("基础软件"));
        ResultMap.put("09", ("发明专利"));
        ResultMap.put("10", ("实用新型专利或外观设计专利"));
        ResultMap.put("11", ("带有技术、工艺参数的图纸、技术标准、操作规范、技术论证、研究报告、咨询评价"));
        ResultMap.put("12", ("自主研制的新产品原型或样机、样件、样品、配方、新装置"));
        ResultMap.put("13", ("自主开发的新技术或新工艺、新工法、新服务"));
        ResultMap.put("14", ("其他"));

        TargetsMap.put(1, ("科学原理的探索、发现"));
        TargetsMap.put(2, ("技术原理的研究"));
        TargetsMap.put(3, ("开发全新产品"));
        TargetsMap.put(4, ("增加产品功能或提高性能"));
        TargetsMap.put(5, ("提高劳动生产率"));
        TargetsMap.put(6, ("减少能源消耗或提高能源使用效率"));
        TargetsMap.put(7, ("节约原材料"));
        TargetsMap.put(8, ("减少环境污染"));
        TargetsMap.put(9, ("其他"));

        ProjectSourceMap.put(1, ("本企业自选项目"));
        ProjectSourceMap.put(2, ("政府部门科技项目"));
        ProjectSourceMap.put(3, ("其他企业（单位）委托项目"));
        ProjectSourceMap.put(4, ("境外项目"));
        ProjectSourceMap.put(5, ("其他项目"));
        for (HighTecIndustryModel htm : htms) {
            map.put(htm.getId(), htm.getLabel());
        }
        for (ProjectListModel plm : result) {
            if (plm.getFormula() != null) {
                Map< Integer, String > valueType = ProjectTypeEnum.valueType;
                String typeName = valueType.get(plm.getFormula());
                plm.setFormulaModel(typeName);
            }
            if (plm.getResult() != null) {
                plm.setResult(ResultMap.get(plm.getResult()));
            }
            if (plm.getTargets() != null) {
                plm.setTargetsModel(TargetsMap.get(plm.getTargets()));
            }
            if (plm.getProjectSource() != null) {
                plm.setProjectSourceModel(ProjectSourceMap.get(plm.getProjectSource()));
            }

            if (plm.getTecIndustry() != null) {
                String tecIndustry = plm.getTecIndustry();
                split = tecIndustry.split(",");
                ArrayList< String > list = new ArrayList<>();
                for (String s : split) {
                    if (map.get(s) != null) {
                        list.add(map.get(s));
                    } else {
                        throw new OwnerException("高新领域项数据错误！");
                    }
                }
                plm.setTecIndustry(StringUtils.arrayToDelimitedString(list.toArray(), "/"));
            }
            if (plm.getFullname() == null) {
                String fullName;
                List< String > fullNames = new ArrayList<>();
                fullNames.add(plm.getDeptName());
                fullNames.add(plm.getWorkshop());
                fullNames.add(plm.getProductLine());
                fullNames.add(plm.getProcessSection());
                fullNames.removeAll(Collections.singleton(null));
                fullNames.removeAll(Collections.singleton(""));
                fullName = String.join("/", fullNames);
                plm.setFullname(fullName);
            }
            if (!plm.getTrialProd()){
                plm.settBeginDate(null);
                plm.settEndDate(null);
            }
        }
        Map< String, Object > dataMap = new HashMap<>();
        dataMap.put("result", result);
        dataMap.put("title", userInfo.getCompanyName() + year + "年项目列表");
        YsExcelUtils.generalReport(dataMap, path, (workbook) -> {
            if (workbook != null) {
                try {
                    workbook.write(out);
                    workbook.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        });

//        return projectDao.queryListByYear(year, userInfo.getCompanyId());
//        List<ProjectExcel> projectExcelList = new ArrayList<>();
//        for (ProjectListModel model : projectModelList) {
//            ProjectExcel projectExcel = new ProjectExcel();
//            BeanUtil.copyProperties(model, projectExcel);
//            projectExcel.setRdStr(model.getRdTitle());
//            projectExcel.setTrialProd(model.getTrialProd() ? "是" : "否");
//            projectExcel.setEstimateExpense(model.getEstimateExpense() != null ? model.getEstimateExpense() / 10000 : null);
//            projectExcelList.add(projectExcel);
//        }
//        return projectExcelList;
    }

    @Override
    public boolean addBatch(UserInfo info, List< TypeModel > typeModel) {
        List< SummaryEntity > entities = new ArrayList<>();
        for (int i = 0; i < typeModel.size(); i++) {
            SummaryEntity entity = new SummaryEntity();
            TypeModel model = typeModel.get(i);
            entity.setProjectId(model.getProjectId());
            entity.setCreatorId(info.getUserId());
            entity.setCreateTime(new Date());
            entity.setLastUpdatorId(info.getUserId());
            entity.setLastUpdateTime(new Date());
            entity.setMonth(DateUtil.getMonthFirstDay(model.getMonth()));
            entity.setRdType(model.getType());
            entity.setRdFunds(model.getValue() != null ? model.getValue() : BigDecimal.ZERO);
            entity.setUpdatorId(info.getUserId());
            entity.setMsCreatorId(info.getMsUserId());
            entity.setMsLastUpdatorId(info.getMsUserId());
            entity.setAccountNumber("");
            entities.add(entity);
        }
        summaryDao.insertOrUpdate(entities);
        return true;
    }

    @Override
    public List< TypeModel > querySummaryByProjectId(Integer projectId) {
        return summaryDao.querySummaryByProjectId(projectId);
    }

    @Override
    public boolean deleteProject(UserInfo info, DelProjectModel model) throws OwnerException {
        Integer companyId = info.getCompanyId();
        // commonService.checkAuditModify(companyId, model.getCurrentYear(), AuditModeEnum.RD_PROJECT, info.getUserSource());
        List< Integer > projectIds = model.getProjectIds();
        if (CollectionUtils.isEmpty(projectIds)) {
            throw new OwnerException("请选择要删除的项目！");
        }
        List< ProjectEntity > projectEntities = projectDao.selectBatchIds(projectIds);
        if (CollectionUtils.isEmpty(projectEntities)) {
            throw new OwnerException("项目不存在或已删除，删除失败");
        }
        for (ProjectEntity project : projectEntities) {
            if (!project.getBeginYear().equals(model.getCurrentYear())) {
                throw new OwnerException("当前只能删除" + model.getCurrentYear() + "年的项目");
            }
        }
        //提交审核，进行中，审核通过的不能删除
        checkProjectAudit(model.getProjectIds(), projectEntities, info.getUserSource());
        List< TypeModel > typeModels = summaryDao.getByProjectIds(projectIds);
        if (!CollectionUtils.isEmpty(typeModels)) {
            throw new OwnerException("已存在[创新项目-研发费用核算-数据归集]中，不能删除!");
        }
        List< SysDocumentEntity > docModels = sysDocumentDao.queryDocByProjectIds(projectIds);
        if (!CollectionUtils.isEmpty(docModels)) {
            throw new OwnerException("已有上传的文档，不能删除!");
        }
        List< InitMemberEntity > initMemberEntities = initMemberDao.queryIMemberByProjectIds(projectIds);
        if (!CollectionUtils.isEmpty(initMemberEntities)) {
            throw new OwnerException("已有项目成员，不能删除!");
        }
        List< InitEquipmentEntity > initEquipmentEntities = initEquipmentDao.queryIEquipmentByProjectIds(projectIds);
        if (!CollectionUtils.isEmpty(initEquipmentEntities)) {
            throw new OwnerException("已有资产清单，不能删除!");
        }
        //1
        List< StageModel > stageModels = stageDao.getByProjectIds(companyId, projectIds);
        if (!CollectionUtils.isEmpty(stageModels)) {
            throw new OwnerException("已有项目阶段，不能删除!");
        }
        List< ProjectEntity > childList = projectDao.getChildByParent(projectIds, companyId);
        if (!CollectionUtils.isEmpty(childList)) {
            throw new OwnerException("项目存在子项目，不能删除!");
        }
        List< RdVoucherEntity > rdVoucherEntities = rdVoucherDao.getListByProject(projectIds, companyId);
        if (!CollectionUtils.isEmpty(rdVoucherEntities)) {
            throw new OwnerException("项目关联研发凭证，不能删除!");
        }
        List< PatentDetailEntity > patentDetailEntities = patentDetailDao.getListByProject(projectIds, companyId);
        if (!CollectionUtils.isEmpty(patentDetailEntities)) {
            throw new OwnerException("项目存在专利，不能删除!");
        }
        if (projectOutsourcingDao.countOutsourcing(projectIds) > 0) {
            throw new OwnerException("项目存在委外费用，不能删除!");
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction();
            summaryDao.deleteByProjectIds(projectIds);
            projectDao.deleteBatchIds(projectIds);
            initMemberDao.deleteMaster(projectIds);
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException("删除失败，请联系管理员。");
        }
    }

    /**
     * 检查项目审核状态
     *
     * @param projectIds
     * @param projects
     * @param userSource
     * @throws OwnerException
     */
    private void checkProjectAudit(List< Integer > projectIds, List< ProjectEntity > projects, Integer userSource) throws OwnerException {
        if (userSource == 1) {
            List< AuditReportEntity > projectAudits = auditReportDao.getProjectAudits(projectIds, 4);
            Map< Integer, Integer > auditStatusMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(projectAudits)) {
                auditStatusMap = projectAudits.stream().collect(Collectors.toMap(e -> e.getProjectId(), e -> e.getStatus()));
            }
            for (ProjectEntity project : projects) {
                if (auditStatusMap.containsKey(project.getId())) {
                    Integer status = auditStatusMap.get(project.getId());
                    if (!FlowInstanceStatusEnum.canModify(status)) {
                        throw new OwnerException(MessageFormat.format("项目：{0}-{1}已提交审核，操作失败！", project.getRdTitle(), project.getPname()));
                    }
                }
            }

        }
    }


    @Override
    public boolean checkRD(Integer companyId, Integer rdIndex, Integer year, Integer projectId) {
        List< ProjectModel > projectModel = projectDao.selectRd(companyId, rdIndex, year, projectId);
        if (projectModel != null && projectModel.size() > 0) {
            return false;
        }
        return true;
    }

    @Override
    public boolean checkPname(String pname, Integer projectId) {
        return projectDao.countPname(pname, projectId) == 0;
    }

    @Override
    public Integer selectMaxRd(Integer companyId, Integer year) {
        Integer maxRd = projectDao.selectMaxRd(companyId, year);
        if (maxRd == null) {
            maxRd = 1;
        } else {
            maxRd += 1;
        }
        return maxRd;
    }

    //////////////////////////////////////////////////Report迁移代码//////////////////////////////////////////////////

    /**
     * 根据年份获取项目列表
     *
     * @param companyId
     * @param year
     * @param formula
     * @return
     * @throws OwnerException
     */
    @Override
    public List< ProjectListModel > getProjectsByYear(Integer companyId, Integer year, Integer formula) {
        return projectDao.getProjectsByYear(year, companyId, formula, "");
    }

    /**
     * 查询项目列表
     *
     * @param companyId
     * @param year
     * @param pname
     * @return
     */
    @Override
    public List< ProjectListModel > queryProject(Integer companyId, Integer year, String pname, String rd) {
        List< ProjectListModel > projectModelList = projectDao.getProjectsByYear(year, companyId, null, pname);
        for (ProjectListModel p : projectModelList) {
            if (p.gettBeginDate() != null && p.gettEndDate() != null) {
                p.setHaveTrialProd(MessageFormat.format("{0}-{1}", cn.hutool.core.date.DateUtil.formatDate(p.gettBeginDate()), cn.hutool.core.date.DateUtil.formatDate(p.gettEndDate())));
            } else {
                p.setHaveTrialProd("无");
            }
        }
        return projectModelList;
    }

    @Override
    public List< ProjectListModel > getProjectList(Integer companyId, QueryProjectListModel query) {
        List< ProjectListModel > projectList = projectDao.getProjectList(companyId, query);
        List< ProjectListModel > parentList = new ArrayList<>();
        List< Integer > ids = new ArrayList<>();
        Map< Integer, List< ProjectListModel > > dataMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(projectList)) {
            projectList.forEach(item -> {
                ids.add(item.getId());
            });
            List< ProposalListModel > proposalList = proposalListDao.getByProjectIds(ids);
            Map< Integer, List< String > > map = new HashMap<>();
            if (!CollectionUtils.isEmpty(proposalList)) {
                proposalList.forEach(item -> {
                    Integer projectId = item.getProjectId();
                    String pname = item.getPname();
                    if (map.containsKey(projectId)) {
                        map.get(projectId).add(pname);
                    } else {
                        List< String > pnames = new ArrayList<>();
                        pnames.add(pname);
                        map.put(projectId, pnames);
                    }
                });
            }
            for (ProjectListModel patentModel : projectList) {
                Integer id = patentModel.getId();
                if (map.containsKey(id)) {
                    patentModel.setProName(org.apache.commons.lang3.StringUtils.join(map.get(id), ","));
                }
                if (patentModel.getParentId() != 0) {
                    if (dataMap.containsKey(patentModel.getParentId())) {
                        dataMap.get(patentModel.getParentId()).add(patentModel);
                    } else {
                        List< ProjectListModel > listModels = new ArrayList<>();
                        listModels.add(patentModel);
                        dataMap.put(patentModel.getParentId(), listModels);
                    }
                } else {
                    parentList.add(patentModel);
                }
                ids.add(patentModel.getId());
            }
            for (ProjectListModel model : parentList) {
                if (dataMap.containsKey(model.getId())) {
                    int seq = 0;
                    List< ProjectListModel > models = dataMap.get(model.getId());
                    for (ProjectListModel projectListModel : models) {
                        seq += 1;
                        projectListModel.setSeq(seq);
                    }
                    model.setChildren(models);
                }
            }
        }
        return parentList;
    }

    @Override
    public Boolean addProject(ProjectModel model, UserInfo userInfo) throws OwnerException {
        Date date = new Date();
        ProjectEntity projectEntity = new ProjectEntity();
        if (model.getDeptId() == null) {
            model.setDeptId(0);
        }
        //TODO:当无试制时仍然会有试制日期
        BeanUtil.copyProperties(model, projectEntity);
        Calendar c = Calendar.getInstance();
        c.setTime(projectEntity.getBeginDate());
        Integer beginYear = c.get(Calendar.YEAR);
        // commonService.checkAuditModify(userInfo.getCompanyId(), beginYear, AuditModeEnum.RD_PROJECT, userInfo.getUserSource());
        projectEntity.setBeginYear(beginYear);
        c.setTime(projectEntity.getEndDate());
        projectEntity.setEndYear(c.get(Calendar.YEAR));
        projectEntity.setCreateTime(date);
        projectEntity.setCreatorId(userInfo.getUserId());
        projectEntity.setMsCreatorId(userInfo.getMsUserId());
        projectEntity.setLastUpdateTime(date);
        projectEntity.setLastUpdatorId(userInfo.getUserId());
        projectEntity.setMsLastUpdatorId(userInfo.getMsUserId());
        projectEntity.setMasterENumber(model.getMasterENumber() != null ? model.getMasterENumber() : "");
        String rdTitle = getRdTitle(beginYear, model.getRdIndex(), userInfo.getCompanyId(), null);
        projectEntity.setRdTitle(rdTitle);
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            projectDao.insert(projectEntity);
            if (!StringUtils.isEmpty(projectEntity.getMasterENumber())) {
                InitMemberEntity initMemberEntity = new InitMemberEntity(userInfo.getUserId(), userInfo.getMsUserId(),
                        date, projectEntity.getMasterENumber(), true, userInfo.getCompanyId(), projectEntity.getId(), projectEntity.getBeginYear(), null);
                initMemberDao.insert(initMemberEntity);
                ProjectYearInfoEntity yearInfo = projectYearInfoDao.getByYear(projectEntity.getId(), projectEntity.getBeginYear());
                if (yearInfo != null) {
                    if (yearInfo.getMasterENumber() == null || !yearInfo.getMasterENumber().equals(projectEntity.getMasterENumber())) {
                        yearInfo.setMasterENumber(projectEntity.getMasterENumber());
                        yearInfo.update(userInfo.getUserId(), userInfo.getMsUserId(), date);
                        projectYearInfoDao.updateById(yearInfo);
                    }
                } else {
                    yearInfo = new ProjectYearInfoEntity(projectEntity.getBeginYear(), projectEntity.getMasterENumber(), null, projectEntity.getId(), userInfo.getCompanyId());
                    yearInfo.create(userInfo.getUserId(), userInfo.getMsUserId(), date);
                    projectYearInfoDao.insert(yearInfo);
                }
            }
//            if (!StringUtils.isEmpty(projectEntity.getMasterENumber())) {
//               List<InitMemberEntity> initMembers = setInitMember(projectEntity, userInfo);
//                initMemberDao.addbatch(initMembers);
//            }
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            TransactionUtils.rollback(transactionStatus);
            logger.error("addProject", ex);
        }

        return true;
    }

    @Override
    public String importProject(UserInfo info, List< ProjectExcel > projectExcels, Integer year) throws OwnerException {
        // commonService.checkAuditModify(info.getCompanyId(), year, AuditModeEnum.RD_PROJECT, info.getUserSource());
        if (projectExcels.size() > 0) {
            Calendar c = Calendar.getInstance();
            for (ProjectExcel projectExcel : projectExcels) {
                //检测RD格式是否正确
                int rdNum = 0;
                if (projectExcel.getRdStr().length() > 6) {
                    if (Pattern.compile("[0-9]*").matcher(projectExcel.getRdStr().substring(0, 4)).matches()) {
                        if (year != Integer.parseInt(projectExcel.getRdStr().substring(0, 4))) {
                            throw new OwnerException("RD:" + projectExcel.getRdStr() + "不是当前年份的项目,请修改后重新导入!");
                        }
                    } else {
                        throw new OwnerException("RD:" + projectExcel.getRdStr() + "格式不对,请修改后重新导入!例如:1或2019RD01");
                    }
                    rdNum = Integer.parseInt(projectExcel.getRdStr().substring(6));
                } else if (projectExcel.getRdStr().length() < 3) {
                    if (Pattern.compile("[0-9]*").matcher(projectExcel.getRdStr()).matches()) {
                        rdNum = Integer.parseInt(projectExcel.getRdStr());
                    } else {
                        throw new OwnerException("RD:" + projectExcel.getRdStr() + "格式不对,请修改后重新导入!例如:1或2019RD01");
                    }
                } else {
                    throw new OwnerException("RD:" + projectExcel.getRdStr() + "格式不对,请修改后重新导入!例如:1或2019RD01");
                }

                c.setTime(projectExcel.getBeginDate());
                int beginYear = c.get(Calendar.YEAR);
                if (year != beginYear) {
                    throw new OwnerException("RD:" + projectExcel.getRdStr() + "的开始日期不是当前年份的日期,请修改后重新导入!");
                }
                if (projectExcel.getBeginDate().compareTo(projectExcel.getEndDate()) > 0) {
                    throw new OwnerException("RD:" + projectExcel.getRdStr() + "的开始日期大于结束日期,请修改后重新导入!");
                }
                if ("是".equals(projectExcel.getTrialProd())) {
                    if (projectExcel.gettBeginDate() == null) {
                        throw new OwnerException("RD:" + projectExcel.getRdStr() + "选择试制则必须指定试制开始时间");
                    }
                    if (projectExcel.gettEndDate() == null) {
                        throw new OwnerException("RD:" + projectExcel.getRdStr() + "选择试制则必须指定试制结束时间");
                    }
                }
                if (projectExcel.gettBeginDate() != null && projectExcel.gettEndDate() != null && projectExcel.gettBeginDate().compareTo(projectExcel.gettEndDate()) > 0) {
                    throw new OwnerException("RD:" + projectExcel.getRdStr() + "的开始试制日期大于结束试制日期,请修改后重新导入!");
                }
                if (projectExcel.gettBeginDate() != null && projectExcel.gettBeginDate().compareTo(projectExcel.getEndDate()) > 0) {
                    throw new OwnerException("RD:" + projectExcel.getRdStr() + "的开始试制日期大于项目结束日期,请修改后重新导入!");
                }
                if (projectExcel.gettEndDate() != null && projectExcel.gettEndDate().compareTo(projectExcel.getEndDate()) > 0) {
                    throw new OwnerException("RD:" + projectExcel.getRdStr() + "的结束试制日期大于项目结束日期,请修改后重新导入!");
                }
                if (projectExcel.gettBeginDate() != null && projectExcel.gettBeginDate().compareTo(projectExcel.getBeginDate()) < 0) {
                    throw new OwnerException("RD:" + projectExcel.getRdStr() + "的开始试制日期小于项目开始日期,请修改后重新导入!");
                }
                if (projectExcel.gettEndDate() != null && projectExcel.gettEndDate().compareTo(projectExcel.getBeginDate()) < 0) {
                    throw new OwnerException("RD:" + projectExcel.getRdStr() + "的结束试制日期小于项目开始日期,请修改后重新导入!");
                }

                projectExcel.setRdStr(rdNum + "");
            }

            //根据项目名判断是否重复
            Map< String, ProjectExcel > map = new HashMap<>();
            for (ProjectExcel projectExcel : projectExcels) {
                if (map.get(projectExcel.getRdStr()) != null) {
                    throw new OwnerException("有重复的RD:" + projectExcel.getRdStr() + ",请修改后重新导入!");
                }
                if (map.get(projectExcel.getPname()) != null) {
                    throw new OwnerException("有重复的RD:" + projectExcel.getPname() + ",请修改后重新导入!");
                }
                map.put(projectExcel.getRdStr(), projectExcel);
                map.put(projectExcel.getPname(), projectExcel);

            }
            Date date = new Date();
            Map< String, ProjectListModel > projectModelMap = new HashMap<>();
            Map< String, Integer > rdDeptMap = commonService.initOrgFullPathMap(info.getCompanyId(), OrgEnum.RD_DEPT, year);
            Map< Integer, ProjectListModel > rdIndexMap = new HashMap<>();
            List< ProjectListModel > projectListModels = projectDao.getProjectsForImport(year, info.getCompanyId());
            if (projectListModels != null && projectListModels.size() > 0) {
                for (ProjectListModel pjl : projectListModels) {
                    projectModelMap.put(pjl.getPname(), pjl);
                    rdIndexMap.put(pjl.getRdIndex(), pjl);
                }
            }
            List< ProjectEntity > insertProjectEntitys = new ArrayList<>();
            List< ProjectEntity > updateProjectEntitys = new ArrayList<>();
            List< String > rdList = new ArrayList<>();
            List< String > pnameRdList = new ArrayList<>();
            List< RdEmployeeModel > rdEmployeeModels = rdEmployeeDao.queryByCompanyIdAndYear(info.getCompanyId(), year);
            Map< String, RdEmployeeModel > rdEmployeeMap = rdEmployeeModels.stream().collect(Collectors.toMap(RdEmployeeModel::getEname, Function.identity(), (existing, replacement) -> existing));
            for (int i = 0; i < projectExcels.size(); i++) {
                ProjectExcel projectExcel = projectExcels.get(i);
                int rdNum = Integer.parseInt(projectExcel.getRdStr());
                String rd = projectExcel.getRdStr();
                if (Integer.parseInt(projectExcel.getRdStr()) < 10) {
                    rd = "0" + projectExcel.getRdStr();
                }
                //不存在该项目名称
                if (projectModelMap.get(projectExcel.getPname()) == null) {
                    ProjectEntity projectEntity = new ProjectEntity();
                    projectEntity.setPname(projectExcel.getPname());
                    projectEntity.setRdIndex(rdNum);
                    projectEntity.setBeginDate(projectExcel.getBeginDate());
                    projectEntity.setEndDate(projectExcel.getEndDate());
                    c.setTime(projectEntity.getBeginDate());
                    projectEntity.setBeginYear(c.get(Calendar.YEAR));
                    c.setTime(projectEntity.getEndDate());
                    projectEntity.setEndYear(c.get(Calendar.YEAR));
                    projectEntity.setTrialProd(projectExcel.getTrialProd() != null && projectExcel.getTrialProd().equals("是"));
                    projectEntity.settBeginDate(projectExcel.gettBeginDate());
                    projectEntity.settEndDate(projectExcel.gettEndDate());
                    projectEntity.setWorkshop(projectExcel.getWorkshop());
                    projectEntity.setProductLine(projectExcel.getProductLine());
                    projectEntity.setProcessSection(projectExcel.getProcessSection());
                    projectEntity.setRdNumber(projectExcel.getRdNumber());
                    projectEntity.setDeptName(projectExcel.getDeptName());
                    if (rdIndexMap.get(rdNum) != null) {
                        rdList.add(projectEntity.getBeginYear() + "RD" + rd + "[" + projectExcel.getPname() + "]");
                        continue;
                    }

                    projectEntity.setCompanyId(info.getCompanyId());
                    projectEntity.setCreateTime(date);
                    projectEntity.setCreatorId(info.getUserId());
                    projectEntity.setMsCreatorId(info.getMsUserId());
                    projectEntity.setLastUpdateTime(date);
                    projectEntity.setLastUpdatorId(info.getUserId());
                    projectEntity.setMsLastUpdatorId(info.getMsUserId());
                    if (projectExcel.getEstimateExpense() != null) {
                        projectEntity.setEstimateExpense(projectExcel.getEstimateExpense() * 10000);
                    }

                    if (StringUtils.isEmpty(projectExcel.getEname())) {
                        projectEntity.setMasterENumber("");
                    } else {
                        RdEmployeeModel employeeModel = rdEmployeeMap.get(projectExcel.getEname());
                        if (employeeModel != null) {
                            projectEntity.setMasterENumber(employeeModel.getEnumber());
                        } else {
                            pnameRdList.add(projectEntity.getBeginYear() + "RD" + rd + "[" + projectExcel.getEname() + "]");
                            continue;
                        }
                    }
                    Integer rdDeptId = ToolUtils.getOrgId(rdDeptMap, projectExcel.getRdDeptName());
                    projectEntity.setRdDeptId(rdDeptId != null ? rdDeptId : -1);
                    projectEntity.setProjectSource(1);
                    projectEntity.setFormula(10);
                    projectEntity.setRdTitle(getRdTitle(projectEntity.getBeginYear(), projectEntity.getRdIndex(), info.getCompanyId(), null));
                    insertProjectEntitys.add(projectEntity);
                } else {
                    //存在该项目名
                    ProjectEntity projectEntity = new ProjectEntity();
                    ProjectListModel projectListModel = projectModelMap.get(projectExcel.getPname());
                    projectEntity.setBeginDate(projectExcel.getBeginDate());
                    projectEntity.setEndDate(projectExcel.getEndDate());
                    c.setTime(projectEntity.getBeginDate());
                    projectEntity.setBeginYear(c.get(Calendar.YEAR));
                    c.setTime(projectEntity.getEndDate());
                    projectEntity.setEndYear(c.get(Calendar.YEAR));
                    projectEntity.setTrialProd("是".equals(projectExcel.getTrialProd()));
                    projectEntity.settBeginDate(projectExcel.gettBeginDate());
                    projectEntity.settEndDate(projectExcel.gettEndDate());
                    projectEntity.setWorkshop(projectExcel.getWorkshop());
                    projectEntity.setProductLine(projectExcel.getProductLine());
                    projectEntity.setProcessSection(projectExcel.getProcessSection());
                    projectEntity.setDeptName(projectExcel.getDeptName());
                    if (rdIndexMap.get(rdNum) != null && projectListModel.getRdIndex() != rdNum) {
                        rdList.add(projectEntity.getBeginYear() + "RD" + rd + "[" + projectExcel.getPname() + "]");
                        continue;
                    }
                    BeanUtils.copyProperties(projectExcel, projectEntity);
                    if (projectExcel.getEstimateExpense() != null) {
                        projectEntity.setEstimateExpense(projectExcel.getEstimateExpense() * 10000);
                    }
                    projectEntity.setRdIndex(rdNum);
                    projectEntity.setId(projectListModel.getId());

                    if (StringUtils.isEmpty(projectExcel.getEname())) {
                        projectEntity.setMasterENumber("");
                    } else {
                        RdEmployeeModel employeeModel = rdEmployeeMap.get(projectExcel.getEname());
                        if (employeeModel != null) {
                            projectEntity.setMasterENumber(employeeModel.getEnumber());
                        } else {
                            pnameRdList.add(projectEntity.getBeginYear() + "RD" + rd + "[" + projectExcel.getEname() + "]");
                            continue;
                        }
                    }
                    projectEntity.setLastUpdatorId(info.getUserId());
                    projectEntity.setMsLastUpdatorId(info.getMsUserId());
                    projectEntity.setLastUpdateTime(date);
                    projectEntity.setProjectSource(1);
                    projectEntity.setFormula(10);
                    Integer rdDeptId = ToolUtils.getOrgId(rdDeptMap, projectExcel.getRdDeptName().trim());
                    projectEntity.setRdDeptId(rdDeptId != null ? rdDeptId : -1);
                    projectEntity.setRdNumber(projectExcel.getRdNumber());
                    projectEntity.setRdTitle(getRdTitle(projectEntity.getBeginYear(), projectEntity.getRdIndex(), info.getCompanyId(), null));
                    updateProjectEntitys.add(projectEntity);
                }
            }
            TransactionStatus transactionStatus = TransactionUtils.newTransaction();
            try {
                List< InitMemberEntity > insertMembers = new ArrayList<>();
                List< ProjectYearInfoEntity > infos = new ArrayList<>();
                List< ProjectYearInfoEntity > updateInfos = new ArrayList<>();
                if (insertProjectEntitys.size() > 0) {
                    projectDao.insertProjectList(insertProjectEntitys);
                    for (ProjectEntity projectEntity : insertProjectEntitys) {
                        if (!StringUtils.isEmpty(projectEntity.getMasterENumber())) {
                            InitMemberEntity initMember = new InitMemberEntity(info.getUserId(), info.getMsUserId(), date, projectEntity.getMasterENumber(),
                                    true, info.getCompanyId(), projectEntity.getId(), year, null);
                            insertMembers.add(initMember);
                            ProjectYearInfoEntity infoEntity = new ProjectYearInfoEntity(year, projectEntity.getMasterENumber(), null, projectEntity.getId(), info.getCompanyId());
                            infoEntity.create(info.getUserId(), info.getMsUserId(), date);
                            infos.add(infoEntity);
                        }
                    }
                }
                if (updateProjectEntitys.size() > 0) {
                    projectDao.updateProjectList(updateProjectEntitys);
                    List< Integer > projectIds = updateProjectEntitys.stream().map(e -> e.getId()).collect(Collectors.toList());
                    initMemberDao.cleanMasters(projectIds, year, info.getUserId(), info.getMsUserId(), date);
                    List< ProjectEntity > dels = new ArrayList<>();
                    for (ProjectEntity project : updateProjectEntitys) {
                        if (!StringUtils.isEmpty(project.getMasterENumber())) {
                            dels.add(project);
                            InitMemberEntity member = new InitMemberEntity(info.getUserId(), info.getMsUserId(), date, project.getMasterENumber(),
                                    true, info.getCompanyId(), project.getId(), year, null);
                            insertMembers.add(member);
                            ProjectYearInfoEntity infoEntity = new ProjectYearInfoEntity(year, project.getMasterENumber(), null, project.getId(), info.getCompanyId());
                            infoEntity.update(info.getUserId(), info.getMsUserId(), date);
                            updateInfos.add(infoEntity);
                        }
                    }
                    if (!CollectionUtils.isEmpty(dels)) {
                        initMemberDao.delMasterByProject(dels, year);
                    }
                }
                if (!CollectionUtils.isEmpty(insertMembers)) {
                    initMemberDao.addbatch(insertMembers);
                }
                if (!CollectionUtils.isEmpty(infos)) {
                    projectYearInfoDao.addBatchMaster(infos);
                }
                if (!CollectionUtils.isEmpty(updateInfos)) {
                    projectYearInfoDao.updateBatch(updateInfos);
                }
                TransactionUtils.commit(transactionStatus);
            } catch (Exception ex) {
                TransactionUtils.rollback(transactionStatus);
                logger.error("importProject", ex);
            } finally {
                String message = "";
                if (rdList.size() > 0) {
                    message += Joiner.on(",").join(rdList) + "的RD已存在,请检查!<br/>";
                }
                if (pnameRdList.size() > 0) {
                    message += Joiner.on(",").join(pnameRdList) + "的负责人不在研发花名册内,请检查!";
                }
                if (!message.equals("")) {
                    throw new OwnerException(message);
                }
            }
        } else {
            throw new OwnerException("未获取到任何数据，请检查表格是否存在数据，上传模板是否正确。");
        }
        return "";
    }

    @Override
    public Map< String, Object > getDataReportFunds(Integer companyId, Integer year, Boolean child) throws OwnerException {
        Map< String, Object > result = new HashMap<>(2);
        List< ProjectDetailModel > projects = projectDao.getProjectIdsByYear(companyId, year, child);
        if (CollectionUtils.isEmpty(projects)) {
            return result;
        }
        String format = "yyyy年MM月";
        Map< Integer, DataReportModel > resultMap = new LinkedHashMap<>();
        Boolean noOutsourcing = true;
        for (ProjectDetailModel item : projects) {
            if (item.getFormula() > 10) {
                noOutsourcing = false;
            }
            resultMap.put(item.getId(),
                    new DataReportModel(item.getId(), item.getRdTitle(),
                            DateUtil.format(item.getBeginDate(), format) + "-" + DateUtil.format(item.getEndDate(), format),
                            item.getPname()));
        }
        result.put("noOutsourcing", noOutsourcing);
        Date beginDate = DateUtil.getYearFirstDay(year);
        Date endDate = DateUtil.getYearLastDay(year);
        List< OutsourcingSummaryModel > outsourcingList = projectOutsourcingDao.getMonthOutsourcing(
                beginDate, endDate, companyId);
        List< DataFundsModel > data = summaryDao.getDataReportFunds(companyId, beginDate, endDate, null);
        if (CollectionUtils.isEmpty(data) && CollectionUtils.isEmpty(outsourcingList)) {
            result.put("list", resultMap.values());
            return result;
        }
        String keyFormat = "{0}_{1}";
        Map< String, List< DataFundsModel > > projectFundsMap = new HashMap<>();
        data.forEach(item -> {
            ToolUtils.putAndAdd(projectFundsMap, MessageFormat.format(keyFormat,
                    item.getParentId() > 0 && !child ? item.getParentId() : item.getProjectId(),
                    cn.hutool.core.date.DateUtil.month(item.getMonth()) + 1), item);
        });
        Map< String, List< OutsourcingSummaryModel > > projectOutsourcingMap = new HashMap<>();
        outsourcingList.forEach(item -> {
            ToolUtils.putAndAdd(projectOutsourcingMap,
                    MessageFormat.format(keyFormat,
                            item.getParentId() > 0 && !child ? item.getParentId() : item.getProjectId(),
                            cn.hutool.core.date.DateUtil.month(item.getMonth()) + 1), item);
        });
        resultMap.values().forEach(item -> {
            for (int i = 1; i <= 12; i++) {
                String key = MessageFormat.format(keyFormat, item.getId(), i);
                boolean containFunds = projectFundsMap.containsKey(key), containOutsourcing = projectOutsourcingMap.containsKey(key);
                if (!containFunds && !containOutsourcing) {
                    continue;
                }
                String monthly = i + " 月份";
                DataReportModel current = new DataReportModel(monthly);
                if (containFunds) {
                    Integer parent;
                    for (DataFundsModel funds : projectFundsMap.get(key)) {
                        parent = funds.getRdType() / 100;
                        // 其他费用，且不是差旅费
                        if (parent >= CostEnum.BOOK.getParent() && parent != CostEnum.TRAVEL.getParent()) {
                            parent = CostEnum.OTHER.getParent();
                        }
                        String parentKey = parent.toString();
                        item.getTotalFunds().put(parentKey, funds.getRdFunds().add(item.getTotalFunds().getOrDefault(parentKey, BigDecimal.ZERO)));
                        current.getTotalFunds().put(parentKey, funds.getRdFunds().add(current.getTotalFunds().getOrDefault(parentKey, BigDecimal.ZERO)));
                    }
                }
                if (containOutsourcing) {
                    for (OutsourcingSummaryModel funds : projectOutsourcingMap.get(key)) {
                        String curTypeStr = "outside";
                        if (funds.getType() == 0) {
                            curTypeStr = "inside";
                        }
                        item.getTotalFunds().put(curTypeStr, funds.getRdFunds().add(item.getTotalFunds().getOrDefault(curTypeStr, BigDecimal.ZERO)));
                        current.getTotalFunds().put(curTypeStr, funds.getRdFunds().add(current.getTotalFunds().getOrDefault(curTypeStr, BigDecimal.ZERO)));
                    }
                }
                item.getChildren().add(current);
            }
        });
        result.put("list", resultMap.values());
        return result;
    }

    @Override
    public List< Map< String, Object > > querySubsidiaryLedger(Integer companyId, Integer year, Boolean child, Integer type) throws ParseException {
        List< ProjectDetailModel > projects = projectDao.getProjectIdsByYear(companyId, year, child);
        if (CollectionUtils.isEmpty(projects)) {
            return new ArrayList<>();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        Date beginDate = cn.hutool.core.date.DateUtil.beginOfYear(calendar.getTime());
        Date endDate = cn.hutool.core.date.DateUtil.endOfYear(beginDate);
        List< DataFundsModel > fundsData;
        if (type.equals(100)) {
            List< Integer > rdTypes = Arrays.asList(CostEnum.SALARY.getType(), CostEnum.INSURANCE.getType());
            fundsData = summaryDao.getDataByRdType(companyId, beginDate, endDate, rdTypes);
        } else {
            fundsData = summaryDao.getDataReportFunds(companyId, beginDate, endDate, null);
        }
        Map< String, BigDecimal > fundsMap = new HashMap<>();
        String keyFormat = "{0}_{1}_{2}";
        Integer projectId;
        for (DataFundsModel fund : fundsData) {
            Integer pType = fund.getRdType() / 100;
            String fundKey;
            if (type.equals(100)) {
                projectId = fund.getParentId() > 0 ? fund.getParentId() : fund.getProjectId();
                fundKey = MessageFormat.format(keyFormat, projectId, pType,
                        DateUtil.format(fund.getMonth(), DateUtil.DEFAULT_YYMM_FORMAT));
            } else {
                projectId = fund.getParentId() > 0 && !child ? fund.getParentId() : fund.getProjectId();
                fundKey = MessageFormat.format(keyFormat, projectId, pType >= 600 && pType <= 603 ? 699 : pType,
                        DateUtil.format(fund.getMonth(), DateUtil.DEFAULT_YYMM_FORMAT));
            }
            fundsMap.put(fundKey, fund.getRdFunds().add(fundsMap.getOrDefault(fundKey, BigDecimal.ZERO)));
        }
        Map< Integer, CostEnum > parentCostMap = CostEnum.getParentMap(false);
        List< Map< String, Object > > result = new ArrayList<>();
        parentCostMap.keySet().forEach(parentType -> {
            if (type.equals(100)) {
                if (parentType > 101) {
                    return;
                }
            } else {
                if ((parentType == 204) || (parentType >= 501 && parentType <= 603)) {
                    return;
                }
            }
            Map< String, Object > costMap = new HashMap<>();
            costMap.put("title", parentCostMap.get(parentType).getTitle());
            Map< String, Object > totalMap = new HashMap<>();
            totalMap.put("rdPname", "合计");
            List< Map< String, Object > > monthFundList = new ArrayList<>();
            Integer finalParentType = parentType;
            projects.forEach(item -> {
                Map< String, Object > dataMap = new HashMap<>();
                dataMap.put("rdPname", item.getRdTitle() + Constant.HYPHEN + Constant.HYPHEN + item.getPname());
                dataMap.put("workshop", item.getWorkshop());
                for (int begin = 1; begin <= 12; begin++) {
                    String monthKey = year + Constant.HYPHEN + StrUtil.padPre(begin + "", 2, "0");
                    String fundKey = MessageFormat.format(keyFormat, item.getId(),
                            finalParentType, monthKey);
                    BigDecimal rdFunds = fundsMap.getOrDefault(fundKey, BigDecimal.ZERO);
                    dataMap.put(monthKey, rdFunds);
                    totalMap.put(monthKey, rdFunds.add((BigDecimal) totalMap.getOrDefault(monthKey, BigDecimal.ZERO)));
                }
                monthFundList.add(dataMap);
            });
            monthFundList.add(totalMap);
            costMap.put("data", monthFundList);
            result.add(costMap);
        });
        return result;
    }

    @Override
    public List< EmployeeSelectModel > getBaseEmployeeSelect(Integer companyId, String ename) {
        if (StringUtils.isEmpty(ename)) {
            return new ArrayList<>();
        }
        return rdEmployeeDao.getBaseEmployeeSelect(companyId, ename);
    }

    @Override
    public List< EmployeeSelectModel > getEmployeeNames(Integer companyId, String ename) {
        if (StringUtils.isEmpty(ename)) {
            return new ArrayList<>();
        }
        List< EmployeeSelectModel > list = rdEmployeeDao.getBaseEmployeeSelect(companyId, ename);
        if (CollectionUtils.isEmpty(list)) {
            EmployeeSelectModel model = new EmployeeSelectModel();
            model.setEname(ename);
            return Arrays.asList(model);
        }
        return list;
    }

    public boolean checkDate(Date date, Date begin, Date end) {
        return ((begin.compareTo(date) == -1 || begin.compareTo(date) == 0) && date.compareTo(end) == -1);
    }

    @Override
    public void exportGeneralLedger(int year, Boolean old, UserInfo info, OutputStream out) throws OwnerException {
        Integer companyId = info.getCompanyId();
        String templatePath = rsConfig.getUploadConfig().getResourcePath() + Constant.TEMPLATE_DIR;
        List< String > sheetNames = new ArrayList<>();
        List< Map< String, Object > > list = new ArrayList<>();
        Map< String, Object > firstMap = new HashMap<>();
        loadGeneralLedgerMap(companyId, year, sheetNames, null, list, firstMap, old);
        CollUtil.reverse(list);
        templatePath += !old ? "2021辅助帐总表模板.xls" :
                (Boolean) firstMap.getOrDefault("isShangHai", false) ? "辅助帐总表_上海模板.xls" : "辅助帐总表模板.xls";
        Map< Integer, List< Map< String, Object > > > dataMap = new HashMap<>();
        dataMap.put(0, CollUtil.newArrayList(firstMap));
        dataMap.put(1, list);
        YsExcelUtils.generalMoreSheetsReport(dataMap, templatePath, (workbook) -> {
            if (workbook != null) {
                // 根据需要合并单元格
                if (old) {
                    YsExcelUtils.mergeCell(workbook, 0, 12, 11 + list.size(), 0, 0, "项目明细\n" + "（填写项目贷方发生额）");
                }
                YsExcelUtils.setSheetName(workbook, sheetNames);
                try {
                    workbook.write(out);
                    workbook.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        });
    }

    private void loadGeneralLedgerMap(Integer companyId, Integer year, List< String > sheetNames, Date month,
                                      List< Map< String, Object > > list, Map< String, Object > firstMap, Boolean old) {
        try {
            CompanyEntity company = companyDao.getGeneralLedgerInfo(companyId);
            boolean isShangHai = company.getAddressCode().contains(SHANG_HAI_CODE);
            firstMap.put("isShangHai", isShangHai);
            Map< Integer, Map< String, Object > > projectMap = getProjectMap(companyId, year);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            Date beginDate = cn.hutool.core.date.DateUtil.beginOfYear(calendar.getTime());
            Date endDate = null;
            if (month != null) {
                endDate = DateUtil.getMonthLastDay(month);
            } else {
                endDate = cn.hutool.core.date.DateUtil.endOfYear(beginDate);
            }
            List< DataFundsModel > fundsList = summaryDao.getDataReportFunds(companyId, beginDate, endDate, null);
            List< ProjectVoucherModel > voucherList = rdVoucherDao.getRdVouchers(companyId, beginDate, endDate);
            CollUtil.sort(fundsList, (o1, o2) -> o1.getRdType().compareTo(o2.getRdType()));
            Map< String, VoucherAppendModel > voucherNosMap = new HashMap<>();
            String voucherFormart = "{0}_{1}_{2}";
            voucherList.forEach(item -> {
                int parent = ToolUtils.getParentType(item.getRdType(), true);
                if (item.getRdType().equals(CostEnum.REPAIR.getType())) {
                    parent = CostEnum.REPAIR.getParent();
                }
                Integer currentProjectId = item.getParentId() > 0 ? item.getParentId() : item.getProjectId();
                String voucherKey = MessageFormat.format(voucherFormart, currentProjectId,
                        DateUtil.format(DateUtil.getMonthLastDay(item.getMonth()), DateUtil.DEFAULT_DATE_FORMAT), parent);
                if (!voucherNosMap.containsKey(voucherKey)) {
                    voucherNosMap.put(voucherKey, new VoucherAppendModel(item.getVoucherNo(), item.getAmount()));
                    return;
                }
                voucherNosMap.get(voucherKey).addVoucherNoAndAmount(item.getVoucherNo(), item.getAmount());
            });
            Map< Integer, List< Map< String, Object > > > fundsMap = new LinkedHashMap<>();
            Map< Integer, Map< String, BigDecimal > > totalFundMap = new LinkedHashMap<>();
            Map< String, Map< String, Map< String, Object > > > childrenMap = new HashMap<>();
            String summary = "summary";
            String fiveSummary = "fiveSummary";
            String monthKey = "month";
            String monthValue;
            int parentRdType, rootRdType, currentRdType;
            Integer currentProjectId;
            CostEnum parentCostEnum, currentCostEnum;
            BigDecimal currentFunds;
            Map< Integer, CostEnum > parentCost = CostEnum.getParentMap(true);
            Map< String, BigDecimal > projectTotalMap = new HashMap<>();
            for (DataFundsModel item : fundsList) {
                currentRdType = item.getRdType();
                rootRdType = ToolUtils.getParentType(currentRdType, old);
                parentRdType = old ? rootRdType : ToolUtils.getParentType(currentRdType, true);
                parentCostEnum = parentCost.get(rootRdType);
                currentCostEnum = CostEnum.getCostEnum(currentRdType);
                monthValue = DateUtil.format(DateUtil.getMonthLastDay(item.getMonth()), DateUtil.DEFAULT_DATE_FORMAT);
                currentProjectId = item.getParentId() > 0 ? item.getParentId() : item.getProjectId();
                if (!fundsMap.containsKey(currentProjectId)) {
                    totalFundMap.put(currentProjectId, new HashMap<>());
                    fundsMap.put(currentProjectId, new ArrayList<>());
                }
                String key = MessageFormat.format("{0}_{1}", currentProjectId, currentRdType);
                if (!childrenMap.containsKey(key)) {
                    childrenMap.put(key, new LinkedHashMap<>());
                }
                if (!childrenMap.get(key).containsKey(monthValue)) {
                    Map< String, Object > currentMap = new LinkedHashMap<>();
                    currentMap.put(summary, BigDecimal.ZERO);
                    currentMap.put(monthKey, monthValue);
                    currentMap.put("m", monthValue.substring(5, 7));
                    currentMap.put("d", monthValue.substring(8, 10));
                    currentMap.put("borrow", "借");
                    currentMap.put("typeName", currentCostEnum.getTitle());
                    String voucherKey = MessageFormat.format(voucherFormart,
                            currentProjectId, monthValue, parentRdType);
                    if (voucherNosMap.containsKey(voucherKey)) {
                        currentMap.put("voucherNo", voucherNosMap.get(voucherKey).getVoucherNo().toString());
                    }
                    childrenMap.get(key).put(monthValue, currentMap);
                    List< Map< String, Object > > currentList = fundsMap.get(currentProjectId);
                    if (currentList.size() > 0) {
                        currentMap.put("amount", currentList.get(currentList.size() - 1).get("amount"));
                    } else {
                        currentMap.put("amount", BigDecimal.ZERO);
                    }
                    currentList.add(currentMap);
                }
                Map< String, Object > monthMap = childrenMap.get(key).get(monthValue);
                currentFunds = item.getRdFunds();
                monthMap.put(parentCostEnum.getField(), currentFunds.add((BigDecimal) monthMap.getOrDefault(parentCostEnum.getField(), BigDecimal.ZERO)));
                monthMap.put(summary, currentFunds.add((BigDecimal) monthMap.get(summary)));
                BigDecimal currentAmount = currentFunds.add((BigDecimal) monthMap.get("amount"));
                monthMap.put("amount", currentAmount);
                totalFundMap.get(currentProjectId).put(parentCostEnum.getField(), currentFunds.add(totalFundMap.get(currentProjectId).getOrDefault(parentCostEnum.getField(), BigDecimal.ZERO)));
                if (parentRdType < 600) {
                    totalFundMap.get(currentProjectId).put(fiveSummary, currentFunds.add(totalFundMap.get(currentProjectId).getOrDefault(fiveSummary, BigDecimal.ZERO)));
                    projectTotalMap.put(fiveSummary, currentFunds.add(projectTotalMap.getOrDefault(fiveSummary, BigDecimal.ZERO)));
                }
                totalFundMap.get(currentProjectId).put(summary, currentFunds.add(totalFundMap.get(currentProjectId).getOrDefault(summary, BigDecimal.ZERO)));
                projectTotalMap.put(summary, currentFunds.add(projectTotalMap.getOrDefault(summary, BigDecimal.ZERO)));
                projectTotalMap.put(parentCostEnum.getField(), currentFunds.add(projectTotalMap.getOrDefault(parentCostEnum.getField(), BigDecimal.ZERO)));
            }
            // 委外费用
            List< ProjectOutsourcing > outsourcingList = projectOutsourcingDao.getProjectOutsourcing(beginDate, endDate, companyId);
            if (!CollectionUtils.isEmpty(outsourcingList)) {
                String inside = "inside", outside = "outside", currentKey;
                BigDecimal zeroEight = BigDecimal.valueOf(0.8);
                boolean isInside;
                for (ProjectOutsourcing item : outsourcingList) {
                    currentProjectId = item.getProjectId();
                    if (!totalFundMap.containsKey(currentProjectId)) {
                        totalFundMap.put(currentProjectId, new HashMap<>());
                    }
                    isInside = item.getType() == 0;
                    currentKey = isInside ? inside : outside;
                    totalFundMap.get(currentProjectId).put(currentKey, item.getRdFunds());
                    projectTotalMap.put(currentKey, item.getRdFunds().add(projectTotalMap.getOrDefault(currentKey, BigDecimal.ZERO)));
                    if (!old) {
                        // List<Map<String, Object>> currentList = fundsMap.get(currentProjectId);
                        // 这里暂时先按(8.1或8.3)*80%计算(8.2或8.4)列
                        BigDecimal limit = item.getRdFunds().multiply(zeroEight);
                        String limitKey = currentKey + "Limit";
                        totalFundMap.get(currentProjectId).put(limitKey, limit);
                        if (isInside) {
                            projectTotalMap.put(limitKey, limit.add(projectTotalMap.getOrDefault(limitKey, BigDecimal.ZERO)));
                        }
                    }
                }
            }
            List< Map< String, Object > > projects = new ArrayList<>();
            sheetNames.add("辅助帐");
            int count = isShangHai ? 4 : 0;
            String summaryLimit = fiveSummary + "Limit";
            BigDecimal zeroOne = BigDecimal.valueOf(0.1);
            BigDecimal zeroNine = BigDecimal.valueOf(0.9);
            BigDecimal totalLimit = BigDecimal.ZERO;
            for (Integer projectId : projectMap.keySet()) {
                if (!totalFundMap.containsKey(projectId)) {
                    continue;
                }
                Map< String, BigDecimal > currentFundsMap = totalFundMap.get(projectId);
                Map< String, Object > tMap = projectMap.get(projectId);
                Map< String, Object > dataMap = new HashMap<>();
                sheetNames.add((String) tMap.get("rdTitle"));
                dataMap.put("rdTitle", tMap.get("rdTitle"));
                dataMap.put("pname", tMap.get("pname"));
                dataMap.put("rdNumber", tMap.get("rdTitle"));
                dataMap.put("data", fundsMap.get(projectId));
                dataMap.put("total", currentFundsMap);
                String finish = (String) tMap.get("finish");
                boolean finished = "完成".equals(finish);
                if (!old || isShangHai) {
                    dataMap.put("finish", finish);
                } else {
                    if (finished) {
                        dataMap.put("finished", "●");
                        dataMap.put("noFinish", "◎");
                    } else {
                        dataMap.put("finished", "◎");
                        dataMap.put("noFinish", "●");
                    }
                }
                BigDecimal tempLimit = BigDecimal.ZERO;
                BigDecimal fiveTotal = currentFundsMap.getOrDefault(fiveSummary, BigDecimal.ZERO);
                if (fiveTotal.compareTo(BigDecimal.ZERO) > 0) {
                    // 旧版（第八项 * 10%）/(1-10%)
                    tempLimit = fiveTotal.multiply(zeroOne).divide(zeroNine, BigDecimal.ROUND_HALF_UP);
                }
                if (!old) {
                    String outsideLimitKey = "outsideLimit";
                    // 2021版，与其他费用相比，孰小值填写
                    BigDecimal book = currentFundsMap.getOrDefault(CostEnum.BOOK.getField(), BigDecimal.ZERO);
                    tempLimit = book.compareTo(tempLimit) < 0 ? book : tempLimit;
                    BigDecimal tempAmount = fiveTotal.add(book).add(currentFundsMap.getOrDefault("insideLimit", BigDecimal.ZERO));
                    BigDecimal outsideLimit = currentFundsMap.getOrDefault(outsideLimitKey, BigDecimal.ZERO);
                    tempAmount = tempAmount.compareTo(outsideLimit) < 0 ? tempAmount : outsideLimit;
                    projectTotalMap.put(outsideLimitKey, tempAmount.add(projectTotalMap.getOrDefault(outsideLimitKey, BigDecimal.ZERO)));
                }
                currentFundsMap.put(summaryLimit, tempLimit);
                totalLimit = totalLimit.add(tempLimit);
                tMap.putAll(currentFundsMap);
                tMap.put("num", ++count);
//                parentCost.keySet().forEach(pType -> {
//                    String field = parentCost.get(pType).getField();
//                    if (!tMap.containsKey(field)) {
//                        tMap.put(field, BigDecimal.ZERO);
//                    }
//                });
                projects.add(tMap);
                list.add(dataMap);
            }
//            parentCost.keySet().forEach(pType -> {
//                String field = parentCost.get(pType).getField();
//                if (!projectTotalMap.containsKey(field)) {
//                    projectTotalMap.put(field, BigDecimal.ZERO);
//                }
//            });
            firstMap.put("company", company);
            firstMap.put("year", year);
            firstMap.put("projects", projects);
            projectTotalMap.put("totalLimit", totalLimit);
            firstMap.put("total", projectTotalMap);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public List< EmployeeSelectModel > getEmployeeSelect(UserInfo userInfo, QueryProjectEmployeeModel model) {
        List< EmployeeSelectModel > selectList = new ArrayList<>();
        if (model.getProjectId() != null && model.getProjectId() > 0) {
            selectList = initMemberDao.getMemberSelect(userInfo.getCompanyId(), model.getProjectId(), model.getEname(), model.getYear());
        } else if (model.getAllEmployee()) {
            selectList = employeeDao.getEmployeeSelect(userInfo.getCompanyId(), model.getEname());
        } else if (model.getYear() != null && model.getRdAndCommittee() != null && model.getRdAndCommittee()) {
            selectList = rdEmployeeDao.getUnionEmployee(userInfo.getCompanyId(), model.getYear(), model.getEname());
        } else if (model.getYear() != null && (model.getRdAndCommittee() == null || !model.getRdAndCommittee())) {
            selectList = rdEmployeeDao.getEmployeeSelect(userInfo.getCompanyId(), model.getYear(), model.getEname());
        }
        return selectList;
    }

    @Override
    public Boolean mergeProject(UserInfo userInfo, ParentProjectModel model) throws OwnerException {
        Date date = new Date();
        if (CollectionUtils.isEmpty(model.getChildIds())) {
            throw new OwnerException("请选择要合并的项目");
        }
        List< Integer > ids = model.getChildIds();
        List< ProjectEntity > entityList = projectDao.selectBatchIds(ids);
        for (ProjectEntity project : entityList) {
            if (project.getHasChild()) {
                throw new OwnerException(MessageFormat.format("{0}RD{1}已存在子项目，不能合并", String.valueOf(project.getBeginYear()), project.getRdIndex() > 9 ? project.getRdIndex() : "0" + project.getRdIndex()));
            }
            if (project.getParentId() != 0) {
                throw new OwnerException("请移出该项目后再进行合并操作");
            }
        }
        ProjectEntity projectEntity;
        Map< String, Object > dataMap = countYear(entityList);
        Integer beginYear = (Integer) dataMap.get("beginYear");
//        commonService.checkAuditModify(userInfo.getCompanyId(), beginYear, AuditModeEnum.RD_PROJECT, userInfo.getUserSource());
        checkProjectAudit(ids, entityList, userInfo.getUserSource());
        Integer endYear = (Integer) dataMap.get("endYear");
        Date beginDate = (Date) dataMap.get("beginDate");
        Date endDate = (Date) dataMap.get("endDate");
        //合并操作
        if (model.getSign() == 1) {
            projectEntity = new ProjectEntity();
            projectEntity.setRdIndex(model.getRdIndex());
            projectEntity.setPname(model.getPname());
            projectEntity.setMasterENumber(model.getMasterENumber());
            projectEntity.setDeptName(model.getDeptName());
            if (model.getDeptId() == null) {
                model.setDeptId(0);
            }
            projectEntity.setDeptId(model.getDeptId());
            projectEntity.setRdDeptId(model.getRdDeptId());
            projectEntity.setWorkshop(model.getWorkshop());
            projectEntity.setProcessSection(model.getProcessSection());
            projectEntity.setProductLine(model.getProductLine());
            projectEntity.setBeginYear(beginYear);
            projectEntity.setEndYear(endYear);
            projectEntity.setEndDate(endDate);
            projectEntity.setBeginDate(beginDate);
            projectEntity.setCreateTime(date);
            projectEntity.setLastUpdateTime(date);
            projectEntity.setCreatorId(userInfo.getUserId());
            projectEntity.setLastUpdatorId(userInfo.getUserId());
            projectEntity.setMsLastUpdatorId(userInfo.getMsUserId());
            projectEntity.setMsCreatorId(userInfo.getMsUserId());
            projectEntity.setParentId(0);
            projectEntity.setHasChild(true);
            projectEntity.setCompanyId(userInfo.getCompanyId());
            projectEntity.setRdNumber(String.valueOf(model.getRdIndex()));
            String title = getRdTitle((Integer) dataMap.get("beginYear"), model.getRdIndex(), userInfo.getCompanyId(), null);
            projectEntity.setRdTitle(title);
            projectEntity.setFormula(10);
            projectEntity.setInvolvedProduct(model.getInvolvedProduct());
        } else {
            //加入大项目并更新大项目日期
            projectEntity = projectDao.selectById(model.getParentId());
            if (projectEntity.getBeginYear() > beginYear) {
                throw new OwnerException("不能合并" + projectEntity.getBeginYear() + "年之前的项目");
            }
            if (projectEntity.getEndDate().getTime() < endDate.getTime()) {
                projectEntity.setEndDate(endDate);
                projectEntity.setEndYear(endYear);
            }
            if (projectEntity.getBeginDate().getTime() > beginDate.getTime()) {
                projectEntity.setBeginDate(beginDate);
            }
            projectEntity.setLastUpdateTime(date);
            projectEntity.setLastUpdatorId(userInfo.getUserId());

        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            Integer parentId = model.getParentId();
            if (model.getSign() == 1) {
                //合并添加父项目
                projectDao.insert(projectEntity);
                parentId = projectEntity.getId();
            } else {
                projectDao.updateById(projectEntity);
            }
            for (ProjectEntity entity : entityList) {
                entity.setLastUpdatorId(userInfo.getUserId());
                entity.setLastUpdateTime(date);
                entity.setParentId(parentId);
                String rdTitle = getRdTitle(projectEntity.getBeginYear(), projectEntity.getRdIndex(), userInfo.getCompanyId(), entity.getRdNumber() != null ? entity.getRdNumber() : "");
                entity.setRdTitle(rdTitle);

            }
            projectDao.updateChildProject(entityList);

            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception ex) {
            TransactionUtils.rollback(transactionStatus);
            logger.error("mergeProject", ex);
        }
        return false;
    }

    @Override
    public Boolean checkParentNo(UserInfo userInfo, Integer rdIndex, List< Integer > projectIds) throws OwnerException {
        List< ProjectEntity > projectEntities = projectDao.selectBatchIds(projectIds);
        Map< String, Object > dataMap = countYear(projectEntities);
        if (dataMap.containsKey("beginYear")) {
            Integer beginYear = (Integer) dataMap.get("beginYear");
            if (beginYear != 0) {
                ProjectEntity projectEntity = projectDao.checkParentRd(rdIndex, beginYear, userInfo.getCompanyId(), projectIds);
                if (projectEntity != null) {
                    throw new OwnerException("RD编号已存在");
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public List< ProjectSelectModel > queryParentList(Integer companyId, List< Integer > projectIds, Integer currentYear) throws OwnerException {
        List< ProjectEntity > projectEntitys = projectDao.getListByIds(projectIds, companyId);
        List< Integer > parentIds = new ArrayList<>();
        Map< Integer, ProjectEntity > entityMap = new HashMap<>();
        for (ProjectEntity entity : projectEntitys) {
            if (entity.getParentId() > 0) {
                if (!entityMap.containsKey(entity.getParentId())) {
                    entityMap.put(entity.getParentId(), entity);
                    parentIds.add(entity.getParentId());
                }
            }
            if (entity.getBeginYear() < currentYear) {
                throw new OwnerException("请选择" + currentYear + "年的项目进行操作");
            }
        }
        List< ProjectSelectModel > list = projectDao.queryAllParent(companyId, parentIds, currentYear);
        return list;
    }

    @Override
    public Boolean removeProject(Integer parentId, Integer rdIndex, UserInfo userInfo, Integer childId) throws OwnerException {
        Date date = new Date();
        //parentId为0,子项目转原项目,非0则更换父项目
        ProjectEntity projectEntity = projectDao.selectById(childId);
        commonService.checkAuditModify(userInfo.getCompanyId(), projectEntity.getBeginYear(), AuditModeEnum.RD_PROJECT, userInfo.getUserSource());
        if (projectEntity.getParentId() == 0 || projectEntity.getHasChild()) {
            throw new OwnerException("非子项目不能进行移出操作");
        }
        String rdTitle = "";
        ProjectEntity parent = null;
        Integer sign = 0;//标记父项目是否修改
        if (parentId > 0) {
            parent = projectDao.selectById(parentId);
            if (!parent.getHasChild()) {
                throw new OwnerException("子项目移出到的项目应为父项目");
            }
            if (projectEntity.getBeginYear() < parent.getBeginYear()) {
                throw new OwnerException("不能移出到" + parent.getBeginYear() + "的项目");
            }
            if (parent.getBeginDate().getTime() > projectEntity.getBeginDate().getTime()) {
                parent.setBeginDate(projectEntity.getBeginDate());
                sign = 1;
            }
            if (parent.getEndDate().getTime() < projectEntity.getEndDate().getTime()) {
                parent.setEndDate(projectEntity.getEndDate());
                parent.setEndYear(projectEntity.getEndYear());
                sign = 2;
            }
            rdTitle = getRdTitle(parent.getBeginYear(), parent.getRdIndex(), userInfo.getCompanyId(), projectEntity.getRdNumber() != null ? projectEntity.getRdNumber() : "");
        } else {
            projectEntity.setRdIndex(rdIndex);
            rdTitle = getRdTitle(projectEntity.getBeginYear(), rdIndex, userInfo.getCompanyId(), null);
        }
        projectEntity.setRdTitle(rdTitle);
        projectEntity.setLastUpdatorId(userInfo.getUserId());
        projectEntity.setLastUpdateTime(date);
        projectEntity.setParentId(parentId);
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            if (sign > 0) {
                parent.setLastUpdateTime(date);
                parent.setLastUpdatorId(userInfo.getUserId());
                projectDao.updateById(parent);
            }
            projectDao.updateById(projectEntity);
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception ex) {
            TransactionUtils.rollback(transactionStatus);
            logger.error("addProject", ex);
        }
        return false;
    }

    @Override
    public List< ProjectListModel > getAllProject(Integer companyId, Integer year) {
        List< ProjectListModel > listModels = projectDao.getAllProject(companyId, year);
        return listModels;
    }

    @Override
    public List< ProjectSelectModel > getSelectList(Integer companyId, Integer year, Integer sign) {
        List< ProjectSelectModel > list = projectDao.getSelectList(companyId, year, sign);
        Map< Integer, String > map = new HashMap<>();
        for (ProjectTypeEnum value : ProjectTypeEnum.values()) {
            map.put(value.getType(), value.getTypeName());
        }
        list.forEach(item -> {
            Integer formula = Integer.valueOf(item.getFormula());
            if (map.containsKey(formula)) {
                item.setFormula(map.get(formula));
            }
        });
        return list;
    }

    @Override
    public List<DocFileFooterModel> getSignatureList(Integer companyId, Integer year) {
        List<DocFileFooterModel> list = projectDao.getSignatureList(companyId, year);
        if (!CollectionUtils.isEmpty(list)){
            List<EmployeeModel> employeeList = employeeDao.getEmployeeList(companyId);
            if (!CollectionUtils.isEmpty(employeeList)){
                Map<String,String> map = new HashMap<>();
                employeeList.forEach(item->{
                    map.put(item.getEnumber(),item.getEname());
                });
                for (DocFileFooterModel footerModel : list) {
                    footerModel.setApproval(map.get(footerModel.getApprovalEnumber()));
                    footerModel.setAuditor(map.get(footerModel.getAuditEnumber()));
                    footerModel.setToCompile(map.get(footerModel.getToCompileEnumber()));
                }
            }
        }
        return list;
    }

    /**
     * 计算项目集合最小开始年/日期和最大结束年/日期
     *
     * @param list
     * @return
     */
    private Map< String, Object > countYear(List< ProjectEntity > list) {
        Map< String, Object > dataMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(list)) {
            Date beginDate = null;
            Integer beginYear = 0;
            Date endDate = null;
            Integer endYear = 0;
            for (ProjectEntity project : list) {
                if (beginDate == null) {
                    beginDate = project.getBeginDate();
                    beginYear = project.getBeginYear();
                } else {
                    if (project.getBeginDate().getTime() < beginDate.getTime()) {
                        beginDate = project.getBeginDate();
                        beginYear = project.getBeginYear();
                    }
                }
                if (endDate == null) {
                    endDate = project.getEndDate();
                    endYear = project.getEndYear();
                } else {
                    if (project.getEndDate().getTime() > endDate.getTime()) {
                        endDate = project.getEndDate();
                        endYear = project.getEndYear();
                    }
                }
            }
            dataMap.put("beginYear", beginYear);
            dataMap.put("endYear", endYear);
            dataMap.put("beginDate", beginDate);
            dataMap.put("endDate", endDate);
        }
        return dataMap;
    }

    private List< InitMemberEntity > setInitMember(ProjectEntity projectEntity, UserInfo userInfo) {
        List< InitMemberEntity > list = new ArrayList<>();
        for (int i = projectEntity.getBeginYear(); i <= projectEntity.getEndYear(); i++) {
            InitMemberEntity initMemberEntity = new InitMemberEntity(userInfo.getUserId(), userInfo.getMsUserId(),
                    new Date(), projectEntity.getMasterENumber(), true, userInfo.getCompanyId(), projectEntity.getId(), i, null);
            list.add(initMemberEntity);
        }
        return list;
    }

    private String getRdTitle(Integer year, Integer rdIndex, Integer companyId, String rdNumber) {
        String rdTitle = "";
        String yearStr = String.valueOf(year);
        String rdIndexStr = rdIndex > 9 ? String.valueOf(rdIndex) : "0" + rdIndex;
        //rdNumber存在“”情况
        if (rdNumber != null) {
            rdTitle = MessageFormat.format("{0}RD{1}.{2}", yearStr, rdIndexStr, rdNumber);
        } else {
            rdTitle = MessageFormat.format("{0}RD{1}", yearStr, rdIndexStr);
        }
        return rdTitle;
    }

    @Override
    public Boolean setRdAmount(UserInfo userInfo, SetRdAmountModel model) throws OwnerException {
        if (CollectionUtils.isEmpty(model.getpRdIds())) {
            throw new OwnerException("请选择要修改的数据");
        }
        CheckStatusModel checkModel = new CheckStatusModel(model.getProjectId(), model.getMonth());
        commonService.checkStatus(Arrays.asList(checkModel), model.getRdTypes(), userInfo);
        switch (CostEnum.getCostEnum(model.getRdTypes().get(0))) {
            case SALARY:
            case INSURANCE:
                return setEmployeeRdAmount(userInfo, model);
            case LAB:
            case PROD:
            case ASSETS_AMORTIZATION:
            case STIMULUS_PROD:
                return setEquipmentRdAmount(userInfo, model);
            case MATERIAL:
            case TRIAL_MATERIAL:
            case REPAIR_MATERIAL:
            case PAPER_MATERIAL:
            case PAPER_TRIAL:
            case IRON_MATERIAL:
            case IRON_TRIAL:
                return setMaterialRdAmount(userInfo, model);
            case TRIAL_STIMULUS:
            case STIMULUS:
            case FUEL:
            case IRON_STIMULUS:
            case IRON_FUEL:
                return setEnergyRdAmount(userInfo, model);

        }
        return false;
    }

    @Override
    public List< ProjectInfoModel > getYearSelectList(Integer companyId, Integer year) {
        return projectDao.getYearSelectList(companyId, year);
    }

    @Override
    public ProjectEntity getProject(Integer projectId) {
        return projectDao.selectById(projectId);
    }

    @Override
    public Boolean setBudget(UserInfo userInfo, List< BudgetInfoModel > models) throws OwnerException {
        if (CollectionUtils.isEmpty(models)) {
            throw new OwnerException("请修改后再保存！");
        }
        Date date = new Date();
        Integer projectId = models.get(0).getProjectId();
        ProjectEntity project = projectDao.selectById(projectId);
        Map< Integer, Boolean > yearMap = new HashMap<>();
        for (int i = project.getBeginYear(); i <= project.getEndYear(); i++) {
            yearMap.put(i, true);
        }
        List< ProjectYearInfoEntity > yearInfos = new ArrayList<>();
        for (BudgetInfoModel model : models) {
            if (!yearMap.containsKey(model.getYear()) || model.getBudget() == null) {
                throw new OwnerException("请输入" + model.getYear() + "年预算后保存！");
            }
            ProjectYearInfoEntity entity = new ProjectYearInfoEntity();
            BeanUtils.copyProperties(model, entity);
            entity.setCompanyId(userInfo.getCompanyId());
            entity.create(userInfo.getUserId(), userInfo.getMsUserId(), date);
            yearInfos.add(entity);
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            projectYearInfoDao.insertOrUpdate(yearInfos);
            List< ProjectYearInfoEntity > projectYearInfos = projectYearInfoDao.getByProject(projectId);
            BigDecimal totalBudget = BigDecimal.ZERO;
            for (ProjectYearInfoEntity entity : projectYearInfos) {
                if (entity.getBudget() != null) {
                    totalBudget = totalBudget.add(entity.getBudget());
                }
            }

            project.setLastUpdatorId(userInfo.getUserId());
            project.setLastUpdateTime(date);
            project.setMsLastUpdatorId(userInfo.getMsUserId());
            project.setEstimateExpense(totalBudget.multiply(Constant.TEN_THOUSAND).intValue());
            projectDao.updateById(project);
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            TransactionUtils.rollback(transactionStatus);
            logger.error(ex.getMessage(), ex);
            throw new OwnerException("保存预算失败。");
        }
        return true;
    }

    @Override
    public Map< String, Object > getGeneralLedgerData(Integer year, UserInfo userInfo) {
        List< String > sheetNames = new ArrayList<>();
        List< Map< String, Object > > list = new ArrayList<>();
        Map< String, Object > firstMap = new HashMap<>();
        loadGeneralLedgerMap(userInfo.getCompanyId(), year, sheetNames, null, list, firstMap, true);
        Map< String, Object > result = new HashMap<>();
        result.put("list", list);
        result.put("firstMap", firstMap);
        result.put("tabs", sheetNames);
        result.put("isShangHai", firstMap.get("isShangHai"));
        return result;

    }

    @Override
    public Map< String, Object > getAssistData(QueryAssistModel model) {
        List< String > sheetNames = new ArrayList<>();
        List< Map< String, Object > > list = new ArrayList<>();
        Map< String, Object > firstMap = new HashMap<>();
        loadGeneralLedgerMap(model.getCompanyId(), model.getYear(), sheetNames, model.getMonth(), list, firstMap, true);
        for (Map< String, Object > map : list) {
            if (map.get("rdTitle").equals(model.getRdTitle())) {
                return map;
            }
        }
        return firstMap;
    }

    @Override
    public Map< String, Object > getRdAccountRiskData(Integer year, Integer companyId) {
        String data = cDocumentDao.getData(year, companyId, 0);
        Map< String, Object > map = new HashMap<>();
        if (!StringUtils.isEmpty(data)) {
            map = JsonUtils.jsonToPojo(data, Map.class);
        }
        return map;
    }


    @Override
    public Map< String, Object > getRdAccountData(Integer year, Integer companyId) {
        String data = cDocumentDao.getData(year, companyId, 1);
        Map< String, Object > map = new HashMap<>();
        if (!StringUtils.isEmpty(data)) {
            map = JsonUtils.jsonToPojo(data, Map.class);
        }
        return map;
    }

    @Override
    public Boolean saveRdAccountData(CDocumentModel model, UserInfo info) {

        Date date = new Date();
        Integer id = cDocumentDao.getId(model);

        //构建对应的p_report类用于保存
        ReportEntity reportEntity = reportDao.queryByYear(info.getCompanyId(), model.getYear());
        if (null == reportEntity || null == reportEntity.getId()) {
            reportEntity = new ReportEntity();
            reportEntity.create(info.getUserId(), info.getMsUserId(), date);
            reportEntity.setRname("");
            reportEntity.setAccountType(model.getAccountType());
            reportEntity.setRyear(model.getYear());
            reportEntity.setCompanyId(info.getCompanyId());
        } else {
            reportEntity.update(info.getUserId(), info.getMsUserId(), date);
            reportEntity.setAccountType(model.getAccountType());
        }

        //构建对应的c_document类用于保存
        CDocumentEntity entity = new CDocumentEntity();
        BeanUtil.copyProperties(model, entity);

        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            //保存c_document表
            if (null != id) {
                entity.setId(id);
                entity.update(info.getUserId(), info.getMsUserId(), date);
                cDocumentDao.updateById(entity);
            } else {
                entity.create(info.getUserId(), info.getMsUserId(), date);
                cDocumentDao.insert(entity);
            }

            //保存p_project表
            if (null == reportEntity.getId()) {
                reportDao.insert(reportEntity);
            } else {
                reportDao.updateById(reportEntity);
            }
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Map< String, String > getMaster(Integer projectId, Integer year) {
        return projectYearInfoDao.getMapByYear(projectId, year);
    }

    @Override
    public List< MeetingCountModel > getAnnualData(Integer year, Integer companyId, Date month) throws ParseException {
        List< MeetingCountModel > annualData = new ArrayList<>();
        if (null != month) {
            annualData = docFileMeetingDao.getStatByMonth(month, companyId);
            return annualData;
        }
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");

        Date openMonth = dft.parse(year + "-01-01");
        Date endMonth = dft.parse(year + "-12-31");
        annualData = docFileMeetingDao.getAnnualData(openMonth, endMonth, companyId);
        return annualData;
    }

    @Override
    public List< MeetingFromModel > getMeetingFromList(Date month, Integer companyId) {
        List< MeetingFromModel > list = new ArrayList<>();
        List< MeetingFromModel > meetingFromModels = docFileMeetingDao.getMeetingFromData(companyId, month);
        if (CollectionUtils.isEmpty(meetingFromModels)) {
            return list;
        }
        List< Integer > fileIds = new ArrayList<>();
        meetingFromModels.forEach(item -> {
            fileIds.add(item.getPdocFileId());
        });
        list = docFileMeetingDao.getMeetingModel(fileIds);
        Map< Integer, List< DocFileMeetingEntity > > map = new HashMap<>();
        list.forEach(item -> {
            map.put(item.getPdocFileId(), item.getFiles());
        });
        meetingFromModels.forEach(item -> {
            String data = item.getData();
            Integer pdocFileId = item.getPdocFileId();
            if (!StringUtils.isEmpty(data)) {
                Map< String, String > dataMap = JsonUtils.jsonToPojo(data, Map.class);
                item.setTheme(dataMap.get("theme"));
                item.setMattersInvolved(dataMap.get("mattersInvolved"));
                item.setData(null);
                item.setCheckedList((dataMap.get("checkedList")));
            }
            if (map.containsKey(pdocFileId)) {
                item.setFiles(map.get(pdocFileId));
            }
        });
        return meetingFromModels;
    }

    @Override
    public Boolean delMeetingFile(List< Integer > ids) {
        return docFileMeetingDao.deleteBatchIds(ids) > 0;
    }

    @Override
    public void insertFile(DocFileMeetingEntity entity) {
        docFileMeetingDao.insert(entity);
    }

    @Override
    public void preview(String docPath, String filePath, OutputStream outGeneralFile) throws Exception {
        if (org.apache.commons.lang3.StringUtils.isEmpty(filePath)) {
            throw new OwnerException("无数据");
        }
        Path path = Paths.get(docPath, filePath);
        String fullPath = path.toString();
        File file = new File(fullPath);
        if (!file.exists()) {
            throw new OwnerException("不存在文件，请联系管理员");
        }
        String name = path.getFileName().toString().toLowerCase();
        if (name.endsWith(Constant.EXCEL_DOC)) {
            IoUtil.write(outGeneralFile, false, YsWordUtils.docToHtml(fullPath).getBytes());
        } else if (name.endsWith(Constant.EXCEL_DOCX)) {
            IoUtil.write(outGeneralFile, false, YsWordUtils.docxToHtml(fullPath).getBytes());
        } else if (name.endsWith(Constant.EXCEL_XLS) || name.endsWith(Constant.EXCEL_XLSX)) {
            IoUtil.write(outGeneralFile, false, YsExcelUtils.excelToHtml(fullPath, name).getBytes());
        } else if (name.endsWith(Constant.EXCEL_PPT) || name.endsWith(Constant.EXCEL_PPTX)) {
            IoUtil.write(outGeneralFile, false, YsPptUtils.toHtml(fullPath).getBytes());
        } else if (name.endsWith(Constant.EXCEL_PDF)) {
            IoUtil.copy(new FileInputStream(file), outGeneralFile);
        } else {
            IoUtil.copy(new FileInputStream(file), outGeneralFile);
        }
        outGeneralFile.flush();
        outGeneralFile.close();
    }

    @Override
    public List< ProjectSelectModel > getSimpleList(Integer companyId, Integer year, Integer sign) {
        return projectDao.getSimpleList(companyId, year, sign);
    }

    @Override
    public Boolean setBudgetTable(UserInfo userInfo, List< SaveBudgetModel > models) throws OwnerException {
        if (CollectionUtils.isEmpty(models)) {
            throw new OwnerException("保存失败！数据不能为空！");
        }
        Integer projectId = models.get(0).getProjectId();
        Date now = new Date();
        Integer userId = userInfo.getUserId(), msUserId = userInfo.getMsUserId(), companyId = userInfo.getCompanyId();
        ProjectEntity project = projectDao.selectById(projectId);
        List< BudgetEntity > list = budgetDao.getListByProject(projectId, project.getBeginYear(), project.getEndYear());
        List< BudgetEntity > insertList = new ArrayList<>();
        Map< String, BudgetEntity > oldDataMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(item -> oldDataMap.put(item.getYear() + item.getKey(), item));
        }
        List< BudgetEntity > updateList = new ArrayList<>();
        models.forEach(item -> {
            String key = item.getKey();
            BigDecimal value = (null == item.getValue() ? BigDecimal.ZERO : item.getValue());
            BudgetEntity entity;
            if (!"spending01".equals(key)) {
                key = key.substring(1);
            }
            entity = oldDataMap.get(item.getYear() + key);
            if (entity == null) {
                entity = new BudgetEntity();
                BeanUtils.copyProperties(item, entity);
                entity.setKey(key);
                entity.setCompanyId(companyId);
                entity.setValue(value);
                entity.create(userId, msUserId, now);
                insertList.add(entity);
            } else if (entity.getValue().compareTo(value) != 0) {
                entity.setValue(value);
                entity.update(userId, msUserId, now);
                updateList.add(entity);
            }
        });
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction();
            if (!CollectionUtils.isEmpty(updateList)) {
                budgetDao.updateBatch(updateList);
            }
            if (!CollectionUtils.isEmpty(insertList)) {
                budgetDao.addBatch(insertList);
            }
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException("保存预算失败");
        }

    }

    @Override
    public List< ChangeRecordModel > getProjectChangeRecord(Integer projectId, UserInfo userInfo) {
        return projectChangeDao.getChangeList(projectId, userInfo.getCompanyId());
    }
    private boolean checkProjectName(String pname, Integer projectId) {
        Integer line = projectDao.checkProjectName(pname);
        if (line != null && !line.equals(projectId) && line > 0) {
            return false;
        }
        return true;
    }

    @Override
    public Integer saveProjectChangeRecord(ChangeRecordModel changeRecordModel, UserInfo userInfo) throws OwnerException {
        String insertContent = changeRecordModel.getContent();
        Integer companyId = userInfo.getCompanyId();
        Integer projectId = changeRecordModel.getProjectId();
        Integer changeType = changeRecordModel.getChangeType();
        if (!StringUtils.hasText(insertContent)) {
            throw new OwnerException("变更内容不能为空!");
        } else if (changeType.equals(1) && !(insertContent.contains("ename") && insertContent.contains("enumber"))) {
            throw new OwnerException("负责人不能为空!");
        }

        Date changeTime = changeRecordModel.getChangeTime();
        Integer insertId = changeRecordModel.getId();
        List<ChangeRecordModel> hasList =  projectChangeDao.selectByTypeAndTime(insertId, projectId, companyId, insertContent, changeTime);
        if (hasList.size() > 0) {
            throw new OwnerException("当前项目名称相同或存在相同生效时间!");
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        ProjectChangeEntity projectChangeEntity = new ProjectChangeEntity();

        // 判断是否为项目名称类型最后一条 是则更新p_project pname
        ProjectEntity projectEntity = null;
        Date currChangeTime = changeRecordModel.getChangeTime();
        if (changeType.equals(0)) {
            ChangeRecordModel lastHistoryEntity = projectChangeDao.getLastHistory(projectId, companyId);
            if (!(lastHistoryEntity != null && DateUtil.compareDate(currChangeTime, lastHistoryEntity.getChangeTime()) < 0)) {
                String pname = changeRecordModel.getContent();
                if (checkProjectName(pname, projectId)) {
                    projectEntity = new ProjectEntity();
                    projectEntity.setId(projectId);
                    projectEntity.setPname(pname);
                } else {
                    String message = MessageFormat.format("项目名称【{0}】与其他项目名称重复，不能保存!", pname);
                    throw new OwnerException(message);
                }
            }
        }
        try {
            if (projectEntity != null) {
                projectDao.updateById(projectEntity);
            }
            projectChangeEntity.setChangeType(changeType);
            projectChangeEntity.setChangeTime(currChangeTime);
            projectChangeEntity.setRemark(changeRecordModel.getRemark());
            projectChangeEntity.setContent(changeRecordModel.getContent());
            projectChangeEntity.setProjectId(changeRecordModel.getProjectId());
            projectChangeEntity.setCompanyId(userInfo.getCompanyId());
            Date now = new Date();
            projectChangeEntity.setLastUpdateTime(now);
            projectChangeEntity.setLastUpdatorId(userInfo.getUserId());
            projectChangeEntity.setMsLastUpdatorId(userInfo.getMsUserId());
            if (insertId == null) {
                // 插入
                projectChangeEntity.setCreateTime(now);
                projectChangeEntity.setCreatorId(userInfo.getUserId());
                projectChangeEntity.setMsCreatorId(userInfo.getMsUserId());

                projectChangeDao.insert(projectChangeEntity);
            } else {
                // 修改
                projectChangeEntity.setId(insertId);
                projectChangeDao.updateById(projectChangeEntity);
            }
            TransactionUtils.commit(transactionStatus);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException("系统异常!");
        }
        return projectChangeEntity.getId();
    }

    @Override
    public String delProjectChangeRecord(ChangeRecordModel changeRecordModel, UserInfo userInfo) throws OwnerException {
        Integer projectId = changeRecordModel.getProjectId();
        Integer historyId = changeRecordModel.getId();
        ProjectChangeEntity currHistoryEntity = projectChangeDao.selectById(historyId);

        if (projectId == null) {
            throw new OwnerException("请选择项目!");
        }
        if (historyId == null || currHistoryEntity == null) {
            throw new OwnerException("请选择已保存记录中要删除的记录!");
        }

        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            Integer companyId = userInfo.getCompanyId();
            // 判断是否为项目记录中最后一条 是则更新项目名称 p_project pname
            projectChangeDao.deleteById(historyId);
            Integer changeType = currHistoryEntity.getChangeType();
            if (changeType.equals(0)) {
                ChangeRecordModel lastHistoryModel = projectChangeDao.getLastHistory(projectId, companyId);
                Date currChangeTime = currHistoryEntity.getChangeTime();
                if (lastHistoryModel != null && DateUtil.compareDate(currChangeTime, lastHistoryModel.getChangeTime()) >= 0) {
                    String lastProjectName = lastHistoryModel.getContent();
                    if (!checkProjectName(lastProjectName, projectId)) {
                        TransactionUtils.rollback(transactionStatus);
                        return lastProjectName;
                    }
                    ProjectEntity projectEntity = new ProjectEntity();
                    projectEntity.setId(projectId);
                    projectEntity.setPname(lastProjectName);
                    projectDao.updateById(projectEntity);
                }
            }
            TransactionUtils.commit(transactionStatus);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException("系统异常!");
        }
        return null;
    }

    private Boolean setEmployeeRdAmount(UserInfo userInfo, SetRdAmountModel model) throws OwnerException {
        List< ProjectRdEmployeeModel > rdEmployeeModels = projectRdEmployeeDao.getRdEmployeeList(model.getpRdIds());
        if (CollectionUtils.isEmpty(rdEmployeeModels)) {
            throw new OwnerException("要修改的数据不存在或已删除，更新失败。");
        }
        List< AdjustEntity > adjustEntities = adjustDao.getByTypeIds(model.getpRdIds(), model.getRdTypes());
        Map< String, BigDecimal > dataMap = new HashMap<>();
        String keyFormat = "{0}_{1}";
        adjustEntities.forEach(item -> {
            dataMap.put(MessageFormat.format(keyFormat, item.getRdType(), item.getpRdId()), item.getAmount());
        });
        Date now = new Date();
        List< AdjustEntity > adjustList = new ArrayList<>();
        List< ProjectRdEmployeeEntity > updateList = new ArrayList<>();
        BigDecimal payAmount = model.getAmount();
        BigDecimal insuranceAmount = model.getOtherAmount();
        boolean hasPay = payAmount != null;
        boolean hasInsurance = insuranceAmount != null;
        int hourBit = companySettingService.getEmployeeHourBit(userInfo.getCompanyId());
        for (ProjectRdEmployeeModel employeeModel : rdEmployeeModels) {
            if (hasPay) {
                if (payAmount.abs().compareTo(employeeModel.getPay().abs()) > 0) {
                    throw new OwnerException(MessageFormat.format("【{0}】【{1}】调整后的研发工资不能超过实际工资{2}",
                            employeeModel.getEnumber(), employeeModel.getEnumber(), employeeModel.getPay()));
                }
                adjustList.add(setValue(null, now, userInfo, employeeModel.getId(), model, CostEnum.SALARY.getType(),
                        payAmount.subtract(employeeModel.getRdPay().
                                subtract(dataMap.getOrDefault(MessageFormat.format(keyFormat, CostEnum.SALARY.getType(), employeeModel.getId()),
                                        BigDecimal.ZERO)))));
                employeeModel.setRdPay(payAmount);
            }
            if (hasInsurance) {
                if (insuranceAmount.abs().compareTo(employeeModel.getInsuranceFund().abs()) > 0) {
                    throw new OwnerException(MessageFormat.format("【{0}】【{1}】调整后的研发五险一金不能超过实际五险一金{2}",
                            employeeModel.getEnumber(), employeeModel.getEnumber(), employeeModel.getInsuranceFund()));
                }
                adjustList.add(setValue(null, now, userInfo, employeeModel.getId(), model, CostEnum.INSURANCE.getType(),
                        insuranceAmount.subtract(employeeModel.getRdInsuranceFund().subtract(
                                dataMap.getOrDefault(MessageFormat.format(keyFormat, CostEnum.INSURANCE.getType(), employeeModel.getId()),
                                        BigDecimal.ZERO)))));
                employeeModel.setRdInsuranceFund(insuranceAmount);
            }
            employeeModel.setMonth(model.getMonth());
            ProjectRdEmployeeEntity entity = ProjectRdEmployeeEntity.build(now, model.getProjectId(), userInfo, employeeModel, hourBit);
            entity.setId(employeeModel.getId());
            updateList.add(entity);
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            projectRdEmployeeDao.updateBatch(updateList);
            if (!CollectionUtils.isEmpty(adjustList)) {
                adjustDao.insertOrUpdate(adjustList);
            }
            projectRdEmployeeService.insertSummary(now, CollUtil.newArrayList(model.getProjectId()), CollUtil.newArrayList(model.getMonth()), userInfo, false);
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            TransactionUtils.rollback(transactionStatus);
            logger.error(ex.getMessage(), ex);
            throw new OwnerException("保存数据失败。");
        }
        return true;
    }

    private Boolean setEquipmentRdAmount(UserInfo userInfo, SetRdAmountModel model) throws OwnerException {
        List< ProjectRdEquipmentModel > rdEquipmentModels = projectRdEquipmentDao.getRdEquipmentList(model.getpRdIds(),
                cn.hutool.core.date.DateUtil.year(model.getMonth()));
        if (CollectionUtils.isEmpty(rdEquipmentModels)) {
            throw new OwnerException("要修改的数据不存在或已删除，更新失败。");
        }
        Map< Integer, List< ProjectRdEquipmentModel > > typeMap = new HashMap<>();
        boolean isEquipment = false;
        Date now = new Date();
        for (Integer rdType : model.getRdTypes()) {
            if (rdType == CostEnum.LAB.getType() || rdType == CostEnum.PROD.getType() ||
                    rdType == CostEnum.ASSETS_AMORTIZATION.getType()) {
                isEquipment = true;
            }
            typeMap.put(rdType, new ArrayList<>());
        }
        List< AdjustEntity > adjustEntities = adjustDao.getByTypeIds(model.getpRdIds(), model.getRdTypes());
        Map< String, BigDecimal > dataMap = new HashMap<>();
        String keyFormat = "{0}_{1}";
        adjustEntities.forEach(item -> {
            dataMap.put(MessageFormat.format(keyFormat, item.getRdType(), item.getpRdId()), item.getAmount());
        });
        Integer stuimulusProd = CostEnum.STIMULUS_PROD.getType();
        rdEquipmentModels.forEach(item -> {
            if (typeMap.containsKey(item.getEtype())) {
                typeMap.get(item.getEtype()).add(item);
            } else {
                typeMap.get(stuimulusProd).add(item);
            }
        });
        List< AdjustEntity > adjustList = new ArrayList<>();
        List< ProjectRdEquipmentEntity > updateList = new ArrayList<>();
        BigDecimal amount = model.getAmount();
        Integer companyId = userInfo.getCompanyId();
        Integer userId = userInfo.getUserId();
        Integer msUserId = userInfo.getMsUserId();
        int hourBit = companySettingService.getEquipmentHourBit(companyId);
        for (Integer type : typeMap.keySet()) {
            List< ProjectRdEquipmentModel > currentList = typeMap.get(type);
            BigDecimal adjustAmount;
            for (ProjectRdEquipmentModel equipmentModel : currentList) {
                if (isEquipment) {
                    if (amount.abs().compareTo(equipmentModel.getDepreciation().abs()) > 0) {
                        throw new OwnerException(MessageFormat.format("【{0}】【{1}】调整后的研发费用不能超过实际折旧费用{2}",
                                equipmentModel.getEname(), equipmentModel.getEcode(), equipmentModel.getDepreciation()));
                    }
                    adjustAmount = amount.subtract(equipmentModel.getRdDepreciation().subtract(dataMap.getOrDefault(
                            MessageFormat.format(keyFormat, type, equipmentModel.getId()), BigDecimal.ZERO
                    )));
                    equipmentModel.setRdDepreciation(amount);
                } else {
                    adjustAmount = amount.subtract(equipmentModel.getPowerRate().subtract(dataMap.getOrDefault(
                            MessageFormat.format(keyFormat, type, equipmentModel.getId()), BigDecimal.ZERO
                    )));
                    equipmentModel.setPowerRate(amount);
                }
                adjustList.add(setValue(null, now, userInfo, equipmentModel.getId(), model, type, adjustAmount));
                equipmentModel.setMonth(model.getMonth());
                equipmentModel.setProjectId(model.getProjectId());
                ProjectRdEquipmentEntity entity = ProjectRdEquipmentEntity.build(now, userId, msUserId, companyId, equipmentModel, hourBit);
                entity.setId(equipmentModel.getId());
                entity.setPowerRate(equipmentModel.getPowerRate());
                updateList.add(entity);
            }
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            adjustDao.insertOrUpdate(adjustList);
            insertEquipmentSummary(updateList, now, model.getProjectId(), model.getMonth(), userInfo, isEquipment);
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            TransactionUtils.rollback(transactionStatus);
            logger.error(ex.getMessage(), ex);
            throw new OwnerException("保存数据失败。");
        }
        return true;
    }

    @Autowired
    private MaterialDao materialDao;
    @Autowired
    private AdjustDao adjustDao;

    private Boolean setMaterialRdAmount(UserInfo userInfo, SetRdAmountModel model) throws OwnerException {
        Date date = new Date();
        List< ProjectMaterialEntity > materials = projectMaterialDao.selectBatchIds(model.getpRdIds());
        if (CollectionUtils.isEmpty(materials)) {
            throw new OwnerException("要修改的数据不存在或已删除，更新失败");
        }
        Integer rdType = model.getRdTypes().get(0);
        Map< Integer, BigDecimal > usedNumMap = new HashMap<>();
        List< Integer > materialIds = new ArrayList<>();
        List< Integer > adjustIds = new ArrayList<>();
        materials.forEach(item -> {
            materialIds.add(item.getMaterialDataId());
            usedNumMap.put(item.getMaterialDataId(), item.getUsed());
            adjustIds.add(item.getId());

        });
        List< MaterialEntity > materialEntityList = materialDao.selectBatchIds(materialIds);
        for (MaterialEntity materialEntity : materialEntityList) {
            BigDecimal maxNum = materialEntity.getRemainQuantity().add(usedNumMap.get(materialEntity.getId()));
            BigDecimal maxAmount = materialEntity.getUnitPrice().multiply(maxNum);
            //修改后的研发费用
            if (maxAmount.abs().compareTo(model.getAmount().abs()) < 0) {
                throw new OwnerException("【" + materialEntity.getMname() + "】调整后的研发费用不能超过" + maxAmount);
            }
        }
        List< AdjustEntity > adjustEntities = adjustDao.getByTypeIds(adjustIds, model.getRdTypes());
        Map< Integer, AdjustEntity > dataMap = adjustEntities.stream().collect(Collectors.toMap(AdjustEntity::getpRdId, e -> e));
        List< AdjustEntity > adjusts = new ArrayList<>();
        for (ProjectMaterialEntity entity : materials) {
            AdjustEntity adjustEntity = dataMap.get(entity.getId());
            adjusts.add(setValue(adjustEntity, date, userInfo, entity.getId(), model, rdType
                    , model.getAmount().subtract(adjustEntity != null ? entity.getRdAmount().subtract(adjustEntity.getAmount()) : entity.getRdAmount())));
            entity.setRdAmount(model.getAmount());
            entity.setLastUpdateTime(date);
            entity.setLastUpdatorId(userInfo.getUserId());
            entity.setMsLastUpdatorId(userInfo.getMsUserId());
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            projectMaterialDao.updateBatch(materials);
            adjustDao.insertOrUpdate(adjusts);
            saveOrDeleteSummary(date, model.getProjectId(), model.getMonth(), userInfo, rdType);
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            TransactionUtils.rollback(transactionStatus);
            logger.error("mergeProject", ex);
        }
        return true;
    }

    private Boolean setEnergyRdAmount(UserInfo userInfo, SetRdAmountModel model) throws OwnerException {
        Date date = new Date();
        List< ProjectEnergyEntity > energyEntities = projectEnergyDao.selectBatchIds(model.getpRdIds());
        if (CollectionUtils.isEmpty(energyEntities)) {
            throw new OwnerException("数据不存在或已删除，操作失败。");
        }
        Integer rdType = model.getRdTypes().get(0);
        List< Integer > energyIds = new ArrayList<>();
        for (ProjectEnergyEntity entity : energyEntities) {
            energyIds.add(entity.getEnergyDataId());
        }
        List< EnergyEntity > energys = energyDao.selectBatchIds(energyIds);
        List< ProjectEnergyEntity > updateProjectEnergys = new ArrayList<>();
        Map< Integer, BigDecimal > remainAmountMap = new HashMap<>();
        Map< Integer, String > messageMap = new HashMap<>();
        for (EnergyEntity energy : energys) {
            remainAmountMap.put(energy.getId(), energy.getRemainAmount());
            messageMap.put(energy.getId(), energy.getEname());
        }
        List< AdjustEntity > adjustEntities = adjustDao.getByTypeIds(model.getpRdIds(), model.getRdTypes());
        Map< Integer, AdjustEntity > adjustMap = adjustEntities.stream().collect(Collectors.toMap(AdjustEntity::getpRdId, e -> e));
        List< AdjustEntity > adjustEntityList = new ArrayList<>();
        for (ProjectEnergyEntity pEntity : energyEntities) {
            BigDecimal remainAmount = remainAmountMap.get(pEntity.getEnergyDataId());
            BigDecimal maxAmount = pEntity.getRdAmount().add(remainAmount);
            if (model.getAmount().abs().compareTo(maxAmount.abs()) > 0) {
                throw new OwnerException("【" + messageMap.get(pEntity.getEnergyDataId()) + "】调整后研发费用不能超过" + maxAmount);
            }
            AdjustEntity adjustEntity = adjustMap.get(pEntity.getId());
            adjustEntityList.add(setValue(adjustEntity, date, userInfo, pEntity.getId(), model, rdType,
                    model.getAmount().subtract(adjustEntity != null ? pEntity.getRdAmount().subtract(adjustEntity.getAmount()) : pEntity.getRdAmount())));
            pEntity.setRdAmount(model.getAmount());
            pEntity.setLastUpdateTime(date);
            pEntity.setLastUpdatorId(userInfo.getUserId());
            pEntity.setMsLastUpdatorId(userInfo.getMsUserId());
            updateProjectEnergys.add(pEntity);
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            projectEnergyDao.updateList(updateProjectEnergys);
            adjustDao.insertOrUpdate(adjustEntityList);
            insertSummaryEntity(model.getProjectId(), date, model.getMonth(), userInfo, rdType, model.getType());
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            TransactionUtils.rollback(transactionStatus);
            logger.error("mergeProject", ex);
        }
        return true;

    }


    private void saveOrDeleteSummary(Date now, Integer projectId, Date month, UserInfo info, Integer type) {
        Date startDate = DateUtil.getMonthFirstDay(month);
        Date endDate = DateUtil.getMonthLastDay(startDate);
        // 查出该项目该月份内的物料list
        BigDecimal rdFunds = projectMaterialDao
                .queryMaterialListByProjectIdAndDateAndType(info.getCompanyId(), projectId, startDate, endDate,
                        type);
        if (rdFunds == null || rdFunds.compareTo(BigDecimal.ZERO) == 0) {
            summaryDao.deleteInfo(projectId, startDate, CollUtil.newArrayList(type));
            return;
        }
        // 统计材料和试制材料的总费用
        summaryDao.insertOrUpdate(CollUtil.newArrayList(
                ToolUtils.build(now, startDate, projectId, type, rdFunds, info)
        ));
    }

    /**
     * 添加汇总信息
     *
     * @param projectId
     * @param now
     * @param month
     * @return
     */
    private boolean insertSummaryEntity(Integer projectId, Date now, Date month, UserInfo userInfo, Integer etype, Integer type) {
        Date monthBegin = DateUtil.getMonthFirstDay(month);
        Date monthEnd = DateUtil.getMonthLastDay(month);
        List< EnergySumModel > energySum = projectEnergyDao.getEnergySum(projectId, monthBegin, monthEnd, etype, type);
        energySum.remove(null);
        if (energySum.size() > 0) {
            List< SummaryEntity > summaryEntities = new ArrayList<>();
            energySum.forEach(item -> {
                summaryEntities.add(ToolUtils.build(now, month, item.getProjectId(), etype, item.getRdAmount(), userInfo));
            });
            if (summaryEntities.size() > 0) {
                summaryDao.insertOrUpdate(summaryEntities);
                return true;
            }
        } else {
            summaryDao.deleteInfo(projectId, month, CollUtil.newArrayList(etype));
            return true;
        }
        return false;
    }


    private AdjustEntity setValue(AdjustEntity adjustEntity, Date now, UserInfo userInfo, Integer pRdId,
                                  SetRdAmountModel model, Integer rdType, BigDecimal amount) {
        if (adjustEntity == null) {
            adjustEntity = new AdjustEntity();
            adjustEntity.setCreateTime(now);
            adjustEntity.setMsCreatorId(userInfo.getMsUserId());
            adjustEntity.setpRdId(pRdId);
            adjustEntity.setProjectId(model.getProjectId());
            adjustEntity.setMonth(model.getMonth());
            adjustEntity.setRdType(rdType);
            adjustEntity.setCompanyId(userInfo.getCompanyId());
        }
        adjustEntity.setMsLastUpdatorId(userInfo.getMsUserId());
        adjustEntity.setLastUpdateTime(now);
        adjustEntity.setAmount(amount);
        return adjustEntity;
    }

    private void insertEquipmentSummary(List< ProjectRdEquipmentEntity > updateList, Date now, Integer projectId, Date month, UserInfo userInfo, Boolean isEquipment) {
        if (isEquipment) {
            projectRdEquipmentDao.insertOrUpdate(updateList);
            projectRdEquipmentService.insertSummary(now, CollUtil.newArrayList(projectId), CollUtil.newArrayList(month), userInfo, false);
        } else {
            projectRdEquipmentDao.updateBatch(updateList, now, userInfo.getUserId(), userInfo.getMsUserId());
            BigDecimal totalFunds = projectRdEquipmentDao.getMonthPowerTotal(month, projectId);
            summaryDao.insertOrUpdate(CollUtil.newArrayList(ToolUtils.build(now, month, projectId, CostEnum.STIMULUS_PROD.getType(), totalFunds, userInfo)));
        }
    }

    /**
     * 获取辅助帐总表项目列表map
     *
     * @param companyId
     * @param year
     * @return
     */
    private Map< Integer, Map< String, Object > > getProjectMap(Integer companyId, Integer year) {
        List< ProjectDetailModel > projectList = projectDao.getProjectIdsByYear(companyId, year, true); // // TODO: 21/06/11 zdf child改为了true
        Map< Integer, Map< String, Object > > projectMap = new LinkedHashMap<>();
        Date now = new Date();
        projectList.forEach(detailModel -> {
            Map< String, Object > pMap = new HashMap<>();
            pMap.put("pname", detailModel.getPname());
            pMap.put("cost", "费用化支出");
            pMap.put("result", "其他技术成果");
            if (detailModel.getFormula() == 30) {
                pMap.put("rd", "委托研发");
                pMap.put("trustExist", "存在");
                pMap.put("trust", "委托境内");
            } else {
                if (detailModel.getFormula() == 10) {
                    pMap.put("rd", "自主研发");
                } else {
                    pMap.put("rd", "合作研发");
                }
                pMap.put("trust", "非委托项目");
                pMap.put("trustExist", "非委托研发");
            }
            String finish = "未完成";
            if (detailModel.getEndDate().compareTo(now) <= 0 && cn.hutool.core.date.DateUtil.year(detailModel.getEndDate()) <= year) {
                finish = "完成";
            }
            pMap.put("finish", finish);
            pMap.put("rdTitle", detailModel.getRdTitle());
            projectMap.put(detailModel.getId(), pMap);
        });
        return projectMap;
    }

}
