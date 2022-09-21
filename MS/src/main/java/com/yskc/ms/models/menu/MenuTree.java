package com.yskc.ms.models.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 部门树
 * @author zhangdingfu
 * @Date 2019年7月4日 下午5:57:48
 */
public class MenuTree implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer parentId;
	private Integer key;
	private String title;
	private String value;
	private Object[] children;
	
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Object[] getChildren() {
		return children;
	}
	public void setChildren(Object[] children) {
		this.children = children;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 获取部门树
	 * @param tree
	 * @return
	 * @codeBy zhangdingfu
	 * @Date 2019年7月4日 下午4:55:26
	 */
	public static List<MenuTree> getTree(List<MenuTree> tree){
		return getTree(tree, -2);
	}
	
	/**
	 * 部门树递归
	 * @param tree
	 * @return
	 * @codeBy zhangdingfu
	 * @Date 2019年7月4日 下午4:55:26
	 */
	public static List<MenuTree> getTree(List<MenuTree> tree, int parentId){
		List<MenuTree> dList = new ArrayList<MenuTree>();
		for(MenuTree d : tree) {
			if(d.getParentId().equals(parentId)) {
				d.setValue(d.getKey().toString());
				d.setChildren(getTree(tree, d.getKey()).toArray());
				dList.add(d);
			}
		}
		return dList;
	}
}
