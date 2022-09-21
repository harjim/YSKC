package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.PageResult;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.companyrdsummary.QueryCompanyRdSummaryModel;
import com.yskc.rs.models.equipment.QueryEquipmentModel;
import com.yskc.rs.models.hightech.HighTechModel;
import com.yskc.rs.models.hightech.QueryHighTechModel;
import com.yskc.rs.models.project.ProjectListModel;
import com.yskc.rs.models.projectrdemployee.QueryProjectRdEmployeeModel;
import com.yskc.rs.models.projectrdemployee.RdEmployeeAggModel;
import com.yskc.rs.models.rdequipment.RdEquipmentInfoModel;
import com.yskc.rs.models.sysDocument.DocListModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2021-07-05 08:36
 * @Description: 公司研发汇总业务接口层
 */
public interface CompanyRdSummaryService {
    /**
     * 获取公司研发汇总
     *
     * @param query
     * @param info
     * @return
     */
    PageResult getList(QueryCompanyRdSummaryModel query, UserInfo info);

    /**
     * 获取研发费用
     *
     * @param year
     * @param companyId
     * @param info
     * @return
     * @throws OwnerException
     */
    Map<String, BigDecimal> getGroupFunds(Integer year, Integer companyId, UserInfo info) throws OwnerException;

    /**
     * 获取RD列表
     *
     * @param year
     * @param companyId
     * @return
     * @throws OwnerException
     */
    List<ProjectListModel> getCompanyRdList(Integer year, Integer companyId) throws OwnerException;

    /**
     * 获取公司研发人员
     *
     * @param companyId
     * @param query
     * @return
     * @throws OwnerException
     */
    PageModel<List<RdEmployeeAggModel>> getCompanyRdEmployeeList(Integer companyId, QueryProjectRdEmployeeModel query) throws OwnerException;

    /**
     * 根据公司获取研发设备列表
     *
     * @param companyId
     * @param query
     * @return
     * @throws OwnerException
     */
    PageModel<List<RdEquipmentInfoModel>> getCompanyRdEquipmentList(Integer companyId, QueryEquipmentModel query) throws OwnerException;

    /**
     * 根据公司获取高品列表
     *
     * @param companyId
     * @param query
     * @return
     * @throws OwnerException
     */
    List<HighTechModel> getCompanyHighTechList(Integer companyId, QueryHighTechModel query) throws OwnerException;

    /**
     * 根据公司获取机构建设列表
     *
     * @param year
     * @param companyId
     * @param listType
     * @return
     * @throws OwnerException
     */
    List<DocListModel> getBuildList(Integer year, Integer companyId, int listType) throws OwnerException;

    /**
     * 检测子公司，非子公司抛出异常
     *
     * @param companyId
     * @param groupId
     * @param fullPath
     * @throws OwnerException
     */
    void checkChildCompany(Integer companyId, Integer groupId, String fullPath) throws OwnerException;
}
