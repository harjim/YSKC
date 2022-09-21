package com.yskc.ms.models.role;

import com.yskc.ms.models.params.PageParams;

import java.io.Serializable;

/**
 * Created by hck
 * on 2020/6/5 8:38
 * description:
 */
public class QueryRoleModel extends PageParams implements Serializable {


    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
