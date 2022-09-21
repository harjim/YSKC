package com.yskc.rs.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @DateTime: 2021/10/12 16:50
 * @Description:
 * @author: hsx
 */
public class SenseModel  implements Serializable {

    private Integer id;

    private Integer type;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date preachDate;

    private List<String> rds;

    private List<String> filePaths;

    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastUpdateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getPreachDate() {
        return preachDate;
    }

    public void setPreachDate(Date preachDate) {
        this.preachDate = preachDate;
    }

    public List<String> getRds() {
        return rds;
    }

    public void setRds(List<String> rds) {
        this.rds = rds;
    }

    public List<String> getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(List<String> filePaths) {
        this.filePaths = filePaths;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
