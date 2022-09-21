package com.yskc.rs.models.employee;

import java.io.Serializable;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.employee
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-16 16:19
 * @Description: 人员下拉框model
 */
public class EmployeeAutographModel implements Serializable {

    private String enumber;
    private String ename;
    private String autographUrl;

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

    public String getAutographUrl() {
        return autographUrl;
    }

    public void setAutographUrl(String autographUrl) {
        this.autographUrl = autographUrl;
    }
}
