package com.yskc.ms.service.impl.es;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.es.ConfigDataDao;
import com.yskc.ms.dao.es.ConfigModuleDao;
import com.yskc.ms.entity.es.ConfigDataEntity;
import com.yskc.ms.entity.es.ConfigModuleEntity;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.newexpert.index.ConfigDataModel;
import com.yskc.ms.models.newexpert.index.ConfigModuleModel;
import com.yskc.ms.models.newexpert.index.QueryConfigDataModel;
import com.yskc.ms.models.newexpert.index.QueryConfigModuleModel;
import com.yskc.ms.service.es.ExpertIndexService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @DateTime: 2021/9/26 16:16
 * @Description:
 * @author: hsx
 */
@Service
public class ExpertIndexServiceImpl implements ExpertIndexService {

    @Autowired
    private ConfigModuleDao configModuleDao;
    @Autowired
    private ConfigDataDao configDataDao;

    @Override
    public PageModel<List<ConfigDataModel>> getDataList(QueryConfigDataModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getDescs()) && CollectionUtils.isEmpty(page.getAscs())) {
            page.setAscs(CollUtil.newArrayList("`type`", "`order`"));
        }
        return PageUtils.build(page, configDataDao.getDataList(page, query));
    }

    @Override
    public Boolean delData(List<Integer> ids) {
        return configDataDao.deleteBatchIds(ids) > 0;
    }

    @Override
    public Boolean addConfigData(ConfigDataModel model, UserInfo userInfo) {
        Date now = new Date();
        Integer userId = userInfo.getId();
        ConfigDataEntity configData = buildConfigData(model, now, userId);
        configData.setMsCreatorId(userId);
        configData.setCreateTime(now);
        return configDataDao.insert(configData) > 0;
    }

    @Override
    public Boolean editConfigData(ConfigDataModel model, UserInfo userInfo) {
        ConfigDataEntity configData = buildConfigData(model, new Date(), userInfo.getId());
        return configDataDao.updateById(configData) > 0;
    }

    @Override
    public Boolean switchConfigData(ConfigDataModel model, UserInfo userInfo) throws OwnerException {
        Integer id = model.getId();
        ConfigDataEntity entity = configDataDao.selectById(id);
        if (entity == null) {
            throw new OwnerException("获取当前记录失败，请稍后重试。");
        }
        Date date = new Date();
        return configDataDao.updateDisabled(id, model.getDisabled(), userInfo.getId(), date) > 0;
    }


    @Override
    public PageModel<List<ConfigModuleModel>> getModuleList(QueryConfigModuleModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getDescs()) && CollectionUtils.isEmpty(page.getAscs())) {
            page.setAscs(CollUtil.newArrayList("`order`"));
        }
        return PageUtils.build(page, configModuleDao.getModuleList(page, query));
    }


    @Override
    public Boolean addConfigModule(ConfigModuleModel model, UserInfo userInfo) {
        Date now = new Date();
        Integer userId = userInfo.getId();
        ConfigModuleEntity configModule = buildConfigModule(model, now, userId);
        configModule.setMsCreatorId(userId);
        configModule.setCreateTime(now);
        return configModuleDao.insert(configModule) > 0;
    }
    @Override
    public Boolean editConfigModule(ConfigModuleModel model, UserInfo userInfo) {
        ConfigModuleEntity configModule = buildConfigModule(model, new Date(), userInfo.getId());
        return configModuleDao.updateById(configModule) > 0;
    }

    @Override
    public Boolean delModule(List<Integer> ids) {
        return configModuleDao.deleteBatchIds(ids) > 0;
    }

    @Override
    public Boolean switchConfigModule(ConfigModuleModel model, UserInfo userInfo) throws OwnerException {
        Integer id = model.getId();
        ConfigModuleEntity entity = configModuleDao.selectById(id);
        if (entity == null) {
            throw new OwnerException("获取当前记录失败，请稍后重试。");
        }
        Date date = new Date();
        return configModuleDao.updateDisabled(id, model.getDisabled(), userInfo.getId(), date) > 0;
    }


    private ConfigModuleEntity buildConfigModule(ConfigModuleModel model, Date now, Integer userId) {
        ConfigModuleEntity configModule = new ConfigModuleEntity();
        BeanUtils.copyProperties(model, configModule);
        configModule.setMsLastUpdatorId(userId);
        configModule.setLastUpdateTime(now);
        if (configModule.getOrder() == null) {
            configModule.setOrder(0);
        }
        return configModule;
    }


    private ConfigDataEntity buildConfigData(ConfigDataModel model, Date now, Integer userId) {
        ConfigDataEntity configData = new ConfigDataEntity();
        BeanUtils.copyProperties(model, configData);
        configData.setMsLastUpdatorId(userId);
        configData.setLastUpdateTime(now);
        if (configData.getOrder() == null) {
            configData.setOrder(0);
        }
        return configData;
    }
}
