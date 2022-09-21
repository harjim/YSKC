package com.yskc.rs.service.impl;

import java.util.Date;

import com.yskc.rs.dao.ProjectImplementDao;
import com.yskc.rs.entity.tech.ProjectImplementEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.service.ProjectImplementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: wangxing
 * @CreateTime: 2019-09-24 08:30
 * @Description: 项目实效效果实现类
 */
@Service
public class ProjectImplementServiceImpl implements ProjectImplementService {

    @Autowired
    private ProjectImplementDao projectImplementDao;

    @Override
    public ProjectImplementEntity queryImplement(Integer companyId, Integer projectId) {
        return projectImplementDao.queryImplement(companyId, projectId);
    }

    @Override
    public Integer addProjectImplement(UserInfo userInfo, ProjectImplementEntity entity) {
        Date date = new Date();
        Integer userId = userInfo.getId();
        if(entity.getId()!=0){
            entity.setLastUpdatorId(userId);
            entity.setLastUpdateTime(date);
            projectImplementDao.updateById(entity);
        }else{
            entity.setCreatorId(userId);
            entity.setCreateTime(date);
            entity.setLastUpdatorId(userId);
            entity.setLastUpdateTime(date);
            entity.setCompanyId(userInfo.getCompanyId());
            projectImplementDao.insert(entity);
        }
        return entity.getId();
    }

}
