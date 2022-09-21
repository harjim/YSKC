package com.yskc.rs.models.outsourcing;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.outsourcing
 * @Author: zhangdingfu
 * @CreateTime: 2021-06-08 15:35
 * @Description: 基础项目委外费用
 */
public class BaseOutsourcingModel {

    private Integer id;

    private Integer projectId;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date month;

    private BigDecimal rdFunds;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public BigDecimal getRdFunds() {
        return rdFunds;
    }

    public void setRdFunds(BigDecimal rdFunds) {
        this.rdFunds = rdFunds;
    }
}
