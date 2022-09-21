package com.yskc.rs.models.projectattendance;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectattendance
 * @Author: zhangdingfu
 * @CreateTime: 2020-07-07 14:43
 * @Description: 所有项目研发工时
 */
public class ProjectAllRdHourModel {

    private Integer projectId;

    private String pname;

    private String rdTitle;

    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date month;

    private String enumber;

    private String ename;

    private Map<Integer, RdHourContentModel> info;

    private BigDecimal rdHour;
    private BigDecimal planTime;

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public String getEnumber() {
        return enumber;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Map<Integer, RdHourContentModel> getInfo() {
        return info;
    }

    public void setInfo(Map<Integer, RdHourContentModel> info) {
        this.info = info;
    }

    public BigDecimal getRdHour() {
        return rdHour;
    }

    public void setRdHour(BigDecimal rdHour) {
        this.rdHour = rdHour;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public BigDecimal getPlanTime() {
        return planTime;
    }

    public void setPlanTime(BigDecimal planTime) {
        this.planTime = planTime;
    }
}
