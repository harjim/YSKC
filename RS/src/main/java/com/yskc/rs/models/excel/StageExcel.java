package com.yskc.rs.models.excel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.common.annotation.Excel;

import java.io.Serializable;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.excel
 * @Author: wangxing
 * @CreateTime: 2019-12-03 10:49
 * @Description:
 */
public class StageExcel implements Serializable {
    @Excel(name = "项目阶段", order = 0, fieldName = "stageName")
    private String stageName;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "开始日期", order = 1, fieldName = "beginDate", dateFormat = "yyyy-MM-dd")
    private Date beginDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "结束日期", order = 2, fieldName = "endDate", dateFormat = "yyyy-MM-dd")
    private Date endDate;
    @Excel(name = "备注", order = 3, fieldName = "remark")
    private String remark;
    @Excel(name = "工作内容", order = 4, fieldName = "workDesc")
    private String workDesc;

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWorkDesc() {
        return workDesc;
    }

    public void setWorkDesc(String workDesc) {
        this.workDesc = workDesc;
    }
}
