package com.yskc.ms.dao.rs;

import com.yskc.ms.models.company.CompanyHolidayModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao.company
 * @Author: zhangdingfu
 * @CreateTime: 2021-01-05 08:54
 * @Description: 公司节假日dao层
 */
@Repository
public interface CompanyHolidayDao{

    /**
     * 获取月份假期
     *
     * @param month
     * @param companyId
     * @return
     */
    CompanyHolidayModel getMonthHoliday(@Param("month") Date month, @Param("companyId") Integer companyId);
}
