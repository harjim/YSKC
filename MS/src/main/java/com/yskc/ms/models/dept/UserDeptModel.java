package com.yskc.ms.models.dept;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.dept
 * @Author: wangxing
 * @CreateTime: 2019-10-16 11:51
 * @Description: UserDeptModel
 */
public class UserDeptModel {
    private Integer userId;
    private Integer deptId;
    private String deptName;

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

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
