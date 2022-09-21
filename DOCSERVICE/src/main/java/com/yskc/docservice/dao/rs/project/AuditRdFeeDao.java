package com.yskc.docservice.dao.rs.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.project.AuditRdFeeEntity;
import com.yskc.docservice.models.rs.CheckStatusModel;
import com.yskc.docservice.models.rs.project.AuditRdFeeModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AuditRdFeeDao extends BaseMapper<AuditRdFeeEntity> {
    /**
     * 获取审核状态
     * @param list
     * @param rdTypes
     * @return
     */
    List<AuditRdFeeModel> getStatusList(@Param("list") List<CheckStatusModel> list, @Param("rdTypes") List<Integer> rdTypes);
}
