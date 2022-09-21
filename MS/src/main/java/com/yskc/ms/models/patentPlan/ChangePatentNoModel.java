package com.yskc.ms.models.patentPlan;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hck
 * on 2020/7/9 15:19
 * description:专利立项修改专利号model
 */
public class ChangePatentNoModel implements Serializable {

    private Integer id;//专利立项id


    private String newPatentNo;//新专利号

    private String patentName;

    private String mainType;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date applyDateTime;

    private String inventor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNewPatentNo() {
        return newPatentNo;
    }

    public void setNewPatentNo(String newPatentNo) {
        this.newPatentNo = newPatentNo;
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
}
