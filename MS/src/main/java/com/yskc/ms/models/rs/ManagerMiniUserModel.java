package com.yskc.ms.models.rs;

import java.io.Serializable;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.entity.rs.models
 * @Author: zhangdingfu
 * @CreateTime: 2019-10-15 15:55
 * @Description: 用户model
 */
public class ManagerMiniUserModel implements Serializable {

    private Integer id;
    private Integer msUserId;
    private String userName;
    private String realName;
    private String roleName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMsUserId() {
        return msUserId;
    }

    public void setMsUserId(Integer msUserId) {
        this.msUserId = msUserId;
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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
