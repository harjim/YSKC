package com.yskc.rs.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-11-05 10:58:47
 */
@TableName("p_material_trace")
public class TraceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		/**
	* 
	*/
		@TableId
		private Integer id;
		/**
	* 
	*/
		private Integer creatorId;
		/**
	* 
	*/
		private Integer lastUpdatorId;
		/**
	* 
	*/
		private Date createTime;
		/**
	* 
	*/
		private Date lastUpdateTime;
		/**
	* 
	*/
		private Integer msCreatorId;
		/**
	* 
	*/
		private Integer msLastUpdatorId;
		/**
	* 
	*/
		private Integer pId;
		/**
	* 
	*/
		private BigDecimal normalOutputRate;
		/**
	* 
	*/
		private BigDecimal rdOutputRate;
		/**
	* 
	*/
		private Integer rdOutPut;
		/**
	* 
	*/
		private BigDecimal rdOutputAmount;
		/**
	* 
	*/
		private BigDecimal rdLossRate;
		/**
	* 
	*/
		private Integer rdLoss;
		/**
	* 
	*/
		private BigDecimal rdLossAmount;
		/**
	* 
	*/
		private BigDecimal scrapRate;
		/**
	* 
	*/
		private Integer scrap;
		/**
	* 
	*/
		private BigDecimal scrapAmount;
		/**
	* 
	*/
		private String scrapNo;
		/**
	* 
	*/
		private BigDecimal sampleRevenue;
		/**
	* 
	*/
		private BigDecimal scrapPrice;
		/**
	* 
	*/
		private BigDecimal specialIncome;
		/**
	* 
	*/
		private String specialIncomeNo;
		    public void setId(Integer id){
    	this.id=id;
	}
	public Integer getId(){
		return id;
	}
	    public void setCreatorId(Integer creatorId){
    	this.creatorId=creatorId;
	}
	public Integer getCreatorId(){
		return creatorId;
	}
	    public void setLastUpdatorId(Integer lastUpdatorId){
    	this.lastUpdatorId=lastUpdatorId;
	}
	public Integer getLastUpdatorId(){
		return lastUpdatorId;
	}
	    public void setCreateTime(Date createTime){
    	this.createTime=createTime;
	}
	public Date getCreateTime(){
		return createTime;
	}
	    public void setLastUpdateTime(Date lastUpdateTime){
    	this.lastUpdateTime=lastUpdateTime;
	}
	public Date getLastUpdateTime(){
		return lastUpdateTime;
	}
	    public void setMsCreatorId(Integer msCreatorId){
    	this.msCreatorId=msCreatorId;
	}
	public Integer getMsCreatorId(){
		return msCreatorId;
	}
	    public void setMsLastUpdatorId(Integer msLastUpdatorId){
    	this.msLastUpdatorId=msLastUpdatorId;
	}
	public Integer getMsLastUpdatorId(){
		return msLastUpdatorId;
	}
	    public void setPId(Integer pId){
    	this.pId=pId;
	}
	public Integer getPId(){
		return pId;
	}
	    public void setNormalOutputRate(BigDecimal normalOutputRate){
    	this.normalOutputRate=normalOutputRate;
	}
	public BigDecimal getNormalOutputRate(){
		return normalOutputRate;
	}
	    public void setRdOutputRate(BigDecimal rdOutputRate){
    	this.rdOutputRate=rdOutputRate;
	}
	public BigDecimal getRdOutputRate(){
		return rdOutputRate;
	}
	    public void setRdOutPut(Integer rdOutPut){
    	this.rdOutPut=rdOutPut;
	}
	public Integer getRdOutPut(){
		return rdOutPut;
	}
	    public void setRdOutputAmount(BigDecimal rdOutputAmount){
    	this.rdOutputAmount=rdOutputAmount;
	}
	public BigDecimal getRdOutputAmount(){
		return rdOutputAmount;
	}
	    public void setRdLossRate(BigDecimal rdLossRate){
    	this.rdLossRate=rdLossRate;
	}
	public BigDecimal getRdLossRate(){
		return rdLossRate;
	}
	    public void setRdLoss(Integer rdLoss){
    	this.rdLoss=rdLoss;
	}
	public Integer getRdLoss(){
		return rdLoss;
	}
	    public void setRdLossAmount(BigDecimal rdLossAmount){
    	this.rdLossAmount=rdLossAmount;
	}
	public BigDecimal getRdLossAmount(){
		return rdLossAmount;
	}
	    public void setScrapRate(BigDecimal scrapRate){
    	this.scrapRate=scrapRate;
	}
	public BigDecimal getScrapRate(){
		return scrapRate;
	}
	    public void setScrap(Integer scrap){
    	this.scrap=scrap;
	}
	public Integer getScrap(){
		return scrap;
	}
	    public void setScrapAmount(BigDecimal scrapAmount){
    	this.scrapAmount=scrapAmount;
	}
	public BigDecimal getScrapAmount(){
		return scrapAmount;
	}
	    public void setScrapNo(String scrapNo){
    	this.scrapNo=scrapNo;
	}
	public String getScrapNo(){
		return scrapNo;
	}
	    public void setSampleRevenue(BigDecimal sampleRevenue){
    	this.sampleRevenue=sampleRevenue;
	}
	public BigDecimal getSampleRevenue(){
		return sampleRevenue;
	}
	    public void setScrapPrice(BigDecimal scrapPrice){
    	this.scrapPrice=scrapPrice;
	}
	public BigDecimal getScrapPrice(){
		return scrapPrice;
	}
	    public void setSpecialIncome(BigDecimal specialIncome){
    	this.specialIncome=specialIncome;
	}
	public BigDecimal getSpecialIncome(){
		return specialIncome;
	}
	    public void setSpecialIncomeNo(String specialIncomeNo){
    	this.specialIncomeNo=specialIncomeNo;
	}
	public String getSpecialIncomeNo(){
		return specialIncomeNo;
	}
	
}
