package com.yskc.docservice.models.ms.login;

import java.util.List;

/**
 * 菜单权限
 *
 * @author huronghua
 */
public class MenuPermModel {
    private Integer menuId;
    private String perms;
    private String url;
    /**
     * 0个人 1部门 2All
     */
    private Integer dataType;
    private Integer parentId;

    private List<String> deptFullPaths;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<String> getDeptFullPaths() {
        return deptFullPaths;
    }

    public void setDeptFullPaths(List<String> deptFullPaths) {
        this.deptFullPaths = deptFullPaths;
    }
}
