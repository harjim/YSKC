package com.yskc.ms.models;

import java.io.Serializable;

/**
 * @DateTime: 2021/11/16 9:48
 * @Description:审核状态分类统计model
 * @author: hsx
 */
public class CountModel implements Serializable {

    //值
    private String value;

    //统计数
    private Integer count;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
