package com.yskc.rs.models.summary;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 其他项目费用model
 *
 * @author zhangdingfu
 */
public class OtherSummaryModel {

    /**
     * 项目id
     */
    private Integer projectId;

    private Date month;

    private int rdType;

    private BigDecimal rdFunds;

    public int getRdType() {
        return rdType;
    }

    public void setRdType(int rdType) {
        this.rdType = rdType;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public BigDecimal getRdFunds() {
        return rdFunds;
    }

    public void setRdFunds(BigDecimal rdFunds) {
        this.rdFunds = rdFunds;
    }


    /**
     * 项目名称
     //	 */
//	private String pname;
//	private BigDecimal Jan;
//	private BigDecimal Feb;
//	private BigDecimal Mar;
//	private BigDecimal Apr;
//	private BigDecimal May;
//	private BigDecimal June;
//	private BigDecimal July;
//	private BigDecimal Aug;
//	private BigDecimal Sept;
//	private BigDecimal Oct;
//	private BigDecimal Nov;
//	private BigDecimal Dec;
//	public Integer getProjectId() {
//		return projectId;
//	}
//	public void setProjectId(Integer projectId) {
//		this.projectId = projectId;
//	}
//	public String getPname() {
//		return pname;
//	}
//	public void setPname(String pname) {
//		this.pname = pname;
//	}
//	public BigDecimal getJan() {
//		return Jan;
//	}
//	public void setJan(BigDecimal jan) {
//		Jan = jan;
//	}
//	public BigDecimal getFeb() {
//		return Feb;
//	}
//	public void setFeb(BigDecimal feb) {
//		Feb = feb;
//	}
//	public BigDecimal getMar() {
//		return Mar;
//	}
//	public void setMar(BigDecimal mar) {
//		Mar = mar;
//	}
//	public BigDecimal getApr() {
//		return Apr;
//	}
//	public void setApr(BigDecimal apr) {
//		Apr = apr;
//	}
//	public BigDecimal getMay() {
//		return May;
//	}
//	public void setMay(BigDecimal may) {
//		May = may;
//	}
//	public BigDecimal getJune() {
//		return June;
//	}
//	public void setJune(BigDecimal june) {
//		June = june;
//	}
//	public BigDecimal getJuly() {
//		return July;
//	}
//	public void setJuly(BigDecimal july) {
//		July = july;
//	}
//	public BigDecimal getAug() {
//		return Aug;
//	}
//	public void setAug(BigDecimal aug) {
//		Aug = aug;
//	}
//	public BigDecimal getSept() {
//		return Sept;
//	}
//	public void setSept(BigDecimal sept) {
//		Sept = sept;
//	}
//	public BigDecimal getOct() {
//		return Oct;
//	}
//	public void setOct(BigDecimal oct) {
//		Oct = oct;
//	}
//	public BigDecimal getNov() {
//		return Nov;
//	}
//	public void setNov(BigDecimal nov) {
//		Nov = nov;
//	}
//	public BigDecimal getDec() {
//		return Dec;
//	}
//	public void setDec(BigDecimal dec) {
//		Dec = dec;
//	}


}
