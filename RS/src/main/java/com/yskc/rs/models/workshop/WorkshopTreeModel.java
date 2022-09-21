package com.yskc.rs.models.workshop;

import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.workshop
 * @Author: wangxing
 * @CreateTime: 2019-11-21 10:56
 * @Description: 下拉选择树
 */
public class WorkshopTreeModel {
    /**
     * 父节点id
     */
    private Integer parentId;
    /**
     * id
     */
    private String key;
    /**
     * 工艺线/车间名称
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Object[] getChildren() {
        return children;
    }

    public void setChildren(Object[] children) {
        this.children = children;
    }


    public static List<WorkshopTreeModel> getData(List<WorkshopTreeModel> tree) {
        return getData(tree, -2);
    }


    public static List<WorkshopTreeModel> getData(List<WorkshopTreeModel> tree, int parentId) {
        List<WorkshopTreeModel> dList = new ArrayList<WorkshopTreeModel>();
        for (WorkshopTreeModel d : tree) {
            if (d.getParentId().equals(parentId)) {
                d.setChildren(getData(tree, Integer.parseInt(d.getValue())).toArray());
                dList.add(d);
            }
        }
        return dList;
    }
}
