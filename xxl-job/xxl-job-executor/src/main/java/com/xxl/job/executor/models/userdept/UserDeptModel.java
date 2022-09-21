package com.xxl.job.executor.models.userdept;

import java.util.Set;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.userdept
 * @Author: zhangdingfu
 * @CreateTime: 2020-12-28 15:14
 * @Description: 用户部门
 */
public class UserDeptModel {
    private Integer userId;
    private Integer deptId;
    private Integer parentId;
    private Set<Integer> userIds;
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Set<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<Integer> userIds) {
        this.userIds = userIds;
    }
}
