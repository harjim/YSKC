package com.xxl.job.executor.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.ms.DistrictSummary;
import com.xxl.job.executor.entity.ms.ProjectSummaryData;
import com.xxl.job.executor.entity.rs.CompanyRdSummary;
import com.xxl.job.executor.models.district.RsSummaryModel;
import com.xxl.job.executor.models.projectsummary.PatentApplySummaryModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by hck
 * on 2020/11/10 11:04
 * description:
 */
@Repository
public interface ProjectSummaryDataDao extends BaseMapper<ProjectSummaryData> {
    /**
     * 插入或更新
     *
     * @param summaries
     * @return
     */
    Integer insertOrUpdate(@Param("summaries") List<ProjectSummaryData> summaries);

    /**
     * 插入或更新分公司统计
     *
     * @param list
     * @param now
     * @return
     */
    Integer insertOrUpdateDistrict(@Param("list") List<ProjectSummaryData> list, @Param("now") Date now);

    /**
     * 获取客户年归集数
     *
     * @param data
     * @return
     */
    List<RsSummaryModel> getCustomerYearRdFunds(@Param("data") List<DistrictSummary> data);

    /**
     * 获取数据
     *
     * @param lastTime
     * @return
     */
    List<CompanyRdSummary> getData(@Param("lastTime") Date lastTime);

    /**
     * 获取专利申请数
     *
     * @param year
     * @param companyIds
     * @return
     */
    List<PatentApplySummaryModel> getPatentApplyCnt(@Param("year") Integer year, @Param("companyIds") Set<Integer> companyIds);

    /**
     * 获取分公司数据
     *
     * @param year
     * @param branchIds
     * @return
     */
    List<ProjectSummaryData> getDistrictData(@Param("year") Integer year, @Param("branchIds") Set<Integer> branchIds);

}
