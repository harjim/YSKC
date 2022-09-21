package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.ms.entity.ms.models.MsBaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @DateTime: 2022/6/2 9:05
 * @Description:质量评分  类型权值
 * @author: hsx
 */
@TableName("quality_type")
public class QualityType extends MsBaseEntity implements Serializable {

    private static final long serialVersionUID = 8893899245186181614L;

    @TableId
    private Integer id;

    private Integer type;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal weight;

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

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
}
