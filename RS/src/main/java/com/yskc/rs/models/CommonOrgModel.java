package com.yskc.rs.models;

import java.io.Serializable;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models
 * @Author: zhangdingfu
 * @CreateTime: 2020-03-19 15:13
 * @Description: 公有组织model
 */
public class CommonOrgModel implements Serializable {

    private Integer id;

    private String name;

    private String fullPath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }
}
