package com.yskc.rs.models.workSheet;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import com.yskc.common.utils.CommonUtils;
import com.yskc.common.utils.DateUtil;

import org.springframework.util.StringUtils;

/**
 * 研发人员工单
 * @author huronghua
 */
public class StaffWorkSheetItem {
    private Integer id;
    private Integer rdIndex;
    private String costElements;
    private String workNo;
    private Date workDate;
    private String workEvent;
    private BigDecimal expenseAmount;
    private String rdDeptName;
    private String attData;
    private BigDecimal rdEndowmentOfCom;
    private BigDecimal rdMedicalOfCom;
    private BigDecimal rdUnemploymentOfCom;
    private BigDecimal rdInjuryOfCom;
    private BigDecimal rdMaternityOfCom;
    private BigDecimal rdHouseOfCom;
    private BigDecimal rdPay;
    private BigDecimal rdBonus;
    private Integer dayHours;
    private BigDecimal workDays;
    private BigDecimal rdDays;
    private String workDateStr;
    private String rdIndexStr;
    private Boolean isSum;

    public Boolean getIsSum() {
        return this.isSum;
    }

    public void setIsSum(Boolean isSum) {
        this.isSum = isSum;
    }

    public String getRdIndexStr() {
        if (!isSum) {
            return "RD" + CommonUtils.patchLeftPosition(String.valueOf(rdIndex), 2, "0");
        } else {
            return null;
        }
    }

    public void setRdIndexStr(String rdIndexStr) {
        this.rdIndexStr = rdIndexStr;
    }

    public String getWorkDateStr() {
        return DateUtil.format(workDate, "yyyy-MM-dd");
    }

    public void setWorkDateStr(String workDateStr) {
        this.workDateStr = workDateStr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRdIndex() {
        return rdIndex;
    }

    public void setRdIndex(Integer rdIndex) {
        this.rdIndex = rdIndex;
    }

    public String getCostElements() {
        if (StringUtils.isEmpty(costElements)) {
            return null;
        }
        if(isSum){
            return costElements;
        }
        return costElements + " - 人员费用";
    }

    public void setCostElements(String costElements) {
        this.costElements = costElements;
    }

    public String getWorkNo() {
        return workNo;
    }

    public void setWorkNo(String workNo) {
        this.workNo = workNo;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public String getWorkEvent() {
        return workEvent;
    }

    public void setWorkEvent(String workEvent) {
        this.workEvent = workEvent;
    }

    public BigDecimal getExpenseAmount() {
        try {
            if(expenseAmount!=null){
                return expenseAmount;
            }
            BigDecimal allAmount = rdBonus.add(rdEndowmentOfCom).add(rdHouseOfCom).add(rdInjuryOfCom).add(rdMaternityOfCom).add(rdMedicalOfCom).add(rdPay).add(rdUnemploymentOfCom);
            BigDecimal allHours = rdDays.multiply(BigDecimal.valueOf(dayHours));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(workDate);
            int day = calendar.get(5);
            String[] hourList = attData.split(",");
            if (hourList.length == 0) {
                return BigDecimal.ZERO;
            }
            return allAmount.divide(allHours, 20, 4).multiply(new BigDecimal(Long.parseLong(hourList[day-1]))).setScale(2, 5);
        }catch (Exception ex){
            return BigDecimal.ZERO;
        }
    }

    public void setExpenseAmount(BigDecimal expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getRdDeptName() {
        return rdDeptName;
    }

    public void setRdDeptName(String rdDeptName) {
        this.rdDeptName = rdDeptName;
    }

    public String getAttData() {
        return attData;
    }

    public void setAttData(String attData) {
        this.attData = attData;
    }

    public BigDecimal getRdEndowmentOfCom() {
        return rdEndowmentOfCom;
    }

    public void setRdEndowmentOfCom(BigDecimal rdEndowmentOfCom) {
        this.rdEndowmentOfCom = rdEndowmentOfCom;
    }

    public BigDecimal getRdMedicalOfCom() {
        return rdMedicalOfCom;
    }

    public void setRdMedicalOfCom(BigDecimal rdMedicalOfCom) {
        this.rdMedicalOfCom = rdMedicalOfCom;
    }

    public BigDecimal getRdUnemploymentOfCom() {
        return rdUnemploymentOfCom;
    }

    public void setRdUnemploymentOfCom(BigDecimal rdUnemploymentOfCom) {
        this.rdUnemploymentOfCom = rdUnemploymentOfCom;
    }

    public BigDecimal getRdInjuryOfCom() {
        return rdInjuryOfCom;
    }

    public void setRdInjuryOfCom(BigDecimal rdInjuryOfCom) {
        this.rdInjuryOfCom = rdInjuryOfCom;
    }

    public BigDecimal getRdMaternityOfCom() {
        return rdMaternityOfCom;
    }

    public void setRdMaternityOfCom(BigDecimal rdMaternityOfCom) {
        this.rdMaternityOfCom = rdMaternityOfCom;
    }

    public BigDecimal getRdHouseOfCom() {
        return rdHouseOfCom;
    }

    public void setRdHouseOfCom(BigDecimal rdHouseOfCom) {
        this.rdHouseOfCom = rdHouseOfCom;
    }

    public BigDecimal getRdPay() {
        return rdPay;
    }

    public void setRdPay(BigDecimal rdPay) {
        this.rdPay = rdPay;
    }

    public BigDecimal getRdBonus() {
        return rdBonus;
    }

    public void setRdBonus(BigDecimal rdBonus) {
        this.rdBonus = rdBonus;
    }

    public Integer getDayHours() {
        return dayHours;
    }

    public void setDayHours(Integer dayHours) {
        this.dayHours = dayHours;
    }

    public BigDecimal getWorkDays() {
        return workDays;
    }

    public void setWorkDays(BigDecimal workDays) {
        this.workDays = workDays;
    }

    public BigDecimal getRdDays() {
        return rdDays;
    }

    public void setRdDays(BigDecimal rdDays) {
        this.rdDays = rdDays;
    }
}
