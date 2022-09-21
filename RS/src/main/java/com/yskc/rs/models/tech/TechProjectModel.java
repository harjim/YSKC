package com.yskc.rs.models.tech;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.tech
 * @Author: zhangdingfu
 * @CreateTime: 2019-09-23 14:34
 * @Description: 技改项目model
 */
public class TechProjectModel implements Serializable {

    private Integer id;

    private Integer pyear;

    @NotBlank(message = "项目名称不能为空")
    @Size(max = 200, message = "项目名称不能超过200个字")
    private String pname;

    private String reportType;

    private String aidType;

    @NotBlank(message = "负责人不能为空")
    @Size(max = 30, message = "负责人不能超过30个字")
    private String masterName;

    @NotBlank(message = "负责人电话不能为空")
    @Size(max = 30, message = "负责人电话不能超过30个字")
    private String masterTel;
    @NotBlank(message = "联系人不能为空")
    @Size(max = 30, message = "联系人不能超过30个字")
    private String linkName;

    @NotBlank(message = "联系人电话不能为空")
    @Size(max = 30, message = "联系人电话不能超过30个字")
    private String linkTel;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date applyDate;

    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date beginDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;

    public Integer getId() {
        return id;
    }

    private Integer productId;

    private Integer directionId;

    private Integer sourceProjectId;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getDirectionId() {
        return directionId;
    }

    public void setDirectionId(Integer directionId) {
        this.directionId = directionId;
    }

    public Integer getSourceProjectId() {
        return sourceProjectId;
    }

    public void setSourceProjectId(Integer sourceProjectId) {
        this.sourceProjectId = sourceProjectId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPyear() {
        return pyear;
    }

    public void setPyear(Integer pyear) {
        this.pyear = pyear;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getAidType() {
        return aidType;
    }

    public void setAidType(String aidType) {
        this.aidType = aidType;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public String getMasterTel() {
        return masterTel;
    }

    public void setMasterTel(String masterTel) {
        this.masterTel = masterTel;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getLinkTel() {
        return linkTel;
    }

    public void setLinkTel(String linkTel) {
        this.linkTel = linkTel;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
}
