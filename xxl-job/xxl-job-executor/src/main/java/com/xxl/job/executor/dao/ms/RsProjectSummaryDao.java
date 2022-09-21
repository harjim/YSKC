package com.xxl.job.executor.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.ms.RsProjectSummary;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/7/22 15:03
 * @Description:
 */
@Repository
public interface RsProjectSummaryDao extends BaseMapper<RsProjectSummary> {
    /**
     * 插入并更新
     * @param list
     * @return
     */
    Boolean insertOrUpdate(@Param("list") List<RsProjectSummary> list);
}
