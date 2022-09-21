package com.yskc.docservice.models.rs.project;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.project
 * @Author: zhangdingfu
 * @CreateTime: 2020-07-15 09:41
 * @Description: 项目信息model
 */
public class ProjectDetailModel {

    private Integer id;

    private String rdTitle;

    private String pname;


    @JsonFormat(pattern = "yyyy年MM月", timezone = "GMT+8")
    private Date beginDate;

    @JsonFormat(pattern = "yyyy年MM月", timezone = "GMT+8")
    private Date endDate;

    private String workshop;

    private Integer formula;

    private String tecIndustry;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getWorkshop() {
        return workshop;
    }

    public void setWorkshop(String workshop) {
        this.workshop = workshop;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public Integer getFormula() {
        return formula;
    }

    public void setFormula(Integer formula) {
        this.formula = formula;
    }

    public String getTecIndustry() {
        return tecIndustry;
    }

    public void setTecIndustry(String tecIndustry) {
        this.tecIndustry = tecIndustry;
    }
}
