package com.yskc.ms.models.newexpert.index;

import java.io.Serializable;

/**
 * @DateTime: 2021/9/26 17:23
 * @Description:
 * @author: hsx
 */
public class ConfigDataModel implements Serializable {

    private Integer id;

    private Integer type;   // 0：企业，1：人才资源库，2：成果转化，3项目类型

    private String label;   // 标签，名称

    private Integer quantity;   //数量

    private Boolean disabled;   //状态: 1：禁用  0启用(默认为0)

    private Integer order;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
