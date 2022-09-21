package com.yskc.rs.dao.tech;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.tech.Investment;
import com.yskc.rs.models.tech.CountInvoiceModel;
import com.yskc.rs.models.tech.InvestmentModel;
import com.yskc.rs.models.tech.QueryInverstmentModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zdf-hck
 * @since 2021-03-18
 */
@Repository
public interface InvestmentDao extends BaseMapper<Investment> {
    /**
     * 获取投资清单列表
     * @param page
     * @param companyId
     * @param query
     * @return
     */
    List<InvestmentModel> getList(@Param("page") Pagination page, @Param("companyId") Integer companyId, @Param("query") QueryInverstmentModel query);

    /**
     * 统计备案发票完成费用
     * @param beianId
     * @return
     */
    List<CountInvoiceModel> getByBeianId(@Param("beianId") Integer beianId);

    /**
     * 统计备案付款完成费用
     * @param beianId
     * @return
     */
    List<CountInvoiceModel> getPayByBeianId(@Param("beianId") Integer beianId);

    /**
     * 获取技改已关联过的发票开具方
     * @param invoiceFrom
     * @param beianId
     * @return
     */
    List<String> getInvoiceFroms(@Param("invoiceFrom") String invoiceFrom, @Param("beianId") Integer beianId);

    /**
     * 获取技改已关联过的合同签订对象
     * @param signTarget
     * @param beianId
     * @return
     */
    List<String> getSignTargets(@Param("signTarget") String signTarget, @Param("beianId") Integer beianId);

    /**
     * 获取技改已关联过的银行水单收款单位
     * @param payee
     * @param beianId
     * @return
     */
    List<String> getPayees(@Param("payee") String payee, @Param("beianId") Integer beianId);

    /**
     * 查询公司设备名称
     * @param deviceName
     * @param companyId
     * @return
     */
    List<String> getDeviceName(@Param("deviceName") String deviceName, @Param("companyId") Integer companyId);
    /**
     * 查询发票开具方、合同签订对象、银行水单收款单位
     * @param companyName
     * @param companyId
     * @return
     */
    List<String> getCompanyName(@Param("companyName") String companyName, @Param("companyId") Integer companyId);

}
