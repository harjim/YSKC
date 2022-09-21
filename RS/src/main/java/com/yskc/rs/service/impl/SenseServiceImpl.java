package com.yskc.rs.service.impl;

import com.yskc.common.exception.OwnerException;
import com.yskc.rs.dao.SenseDao;
import com.yskc.rs.entity.SenseEntity;
import com.yskc.rs.models.SenseModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.service.SenseService;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @DateTime: 2021/10/12 16:43
 * @Description:
 * @author: hsx
 */
@Service
public class SenseServiceImpl implements SenseService {

    @Autowired
    private SenseDao senseDao;

    @Override
    public Map<Integer, List<SenseModel>> getSenses(Integer companyId) {
        List<SenseEntity> entities = senseDao.getSenseByCompanyId(companyId);
        if (null == entities && entities.isEmpty()) {
            return null;
        }
        Map<Integer, List<SenseModel>> map = new HashMap<>();
        for (SenseEntity entity : entities) {
            Integer type = entity.getType();
            if (!map.containsKey(type)) {
                List<SenseModel> list = new ArrayList<>();
                map.put(type, list);
                SenseModel model = entityConvert2Model(entity);
                list.add(model);
            } else {
                SenseModel model = entityConvert2Model(entity);
                map.get(type).add(model);
            }
        }
        return map;
    }

    @Override
    public boolean delSense(List<Integer> ids) {
        return senseDao.deleteBatchIds(ids)>0;
    }

    @Override
    public boolean editSense(SenseModel model , UserInfo info) throws OwnerException{
        Integer id = senseDao.sensDuplicateChecking(model.getType(), model.getPreachDate(), info.getCompanyId(),model.getId());
        if (null != id) {
            throw new OwnerException("该类型下已存在相同时间段，请勿重复添加");
        }
        Date now = new Date();
        SenseEntity entity = modelConvert2Entity(model);
        entity.setId(model.getId());
        entity.update(info.getUserId(), info.getMsUserId(),now);
        return senseDao.updateSenseById(entity)>0;
    }

    @Override
    public boolean addSense(SenseModel model , UserInfo info) throws OwnerException{
        Integer id = senseDao.sensDuplicateChecking(model.getType(), model.getPreachDate(), info.getCompanyId(), null);
        if (null != id) {
            throw new OwnerException("该类型下已存在相同时间段，请勿重复添加");
        }
        Date now = new Date();
        SenseEntity entity = modelConvert2Entity(model);
        entity.create(info.getUserId(), info.getMsUserId(), now);
        entity.setType(model.getType());
        entity.setCompanyId(info.getCompanyId());
        return senseDao.insert(entity)>0;
    }

    private SenseModel entityConvert2Model(SenseEntity entity) {
        SenseModel model = new SenseModel();
        model.setId(entity.getId());
        if (null != entity.getRds() && entity.getRds() != "") {
            model.setRds(Arrays.asList(entity.getRds().split(",")));
        }
        if (null != entity.getFilePaths() && entity.getFilePaths() != "") {
            model.setFilePaths(Arrays.asList(entity.getFilePaths().split(",")));
        }
        model.setPreachDate(entity.getPreachDate());
        model.setRemark(entity.getRemark());
        model.setType(entity.getType());
        model.setLastUpdateTime(entity.getLastUpdateTime());
        return model;
    }

    private SenseEntity modelConvert2Entity(SenseModel model) {
        SenseEntity entity = new SenseEntity();
        if (null != model.getRds() && !model.getRds().isEmpty()) {
            entity.setRds(StringUtil.join(model.getRds().toArray(), ","));
        }
        entity.setPreachDate(model.getPreachDate());
        if (null != model.getFilePaths() && !model.getFilePaths().isEmpty()) {
            entity.setFilePaths(StringUtil.join(model.getFilePaths().toArray(),","));
        } else {
            entity.setFilePaths("");
        }
        entity.setRemark(model.getRemark());

        return entity;
    }
}
