package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.hightech.QueryInspectionModel;
import com.yskc.rs.models.projectinspection.*;

import java.util.List;

/**
 * @author Administrator
 */
public interface ProjectInspectionService {
    /**
     * 获取项目费用数据
     *
     * @param companyId
     * @param query
     * @return
     */
    PageModel<List<ProjectInspectionModel>> queryProjectInspection(Integer companyId, QueryProjectInspectionModel query);

    /**
     * 获取高新进度其他费用明细表
     * @param query
     * @return
     */
    PageModel<List<HighTechInspectionModel>> getProjectInspection(QueryInspectionModel query);


    /**
     * 添加项目费用数据
     *
     * @param info
     * @param batch
     * @return
     * @throws OwnerException
     */
    boolean addProjectInspection(UserInfo info, BatchProjectInspectionModel batch) throws OwnerException;

    /**
     * 按条件添加费用数据
     *
     * @param info
     * @param query
     * @return
     * @throws OwnerException
     */
    boolean addProjectInspectionByTerm(UserInfo info,QueryProjectInspectionModel query) throws OwnerException;

    /**
     * 获取费用
     *
     * @param companyId
     * @param projectId
     * @param type
     * @param accDate
     * @param summary
     * @param deptName
     * @return
     */
    // ProjectInspectionModel getProjectInspectionCount(Integer companyId, Integer projectId, String[] type, Date accDate, Date projectMonth, String summary, String deptName);

    /**
     * 批量删除
     *
     * @param userInfo
     * @param batch
     * @return
     * @throws OwnerException
     */
    boolean delInspectionBatch(UserInfo userInfo, BatchProjectInspectionModel batch) throws OwnerException;

    /**
     * 批量设置研发费用（费用一致）
     * @param userInfo
     * @param batch
     * @return
     */
    boolean setInspectAmounts(UserInfo userInfo, SetInspectionAmountModel batch) throws OwnerException;

    /**
     * 批量设置研发费用（费用不一致）
     * @param userInfo
     * @param batch
     * @return
     * @throws OwnerException
     */
    boolean setRdAmounts(UserInfo userInfo, SetInspectionAmountModel batch) throws OwnerException;
}
