package com.yskc.rs.models.tech;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.rs.models.params.PageParams;

import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/3/19 9:25
 * @Description:
 */
public class QueryInverstmentModel extends PageParams {

    private Integer type;

    private String ename;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date entryDate;

    private Integer beianId;

    public Integer getBeianId() {
        return beianId;
    }

    public void setBeianId(Integer beianId) {
        this.beianId = beianId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }
}
