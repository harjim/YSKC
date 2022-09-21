package com.yskc.rs.entity.project;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.rs.entity.tech.BaseEntity;

import java.math.BigDecimal;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.entity.project
 * @Author: zhangdingfu
 * @CreateTime: 2022-05-07 08:52
 * @Description: 优惠明细表年费用
 */
@TableName("p_year_fee")
public class ProjectYearFeeEntity extends BaseEntity {
    @TableId
    private Integer id;

    private Integer companyId;

    private Integer year;
    /**
     * 46.减：特殊收入部分
     */
    private BigDecimal k46;
    /**
     * 48.减：当年销售研发活动直接形成产品（包括组成部分）对应的材料部分
     */
    private BigDecimal k48;
    /**
     * 49.减：以前年度销售研发活动直接形成产品（包括组成部分）对应材料部分结转金额
     */
    private BigDecimal k49;
    private BigDecimal rdRatio;

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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BigDecimal getK46() {
        return k46;
    }

    public void setK46(BigDecimal k46) {
        this.k46 = k46;
    }

    public BigDecimal getK48() {
        return k48;
    }

    public void setK48(BigDecimal k48) {
        this.k48 = k48;
    }

    public BigDecimal getK49() {
        return k49;
    }

    public void setK49(BigDecimal k49) {
        this.k49 = k49;
    }

    public BigDecimal getRdRatio() {
        return rdRatio;
    }

    public void setRdRatio(BigDecimal rdRatio) {
        this.rdRatio = rdRatio;
    }
}
