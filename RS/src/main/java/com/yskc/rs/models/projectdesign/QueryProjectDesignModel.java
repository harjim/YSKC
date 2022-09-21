package com.yskc.rs.models.projectdesign;

import com.yskc.rs.models.params.PageParams;

import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectdesign
 * @Author: zhangdingfu
 * @CreateTime: 2020-06-09 10:51
 * @Description: 研发费用查询model
 */
public class QueryProjectDesignModel extends PageParams {
    private Integer projectId;
    private String name;
    private Date projectMonth;
    private String deptName;
    private String remark;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getProjectMonth() {
        return projectMonth;
    }

    public void setProjectMonth(Date projectMonth) {
        this.projectMonth = projectMonth;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
