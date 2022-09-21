package com.yskc.ms.chuanyun.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 批量查询业务数据
 * @author huronghua
 */
public class LoadBizObjectsRequest {
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
     * 过滤条件。默认返回前500条数据
     */
    @JsonProperty("Filter")
    private String filter;

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

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}
