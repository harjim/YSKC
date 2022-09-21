package com.yskc.rs.models.projectdesign;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.rs.entity.DesignEntity;
import com.yskc.rs.models.UsedModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 研发设计费用
 */
public class ProjectDesignModel implements Serializable {
    private Integer id;
    private Integer projectId;
    private BigDecimal dFee;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date designDate;
    private String dname;
    private Date createTime;
    private String deptName;
    @Deprecated
    private String rdDeptPath;
    @Deprecated
    private String deptPath;

    private Integer deptId;
    private Integer rdDeptId;

    private BigDecimal designCount;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date projectMonth;

    private BigDecimal rdAmount;//研发费用

    private BigDecimal remainDFee;//剩余金额

    private List<UsedModel> usedModels;

    private Integer designDataId;

    public Integer getDesignDataId() {
        return designDataId;
    }

    public void setDesignDataId(Integer designDataId) {
        this.designDataId = designDataId;
    }

    public List<UsedModel> getUsedModels() {
        return usedModels;
    }

    public void setUsedModels(List<UsedModel> usedModels) {
        this.usedModels = usedModels;
    }

    public BigDecimal getRemainDFee() {
        return remainDFee;
    }

    public void setRemainDFee(BigDecimal remainDFee) {
        this.remainDFee = remainDFee;
    }

    public BigDecimal getRdAmount() {
        return rdAmount;
    }

    public void setRdAmount(BigDecimal rdAmount) {
        this.rdAmount = rdAmount;
    }

    public Date getProjectMonth() {
        return projectMonth;
    }

    public void setProjectMonth(Date projectMonth) {
        this.projectMonth = projectMonth;
    }

    public BigDecimal getDesignCount() {
        return designCount;
    }

    public void setDesignCount(BigDecimal designCount) {
        this.designCount = designCount;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    private List<DesignEntity> designEntityList;

    public List<DesignEntity> getDesignEntityList() {
        return designEntityList;
    }

    public void setDesignEntityList(List<DesignEntity> designEntityList) {
        this.designEntityList = designEntityList;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }


    public Date getDesignDate() {
        return designDate;
    }

    public BigDecimal getdFee() {
        return dFee;
    }

    public void setdFee(BigDecimal dFee) {
        this.dFee = dFee;
    }

    public void setDesignDate(Date designDate) {
        this.designDate = designDate;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getRdDeptPath() {
        return rdDeptPath;
    }

    public void setRdDeptPath(String rdDeptPath) {
        this.rdDeptPath = rdDeptPath;
    }

    public String getDeptPath() {
        return deptPath;
    }

    public void setDeptPath(String deptPath) {
        this.deptPath = deptPath;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getRdDeptId() {
        return rdDeptId;
    }

    public void setRdDeptId(Integer rdDeptId) {
        this.rdDeptId = rdDeptId;
    }
}
