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
@TableName("t_investment_contract")
public class InvestmentContract extends BaseEntity {
    @TableId
    private Integer id;
    private Integer investmentId;
    private Integer contactDetailId;


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

    public Integer getContactDetailId() {
        return contactDetailId;
    }

    public void setContactDetailId(Integer contactDetailId) {
        this.contactDetailId = contactDetailId;
    }
}
