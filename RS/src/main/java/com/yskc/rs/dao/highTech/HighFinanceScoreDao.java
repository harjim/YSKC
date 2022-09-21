package com.yskc.rs.dao.highTech;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.hightech.HighFinanceScoreEntity;
import com.yskc.rs.models.highscore.HighFinanceScoreModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @DateTime: 2021/11/4 10:43
 * @Description:
 * @author: hsx
 */
@Repository
public interface HighFinanceScoreDao extends BaseMapper<HighFinanceScoreEntity> {

    /**
     * 获取id
     * @param companyId
     * @param year
     * @return
     */
    Integer getId(@Param("companyId") Integer companyId, @Param("year") Integer year);
    /**
     * 获取高新评分评价
     *
     * @param companyId
     * @param year
     * @return
     */
    HighFinanceScoreModel getFinanceScore(@Param("companyId") Integer companyId, @Param("year") Integer year);
}
