package com.yskc.rs.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.dao.ProjectDao;
import com.yskc.rs.dao.project.ProjectFinaScheduleDao;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.entity.project.ProjectFinaSchedule;
import com.yskc.rs.enums.CostEnum;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.ProjectFinaScheduleExcel;
import com.yskc.rs.models.projectfinaschedule.ProjectFinaScheduleAggModel;
import com.yskc.rs.models.projectfinaschedule.ProjectFinaScheduleModel;
import com.yskc.rs.models.projectfinaschedule.QueryProjectFinaScheduleModel;
import com.yskc.rs.service.ProjectEnergyService;
import com.yskc.rs.service.ProjectFinaScheduleService;
import com.yskc.rs.service.ProjectRdEquipmentService;
import com.yskc.rs.utils.ListUtils;
import com.yskc.rs.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: zhangdingfu
 * @CreateTime: 2021-06-17 14:19
 * @Description: 项目财务排期表业务实现层
 */
@Service
public class ProjectFinaScheduleServiceImpl implements ProjectFinaScheduleService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProjectFinaScheduleDao projectFinaScheduleDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private ProjectRdEquipmentService projectRdEquipmentService;
    @Autowired
    private ProjectEnergyService projectEnergyService;

    @Override
    public PageModel<List<ProjectFinaScheduleModel>> getList(QueryProjectFinaScheduleModel query, Integer companyId) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            page.setDescs(CollUtil.newArrayList("pf.month", "p.rdTitle"));
        }
        List<ProjectFinaScheduleModel> list = projectFinaScheduleDao.getList(page, query, companyId);
        return PageUtils.build(page, list);
    }

    @Override
    public Boolean save(List<ProjectFinaSchedule> list, UserInfo userInfo) throws OwnerException {
        List<Integer> deleteIds = new ArrayList<>();
        List<ProjectFinaSchedule> data = new ArrayList<>();
        Integer companyId = userInfo.getCompanyId(), userId = userInfo.getUserId(), msUserId = userInfo.getMsUserId();
        Date now = new Date();
        list.forEach(item -> {
            if ((null == item.getTestHour() || item.getTestHour().compareTo(BigDecimal.ZERO) == 0) &&
                    (null == item.getTrialHour() || item.getTrialHour().compareTo(BigDecimal.ZERO) == 0) &&
                    (null == item.getWorkHours() || item.getWorkHours().compareTo(BigDecimal.ZERO) == 0)) {
                if (null != item.getId()) {
                    deleteIds.add(item.getId());
                }
                return;
            }
            data.add(item);
            item.setCompanyId(companyId);
            item.create(userId, msUserId, now);
        });

        return save(data, deleteIds);
    }

    @Override
    public Boolean importFinaSchedule(UserInfo info, List<ProjectFinaScheduleExcel> data) throws OwnerException {
        if (CollectionUtils.isEmpty(data)) {
            throw new OwnerException("未获取到任何数据，请检查表格是否存在数据，上传模板是否正确。");
        }
        Set<String> rdTitles = data.stream().map(ProjectFinaScheduleExcel::getRdTitle).collect(Collectors.toSet());
        Integer companyId = info.getCompanyId();
        List<ProjectEntity> projects = projectDao.getByRdTitles(rdTitles, companyId);
        if (CollectionUtils.isEmpty(projects)) {
            throw new OwnerException("导入的RD全部不存在，请检查。");
        }
        Map<String, ProjectEntity> rdTitleMap = projects.stream().collect(Collectors.toMap(ProjectEntity::getRdTitle, b -> {
            b.setBeginDate(DateUtil.getMonthFirstDay(b.getBeginDate()));
            b.setEndDate(DateUtil.getMonthLastDay(b.getEndDate()));
            return b;
        }, (o, n) -> n));
        Collection<String> noRdTitles = CollUtil.disjunction(rdTitleMap.keySet(), rdTitles);
        if (!CollectionUtils.isEmpty(noRdTitles)) {
            throw new OwnerException("RD【" + String.join(",", noRdTitles) + "】不存在，请检查。");
        }
        List<ProjectFinaScheduleExcel> deleteList = new ArrayList<>();
        List<ProjectFinaSchedule> dataList = new ArrayList<>();
        Date now = new Date();
        Integer userId = info.getUserId(), msUserId = info.getMsUserId();
        for (ProjectFinaScheduleExcel item : data) {
            Date currentMonth = DateUtil.getMonthFirstDay(item.getMonth());
            ProjectEntity currentProject = rdTitleMap.get(item.getRdTitle());
            if (currentProject.getBeginDate().compareTo(currentMonth) > 0 ||
                    currentProject.getEndDate().compareTo(currentMonth) < 0) {
                throw new OwnerException(MessageFormat.format("月份【{0}】不存在【{1}】的项目周期【{2}-{3}】内。",
                        DateUtil.format(currentMonth, DateUtil.DEFAULT_YYMM_FORMAT), currentProject.getRdTitle(),
                        DateUtil.format(currentProject.getBeginDate(), DateUtil.DEFAULT_YYMM_FORMAT),
                        DateUtil.format(currentProject.getEndDate(), DateUtil.DEFAULT_YYMM_FORMAT)));
            }
            if ((null == item.getTestHour() || item.getTestHour().compareTo(BigDecimal.ZERO) == 0) &&
                    (null == item.getTrialHour() || item.getTrialHour().compareTo(BigDecimal.ZERO) == 0) &&
                    (null == item.getWorkHours() || item.getWorkHours().compareTo(BigDecimal.ZERO) == 0)) {
                item.setProjectId(currentProject.getId());
                item.setMonth(currentMonth);
                deleteList.add(item);
                continue;
            }
            dataList.add(ProjectFinaSchedule.build(item.getTestHour(), item.getTrialHour(), item.getWorkHours(), currentMonth,
                    currentProject.getId(), companyId, now, userId, msUserId, item.getDeptName()));
        }
        return save(dataList, CollectionUtils.isEmpty(deleteList) ? null : projectFinaScheduleDao.getExistIds(deleteList, companyId));
    }

    @Override
    public Map<String, Object> getExportFinaSchedule(QueryProjectFinaScheduleModel query, Integer companyId) {
        List<ProjectFinaScheduleModel> list = projectFinaScheduleDao.getData(query, companyId);
        Map<String, Object> result = new HashMap<>();
        Map<String, Map<String, Object>> workHourMap = new LinkedHashMap<>();
        Map<String, Map<String, Object>> testHourMap = new LinkedHashMap<>();
        Map<String, Map<String, Object>> trialHourMap = new LinkedHashMap<>();
        Map<String, Object> workHourTotal = new HashMap<>();
        workHourTotal.put("rdTitle", "合计");
        Map<String, Object> testHourTotal = new HashMap<>();
        testHourTotal.put("rdTitle", "合计");
        Map<String, Object> trialHourTotal = new HashMap<>();
        trialHourTotal.put("rdTitle", "合计");
        String keyFormat = "{0}_{1}_{2}", currentKey, m;
        for (ProjectFinaScheduleModel item : list) {
            m = (cn.hutool.core.date.DateUtil.month(item.getMonth()) + 1) + "";
            currentKey = MessageFormat.format(keyFormat, item.getProjectId(), m, item.getDeptName());
            // 任意一个HourMap存在currentKey，则所有HourMap都存在该key
            if (!workHourMap.containsKey(currentKey)) {
                Map<String, Object> baseMap = new HashMap<>();
                baseMap.put("rdTitle", item.getRdTitle());
                baseMap.put("eas", item.getEas());
                baseMap.put("panme", item.getPname());
                baseMap.put("deptName", item.getDeptName());
                Map<String, Object> curWorkHour = new HashMap<>();
                Map<String, Object> curTestHour = new HashMap<>();
                Map<String, Object> curTrialHour = new HashMap<>();
                curWorkHour.putAll(baseMap);
                curTestHour.putAll(baseMap);
                curTrialHour.putAll(baseMap);
                workHourMap.put(currentKey, curWorkHour);
                testHourMap.put(currentKey, curTestHour);
                trialHourMap.put(currentKey, curTrialHour);
            }
            BigDecimal workHour = item.getWorkHours(), testHour = item.getTestHour(), trialHour = item.getTrialHour();
            putHour(m, workHour, workHourMap.get(currentKey), workHourTotal);
            putHour(m, testHour, testHourMap.get(currentKey), testHourTotal);
            putHour(m, trialHour, trialHourMap.get(currentKey), trialHourTotal);
        }
        workHourMap.put("total", workHourTotal);
        testHourMap.put("total", testHourTotal);
        trialHourMap.put("total", trialHourTotal);
        result.put("workHourList", workHourMap.values());
        result.put("testHourList", testHourMap.values());
        result.put("trialHourList", trialHourMap.values());
        return result;
    }

    @Override
    public Boolean aggFee(ProjectFinaScheduleAggModel agg, UserInfo userInfo) throws OwnerException {
        Date month = DateUtil.getMonthFirstDay(agg.getMonth());
        List<ProjectFinaScheduleModel> hours = projectFinaScheduleDao.getByMonth(month, userInfo.getCompanyId());
        if (CollectionUtils.isEmpty(hours)) {
            throw new OwnerException("不存在动力试验/试制实际工时，归集失败。");
        }
        for (Integer type : agg.getTypes()) {
            switch (type) {
                case 0:
                    projectRdEquipmentService.aggByFinaScheduleHours(hours.stream().collect(Collectors.toMap(ProjectFinaScheduleModel::getProjectId, b -> b, (o, n) -> n)), month, userInfo);
                    break;
                default:
                    String keyFormat = "{0}_{1}";
                    Integer energyType = null;
                    if (type == 1) {
                        energyType = CostEnum.STIMULUS.getType();
                    }else if(type == 2){
                        energyType = CostEnum.FUEL.getType();
                    }
                    if(null != energyType){
                        projectEnergyService.aggByFinaScheduleHours(hours.stream().collect(Collectors.toMap(
                                a->MessageFormat.format(keyFormat,a.getProjectId(),a.getDeptName()), b -> b, (o, n) -> n)),
                                month, userInfo,energyType,keyFormat);
                    }
                    break;
            }
        }
        return true;
    }

    @Override
    public Boolean del(List<Integer> ids) throws OwnerException {
        if (CollectionUtils.isEmpty(ids)) {
            throw new OwnerException("删除失败，请稍后再试。");
        }
        return projectFinaScheduleDao.deleteBatchIds(ids) > 0;
    }

    @Override
    public Boolean add(ProjectFinaSchedule entity, UserInfo userInfo) throws OwnerException {
        Integer companyId = userInfo.getCompanyId();
        Integer sameId = projectFinaScheduleDao.getSameDataId(companyId, entity.getProjectId(), entity.getMonth(), entity.getDeptName());
        if (null != sameId) {
            throw new OwnerException("系统已存在相同RD，月份，部门的数据。请检查。");
        }
        entity.setCompanyId(companyId);
        entity.create(userInfo.getUserId(), userInfo.getMsUserId(), new Date());
        return projectFinaScheduleDao.insert(entity) > 0;
    }

    @Override
    public Boolean edit(ProjectFinaSchedule entity, UserInfo userInfo) throws OwnerException {
        Integer companyId = userInfo.getCompanyId();
        Integer sameId = projectFinaScheduleDao.getSameDataId(companyId, entity.getProjectId(), entity.getMonth(), entity.getDeptName());
        if (null != sameId && !sameId.equals(entity.getId())) {
            throw new OwnerException("系统已存在相同RD、月份、部门的数据。请检查。");
        }
        entity.setCompanyId(companyId);
        entity.update(userInfo.getUserId(), userInfo.getMsUserId(), new Date());
        return projectFinaScheduleDao.updateEntity(entity) > 0;
    }

    private Boolean save(List<ProjectFinaSchedule> data, List<Integer> deleteIds) throws OwnerException {
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction();
            if (!CollectionUtils.isEmpty(data)) {
                for (List<ProjectFinaSchedule> currentList : ListUtils.subList(data, Constant.MAX_INSERT_OR_UPDATE_COUNT)) {
                    projectFinaScheduleDao.insertOrUpdate(currentList);
                }
            }
            if (!CollectionUtils.isEmpty(deleteIds)) {
                projectFinaScheduleDao.deleteBatchIds(deleteIds);
            }
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException("保存失败");
        }

    }

    private void putHour(String monthStr, BigDecimal hour, Map<String, Object> hourMap, Map<String, Object> totalMap) {
        if (hour != null) {
            hourMap.put(monthStr, hour);
            totalMap.put(monthStr, hour.add((BigDecimal) totalMap.getOrDefault(monthStr, BigDecimal.ZERO)));
            hourMap.put("total", hour.add((BigDecimal) hourMap.getOrDefault("total", BigDecimal.ZERO)));
            totalMap.put("total", hour.add((BigDecimal) totalMap.getOrDefault("total", BigDecimal.ZERO)));
        }

    }
}
