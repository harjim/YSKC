package com.yskc.docservice.entity.rs;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 字典表
 * @author huronghua
 */
@TableName("sys_dictionary")
public class SysDictionaryEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private int type;
    private String key;
    private String value;
    private String parentKey;
    private String remark;
    private int order;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

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
}
