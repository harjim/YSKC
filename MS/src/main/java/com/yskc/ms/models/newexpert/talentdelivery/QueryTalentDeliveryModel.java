package com.yskc.ms.models.newexpert.talentdelivery;

import com.yskc.ms.models.params.PageParams;

import java.io.Serializable;

/**
 * @program: ms
 * @description: 人才需求投递查询
 * @author: cyj
 * @create: 2021-12-09 10:10
 **/
public class QueryTalentDeliveryModel extends PageParams implements Serializable {

    //相关联人才需求id
    private Integer talentId;

    private String name;

    private Integer eduLevel;

    public Integer getTalentId() {
        return talentId;
    }

    public void setTalentId(Integer talentId) {
        this.talentId = talentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(Integer eduLevel) {
        this.eduLevel = eduLevel;
    }
}
