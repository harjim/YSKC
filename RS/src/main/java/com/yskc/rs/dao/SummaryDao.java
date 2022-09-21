package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.SummaryEntity;
import com.yskc.rs.models.rdAgg.AggDetailModel;
import com.yskc.rs.models.company.CountBudgetModel;
import com.yskc.rs.models.company.CountRdFundModel;
import com.yskc.rs.models.finance.AuditFinanceModel;
import com.yskc.rs.models.highscore.HighTechScoreModel;
import com.yskc.rs.models.project.ProjectBudgetModel;
import com.yskc.rs.models.project.ProjectInfoModel;
import com.yskc.rs.models.project.RdSalaryDetailModel;
import com.yskc.rs.models.project.TypeModel;
import com.yskc.rs.models.summary.*;
import com.yskc.rs.models.voucher.UpdateAccount;
import com.yskc.rs.models.voucher.VoucherInfo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Description: summary 汇总 dao层
 * @Author: zhangdingfu
 * @date: 2019-07-19
 */
@Repository
public interface SummaryDao extends BaseMapper<SummaryEntity> {

    /**
     * 根据项目id + 费用类型 获取汇总数据
     *
     * @param projectId
     * @param rdType
     * @return
     */
    List<SummaryEntity> getByProjectIdAndRdType(@Param("projectId") Integer projectId, @Param("rdType") int rdType);

    /**
     * 按月份获取研发费用
     *
     * @param companyId
     * @param rdYear
     * @param rdMonth
     * @param rdTypes
     * @param projectId
     * @return
     */
    List<SimpleSummaryModel> getRdSummaryByMonth(@Param("companyId") Integer companyId, @Param("rdYear") Integer rdYear, @Param("rdMonth") Date rdMonth, @Param("rdTypes") int[] rdTypes, @Param("projectId") Integer projectId);

    /**
     * 插入或者更新
     *
     * @param summaryEntities
     */
    void insertOrUpdate(@Param("summaryEntities") List<SummaryEntity> summaryEntities);

    /**
     * 删除
     *
     * @param projectId
     * @param month
     * @param rdTypeList
     */
    void deleteInfo(@Param("projectId") Integer projectId, @Param("month") Date month, @Param("rdTypeList") List<Integer> rdTypeList);

    /**
     * 根据项目ID和类型删除汇总信息
     *
     * @param projectIds
     * @param rdTypeList
     * @return
     */
    int deleteByProjectIdAndType(@Param("projectIds") List<Integer> projectIds, @Param("rdTypeList") List<Integer> rdTypeList);

    /**
     * 删除为0数据
     *
     * @param projectId
     * @param months
     */
    int delRdFundsForZero(@Param("projectId") Integer projectId, @Param("months") List<Date> months);

    /**
     * 获取项目研发费用（单项目）
     *
     * @param projectId
     * @param beginMonth
     * @param endMonth
     * @return
     */
    List<RdFundsModel> getRdFundsByProjectId(@Param("projectId") Integer projectId, @Param("beginMonth") Date beginMonth,
                                             @Param("endMonth") Date endMonth);


    /**
     * 获取项目研发费用（多项目）
     *
     * @param companyId
     * @param beginMonth
     * @param endMonth
     * @param projectId(可空，指定项目查单个)
     * @return
     */
    List<DataFundsModel> getDataReportFunds(@Param("companyId") Integer companyId, @Param("beginMonth") Date beginMonth,
                                            @Param("endMonth") Date endMonth, @Param("projectId") Integer projectId);


    /**
     * 获取项目归集数据根据类型（多项目）
     *
     * @param companyId
     * @param beginMonth
     * @param endMonth
     * @param rdTypes
     * @return
     */
    List<DataFundsModel> getDataByRdType(@Param("companyId") Integer companyId, @Param("beginMonth") Date beginMonth,
                                         @Param("endMonth") Date endMonth, @Param("rdTypes") List<Integer> rdTypes);


    /**
     * 获取研究开发项目情况
     *
     * @param companyId
     * @param year
     * @return
     */
    List<RdSituationModel> getRdSituation(@Param("companyId") Integer companyId, @Param("year") Integer year);


    /**
     * 获取研究开发项目人员数和总工时
     *
     * @param beginMonth
     * @param endMonth
     * @return
     */
    List<MiniRdSituationModel> getRdSituationEmployee(@Param("beginMonth") Date beginMonth, @Param("endMonth") Date endMonth);

    /**
     * 研究开发项目活动情况
     *
     * @param companyId
     * @param yearBegin
     * @param yearEnd
     * @return
     */
    Map<String, Object> getRdActive(@Param("companyId") Integer companyId, @Param("yearBegin") Date yearBegin, @Param("yearEnd") Date yearEnd);

    /**
     * 获取开发项目活动费用
     *
     * @param companyId
     * @param year
     * @param beginMonth
     * @param endMonth
     * @return
     */
    Map<String, Object> getActiveSummary(@Param("companyId") Integer companyId, @Param("year") Integer year,
                                         @Param("beginMonth") Date beginMonth, @Param("endMonth") Date endMonth);

    /**
     * 获取项目研发费用（不分费用类别）
     *
     * @param projectId
     * @return
     */
    List<TypeModel> querySummaryByProjectId(@Param("projectId") Integer projectId);

    List<TypeModel> getByProjectIds(@Param("projectIds") List<Integer> projectIds);

    /**
     * 根据费用类型获取归集数据
     *
     * @param companyId
     * @param projectId
     * @param month
     * @param rdTypes
     * @return
     */
    List<VoucherInfo> getVoucherInfoList(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId, @Param("month") Date month, @Param("rdTypes") List<Integer> rdTypes);

    /**
     * 更新科目
     *
     * @param updateAccount
     * @return
     */
    Integer updateAccount(@Param("updateAccount") UpdateAccount updateAccount);

    List<OtherSummaryModel> queryByYearAndProjectIds(@Param("beginYear") String beginYear, @Param("endYear") String endYear, @Param("projectIds") List<Integer> projectIds);


    /**
     * 获取项目科目薪资明细
     *
     * @param month
     * @param projectIds
     * @param companyId
     * @return
     */
    List<RdSalaryDetailModel> getAccountSalaryDetail(@Param("month") Date month, @Param("projectIds") List<Integer> projectIds,
                                                     @Param("companyId") Integer companyId);

    /**
     * 获取项目基础信息
     *
     * @param begin
     * @param end
     * @param companyId
     * @return
     */
    List<ProjectInfoModel> getProjectInfo(@Param("begin") Date begin, @Param("end") Date end, @Param("companyId") Integer companyId);

    /**
     * 根据年获取每月的研发费用统计
     *
     * @param beginMonth
     * @param endMOnth
     * @param companyId
     * @return
     */
    List<Map<String, Object>> getSummaryCostByYear(@Param("beginMonth") Date beginMonth, @Param("endMonth") Date endMOnth, @Param("companyId") Integer companyId);

    /**
     * 获取公司各项费用总计
     *
     * @param beginMonth
     * @param endMonth
     * @param companyId
     * @return
     */
    Map<String, BigDecimal> getSummaryByCompany(@Param("beginMonth") Date beginMonth, @Param("endMonth") Date endMonth,
                                                @Param("companyId") Integer companyId, @Param("year") Integer year);

    /**
     * 获取公司各个项目费用总计
     *
     * @param beginMonth
     * @param endMonth
     * @param projectIds
     * @return
     */
    @MapKey("projectId")
    Map<Integer, Map<String, BigDecimal>> getSummaryByProjectList(@Param("beginMonth") Date beginMonth, @Param("endMonth") Date endMonth,
                                                                  @Param("projectIds") Set<Integer> projectIds, @Param("year") Integer year);

    /**
     * 获取各个项目各个月份费用
     *
     * @param beginMonth
     * @param endMonth
     * @param projectId
     * @return
     */
    @MapKey("month")
    Map<Integer, Map<String, BigDecimal>> getSummaryByProject(@Param("beginMonth") Date beginMonth, @Param("endMonth") Date endMonth,
                                                              @Param("projectId") Integer projectId, @Param("year") Integer year);

    /**
     * 获取年费用
     *
     * @param companyId
     * @param beginMonth
     * @param endMonth
     * @return
     */
    List<DataFundsModel> getYearFunds(@Param("companyId") Integer companyId, @Param("beginMonth") Date beginMonth,
                                      @Param("endMonth") Date endMonth);

    /**
     * 获取研发情况费用
     *
     * @param companyId
     * @param beginMonth
     * @param endMonth
     * @return
     */
    List<RdSituationModel> getRdSituationFunds(@Param("companyId") Integer companyId, @Param("beginMonth") Date beginMonth,
                                               @Param("endMonth") Date endMonth);

    /**
     * 获取项目归集数
     *
     * @param projectId
     * @param beginDate 开始归集月
     * @param endDate   结束归集月
     * @return
     */
    List<ProjectBudgetModel> getProjectSummary(@Param("projectId") Integer projectId, @Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

    /**
     * 根据年获取归集数据
     *
     * @param projectId
     * @param year
     * @return
     */
    List<ProjectBudgetModel> getBudgetByYear(@Param("projectId") Integer projectId, @Param("year") Integer year);

    /**
     * 获取项目最大归集月
     *
     * @param projectId
     * @return
     */
    Map<String, Date> getMinMaxMonth(@Param("projectId") Integer projectId);

    /**
     * 项目归集总数（年份）
     *
     * @param firstDay
     * @param lastDay
     * @param projectIds
     * @return
     */
    List<ProjectBudgetModel> getSummaryByYear(@Param("firstDay") Date firstDay, @Param("lastDay") Date lastDay, @Param("projectIds") List<Integer> projectIds);

    /**
     * 统计存在的归集费用
     *
     * @param begin
     * @param end
     * @param projectId
     * @return
     */
    int countExist(@Param("begin") Date begin, @Param("end") Date end, @Param("projectId") Integer projectId);

    /**
     * 删除项目
     *
     * @param projectIds
     * @return
     */
    int deleteByProjectIds(@Param("projectIds") List<Integer> projectIds);

    /**
     * 获取集团或分公司归集费[月份区间]
     *
     * @param begin
     * @param end
     * @param year
     * @param companyId
     * @param groupFullPath
     * @return
     */
    List<DataFundsModel> getGroupFunds(@Param("begin") Date begin, @Param("end") Date end, @Param("year") Integer year,
                                       @Param("companyId") Integer companyId, @Param("groupFullPath") String groupFullPath);

    /**
     * 统计项目预算
     *
     * @param projectIds
     * @param year
     * @param companyId
     * @return
     */
    List<CountRdFundModel> countFunds(@Param("projectIds") List<Integer> projectIds, @Param("year") Integer year, @Param("companyId") Integer companyId);

    /**
     * 获取年归集费用
     *
     * @param projectIds
     * @param beginOfYear
     * @param endOfYear
     * @return
     */
    List<CountRdFundModel> getDataByYear(@Param("projectIds") List<Integer> projectIds, @Param("beginOfYear") Date beginOfYear, @Param("endOfYear") Date endOfYear);

    /**
     * 按月获取公司归集数
     *
     * @param projectIds
     * @param beginOfYear
     * @param endOfYear
     * @return
     */
    List<CountBudgetModel> getFundByMonth(List<Integer> projectIds, Date beginOfYear, Date endOfYear);

    /**
     * 按年获取项目总费用支出
     *
     * @param projectIds
     * @param beginDate
     * @param endDate
     * @return
     */
    @MapKey("projectId")
    Map<Integer, Map<String, BigDecimal>> getFundByYearAndProId(@Param("projectIds") List<Integer> projectIds, @Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

    /**
     * 获取时间段内Rd的个数和归集费用
     *
     * @param start
     * @param end
     * @param companyId
     * @return
     */
    HighTechScoreModel getRdCntAndRdFunds(@Param("start") Date start, @Param("end") Date end, @Param("companyId") Integer companyId);

    /**
     * 获取项目所有的归集费用
     *
     * @param projectId
     * @return
     */
    BigDecimal getProjectRdFundsSum(@Param("projectId") Integer projectId);

    /**
     * 通过项目删除月份归集
     *
     * @param projectIds
     * @param month
     * @param rdTypes
     * @return
     */
    int deleteByProjects(@Param("projectIds") List<Integer> projectIds, @Param("month") Date month, @Param("rdTypes") ArrayList<Integer> rdTypes);

    /**
     * 获取研发费
     *
     * @param model
     * @param rdTypes
     * @return
     */
    BigDecimal getRdFunds(@Param("model") AuditFinanceModel model,@Param("rdTypes") List<Integer> rdTypes);

    /**
     * 按年、费用类型获取研发费
     *
     * @param beginMonth
     * @param endMonth
     * @param rdTypes
     * @param companyId
     * @return
     */
    List< AggDetailModel > getMonthCostByRdType(@Param("beginMonth") Date beginMonth, @Param("endMonth") Date endMonth, @Param("rdTypes")List<Integer> rdTypes, @Param("companyId")Integer companyId);
}
