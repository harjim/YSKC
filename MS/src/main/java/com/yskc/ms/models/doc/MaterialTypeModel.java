package com.yskc.ms.models.doc;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.doc
 * @Author: zhangdingfu
 * @CreateTime: 2022-05-13 16:17
 * @Description: 物料类型model
 */
public class MaterialTypeModel {

    private Integer type;
    private Integer rdType;
    private String month;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getRdType() {
        return rdType;
    }

    public void setRdType(Integer rdType) {
        this.rdType = rdType;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
