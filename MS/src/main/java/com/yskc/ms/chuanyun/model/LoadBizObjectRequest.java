package com.yskc.ms.chuanyun.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 查询单条业务数据
 * @author huronghua
 */
public class LoadBizObjectRequest {
    /**
     * 调用的方法名
     */
    @JsonProperty("ActionName")
    private String actionName;
    /**
     * 表单编码
     */
    @JsonProperty("SchemaCode")
    private String schemaCode;
    /**
     * 表单ObjectId值
     */
    @JsonProperty("BizObjectId")
    private String bizObjectId;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getSchemaCode() {
        return schemaCode;
    }

    public void setSchemaCode(String schemaCode) {
        this.schemaCode = schemaCode;
    }

    public String getBizObjectId() {
        return bizObjectId;
    }

    public void setBizObjectId(String bizObjectId) {
        this.bizObjectId = bizObjectId;
    }
}
