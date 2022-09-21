package com.yskc.rs.service.impl;

import com.yskc.rs.dao.FieldConfigDao;
import com.yskc.rs.entity.FieldConfigEntity;
import com.yskc.rs.enums.FieldConfigTypeEnum;
import com.yskc.rs.models.fieldconfig.FieldConfigModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.service.FieldConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: rs
 * @description: 配置明细
 * @author: cyj
 * @create: 2022-06-14 10:53
 **/
@Service
public class FieldConfigServiceImpl implements FieldConfigService {
    @Autowired
    private FieldConfigDao fieldConfigDao;

    @Override
    public boolean addFieldConfigCol(FieldConfigModel model, UserInfo userInfo) {
        Date now = new Date();
        FieldConfigEntity entity = FieldConfigEntity.build(model.getId(), userInfo.getCompanyId(),
                model.getConfig(), model.getNumber(), now, userInfo.getMsUserId(), userInfo.getUserId(),
                model.getType());
        if (entity.getId() == null || entity.getId() <= 0) {
            entity.setMsCreatorId(userInfo.getMsUserId());
            entity.setCreatorId(userInfo.getUserId());
            entity.setCreateTime(now);
            return fieldConfigDao.insert(entity) > 0;
        } else {
            return fieldConfigDao.updateConfig(entity) > 0;
        }
    }

    @Override
    public boolean editFieldConfigCol(FieldConfigModel model, UserInfo userInfo) {
        return addFieldConfigCol(model, userInfo);
    }

    @Override
    public FieldConfigModel getTypeConfig(Integer companyId, Integer type) {
        FieldConfigModel config = fieldConfigDao.getTypeConfig(companyId, type);
        if (config == null && type == FieldConfigTypeEnum.INSURANCE.getType()) {
            config = fieldConfigDao.getDefault();
        }
        if (config == null) {
            config = new FieldConfigModel();
            config.setConfig("[]");
        }
        return config;
    }
}
