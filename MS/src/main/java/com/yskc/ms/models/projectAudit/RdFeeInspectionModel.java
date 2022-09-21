package com.yskc.ms.models.projectAudit;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.ms.models.params.PageParams;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: ms
 * @description: 据项目月份返回其他数据
 * @author: cyj
 * @create: 2022-04-27 11:50
 **/
public class RdFeeInspectionModel  extends PageParams implements Serializable {

    /**
     * 摘要
     */
    public String summary;
    /**
     * 记帐日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date accDate;
    private String accNumber;
    /**
     * 0：检测，1：修理
     */
    public Integer type;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public BigDecimal expense;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal rdAmount;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getAccDate() {
        return accDate;
    }

    public void setAccDate(Date accDate) {
        this.accDate = accDate;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getExpense() {
        return expense;
    }

    public void setExpense(BigDecimal expense) {
        this.expense = expense;
    }

    public BigDecimal getRdAmount() {
        return rdAmount;
    }

    public void setRdAmount(BigDecimal rdAmount) {
        this.rdAmount = rdAmount;
    }
}
