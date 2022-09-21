package com.yskc.ms.service.impl.ms;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.AuditModeEnum;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.enums.FlowInstanceStatusEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.PageResult;
import com.yskc.common.utils.JsonUtils;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.config.Constant;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.dao.ms.*;
import com.yskc.ms.dao.rs.*;
import com.yskc.ms.entity.ms.*;
import com.yskc.ms.entity.rs.*;
import com.yskc.ms.entity.rs.models.ProjectListModel;
import com.yskc.ms.enums.MemberTypeEnum;
import com.yskc.ms.enums.ScoreElementEnum;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.MiniModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.flow.FlowInstanceUserModel;
import com.yskc.ms.models.flowInstance.FlowInstanceModel;
import com.yskc.ms.models.flowInstance.RdInstanceModel;
import com.yskc.ms.models.innovationproject.InnovationMemberModel;
import com.yskc.ms.models.patentPlan.PatentAuditModel;
import com.yskc.ms.models.patentPlan.PatentFileModel;
import com.yskc.ms.models.patentPlan.PatentPlanModel;
import com.yskc.ms.models.project.*;
import com.yskc.ms.models.projectAudit.*;
import com.yskc.ms.models.projectsummary.ProjectSummaryTotal;
import com.yskc.ms.models.qualityscore.QualityScoreInfoModel;
import com.yskc.ms.models.qualityscore.QualityScoreModel;
import com.yskc.ms.models.rs.DocAuditModel;
import com.yskc.ms.models.rs.RdDeptTree;
import com.yskc.ms.models.rs.RsProjectDocFileModel;
import com.yskc.ms.models.rs.RsStageModel;
import com.yskc.ms.service.ms.ProjectProgressService;
import com.yskc.ms.service.rs.KafkaQueueService;
import com.yskc.ms.utils.ToolUtil;
import com.yskc.ms.utils.TransactionUtils;
import com.yskc.ms.utils.YsExcelUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service.impl.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-01-08 16:12
 * @Description: 项目进度业务实现层
 */
@Service
public class ProjectProgressServiceImpl implements ProjectProgressService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ProjectProgressDetailDao projectProgressDetailDao;
    @Autowired
    private ProjectMemberDao projectMemberDao;
    @Autowired
    private RsDeptDao rsDeptDao;
    @Autowired
    private ProjectDocFileDao projectDocFileDao;
    @Autowired
    private FlowInstanceLogDao flowInstanceLogDao;
    @Autowired
    private FlowNodeDao flowNodeDao;
    @Autowired
    private FlowInstanceDao flowInstanceDao;
    @Autowired
    private FlowModuleDao flowModuleDao;
    @Autowired
    private FlowInstanceUserDao flowInstanceUserDao;
    @Autowired
    private ProjectRdEmployeeDao rdEmployeeDao;
    @Autowired
    private ProjectRdEquipmentDao rdEquipmentDao;
    @Autowired
    private RsPatentDao patentDao;
    @Autowired
    private ProjectDocFileDataDao projectDocFileDataDao;
    @Autowired
    private ProjectSummaryDataDao projectSummaryDataDao;
    @Autowired
    private FlowInstanceDocFileDao flowInstanceDocFileDao;
    @Autowired
    private RsStageDao stageDao;
    @Autowired
    private FlowInstanceProjectDao flowInstanceProjectDao;
    @Autowired
    private KafkaQueueService kafkaQueueService;
    @Autowired
    private FlowInstanceReportDao flowInstanceReportDao;
    @Autowired
    private RsProjectDao rsProjectDao;
    @Autowired
    private FlowInstancePatentDao flowInstancePatentDao;

    @Autowired
    private AuditReportDao auditReportDao;

    @Autowired
    private InnovationProjectDao innovationProjectDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private MsConfig msConfig;
    @Autowired
    private PatentPlanDao patentPlanDao;
    @Autowired
    private PatentFileDao patentFileDao;
    @Autowired
    private FlowInstanceAchievementDao flowInstanceAchievementDao;
    @Autowired
    private RsAchievementDao rsAchievementDao;
    @Autowired
    private RsAchievementFileDao rsAchievementFileDao;
    @Autowired
    private FlowInstanceProposalDao flowInstanceProposalDao;
    @Autowired
    private ProposalManagementDao proposalManagementDao;
    @Autowired
    private RsReportDao rsReportDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private PatentPlanProcessDao patentPlanProcessDao;
    @Autowired
    private PatentMasterDao patentMasterDao;
    @Autowired
    private FlowInstanceRdFeeDao flowInstanceRdFeeDao;
    @Autowired
    private QualityScoreDao qualityScoreDao;
    @Autowired
    private QualityScoreLogDao qualityScoreLogDao;
    @Autowired
    private QualityTypeDao qualityTypeDao;
    @Autowired
    private QualityScoreMonthDao qualityScoreMonthDao;

    @Override
    public List<RsStageModel> queryStage(Integer companyId, Integer projectId, Integer userId) {
        List<RsStageModel> queryModels = stageDao.queryStage(companyId, projectId);
        List<String> stageList = new ArrayList<>();
        Map<String, RsStageModel> stageMap = new LinkedHashMap<>();
        for (RsStageModel stage : queryModels) {
            stageList.add(stage.getStageKey());
            stageMap.put(stage.getStageKey(), stage);
        }
        if (stageList.size() > 0) {
            List<RsProjectDocFileModel> projectDocFileModels = projectDocFileDao.selectByStageList(projectId, stageList);
            if (CollectionUtils.isEmpty(projectDocFileModels)) {
                return new ArrayList<>(stageMap.values());
            }
//            List<Integer> docFileIds = projectDocFileModels.stream().map(e -> e.getId()).collect(Collectors.toList());
            List<FlowInstanceDocFile> files = flowInstanceDocFileDao.getDocAudit(Arrays.asList(projectId), null, userId);
            Map<Integer, FlowInstanceDocFile> permissionMap = files.stream().collect(Collectors.toMap(e -> e.getDocFileId(), e -> e));
            for (RsProjectDocFileModel docModel : projectDocFileModels) {
                RsStageModel stage = stageMap.get(docModel.getStage());
                if (null == stage) {
                    continue;
                }
                docModel.setHasContent(docModel.getWordLength() != null && docModel.getWordLength() > 0);
                docModel.setHasPermission(permissionMap.containsKey(docModel.getId()));
                stage.addDoc(docModel);
            }
        }
        return new ArrayList<>(stageMap.values());
    }

    @Override
    public PageResult getList(QueryProjectProgressModel query, UserInfo userInfo, DataPermModel dataPerm) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getDescs()) && CollectionUtils.isEmpty(page.getAscs())) {
            page.setDescs(new ArrayList<>());
            page.getDescs().add("ongoingTotal");
            page.getDescs().add("operationTime");
        }
        List<ProjectProgressModel> list = projectProgressDetailDao.getList(page, query, dataPerm);
        if (!CollectionUtils.isEmpty(list)) {
            String format = "{0}-{1}";
            List<ProjectReportModel> reportModels = projectDocFileDao.getReportNum(list);
            Map<String, Integer> dataMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(reportModels)) {
                for (ProjectReportModel model : reportModels) {
                    dataMap.put(MessageFormat.format(format, model.getCompanyId(), model.getYear()), model.getReportNum());
                }
            }
            List<Integer> groupIds = list.stream().filter(a -> a.getGroupId() > 0).map(a -> a.getGroupId()).distinct().collect(Collectors.toList());
            Map<Integer, String> groupMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(groupIds)) {
                List<MiniModel> groups = customerDao.getGroupList(groupIds);
                groups.forEach(item -> groupMap.put(item.getId(), item.getTitle()));
            }
            if (!CollectionUtils.isEmpty(dataMap) || !CollectionUtils.isEmpty(groupMap)) {
                for (ProjectProgressModel progressModel : list) {
                    progressModel.setReportNum(dataMap.get(MessageFormat.format(format, progressModel.getCompanyId(), progressModel.getYear())));
                    progressModel.setGroupName(groupMap.get(progressModel.getGroupId()));
                }
            }
            loadAuditTotal(userInfo.getId(), list, format);
        }
        ProjectSummaryTotal total = projectProgressDetailDao.getTotal(query, dataPerm);
        if (total == null) {
            total = new ProjectSummaryTotal();
        }
        loadList(list);
        return PageUtils.buildPageResult(page, list, total);
    }

    private void loadAuditTotal(Integer userId, List<ProjectProgressModel> list, String keyFormat) {
        List<FlowInstanceModel> flowInstances = flowInstanceDao.getByCompanyIds(userId, list.stream().map(ProjectProgressModel::getCompanyId).collect(Collectors.toList()));
        if (CollectionUtils.isEmpty(flowInstances)) {
            return;
        }
        List<Integer> docInstances = new ArrayList<>();
        List<Integer> projectInstances = new ArrayList<>();
        List<Integer> patentInstances = new ArrayList<>();
        List<Integer> rdNameInstances = new ArrayList<>();
        List<Integer> proposalInstances = new ArrayList<>();
        List<Integer> achievementInstances = new ArrayList<>();

        for (FlowInstanceModel fi : flowInstances) {
            int type = fi.getType();
            // 项目
            if (type == 1) {
                projectInstances.add(fi.getId());
                // 过程文档 // 查新
            } else if (type == 4) {
                patentInstances.add(fi.getId());
            } else if (type == 5) {
                rdNameInstances.add(fi.getId());
            } else if (type == 6) {
                proposalInstances.add(fi.getId());
            } else if (type == 7) {
                achievementInstances.add(fi.getId());
            } else {
                docInstances.add(fi.getId());
            }
        }
        Map<String, Integer> auditTotalMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(projectInstances)) {
            List<ProjectProgressModel> projectList = flowInstanceProjectDao.getAuditTotal(projectInstances);
            if (!CollectionUtils.isEmpty(projectList)) {
                projectList.forEach(item -> {
                    String key = MessageFormat.format(keyFormat, item.getCompanyId(), item.getYear());
                    Integer count = auditTotalMap.getOrDefault(key, 0);
                    auditTotalMap.put(key, count + item.getAuditTotal());
                });
            }
        }
        List<RdInstanceModel> docFileList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(docInstances)) {
            docFileList.addAll(flowInstanceDocFileDao.getProjectInfo(docInstances));
        }
        // 专利和过程文件取得数据一样
        if (!CollectionUtils.isEmpty(patentInstances)) {
            List<PatentPlanModel> patentPlanInfos = flowInstancePatentDao.getPatentPlanInfo(patentInstances);
            patentPlanInfos.forEach(item -> {
                String key = MessageFormat.format(keyFormat, item.getCompanyId(), item.getYear());
                auditTotalMap.put(key, auditTotalMap.getOrDefault(key, 0) + 1);
            });
        }
        // 项目和过程文件取得数据一样
        if (!CollectionUtils.isEmpty(rdNameInstances)) {
            docFileList.addAll(flowInstanceReportDao.getProjectInfo(rdNameInstances));
        }

        Map<Integer, List<Integer>> projectInstancesMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(docFileList)) {
            docFileList.forEach(item -> ToolUtil.putAndAdd(projectInstancesMap, item.getRsProjectId(), item.getInstanceId()));
        }
        if (!CollectionUtils.isEmpty(proposalInstances)) {
            List<RdInstanceModel> propsalList = flowInstanceProposalDao.getProposalInfo(proposalInstances);
            if (!CollectionUtils.isEmpty(propsalList)) {
                List<ProposalManagement> proposalInfos = proposalManagementDao.getProposals(propsalList.stream().map(a -> a.getDataId()).collect(Collectors.toSet()));
                proposalInfos.forEach(item -> {
                    String key = MessageFormat.format(keyFormat, item.getCompanyId(), item.getYear());
                    auditTotalMap.put(key, auditTotalMap.getOrDefault(key, 0) + 1);
                });
            }
        }
        if (!CollectionUtils.isEmpty(achievementInstances)) {
            List<Integer> achievementIds = flowInstanceAchievementDao.getAchievementIds(achievementInstances);
            if (!CollectionUtils.isEmpty(achievementIds)) {
                List<RsAchievementEntity> achievements = rsAchievementDao.getAchievements(achievementIds);
                achievements.forEach(item -> {
                    String key = MessageFormat.format(keyFormat, item.getCompanyId(), item.getYear());
                    auditTotalMap.put(key, auditTotalMap.getOrDefault(key, 0) + 1);
                });
            }
        }
        if (!CollectionUtils.isEmpty(projectInstancesMap)) {
            List<ProjectEntity> projects = rsProjectDao.getInfos(new ArrayList<>(projectInstancesMap.keySet()));
            projects.forEach(item -> {
                if (projectInstancesMap.containsKey(item.getId())) {
                    int beginYear = item.getBeginYear();
                    while (beginYear <= item.getEndYear()) {
                        String key = MessageFormat.format(keyFormat, item.getCompanyId(), beginYear);
                        Integer count = auditTotalMap.getOrDefault(key, 0);
                        auditTotalMap.put(key, count + projectInstancesMap.get(item.getId()).size());
                        ++beginYear;
                    }
                }
            });
        }
        if (!CollectionUtils.isEmpty(auditTotalMap)) {
            list.forEach(item -> {
                String key = MessageFormat.format(keyFormat, item.getCompanyId(), item.getYear());
                item.addAuditTotal(auditTotalMap.getOrDefault(key, 0));
            });
        }
    }

    @Override
    public PageModel<List<ProjectProgressModel>> getData(QueryProjectProgressModel query, UserInfo userInfo, DataPermModel dataPerm) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getDescs()) && CollectionUtils.isEmpty(page.getAscs())) {
            page.setDescs(new ArrayList<>());
            page.getDescs().add("operationTime");
        }
        List<ProjectProgressModel> list = projectProgressDetailDao.getList(page, query, dataPerm);
        loadList(list);
        return PageUtils.build(page, list);
    }

    @Override
    public void export(QueryProjectProgressModel query, UserInfo userInfo, DataPermModel dataPerm, OutputStream out, String path) throws OwnerException {
        List<ProjectProgressModel> list = projectProgressDetailDao.getList(query, dataPerm);
        if (!CollectionUtils.isEmpty(list)) {
            loadList(list);
            String format = "{0}-{1}";
            List<ProjectReportModel> reportModels = projectDocFileDao.getReportNum(list);
            Map<String, Integer> dataMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(reportModels)) {
                for (ProjectReportModel model : reportModels) {
                    dataMap.put(MessageFormat.format(format, model.getCompanyId(), model.getYear()), model.getReportNum());
                }
            }
            List<Integer> groupIds = list.stream().filter(a -> a.getGroupId() > 0).map(a -> a.getGroupId()).distinct().collect(Collectors.toList());
            Map<Integer, String> groupMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(groupIds)) {
                List<MiniModel> groups = customerDao.getGroupList(groupIds);
                groups.forEach(item -> groupMap.put(item.getId(), item.getTitle()));
            }
            for (ProjectProgressModel progressModel : list) {
                BigDecimal feeCount = progressModel.getRdFunds();
                if (feeCount != null) {
                    if (feeCount.compareTo(new BigDecimal(0.00)) != 0) {
                        feeCount = progressModel.getRdFunds().divide(Constant.TEN_THOUSAND, 2, BigDecimal.ROUND_HALF_UP);
                    } else {
                        feeCount = null;
                    }
                }
                progressModel.setRdFunds(feeCount);
                progressModel.setReportNum(dataMap.get(MessageFormat.format(format, progressModel.getCompanyId(), progressModel.getYear())));
                progressModel.setGroupName(groupMap.get(progressModel.getGroupId()));
                //            if (progressModel.getRdDeptLevel() != null && progressModel.getRdDeptLevel() > 0) {
//                progressModel.setRdDeptLevelStr(String.valueOf(progressModel.getRdDeptLevel()));
//            } else {
//                progressModel.setRdDeptLevelStr("无");
//            }
            }
        }


        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("list", list);
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

    }

    private void loadList(List<ProjectProgressModel> list) {
        if (list.size() > 0) {

            if (!CollectionUtils.isEmpty(list)) {
                Set<Integer> productIds = new HashSet<>();
                List<Integer> ids = new ArrayList<>();
                Map<String, List<Integer>> productTypeMap = new HashMap<>();
                Integer curProductId;
                String types;
                for (ProjectProgressModel pModel : list) {
                    ids.add(pModel.getId());
                    types = pModel.getTypes();
                    if (productTypeMap.containsKey(types)) {
                        continue;
                    }
                    for (String cur : types.split(",")) {
                        curProductId = Integer.valueOf(cur);
                        ToolUtil.putAndAdd(productTypeMap, types, curProductId);
                        productIds.add(curProductId);
                    }
                }

                Map<Integer, String> productNameMap = new HashMap<>();
                if (!CollectionUtils.isEmpty(productIds)) {
                    List<Product> products = productDao.getProductNames(productIds);
                    products.forEach(item -> productNameMap.put(item.getId(), item.getProductName()));
                }
                List<InnovationMemberModel> memberList = innovationProjectDao.queryMemberByIds(ids);
                Map<String, List<String>> mapMember = new HashMap<>();
                String keyFormat = "{0}_{1}";
//                List<ProjectProgressModel> rdDeptLevels = rsDeptDao.getRdDeptLevels(list);
//                Map<String, Integer> levelMap = new HashMap<>();
//                if (!CollectionUtils.isEmpty(rdDeptLevels)) {
//                    rdDeptLevels.forEach(item -> {
//                        levelMap.put(MessageFormat.format(keyFormat, item.getCompanyId(), item.getYear()), item.getRdDeptLevel());
//                    });
//                }
                for (InnovationMemberModel ml : memberList) {
                    if (ml.getRealName() == null) {
                        continue;
                    }
                    ToolUtil.putAndAdd(mapMember, MessageFormat.format(keyFormat, ml.getInnovationId(), ml.getmType()), ml.getRealName());
                }
                for (ProjectProgressModel p : list) {
                    // String sign = MessageFormat.format("{0}_{1}", p.getCompanyId(), p.getYear());
                    // 技术人员
                    String tk = MessageFormat.format(keyFormat, p.getId(), MemberTypeEnum.Tech.getId());
                    // 财务人员
                    String fk = MessageFormat.format(keyFormat, p.getId(), MemberTypeEnum.FINANCE.getId());
                    // 研发部门层级
                    if (mapMember.containsKey(tk)) {
                        p.setTechRealName(String.join(",", mapMember.get(tk)));
                    }
                    if (mapMember.containsKey(fk)) {
                        p.setFinanceRealName(String.join(",", mapMember.get(fk)));
                    }
                    List<Integer> ptIds = productTypeMap.get(p.getTypes());
                    if (!CollectionUtils.isEmpty(ptIds)) {
                        List<String> productNames = new ArrayList<>(ptIds.size());
                        ptIds.forEach(item -> {
                            if (productNameMap.containsKey(item)) {
                                productNames.add(productNameMap.get(item));
                            }
                        });
                        p.setTypesStr(String.join(",", productNames));
                    }
//                    String dk = MessageFormat.format(keyFormat, p.getCompanyId(), p.getYear());
//                    if (levelMap.containsKey(dk)) {
//                        p.setRdDeptLevel(levelMap.get(dk));
//                    }
                }
            }
        }

    }

    @Override
    public List<ProjectStageModel> getProgressDetail(Integer year, Integer companyId) {
        if (year == null || companyId == null) {
            return new ArrayList<>();
        }
        return projectProgressDetailDao.getDetailList(year, companyId);
    }

    @Override
    public List<RdDeptTree> getRdDept(Integer companyId, Integer year) {
        return rsDeptDao.getRdDept(companyId, year);
    }

    @Override
    public Boolean projectAudit(ProjectAuditModel model, UserInfo userInfo) throws OwnerException {
        //判断当前用户有无审核权限
        Integer userId = userInfo.getId();
        FlowInstanceUser instanceUser = flowInstanceDao.getUserPerms(model.getInstanceId(), model.getNodeId(), userId);
        if (instanceUser == null) {
            throw new OwnerException("无此审核权限，请联系管理员！");
        }
        Date date = new Date();
        Integer instanceId = instanceUser.getInstanceId(), nodeId = instanceUser.getNodeId();
        FlowNodeEntity flowNode = flowNodeDao.selectById(nodeId);//当前节点
        Integer auditStatus = model.getPass() ? FlowInstanceStatusEnum.DONE.getStatus() : FlowInstanceStatusEnum.REJECT.getStatus();
        if (!flowNode.getMode().equals(0) && model.getPass()) {
            if (flowInstanceUserDao.countNodeUser(instanceId, nodeId, userId) > 0) {
                auditStatus = FlowInstanceStatusEnum.ONGOING.getStatus();
            }
        }
        FlowInstanceLog instanceLog = new FlowInstanceLog(instanceId, auditStatus, model.getSuggestion(), userId, date, model.getNodeId(), instanceUser.getLastSubmiter());
        instanceLog.setSubmiter(instanceUser.getLastSubmiter());

        instanceUser.setStatus(model.getPass());
        //项目进度页打分
        QualityScore qualityScore = null;
        QualityScoreLog scoreLog = null;
        List<Integer> updateList = new ArrayList<>();
        Boolean scoring = model.getScoring();
        if (null != scoring && scoring) {
            QualityScoreModel pasModel = model.getModel();
            if (null != pasModel) {
                Integer scoreCount = pasModel.getScoreCount();
                pasModel.setPassed(model.getPass());
                if (scoreCount < 3) {
                    if (null == pasModel.getEngineerId()) {
                        throw new OwnerException("工程师信息不能为空！");
                    }
                    Integer type = pasModel.getType();
                    Map<Integer, Integer> scoreMap = pasModel.getScores();
                    if (null != scoreMap && !CollectionUtils.isEmpty(scoreMap.values())) {
                        List<Integer> scores = new ArrayList<>(scoreMap.values());
                        BigDecimal sum = new BigDecimal(scores.stream().reduce(Integer::sum).orElse(0));
                        if (pasModel.getTotalScore().compareTo(sum) != 0) {
                            throw new OwnerException("总分计算有误，请重新计分");
                        }
                        ScoreElementEnum.ScoreCheck(scores, type);
                    }
                    qualityScore = new QualityScore();
                    BeanUtils.copyProperties(pasModel, qualityScore);
                    qualityScore.setTotalWeight(qualityScore.getWeight());
                    qualityScore.setFinal(model.getPass());
                    scoreLog = new QualityScoreLog();
                    BeanUtils.copyProperties(pasModel, scoreLog);
                    scoreLog.setPassed(model.getPass());
                    scoreLog.setScores(null == scoreMap ? null : JsonUtils.objectToJson(scoreMap));
                    qualityScore.setYear(rsProjectDao.getBeginYear(pasModel.getRsProjectId()));
                    List<QualityScoreModel> models = qualityScoreLogDao.getScoreList(pasModel, null);
                    BigDecimal totalScore = BigDecimal.ZERO;
                    if (!CollectionUtils.isEmpty(models)) {
                        if (scoreCount == 2 && !pasModel.getPassed()) {
                            qualityScore.setPassRate(totalScore);
                        }
                        for (QualityScoreModel scoreModel : models) {
                            totalScore = totalScore.add(scoreModel.getTotalScore());
                        }
                        qualityScore.setAvgScore(totalScore.add(pasModel.getTotalScore())
                                .divide(BigDecimal.valueOf(scoreCount), 2, BigDecimal.ROUND_HALF_UP));
                        scoreLog.setAvgScore(qualityScore.getAvgScore());
                        qualityScore.setId(models.get(0).getId());
                    } else {
                        if (!pasModel.getPassed()) {
                            qualityScore.setPassRate(new BigDecimal(0.8));
                        }
                        qualityScore.create(userId, date);
                        qualityScore.setAvgScore(pasModel.getTotalScore());
                        scoreLog.setAvgScore(pasModel.getTotalScore());
                    }
                    scoreLog.create(userId, date);

                    //提交时间和审核时间属性填充
                    setTime(pasModel, qualityScore);
                    scoreLog.setSubmitTime(qualityScore.getSubmitTime());
                    scoreLog.setAuditTime(qualityScore.getAuditTime());
                }
            }
        }
        //第三次评分改完成状态
        if (null != model.getModel()) {
            Integer id = model.getModel().getQualityId();
            if (null != id) {
                qualityScore = new QualityScore();
                qualityScore.setId(id);
                qualityScore.setFinal(model.getPass());
            }
        }
        List<PatentPlanEntity> planEntities;
        List<Integer> kafkaInstanceIds = new ArrayList<>();
        Integer id = null;
        TransactionStatus status = TransactionUtils.newTransaction(DataSourceEnum.MS);
        try {
            planEntities = patentPlanDao.getByInstanceId(Arrays.asList(instanceId));
            if (!CollectionUtils.isEmpty(planEntities)) {
                PatentPlanEntity entity = planEntities.get(0);
                Integer process = entity.getProcess();
                if (process > 2 && null == entity.getMasterId()) {
                    throw new OwnerException("专利【" + entity.getPatentName() + "】代理人未分配，请配置后再操作！");
                }
                Integer nodeNumber = flowNode.getNodeNumber();
                if (null != nodeNumber && nodeNumber == 2 && null != model.getMasterId()) {
                    entity.setMasterId(model.getMasterId());
                }
                entity.setProcess(nodeNumber == 7 ? 7 : nodeNumber + 1);
                entity.update(userId, date);
                PatentPlanProcess planProcess = new PatentPlanProcess(entity.getId(), userId, date, nodeNumber, model.getSuggestion());
                patentPlanProcessDao.insert(planProcess);
                patentPlanDao.updateById(entity);

                if (Arrays.asList(2, 3, 5).contains(nodeNumber)) {
                    id = patentMasterDao.getUserId(entity.getMasterId());
                    kafkaInstanceIds = Arrays.asList(model.getInstanceId());
                } else if (Arrays.asList(4, 6).contains(nodeNumber)) {
                    id = entity.getOwnerId();
                    kafkaInstanceIds = Arrays.asList(model.getInstanceId());
                }
            }

            if (auditStatus != 0) {
                flowInstanceDao.updateNodes(date, userId, Collections.singletonList(new FlowInstanceCurNode(instanceId, auditStatus, nodeId)));
            }
            if (null != qualityScore) {
                if (null == qualityScore.getId()) {
                    qualityScoreDao.insert(qualityScore);
                } else {
                    qualityScore.update(userId, date);
                    qualityScoreDao.updateById(qualityScore);
                }
                if (null != scoreLog) {
                    scoreLog.setQualityId(qualityScore.getId());
                    qualityScoreLogDao.insert(scoreLog);
                }
            }
            flowInstanceUserDao.updateById(instanceUser);
            flowInstanceLogDao.insert(instanceLog);
            TransactionUtils.commit(DataSourceEnum.MS, status);
        } catch (Exception ex) {
            TransactionUtils.rollback(DataSourceEnum.MS, status);
            throw new OwnerException(ErrorEnum.FAIL, ex.getMessage());
        }
        if (!FlowInstanceStatusEnum.isOngoing(auditStatus)) {
            if (!CollectionUtils.isEmpty(kafkaInstanceIds) && null != id) {
                kafkaQueueService.sendAudit(Constant.TOPIC_PUSH_AUDIT, kafkaInstanceIds, userId, Arrays.asList(id), null, null);
            } else {
                kafkaQueueService.sendAudit(Constant.TOPIC_PUSH_AUDIT, CollUtil.newArrayList(instanceId), userId);
            }
        }
        return true;
    }

    @Override
    public Boolean patentAudit(ProjectAuditModel model, UserInfo userInfo) throws OwnerException {
        Integer userId = userInfo.getId(),instanceId=model.getInstanceId();
        //判断当前用户有无审核权限
        FlowInstanceUser instanceUser = flowInstanceDao.getUserPerms(model.getInstanceId(), model.getNodeId(), userId);
        List<PatentPlanEntity> planEntities = new ArrayList<>();
        if (instanceUser == null) {
            throw new OwnerException("无此审核权限，请联系管理员！");
        }
        Date date = new Date();
        Integer nodeId=instanceUser.getNodeId();
        FlowNodeEntity flowNode = flowNodeDao.selectById(nodeId);//当前节点
        Integer auditStatus = model.getPass() ? FlowInstanceStatusEnum.DONE.getStatus() : FlowInstanceStatusEnum.REJECT.getStatus();
        if (!flowNode.getMode().equals(0) && model.getPass()) {
            //会签
            if (flowInstanceUserDao.countNodeUser(instanceId, instanceUser.getNodeId(), userId) > 0) {
                auditStatus = FlowInstanceStatusEnum.ONGOING.getStatus();
            }
        }
        FlowInstanceLog instanceLog = new FlowInstanceLog(instanceId, auditStatus, model.getSuggestion(), userId, date, nodeId, instanceUser.getLastSubmiter());
        instanceUser.setStatus(model.getPass());
        List<Integer> kafkaInstanceIds = new ArrayList<>();
        Integer id = null;
        PatentPlanEntity entity = null;
        String conName = null;
        Integer process = null;
        TransactionStatus status = TransactionUtils.newTransaction(DataSourceEnum.MS);
        try {

            //审批专利流程时更新专利节点
            if (model.getPass()) {
                planEntities = patentPlanDao.getByInstanceId(Arrays.asList(instanceId));
                if (!CollectionUtils.isEmpty(planEntities)) {
                    entity = planEntities.get(0);
                    conName = entity.getConfirmName();
                    process = entity.getProcess();
                    if (process > 2 && null == entity.getMasterId()) {
                        throw new OwnerException("专利【" + entity.getPatentName() + "】代理人未分配，请配置后再操作！");
                    }
                    Integer nodeNumber = flowNode.getNodeNumber();
                    if (null != nodeNumber && nodeNumber == 2 && null != model.getMasterId()) {
                        entity.setMasterId(model.getMasterId());
                    }
                    entity.setProcess(nodeNumber == 7 ? 7 : nodeNumber + 1);
                    entity.update(userId, date);
                    entity.setConfirmName(model.getConfirmName());
                    entity.setConfirmType(model.getConfirmType());
                    PatentPlanProcess planProcess = new PatentPlanProcess(entity.getId(), userId, date, nodeNumber, model.getSuggestion());
                    patentPlanProcessDao.insert(planProcess);
                    patentPlanDao.updateById(entity);

                    if (Arrays.asList(2, 3, 5).contains(nodeNumber)) {
                        id = patentMasterDao.getUserId(entity.getMasterId());
                        kafkaInstanceIds = Arrays.asList(model.getInstanceId());
                    } else if (Arrays.asList(4, 6).contains(nodeNumber)) {
                        id = entity.getOwnerId();
                        kafkaInstanceIds = Arrays.asList(model.getInstanceId());
                    }


                }
            }
            flowInstanceDao.updateNodes(date, userId, Collections.singletonList(new FlowInstanceCurNode(instanceId, auditStatus, nodeId)));
            flowInstanceUserDao.updateById(instanceUser);
            flowInstanceLogDao.insert(instanceLog);
            TransactionUtils.commit(DataSourceEnum.MS, status);
        } catch (OwnerException oe) {
            TransactionUtils.rollback(DataSourceEnum.MS, status);
            throw oe;
        } catch (Exception ex) {
            TransactionUtils.rollback(DataSourceEnum.MS, status);
            throw new OwnerException(ErrorEnum.FAIL, ex.getMessage());
        }

        createAchievement(model.getPass(),entity,process,conName,userId);

        if (!FlowInstanceStatusEnum.isOngoing(auditStatus)) {
            if (!CollectionUtils.isEmpty(kafkaInstanceIds) && null != id) {
                kafkaQueueService.sendAudit(Constant.TOPIC_PUSH_AUDIT, kafkaInstanceIds, userId, Arrays.asList(id), null, null);
            } else {
                kafkaQueueService.sendAudit(Constant.TOPIC_PUSH_AUDIT, CollUtil.newArrayList(instanceId), userId);
            }
        }
        return true;
    }

    private void createAchievement(Boolean pass,PatentPlanEntity entity,Integer process,String conName,Integer userId)throws OwnerException{
        List<PatentFileModel> fileModelList = null;
        RsAchievementEntity achEntity = null;
        if (pass&&entity!=null&&process==5 && entity.getProjectId()>0){
            fileModelList = patentFileDao.getPatentByType(entity.getId(), 3);
            Integer companyId = customerDao.getCompanyId(entity.getCustomerId());
            if (CollectionUtils.isEmpty(fileModelList)){
                throw new OwnerException("请上传受理通知书！");
            }
            if (companyId!=null&&companyId!=0){
                achEntity = new RsAchievementEntity();
                achEntity.setAchievementName(conName);
                achEntity.setType(0);
                achEntity.setSource(0);
                achEntity.setConverType(0);
                achEntity.setFileCnt(fileModelList.size());
                achEntity.setProjectId(entity.getProjectId());
                achEntity.setYear(entity.getYear());
                achEntity.setCompanyId(companyId);
                achEntity.setLastUploadTime(fileModelList.get(fileModelList.size()-1)
                        .getLastUpdateTime());
                achEntity.create(-1,userId,new Date());
            }
        }
        if (achEntity!=null){
            TransactionStatus rsStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
            try {
                rsAchievementDao.insert(achEntity);
                List<RsAchievementFileEntity> fileEntities = new ArrayList<>();
                if (!CollectionUtils.isEmpty(fileModelList)){
                    Integer seq = 1;
                    for (PatentFileModel fileModel : fileModelList) {
                        RsAchievementFileEntity fileEntity = new RsAchievementFileEntity(achEntity.getCompanyId(),
                                achEntity.getId(),fileModel.getFilePath(),fileModel.getFileName(),null,1,seq,0);
                        seq++;
                        fileEntity.create(-1,userId,new Date());
                        fileEntities.add(fileEntity);
                    }
                    rsAchievementFileDao.addList(fileEntities);
                }

                TransactionUtils.commit(DataSourceEnum.RS,rsStatus);
            } catch (Exception ex) {
                TransactionUtils.rollback(DataSourceEnum.RS, rsStatus);
                throw new OwnerException(ErrorEnum.FAIL, ex.getMessage());
            }
        }
    }

    @Override
    public Boolean patentAudits(BatchAuditModel batchAudit, UserInfo userInfo, Integer id) throws OwnerException {
        Integer userId = userInfo.getId();
        List<FlowInstanceUser> instanceUsers = flowInstanceDao.getInstanceUsers(batchAudit.getInstanceIds(), null, 1);
        if (CollectionUtils.isEmpty(instanceUsers)) {
            return true;
        }
        String format = "{0}_{1}";
        Map<String, FlowInstanceCurNode> instanceNodeMap = new HashMap<>();
        List<FlowInstanceLog> flowInstanceLogs = new ArrayList<>();
        Boolean pass = batchAudit.getPass();
        Integer nodeStatus = pass ? FlowInstanceStatusEnum.DONE.getStatus() : FlowInstanceStatusEnum.REJECT.getStatus();
        String suggestion = batchAudit.getSuggestion();
        Date now = new Date();
        List<Integer> instanceIdList = new ArrayList<>();
        instanceUsers.forEach(item -> {
            Integer instanceId = item.getInstanceId();
            instanceIdList.add(instanceId);
            Integer nodeId = item.getNodeId();
            flowInstanceLogs.add(new FlowInstanceLog(instanceId, nodeStatus, suggestion, userId, now, nodeId, item.getLastSubmiter()));
            instanceNodeMap.put(MessageFormat.format(format, instanceId, nodeId), new FlowInstanceCurNode(instanceId, nodeStatus, nodeId));
        });
        if (pass) {
            // 查询是否存在会签实例且其他审核人员还未通过的实例id
            List<FlowInstanceUser> unAuditInstances = flowInstanceUserDao.getUnAuditInstanceIds(instanceIdList, userId);
            if (!CollectionUtils.isEmpty(unAuditInstances)) {
                unAuditInstances.forEach(item -> {
                    instanceNodeMap.get(MessageFormat.format(format, item.getInstanceId(), item.getNodeId())).setNodeStatus(FlowInstanceStatusEnum.ONGOING.getStatus());
                });
            }
        }
        List<PatentPlanEntity> planList = patentPlanDao.getByInstanceId(instanceIdList);
        List<PatentPlanProcess> insertProcesses = new ArrayList<>();
        List<Integer> updatePlanList = new ArrayList<>();
        planList.forEach(item -> {
            PatentPlanProcess planProcess = new PatentPlanProcess(item.getId(), userInfo.getId(), now, 2, null);
            insertProcesses.add(planProcess);
            updatePlanList.add(item.getId());
        });
        List<FlowInstanceCurNode> curNodes = new ArrayList<>(instanceNodeMap.values());
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            patentPlanProcessDao.insertBatch(insertProcesses);
            patentPlanDao.updateProcessBatch(updatePlanList, now, userId);
            flowInstanceUserDao.updateStatus(instanceUsers.stream().map(FlowInstanceUser::getId).collect(Collectors.toList()), batchAudit.getPass(), now, userId);
            flowInstanceLogDao.addBatch(flowInstanceLogs);
            flowInstanceDao.updateNodes(now, userId, curNodes);
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException(ErrorEnum.FAIL, e.getMessage());
        }
        List<Integer> instanceIds = curNodes.stream().filter(a -> !FlowInstanceStatusEnum.isOngoing(a.getNodeStatus())).map(a -> a.getInstanceId()).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(instanceIds)) {
            kafkaQueueService.sendAudit(Constant.TOPIC_PUSH_AUDIT, instanceIds,userId, Arrays.asList(id), null, null);
        }
        return true;
    }

    @Override
    public Boolean projectAudits(BatchAuditModel batchAudit, UserInfo userInfo) throws OwnerException {
        Integer userId = userInfo.getId();
        Date now = new Date();
        List<FlowInstanceUser> instanceUsers = null;
        if (!CollectionUtils.isEmpty(batchAudit.getDocFileIds())) {
            instanceUsers = flowInstanceDocFileDao.getInstanceUsers(batchAudit.getDocFileIds(), userId, batchAudit.getProjectId());
        } else if (!CollectionUtils.isEmpty(batchAudit.getInstanceIds())) {
            instanceUsers = flowInstanceDao.getInstanceUsers(batchAudit.getInstanceIds(), userId, null);
        } else if (!CollectionUtils.isEmpty(batchAudit.getProjectIds())) {
            if (batchAudit.getModuleId() != null && batchAudit.getModuleId() == 4) {
                instanceUsers = flowInstanceReportDao.getProjectInstanceUsers(batchAudit.getProjectIds(), userId, batchAudit.getModuleId());
            } else {
                // 查新报告
                instanceUsers = flowInstanceDocFileDao.getReportInstanceUsers(batchAudit.getProjectIds(), userId, 8);

            }
        } else if (!CollectionUtils.isEmpty(batchAudit.getPatentIds())) {
            instanceUsers = flowInstancePatentDao.getPatentInstanceUsers(batchAudit.getPatentIds(), userId);
        } else if (!CollectionUtils.isEmpty(batchAudit.getProposalIds())) {
            instanceUsers = flowInstanceProposalDao.getProposalInstanceUsers(batchAudit.getProposalIds(), userId);
        } else if (!CollectionUtils.isEmpty(batchAudit.getAchievementIds())) {
            instanceUsers = flowInstanceAchievementDao.getAchievementInstanceUsers(batchAudit.getAchievementIds(), userId);
        }

        if (CollectionUtils.isEmpty(instanceUsers)) {
            throw new OwnerException("审核失败，无审核权限！");
        }
        Map<String, FlowInstanceCurNode> instanceNodeMap = new HashMap<>();
        List<FlowInstanceLog> flowInstanceLogs = new ArrayList<>();
        Boolean pass = batchAudit.getPass();
        Integer nodeStatus;
        if (batchAudit.getStatus() != null) {
            nodeStatus = batchAudit.getStatus();
        } else {
            nodeStatus = pass ? 1 : 2;
        }
        String suggestion = batchAudit.getSuggestion();

        List<Integer> allInstanceIds = new ArrayList<>();
        instanceUsers.forEach(item -> {
            Integer instanceId = item.getInstanceId();
            allInstanceIds.add(instanceId);
            Integer nodeId = item.getNodeId();
            flowInstanceLogs.add(new FlowInstanceLog(instanceId, nodeStatus, suggestion, userId, now, nodeId, item.getLastSubmiter()));
            instanceNodeMap.put(instanceId + "_" + nodeId, new FlowInstanceCurNode(instanceId, nodeStatus, nodeId));
        });
        if (pass) {
            List<FlowInstanceUser> unAuditInstances = flowInstanceUserDao.getUnAuditInstanceIds(allInstanceIds, userId);
            if (!CollectionUtils.isEmpty(unAuditInstances)) {
                unAuditInstances.forEach(item -> {
                    instanceNodeMap.get(item.getInstanceId() + "_" + item.getNodeId()).setNodeStatus(0);
                });
            }
        }
        List<FlowInstanceCurNode> curNodes = new ArrayList<>(instanceNodeMap.values());
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            flowInstanceUserDao.updateStatus(instanceUsers.stream().map(FlowInstanceUser::getId).collect(Collectors.toList()), batchAudit.getPass(), now, userId);
            flowInstanceLogDao.addBatch(flowInstanceLogs);
            flowInstanceDao.updateNodes(now, userId, curNodes);
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException(ErrorEnum.FAIL, e.getMessage());
        }
        List<Integer> instanceIds = curNodes.stream().filter(a -> !FlowInstanceStatusEnum.isOngoing(a.getNodeStatus())).map(a -> a.getInstanceId()).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(instanceIds)) {
            kafkaQueueService.sendAudit(Constant.TOPIC_PUSH_AUDIT, instanceIds,userId);
        }
        return true;
    }

    @Override
    public Boolean checkAudits(CheckAuditModel model, UserInfo info) throws OwnerException {
        Integer userId = info.getId();
        Date now = new Date();
        // 查新报告
        List<FlowInstanceUser> instanceUsers = flowInstanceDocFileDao.getReportInstanceUsers(model.getProjectIds(), userId, 8);
        if (CollectionUtils.isEmpty(instanceUsers)) {
            throw new OwnerException("审核失败，无审核权限！");
        }
        List<QualityScore> scoreList = new ArrayList<>();
        List<QualityScoreLog> scoreLogs = new ArrayList<>();
        if (model.getPass()){
            BigDecimal weight = qualityTypeDao.getWeightByType(AuditModeEnum.RD_NOVELTY_SEARCH.getModuleId()).getWeight();
            //项目进度页打分
            for (Integer projectId : model.getProjectIds()) {
                QualityScore qualityScore = null;
                QualityScoreLog scoreLog = null;
                QualityScoreModel pasModel = model.getModel();
                if (null != pasModel) {
                    pasModel.setRsProjectId(projectId);
                    pasModel.setType(AuditModeEnum.RD_NOVELTY_SEARCH.getModuleId());
                    pasModel.setPassed(model.getPass());
                        if (null == pasModel.getEngineerId()) {
                            throw new OwnerException("工程师信息不能为空！");
                        }
                        Integer type = pasModel.getType();
                        Map<Integer, Integer> scoreMap = pasModel.getScores();
                        if (null != scoreMap && !CollectionUtils.isEmpty(scoreMap.values())) {
                            List<Integer> scores = new ArrayList<>(scoreMap.values());
                            BigDecimal sum = new BigDecimal(scores.stream().reduce(Integer::sum).orElse(0));
                            if (pasModel.getTotalScore().compareTo(sum) != 0) {
                                throw new OwnerException("总分计算有误，请重新计分");
                            }
                            ScoreElementEnum.ScoreCheck(scores, type);
                        }
                        qualityScore = new QualityScore();
                        BeanUtils.copyProperties(pasModel, qualityScore);
                        qualityScore.setWeight(weight);
                        qualityScore.setTotalWeight(qualityScore.getWeight());
                        qualityScore.setFinal(model.getPass());
                        scoreLog = new QualityScoreLog();
                        BeanUtils.copyProperties(pasModel, scoreLog);
                        scoreLog.setPassed(model.getPass());
                        scoreLog.setScores(null == scoreMap ? null : JsonUtils.objectToJson(scoreMap));
                        qualityScore.setYear(rsProjectDao.getBeginYear(projectId));
                        List<QualityScoreModel> models = qualityScoreLogDao.getScoreList(pasModel, null);
                        BigDecimal totalScore = BigDecimal.ZERO;
                        qualityScore.setRsProjectId(projectId);
                        qualityScore.setScoreCount(1);
                        qualityScore.setPassRate(BigDecimal.valueOf(1));
                        scoreLog.setScoreCount(1);

                        if (!CollectionUtils.isEmpty(models)) {

                            for (QualityScoreModel scoreModel : models) {
                                totalScore = totalScore.add(scoreModel.getTotalScore());
                            }
                            qualityScore.setAvgScore(totalScore.add(pasModel.getTotalScore())
                                    .divide(BigDecimal.valueOf(1), 2, BigDecimal.ROUND_HALF_UP));
                            scoreLog.setAvgScore(qualityScore.getAvgScore());
                            qualityScore.setId(models.get(0).getId());
                        } else {
                            qualityScore.create(userId, now);
                            qualityScore.setAvgScore(pasModel.getTotalScore());
                            scoreLog.setAvgScore(pasModel.getTotalScore());
                        }
                        scoreLog.create(userId, now);

                        //提交时间和审核时间属性填充
                        setTime(pasModel, qualityScore);
                        scoreLog.setSubmitTime(qualityScore.getSubmitTime());
                        scoreLog.setAuditTime(qualityScore.getAuditTime());
                }

                scoreList.add(qualityScore);
                scoreLogs.add(scoreLog);
            }
        }

        Map<String, FlowInstanceCurNode> instanceNodeMap = new HashMap<>();
        List<FlowInstanceLog> flowInstanceLogs = new ArrayList<>();
        Boolean pass = model.getPass();
        Integer nodeStatus;
        nodeStatus = pass ? 1 : 2;
        String suggestion = model.getSuggestion();

        List<Integer> allInstanceIds = new ArrayList<>();
        instanceUsers.forEach(item -> {
            Integer instanceId = item.getInstanceId();
            allInstanceIds.add(instanceId);
            Integer nodeId = item.getNodeId();
            flowInstanceLogs.add(new FlowInstanceLog(instanceId, nodeStatus, suggestion, userId, now, nodeId, item.getLastSubmiter()));
            instanceNodeMap.put(instanceId + "_" + nodeId, new FlowInstanceCurNode(instanceId, nodeStatus, nodeId));
        });
        if (pass) {
            List<FlowInstanceUser> unAuditInstances = flowInstanceUserDao.getUnAuditInstanceIds(allInstanceIds, userId);
            if (!CollectionUtils.isEmpty(unAuditInstances)) {
                unAuditInstances.forEach(item -> {
                    instanceNodeMap.get(item.getInstanceId() + "_" + item.getNodeId()).setNodeStatus(0);
                });
            }
        }
        List<FlowInstanceCurNode> curNodes = new ArrayList<>(instanceNodeMap.values());

        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            if (!CollectionUtils.isEmpty(scoreList)){
                qualityScoreDao.addScoreList(scoreList);
                List<Integer> ids = scoreList.stream().map(QualityScore::getId).collect(Collectors.toList());
                for (int i = 0; i < ids.size(); i++) {
                    scoreLogs.get(i).setQualityId(ids.get(i));
                }
                qualityScoreLogDao.addList(scoreLogs);
            }

            flowInstanceUserDao.updateStatus(instanceUsers.stream().map(FlowInstanceUser::getId).collect(Collectors.toList()), model.getPass(), now, userId);
            flowInstanceLogDao.addBatch(flowInstanceLogs);
            flowInstanceDao.updateNodes(now, userId, curNodes);
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException(ErrorEnum.FAIL, e.getMessage());
        }
        List<Integer> instanceIds = curNodes.stream().filter(a -> !FlowInstanceStatusEnum.isOngoing(a.getNodeStatus())).map(a -> a.getInstanceId()).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(instanceIds)) {
            kafkaQueueService.sendAudit(Constant.TOPIC_PUSH_AUDIT, instanceIds,userId);
        }
        return true;
    }


    @Override
    public Boolean cancelPatent(BatchAuditModel batchAudit, UserInfo userInfo) throws OwnerException {
        Integer userId = userInfo.getId();
        List<FlowInstanceUser> instanceUsers = flowInstancePatentDao.getCancelPatentInstanceUsers(batchAudit.getPatentIds());
        if (CollectionUtils.isEmpty(instanceUsers)) {
            throw new OwnerException("取消失败，所选专利申请已结束或已取消！");
        }
        List<FlowInstanceLog> flowInstanceLogs = new ArrayList<>();
        batchAudit.setPass(false);
        Integer nodeStatus = FlowInstanceStatusEnum.CANCEL.getStatus();
        batchAudit.setStatus(nodeStatus);
        String suggestion = batchAudit.getSuggestion();
        Date now = new Date();
        String format = "{0}_{1}";
        List<Integer> instanceIds = new ArrayList<>();
        List<FlowInstanceCurNode> curNodes = new ArrayList<>();
        instanceUsers.forEach(item -> {
            Integer instanceId = item.getInstanceId();
            instanceIds.add(instanceId);
            Integer nodeId = item.getNodeId();
            flowInstanceLogs.add(new FlowInstanceLog(instanceId, nodeStatus, suggestion, userId, now, nodeId, item.getLastSubmiter()));
            curNodes.add(new FlowInstanceCurNode(instanceId, nodeStatus, nodeId));
        });
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            flowInstanceUserDao.updateStatus(instanceUsers.stream().map(FlowInstanceUser::getId).collect(Collectors.toList()), batchAudit.getPass(), now, userId);
            flowInstanceLogDao.addBatch(flowInstanceLogs);
            flowInstanceDao.updateNodes(now, userId, curNodes);
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException(ErrorEnum.FAIL, e.getMessage());
        }
        kafkaQueueService.sendAudit(Constant.TOPIC_PUSH_AUDIT, instanceIds,userId);
        return true;
    }

    @Override
    public Map<Integer, Boolean> getProjectAudit(Integer companyId, Integer year, UserInfo userInfo) {
        Map<Integer, Boolean> resultMap = new HashMap<>();
        List<FlowInstanceProject> instanceProjects = flowInstanceProjectDao.getProjectAudit(companyId, year, userInfo.getId());
        if (!CollectionUtils.isEmpty(instanceProjects)) {
            instanceProjects.forEach(item -> {
                resultMap.put(item.getModuleId(), true);
            });
        }
        for (int i = 1; i < 10; i++) {
            if (i >= 5 && i <= 8) {
                continue;
            }
            if (resultMap.containsKey(i)) {
                resultMap.put(i, true);
            } else {
                resultMap.put(i, false);
            }
        }
        return resultMap;
    }

    @Override
    public Map<Integer, Integer> getDocAudit(Integer companyId, Integer year, Integer userId) {
        List<DocAuditModel> docAudits = projectDocFileDao.countAuditInfo(year, companyId);
        //立项报告
        Integer reportNum = 0;
        //过程文件数
        Integer docNum = 0;
        //查新报告数
        Integer newReportNum = 0;
        //备查资料
        Integer backupDataNum = 0;
        if (!CollectionUtils.isEmpty(docAudits)) {
            Set<Integer> projectIdSet = new HashSet<>();
//            List<Integer> pDocFileIds = new ArrayList<>();
            Map<Integer, Integer> backupDataMap = new HashMap<>();
            for (DocAuditModel model : docAudits) {
                projectIdSet.add(model.getProjectId());
//                pDocFileIds.add(model.getpDocFileId());
                if (model.getDocFileId() == 3 || model.getDocFileId() == 27 || model.getDocFileId() == 22 || model.getDocFileId() == 38) {
                    backupDataMap.put(model.getpDocFileId(), model.getDocFileId());
                }
                if (model.getBeginYear() < model.getEndYear() && year < model.getEndYear()) {
                    if (model.getDocFileId() == 24) {
                        continue;
                    }
                } else {
                    if (model.getDocFileId() == 33) {
                        backupDataMap.put(model.getpDocFileId(), model.getDocFileId());
                    }
                }
            }
            List<ProjectDocFileEntity> docs = projectDocFileDao.getDocFiles(projectIdSet, year);
            Map<Integer, ProjectDocFileEntity> existDocMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(docs)) {
                for (ProjectDocFileEntity doc : docs) {
                    if (!existDocMap.containsKey(doc.getProjectId())) {
                        existDocMap.put(doc.getProjectId(), doc);
                        backupDataMap.put(doc.getId(), doc.getDocFileId());
                    }
                }
            }
            List<FlowInstanceDocFile> docFiles = flowInstanceDocFileDao.getDocAudit(new ArrayList<>(projectIdSet), null, userId);
            if (!CollectionUtils.isEmpty(docFiles)) {
                for (FlowInstanceDocFile file : docFiles) {
                    if (file.getModuleId() == 5) {
                        reportNum += 1;
                    }
                    if (file.getModuleId() == 5 || file.getModuleId() == 6 || file.getModuleId() == 8) {
                        docNum += 1;
                    }
                    if (file.getModuleId() == 8) {
                        newReportNum += 1;
                    }
                    if (backupDataMap.containsKey(file.getDocFileId())) {
                        backupDataNum += 1;
                    }
                }
            }
        }
        Map<Integer, Integer> dataMap = new HashMap<>();
        dataMap.put(5, reportNum);
        dataMap.put(6, docNum);
        dataMap.put(8, newReportNum);
        dataMap.put(10, backupDataNum);
        return dataMap;
    }

    @Override
    public Integer getReportAudit(Integer companyId, Integer year, Integer userId) {
        List<ProjectListModel> projects = rsProjectDao.getProjectsByYear(year, companyId);
        Integer auditReportNum = 0;
        if (!CollectionUtils.isEmpty(projects)) {
            List<Integer> projectIds = projects.stream().map(e -> e.getId()).distinct().collect(Collectors.toList());
            auditReportNum = flowInstanceReportDao.getAuditInfo(projectIds, userId, companyId);
        }
        return auditReportNum;
    }

    @Override
    public List<Object> getOrgPath(Integer companyId, Integer year) throws OwnerException {
        String fileName = MessageFormat.format("org_{0,number,#}_{1,number,#}.png", companyId, year);
        String tempPath = MessageFormat.format("{0}/static/images/{1,number,#}/org", msConfig.getUploadConfig().getResourcePath(), companyId);
        String orgUrl = null;
        File file = new File(tempPath + "/" + fileName);
        if (file.exists()) {
            orgUrl = msConfig.getExpertConfig().getDomainName() + MessageFormat.format("/static/images/{0,number,#}/org/{1}", companyId, fileName);
        }
        String techIntro = rsReportDao.getTechIntro(companyId, year);
        List<Object> list = new ArrayList<>();
        list.add(orgUrl);
        list.add(techIntro);
        return list;
    }

    @Override
    public Map<Integer, Integer> getPatentAudit(Integer companyId, Integer year, Integer userId) {
        List<Integer> patentIds = patentPlanDao.getPatentPlanIds(new QueryAuditDataModel(companyId, year));
        Integer patentNum = 0;
        Map<Integer, Integer> patentMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(patentIds)) {
            List<FlowInstancePatent> patentInstances = flowInstancePatentDao.getPatents(patentIds, userId);
            patentNum = CollectionUtils.isEmpty(patentInstances) ? 0 : patentInstances.size();
        }
        patentMap.put(7, patentNum);
        return patentMap;
    }

    @Override
    public Integer getProjectAuditInfo(Integer year, Integer companyId, Integer userId) {
        List<AuditReportEntity> projectAudits = auditReportDao.getAuditInfo(year, companyId);
        Integer projectAuditNum = 0;
        if (!CollectionUtils.isEmpty(projectAudits)) {
            List<Integer> projectIds = projectAudits.stream().map(e -> e.getProjectId()).collect(Collectors.toList());
            List<FlowInstanceReport> reports = flowInstanceReportDao.getProjectAudits(projectIds, userId);
            projectAuditNum = CollectionUtils.isEmpty(reports) ? 0 : reports.size();
        }
        return projectAuditNum;
    }

    @Override
    public Map<Integer, Integer> getProposalAudit(Integer companyId, Integer year, Integer userId) {
        Map<Integer, Boolean> resultMap = new HashMap<>();

        return null;
    }

    @Override
    public Map<Integer, Integer> getResultAudit(Integer companyId, Integer year, Integer userId) {
        return null;
    }

    @Override
    public void exportEmployeeBindInfo(OutputStream out, QueryAuditDataModel query) {
        List<RdEmployeeListModel> list = rdEmployeeDao.getList(query);
        Map<String, Object> map = new HashMap<>();
        map.put("companyName", query.getCompanyName());
        map.put("year", query.getYear());
        map.put("list", list);
        String path = msConfig.getUploadConfig().getResourcePath() + Constant.TEMPLATE_DIR + "研发考勤绑定情况模板.xlsx";
        YsExcelUtils.generalReport(map, path, workbook -> {
            try {
                workbook.write(out);
                workbook.close();
                out.flush();
                out.close();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        });
    }


    @Override
    public List<AuditStatusModel> getAuditInfo(Integer projectId, Integer userId) {
        List<AuditStatusModel> auditList = flowModuleDao.getByProject(projectId);
        loadAuditor(auditList);
        return auditList;
    }

    public void loadAuditor(List<? extends AuditStatusModel> auditList) {
        List<AuditStatusModel> curNodeUser = auditList.stream().filter(e -> e.getCurNodeId() != null).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(curNodeUser)) {
            List<FlowInstanceUserModel> users = flowInstanceUserDao.getByNode(curNodeUser);
            Map<String, List<String>> userMap = new HashMap<>();
            String format = "{0}-{1}";
            for (FlowInstanceUserModel model : users) {
                String key = MessageFormat.format(format, model.getNodeId(), model.getInstanceId());
                if (!userMap.containsKey(key)) {
                    List<String> userNames = new ArrayList<>();
                    userMap.put(key, userNames);
                }
                userMap.get(key).add(model.getUserName());
            }
            for (AuditStatusModel audit : auditList) {
                if (null != audit.getInstanceId() && audit.getStatus().equals(0)) {
                    String sign = MessageFormat.format(format, audit.getCurNodeId(), audit.getInstanceId());
                    if (userMap.containsKey(sign)) {
                        String auditor = StringUtils.join(userMap.get(sign), ",");
                        audit.setAuditor(auditor);
                    }
                }

            }
        }
    }

    @Override
    public Map<String, Object> getAuditLog(Integer companyId, Integer year, Integer moduleId, Integer docFileId,
                                           Integer userId, Integer rsProjectId, Integer patentPlanId,
                                           Integer proposalId, Integer achievementId, Integer rdFeeId) {
        Map<String, Object> dataMap = new HashMap<>();
        AuditStatusModel auditStatus = null;
        if (null != companyId && null != moduleId) {
            auditStatus = flowInstanceProjectDao.getAuditStatus(companyId, year, moduleId);
        } else if (null != docFileId) {
            auditStatus = flowInstanceDocFileDao.getAuditStatus(docFileId);
        } else if (null != rsProjectId) {
            auditStatus = flowInstanceReportDao.getAuditStatus(rsProjectId);
        } else if (null != patentPlanId) {
            auditStatus = flowInstancePatentDao.getAuditStatus(patentPlanId);
        } else if (null != proposalId) {
            auditStatus = flowInstanceProposalDao.getAuditStatus(proposalId);
        } else if (null != achievementId) {
            auditStatus = flowInstanceAchievementDao.getAuditStatus(achievementId);
        } else if (null != rdFeeId) {
            auditStatus = flowInstanceRdFeeDao.getAuditStatus(rdFeeId);
        }
        if (auditStatus == null) {
            dataMap.put("logs", new ArrayList<>());
            dataMap.put("nodePerms", false);
            dataMap.put("auditStatus", null);
            return dataMap;
        }
        dataMap.put("nodePerms", null != flowInstanceDao.getUserPerms(auditStatus.getInstanceId(), auditStatus.getCurNodeId(), userId));
        dataMap.put("logs", flowInstanceLogDao.getInstanceLog(CollUtil.newArrayList(auditStatus.getInstanceId())));
        loadAuditor(CollUtil.newArrayList(auditStatus));
        dataMap.put("auditStatus", auditStatus);
        return dataMap;
    }

    @Override
    public Boolean activateFlow(ProjectAuditModel model, UserInfo userInfo) throws OwnerException {
        Date date = new Date();
        Integer instanceId = model.getInstanceId();
        FlowInstanceCurNode flowInstanceCurNode = flowInstanceDao.getLastNode(instanceId);
        if (!FlowInstanceStatusEnum.isDone(flowInstanceCurNode.getNodeStatus())) {
            throw new OwnerException("未通过最终审核，不能进行激活操作");
        }
        Integer status = FlowInstanceStatusEnum.ACTIVATE.getStatus(),userId= userInfo.getId();
        FlowInstanceLog instanceLog = new FlowInstanceLog();
        instanceLog.setInstanceId(instanceId);
        instanceLog.setSuggestion(model.getSuggestion());
        instanceLog.setCreateTime(date);
        instanceLog.setMsCreatorId(userId);
        instanceLog.setNodeId(flowInstanceCurNode.getCurNodeId());
        instanceLog.setStatus(status);
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
        try {
            flowInstanceDao.updateAllNodes(status, Collections.singletonList(instanceId), userId, date);
            flowInstanceLogDao.insert(instanceLog);
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
        } catch (Exception ex) {
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException(ErrorEnum.FAIL, ex.getMessage());
        }
        kafkaQueueService.sendAudit(Constant.TOPIC_ACTIVATE_AUDIT, CollUtil.newArrayList(instanceId),userId);
        return true;
    }

    @Override
    public Boolean activateFlows(BatchAuditModel batchAudit, UserInfo userInfo) throws OwnerException {
        Date date = new Date();
        List<FlowInstanceCurNode> flowInstanceCurNodes = null;
        List<Integer> ids = new ArrayList<>();
        if (!CollectionUtils.isEmpty(batchAudit.getDocFileIds())) {
            flowInstanceCurNodes = flowInstanceDocFileDao.getInstances(batchAudit.getDocFileIds(), batchAudit.getProjectId());
            // // TODO: 22/08/13 立项报告获取实例问题，应和过程文档一致
        } else if (!CollectionUtils.isEmpty(batchAudit.getProjectDocFiles())) {
            flowInstanceCurNodes = flowInstanceDocFileDao.getDocFileInstance(batchAudit.getProjectDocFiles());
        } else if (!CollectionUtils.isEmpty(batchAudit.getProjectIds())) {
            if (batchAudit.getModuleId() != null && batchAudit.getModuleId() == 4) {
                flowInstanceCurNodes = flowInstanceReportDao.getProjectInstances(batchAudit.getProjectIds(), batchAudit.getModuleId());
            } else {
                // 查新报告
                flowInstanceCurNodes = flowInstanceDocFileDao.getReportInstances(batchAudit.getProjectIds(), 8);
            }
        } else if (!CollectionUtils.isEmpty(batchAudit.getPatentIds())) {
            flowInstanceCurNodes = flowInstancePatentDao.getPatentInstances(batchAudit.getPatentIds());
        } else if (!CollectionUtils.isEmpty(batchAudit.getProposalIds())) {
            flowInstanceCurNodes = flowInstanceProposalDao.getProposalInstances(batchAudit.getProposalIds());
        } else if (!CollectionUtils.isEmpty(batchAudit.getAchievementIds())) {
            flowInstanceCurNodes = flowInstanceAchievementDao.getAchievementInstances(batchAudit.getAchievementIds());
        }
        if (CollectionUtils.isEmpty(flowInstanceCurNodes)) {
            throw new OwnerException("激活失败，暂无可激活的数据！");
        }
        Integer status = FlowInstanceStatusEnum.ACTIVATE.getStatus();
        Map<Integer, FlowInstanceLog> logMap = new HashMap<>();
        Integer curInstanceId, userId = userInfo.getId();
        for (FlowInstanceCurNode curNode : flowInstanceCurNodes) {
            curInstanceId = curNode.getInstanceId();
            ids.add(curInstanceId);
            FlowInstanceLog instanceLog = new FlowInstanceLog();
            instanceLog.setInstanceId(curInstanceId);
            instanceLog.setSuggestion(batchAudit.getSuggestion());
            instanceLog.setCreateTime(date);
            instanceLog.setMsCreatorId(userId);
            instanceLog.setNodeId(curNode.getCurNodeId());
            instanceLog.setStatus(status);
            logMap.put(curInstanceId, instanceLog);
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
        try {
            flowInstanceDao.updateAllNodes(status, ids, userId, date);
            flowInstanceLogDao.insertAll(new ArrayList<>(logMap.values()));
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
        } catch (Exception ex) {
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException(ErrorEnum.FAIL, ex.getMessage());
        }
        kafkaQueueService.sendAudit(Constant.TOPIC_ACTIVATE_AUDIT, ids,userId);
        return true;
    }


    @Override
    public List<RsStageModel> getStageAudit(Integer companyId, Integer projectId, Integer userId) {
        List<RsStageModel> stageModels = queryStage(companyId, projectId, userId);
        List<Integer> docFileIds = new ArrayList<>();
        stageModels.forEach(item -> {
            if (!CollectionUtils.isEmpty(item.getProjectDocList())) {
                item.getProjectDocList().forEach(doc -> docFileIds.add(doc.getId()));
            }
        });
        if (CollectionUtils.isEmpty(docFileIds)) {
            return stageModels;
        }
        List<DocFileAuditModel> docFileAudits = flowInstanceDocFileDao.getDocFileAudits(docFileIds);
        if (!CollectionUtils.isEmpty(docFileAudits)) {
            Map<Integer, Boolean> docDoneMap = docFileAudits.stream().collect(Collectors.toMap(
                    DocFileAuditModel::getDocFileId, b -> FlowInstanceStatusEnum.isDone(b.getStatus()), (o, n) -> n));
            stageModels.forEach(item -> {
                if (!CollectionUtils.isEmpty(item.getProjectDocList())) {
                    item.getProjectDocList().forEach(doc -> doc.setDone(docDoneMap.getOrDefault(doc.getId(), false)));
                }
            });
        }
        return stageModels;
    }

    @Override
    public PageResult getEmployeeList(QueryAuditDataModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> ascs = new ArrayList<>();
            ascs.add("re.enumber");
            page.setAscs(ascs);
        }
        Map<String, Object> dataMap = rdEmployeeDao.getTotal(query);
        ProjectProgressModel model = projectProgressDetailDao.countRdInfo(query.getCompanyId(), query.getYear());
        if (model != null && model.getEmployeeCount() != 0) {
            dataMap.put("employeeCount", model.getEmployeeCount());
            dataMap.put("rdEmployeeCount", model.getRdEmployeeCount());
            dataMap.put("proportion", new BigDecimal(model.getRdEmployeeCount() * 100).divide(new BigDecimal(model.getEmployeeCount()), 2, BigDecimal.ROUND_HALF_UP));
        }
        return PageUtils.buildPageResult(page, rdEmployeeDao.getList(page, query), null, dataMap);
    }

    @Override
    public PageResult getEquipmentList(QueryAuditDataModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> asc = new ArrayList<>();
            asc.add("re.ecode");
            page.setAscs(asc);
        }
        List<RdEquipmentListModel> listModels = rdEquipmentDao.getList(page, query);
        Map<String, Object> dataMap = rdEquipmentDao.getTotal(query);
        ProjectProgressModel model = projectProgressDetailDao.countRdInfo(query.getCompanyId(), query.getYear());
        if (model != null && model.getEquipmentCount() != 0) {
            dataMap.put("equipmentCount", model.getEquipmentCount());
            dataMap.put("rdEquipmentCount", model.getRdEquipmentCount());
            BigDecimal proportion = BigDecimal.ZERO;
            if (model.getRdEquipmentCount() != null && model.getEquipmentCount() != null) {
                proportion = new BigDecimal(model.getRdEquipmentCount() * 100).divide(new BigDecimal(model.getEquipmentCount()), 2, BigDecimal.ROUND_HALF_UP);
            }
            dataMap.put("proportion", proportion);
        }
        return PageUtils.buildPageResult(page, listModels, null, dataMap);
    }

    @Override
    public PageModel<List<PatentAuditModel>> getPatentList(QueryAuditDataModel query, Integer userId, Integer type) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> asc = new ArrayList<>();
            if (type == 2) {
                asc.add("pap.status");
                asc.add("c.companyName");
            }
            asc.add("pp.rdTitle");
            page.setAscs(asc);
        }
        List<PatentAuditModel> list = patentDao.getPatentList(page, query, type);
        if (!CollectionUtils.isEmpty(list)) {
            List<Integer> ids = list.stream().map(e -> e.getId()).collect(Collectors.toList());
            List<PatentAuditModel> patents = flowInstancePatentDao.getPatentAudit(ids);
            Map<Integer, PatentAuditModel> auditMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(patents)) {
                auditMap = patents.stream().collect(Collectors.toMap(e -> e.getPatentPlanId(), e -> e));
            }
            List<FlowInstancePatent> patentInstances = flowInstancePatentDao.getPatents(ids, userId);
            Map<Integer, FlowInstancePatent> userPermissionMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(patentInstances)) {
                userPermissionMap = patentInstances.stream().collect(Collectors.toMap(e -> e.getPatentPlanId(), e -> e));
            }
            for (PatentAuditModel model : list) {
                Boolean hasPermission = false;
                if (auditMap.containsKey(model.getId())) {
                    PatentAuditModel auditModel = auditMap.get(model.getId());
                    model.setAuditStatus(auditModel.getAuditStatus());
                    model.setInstanceId(auditModel.getInstanceId());
                    model.setCurNodeId(auditModel.getCurNodeId());
                    if (userPermissionMap.containsKey(model.getId())) {
                        hasPermission = true;
                    }
                }
                model.setHasPermission(hasPermission);
            }
        }
        return PageUtils.build(page, list);
    }

    @Override
    public List<ReportListModel> getProjectReport(Integer year, Integer companyId, Integer userId, Integer
            docFileId) {
        List<ReportListModel> list = projectDocFileDataDao.getReportList(year, companyId, docFileId);
        if (CollectionUtils.isEmpty(list)) {
            return list;
        }
        List<Integer> docFileIds = list.stream().filter(a -> a.getDocFileId() != null).map(ReportListModel::getDocFileId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(docFileIds)) {
            List<Integer> projectIds = list.stream().map(e -> e.getProjectId()).distinct().collect(Collectors.toList());
            List<FlowInstanceDocFile> docFiles = flowInstanceDocFileDao.getDocAudit(projectIds, docFileIds, userId);
            Map<Integer, FlowInstanceDocFile> permissionMap = docFiles.stream().collect(Collectors.toMap(e -> e.getDocFileId(), e -> e));
            List<DocFileAuditModel> docFileAudits = flowInstanceDocFileDao.getDocFileAudits(docFileIds);
            if (!CollectionUtils.isEmpty(docFileAudits)) {
                Map<Integer, Boolean> docDoneMap = docFileAudits.stream().collect(Collectors.toMap(
                        DocFileAuditModel::getDocFileId, b -> FlowInstanceStatusEnum.isDone(b.getStatus()), (o, n) -> n));
                list.forEach(item -> {
                    item.setDone(docDoneMap.getOrDefault(item.getDocFileId(), false));
                    item.setHasPermission(permissionMap.containsKey(item.getDocFileId()));
                });
            }
        }
        return list;
    }

    @Override
    public ProjectSummaryDataModel getCountData(Integer year, Integer customerId, Integer companyId) {
        ProjectSummaryDataModel model = projectSummaryDataDao.getData(year, customerId);
        if (model != null) {
            List<Integer> patentPlanIds = patentPlanDao.getPatentPlanIds(new QueryAuditDataModel(companyId, year));
            List<ReportListModel> reports = projectDocFileDataDao.getReportList(year, companyId, 27);
            model.setReportNum(CollectionUtils.isEmpty(reports) ? 0 : (int) reports.stream().filter(a -> a.getDocFileId() != null).count());
            model.setPatentNum(CollectionUtils.isEmpty(patentPlanIds) ? 0 : patentPlanIds.size());
            Integer rdDept = rsDeptDao.getDeptLevel(year, companyId);
            model.setRdDeptLevel(rdDept);
        } else {
            model = new ProjectSummaryDataModel();
        }
        return model;
    }

    //获取评分对象（查新报告，立项报告）的提交，审核时间 赋值
    private void setTime(QualityScoreModel model, QualityScore qualityScore) throws OwnerException {
        Integer type = model.getType();
        Integer projectId = model.getRsProjectId();
        Date date = new Date();
        FlowInstanceLog log = new FlowInstanceLog();
        //查新报告，按项目取
        if (type == AuditModeEnum.RD_NOVELTY_SEARCH.getModuleId()) {
            log = flowInstanceLogDao.getSubmitTimeByProjectId(model);
            //立项报告  按docFileId取
        } else if (type == AuditModeEnum.RD_REPORT.getModuleId()) {
            Integer fileId = projectDocFileDao.getRdReportId(projectId);
            log = flowInstanceLogDao.getSubmitTimeByFileIds(Arrays.asList(fileId));
        }
        if (null != log && null != log.getId()) {
            List<Date> times = flowInstanceLogDao.getAuditTime(log);
            if (times.size() < 2) {
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                c.add(Calendar.DAY_OF_MONTH, -1);
                qualityScore.setAuditTime(c.getTime());
            } else {
                qualityScore.setAuditTime(times.get(1));
            }
            qualityScore.setSubmitTime(log.getCreateTime());
        } else {
            throw new OwnerException("当前评分数据不存在相关提交信息，无法质量评分");
        }
    }
}
