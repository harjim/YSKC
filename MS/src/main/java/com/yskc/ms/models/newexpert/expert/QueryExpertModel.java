package com.yskc.ms.models.newexpert.expert;

import com.yskc.ms.models.params.PageParams;

import java.io.Serializable;
import java.util.Date;

/**
 * @DateTime: 2021/9/23 16:19
 * @Description:查询专家库用户列表model
 * @author: hsx
 */
public class QueryExpertModel extends PageParams implements Serializable {

    private String realname;

    private String types;              //查询user_expert时为专家类型，查询user表时为用户类型

    private Integer status;

    private Integer gender;

    private Date birthday;

    private String titles;              //职称

    private String researchArea;        //研究领域

    private String industries;          //熟悉的行业【多选】

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
