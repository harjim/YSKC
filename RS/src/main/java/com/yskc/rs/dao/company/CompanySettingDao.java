package com.yskc.rs.dao.company;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.company.CompanySettingEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao
 * @Author: zhangdingfu
 * @CreateTime: 2021-02-03 11:43
 * @Description: 系统设置-客户设置dao层
 */
@Repository
public interface CompanySettingDao extends BaseMapper<CompanySettingEntity> {
    /**
     * 获取系统设置-客户设置
     *
     * @param companyId
     * @return
     */
    CompanySettingEntity getSetting(@Param("companyId") Integer companyId);

    /**
     * 保存设置
     *
     * @param col
     * @param setting
     * @param companyId
     * @param userId
     * @param msUserId
     * @param now
     * @return
     */
    int saveSetting(@Param("col") String col, @Param("setting") String setting,
                    @Param("companyId") Integer companyId, @Param("userId") Integer userId,
                    @Param("msUserId") Integer msUserId,@Param("now") Date now);

    /**
     * 统计存在设置数
     * @param companyId
     * @return
     */
    int countExit(@Param("companyId") Integer companyId);
}
