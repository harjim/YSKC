package com.yskc.rs.models.hightech;


import java.io.Serializable;

/**
 * @Author: hck
 * @DateTime: 2021/5/28 10:33
 * @Description:高品详情
 */
public class HighTechDetailModel implements Serializable {

    private Integer id;
    private Integer highTechId;
    // 同类产品是否已有技术标准 [有，无]
    private Boolean hasSameTechStandard;
    // 技术标准 [0:国际标准,1:国家标准,2:行业标准,3:企业标准,4:其他]
    private String techStandard;
    // 其他标准,当技术标准[standard]选[4:其他]时，可输入该项。
    private String otherTechStandard;
    // 新产品是否制定质量标准[有，无]
    private Boolean hasQualityStandard;
    // 质量标准,当新产品是否制定质量标准[hasQualityStandard]为true时，可输入该项。
    private String qualityStandard;
    // 新产品是否通过鉴定或第三方检测 [是，否]
    private Boolean passTest;
    // 新产品技术标准指标或鉴定指标是否达到国内外先进技术标准的水平
    ///[0:全部指标达国际标准水平, 1;部分指标达国际标准水平, 2:全部指标达国内标准先进水平,
    //  3:部分指标达国内先进标准水平, 4:仅达到国内产品基本标准要求水平]
    private Integer techLevel;
    // 新产品是否国内首创新产品 [是，否]
    private Boolean domesticFirst;
    // 技术指标是否达到国际标准水平 [0:全部指标达国际标准先进水平,1:部分指标达国际标准先进水平,2全部指标与国际标准先进水平存在差距]
    private Integer internationalLevel;
    // 项目来源 [0:国家计划,1:部门计划,2:省计划,3:市计划,4自行开发]
    private Integer projectSource;
    // 技术来源 [0:国外技术,1:国内高校、科研院所技术,2:自有技术]
    private Integer techSource;
    // 产品开发形式 [0:合作开发,1:独立开发,2:消化引进吸收,3:其它]
    private Integer devType;
    // 本高新技术产品先进性说明
    private String advancedExplain;
    // 相关的专利
    private String patents;
    // 应用专利关键技术
    private String patentsTech;
    // 新产品与国际标准、国内标准（国标、行标、先进企业标准）具体技术指标数据比较情况。
    // 主要技术指标或与之前技术对比
    private String techCompare;
    // 与同类产品（服务）或研究之前产品的优势
    private String advantage;
    // 高新技术产品认定或第三方评价
    private String evaluate;
    // 产品对产业发展的影响和贡献情况
    private String contribution;
    //自主知识产权含量 [0:核心技术,1:较高,2:一般,3:无]
    private Integer ownerProperty;
    //创新性 [0:首创,1:重大改进,2:较大改进,3:消化吸收]
    private Integer innovation;
    //先进性 [0:国际领先,1:国际先进,2:国内领先,3:国内先进,4:省内先进]
    private Integer advanced;
    //成熟度 [0:产品样机（样品）,1:小批量生产,2:批量（规模）生产]
    private Integer maturity;
    //产品获奖情况 [0:国家级,1:部、省级,2:市级,3:其它,4:无]
    private Integer award;

    public Integer getAward() {
        return award;
    }

    public void setAward(Integer award) {
        this.award = award;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHighTechId() {
        return highTechId;
    }

    public void setHighTechId(Integer highTechId) {
        this.highTechId = highTechId;
    }

    public Boolean getHasSameTechStandard() {
        return hasSameTechStandard;
    }

    public void setHasSameTechStandard(Boolean hasSameTechStandard) {
        this.hasSameTechStandard = hasSameTechStandard;
    }

    public String getTechStandard() {
        return techStandard;
    }

    public void setTechStandard(String techStandard) {
        this.techStandard = techStandard;
    }

    public String getOtherTechStandard() {
        return otherTechStandard;
    }

    public void setOtherTechStandard(String otherTechStandard) {
        this.otherTechStandard = otherTechStandard;
    }

    public Boolean getHasQualityStandard() {
        return hasQualityStandard;
    }

    public void setHasQualityStandard(Boolean hasQualityStandard) {
        this.hasQualityStandard = hasQualityStandard;
    }

    public String getQualityStandard() {
        return qualityStandard;
    }

    public void setQualityStandard(String qualityStandard) {
        this.qualityStandard = qualityStandard;
    }

    public Boolean getPassTest() {
        return passTest;
    }

    public void setPassTest(Boolean passTest) {
        this.passTest = passTest;
    }

    public Integer getTechLevel() {
        return techLevel;
    }

    public void setTechLevel(Integer techLevel) {
        this.techLevel = techLevel;
    }

    public Boolean getDomesticFirst() {
        return domesticFirst;
    }

    public void setDomesticFirst(Boolean domesticFirst) {
        this.domesticFirst = domesticFirst;
    }

    public Integer getInternationalLevel() {
        return internationalLevel;
    }

    public void setInternationalLevel(Integer internationalLevel) {
        this.internationalLevel = internationalLevel;
    }

    public Integer getProjectSource() {
        return projectSource;
    }

    public void setProjectSource(Integer projectSource) {
        this.projectSource = projectSource;
    }

    public Integer getTechSource() {
        return techSource;
    }

    public void setTechSource(Integer techSource) {
        this.techSource = techSource;
    }

    public Integer getDevType() {
        return devType;
    }

    public void setDevType(Integer devType) {
        this.devType = devType;
    }

    public String getAdvancedExplain() {
        return advancedExplain;
    }

    public void setAdvancedExplain(String advancedExplain) {
        this.advancedExplain = advancedExplain;
    }

    public String getPatents() {
        return patents;
    }

    public void setPatents(String patents) {
        this.patents = patents;
    }

    public String getPatentsTech() {
        return patentsTech;
    }

    public void setPatentsTech(String patentsTech) {
        this.patentsTech = patentsTech;
    }

    public String getTechCompare() {
        return techCompare;
    }

    public void setTechCompare(String techCompare) {
        this.techCompare = techCompare;
    }

    public String getAdvantage() {
        return advantage;
    }

    public void setAdvantage(String advantage) {
        this.advantage = advantage;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public String getContribution() {
        return contribution;
    }

    public void setContribution(String contribution) {
        this.contribution = contribution;
    }

    public Integer getOwnerProperty() {
        return ownerProperty;
    }

    public void setOwnerProperty(Integer ownerProperty) {
        this.ownerProperty = ownerProperty;
    }

    public Integer getInnovation() {
        return innovation;
    }

    public void setInnovation(Integer innovation) {
        this.innovation = innovation;
    }

    public Integer getAdvanced() {
        return advanced;
    }

    public void setAdvanced(Integer advanced) {
        this.advanced = advanced;
    }

    public Integer getMaturity() {
        return maturity;
    }

    public void setMaturity(Integer maturity) {
        this.maturity = maturity;
    }
}
