package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.HighTechScoreEntity;
import com.yskc.rs.models.highscore.HighTechScoreModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao
 * @Author: zhangdingfu
 * @CreateTime: 2021-10-25 08:30
 * @Description: 高新评分评价dao层
 */
@Repository
public interface HighTechScoreDao extends BaseMapper<HighTechScoreEntity> {

    /**
     * 获取id
     * @param companyId
     * @param year
     * @return
     */
    Integer getId(@Param("companyId") Integer companyId,@Param("year") Integer year);
    /**
     * 获取高新评分评价
     *
     * @param companyId
     * @param year
     * @return
     */
    HighTechScoreModel getTechScore(@Param("companyId") Integer companyId, @Param("year") Integer year);
}
