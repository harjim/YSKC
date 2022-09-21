package com.yskc.ms.models.newexpert.achievement;

import java.io.Serializable;
import java.util.Date;

/**
 * @DateTime: 2021/11/16 8:28
 * @Description:
 * @author: hsx
 */
public class AchievementLogModel implements Serializable {

    private Integer id;

    private Integer achievementId;

    private Integer status;

    private Integer msCreatorId;

    private Date createTime;

    private String suggestion;

    private String msCreatorName;

    private String achievementName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(Integer achievementId) {
        this.achievementId = achievementId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getMsCreatorId() {
        return msCreatorId;
    }

    public void setMsCreatorId(Integer msCreatorId) {
        this.msCreatorId = msCreatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMsCreatorName() {
        return msCreatorName;
    }

    public void setMsCreatorName(String msCreatorName) {
        this.msCreatorName = msCreatorName;
    }

    public String getAchievementName() {
        return achievementName;
    }

    public void setAchievementName(String achievementName) {
        this.achievementName = achievementName;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }
}
