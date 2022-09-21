package com.yskc.ms.models.newexpert.index;

import java.io.Serializable;

/**
 * @DateTime: 2021/9/26 17:23
 * @Description:
 * @author: hsx
 */
public class ConfigModuleModel implements Serializable {

    private Integer id;

    private String moduleName;     //模块名

    private String iconPath;      //小图标图片链接

    private String activeIconPath;  //激活状态小图标；链接

    private String imagePath;     //大图链接

    private String description;   //描述

    private Integer order;        //排序

    private Boolean disabled;     //状态: 1：禁用  0 启用(默认为0)

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
}
