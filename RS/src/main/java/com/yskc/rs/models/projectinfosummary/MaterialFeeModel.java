package com.yskc.rs.models.projectinfosummary;

import java.math.BigDecimal;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectinfosummary
 * @Author: zhangdingfu
 * @CreateTime: 2021-05-11 14:56
 * @Description: 材料费用
 */
public class MaterialFeeModel {

    private Integer projectId;
    /**
     * 原材料
     */
    private BigDecimal raw;
    /**
     * 辅料
     */
    private BigDecimal reserve;
    /**
     * 备品备件[工艺装备]归集数
     */
    private BigDecimal auxiliary;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public BigDecimal getRaw() {
        return raw;
    }

    public void setRaw(BigDecimal raw) {
        this.raw = raw;
    }

    public BigDecimal getReserve() {
        return reserve;
    }

    public void setReserve(BigDecimal reserve) {
        this.reserve = reserve;
    }

    public BigDecimal getAuxiliary() {
        return auxiliary;
    }

    public void setAuxiliary(BigDecimal auxiliary) {
        this.auxiliary = auxiliary;
    }
}
