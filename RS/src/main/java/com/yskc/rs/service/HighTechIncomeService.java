package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.hightech.BindHighTechModel;
import com.yskc.rs.models.hightech.HighTechIncomeModel;
import com.yskc.rs.models.hightech.QueryHighTechIncomeModel;

import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/5/28 8:18
 * @Description:
 */
public interface HighTechIncomeService {
    /**
     * 获取高品收入
     *
     * @param companyId
     * @param query
     * @return
     */
    PageModel<List<HighTechIncomeModel>> getList(Integer companyId, QueryHighTechIncomeModel query);

    /**
     * 添加高品收入
     *
     * @param userInfo
     * @param income
     * @return
     */
    boolean add(UserInfo userInfo, HighTechIncomeModel income);

    /**
     * 编辑高品收入
     *
     * @param userInfo
     * @param income
     * @return
     */
    boolean edit(UserInfo userInfo, HighTechIncomeModel income);

    /**
     * 删除高品收入
     *
     * @param ids
     * @return
     * @throws OwnerException
     */
    boolean deleteList(List<Integer> ids) throws OwnerException;

    /**
     * 导入高品收入
     *
     * @param userInfo
     * @param data
     * @return
     */
    boolean importIncome(UserInfo userInfo, List<HighTechIncomeModel> data) throws OwnerException;

    /**
     * 获取导出的高品收入
     *
     * @param companyId
     * @param query
     * @return
     */
    List<HighTechIncomeModel> getExportIncome(Integer companyId, QueryHighTechIncomeModel query);

    /**
     * 高品收入关联高新产品
     *
     * @param userInfo
     * @param bind
     * @return
     */
    boolean bindHighTech(UserInfo userInfo, BindHighTechModel bind)throws OwnerException;
}
