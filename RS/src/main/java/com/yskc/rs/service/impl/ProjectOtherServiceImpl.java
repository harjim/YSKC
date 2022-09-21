package com.yskc.rs.service.impl;

import com.yskc.rs.dao.tech.TechProjectOtherDao;
import com.yskc.rs.entity.tech.ProjectOtherEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.tech.ProjectOtherModel;
import com.yskc.rs.service.ProjectOtherService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 项目other 业务实现层
 *
 * @author zhangdingfu
 */
@Service
public class ProjectOtherServiceImpl implements ProjectOtherService {
    @Autowired
    TechProjectOtherDao techProjectOtherDao;

    @Override
    public Integer saveOther(UserInfo userInfo, ProjectOtherModel model) {
        Date date = new Date();
        Integer userId = userInfo.getId();
        String[] addressCode = model.getAddressCode();
        String areaCode = "";
        for (int i = 0; i < addressCode.length; i++) {
            areaCode += addressCode[i];
            if (i != addressCode.length - 1) {
                areaCode += ",";
            }
        }
        model.setAreaCode(areaCode);
        ProjectOtherEntity entity = new ProjectOtherEntity();
        if (model.getId() != null && model.getId() != 0) {
            entity = techProjectOtherDao.selectById(model.getId());
            BeanUtils.copyProperties(model, entity);
            entity.setLastUpdatorId(userId);
            entity.setLastUpdateTime(date);
            techProjectOtherDao.updateById(entity);
        } else {
            BeanUtils.copyProperties(model, entity);
            entity.setCreatorId(userId);
            entity.setCreateTime(date);
            entity.setLastUpdatorId(userId);
            entity.setLastUpdateTime(date);
            entity.setCompanyId(userInfo.getCompanyId());
            techProjectOtherDao.insert(entity);
        }
        return entity.getId();
    }

    @Override
    public ProjectOtherModel queryAll(Integer companyId, Integer projectId) {
        ProjectOtherModel projectOtherModel = techProjectOtherDao.selectByProjectId(companyId, projectId);
        if (projectOtherModel != null) {
            String areaCode = projectOtherModel.getAreaCode();
            projectOtherModel.setAddressCode(areaCode.split(","));
        }
        return projectOtherModel;
    }
}
