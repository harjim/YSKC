package com.yskc.ms.models.newexpert.expert;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @DateTime: 2021/10/19 15:13
 * @Description:
 * @author: hsx
 */
public class UserExpertLogModel implements Serializable {

    private Integer id;

    private List<Integer> userExpertIds;

    private Integer status;

    private String suggestion;   //审核意见

    private Date createTime;

    private Integer msCreatorId;

    private String msCreatorName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Integer> getUserExpertIds() {
        return userExpertIds;
    }

    public void setUserExpertIds(List<Integer> userExpertIds) {
        this.userExpertIds = userExpertIds;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getMsCreatorId() {
        return msCreatorId;
    }

    public void setMsCreatorId(Integer msCreatorId) {
        this.msCreatorId = msCreatorId;
    }

    public String getMsCreatorName() {
        return msCreatorName;
    }

    public void setMsCreatorName(String msCreatorName) {
        this.msCreatorName = msCreatorName;
    }
}
