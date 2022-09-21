package com.yskc.ms.models.patent;

import com.yskc.ms.models.params.PageParams;

/**
 * Created by hck
 * on 2020/6/5 16:25
 * description:
 */
public class QueryPatentCostModel extends PageParams {

    private String patentNo;

    private Integer type;

    public String getPatentNo() {
        return patentNo;
    }

    public void setPatentNo(String patentNo) {
        this.patentNo = patentNo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
