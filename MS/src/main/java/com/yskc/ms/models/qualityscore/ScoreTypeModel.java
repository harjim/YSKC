package com.yskc.ms.models.qualityscore;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @DateTime: 2022/6/9 10:16
 * @Description:评分权值model
 * @author: hsx
 */
public class ScoreTypeModel implements Serializable {

    private Integer type;

    private BigDecimal weight;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
}
