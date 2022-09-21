package com.yskc.ms.models.company;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by hck
 * on 2020/12/4 15:15
 * description:公司信息拓展
 */
public class ExtraModel implements Serializable {

    private Integer id;
    private String manageAddress;//经营地址
    private String registerAddrCode;//注册所在城市
    private String registerAddress;//注册地址
    private String registerType;//注册类型
    private String mainProducts;//主营产品
    private String officeAddrCode;//办公所在区
    private Integer cooperateOrg;//合作组织数量
    private Integer insideRdOrg;//内部研发机构数
    private String productAddrCode;//生产所在区
    private BigDecimal officeArea;//办公用房面积
    private Integer overSeaMarketings;//海外营销机构数
    private BigDecimal yearKWh;//全年用电量
    private BigDecimal productArea;//生产用房面积
    private Integer overSeaRdOrg;//海外研发机构数
    private BigDecimal yearMwo;//全年用水量
    private Boolean onlineReport;//是否统计联网直报企业
    private Boolean ownerECP;//是否拥有自营电子商务交易平台
    private String nameECP;//电子商务交易平台名称
    private String urlECP;//电子商务交易平台地址
    private String qualification;//单位资质: 国家高新技术企业,深圳市高新技术企业,深圳市软件企业,服务外包企业,先进服务业
    private Integer companyId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
