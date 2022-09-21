package com.yskc.ms.models.patent;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.*;

/**
 * @program: ms
 * @description: 专利需求model
 * @author: cyj
 * @create: 2022-02-09 08:46
 **/
public class PatentDemandModel implements Serializable {
    private Integer id;
    //需求编号,格式yymmdd0001
    private String demandNo;
    private Integer companyId;
    //客户名称
    private String customerName;
    private Integer customerId;
    //年份
    private Integer year;
    //需求类型 0:购买 1:撰写
    private Integer type;
    //发明专利需求数量
    private Integer inventionNum;
    //实用新型需求数量
    private Integer utilityNum;
    //外观设计需求数量
    private Integer appearanceDesignNum;
    //总计
    private Integer total;
    //业务员
    private String ownerName;
    //技术人员(工程师)
    private String techName;
    //业务员id
    private Integer ownerId;
    //技术人员(工程师)
    private List<Map<String,Object>> techIds = new ArrayList<>();
    //备注
    private String remark;
    //委托书路径
    private String proxyPath;
    //营业执照路径
    private String blPath;
    //减免路径
    private String remissionPath;
    //其它资料路径
    private String otherPath;
    //计划提交国知局日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date planSubmitDate;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

    // 实际工程师申请过的数量
    private Integer noOfPlan;

    //软件著作
    private Integer softRightNum;
    private String groupName;
    private Integer groupId;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDemandNo() {
        return demandNo;
    }

    public void setDemandNo(String demandNo) {
        this.demandNo = demandNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getInventionNum() {
        return inventionNum;
    }

    public void setInventionNum(Integer inventionNum) {
        this.inventionNum = inventionNum;
    }

    public Integer getUtilityNum() {
        return utilityNum;
    }

    public void setUtilityNum(Integer utilityNum) {
        this.utilityNum = utilityNum;
    }

    public Integer getAppearanceDesignNum() {
        return appearanceDesignNum;
    }

    public void setAppearanceDesignNum(Integer appearanceDesignNum) {
        this.appearanceDesignNum = appearanceDesignNum;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getTechName() {
        return techName;
    }

    public void setTechName(String techName) {
        this.techName = techName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProxyPath() {
        return proxyPath;
    }

    public void setProxyPath(String proxyPath) {
        this.proxyPath = proxyPath;
    }

    public String getBlPath() {
        return blPath;
    }

    public void setBlPath(String blPath) {
        this.blPath = blPath;
    }

    public String getRemissionPath() {
        return remissionPath;
    }

    public void setRemissionPath(String remissionPath) {
        this.remissionPath = remissionPath;
    }

    public String getOtherPath() {
        return otherPath;
    }

    public void setOtherPath(String otherPath) {
        this.otherPath = otherPath;
    }

    public Date getPlanSubmitDate() {
        return planSubmitDate;
    }

    public void setPlanSubmitDate(Date planSubmitDate) {
        this.planSubmitDate = planSubmitDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public List<Map<String, Object>> getTechIds() {
        return techIds;
    }

    public void setTechIds(List<Map<String, Object>> techIds) {
        this.techIds = techIds;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getSoftRightNum() {
        return softRightNum;
    }

    public void setSoftRightNum(Integer softRightNum) {
        this.softRightNum = softRightNum;
    }

    public Integer getNoOfPlan() {
        return noOfPlan;
    }

    public void setNoOfPlan(Integer noOfPlan) {
        this.noOfPlan = noOfPlan;
    }
}
