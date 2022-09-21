package com.yskc.rs.models.menu;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 菜单实体
 * @author Administrator
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppMenuModel implements Serializable  {
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
    private boolean hasYearSelect;

	public boolean isHasYearSelect() {
		return hasYearSelect;
	}

	public void setHasYearSelect(boolean hasYearSelect) {
		this.hasYearSelect = hasYearSelect;
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
	@Override
	public String toString() {
		return "AppMenuModel [id=" + id + ", parentId=" + parentId + ", name=" + name + ", type=" + type + ", level="
				+ level + ", url=" + url + ", icon=" + icon + ", perms=" + perms + ", status=" + status + ", seq=" + seq
				+ ", remark=" + remark + "]";
	}
    
    
}
