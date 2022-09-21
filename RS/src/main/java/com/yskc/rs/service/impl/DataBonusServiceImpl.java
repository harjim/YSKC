package com.yskc.rs.service.impl;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.dao.AccountTitleDao;
import com.yskc.rs.dao.EmployeeDao;
import com.yskc.rs.dao.data.DataBonusDao;
import com.yskc.rs.entity.AccountTitleEntity;
import com.yskc.rs.entity.EmployeeEntity;
import com.yskc.rs.entity.data.DataBonusEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.data.DataBonusInfo;
import com.yskc.rs.models.data.DataBonusModel;
import com.yskc.rs.models.data.DataBonusQuery;
import com.yskc.rs.models.employee.EmployeeModel;
import com.yskc.rs.models.excel.BonusExcel;
import com.yskc.rs.service.DataBonusService;
import com.yskc.rs.utils.ListUtils;
import com.yskc.rs.utils.TransactionUtils;
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
 * 奖金服务
 *
 * @author huronghua
 */
@Service
public class DataBonusServiceImpl implements DataBonusService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DataBonusDao dataBonusDao;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private AccountTitleDao accountTitleDao;

    /**
     * @param userInfo
     * @param model
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean add(UserInfo userInfo, DataBonusModel model) throws OwnerException {
        try {
            List<Date> monthList = DateUtil.getMonthBetween(model.getBeginDay(), model.getEndDay());
            EmployeeEntity employeeEntity = employeeDao.getByNumber(userInfo.getCompanyId(), model.getEnumber());
            List<DataBonusEntity> dataBonusEntities = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            Date now = new Date();
            Integer length = monthList.size();
            Integer tempMonthCount = length - 2;
            Integer maxBeginDay = DateUtil.getMonthMaxDays(model.getBeginDay());
            Integer firstMonthDays = DateUtil.daysBetween(model.getBeginDay(), DateUtil.getMonthLastDay(model.getBeginDay())) + 1;
            BigDecimal first = BigDecimal.valueOf(firstMonthDays).divide(BigDecimal.valueOf(maxBeginDay), 2, 2);
            Integer endBeginDay = DateUtil.getMonthMaxDays(model.getEndDay());
            Integer endMonthDays = DateUtil.daysBetween(DateUtil.getMonthFirstDay(model.getEndDay()), model.getEndDay()) + 1;
            BigDecimal end = BigDecimal.valueOf(endMonthDays).divide(BigDecimal.valueOf(endBeginDay), 2, 2);
            BigDecimal averageMonthBonus = model.getBonus().divide(BigDecimal.valueOf(tempMonthCount).add(first).add(end), 2, 2);
            BigDecimal total = model.getBonus();
            for (int i = 0; i < length; i++) {
                DataBonusEntity dataBonusEntity = new DataBonusEntity();
                dataBonusEntity.setIsUsed(false);
                dataBonusEntity.setDeptName(model.getDeptName());
                dataBonusEntity.setStartTime(model.getBeginDay());
                dataBonusEntity.setEndTime(model.getEndDay());
                dataBonusEntity.setTotalBonus(model.getBonus());
                dataBonusEntity.setCompanyId(userInfo.getCompanyId());
                dataBonusEntity.setMsCreatorId(userInfo.getUserSource() == 1 ? userInfo.getId() : -1);
                dataBonusEntity.setMsLastUpdatorId(userInfo.getUserSource() == 1 ? userInfo.getId() : -1);
                dataBonusEntity.setCreatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
                dataBonusEntity.setLastUpdatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
                dataBonusEntity.setCreateTime(new Date());
                dataBonusEntity.setLastUpdateTime(new Date());
                dataBonusEntity.setCreatorId(userInfo.getId());
                dataBonusEntity.setEname(employeeEntity.getEname());
                dataBonusEntity.setEnumber(model.getEnumber());
                dataBonusEntity.setMonth(monthList.get(i));
                dataBonusEntity.setAccountTitleId(model.getAccountTitleId());
                dataBonusEntity.setRemark(model.getRemark());
                if (i == (length - 1)) {
                    dataBonusEntity.setBeginDay(1);
                    calendar.setTime(model.getEndDay());
                    dataBonusEntity.setBonus(total);
                    dataBonusEntity.setEndDay(calendar.get(Calendar.DAY_OF_MONTH));
                } else if (i == 0) {
                    calendar.setTime(model.getBeginDay());
                    dataBonusEntity.setBeginDay(calendar.get(Calendar.DAY_OF_MONTH));
                    dataBonusEntity.setEndDay(calendar.getActualMaximum(Calendar.DATE));
                    dataBonusEntity.setBonus(averageMonthBonus.multiply(first).setScale(2, 2));
                    total = total.subtract(dataBonusEntity.getBonus());
                } else {
                    dataBonusEntity.setBonus(averageMonthBonus.setScale(2, 2));
                    calendar.setTime(monthList.get(i));
                    dataBonusEntity.setBeginDay(1);
                    dataBonusEntity.setEndDay(calendar.getActualMaximum(Calendar.DATE));
                    total = total.subtract(dataBonusEntity.getBonus());
                }
                dataBonusEntity.setCreateTime(now);
                dataBonusEntity.setLastUpdateTime(now);
                dataBonusEntities.add(dataBonusEntity);
            }
            dataBonusDao.insertOrUpdate(dataBonusEntities);
        } catch (Exception ex) {
            logger.error("添加员工奖金错误", ex);
            throw new OwnerException(ErrorEnum.FAIL);
        }
        return true;
    }

    /**
     * 获取人员奖金
     *
     * @param dataBonusQuery
     * @return
     * @throws OwnerException
     */
    @Override
    public PageModel<List<DataBonusInfo>> getDataBonusList(DataBonusQuery dataBonusQuery) throws OwnerException {
        Pagination page = dataBonusQuery.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> descs = new ArrayList<>();
            descs.add("db.month");
            descs.add("db.createTime");
            page.setDescs(descs);

        }
        if (dataBonusQuery.getMonth() != null) {
            dataBonusQuery.setMonth(DateUtil.getMonthFirstDay(dataBonusQuery.getMonth()));
        }
        if (dataBonusQuery.getStartMonth() != null) {
            dataBonusQuery.setStartMonth(DateUtil.getMonthFirstDay(dataBonusQuery.getStartMonth()));
        }
        if (dataBonusQuery.getEndMonth() != null) {
            dataBonusQuery.setEndMonth(DateUtil.getMonthFirstDay(dataBonusQuery.getEndMonth()));
        }
        List<DataBonusInfo> dataBonusInfoList = dataBonusDao.getDataBonusList(page, dataBonusQuery);
        return PageUtils.build(page, dataBonusInfoList);
    }

    /**
     * @param userInfo
     * @param ids
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean deleteList(UserInfo userInfo, List<Integer> ids) throws OwnerException {
//        List<UsedBonusModel> usedBonusModels = dataBonusDao.getUsedBonusList(userInfo.getCompanyId(), ids);
//        if (!usedBonusModels.isEmpty()) {
//        UsedBonusModel usedBonusModel = usedBonusModels.get(0);
//        if (ids.size() == 1) {
//            throw new OwnerException(MessageFormat.format("员工【{0}】,【{2}】月的奖金,在项目【RD-{1}】归集中有使用记录，不能删除", usedBonusModel.getEnumber(), usedBonusModel.getRdIndex(), DateUtil.getDateTime(usedBonusModel.getMonth(), "yyyy-MM")));
//        }
//        List<Integer> notDeleteIds = usedBonusModels.stream().map(a -> a.getId()).collect(Collectors.toList());
//        ids.removeAll(notDeleteIds);
//        // }
//        if (ids.size() == 0) {
//            return true;
//        }
        return dataBonusDao.deleteBatchIds(ids) > 0;
    }

    /**
     * @param userInfo
     * @param bonusExcels
     * @return
     * @throws OwnerException
     */
    @Override
    public String importData(UserInfo userInfo, List<BonusExcel> bonusExcels, Integer year) throws OwnerException {
        String returnMsg = "";
        if (bonusExcels.size() == 0) {
            return returnMsg;
        }
        List<String> fullAccountNameList = new ArrayList<>();
        List<DataBonusEntity> dataBonusEntities = new ArrayList<>();
        List<String> importEnumberList = bonusExcels.stream().map(BonusExcel::getEnumber).collect(Collectors.toList());
        List<EmployeeModel> employeeModelList = employeeDao.getListByeEnumbers(userInfo.getCompanyId(), importEnumberList, year);
        if (employeeModelList.size() == 0) {
            throw new OwnerException("导入的员工编号在花名册中不存在，请先导入员工花名册");
        }
        Map<String, String> employeeModelMap = employeeModelList.stream().collect(Collectors.toMap(EmployeeModel::getEnumber, EmployeeModel::getEname));
        for (BonusExcel bonusExcel : bonusExcels) {
            if (employeeModelMap.containsKey(bonusExcel.getEnumber())) {
                if (!employeeModelMap.get(bonusExcel.getEnumber()).equals(bonusExcel.getEname())) {
                    throw new OwnerException(MessageFormat.format("导入数据中的工号：【{0}】的姓名:【{1}】与员工花名册中的姓名：【{2}】不一致，请检查修复后再提交", bonusExcel.getEnumber(), bonusExcel.getEname(), employeeModelMap.get(bonusExcel.getEnumber())));
                }
            }
            if (!StringUtils.isEmpty(bonusExcel.getFullAccountName())) {
                fullAccountNameList.add(bonusExcel.getFullAccountName());
            }
        }
        List<String> enumberList = employeeModelList.stream().map(EmployeeModel::getEnumber).collect(Collectors.toList());
        importEnumberList.removeAll(enumberList);
        if (importEnumberList.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder("部分成功，以下员工编号不存在:");
            stringBuilder.append(String.join(",", importEnumberList));
            stringBuilder.append(" ；请先导入员工花名册");
            returnMsg = stringBuilder.toString();
        }
        List<AccountTitleEntity> accountTitleEntityList = fullAccountNameList.size() == 0 ? new ArrayList<>() : accountTitleDao.getByFullAccountName(userInfo.getCompanyId(), fullAccountNameList);
        Map<String, Integer> accountTitleMap = accountTitleEntityList.stream().collect(Collectors.toMap(AccountTitleEntity::getFullAccountName, AccountTitleEntity::getId));
        bonusExcels = bonusExcels.stream().filter(a -> !importEnumberList.contains(a.getEnumber())).collect(Collectors.toList());
        for (BonusExcel bonusExcel : bonusExcels) {
            if (bonusExcel.getStartTime().getTime() > bonusExcel.getEndTime().getTime()) {
                throw new OwnerException(MessageFormat.format(
                        "导入数据中的工号【{0}】，姓名:【{1}】：开始日期【{2}】不能大于结束日期【{3}】",
                        bonusExcel.getEnumber(), bonusExcel.getEname(),
                        DateUtil.format(bonusExcel.getStartTime(), "yyyy-MM-dd"),
                        DateUtil.format(bonusExcel.getEndTime(), "yyyy-MM-dd")));
            }
            try {
                List<Date> monthList = DateUtil.getMonthBetween(bonusExcel.getStartTime(), bonusExcel.getEndTime());
                Calendar calendar = Calendar.getInstance();
                Date now = new Date();
                Integer length = monthList.size();
                Integer tempMonthCount = length - 2;
                Integer maxBeginDay = DateUtil.getMonthMaxDays(bonusExcel.getStartTime());
                Integer firstMonthDays = DateUtil.daysBetween(bonusExcel.getStartTime(), DateUtil.getMonthLastDay(bonusExcel.getStartTime())) + 1;
                BigDecimal first = BigDecimal.valueOf(firstMonthDays).divide(BigDecimal.valueOf(maxBeginDay), 2, 2);
                Integer endBeginDay = DateUtil.getMonthMaxDays(bonusExcel.getStartTime());
                Integer endMonthDays = DateUtil.daysBetween(DateUtil.getMonthFirstDay(bonusExcel.getEndTime()), bonusExcel.getEndTime()) + 1;
                BigDecimal end = BigDecimal.valueOf(endMonthDays).divide(BigDecimal.valueOf(endBeginDay), 2, 2);
                BigDecimal averageMonthBonus = bonusExcel.getBonus().divide(BigDecimal.valueOf(tempMonthCount).add(first).add(end), 2, 2);
                Integer accountTitleId = accountTitleMap.getOrDefault(bonusExcel.getFullAccountName(), -1);
                BigDecimal total = bonusExcel.getBonus();

                for (int i = 0; i < length; i++) {
                    DataBonusEntity dataBonusEntity = new DataBonusEntity();
                    dataBonusEntity.setAccountTitleId(accountTitleId);
                    dataBonusEntity.setIsUsed(false);
                    dataBonusEntity.setDeptName(bonusExcel.getDeptName());
                    dataBonusEntity.setStartTime(bonusExcel.getStartTime());
                    dataBonusEntity.setEndTime(bonusExcel.getEndTime());
                    dataBonusEntity.setTotalBonus(bonusExcel.getBonus());
                    dataBonusEntity.setCompanyId(userInfo.getCompanyId());
                    dataBonusEntity.setMsCreatorId(userInfo.getUserSource() == 1 ? userInfo.getId() : -1);
                    dataBonusEntity.setMsLastUpdatorId(userInfo.getUserSource() == 1 ? userInfo.getId() : -1);
                    dataBonusEntity.setCreatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
                    dataBonusEntity.setLastUpdatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
                    dataBonusEntity.setCreateTime(new Date());
                    dataBonusEntity.setLastUpdateTime(new Date());
                    dataBonusEntity.setEname(bonusExcel.getEname());
                    dataBonusEntity.setEnumber(bonusExcel.getEnumber());
                    dataBonusEntity.setMonth(monthList.get(i));
                    dataBonusEntity.setRemark(bonusExcel.getRemark());
                    if (i == (length - 1)) {
                        dataBonusEntity.setBeginDay(1);
                        calendar.setTime(bonusExcel.getEndTime());
                        dataBonusEntity.setEndDay(calendar.get(Calendar.DAY_OF_MONTH));
                        dataBonusEntity.setBonus(total);
                    } else if (i == 0) {
                        calendar.setTime(bonusExcel.getStartTime());
                        dataBonusEntity.setBeginDay(calendar.get(Calendar.DAY_OF_MONTH));
                        dataBonusEntity.setEndDay(calendar.getActualMaximum(Calendar.DATE));
                        dataBonusEntity.setBonus(averageMonthBonus.multiply(first).setScale(18, 2));
                        total = total.subtract(dataBonusEntity.getBonus());
                    } else {
                        calendar.setTime(monthList.get(i));
                        dataBonusEntity.setBeginDay(1);
                        dataBonusEntity.setEndDay(calendar.getActualMaximum(Calendar.DATE));
                        dataBonusEntity.setBonus(averageMonthBonus.setScale(18, 2));
                        total = total.subtract(dataBonusEntity.getBonus());
                    }
                    dataBonusEntity.setCreateTime(now);
                    dataBonusEntity.setLastUpdateTime(now);
                    dataBonusEntities.add(dataBonusEntity);
                }
            } catch (Exception ex) {
                logger.error("构造导入奖金数据出错", ex);
            }
        }
        if (dataBonusEntities.size() > 0) {
            TransactionStatus transactionStatus = TransactionUtils.newTransaction();
            try {
                List<List<DataBonusEntity>> insertList = ListUtils.subList(dataBonusEntities, Constant.MAX_INSERT_OR_UPDATE_COUNT);
                for (List<DataBonusEntity> items : insertList) {
                    dataBonusDao.insertOrUpdate(items);
                }
                TransactionUtils.commit(transactionStatus);
            } catch (Exception ex) {
                TransactionUtils.rollback(transactionStatus);
                logger.error("importData", ex);
                throw new OwnerException(ErrorEnum.FAIL);
            }
        }
        return returnMsg;
    }

    @Override
    public List<BonusExcel> exportBonusData(DataBonusQuery dataBonusQuery) {
        return dataBonusDao.exportBonusData(dataBonusQuery);
    }
}
