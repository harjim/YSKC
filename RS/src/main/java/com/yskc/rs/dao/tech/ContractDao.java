package com.yskc.rs.dao.tech;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.tech.Contract;
import com.yskc.rs.entity.tech.ContractDetail;
import com.yskc.rs.models.tech.ContractInfoModel;
import com.yskc.rs.models.tech.QueryContractModel;
import com.yskc.rs.models.tech.QueryEquipmentModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zdf-hck123
 * @since 2021-03-18
 */
@Repository
public interface ContractDao extends BaseMapper<Contract> {
    /**
     * 获得合同信息列表
     * @param companyId
     * @param query
     * @return
     */
    List<ContractInfoModel> getContracts(@Param("companyId") Integer companyId, @Param("query") QueryContractModel query);

    /**
     * 获取合同设备信息+
     * @param companyId
     * @param query
     * @return
     */
    List<ContractInfoModel> getContractDetails(@Param("page")Pagination page,@Param("companyId") Integer companyId, @Param("query") QueryContractModel query);

    /**
     * 验证发票号
     * @param companyId
     * @param contractNo
     * @return
     */
    Contract verifyContract(@Param("companyId") Integer companyId, @Param("contractNo") String contractNo);
}
