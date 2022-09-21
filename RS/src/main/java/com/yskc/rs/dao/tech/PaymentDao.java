package com.yskc.rs.dao.tech;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.tech.PaymentEntity;
import com.yskc.rs.models.tech.InvestmentInfoModel;
import com.yskc.rs.models.tech.PaymentModel;
import com.yskc.rs.models.tech.QueryBankReceiptModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentDao extends BaseMapper<PaymentEntity> {


    List<InvestmentInfoModel> countInfo(@Param("companyId") Integer companyId, @Param("ids") List<Integer> ids);

    /**
     * 根据投资清单获取付款单
     * @param investmentId
     * @return
     */
    List<PaymentModel> getByInvestmentId(@Param("investmentId") Integer investmentId);

    /**
     * 根据关联获取ids
     * @param investmentId
     * @return
     */
    List<Integer> getByInvestment(@Param("investmentId") Integer investmentId);

    /**
     * 获取可添加的付款单（原银行水单）
     * @param page
     * @param query
     * @param companyId
     * @return
     */
    List<PaymentModel> getReceiptInfo(@Param("page") Pagination page, @Param("query") QueryBankReceiptModel query, @Param("companyId") Integer companyId);

    /**
     * 删除付款单关联投资清单
     * @param delIds
     * @param investmentId
     * @return
     */
    Boolean delRelated(@Param("delIds") List<Integer> delIds, @Param("investmentId") Integer investmentId);

    /**
     * 删除付款单关联
     * @param delIds
     * @return
     */
    Integer updateByInvestmentIds(@Param("delIds") List<Integer> delIds);

    /**
     * 批量更新关联
     * @param ids
     * @param investmentId
     * @return
     */
    Integer updateList(@Param("ids") List<Integer> ids,@Param("investmentId") Integer investmentId);

    /**
     * 获取已关联的付款单
     * @param ids
     * @param investmentId
     * @return
     */
    List<Integer> getRelated(@Param("ids") List<Integer> ids, @Param("investmentId") Integer investmentId);
}
