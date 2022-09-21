package com.yskc.rs.models.workSheet;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.workSheet
 * @Author: zhangdingfu
 * @CreateTime: 2019-10-30 08:40
 * @Description: 工单model
 */
public class WorkSheetModel implements Serializable {

    private String num;

    private String costElement;

    private String sheetNum;

    private String period;

    private String workEvent;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal cost;

    private String coObj;

    private String rdNum;
    public WorkSheetModel(String num, String costElement, String sheetNum, String period, String workEvent, BigDecimal cost, String coObj, String rdNum) {
        this.num = num;
        this.costElement = costElement;
        this.sheetNum = sheetNum;
        this.period = period;
        this.workEvent = workEvent;
        this.cost = cost;
        this.coObj = coObj;
        this.rdNum = rdNum;
    }

    public WorkSheetModel() {
    }
    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getCostElement() {
        return costElement;
    }

    public void setCostElement(String costElement) {
        this.costElement = costElement;
    }

    public String getSheetNum() {
        return sheetNum;
    }

    public void setSheetNum(String sheetNum) {
        this.sheetNum = sheetNum;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getWorkEvent() {
        return workEvent;
    }

    public void setWorkEvent(String workEvent) {
        this.workEvent = workEvent;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getCoObj() {
        return coObj;
    }

    public void setCoObj(String coObj) {
        this.coObj = coObj;
    }

    public String getRdNum() {
        return rdNum;
    }

    public void setRdNum(String rdNum) {
        this.rdNum = rdNum;
    }


}
