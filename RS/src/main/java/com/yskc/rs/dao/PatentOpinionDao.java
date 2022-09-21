package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.PatentOpinionEntity;
import com.yskc.rs.models.PatentPlan.PatentOpinionModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/6/8 11:13
 * @Description:
 */
@Repository
public interface PatentOpinionDao extends BaseMapper<PatentOpinionEntity> {
    /**
     * 获取专利资料意见列表
     * @param patentPlanId
     * @return
     */
    List<PatentOpinionModel> getOpinions(@Param("patentPlanId") Integer patentPlanId);
}
