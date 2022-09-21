package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.DistrictSummary;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.dao.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-06-18 14:27
 * @Description: 区域汇总费用
 */
@Repository
public interface DistrictSummaryDao extends BaseMapper<DistrictSummary> {

    /**
     * 按年获取部门区域归集费
     * @param year
     * @return
     */
    List<Map<String,Object>> getYearDistrictSummary(@Param("year") Integer year);
}
