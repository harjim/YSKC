package com.yskc.rs.models.energy;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.energy
 * @Author: zhangdingfu
 * @CreateTime: 2019-08-29 08:28
 * @Description: 更新剩余比例
 */
public class UpdateEnergyRatio implements Serializable {

    private Integer id;

    private BigDecimal remainRatio;

    private Integer type;

    public BigDecimal getRemainRatio() {
        return remainRatio;
    }

    public void setRemainRatio(BigDecimal remainRatio) {
        this.remainRatio = remainRatio;
    }

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

    public UpdateEnergyRatio(Integer id, BigDecimal remainRatio,Integer type) {
        this.id = id;
        this.remainRatio = remainRatio;
        this.type = type;
    }
}
