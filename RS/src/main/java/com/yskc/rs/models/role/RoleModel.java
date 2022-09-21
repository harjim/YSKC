package com.yskc.rs.models.role;

/**
 * 添加角色模型
 * @author huronghua
 */
public class RoleModel {
    private String roleName;
    private String remark;
    private Integer roleId;
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
