package com.yskc.rs.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUnit;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.JsonUtils;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.dao.CustomerAttendanceDao;
import com.yskc.rs.dao.ProjectDao;
import com.yskc.rs.dao.company.BackupDataDao;
import com.yskc.rs.dao.company.CompanyHolidayDao;
import com.yskc.rs.dao.project.ProjectAttendanceDao;
import com.yskc.rs.dao.project.ProjectAttendanceUsedDao;
import com.yskc.rs.dao.project.ProjectRdEmployeeDao;
import com.yskc.rs.dao.project.ProjectRdHourConfigDao;
import com.yskc.rs.entity.CustomerAttendanceEntity;
import com.yskc.rs.entity.company.BackupDataEntity;
import com.yskc.rs.entity.project.*;
import com.yskc.rs.enums.AuditRdTypeEnum;
import com.yskc.rs.enums.BackupTypeEnum;
import com.yskc.rs.enums.EmployeeTypeEnum;
import com.yskc.rs.models.CheckStatusModel;
import com.yskc.rs.models.TimeRangeModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.attendance.AttendanceModel;
import com.yskc.rs.models.company.CompanyHolidayModel;
import com.yskc.rs.models.excel.ProjectAttendanceExcel;
import com.yskc.rs.models.projectattendance.*;
import com.yskc.rs.models.rdhourconfig.RdHourConfigInfoModel;
import com.yskc.rs.service.ProjectAttendanceService;
import com.yskc.rs.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 研发人员费用
 *
 * @author huronghua
 */
@Service
public class ProjectAttendanceServiceImpl implements ProjectAttendanceService {

    @Autowired
    private ProjectAttendanceDao projectAttendanceDao;

    @Autowired
    private CustomerAttendanceDao customerAttendanceDao;

    @Autowired
    private ProjectAttendanceUsedDao projectAttendanceUsedDao;

    @Autowired
    private ProjectRdEmployeeDao projectRdEmployeeDao;

    @Autowired
    private CommonService commonService;

    @Autowired
    private CompanyHolidayDao companyHolidayDao;
    @Autowired
    private BackupDataDao backupDataDao;
    @Autowired
    private ProjectRdHourConfigDao projectRdHourConfigDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private RsConfig rsConfig;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public PageModel<List<ProjectHourAttendance>> getHourList(Integer companyId, QueryBatchEmployee query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            page.setDescs(CollUtil.newArrayList("rdHour - attendanceHour"));
        }
        String year = new SimpleDateFormat("yyyy").format(query.getMonth());
        List<ProjectHourAttendance> data = projectAttendanceDao.getHourList(page, query, year);
        loadInfo(data, companyId, query);
        return PageUtils.build(page, data);
    }

    @Override
    public Boolean refresh(UserInfo userInfo, RefreshAttendance refresh) throws OwnerException {
        Date monthBegin = DateUtil.getMonthFirstDay(refresh.getMonth());
        List<CheckStatusModel> models = new ArrayList<>();
        CheckStatusModel checkStatusModel = new CheckStatusModel();
        checkStatusModel.setMonth(monthBegin);
        checkStatusModel.setProjectId(refresh.getProjectId());
        models.add(checkStatusModel);
        List<Integer> rdTypes = new ArrayList<>();
        rdTypes.add(AuditRdTypeEnum.EMPLOYEE.getRdType());
        commonService.checkStatus(models, rdTypes, userInfo);
        boolean termFresh = null != refresh.getType();
        if (termFresh && CollectionUtils.isEmpty(refresh.getConfigs())) {
            throw new OwnerException("请添加研发工时配置。");
        }
        Date monthEnd = DateUtil.getMonthLastDay(refresh.getMonth());
        if (projectAttendanceDao.countOwner(userInfo.getCompanyId(), monthBegin, monthEnd) > 0) {
            throw new OwnerException("当月已存在小程序打卡记录。不可刷新研发考勤。");
        }
        List<ProjectAttendanceModel> data = projectAttendanceDao.getRdAttendanceList(refresh.getProjectId(), monthBegin, refresh.getIds());
        if (CollectionUtils.isEmpty(data)) {
            return true;
        }
        Date now = new Date();
        CompanyHolidayModel holiday = companyHolidayDao.getMonthHoliday(monthBegin, userInfo.getCompanyId());
        Map<Integer, Boolean> holidayMap = ToolUtils.getHolidayMap(holiday, refresh.getSkipHoliday(), monthBegin);
        Set<String> enumbers = new HashSet<>();
        data.forEach(item -> {
            enumbers.add(item.getEnumber());
        });
        String rdHourConfig = null;
        if (termFresh) {
            rdHourConfig = JsonUtils.objectToJson(refresh.getConfigs());
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction();
            deleteUsed(userInfo, enumbers, monthBegin, monthEnd, refresh.getProjectId());
            Map<String, Map<Integer, CustomerAttendanceEntity>> infoMap = initInfoMap(userInfo.getCompanyId(), refresh.getStartDay(), monthEnd, enumbers);
            Map<String, Map<Integer, ProjectAttendanceUsed>> usedMap = initUsedMap(userInfo.getCompanyId(), monthBegin, monthEnd, enumbers);
            List<ProjectRdEmployeeEntity> updateRdEmployees = new ArrayList<>();
            List<ProjectAttendance> rdAttendanceList = new ArrayList<>();
            if (termFresh) {
                termFresh(data, userInfo, refresh, now, infoMap, usedMap, holidayMap, updateRdEmployees, rdAttendanceList);
            } else {
                normalFresh(data, userInfo, refresh, now, infoMap, usedMap, holidayMap, updateRdEmployees, rdAttendanceList);
            }
            List<ProjectAttendanceUsed> usedInsertList = new ArrayList<>();
            List<ProjectAttendanceUsed> usedUpdateList = new ArrayList<>();
            usedMap.keySet().forEach(key ->
                    usedMap.get(key).values().forEach(item -> {
                        item.setUpdate(now, userInfo.getUserId(), userInfo.getMsUserId());
                        if (null != item.getId()) {
                            usedUpdateList.add(item);
                        } else {
                            item.setCreate(key, userInfo.getCompanyId());
                            usedInsertList.add(item);
                        }
                    }));
            if (!CollectionUtils.isEmpty(rdAttendanceList)) {
                List<List<ProjectAttendance>> tempInsert = ListUtils.subList(rdAttendanceList, Constant.MAX_INSERT_OR_UPDATE_COUNT);
                for (List<ProjectAttendance> list : tempInsert) {
                    projectAttendanceDao.insertBatch(list);
                }
                projectRdEmployeeDao.updateAttendances(updateRdEmployees);
            }
            if (!CollectionUtils.isEmpty(usedInsertList)) {
                List<List<ProjectAttendanceUsed>> tempUsedInsert = ListUtils.subList(usedInsertList, Constant.MAX_INSERT_OR_UPDATE_COUNT);
                for (List<ProjectAttendanceUsed> list : tempUsedInsert) {
                    projectAttendanceUsedDao.insertBatch(list);
                }
            }
            if (!CollectionUtils.isEmpty(usedUpdateList)) {
                List<List<ProjectAttendanceUsed>> tempUsedUpdate = ListUtils.subList(usedUpdateList, Constant.MAX_INSERT_OR_UPDATE_COUNT);
                for (List<ProjectAttendanceUsed> list : tempUsedUpdate) {
                    projectAttendanceUsedDao.updateBatch(list);
                }
            }
            TransactionUtils.commit(transactionStatus);
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException("刷新研发考勤失败，请联系管理员！");
        }
        if (!StringUtils.isEmpty(rdHourConfig)) {
            projectRdHourConfigDao.insertOrUpdate(ProjectRdHourConfig.build(rdHourConfig, userInfo, now, refresh.getProjectId(), monthBegin, refresh.getType()));
        }
        return true;
    }

    private void termFresh(List<ProjectAttendanceModel> data, UserInfo userInfo, RefreshAttendance refresh, Date now,
                           Map<String, Map<Integer, CustomerAttendanceEntity>> infoMap,
                           Map<String, Map<Integer, ProjectAttendanceUsed>> usedMap, Map<Integer, Boolean> holidayMap,
                           List<ProjectRdEmployeeEntity> updateRdEmployees, List<ProjectAttendance> rdAttendanceList) {
        Date month = refresh.getMonth();
        int maxDay = DateUtil.getMonthMaxDays(month);
        Integer projectId = refresh.getProjectId();
        List<RdHourConfigInfoModel> configs = refresh.getConfigs();
        configs.sort(Comparator.comparing(RdHourConfigInfoModel::getStartDay));
        for (int i = 0; i < configs.size(); i++) {
            RdHourConfigInfoModel item = configs.get(i);
            Integer endDay = maxDay;
            if (i + 1 < configs.size()) {
                endDay = Math.min(configs.get(i + 1).getStartDay() - 1, maxDay);
            }
            item.setEndDay(endDay);
        }
        data.forEach(item -> {
            String enumber = item.getEnumber();
            if (!usedMap.containsKey(enumber)) {
                usedMap.put(enumber, new HashMap<>());
            }
            List<ProjectAttendance> list = AttDataUtils.termDistribution(item.getRdHour(), month, infoMap.get(enumber),
                    usedMap.get(enumber), holidayMap, configs);
            BigDecimal total = BigDecimal.ZERO;
            for (ProjectAttendance attendance : list) {
                setAttendanceBase(attendance, now, userInfo, enumber, projectId);
                total = total.add(attendance.getWorkHour());
            }
            rdAttendanceList.addAll(list);
            updateRdEmployees.add(ProjectRdEmployeeEntity.build(userInfo, now, total, item.getId()));
        });
    }

    private void normalFresh(List<ProjectAttendanceModel> data, UserInfo userInfo, RefreshAttendance refresh,
                             Date now, Map<String, Map<Integer, CustomerAttendanceEntity>> infoMap,
                             Map<String, Map<Integer, ProjectAttendanceUsed>> usedMap, Map<Integer, Boolean> holidayMap,
                             List<ProjectRdEmployeeEntity> updateRdEmployees, List<ProjectAttendance> rdAttendanceList) {
        Date month = refresh.getMonth();
        int max = DateUtil.getMonthMaxDays(month);
        BigDecimal dayHour = refresh.getDayHour();
        Integer projectId = refresh.getProjectId();
        int startDay = cn.hutool.core.date.DateUtil.dayOfMonth(refresh.getStartDay());
        data.forEach(item -> {
            String enumber = item.getEnumber();
            if (!usedMap.containsKey(enumber)) {
                usedMap.put(enumber, new HashMap<>());
            }
            List<ProjectAttendance> list = AttDataUtils.distribution(item.getRdHour(), max, month, infoMap.get(enumber),
                    usedMap.get(enumber), holidayMap, startDay, dayHour);
            BigDecimal total = BigDecimal.ZERO;
            for (ProjectAttendance attendance : list) {
                setAttendanceBase(attendance, now, userInfo, enumber, projectId);
                total = total.add(attendance.getWorkHour());
            }
            rdAttendanceList.addAll(list);
            updateRdEmployees.add(ProjectRdEmployeeEntity.build(userInfo, now, total, item.getId()));
        });
    }

    @Override
    public Boolean saveData(UserInfo userInfo, BatchAttendanceUsedModel batch, boolean isImport) throws OwnerException {
        Date monthBegin = DateUtil.getMonthFirstDay(batch.getMonth());
        List<CheckStatusModel> models = new ArrayList<>();
        CheckStatusModel checkStatusModel = new CheckStatusModel();
        checkStatusModel.setMonth(monthBegin);
        checkStatusModel.setProjectId(batch.getProjectId());
        models.add(checkStatusModel);
        List<Integer> rdTypes = new ArrayList<>();
        rdTypes.add(AuditRdTypeEnum.EMPLOYEE.getRdType());
        commonService.checkStatus(models, rdTypes, userInfo);
        Date monthEnd = DateUtil.getMonthLastDay(batch.getMonth());
        if (projectAttendanceDao.countOwner(userInfo.getCompanyId(), monthBegin, monthEnd) > 0) {
            throw new OwnerException("当月已存在小程序打卡记录。不可更新研发考勤。");
        }
        Set<String> enumbers = new HashSet<>();
        List<Integer> ids = new ArrayList<>();
        batch.getList().forEach(item -> {
            if (null != item.getId()) {
                ids.add(item.getId());
            }
            enumbers.add(item.getEnumber());
        });
        Date now = new Date();
        List<ProjectRdEmployeeEntity> rdEmployees = projectRdEmployeeDao.getAtteByEnumbers(enumbers, batch.getProjectId(), monthBegin);
        Map<String, ProjectRdEmployeeEntity> rdEmployeeMap = new HashMap<>();
        rdEmployees.forEach(item -> {
            item.setAttendanceEdit(true);
            rdEmployeeMap.put(item.getEnumber(), item);
        });
        List<ProjectAttendance> baseList = CollectionUtils.isEmpty(ids) ? new ArrayList<>() : projectAttendanceDao.getByIds(ids);
        Map<Integer, ProjectAttendance> baseMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(baseList)) {
            baseList.forEach(item -> baseMap.put(item.getId(), item));
        }
        Map<String, Map<Integer, CustomerAttendanceEntity>> infoMap = initInfoMap(userInfo.getCompanyId(), monthBegin, monthEnd, enumbers);
        Map<String, Map<Integer, ProjectAttendanceUsed>> usedMap = initUsedMap(userInfo.getCompanyId(), monthBegin, monthEnd, enumbers);
        List<ProjectAttendance> insertList = new ArrayList<>(), updateList = new ArrayList<>();
        List<ProjectAttendanceUsed> insertUsedList = new ArrayList<>(), updateUsedList = new ArrayList<>();
        List<Integer> deleteIds = new ArrayList<>();
        Map<String, BigDecimal> enumberHourMap = new HashMap<>();
        for (BatchProjectHourAttendance item : batch.getList()) {
            String enumber = item.getEnumber();
            ProjectRdEmployeeEntity currentRdEmployee = rdEmployeeMap.get(enumber);
            if (currentRdEmployee == null) {
                throw new OwnerException("工号【" + enumber + "】未在当前项目归集工时，请检查");
            }
            enumberHourMap.put(enumber, enumberHourMap.getOrDefault(enumber, BigDecimal.ZERO).add(item.getWorkHour()));
            if (enumberHourMap.get(enumber).compareTo(currentRdEmployee.getRdHour()) > 0) {
                throw new OwnerException(MessageFormat.format("工号【{0}】研发考勤记录工时合计超过总研发工时【{1}】，{2}失败",
                        enumber, currentRdEmployee.getRdHour(), isImport ? "导入" : "保存"));
            }
            ProjectAttendance attendance = new ProjectAttendance();
            attendance.setId(item.getId());
            CustomerAttendanceEntity entity = null;
            ProjectAttendanceUsed used = new ProjectAttendanceUsed();
            Date currentDate = cn.hutool.core.date.DateUtil.offsetDay(batch.getMonth(), item.getIndex() - 1);
            attendance.setWorkDate(currentDate);
            if (infoMap.containsKey(enumber)) {
                entity = infoMap.get(enumber).get(item.getIndex());
            }
            if (usedMap.containsKey(enumber)) {
                used = usedMap.get(enumber).getOrDefault(item.getIndex(), ProjectAttendanceUsed.build(null, null, null, null));
            }
            used.setWorkDate(currentDate);
            ProjectAttendance baseAttendance = baseMap.getOrDefault(item.getId(), null);
            AttDataUtils.distributionOne(attendance, item.getWorkHour(), used, entity, baseAttendance, isImport);
            setAttendanceBase(attendance, now, userInfo, enumber, batch.getProjectId());
            BigDecimal attendanceHour = currentRdEmployee.getAttendanceHour();
            if (null != baseAttendance) {
                attendanceHour = attendanceHour.subtract(baseAttendance.getWorkHour());
            }
            attendanceHour = attendanceHour.add(attendance.getWorkHour());
            currentRdEmployee.setAttendanceHour(attendanceHour);
            if (attendance.getId() == null) {
                insertList.add(attendance);
            } else {
                if (attendance.getWorkHour().compareTo(BigDecimal.ZERO) <= 0) {
                    deleteIds.add(attendance.getId());
                } else {
                    updateList.add(attendance);
                }
            }
            used.setLastUpdateTime(now);
            used.setLastUpdatorId(userInfo.getId());
            used.setMsLastUpdatorId(userInfo.getMsUserId());
            if (used.getId() == null) {
                used.setEnumber(item.getEnumber());
                used.setCompanyId(userInfo.getCompanyId());
                used.setCreateTime(now);
                used.setCreatorId(userInfo.getId());
                used.setMsCreatorId(userInfo.getMsUserId());
                insertUsedList.add(used);
            } else {
                updateUsedList.add(used);
            }
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction();
            if (!CollectionUtils.isEmpty(deleteIds)) {
                projectAttendanceDao.deleteBatchIds(deleteIds);
            }
            if (!CollectionUtils.isEmpty(insertList)) {
                projectAttendanceDao.insertBatch(insertList);
            }
            if (!CollectionUtils.isEmpty(updateList)) {
                projectAttendanceDao.updateBatch(updateList);
            }
            if (!CollectionUtils.isEmpty(insertUsedList)) {
                projectAttendanceUsedDao.insertBatch(insertUsedList);
            }
            if (!CollectionUtils.isEmpty(updateUsedList)) {
                projectAttendanceUsedDao.updateBatch(updateUsedList);
            }
            projectRdEmployeeDao.updateAttendances(new ArrayList<>(rdEmployeeMap.values()));
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException("保存失败。请联系管理员！");
        }
    }

    @Override
    public void exportHourData(QueryBatchEmployee query, UserInfo info, OutputStream out) throws OwnerException {
        Date monthBegin = DateUtil.getMonthFirstDay(query.getMonth());
        Date monthEnd = DateUtil.getMonthLastDay(query.getMonth());
        Integer year = cn.hutool.core.date.DateUtil.year(query.getMonth());
        Date month = query.getMonth();
        int maxDay = cn.hutool.core.date.DateUtil.dayOfMonth(DateUtil.getMonthLastDay(month));
        List<ProjectAttendance> infoList = projectAttendanceDao.getExportData(monthBegin, monthEnd, info.getCompanyId(), query);
        List<ExportProjectAttendanceModel> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(infoList)) {
            List<BigDecimal> emptyList = new ArrayList<>();
            for (int i = 1; i <= maxDay; i++) {
                emptyList.add(null);
            }
            Set<String> enumbers = new HashSet<>();
            Map<String, List<BigDecimal>> enumberHoursMap = new HashMap<>();
            String keyFormat = "{0}_{1}";
            infoList.forEach(item -> {
                String key = MessageFormat.format(keyFormat, item.getProjectId(), item.getEnumber());
                if (!enumberHoursMap.containsKey(key)) {
                    enumberHoursMap.put(key, CollUtil.newArrayList(emptyList));
                }
                enumberHoursMap.get(key).set(cn.hutool.core.date.DateUtil.dayOfMonth(item.getWorkDate()) - 1, item.getWorkHour());
                enumbers.add(item.getEnumber());
            });
            list = projectRdEmployeeDao.getByEnumbers(info.getCompanyId(), enumbers, monthBegin, query.getAll(), query.getProjectId(), year, query.getEtype());
            list.forEach(item -> item.setHours(enumberHoursMap.getOrDefault(MessageFormat.format(keyFormat, item.getProjectId(), item.getEnumber()), emptyList)));
        }
        Map<String, Object> dataMap = new HashMap<>();
        List<Integer> cols = new ArrayList<>(maxDay);
        for (int i = 1; i <= maxDay; i++) {
            cols.add(i);
        }
        dataMap.put("cols", cols);
        dataMap.put("list", list);
        Map<Integer, List<Map<String, Object>>> data = new HashMap<>(1);
        data.put(0, CollUtil.newArrayList(dataMap));
        YsExcelUtils.generalMoreSheetsReport(data, rsConfig.getUploadConfig().getResourcePath() + Constant.TEMPLATE_DIR + "研发工时打卡记录.xlsx", (workbook) -> {
            if (workbook != null) {
                try {
                    YsExcelUtils.mergeCell(workbook, 0, 0, 0, 0, 6 + maxDay, info.getCompanyName(), true);
                    YsExcelUtils.mergeCell(workbook, 0, 1, 1, 0, 6 + maxDay, DateUtil.format(month, DateUtil.DEFAULT_YYMM_FORMAT) + "研发技术人员工作打卡记录表", true);
                    YsExcelUtils.setSheetName(workbook, null);
                    workbook.write(out);
                    workbook.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        });

    }

    @Override
    public PageModel<List<TimeAttendanceModel>> getTimeList(QueryBatchEmployee query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            page.setAscs(CollUtil.newArrayList("enumber"));
            page.setDescs(CollUtil.newArrayList("workDate"));
        }
        String year = new SimpleDateFormat("yyyy").format(query.getMonth());
        return PageUtils.build(page, projectAttendanceDao.getTimeList(page, query,
                DateUtil.getMonthFirstDay(query.getMonth()),
                DateUtil.getMonthLastDay(query.getMonth()), year));
    }

    @Override
    public List<TimeAttendanceModel> exportTimeData(QueryBatchEmployee query) {
        String year = new SimpleDateFormat("yyyy").format(query.getMonth());
        List<TimeAttendanceModel> timeAttendanceModelList = projectAttendanceDao.getTimeList(query, DateUtil.getMonthFirstDay(query.getMonth()),
                DateUtil.getMonthLastDay(query.getMonth()), year);
        timeAttendanceModelList.forEach(item -> {
            item.setEtypeName(EmployeeTypeEnum.getEmployeeTypeEnum(item.getEtype()).getType());
        });
        return timeAttendanceModelList;
    }

    @Override
    public PageModel<List<ProjectAllRdHourModel>> getAllRdHour(QueryAllRdHourModel query, Integer companyId) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(query.getProjectIds())) {
            return PageUtils.build(page, new ArrayList<>());
        }
        List<ProjectAllRdHourModel> rdHourModels = projectAttendanceDao.getForRdHour(page, query, companyId);
        if (!CollectionUtils.isEmpty(rdHourModels)) {
            setAllRdHour(rdHourModels, query.getMonth());
        }
        return PageUtils.build(page, rdHourModels);
    }


    @Override
    public PageModel<List<ProjectAllRdTimeModel>> getAllRdTime(QueryAllRdHourModel query, Integer companyId) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(query.getProjectIds())) {
            return PageUtils.build(page, new ArrayList<>());
        }
        return PageUtils.build(page, projectAttendanceDao.getAllRdTime(page, query, companyId));
    }

    @Override
    public List<ProjectAllRdHourModel> exportAllRdHour(QueryAllRdHourModel query, Integer companyId) {
        if (CollectionUtils.isEmpty(query.getProjectIds())) {
            return new ArrayList<>();
        }
        List<ProjectAllRdHourModel> rdHourModels = projectAttendanceDao.getForRdHour(query, companyId);
        if (!CollectionUtils.isEmpty(rdHourModels)) {
            setAllRdHour(rdHourModels, query.getMonth());
        }
        return rdHourModels;
    }

    @Override
    public void exportAllRdHour(QueryAllRdHourModel query, Integer companyId, OutputStream out, String path) throws OwnerException {
        List<ProjectAllRdHourModel> rdHourModels = projectAttendanceDao.getForRdHour(query, companyId);
        Map<String, Object> dataMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(rdHourModels)) {
            setAllRdHour(rdHourModels, query.getMonth());
        }
        dataMap.put("rdHourModels", rdHourModels);
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


    @Override
    public List<ProjectAllRdTimeModel> exportAllRdTime(QueryAllRdHourModel query, Integer companyId) {
        if (CollectionUtils.isEmpty(query.getProjectIds())) {
            return new ArrayList<>();
        }
        return projectAttendanceDao.getAllRdTime(query, companyId);
    }

    @Override
    public Boolean hasAttendance(Integer companyId) {
        return customerAttendanceDao.getLimitOneId(companyId) != null;
    }

    @Override
    public Boolean delData(UserInfo userInfo, List<AttendanceModel> model) throws OwnerException {
        if (CollectionUtils.isEmpty(model)) {
            return true;
        }
        List<ProjectAttendance> attendances = projectAttendanceDao.getAttendances(model);
        if (CollectionUtils.isEmpty(attendances)) {
            throw new OwnerException("所选考勤记录已删除或不存在，请核对数据");
        }
        List<BackupDataEntity> backupDatas = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();
        Integer deleteType = BackupTypeEnum.DELETE.getType();
        Date now = new Date();
        for (ProjectAttendance attendance : attendances) {
            ids.add(attendance.getId());
            String jsonData = JsonUtils.objectToJson(attendance);
            BackupDataEntity backupData = new BackupDataEntity(userInfo.getCompanyId(), "p_attendance", jsonData, userInfo.getUserId(), now, userInfo.getMsUserId(), deleteType);
            backupDatas.add(backupData);
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction();
            projectAttendanceDao.deleteBatchIds(ids);
            if (!CollectionUtils.isEmpty(backupDatas)) {
                backupDataDao.insertList(backupDatas);
            }
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException("保存失败。请联系管理员！");
        }

    }

    @Override
    public Boolean editAttendance(UserInfo userInfo, ProjectAttendance attendance) throws OwnerException {
        ProjectAttendance oldAttendance = projectAttendanceDao.selectById(attendance.getId());
        if (null == oldAttendance) {
            throw new OwnerException("不存在研发考勤，请联系管理员。");
        }
        if (!oldAttendance.getOwner()) {
            throw new OwnerException("不可编辑生成的研发工时。");
        }
        Date now = new Date();
        BackupDataEntity backupData = new BackupDataEntity(userInfo.getCompanyId(), "p_attendance",
                JsonUtils.objectToJson(oldAttendance), userInfo.getUserId(), now, userInfo.getMsUserId(), BackupTypeEnum.EDIT.getType());
        BigDecimal workTime = BigDecimal.ZERO;
        workTime = workTime.add(checkTime(attendance.getOnTime1(), attendance.getOffTime1(), attendance.getOnTime2(), 1));
        workTime = workTime.add(checkTime(attendance.getOnTime2(), attendance.getOffTime2(), attendance.getOnTime3(), 2));
        workTime = workTime.add(checkTime(attendance.getOnTime2(), attendance.getOffTime2(), null, 3));
        attendance.setWorkHour(workTime.divide(new BigDecimal(60), 2, BigDecimal.ROUND_HALF_UP));
        attendance.setLastUpdateTime(now);
        attendance.setLastUpdatorId(userInfo.getUserId());
        attendance.setMsLastUpdatorId(userInfo.getMsUserId());
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction();
            projectAttendanceDao.updateTime(attendance);
            backupDataDao.insert(backupData);
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException("保存失败。请联系管理员！");
        }
    }

    private BigDecimal checkTime(Date onTime, Date offTime, Date nextTime, Integer stage) throws OwnerException {
        if (offTime != null && onTime == null) {
            throw new OwnerException(MessageFormat.format("上班时间{0}不能为空", stage));
        }
        if (onTime != null && offTime != null) {
            if (onTime.compareTo(offTime) > 0) {
                throw new OwnerException(
                        MessageFormat.format("下班时间{2}【{0}】不能早于上班时间{2}【{1}】",
                                cn.hutool.core.date.DateUtil.format(onTime, "HH:mm"),
                                cn.hutool.core.date.DateUtil.format(offTime, "HH:mm"), stage)
                );
            }
            return new BigDecimal(cn.hutool.core.date.DateUtil.between(onTime, offTime, DateUnit.MINUTE));
        } else if (nextTime != null) {
            throw new OwnerException(MessageFormat.format("打卡时间段之间不能出现空缺。。", stage));
        }
        return BigDecimal.ZERO;
    }

    private void setAllRdHour(List<ProjectAllRdHourModel> rdHourModels, Date month) {
        Map<String, ProjectAllRdHourModel> map = new HashMap<>();
        Set<Integer> projectIds = new HashSet<>();
        rdHourModels.forEach(item -> {
            item.setInfo(new HashMap<>());
            projectIds.add(item.getProjectId());
            map.put(MessageFormat.format("{0}_{1}_{2}", item.getProjectId(), DateUtil.format(item.getMonth(), DateUtil.DEFAULT_YYMM_FORMAT), item.getEnumber()), item);
        });
        List<MiniRdHourModel> miniRdHourModels = projectAttendanceDao.getAllRdHour(projectIds,
                DateUtil.getMonthFirstDay(month), DateUtil.getMonthLastDay(month));
        if (!CollectionUtils.isEmpty(miniRdHourModels)) {
            miniRdHourModels.forEach(item -> {
                String key = MessageFormat.format("{0}_{1}_{2}", item.getProjectId(), DateUtil.format(item.getWorkDate(), DateUtil.DEFAULT_YYMM_FORMAT), item.getEnumber());
                if (map.containsKey(key)) {
                    map.get(key).getInfo().put(cn.hutool.core.date.DateUtil.dayOfMonth(item.getWorkDate()), new RdHourContentModel(item.getWorkHour(), item.getContent()));
                }
            });
        }
    }

    private void setAttendanceBase(ProjectAttendance attendance, Date now, UserInfo userInfo, String enumber, Integer projectId) {
        attendance.setCreateTime(now);
        attendance.setLastUpdateTime(now);
        attendance.setCreatorId(userInfo.getUserId());
        attendance.setLastUpdatorId(userInfo.getUserId());
        attendance.setMsLastUpdatorId(userInfo.getMsUserId());
        attendance.setMsCreatorId(userInfo.getMsUserId());
        attendance.setCompanyId(userInfo.getCompanyId());
        attendance.setProjectId(projectId);
        attendance.setEnumber(enumber);
    }

    private Map<String, Map<Integer, CustomerAttendanceEntity>> initInfoMap(Integer companyId, Date monthBegin, Date monthEnd, Set<String> enumbers) {
        List<CustomerAttendanceEntity> infoList = customerAttendanceDao.getAttendanceInfo(companyId, monthBegin, monthEnd, enumbers);
        Map<String, Map<Integer, CustomerAttendanceEntity>> infoMap = new LinkedHashMap<>();
        if (!CollectionUtils.isEmpty(infoList)) {
            infoList.forEach(item -> {
                if (!infoMap.containsKey(item.getEnumber())) {
                    infoMap.put(item.getEnumber(), new HashMap<>());
                }
                infoMap.get(item.getEnumber()).put(cn.hutool.core.date.DateUtil.dayOfMonth(item.getWorkDate()), item);
            });
        }
        return infoMap;
    }

    private Map<String, Map<Integer, ProjectAttendanceUsed>> initUsedMap(Integer companyId, Date monthBegin, Date monthEnd, Set<String> enumbers) {
        List<ProjectAttendanceUsed> usedList = projectAttendanceUsedDao.getByEnumbers(companyId, monthBegin, monthEnd, enumbers);
        Map<String, Map<Integer, ProjectAttendanceUsed>> usedMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(usedList)) {
            usedList.forEach(item -> {
                if (!usedMap.containsKey(item.getEnumber())) {
                    usedMap.put(item.getEnumber(), new HashMap<>());
                }
                usedMap.get(item.getEnumber()).put(cn.hutool.core.date.DateUtil.dayOfMonth(item.getWorkDate()), item);
            });
        }
        return usedMap;
    }

    /**
     * BatchProjectHourAttendance
     * 加载考勤详细信息
     *
     * @param data
     * @param companyId
     * @param query
     */
    private void loadInfo(List<ProjectHourAttendance> data, Integer companyId, QueryBatchEmployee query) {
        if (!CollectionUtils.isEmpty(data)) {
            Map<String, ProjectHourAttendance> enumberMap = new HashMap<>();
            data.forEach(item -> {
                item.setInfo(new HashMap<>());
                enumberMap.put(item.getEnumber(), item);
            });
            List<String> enumbers = new ArrayList<>(enumberMap.keySet());
            Integer projectId = query.getProjectId();
            Date monthBegin = DateUtil.getMonthFirstDay(query.getMonth());
            Date monthEnd = DateUtil.getMonthLastDay(query.getMonth());
            int maxDay = DateUtil.getMonthMaxDays(monthEnd);
            List<BatchProjectHourAttendance> rdAttendanceList = projectAttendanceDao.getRdAttendanceInfoList(
                    projectId, enumbers, monthBegin, monthEnd);
            List<BatchProjectHourAttendance> infoList = customerAttendanceDao.getRdAttendanceInfo(companyId, enumbers, monthBegin, monthEnd);
            List<BatchProjectHourAttendance> usedList = projectAttendanceUsedDao.getRdAttendanceUsed(companyId, enumbers, monthBegin, monthEnd);
            Map<String, Map<Integer, BatchProjectHourAttendance>> infoMap = initAttendanceMap(infoList);
            Map<String, Map<Integer, BatchProjectHourAttendance>> usedMap = initAttendanceMap(usedList);
            Map<String, Map<Integer, BatchProjectHourAttendance>> attendanceMap = initAttendanceMap(rdAttendanceList);
            if (CollectionUtils.isEmpty(infoMap)) {
                for (String enumber : enumberMap.keySet()) {
                    Map<Integer, BatchProjectHourAttendance> currentMap = enumberMap.get(enumber).getInfo();
                    Map<Integer, BatchProjectHourAttendance> usedDayMap = usedMap.getOrDefault(enumber, new HashMap<>());
                    Map<Integer, BatchProjectHourAttendance> attendanceDayMap = attendanceMap.getOrDefault(enumber, new HashMap<>());
                    for (int i = 1; i <= maxDay; i++) {
                        initAttendanceUsed(currentMap.getOrDefault(i, new BatchProjectHourAttendance()),
                                currentMap, i, attendanceDayMap, usedDayMap, true);
                    }
                }
            } else {
                for (String enumber : enumberMap.keySet()) {
                    Map<Integer, BatchProjectHourAttendance> infoDayMap = infoMap.getOrDefault(enumber, new HashMap<>());
                    Boolean defaultRemain = CollectionUtils.isEmpty(infoDayMap);
                    Map<Integer, BatchProjectHourAttendance> currentMap = enumberMap.get(enumber).getInfo();
                    Map<Integer, BatchProjectHourAttendance> usedDayMap = usedMap.getOrDefault(enumber, new HashMap<>());
                    Map<Integer, BatchProjectHourAttendance> attendanceDayMap = attendanceMap.getOrDefault(enumber, new HashMap<>());
                    for (int i = 1; i <= maxDay; i++) {
                        BatchProjectHourAttendance info = currentMap.getOrDefault(i, new BatchProjectHourAttendance());
                        if (infoDayMap.containsKey(i)) {
                            info.setBaseHours(infoDayMap.get(i).getBaseHours());
                        }
                        initAttendanceUsed(info, currentMap, i, attendanceDayMap, usedDayMap, defaultRemain);
                    }
                }
            }
        }
    }

    private void initAttendanceUsed(BatchProjectHourAttendance info, Map<Integer, BatchProjectHourAttendance> currentMap, Integer key,
                                    Map<Integer, BatchProjectHourAttendance> attendanceDayMap,
                                    Map<Integer, BatchProjectHourAttendance> usedDayMap, Boolean defaultRemain) {
        BigDecimal remain = BigDecimal.ZERO;
        if (usedDayMap.containsKey(key)) {
            remain = usedDayMap.get(key).getRemainHours();
        } else if (defaultRemain) {
            remain = AttDataUtils.DEFAULT_MAX_HOUR;
        }
        if (attendanceDayMap.containsKey(key)) {
            BatchProjectHourAttendance attendance = attendanceDayMap.get(key);
            info.setWorkHour(attendance.getWorkHour());
            remain = remain.add(info.getWorkHour());
            info.setId(attendance.getId());
        }
        info.setRemainHours(remain);
        info.setIndex(key);
        currentMap.put(key, info);
    }

    private Map<String, Map<Integer, BatchProjectHourAttendance>> initAttendanceMap(List<BatchProjectHourAttendance> data) {
        Map<String, Map<Integer, BatchProjectHourAttendance>> result = new HashMap<>();
        if (!CollectionUtils.isEmpty(data)) {
            data.forEach(item -> {
                if (!result.containsKey(item.getEnumber())) {
                    result.put(item.getEnumber(), new HashMap<>());
                }
                result.get(item.getEnumber()).put(cn.hutool.core.date.DateUtil.dayOfMonth(item.getWorkDate()), item);
            });
        }
        return result;
    }

    @Override
    public void deleteUsed(UserInfo info, Set<String> enumbers, Date monthBegin, Date monthEnd, Integer projectId) {
        List<BatchProjectAttendance> list = projectAttendanceDao.getUseless(enumbers, monthBegin, monthEnd, projectId);
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        Date now = new Date();
        Map<Integer, ProjectAttendanceUsed> usedMap = new HashMap<>();
        List<Integer> ids = new ArrayList<>();
        List<Integer> delUsedIds = new ArrayList<>();
        BigDecimal totalHour;
        for (BatchProjectAttendance item : list) {
            ids.add(item.getId());
            ProjectAttendanceUsed used = usedMap.getOrDefault(item.getUsedId(), ProjectAttendanceUsed.build(item.getUsedId(), item.getRemainHours(), item.getUsedWorkHour(), item.getTimeRange()));
            List<TimeRangeModel> rangeList = new ArrayList<>();
            if (item.getOnTime1() != null) {
                rangeList.add(new TimeRangeModel(item.getOnTime1(), item.getOffTime1()));
            }
            if (item.getOffTime2() != null) {
                rangeList.add(new TimeRangeModel(item.getOnTime2(), item.getOffTime2()));
            }
            if (item.getOffTime3() != null) {
                rangeList.add(new TimeRangeModel(item.getOnTime3(), item.getOffTime3()));
            }
            String timeRange = AttDataUtils.removeRange(used.getTimeRange(), rangeList);
            if (StringUtils.isEmpty(timeRange)) {
                delUsedIds.add(used.getId());
            } else {
                totalHour = item.getWorkHour().add(item.getRemainHours());
                if (totalHour.compareTo(item.getUsedWorkHour()) > 0) {
                    totalHour = item.getUsedWorkHour();
                }
                used.setRemainHours(totalHour);
                used.setTimeRange(timeRange);
                used.setLastUpdateTime(now);
                used.setMsLastUpdatorId(info.getMsUserId());
                used.setLastUpdatorId(info.getUserId());
                usedMap.put(used.getId(), used);
            }
        }
        if (!CollectionUtils.isEmpty(usedMap.values())) {
            projectAttendanceUsedDao.updateBatch(new ArrayList<>(usedMap.values()));
        }
        if (!CollectionUtils.isEmpty(delUsedIds)) {
            projectAttendanceUsedDao.deleteBatchIds(delUsedIds);
        }
        projectAttendanceDao.deleteBatchIds(ids);
    }

    @Override
    public void importRdAttendance(UserInfo userInfo, List<ProjectAttendanceExcel> data, Date month) throws OwnerException {
        Date finalMonth = DateUtil.getMonthFirstDay(month);
        Set<String> enumbers = new HashSet<>();
        Map<String, List<ProjectAttendanceExcel>> projectDataMap = new LinkedHashMap<>();
        data.forEach(item -> {
            ToolUtils.putAndAdd(projectDataMap, item.getRdTitle(), item);
            enumbers.add(item.getEnumber());
        });
        Integer companyId = userInfo.getCompanyId();
        Set<String> rdTitles = projectDataMap.keySet();
        List<ProjectEntity> projects = projectDao.getByRdTitles(rdTitles, companyId);
        if (CollectionUtils.isEmpty(projects)) {
            throw new OwnerException("导入数据的所有RD不存在或已删除，请检查后导入！");
        }
        if (projects.size() < rdTitles.size()) {
            throw new OwnerException(MessageFormat.format("RD【{0}】不存在，请检查。",
                    String.join(",", CollUtil.disjunction(projects.stream().map(ProjectEntity::getRdTitle).collect(Collectors.toList()), rdTitles))));
        }
        Map<String, ProjectEntity> projectMap = projects.stream().collect(Collectors.toMap(ProjectEntity::getRdTitle, b -> b, (o, n) -> n));
        List<BatchProjectHourAttendance> usedData = projectAttendanceDao.getUsedData(enumbers, companyId, finalMonth, DateUtil.getMonthLastDay(finalMonth));
        Map<String, BigDecimal> remainMap = new HashMap<>();
        Map<String, BatchProjectHourAttendance> exsitMap = new HashMap<>();
        String format = "{0}_{1}";
        if (!CollectionUtils.isEmpty(usedData)) {
            usedData.forEach(a -> {
                // 存放
                String key = MessageFormat.format(format, a.getEnumber(), DateUtil.format(a.getWorkDate(), DateUtil.DEFAULT_DATE_FORMAT));
                if (!remainMap.containsKey(key)) {
                    remainMap.put(key, a.getRemainHours());
                }
                if (a.getId() != null) {
                    exsitMap.put(MessageFormat.format(format, key, a.getProjectId()), a);
                }
            });
        }
        List<BatchAttendanceUsedModel> batchList = new ArrayList<>();
        Integer maxDay = DateUtil.getMonthMaxDays(finalMonth), curProjectId, curId;
        String ENUMBER = "enumber", ENAME = "ename", RDTITLE = "rdTitle", usedKey, enumber;
        Date curBegin, curEnd;
        BigDecimal curRemain;
        for (String rdTitle : projectDataMap.keySet()) {
            ProjectEntity curProject = projectMap.get(rdTitle);
            curProjectId = curProject.getId();
            curBegin = curProject.getBeginDate();
            curEnd = curProject.getEndDate();
            List<BatchProjectHourAttendance> list = new ArrayList<>();
            for (ProjectAttendanceExcel item : projectDataMap.get(rdTitle)) {
                Map<String, Object> map = BeanUtil.beanToMap(item);
                enumber = item.getEnumber();
                for (String col : map.keySet()) {
                    if (col.equals(ENUMBER) || col.equals(ENAME) || col.equals(RDTITLE)) {
                        continue;
                    }
                    int index = Integer.parseInt(col.replace("d", ""));
                    Object tmp = map.get(col);
                    BigDecimal workHour = null == tmp ? BigDecimal.ZERO : (BigDecimal) tmp;
                    if (workHour.compareTo(AttDataUtils.DAY_HOUR) > 0) {
                        throw new OwnerException(MessageFormat.format("RD【{1}】，工号【{2}】，【{3}】日，研发工时最大不能超过【{0}】小时，请检查。",
                                AttDataUtils.DAY_HOUR.toString(), rdTitle, enumber, index));
                    }
                    if (workHour.compareTo(BigDecimal.ZERO) < 0) {
                        throw new OwnerException(MessageFormat.format("RD【{1}】，工号【{2}】，【{3}】日，研发工时不能小于0，请检查。",
                                AttDataUtils.DAY_HOUR.toString(), rdTitle, enumber, index));
                    }
                    if (index > maxDay) {
                        continue;
                    }
                    Date workDate = cn.hutool.core.date.DateUtil.offsetDay(finalMonth, index - 1);
                    if (curBegin.compareTo(workDate) > 0 || curEnd.compareTo(workDate) < 0) {
                        throw new OwnerException(MessageFormat.format("RD【{0}】，工号【{1}】，研发日期【{2}】不存在项目周期【{3}~{4}】中，请检查。",
                                rdTitle, item.getEnumber(), DateUtil.format(workDate, DateUtil.DEFAULT_DATE_FORMAT),
                                DateUtil.format(curBegin, DateUtil.DEFAULT_DATE_FORMAT), DateUtil.format(curEnd, DateUtil.DEFAULT_DATE_FORMAT)));
                    }
                    usedKey = MessageFormat.format(format, enumber, DateUtil.format(workDate, DateUtil.DEFAULT_DATE_FORMAT));
                    curRemain = remainMap.getOrDefault(usedKey, AttDataUtils.DAY_HOUR);
                    BatchProjectHourAttendance oldItem = exsitMap.get(MessageFormat.format(format, usedKey, curProjectId));
                    curId = null;
                    if (null != oldItem) {
                        curId = oldItem.getId();
                        curRemain = curRemain.add(oldItem.getWorkHour());
                    }
                    if (workHour.compareTo(curRemain) > 0) {
                        throw new OwnerException(MessageFormat.format("RD【{4}】，工号【{0}】，【{1}】日分配工时【{2}】超过可分配工时【{3}】，请检查。",
                                enumber, index, workHour, curRemain.setScale(2, BigDecimal.ROUND_HALF_UP).toString(),
                                rdTitle));
                    }
                    if (workHour.compareTo(BigDecimal.ZERO) > 0 || curId != null) {
                        if (index > maxDay) {
                            throw new OwnerException(MessageFormat.format("当前导入月不存在【{0}】日，请检查", index));
                        }
                        list.add(new BatchProjectHourAttendance(curId, enumber, workDate, workHour, index));
                        remainMap.put(usedKey, curRemain.subtract(workHour));
                    }
                }
            }
            if (!CollectionUtils.isEmpty(list)) {
                BatchAttendanceUsedModel batch = new BatchAttendanceUsedModel();
                batch.setMonth(finalMonth);
                batch.setProjectId(curProjectId);
                batch.setList(list);
                batchList.add(batch);
            }
        }
        for (BatchAttendanceUsedModel batch : batchList) {
            saveData(userInfo, batch, true);
        }
    }
}
