package com.yskc.rs.models.aggregation;

import com.yskc.common.utils.DateUtil;
import com.yskc.rs.config.Constant;
import com.yskc.rs.enums.EmployeeTypeEnum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.aggregation
 * @Author: zhangdingfu
 * @CreateTime: 2020-08-05 08:09
 * @Description: 人员研发费用导出model
 */
public class EmployeeExportModel extends AggExportModel {

    private String enumber;

    private String ename;

    private String position;

    private String deptName;

    private Integer etype;

    private BigDecimal pay;

    private BigDecimal insuranceFund;

    private BigDecimal workDays;

    private BigDecimal workHours;

    private String payDetail;

    private String insuranceDetail;

    private BigDecimal rdPay;

    private BigDecimal rdInsuranceFund;

    private BigDecimal rdHour;

    private String typeName;

    private String monthStr;

    private List<Object> pays = new ArrayList<>();

    private List<Object> insurances = new ArrayList<>();

    private List<Object> rdPays = new ArrayList<>();

    private List<Object> rdInsurances = new ArrayList<>();

    public String getEnumber() {
        return enumber;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }

    public BigDecimal getPay() {
        return pay;
    }

    public void setPay(BigDecimal pay) {
        this.pay = pay;
    }

    public BigDecimal getInsuranceFund() {
        return insuranceFund;
    }

    public void setInsuranceFund(BigDecimal insuranceFund) {
        this.insuranceFund = insuranceFund;
    }

    public BigDecimal getWorkDays() {
        return workDays;
    }

    public void setWorkDays(BigDecimal workDays) {
        this.workDays = workDays;
    }

    public BigDecimal getWorkHours() {
        return workHours;
    }

    public void setWorkHours(BigDecimal workHours) {
        this.workHours = workHours;
    }

    public String getPayDetail() {
        return payDetail;
    }

    public void setPayDetail(String payDetail) {
        this.payDetail = payDetail;
    }

    public BigDecimal getRdPay() {
        return rdPay;
    }

    public void setRdPay(BigDecimal rdPay) {
        this.rdPay = rdPay;
    }

    public BigDecimal getRdInsuranceFund() {
        return rdInsuranceFund;
    }

    public void setRdInsuranceFund(BigDecimal rdInsuranceFund) {
        this.rdInsuranceFund = rdInsuranceFund;
    }

    public BigDecimal getRdHour() {
        if (rdHour != null) {
            return rdHour.setScale(1, BigDecimal.ROUND_DOWN);
        }
        return null;
    }

    public void setRdHour(BigDecimal rdHour) {
        this.rdHour = rdHour;
    }

    public BigDecimal getRdRatio() {
        if (rdHour == null || workHours.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return rdHour.divide(workHours, Constant.MAX_SCALE, BigDecimal.ROUND_HALF_UP);
    }

    public String getTypeName() {
        if (etype == null) {
            return null;
        }
        return EmployeeTypeEnum.getEmployeeTypeEnum(etype).getType();
    }

    public String getMonthStr() {
        if (monthStr != null) {
            return monthStr;
        }
        return DateUtil.format(this.getDate(), DateUtil.DEFAULT_YYMM_FORMAT);
    }

    public void setMonthStr(String monthStr) {
        monthStr = monthStr;
    }

    public String getInsuranceDetail() {
        return insuranceDetail;
    }

    public void setInsuranceDetail(String insuranceDetail) {
        this.insuranceDetail = insuranceDetail;
    }

    public List<Object> getPays() {
        return pays;
    }

    public List<Object> getInsurances() {
        return insurances;
    }

    public List<Object> getRdPays() {
        return rdPays;
    }

    public List<Object> getRdInsurances() {
        return rdInsurances;
    }

    @Override
    public void sum(AggExportModel source) {
        EmployeeExportModel beanSource = (EmployeeExportModel) source;
        rdPay = rdPay.add(beanSource.rdPay);
        rdInsuranceFund = rdInsuranceFund.add(beanSource.rdInsuranceFund);
    }

    @Override
    public AggExportModel createSumObj() {
        EmployeeExportModel obj = new EmployeeExportModel();
        obj.monthStr = "合计";
        obj.rdPay = BigDecimal.ZERO;
        obj.rdInsuranceFund = BigDecimal.ZERO;
        for (int i = 0; i < pays.size(); i++) {
            obj.pays.add(null);
        }
        for (int i = 0; i < insurances.size(); i++) {
            obj.insurances.add(null);
        }
        for (int i = 0; i < rdPays.size(); i++) {
            obj.rdPays.add(null);
        }
        for (int i = 0; i < rdInsurances.size(); i++) {
            obj.rdInsurances.add(null);
        }
        return obj;
    }
}
