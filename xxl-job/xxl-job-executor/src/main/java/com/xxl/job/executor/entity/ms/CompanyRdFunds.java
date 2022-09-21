package com.xxl.job.executor.entity.ms;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.entity.ms
 * @Author: zhangdingfu
 * @CreateTime: 2022-01-21 09:03
 * @Description: 公司研发
 */
@TableName("c_rd_funds")
public class CompanyRdFunds {
    @TableId
    private Integer id;
    private Integer companyId;
    @TableField(exist = false)
    private String deptId;
    private Integer year;
    private Date month;
    /**
     * 类型：0月份数据，1：月份累计
     */
    private Integer type;
    /**
     * 工资
     */
    private BigDecimal k10000;
    /**
     * 五险一金
     */
    private BigDecimal k10100;
    /**
     * 材料
     */
    private BigDecimal k20000;
    /**
     * 动力
     */
    private BigDecimal k20100;
    /**
     * 燃料
     */
    private BigDecimal k20200;
    /**
     * 试制
     */
    private BigDecimal k20300;
    /**
     * 检测
     */
    private BigDecimal k20500;
    /**
     * 修理
     */
    private BigDecimal k20600;
    /**
     * 样机
     */
    private BigDecimal k20700;
    /**
     * 设备折旧(300,301)
     */
    private BigDecimal k30000;
    /**
     * 软件摊销
     */
    private BigDecimal k40000;
    /**
     * 其他摊销(401,402)
     */
    private BigDecimal k40200;
    /**
     * 设计
     */
    private BigDecimal k50000;
    /**
     * 其他(所有>=60000)
     */
    private BigDecimal k69900;
    /**
     * 研发费总计
     */
    private BigDecimal amount;
    /**
     * 营收总计
     */
    private BigDecimal revenue;
    private Date createTime;
    private Date lastUpdateTime;

    /**
     * 构建累计实例
     *
     * @param year
     * @param companyId
     * @param month
     * @return
     */
    public static CompanyRdFunds buildAddData(Integer year, Integer companyId, DateTime month) {
        CompanyRdFunds result = new CompanyRdFunds();
        result.year = year;
        result.companyId = companyId;
        result.month = month;
        result.type = 1;
        // 置为0
        result.k10000 = result.k10100 = result.k20000 = result.k20100 = result.k20200 = result.k20300 = result.k20500
                = result.k20600 = result.k20700 = result.k30000 = result.k40000 = result.k40200 = result.k50000
                = result.k69900 = result.amount = result.revenue = BigDecimal.ZERO;
        return result;
    }

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

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public void addFee(CompanyRdFunds fund) {
        k10000 = k10000.add(null != fund.k10000 ? fund.k10000 : BigDecimal.ZERO);
        k10100 = k10100.add(null != fund.k10100 ? fund.k10100 : BigDecimal.ZERO);
        k20000 = k20000.add(null != fund.k20000 ? fund.k20000 : BigDecimal.ZERO);
        k20100 = k20100.add(null != fund.k20100 ? fund.k20100 : BigDecimal.ZERO);
        k20200 = k20200.add(null != fund.k20200 ? fund.k20200 : BigDecimal.ZERO);
        k20300 = k20300.add(null != fund.k20300 ? fund.k20300 : BigDecimal.ZERO);
        k20500 = k20500.add(null != fund.k20500 ? fund.k20500 : BigDecimal.ZERO);
        k20600 = k20600.add(null != fund.k20600 ? fund.k20600 : BigDecimal.ZERO);
        k20700 = k20700.add(null != fund.k20700 ? fund.k20700 : BigDecimal.ZERO);
        k30000 = k30000.add(null != fund.k30000 ? fund.k30000 : BigDecimal.ZERO);
        k40000 = k40000.add(null != fund.k40000 ? fund.k40000 : BigDecimal.ZERO);
        k40200 = k40200.add(null != fund.k40200 ? fund.k40200 : BigDecimal.ZERO);
        k50000 = k50000.add(null != fund.k50000 ? fund.k50000 : BigDecimal.ZERO);
        k69900 = k69900.add(null != fund.k69900 ? fund.k69900 : BigDecimal.ZERO);
        amount = amount.add(null != fund.amount ? fund.amount : BigDecimal.ZERO);
        revenue = revenue.add(null != fund.revenue ? fund.revenue : BigDecimal.ZERO);
    }
}
