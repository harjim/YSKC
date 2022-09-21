package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.ms.entity.ms.models.MsBaseEntity;

import java.io.Serializable;

/**
 * @DateTime: 2022/6/2 9:03
 * @Description:质量评分 过程文件月份组
 * @author: hsx
 */
@TableName("quality_score_month")
public class QualityScoreMonth extends MsBaseEntity implements Serializable {

    private static final long serialVersionUID = -7087057681956761002L;

    @TableId
    private Integer id;

    private String months;

    private Integer rsProjectId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public Integer getRsProjectId() {
        return rsProjectId;
    }

    public void setRsProjectId(Integer rsProjectId) {
        this.rsProjectId = rsProjectId;
    }
}
