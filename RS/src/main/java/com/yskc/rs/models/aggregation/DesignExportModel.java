package com.yskc.rs.models.aggregation;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by hck
 * on 2020/8/4 9:20
 * description:设计费用
 */
public class DesignExportModel extends AggExportModel implements Serializable {


    private String dname;//设计名称


    private BigDecimal rdAmount;

    private String deptName;

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public BigDecimal getRdAmount() {
        return rdAmount;
    }

    public void setRdAmount(BigDecimal rdAmount) {
        this.rdAmount = rdAmount;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Override
    public void sum(AggExportModel source) {
        rdAmount = rdAmount.add(((DesignExportModel) source).rdAmount);
    }

    @Override
    public AggExportModel createSumObj() {
        DesignExportModel obj = new DesignExportModel();
        obj.setDateStr("合计");
        obj.rdAmount = BigDecimal.ZERO;
        return obj;
    }
}
