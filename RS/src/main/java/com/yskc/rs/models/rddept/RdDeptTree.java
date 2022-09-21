package com.yskc.rs.models.rddept;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.rddept
 * @Author: wangxing
 * @CreateTime: 2019-08-08 15:40
 * @Description: rdDeptModel
 */
public class RdDeptTree {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 父节点id
     */
    private Integer parentId;

    private Integer level;
    /**
     * idPath
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
    /**
     * 委员会
     */
    private Object[] committee;
    /**
     * 文字方向
     */
    private Integer textDirection;
    /**
     * 节点类型
     */
    private Integer nodeType;
    /**
     * 对齐方式，0 左 1中 2右
     */
    private Integer align;

    public Integer getAlign() {
        return align;
    }

    public void setAlign(Integer align) {
        this.align = align;
    }

    public Integer getTextDirection() {
        return textDirection;
    }

    public void setTextDirection(Integer textDirection) {
        this.textDirection = textDirection;
    }

    public Integer getNodeType() {
        return nodeType;
    }

    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
    }

    public Object[] getCommittee() {
        return committee;
    }

    public void setCommittee(Object[] committee) {
        this.committee = committee;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 获取部门树
     *
     * @param tree
     * @return
     */
    public static List<RdDeptTree> getTree(List<RdDeptTree> tree, Map<Integer,List<RdDeptTree>> treeMap) {
        return getTree(tree, -1,treeMap);
    }

    /**
     * 部门树递归
     *
     * @param tree
     * @param parentId
     * @return
     */
    private static List<RdDeptTree> getTree(List<RdDeptTree> tree, int parentId,Map<Integer,List<RdDeptTree>> treeMap) {
        List<RdDeptTree> dList = new ArrayList<RdDeptTree>();
        for (RdDeptTree d : tree) {
            if (d.parentId.equals(parentId) && d.getNodeType()==0) {
                d.setChildren(getTree(tree, Integer.parseInt(d.value),treeMap).toArray());
                if(treeMap.containsKey(Integer.parseInt(d.value))) {
                    d.setCommittee(treeMap.get(Integer.parseInt(d.value)).toArray());
                }
                dList.add(d);
            }
        }
        return dList;
    }
}
