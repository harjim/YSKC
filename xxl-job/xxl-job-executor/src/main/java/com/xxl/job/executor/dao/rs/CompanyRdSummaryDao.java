package com.xxl.job.executor.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.ms.CompanyRdFunds;
import com.xxl.job.executor.entity.ms.ProjectSummaryData;
import com.xxl.job.executor.entity.rs.CompanyRdSummary;
import com.xxl.job.executor.models.projectsummary.BuildSummaryModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.dao.rs
 * @Author: zhangdingfu
 * @CreateTime: 2021-07-02 10:23
 * @Description: 公司研发汇总相关
 */
@Repository
public interface CompanyRdSummaryDao extends BaseMapper<CompanyRdSummary> {
    /**
     * 插入或更新
     *
     * @param list
     * @return
     */
    int insertOrUpdate(@Param("list") List<ProjectSummaryData> list);

    /**
     * 获取机构建设附件数
     *
     * @param year
     * @param companyIds
     * @param listType
     * @return
     */
    List<BuildSummaryModel> getBuildCnt(@Param("year") Integer year, @Param("companyIds") Set<Integer> companyIds, @Param("listType") int listType);

    /**
     * 插入或更新
     *
     * @param list
     * @param now
     * @return
     */
    int insertOrUpdateFund(@Param("list") List<CompanyRdFunds> list,@Param("now") Date now);
}
