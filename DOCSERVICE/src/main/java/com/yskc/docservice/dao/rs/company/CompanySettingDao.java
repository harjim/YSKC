package com.yskc.docservice.dao.rs.company;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.company.CompanySettingEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao
 * @Author: zhangdingfu
 * @CreateTime: 2021-02-03 11:43
 * @Description: 系统设置-客户设置dao层
 */
@Repository
public interface CompanySettingDao extends BaseMapper<CompanySettingEntity> {  /**
 * 获取系统设置-客户设置
 *
 * @param companyId
 * @return
 */
CompanySettingEntity getSetting(@Param("companyId") Integer companyId);
}
