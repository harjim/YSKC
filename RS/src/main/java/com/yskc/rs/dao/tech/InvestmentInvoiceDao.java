package com.yskc.rs.dao.tech;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.tech.InvestmentInvoice;
import com.yskc.rs.entity.tech.InvoiceDetail;
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
public interface InvestmentInvoiceDao extends BaseMapper<InvestmentInvoice> {

//    /**
//     * 批量插入联系
//     * @return
//     */
//    Boolean insertList(@Param("invoices") List<InvestmentInvoice> invoices);

    /**
     * 发票与投资清单创建关联
     * @param userId
     * @param msUserId
     * @param date
     * @param companyId
     * @param invoiceIds
     * @param investmentId
     * @return
     */
    Integer insertList(@Param("userId") Integer userId, @Param("msUserId") Integer msUserId, @Param("date") Date date,
                       @Param("companyId") Integer companyId, @Param("invoiceIds") List<Integer> invoiceIds,
                       @Param("investmentId") Integer investmentId);

    /**
     * 删除投资清单关联发票
     * @param id
     * @return
     */
    Integer delByInvestmentId(@Param("id") Integer id, @Param("invoiceDetailIds") List<Integer> invoiceDetailIds);

    /**
     * 获取投资清单关联数据
     * @param investmentId
     * @return
     */
    List<Integer> getByInvestment(@Param("investmentId") Integer investmentId);

    /**
     * 删除投资清单关联发票
     * @param investmentIds
     * @return
     */
    Integer delInvestment(@Param("investmentIds") List<Integer> investmentIds);
}
