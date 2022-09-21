package com.yskc.ms.models.contract;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * @program: ms
 * @description:
 * @author: wjy
 * @create: 2022-08-30 13:38
 **/
public class AddContractModel {
    private Integer id;
    private Integer customerId;
    private Integer deptId;
    private Integer ownerId;
    private Integer techId;
    private Integer finaId;
    private Integer businessId;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date signDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date effectDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date closeDate;

    private String prefix;//合同编号前缀
    private String filepath;
    private String qrcode;
    @Size(max=200,message="备注不能超过200个字")
    private String remark;
    private List<Integer> sealType;
    private List<ContractMemberModel> memberList;
    private List<ContractProjectModel> projectList;

    public List<Integer> getSealType() {
        return sealType;
    }

    public void setSealType(List<Integer> sealType) {
        this.sealType = sealType;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
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

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getTechId() {
        return techId;
    }

    public void setTechId(Integer techId) {
        this.techId = techId;
    }

    public Integer getFinaId() {
        return finaId;
    }

    public void setFinaId(Integer finaId) {
        this.finaId = finaId;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public Date getEffectDate() {
        return effectDate;
    }

    public void setEffectDate(Date effectDate) {
        this.effectDate = effectDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<ContractMemberModel> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<ContractMemberModel> memberList) {
        this.memberList = memberList;
    }

    public List<ContractProjectModel> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ContractProjectModel> projectList) {
        this.projectList = projectList;
    }
}
