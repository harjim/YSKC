package com.yskc.rs.models.voucher;

import com.yskc.common.utils.CommonUtils;
import com.yskc.common.utils.DateUtil;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 凭证
 * @author huronghua
 */
public class VoucherInfo {
    private Integer id;
    private Date month;

    private BigDecimal rdFunds;

    private Integer rdType;

    private String accountNumber;

    private String accountName;

    private Integer projectId;
    private String remark;
    private String workNo;
    private String yearStr;
    private String monthStr;
    private String dayStr;
    private Integer rdIndex;
    /**
     * 借方金额
     */
    private BigDecimal debitAmount;
    /**
     * 贷方金额
     */
    private BigDecimal creditAmount;
    private Boolean isSum;
    private String rdIndexStr;
    private Integer accountId;
    private String baseWorkNo;

    public String getBaseWorkNo() {
        return baseWorkNo;
    }

    public void setBaseWorkNo(String baseWorkNo) {
        this.baseWorkNo = baseWorkNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getRdIndexStr() {
        if (isSum != null && !isSum) {
            return "RD" + CommonUtils.patchLeftPosition(String.valueOf(rdIndex), 2, "0");
        } else {
            return null;
        }
    }

    public void setRdIndexStr(String rdIndexStr) {
        this.rdIndexStr = rdIndexStr;
    }

    public Boolean getSum() {
        return isSum;
    }

    public void setSum(Boolean sum) {
        isSum = sum;
    }

    public BigDecimal getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(BigDecimal debitAmount) {
        this.debitAmount = debitAmount;
    }

    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }

    public Integer getRdIndex() {
        return rdIndex;
    }

    public void setRdIndex(Integer rdIndex) {
        this.rdIndex = rdIndex;
    }

    public String getYearStr() {
        if (month != null && !StringUtils.isEmpty(workNo)) {
            return DateUtil.getDateTime(month, "yyyy");
        }
        return yearStr;
    }

    public void setYearStr(String yearStr) {
        this.yearStr = yearStr;
    }

    public String getMonthStr() {
        if (month != null && !StringUtils.isEmpty(workNo)) {
            return DateUtil.getDateTime(month, "MM");
        }
        return monthStr;
    }

    public void setMonthStr(String monthStr) {
        this.monthStr = monthStr;
    }

    public String getDayStr() {
        if (month != null && !StringUtils.isEmpty(workNo)) {
            return DateUtil.getDateTime(month, "dd");
        }
        return dayStr;
    }

    public void setDayStr(String dayStr) {
        this.dayStr = dayStr;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWorkNo() {
        return workNo;
    }

    public void setWorkNo(String workNo) {
        this.workNo = workNo;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public BigDecimal getRdFunds() {
        return rdFunds;
    }

    public void setRdFunds(BigDecimal rdFunds) {
        this.rdFunds = rdFunds;
    }

    public Integer getRdType() {
        return rdType;
    }

    public void setRdType(Integer rdType) {
        this.rdType = rdType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
