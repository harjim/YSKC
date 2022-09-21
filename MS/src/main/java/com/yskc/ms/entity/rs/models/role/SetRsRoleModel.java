package com.yskc.ms.entity.rs.models.role;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.entity.rs.models.role
 * @Author: zhangdingfu
 * @CreateTime: 2019-08-01 14:58
 * @Description: 设置角色功能
 */
public class SetRsRoleModel implements Serializable {
    @NotNull(message = "无法获取操作的角色")
    private Integer roleId;

    private Integer[] menuArr;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer[] getMenuArr() {
        return menuArr;
    }

    public void setMenuArr(Integer[] menuArr) {
        this.menuArr = menuArr;
    }
}
