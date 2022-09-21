package com.yskc.rs.models.Patent;

import com.yskc.rs.models.params.PageParams;

/**
 * Created by hck
 * on 2020/6/29 15:09
 * description:
 */
public class QueryPatentDetialModel extends PageParams {

    private String patentNo;//专利号

    private String patentName;//专利名称

    private String mainType;//类型

    private String applyName;

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
}
