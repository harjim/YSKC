package com.yskc.ms.service.impl.ms;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fasterxml.jackson.core.type.TypeReference;
import com.yskc.common.enums.AuditModeEnum;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.PageResult;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.JsonUtils;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.config.Constant;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.dao.ms.*;
import com.yskc.ms.dao.rs.ProjectDocFileDao;
import com.yskc.ms.dao.rs.RsProjectDao;
import com.yskc.ms.entity.ms.FlowInstanceLog;
import com.yskc.ms.entity.ms.QualityScore;
import com.yskc.ms.entity.ms.QualityScoreLog;
import com.yskc.ms.entity.ms.QualityScoreMonth;
import com.yskc.ms.entity.rs.ProjectEntity;
import com.yskc.ms.enums.ScoreElementEnum;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.qualityscore.*;
import com.yskc.ms.models.rs.RsProjectBaseModel;
import com.yskc.ms.service.ms.QualityScoreService;
import com.yskc.ms.utils.ToolUtil;
import com.yskc.ms.utils.TransactionUtils;
import com.yskc.ms.utils.YsExcelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @DateTime: 2022/5/13 15:48
 * @Description:
 * @author: hsx
 */
@Service
public class QualityScoreServiceImpl implements QualityScoreService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QualityScoreDao qualityScoreDao;
    @Autowired
    private QualityTypeDao qualityTypeDao;
    @Autowired
    private QualityScoreMonthDao qualityScoreMonthDao;
    @Autowired
    private QualityScoreLogDao qualityScoreLogDao;
    @Autowired
    private RsProjectDao rsProjectDao;
    @Autowired
    private FlowInstanceLogDao flowInstanceLogDao;
    @Autowired
    private ProjectDocFileDao projectDocFileDao;
    @Autowired
    private MsConfig msConfig;

    @Override
    public Boolean saveScore(QualityScoreModel model, UserInfo info) throws OwnerException {
        //超过两次评分之后 只传isFinal的值
        Integer year = model.getYear();
        Date now = new Date();
        if (model.getFinal()) {
            if (null == model.getQualityId()) {
                return false;
            }
            QualityScore qualityScore = new QualityScore();
            qualityScore.setFinal(model.getFinal());
            qualityScore.setId(model.getQualityId());
            qualityScore.update(info.getId(), now);
            return qualityScoreDao.updateById(qualityScore) > 0;
        }

        Integer scoreCount = model.getScoreCount();
        if (scoreCount < 3) {
            if (null == model.getEngineerId()) {
                throw new OwnerException("工程师信息不能为空！");
            }
            Integer type = model.getType();
            if (type == AuditModeEnum.RD_DOC.getModuleId() && StringUtils.isEmpty(model.getMonths())) {
                throw new OwnerException("过程文件月份时段不能为空！");
            }
            Map<Integer, Integer> scoreMap = model.getScores();
            if (null != scoreMap && !CollectionUtils.isEmpty(scoreMap.values())) {
                List<Integer> scores = new ArrayList<>(scoreMap.values());
                BigDecimal sum = new BigDecimal(scores.stream().reduce(Integer::sum).orElse(0));
                if (model.getTotalScore().compareTo(sum) != 0) {
                    throw new OwnerException("总分计算有误，请重新计分");
                }
                ScoreElementEnum.ScoreCheck(scores, type);
            }
            QualityScore qualityScore = new QualityScore();
            BeanUtils.copyProperties(model, qualityScore);
            qualityScore.setTotalWeight(qualityScore.getWeight());
            QualityScoreLog scoreLog = new QualityScoreLog();
            BeanUtils.copyProperties(model, scoreLog);
            scoreLog.setScores(null == scoreMap ? null : JsonUtils.objectToJson(scoreMap));
            if (type == AuditModeEnum.RD_INNOVATION_SYS.getModuleId()) {
                if (null == model.getYear()) {
                    throw new OwnerException("创新体系年份不能为空");
                }
            } else {
                if (type == AuditModeEnum.RD_DOC.getModuleId()) {
                    List<String> months = JsonUtils.jsonToPojo(model.getMonths(), List.class);
                    if (!CollectionUtils.isEmpty(months)) {
                        qualityScore.setTotalWeight(model.getWeight().multiply(new BigDecimal(months.size())));
                    }
                } else {
                    qualityScore.setYear(rsProjectDao.getBeginYear(model.getRsProjectId()));
                }
            }
            List<QualityScoreModel> models = new ArrayList<>();
            if (!(null == model.getMonthsId() && model.getType() == AuditModeEnum.RD_DOC.getModuleId())) {
                models = qualityScoreLogDao.getScoreList(model, year);
            }

            qualityScore.setFinal(model.getPassed());

            BigDecimal totalScore = BigDecimal.ZERO;

            if (!CollectionUtils.isEmpty(models)) {
                if (scoreCount == 2 && !model.getPassed()) {
                    qualityScore.setPassRate(totalScore);
                }
                for (QualityScoreModel scoreModel : models) {
                    totalScore = totalScore.add(scoreModel.getTotalScore());
                }
                qualityScore.setAvgScore(totalScore.add(model.getTotalScore())
                        .divide(BigDecimal.valueOf(scoreCount), 2, BigDecimal.ROUND_HALF_UP));
                scoreLog.setAvgScore(qualityScore.getAvgScore());
                qualityScore.setId(models.get(0).getId());
            } else {
                if (!model.getPassed()) {
                    qualityScore.setPassRate(new BigDecimal(0.8));
                }
                qualityScore.create(info.getId(), now);
                qualityScore.setAvgScore(model.getTotalScore());
                scoreLog.setAvgScore(model.getTotalScore());
            }
            scoreLog.create(info.getId(), now);

            //提交时间和审核时间属性填充
            setTime(model, qualityScore);
            scoreLog.setSubmitTime(qualityScore.getSubmitTime());
            scoreLog.setAuditTime(qualityScore.getAuditTime());

            TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            try {
                if (null == model.getMonthsId() && !StringUtils.isEmpty(model.getMonths()) && type == AuditModeEnum.RD_DOC.getModuleId()) {
                    QualityScoreMonth qualityScoreMonth = new QualityScoreMonth();
                    qualityScoreMonth.setRsProjectId(model.getRsProjectId());
                    qualityScoreMonth.setMonths(model.getMonths());
                    qualityScoreMonth.create(info.getId(), now);
                    qualityScoreMonthDao.insert(qualityScoreMonth);
                    qualityScore.setMonthsId(qualityScoreMonth.getId());
                }
                if (type == AuditModeEnum.RD_INNOVATION_SYS.getModuleId()) {
                    qualityScore.setRsProjectId(null);
                }
                if (null == qualityScore.getId()) {
                    qualityScoreDao.insert(qualityScore);
                } else {
                    qualityScoreDao.updateById(qualityScore);
                }
                scoreLog.setQualityId(qualityScore.getId());
                qualityScoreLogDao.insert(scoreLog);
                TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
                return true;
            } catch (Exception e) {
                TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
                logger.error(e.getMessage(), e);
                throw new OwnerException("评分保存失败，请联系管理员");
            }
        }
        return true;
    }

    @Override
    public Map<String, List<QualityScoreLogModel>> getList(QueryQualityScoreModel model) {
        List<QualityScoreLogModel> list = qualityScoreLogDao.getList(model);
        if (!CollectionUtils.isEmpty(list)) {
            Integer projectId = list.get(0).getRsProjectId();
            if (null != projectId) {
                RsProjectBaseModel projectInfo = rsProjectDao.getProjectNameAndRd(projectId);
                if (projectInfo!=null) {
                    list.forEach(item -> {
                        item.setPname(projectInfo.getPname());
                        item.setRdTitle(projectInfo.getRdTitle());
                    });
                }
            }
            //Date date = DateUtil.addDays(new Date(), Constant.SCORE_EDIT_DAYS);
            //过程文档月份组和其他类型判断最后一条评分记录，评分记录修改只允许对最新的一条评分编辑
            if (model.getType() != AuditModeEnum.RD_DOC.getModuleId()) {
                QualityScoreLogModel logModel = list.get(list.size() - 1);
                /*if (date.before(logModel.getCreateTime())) {
                    logModel.setEditable(true);
                }*/
                logModel.setEditable(true);
            } else {
                Map<String, List<QualityScoreLogModel>> map = new HashMap<>();
                list.forEach(item -> {
                    String months = item.getMonths();
                    if (!StringUtils.isEmpty(months)) {
                        ToolUtil.putAndAdd(map, months, item);
                    }
                });
                for (String s : map.keySet()) {
                    List<QualityScoreLogModel> logModels = map.get(s);
                    QualityScoreLogModel logModel = logModels.get(logModels.size() - 1);
                    /*if (date.before(logModel.getCreateTime())) {
                        logModel.setEditable(true);
                    }*/
                    logModel.setEditable(true);
                }
            }
        }
        list.forEach(item -> {
            //完成状况
            if (null == item.getIsFinal()) {
                item.setIsFinal(false);
            }
            //评分要素map转换
            String scores = item.getScoreData();
            Map<String, Integer> map = StringUtils.isEmpty(scores) ? null : JsonUtils.jsonToPojo(scores, Map.class);
            Map<Integer, Integer> scoreMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(map)) {
                for (String key : map.keySet()) {
                    int numberKey = Integer.parseInt(key);
                    scoreMap.put(numberKey, map.get(key));
                }
            }
            item.setScores(scoreMap);

            //月份组格式转换
            String months = item.getMonths();
            if (!StringUtils.isEmpty(months)) {
                item.setMonths(monthsConversion(months));
            }
        });
        Map<String, List<QualityScoreLogModel>> map = new HashMap<>();
        map.put("data", list);
        return map;
    }

    @Override
    public QualityScoreInfoModel getScoreConfig(QueryQualityScoreModel model) {
        Integer projectId = model.getRsProjectId();
        Integer type = model.getType();
        QualityScoreInfoModel qualityScoreInfo;
        if (type != AuditModeEnum.RD_DOC.getModuleId()) {
            qualityScoreInfo = qualityTypeDao.getConfig(model);
        } else {
            qualityScoreInfo = qualityTypeDao.getWeightByType(AuditModeEnum.RD_DOC.getModuleId());
        }
        //工程师信息list
        List<EngineerModel> users = new ArrayList<>();
        //留存备查资料 按部分docFileId取
        if (type == AuditModeEnum.RD_BACKUP_FILE.getModuleId()) {
            List<Integer> fileIds = projectDocFileDao.getBackupDataFileIds(projectId);
            if (!CollectionUtils.isEmpty(fileIds)) {
                users = flowInstanceLogDao.getSubmitter(model, fileIds);
            }
            //过程文件，查新报告 按项目取
        } else if (type == AuditModeEnum.RD_DOC.getModuleId()
                || type == AuditModeEnum.RD_NOVELTY_SEARCH.getModuleId()) {
            users = flowInstanceLogDao.getSubmitter(model, null);
            //创新体系 按公司取
        } else if (type == AuditModeEnum.RD_INNOVATION_SYS.getModuleId()) {
            users = flowInstanceLogDao.getSubmitterByCompany(model);
            //立项报告  按docFileId取
        } else if (type == AuditModeEnum.RD_REPORT.getModuleId()) {
            Integer fileId = projectDocFileDao.getRdReportId(projectId);
            users = flowInstanceLogDao.getSubmitterByFileId(fileId);
        }
        Map<Integer, EngineerModel> engineerModelMap = new LinkedHashMap<>();
        if (!CollectionUtils.isEmpty(users)) {
            users.forEach(item -> engineerModelMap.putIfAbsent(item.getId(), item));
        }
        qualityScoreInfo.setEngineerList(new ArrayList<>(engineerModelMap.values()));
        //返回过程文件需要的月份组和年权值
        if (AuditModeEnum.RD_DOC.getModuleId() == model.getType()) {
            List<QualityScoreMonthModel> list = qualityScoreMonthDao.getList(model);
            if (!CollectionUtils.isEmpty(list)) {
                for (QualityScoreMonthModel monthModel : list) {
                    monthModel.setScoreCount(monthModel.getScoreCount() + 1);
//                    List<String> months = JsonUtils.jsonToPojo(monthModel.getMonths(), List.class);
//                    if (!CollectionUtils.isEmpty(months)) {
//                        monthModel.setWeight(monthModel.getWeight().divide(new BigDecimal(months.size()), 3, BigDecimal.ROUND_HALF_UP));
//                    }
                    if (StringUtils.hasLength(monthModel.getScoresDetail())){
                        Map<Integer,Integer> map = JsonUtils.jsonToObject(monthModel.getScoresDetail(), new TypeReference<HashMap<Integer,Integer>>() {
                        });
                        monthModel.setScores(map);
                    }
                }
            }
            qualityScoreInfo.setMonths(list);
        } else {
            if (null == qualityScoreInfo.getScoreCount()) {
                qualityScoreInfo.setScoreCount(1);
                qualityScoreInfo.setFinal(false);
            } else {
                qualityScoreInfo.setScoreCount(qualityScoreInfo.getScoreCount() + 1);
            }
            if (StringUtils.hasLength(qualityScoreInfo.getScoresDetail())){
                Map<Integer,Integer> map = JsonUtils.jsonToObject(qualityScoreInfo.getScoresDetail(), new TypeReference<HashMap<Integer,Integer>>() {
                });
                qualityScoreInfo.setScores(map);
            }

        }
        return qualityScoreInfo;
    }

    @Override
    public List<EngineerModel> getCheckConfig(List<Integer> instanceIds) {
        if (CollectionUtils.isEmpty(instanceIds)){
            return new ArrayList<>();
        }
        return flowInstanceLogDao.getCheckSubmitter(instanceIds);
    }

    @Override
    public Boolean editScore(QualityScoreModel model, UserInfo userInfo) throws OwnerException {

        QualityScoreLog qualityScoreLog = qualityScoreLogDao.selectById(model.getId());
        if (null == qualityScoreLog) {
            throw new OwnerException("修改数据错误，请联系管理员");
        }
        QualityScore qualityScore = new QualityScore();
        //分数校验
        Map<Integer, Integer> scoreMap = model.getScores();
        if (null != scoreMap && !CollectionUtils.isEmpty(scoreMap.values())) {
            List<Integer> scores = new ArrayList<>(scoreMap.values());
            BigDecimal sum = new BigDecimal(scores.stream().reduce(Integer::sum).orElse(0));
            if (model.getTotalScore().compareTo(sum) != 0) {
                throw new OwnerException("总分计算有误，请重新计分");
            }
            Integer type = qualityScoreDao.getTypeById(qualityScoreLog.getQualityId());
            ScoreElementEnum.ScoreCheck(scores, type);
        }

        Date now = new Date();
        Boolean passed = model.getPassed(),isFinal = model.getFinal();
        /*Date date = DateUtil.addDays(now, Constant.SCORE_EDIT_DAYS);
        if (date.after(qualityScoreLog.getCreateTime())) {
            throw new OwnerException("该条评分记录已超过可编辑时限，无法修改");
        }*/
        //平均分修改
        Integer scoreCount = qualityScoreLog.getScoreCount();
        if (scoreCount > 1) {
            if (passed && !isFinal) {
                throw new OwnerException("评分通过时，完成状态不能标识为未完成！");
            }
            //（修改前平均分-（修改前总分-修改后的总分）/评分次数）
            qualityScoreLog.setAvgScore(
                    qualityScoreLog.getAvgScore()
                            .subtract((qualityScoreLog.getTotalScore()
                                    .subtract(model.getTotalScore()))
                                    .divide(new BigDecimal(scoreCount), 2, BigDecimal.ROUND_HALF_UP)));
            qualityScore.setPassRate(!passed ? BigDecimal.ZERO : new BigDecimal(0.8));
        } else {
            if (!Objects.equals(passed, isFinal)) {
                throw new OwnerException("通过和完成状态必须一致。");
            }
            qualityScore.setPassRate(BigDecimal.ONE);
            qualityScoreLog.setAvgScore(model.getTotalScore());
        }
        Map<Integer, Integer> scores = model.getScores();
        qualityScoreLog.setScores(null == scores ? null : JsonUtils.objectToJson(scores));
        qualityScoreLog.setTotalScore(model.getTotalScore());
        qualityScoreLog.setPassed(model.getPassed());
        qualityScoreLog.setMonth(model.getMonth());
        Integer userId = userInfo.getId();
        ToolUtil.entityUpdate(qualityScoreLog, userId, now);
        qualityScore.setTotalScore(qualityScoreLog.getTotalScore());
        qualityScore.setAvgScore(qualityScoreLog.getAvgScore());
        qualityScore.setFinal(model.getFinal());
        ToolUtil.entityUpdate(qualityScore, userId, now);
        qualityScore.setMonth(model.getMonth());
        qualityScore.setId(qualityScoreLog.getQualityId());
        qualityScore.setWeight(model.getWeight());
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
        try {
            qualityScoreDao.editScore(qualityScore);
            qualityScoreLogDao.editScore(qualityScoreLog);
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException("评分记录编辑失败");
        }
        return true;
    }

    @Override
    public List<ScoreTypeModel> getWeights() {
        return qualityTypeDao.getAllWeight();
    }

    @Override
    public Boolean saveWeight(List<ScoreTypeModel> list, Integer userId) {
        return qualityTypeDao.saveWeight(list, userId, new Date()) > 0;
    }

    @Override
    public PageResult getCollectList(QueryScoreCollectModel model, DataPermModel dataPermModel) {
        Pagination page = model.getPagination();
        if (CollectionUtils.isEmpty(page.getDescs()) && CollectionUtils.isEmpty(page.getAscs())) {
            page.setDescs(Collections.singletonList("lastUpdateTime"));
        }
        Date scoreMonth = model.getScoreMonth();
        if (null != scoreMonth) {
            model.setScoreMonth(DateUtil.getMonthFirstDay(scoreMonth));
        }
        List<ScoreCollectModel> list = qualityScoreDao.getCollectList(page, model, dataPermModel);
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(item -> {
                BigDecimal count = new BigDecimal(item.getCount());
                BigDecimal avgScore = item.getAvgScore() == null ? BigDecimal.ZERO : item.getAvgScore();
//                BigDecimal totalWeight = item.getTotalWeight() == null ? BigDecimal.ZERO : item.getTotalWeight();
//                BigDecimal value = item.getjValue() == null ? BigDecimal.ZERO : item.getjValue();
                item.setAvgScore(avgScore.divide(count, 2, BigDecimal.ROUND_HALF_UP));
                item.setWeight(item.getTotalWeight().divide(count, 3, BigDecimal.ROUND_HALF_UP));
                item.setjValue(item.getjValue().setScale(2, BigDecimal.ROUND_HALF_UP));
            });
        }
        ScoreHeaderModel headerModel = qualityScoreDao.getTotal(model, dataPermModel);
        BigDecimal value = headerModel.getjValue() == null ? BigDecimal.ZERO : headerModel.getjValue();
        BigDecimal avgScore = headerModel.getAvgScore() == null ? BigDecimal.ZERO : headerModel.getAvgScore();
        headerModel.setjValue(value.setScale(2, BigDecimal.ROUND_HALF_UP));
        Integer count = headerModel.getCount();
        if (0 != count) {
            headerModel.setAvgScore(avgScore.divide(new BigDecimal(headerModel.getCount()), 2, BigDecimal.ROUND_HALF_UP));
        } else {
            headerModel.setAvgScore(BigDecimal.ZERO);
        }
        return PageUtils.buildPageResult(page, list, null, headerModel);
    }

    @Override
    public PageModel<List<ScoreModel>> getScoreList(QueryScoreModel model, DataPermModel dataPermModel) {
        Date month = DateUtil.getMonthFirstDay(DateUtil.parse(model.getMonth(), "yyyyMM"));
        Pagination page = model.getPagination();
        if (CollectionUtils.isEmpty(page.getDescs()) && CollectionUtils.isEmpty(page.getAscs())) {
            page.setAscs(Arrays.asList("qs.createTime"));
        }
        List<ScoreModel> list = qualityScoreDao.getScoreList(page, model, month, dataPermModel);
        if (!CollectionUtils.isEmpty(list)) {
            List<Integer> ids = list.stream().map(ScoreModel::getRsProjectId).collect(Collectors.toList());
            List<ProjectEntity> infos = rsProjectDao.getInfos(ids);
            Map<Integer, ProjectEntity> map = infos.stream().collect(Collectors.toMap(e -> e.getId(), e -> e));
            list.forEach(item -> {
                ProjectEntity entity = map.get(item.getRsProjectId());
                if (null != entity) {
                    item.setRdTitle(entity.getRdTitle());
                    item.setPname(entity.getPname());
                }
                if (!StringUtils.isEmpty(item.getMonths())) {
                    List<String> months = JsonUtils.jsonToPojo(item.getMonths(), List.class);
                    item.setMonths(org.apache.commons.lang3.StringUtils.join(months.toArray(), ","));
                }
                if (Objects.equals(item.getStatus(), 1)) {
                    item.setjValue((item.getAvgScore().multiply(item.getPassRate()
                            .multiply(item.getTotalWeight())))
                            .setScale(2, BigDecimal.ROUND_HALF_UP));
                }
                ;
            });
        }
        return PageUtils.build(page, list);
    }

    @Override
    public void export(OutputStream out, QueryScoreCollectModel model, DataPermModel dataPerm) {
        Date scoreMonth = model.getScoreMonth();
        if (null != scoreMonth) {
            model.setScoreMonth(DateUtil.getMonthFirstDay(scoreMonth));
        }
        List<ExportScoreModel> list = qualityScoreDao.getExportList(model, dataPerm);
        if (!CollectionUtils.isEmpty(list)) {
            Set<Integer> ids = new HashSet<>();
            list.forEach(item -> {
                if ("1".equals(item.getStatus())) {
                    item.setjValue((item.getAvgScore().multiply(item.getTotalWeight()).multiply(item.getPassRate())).setScale(2, BigDecimal.ROUND_HALF_UP));
                }
                ids.add(item.getRsProjectId());
                item.setStatus("1".equals(item.getStatus()) ? "完成" : "未完成");
                item.setType(AuditModeEnum.getByModuleId(Integer.valueOf(item.getType())).getAuditName());
                item.setNum(1);
                if (!StringUtils.isEmpty(item.getMonths())) {
                    List<String> months = JsonUtils.jsonToPojo(item.getMonths(), List.class);
                    item.setMonths(org.apache.commons.lang3.StringUtils.join(months.toArray(), ","));
                }
            });
            List<ProjectEntity> infos = rsProjectDao.getInfos(ids);
            Map<Integer, ProjectEntity> map = infos.stream().collect(Collectors.toMap(e -> e.getId(), e -> e));
            list.forEach(item -> {
                ProjectEntity entity = map.get(item.getRsProjectId());
                if (null != entity) {
                    item.setRdTitle(entity.getRdTitle());
                    item.setPname(entity.getPname());
                }
            });
        }
        Map<String, Object> data = new HashMap<>(1);
        data.put("list", list);
        YsExcelUtils.generalReport(data, msConfig.getUploadConfig().getResourcePath() + Constant.TEMPLATE_DIR + "审核序时账模板.xlsx", workbook -> {
            try {
                workbook.write(out);
                workbook.close();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        });
    }

    @Override
    public Boolean editCompletion(CompletionEditModel model, Integer id) {
        return qualityScoreDao.editCompletion(model, id, new Date()) > 0;
    }

    //获取评分对象（留存备查资料，过程文件，创新体系）的提交、审核时间,赋值
    private void setTime(QualityScoreModel model, QualityScore qualityScore) throws OwnerException {
        Integer type = model.getType();
        Integer projectId = model.getRsProjectId();
        Date date = new Date();
        FlowInstanceLog log = new FlowInstanceLog();
        //留存备查资料 按部分docFileId取
        if (type == AuditModeEnum.RD_BACKUP_FILE.getModuleId()) {
            List<Integer> fileIds = projectDocFileDao.getBackupDataFileIds(projectId);
            log = flowInstanceLogDao.getSubmitTimeByFileIds(fileIds);
            //过程文件 按项目取
        } else if (type == AuditModeEnum.RD_DOC.getModuleId()) {
            log = flowInstanceLogDao.getSubmitTimeByProjectId(model);
            //创新体系 按公司取
        } else if (type == AuditModeEnum.RD_INNOVATION_SYS.getModuleId()) {
            log = flowInstanceLogDao.getSubmitTimeByCompany(model);
        }
        if (null != log) {
            List<Date> times = flowInstanceLogDao.getAuditTime(log);
            if (times.size() < 2) {
                qualityScore.setAuditTime(DateUtil.addDays(-1));
            } else {
                qualityScore.setAuditTime(times.get(1));
            }
            qualityScore.setSubmitTime(log.getCreateTime());
        } else {
            throw new OwnerException("当前评分数据不存在相关提交信息，无法质量评分");
        }
    }

    /**
     * 评分记录月份组格式转换
     * @param months
     * @return
     */
    private String monthsConversion(String months) {
        List<String> monthList = JsonUtils.jsonToList(months, String.class);
        Map<String, String> monthMap = new LinkedHashMap<>();
        for (String s : monthList) {
            String year = s.substring(0, 4);
            String month = s.substring(5);
            if (null == monthMap.get(year)) {
                monthMap.put(year, year + month);
            } else {
                monthMap.put(year, monthMap.get(year) + month);
            }
        }
        return String.join(",", monthMap.values());
    }
}
