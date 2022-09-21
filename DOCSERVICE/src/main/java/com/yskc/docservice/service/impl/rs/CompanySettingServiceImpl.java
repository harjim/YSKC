package com.yskc.docservice.service.impl.rs;

import com.yskc.common.utils.RedisUtils;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.dao.rs.company.CompanySettingDao;
import com.yskc.docservice.entity.rs.company.CompanySettingEntity;
import com.yskc.docservice.models.rs.company.CompanySettingModel;
import com.yskc.docservice.service.rs.CompanySettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: zhangdingfu
 * @CreateTime: 2021-02-03 11:46
 * @Description: 系统设置-客户设置业务实现层
 */
@Service
public class CompanySettingServiceImpl implements CompanySettingService {

    @Autowired
    private CompanySettingDao companySettingDao;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public CompanySettingModel getSetting(Integer companyId) {
        String key = MessageFormat.format(Constant.REDIS_COMPANY_SETTING_KEY, companyId);
        if (redisUtils.hasKey(key)) {
            CompanySettingModel model = redisUtils.get(key, CompanySettingModel.class);
            if (model.getDocumentNo() == null) {
                model.setDocumentNo(new HashMap<>());
            }
            if (model.getHourBit() == null) {
                model.setHourBit(new HashMap<>());
            }
            return model;
        } else {
            CompanySettingEntity companySetting = companySettingDao.getSetting(companyId);
            CompanySettingModel companySettingModel = new CompanySettingModel();
            // 不存在设置，插入一个默认设置（空设置）
            if (companySetting == null) {
                companySettingModel.loadDefault();
            } else {
                companySettingModel.buildSetting(companySetting);
            }
            // 保留一天
            redisUtils.set(key, companySettingModel, Constant.ONE_DAY_TIME);
            return companySettingModel;
        }
    }

    @Override
    public Integer getEquipmentHourBit(Integer companyId) {
        return getHourBit("equipment", companyId);
    }

    @Override
    public Integer getEmployeeHourBit(Integer companyId) {
        return getHourBit("employee", companyId);
    }

    private int getHourBit(String key, Integer companyId) {
        Map<String, Object> map = getSetting(companyId).getHourBit();
        if (!CollectionUtils.isEmpty(map) && !StringUtils.isEmpty(map.get(key))) {
            return Integer.parseInt(map.get(key).toString());
        }

        return 1;
    }

}
