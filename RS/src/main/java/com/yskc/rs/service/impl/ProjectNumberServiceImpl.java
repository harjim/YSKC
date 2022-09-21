package com.yskc.rs.service.impl;

import com.yskc.rs.dao.ProjectNumberDao;
import com.yskc.rs.entity.ProjectNumberEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.service.ProjectNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProjectNumberServiceImpl implements ProjectNumberService {

    @Autowired
    private ProjectNumberDao projectNumberDao;

    @Override
    public Boolean setProjectNum(ProjectNumberEntity entity, UserInfo userInfo) {
        ProjectNumberEntity projectNumberEntity = projectNumberDao.queryByYear(userInfo.getCompanyId(), entity.getYear());
        if (projectNumberEntity == null) {
            projectNumberEntity = new ProjectNumberEntity();
            projectNumberEntity.setCompanyId(userInfo.getCompanyId());
            projectNumberEntity.setYear(entity.getYear());
            projectNumberEntity.setProjectNum(entity.getProjectNum());
            projectNumberEntity.setCreateTime(new Date());
            projectNumberEntity.setLastUpdateTime(new Date());
            projectNumberEntity.setCreatorId(userInfo.getUserId());
            projectNumberEntity.setMsCreatorId(userInfo.getMsUserId());
            projectNumberEntity.setLastUpdatorId(userInfo.getUserId());
            projectNumberEntity.setMsLastUpdatorId(userInfo.getMsUserId());
            projectNumberDao.insert(projectNumberEntity);
        } else {
            projectNumberEntity.setProjectNum(entity.getProjectNum());
            projectNumberEntity.setLastUpdatorId(userInfo.getUserId());
            projectNumberEntity.setMsLastUpdatorId(userInfo.getMsUserId());
            projectNumberEntity.setLastUpdateTime(new Date());
            projectNumberDao.updateById(projectNumberEntity);
        }
        return true;
    }

    @Override
    public Integer queryProjectNum(Integer companyId, Integer year) {
        ProjectNumberEntity projectNumberEntity = projectNumberDao.queryByYear(companyId, year);
        Integer num = -1;
        if (projectNumberEntity != null) {
            num = projectNumberEntity.getProjectNum();
        }
        return num;
    }
}
