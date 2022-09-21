package com.yskc.ms.models.role;

import java.io.Serializable;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.role
 * @Author: zhangdingfu
 * @CreateTime: 2020-01-11 09:48
 * @Description: 权限及数据权限
 */
public class RoleDataModel implements Serializable {

    private Integer menuId;

    private Integer dataType;

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }
}
