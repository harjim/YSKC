package com.yskc.rs.models.equipment;

import java.io.Serializable;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.equipment
 * @Author: zhangdingfu
 * @CreateTime: 2019-09-02 16:17
 * @Description: 缩小字段设备
 */
public class EquipmentMinModel implements Serializable {

    private String ecode;

    private String ename;

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
}
