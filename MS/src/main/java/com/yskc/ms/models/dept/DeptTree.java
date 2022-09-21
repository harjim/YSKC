package com.yskc.ms.models.dept;

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
    private static final long serialVersionUID = 1L;
    private Integer parentId;
    private String key;
    private String title;
    private String value;
    private String fullPath;
    private ScopedSlots scopedSlots = new ScopedSlots();
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

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
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

    public ScopedSlots getScopedSlots() {
        return scopedSlots;
    }

    public void setScopedSlots(ScopedSlots scopedSlots) {
        this.scopedSlots = scopedSlots;
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
            if (d.getParentId().equals(parentId)) {
                d.setValue(d.getKey());
                d.key = d.fullPath;
                d.setChildren(getTree(tree, Integer.valueOf(d.value)).toArray());
                dList.add(d);
            }
        }
        return dList;
    }
}

class ScopedSlots {
    private String title = "title";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
