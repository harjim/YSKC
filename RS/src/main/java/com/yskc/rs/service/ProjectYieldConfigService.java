package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.entity.project.ProjectYieldConfigEntity;
import com.yskc.rs.models.BatchDeleteModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.projectyieldconfig.*;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2020-09-02 18:16
 * @Description: 项目试制量配置业务接口层
 */
public interface ProjectYieldConfigService {
    /**
     * 添加试制量配置
     *
     * @param userInfo
     * @param config
     * @return
     * @throws OwnerException
     */
    boolean add(UserInfo userInfo, ProjectYieldConfigEntity config) throws OwnerException;

    /**
     * 编辑试制量配置
     *
     * @param userInfo
     * @param config
     * @return
     * @throws OwnerException
     */
    boolean edit(UserInfo userInfo, ProjectYieldConfigEntity config) throws OwnerException;

    /**
     * 删除试制量配置
     *
     * @param model
     * @return
     * @throws OwnerException
     */
    boolean delete(BatchDeleteModel model) throws OwnerException;

    /**
     * 获取试制量列表
     *
     * @param query
     * @return
     */
    List<ProjectYieldConfigModel> getList(QueryYieldConfigModel query);

    /**
     * 应用试制量配置
     *
     * @param userInfo
     * @param config
     * @return
     * @throws OwnerException
     */
    boolean handleRd(UserInfo userInfo, QueryYieldConfigModel config) throws OwnerException;

    /**
     * 导入试制量配置
     *
     * @param userInfo
     * @param data
     * @param projectId
     * @return
     */
    boolean importYield(UserInfo userInfo, List<ProjectYieldConfigEntity> data, Integer projectId) throws OwnerException;

    PageModel<List<ProjectYieldConfigModel>> queryPYieldConfigList(QueryYieldConfigParams param, Integer companyId);

    void exportPlan(QueryYieldConfigParams param, UserInfo info, OutputStream out) throws OwnerException;

    /**
     * 刷新研发试制计划
     *
     * @param refresh
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    Boolean refreshYieldConfig(RefreshYieldConfigModel refresh, UserInfo userInfo) throws OwnerException;

    /**
     * 获取刷新研发试制计划配置
     *
     * @param companyId
     * @return
     */
    RefreshYieldConfigModel getTrialConfig(Integer companyId);

    /**
     * 获取刷新研发试制计划配置
     *
     * @param projectId,companyId,companyId
     * @return
     */
    Map<String,List<String>> getDate(Integer projectId, Date month, Integer companyId);

    /**
     * 批量修改状态
     *
     * @param model
     * @param info
     * @return
     */
    Boolean editSelected(UpdateSelectedModel model, UserInfo info);

    /**
     * 试验试制通知单引入试制时间和发出时间
     *
     * @param model
     * @return
     */
    Map<String, String> calculateDate(QueryYieldConfigModel model);

    /**
     * 归集费用
     *
     * @param aggModel
     * @param userInfo
     * @throws OwnerException
     * @return
     */
    List<AggMsgModel> aggFee(TrialAggModel aggModel, UserInfo userInfo)throws OwnerException;
}
