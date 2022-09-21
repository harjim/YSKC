package com.xxl.job.executor.models.rs_project_uncollected;

import java.util.Date;
import java.util.List;

/**
 * @program: xxl-job
 * @description: 未归集RD模型
 * @author: cyj
 * @create: 2021-12-10 16:06
 **/
public class RsProjectUnCollectedModel {
    private Integer id;

    private Integer rsProjectId;

    private String pname;

    private Integer companyId;

    private String rdTitle;

    private Integer beginYear;

    private Integer endYear;

    private List<Integer> years;


    private Date createTime;

    private Date lastUpdateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRsProjectId() {
        return rsProjectId;
    }

    public void setRsProjectId(Integer rsProjectId) {
        this.rsProjectId = rsProjectId;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public Integer getBeginYear() {
        return beginYear;
    }

    public void setBeginYear(Integer beginYear) {
        this.beginYear = beginYear;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }

    public List<Integer> getYears() {
        return years;
    }

    public void setYears(List<Integer> years) {
        this.years = years;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
