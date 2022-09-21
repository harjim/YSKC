package com.yskc.ms.enums;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.enums
 * @Author: zhangdingfu
 * @CreateTime: 2020-01-02 09:46
 * @Description:
 */
public enum PatentStatusEnum {

    /**
     * 错误账号
     */
    WRONG_NAME("wrongName", 1),
    BLZ000("BLZ000", 1),
    /**
     * 超时
     */
    OVER_TIME("超时", 2),
    /**
     * 412重复访问
     */
    REPEAT("repeat", 3),
    /**
     * 成功
     */
    SUCCESS("success", 4),
    /**
     * 账号过时
     */
    TZ001("TZ001", 5);

    private String value;

    private Integer status;

    PatentStatusEnum(String value, Integer status) {
        this.value = value;
        this.status = status;
    }

    public String getValue() {
        return value;
    }

    public Integer getStatus() {
        return status;
    }
}
