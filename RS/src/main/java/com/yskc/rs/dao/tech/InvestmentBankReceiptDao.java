package com.yskc.rs.dao.tech;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.tech.InvestmentBankReceipt;
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
public interface InvestmentBankReceiptDao extends BaseMapper<InvestmentBankReceipt> {
    /**
     * 关联投资清单
     * @param userId
     * @param msUserId
     * @param date
     * @param companyId
     * @param bankReceiptIds
     * @param investmentId
     * @return
     */
    Integer insertList(@Param("userId") Integer userId, @Param("msUserId") Integer msUserId, @Param("date") Date date,
                       @Param("companyId") Integer companyId, @Param("bankReceiptIds") List<Integer> bankReceiptIds,
                       @Param("investmentId") Integer investmentId);

    /**
     * 删除投资清单所有关联银行水单
     * @param id
     * @return
     */
    Integer delByInvestmentId(@Param("id") Integer id, @Param("receiptIds") List<Integer> receiptIds);

    /**
     * 获取关联银行水单
     * @param investmentId
     * @return
     */
    List<Integer> getByInvestment(@Param("investmentId") Integer investmentId);

    /**
     * 删除投资清单管理银行水单
     * @param investmentIds
     * @return
     */
    Integer delBankReceipt(@Param("investmentIds") List<Integer> investmentIds);
}
