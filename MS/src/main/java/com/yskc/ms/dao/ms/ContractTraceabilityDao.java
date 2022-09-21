package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.ContractTraceability;
import com.yskc.ms.entity.ms.models.ContractTraceabilityModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.QueryContractTraceabilityModel;
import com.yskc.ms.models.customer.QueryCustomerModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.dao.rs
 * @Author: wangxing
 * @CreateTime: 2020-03-09 10:39
 * @Description: 合同追溯Dao
 */
@Repository
public interface ContractTraceabilityDao extends BaseMapper<ContractTraceability> {
    /**
     * 查询所有合同信息
     *
     * @param page
     * @return
     */
    List<ContractTraceabilityModel> queryConTraceability(@Param("page") Pagination page, @Param("query") QueryContractTraceabilityModel query, @Param("perm") DataPermModel perm);

    /**
     * 查询相同合同编号个数
     *
     * @param contractNumber
     * @return
     */
    int countByContractNo(@Param("contractNumber") String contractNumber);
}
