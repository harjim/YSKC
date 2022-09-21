package com.yskc.rs.models.project;

import com.yskc.rs.config.Constant;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.project
 * @Author: zhangdingfu
 * @CreateTime: 2020-04-02 16:34
 * @Description: 研发薪资明细
 */
public class RdSalaryDetailModel implements Serializable {
    private Integer projectId;
    private String rdTitle;
    private String accountName;
    private Integer accountTitleId;
    private String payDetail;
    private BigDecimal rdInsuranceFund;
    private BigDecimal rdPay;
    private Map<String, BigDecimal> salaryMap;
    private Map<String, BigDecimal> insuranceMap;
    private Integer num;
    private BigDecimal pay;
    private BigDecimal total;
    private String workshop;
    private String insuranceDetail;
    private BigDecimal insuranceFund;
    private BigDecimal rdHour;
    private BigDecimal workHours;
    private String pname;
    private String rdNumber;

    public static RdSalaryDetailModel copyValue(RdSalaryDetailModel rdSalaryDetailModel) {
        RdSalaryDetailModel result = new RdSalaryDetailModel();
        result.projectId = rdSalaryDetailModel.projectId;
        result.rdTitle = rdSalaryDetailModel.rdTitle;
        result.workshop = rdSalaryDetailModel.workshop;
        result.pname = rdSalaryDetailModel.pname;
        result.rdNumber = rdSalaryDetailModel.rdNumber;
        return result;
    }

    public static void compareToDetail(RdSalaryDetailModel m) {
        if (null == m.getRdPay() || null == m.getRdInsuranceFund()) {
            return;
        }
        BigDecimal totalPay = BigDecimal.ZERO;
        if (CollectionUtils.isEmpty(m.salaryMap)) {
            totalPay = m.pay;
        } else {
            for (BigDecimal p : m.salaryMap.values()) {
                totalPay = totalPay.add(p);
            }
        }
        loadDetailMap(m.pay, totalPay, m.getRdPay(), m.salaryMap);
        BigDecimal totalInsurance = BigDecimal.ZERO;
        if (CollectionUtils.isEmpty(m.insuranceMap)) {
            totalInsurance = m.insuranceFund;
        } else {
            for (BigDecimal p : m.insuranceMap.values()) {
                totalInsurance = totalInsurance.add(p);
            }
        }
        loadDetailMap(m.insuranceFund, totalInsurance, m.getRdInsuranceFund(), m.insuranceMap);
    }

    public static BigDecimal loadDetailMap(BigDecimal source, BigDecimal total, BigDecimal rd, Map<String, BigDecimal> detailMap) {
        if (total.compareTo(rd) != 0) {
            BigDecimal subtract = rd.subtract(total);
            if (CollectionUtils.isEmpty(detailMap)) {
                return subtract.add(source);
            } else {
                BigDecimal sub;
                List<String> keys = new ArrayList(detailMap.keySet());
                for (int i = 0; i < keys.size(); i++) {
                    String key = keys.get(i);
                    if (detailMap.get(key).compareTo(BigDecimal.ZERO) != 0) {
                        sub = ratioSub(detailMap.get(key), subtract);
                        detailMap.put(key, detailMap.get(key).add(subtract.subtract(sub)));
                        if (sub.compareTo(BigDecimal.ZERO) == 0) {
                            break;
                        }
                    }
                }
            }
        }
        return BigDecimal.ZERO;
    }

    private static BigDecimal ratioSub(BigDecimal v, BigDecimal sub) {
        if (v.abs().compareTo(sub.abs()) < 0) {
            sub = sub.subtract(v);
        } else {
            sub = BigDecimal.ZERO;
        }
        return sub;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public Integer getAccountTitleId() {
        return accountTitleId;
    }

    public void setAccountTitleId(Integer accountTitleId) {
        this.accountTitleId = accountTitleId;
    }

    public String getPayDetail() {
        return payDetail;
    }

    public void setPayDetail(String payDetail) {
        this.payDetail = payDetail;
    }

    public BigDecimal getRdInsuranceFund() {
        return rdInsuranceFund;
    }

    public void setRdInsuranceFund(BigDecimal rdInsuranceFund) {
        this.rdInsuranceFund = rdInsuranceFund;
    }

    public BigDecimal getRdPay() {
        return rdPay;
    }

    public void setRdPay(BigDecimal rdPay) {
        this.rdPay = rdPay;
    }

    public BigDecimal getRdRatio() {
        if (workHours != null && rdHour != null && workHours.compareTo(BigDecimal.ZERO) != 0) {
            return rdHour.divide(workHours, Constant.MAX_SCALE, BigDecimal.ROUND_HALF_UP);
        }
        return null;
    }

    public String getWorkshop() {
        return workshop;
    }

    public void setWorkshop(String workshop) {
        this.workshop = workshop;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getPay() {
        return pay;
    }

    public void setPay(BigDecimal pay) {
        if (null != pay) {
            this.pay = pay.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getInsuranceDetail() {
        return insuranceDetail;
    }

    public void setInsuranceDetail(String insuranceDetail) {
        this.insuranceDetail = insuranceDetail;
    }

    public BigDecimal getInsuranceFund() {
        return insuranceFund;
    }

    public void setInsuranceFund(BigDecimal insuranceFund) {
        if (null != insuranceFund) {
            this.insuranceFund = insuranceFund.setScale(2,BigDecimal.ROUND_HALF_UP);
        }
    }

    public Map<String, BigDecimal> getSalaryMap() {
        return salaryMap;
    }

    public void setSalaryMap(Map<String, BigDecimal> salaryMap) {
        this.salaryMap = salaryMap;
    }

    public Map<String, BigDecimal> getInsuranceMap() {
        return insuranceMap;
    }

    public void setInsuranceMap(Map<String, BigDecimal> insuranceMap) {
        this.insuranceMap = insuranceMap;
    }

    public BigDecimal getRdHour() {
        return rdHour;
    }

    public void setRdHour(BigDecimal rdHour) {
        this.rdHour = rdHour;
    }

    public BigDecimal getWorkHours() {
        return workHours;
    }

    public void setWorkHours(BigDecimal workHours) {
        this.workHours = workHours;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getRdNumber() {
        return rdNumber;
    }

    public void setRdNumber(String rdNumber) {
        this.rdNumber = rdNumber;
    }
}
