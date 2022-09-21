package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.RdAccountDetailExcel;
import com.yskc.rs.models.rdaccountdetail.QueryRdAccountDetailModel;
import com.yskc.rs.models.rdaccountdetail.RdAccountDetailModel;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2019-09-30 10:54
 * @Description: 研发费用明细业务的接口
 */
public interface RdAccountDetailService {
    /**
     * 获取费用明细
     *
     * @param companyId
     * @param query
     * @return
     */
    PageModel<List<RdAccountDetailModel>> getList(Integer companyId, QueryRdAccountDetailModel query);

    /**
     * 添加研发费用明细
     *
     * @param userInfo
     * @param rdAccountDetailModel
     * @return
     */
    boolean add(UserInfo userInfo, RdAccountDetailModel rdAccountDetailModel);

    /**
     * 更新研发费用明细
     *
     * @param userInfo
     * @param rdAccountDetailModel
     * @return
     */
    boolean update(UserInfo userInfo, RdAccountDetailModel rdAccountDetailModel);

    /**
     * 删除研发费用明细
     *
     * @param rdAccountDetailModel
     * @return
     */
    boolean del(RdAccountDetailModel rdAccountDetailModel);


    /**
     * 批量删除研发费用明细
     *
     * @param rdAccountDetailModels
     * @return
     */
    boolean dels(List<RdAccountDetailModel> rdAccountDetailModels);

    /**
     * 导入研发费用明细
     *
     * @param userInfo
     * @param rdAccountDetailExcels
     * @param accType
     * @return
     * @throws OwnerException
     */
    boolean importInfo(UserInfo userInfo, List<RdAccountDetailExcel> rdAccountDetailExcels, int accType) throws OwnerException;
}
