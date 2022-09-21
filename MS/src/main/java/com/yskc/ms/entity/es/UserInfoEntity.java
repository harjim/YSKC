package com.yskc.ms.entity.es;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.ms.entity.es.models.EsBaseEntity;

import java.io.Serializable;

/**
 * @DateTime: 2021/9/23 15:37
 * @Description:
 * @author: hsx
 */
@TableName("user_info")
public class UserInfoEntity extends EsBaseEntity implements Serializable {

    private static final long serialVersionUID = 5895602853135680219L;

    @TableId
    private Integer id;

    private Integer userId;

    private String nativePlace;         //籍贯

    private Integer eduLevel;           // 学历 0: 研究生，1：本科，2：大专，3：其他

    private Integer degree;             // 学位 0：博士，1：硕士，2：学士，3：无

    private String titles;              // 职称【多选】

    private String graduatedSchool;     // 毕业院校

    private String researchArea;        //研究领域

    private String industries;          //熟悉的行业【多选】

    private String unitName;            //工作单位

    private String unitType;            //单位性质

    private String job;                 //职务

    private String unitAddressCode;     // 工作所在地代码

    private String unitAddress;         // 所在地代码

    private String workExperience;      //工作经历

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public Integer getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(Integer eduLevel) {
        this.eduLevel = eduLevel;
    }

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public String getGraduatedSchool() {
        return graduatedSchool;
    }

    public void setGraduatedSchool(String graduatedSchool) {
        this.graduatedSchool = graduatedSchool;
    }

    public String getResearchArea() {
        return researchArea;
    }

    public void setResearchArea(String researchArea) {
        this.researchArea = researchArea;
    }

    public String getIndustries() {
        return industries;
    }

    public void setIndustries(String industries) {
        this.industries = industries;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getUnitAddressCode() {
        return unitAddressCode;
    }

    public void setUnitAddressCode(String unitAddressCode) {
        this.unitAddressCode = unitAddressCode;
    }

    public String getUnitAddress() {
        return unitAddress;
    }

    public void setUnitAddress(String unitAddress) {
        this.unitAddress = unitAddress;
    }

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }
}
