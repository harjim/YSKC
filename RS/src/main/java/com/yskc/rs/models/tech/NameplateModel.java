package com.yskc.rs.models.tech;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/3/22 16:03
 * @Description:铭牌model
 */
public class NameplateModel implements Serializable {

    private Integer id;

    private Integer seq;
    private String ename;

    private String emodal;

    private String manufacturer;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date factoryDate;

    private String factoryNo;
    private String filePath;
    private Integer investmentId;
    private String setPlace;

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getSetPlace() {
        return setPlace;
    }

    public void setSetPlace(String setPlace) {
        this.setPlace = setPlace;
    }

    public Integer getInvestmentId() {
        return investmentId;
    }

    public void setInvestmentId(Integer investmentId) {
        this.investmentId = investmentId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEname() {
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

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Date getFactoryDate() {
        return factoryDate;
    }

    public void setFactoryDate(Date factoryDate) {
        this.factoryDate = factoryDate;
    }

    public String getFactoryNo() {
        return factoryNo;
    }

    public void setFactoryNo(String factoryNo) {
        this.factoryNo = factoryNo;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
