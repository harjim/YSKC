package com.yskc.docservice.models.rs.excel;

import com.yskc.common.annotation.Excel;

import java.io.Serializable;

public class WorkshopExcel implements Serializable {

    private int id;
    @Excel(name = "序号", order = 0, fieldName = "seq")
    private Integer seq;
    @Excel(name = "工艺线/车间", order = 1, fieldName = "workshopName")
    private String workshopName;
    @Excel(name = "备注", order = 2, fieldName = "remark")
    private String remark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        if (seq == null) {
            seq = 1;
        }
        this.seq = seq;
    }

    public String getWorkshopName() {
        return workshopName;
    }

    public void setWorkshopName(String workshopName) {
        this.workshopName = workshopName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
