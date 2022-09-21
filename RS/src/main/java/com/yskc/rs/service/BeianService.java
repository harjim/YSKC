package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.tech.BeianInfoModel;
import com.yskc.rs.models.tech.InvestmentModel;
import com.yskc.rs.models.tech.QueryBeianModel;

import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/3/18 15:45
 * @Description:
 */
public interface BeianService {
    /**
     * 获取备案列表
     * @param companyId
     * @param query
     * @return
     */
    PageModel<List<BeianInfoModel>> getList(Integer companyId, QueryBeianModel query);

    /**
     * 编辑备案信息
     * @param userInfo
     * @param model
     * @return
     */
    Boolean save(UserInfo userInfo, BeianInfoModel model) throws OwnerException;

    /**
     * 获取备案信息
     * @param companyId
     * @param beianId
     * @return
     */
    BeianInfoModel getBeianInfo(Integer companyId, Integer beianId);
}
