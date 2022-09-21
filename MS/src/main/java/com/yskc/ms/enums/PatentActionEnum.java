package com.yskc.ms.enums;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.enums
 * @Author: blueToWhite
 * @CreateTime: 2020-07-01 10:58
 * @Description: 专利执行事件枚举
 */
public enum PatentActionEnum {

    /**
     * 获取验证
     */
    GET_VALID("getValid"),
    SET_VALID("setValid"),

    ;


    private String value;

    PatentActionEnum(String value) {
        this.value = value;
    }
}
