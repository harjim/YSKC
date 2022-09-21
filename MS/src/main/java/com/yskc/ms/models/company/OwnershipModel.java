package com.yskc.ms.models.company;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by hck
 * on 2020/12/4 15:51
 * description:股权架构model
 */
public class OwnershipModel implements Serializable {
    private Integer id;
    private Integer companyId;
    /**
     * 股东名称
     */
    @NotEmpty(message = "股东不能为空")
    @NotNull(message = "股东不能为空")
    private String shareholder;
    /**
     * 出资额（万元）
     */
    @NotNull(message = "出资额不能为空")
    private BigDecimal capitalContribution;
    /**
     * 出资方式
     */
    @NotEmpty(message = "出资方式不能为空")
    @NotNull(message = "出资方式不能为空")
    private String contributionType;
    /**
     * 比例
     */
    @NotNull(message = "所占比例不能为空")
    private BigDecimal proportion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getShareholder() {
        return shareholder;
    }

    public void setShareholder(String shareholder) {
        this.shareholder = shareholder;
    }

    public BigDecimal getCapitalContribution() {
        return capitalContribution;
    }

    public void setCapitalContribution(BigDecimal capitalContribution) {
        this.capitalContribution = capitalContribution;
    }

    public String getContributionType() {
        return contributionType;
    }

    public void setContributionType(String contributionType) {
        this.contributionType = contributionType;
    }

    public BigDecimal getProportion() {
        return proportion;
    }

    public void setProportion(BigDecimal proportion) {
        this.proportion = proportion;
    }
}
