package com.yskc.rs.models.projectequipment;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectequipment
 * @Author: zhangdingfu
 * @CreateTime: 2019-10-30 09:35
 * @Description: 设备工单model
 */
public class WorkSheetEquipmentModel implements Serializable {

    private BigDecimal rdDepreciation;
    private Integer rdHour;
    private String equData;
    private String ename;
    private Integer rdIndex;
    private String deptName;
    private String effect;

    public BigDecimal getRdDepreciation() {
        return rdDepreciation;
    }

    public void setRdDepreciation(BigDecimal rdDepreciation) {
        this.rdDepreciation = rdDepreciation;
    }

    public Integer getRdHour() {
        return rdHour;
    }

    public void setRdHour(Integer rdHour) {
        this.rdHour = rdHour;
    }

    public String getEquData() {
        return equData;
    }

    public void setEquData(String equData) {
        this.equData = equData;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public Integer getRdIndex() {
        return rdIndex;
    }

    public void setRdIndex(Integer rdIndex) {
        this.rdIndex = rdIndex;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }
}
