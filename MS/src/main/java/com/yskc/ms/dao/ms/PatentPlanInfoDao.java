package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.PatentPlanInfoEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @DateTime: 2022/2/23 10:23
 * @Description:
 * @author: hsx
 */
@Repository
public interface PatentPlanInfoDao extends BaseMapper<PatentPlanInfoEntity> {
    /**
     * 根据patentPlanId获取info信息
     * @param patentPlanId
     * @return
     */
    PatentPlanInfoEntity selectByPatentId(@Param("patentPlanId") Integer patentPlanId);
}
