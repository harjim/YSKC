package com.yskc.rs.models.excel;

import com.yskc.rs.entity.init.TableField;

public class MaterialTypeExcel extends TableField {

    private Integer type;

    private Integer year;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
