package com.yskc.rs.models.role;

/**
 * 功能模型
 * @author huronghua
 */
public class ActionModel {
    private Integer menuId;
    private String  menuName;
    private boolean defaultCheck;

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public boolean isDefaultCheck() {
        return defaultCheck;
    }

    public void setDefaultCheck(boolean defaultCheck) {
        this.defaultCheck = defaultCheck;
    }
}
