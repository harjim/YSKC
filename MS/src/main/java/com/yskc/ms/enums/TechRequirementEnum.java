package com.yskc.ms.enums;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.enums
 * @Author: zhangdingfu
 * @CreateTime: 2020-06-15 15:58
 * @Description: 技术需求枚举
 */
public enum TechRequirementEnum {
    /**
     * 普通
     */
    NORMAL(0, "正常"),
    DONE(2, "已合作"),
    INVALID(1, "作废");

    private Integer status;
    private String name;

    TechRequirementEnum(int status, String name) {
        this.status = status;
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }
}
