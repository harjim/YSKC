package com.yskc.docservice.enums;

/**
 * 用户状态
 * @author huronghua
 */
public enum UserStatusEnum {
    /**
     * 禁用
     */
    DISABLED(1, "禁用"),
    /**
     * 启用
     */
    ENABLED(0, "启用");
    private int type;
    private String value;

    UserStatusEnum(int type, String value) {
        this.type = type;
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
