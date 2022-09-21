package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

/**
 * Created by hck
 * on 2020/12/15 10:29
 * description:流程
 */
@TableName("flow")
public class FlowEntity {

    @TableId
    private Integer id;
    private String name;
    private Integer type;//0子流程 1分支流程
    private String remark;
    private Date createTime;

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

    public FlowEntity() {
    }

    public FlowEntity(String name, Integer type, String remark, Date createTime) {
        this.name = name;
        this.type = type;
        this.remark = remark;
        this.createTime = createTime;
    }
}
