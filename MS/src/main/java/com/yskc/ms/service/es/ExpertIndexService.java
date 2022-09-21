package com.yskc.ms.service.es;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.newexpert.index.ConfigDataModel;
import com.yskc.ms.models.newexpert.index.ConfigModuleModel;
import com.yskc.ms.models.newexpert.index.QueryConfigDataModel;
import com.yskc.ms.models.newexpert.index.QueryConfigModuleModel;

import java.util.List;

/**
 * @DateTime: 2021/9/26 16:15
 * @Description:
 * @author: hsx
 */
public interface ExpertIndexService {

    /**
     * @param ids 批量删除专家库首页数据
     * @return
     */
    Boolean delData(List<Integer> ids);

    /**
     * 分页查询专家库首页数据
     *
     * @param query
     * @return
     */
    PageModel<List<ConfigDataModel>> getDataList(QueryConfigDataModel query);

    /**
     * 批量删除专家库首页模板
     *
     * @param ids
     * @return
     */
    Boolean delModule(List<Integer> ids);

    /**
     * 分页查询专家库首页模板
     *
     * @param query
     * @return
     */
    PageModel<List<ConfigModuleModel>> getModuleList(QueryConfigModuleModel query);

    /**
     * 添加首页数值配置数据
     *
     * @param model
     * @param userInfo
     * @return
     */
    Boolean addConfigData(ConfigDataModel model, UserInfo userInfo);

    /**
     * 编辑首页数值配置数据
     *
     * @param model
     * @param userInfo
     * @return
     */
    Boolean editConfigData(ConfigDataModel model, UserInfo userInfo);

    /**
     * 禁用/启用es数值配置数据
     *
     * @param model
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    Boolean switchConfigData(ConfigDataModel model, UserInfo userInfo) throws OwnerException;

    /**
     * 禁用/启用es首页模块配置
     *
     * @param model
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    Boolean switchConfigModule(ConfigModuleModel model, UserInfo userInfo) throws OwnerException;

    /**
     * 添加es首页模块配置
     *
     * @param model
     * @param userInfo
     * @return
     */
    Boolean addConfigModule(ConfigModuleModel model, UserInfo userInfo);

    /**
     * 编辑es首页模块配置
     *
     * @param model
     * @param userInfo
     * @return
     */
    Boolean editConfigModule(ConfigModuleModel model, UserInfo userInfo);
}
