package com.yskc.rs.models;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models
 * @Author: zhangdingfu
 * @CreateTime: 2021-01-27 09:35
 * @Description: key + id
 */
public class KeysAndIdsModel {

    private List<Integer> ids;

    private List<String> keys;

    private Integer year;

    private Integer roleType;

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }
}
