package com.yskc.rs.service;

import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.accountingrdsalary.AccountPeriodModel;
import com.yskc.rs.models.company.CompanySettingModel;

import java.util.Date;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2021-02-03 11:45
 * @Description: 系统设置-客户设置业务接口层
 */
public interface CompanySettingService {
    /**
     * 获取系统设置-客户设置,没有设置将插入一个默认设置
     *
     * @param companyId
     * @return
     */
    CompanySettingModel getSetting(Integer companyId);

    /**
     * 保存设置
     *
     * @param key
     * @param map
     * @param info
     * @return
     */
    Boolean saveSetting(String key, Map<String, Object> map, UserInfo info);

    /**
     * 获取账期区间
     *
     * @param month
     * @param companyId
     * @return
     */
    AccountPeriodModel getAccountPeriod(Date month, Integer companyId);

    /**
     * 当已获取setting时，通过配置项获取账期区间
     *
     * @param month
     * @param accountPeriod
     * @return
     */
    AccountPeriodModel getAccountPeriod(Date month, Map<String, Object> accountPeriod);

    /**
     * 获取设备工时位数
     *
     * @param companyId
     * @return
     */
    Integer getEquipmentHourBit(Integer companyId);

    /**
     * 获取人员工时位数
     *
     * @param companyId
     * @return
     */
    Integer getEmployeeHourBit(Integer companyId);
}
