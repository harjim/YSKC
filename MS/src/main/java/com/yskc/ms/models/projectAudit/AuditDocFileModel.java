package com.yskc.ms.models.projectAudit;

import java.io.Serializable;

/**
 * @Author: hck
 * @DateTime: 2021/3/26 15:31
 * @Description:审核文档统计
 */
public class AuditDocFileModel implements Serializable {

    private Integer rsProjectId;

    private Integer auditDocNum;

    public Integer getRsProjectId() {
        return rsProjectId;
    }

    public void setRsProjectId(Integer rsProjectId) {
        this.rsProjectId = rsProjectId;
    }

    public Integer getAuditDocNum() {
        return auditDocNum;
    }

    public void setAuditDocNum(Integer auditDocNum) {
        this.auditDocNum = auditDocNum;
    }
}
