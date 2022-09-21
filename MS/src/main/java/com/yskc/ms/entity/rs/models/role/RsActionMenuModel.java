package com.yskc.ms.entity.rs.models.role;

import java.io.Serializable;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.entity.rs.models.role
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-31 10:45
 * @Description: rs角色菜单model
 */
public class RsActionMenuModel implements Serializable {
    private Integer id;
    private String name;
    private Integer parentId;
    private Integer type;

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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
