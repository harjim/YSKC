package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.SituationSummaryEntity;
import com.yskc.rs.models.projectSummary.SituationSummaryModel;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/2/24 11:50
 * @Description:
 */
@Repository
public interface SituationSummaryDao extends BaseMapper<SituationSummaryEntity> {
    /**
     * 查询项目总结
     * @param year
     * @param companyId
     * @return
     */
    List<SituationSummaryModel> querySituation(@Param("year") Integer year, @Param("companyId") Integer companyId);

    Integer insertOrUpdate(SituationSummaryEntity entity);
}
