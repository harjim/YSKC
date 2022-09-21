package com.xxl.job.executor.models.userdept;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.userdept
 * @Author: zhangdingfu
 * @CreateTime: 2020-12-16 11:35
 * @Description: 用户部门主管model
 */
public class UserAdminModel {
    private Integer userId;
    private Integer adminId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }
}
