package com.yskc.ms.enums;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.enums
 * @Author: blueToWhite
 * @CreateTime: 2021-04-23 14:23
 * @Description: rs研发类型表枚举
 */
public enum RsRdTypeTableEnum {
    /**
     * 人员
     */
    EMPLOYEE(1,"人员"),
    EQUIPMENT(2,"设备"),
    MATERIAL(3,"物料"),
    ENERGY(4,"设计"),
    DESIGN(5,"设计"),
    OTHER(6,"其他");
    private Integer type;

    private String name;

    RsRdTypeTableEnum(Integer type, String name) {
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
