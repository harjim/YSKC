package com.yskc.docservice.models.ms;

import com.yskc.docservice.models.BaseUserInfo;

import java.util.List;

/**
 * 返回前端使用
 *
 * @author huronghua
 */
public class MsUserInfo extends BaseUserInfo {
    private Boolean reSetPassword;
    private List<Integer> roleIds;
    private List<Integer> deptIds;
    private List<String> deptFullPaths;
    /**
     * 管理类型： 1.技术人员;2.财务人员;3.业务员；4.谈单经理(可多个类型)
     */
    private String mtypes;

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
}
