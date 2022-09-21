package com.xxl.job.executor.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.rs.CompanySettingEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

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
     * 获取客户账期
     *
     * @param months
     * @return
     */
    List<CompanySettingEntity> getAccountPeriods(@Param("months") List<Date> months);

}
