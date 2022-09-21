package com.yskc.ms.models.flow;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hck
 * on 2020/12/18 11:24
 * description:流程
 */
public class FlowModel implements Serializable {
    private Integer id;

    private String name;
    private Integer type;
    private String remark;
    private Date createTime;

    private Boolean sign;//是否可修补标识 true 可修改 false 不能修改

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getSign() {
        return sign;
    }

    public void setSign(Boolean sign) {
        this.sign = sign;
    }
}
