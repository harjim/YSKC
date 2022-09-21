package com.yskc.rs.models;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models
 * @Author: zhangdingfu
 * @CreateTime: 2021-05-31 09:09
 * @Description: 基础下拉选择
 */
public class BaseSelectModel {

    private Integer id;

    private String title;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
