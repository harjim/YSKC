package com.yskc.docservice.models.ms.login;

import com.yskc.docservice.models.ms.role.UserRoleDept;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: DOCSERVICE
 * @BelongsPackage: com.yskc.docservice.models.ms.login
 * @Author: zhangdingfu
 * @CreateTime: 2022-03-17 10:52
 * @Description: ms用户session
 */
public class MsUserSession {
    private Integer id;
    private String userName;
    private String realName;
    private String avatar;
    private String token;
    private Integer status;
    private Date createTime;
    private Boolean reSetPassword;
    private List<Integer> deptIds;
    private List<String> deptFullPaths;
    private List<UserRoleDept> roleDepts;
    private String mtypes;

    public Boolean getReSetPassword() {
        return reSetPassword;
    }

    public void setReSetPassword(Boolean reSetPassword) {
        this.reSetPassword = reSetPassword;
    }

    public List<UserRoleDept> getRoleDepts() {
        return roleDepts;
    }

    public void setRoleDepts(List<UserRoleDept> roleDepts) {
        this.roleDepts = roleDepts;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<String> getDeptFullPaths() {
        return deptFullPaths;
    }

    public void setDeptFullPaths(List<String> deptFullPaths) {
        this.deptFullPaths = deptFullPaths;
    }

    public List<Integer> getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(List<Integer> deptIds) {
        this.deptIds = deptIds;
    }

    public String getMtypes() {
        return mtypes;
    }

    public void setMtypes(String mtypes) {
        this.mtypes = mtypes;
    }
}


