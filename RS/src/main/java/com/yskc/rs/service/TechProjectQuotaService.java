package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.rs.entity.tech.TechProjectQuotaEntity;
import com.yskc.rs.models.UserInfo;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: wangxing
 * @CreateTime: 2019-09-24 08:50
 * @Description: ProjectQuotaService
 */
public interface TechProjectQuotaService {
    /**
     * 获取项目改造指标
     *
     * @param companyId
     * @param projectId
     * @return
     */
    TechProjectQuotaEntity getQuota(Integer companyId, Integer projectId);

    /**
     * 保存项目改造指标
     *
     * @param info
     * @param projectQuotaEntity
     * @return
     * @throws OwnerException
     */
    Integer save(UserInfo info, TechProjectQuotaEntity projectQuotaEntity) throws OwnerException;


}
