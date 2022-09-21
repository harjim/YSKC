package com.yskc.ms.models;

import com.yskc.ms.models.role.MenuPermModel;

import java.util.List;
import java.util.Map;

/**
 * 返回前端使用
 *
 * @author huronghua
 */
public class UserInfo {
    private Integer id;
    private String userName;
    private String realName;
    private String avatar;
    private Integer status;
    private Boolean reSetPassword;
    private Map<String, MenuPermModel> permDataMap;
    private List<Integer> roleIds;
    private List<Integer> deptIds;
    private List<String> deptFullPaths;
    /**
     * 管理类型： 1.技术人员;2.财务人员;3.业务员；4.谈单经理(可多个类型)
     */
    private String mtypes;

    /**
     * 登录平台：0 pc端，1 移动端
     */
    private Integer platform;
    private String dingUserId;
    private String position;

    public Boolean getReSetPassword() {
        return reSetPassword;
    }

    public void setReSetPassword(Boolean reSetPassword) {
        this.reSetPassword = reSetPassword;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    public Map<String, MenuPermModel> getPermDataMap() {
        return permDataMap;
    }

    public void setPermDataMap(Map<String, MenuPermModel> permDataMap) {
        this.permDataMap = permDataMap;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public List<Integer> getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(List<Integer> deptIds) {
        this.deptIds = deptIds;
    }

    public List<String> getDeptFullPaths() {
        return deptFullPaths;
    }

    public void setDeptFullPaths(List<String> deptFullPaths) {
        this.deptFullPaths = deptFullPaths;
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
