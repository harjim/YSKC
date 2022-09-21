package com.yskc.rs.dao.tech;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.tech.Invoice;
import com.yskc.rs.models.tech.*;
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
public interface InvoiceDao extends BaseMapper<Invoice> {
    /**
     * 统计数据
     * @param ids
     * @param companyId
     * @return
     */
    List<InvestmentInfoModel> countInfo(@Param("ids") List<Integer> ids, @Param("companyId") Integer companyId);

    /**
     * 获取发票列表
     * @param companyId
     * @param query
     * @return
     */
    List<InvoiceModel> getInvoices( @Param("companyId") Integer companyId, @Param("query") QueryInvoiceModel query);

    /**
     * 获取发票详情列表
     * @param companyId
     * @param query
     * @return
     */
    List<InvoiceModel> getInvoiceDetails(@Param("page")Pagination page,@Param("companyId") Integer companyId, @Param("query") QueryInvoiceModel query);

    /**
     * 获取发票
     * @param companyId
     * @param invoiceNo
     * @return
     */
    Invoice verifyInvoice(@Param("companyId") Integer companyId, @Param("invoiceNo") String invoiceNo);

}
