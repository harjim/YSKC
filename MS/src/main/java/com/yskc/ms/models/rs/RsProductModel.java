package com.yskc.ms.models.rs;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hck
 * on 2020/10/13 8:51
 * description:申报项目model
 */
public class RsProductModel implements Serializable {

    private Integer id;
    private String addressCode;//区域代码
    private String productName;//项目名称
    private Integer year;
    private String pType;//项目类型 0:经信类
    private String pLevel;// 项目级别
    private String govName;//所属政府机构
    private String address;//办公地址
    private List<RsDirectionModel> directionModels;//申报方向集合
    private Integer managerId;
    private String realName;//负责人姓名

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public List<RsDirectionModel> getDirectionModels() {
        return directionModels;
    }

    public void setDirectionModels(List<RsDirectionModel> directionModels) {
        this.directionModels = directionModels;
    }

    public String getpLevel() {
        return pLevel;
    }

    public void setpLevel(String pLevel) {
        this.pLevel = pLevel;
    }

    public String getGovName() {
        return govName;
    }

    public void setGovName(String govName) {
        this.govName = govName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
