package com.xxl.job.executor.models.hightech;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.hightech
 * @Author: zhangdingfu
 * @CreateTime: 2021-07-02 11:23
 * @Description: 高品
 */
public class HighTechModel {

    private Integer id;

    private Integer companyId;

    private Integer startYear;

    private Integer endYear;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }
}
