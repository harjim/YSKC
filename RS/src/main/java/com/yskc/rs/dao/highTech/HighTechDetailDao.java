package com.yskc.rs.dao.highTech;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.hightech.HighTechDetailEntity;
import com.yskc.rs.models.hightech.HighTechDetailModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/5/27 17:07
 * @Description:
 */
@Repository
public interface HighTechDetailDao extends BaseMapper<HighTechDetailEntity> {
    /**
     * 获取高品信息
     * @param companyId
     * @param highTechId
     * @return
     */
    HighTechDetailModel getTechDetail(@Param("companyId") Integer companyId, @Param("highTechId") Integer highTechId);

    /**
     * 删除详情
     * @param highTechIds
     * @param companyId
     * @return
     */
    Integer delByTech(@Param("highTechIds") List<Integer> highTechIds, @Param("companyId") Integer companyId);
}
