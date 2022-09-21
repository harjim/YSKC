package com.yskc.rs.models.data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.data
 * @Author: zhangdingfu
 * @CreateTime: 2019-08-16 09:48
 * @Description: 操作model
 */
public class DataEquipmentModifyModel implements Serializable {

    private Integer id;
    private BigDecimal workHours;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getWorkHours() {
        return workHours;
    }

    public void setWorkHours(BigDecimal workHours) {
        this.workHours = workHours;
    }
}
