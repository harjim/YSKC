package com.yskc.docservice.models.rs;

import java.io.Serializable;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.employee
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-16 16:19
 * @Description: 人员下拉框model
 */
public class EmployeeSelectModel implements Serializable {

    private String enumber;
    private String ename;

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
}
