package com.yskc.rs.dao.tech;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.tech.ContractDetail;
import com.yskc.rs.models.tech.ContractDetailModel;
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
public interface ContractDetailDao extends BaseMapper<ContractDetail> {
    /**
     * 删除合同关联
     * @param delIds
     * @return
     */
    Integer updateByInvestmentIds(@Param("delIds") List<Integer> delIds);
    /**
     * 根据关联获取ids
     * @param investmentId
     * @return
     */
    List<Integer> getByInvestment(@Param("investmentId") Integer investmentId);

    /**
     * 批量更新关联
     * @param ids
     * @param investmentId
     * @return
     */
    Integer updateByInvestment(@Param("ids") List<Integer> ids,@Param("investmentId") Integer investmentId);
    /**
     * 批量更新
     * @param updateList
     * @return
     */
    Boolean updateList(@Param("updateList") List<ContractDetail> updateList);

    /**
     * 批量插入
     * @param insertList
     * @return
     */
    Integer insertList(@Param("insertList") List<ContractDetail> insertList);

    /**
     * 根据合同获取信息
     * @param contractId
     * @return
     */
    List<ContractDetailModel> getByContractId(@Param("contractId") Integer contractId);

    /**
     * 删除投资清单关联合同
     * @param delIds
     * @param investmentId
     * @return
     */
    Integer delRelatedData(@Param("delIds") List<Integer> delIds, @Param("investmentId") Integer investmentId);

    /**
     * 获取关联的合同
     * @param contractDetailIds
     * @return
     */
    List<ContractDetail> getRelatedContract(@Param("contractDetailIds") List<Integer> contractDetailIds, @Param("investmentId") Integer investmentId);

    /**
     * 获取合同其他设备信息
     * @param contractIds
     * @param detailIds
     * @return
     */
    List<ContractDetail> getOtherContract(@Param("contractIds") List<Integer> contractIds, @Param("detailIds") List<Integer> detailIds);
}
