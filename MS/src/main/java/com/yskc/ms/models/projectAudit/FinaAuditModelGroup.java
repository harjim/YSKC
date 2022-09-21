package com.yskc.ms.models.projectAudit;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: ms
 * @description: 财务审批按月分
 * @author: cyj
 * @create: 2022-04-22 16:52
 **/
public class FinaAuditModelGroup implements Serializable {
    private Integer id;
    private Integer customerId;
    private Integer companyId;
    private Integer year;
    private String deptName;
    private String companyName;
    private String techRealName;
    private String financeRealName;
    //业务员
    private String owerName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date operationTime;
    private Integer ongoingCnt;
    private Integer doneCnt;
    private Integer auditCnt = 0;

    private BigDecimal rdFunds;
    private Map<Integer,FinaAuditMonthModel> monthFundMap;

    public void loadFundAndAudit(List<FinaAuditMonthModel> fundList){
        monthFundMap = new HashMap<>();
        if(CollectionUtils.isEmpty(fundList)){
            return;
        }
        for(FinaAuditMonthModel item: fundList){
            monthFundMap.put(DateUtil.month(item.getMonth()),item);
            auditCnt += item.getAuditCnt();
        }
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public Map<Integer, FinaAuditMonthModel> getMonthFundMap() {
        return monthFundMap;
    }

    public void setMonthFundMap(Map<Integer, FinaAuditMonthModel> monthFundMap) {
        this.monthFundMap = monthFundMap;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTechRealName() {
        return techRealName;
    }

    public void setTechRealName(String techRealName) {
        this.techRealName = techRealName;
    }

    public String getFinanceRealName() {
        return financeRealName;
    }

    public void setFinanceRealName(String financeRealName) {
        this.financeRealName = financeRealName;
    }

    public String getOwerName() {
        return owerName;
    }

    public void setOwerName(String owerName) {
        this.owerName = owerName;
    }

    public BigDecimal getRdFunds() {
        return rdFunds;
    }

    public void setRdFunds(BigDecimal rdFunds) {
        this.rdFunds = rdFunds;
    }

    public Integer getOngoingCnt() {
        return ongoingCnt;
    }

    public void setOngoingCnt(Integer ongoingCnt) {
        this.ongoingCnt = ongoingCnt;
    }

    public Integer getDoneCnt() {
        return doneCnt;
    }

    public void setDoneCnt(Integer doneCnt) {
        this.doneCnt = doneCnt;
    }

    public Integer getAuditCnt() {
        return auditCnt;
    }

    public void setAuditCnt(Integer auditCnt) {
        this.auditCnt = auditCnt;
    }
}
