package com.yskc.ms.models.role;

import java.util.List;

/**
 * 配置权限模型
 *
 * @author huronghua
 */
public class SetRoleModel {
    private Integer roleId;
    private List<Integer> menuIdList;
    private List<RoleDataModel> dataPerms;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public List<Integer> getMenuIdList() {
        return menuIdList;
    }

    public void setMenuIdList(List<Integer> menuIdList) {
        this.menuIdList = menuIdList;
    }

    public List<RoleDataModel> getDataPerms() {
        return dataPerms;
    }

    public void setDataPerms(List<RoleDataModel> dataPerms) {
        this.dataPerms = dataPerms;
    }
}
