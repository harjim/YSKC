package com.yskc.docservice.entity.rs;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.common.utils.DateUtil;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.salary.SalaryModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-11 09:04:31
 */
@TableName("d_salary")
public class SalaryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer id;
    /**
     *
     */
    private String enumber;
    /**
     *
     */
    private String ename;
    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date month;
    /**
     *
     */
    private BigDecimal workDays;
    /**
     *
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal pay;
    /**
     *
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal insuranceFund;
    /**
     *
     */
    private Integer companyId;
    /**
     *
     */
    private Integer creatorId;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private String remark;
//    /**
//     * 养老
//     */
//    private BigDecimal endowment;
//    /**
//     * 医疗
//     */
//    private BigDecimal medical;
//    /**
//     * 失业
//     */
//    private BigDecimal unemployment;
//    /**
//     * 工伤
//     */
//    private BigDecimal injury;
//    /**
//     * 生育
//     */
//    private BigDecimal maternity;
//    /**
//     * 公积金
//     */
//    private BigDecimal house;
//    /**
//     * 养老,公司上交部分
//     */
//    private BigDecimal endowmentOfCom;
//    /**
//     * 医疗,公司上交部分
//     */
//    private BigDecimal medicalOfCom;
//    /**
//     * 失业,公司上交部分
//     */
//    private BigDecimal unemploymentOfCom;
//    /**
//     * 生育,公司上交部分
//     */
//    private BigDecimal maternityOfCom;
//    /**
//     * 工伤,公司上交部分
//     */
//    private BigDecimal injuryOfCom;
//    /**
//     * 公积金,公司上交部分
//     */
//    private BigDecimal houseOfCom;

    private Integer dayHours;

    private Integer accountTitleId;
    private Date lastUpdateTime;
    private Integer lastUpdatorId;
    private Integer msCreatorId;
    private Integer msLastUpdatorId;
    private BigDecimal workHours;
    private String payDetail;
    private String deptName;
    private String insuranceDetail;

    public static SalaryEntity build(Date now, RsUserInfo info, SalaryModel syModel) {
        SalaryEntity salary = new SalaryEntity();
        if (null != syModel.getId() && syModel.getId() > 0) {
            salary.id = syModel.getId();
        }
        salary.companyId = info.getCompanyId();
        salary.accountTitleId = syModel.getAccountTitleId();
        salary.enumber = syModel.getEnumber();
        salary.ename = syModel.getEname();
        salary.month = DateUtil.getMonthFirstDay(syModel.getMonth());
        salary.pay = syModel.getPay();
        salary.remark = syModel.getRemark();
        salary.workDays = syModel.getWorkDays();
        salary.dayHours = 8;
        salary.workHours = syModel.getWorkHours();
        salary.msLastUpdatorId = info.getMsUserId();
        salary.lastUpdatorId = info.getUserId();
        salary.lastUpdateTime = now;
        salary.payDetail = syModel.getPayDetail();
        salary.deptName = syModel.getDeptName();
        salary.insuranceDetail = syModel.getInsuranceDetail();
        salary.insuranceFund = syModel.getInsuranceFund();
        return salary;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
    }

    public Integer getMsCreatorId() {
        return msCreatorId;
    }

    public void setMsCreatorId(Integer msCreatorId) {
        this.msCreatorId = msCreatorId;
    }

    public Integer getMsLastUpdatorId() {
        return msLastUpdatorId;
    }

    public void setMsLastUpdatorId(Integer msLastUpdatorId) {
        this.msLastUpdatorId = msLastUpdatorId;
    }

    public Integer getAccountTitleId() {
        return accountTitleId;
    }

    public void setAccountTitleId(Integer accountTitleId) {
        this.accountTitleId = accountTitleId;
    }

    public Integer getDayHours() {
        return dayHours;
    }

    public void setDayHours(Integer dayHours) {
        this.dayHours = dayHours;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }

    public String getEnumber() {
        return enumber;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getEname() {
        return ename;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Date getMonth() {
        return month;
    }

    public void setWorkDays(BigDecimal workDays) {
        this.workDays = workDays;
    }

    public BigDecimal getWorkDays() {
        return workDays;
    }

    public void setPay(BigDecimal pay) {
        this.pay = pay;
    }

    public BigDecimal getPay() {
        return pay;
    }

    public void setInsuranceFund(BigDecimal insuranceFund) {
        this.insuranceFund = insuranceFund;
    }

    public BigDecimal getInsuranceFund() {
        return insuranceFund;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
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

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getInsuranceDetail() {
        return insuranceDetail;
    }

    public void setInsuranceDetail(String insuranceDetail) {
        this.insuranceDetail = insuranceDetail;
    }
}
