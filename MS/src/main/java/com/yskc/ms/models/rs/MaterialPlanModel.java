package com.yskc.ms.models.rs;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by hck
 * on 2020/10/20 15:51
 * description:用料计划表数据
 */
public class MaterialPlanModel implements Serializable {

    private  Integer id;
    private String mcode;//材料编码
    private String mname;//材料名称
    private BigDecimal quantity;//研发数量
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date acqDate;//日期
    private String billNo;//出库单号
    private String remark;//备注
    private String acqDateStr;
    private String unit;

    public String getAcqDateStr() {
        return acqDateStr;
    }

    public void setAcqDateStr(String acqDateStr) {
        this.acqDateStr = acqDateStr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMcode() {
        return mcode;
    }

    public void setMcode(String mcode) {
        this.mcode = mcode;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Date getAcqDate() {
        return acqDate;
    }

    public void setAcqDate(Date acqDate) {
        this.acqDate = acqDate;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
