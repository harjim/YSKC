package com.yskc.rs.models.projectequipment;

import com.yskc.rs.config.Constant;
import com.yskc.rs.utils.AttDataUtils;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.project
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-17 16:33
 * @Description: 设备研发记录Model
 */
public class ProjectEquipmentModel implements Serializable {

    private Integer id;

    private Integer peId;

    private String ecode;

    private String ename;

    private String equData;

    private String usedEquData;

    private BigDecimal rdHour;

    private BigDecimal remainHours;

    private String deptName;

    private BigDecimal usedWorkHours;

    private BigDecimal workHours;

    public BigDecimal getWorkHours() {
        return workHours;
    }

    public void setWorkHours(BigDecimal workHours) {
        this.workHours = workHours;
    }

    public BigDecimal getUsedWorkHours() {
        return usedWorkHours;
    }

    public void setUsedWorkHours(BigDecimal usedWorkHours) {
        this.usedWorkHours = usedWorkHours;
    }

    public void setEquData(String equData) {
        this.equData = equData;
    }

    public String getEquData() {
        return equData;
    }

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsedEquData() {
        return usedEquData;
    }

    public void setUsedEquData(String usedEquData) {
        this.usedEquData = usedEquData;
    }

    public BigDecimal getRdHour() {
        return rdHour;
    }

    public void setRdHour(BigDecimal rdHour) {
        this.rdHour = rdHour;
    }

    public BigDecimal getRemainHours() {
        return remainHours;
    }

    public void setRemainHours(BigDecimal remainHours) {
        this.remainHours = remainHours;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getPeId() {
        return peId;
    }

    public void setPeId(Integer peId) {
        this.peId = peId;
    }

    public String[] getEquDataArr() {
        if (null == equData) {
            return new String[0];
        }
        return equData.split(",");
    }

    public String[] getRemainEquDataArr() {
        if (null == usedEquData) {
            return Constant.DEFAULT_HOUR_EQU_DATA.split(",");
        }
        return AttDataUtils.subAttData(Constant.DEFAULT_HOUR_EQU_DATA, usedEquData).split(",");
        //return null != equData ? AttDataUtils.subAttData(remain, equData).split(",") : remain.split(",");
    }
}
