package com.yskc.docservice.entity.rs.project;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-15 10:15:45
 */
@TableName("p_project")
public class ProjectEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;
    /**
     *
     */
    private String pname;
    /**
     *
     */
    private Integer rdIndex;
    /**
     *
     */
    private Date beginDate;
    /**
     *
     */
    private Date endDate;
    /**
     *
     */
    private Integer companyId;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Integer creatorId;
    /**
     *
     */
    private Integer estimateExpense;
    /**
     *
     */
    private String masterENumber;
    /**
     *
     */
    private String tecIndustry;

    private Integer rdDeptId;

    private Integer lastUpdatorId;

    private Date lastUpdateTime;

    private Integer msCreatorId;
    private Integer msLastUpdatorId;
    /**
     * 项目来源
     * /// 1.本企业自选项目；
     * /// 2.政府部门科技项目；
     * /// 3.其他企业（单位）委托项目；
     * /// 4.境外项目；
     * /// 5.其他项目。
     */
    private Integer projectSource;

    /**
     * /// 项目开展形式
     * ///  10.自主完成；
     * ///  21.与境内研究机构合作；
     * ///  22.与境内高等学校合作；
     * ///  23.与境内其他企业或单位合作；\
     * <p>
     * ///  24.与境外机构合作；
     * ///  30.委托其他企业或单位；
     * ///  40.其他形式
     */
    private Integer formula;
    /**
     * '01': '论文或专著',
     * '02': '新产品、新工艺等推广与示范活动',
     * '03': '对已有产品、工艺等进行一般性改进',
     * '04': '对已有产品、工艺等实现突破性变革',
     * '05': '软件著作权',
     * '06': '应用软件',
     * '07': '中间件或新算法',
     * '08': '基础软件',
     * '09': '发明专利',
     * '10': '实用新型专利或外观设计专利',
     * '11': '带有技术、工艺参数的图纸、技术标准、操作规范、技术论证、研究报告、咨询评价',
     * '12': '自主研制的新产品原型或样机、样件、样品、配方、新装置',
     * '13': '自主开发的新技术或新工艺、新工法、新服务',
     * '14': '其他'
     */
    private String result;
    /**
     * /// 项目技术经济目标
     * ///   1.科学原理的探索、发现；
     * ///   2.技术原理的研究；
     * ///   3.开发全新产品；
     * ///   4.增加产品功能或提高性能；
     * ///   5.提高劳动生产率；
     * ///   6.减少能源消耗或提高能源使用效率；
     * ///   7.节约原材料；
     * ///   8.减少环境污染；
     * ///   9.其他。
     */
    public Integer targets;

    private BigDecimal govCost;

    private Integer beginYear;
    private Integer endYear;

    private Boolean trialProd;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date tBeginDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date tEndDate;

    private String rdNumber;

    private Integer deptId;
    private String deptName;//部门

    private String workshop;//车间

    private String productLine;//产线

    private String processSection;//工艺段

    private Integer parentId;

    private Boolean hasChild;

    private String rdTitle;

    private String involvedProduct;//涉及产品

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getInvolvedProduct() {
        return involvedProduct;
    }

    public void setInvolvedProduct(String involvedProduct) {
        this.involvedProduct = involvedProduct;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getWorkshop() {
        return workshop;
    }

    public void setWorkshop(String workshop) {
        this.workshop = workshop;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getProcessSection() {
        return processSection;
    }

    public void setProcessSection(String processSection) {
        this.processSection = processSection;
    }

    public String getRdNumber() {
        return rdNumber;
    }

    public void setRdNumber(String rdNumber) {
        this.rdNumber = rdNumber;
    }

    public Boolean getTrialProd() {
        return trialProd;
    }

    public void setTrialProd(Boolean trialProd) {
        this.trialProd = trialProd;
    }

    public Date gettBeginDate() {
        return tBeginDate;
    }

    public void settBeginDate(Date tBeginDate) {
        this.tBeginDate = tBeginDate;
    }

    public Date gettEndDate() {
        return tEndDate;
    }

    public void settEndDate(Date tEndDate) {
        this.tEndDate = tEndDate;
    }

    public Integer getBeginYear() {
        return beginYear;
    }

    public void setBeginYear(Integer beginYear) {
        this.beginYear = beginYear;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }

    public BigDecimal getGovCost() {
        return govCost;
    }

    public void setGovCost(BigDecimal govCost) {
        this.govCost = govCost;
    }

    public Integer getProjectSource() {
        return projectSource;
    }

    public void setProjectSource(Integer projectSource) {
        this.projectSource = projectSource;
    }

    public Integer getFormula() {
        return formula;
    }

    public void setFormula(Integer formula) {
        this.formula = formula;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getTargets() {
        return targets;
    }

    public void setTargets(Integer targets) {
        this.targets = targets;
    }

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
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

    public Integer getRdDeptId() {
        return rdDeptId;
    }

    public void setRdDeptId(Integer rdDeptId) {
        this.rdDeptId = rdDeptId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPname() {
        return pname;
    }

    public void setRdIndex(Integer rdIndex) {
        this.rdIndex = rdIndex;
    }

    public Integer getRdIndex() {
        return rdIndex;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setEstimateExpense(Integer estimateExpense) {
        this.estimateExpense = estimateExpense;
    }

    public Integer getEstimateExpense() {
        return estimateExpense;
    }

    public void setMasterENumber(String masterENumber) {
        this.masterENumber = masterENumber;
    }

    public String getMasterENumber() {
        return masterENumber;
    }

    public void setTecIndustry(String tecIndustry) {
        this.tecIndustry = tecIndustry;
    }

    public String getTecIndustry() {
        return tecIndustry;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Boolean getHasChild() {
        return hasChild;
    }

    public void setHasChild(Boolean hasChild) {
        this.hasChild = hasChild;
    }
}
