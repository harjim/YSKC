package com.yskc.rs.models.dept;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 部门树
 *
 * @author zhangdingfu
 * @Date 2019年7月4日 下午5:57:48
 */
public class DeptTree implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 父节点id
     */
    private Integer parentId;
    /**
     * id
     */
    private String key;
    /**
     * 部门名称
     */
    private String title;
    /**
     * id
     */
    private String value;

    /**
     * 子节点
     */
    private Object[] children;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
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
     *
     * @param tree
     * @return
     * @codeBy zhangdingfu
     * @Date 2019年7月4日 下午4:55:26
     */
    public static List<DeptTree> getTree(List<DeptTree> tree) {
        return getTree(tree, -1);
    }

    /**
     * 部门树递归
     *
     * @param tree
     * @return
     * @codeBy zhangdingfu
     * @Date 2019年7月4日 下午4:55:26
     */
    public static List<DeptTree> getTree(List<DeptTree> tree, int parentId) {
        List<DeptTree> dList = new ArrayList<DeptTree>();
        for (DeptTree d : tree) {
            if (d.parentId.equals(parentId)) {
                d.setChildren(getTree(tree, Integer.parseInt(d.value)).toArray());
                dList.add(d);
            }
        }
        return dList;
    }
}
