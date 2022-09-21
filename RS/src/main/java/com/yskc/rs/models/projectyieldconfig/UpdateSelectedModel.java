package com.yskc.rs.models.projectyieldconfig;


import java.io.Serializable;
import java.util.List;

/**
 * @DateTime: 2022/1/18 14:02
 * @Description:
 * @author: hsx
 */
public class UpdateSelectedModel implements Serializable {

    /**
     * 设置为显示的数据
     */
    private List<Integer> activeIds;

    /**
     * 设置为隐藏的数据
     */
    private List<Integer> inactiveIds;

    public List<Integer> getActiveIds() {
        return activeIds;
    }

    public void setActiveIds(List<Integer> activeIds) {
        this.activeIds = activeIds;
    }

    public List<Integer> getInactiveIds() {
        return inactiveIds;
    }

    public void setInactiveIds(List<Integer> inactiveIds) {
        this.inactiveIds = inactiveIds;
    }
}
