package com.yskc.rs.models.projectequipment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectrdequipment
 * @Author: zhangdingfu
 * @CreateTime: 2020-04-16 10:37
 * @Description: 导出设备研发记录类
 */
public class ExportProjectEquipmentModel implements Serializable {

    private String ecode;

    private String ename;

    private String deptName;

    private BigDecimal rdHour;

    private String equData;

    private String rdTitle;
    private List<BigDecimal> hours;

    public String getEcode() {
        return ecode;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public BigDecimal getRdHour() {
        return rdHour;
    }

    public void setRdHour(BigDecimal rdHour) {
        this.rdHour = rdHour;
    }

    public String getEquData() {
        return equData;
    }

    public void setEquData(String equData) {
        this.equData = equData;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public List<BigDecimal> getHours() {
        return hours;
    }

    public void setHours(List<BigDecimal> hours) {
        this.hours = hours;
    }
}
