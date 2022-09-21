package com.yskc.ms.enums;

/**
 * @DateTime: 2021/11/29 17:40
 * @Description:技术需求状态枚举
 * @author: hsx
 */
public enum RequirementStatus {

    /**
     * 技术需求状态枚举
     */
    DRAFT(0, "草稿"),DOING(1,"发布"),IN_SERVICE(2,"服务中"),
    ACHIEVE(3,"完成"), STOP(4, "终止"),PASS_DUE(5,"过期");

    private Integer code;

    private String value;

    RequirementStatus(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
