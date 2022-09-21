package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.company.CompanyHolidayModel;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2021-01-05 09:02
 * @Description: 节假日业务接口层
 */
public interface CompanyHolidayService {
    /**
     * 获取节假日
     *
     * @param year
     * @param companyId
     * @return
     */
    List<CompanyHolidayModel> getHolidays(Integer year, Integer companyId);

    /**
     * 保存节假日
     *
     * @param list
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    Boolean saveHoliday(List<CompanyHolidayModel> list, UserInfo userInfo) throws OwnerException;
}
