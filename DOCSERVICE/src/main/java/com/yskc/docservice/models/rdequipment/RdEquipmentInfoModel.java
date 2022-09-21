package com.yskc.docservice.models.rdequipment;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.rdequipment
 * @Author: zhangdingfu
 * @CreateTime: 2021-07-06 16:06
 * @Description: 研发设备信息model
 */
public class RdEquipmentInfoModel {
    private String ecode;
    private String ename;
    private String rdDeptName;
    private Integer etype;
    private String rds;

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

    public String getRdDeptName() {
        return rdDeptName;
    }

    public void setRdDeptName(String rdDeptName) {
        this.rdDeptName = rdDeptName;
    }

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }

    public String getRds() {
        return rds;
    }

    public void setRds(String rds) {
        this.rds = rds;
    }
}
