package com.yskc.rs.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.rs.entity.tech.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * @DateTime: 2021/11/2 14:29
 * @Description:计算机软件著作实体类
 * @author: hsx
 */
@TableName("ICRegistration")
public class ICRegistrationEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 6938137650707837158L;

    @TableId
    private Integer id;

    private Integer companyId;

    private Integer projectId;

    private String desginName;      //设计名称

    private String registerNo;      //设计登记号   唯一

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date applyDate;         //申请日期

    private String ownerName;    //权利人名称

    private String ownerAddress;    //权利人地址

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date issueDate;     //颁证日期

    private String certificateNo;       //登记证书号   非必填，唯一

    private String filepath;            //文件路径

    private String claimContent;    //权利要求内容

    private Integer claimNum;       //权利要求数量

    private Integer source;         //来源

    private String specification;   //说明书内容

    private Integer usedCnt;        //使用次数

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

    public String getDesginName() {
        return desginName;
    }

    public void setDesginName(String desginName) {
        this.desginName = desginName;
    }

    public String getRegisterNo() {
        return registerNo;
    }

    public void setRegisterNo(String registerNo) {
        this.registerNo = registerNo;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public void setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getClaimContent() {
        return claimContent;
    }

    public void setClaimContent(String claimContent) {
        this.claimContent = claimContent;
    }

    public Integer getClaimNum() {
        return claimNum;
    }

    public void setClaimNum(Integer claimNum) {
        this.claimNum = claimNum;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public Integer getUsedCnt() {
        return usedCnt;
    }

    public void setUsedCnt(Integer usedCnt) {
        this.usedCnt = usedCnt;
    }
}
