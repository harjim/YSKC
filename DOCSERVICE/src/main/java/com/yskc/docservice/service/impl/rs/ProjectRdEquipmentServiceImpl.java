package com.yskc.docservice.service.impl.rs;

import cn.hutool.core.collection.CollUtil;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.DateUtil;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.dao.rs.EquipmentUsedDao;
import com.yskc.docservice.dao.rs.RdEquipmentDao;
import com.yskc.docservice.dao.rs.SummaryDao;
import com.yskc.docservice.dao.rs.init.InitEquipmentDao;
import com.yskc.docservice.dao.rs.project.ProjectDao;
import com.yskc.docservice.dao.rs.project.ProjectEquipmentDao;
import com.yskc.docservice.dao.rs.project.ProjectRdEquipmentDao;
import com.yskc.docservice.entity.rs.SummaryEntity;
import com.yskc.docservice.entity.rs.project.EquipmentUsedEntity;
import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.entity.rs.project.ProjectEquipmentEntity;
import com.yskc.docservice.entity.rs.project.ProjectRdEquipmentEntity;
import com.yskc.docservice.enums.CostEnum;
import com.yskc.docservice.models.rs.CheckStatusModel;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.projectrdequipment.BatchProjectRdEquipmentModel;
import com.yskc.docservice.models.rs.projectrdequipment.ProjectRdEquipmentModel;
import com.yskc.docservice.models.rs.projectrdequipment.ProjectRdEquipmentTotalModel;
import com.yskc.docservice.service.rs.*;
import com.yskc.docservice.utils.AttDataUtils;
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
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: zhangdingfu
 * @CreateTime: 2020-04-01 11:02
 * @Description: 项目设备研发折旧业务实现层
 */
@Service
public class ProjectRdEquipmentServiceImpl implements ProjectRdEquipmentService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProjectRdEquipmentDao projectRdEquipmentDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private InitEquipmentDao initEquipmentDao;
    @Autowired
    private ProjectEquipmentDao projectEquipmentDao;
    @Autowired
    private EquipmentUsedDao equipmentUsedDao;
    @Autowired
    private CompanySettingService companySettingService;
    @Autowired
    private SummaryDao summaryDao;
    @Autowired
    private CommonService commonService;
    @Autowired
    private RdEquipmentDao rdEquipmentDao;

    @Override
    public Boolean importRdHour(RsUserInfo info, BatchProjectRdEquipmentModel batchModel, int year) throws OwnerException {
        List<ProjectRdEquipmentModel> dataList = batchModel.getList();
        if (CollectionUtils.isEmpty(dataList)) {
            throw new OwnerException("未导入任何数据。");
        }
        Integer projectId = batchModel.getProjectId();
        Map<String, ProjectRdEquipmentModel> monthDataMap = new LinkedHashMap<>();
        String keyFormat = "{0}_{1}";
        Set<String> ecodes = new HashSet<>();
        Set<Date> months = new HashSet<>();
        checkAndLoadMonthData(monthDataMap, ecodes, months, projectId, dataList, year, keyFormat);
        List<Integer> rdTypes  = rdEquipmentDao.getEtype(info.getCompanyId(), year, ecodes);
        commonService.checkStatus(projectId,months,rdTypes,info);
        int hourBit = companySettingService.getEquipmentHourBit(info.getCompanyId());
        List<String> existInit = initEquipmentDao.getByEcodes(info.getCompanyId(), projectId, ecodes, year);
        Collection<String> initDisjunction = CollUtil.disjunction(ecodes, existInit);
        if (!CollectionUtils.isEmpty(initDisjunction)) {
            throw new OwnerException(MessageFormat.format("资产清单不存在资产代码为：【{0}】 的设备。", String.join(",", initDisjunction)));
        }
        List<ProjectRdEquipmentModel> dataEquipments = projectRdEquipmentDao.getUsedSum(info.getCompanyId(), projectId, months,ecodes);
        Map<String, ProjectRdEquipmentModel> dataEquipmentMap = dataEquipments.stream().filter(a -> a != null).collect(
                Collectors.toMap(
                        a -> MessageFormat.format(keyFormat, DateUtil.format(a.getMonth(), DateUtil.DEFAULT_YYMM_FORMAT), a.getEcode()),
                        b -> b, (e, n) -> n));
        List<ProjectRdEquipmentEntity> oldRdEquipments = projectRdEquipmentDao.getEcodeId(projectId, months,ecodes);
        Map<String, Integer> oldRdEquipmentMap = oldRdEquipments.stream().collect(Collectors.toMap(
                a -> MessageFormat.format(keyFormat, DateUtil.format(a.getMonth(), DateUtil.DEFAULT_YYMM_FORMAT), a.getEcode()),
                b -> b.getId(), (e, n) -> n));
        for (String key : monthDataMap.keySet()) {
            ProjectRdEquipmentModel current = monthDataMap.get(key);
            ProjectRdEquipmentModel dataEquipment = dataEquipmentMap.get(key);
            if (null == dataEquipment) {
                throw new OwnerException(MessageFormat.format("不存在【{0}】月，资产代码为【{1}】的设备使用。",
                        key.substring(0, 7), current.getEcode()));
            }
            current.setProjectId(projectId);
            BigDecimal curRdHour = current.getRdHourByBit(hourBit);
            if (curRdHour.compareTo(BigDecimal.ZERO) < 0) {
                throw new OwnerException(MessageFormat.format(
                        "月份【{3}】、资产代码【{1}】、设备名称【{2}】，分配工时【{0,number,#.#}】不能小于0。", curRdHour,
                        current.getEcode(), current.getEname(), key.substring(0, 7)));
            }
            if (dataEquipment.getRemainHour() == null) {
                dataEquipment.setRemainHour(dataEquipment.getWorkHours());
            }
            if (curRdHour.compareTo(dataEquipment.getRemainHour()) > 0) {
                throw new OwnerException(MessageFormat.format(
                        "月份【{4}】、资产代码【{2}】、设备名称【{3}】,分配工时【{0,number,#.#}】超过最大可分配工时【{1,number,#.#}】。",
                        curRdHour, dataEquipment.getRemainHour(), current.getEcode(), current.getEname(), key.substring(0, 7)));
            }
            BigDecimal ratio = BigDecimal.ZERO;
            if (dataEquipment.getWorkHours().compareTo(BigDecimal.ZERO) > 0) {
                ratio = curRdHour.divide(dataEquipment.getWorkHours(), Constant.MAX_SCALE, BigDecimal.ROUND_HALF_UP);
            }
            current.setRdDepreciation(ratio.multiply(dataEquipment.getDepreciation()).setScale(4, BigDecimal.ROUND_HALF_UP));
            current.setId(oldRdEquipmentMap.get(key));
        }
        return saveData(new ArrayList<>(months), CollUtil.newArrayList(projectId), new ArrayList<>(monthDataMap.values()), info, hourBit);
    }


    private Boolean saveData(List<Date> months, List<Integer> projectIds, List<ProjectRdEquipmentModel> data, RsUserInfo userInfo, Integer hourBit) throws OwnerException {
        Date now = new Date();
        List<ProjectRdEquipmentEntity> insertOrUpdateList = new ArrayList<>();
        List<Integer> deleteIds = new ArrayList<>();
        Map<Integer, List<ProjectRdEquipmentModel>> deleteMap = new HashMap<>();
        Integer userId = userInfo.getUserId();
        Integer msUserId = userInfo.getMsUserId();
        Integer companyId = userInfo.getCompanyId();
        data.forEach(item -> {
            if (null == item.getRdHour() || item.getRdHour().compareTo(BigDecimal.ZERO) == 0) {
                if (null != item.getId()) {
                    deleteIds.add(item.getId());
                    if (null == item.getMonth()) {
                        item.setMonth(months.get(0));
                    }
                    ToolUtils.putAndAdd(deleteMap, item.getProjectId(), item);
                }
                return;
            }
            ProjectRdEquipmentEntity entity = ProjectRdEquipmentEntity.build(now, userId, msUserId, companyId, item, hourBit);
            insertOrUpdateList.add(entity);
        });
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
        try {
            if (!CollectionUtils.isEmpty(insertOrUpdateList)) {
                projectRdEquipmentDao.insertOrUpdate(insertOrUpdateList);
            }
            if (!CollectionUtils.isEmpty(deleteMap)) {
                projectRdEquipmentDao.deleteBatchIds(deleteIds);
                for (Integer projectId : deleteMap.keySet()) {
                    deleteProjectEquipment(deleteMap.get(projectId), projectId, userInfo, now);
                }
            }
            insertSummary(now, projectIds, months, userInfo);
            TransactionUtils.commit(DataSourceEnum.RS,transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.RS,transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException("保存失败");
        }
    }

    private void deleteProjectEquipment(List<ProjectRdEquipmentModel> deleteList, Integer projectId, RsUserInfo info, Date now) {
        List<ProjectEquipmentEntity> list = projectEquipmentDao.getProjectEquipments(deleteList, projectId);
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        List<EquipmentUsedEntity> usedList = equipmentUsedDao.getUseless(info.getCompanyId(), list);
        boolean hasUsed = !CollectionUtils.isEmpty(usedList);
        if (hasUsed) {
            Map<String, String> equipmentEquDataMap = new HashMap<>();
            String keyFormat = "{0}_{1}";

            Integer userId = info.getUserId();
            Integer msUserId = info.getMsUserId();
            list.forEach(item -> equipmentEquDataMap.put(MessageFormat.format(keyFormat, DateUtil.format(item.getMonth(), DateUtil.DEFAULT_YYMM_FORMAT), item.getEcode()), item.getEquData()));
            usedList.forEach(item -> {
                String equData = equipmentEquDataMap.get(MessageFormat.format(keyFormat, DateUtil.format(item.getMonth(), DateUtil.DEFAULT_YYMM_FORMAT), item.getEcode()));
                item.setMsLastUpdatorId(msUserId);
                item.setLastUpdatorId(userId);
                item.setLastUpdateTime(now);
                if (StringUtils.isEmpty(equData)) {
                    return;
                }
                BigDecimal total = AttDataUtils.getTotal(equData);
                item.setUsedEquData(AttDataUtils.subAttData(item.getUsedEquData(), equData));
                item.setRemainHours(item.getRemainHours().add(total));
            });
        }
        projectEquipmentDao.deleteBatchIds(list.stream().map(ProjectEquipmentEntity::getId).collect(Collectors.toList()));
        if (hasUsed) {
            equipmentUsedDao.updateBatch(usedList);
        }
    }

    private void checkAndLoadMonthData(Map<String, ProjectRdEquipmentModel> monthDataMap,
                                       Set<String> ecodes, Set<Date> months,
                                       Integer projectId, List<ProjectRdEquipmentModel> dataList,
                                       int year, String keyFormat) throws OwnerException {
        ProjectEntity projectEntity = projectDao.selectById(projectId);
        long beginTime = DateUtil.getMonthFirstDay(projectEntity.getBeginDate()).getTime();
        long endTime = projectEntity.getEndDate().getTime();
        for (ProjectRdEquipmentModel item : dataList) {
            Date m = DateUtil.getMonthFirstDay(item.getMonth());
            item.setMonth(m);
            months.add(m);
            if (cn.hutool.core.date.DateUtil.year(m) != year) {
                throw new OwnerException(MessageFormat.format(
                        "当前操作年：{0,number,#}不能导入月份【{1}】的数据。", year,
                        DateUtil.format(m, DateUtil.DEFAULT_YYMM_FORMAT)
                ));
            }
            if (beginTime > m.getTime() || endTime < m.getTime()) {
                throw new OwnerException(MessageFormat.format(
                        "月份【{0}】不在当前项目【{1}】的起止日期【{2}~{3}】内。", DateUtil.format(m, DateUtil.DEFAULT_YYMM_FORMAT),
                        projectEntity.getRdTitle(), DateUtil.format(projectEntity.getBeginDate(), DateUtil.DEFAULT_DATE_FORMAT),
                        DateUtil.format(projectEntity.getEndDate(), DateUtil.DEFAULT_DATE_FORMAT)
                ));
            }
            monthDataMap.put(
                    MessageFormat.format(keyFormat, DateUtil.format(item.getMonth(), DateUtil.DEFAULT_YYMM_FORMAT), item.getEcode()),
                    item
            );
            ecodes.add(item.getEcode());
        }
    }
    public void insertSummary(Date now, List<Integer> projectIds, List<Date> months, RsUserInfo userInfo) {
        List<ProjectRdEquipmentTotalModel> totals = projectRdEquipmentDao.getSummary(projectIds, months, cn.hutool.core.date.DateUtil.year(months.get(0)));
        List<SummaryEntity> summaryList = new ArrayList<>();
        Map<String, ProjectRdEquipmentTotalModel> totalMap = new HashMap<>();
        String keyFormat = "{0}_{1}";
        if (!CollectionUtils.isEmpty(totals)) {
            totals.forEach(item -> totalMap.put(MessageFormat.format(keyFormat, item.getProjectId(), DateUtil.format(item.getMonth(), DateUtil.DEFAULT_YYMM_FORMAT)), item));
        }

        months.forEach(m -> {
            projectIds.forEach(pId -> {
                BigDecimal lab = BigDecimal.ZERO, assets = BigDecimal.ZERO, prod = BigDecimal.ZERO, power = BigDecimal.ZERO;
                ProjectRdEquipmentTotalModel total = totalMap.get(MessageFormat.format(keyFormat, pId, DateUtil.format(m, DateUtil.DEFAULT_YYMM_FORMAT)));
                if (null != total) {
                    lab = total.getLab();
                    assets = total.getAssets();
                    prod = total.getProd();
                    power = total.getPower();
                }
                summaryList.add(ToolUtils.build(now, m, pId, CostEnum.LAB.getType(), lab, userInfo));
                summaryList.add(ToolUtils.build(now, m, pId, CostEnum.PROD.getType(), prod, userInfo));
                summaryList.add(ToolUtils.build(now, m, pId, CostEnum.ASSETS_AMORTIZATION.getType(), assets, userInfo));
                summaryList.add(ToolUtils.build(now, m, pId, CostEnum.STIMULUS_PROD.getType(), power, userInfo));

            });

        });
        summaryDao.insertOrUpdate(summaryList);
        // 已在删除项目时清理
        // summaryDao.delRdFundsForZero(projectId,months);
    }

}
