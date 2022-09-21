package com.yskc.rs.dao.company;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.company.CompanyHoliday;
import com.yskc.rs.models.company.CompanyHolidayModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao.company
 * @Author: zhangdingfu
 * @CreateTime: 2021-01-05 08:54
 * @Description: 公司节假日dao层
 */
@Repository
public interface CompanyHolidayDao extends BaseMapper<CompanyHoliday> {
    /**
     * 获取节假日
     *
     * @param yearBegin
     * @param yearEnd
     * @param companyId
     * @return
     */
    List<CompanyHolidayModel> getHolidays(@Param("yearBegin") Date yearBegin, @Param("yearEnd") Date yearEnd,
                                          @Param("companyId") Integer companyId);

    /**
     * 插入或更新
     *
     * @param list
     * @return
     */
    int insertOrUpdate(@Param("list") List<CompanyHoliday> list);

    /**
     * 获取月份假期
     *
     * @param month
     * @param companyId
     * @return
     */
    CompanyHolidayModel getMonthHoliday(@Param("month") Date month,@Param("companyId") Integer companyId);
}
