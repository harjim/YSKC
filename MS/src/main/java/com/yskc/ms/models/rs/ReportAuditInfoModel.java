package com.yskc.ms.models.rs;

import java.io.Serializable;

/**
 * @Author: hck
 * @DateTime: 2021/4/14 14:44
 * @Description:查新报告项目审核状态
 */
public class ReportAuditInfoModel implements Serializable {

    private Integer rsProjectId;

    private Integer status;

    private Integer userId;

    public Integer getRsProjectId() {
        return rsProjectId;
    }

    public void setRsProjectId(Integer rsProjectId) {
        this.rsProjectId = rsProjectId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
