package com.yskc.rs.dao.tech;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.tech.TechProjectQuotaEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
/**
 * 
 * 
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-09-24 08:47:37
 */
@Repository
public interface TechProjectQuotaDao extends BaseMapper<TechProjectQuotaEntity> {

    /**
     * 获取项目改造指标
     * @param companyId
     * @param projectId
     * @return
     */
    TechProjectQuotaEntity getQuota(@Param("companyId") Integer companyId,@Param("projectId") Integer projectId);
}
