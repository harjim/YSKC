package com.yskc.rs.entity.proposal;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.rs.entity.tech.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * @DateTime: 2022/3/24 15:11
 * @Description:
 * @author: hsx
 */
@TableName("proposal_list")
public class ProposalListEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 8457784740697679524L;

    @TableId
    private Integer id;

    private Integer companyId;

    private Integer projectId;

    // 提案日期
    private Date proposalDate;

    //项目名
    private String pname;

    /**
     * 提案日期
     * 项目开展形式
     *  10.自主完成；
     *  21.与境内研究机构合作；
     *  22.与境内高等学校合作；
     *  23.与境内其他企业或单位合作；
     *  24.与境外机构合作；
     *  30.委托其他企业或单位；
     *  40.其他形式。
     */
    private Integer formula;

    // 立项部门
    private String deptName;

    // 项目开始日期
    private Date beginDate;

    //项目结束日期
    private Date endDate;

    // 试制开始日期
    private Date tBeginDate;

    //试制结束日期
    private Date tEndDate;

    //项目提出人
    private String initiator;

    //项目对应产品
    private String involvedProduct;

    //项目人员名单
    private String members;

    //主要设备仪器
    private String equipments;

    //现状分析
    private String situation;

    //主要研究内容
    private String research;

    //前后实施对比
    private String comparison;

    //进度计划
    private String progressPlan;

    //技术指标以及经济效益
    private String target;

    //创新点
    private String innovation;

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

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Date getProposalDate() {
        return proposalDate;
    }

    public void setProposalDate(Date proposalDate) {
        this.proposalDate = proposalDate;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Integer getFormula() {
        return formula;
    }

    public void setFormula(Integer formula) {
        this.formula = formula;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public String getInvolvedProduct() {
        return involvedProduct;
    }

    public void setInvolvedProduct(String involvedProduct) {
        this.involvedProduct = involvedProduct;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public String getEquipments() {
        return equipments;
    }

    public void setEquipments(String equipments) {
        this.equipments = equipments;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public String getResearch() {
        return research;
    }

    public void setResearch(String research) {
        this.research = research;
    }

    public String getComparison() {
        return comparison;
    }

    public void setComparison(String comparison) {
        this.comparison = comparison;
    }

    public String getProgressPlan() {
        return progressPlan;
    }

    public void setProgressPlan(String progressPlan) {
        this.progressPlan = progressPlan;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getInnovation() {
        return innovation;
    }

    public void setInnovation(String innovation) {
        this.innovation = innovation;
    }
}
