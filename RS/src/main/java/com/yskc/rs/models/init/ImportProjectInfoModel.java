package com.yskc.rs.models.init;


import java.io.Serializable;

/**
 * @Author: hck
 * @DateTime: 2021/5/21 9:00
 * @Description:引入项目成员或设备
 */
public class ImportProjectInfoModel implements Serializable {

    private Integer projectId;

    private Integer sourceYear;//引入数据来源的年份

    private Integer targetYear;//引入数据保存的年份

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getSourceYear() {
        return sourceYear;
    }

    public void setSourceYear(Integer sourceYear) {
        this.sourceYear = sourceYear;
    }

    public Integer getTargetYear() {
        return targetYear;
    }

    public void setTargetYear(Integer targetYear) {
        this.targetYear = targetYear;
    }
}
