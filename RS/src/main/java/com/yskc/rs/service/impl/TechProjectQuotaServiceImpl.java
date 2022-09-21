package com.yskc.rs.service.impl;

import java.util.Date;

import com.yskc.common.exception.OwnerException;
import com.yskc.rs.dao.tech.TechProjectQuotaDao;
import com.yskc.rs.entity.tech.TechProjectQuotaEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.service.TechProjectQuotaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: wangxing
 * @CreateTime: 2019-09-24 08:51
 * @Description: ProjectQuotaServiceImpl
 */
@Service
public class TechProjectQuotaServiceImpl implements TechProjectQuotaService {

    @Autowired
    private TechProjectQuotaDao techProjectQuotaDao;

    @Override
    public TechProjectQuotaEntity getQuota(Integer companyId, Integer projectId) {
        return techProjectQuotaDao.getQuota(companyId, projectId);
    }

    @Override
    public Integer save(UserInfo info, TechProjectQuotaEntity projectQuotaEntity) throws OwnerException {
        Date now = new Date();
        if (projectQuotaEntity.getId() == null) {
            projectQuotaEntity.setCompanyId(info.getCompanyId());
            projectQuotaEntity.setCreatorId(info.getId());
            projectQuotaEntity.setLastUpdatorId(info.getId());
            projectQuotaEntity.setCreateTime(now);
            projectQuotaEntity.setLastUpdateTime(now);
            techProjectQuotaDao.insert(projectQuotaEntity);
        } else {
            projectQuotaEntity.setLastUpdatorId(info.getId());
            projectQuotaEntity.setLastUpdateTime(now);
            techProjectQuotaDao.updateById(projectQuotaEntity);
        }
        if (projectQuotaEntity.getId() == null || projectQuotaEntity.getId() <= 0) {
            throw new OwnerException("保存失败,请稍后再试。");
        }
        return projectQuotaEntity.getId();
    }

}
