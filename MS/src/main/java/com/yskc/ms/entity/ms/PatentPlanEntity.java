package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.ms.entity.ms.models.MsBaseEntity;

import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/8/19 10:34
 * @Description:
 */
@TableName("patent_plan")
public class PatentPlanEntity extends MsBaseEntity {
    @TableId
    private Integer id;
    private Integer customerId;
    private String patentNo;
    private String patentName;// 申请名称
    private Integer patentType;// 专利类型 '1': '发明专利','2': '实用新型', '3': '外观设计'
    private Integer unitType;// 单位类型 0：工矿企业，1：个人，2大专院校，3：科研单位，4：事业单位
    private Integer planType;// 申请类型（版本）：0：高新版，1：保护版
    private Integer year;
    private Integer projectId;
    private String code;// 身份证号码或统一信息代码
    private String linkMan;// 联系人
    private String linkTel;// 联系方式
    private String postalCode;// 邮政编码
    private String otherItem;// 其他事项（多选） 0：发明+实用新型同时申请 1：申请时办理提前公开请求 2：是否办理费减
    private String address;// 详情地址
    private String firstInventor;// 第一发明人
    private String otherInventor;// 其他发明人
    private Integer process;// 0：发起，1：审批，2：初稿，3：定稿:4：受理，5：授权
    private Integer ownerId;// 业务员
    private Integer masterId;//代理人id
    private Integer engineerId;//工程师id
    private String remark;//备注
    private String applicant;//申请人
    private Date demandDate;
    private Date submitDate;
    private Integer demandId;
    private String confirmName;
    private Integer confirmType;

    public String getConfirmName() {
        return confirmName;
    }

    public void setConfirmName(String confirmName) {
        this.confirmName = confirmName;
    }

    public Integer getConfirmType() {
        return confirmType;
    }

    public void setConfirmType(Integer confirmType) {
        this.confirmType = confirmType;
    }

    public Integer getEngineerId() {
        return engineerId;
    }

    public void setEngineerId(Integer engineerId) {
        this.engineerId = engineerId;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getProcess() {
        return process;
    }

    public void setProcess(Integer process) {
        this.process = process;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
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

    public Integer getDemandId() {
        return demandId;
    }

    public void setDemandId(Integer demandId) {
        this.demandId = demandId;
    }
}
