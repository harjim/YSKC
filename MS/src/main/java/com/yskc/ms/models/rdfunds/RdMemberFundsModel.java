package com.yskc.ms.models.rdfunds;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @DateTime: 2022/4/28 11:25
 * @Description:人员费用
 * @author: hsx
 */
public class RdMemberFundsModel extends RdFinaFundsModel implements Serializable {

    private BigDecimal salary;  //工资

    private BigDecimal insurance;  //五险一金

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public BigDecimal getInsurance() {
        return insurance;
    }

    public void setInsurance(BigDecimal insurance) {
        this.insurance = insurance;
    }

    @Override
    public void addTotal() {
        this.setTotalAmount(this.salary.add(this.insurance));
    }
}
