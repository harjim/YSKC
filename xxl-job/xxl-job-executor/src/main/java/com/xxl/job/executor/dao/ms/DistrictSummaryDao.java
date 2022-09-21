package com.xxl.job.executor.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.ms.DistrictSummary;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.dao.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-06-16 17:39
 * @Description: 区域费用统计dao层
 */
@Repository
public interface DistrictSummaryDao extends BaseMapper<DistrictSummary> {
    /**
     * 插入或更新
     *
     * @param list
     * @return
     */
    int insertOrUpdate(@Param("list") List<DistrictSummary> list);
}
