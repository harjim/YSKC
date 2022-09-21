package com.xxl.job.executor.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.ms.ProjectSummary;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.dao.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-26 15:10
 * @Description: 项目
 */
@Repository
public interface ProjectSummaryDao extends BaseMapper<ProjectSummary> {
    /**
     * 插入或更新
     *
     * @param data
     * @return
     */
    int insertOrUpdate(@Param("data") Collection<ProjectSummary> data);

    /**
     * 获取客户归集费用
     * @param data
     * @return
     */
    // List<RsSummaryModel> getCustomerYearRdFunds(@Param("data") List<DistrictSummary> data);
}
