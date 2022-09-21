package com.yskc.ms.models.menu;

import com.baomidou.mybatisplus.annotations.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单实体
 *
 * @author Administrator
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppMenuModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId
    private Integer id;
    private Integer parentId;
    private String name;
    private int type;
    private int level;
    private String url;
    private String icon;
    private String perms;
    private int status;
    private String seq;
    private String remark;
    private List<AppMenuModel> children;
    private String fullPath;
    private int menuManageType;
    private int rsMenuManageType;
    private boolean hasYearSelect;
    /**
     * 菜单类型【RS】
     */
    private Integer menuType;
    private Integer platform;

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public boolean isHasYearSelect() {
        return hasYearSelect;
    }

    public void setHasYearSelect(boolean hasYearSelect) {
        this.hasYearSelect = hasYearSelect;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public int getMenuManageType() {
        return menuManageType;
    }

    public int getRsMenuManageType() {
        return rsMenuManageType;
    }

    public void setRsMenuManageType(int rsMenuManageType) {
        this.rsMenuManageType = rsMenuManageType;
    }

    public void setMenuManageType(int menuManageType) {
        this.menuManageType = menuManageType;
    }

    public List<AppMenuModel> getChildren() {
        return children;
    }

    public void setChildren(List<AppMenuModel> children) {
        this.children = children;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }
}
