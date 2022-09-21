package com.yskc.ms.models.newexpert.cooperation;

import com.yskc.ms.models.params.PageParams;

import java.io.Serializable;

/**
 * @DateTime: 2021/11/12 14:13
 * @Description:
 * @author: hsx
 */
public class QueryCooperationModel extends PageParams implements Serializable {

    //发送意向人姓名
    private String fullname;

    //意向对象(专家)姓名
    private String intentionUserName;

    //专家，成果，技术ID
    private Integer intentionId;

    //单位名称
    private String unitName;

    //状态
    private Integer status;

    //联系电话
    private String linkInfo;

    //0专家，1成果，2技术
    private Integer type;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getIntentionUserName() {
        return intentionUserName;
    }

    public void setIntentionUserName(String intentionUserName) {
        this.intentionUserName = intentionUserName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getLinkInfo() {
        return linkInfo;
    }

    public void setLinkInfo(String linkInfo) {
        this.linkInfo = linkInfo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIntentionId() {
        return intentionId;
    }

    public void setIntentionId(Integer intentionId) {
        this.intentionId = intentionId;
    }
}
