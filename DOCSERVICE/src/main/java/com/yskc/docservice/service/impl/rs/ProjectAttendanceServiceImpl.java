package com.yskc.docservice.service.impl.rs;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.DateUtil;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.dao.rs.CustomerAttendanceDao;
import com.yskc.docservice.dao.rs.project.ProjectAttendanceDao;
import com.yskc.docservice.dao.rs.project.ProjectAttendanceUsedDao;
import com.yskc.docservice.dao.rs.project.ProjectDao;
import com.yskc.docservice.dao.rs.project.ProjectRdEmployeeDao;
import com.yskc.docservice.entity.rs.CustomerAttendanceEntity;
import com.yskc.docservice.entity.rs.project.ProjectAttendance;
import com.yskc.docservice.entity.rs.project.ProjectAttendanceUsed;
import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.entity.rs.project.ProjectRdEmployeeEntity;
import com.yskc.docservice.enums.AuditRdTypeEnum;
import com.yskc.docservice.models.rs.*;
import com.yskc.docservice.models.rs.excel.ProjectAttendanceExcel;
import com.yskc.docservice.models.rs.projectattendance.BatchProjectAttendance;
import com.yskc.docservice.service.rs.ProjectAttendanceService;
import com.yskc.docservice.utils.AttDataUtils;
import com.yskc.docservice.utils.ListUtils;
import com.yskc.docservice.utils.ToolUtils;
import com.yskc.docservice.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 研发人员费用
 *
 * @author huronghua
 */
@Service
public class ProjectAttendanceServiceImpl implements ProjectAttendanceService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProjectAttendanceDao projectAttendanceDao;
    @Autowired
    private ProjectAttendanceUsedDao projectAttendanceUsedDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private ProjectRdEmployeeDao projectRdEmployeeDao;
    @Autowired
    private CommonService commonService;
    @Autowired
    private CustomerAttendanceDao customerAttendanceDao;


    @Override
    public void deleteUsed(RsUserInfo info, Set<String> enumbers, Date monthBegin, Date monthEnd, Integer projectId) {
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
            Date offTime1 = item.getOffTime1();
            if (null == offTime1) {
                offTime1 = AttDataUtils.DEFAULT_OFF_TIME3;
            }
            rangeList.add(new TimeRangeModel(item.getOnTime1(), offTime1));
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
    public void importRdAttendance(RsUserInfo userInfo, List<ProjectAttendanceExcel> data, Date month) throws OwnerException {
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
        Set<Integer> projectIds = projects.stream().map(ProjectEntity::getId).collect(Collectors.toSet());
        List<Integer> rdTypes = Arrays.asList(AuditRdTypeEnum.EMPLOYEE.getRdType());
        commonService.checkStatus(projectIds, finalMonth, rdTypes, userInfo);
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
                        throw new OwnerException(MessageFormat.format("RD【{0}】，工号【{1}】，【{2}】日，研发工时不能小于0，请检查。",
                                rdTitle, enumber, index));
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

    public Boolean saveData(RsUserInfo userInfo, BatchAttendanceUsedModel batch, boolean isImport) throws OwnerException {
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
            item.setAttendanceHour(BigDecimal.ZERO);
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
            AttDataUtils.distributionOne(attendance, item.getWorkHour(), used, entity, baseMap.getOrDefault(item.getId(), null), isImport);
            setAttendanceBase(attendance, now, userInfo, enumber, batch.getProjectId());
            currentRdEmployee.setAttendanceHour(currentRdEmployee.getAttendanceHour().add(attendance.getWorkHour()));
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
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
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
            for (List<ProjectRdEmployeeEntity> list : ListUtils.subList(new ArrayList<>(rdEmployeeMap.values()), Constant.MAX_INSERT_OR_UPDATE_COUNT)) {
                projectRdEmployeeDao.updateAttendances(list);
            }
            TransactionUtils.commit(DataSourceEnum.RS, transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.RS, transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException("保存失败。请联系管理员！");
        }
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

    private void setAttendanceBase(ProjectAttendance attendance, Date now, RsUserInfo userInfo, String enumber, Integer projectId) {
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
}
