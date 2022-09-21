package com.yskc.ms.models;

import com.yskc.ms.models.params.PageParams;

public class SysDictionaryParams extends PageParams {


    private String key;

    private String parentKeyValue;

    private String value;

    private  Integer type;

    public String getParentKeyValue() {
        return parentKeyValue;
    }

    public void setParentKeyValue(String parentKeyValue) {
        this.parentKeyValue = parentKeyValue;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
