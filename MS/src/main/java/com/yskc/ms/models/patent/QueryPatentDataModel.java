package com.yskc.ms.models.patent;

import cn.hutool.core.date.DateUtil;
import com.yskc.ms.models.params.PageParams;

import java.util.Date;

/**
 * Created by hck
 * on 2020/9/11 8:40
 * description:专利数据查询model
 */
public class QueryPatentDataModel extends PageParams {

    private String patentNo;

    private String inventor;

    private Date applyDateTime;


    private String caseStatus;

    private Date publicDate;

    private String patentName;

    private Integer mainType;

    private String applyName;


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

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public String getPatentNo() {
        return patentNo;
    }

    public void setPatentNo(String patentNo) {
        this.patentNo = patentNo;
    }

    public String getInventor() {
        return inventor;
    }

    public void setInventor(String inventor) {
        this.inventor = inventor;
    }


    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public Date getApplyDateTime() {
        if(applyDateTime!=null){
            return DateUtil.beginOfDay(applyDateTime);
        }
        return null;
    }

    public void setApplyDateTime(Date applyDateTime) {
        this.applyDateTime = applyDateTime;
    }

    public Date getPublicDate() {
        if(publicDate!=null){
            return DateUtil.beginOfDay(publicDate);
        }
        return null;
    }

    public void setPublicDate(Date publicDate) {
        this.publicDate = publicDate;
    }
}
