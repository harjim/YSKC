package com.yskc.ms.models.serviceApply;

import com.yskc.ms.models.FormAuditModel;
import com.yskc.ms.models.projectAudit.ProjectAuditModel;

/**
 * @program: ms
 * @description:
 * @author: wjy
 * @create: 2022-08-26 14:23
 **/
public class QueryEditModel {
    private FormAuditModel model;
    private ApplyEditModel applyModel;

    public FormAuditModel getModel() {
        return model;
    }

    public void setModel(FormAuditModel model) {
        this.model = model;
    }

    public ApplyEditModel getApplyModel() {
        return applyModel;
    }

    public void setApplyModel(ApplyEditModel applyModel) {
        this.applyModel = applyModel;
    }
}
