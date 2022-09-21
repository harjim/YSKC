package com.yskc.rs.models.outsourcing;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.outsourcing
 * @Author: zhangdingfu
 * @CreateTime: 2021-06-08 15:49
 * @Description: 保存委外项目
 */
public class SaveOutsourcingModel {

    private Integer type;

    private Integer year;

    private List<BaseOutsourcingModel> list;


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<BaseOutsourcingModel> getList() {
        return list;
    }

    public void setList(List<BaseOutsourcingModel> list) {
        this.list = list;
    }
}
