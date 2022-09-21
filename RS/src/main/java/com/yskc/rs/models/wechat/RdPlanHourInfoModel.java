package com.yskc.rs.models.wechat;

import java.math.BigDecimal;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.wechat
 * @Author: zhangdingfu
 * @CreateTime: 2020-11-17 13:37
 * @Description: 研发计划工时
 */
public class RdPlanHourInfoModel {
    private Integer projectId;
    private String rdTitle;
    private String pname;
    private BigDecimal planTime;
    private BigDecimal rdHour;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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

    public BigDecimal getPlanTime() {
        return planTime;
    }

    public void setPlanTime(BigDecimal planTime) {
        this.planTime = planTime;
    }

    public BigDecimal getRdHour() {
        return rdHour;
    }

    public void setRdHour(BigDecimal rdHour) {
        this.rdHour = rdHour;
    }
}
