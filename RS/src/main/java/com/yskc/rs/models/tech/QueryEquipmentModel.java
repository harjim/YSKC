package com.yskc.rs.models.tech;

import com.yskc.rs.models.params.PageParams;

/**
 * @Author: hck
 * @DateTime: 2021/3/18 11:13
 * @Description:设备查询modal
 */
public class QueryEquipmentModel extends PageParams {

    private String ename;

    private String emodal;

    private Integer beianId;
    private Integer id;//获取合票信息时传投资清单id

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getEmodal() {
        return emodal;
    }

    public void setEmodal(String emodal) {
        this.emodal = emodal;
    }

    public Integer getBeianId() {
        return beianId;
    }

    public void setBeianId(Integer beianId) {
        this.beianId = beianId;
    }
}
