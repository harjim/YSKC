package com.yskc.rs.models.energy;

import java.io.Serializable;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.energy
 * @Author: zhangdingfu
 * @CreateTime: 2019-08-16 15:39
 * @Description: 操作model
 */
public class EnergyModifyModel implements Serializable {

    private Integer rdDeptId;

    private Integer type;

    private List<Integer> ids;

    public Integer getRdDeptId() {
        return rdDeptId;
    }

    public void setRdDeptId(Integer rdDeptId) {
        this.rdDeptId = rdDeptId;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
