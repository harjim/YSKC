package com.yskc.ms.models.rs.achievement;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.rs.achievement
 * @Author: zhangdingfu
 * @CreateTime: 2021-11-19 16:49
 * @Description: 成果model
 */
public class AchievementModel {
    private Integer id;
    private String achievementName;
    private Integer year;
    private String rdTitle;
    private String pname;
    /**
     * 最后上传时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastUploadTime;
    private Integer fileCnt;
    private Integer status;
    private Boolean hasPermission;
    private String description;
    private Integer type;
    private Integer converType;
    private Integer source;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAchievementName() {
        return achievementName;
    }

    public void setAchievementName(String achievementName) {
        this.achievementName = achievementName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Date getLastUploadTime() {
        return lastUploadTime;
    }

    public void setLastUploadTime(Date lastUploadTime) {
        this.lastUploadTime = lastUploadTime;
    }

    public Integer getFileCnt() {
        return fileCnt;
    }

    public void setFileCnt(Integer fileCnt) {
        this.fileCnt = fileCnt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getHasPermission() {
        return hasPermission;
    }

    public void setHasPermission(Boolean hasPermission) {
        this.hasPermission = hasPermission;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getConverType() {
        return converType;
    }

    public void setConverType(Integer converType) {
        this.converType = converType;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }
}
