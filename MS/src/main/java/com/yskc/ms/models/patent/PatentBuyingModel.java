package com.yskc.ms.models.patent;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/7/9 11:02
 * @Description:专利购买
 */
public class PatentBuyingModel implements Serializable {

    private Integer id;

    private String filePath;

    private Integer status;

    private Integer customerId;

    private Integer demandId;

    private Integer patentSeaId;

    private String companyName;

    private String patentNo;

    private String patentName;

    private Integer mainType;

    private String inventor;

    private Date applyDateTime;

    private String patentSeaFile;

    private String sellFile;

    public String getPatentSeaFile() {
        return patentSeaFile;
    }

    public void setPatentSeaFile(String patentSeaFile) {
        this.patentSeaFile = patentSeaFile;
    }

    public Date getApplyDateTime() {
        return applyDateTime;
    }

    public void setApplyDateTime(Date applyDateTime) {
        this.applyDateTime = applyDateTime;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public Integer getMainType() {
        return mainType;
    }

    public void setMainType(Integer mainType) {
        this.mainType = mainType;
    }

    public String getInventor() {
        return inventor;
    }

    public void setInventor(String inventor) {
        this.inventor = inventor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getDemandId() {
        return demandId;
    }

    public void setDemandId(Integer demandId) {
        this.demandId = demandId;
    }

    public Integer getPatentSeaId() {
        return patentSeaId;
    }

    public void setPatentSeaId(Integer patentSeaId) {
        this.patentSeaId = patentSeaId;
    }

    public String getSellFile() {
        return sellFile;
    }

    public void setSellFile(String sellFile) {
        this.sellFile = sellFile;
    }
}
