package com.yskc.docservice.models.rs.trialprod;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by hck
 * on 2020/7/4 8:52
 * description:
 */
public class ImportDocTrialModel implements Serializable {

    private Integer index;

    private String trialDate;

    private BigDecimal actualPO;

    private String place;

    private String  startTime;

    private String endTime;

    private String unit;
    private String trialProduct;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getTrialDate() {
        return trialDate;
    }

    public void setTrialDate(String trialDate) {
        this.trialDate = trialDate;
    }

    public BigDecimal getActualPO() {
        return actualPO;
    }

    public void setActualPO(BigDecimal actualPO) {
        this.actualPO = actualPO;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTrialProduct() {
        return trialProduct;
    }

    public void setTrialProduct(String trialProduct) {
        this.trialProduct = trialProduct;
    }
}
