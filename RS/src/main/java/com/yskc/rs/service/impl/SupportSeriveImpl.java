package com.yskc.rs.service.impl;

import cn.hutool.core.date.DateUtil;
import com.yskc.rs.dao.SupportDao;
import com.yskc.rs.entity.SupportEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.service.SupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: wangxing
 * @CreateTime: 2019-09-25 11:31
 * @Description: SupportServiceImpl
 */
@Service
public class SupportSeriveImpl implements SupportService {
    @Autowired
    private SupportDao supportDao;


    @Override
    public List<SupportEntity> querySupportList(Integer companyId, Date checkTime, String supportTime, String supportDeptName, String projectName) {
         if(checkTime!=null){
             checkTime=  DateUtil.beginOfDay(checkTime);
         }
        return supportDao.querySupportList(companyId, checkTime,supportTime, supportDeptName, projectName);
    }

    @Override
    public boolean updateSupport(UserInfo info, SupportEntity entity) {
        entity.setLastUpdatorId(info.getId());
        entity.setLastUpdateTime(new Date());
        return supportDao.updateById(entity) > 0;
    }

    @Override
    public boolean delSupport(Integer id) {
        return supportDao.deleteById(id)>0;
    }

    @Override
    public boolean addSupport(UserInfo info, SupportEntity entity) {
        Integer userId = info.getId();
        Date date = new Date();
        entity.setCreatorId(userId);
        entity.setCreateTime(date);
        entity.setLastUpdatorId(userId);
        entity.setLastUpdateTime(date);
        entity.setCompanyId(info.getCompanyId());
        return supportDao.insert(entity) > 0;

    }


}
