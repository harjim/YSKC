package com.yskc.rs.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.dao.AccountTitleDao;
import com.yskc.rs.dao.ProjectMaterialDao;
import com.yskc.rs.dao.SummaryDao;
import com.yskc.rs.dao.project.*;
import com.yskc.rs.entity.AccountTitleEntity;
import com.yskc.rs.entity.project.ProjectAttendance;
import com.yskc.rs.enums.CostEnum;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.projectequipment.WorkSheetEquipmentModel;
import com.yskc.rs.models.voucher.UpdateAccount;
import com.yskc.rs.models.voucher.VoucherAccount;
import com.yskc.rs.models.voucher.VoucherInfo;
import com.yskc.rs.models.workSheet.*;
import com.yskc.rs.service.WorkSheetService;
import com.yskc.rs.utils.ToolUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 工单服务
 *
 * @author huronghua
 */
@Service
public class WorkSheetServiceImpl implements WorkSheetService {
    @Autowired
    private ProjectAttendanceDao projectAttendanceDao;

    @Autowired
    private ProjectRdEmployeeDao projectRdEmployeeDao;

    @Autowired
    private ProjectEquipmentDao projectEquipmentDao;

    @Autowired
    private ProjectEnergyDao projectEnergyDao;

    @Autowired
    private ProjectInspectionDao projectInspectionDao;
    @Autowired
    private ProjectDesignDao projectDesignDao;

    @Autowired
    private ProjectMaterialDao projectMaterialDao;
    @Autowired
    private SummaryDao summaryDao;
    @Autowired
    private AccountTitleDao accountTitleDao;

    /**
     * 获取人员工单
     *
     * @param userInfo
     * @param query
     * @return
     * @throws OwnerException
     */
    @Override
    public List<WorkSheetModel> getStaffWorkSheetList(UserInfo userInfo, WorkSheetQuery query) throws OwnerException {
        List<ProjectAttendance> attendanceList = projectAttendanceDao.getByMonth(query.getProjectId(), query.getWorkDate(), query.getWorkDate());
        if (CollectionUtils.isEmpty(attendanceList)) {
            return null;
        }
        int num = 0;
        String empty = "";
        String period = DateUtil.formatDate(query.getWorkDate());
        List<WorkSheetModel> result = new ArrayList<>();
        query.setMonth(DateUtil.beginOfMonth(query.getWorkDate()));
        // 存在研发考勤，则存在研发记录
        List<MonthWorkSheetItem> list = projectRdEmployeeDao.getMonthWorkSheetList(query);
        String rdNum = "RD" + (list.get(0).getRdIndex() > 10 ? list.get(0).getRdIndex() : "0" + list.get(0).getRdIndex());
        Map<String, MonthWorkSheetItem> enumberAvgMap = new HashMap<>();
        BigDecimal avg;
        BigDecimal total = BigDecimal.ZERO;
        list.forEach(item -> enumberAvgMap.put(item.getEnumber(), item));
        // (item.getRdPay().add(item.getRdInsuranceFund())).divide(item.getRdHour(), 2, BigDecimal.ROUND_HALF_UP))
        for (ProjectAttendance item : attendanceList) {
            MonthWorkSheetItem sheetItem = enumberAvgMap.get(item.getEnumber());
            if (sheetItem == null) {
                continue;
            }
            avg = (sheetItem.getRdPay().add(sheetItem.getRdInsuranceFund())).divide(sheetItem.getRdHour(), 2, BigDecimal.ROUND_HALF_UP);
            BigDecimal pay = avg.multiply(item.getWorkHour()).setScale(2, BigDecimal.ROUND_HALF_UP);
            total = total.add(pay);
            result.add(new WorkSheetModel(++num + empty, sheetItem.getEname(), empty, period
                    , sheetItem.getRole(), pay,
                    sheetItem.getDeptName(), rdNum));

        }
        result.add(new WorkSheetModel(empty, "合计", empty, empty, empty, total.setScale(2, BigDecimal.ROUND_HALF_UP), empty, empty));
        return result;
    }

    @Override
    public List<WorkSheetMonthModel> getMonthWorkSheetList(UserInfo userInfo, WorkSheetQuery workSheetQuery) throws OwnerException {
        Date monthBegin = workSheetQuery.getMonth();
        Date monthEnd = DateUtil.endOfMonth(monthBegin);
        List<ProjectAttendance> attendanceList = projectAttendanceDao.getByMonth(workSheetQuery.getProjectId(), monthBegin, monthEnd);
        if (CollectionUtils.isEmpty(attendanceList)) {
            return null;
        }
        // 存在研发考勤，则存在研发记录
        List<MonthWorkSheetItem> list = projectRdEmployeeDao.getMonthWorkSheetList(workSheetQuery);
        Integer rdIndex = list.get(0).getRdIndex();
        String rdNum = "RD" + (rdIndex > 10 ? rdIndex : "0" + rdIndex);
        Map<String, BigDecimal> enumberAvgMap = new HashMap<>();
        list.forEach(item -> enumberAvgMap.put(item.getEnumber(), (item.getRdPay().add(item.getRdInsuranceFund())).divide(item.getRdHour(), 2, BigDecimal.ROUND_HALF_UP)));
        Map<Date, WorkSheetMonthModel> dayMap = new LinkedHashMap<>();
        BigDecimal total = BigDecimal.ZERO;
        for (ProjectAttendance item : attendanceList) {
            BigDecimal avg = enumberAvgMap.getOrDefault(item.getEnumber(), BigDecimal.ZERO);
            WorkSheetMonthModel current = dayMap.getOrDefault(item.getWorkDate(), new WorkSheetMonthModel(
                    ToolUtils.getWorkNo(userInfo.getCompanyId(), rdIndex, item.getWorkDate(), CostEnum.SALARY)
                    , DateUtil.formatDate(item.getWorkDate()),
                    BigDecimal.ZERO,
                    rdNum));
            BigDecimal amount = item.getWorkHour().multiply(avg);
            current.setExpenseAmount(current.getExpenseAmount().add(amount).setScale(2, BigDecimal.ROUND_HALF_UP));
            total = total.add(amount);
            dayMap.put(item.getWorkDate(), current);
        }
        List<WorkSheetMonthModel> result = new ArrayList<>(dayMap.values());
        result.add(new WorkSheetMonthModel("合计", "", total.setScale(2, BigDecimal.ROUND_HALF_UP), ""));
        return result;
    }

    /**
     * @param userInfo
     * @param workSheetQuery
     * @return
     * @throws OwnerException
     */
    @Override
    public List<VoucherInfo> getWorkVoucherList(UserInfo userInfo, WorkSheetQuery workSheetQuery) throws OwnerException {
        List<VoucherInfo> voucherInfos = summaryDao.getVoucherInfoList(userInfo.getCompanyId(), workSheetQuery.getProjectId(), workSheetQuery.getMonth(), workSheetQuery.getTypes());
        List<VoucherInfo> returnList = new ArrayList<>();
        if (voucherInfos.isEmpty()) {
            return returnList;
        }
        voucherInfos = voucherInfos.stream().filter(item -> (item.getRdFunds().compareTo(BigDecimal.ZERO) == 1)).sorted(Comparator.comparing(VoucherInfo::getMonth).thenComparing(VoucherInfo::getRdType)).collect(Collectors.toList());
        Map<Date, List<VoucherInfo>> dateListMap = voucherInfos.stream().collect(Collectors.groupingBy(VoucherInfo::getMonth));
        for (List<VoucherInfo> voucherInfoList : dateListMap.values()) {
            if (!voucherInfoList.isEmpty()) {
                boolean setWorkNo = true;
                List<Integer> salaryTypes = new ArrayList<>();
                salaryTypes.add(CostEnum.SALARY.getType());
                setWorkNo = setVoucherInfo(setWorkNo, CostEnum.SALARY, salaryTypes, voucherInfoList, returnList);
                List<Integer> bonusTypes = new ArrayList<>();
                bonusTypes.add(CostEnum.BONUS.getType());
                setVoucherInfo(setWorkNo, CostEnum.BONUS, bonusTypes, voucherInfoList, returnList);
                setVoucherInfo(setWorkNo, CostEnum.INSURANCE, CostEnum.getInsuranceList(), voucherInfoList, returnList);
                List<Integer> prodTypes = new ArrayList<>();
                prodTypes.add(CostEnum.PROD.getType());
                prodTypes.add(CostEnum.LAB.getType());
                setVoucherInfo(true, CostEnum.PROD, prodTypes, voucherInfoList, returnList);
                setVoucherInfo(true, CostEnum.MATERIAL, CostEnum.getCostEnumList(CostEnum.MATERIAL), voucherInfoList, returnList);
                setVoucherInfo(true, CostEnum.TRIAL_MATERIAL, CostEnum.getCostEnumList(CostEnum.TRIAL_MATERIAL), voucherInfoList, returnList);
                setVoucherInfo(true, CostEnum.REPAIR_MATERIAL, CostEnum.getCostEnumList(CostEnum.REPAIR_MATERIAL), voucherInfoList, returnList);
                setVoucherInfo(true, CostEnum.STIMULUS, CostEnum.getCostEnumList(CostEnum.STIMULUS), voucherInfoList, returnList);
                setVoucherInfo(true, CostEnum.FUEL, CostEnum.getCostEnumList(CostEnum.FUEL), voucherInfoList, returnList);
                setVoucherInfo(true, CostEnum.DESIGN, CostEnum.getCostEnumList(CostEnum.DESIGN), voucherInfoList, returnList);
                setVoucherInfo(true, CostEnum.INSPECTION, CostEnum.getCostEnumList(CostEnum.INSPECTION), voucherInfoList, returnList);
                setVoucherInfo(true, CostEnum.REPAIR, CostEnum.getCostEnumList(CostEnum.REPAIR), voucherInfoList, returnList);
                setVoucherInfo(true, CostEnum.TRAVEL, CostEnum.getCostEnumList(CostEnum.TRAVEL), voucherInfoList, returnList);
                setVoucherInfo(true, CostEnum.SOFT_AMORTIZATION, CostEnum.getCostEnumList(CostEnum.SOFT_AMORTIZATION), voucherInfoList, returnList);
                setVoucherInfo(true, CostEnum.TRIAL_PROD, CostEnum.getCostEnumList(CostEnum.TRIAL_PROD), voucherInfoList, returnList);
                setVoucherInfo(true, CostEnum.OTHER, CostEnum.getCostEnumList(CostEnum.OTHER), voucherInfoList, returnList);
                //setVoucherInfo(true, CostEnum.STIMULUS_PROD, CostEnum.getCostEnumList(CostEnum.STIMULUS_PROD), voucherInfoList, returnList);
                //setVoucherInfo(true, CostEnum.TRIAL_STIMULUS, CostEnum.getCostEnumList(CostEnum.TRIAL_STIMULUS), voucherInfoList, returnList);
            }
        }
        if (returnList.isEmpty()) {
            return returnList;
        }
        VoucherInfo voucherInfo = new VoucherInfo();
        voucherInfo.setMonth(com.yskc.common.utils.DateUtil.addDays(new Date(), 10000));
        voucherInfo.setId(Integer.MAX_VALUE);
        voucherInfo.setSum(true);
        voucherInfo.setRdType(Integer.MAX_VALUE);
        voucherInfo.setWorkNo("合计");
        voucherInfo.setRdFunds(returnList.stream().map(VoucherInfo::getRdFunds).reduce(BigDecimal.ZERO, BigDecimal::add));
        voucherInfo.setDebitAmount(returnList.stream().map(VoucherInfo::getDebitAmount).reduce(BigDecimal.ZERO, BigDecimal::add));
        voucherInfo.setCreditAmount(returnList.stream().map(VoucherInfo::getCreditAmount).reduce(BigDecimal.ZERO, BigDecimal::add));
        returnList.add(voucherInfo);
        return returnList.stream().filter(item -> (item.getRdFunds().compareTo(BigDecimal.ZERO) == 1)).sorted(Comparator.comparing(VoucherInfo::getMonth).thenComparing(VoucherInfo::getRdType)).collect(Collectors.toList());
    }

    /**
     * 设置相应的凭证
     *
     * @param setWorkNo
     * @param costEnum
     * @param costTypes
     * @param voucherInfoList
     * @param returnList
     * @return
     */
    private Boolean setVoucherInfo(boolean setWorkNo, CostEnum costEnum, List<Integer> costTypes, List<VoucherInfo> voucherInfoList, List<VoucherInfo> returnList) {
        List<VoucherInfo> voucherInfos = voucherInfoList.stream().filter(item -> costTypes.contains(item.getRdType().intValue())).collect(Collectors.toList());
        if (voucherInfos.isEmpty()) {
            return setWorkNo;
        }
        BigDecimal reduce = voucherInfos.stream().map(VoucherInfo::getRdFunds).reduce(BigDecimal.ZERO, BigDecimal::add);
        VoucherInfo voucherInfo = voucherInfos.get(0);
        voucherInfo.setDebitAmount(reduce);
        voucherInfo.setSum(false);
        voucherInfo.setRdFunds(reduce);
        voucherInfo.setCreditAmount(reduce);
        voucherInfo.setRemark(costEnum.getTitle());
        voucherInfo.setRdType(costEnum.getType());
        if (costEnum == CostEnum.INSURANCE) {
            costEnum = CostEnum.BONUS;
        }
        String workNo = ToolUtils.getParentVoucherNo(voucherInfo.getRdIndex(), voucherInfo.getMonth(), costEnum);
        voucherInfo.setBaseWorkNo(workNo);
        if (setWorkNo) {
            voucherInfo.setWorkNo(workNo);
            setWorkNo = false;
        }
        returnList.add(voucherInfo);
        return setWorkNo;
    }

    @Override
    public List<WorkSheetModel> getEquipmentWorkSheetList(UserInfo userInfo, WorkSheetQuery workSheetQuery) {
        Integer year = DateUtil.year(workSheetQuery.getMonth());
        List<WorkSheetEquipmentModel> equipments = projectEquipmentDao.getEquipmentWorkSheetList(userInfo.getCompanyId(), workSheetQuery, year);
        List<WorkSheetModel> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(equipments)) {
            int num = 0;
            int dayIndex = DateUtil.dayOfMonth(workSheetQuery.getWorkDate()) - 1;
            String empty = "";
            String period = DateUtil.format(workSheetQuery.getWorkDate(), "yyyy-MM-dd");
            BigDecimal total = BigDecimal.ZERO;
            String rdNum = "RD" + (equipments.get(0).getRdIndex() > 10 ? equipments.get(0).getRdIndex() : "0" + equipments.get(0).getRdIndex());
            for (WorkSheetEquipmentModel item : equipments) {
                BigDecimal hour = BigDecimal.valueOf(Double.valueOf(item.getEquData().split(",")[dayIndex]));
                if (hour.compareTo(BigDecimal.ZERO) <= 0) {
                    continue;
                }
                BigDecimal hourCost = item.getRdDepreciation().divide(new BigDecimal(item.getRdHour()), 20, BigDecimal.ROUND_HALF_UP);
                BigDecimal sheetCost = hourCost.multiply(hour).setScale(2, BigDecimal.ROUND_HALF_UP);
                total = total.add(sheetCost);
                result.add(new WorkSheetModel(++num + empty, item.getEname(), empty, period, item.getEffect(), sheetCost, item.getDeptName(), rdNum));
            }
            if (!CollectionUtils.isEmpty(result)) {
                result.add(new WorkSheetModel(empty, "合计", empty, empty, empty, total, empty, empty));
            }
        }
        return result;
    }


    @Override
    public List<WorkSheetMonthModel> getEquipmentWorkSheetMonthList(UserInfo userInfo, WorkSheetQuery workSheetQuery) {
        List<WorkSheetMonthModel> result = new ArrayList<>();
        List<WorkSheetEquipmentModel> equipments = projectEquipmentDao.getEquipmentWorkSheetList(userInfo.getCompanyId(),
                workSheetQuery,DateUtil.year(workSheetQuery.getMonth()));
        if (CollectionUtils.isEmpty(equipments)) {
            return result;
        }
        Date month = workSheetQuery.getMonth();
        int dayLength = DateUtil.dayOfMonth(DateUtil.endOfMonth(month));
        Map<Integer, BigDecimal> dayCosts = getEquipmentWorkSheet(dayLength, equipments);
        Integer rdIndex = equipments.get(0).getRdIndex();
        String rdNum = "RD" + (rdIndex > 10 ? rdIndex : "0" + rdIndex);
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Integer, BigDecimal> map : dayCosts.entrySet()) {
            Date dayTime = DateUtil.offsetDay(month, map.getKey());
            result.add(new WorkSheetMonthModel(
                    ToolUtils.getWorkNo(userInfo.getCompanyId(), rdIndex, dayTime, CostEnum.PROD),
                    DateUtil.format(dayTime, "yyyy-MM-dd"),
                    map.getValue(), rdNum));
            total = total.add(map.getValue());
        }

        if (!CollectionUtils.isEmpty(result)) {
            result.add(new WorkSheetMonthModel("合计", "", total, ""));
        }
        return result;
    }

    private Map<Integer, BigDecimal> getEquipmentWorkSheet(Integer dayLength, List<WorkSheetEquipmentModel> equipments) {
        Map<Integer, BigDecimal> dayCosts = new HashMap<>();
        for (WorkSheetEquipmentModel item : equipments) {
            String[] dataArr = item.getEquData().split(",");
            BigDecimal hourCost = item.getRdDepreciation().divide(new BigDecimal(item.getRdHour()), 20, BigDecimal.ROUND_HALF_UP);
            BigDecimal hour;
            for (int i = 0; i < dayLength; i++) {
                hour = BigDecimal.valueOf(Double.valueOf(dataArr[i]));
                if (hour.compareTo(BigDecimal.ZERO) <= 0) {
                    continue;
                }
                BigDecimal sheetCost = hourCost.multiply(hour).setScale(2, BigDecimal.ROUND_HALF_UP);
                if (null == dayCosts.get(i)) {
                    dayCosts.put(i, sheetCost);
                } else {
                    dayCosts.put(i, dayCosts.get(i).add(sheetCost));
                }

            }
        }
        return dayCosts;
    }

    @Override
    public List<VoucherModel> getEquipmentVoucherList(UserInfo userInfo, WorkSheetQuery workSheetQuery) {
        List<VoucherModel> result = new ArrayList<>();
        List<WorkSheetEquipmentModel> equipments = projectEquipmentDao.getEquipmentWorkSheetList(userInfo.getCompanyId(),
                workSheetQuery,DateUtil.year(workSheetQuery.getMonth()));
        if (CollectionUtils.isEmpty(equipments)) {
            return result;
        }
        Date month = workSheetQuery.getMonth();
        int dayLength = DateUtil.dayOfMonth(DateUtil.endOfMonth(month));
        Map<Integer, BigDecimal> dayCosts = getEquipmentWorkSheet(dayLength, equipments);
        Integer rdIndex = equipments.get(0).getRdIndex();
        String rdNum = "RD" + (rdIndex > 10 ? rdIndex : "0" + rdIndex);
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Integer, BigDecimal> map : dayCosts.entrySet()) {
            Date dayTime = DateUtil.offsetDay(month, map.getKey());
            result.add(new VoucherModel(
                    DateUtil.year(dayTime) + "",
                    (DateUtil.month(dayTime) + 1) + "",
                    DateUtil.dayOfMonth(dayTime) + "",
                    map.getValue(),
                    ToolUtils.getParentVoucherNo(rdIndex, dayTime, CostEnum.PROD), rdNum));
            total = total.add(map.getValue());
        }
        if (!CollectionUtils.isEmpty(result)) {
            result.add(new VoucherModel("", "", "", total, "合计", rdNum));
        }
        return result;
    }


    @Override
    public List<WorkSheetModel> getEnergyWorkSheetList(UserInfo userInfo, WorkSheetQuery workSheetQuery) throws OwnerException {
        if (null == workSheetQuery.getTypes() || workSheetQuery.getTypes().size() < 1) {
            throw new OwnerException("获取工单数据失败，请刷新后重试");
        }
        List<WorkSheetModel> result = projectEnergyDao.getEnergyWorkSheetList(
                userInfo.getCompanyId(), workSheetQuery, workSheetQuery.getTypes().get(0));

        if (!CollectionUtils.isEmpty(result)) {
            String empty = "";
            int num = 0;
            String period = DateUtil.format(workSheetQuery.getWorkDate(), "yyyy-MM-dd");
            String rdNum = "RD" + (result.get(0).getRdNum().length() > 2 ? result.get(0).getRdNum() : "0" + result.get(0).getRdNum());
            BigDecimal total = BigDecimal.ZERO;
            for (WorkSheetModel item : result) {
                item.setNum(++num + empty);
                item.setPeriod(period);
                total = total.add(item.getCost());
                item.setRdNum(rdNum);
                item.setWorkEvent(item.getCostElement());
            }
            result.add(new WorkSheetModel(empty, "合计", empty, empty, empty, total, empty, empty));
        }
        return result;
    }

    @Override
    public List<WorkSheetMonthModel> getEnergyWorkSheetMonthList(UserInfo userInfo, WorkSheetQuery workSheetQuery) throws OwnerException {
        if (null == workSheetQuery.getTypes() || workSheetQuery.getTypes().size() < 1) {
            throw new OwnerException("获取工单数据失败，请刷新后重试");
        }
        List<WorkSheetMonthModel> result = new ArrayList<>();
        Date begin = DateUtil.beginOfMonth(workSheetQuery.getMonth());
        Date end = DateUtil.endOfMonth(workSheetQuery.getMonth());
        List<WorkSheetMiniModel> workSheetList = projectEnergyDao.getEnergyWorkSheetMonthList(
                userInfo.getCompanyId(), workSheetQuery, workSheetQuery.getTypes().get(0), begin, end);
        if (CollectionUtils.isEmpty(workSheetList)) {
            return result;
        }
        Integer rdIndex = workSheetList.get(0).getRdIndex();
        Integer etype = workSheetQuery.getTypes().get(0);
        String rdNum = "RD" + (rdIndex > 9 ? rdIndex : "0" + rdIndex);
        Map<Date, BigDecimal> costMap = getWorkSheet(workSheetList);
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Date, BigDecimal> map : costMap.entrySet()) {
            result.add(new WorkSheetMonthModel(
                    ToolUtils.getWorkNo(userInfo.getCompanyId(), rdIndex, map.getKey(), CostEnum.getCostEnum(etype)),
                    DateUtil.format(map.getKey(), "yyyy-MM-dd"),
                    map.getValue(), rdNum));
            total = total.add(map.getValue());
        }

        if (!CollectionUtils.isEmpty(result)) {
            result.add(new WorkSheetMonthModel("合计", "", total, ""));
        }
        return result;
    }

    @Override
    public List<VoucherModel> getEnergyVoucherList(UserInfo userInfo, WorkSheetQuery workSheetQuery) throws OwnerException {
        if (null == workSheetQuery.getTypes() || workSheetQuery.getTypes().size() < 1) {
            throw new OwnerException("获取凭证数据失败，请刷新后重试");
        }
        List<VoucherModel> result = new ArrayList<>();
        Integer type = workSheetQuery.getTypes().get(0);
        Date begin = DateUtil.beginOfMonth(workSheetQuery.getMonth());
        Date end = DateUtil.endOfMonth(workSheetQuery.getMonth());
        List<WorkSheetMiniModel> workSheetList = projectEnergyDao.getEnergyWorkSheetMonthList(
                userInfo.getCompanyId(), workSheetQuery, type, begin, end);
        if (CollectionUtils.isEmpty(workSheetList)) {
            return result;
        }

        Integer rdIndex = workSheetList.get(0).getRdIndex();
        String rdNum = "RD" + (rdIndex > 9 ? rdIndex : "0" + rdIndex);
        Map<Date, BigDecimal> costMap = getWorkSheet(workSheetList);
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Date, BigDecimal> map : costMap.entrySet()) {
            result.add(new VoucherModel(
                    DateUtil.year(map.getKey()) + "",
                    (DateUtil.month(map.getKey()) + 1) + "",
                    DateUtil.dayOfMonth(map.getKey()) + "",
                    map.getValue(), ToolUtils.getParentVoucherNo(rdIndex, map.getKey(), CostEnum.getCostEnum(type)),
                    rdNum
            ));
            total = total.add(map.getValue());
        }

        if (!CollectionUtils.isEmpty(result)) {
            result.add(new VoucherModel("", "", "", total, "合计", ""));
        }
        return result;
    }

    @Override
    public List<VoucherModel> getMaterialVoucherList(UserInfo userInfo, WorkSheetQuery workSheetQuery) {
        List<VoucherModel> result = new ArrayList<>();
        Integer type = workSheetQuery.getTypes().get(0);
        Date begin = DateUtil.beginOfMonth(workSheetQuery.getMonth());
        Date end = DateUtil.endOfMonth(workSheetQuery.getMonth());
        List<WorkSheetMiniModel> workSheetList = projectMaterialDao.getMaterialWorkSheetMonthList(
                userInfo.getCompanyId(), workSheetQuery, type, begin, end);
        if (CollectionUtils.isEmpty(workSheetList)) {
            return result;
        }

        Integer rdIndex = workSheetList.get(0).getRdIndex();
        Map<Date, BigDecimal> costMap = getWorkSheet(workSheetList);
        String rdNum = "RD" + (rdIndex > 9 ? rdIndex : "0" + rdIndex);
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Date, BigDecimal> map : costMap.entrySet()) {
            result.add(new VoucherModel(
                    DateUtil.year(map.getKey()) + "",
                    (DateUtil.month(map.getKey()) + 1) + "",
                    DateUtil.dayOfMonth(map.getKey()) + "",
                    map.getValue(), ToolUtils.getParentVoucherNo(rdIndex, map.getKey(), CostEnum.getCostEnum(type)),
                    rdNum
            ));
            total = total.add(map.getValue());
        }

        if (!CollectionUtils.isEmpty(result)) {
            result.add(new VoucherModel("", "", "", total, "合计", ""));
        }
        return result;
    }


    private Map<Date, BigDecimal> getWorkSheet(List<WorkSheetMiniModel> workSheetList) {
        Map<Date, BigDecimal> costMap = new HashMap<>();
        for (WorkSheetMiniModel item : workSheetList) {
            if (costMap.get(item.getWorkDate()) == null) {
                costMap.put(item.getWorkDate(), item.getCost());
            } else {
                costMap.put(item.getWorkDate(), costMap.get(item.getWorkDate()).add(item.getCost()));
            }
        }
        return costMap;
    }

    @Override
    public List<WorkSheetMonthModel> getMaterialWorkSheetMonthList(UserInfo userInfo, WorkSheetQuery workSheetQuery) {
        List<WorkSheetMonthModel> result = new ArrayList<>();
        Date begin = DateUtil.beginOfMonth(workSheetQuery.getMonth());
        Date end = DateUtil.endOfMonth(workSheetQuery.getMonth());
        List<WorkSheetMiniModel> workSheetList = projectMaterialDao.getMaterialWorkSheetMonthList(
                userInfo.getCompanyId(), workSheetQuery, workSheetQuery.getTypes().get(0), begin, end);
        if (CollectionUtils.isEmpty(workSheetList)) {
            return result;
        }
        Map<Date, BigDecimal> costMap = getWorkSheet(workSheetList);
        Integer rdIndex = workSheetList.get(0).getRdIndex();
        String rdNum = "RD" + (workSheetList.get(0).getRdIndex() > 9 ? result.get(0).getRdNum() : "0" + workSheetList.get(0).getRdIndex());
        BigDecimal total = BigDecimal.ZERO;

        costMap.forEach((k, v) -> {
            result.add(new WorkSheetMonthModel(
                    ToolUtils.getWorkNo(userInfo.getCompanyId(), rdIndex, k, CostEnum.getCostEnum(workSheetQuery.getTypes().get(0))),
                    DateUtil.format(k, "yyyy-MM-dd"),
                    v, rdNum));
        });
        if (!CollectionUtils.isEmpty(result)) {
            result.add(new WorkSheetMonthModel("合计", "", total, ""));
        }
        return result;
    }

    @Override
    public List<WorkSheetModel> getInspectionList(UserInfo info, WorkSheetQuery workSheetQuery) {
        List<WorkSheetModel> list = projectInspectionDao.queryWorkOrderData(workSheetQuery.getProjectId(), info.getCompanyId(), workSheetQuery.getWorkDate(), workSheetQuery.getTypes());
        if (!CollectionUtils.isEmpty(list)) {
            String empty = "";
            int num = 0;
            String period = DateUtil.format(workSheetQuery.getWorkDate(), "yyyy-MM-dd");
            String rdNum = "RD" + (list.get(0).getRdNum().length() > 2 ? list.get(0).getRdNum() : "0" + list.get(0).getRdNum());

            BigDecimal total = BigDecimal.ZERO;
            for (WorkSheetModel item : list) {
                item.setNum(++num + empty);
                item.setPeriod(period);
                total = total.add(item.getCost());
                item.setRdNum(rdNum);
            }
            list.add(new WorkSheetModel(empty, "合计", empty, empty, empty, total, empty, empty));
        }
        return list;
    }

    @Override
    public List<WorkSheetModel> getDesignList(UserInfo info, WorkSheetQuery workSheetQuery) {
        List<WorkSheetModel> list = projectDesignDao.queryuOutData(info.getCompanyId(), workSheetQuery.getProjectId(), workSheetQuery.getWorkDate());
        if (!CollectionUtils.isEmpty(list)) {
            String empty = "";
            int num = 0;
            String period = DateUtil.format(workSheetQuery.getWorkDate(), "yyyy-MM-dd");
            String rdNum = "RD" + (list.get(0).getRdNum().length() > 2 ? list.get(0).getRdNum() : "0" + list.get(0).getRdNum());

            BigDecimal total = BigDecimal.ZERO;
            for (WorkSheetModel item : list) {
                item.setNum(++num + empty);
                item.setPeriod(period);
                total = total.add(item.getCost());
                item.setRdNum(rdNum);
            }
            list.add(new WorkSheetModel(empty, "合计", empty, empty, empty, total, empty, empty));
        }
        return list;
    }

    @Override
    public List<WorkSheetModel> getMaterialWorkSheetList(UserInfo userInfo, WorkSheetQuery workSheetQuery) {
        List<WorkSheetMaterialModel> result = projectMaterialDao.getMaterialWorkSheetList(
                userInfo.getCompanyId(), workSheetQuery, workSheetQuery.getTypes().get(0));

        List<WorkSheetModel> resultList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            String empty = "";
            int num = 0;
            String period = DateUtil.format(workSheetQuery.getWorkDate(), "yyyy-MM-dd");
            String rdNum = "RD" + (result.get(0).getRdNum().length() > 2 ? result.get(0).getRdNum() : "0" + result.get(0).getRdNum());

            BigDecimal total = BigDecimal.ZERO;
            for (WorkSheetMaterialModel item : result) {
                WorkSheetModel model = new WorkSheetModel();
                BeanUtil.copyProperties(item, model);
                model.setNum(++num + empty);
                model.setPeriod(period);
                model.setRdNum(rdNum);
                if (item.getQuantity().compareTo(item.getUsed()) == 0) {
                    total = total.add(item.getTotalAmount());
                    model.setCost(item.getTotalAmount());
                } else {
                    total = total.add(item.getUnitPrice().multiply(item.getUsed()));
                    model.setCost(item.getUnitPrice().multiply(item.getUsed()));
                }
                resultList.add(model);
            }
            resultList.add(new WorkSheetModel(empty, "合计", empty, empty, empty, total, empty, empty));
        }
        return resultList;
    }

    @Override
    public List<WorkSheetMonthModel> getDesignListByMonth(UserInfo info, WorkSheetQuery workSheetQuery) {
        List<WorkSheetMonthModel> result = new ArrayList<>();
        Date endDate = DateUtil.endOfMonth(workSheetQuery.getMonth());
        List<WorkSheetMiniModel> workSheetList = projectDesignDao.getDataBymonth(info, workSheetQuery, endDate);
        if (CollectionUtils.isEmpty(workSheetList)) {
            return result;
        }
        Map<Date, BigDecimal> costMap = new HashMap<>();
        Integer rdIndex = workSheetList.get(0).getRdIndex();
        String rdNum = "RD" + (workSheetList.get(0).getRdIndex() > 9 ? result.get(0).getRdNum() : "0" + workSheetList.get(0).getRdIndex());
        BigDecimal total = BigDecimal.ZERO;
        for (WorkSheetMiniModel item : workSheetList) {
            if (costMap.get(item.getWorkDate()) == null) {
                costMap.put(item.getWorkDate(), item.getCost());
            } else {
                costMap.put(item.getWorkDate(), costMap.get(item.getWorkDate()).add(item.getCost()));
            }
            total = total.add(item.getCost());
        }
        costMap.forEach((k, v) -> {
            result.add(new WorkSheetMonthModel(
                    ToolUtils.getWorkNo(info.getCompanyId(), rdIndex, k, CostEnum.DESIGN),
                    DateUtil.format(k, "yyyy-MM-dd"),
                    v, rdNum));
        });
        if (!CollectionUtils.isEmpty(result)) {
            result.add(new WorkSheetMonthModel("合计", "", total, ""));
        }
        return result;
    }

    @Override
    public List<WorkSheetMonthModel> getInspectionListByMonth(UserInfo info, WorkSheetQuery workSheetQuery) {
        List<WorkSheetMonthModel> result = new ArrayList<>();
        Date endDate = DateUtil.endOfMonth(workSheetQuery.getMonth());
        List<WorkSheetMiniModel> workSheetList = projectInspectionDao.queryWorkOrderByMonth(info, workSheetQuery, endDate);
        if (CollectionUtils.isEmpty(workSheetList)) {
            return result;
        }
        Map<Date, BigDecimal> costMap = new HashMap<>();
        Integer rdIndex = workSheetList.get(0).getRdIndex();
        String rdNum = "RD" + (workSheetList.get(0).getRdIndex() > 9 ? result.get(0).getRdNum() : "0" + workSheetList.get(0).getRdIndex());
        BigDecimal total = BigDecimal.ZERO;
        for (WorkSheetMiniModel item : workSheetList) {
            if (costMap.get(item.getWorkDate()) == null) {
                costMap.put(item.getWorkDate(), item.getCost());
            } else {
                costMap.put(item.getWorkDate(), costMap.get(item.getWorkDate()).add(item.getCost()));
            }
            total = total.add(item.getCost());
        }
        costMap.forEach((k, v) -> {
            result.add(new WorkSheetMonthModel(
                    ToolUtils.getWorkNo(info.getCompanyId(), rdIndex, k, CostEnum.getCostEnum(Integer.valueOf(workSheetQuery.getTypes().get(0)))),
                    DateUtil.format(k, "yyyy-MM-dd"),
                    v, rdNum));
        });
        if (!CollectionUtils.isEmpty(result)) {
            result.add(new WorkSheetMonthModel("合计", "", total, ""));
        }
        return result;
    }

    @Override
    public List<VoucherModel> getDesignListVoucherList(UserInfo userInfo, WorkSheetQuery workSheetQuery) {
        List<VoucherModel> result = new ArrayList<>();
        Integer type = workSheetQuery.getTypes().get(0);
        Date endDate = DateUtil.endOfMonth(workSheetQuery.getMonth());
        List<WorkSheetMiniModel> workSheetList = projectDesignDao.getDataBymonth(userInfo, workSheetQuery, endDate);
        if (CollectionUtils.isEmpty(workSheetList)) {
            return result;
        }

        Integer rdIndex = workSheetList.get(0).getRdIndex();
        String rdNum = "RD" + (rdIndex > 9 ? rdIndex : "0" + rdIndex);
        Map<Date, BigDecimal> costMap = getWorkSheet(workSheetList);
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Date, BigDecimal> map : costMap.entrySet()) {
            result.add(new VoucherModel(
                    DateUtil.year(map.getKey()) + "",
                    (DateUtil.month(map.getKey()) + 1) + "",
                    DateUtil.dayOfMonth(map.getKey()) + "",
                    map.getValue(), ToolUtils.getParentVoucherNo(rdIndex, map.getKey(), CostEnum.getCostEnum(type)),
                    rdNum
            ));
            total = total.add(map.getValue());
        }

        if (!CollectionUtils.isEmpty(result)) {
            result.add(new VoucherModel("", "", "", total, "合计", ""));
        }
        return result;
    }


    @Override
    public List<VoucherModel> getInspectionVoucherList(UserInfo userInfo, WorkSheetQuery workSheetQuery) {
        List<VoucherModel> result = new ArrayList<>();
        Integer type = workSheetQuery.getTypes().get(0);
        Date endDate = DateUtil.endOfMonth(workSheetQuery.getMonth());
        List<WorkSheetMiniModel> workSheetList = projectInspectionDao.queryWorkOrderByMonth(userInfo, workSheetQuery, endDate);
        if (CollectionUtils.isEmpty(workSheetList)) {
            return result;
        }
        Integer rdIndex = workSheetList.get(0).getRdIndex();
        String rdNum = "RD" + (rdIndex > 9 ? rdIndex : "0" + rdIndex);
        Map<Date, BigDecimal> costMap = getWorkSheet(workSheetList);
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Date, BigDecimal> map : costMap.entrySet()) {
            result.add(new VoucherModel(
                    DateUtil.year(map.getKey()) + "",
                    (DateUtil.month(map.getKey()) + 1) + "",
                    DateUtil.dayOfMonth(map.getKey()) + "",
                    map.getValue(), ToolUtils.getParentVoucherNo(rdIndex, map.getKey(), CostEnum.getCostEnum(type)),
                    rdNum
            ));
            total = total.add(map.getValue());
        }

        if (!CollectionUtils.isEmpty(result)) {
            result.add(new VoucherModel("", "", "", total, "合计", ""));
        }
        return result;
    }

    /**
     * 保存凭证科目
     *
     * @param userInfo
     * @param voucherAccount
     * @return
     * @throws OwnerException
     */
    @Override
    public Boolean saveVoucherAccount(UserInfo userInfo, VoucherAccount voucherAccount) throws OwnerException {
        AccountTitleEntity accountTitleEntity = accountTitleDao.selectById(voucherAccount.getAccountId());
        if (accountTitleEntity == null) {
            throw new OwnerException("数据异常，请联系管理员");
        }
        List<Integer> rdTypes = new ArrayList<>();
        CostEnum costEnum = CostEnum.getCostEnum(voucherAccount.getRdType());
        switch (costEnum) {
            case SALARY:
            case BONUS:
                rdTypes.add(voucherAccount.getRdType());
                break;
            case INSURANCE:
                rdTypes.addAll(CostEnum.getInsuranceList());
                break;
            case PROD:
                rdTypes.addAll(CostEnum.getCostEnumList(CostEnum.LAB, CostEnum.PROD));
                break;
            case MATERIAL:
                rdTypes.addAll(CostEnum.getCostEnumList(CostEnum.MATERIAL));
                break;
            case TRIAL_MATERIAL:
                rdTypes.addAll(CostEnum.getCostEnumList(CostEnum.TRIAL_MATERIAL));
                break;
            case REPAIR_MATERIAL:
                rdTypes.addAll(CostEnum.getCostEnumList(CostEnum.REPAIR_MATERIAL));
                break;
            case STIMULUS:
                rdTypes.addAll(CostEnum.getCostEnumList(CostEnum.STIMULUS));
                break;
            case FUEL:
                rdTypes.addAll(CostEnum.getCostEnumList(CostEnum.FUEL));
                break;
            case DESIGN:
                rdTypes.addAll(CostEnum.getCostEnumList(CostEnum.DESIGN));
                break;
            case INSPECTION:
                rdTypes.addAll(CostEnum.getCostEnumList(CostEnum.INSPECTION));
                break;
            case REPAIR:
                rdTypes.addAll(CostEnum.getCostEnumList(CostEnum.REPAIR));
                break;
            case TRAVEL:
                rdTypes.addAll(CostEnum.getCostEnumList(CostEnum.TRAVEL));
                break;
            case SOFT_AMORTIZATION:
                rdTypes.addAll(CostEnum.getCostEnumList(CostEnum.SOFT_AMORTIZATION));
                break;
            case TRIAL_PROD:
                rdTypes.addAll(CostEnum.getCostEnumList(CostEnum.TRIAL_PROD));
                break;
            case OTHER:
                rdTypes.addAll(CostEnum.getCostEnumList(CostEnum.OTHER));
                break;
            default:
                break;
        }
        if (rdTypes.isEmpty()) {
            return true;
        }
        UpdateAccount updateAccount = new UpdateAccount();
        updateAccount.setAccountNumber(accountTitleEntity.getAccountNumber());
        updateAccount.setLastUpdateTime(new Date());
        updateAccount.setLastUpdatorId(userInfo.getUserId());
        updateAccount.setMonth(voucherAccount.getMonth());
        updateAccount.setMsLastUpdatorId(userInfo.getMsUserId());
        updateAccount.setProjectId(voucherAccount.getProjectId());
        updateAccount.setRdType(rdTypes);
        return summaryDao.updateAccount(updateAccount) > 0;
    }
}
