package com.yskc.rs.service.impl;

import com.yskc.rs.dao.KeypointStatisticDao;
import com.yskc.rs.dao.StatDao;
import com.yskc.rs.entity.KeypointStatisticEntity;
import com.yskc.rs.models.KeypointStaticModel;
import com.yskc.rs.service.StatService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StatServiceImpl implements StatService {
    @Autowired
    StatDao statDao;
    @Autowired
    private KeypointStatisticDao keypointStatisticDao;

    @Override
    public Map statProject(Integer companyId) {
        Map map = new HashMap();
        // 从project表中取项目相关数据
        map.put("p",statDao.getProjectStat(companyId));
        // 从summary表取项目归集数据
        map.put("s",statDao.getProjectRDStat(companyId));
        // 从技术改造表中取项目数据
        map.put("t",statDao.getTechProjectStat(companyId));
        return map;
    }

    @Override
    public KeypointStaticModel getStat() {
        KeypointStatisticEntity entity = keypointStatisticDao.selectById(1);
        KeypointStaticModel model = new KeypointStaticModel();
        BeanUtils.copyProperties(entity,model);
        return model;
    }
}
