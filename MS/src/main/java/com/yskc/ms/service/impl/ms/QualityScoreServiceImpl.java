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
        //???????????????????????? ??????isFinal??????
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
                throw new OwnerException("??????????????????????????????");
            }
            Integer type = model.getType();
            if (type == AuditModeEnum.RD_DOC.getModuleId() && StringUtils.isEmpty(model.getMonths())) {
                throw new OwnerException("???????????????????????????????????????");
            }
            Map<Integer, Integer> scoreMap = model.getScores();
            if (null != scoreMap && !CollectionUtils.isEmpty(scoreMap.values())) {
                List<Integer> scores = new ArrayList<>(scoreMap.values());
                BigDecimal sum = new BigDecimal(scores.stream().reduce(Integer::sum).orElse(0));
                if (model.getTotalScore().compareTo(sum) != 0) {
                    throw new OwnerException("????????????????????????????????????");
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
                    throw new OwnerException("??????????????????????????????");
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

            //???????????????????????????????????????
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
                throw new OwnerException("???????????????????????????????????????");
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
            //??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
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
            //????????????
            if (null == item.getIsFinal()) {
                item.setIsFinal(false);
            }
            //????????????map??????
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

            //?????????????????????
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
        //???????????????list
        List<EngineerModel> users = new ArrayList<>();
        //?????????????????? ?????????docFileId???
        if (type == AuditModeEnum.RD_BACKUP_FILE.getModuleId()) {
            List<Integer> fileIds = projectDocFileDao.getBackupDataFileIds(projectId);
            if (!CollectionUtils.isEmpty(fileIds)) {
                users = flowInstanceLogDao.getSubmitter(model, fileIds);
            }
            //??????????????????????????? ????????????
        } else if (type == AuditModeEnum.RD_DOC.getModuleId()
                || type == AuditModeEnum.RD_NOVELTY_SEARCH.getModuleId()) {
            users = flowInstanceLogDao.getSubmitter(model, null);
            //???????????? ????????????
        } else if (type == AuditModeEnum.RD_INNOVATION_SYS.getModuleId()) {
            users = flowInstanceLogDao.getSubmitterByCompany(model);
            //????????????  ???docFileId???
        } else if (type == AuditModeEnum.RD_REPORT.getModuleId()) {
            Integer fileId = projectDocFileDao.getRdReportId(projectId);
            users = flowInstanceLogDao.getSubmitterByFileId(fileId);
        }
        Map<Integer, EngineerModel> engineerModelMap = new LinkedHashMap<>();
        if (!CollectionUtils.isEmpty(users)) {
            users.forEach(item -> engineerModelMap.putIfAbsent(item.getId(), item));
        }
        qualityScoreInfo.setEngineerList(new ArrayList<>(engineerModelMap.values()));
        //????????????????????????????????????????????????
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
            throw new OwnerException("???????????????????????????????????????");
        }
        QualityScore qualityScore = new QualityScore();
        //????????????
        Map<Integer, Integer> scoreMap = model.getScores();
        if (null != scoreMap && !CollectionUtils.isEmpty(scoreMap.values())) {
            List<Integer> scores = new ArrayList<>(scoreMap.values());
            BigDecimal sum = new BigDecimal(scores.stream().reduce(Integer::sum).orElse(0));
            if (model.getTotalScore().compareTo(sum) != 0) {
                throw new OwnerException("????????????????????????????????????");
            }
            Integer type = qualityScoreDao.getTypeById(qualityScoreLog.getQualityId());
            ScoreElementEnum.ScoreCheck(scores, type);
        }

        Date now = new Date();
        Boolean passed = model.getPassed(),isFinal = model.getFinal();
        /*Date date = DateUtil.addDays(now, Constant.SCORE_EDIT_DAYS);
        if (date.after(qualityScoreLog.getCreateTime())) {
            throw new OwnerException("?????????????????????????????????????????????????????????");
        }*/
        //???????????????
        Integer scoreCount = qualityScoreLog.getScoreCount();
        if (scoreCount > 1) {
            if (passed && !isFinal) {
                throw new OwnerException("?????????????????????????????????????????????????????????");
            }
            //?????????????????????-??????????????????-?????????????????????/???????????????
            qualityScoreLog.setAvgScore(
                    qualityScoreLog.getAvgScore()
                            .subtract((qualityScoreLog.getTotalScore()
                                    .subtract(model.getTotalScore()))
                                    .divide(new BigDecimal(scoreCount), 2, BigDecimal.ROUND_HALF_UP)));
            qualityScore.setPassRate(!passed ? BigDecimal.ZERO : new BigDecimal(0.8));
        } else {
            if (!Objects.equals(passed, isFinal)) {
                throw new OwnerException("????????????????????????????????????");
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
            throw new OwnerException("????????????????????????");
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
                item.setStatus("1".equals(item.getStatus()) ? "??????" : "?????????");
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
        YsExcelUtils.generalReport(data, msConfig.getUploadConfig().getResourcePath() + Constant.TEMPLATE_DIR + "?????????????????????.xlsx", workbook -> {
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

    //????????????????????????????????????????????????????????????????????????????????????????????????,??????
    private void setTime(QualityScoreModel model, QualityScore qualityScore) throws OwnerException {
        Integer type = model.getType();
        Integer projectId = model.getRsProjectId();
        Date date = new Date();
        FlowInstanceLog log = new FlowInstanceLog();
        //?????????????????? ?????????docFileId???
        if (type == AuditModeEnum.RD_BACKUP_FILE.getModuleId()) {
            List<Integer> fileIds = projectDocFileDao.getBackupDataFileIds(projectId);
            log = flowInstanceLogDao.getSubmitTimeByFileIds(fileIds);
            //???????????? ????????????
        } else if (type == AuditModeEnum.RD_DOC.getModuleId()) {
            log = flowInstanceLogDao.getSubmitTimeByProjectId(model);
            //???????????? ????????????
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
            throw new OwnerException("??????????????????????????????????????????????????????????????????");
        }
    }

    /**
     * ?????????????????????????????????
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
