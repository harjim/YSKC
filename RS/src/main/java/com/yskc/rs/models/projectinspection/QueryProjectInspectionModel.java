package com.yskc.rs.models.projectinspection;

import com.yskc.common.utils.DateUtil;
import com.yskc.rs.models.params.PageParams;

import java.util.Date;


/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectinspection
 * @Author: zhangdingfu
 * @CreateTime: 2020-06-09 17:18
 * @Description: 查询项目检测修理等model
 */
public class QueryProjectInspectionModel extends PageParams {
    private Integer projectId;
    private String[] types;
    private Date accDate;
    private Date projectMonth;
    private String summary;
    private String deptName;
    private String ename;
    private String enumber;
    private String accNumber;
    private String remark;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

    public Date getAccDate() {
        return accDate != null ? DateUtil.getDayBegin(accDate) : null;
    }

    public void setAccDate(Date accDate) {
        this.accDate = accDate;
    }

    public Date getProjectMonth() {
        return projectMonth;
    }

    public void setProjectMonth(Date projectMonth) {
        this.projectMonth = projectMonth;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getEnumber() {
        return enumber;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
