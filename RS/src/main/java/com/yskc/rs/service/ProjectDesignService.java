package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.design.DesignAmountModel;
import com.yskc.rs.models.projectdesign.ProjectDesignModel;
import com.yskc.rs.models.projectdesign.QueryProjectDesignModel;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
public interface ProjectDesignService {
    /**
     * 查询项目的研发设计费用
     *
     * @param companyId
     * @param query
     * @return
     */
    PageModel<List<ProjectDesignModel>> queryProjectDesign(Integer companyId, QueryProjectDesignModel query);


    /**
     * 新增项目研发设计费用
     *
     * @param info
     * @param projectDesignModels
     * @return
     */
    boolean addProjectDesign(UserInfo info, ProjectDesignModel projectDesignModels) throws OwnerException;

    /**
     * 按条件添加研发设计费用
     *
     * @param info
     * @param eName
     * @param projectId
     * @param projectMonth
     * @param deptName
     * @return
     * @throws OwnerException
     */
    boolean addProjectDesignByTerm(UserInfo info, String eName, Integer projectId, Date projectMonth, String deptName) throws OwnerException;

    /**
     * 批量删除
     *
     * @param userInfo
     * @param model
     * @return
     * @throws OwnerException
     */
    boolean delDesignBatch(UserInfo userInfo, List<ProjectDesignModel> model) throws OwnerException;

    /**
     * 批量设置研发设计研发费用（单个研发费用不同）
     * @param userInfo
     * @param models
     * @return
     */
    Boolean setRdAmounts(UserInfo userInfo,List<ProjectDesignModel> models) throws OwnerException;

    /**
     * 批量设置研发费用(费用一致)
     * @param userInfo
     * @param model
     * @return
     */
    Boolean setDesignRdAmounts(UserInfo userInfo, DesignAmountModel model) throws OwnerException;


}
