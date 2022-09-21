package com.yskc.ms.models.user;

import java.io.Serializable;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.user
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-19 10:15
 * @Description: mini
 */
public class MiniUserModel implements Serializable {

    private Integer id;

    private String userName;

    private String realName;

    private Integer deptId;

    private String deptName;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }
}
