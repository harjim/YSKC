package com.yskc.ms.entity.es;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @DateTime: 2021/9/26 15:40
 * @Description:
 * @author: hsx
 */
@TableName("config_data")
public class ConfigDataEntity implements Serializable {

    private static final long serialVersionUID = 1533008558755829069L;

    @TableId
    private Integer id;

    /**
     * 0：企业，1：人才资源库，2：成果转化，3项目类型
     */
    private Integer type;

    /**
     * 标签，名称
     */
    private String label;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 状态: 1：禁用  0启用(默认为0)
     */
    private Boolean disabled;

    private Integer order;
    private Integer msCreatorId;

    private Integer msLastUpdatorId;

    private Date createTime;

    private Date lastUpdateTime;


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

    public Integer getMsCreatorId() {
        return msCreatorId;
    }

    public void setMsCreatorId(Integer msCreatorId) {
        this.msCreatorId = msCreatorId;
    }

    public Integer getMsLastUpdatorId() {
        return msLastUpdatorId;
    }

    public void setMsLastUpdatorId(Integer msLastUpdatorId) {
        this.msLastUpdatorId = msLastUpdatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
