package com.xxl.job.executor.models.h3;

import com.fasterxml.jackson.annotation.JsonProperty;

public class H3Response<T> {
    /**
     * 返回结果是否成功true/false
     */
    @JsonProperty("Successful")
    private Boolean successful;
    /**
     * 错误信息
     */
    @JsonProperty("ErrorMessage")
    private String errorMessage;
    /**
     * 返回的数据
     */
    @JsonProperty("ReturnData")
    private T returnData;
    /**
     * 返回的数据类型，默认0
     */
    @JsonProperty("DataType")
    private Integer dataType;

    public Boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(Boolean successful) {
        this.successful = successful;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public T getReturnData() {
        return returnData;
    }

    public void setReturnData(T returnData) {
        this.returnData = returnData;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }
}
