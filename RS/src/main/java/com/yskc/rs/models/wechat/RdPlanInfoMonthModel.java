package com.yskc.rs.models.wechat;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.wechat
 * @Author: zhangdingfu
 * @CreateTime: 2021-02-05 15:04
 * @Description: 研发项目月计划工时
 */
public class RdPlanInfoMonthModel {
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date month;
    private List<RdPlanHourInfoModel> rdInfos;
    private BigDecimal rangeValue;
    private Integer rdIndex;

    public RdPlanInfoMonthModel(Date month, List<RdPlanHourInfoModel> rdInfos,BigDecimal rangeValue,Integer rdIndex) {
        this.month = month;
        this.rdInfos = rdInfos;
        this.rangeValue = rangeValue;
        this.rdIndex = rdIndex;
    }


    public RdPlanInfoMonthModel() {
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public List<RdPlanHourInfoModel> getRdInfos() {
        return rdInfos;
    }

    public void setRdInfos(List<RdPlanHourInfoModel> rdInfos) {
        this.rdInfos = rdInfos;
    }

    public BigDecimal getRangeValue() {
        return rangeValue;
    }

    public void setRangeValue(BigDecimal rangeValue) {
        this.rangeValue = rangeValue;
    }

    public Integer getRdIndex() {
        return rdIndex;
    }

    public void setRdIndex(Integer rdIndex) {
        this.rdIndex = rdIndex;
    }
}
