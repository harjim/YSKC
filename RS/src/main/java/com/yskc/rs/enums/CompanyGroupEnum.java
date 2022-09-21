package com.yskc.rs.enums;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.enums
 * @Author: zhangdingfu
 * @CreateTime: 2022-01-11 08:08
 * @Description: 公司群组枚举
 */
public enum CompanyGroupEnum {

    /**
     * 默认
     */
    DEFAULT(2, "默认"),
    GROUP(1, "集团"),
    CHILD_GROUP(3, "子集团");

    private Integer type;

    private String name;

    public static boolean isGroup(Integer type) {
        return GROUP.type.equals(type) || CHILD_GROUP.type.equals(type);
    }

    CompanyGroupEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
