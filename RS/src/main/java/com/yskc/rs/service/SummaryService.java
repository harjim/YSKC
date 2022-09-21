package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.rs.entity.data.CompanyCostEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.activity.ModifyModel;
import com.yskc.rs.models.aggregation.ExportTermModel;
import com.yskc.rs.models.project.RdSalaryDetailModel;
import com.yskc.rs.models.summary.RdSituationModel;
import com.yskc.rs.models.summary.SimpleSummaryModel;
import io.swagger.models.auth.In;
import org.apache.poi.hpsf.Decimal;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-19 14:43
 * @Description: 费用汇总业务层接口
 */
public interface SummaryService {


    /**
     * 获取项目研发费用
     *
     * @param projectId
     * @param year
     * @return
     */
    Map<String, Object> getRdFundsByProjectId(Integer projectId, Integer year);

    /**
     * 获取项目月研发费用(按类别)
     *
     * @param companyId
     * @param rdYear
     * @param rdMonth
     * @param rdTypes
     * @param projectId
     * @return
     */
    List<SimpleSummaryModel> getRdSummaryByMonth(Integer companyId, Integer rdYear, Date rdMonth, int[] rdTypes, Integer projectId);

    /**
     * 获取研究开发项目情况
     *
     * @param companyId
     * @param year
     * @return
     */
    List<RdSituationModel> rdSituation(Integer companyId, Integer year);

    /**
     * 研究开发项目活动情况
     *
     * @param companyId
     * @param year
     * @return
     */
    Map<String, Object> rdActive(Integer companyId, Integer year);

    /**
     * 导出研究开发项目情况
     *
     * @param companyId
     * @param year
     * @param out
     */
    void exportSituation(Integer companyId, Integer year, OutputStream out);

    /**
     * 导出研究开发项目活动情况
     *
     * @param companyId
     * @param year
     * @param out
     */
    void exportActive(Integer companyId, Integer year, OutputStream out);

    /**
     * 保存公司成本
     *
     * @param userInfo
     * @param costEntity
     * @return
     * @throws OwnerException
     */
    Boolean saveCompanyCost(UserInfo userInfo, CompanyCostEntity costEntity) throws OwnerException;

    /**
     * 获取公司年度成本
     *
     * @param year
     * @param userInfo
     * @return
     */
    List<CompanyCostEntity> getCompanyCostList(Integer year, UserInfo userInfo);


    /**
     * 保存项目政府资金
     *
     * @param userInfo
     * @param rdSituationModel
     * @return
     */
    Boolean saveProjectGovCost(UserInfo userInfo, RdSituationModel rdSituationModel);


    /**
     * 保存项目研发情况
     *
     * @param userInfo
     * @param modifyModel
     * @return
     */
    Boolean saveActivity(UserInfo userInfo, ModifyModel modifyModel);

    /**
     * 获取项目科目薪资明细
     *
     * @param month
     * @param userInfo
     * @return
     */
    List<RdSalaryDetailModel> getRdSalaryDetail(Date month, UserInfo userInfo);

    /**
     * 根据年获取每月的研发费用统计
     *
     * @param year
     * @param companyId
     * @return
     */
    Map<String, Object> getSummaryCostByYear(Integer year, Integer companyId);

    /**
     * 导出研发费用
     *
     * @param exportTerm
     * @param info
     * @param out
     * @throws OwnerException
     */
    void exportFunds(ExportTermModel exportTerm, UserInfo info, OutputStream out) throws OwnerException;

    /**
     * 导出归集明细表(按项目/费用类型)
     *
     * @param year
     * @param info
     * @param exportType
     * @param out
     * @throws OwnerException
     */
    void exportDataDetail(Integer year, UserInfo info, Integer exportType, OutputStream out) throws OwnerException;

    /**
     * 归集明细表(按项目/费用类型)
     * @param year
     * @param userInfo
     * @param exportType
     * @return
     */
    List<Map<String, Object>> getDataDetail(Integer year, UserInfo userInfo, Integer exportType);

    /**
     * 导出归集明细表
     *
     * @param year
     * @param userInfo
     * @param out
     * @param type 归集数据类型(0所有100研发工资)
     */
    void exportSubsidiaryLedger(Integer year, UserInfo userInfo, OutputStream out, Integer type);


    /**
     * 导出归集总表
     *
     * @param year
     * @param userInfo
     * @param out
     */
    void exportSummaryReport(Integer year, UserInfo userInfo, OutputStream out);

    /**
     * 导出研发薪资明细
     *
     * @param month
     * @param userInfo
     * @param out
     */
    void exportRdSalaryDetail(Date month, UserInfo userInfo, OutputStream out);

    /**
     * 导出rd费用数据
     *
     * @param projectId
     * @param year
     * @param userInfo
     * @param out
     */
    void exportByRD(Integer projectId, Integer year, UserInfo userInfo, OutputStream out);

    /**
     * 导出月份费用数据
     *
     * @param month
     * @param year
     * @param userInfo
     * @param out
     */
    void exportByMonth(Date month,Integer year, UserInfo userInfo, OutputStream out);

    /**
     * 获取项目归集数据
     * @param projectId
     * @param year
     * @param companyId
     * @return
     */
    List<Map<String, Object>> getProjectSummary(Integer projectId, Integer year, Integer companyId);

    /**
     * 按年获取项目总费用支出
     * @param projectIds
     * @param year
     * @return
     */
    Map<Integer,Map<String,BigDecimal>> getFundByYearAndProId(List<Integer> projectIds, Integer year);
}
