package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.employeePlan.PlanTimeModel;
import com.yskc.rs.models.employeePlan.QueryPlanModel;
import com.yskc.rs.models.employeePlan.RdEmployeePlanModel;
import com.yskc.rs.models.employeePlan.RdEmployeePlanTotalModel;
import com.yskc.rs.models.excel.RdEmployeePlanExcel;
import com.yskc.rs.models.excel.RdPlanExcel;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/11/16 17:19
 * description:
 */
public interface RdEmployeePlanService {
    /**
     * 保存研发人员计划
     *
     * @param userInfo
     * @param models
     * @return
     */
    boolean save(UserInfo userInfo, List<RdEmployeePlanModel> models) throws OwnerException;

    /**
     * 查询研发人员计划
     *
     * @param companyId
     * @param query
     * @return
     */
    PageModel<List<PlanTimeModel>> getList(Integer companyId, QueryPlanModel query);

    /**
     * 获取人员计划工时汇总
     *
     * @param companyId
     * @param query
     * @return
     */
    PageModel<List<RdEmployeePlanTotalModel>> getTotalList(Integer companyId, QueryPlanModel query);

    /**
     * 导入研发人员计划
     *
     * @param info
     * @param data
     * @param year
     * @param projectId
     * @return
     */
    Boolean importPlan(UserInfo info, List<RdEmployeePlanExcel> data, int year, Integer projectId) throws OwnerException;

    /**
     * 导出研发人员计划（单项目多月）
     *
     * @param year
     * @param months
     * @param userInfo
     * @throws OwnerException
     */
    void exportPlan(Integer year, Date[] months, UserInfo userInfo, OutputStream out) throws OwnerException;

    /**
     * 导入研发人员计划（多项目多月）
     *
     * @param info
     * @param data
     * @return
     */
    Boolean importPlanTime(UserInfo info, List<RdPlanExcel> data) throws OwnerException;
}
