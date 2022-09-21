package com.yskc.rs.models.accounttitle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 科目树
 *
 * @author zhangdingfu
 */
public class AccountTree implements Serializable {
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
    private Integer key;
    /**
     * 科目名称
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
     * 获取科目树
     *
     * @param tree
     * @return
     */
    public static List<AccountTree> getTree(List<AccountTree> tree) {
        return getTree(tree, -2);
    }

    /**
     * @param tree
     * @param parentId
     * @return
     */
    public static List<AccountTree> getTree(List<AccountTree> tree, int parentId) {
        List<AccountTree> dList = new ArrayList<AccountTree>();
        for (AccountTree d : tree) {
            if (d.getParentId().equals(parentId)) {
                d.setValue(d.getKey().toString());
                d.setChildren(getTree(tree, d.getKey()).toArray());
                dList.add(d);
            }
        }
        return dList;
    }
}
