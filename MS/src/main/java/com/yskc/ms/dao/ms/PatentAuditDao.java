package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.PatentAuditEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/8/21 11:33
 * @Description:
 */
@Repository
public interface PatentAuditDao extends BaseMapper<PatentAuditEntity> {
    /**
     * 获取专利申请审核
     *
     * @param patentIds
     * @return
     */
    List<PatentAuditEntity> getByPatent(@Param("patentIds") List<Integer> patentIds);
}
