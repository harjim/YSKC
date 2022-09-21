package com.xxl.job.executor.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @DateTime: 2021/12/30 8:24
 * @Description:
 * @author: hsx
 */
@TableName("patent_plan")
public class PatentPlanEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;

    private Integer creatorId;

    private Integer lastUpdatorId;

    private Date createTime;

    private Date lastUpdateTime;

    // 公司id
    private Integer customerId;

    // 专利号
    private String patentNo;

    // 申请名称
    private String patentName;

    // 专利类型 '1': '发明专利','2': '实用新型', '3': '外观设计'
    private Integer patentType;

    // 单位类型 0：工矿企业，1：个人，2大专院校，3：科研单位，4：事业单位
    private Integer unitType;

    // 申请类型（版本）：0：高新版，1：保护版
    private Integer planType;

    // 年份
    private Integer year;

    // 项目ID 没有为-1(其他)
    private Integer projectId;

    // 身份证号码或统一信息代码
    private String code;

    // 联系人
    private String linkMan;

    // 联系方式
    private String linkTel;

    // 邮政编码
    private String postalCode;

    // 其他事项（多选） 0：发明+实用新型同时申请 1：申请时办理提前公开请求 2：是否办理费减
    private String otherItem;

    // 详细地址
    private String address;

    // 第一发明人
    private String firstInventor;

    // 其他发明人
    private String otherInventor;

    // 工程师id
    private Integer ownerId;

    // 0：审批，1：初稿，2：定稿，3：受理，4：授权
    private Integer process;

    // 业务员
    private String applicant;

    // 申请人
    private Integer engineerId;

    // 提交国知局时间
    private Date demandDate;

    // 初稿需求时间
    private Date submitDate;

    // 代理商
    private Integer masterId;

    // 备注
    private String remark;

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

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getPatentNo() {
        return patentNo;
    }

    public void setPatentNo(String patentNo) {
        this.patentNo = patentNo;
    }

    public String getPatentName() {
        return patentName;
    }

    public void setPatentName(String patentName) {
        this.patentName = patentName;
    }

    public Integer getPatentType() {
        return patentType;
    }

    public void setPatentType(Integer patentType) {
        this.patentType = patentType;
    }

    public Integer getUnitType() {
        return unitType;
    }

    public void setUnitType(Integer unitType) {
        this.unitType = unitType;
    }

    public Integer getPlanType() {
        return planType;
    }

    public void setPlanType(Integer planType) {
        this.planType = planType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getLinkTel() {
        return linkTel;
    }

    public void setLinkTel(String linkTel) {
        this.linkTel = linkTel;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getOtherItem() {
        return otherItem;
    }

    public void setOtherItem(String otherItem) {
        this.otherItem = otherItem;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirstInventor() {
        return firstInventor;
    }

    public void setFirstInventor(String firstInventor) {
        this.firstInventor = firstInventor;
    }

    public String getOtherInventor() {
        return otherInventor;
    }

    public void setOtherInventor(String otherInventor) {
        this.otherInventor = otherInventor;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getProcess() {
        return process;
    }

    public void setProcess(Integer process) {
        this.process = process;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public Integer getEngineerId() {
        return engineerId;
    }

    public void setEngineerId(Integer engineerId) {
        this.engineerId = engineerId;
    }

    public Date getDemandDate() {
        return demandDate;
    }

    public void setDemandDate(Date demandDate) {
        this.demandDate = demandDate;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public Integer getMasterId() {
        return masterId;
    }

    public void setMasterId(Integer masterId) {
        this.masterId = masterId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
