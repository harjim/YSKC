package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @DateTime: 2022/1/20 10:58
 * @Description:
 * @author: hsx
 */
@TableName("c_rd_funds_district")
public class RdFundsDistrictEntity implements Serializable {

    private static final long serialVersionUID = 3880155009354474254L;

    @TableId
    private Integer id;

    private Integer deptId;

    private Integer year;

    private Date month;

    // 类型：0月份数据，1：月份累计
    private Integer type;

    // 工资
    private BigDecimal k10000;

    // 五险一金
    private BigDecimal k10100;

    // 材料
    private BigDecimal k20000;

    // 动力
    private BigDecimal k20100;

    // 燃料
    private BigDecimal k20200;

    // 试制
    private BigDecimal k20300;

    // 检测
    private BigDecimal k20500;

    // 修理
    private BigDecimal k20600;

    // 样机
    private BigDecimal k20700;

    // 设备折旧(300,301)
    private BigDecimal k30000;

    // 软件摊销
    private BigDecimal k40000;

    // 其他摊销(401,402)
    private BigDecimal k40200;

    // 设计
    private BigDecimal k50000;

    // 其他(所有>=60000)
    private BigDecimal k69900;

    // 研发费总计
    private BigDecimal amount;

    //营收总计
    private BigDecimal revenue;

    private Date createTime;

    private Date lastUpdateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getK10000() {
        return k10000;
    }

    public void setK10000(BigDecimal k10000) {
        this.k10000 = k10000;
    }

    public BigDecimal getK10100() {
        return k10100;
    }

    public void setK10100(BigDecimal k10100) {
        this.k10100 = k10100;
    }

    public BigDecimal getK20000() {
        return k20000;
    }

    public void setK20000(BigDecimal k20000) {
        this.k20000 = k20000;
    }

    public BigDecimal getK20100() {
        return k20100;
    }

    public void setK20100(BigDecimal k20100) {
        this.k20100 = k20100;
    }

    public BigDecimal getK20200() {
        return k20200;
    }

    public void setK20200(BigDecimal k20200) {
        this.k20200 = k20200;
    }

    public BigDecimal getK20300() {
        return k20300;
    }

    public void setK20300(BigDecimal k20300) {
        this.k20300 = k20300;
    }

    public BigDecimal getK20500() {
        return k20500;
    }

    public void setK20500(BigDecimal k20500) {
        this.k20500 = k20500;
    }

    public BigDecimal getK20600() {
        return k20600;
    }

    public void setK20600(BigDecimal k20600) {
        this.k20600 = k20600;
    }

    public BigDecimal getK20700() {
        return k20700;
    }

    public void setK20700(BigDecimal k20700) {
        this.k20700 = k20700;
    }

    public BigDecimal getK30000() {
        return k30000;
    }

    public void setK30000(BigDecimal k30000) {
        this.k30000 = k30000;
    }

    public BigDecimal getK40000() {
        return k40000;
    }

    public void setK40000(BigDecimal k40000) {
        this.k40000 = k40000;
    }

    public BigDecimal getK40200() {
        return k40200;
    }

    public void setK40200(BigDecimal k40200) {
        this.k40200 = k40200;
    }

    public BigDecimal getK50000() {
        return k50000;
    }

    public void setK50000(BigDecimal k50000) {
        this.k50000 = k50000;
    }

    public BigDecimal getK69900() {
        return k69900;
    }

    public void setK69900(BigDecimal k69900) {
        this.k69900 = k69900;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
