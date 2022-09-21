package com.yskc.rs.models.project;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/6/18 11:31
 * @Description:多层级菜单model 部门/车间
 */
public class RdManageModel implements Serializable {

    private String deptName;//研发部门

    private String workshop;//车间

    private String menu;//菜单 部门/车间

    private List<RdManageMenuModel> models;//产线/工艺段集合

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getWorkshop() {
        return workshop;
    }

    public void setWorkshop(String workshop) {
        this.workshop = workshop;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public List<RdManageMenuModel> getModels() {
        return models;
    }

    public void setModels(List<RdManageMenuModel> models) {
        this.models = models;
    }
}
