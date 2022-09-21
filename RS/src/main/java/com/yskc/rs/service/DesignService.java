package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.design.DesignModel;
import com.yskc.rs.models.design.DesignQuery;
import com.yskc.rs.models.excel.DesginExcel;
import com.yskc.rs.models.projectdesign.QueryProjectDesignModel;

import java.util.List;

/**
 * @author Administrator
 */
public interface DesignService {
    /**
     * 分页查询设计费用
     *
     * @param companyId
     * @param designQuery
     * @return
     */
    PageModel<List<DesignModel>> queryDesignEntity(int companyId, DesignQuery designQuery);

    /**
     * 删除设计费用
     *
     * @param dModel
     * @return
     * @throws OwnerException
     */
    boolean delDesign(DesignModel dModel) throws OwnerException;

    /**
     * 修改设计费用
     *
     * @param userInfo
     * @param dModel
     * @return
     * @throws OwnerException
     */
    boolean updateDesign(UserInfo userInfo, DesignModel dModel) throws OwnerException;

    /**
     * 新增设计费用
     *
     * @param currentUserId
     * @param info
     * @param dModel
     * @return
     * @throws OwnerException
     */
    boolean addDesign(int currentUserId, UserInfo info, DesignModel dModel) throws OwnerException;

    /**
     * 导出数据
     *
     * @param compantyId
     * @param designQuery
     * @return
     * @throws OwnerException
     */
    List<DesginExcel> exportDesignModel(Integer compantyId, DesignQuery designQuery) throws OwnerException;

    /**
     * 导入数据
     *
     * @param info
     * @param desginExcels
     * @return
     * @throws OwnerException
     */
    boolean importDesign(UserInfo info, List<DesginExcel> desginExcels, Integer year) throws OwnerException;

    /**
     * 根据条件查询研发费用(项目选择用)
     *
     * @param companyId
     * @param query
     * @return
     */
    PageModel<List<DesignModel>> queryDesignByTerm(Integer companyId, QueryProjectDesignModel query);

    /**
     * 修改研发部门
     *
     * @param info
     * @param model
     * @return
     */
    boolean updateDesignRdDept(UserInfo info, DesignModel model);

    /**
     * 批量删除
     *
     * @param models
     * @return
     */
    boolean delDesignBatch(List<DesignModel> models) throws OwnerException;


}
