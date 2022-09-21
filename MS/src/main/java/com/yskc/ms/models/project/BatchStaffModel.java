package com.yskc.ms.models.project;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.project
 * @Author: zhangdingfu
 * @CreateTime: 2020-06-28 11:52
 * @Description: 批量人员modal
 */
public class BatchStaffModel {

    private List<Integer> projectIds;

    private List<Integer> staffIds;

    private Boolean isAdd;

    public List<Integer> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(List<Integer> projectIds) {
        this.projectIds = projectIds;
    }

    public List<Integer> getStaffIds() {
        return staffIds;
    }
    public void setStaffIds(List<Integer> staffIds) {
        this.staffIds = staffIds;
    }

    public Boolean getAdd() {
        return isAdd;
    }

    public void setAdd(Boolean add) {
        isAdd = add;
    }
}
