package com.yskc.ms.models;

import java.util.List;

/**
 * 字典树
 *
 * @author huronghua
 */
public class SysDictionaryModel {
    private Integer id;
    private String key;
    private String parentKey;
    private String value;
    private int order;
    private int type;
    private List<SysDictionaryModel> children;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<SysDictionaryModel> getChildren() {
        return children;
    }

    public void setChildren(List<SysDictionaryModel> children) {
        this.children = children;
    }
}
