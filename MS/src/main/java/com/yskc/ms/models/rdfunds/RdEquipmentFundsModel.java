package com.yskc.ms.models.rdfunds;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @DateTime: 2022/4/28 11:27
 * @Description:设备折旧
 * @author: hsx
 */
public class RdEquipmentFundsModel extends RdFinaFundsModel implements Serializable {


    private BigDecimal prod;  //设备折旧

    private BigDecimal lad;  //仪器折旧

    public BigDecimal getProd() {
        return prod;
    }

    public void setProd(BigDecimal prod) {
        this.prod = prod;
    }

    public BigDecimal getLad() {
        return lad;
    }

    public void setLad(BigDecimal lad) {
        this.lad = lad;
    }

    @Override
    public void addTotal() {
        this.setTotalAmount(this.prod.add(this.lad));
    }
}
