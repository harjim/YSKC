package com.yskc.ms.models.rs;

import java.util.List;

/**
 * rsSession
 * @author zdf
 */
public class RsUserSession {
    private String token;
    private Integer id;
    private List<Integer> roleIds;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }
}
