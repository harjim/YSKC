package com.yskc.rs.service.impl;

import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.JsonUtils;
import com.yskc.common.utils.RedisUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.dao.company.CompanySettingDao;
import com.yskc.rs.entity.company.CompanySettingEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.accountingrdsalary.AccountPeriodModel;
import com.yskc.rs.models.company.CompanySettingModel;
import com.yskc.rs.service.CompanySettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.Date;
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
            redisUtils.set(key, companySettingModel, Constant.RS_USER_SESSION_TIMEOUT);
            return companySettingModel;
        }
    }


    @Override
    public Boolean saveSetting(String key, Map<String, Object> map, UserInfo info) {
        Integer companyId = info.getCompanyId();
        CompanySettingModel companySettingModel = getSetting(companyId);

        if ("accountPeriod".equals(key)) {
            companySettingModel.setAccountPeriod(map);
        }
        if ("miniProgram".equals(key)) {
            companySettingModel.setMiniProgram(map);
        }
        if ("rdRatio".equals(key)) {
            companySettingModel.setRdRatio(map);
        }
        if ("documentNo".equals(key)) {
            companySettingModel.setDocumentNo(map);
        }
        if ("hourBit".equals(key)) {
            companySettingModel.setHourBit(map);
        }
        // 保留一天
        redisUtils.set(MessageFormat.format(Constant.REDIS_COMPANY_SETTING_KEY, companyId), companySettingModel, Constant.RS_USER_SESSION_TIMEOUT);
        if (companySettingDao.countExit(companyId) > 0) {
            return companySettingDao.saveSetting(key, JsonUtils.objectToJson(map), companyId, info.getUserId(), info.getMsUserId(), new Date()) > 0;
        }
        return companySettingDao.insert(CompanySettingEntity.build(info, new Date(), companySettingModel)) > 0;
    }

    @Override
    public AccountPeriodModel getAccountPeriod(Date month, Integer companyId) {
        CompanySettingModel companySettingModel = getSetting(companyId);
        return getAccountPeriod(month, companySettingModel.getAccountPeriod());
    }

    @Override
    public AccountPeriodModel getAccountPeriod(Date month, Map<String, Object> accountPeriod) {
        Date monthBegin = DateUtil.getMonthFirstDay(month);
        Date monthEnd;
        if (CollectionUtils.isEmpty(accountPeriod)) {
            monthEnd = DateUtil.getMonthLastDay(monthBegin);
        } else {
            int accountMonth = Integer.parseInt(accountPeriod.get("accountMonth").toString());
            // 月份时间默认为1号，获取的开始日期减1，即 2021-01-01 + (1 -1) = 2021-01-01
            int day = Integer.parseInt(accountPeriod.get("day").toString()) - 1;
            // 0 上月
            if (accountMonth == 0) {
                // 结束时间为当月 + day - 1
                monthEnd = cn.hutool.core.date.DateUtil.endOfDay(cn.hutool.core.date.DateUtil.offsetDay(monthBegin, day - 1));
                // 开始时间为上月 + day
                monthBegin = cn.hutool.core.date.DateUtil.offsetDay(cn.hutool.core.date.DateUtil.offsetMonth(monthBegin, -1), day);
            } else {
                // 结束时间为下月 + day -1
                monthEnd = cn.hutool.core.date.DateUtil.endOfDay(
                        cn.hutool.core.date.DateUtil.offsetDay(
                                cn.hutool.core.date.DateUtil.offsetMonth(monthBegin, 1),
                                day - 1)
                );
                // 开始时间为当月 +day
                monthBegin = cn.hutool.core.date.DateUtil.offsetDay(monthBegin, day);
            }
        }
        AccountPeriodModel result = new AccountPeriodModel();
        result.setBegin(monthBegin);
        result.setEnd(monthEnd);
        return result;
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
