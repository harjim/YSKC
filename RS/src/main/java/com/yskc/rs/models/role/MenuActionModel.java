package com.yskc.rs.models.role;

import java.util.List;

/**
 * 菜单功能模型
 * @author huronghua
 */
public class MenuActionModel {
    private String catalogName;
    private String menuName;
    private Integer menuId;
    private Integer menuCount;
    private Boolean changeSpan;
    private List<ActionModel> actionModelList;

    public Boolean getChangeSpan() {
        return changeSpan;
    }

    public void setChangeSpan(Boolean changeSpan) {
        this.changeSpan = changeSpan;
    }

    public Integer getMenuCount() {
        return menuCount;
    }

    public void setMenuCount(Integer menuCount) {
        this.menuCount = menuCount;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public List<ActionModel> getActionModelList() {
        return actionModelList;
    }

    public void setActionModelList(List<ActionModel> actionModelList) {
        this.actionModelList = actionModelList;
    }
}
