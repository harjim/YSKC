package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.entity.tech.TechProjectCostEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.TechProjectCostExcel;
import com.yskc.rs.models.tech.cost.QueryTechProjectCostModel;
import com.yskc.rs.models.tech.cost.TechProjectCostModel;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2019-09-25 08:24
 * @Description: 项目支出业务接口层
 */
public interface TechProjectCostService {

    /**
     * 查询项目支出
     *
     * @param companyId
     * @param query
     * @return
     */
    PageModel<List<TechProjectCostModel>> getList(Integer companyId, QueryTechProjectCostModel query);

    /**
     * 添加项目支出
     *
     * @param userInfo
     * @param techProjectCostModel
     * @return
     */
    boolean add(UserInfo userInfo, TechProjectCostModel techProjectCostModel);

    /**
     * 更新项目支出
     *
     * @param userInfo
     * @param techProjectCostModel
     * @return
     */
    boolean update(UserInfo userInfo, TechProjectCostModel techProjectCostModel);

    /**
     * 删除项目支出
     *
     * @param id
     * @return
     */
    boolean del(Integer id);

    /**
     * 删除多条项目支出
     *
     * @param techProjectCostEntities
     * @return
     */
    boolean dels(List<TechProjectCostEntity> techProjectCostEntities);

    /**
     * 导入项目支出
     *
     * @param userInfo
     * @param data
     * @param projectId
     * @return
     * @throws OwnerException
     */
    boolean importInfo(UserInfo userInfo, List<TechProjectCostExcel> data, Integer projectId) throws OwnerException;

    /**
     * 导出项目支出明细
     *
     * @param projectId
     * @param userInfo
     * @param out
     */
    void exportCost(Integer projectId, UserInfo userInfo, OutputStream out);

    /**
     * 获取项目支出
     *
     * @param companyId
     * @param projectId
     * @return
     */
    Map<String, Object> getProjectCost(Integer companyId, Integer projectId);
}
