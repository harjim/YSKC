package com.yskc.ms.models.newexpert.index;

import com.yskc.ms.models.params.PageParams;

import java.io.Serializable;

/**
 * @DateTime: 2021/9/26 17:24
 * @Description:
 * @author: hsx
 */
public class QueryConfigDataModel extends PageParams implements Serializable {

    private Integer type;

    private String label;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
