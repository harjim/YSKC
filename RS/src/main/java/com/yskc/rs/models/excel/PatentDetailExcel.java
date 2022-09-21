package com.yskc.rs.models.excel;

import com.yskc.common.annotation.Excel;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hck
 * on 2020/6/29 16:41
 * description:导入专利
 */
public class PatentDetailExcel implements Serializable {

    @Excel(name = "专利号", order = 0, fieldName = "patentNo")
    private String patentNo;
    @Excel(name = "专利名称", order = 1, fieldName = "patentName")
    private String patentName;
    @Excel(name = "专利类型", order = 2, fieldName = "mainType")
    private String mainType;
    @Excel(name = "申请日期", order = 3, fieldName = "applyDateTime")
    private Date applyDateTime;
    @Excel(name = "发明人", order = 4, fieldName = "inventor")
    private String inventor;
    private Integer claimNum;
    private String claimContent;
    private Integer usedCnt;
    private String specification;
    private Date authDate;
    private String summary;

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

    public String getMainType() {
        return mainType;
    }

    public void setMainType(String mainType) {
        this.mainType = mainType;
    }

    public Date getApplyDateTime() {
        return applyDateTime;
    }

    public void setApplyDateTime(Date applyDateTime) {
        this.applyDateTime = applyDateTime;
    }

    public String getInventor() {
        return inventor;
    }

    public void setInventor(String inventor) {
        this.inventor = inventor;
    }

    public Integer getClaimNum() {
        return claimNum;
    }

    public void setClaimNum(Integer claimNum) {
        this.claimNum = claimNum;
    }

    /**
     * 字符长度尝过1000 ，从1000处切断
     *
     * @return
     */
    public String getClaimContent() {
        return claimContent != null ? claimContent.length() < 2000 ? claimContent : claimContent.substring(0, 1000) : null;
    }

    public void setClaimContent(String claimContent) {
        this.claimContent = claimContent;
    }

    public Integer getUsedCnt() {
        return usedCnt;
    }

    public void setUsedCnt(Integer usedCnt) {
        this.usedCnt = usedCnt;
    }

    /**
     * 字符长度尝过2000 ，从2000处切断
     */
    public String getSpecification() {
        return specification != null ? specification.length() < 2000 ? specification : specification.substring(0, 2000) : null;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public Date getAuthDate() {
        return authDate;
    }

    public void setAuthDate(Date authDate) {
        this.authDate = authDate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
