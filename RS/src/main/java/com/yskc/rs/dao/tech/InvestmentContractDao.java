package com.yskc.rs.dao.tech;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.tech.ContractDetail;
import com.yskc.rs.entity.tech.InvestmentContract;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
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
public interface InvestmentContractDao extends BaseMapper<InvestmentContract> {
    /**
     * 批量插入
     * @param contracts
     * @return
     */
//    Integer insertList(@Param("contracts") List<InvestmentContract> contracts);

    /**
     * 关联投资清单
     * @param userId
     * @param msUserId
     * @param date
     * @param contractDetailIds
     * @param investmentId
     * @return
     */
    Integer insertList(@Param("userId") Integer userId, @Param("msUserId") Integer msUserId, @Param("date") Date date,
                        @Param("contractDetailIds") List<Integer> contractDetailIds, @Param("investmentId") Integer investmentId);

    /**
     * 删除投资清单所有关联合同
     * @param id
     * @return
     */
    Integer delByInvestmentId(@Param("id") Integer id, @Param("contractDetailIds") List<Integer> contractDetailIds);

    /**
     * 获取关联合同
     * @param investmentId
     * @return
     */
    List<Integer> getByInvestment(@Param("investmentId") Integer investmentId);

    /**
     * 删除投资清单关联合同
     * @param investmentIds
     * @return
     */
    Integer delContract(@Param("investmentIds") List<Integer> investmentIds);
}
