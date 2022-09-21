package com.yskc.rs.models.projectrdemployee;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.rs.models.project.RdUsedHourModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectRdEmployee
 * @Author: zhangdingfu
 * @CreateTime: 2020-03-31 11:16
 * @Description: 项目人员费用model
 */
public class ProjectRdEmployeeModel implements Serializable {
    private Integer id;
    private String enumber;
    private String ename;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal pay;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal insuranceFund;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal workHours;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal rdPay;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal rdInsuranceFund;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal rdHour;
    private String deptName;
    private Integer etype;
    private List<RdUsedHourModel> usedList;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal remainHour;
    private String position;
    private String payDetail;
    private String insuranceDetail;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date month;
    private Integer projectId;

    public static ProjectRdEmployeeModel build(String ename, String enumber, Date month, BigDecimal rdHour) {
        ProjectRdEmployeeModel rdEmployeeModel = new ProjectRdEmployeeModel();
        rdEmployeeModel.ename = ename;
        rdEmployeeModel.enumber = enumber;
        rdEmployeeModel.month = month;
        rdEmployeeModel.rdHour = rdHour;
        return rdEmployeeModel;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public BigDecimal getRdHour() {
        return rdHour;
    }

    public BigDecimal getRdHourByBit(int bit){
        if (rdHour != null) {
            return rdHour.setScale(bit, BigDecimal.ROUND_DOWN);
        }
        return null;
    }

    public void setRdHour(BigDecimal rdHour) {
        this.rdHour = rdHour;
    }

    public BigDecimal getInsuranceFund() {
        return insuranceFund;
    }

    public void setInsuranceFund(BigDecimal insuranceFund) {
        this.insuranceFund = insuranceFund;
    }

    public BigDecimal getRdPay() {
        return rdPay;
    }

    public void setRdPay(BigDecimal rdPay) {
        this.rdPay = rdPay;
    }

    public BigDecimal getPay() {
        return pay;
    }

    public void setPay(BigDecimal pay) {
        this.pay = pay;
    }

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }

    public BigDecimal getRdInsuranceFund() {
        return rdInsuranceFund;
    }

    public void setRdInsuranceFund(BigDecimal rdInsuranceFund) {
        this.rdInsuranceFund = rdInsuranceFund;
    }

    public BigDecimal getWorkHours() {
        return workHours;
    }

    public void setWorkHours(BigDecimal workHours) {
        this.workHours = workHours;
    }

    public List<RdUsedHourModel> getUsedList() {
        return usedList;
    }

    public void setUsedList(List<RdUsedHourModel> usedList) {
        this.usedList = usedList;
    }

    public BigDecimal getRemainHour() {
        return remainHour;
    }

    public void setRemainHour(BigDecimal remainHour) {
        this.remainHour = remainHour;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getPayDetail() {
        return payDetail;
    }

    public void setPayDetail(String payDetail) {
        this.payDetail = payDetail;
    }

    public String getInsuranceDetail() {
        return insuranceDetail;
    }

    public void setInsuranceDetail(String insuranceDetail) {
        this.insuranceDetail = insuranceDetail;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}