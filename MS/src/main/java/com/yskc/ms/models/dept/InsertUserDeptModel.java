package com.yskc.ms.models.dept;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.dept
 * @Author: wangxing
 * @CreateTime: 2020-01-13 16:00
 * @Description:
 */
public class InsertUserDeptModel {
    private List<Integer> userIds;
    private Integer deptId;
    private Boolean addTo;

    public Boolean getAddTo() {
        return addTo;
    }

    public void setAddTo(Boolean addTo) {
        this.addTo = addTo;
    }

    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }
}
