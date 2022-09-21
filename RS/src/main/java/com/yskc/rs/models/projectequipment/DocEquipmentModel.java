package com.yskc.rs.models.projectequipment;

import java.io.Serializable;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.project
 * @Author: zhangdingfu
 * @CreateTime: 2019-09-17 08:26
 * @Description: 文档相关设备
 */
public class DocEquipmentModel implements Serializable {

    private String ecode;

    private String ename;

    private Integer etype;

    private String deptName;

    private Integer rdDeptId;

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

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getRdDeptId() {
        return rdDeptId;
    }

    public void setRdDeptId(Integer rdDeptId) {
        this.rdDeptId = rdDeptId;
    }
}
