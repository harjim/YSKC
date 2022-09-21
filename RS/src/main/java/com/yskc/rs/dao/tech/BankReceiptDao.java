package com.yskc.rs.dao.tech;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.tech.BankReceipt;
import com.yskc.rs.models.tech.BankReceiptModel;
import com.yskc.rs.models.tech.InvestmentInfoModel;
import com.yskc.rs.models.tech.QueryBankReceiptModel;
import com.yskc.rs.models.tech.QueryContractModel;
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
public interface BankReceiptDao extends BaseMapper<BankReceipt> {


    List<InvestmentInfoModel> countInfo(@Param("companyId") Integer companyId, @Param("ids") List<Integer> ids);

    /**
     * 根据投资清单获取银行水单
     * @param investmentId
     * @return
     */
    List<BankReceiptModel> getByInvestmentId(@Param("investmentId") Integer investmentId);

    /**
     * 获取可添加的银行水单
     * @param query
     * @param companyId
     * @return
     */
    List<BankReceiptModel> getReceiptInfo(@Param("page")Pagination page, @Param("query") QueryBankReceiptModel query, @Param("companyId") Integer companyId);

    /**
     * 删除银行水单关联投资清单
     * @param delIds
     * @param investmentId
     * @return
     */
    Boolean delRelated(@Param("delIds") List<Integer> delIds, @Param("investmentId") Integer investmentId);

    /**
     * 获取已关联的银行水单
     * @param bankReceiptIds
     * @return
     */
    List<BankReceipt> getRelatedReceipt(@Param("bankReceiptIds") List<Integer> bankReceiptIds, @Param("investmentId") Integer investmentId);
}
