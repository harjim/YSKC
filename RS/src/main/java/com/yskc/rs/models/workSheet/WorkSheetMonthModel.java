package com.yskc.rs.models.workSheet;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.workSheet
 * @Author: zhangdingfu
 * @CreateTime: 2019-11-21 15:47
 * @Description: 月工单model
 */
public class WorkSheetMonthModel implements Serializable {

    private String sheetNum;

    private String workDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal expenseAmount;

    private String rdNum;
    public WorkSheetMonthModel() {
    }

    public WorkSheetMonthModel(String sheetNum, String workDate, BigDecimal expenseAmount, String rdNum) {
        this.sheetNum = sheetNum;
        this.workDate = workDate;
        this.expenseAmount = expenseAmount;
        this.rdNum = rdNum;
    }
    public String getSheetNum() {
        return sheetNum;
    }

    public void setSheetNum(String sheetNum) {
        this.sheetNum = sheetNum;
    }

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    public BigDecimal getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(BigDecimal expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getRdNum() {
        return rdNum;
    }

    public void setRdNum(String rdNum) {
        this.rdNum = rdNum;
    }


}
