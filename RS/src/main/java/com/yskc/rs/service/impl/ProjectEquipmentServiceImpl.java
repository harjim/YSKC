package com.yskc.rs.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.JsonUtils;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.dao.company.CompanyHolidayDao;
import com.yskc.rs.dao.data.DataEquipmentDao;
import com.yskc.rs.dao.project.EquipmentUsedDao;
import com.yskc.rs.dao.project.ProjectAttendanceDao;
import com.yskc.rs.dao.project.ProjectEquipmentDao;
import com.yskc.rs.dao.project.ProjectRdHourConfigDao;
import com.yskc.rs.entity.project.EquipmentUsedEntity;
import com.yskc.rs.entity.project.ProjectEquipmentEntity;
import com.yskc.rs.entity.project.ProjectRdHourConfig;
import com.yskc.rs.enums.AuditRdTypeEnum;
import com.yskc.rs.models.CheckStatusModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.company.CompanyHolidayModel;
import com.yskc.rs.models.data.DataEquipmentInfoModel;
import com.yskc.rs.models.projectattendance.RefreshAttendance;
import com.yskc.rs.models.projectequipment.BatchEquipmentModel;
import com.yskc.rs.models.projectequipment.ExportProjectEquipmentModel;
import com.yskc.rs.models.projectequipment.ProjectEquipmentModel;
import com.yskc.rs.models.projectequipment.QueryProjectEquipmentModel;
import com.yskc.rs.models.rdhourconfig.RdHourConfigInfoModel;
import com.yskc.rs.service.ProjectEquipmentService;
import com.yskc.rs.utils.AttDataUtils;
import com.yskc.rs.utils.ToolUtils;
import com.yskc.rs.utils.TransactionUtils;
import com.yskc.rs.utils.YsExcelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-17 15:06
 * @Description: 设备研发记录业务实现层
 */
@Service
public class ProjectEquipmentServiceImpl implements ProjectEquipmentService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProjectEquipmentDao projectEquipmentDao;

    @Autowired
    private EquipmentUsedDao equipmentUsedDao;

    @Autowired
    private DataEquipmentDao dataEquipmentDao;

    @Autowired
    private ProjectAttendanceDao projectAttendanceDao;

    @Autowired
    private CommonService commonService;
    @Autowired
    private CompanyHolidayDao companyHolidayDao;

    @Autowired
    private ProjectRdHourConfigDao projectRdHourConfigDao;
    @Autowired
    private RsConfig rsConfig;

    @Override
    public PageModel<List<ProjectEquipmentModel>> getList(Integer companyId, QueryProjectEquipmentModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            page.setDescs(CollUtil.newArrayList("pe.workHours is null", "pre.rdHour - pe.workHours"));
        }
        List<ProjectEquipmentModel> data = projectEquipmentDao.getList(page, query);
        loadInfo(companyId, data, query);
        return PageUtils.build(page, data);
    }

    @Override
    public Boolean refresh(UserInfo userInfo, RefreshAttendance fresh) throws OwnerException {
        Date monthBegin = DateUtil.getMonthFirstDay(fresh.getMonth());
        Integer projectId = fresh.getProjectId();
        CheckStatusModel statusModel = new CheckStatusModel(projectId, monthBegin);
        commonService.checkStatus(Arrays.asList(statusModel), Arrays.asList(AuditRdTypeEnum.EQUIPMENT.getRdType()), userInfo);
        boolean termFresh = null != fresh.getType();
        if (termFresh && CollectionUtils.isEmpty(fresh.getConfigs())) {
            throw new OwnerException("请添加研发工时配置。");
        }
        List<ProjectEquipmentModel> rdEquipmentList = projectEquipmentDao.getRdEquipment(projectId, monthBegin, fresh.getIds());
        if (CollectionUtils.isEmpty(rdEquipmentList)) {
            return true;
        }
        CompanyHolidayModel holiday = companyHolidayDao.getMonthHoliday(monthBegin, userInfo.getCompanyId());
        Map<Integer, Boolean> holidayMap = ToolUtils.getHolidayMap(holiday, fresh.getSkipHoliday(), monthBegin);
        List<Integer> deleteIds = new ArrayList<>();
        List<String> ecodes = rdEquipmentList.stream().map(ProjectEquipmentModel::getEcode).collect(Collectors.toList());
        List<EquipmentUsedEntity> usedList = equipmentUsedDao.getByEcodes(userInfo.getCompanyId(), monthBegin, ecodes);
        // 若rdEquipment不为空，该查询不为空
        List<DataEquipmentInfoModel> dataEquipmentList = dataEquipmentDao.getByEcodes(userInfo.getCompanyId(), monthBegin, ecodes, false);
        List<Date> attDates = projectAttendanceDao.getAttData(projectId, monthBegin, DateUtil.getMonthLastDay(fresh.getMonth()));
        Map<Integer, Boolean> attDayMap = new LinkedHashMap<>();
        if (!CollectionUtils.isEmpty(attDates)) {
            attDates.forEach(item -> attDayMap.put(cn.hutool.core.date.DateUtil.dayOfMonth(item), true));
        }
        Map<String, DataEquipmentInfoModel> dataEquipmentMap = new HashMap<>();
        dataEquipmentList.forEach(item -> dataEquipmentMap.put(item.getEcode(), item));
        Map<String, EquipmentUsedEntity> usedMap = new LinkedHashMap<>();
        if (!CollectionUtils.isEmpty(usedList)) {
            usedList.forEach(item -> usedMap.put(item.getEcode(), item));
        }
        initDeleteInfo(monthBegin, projectId, ecodes, deleteIds, usedMap);
        Date now = new Date();
        List<ProjectEquipmentEntity> insertList = new ArrayList<>();
        List<EquipmentUsedEntity> usedInsert = new ArrayList<>();
        List<EquipmentUsedEntity> usedUpdate = new ArrayList<>();
        String rdHourConfig = null;
        if (termFresh) {
            rdHourConfig = JsonUtils.objectToJson(fresh.getConfigs());
            termFresh(rdEquipmentList, dataEquipmentMap, usedMap, attDayMap, now, holidayMap, monthBegin, projectId,
                    fresh, userInfo, insertList, usedInsert, usedUpdate);
        } else {
            normalFresh(rdEquipmentList, dataEquipmentMap, usedMap, attDayMap, now, holidayMap, monthBegin, projectId,
                    fresh, userInfo, insertList, usedInsert, usedUpdate);
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction();
            if (!CollectionUtils.isEmpty(deleteIds)) {
                projectEquipmentDao.deleteBatchIds(deleteIds);
            }
            if (!CollectionUtils.isEmpty(insertList)) {
                projectEquipmentDao.insertBatch(insertList);
            }
            if (!CollectionUtils.isEmpty(usedInsert)) {
                equipmentUsedDao.insertBatch(usedInsert);
            }
            if (!CollectionUtils.isEmpty(usedUpdate)) {
                equipmentUsedDao.updateBatch(usedUpdate);
            }
            if (!StringUtils.isEmpty(rdHourConfig)) {
                projectRdHourConfigDao.insertOrUpdate(ProjectRdHourConfig.build(rdHourConfig, userInfo, now, projectId, monthBegin, fresh.getType()));
            }
            TransactionUtils.commit(transactionStatus);
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException("刷新研发工时失败，请联系管理员！");
        }
        return true;
    }

    /**
     * 间隔
     *
     * @param rdEquipmentList
     * @param dataEquipmentMap
     * @param usedMap
     * @param attDayMap
     * @param now
     * @param holidayMap
     * @param monthBegin
     * @param projectId
     * @param fresh
     * @param userInfo
     * @param insertList
     * @param usedInsert
     * @param usedUpdate
     */
    private void termFresh(List<ProjectEquipmentModel> rdEquipmentList, Map<String, DataEquipmentInfoModel> dataEquipmentMap,
                           Map<String, EquipmentUsedEntity> usedMap, Map<Integer, Boolean> attDayMap, Date now,
                           Map<Integer, Boolean> holidayMap, Date monthBegin, Integer projectId, RefreshAttendance fresh,
                           UserInfo userInfo, List<ProjectEquipmentEntity> insertList, List<EquipmentUsedEntity> usedInsert,
                           List<EquipmentUsedEntity> usedUpdate) {
        int maxDay = com.yskc.common.utils.DateUtil.getMonthMaxDays(monthBegin);
        Map<Integer, RdHourConfigInfoModel> termDayMap = new LinkedHashMap<>();
        List<RdHourConfigInfoModel> configs = fresh.getConfigs();
        configs.sort(Comparator.comparing(RdHourConfigInfoModel::getStartDay));
        for (int i = 0; i < configs.size(); i++) {
            RdHourConfigInfoModel item = configs.get(i);
            Integer startDay = item.getStartDay();
            Integer endDay = maxDay;
            if (i + 1 < configs.size()) {
                endDay = configs.get(i + 1).getStartDay() - 1;
            }
            for (Integer day = startDay; day <= endDay; day++) {
                termDayMap.put(day, item);
            }
        }

        boolean noRepeat = fresh.getNoRepeat();
        rdEquipmentList.forEach(item -> {
            String key = item.getEcode();
            if (dataEquipmentMap.containsKey(key)) {
                DataEquipmentInfoModel d = dataEquipmentMap.get(key);
                EquipmentUsedEntity used = usedMap.getOrDefault(key, EquipmentUsedEntity.buildUsed(key, d.getWorkHours(), d.getWorkHours(), Constant.DEFAULT_HOUR_ATT_DATA));
                termDayMap.values().forEach(RdHourConfigInfoModel::resetHour);
                String equData = AttDataUtils.termDistribution(item.getRdHour(), holidayMap, used.getUsedEquData(), termDayMap, attDayMap, noRepeat);
                ProjectEquipmentEntity entity = ProjectEquipmentEntity.build(now, key, equData, monthBegin, projectId, userInfo, AttDataUtils.getTotal(equData), d.getId());
                insertList.add(entity);
                BigDecimal sub = used.getRemainHours().subtract(entity.getWorkHours());
                used.setUpdate(sub.compareTo(BigDecimal.ZERO) <= 0 ? BigDecimal.ZERO : sub,
                        AttDataUtils.addAttData(used.getUsedEquData(), entity.getEquData()), now, userInfo.getId(), userInfo.getMsUserId());
                if (null != used.getId()) {
                    usedUpdate.add(used);
                } else {
                    used.setCreate(monthBegin, userInfo.getCompanyId());
                    usedInsert.add(used);
                }
            }
        });
    }

    /**
     * 连续
     *
     * @param rdEquipmentList
     * @param dataEquipmentMap
     * @param usedMap
     * @param attDayMap
     * @param now
     * @param holidayMap
     * @param monthBegin
     * @param projectId
     * @param fresh
     * @param userInfo
     * @param insertList
     * @param usedInsert
     * @param usedUpdate
     */
    private void normalFresh(List<ProjectEquipmentModel> rdEquipmentList, Map<String, DataEquipmentInfoModel> dataEquipmentMap,
                             Map<String, EquipmentUsedEntity> usedMap, Map<Integer, Boolean> attDayMap, Date now,
                             Map<Integer, Boolean> holidayMap, Date monthBegin, Integer projectId, RefreshAttendance fresh,
                             UserInfo userInfo, List<ProjectEquipmentEntity> insertList,
                             List<EquipmentUsedEntity> usedInsert, List<EquipmentUsedEntity> usedUpdate) {
        Integer maxDay = DateUtil.getMonthMaxDays(monthBegin);
        BigDecimal dayHour = fresh.getDayHour();
        int startDay = cn.hutool.core.date.DateUtil.dayOfMonth(fresh.getStartDay());
        boolean noRepeat = fresh.getNoRepeat();
        rdEquipmentList.forEach(item -> {
            String key = item.getEcode();
            if (dataEquipmentMap.containsKey(key)) {
                DataEquipmentInfoModel d = dataEquipmentMap.get(key);
                EquipmentUsedEntity used = usedMap.getOrDefault(key, EquipmentUsedEntity.buildUsed(key, d.getWorkHours(), d.getWorkHours(), Constant.DEFAULT_HOUR_ATT_DATA));
                String equData = AttDataUtils.distribution(item.getRdHour(), holidayMap, used.getUsedEquData(), maxDay,
                        dayHour, startDay, attDayMap, noRepeat);
                ProjectEquipmentEntity entity = ProjectEquipmentEntity.build(now, key, equData, monthBegin, projectId, userInfo, AttDataUtils.getTotal(equData), d.getId());
                insertList.add(entity);
                BigDecimal sub = used.getRemainHours().subtract(entity.getWorkHours());
                used.setUpdate(sub.compareTo(BigDecimal.ZERO) <= 0 ? BigDecimal.ZERO : sub,
                        AttDataUtils.addAttData(used.getUsedEquData(), entity.getEquData()), now, userInfo.getId(), userInfo.getMsUserId());
                if (null != used.getId()) {
                    usedUpdate.add(used);
                } else {
                    used.setCreate(monthBegin, userInfo.getCompanyId());
                    usedInsert.add(used);
                }
            }
        });
    }

    @Override
    public Boolean saveList(UserInfo userInfo, BatchEquipmentModel batch) throws OwnerException {
        CheckStatusModel statusModel = new CheckStatusModel(batch.getProjectId(), batch.getMonth());
        commonService.checkStatus(Arrays.asList(statusModel), Arrays.asList(AuditRdTypeEnum.EQUIPMENT.getRdType()), userInfo);
        List<ProjectEquipmentModel> data = batch.getList();
        if (CollectionUtils.isEmpty(data)) {
            throw new OwnerException("无可保存的设备研发使用记录。");
        }
        Date month = DateUtil.getMonthFirstDay(batch.getMonth());
        List<String> ecodes = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();
        data.forEach(item -> {
            ids.add(item.getPeId());
            ecodes.add(item.getEcode());
        });

        List<EquipmentUsedEntity> usedList = equipmentUsedDao.getByEcodes(userInfo.getCompanyId(), month, ecodes);
        Map<String, EquipmentUsedEntity> usedMap = new HashMap<>();
        usedList.forEach(item -> usedMap.put(item.getEcode(), item));
        initEditInfo(ids, usedMap);
        Date now = new Date();
        List<ProjectEquipmentEntity> updateList = new ArrayList<>();
        data.forEach(item -> {
            if (usedMap.containsKey(item.getEcode())) {
                ProjectEquipmentEntity equipmentEntity = new ProjectEquipmentEntity();
                EquipmentUsedEntity used = usedMap.get(item.getEcode());
                BigDecimal workHours = AttDataUtils.getTotal(item.getEquData());
                equipmentEntity.setWorkHours(workHours);
                equipmentEntity.setId(item.getPeId());
                equipmentEntity.setEquData(item.getEquData());
                equipmentEntity.setLastUpdatorId(userInfo.getId());
                equipmentEntity.setMsLastUpdatorId(userInfo.getMsUserId());
                equipmentEntity.setLastUpdateTime(now);
                updateList.add(equipmentEntity);
                BigDecimal sub = used.getRemainHours().subtract(workHours);
                used.setRemainHours(sub.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : sub);
                used.setUsedEquData(AttDataUtils.addAttData(used.getUsedEquData(), item.getEquData()));
                used.setLastUpdatorId(userInfo.getId());
                used.setMsLastUpdatorId(userInfo.getMsUserId());
                used.setLastUpdateTime(now);
            }
        });
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction();
            projectEquipmentDao.updateBatch(updateList);
            equipmentUsedDao.updateBatch(new ArrayList<>(usedMap.values()));
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException("保存设备研发使用记录失败，请联系管理员！");
        }
    }

    @Override
    public void exportData(UserInfo info, QueryProjectEquipmentModel query, OutputStream out) {
        List<ExportProjectEquipmentModel> list = projectEquipmentDao.getExportData(query, info.getCompanyId());
        Date month = query.getMonth();
        int maxDay = cn.hutool.core.date.DateUtil.dayOfMonth(DateUtil.getMonthLastDay(month));
        List<Integer> cols = new ArrayList<>(maxDay);
        List<BigDecimal> emptyList = new ArrayList<>();
        for (int i = 1; i <= maxDay; i++) {
            cols.add(i);
            emptyList.add(null);
        }
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(item -> {
                if (StringUtils.isEmpty(item.getEquData())) {
                    item.setHours(emptyList);
                    return;
                }
                List<BigDecimal> hours = new ArrayList<>(maxDay);
                String[] equArr = item.getEquData().split(",");
                int equLength = equArr.length;
                BigDecimal hour;
                for (int i = 0; i < maxDay; i++) {
                    hours.add(i >= equLength || (hour = new BigDecimal(equArr[i])).compareTo(BigDecimal.ZERO) <= 0 ? null : hour);
                }
                item.setHours(hours);
            });
        }
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("cols", cols);
        dataMap.put("list", list);
        Map<Integer, List<Map<String, Object>>> data = new HashMap<>(1);
        data.put(0, CollUtil.newArrayList(dataMap));
        YsExcelUtils.generalMoreSheetsReport(data, rsConfig.getUploadConfig().getResourcePath() + Constant.TEMPLATE_DIR + "研发设备工作记录.xlsx", (workbook) -> {
            if (workbook != null) {
                try {
                    YsExcelUtils.mergeCell(workbook, 0, 0, 0, 0, 4 + maxDay, info.getCompanyName(), true);
                    YsExcelUtils.mergeCell(workbook, 0, 1, 1, 0, 4 + maxDay, DateUtil.format(month, DateUtil.DEFAULT_YYMM_FORMAT) + "研发设备工作记录表", true);
                    YsExcelUtils.setSheetName(workbook,null);
                    workbook.write(out);
                    workbook.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        });
    }

    @Override
    public Map<Integer, Integer> getAttData(QueryProjectEquipmentModel query) {
        Date monthBegin = DateUtil.getMonthFirstDay(query.getMonth());
        Date monthEnd = DateUtil.getMonthLastDay(query.getMonth());
        List<Date> list = projectAttendanceDao.getAttData(query.getProjectId(), monthBegin, monthEnd);
        if (!CollectionUtils.isEmpty(list)) {
            Map<Integer, Integer> result = new HashMap<>();
            list.forEach(item -> {
                result.put(cn.hutool.core.date.DateUtil.dayOfMonth(item), 1);
            });
            return result;
        }
        return null;
    }

    @Override
    public String getRdHourConfig(ProjectRdHourConfig rdHourConfig, Integer companyId) {
        return projectRdHourConfigDao.getRdHourConfig(rdHourConfig, companyId);
    }

    /**
     * 加载研发设备使用记录
     *
     * @param data
     */
    private void loadInfo(Integer companyId, List<ProjectEquipmentModel> data, QueryProjectEquipmentModel query) {
        Date month = query.getMonth();
        if (!CollectionUtils.isEmpty(data)) {
            List<String> ecodes = new ArrayList<>();
            data.forEach(item -> ecodes.add(item.getEcode()));
            List<EquipmentUsedEntity> usedList = equipmentUsedDao.getByEcodes(companyId, month, ecodes);
            if (!CollectionUtils.isEmpty(usedList)) {
                Map<String, EquipmentUsedEntity> usedMap = new HashMap<>();
                usedList.forEach(item -> usedMap.put(item.getEcode(), item));
                data.forEach(d -> {
                    if (usedMap.containsKey(d.getEcode())) {
                        EquipmentUsedEntity used = usedMap.get(d.getEcode());
                        d.setUsedEquData(used.getUsedEquData());
                        d.setRemainHours(used.getRemainHours());
                    }
                });
            }
        }
    }

    private void initDeleteInfo(Date month, Integer projectId, List<String> ecodes, List<Integer> deleteIds, Map<String, EquipmentUsedEntity> usedMap) {
        List<ProjectEquipmentModel> projectEquipmentList = projectEquipmentDao.getByEcodes(projectId, month, ecodes);
        if (!CollectionUtils.isEmpty(projectEquipmentList)) {
            projectEquipmentList.forEach(item -> {
                deleteIds.add(item.getId());
                if (usedMap.containsKey(item.getEcode())) {
                    EquipmentUsedEntity used = usedMap.get(item.getEcode());
                    String sub = AttDataUtils.subAttData(used.getUsedEquData(), item.getEquData());
                    used.setUsedEquData(sub);
                    BigDecimal total = used.getRemainHours().add(item.getWorkHours());
                    if (total.compareTo(used.getWorkHours()) > 0) {
                        total = used.getWorkHours();
                    }
                    used.setRemainHours(total);
                }
            });
        }
    }

    /**
     * 初始usedMap（used.usedEquData - ProjectEquipmentEntity.equData）
     *
     * @param usedMap
     */
    private void initEditInfo(List<Integer> ids, Map<String, EquipmentUsedEntity> usedMap) {
        List<ProjectEquipmentEntity> baseList = projectEquipmentDao.getByIds(ids);
        baseList.forEach(item -> {
            if (usedMap.containsKey(item.getEcode())) {
                EquipmentUsedEntity used = usedMap.get(item.getEcode());
                used.setUsedEquData(AttDataUtils.subAttData(used.getUsedEquData(), item.getEquData()));
                BigDecimal total = used.getRemainHours().add(item.getWorkHours());
                used.setRemainHours(total.compareTo(used.getWorkHours()) > 0 ? used.getWorkHours() : total);
            }
        });
    }

}
