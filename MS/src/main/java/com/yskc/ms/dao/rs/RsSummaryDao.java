package com.yskc.ms.dao.rs;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.SummaryEntity;
import com.yskc.ms.models.project.DataReportModel;
import com.yskc.ms.models.projectsummary.QuerySummaryMonthModel;
import com.yskc.ms.models.projectsummary.SummaryDataMonthModel;
import com.yskc.ms.models.rs.DataFundsModel;
import com.yskc.ms.models.rs.ProjectBudgetModel;
import com.yskc.ms.models.rs.RdMonthFundsStatusModel;
import com.yskc.ms.models.rs.summary.BaseRemainModel;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface RsSummaryDao extends BaseMapper<SummaryEntity> {

    /**
     * 获取项目研发费用（多项目）
     *
     * @param companyId
     * @param year
     * @param beginMonth
     * @param endMonth
     * @return
     */
    List<DataReportModel> getDataReportFunds(@Param("companyId") Integer companyId, @Param("year") Integer year,
                                             @Param("beginMonth") Date beginMonth, @Param("endMonth") Date endMonth);

    /**
     * 按月份项目费用汇总(人员，设备...)
     * @param query
     * @param beginMonth
     * @param endMonth
     * @return
     */
    List<SummaryDataMonthModel> getMonthFee(@Param("query")QuerySummaryMonthModel query,@Param("beginMonth") Date beginMonth, @Param("endMonth") Date endMonth);
    /**
     * 按月份累计项目费用汇总(人员，设备...)
     * @param query
     * @param beginMonth
     * @param endMonth
     * @param beginYear
     * @return
     */
    List<SummaryDataMonthModel> getMonthFeeTotal(@Param("query")QuerySummaryMonthModel query,@Param("beginYear") Date beginYear,@Param("beginMonth") Date beginMonth, @Param("endMonth") Date endMonth);

    /**
     * 获取项目研发费用（多项目）
     *
     * @param companyId
     * @param beginMonth
     * @param endMonth
     * @param projectId(可空，指定项目查单个)
     * @return
     */
    List<DataFundsModel> getDataFunds(@Param("companyId") Integer companyId, @Param("beginMonth") Date beginMonth,
                                      @Param("endMonth") Date endMonth, @Param("projectId") Integer projectId);

    /**
     * 获取月份归集
     *
     * @param companyId
     * @param beginMonth
     * @param endMonth
     * @return
     */
    List<RdMonthFundsStatusModel> getRdMonthFunds(@Param("companyId") Integer companyId,
                                                  @Param("beginMonth") Date beginMonth,
                                                  @Param("endMonth") Date endMonth);

    /**
     * 统计研发费用
     *
     * @param yearFirstDay
     * @param yearLastDay
     * @return
     */
    List<Map<String, Object>> getSummaryCost(@Param("yearFirstDay") Date yearFirstDay, @Param("yearLastDay") Date yearLastDay, @Param("unUsedTypes") List<Integer> unUsedTypes);

    /**
     * 获取项目归集数
     *
     * @param projectId
     * @return
     */
    List<ProjectBudgetModel> getProjectSummary(@Param("projectId") Integer projectId, @Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

    /**
     * 获取项目最大归集月
     *
     * @param projectId
     * @return
     */
    Map<String, Date> getMinMaxMonth(@Param("projectId") Integer projectId);

    /**
     * 获取项目归集的费用类型
     *
     * @param projectId
     * @return
     */
    List<Integer> getRdTypes(@Param("projectId") Integer projectId);

    /**
     * 通过项目id删除
     *
     * @param projectId
     * @return
     */
    int deleteByProjectId(@Param("projectId") Integer projectId);

    /**
     * 获取材料归集数及剩余数
     *
     * @param projectId
     * @return
     */
    List<BaseRemainModel> getMaterialUsedAndRemain(@Param("projectId") Integer projectId);

    /**
     * 获取能源归集费及剩余费用
     *
     * @param projectId
     * @return
     */
    List<BaseRemainModel> getEnergyFeeAndRemain(@Param("projectId") Integer projectId);

    /**
     * 获取设计归集费及剩余费用
     *
     * @param projectId
     * @return
     */
    List<BaseRemainModel> getDesignFeeAndRemain(@Param("projectId") Integer projectId);

    /**
     * 获取inspection归集费及剩余费用
     *
     * @param projectId
     * @return
     */
    List<BaseRemainModel> getOtherFeeAndRemain(@Param("projectId") Integer projectId);

    /**
     * 更新d_material剩余数量
     *
     * @param now
     * @param list
     * @param msUserId
     * @return
     */
    int updateDataMaterial(@Param("now") Date now, @Param("msUserId") Integer msUserId, @Param("list") List<BaseRemainModel> list);

    /**
     * 删除p_material归集数据
     *
     * @param projectId
     * @return
     */
    int deleteProjectMaterial(@Param("projectId") Integer projectId);

    /**
     * 更新d_energy表剩余费用
     *
     * @param now
     * @param msUserId
     * @param list
     * @return
     */
    int updateDataEnergy(@Param("now") Date now, @Param("msUserId") Integer msUserId, @Param("list") List<BaseRemainModel> list);

    /**
     * 删除p_energy归集数据
     *
     * @param projectId
     * @return
     */
    int deleteProjectEnergy(@Param("projectId") Integer projectId);

    /**
     * 更新d_design表剩余费用
     *
     * @param now
     * @param msUserId
     * @param list
     * @return
     */
    int updateDataDesign(@Param("now") Date now, @Param("msUserId") Integer msUserId, @Param("list") List<BaseRemainModel> list);

    /**
     * 删除p_design 归集数据
     *
     * @param projectId
     * @return
     */
    int deleteProjectDesign(@Param("projectId") Integer projectId);

    /**
     * 更新d_inspection表剩余费用
     *
     * @param now
     * @param msUserId
     * @param list
     * @return
     */
    int updateDataInspection(@Param("now") Date now, @Param("msUserId") Integer msUserId, @Param("list") List<BaseRemainModel> list);

    /**
     * 删除p_inspection表归集费用
     *
     * @param projectId
     * @return
     */
    int deleteProjectInspection(@Param("projectId") Integer projectId);

    /**
     * 批量更新或插入
     *
     * @param list
     * @return
     */
    int insertOrUpdate(@Param("list") List<SummaryEntity> list);

    /**
     * 删除项目费用，工时汇总
     *
     * @param projectId
     * @return
     */
    int deleteInfoSummary(@Param("projectId") Integer projectId);

    /**
     * 根据年获取预算
     *
     * @param projectId
     * @param year
     * @return
     */
    List<ProjectBudgetModel> getBudgetByYear(@Param("projectId") Integer projectId, @Param("year") int year);

    /**
     * 按年获取项目总费用支出
     * @param projectIds
     * @param beginDate
     * @param endDate
     * @return
     */
    @MapKey("projectId")
    Map<Integer,Map<String, BigDecimal>> getFundByYearAndProId(@Param("projectIds")List<Integer> projectIds, @Param("beginDate")Date beginDate, @Param("endDate")Date endDate);

    /**
     * 获取项目所有的归集费用
     *
     * @param projectId
     * @return
     */
    BigDecimal getProjectRdFundsSum(@Param("projectId") Integer projectId);
}
