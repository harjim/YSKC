package com.yskc.rs.dao.init;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.init.InsuranceConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 五险一金
 * @author huronghua
 */
@Repository
public interface InsuranceDao extends BaseMapper<InsuranceConfig> {
    /**
     * 批量更新
     * @param insuranceConfigs
     * @return
     */
    Integer insertOrUpdate(@Param("insuranceConfigs") List<InsuranceConfig> insuranceConfigs);
}
