package com.xxl.job.executor.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.ms.ProjectAuditSummary;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.dao.ms
 * @Author: zhangdingfu
 * @CreateTime: 2021-02-20 17:07
 * @Description: 项目审核统计
 */
@Repository
public interface ProjectAuditSummaryDao extends BaseMapper<ProjectAuditSummary> {
    /**
     * 插入或更新
     *
     * @param list
     * @param isDoc
     * @return
     */
    int insertOrUpdate(@Param("list") List<ProjectAuditSummary> list, @Param("isDoc") boolean isDoc);

    /**
     * 更新总计
     *
     * @param projectIds
     * @param now
     * @return
     */
    int updateTotal(@Param("projectIds") Set<Integer> projectIds,@Param("now") Date now);
}
