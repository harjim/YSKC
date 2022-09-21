package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.CompanySettingEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author: hck
 * @DateTime: 2021/3/26 13:42
 * @Description:
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
}
