package com.yskc.rs.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.dao.DocFileTrialDao;
import com.yskc.rs.dao.ProjectDao;
import com.yskc.rs.dao.company.CompanyHolidayDao;
import com.yskc.rs.dao.project.ProjectTrialConfigDao;
import com.yskc.rs.dao.project.ProjectYieldConfigDao;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.entity.project.ProjectTrialConfigEntity;
import com.yskc.rs.entity.project.ProjectYieldConfigEntity;
import com.yskc.rs.enums.CostEnum;
import com.yskc.rs.models.BatchDeleteModel;
import com.yskc.rs.models.CheckStatusModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.company.CompanyHolidayModel;
import com.yskc.rs.models.projectyieldconfig.*;
import com.yskc.rs.service.*;
import com.yskc.rs.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: zhangdingfu
 * @CreateTime: 2020-09-02 18:17
 * @Description: 项目试制量配置业务实现层
 */
@Service
public class ProjectYieldConfigServiceImpl implements ProjectYieldConfigService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProjectYieldConfigDao projectYieldConfigDao;
    @Autowired
    private ProjectMaterialService projectMaterialService;
    @Autowired
    private ProjectEnergyService projectEnergyService;
    @Autowired
    private ProjectRdEquipmentService projectRdEquipmentService;
    @Autowired
    private ProjectRdEmployeeService projectRdEmployeeService;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private CommonService commonService;
    @Autowired
    private RsConfig rsConfig;
    @Autowired
    private ProjectTrialConfigDao projectTrialConfigDao;
    @Autowired
    private CompanyHolidayDao companyHolidayDao;
    @Autowired
    private DocFileTrialDao docFileTrialDao;

    @Override
    public boolean add(UserInfo userInfo, ProjectYieldConfigEntity config) throws OwnerException {
        config.setDeptName(config.getDeptName().trim());
        if (projectYieldConfigDao.getRepeat(config) != null) {
            throw new OwnerException(MessageFormat.format("部门【{0}】，试制日期【{1}】，试制开始时间【{2}】已存在配置。",
                    config.getDeptName(), DateUtil.format(config.getTrialDate(), DateUtil.DEFAULT_DATE_FORMAT), DateUtil.format(config.getTrialDate(), "HH:mm")));
        }
        //判断同RD同部门同日期的试制数据是否时间段重复
        ProjectYieldConfigEntity entity = projectYieldConfigDao.getRepeatTime(config);
        if (entity!=null) {
            String start = null;
            String end = null;
            Date entityStartTime = entity.getStartTime();
            Date entityEndTime = entity.getEndTime();
            Date entityTestStartTime = entity.getTestStartTime();
            Date entityTestEndTime = entity.getTestEndTime();
            start = DateUtil.format(entityStartTime,"HH:mm")+"-"+DateUtil.format(entityEndTime,"HH:mm");
            end = DateUtil.format(entityTestStartTime,"HH:mm")+"-"+DateUtil.format(entityTestEndTime,"HH:mm");

            if (entityTestStartTime!=null){
                throw new OwnerException("RD：【"+entity.getRdTitle()+"】在【"+DateUtil.format(entity.getTrialDate(),DateUtil.DEFAULT_DATE_FORMAT)+"】已有试制时间【"+start+"】，检验时间【"+end+"】，请检查重复时间！");
            }else {
                throw new OwnerException("RD：【"+entity.getRdTitle()+"】在【"+DateUtil.format(entity.getTrialDate(),DateUtil.DEFAULT_DATE_FORMAT)+"】已有试制时间【"+start+"】，请检查重复时间！");
            }
        }
        if (null != config.getTestStartTime() && null != config.getTestEndTime()) {
            long startTime = config.getStartTime().getTime();
            long endTime = config.getEndTime().getTime();
            long testStartTime = config.getTestStartTime().getTime();
            long testEndTime = config.getTestEndTime().getTime();
            if ((startTime>(testStartTime) && startTime>=(testEndTime)) || (endTime<=(testStartTime) && endTime<(testEndTime))) {
            } else {
                throw new OwnerException("试制时间与检验时间存在重合，请错开后再次保存。");
            }
        }
        config.buildCreate(new Date(), userInfo);
        return projectYieldConfigDao.insert(config) > 0;
    }

    @Override
    public boolean edit(UserInfo userInfo, ProjectYieldConfigEntity config) throws OwnerException {
        config.setDeptName(config.getDeptName().trim());
        ProjectYieldConfigEntity old = projectYieldConfigDao.getRepeat(config);
        if (null != old && !old.getId().equals(config.getId())) {
            throw new OwnerException(MessageFormat.format("部门【{0}】，试制日期【{1}】，试制开始时间【{2}】已存在配置。",
                    config.getDeptName(), DateUtil.format(config.getTrialDate(), DateUtil.DEFAULT_DATE_FORMAT), DateUtil.format(config.getTrialDate(), "HH:mm")));
        }

        //判断同RD同部门同日期的试制数据是否时间段重复
        ProjectYieldConfigEntity entity = projectYieldConfigDao.getRepeatTime(config);
        if (entity!=null) {
            String start = null;
            String end = null;
            Date entityStartTime = entity.getStartTime();
            Date entityEndTime = entity.getEndTime();
            Date entityTestStartTime = entity.getTestStartTime();
            Date entityTestEndTime = entity.getTestEndTime();
            start = DateUtil.format(entityStartTime,"HH:mm")+"-"+DateUtil.format(entityEndTime,"HH:mm");
            end = DateUtil.format(entityTestStartTime,"HH:mm")+"-"+DateUtil.format(entityTestEndTime,"HH:mm");

            if (entityTestStartTime!=null){
                throw new OwnerException("RD：【"+entity.getRdTitle()+"】在【"+DateUtil.format(entity.getTrialDate(),DateUtil.DEFAULT_DATE_FORMAT)+"】已有试制时间【"+start+"】，检验时间【"+end+"】，请检查重复时间！");
            }else {
                throw new OwnerException("RD：【"+entity.getRdTitle()+"】在【"+DateUtil.format(entity.getTrialDate(),DateUtil.DEFAULT_DATE_FORMAT)+"】已有试制时间【"+start+"】，请检查重复时间！");
            }
        }
        if (null != config.getTestStartTime() && null != config.getTestEndTime()) {
            long startTime = config.getStartTime().getTime();
            long endTime = config.getEndTime().getTime();
            long testStartTime = config.getTestStartTime().getTime();
            long testEndTime = config.getTestEndTime().getTime();
            if (startTime>(testStartTime) && startTime>=(testEndTime) || (endTime<=(testStartTime) && endTime<(testEndTime))) {
            } else {
                throw new OwnerException("试制时间与检验时间存在重合，请错开后再次保存。");
            }
        }
        config.buildUpdate(new Date(), userInfo);
        return projectYieldConfigDao.updateById(config) > 0;
    }

    @Override
    public boolean delete(BatchDeleteModel model) throws OwnerException {
        List<Integer> ids = model.getIds();

        if (projectYieldConfigDao.getUsedCount(ids) > 0) {
            throw new OwnerException("有项目已存在【创新项目-研发项目管理-研发过程管理】中，不能删除!");
        }
        return projectYieldConfigDao.deleteBatchIds(ids) > 0;
    }

    @Override
    public List<ProjectYieldConfigModel> getList(QueryYieldConfigModel query) {
        return projectYieldConfigDao.getList(query);
    }

    @Override
    public boolean handleRd(UserInfo userInfo, QueryYieldConfigModel config) throws OwnerException {
        CheckStatusModel model = new CheckStatusModel(config.getProjectId(),config.getMonth());
        commonService.checkStatus(Arrays.asList(model), Arrays.asList(config.getRdType()),userInfo);
        List<ProjectYieldConfigModel> yieldList = getList(config);
        Map<String, ProjectYieldConfigModel> yieldMap = new HashMap<>();
        String keyFormat = "{0}{1}";
        Map<String, BigDecimal> ratioMap = new HashMap<>();
        yieldList.forEach(item -> {
            String key = MessageFormat.format(keyFormat, DateUtil.format(item.getTrialDate(), DateUtil.DEFAULT_DATE_FORMAT), item.getDeptName());
            yieldMap.put(key, item);
            BigDecimal ratio;
            if (item.getTotalYield().compareTo(BigDecimal.ZERO) == 0 || item.getRdYield().compareTo(BigDecimal.ZERO) == 0) {
                ratio = BigDecimal.ZERO;
            } else {
                ratio = item.getRdYield().divide(item.getTotalYield(), Constant.MAX_SCALE, BigDecimal.ROUND_HALF_UP);
            }
            ratioMap.put(key, ratio);
        });
        if (CollectionUtils.isEmpty(yieldList)) {
            throw new OwnerException("应用失败，请先设置总产量/试制量。");
        }
        switch (CostEnum.getCostEnum(config.getRdType())) {
            case IRON_STIMULUS:
            case IRON_FUEL:
                return projectEnergyService.setYield(userInfo, yieldMap, ratioMap, config);
            case IRON_MATERIAL:
            case IRON_TRIAL:
                return projectMaterialService.setYield(userInfo, yieldMap, ratioMap, config);
            default:
                throw new OwnerException("应用失败，无法获取费用类型。");
        }

    }

    @Override
    public boolean importYield(UserInfo userInfo, List<ProjectYieldConfigEntity> data, Integer projectId) throws OwnerException {
        if (CollectionUtils.isEmpty(data)) {
            throw new OwnerException("未导入任何数据。");
        }
        //获取所有rdTitle
        Set<String> rdTitles = new HashSet<>();
        for (ProjectYieldConfigEntity entity : data) {
            rdTitles.add(entity.getRdTitle());
        }
        //根据所有的rdTitle与公司id查询出所有数据
        List<ProjectEntity> tdtitleList = projectDao.getRdTitle(rdTitles, userInfo.getCompanyId());
        if (CollectionUtils.isEmpty(tdtitleList)) {
            throw new OwnerException("导入的RD不存在");
        }

        Map<String, ProjectEntity> projectEntityMap = new HashMap<>();
        //rdTitle作为Key
        for (ProjectEntity projectEntity : tdtitleList) {
            projectEntityMap.put(projectEntity.getRdTitle(), projectEntity);
        }

        Map<String, ProjectYieldConfigEntity> filterMap = new HashMap<>();
        //格式化key
        String keyFormat = "{0}_{1}_{2}_{3}";
        String timeFormat = "HH:mm";
        for (int i = 0; i < data.size(); i++) {
            ProjectYieldConfigEntity yield = data.get(i);
            ProjectEntity projectEntity = projectEntityMap.get(yield.getRdTitle());
            if (null == projectEntity) {
                throw new OwnerException(MessageFormat.format("第{1,number,#}行，不存在【{0}】的项目。", yield.getRdTitle(), i + 2));
            }
            String key = MessageFormat.format(keyFormat, projectEntity.getId(), yield.getDeptName(),
                    DateUtil.format(yield.getTrialDate(), DateUtil.DEFAULT_DATE_FORMAT),DateUtil.format(yield.getStartTime(),"HH:mm"));
            if (filterMap.containsKey(key)) {
                continue;
            }
            Date month = DateUtil.getMonthFirstDay(yield.getMonth());
            if (month.compareTo(projectEntity.getBeginDate()) < 0 || month.compareTo(projectEntity.getEndDate()) > 0) {
                throw new OwnerException(MessageFormat.format("第{1,number,#}行，月份【{4}】不存在项目【{0}】的起止日期【{2}~{3}】内。",
                        yield.getRdTitle(), i + 2, DateUtil.format(projectEntity.getBeginDate(), DateUtil.DEFAULT_DATE_FORMAT),
                        DateUtil.format(projectEntity.getEndDate(), DateUtil.DEFAULT_DATE_FORMAT), DateUtil.format(yield.getMonth(), DateUtil.DEFAULT_YYMM_FORMAT)));
            }
            if (month.compareTo(DateUtil.getMonthFirstDay(yield.getTrialDate())) != 0) {
                throw new OwnerException(MessageFormat.format("第{0,number,#}行，试制日期【{1}】不存在月份【{2}】内。", i + 2,
                        DateUtil.format(yield.getTrialDate(), DateUtil.DEFAULT_DATE_FORMAT),
                        DateUtil.format(yield.getMonth(), DateUtil.DEFAULT_YYMM_FORMAT)));
            }
            if (yield.getStartTime() != null && yield.getEndTime() != null && yield.getStartTime().getTime() > yield.getEndTime().getTime()) {

                throw new OwnerException(MessageFormat.format(
                        "第{0,number,#}行，月份【{1}】的开始时间【{2}】不能大于结束时间【{3}】", i + 2,
                        DateUtil.format(month, DateUtil.DEFAULT_YYMM_FORMAT),
                        DateUtil.format(yield.getStartTime(), timeFormat),
                        DateUtil.format(yield.getEndTime(), timeFormat)
                ));
            }
            yield.setMonth(month);
            yield.setProjectId(projectEntity.getId());
            yield.setCreatorId(projectEntity.getCreatorId());
            // source默认为0
            yield.setSource(0);
            filterMap.put(key, yield);
        }

        List<ProjectYieldConfigEntity> projectEntityList = projectYieldConfigDao.findByBean(new ArrayList<>(filterMap.values()));
        //需要修改的数据集合
        List<ProjectYieldConfigEntity> updateList = new ArrayList<>();
        //需要插入的数据集合
        List<ProjectYieldConfigEntity> insertList = new ArrayList<>();
        Map<String, Integer> idMap = new HashMap<>();
        for (ProjectYieldConfigEntity bean : projectEntityList) {
            idMap.put(MessageFormat.format(keyFormat, bean.getProjectId(), bean.getDeptName(),
                    DateUtil.format(bean.getTrialDate(), DateUtil.DEFAULT_DATE_FORMAT),DateUtil.format(bean.getStartTime(),"HH:mm")), bean.getId());
        }
        Date date = new Date();
        List<ProjectYieldConfigEntity> checkList = new ArrayList<>();
        for (String key : filterMap.keySet()) {
            ProjectYieldConfigEntity project = filterMap.get(key);
            project.setLastUpdateTime(date);
            project.setLastUpdatorId(userInfo.getUserId());
            project.setMsLastUpdatorId(userInfo.getMsUserId());
            if (idMap.containsKey(key)) {
                // 更新
                project.setId(idMap.get(key));
                updateList.add(project);
            } else {
                // 插入
                project.setCompanyId(userInfo.getCompanyId());
                project.setCreatorId(userInfo.getUserId());
                project.setCreateTime(date);
                project.setMsCreatorId(userInfo.getMsUserId());
                insertList.add(filterMap.get(key));
            }
            checkList.add(project);
        }

        ProjectYieldConfigEntity report = projectYieldConfigDao.getReport(checkList);
        if (report!=null){
            String format = DateUtil.format(report.getTrialDate(), DateUtil.DEFAULT_YYMM_FORMAT);
            String start = DateUtil.format(report.getStartTime(),timeFormat)+"-"+DateUtil.format(report.getEndTime(),timeFormat);
            String end = DateUtil.format(report.getTestStartTime(),timeFormat)+"-"+DateUtil.format(report.getTestEndTime(),timeFormat);
            throw new OwnerException("系统中已有数据：项目【"+ report.getRdTitle()+"】部门【"+report.getDeptName()+"】日期【"+format+"】" +
                    "时间段【"+start+"】时间段【"+end+"】，请检查重复时间！");
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction();
            if (!CollectionUtils.isEmpty(updateList)) {
                List<List<ProjectYieldConfigEntity>> updateLists = ListUtils.subList(updateList, Constant.MAX_INSERT_OR_UPDATE_COUNT);
                for (List<ProjectYieldConfigEntity> currents : updateLists) {
                    projectYieldConfigDao.updateBatch(currents);
                }
            }
            if (!CollectionUtils.isEmpty(insertList)) {
                List<List<ProjectYieldConfigEntity>> insertLists = ListUtils.subList(insertList, Constant.MAX_INSERT_OR_UPDATE_COUNT);
                for (List<ProjectYieldConfigEntity> currents : insertLists) {
                    projectYieldConfigDao.addBatch(currents);
                }
            }
            TransactionUtils.commit(transactionStatus);
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException("保存失败");
        }

        return true;
    }

    @Override
    public PageModel<List<ProjectYieldConfigModel>> queryPYieldConfigList(QueryYieldConfigParams param, Integer companyId) {
        Pagination pagination = param.getPagination();
        if (CollectionUtils.isEmpty(pagination.getDescs()) && CollectionUtils.isEmpty(pagination.getAscs())) {
            pagination.setAscs(CollUtil.newArrayList("rdTitle","trialDate"));
        }
        if (param.getTrialDate() != null) {
            param.setTrialDate(cn.hutool.core.date.DateUtil.beginOfDay(param.getTrialDate()));
        }
        Date beginMonth = DateUtil.getYearFirstDay(param.getYear());
        Date endMonth = DateUtil.getYearLastDay(param.getYear());
        List<ProjectYieldConfigModel> list = projectYieldConfigDao.queryPYieldConfigList(pagination, param, companyId, beginMonth, endMonth);
        if (CollectionUtils.isEmpty(list)) {
            return PageUtils.build(pagination, list);
        }
        List<Integer> ids = new ArrayList<>();
        list.forEach(item->{
            ids.add(item.getId());
            item.setDel(true);
        });
        List<Integer> counts = docFileTrialDao.getCountByTrialId(ids);
        if (!CollectionUtils.isEmpty(counts)) {
            Map<Integer, Boolean> map = new HashMap<>();
            counts.forEach(item -> {
                map.put(item, false);
            });
            list.forEach(item->{
                Boolean del = map.get(item.getId());
                if (null != del) {
                    item.setDel(del);
                }
            });
        }
        return PageUtils.build(pagination, list);
    }

    @Override
    public void exportPlan(QueryYieldConfigParams param, UserInfo info, OutputStream out) throws OwnerException {
        String templatePath = rsConfig.getUploadConfig().getResourcePath() + Constant.TEMPLATE_DIR + "研发试制计划表模板.xlsx";
        if (StrUtil.isEmpty(templatePath)) {
            throw new OwnerException("导出失败，无法获取模板。");
        }
        if (param.getTrialDate() != null) {
            param.setTrialDate(cn.hutool.core.date.DateUtil.beginOfDay(param.getTrialDate()));
        }
        Date beginMonth = DateUtil.getYearFirstDay(param.getYear());
        Date endMonth = DateUtil.getYearLastDay(param.getYear());
        List<ProjectYieldConfigModel> list = projectYieldConfigDao.queryPYieldConfigList(param, info.getCompanyId(), beginMonth, endMonth);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("list", list);
        dataMap.put("title", info.getCompanyName() + "-" + param.getYear() + "研发试制计划表");
        YsExcelUtils.generalReport(dataMap, templatePath, (workbook) -> {
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

    @Override
    public Boolean refreshYieldConfig(RefreshYieldConfigModel refresh, UserInfo userInfo) throws OwnerException {
        Date startTime = refresh.getStartTime();
        Date endTime = refresh.getEndTime();
        Date lastTime = AttDataUtils.DEFAULT_OFF_TIME3;
        // 休息起止时间有任意一个为null，则取23:59:59
        if (startTime == null || endTime == null) {
            startTime = endTime = lastTime;
        }
        Date trialTime = refresh.getTrialTime();
        Integer projectId = refresh.getProjectId();
        ProjectEntity projectDept = projectDao.getYieldDept(projectId);
        if (projectDept == null) {
            throw new OwnerException("获取项目失败，请稍后再试。");
        }
        List<ProjectYieldConfigEntity> yieldList = new ArrayList<>();
        Integer companyId = userInfo.getCompanyId();
        Date month = refresh.getMonth();
        BigDecimal[] equDataArr = refresh.getEquDataArr();
        // 0：手动添加/导入 1：刷新
        Integer source = 1;
        Date now = new Date();
        Integer userId = userInfo.getUserId();
        Integer msUserId = userInfo.getMsUserId();
        if (equDataArr != null) {
            String place = ToolUtils.getProjectPlace(projectDept);
            Date curTrialDate;
            String unit = "小时";
            BigDecimal current;
            Date currentEndTime;
            Long restMinute = null;
            boolean trialTimeLeEndTime = trialTime.compareTo(endTime) < 0;
            Date restBegin = trialTime.compareTo(startTime) > 0 ? trialTime : startTime;
            for (int i = 0; i < equDataArr.length; i++) {
                current = equDataArr[i];
                if (current != null && current.compareTo(BigDecimal.ZERO) > 0) {
                    curTrialDate = cn.hutool.core.date.DateUtil.offsetDay(month, i);
                    int minute = (int) Math.ceil(current.doubleValue() * 60);
                    currentEndTime = cn.hutool.core.date.DateUtil.offsetMinute(trialTime, minute);
                    // 结束时间大于休息开始时间且试制开始时间小于休息结束时间
                    if (currentEndTime.compareTo(startTime) > 0 && trialTimeLeEndTime) {
                        if (restMinute == null) {
                            restMinute = cn.hutool.core.date.DateUtil.between(restBegin, endTime, DateUnit.MINUTE);
                        }
                        minute += restMinute;
                        currentEndTime = cn.hutool.core.date.DateUtil.offsetMinute(trialTime, minute);
                    }
                    if (currentEndTime.compareTo(lastTime) > 0) {
                        currentEndTime = lastTime;
                    }
                    yieldList.add(ProjectYieldConfigEntity.build(now, userId, msUserId, projectId, companyId, month, place, unit, current, AttDataUtils.DAY_HOUR,
                            curTrialDate, trialTime, currentEndTime, source,null,BigDecimal.ZERO,null,BigDecimal.ZERO,BigDecimal.ZERO));
                }
            }
        }

        ProjectTrialConfigEntity configEntity = ProjectTrialConfigEntity.build(refresh, companyId, userId, msUserId, now);
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction();
            projectYieldConfigDao.deleteOldTrial(companyId, projectId, month, source);
            if (!CollectionUtil.isEmpty(yieldList)) {
                projectYieldConfigDao.addBatch(yieldList);
            }
            projectTrialConfigDao.insertOrUpdate(configEntity);
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException("刷新试制失败");
        }
    }

    @Override
    public List<AggMsgModel> aggFee(TrialAggModel aggModel, UserInfo userInfo) throws OwnerException {
        List<ProjectYieldConfigInfoModel> infoList = projectYieldConfigDao.getInfos(userInfo.getCompanyId(), aggModel.getMonth(), aggModel.getAggType());
        if (CollectionUtils.isEmpty(infoList)) {
            return CollUtil.newArrayList(AggMsgModel.buildFail(MessageFormat.format("月份【{0}】不存在试制计划数据，请检查！", DateUtil.format(aggModel.getMonth(), DateUtil.DEFAULT_YYMM_FORMAT))));
        }
        Map<Integer, ProjectYieldConfigTotalModel> projectTotalMap = new LinkedHashMap<>();
        Integer projectId;
        for (ProjectYieldConfigInfoModel info : infoList) {
            projectId = info.getProjectId();
            if (!projectTotalMap.containsKey(projectId)) {
                projectTotalMap.put(projectId, ProjectYieldConfigTotalModel.build(projectId));
            }
            projectTotalMap.get(projectId).add(info);
        }
        aggModel.setProjectTotalMap(projectTotalMap);
        Map<CostEnum, TrialAggService> serviceMap = new HashMap<>();
        serviceMap.put(CostEnum.SALARY, projectRdEmployeeService);
        serviceMap.put(CostEnum.PROD, projectRdEquipmentService);
        serviceMap.put(CostEnum.STIMULUS, projectEnergyService);
        serviceMap.put(CostEnum.TRIAL_STIMULUS, projectEnergyService);
        serviceMap.put(CostEnum.IRON_STIMULUS, projectEnergyService);
        serviceMap.put(CostEnum.FUEL, projectEnergyService);
        serviceMap.put(CostEnum.IRON_FUEL, projectEnergyService);
        List<AggMsgModel> result = new ArrayList<>();
        CostEnum costEnum;
        for (Integer rdType : aggModel.getRdTypes()) {
            costEnum = CostEnum.getCostEnum(rdType);
            if (null == costEnum) {
                continue;
            }
            commonService.checkStatus(projectTotalMap.keySet(), DateUtil.getMonthFirstDay(aggModel.getMonth()), Arrays.asList(rdType), userInfo);
            if (!serviceMap.containsKey(costEnum)) {
                result.add(AggMsgModel.buildFail(MessageFormat.format("归集【{0}】失败，不支持归集该费用类型！", costEnum.getTitle())));
            }
            result.add(serviceMap.get(costEnum).aggRdTrialFee(aggModel, costEnum, userInfo));
        }
        return result;
    }

    @Override
    public RefreshYieldConfigModel getTrialConfig(Integer companyId) {
        return projectTrialConfigDao.getTrialConfig(companyId);
    }

    @Override
    public Map<String,List<String>> getDate(Integer projectId, Date month, Integer companyId) {
        List<Date> dates = projectYieldConfigDao.getDate(projectId, month, companyId);
        Map<String,List<String>> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(dates)) {
            SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
            for (Date d : dates) {
                list.add(sp.format(d));
            }
            Date emitTime = DateUtil.addDays(dates.get(0),-1);
            Date firstDay = DateUtil.getMonthFirstDay(emitTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(emitTime);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            while (true) {
                CompanyHolidayModel holiday = companyHolidayDao.getMonthHoliday(firstDay, companyId);
                Map<Integer,Boolean> holidayMap = ToolUtils.getHolidayMap(holiday, false, firstDay);
                Boolean hasWorkDay = getEmitDay(holidayMap, day,calendar);
                if (hasWorkDay) {
                    break;
                }
                firstDay = DateUtil.addMonths(firstDay, -1);
                calendar.setTime(DateUtil.getMonthLastDay(firstDay));
                day = calendar.get(Calendar.DAY_OF_MONTH);
            }
            map.put("trialDates", list);
            map.put("emitTime", Arrays.asList(sp.format(calendar.getTime())));
        }
        return map;
    }

    public Boolean getEmitDay(Map<Integer, Boolean> map, int day,Calendar calendar) {
        while (true) {
            if (map.containsKey(day)) {
                if (day > 1) {
                    day--;
                } else {
                    return false;
                }
            } else {
                calendar.set(Calendar.DAY_OF_MONTH,day);
                return true;
            }
        }
    }

    @Override
    public Boolean editSelected(UpdateSelectedModel model, UserInfo info) {
        ProjectYieldConfigEntity entity = new ProjectYieldConfigEntity();
        entity.buildUpdate(new Date(), info);
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            if (!CollectionUtils.isEmpty(model.getInactiveIds())) {
                projectYieldConfigDao.editSelected(model.getInactiveIds(), entity, 0);
            }
            if (!CollectionUtils.isEmpty(model.getActiveIds())) {
                projectYieldConfigDao.editSelected(model.getActiveIds(), entity, 1);
            }
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionUtils.rollback(transactionStatus);
            return false;
        }
    }

    @Override
    public Map<String, String> calculateDate(QueryYieldConfigModel model) {
        Map<String, String> dataMap = new HashMap<>(2);
        QueryYieldConfigModel queryModel = new QueryYieldConfigModel();
        queryModel.setProjectId(model.getProjectId());
        queryModel.setMonth(model.getMonth());
        List<Date> list = projectYieldConfigDao.getTrialDate(queryModel);
        if (!CollectionUtils.isEmpty(list)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            List<String> dates = new ArrayList<>();
            list.forEach(item -> {
                String format = dateFormat.format(item);
                dates.add(format);
            });
            dataMap.put("trialDates", StringUtils.join(dates, ","));
            Calendar cal = Calendar.getInstance();
            cal.setTime(list.get(0));
            cal.add(Calendar.DAY_OF_MONTH, -1);
            if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
                cal.add(Calendar.DAY_OF_MONTH, -2);
            } else if (cal.get(Calendar.DAY_OF_WEEK) == 7) {
                cal.add(Calendar.DAY_OF_MONTH, -1);
            }
            dataMap.put("emitTime", dateFormat.format(cal.getTime()));
        }
        return dataMap;
    }
}
