package com.yskc.ms.entity.es;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @DateTime: 2021/9/26 15:51
 * @Description:
 * @author: hsx
 */
@TableName("config_module")
public class ConfigModuleEntity implements Serializable {

    private static final long serialVersionUID = -5930848410802782205L;

    @TableId
    private Integer id;

    private String moduleName;     //模块名

    private String iconPath;      //小图标图片链接

    private String activeIconPath;  //激活状态小图标；链接

    private String imagePath;     //大图链接

    private String description;   //描述

    private Integer order;        //排序

    private Boolean disabled;     //状态: 1：禁用  0 启用(默认为0)

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

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public String getActiveIconPath() {
        return activeIconPath;
    }

    public void setActiveIconPath(String activeIconPath) {
        this.activeIconPath = activeIconPath;
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
