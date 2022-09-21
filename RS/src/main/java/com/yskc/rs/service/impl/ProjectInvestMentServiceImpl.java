package com.yskc.rs.service.impl;

import com.yskc.rs.dao.tech.TechProjectInvestMentDao;
import com.yskc.rs.entity.tech.ProjectInvestMentEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.service.ProjectInvestMentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProjectInvestMentServiceImpl implements ProjectInvestMentService {
    @Autowired
    TechProjectInvestMentDao techProjectInvestMentDao;


    @Override
    public ProjectInvestMentEntity selectByProjectId(Integer companyId, Integer projectId) {

        return techProjectInvestMentDao.selectByProjectId(companyId,projectId);
    }

    @Override
    public Integer saveInvestMent(UserInfo userInfo, ProjectInvestMentEntity model) {
        Date date = new Date();
        Integer userId = userInfo.getId();
        if(model.getId()!=0){
            model.setLastUpdatorId(userId);
            model.setLastUpdateTime(date);
            techProjectInvestMentDao.updateById(model);
        }else{
            model.setCreatorId(userId);
            model.setCreateTime(date);
            model.setLastUpdatorId(userId);
            model.setLastUpdateTime(date);
            model.setCompanyId(userInfo.getCompanyId());
            techProjectInvestMentDao.insert(model);
        }
        return model.getId();
    }
}
