package com.yskc.rs.entity.tech;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 *
 * </p>
 *
 * @author hck
 * @since 2021-03-18
 */
@TableName("t_investment_bankReceipt")
public class InvestmentBankReceipt extends BaseEntity {

    @TableId
    private Integer id;
    private Integer investmentId;
    private Integer bankReceiptId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInvestmentId() {
        return investmentId;
    }

    public void setInvestmentId(Integer investmentId) {
        this.investmentId = investmentId;
    }

    public Integer getBankReceiptId() {
        return bankReceiptId;
    }

    public void setBankReceiptId(Integer bankReceiptId) {
        this.bankReceiptId = bankReceiptId;
    }
}
