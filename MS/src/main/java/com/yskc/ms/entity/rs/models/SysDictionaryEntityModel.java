package com.yskc.ms.entity.rs.models;

public class SysDictionaryEntityModel {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private int type;
    private String key;
    private String value;
    private String parentKey;
    private String remark;
    private int order;
    private String parentKeyValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getParentKeyValue() {
        return parentKeyValue;
    }

    public void setParentKeyValue(String parentKeyValue) {
        this.parentKeyValue = parentKeyValue;
    }
}
