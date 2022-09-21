package com.yskc.rs.entity.tech;

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
 * @date 2019-09-23 14:05:59
 */
@TableName("t_projectInvestMent")
public class ProjectInvestMentEntity implements Serializable {
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
		private Date createTime;
		/**
	* 
	*/
		private Integer lastUpdatorId;
		/**
	* 
	*/
		private Date lastUpdateTime;
		/**
	* 
	*/
		private Integer projectId;
		/**
	* 
	*/
		private Integer companyId;
		/**
	* 
	*/
		private BigDecimal equipmentCostOfReport;
		/**
	* 
	*/
		private BigDecimal equipmentCostOfPaid;
		/**
	* 
	*/
		private BigDecimal installationCostOfReport;
		/**
	* 
	*/
		private BigDecimal installationCostOfPaid;
		/**
	* 
	*/
		private BigDecimal constructionCostOfReport;
		/**
	* 
	*/
		private BigDecimal constructionCostOfPaid;
		/**
	* 
	*/
		private BigDecimal softwareKitOfReport;
		/**
	* 
	*/
		private BigDecimal softwareKitOfPaid;
		/**
	* 
	*/
		private BigDecimal inspectionOfReport;
		/**
	* 
	*/
		private BigDecimal inspectionOfPaid;
		/**
	* 
	*/
		private BigDecimal digitalIntegrationOfReport;
		/**
	* 
	*/
		private BigDecimal digitalIntegrationOfPaid;
		/**
	* 
	*/
		private BigDecimal rdOutsourcingOfReport;
		/**
	* 
	*/
		private BigDecimal rdOutsourcingOfPaid;
		/**
	* 
	*/
		private BigDecimal waterOfReport;
		/**
	* 
	*/
		private BigDecimal waterOfPaid;
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
	    public void setCreateTime(Date createTime){
    	this.createTime=createTime;
	}
	public Date getCreateTime(){
		return createTime;
	}
	    public void setLastUpdatorId(Integer lastUpdatorId){
    	this.lastUpdatorId=lastUpdatorId;
	}
	public Integer getLastUpdatorId(){
		return lastUpdatorId;
	}
	    public void setLastUpdateTime(Date lastUpdateTime){
    	this.lastUpdateTime=lastUpdateTime;
	}
	public Date getLastUpdateTime(){
		return lastUpdateTime;
	}
	    public void setProjectId(Integer projectId){
    	this.projectId=projectId;
	}
	public Integer getProjectId(){
		return projectId;
	}
	    public void setCompanyId(Integer companyId){
    	this.companyId=companyId;
	}
	public Integer getCompanyId(){
		return companyId;
	}
	    public void setEquipmentCostOfReport(BigDecimal equipmentCostOfReport){
    	this.equipmentCostOfReport=equipmentCostOfReport;
	}
	public BigDecimal getEquipmentCostOfReport(){
		return equipmentCostOfReport;
	}
	    public void setEquipmentCostOfPaid(BigDecimal equipmentCostOfPaid){
    	this.equipmentCostOfPaid=equipmentCostOfPaid;
	}
	public BigDecimal getEquipmentCostOfPaid(){
		return equipmentCostOfPaid;
	}
	    public void setInstallationCostOfReport(BigDecimal installationCostOfReport){
    	this.installationCostOfReport=installationCostOfReport;
	}
	public BigDecimal getInstallationCostOfReport(){
		return installationCostOfReport;
	}
	    public void setInstallationCostOfPaid(BigDecimal installationCostOfPaid){
    	this.installationCostOfPaid=installationCostOfPaid;
	}
	public BigDecimal getInstallationCostOfPaid(){
		return installationCostOfPaid;
	}
	    public void setConstructionCostOfReport(BigDecimal constructionCostOfReport){
    	this.constructionCostOfReport=constructionCostOfReport;
	}
	public BigDecimal getConstructionCostOfReport(){
		return constructionCostOfReport;
	}
	    public void setConstructionCostOfPaid(BigDecimal constructionCostOfPaid){
    	this.constructionCostOfPaid=constructionCostOfPaid;
	}
	public BigDecimal getConstructionCostOfPaid(){
		return constructionCostOfPaid;
	}
	    public void setSoftwareKitOfReport(BigDecimal softwareKitOfReport){
    	this.softwareKitOfReport=softwareKitOfReport;
	}
	public BigDecimal getSoftwareKitOfReport(){
		return softwareKitOfReport;
	}
	    public void setSoftwareKitOfPaid(BigDecimal softwareKitOfPaid){
    	this.softwareKitOfPaid=softwareKitOfPaid;
	}
	public BigDecimal getSoftwareKitOfPaid(){
		return softwareKitOfPaid;
	}
	    public void setInspectionOfReport(BigDecimal inspectionOfReport){
    	this.inspectionOfReport=inspectionOfReport;
	}
	public BigDecimal getInspectionOfReport(){
		return inspectionOfReport;
	}
	    public void setInspectionOfPaid(BigDecimal inspectionOfPaid){
    	this.inspectionOfPaid=inspectionOfPaid;
	}
	public BigDecimal getInspectionOfPaid(){
		return inspectionOfPaid;
	}
	    public void setDigitalIntegrationOfReport(BigDecimal digitalIntegrationOfReport){
    	this.digitalIntegrationOfReport=digitalIntegrationOfReport;
	}
	public BigDecimal getDigitalIntegrationOfReport(){
		return digitalIntegrationOfReport;
	}
	    public void setDigitalIntegrationOfPaid(BigDecimal digitalIntegrationOfPaid){
    	this.digitalIntegrationOfPaid=digitalIntegrationOfPaid;
	}
	public BigDecimal getDigitalIntegrationOfPaid(){
		return digitalIntegrationOfPaid;
	}
	    public void setRdOutsourcingOfReport(BigDecimal rdOutsourcingOfReport){
    	this.rdOutsourcingOfReport=rdOutsourcingOfReport;
	}
	public BigDecimal getRdOutsourcingOfReport(){
		return rdOutsourcingOfReport;
	}
	    public void setRdOutsourcingOfPaid(BigDecimal rdOutsourcingOfPaid){
    	this.rdOutsourcingOfPaid=rdOutsourcingOfPaid;
	}
	public BigDecimal getRdOutsourcingOfPaid(){
		return rdOutsourcingOfPaid;
	}
	    public void setWaterOfReport(BigDecimal waterOfReport){
    	this.waterOfReport=waterOfReport;
	}
	public BigDecimal getWaterOfReport(){
		return waterOfReport;
	}
	    public void setWaterOfPaid(BigDecimal waterOfPaid){
    	this.waterOfPaid=waterOfPaid;
	}
	public BigDecimal getWaterOfPaid(){
		return waterOfPaid;
	}
	
}
