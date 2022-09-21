package com.yskc.rs.models.hightech;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/5/28 14:02
 * @Description: 高品绑定model
 */
public class BindHighTechModel implements Serializable {

    private Integer highTechId;

    private List<Integer> ids;

    public Integer getHighTechId() {
        return highTechId;
    }

    public void setHighTechId(Integer highTechId) {
        this.highTechId = highTechId;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
