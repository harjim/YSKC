package com.yskc.rs.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.rs.entity.tech.BaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @DateTime: 2022/1/14 8:23
 * @Description:年度营收实体类
 * @author: hsx
 */
@TableName("c_revenue")
public class RevenueEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -7153153662387166921L;

    @TableId
    private Integer id;

    private Integer companyId;

    private Integer year;

    private Date month;

    private BigDecimal revenue;

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

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj){
            return true;
        }
        if (!(obj instanceof RevenueEntity)) {
            throw new ClassCastException("类型错误");
        }
        RevenueEntity revenue=(RevenueEntity)obj;
        return revenue.companyId.equals(this.companyId) && this.month.equals(revenue.month)
                && this.year.equals(revenue.year) && this.revenue.compareTo(revenue.revenue) == 0;
    }
}
