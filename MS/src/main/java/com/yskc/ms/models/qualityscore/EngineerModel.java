package com.yskc.ms.models.qualityscore;

import java.io.Serializable;

/**
 * @DateTime: 2022/6/6 10:24
 * @Description:质量评分工程师model
 * @author: hsx
 */
public class EngineerModel implements Serializable {

    private Integer id;

    private String realName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
