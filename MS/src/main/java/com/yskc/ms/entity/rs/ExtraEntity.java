package com.yskc.ms.entity.rs;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by hck
 * on 2020/12/4 13:53
 * description:公司信息拓展
 */
@TableName("c_extra")
public class ExtraEntity {

    @TableId
    private Integer id;
    private Integer creatorId;
    private Integer lastUpdatorId;
    private Date createTime;
    private Date lastUpdateTime;
    private Integer msCreatorId;
    private Integer msLastUpdatorId;
    private String manageAddress;
    private String registerAddrCode;
    private String registerAddress;
    private String registerType;
    private String mainProducts;
    private String officeAddrCode;
    private Integer cooperateOrg;
    private Integer insideRdOrg;
    private String productAddrCode;
    private BigDecimal officeArea;
    private Integer overSeaMarketings;
    private BigDecimal yearKWh;
    private BigDecimal productArea;
    private Integer overSeaRdOrg;
    private BigDecimal yearMwo;
    private Boolean onlineReport;
    private Boolean ownerECP;
    private String nameECP;
    private String urlECP;
    private String qualification;
    private Integer companyId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
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

    public Integer getMsCreatorId() {
        return msCreatorId;
    }

    public void setMsCreatorId(Integer msCreatorId) {
        this.msCreatorId = msCreatorId;
    }

    public Integer getMsLastUpdatorId() {
        return msLastUpdatorId;
    }

    public void setMsLastUpdatorId(Integer msLastUpdatorId) {
        this.msLastUpdatorId = msLastUpdatorId;
    }

    public String getManageAddress() {
        return manageAddress;
    }

    public void setManageAddress(String manageAddress) {
        this.manageAddress = manageAddress;
    }

    public String getRegisterAddrCode() {
        return registerAddrCode;
    }

    public void setRegisterAddrCode(String registerAddrCode) {
        this.registerAddrCode = registerAddrCode;
    }

    public String getRegisterAddress() {
        return registerAddress;
    }

    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
    }

    public String getRegisterType() {
        return registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType;
    }

    public String getMainProducts() {
        return mainProducts;
    }

    public void setMainProducts(String mainProducts) {
        this.mainProducts = mainProducts;
    }

    public String getOfficeAddrCode() {
        return officeAddrCode;
    }

    public void setOfficeAddrCode(String officeAddrCode) {
        this.officeAddrCode = officeAddrCode;
    }

    public Integer getCooperateOrg() {
        return cooperateOrg;
    }

    public void setCooperateOrg(Integer cooperateOrg) {
        this.cooperateOrg = cooperateOrg;
    }

    public Integer getInsideRdOrg() {
        return insideRdOrg;
    }

    public void setInsideRdOrg(Integer insideRdOrg) {
        this.insideRdOrg = insideRdOrg;
    }

    public String getProductAddrCode() {
        return productAddrCode;
    }

    public void setProductAddrCode(String productAddrCode) {
        this.productAddrCode = productAddrCode;
    }

    public BigDecimal getOfficeArea() {
        return officeArea;
    }

    public void setOfficeArea(BigDecimal officeArea) {
        this.officeArea = officeArea;
    }

    public Integer getOverSeaMarketings() {
        return overSeaMarketings;
    }

    public void setOverSeaMarketings(Integer overSeaMarketings) {
        this.overSeaMarketings = overSeaMarketings;
    }

    public BigDecimal getYearKWh() {
        return yearKWh;
    }

    public void setYearKWh(BigDecimal yearKWh) {
        this.yearKWh = yearKWh;
    }

    public BigDecimal getProductArea() {
        return productArea;
    }

    public void setProductArea(BigDecimal productArea) {
        this.productArea = productArea;
    }

    public Integer getOverSeaRdOrg() {
        return overSeaRdOrg;
    }

    public void setOverSeaRdOrg(Integer overSeaRdOrg) {
        this.overSeaRdOrg = overSeaRdOrg;
    }

    public BigDecimal getYearMwo() {
        return yearMwo;
    }

    public void setYearMwo(BigDecimal yearMwo) {
        this.yearMwo = yearMwo;
    }

    public Boolean getOnlineReport() {
        return onlineReport;
    }

    public void setOnlineReport(Boolean onlineReport) {
        this.onlineReport = onlineReport;
    }

    public Boolean getOwnerECP() {
        return ownerECP;
    }

    public void setOwnerECP(Boolean ownerECP) {
        this.ownerECP = ownerECP;
    }

    public String getNameECP() {
        return nameECP;
    }

    public void setNameECP(String nameECP) {
        this.nameECP = nameECP;
    }

    public String getUrlECP() {
        return urlECP;
    }

    public void setUrlECP(String urlECP) {
        this.urlECP = urlECP;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
