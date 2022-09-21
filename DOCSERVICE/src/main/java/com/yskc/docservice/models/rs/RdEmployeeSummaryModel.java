package com.yskc.docservice.models.rs;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by hck
 * on 2020/5/28 10:55
 * description:
 */
public class RdEmployeeSummaryModel implements Serializable {
    /**
     * 序号
     */
    private Integer num;
    /**
     * 姓名
     */
    private String ename;
    /**
     * 研发部门名称
     */
    private String rdDeptName;
    /**
     * 项目角色
     */
    private String role;
    /**
     * 月工作时常
     */
    private BigDecimal monthWorkOurs;
    /**
     * 年工作时长
     */
    private BigDecimal yearWorkOurs;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getRdDeptName() {
        return rdDeptName;
    }

    public void setRdDeptName(String rdDeptName) {
        this.rdDeptName = rdDeptName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public BigDecimal getMonthWorkOurs() {
        return monthWorkOurs;
    }

    public void setMonthWorkOurs(BigDecimal monthWorkOurs) {
        this.monthWorkOurs = monthWorkOurs;
    }

    public BigDecimal getYearWorkOurs() {
        return yearWorkOurs;
    }

    public void setYearWorkOurs(BigDecimal yearWorkOurs) {
        this.yearWorkOurs = yearWorkOurs;
    }
}
