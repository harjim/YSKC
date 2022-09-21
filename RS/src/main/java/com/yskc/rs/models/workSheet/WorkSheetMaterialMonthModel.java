package com.yskc.rs.models.workSheet;

import java.math.BigDecimal;

public class WorkSheetMaterialMonthModel extends WorkSheetMiniModel {
     private BigDecimal rdAmount;

    public BigDecimal getRdAmount() {
        return rdAmount;
    }

    public void setRdAmount(BigDecimal rdAmount) {
        this.rdAmount = rdAmount;
    }
}
