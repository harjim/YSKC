package com.yskc.rs.models.rddept;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.rddept
 * @Author: zhangdingfu
 * @CreateTime: 2020-03-13 09:39
 * @Description: 克隆研发部门modal
 */
public class CloneRdDeptModal {

    private Integer parentId;

    private Integer cloneId;

    private String parentIdentity;

    private String cloneIdentity;

    private Integer parentLevel;

    private Integer cloneLevel;

    private Integer currentYear;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getCloneId() {
        return cloneId;
    }

    public void setCloneId(Integer cloneId) {
        this.cloneId = cloneId;
    }

    public String getParentIdentity() {
        return parentIdentity;
    }

    public void setParentIdentity(String parentIdentity) {
        this.parentIdentity = parentIdentity;
    }

    public String getCloneIdentity() {
        return cloneIdentity;
    }

    public void setCloneIdentity(String cloneIdentity) {
        this.cloneIdentity = cloneIdentity;
    }


    public Integer getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(Integer currentYear) {
        this.currentYear = currentYear;
    }

    public Integer getParentLevel() {
        return parentLevel;
    }

    public void setParentLevel(Integer parentLevel) {
        this.parentLevel = parentLevel;
    }

    public Integer getCloneLevel() {
        return cloneLevel;
    }

    public void setCloneLevel(Integer cloneLevel) {
        this.cloneLevel = cloneLevel;
    }
}
