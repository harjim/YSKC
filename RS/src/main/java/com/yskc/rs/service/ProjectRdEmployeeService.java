package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.PageResult;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.accountingrdsalary.AccountingRdSalaryModel;
import com.yskc.rs.models.aggregation.AggFeeModel;
import com.yskc.rs.models.employee.RdEmployeeSummaryModel;
import com.yskc.rs.models.projectrdemployee.BatchProjectRdEmployeeModel;
import com.yskc.rs.models.projectrdemployee.QueryProjectRdEmployeeModel;
import com.yskc.rs.models.projectrdemployee.RdEmployeeAggModel;
import com.yskc.rs.models.projectrdemployee.SalaryRdFeeInfoModel;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2020-03-31 11:03
 * @Description: 项目人员费用业务层
 */
public interface ProjectRdEmployeeService extends TrialAggService {

    /**
     * 获取人员费用列表
     *
     * @param userInfo
     * @param query
     * @return
     */
    PageResult getList(UserInfo userInfo, QueryProjectRdEmployeeModel query);


    /**
     * 保存项目人员费用
     *
     * @param userInfo
     * @param batchModel
     * @return
     * @throws OwnerException
     */
    Boolean saveList(UserInfo userInfo, BatchProjectRdEmployeeModel batchModel) throws OwnerException;


    /**
     * 按月查询项目成员工作时长
     *
     * @param userInfo
     * @return
     */
    PageModel<List<RdEmployeeSummaryModel>> queryListByMonth(UserInfo userInfo, QueryProjectRdEmployeeModel model);


    /**
     * 按年查询项目成员工作时长
     *
     * @param userInfo
     * @param model
     * @return
     */
    List<RdEmployeeSummaryModel> queryListByYear(UserInfo userInfo, QueryProjectRdEmployeeModel model);


    /**
     * 导入研发人员工时
     *
     * @param info
     * @param batchModel
     * @param year
     * @return
     * @throws OwnerException
     */
    Boolean importRdHour(UserInfo info, BatchProjectRdEmployeeModel batchModel, int year) throws OwnerException;

    /**
     * 插入summary
     *
     * @param now
     * @param projectIds
     * @param months
     * @param userInfo
     * @param filterZero 不存入无归集的数据
     */
    void insertSummary(Date now, List<Integer> projectIds, List<Date> months, UserInfo userInfo, Boolean filterZero);

    /**
     * 获取研发工资核算列表
     *
     * @param info
     * @param query
     * @return
     */
    PageResult getRdAccountingList(UserInfo info, QueryProjectRdEmployeeModel query);

    /**
     * 核算研发工资
     *
     * @param userInfo
     * @param accountingRdSalary
     * @return
     * @throws OwnerException
     */
    Boolean accountingRdSalary(UserInfo userInfo, AccountingRdSalaryModel accountingRdSalary) throws OwnerException;

    /**
     * 核算所有人员研发工资
     *
     * @param userInfo
     * @param accountingRdSalary
     * @return
     * @throws OwnerException
     */
    Boolean accountingAllRdSalary(UserInfo userInfo, AccountingRdSalaryModel accountingRdSalary) throws OwnerException;

    /**
     * 获取研发花名册及关联的项目列表
     *
     * @param companyId
     * @param query
     * @return
     */
    PageModel<List<RdEmployeeAggModel>> getRdEmployeeRdsList(Integer companyId, QueryProjectRdEmployeeModel query);

    /**
     * 获取研发归集配置
     *
     * @param type
     * @param month
     * @param companyId
     * @return
     */
    String getRdAggConfig(Integer type, Date month, Integer companyId);

    /**
     * 归集费用
     *
     * @param aggFee
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    Boolean aggFee(AggFeeModel aggFee, UserInfo userInfo) throws OwnerException;

    /**
     * 刷新人员归集费用
     *
     * @param aggFee
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    Boolean refreshFee(AggFeeModel aggFee, UserInfo userInfo) throws OwnerException;

    /**
     * 根据月份获取所有人员工时
     *
     * @param userInfo
     * @param model
     * @return
     */
    PageModel<List<RdEmployeeSummaryModel>> getMonthTotalStaff(UserInfo userInfo, QueryProjectRdEmployeeModel model);

    /**
     * 计划工时归集人员类型费用(全删全增式)
     *
     * @param aggFee
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    Boolean aggRdPlanFee(AggFeeModel aggFee, UserInfo userInfo) throws OwnerException;

    /**
     * 获取费用明细
     * @param query
     * @return
     */
    List<SalaryRdFeeInfoModel> getFeeDetail(QueryProjectRdEmployeeModel query);
}

