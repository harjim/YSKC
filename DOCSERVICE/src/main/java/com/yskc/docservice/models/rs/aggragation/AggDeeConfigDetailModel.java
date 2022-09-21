package com.yskc.docservice.models.rs.aggragation;

import java.math.BigDecimal;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.aggregation
 * @Author: zhangdingfu
 * @CreateTime: 2021-05-21 16:52
 * @Description: 研发归集配置明细
 */
public class AggDeeConfigDetailModel {

    private List<List<BigDecimal>> configs;

    private BigDecimal etypeRatio;

    private Integer max;

    public List<List<BigDecimal>> getConfigs() {
        return configs;
    }

    public void setConfigs(List<List<BigDecimal>> configs) {
        this.configs = configs;
    }

    public BigDecimal getEtypeRatio() {
        return etypeRatio;
    }

    public void setEtypeRatio(BigDecimal etypeRatio) {
        this.etypeRatio = etypeRatio;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public boolean noMax() {
        return max == null || max <= 0;
    }
}
