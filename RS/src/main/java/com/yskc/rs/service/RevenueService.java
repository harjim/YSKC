package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.revenuemanage.AnnualRevenueModel;
import com.yskc.rs.models.revenuemanage.RevenueModel;

import java.util.List;
import java.util.Map;

/**
 * @DateTime: 2022/1/14 8:41
 * @Description:
 * @author: hsx
 */
public interface RevenueService {

    /**
     * 获取年度营收
     * @param year
     * @param companyId
     * @return
     */
    List<AnnualRevenueModel> getList(Integer year, Integer companyId);

    /**
     * 新增年度营收
     * @param model
     * @param info
     * @return
     */
    Boolean add(AnnualRevenueModel model, UserInfo info) throws Exception;

    /**
     * 修改年度营收
     * @param model
     * @param info
     * @return
     */
    Boolean edit(AnnualRevenueModel model, UserInfo info) throws Exception;

    /**
     * 删除年度营收
     * @param year
     * @param companyId
     * @return
     */
    Boolean del(Integer year, Integer companyId);

    /**
     * 根据年份获取当年数据
     * @param year
     * @param companyId
     * @return
     */
    AnnualRevenueModel getData(Integer year, Integer companyId);

}
