package com.yskc.ms.models.projectAudit;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.projectAudit
 * @Author: zhangdingfu
 * @CreateTime: 2021-01-28 15:13
 * @Description: 文档审核状态
 */
public class DocFileAuditModel extends AuditStatusModel {
    private Integer docFileId;

    public Integer getDocFileId() {
        return docFileId;
    }

    public void setDocFileId(Integer docFileId) {
        this.docFileId = docFileId;
    }
}
