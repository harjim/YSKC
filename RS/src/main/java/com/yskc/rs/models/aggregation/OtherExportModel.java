package com.yskc.rs.models.aggregation;

import com.yskc.rs.enums.CostEnum;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by hck
 * on 2020/8/4 11:43
 * description:其他费用导出model
 */
public class OtherExportModel extends AggExportModel implements Serializable {

    private String accNumber;

    private String summary;//摘要

    private BigDecimal rdAmount;

    private Integer type;//费用类型

    private String deptName;

    private String typeName;

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public BigDecimal getRdAmount() {
        return rdAmount;
    }

    public void setRdAmount(BigDecimal rdAmount) {
        this.rdAmount = rdAmount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getTypeName() {
        if (type == null) {
            return null;
        }
        return CostEnum.getCostEnum(type).getTitle();
    }

    @Override
    public void sum(AggExportModel source) {
        rdAmount = rdAmount.add(((OtherExportModel) source).rdAmount);
    }

    @Override
    public AggExportModel createSumObj() {
        OtherExportModel obj = new OtherExportModel();
        obj.setDateStr("合计");
        obj.rdAmount = BigDecimal.ZERO;
        return obj;
    }
}
