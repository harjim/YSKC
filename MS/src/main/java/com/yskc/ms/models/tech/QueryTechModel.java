package com.yskc.ms.models.tech;

import com.yskc.ms.models.params.PageParams;


/**
 * Created by hck
 * on 2020/7/23 17:29
 * description:
 */
public class QueryTechModel extends PageParams {

    private String ptName;//项目名称

    private String companyName;//客户名称

    private Integer year;//年份

    private String fullPath;//所属部门

    private Integer[] tIds;//技术人员ids

    private String stageKey;//阶段

    private String status;//状态

    public String getStageKey() {
        return stageKey;
    }

    public void setStageKey(String stageKey) {
        this.stageKey = stageKey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPtName() {
        return ptName;
    }

    public void setPtName(String ptName) {
        this.ptName = ptName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public Integer[] gettIds() {
        return tIds;
    }

    public void settIds(Integer[] tIds) {
        this.tIds = tIds;
    }
}
