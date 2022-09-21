package com.yskc.ms.entity.rs;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-30 16:22:19
 */
@TableName("app_menu")
public class RsMenu implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer id;
    /**
     *
     */
    private Integer parentId;
    /**
     *
     */
    private String name;
    /**
     *
     */
    private Integer type;
    /**
     *
     */
    private Integer level;
    /**
     *
     */
    private String url;
    /**
     *
     */
    private String icon;
    /**
     *
     */
    private String perms;
    /**
     *
     */
    private Integer status;
    /**
     *
     */
    private Integer seq;
    /**
     *
     */
    private String remark;
    /**
     *
     */
    private String fullPath;

    private boolean hasYearSelect;
    /**
     * 0 默认，1 集团菜单
     */
    private Integer menuType;

    public boolean isHasYearSelect() {
        return hasYearSelect;
    }

    public void setHasYearSelect(boolean hasYearSelect) {
        this.hasYearSelect = hasYearSelect;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getLevel() {
        return level;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public String getPerms() {
        return perms;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getFullPath() {
        return fullPath;
    }

	public Integer getMenuType() {
		return menuType;
	}

	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
	}
}
