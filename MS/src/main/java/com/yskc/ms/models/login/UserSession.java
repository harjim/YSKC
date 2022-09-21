package com.yskc.ms.models.login;

import com.yskc.ms.enums.PlatformEnum;
import com.yskc.ms.models.role.UserRoleDept;

import java.util.Date;
import java.util.List;

/**
 * 用户会话信息
 *
 * @author huronghua
 */
public class UserSession {
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
    private String dingUserId;
    private String position;
    /**
     * 登录类型：0 pc端，1 移动端，默认为pc平台登录
     */
    private Integer platform = PlatformEnum.PC.getPlatform();

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

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getDingUserId() {
        return dingUserId;
    }

    public void setDingUserId(String dingUserId) {
        this.dingUserId = dingUserId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
