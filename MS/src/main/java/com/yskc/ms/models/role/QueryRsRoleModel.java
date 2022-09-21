package com.yskc.ms.models.role;

import com.yskc.ms.models.params.PageParams;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.role
 * @Author: zhangdingfu
 * @CreateTime: 2020-06-04 14:20
 * @Description: rs角色查询model
 */
public class QueryRsRoleModel extends PageParams {
    private String roleName;
    private String companyName;
    private Integer companyId;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
