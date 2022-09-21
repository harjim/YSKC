package com.yskc.docservice.service.rs;

import com.yskc.docservice.models.rs.company.CompanySettingModel;

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
