package com.yskc.rs.models.equipment;

import java.io.Serializable;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.equipment
 * @Author: zhangdingfu
 * @CreateTime: 2019-08-31 09:35
 * @Description: 设备操作
 */
public class EquipmentModifyModel implements Serializable {

    private Integer etype;

    private Integer rdDeptId;

    private Integer year;

    private List<Integer> ids;

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }

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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
