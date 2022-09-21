package com.yskc.rs.models.hightech;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models
 * @Author: zhangdingfu
 * @CreateTime: 2021-05-31 09:09
 * @Description: 基础下拉选择
 */
public class HighTechSelectModel {

    private Integer id;

    private String hname;

    private String hcode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHname() {
        return hname;
    }

    public void setHname(String hname) {
        this.hname = hname;
    }

    public String getHcode() {
        return hcode;
    }

    public void setHcode(String hcode) {
        this.hcode = hcode;
    }
}
