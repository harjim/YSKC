package com.yskc.rs.models.activity;

import java.io.Serializable;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.activity
 * @Author: zhangdingfu
 * @CreateTime: 2019-11-04 16:11
 * @Description: 操作model
 */
public class ModifyModel implements Serializable {

    private String pKey;

    private String pValue;

    private Integer year;

    public String getpKey() {
        return pKey;
    }

    public void setpKey(String pKey) {
        this.pKey = pKey;
    }

    public String getpValue() {
        return pValue;
    }

    public void setpValue(String pValue) {
        this.pValue = pValue;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
