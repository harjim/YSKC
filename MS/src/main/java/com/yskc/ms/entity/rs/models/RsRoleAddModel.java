package com.yskc.ms.entity.rs.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.entity.rs.models.role
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-30 17:45
 * @Description: rs角色添加新角色model
 */
public class RsRoleAddModel implements Serializable {
    private Integer id;

    private Integer companyId;

    @NotNull(message = "角色名称不能为空")
    @NotEmpty(message = "角色名称不能为空")
    private String roleName;

    private String remark;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
