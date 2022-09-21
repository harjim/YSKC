package com.yskc.docservice.service.impl.rs;

import cn.hutool.core.bean.BeanUtil;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.DateUtil;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.dao.rs.CustomerAttendanceDao;
import com.yskc.docservice.dao.rs.EmployeeDao;
import com.yskc.docservice.entity.rs.CustomerAttendanceEntity;
import com.yskc.docservice.entity.rs.EmployeeEntity;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.CustomerAttendanceExcel;
import com.yskc.docservice.service.rs.CustomerAttendanceService;
import com.yskc.docservice.utils.ListUtils;
import com.yskc.docservice.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-06 09:35
 * @Description: 员工考勤业务实现层
 */
@Service
public class CustomerAttendanceServiceImpl implements CustomerAttendanceService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CustomerAttendanceDao customerAttendanceDao;
    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public void importAttendance(RsUserInfo userInfo, List<CustomerAttendanceExcel> data) throws OwnerException {
        // 去重
        Map<String, CustomerAttendanceExcel> dataMap = new HashMap<>();
        data.forEach(item -> {
            String key = item.getEnumber() + DateUtil.format(item.getWorkDate(), DateUtil.DEFAULT_DATE_FORMAT);
            //不覆盖旧值
            if (!dataMap.containsKey(key)) {
                dataMap.put(key, item);
            }
        });
        data = new ArrayList<>(dataMap.size());
        data.addAll(dataMap.values());
        List<String> enumberList = data.stream().filter(a -> !StringUtils.isEmpty(a.getEnumber())).map(CustomerAttendanceExcel::getEnumber).collect(Collectors.toList());
        List<EmployeeEntity> existEmployeeList = employeeDao.getByNumbers(userInfo.getCompanyId(), enumberList);
        Map<String, Integer> existEmployeeMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(existEmployeeList)) {
            existEmployeeList.forEach(item -> existEmployeeMap.put(item.getEnumber(), item.getId()));
        }
        Date now = new Date();
        List<CustomerAttendanceEntity> insertAttendanceList = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            CustomerAttendanceExcel excel = data.get(i);
            boolean checkHour = excel.getWorkHour().intValue() > 24 || excel.getWorkHour().intValue() < 0;
            if (checkHour) {
                throw new OwnerException(MessageFormat.format(
                        "工号【{0}】，姓名【{1}】,考勤日期【{2}】工时不能大于24且不能小于0",
                        excel.getEnumber(), excel.getEname(), DateUtil.format(excel.getWorkDate(), DateUtil.DEFAULT_DATE_FORMAT)));
            }
            String checkWorkTime = checkWorkTime(excel);
            if (!StringUtils.isEmpty(checkWorkTime)) {
                throw new OwnerException(checkWorkTime);
            }
            String enumber = excel.getEnumber();
            if (!existEmployeeMap.containsKey(enumber)) {
                throw new OwnerException(MessageFormat.format(
                        "工号【{0}】，姓名【{1}】,不存在花名册中，请先在花名册中添加该人员。",
                        excel.getEnumber(), excel.getEname()));
            }
            CustomerAttendanceEntity customerAttendanceEntity = new CustomerAttendanceEntity();
            BeanUtil.copyProperties(excel, customerAttendanceEntity);
            setAttendanceBase(userInfo, customerAttendanceEntity, now);
            insertAttendanceList.add(customerAttendanceEntity);
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
        try {

            if (!CollectionUtils.isEmpty(insertAttendanceList)) {
                List<List<CustomerAttendanceEntity>> insertList = ListUtils.subList(insertAttendanceList, Constant.MAX_INSERT_OR_UPDATE_COUNT);
                for (List<CustomerAttendanceEntity> items : insertList) {
                    customerAttendanceDao.insertOrUpdate(items);
                }
            }
            TransactionUtils.commit(DataSourceEnum.RS,transactionStatus);
        } catch (Exception e) {
            logger.error("导入员工考勤失败", e);
            TransactionUtils.rollback(DataSourceEnum.RS,transactionStatus);
            throw new OwnerException("保存数据失败");
        }

    }

    private String checkWorkTime(CustomerAttendanceExcel excel) {
        long offTime1 = excel.getOffTime1().getTime();
        if (excel.getOnTime1().getTime() > offTime1) {
            return MessageFormat.format(
                    Constant.ATTENDANCE_TIME_ERROR,
                    excel.getEnumber(), excel.getEname(), DateUtil.format(excel.getWorkDate(), DateUtil.DEFAULT_DATE_FORMAT), "上班时间1不能大于下班时间1");
        }
        if (null != excel.getOnTime2() && null != excel.getOffTime2()) {
            long onTime2 = excel.getOnTime2().getTime();
            if (onTime2 < offTime1) {
                return MessageFormat.format(
                        Constant.ATTENDANCE_TIME_ERROR,
                        excel.getEnumber(), excel.getEname(), DateUtil.format(excel.getWorkDate(),
                                DateUtil.DEFAULT_DATE_FORMAT), "上班时间2不能小于下班时间1");
            }
            if (onTime2 > excel.getOffTime2().getTime()) {
                return MessageFormat.format(
                        Constant.ATTENDANCE_TIME_ERROR,
                        excel.getEnumber(), excel.getEname(),
                        DateUtil.format(excel.getWorkDate(), DateUtil.DEFAULT_DATE_FORMAT), "上班时间2不能大于下班时间2");
            }
        } else if ((null != excel.getOnTime2() && null == excel.getOffTime2()) ||
                (null == excel.getOnTime2() && null != excel.getOffTime2())) {
            return MessageFormat.format(
                    Constant.ATTENDANCE_TIME_ERROR,
                    excel.getEnumber(), excel.getEname(),
                    DateUtil.format(excel.getWorkDate(), DateUtil.DEFAULT_DATE_FORMAT), "上班时间2、下班时间2必须同时存在数据。");
        }
        if (null != excel.getOnTime3() && null != excel.getOffTime3()) {
            long onTime3 = excel.getOnTime3().getTime();
            if (onTime3 < offTime1) {
                return MessageFormat.format(
                        Constant.ATTENDANCE_TIME_ERROR,
                        excel.getEnumber(), excel.getEname(),
                        DateUtil.format(excel.getWorkDate(), DateUtil.DEFAULT_DATE_FORMAT), "上班时间3不能小于下班时间1");
            }
            if (null != excel.getOnTime2()) {
                if (onTime3 < excel.getOffTime2().getTime()) {
                    return MessageFormat.format(
                            Constant.ATTENDANCE_TIME_ERROR,
                            excel.getEnumber(), excel.getEname(),
                            DateUtil.format(excel.getWorkDate(), DateUtil.DEFAULT_DATE_FORMAT), "上班时间3不能小于下班时间2");
                }
            }
            if (onTime3 > excel.getOffTime3().getTime()) {
                return MessageFormat.format(
                        Constant.ATTENDANCE_TIME_ERROR,
                        excel.getEnumber(), excel.getEname(),
                        DateUtil.format(excel.getWorkDate(), DateUtil.DEFAULT_DATE_FORMAT), "上班时间3不能大于下班时间3");
            }

        } else if ((null != excel.getOnTime3() && null == excel.getOffTime3()) ||
                (null == excel.getOnTime3() && null != excel.getOffTime3())) {
            return MessageFormat.format(
                    Constant.ATTENDANCE_TIME_ERROR,
                    excel.getEnumber(), excel.getEname(),
                    DateUtil.format(excel.getWorkDate(), DateUtil.DEFAULT_DATE_FORMAT), "上班时间3、下班时间3必须同时存在数据。");
        }
        return "";
    }

    /**
     * 设置用户id
     *
     * @param userInfo
     * @param entity
     * @param now
     */
    private void setAttendanceBase(RsUserInfo userInfo, CustomerAttendanceEntity entity, Date now) {
        entity.setLastUpdatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
        entity.setMsLastUpdatorId(userInfo.getUserSource() == 1 ? userInfo.getId() : -1);
        entity.setCreatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
        entity.setMsCreatorId(userInfo.getUserSource() == 1 ? userInfo.getId() : -1);
        entity.setCreateTime(now);
        entity.setLastUpdateTime(now);
        entity.setCompanyId(userInfo.getCompanyId());
    }
}
