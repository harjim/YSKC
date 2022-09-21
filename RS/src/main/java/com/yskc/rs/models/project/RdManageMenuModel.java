package com.yskc.rs.models.project;

import java.io.Serializable;

/**
 * @Author: hck
 * @DateTime: 2021/6/18 11:35
 * @Description: 多层级研发管理菜单  产线/工艺段
 */
public class RdManageMenuModel implements Serializable {

    private String productLine;//产线

    private String processSection;//工艺段

    private String menuInfo;//菜单 产线/工艺段

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getProcessSection() {
        return processSection;
    }

    public void setProcessSection(String processSection) {
        this.processSection = processSection;
    }

    public String getMenuInfo() {
        return menuInfo;
    }

    public void setMenuInfo(String menuInfo) {
        this.menuInfo = menuInfo;
    }
}
