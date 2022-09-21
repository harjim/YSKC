package com.yskc.rs.dao.tech;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.tech.InvestmentInvoice;
import com.yskc.rs.entity.tech.InvoiceDetail;
import com.yskc.rs.models.tech.InvoiceDetailModel;
import io.swagger.models.auth.In;
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
public interface InvoiceDetailDao extends BaseMapper<InvoiceDetail> {
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
    Integer updateList(@Param("updateList") List<InvoiceDetail> updateList);

    /**
     * 批量插入
     * @param insertList
     * @return
     */
    Integer insertList(@Param("insertList") List<InvoiceDetail> insertList);

    /**
     * 根据发票id获取发票信息详情
     * @param invoiceId
     * @return
     */
    List<InvoiceDetailModel> getByInvoiceId(@Param("invoiceId") Integer invoiceId,@Param("companyId")Integer companyId);

    /**
     * 删除投资清单关联发票设备
     * @param delIds
     * @param investmentId
     * @return
     */
    Integer delRelatedData(@Param("delIds") List<Integer> delIds, @Param("investmentId") Integer investmentId);

    /**
     * 获取关联的发票
     * @param invoiceDetailIds
     * @return
     */
    List<InvoiceDetail> getRalatedInvocie(@Param("invoiceDetailIds") List<Integer> invoiceDetailIds, @Param("investmentId") Integer investmentId);

    /**
     * 获取排除参数外的发票设备
     * @param detailIds
     * @param invoiceIds
     * @return
     */
    List<InvoiceDetail> getOtherDetail(@Param("invoiceIds") List<Integer> invoiceIds,@Param("detailIds") List<Integer> detailIds);
}
