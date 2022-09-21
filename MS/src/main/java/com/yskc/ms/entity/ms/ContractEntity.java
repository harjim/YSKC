package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.ms.entity.ms.models.MsBaseEntity;

import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @program: ms
 * @description:
 * @author: wjy
 * @create: 2022-08-30 10:31
 **/
@TableName("contract")
public class ContractEntity extends MsBaseEntity {
    @TableId
    private Integer id;
    private Integer customerId;
    private Integer deptId;
    private Integer projectCnt;
    private Integer partnerCnt;
    private String contractNo;

    private Date signDate;
    private Date effectDate;
    private Date closeDate;

    private Integer techId;
    private Integer finaId;
    private String sealType;
    @Size(max=200,message="文件路径不能超过200个字")
    private String filepath;
    private String qrcode;
    @Size(max=200,message="备注不能超过200个字")
    private String remark;
    private String expressNo;
    private String expressAddress;

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getExpressAddress() {
        return expressAddress;
    }

    public void setExpressAddress(String expressAddress) {
        this.expressAddress = expressAddress;
    }

    public String getSealType() {
        return sealType;
    }

    public void setSealType(String sealType) {
        this.sealType = sealType;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
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

    public Integer getProjectCnt() {
        return projectCnt;
    }

    public void setProjectCnt(Integer projectCnt) {
        this.projectCnt = projectCnt;
    }

    public Integer getPartnerCnt() {
        return partnerCnt;
    }

    public void setPartnerCnt(Integer partnerCnt) {
        this.partnerCnt = partnerCnt;
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
}
