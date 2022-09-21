package com.yskc.ms.models.solution;

import com.yskc.ms.models.params.PageParams;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.solution
 * @Author: zhangdingfu
 * @CreateTime: 2021-07-09 10:28
 * @Description: 查询方案model
 */
public class QuerySolutionModel extends PageParams {

    private String title;
    private String customer;
    private String serveProject;
    private String industry;
    private Integer type;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getServeProject() {
        return serveProject;
    }

    public void setServeProject(String serveProject) {
        this.serveProject = serveProject;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
