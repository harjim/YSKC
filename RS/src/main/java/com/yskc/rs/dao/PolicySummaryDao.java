package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.PolicySummaryEntity;
import com.yskc.rs.models.projectSummary.PolicySummaryModel;
import org.springframework.stereotype.Repository;

/**
 * @Author: hck
 * @DateTime: 2021/2/25 14:43
 * @Description:
 */
@Repository
public interface PolicySummaryDao extends BaseMapper<PolicySummaryEntity> {
    /**
     * 获取政策及要求
     * @return
     */
    PolicySummaryModel getInfo();
}
