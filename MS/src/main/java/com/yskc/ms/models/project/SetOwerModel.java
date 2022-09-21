package com.yskc.ms.models.project;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.project
 * @Author: zhangdingfu
 * @CreateTime: 2020-10-20 09:16
 * @Description: 设置业务人员model
 */
public class SetOwerModel {

    private List<Integer> projectIds;
    private Integer owerId;
    private Integer businessId;
    private Integer deptId;


    public List<Integer> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(List<Integer> projectIds) {
        this.projectIds = projectIds;
    }

    public Integer getOwerId() {
        return owerId;
    }

    public void setOwerId(Integer owerId) {
        this.owerId = owerId;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }
}
